/*
 * LaunchDarkly REST API
 * This documentation describes LaunchDarkly's REST API.  To access the complete OpenAPI spec directly, use [Get OpenAPI spec](https://launchdarkly.com/docs/api/other/get-openapi-spec).  ## Authentication  LaunchDarkly's REST API uses the HTTPS protocol with a minimum TLS version of 1.2.  All REST API resources are authenticated with either [personal or service access tokens](https://launchdarkly.com/docs/home/account/api), or session cookies. Other authentication mechanisms are not supported. You can manage personal access tokens on your [**Authorization**](https://app.launchdarkly.com/settings/authorization) page in the LaunchDarkly UI.  LaunchDarkly also has SDK keys, mobile keys, and client-side IDs that are used by our server-side SDKs, mobile SDKs, and JavaScript-based SDKs, respectively. **These keys cannot be used to access our REST API**. These keys are environment-specific, and can only perform read-only operations such as fetching feature flag settings.  | Auth mechanism                                                                                  | Allowed resources                                                                                     | Use cases                                          | | ----------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------- | -------------------------------------------------- | | [Personal or service access tokens](https://launchdarkly.com/docs/home/account/api) | Can be customized on a per-token basis                                                                | Building scripts, custom integrations, data export. | | SDK keys                                                                                        | Can only access read-only resources specific to server-side SDKs. Restricted to a single environment. | Server-side SDKs                     | | Mobile keys                                                                                     | Can only access read-only resources specific to mobile SDKs, and only for flags marked available to mobile keys. Restricted to a single environment.           | Mobile SDKs                                        | | Client-side ID                                                                                  | Can only access read-only resources specific to JavaScript-based client-side SDKs, and only for flags marked available to client-side. Restricted to a single environment.           | Client-side JavaScript                             |  > #### Keep your access tokens and SDK keys private > > Access tokens should _never_ be exposed in untrusted contexts. Never put an access token in client-side JavaScript, or embed it in a mobile application. LaunchDarkly has special mobile keys that you can embed in mobile apps. If you accidentally expose an access token or SDK key, you can reset it from your [**Authorization**](https://app.launchdarkly.com/settings/authorization) page. > > The client-side ID is safe to embed in untrusted contexts. It's designed for use in client-side JavaScript.  ### Authentication using request header  The preferred way to authenticate with the API is by adding an `Authorization` header containing your access token to your requests. The value of the `Authorization` header must be your access token.  Manage personal access tokens from the [**Authorization**](https://app.launchdarkly.com/settings/authorization) page.  ### Authentication using session cookie  For testing purposes, you can make API calls directly from your web browser. If you are logged in to the LaunchDarkly application, the API will use your existing session to authenticate calls.  If you have a [role](https://launchdarkly.com/docs/home/account/built-in-roles) other than Admin, or have a [custom role](https://launchdarkly.com/docs/home/account/custom-roles) defined, you may not have permission to perform some API calls. You will receive a `401` response code in that case.  > ### Modifying the Origin header causes an error > > LaunchDarkly validates that the Origin header for any API request authenticated by a session cookie matches the expected Origin header. The expected Origin header is `https://app.launchdarkly.com`. > > If the Origin header does not match what's expected, LaunchDarkly returns an error. This error can prevent the LaunchDarkly app from working correctly. > > Any browser extension that intentionally changes the Origin header can cause this problem. For example, the `Allow-Control-Allow-Origin: *` Chrome extension changes the Origin header to `http://evil.com` and causes the app to fail. > > To prevent this error, do not modify your Origin header. > > LaunchDarkly does not require origin matching when authenticating with an access token, so this issue does not affect normal API usage.  ## Representations  All resources expect and return JSON response bodies. Error responses also send a JSON body. To learn more about the error format of the API, read [Errors](https://launchdarkly.com/docs/api#errors).  In practice this means that you always get a response with a `Content-Type` header set to `application/json`.  In addition, request bodies for `PATCH`, `POST`, and `PUT` requests must be encoded as JSON with a `Content-Type` header set to `application/json`.  ### Summary and detailed representations  When you fetch a list of resources, the response includes only the most important attributes of each resource. This is a _summary representation_ of the resource. When you fetch an individual resource, such as a single feature flag, you receive a _detailed representation_ of the resource.  The best way to find a detailed representation is to follow links. Every summary representation includes a link to its detailed representation.  ### Expanding responses  Sometimes the detailed representation of a resource does not include all of the attributes of the resource by default. If this is the case, the request method will clearly document this and describe which attributes you can include in an expanded response.  To include the additional attributes, append the `expand` request parameter to your request and add a comma-separated list of the attributes to include. For example, when you append `?expand=members,maintainers` to the [Get team](https://launchdarkly.com/docs/api/teams/get-team) endpoint, the expanded response includes both of these attributes.  ### Links and addressability  The best way to navigate the API is by following links. These are attributes in representations that link to other resources. The API always uses the same format for links:  - Links to other resources within the API are encapsulated in a `_links` object - If the resource has a corresponding link to HTML content on the site, it is stored in a special `_site` link  Each link has two attributes:  - An `href`, which contains the URL - A `type`, which describes the content type  For example, a feature resource might return the following:  ```json {   \"_links\": {     \"parent\": {       \"href\": \"/api/features\",       \"type\": \"application/json\"     },     \"self\": {       \"href\": \"/api/features/sort.order\",       \"type\": \"application/json\"     }   },   \"_site\": {     \"href\": \"/features/sort.order\",     \"type\": \"text/html\"   } } ```  From this, you can navigate to the parent collection of features by following the `parent` link, or navigate to the site page for the feature by following the `_site` link.  Collections are always represented as a JSON object with an `items` attribute containing an array of representations. Like all other representations, collections have `_links` defined at the top level.  Paginated collections include `first`, `last`, `next`, and `prev` links containing a URL with the respective set of elements in the collection.  ## Updates  Resources that accept partial updates use the `PATCH` verb. Most resources support the [JSON patch](https://launchdarkly.com/docs/api#updates-using-json-patch) format. Some resources also support the [JSON merge patch](https://launchdarkly.com/docs/api#updates-using-json-merge-patch) format, and some resources support the [semantic patch](https://launchdarkly.com/docs/api#updates-using-semantic-patch) format, which is a way to specify the modifications to perform as a set of executable instructions. Each resource supports optional [comments](https://launchdarkly.com/docs/api#updates-with-comments) that you can submit with updates. Comments appear in outgoing webhooks, the audit log, and other integrations.  When a resource supports both JSON patch and semantic patch, we document both in the request method. However, the specific request body fields and descriptions included in our documentation only match one type of patch or the other.  ### Updates using JSON patch  [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) is a way to specify the modifications to perform on a resource. JSON patch uses paths and a limited set of operations to describe how to transform the current state of the resource into a new state. JSON patch documents are always arrays, where each element contains an operation, a path to the field to update, and the new value.  For example, in this feature flag representation:  ```json {     \"name\": \"New recommendations engine\",     \"key\": \"engine.enable\",     \"description\": \"This is the description\",     ... } ``` You can change the feature flag's description with the following patch document:  ```json [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"This is the new description\" }] ```  You can specify multiple modifications to perform in a single request. You can also test that certain preconditions are met before applying the patch:  ```json [   { \"op\": \"test\", \"path\": \"/version\", \"value\": 10 },   { \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" } ] ```  The above patch request tests whether the feature flag's `version` is `10`, and if so, changes the feature flag's description.  Attributes that are not editable, such as a resource's `_links`, have names that start with an underscore.  ### Updates using JSON merge patch  [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) is another format for specifying the modifications to perform on a resource. JSON merge patch is less expressive than JSON patch. However, in many cases it is simpler to construct a merge patch document. For example, you can change a feature flag's description with the following merge patch document:  ```json {   \"description\": \"New flag description\" } ```  ### Updates using semantic patch  Some resources support the semantic patch format. A semantic patch is a way to specify the modifications to perform on a resource as a set of executable instructions.  Semantic patch allows you to be explicit about intent using precise, custom instructions. In many cases, you can define semantic patch instructions independently of the current state of the resource. This can be useful when defining a change that may be applied at a future date.  To make a semantic patch request, you must append `domain-model=launchdarkly.semanticpatch` to your `Content-Type` header.  Here's how:  ``` Content-Type: application/json; domain-model=launchdarkly.semanticpatch ```  If you call a semantic patch resource without this header, you will receive a `400` response because your semantic patch will be interpreted as a JSON patch.  The body of a semantic patch request takes the following properties:  * `comment` (string): (Optional) A description of the update. * `environmentKey` (string): (Required for some resources only) The environment key. * `instructions` (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a `kind` property that indicates the instruction. If the instruction requires parameters, you must include those parameters as additional fields in the object. The documentation for each resource that supports semantic patch includes the available instructions and any additional parameters.  For example:  ```json {   \"comment\": \"optional comment\",   \"instructions\": [ {\"kind\": \"turnFlagOn\"} ] } ```  Semantic patches are not applied partially; either all of the instructions are applied or none of them are. If **any** instruction is invalid, the endpoint returns an error and will not change the resource. If all instructions are valid, the request succeeds and the resources are updated if necessary, or left unchanged if they are already in the state you request.  ### Updates with comments  You can submit optional comments with `PATCH` changes.  To submit a comment along with a JSON patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"patch\": [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" }] } ```  To submit a comment along with a JSON merge patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"merge\": { \"description\": \"New flag description\" } } ```  To submit a comment along with a semantic patch, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"instructions\": [ {\"kind\": \"turnFlagOn\"} ] } ```  ## Errors  The API always returns errors in a common format. Here's an example:  ```json {   \"code\": \"invalid_request\",   \"message\": \"A feature with that key already exists\",   \"id\": \"30ce6058-87da-11e4-b116-123b93f75cba\" } ```  The `code` indicates the general class of error. The `message` is a human-readable explanation of what went wrong. The `id` is a unique identifier. Use it when you're working with LaunchDarkly Support to debug a problem with a specific API call.  ### HTTP status error response codes  | Code | Definition        | Description                                                                                       | Possible Solution                                                | | ---- | ----------------- | ------------------------------------------------------------------------------------------- | ---------------------------------------------------------------- | | 400  | Invalid request       | The request cannot be understood.                                    | Ensure JSON syntax in request body is correct.                   | | 401  | Invalid access token      | Requestor is unauthorized or does not have permission for this API call.                                                | Ensure your API access token is valid and has the appropriate permissions.                                     | | 403  | Forbidden         | Requestor does not have access to this resource.                                                | Ensure that the account member or access token has proper permissions set. | | 404  | Invalid resource identifier | The requested resource is not valid. | Ensure that the resource is correctly identified by ID or key. | | 405  | Method not allowed | The request method is not allowed on this resource. | Ensure that the HTTP verb is correct. | | 409  | Conflict          | The API request can not be completed because it conflicts with a concurrent API request. | Retry your request.                                              | | 422  | Unprocessable entity | The API request can not be completed because the update description can not be understood. | Ensure that the request body is correct for the type of patch you are using, either JSON patch or semantic patch. | 429  | Too many requests | Read [Rate limiting](https://launchdarkly.com/docs/api#rate-limiting).                                               | Wait and try again later.                                        |  ## CORS  The LaunchDarkly API supports Cross Origin Resource Sharing (CORS) for AJAX requests from any origin. If an `Origin` header is given in a request, it will be echoed as an explicitly allowed origin. Otherwise the request returns a wildcard, `Access-Control-Allow-Origin: *`. For more information on CORS, read the [CORS W3C Recommendation](http://www.w3.org/TR/cors). Example CORS headers might look like:  ```http Access-Control-Allow-Headers: Accept, Content-Type, Content-Length, Accept-Encoding, Authorization Access-Control-Allow-Methods: OPTIONS, GET, DELETE, PATCH Access-Control-Allow-Origin: * Access-Control-Max-Age: 300 ```  You can make authenticated CORS calls just as you would make same-origin calls, using either [token or session-based authentication](https://launchdarkly.com/docs/api#authentication). If you are using session authentication, you should set the `withCredentials` property for your `xhr` request to `true`. You should never expose your access tokens to untrusted entities.  ## Rate limiting  We use several rate limiting strategies to ensure the availability of our APIs. Rate-limited calls to our APIs return a `429` status code. Calls to our APIs include headers indicating the current rate limit status. The specific headers returned depend on the API route being called. The limits differ based on the route, authentication mechanism, and other factors. Routes that are not rate limited may not contain any of the headers described below.  > ### Rate limiting and SDKs > > LaunchDarkly SDKs are never rate limited and do not use the API endpoints defined here. LaunchDarkly uses a different set of approaches, including streaming/server-sent events and a global CDN, to ensure availability to the routes used by LaunchDarkly SDKs.  ### Global rate limits  Authenticated requests are subject to a global limit. This is the maximum number of calls that your account can make to the API per ten seconds. All service and personal access tokens on the account share this limit, so exceeding the limit with one access token will impact other tokens. Calls that are subject to global rate limits may return the headers below:  | Header name                    | Description                                                                      | | ------------------------------ | -------------------------------------------------------------------------------- | | `X-Ratelimit-Global-Remaining` | The maximum number of requests the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`            | The time at which the current rate limit window resets in epoch milliseconds.    |  We do not publicly document the specific number of calls that can be made globally. This limit may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limit.  ### Route-level rate limits  Some authenticated routes have custom rate limits. These also reset every ten seconds. Any service or personal access tokens hitting the same route share this limit, so exceeding the limit with one access token may impact other tokens. Calls that are subject to route-level rate limits return the headers below:  | Header name                   | Description                                                                                           | | ----------------------------- | ----------------------------------------------------------------------------------------------------- | | `X-Ratelimit-Route-Remaining` | The maximum number of requests to the current route the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`           | The time at which the current rate limit window resets in epoch milliseconds.                         |  A _route_ represents a specific URL pattern and verb. For example, the [Delete environment](https://launchdarkly.com/docs/api/environments/delete-environment) endpoint is considered a single route, and each call to delete an environment counts against your route-level rate limit for that route.  We do not publicly document the specific number of calls that an account can make to each endpoint per ten seconds. These limits may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limits.  ### IP-based rate limiting  We also employ IP-based rate limiting on some API routes. If you hit an IP-based rate limit, your API response will include a `Retry-After` header indicating how long to wait before re-trying the call. Clients must wait at least `Retry-After` seconds before making additional calls to our API, and should employ jitter and backoff strategies to avoid triggering rate limits again.  ## OpenAPI (Swagger) and client libraries  We have a [complete OpenAPI (Swagger) specification](https://app.launchdarkly.com/api/v2/openapi.json) for our API.  We auto-generate multiple client libraries based on our OpenAPI specification. To learn more, visit the [collection of client libraries on GitHub](https://github.com/search?q=topic%3Alaunchdarkly-api+org%3Alaunchdarkly&type=Repositories). You can also use this specification to generate client libraries to interact with our REST API in your language of choice.  Our OpenAPI specification is supported by several API-based tools such as Postman and Insomnia. In many cases, you can directly import our specification to explore our APIs.  ## Method overriding  Some firewalls and HTTP clients restrict the use of verbs other than `GET` and `POST`. In those environments, our API endpoints that use `DELETE`, `PATCH`, and `PUT` verbs are inaccessible.  To avoid this issue, our API supports the `X-HTTP-Method-Override` header, allowing clients to \"tunnel\" `DELETE`, `PATCH`, and `PUT` requests using a `POST` request.  For example, to call a `PATCH` endpoint using a `POST` request, you can include `X-HTTP-Method-Override:PATCH` as a header.  ## Beta resources  We sometimes release new API resources in **beta** status before we release them with general availability.  Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  We try to promote resources into general availability as quickly as possible. This happens after sufficient testing and when we're satisfied that we no longer need to make backwards-incompatible changes.  We mark beta resources with a \"Beta\" callout in our documentation, pictured below:  > ### This feature is in beta > > To use this feature, pass in a header including the `LD-API-Version` key with value set to `beta`. Use this header with each call. To learn more, read [Beta resources](https://launchdarkly.com/docs/api#beta-resources). > > Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  ### Using beta resources  To use a beta resource, you must include a header in the request. If you call a beta resource without this header, you receive a `403` response.  Use this header:  ``` LD-API-Version: beta ```  ## Federal environments  The version of LaunchDarkly that is available on domains controlled by the United States government is different from the version of LaunchDarkly available to the general public. If you are an employee or contractor for a United States federal agency and use LaunchDarkly in your work, you likely use the federal instance of LaunchDarkly.  If you are working in the federal instance of LaunchDarkly, the base URI for each request is `https://app.launchdarkly.us`.  To learn more, read [LaunchDarkly in federal environments](https://launchdarkly.com/docs/home/infrastructure/federal).  ## Versioning  We try hard to keep our REST API backwards compatible, but we occasionally have to make backwards-incompatible changes in the process of shipping new features. These breaking changes can cause unexpected behavior if you don't prepare for them accordingly.  Updates to our REST API include support for the latest features in LaunchDarkly. We also release a new version of our REST API every time we make a breaking change. We provide simultaneous support for multiple API versions so you can migrate from your current API version to a new version at your own pace.  ### Setting the API version per request  You can set the API version on a specific request by sending an `LD-API-Version` header, as shown in the example below:  ``` LD-API-Version: 20240415 ```  The header value is the version number of the API version you would like to request. The number for each version corresponds to the date the version was released in `yyyymmdd` format. In the example above the version `20240415` corresponds to April 15, 2024.  ### Setting the API version per access token  When you create an access token, you must specify a specific version of the API to use. This ensures that integrations using this token cannot be broken by version changes.  Tokens created before versioning was released have their version set to `20160426`, which is the version of the API that existed before the current versioning scheme, so that they continue working the same way they did before versioning.  If you would like to upgrade your integration to use a new API version, you can explicitly set the header described above.  > ### Best practice: Set the header for every client or integration > > We recommend that you set the API version header explicitly in any client or integration you build. > > Only rely on the access token API version during manual testing.  ### API version changelog  <table>   <tr>     <th>Version</th>     <th>Changes</th>     <th>End of life (EOL)</th>   </tr>   <tr>     <td>`20240415`</td>     <td>       <ul><li>Changed several endpoints from unpaginated to paginated. Use the `limit` and `offset` query parameters to page through the results.</li> <li>Changed the [list access tokens](https://launchdarkly.com/docs/api/access-tokens/get-tokens) endpoint: <ul><li>Response is now paginated with a default limit of `25`</li></ul></li> <li>Changed the [list account members](https://launchdarkly.com/docs/api/account-members/get-members) endpoint: <ul><li>The `accessCheck` filter is no longer available</li></ul></li> <li>Changed the [list custom roles](https://launchdarkly.com/docs/api/custom-roles/get-custom-roles) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li></ul></li> <li>Changed the [list feature flags](https://launchdarkly.com/docs/api/feature-flags/get-feature-flags) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li><li>The `environments` field is now only returned if the request is filtered by environment, using the `filterEnv` query parameter</li><li>The `followerId`, `hasDataExport`, `status`, `contextKindTargeted`, and `segmentTargeted` filters are no longer available</li><li>The `compare` query parameter is no longer available</li></ul></li> <li>Changed the [list segments](https://launchdarkly.com/docs/api/segments/get-segments) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li></ul></li> <li>Changed the [list teams](https://launchdarkly.com/docs/api/teams/get-teams) endpoint: <ul><li>The `expand` parameter no longer supports including `projects` or `roles`</li><li>In paginated results, the maximum page size is now 100</li></ul></li> <li>Changed the [get workflows](https://launchdarkly.com/docs/api/workflows/get-workflows) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li><li>The `_conflicts` field in the response is no longer available</li></ul></li> </ul>     </td>     <td>Current</td>   </tr>   <tr>     <td>`20220603`</td>     <td>       <ul><li>Changed the [list projects](https://launchdarkly.com/docs/api/projects/get-projects) return value:<ul><li>Response is now paginated with a default limit of `20`.</li><li>Added support for filter and sort.</li><li>The project `environments` field is now expandable. This field is omitted by default.</li></ul></li><li>Changed the [get project](https://launchdarkly.com/docs/api/projects/get-project) return value:<ul><li>The `environments` field is now expandable. This field is omitted by default.</li></ul></li></ul>     </td>     <td>2025-04-15</td>   </tr>   <tr>     <td>`20210729`</td>     <td>       <ul><li>Changed the [create approval request](https://launchdarkly.com/docs/api/approvals/post-approval-request) return value. It now returns HTTP Status Code `201` instead of `200`.</li><li> Changed the [get user](https://launchdarkly.com/docs/api/users/get-user) return value. It now returns a user record, not a user. </li><li>Added additional optional fields to environment, segments, flags, members, and segments, including the ability to create big segments. </li><li> Added default values for flag variations when new environments are created. </li><li>Added filtering and pagination for getting flags and members, including `limit`, `number`, `filter`, and `sort` query parameters. </li><li>Added endpoints for expiring user targets for flags and segments, scheduled changes, access tokens, Relay Proxy configuration, integrations and subscriptions, and approvals. </li></ul>     </td>     <td>2023-06-03</td>   </tr>   <tr>     <td>`20191212`</td>     <td>       <ul><li>[List feature flags](https://launchdarkly.com/docs/api/feature-flags/get-feature-flags) now defaults to sending summaries of feature flag configurations, equivalent to setting the query parameter `summary=true`. Summaries omit flag targeting rules and individual user targets from the payload. </li><li> Added endpoints for flags, flag status, projects, environments, audit logs, members, users, custom roles, segments, usage, streams, events, and data export. </li></ul>     </td>     <td>2022-07-29</td>   </tr>   <tr>     <td>`20160426`</td>     <td>       <ul><li>Initial versioning of API. Tokens created before versioning have their version set to this.</li></ul>     </td>     <td>2020-12-12</td>   </tr> </table>  To learn more about how EOL is determined, read LaunchDarkly's [End of Life (EOL) Policy](https://launchdarkly.com/policies/end-of-life-policy/). 
 *
 * The version of the OpenAPI document: 2.0
 * Contact: support@launchdarkly.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.launchdarkly.api.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.launchdarkly.api.model.Access;
import com.launchdarkly.api.model.DependentExperimentRep;
import com.launchdarkly.api.model.DependentMetricGroupRep;
import com.launchdarkly.api.model.FlagListingRep;
import com.launchdarkly.api.model.Link;
import com.launchdarkly.api.model.MemberSummary;
import com.launchdarkly.api.model.MetricEventDefaultRep;
import com.launchdarkly.api.model.Modification;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.launchdarkly.api.JSON;

/**
 * MetricRep
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-04-04T21:33:11.424117Z[Etc/UTC]")
public class MetricRep {
  public static final String SERIALIZED_NAME_EXPERIMENT_COUNT = "experimentCount";
  @SerializedName(SERIALIZED_NAME_EXPERIMENT_COUNT)
  private Integer experimentCount;

  public static final String SERIALIZED_NAME_METRIC_GROUP_COUNT = "metricGroupCount";
  @SerializedName(SERIALIZED_NAME_METRIC_GROUP_COUNT)
  private Integer metricGroupCount;

  public static final String SERIALIZED_NAME_ID = "_id";
  @SerializedName(SERIALIZED_NAME_ID)
  private String id;

  public static final String SERIALIZED_NAME_VERSION_ID = "_versionId";
  @SerializedName(SERIALIZED_NAME_VERSION_ID)
  private String versionId;

  public static final String SERIALIZED_NAME_KEY = "key";
  @SerializedName(SERIALIZED_NAME_KEY)
  private String key;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  /**
   * The kind of event the metric tracks
   */
  @JsonAdapter(KindEnum.Adapter.class)
  public enum KindEnum {
    PAGEVIEW("pageview"),
    
    CLICK("click"),
    
    CUSTOM("custom");

    private String value;

    KindEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static KindEnum fromValue(String value) {
      for (KindEnum b : KindEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<KindEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final KindEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public KindEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return KindEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_KIND = "kind";
  @SerializedName(SERIALIZED_NAME_KIND)
  private KindEnum kind;

  public static final String SERIALIZED_NAME_ATTACHED_FLAG_COUNT = "_attachedFlagCount";
  @SerializedName(SERIALIZED_NAME_ATTACHED_FLAG_COUNT)
  private Integer attachedFlagCount;

  public static final String SERIALIZED_NAME_LINKS = "_links";
  @SerializedName(SERIALIZED_NAME_LINKS)
  private Map<String, Link> links = new HashMap<>();

  public static final String SERIALIZED_NAME_SITE = "_site";
  @SerializedName(SERIALIZED_NAME_SITE)
  private Link site;

  public static final String SERIALIZED_NAME_ACCESS = "_access";
  @SerializedName(SERIALIZED_NAME_ACCESS)
  private Access access;

  public static final String SERIALIZED_NAME_TAGS = "tags";
  @SerializedName(SERIALIZED_NAME_TAGS)
  private List<String> tags = new ArrayList<>();

  public static final String SERIALIZED_NAME_CREATION_DATE = "_creationDate";
  @SerializedName(SERIALIZED_NAME_CREATION_DATE)
  private Long creationDate;

  public static final String SERIALIZED_NAME_LAST_MODIFIED = "lastModified";
  @SerializedName(SERIALIZED_NAME_LAST_MODIFIED)
  private Modification lastModified;

  public static final String SERIALIZED_NAME_MAINTAINER_ID = "maintainerId";
  @SerializedName(SERIALIZED_NAME_MAINTAINER_ID)
  private String maintainerId;

  public static final String SERIALIZED_NAME_MAINTAINER = "_maintainer";
  @SerializedName(SERIALIZED_NAME_MAINTAINER)
  private MemberSummary maintainer;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_CATEGORY = "category";
  @SerializedName(SERIALIZED_NAME_CATEGORY)
  private String category;

  public static final String SERIALIZED_NAME_IS_NUMERIC = "isNumeric";
  @SerializedName(SERIALIZED_NAME_IS_NUMERIC)
  private Boolean isNumeric;

  /**
   * For custom metrics, the success criteria
   */
  @JsonAdapter(SuccessCriteriaEnum.Adapter.class)
  public enum SuccessCriteriaEnum {
    HIGHERTHANBASELINE("HigherThanBaseline"),
    
    LOWERTHANBASELINE("LowerThanBaseline");

    private String value;

    SuccessCriteriaEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static SuccessCriteriaEnum fromValue(String value) {
      for (SuccessCriteriaEnum b : SuccessCriteriaEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<SuccessCriteriaEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final SuccessCriteriaEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public SuccessCriteriaEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return SuccessCriteriaEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_SUCCESS_CRITERIA = "successCriteria";
  @SerializedName(SERIALIZED_NAME_SUCCESS_CRITERIA)
  private SuccessCriteriaEnum successCriteria;

  public static final String SERIALIZED_NAME_UNIT = "unit";
  @SerializedName(SERIALIZED_NAME_UNIT)
  private String unit;

  public static final String SERIALIZED_NAME_EVENT_KEY = "eventKey";
  @SerializedName(SERIALIZED_NAME_EVENT_KEY)
  private String eventKey;

  public static final String SERIALIZED_NAME_RANDOMIZATION_UNITS = "randomizationUnits";
  @SerializedName(SERIALIZED_NAME_RANDOMIZATION_UNITS)
  private List<String> randomizationUnits = null;

  /**
   * The method by which multiple unit event values are aggregated
   */
  @JsonAdapter(UnitAggregationTypeEnum.Adapter.class)
  public enum UnitAggregationTypeEnum {
    AVERAGE("average"),
    
    SUM("sum");

    private String value;

    UnitAggregationTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static UnitAggregationTypeEnum fromValue(String value) {
      for (UnitAggregationTypeEnum b : UnitAggregationTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<UnitAggregationTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final UnitAggregationTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public UnitAggregationTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return UnitAggregationTypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_UNIT_AGGREGATION_TYPE = "unitAggregationType";
  @SerializedName(SERIALIZED_NAME_UNIT_AGGREGATION_TYPE)
  private UnitAggregationTypeEnum unitAggregationType;

  /**
   * The method for analyzing metric events
   */
  @JsonAdapter(AnalysisTypeEnum.Adapter.class)
  public enum AnalysisTypeEnum {
    MEAN("mean"),
    
    PERCENTILE("percentile");

    private String value;

    AnalysisTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static AnalysisTypeEnum fromValue(String value) {
      for (AnalysisTypeEnum b : AnalysisTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<AnalysisTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final AnalysisTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public AnalysisTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return AnalysisTypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_ANALYSIS_TYPE = "analysisType";
  @SerializedName(SERIALIZED_NAME_ANALYSIS_TYPE)
  private AnalysisTypeEnum analysisType;

  public static final String SERIALIZED_NAME_PERCENTILE_VALUE = "percentileValue";
  @SerializedName(SERIALIZED_NAME_PERCENTILE_VALUE)
  private Integer percentileValue;

  public static final String SERIALIZED_NAME_EVENT_DEFAULT = "eventDefault";
  @SerializedName(SERIALIZED_NAME_EVENT_DEFAULT)
  private MetricEventDefaultRep eventDefault;

  public static final String SERIALIZED_NAME_EXPERIMENTS = "experiments";
  @SerializedName(SERIALIZED_NAME_EXPERIMENTS)
  private List<DependentExperimentRep> experiments = null;

  public static final String SERIALIZED_NAME_METRIC_GROUPS = "metricGroups";
  @SerializedName(SERIALIZED_NAME_METRIC_GROUPS)
  private List<DependentMetricGroupRep> metricGroups = null;

  public static final String SERIALIZED_NAME_IS_ACTIVE = "isActive";
  @SerializedName(SERIALIZED_NAME_IS_ACTIVE)
  private Boolean isActive;

  public static final String SERIALIZED_NAME_ATTACHED_FEATURES = "_attachedFeatures";
  @SerializedName(SERIALIZED_NAME_ATTACHED_FEATURES)
  private List<FlagListingRep> attachedFeatures = null;

  public static final String SERIALIZED_NAME_VERSION = "_version";
  @SerializedName(SERIALIZED_NAME_VERSION)
  private Integer version;

  public static final String SERIALIZED_NAME_SELECTOR = "selector";
  @SerializedName(SERIALIZED_NAME_SELECTOR)
  private String selector;

  public static final String SERIALIZED_NAME_URLS = "urls";
  @SerializedName(SERIALIZED_NAME_URLS)
  private List<Map<String, Object>> urls = null;

  public MetricRep() { 
  }

  public MetricRep experimentCount(Integer experimentCount) {
    
    this.experimentCount = experimentCount;
    return this;
  }

   /**
   * The number of experiments using this metric
   * @return experimentCount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "0", value = "The number of experiments using this metric")

  public Integer getExperimentCount() {
    return experimentCount;
  }


  public void setExperimentCount(Integer experimentCount) {
    this.experimentCount = experimentCount;
  }


  public MetricRep metricGroupCount(Integer metricGroupCount) {
    
    this.metricGroupCount = metricGroupCount;
    return this;
  }

   /**
   * The number of metric groups using this metric
   * @return metricGroupCount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "0", value = "The number of metric groups using this metric")

  public Integer getMetricGroupCount() {
    return metricGroupCount;
  }


  public void setMetricGroupCount(Integer metricGroupCount) {
    this.metricGroupCount = metricGroupCount;
  }


  public MetricRep id(String id) {
    
    this.id = id;
    return this;
  }

   /**
   * The ID of this metric
   * @return id
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "5902deadbeef667524a01290", required = true, value = "The ID of this metric")

  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }


  public MetricRep versionId(String versionId) {
    
    this.versionId = versionId;
    return this;
  }

   /**
   * The version ID of the metric
   * @return versionId
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "version-id-123abc", required = true, value = "The version ID of the metric")

  public String getVersionId() {
    return versionId;
  }


  public void setVersionId(String versionId) {
    this.versionId = versionId;
  }


  public MetricRep key(String key) {
    
    this.key = key;
    return this;
  }

   /**
   * A unique key to reference the metric
   * @return key
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "metric-key-123abc", required = true, value = "A unique key to reference the metric")

  public String getKey() {
    return key;
  }


  public void setKey(String key) {
    this.key = key;
  }


  public MetricRep name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * A human-friendly name for the metric
   * @return name
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "My metric", required = true, value = "A human-friendly name for the metric")

  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public MetricRep kind(KindEnum kind) {
    
    this.kind = kind;
    return this;
  }

   /**
   * The kind of event the metric tracks
   * @return kind
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "custom", required = true, value = "The kind of event the metric tracks")

  public KindEnum getKind() {
    return kind;
  }


  public void setKind(KindEnum kind) {
    this.kind = kind;
  }


  public MetricRep attachedFlagCount(Integer attachedFlagCount) {
    
    this.attachedFlagCount = attachedFlagCount;
    return this;
  }

   /**
   * The number of feature flags currently attached to this metric
   * @return attachedFlagCount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "0", value = "The number of feature flags currently attached to this metric")

  public Integer getAttachedFlagCount() {
    return attachedFlagCount;
  }


  public void setAttachedFlagCount(Integer attachedFlagCount) {
    this.attachedFlagCount = attachedFlagCount;
  }


  public MetricRep links(Map<String, Link> links) {
    
    this.links = links;
    return this;
  }

  public MetricRep putLinksItem(String key, Link linksItem) {
    this.links.put(key, linksItem);
    return this;
  }

   /**
   * The location and content type of related resources
   * @return links
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "{\"parent\":{\"href\":\"/api/v2/metrics/my-project\",\"type\":\"application/json\"},\"self\":{\"href\":\"/api/v2/metrics/my-project/my-metric\",\"type\":\"application/json\"}}", required = true, value = "The location and content type of related resources")

  public Map<String, Link> getLinks() {
    return links;
  }


  public void setLinks(Map<String, Link> links) {
    this.links = links;
  }


  public MetricRep site(Link site) {
    
    this.site = site;
    return this;
  }

   /**
   * Get site
   * @return site
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Link getSite() {
    return site;
  }


  public void setSite(Link site) {
    this.site = site;
  }


  public MetricRep access(Access access) {
    
    this.access = access;
    return this;
  }

   /**
   * Get access
   * @return access
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Access getAccess() {
    return access;
  }


  public void setAccess(Access access) {
    this.access = access;
  }


  public MetricRep tags(List<String> tags) {
    
    this.tags = tags;
    return this;
  }

  public MetricRep addTagsItem(String tagsItem) {
    this.tags.add(tagsItem);
    return this;
  }

   /**
   * Tags for the metric
   * @return tags
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "[]", required = true, value = "Tags for the metric")

  public List<String> getTags() {
    return tags;
  }


  public void setTags(List<String> tags) {
    this.tags = tags;
  }


  public MetricRep creationDate(Long creationDate) {
    
    this.creationDate = creationDate;
    return this;
  }

   /**
   * Get creationDate
   * @return creationDate
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public Long getCreationDate() {
    return creationDate;
  }


  public void setCreationDate(Long creationDate) {
    this.creationDate = creationDate;
  }


  public MetricRep lastModified(Modification lastModified) {
    
    this.lastModified = lastModified;
    return this;
  }

   /**
   * Get lastModified
   * @return lastModified
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Modification getLastModified() {
    return lastModified;
  }


  public void setLastModified(Modification lastModified) {
    this.lastModified = lastModified;
  }


  public MetricRep maintainerId(String maintainerId) {
    
    this.maintainerId = maintainerId;
    return this;
  }

   /**
   * The ID of the member who maintains this metric
   * @return maintainerId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "569fdeadbeef1644facecafe", value = "The ID of the member who maintains this metric")

  public String getMaintainerId() {
    return maintainerId;
  }


  public void setMaintainerId(String maintainerId) {
    this.maintainerId = maintainerId;
  }


  public MetricRep maintainer(MemberSummary maintainer) {
    
    this.maintainer = maintainer;
    return this;
  }

   /**
   * Get maintainer
   * @return maintainer
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public MemberSummary getMaintainer() {
    return maintainer;
  }


  public void setMaintainer(MemberSummary maintainer) {
    this.maintainer = maintainer;
  }


  public MetricRep description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * Description of the metric
   * @return description
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Description of the metric")

  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public MetricRep category(String category) {
    
    this.category = category;
    return this;
  }

   /**
   * The category of the metric
   * @return category
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "Error monitoring", value = "The category of the metric")

  public String getCategory() {
    return category;
  }


  public void setCategory(String category) {
    this.category = category;
  }


  public MetricRep isNumeric(Boolean isNumeric) {
    
    this.isNumeric = isNumeric;
    return this;
  }

   /**
   * For custom metrics, whether to track numeric changes in value against a baseline (&lt;code&gt;true&lt;/code&gt;) or to track a conversion when an end user takes an action (&lt;code&gt;false&lt;/code&gt;).
   * @return isNumeric
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "true", value = "For custom metrics, whether to track numeric changes in value against a baseline (<code>true</code>) or to track a conversion when an end user takes an action (<code>false</code>).")

  public Boolean getIsNumeric() {
    return isNumeric;
  }


  public void setIsNumeric(Boolean isNumeric) {
    this.isNumeric = isNumeric;
  }


  public MetricRep successCriteria(SuccessCriteriaEnum successCriteria) {
    
    this.successCriteria = successCriteria;
    return this;
  }

   /**
   * For custom metrics, the success criteria
   * @return successCriteria
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "For custom metrics, the success criteria")

  public SuccessCriteriaEnum getSuccessCriteria() {
    return successCriteria;
  }


  public void setSuccessCriteria(SuccessCriteriaEnum successCriteria) {
    this.successCriteria = successCriteria;
  }


  public MetricRep unit(String unit) {
    
    this.unit = unit;
    return this;
  }

   /**
   * For numeric custom metrics, the unit of measure
   * @return unit
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "For numeric custom metrics, the unit of measure")

  public String getUnit() {
    return unit;
  }


  public void setUnit(String unit) {
    this.unit = unit;
  }


  public MetricRep eventKey(String eventKey) {
    
    this.eventKey = eventKey;
    return this;
  }

   /**
   * For custom metrics, the event key to use in your code
   * @return eventKey
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "Order placed", value = "For custom metrics, the event key to use in your code")

  public String getEventKey() {
    return eventKey;
  }


  public void setEventKey(String eventKey) {
    this.eventKey = eventKey;
  }


  public MetricRep randomizationUnits(List<String> randomizationUnits) {
    
    this.randomizationUnits = randomizationUnits;
    return this;
  }

  public MetricRep addRandomizationUnitsItem(String randomizationUnitsItem) {
    if (this.randomizationUnits == null) {
      this.randomizationUnits = new ArrayList<>();
    }
    this.randomizationUnits.add(randomizationUnitsItem);
    return this;
  }

   /**
   * An array of randomization units allowed for this metric
   * @return randomizationUnits
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "[\"user\"]", value = "An array of randomization units allowed for this metric")

  public List<String> getRandomizationUnits() {
    return randomizationUnits;
  }


  public void setRandomizationUnits(List<String> randomizationUnits) {
    this.randomizationUnits = randomizationUnits;
  }


  public MetricRep unitAggregationType(UnitAggregationTypeEnum unitAggregationType) {
    
    this.unitAggregationType = unitAggregationType;
    return this;
  }

   /**
   * The method by which multiple unit event values are aggregated
   * @return unitAggregationType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "average", value = "The method by which multiple unit event values are aggregated")

  public UnitAggregationTypeEnum getUnitAggregationType() {
    return unitAggregationType;
  }


  public void setUnitAggregationType(UnitAggregationTypeEnum unitAggregationType) {
    this.unitAggregationType = unitAggregationType;
  }


  public MetricRep analysisType(AnalysisTypeEnum analysisType) {
    
    this.analysisType = analysisType;
    return this;
  }

   /**
   * The method for analyzing metric events
   * @return analysisType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "mean", value = "The method for analyzing metric events")

  public AnalysisTypeEnum getAnalysisType() {
    return analysisType;
  }


  public void setAnalysisType(AnalysisTypeEnum analysisType) {
    this.analysisType = analysisType;
  }


  public MetricRep percentileValue(Integer percentileValue) {
    
    this.percentileValue = percentileValue;
    return this;
  }

   /**
   * The percentile for the analysis method. An integer denoting the target percentile between 0 and 100. Required when &lt;code&gt;analysisType&lt;/code&gt; is &lt;code&gt;percentile&lt;/code&gt;.
   * @return percentileValue
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "95", value = "The percentile for the analysis method. An integer denoting the target percentile between 0 and 100. Required when <code>analysisType</code> is <code>percentile</code>.")

  public Integer getPercentileValue() {
    return percentileValue;
  }


  public void setPercentileValue(Integer percentileValue) {
    this.percentileValue = percentileValue;
  }


  public MetricRep eventDefault(MetricEventDefaultRep eventDefault) {
    
    this.eventDefault = eventDefault;
    return this;
  }

   /**
   * Get eventDefault
   * @return eventDefault
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public MetricEventDefaultRep getEventDefault() {
    return eventDefault;
  }


  public void setEventDefault(MetricEventDefaultRep eventDefault) {
    this.eventDefault = eventDefault;
  }


  public MetricRep experiments(List<DependentExperimentRep> experiments) {
    
    this.experiments = experiments;
    return this;
  }

  public MetricRep addExperimentsItem(DependentExperimentRep experimentsItem) {
    if (this.experiments == null) {
      this.experiments = new ArrayList<>();
    }
    this.experiments.add(experimentsItem);
    return this;
  }

   /**
   * Get experiments
   * @return experiments
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<DependentExperimentRep> getExperiments() {
    return experiments;
  }


  public void setExperiments(List<DependentExperimentRep> experiments) {
    this.experiments = experiments;
  }


  public MetricRep metricGroups(List<DependentMetricGroupRep> metricGroups) {
    
    this.metricGroups = metricGroups;
    return this;
  }

  public MetricRep addMetricGroupsItem(DependentMetricGroupRep metricGroupsItem) {
    if (this.metricGroups == null) {
      this.metricGroups = new ArrayList<>();
    }
    this.metricGroups.add(metricGroupsItem);
    return this;
  }

   /**
   * Metric groups that use this metric
   * @return metricGroups
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Metric groups that use this metric")

  public List<DependentMetricGroupRep> getMetricGroups() {
    return metricGroups;
  }


  public void setMetricGroups(List<DependentMetricGroupRep> metricGroups) {
    this.metricGroups = metricGroups;
  }


  public MetricRep isActive(Boolean isActive) {
    
    this.isActive = isActive;
    return this;
  }

   /**
   * Whether the metric is active
   * @return isActive
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "true", value = "Whether the metric is active")

  public Boolean getIsActive() {
    return isActive;
  }


  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }


  public MetricRep attachedFeatures(List<FlagListingRep> attachedFeatures) {
    
    this.attachedFeatures = attachedFeatures;
    return this;
  }

  public MetricRep addAttachedFeaturesItem(FlagListingRep attachedFeaturesItem) {
    if (this.attachedFeatures == null) {
      this.attachedFeatures = new ArrayList<>();
    }
    this.attachedFeatures.add(attachedFeaturesItem);
    return this;
  }

   /**
   * Details on the flags attached to this metric
   * @return attachedFeatures
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Details on the flags attached to this metric")

  public List<FlagListingRep> getAttachedFeatures() {
    return attachedFeatures;
  }


  public void setAttachedFeatures(List<FlagListingRep> attachedFeatures) {
    this.attachedFeatures = attachedFeatures;
  }


  public MetricRep version(Integer version) {
    
    this.version = version;
    return this;
  }

   /**
   * Version of the metric
   * @return version
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "1", value = "Version of the metric")

  public Integer getVersion() {
    return version;
  }


  public void setVersion(Integer version) {
    this.version = version;
  }


  public MetricRep selector(String selector) {
    
    this.selector = selector;
    return this;
  }

   /**
   * For click metrics, the CSS selectors
   * @return selector
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "For click metrics, the CSS selectors")

  public String getSelector() {
    return selector;
  }


  public void setSelector(String selector) {
    this.selector = selector;
  }


  public MetricRep urls(List<Map<String, Object>> urls) {
    
    this.urls = urls;
    return this;
  }

  public MetricRep addUrlsItem(Map<String, Object> urlsItem) {
    if (this.urls == null) {
      this.urls = new ArrayList<>();
    }
    this.urls.add(urlsItem);
    return this;
  }

   /**
   * Get urls
   * @return urls
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<Map<String, Object>> getUrls() {
    return urls;
  }


  public void setUrls(List<Map<String, Object>> urls) {
    this.urls = urls;
  }

  /**
   * A container for additional, undeclared properties.
   * This is a holder for any undeclared properties as specified with
   * the 'additionalProperties' keyword in the OAS document.
   */
  private Map<String, Object> additionalProperties;

  /**
   * Set the additional (undeclared) property with the specified name and value.
   * If the property does not already exist, create it otherwise replace it.
   */
  public MetricRep putAdditionalProperty(String key, Object value) {
    if (this.additionalProperties == null) {
        this.additionalProperties = new HashMap<String, Object>();
    }
    this.additionalProperties.put(key, value);
    return this;
  }

  /**
   * Return the additional (undeclared) property.
   */
  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }

  /**
   * Return the additional (undeclared) property with the specified name.
   */
  public Object getAdditionalProperty(String key) {
    if (this.additionalProperties == null) {
        return null;
    }
    return this.additionalProperties.get(key);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MetricRep metricRep = (MetricRep) o;
    return Objects.equals(this.experimentCount, metricRep.experimentCount) &&
        Objects.equals(this.metricGroupCount, metricRep.metricGroupCount) &&
        Objects.equals(this.id, metricRep.id) &&
        Objects.equals(this.versionId, metricRep.versionId) &&
        Objects.equals(this.key, metricRep.key) &&
        Objects.equals(this.name, metricRep.name) &&
        Objects.equals(this.kind, metricRep.kind) &&
        Objects.equals(this.attachedFlagCount, metricRep.attachedFlagCount) &&
        Objects.equals(this.links, metricRep.links) &&
        Objects.equals(this.site, metricRep.site) &&
        Objects.equals(this.access, metricRep.access) &&
        Objects.equals(this.tags, metricRep.tags) &&
        Objects.equals(this.creationDate, metricRep.creationDate) &&
        Objects.equals(this.lastModified, metricRep.lastModified) &&
        Objects.equals(this.maintainerId, metricRep.maintainerId) &&
        Objects.equals(this.maintainer, metricRep.maintainer) &&
        Objects.equals(this.description, metricRep.description) &&
        Objects.equals(this.category, metricRep.category) &&
        Objects.equals(this.isNumeric, metricRep.isNumeric) &&
        Objects.equals(this.successCriteria, metricRep.successCriteria) &&
        Objects.equals(this.unit, metricRep.unit) &&
        Objects.equals(this.eventKey, metricRep.eventKey) &&
        Objects.equals(this.randomizationUnits, metricRep.randomizationUnits) &&
        Objects.equals(this.unitAggregationType, metricRep.unitAggregationType) &&
        Objects.equals(this.analysisType, metricRep.analysisType) &&
        Objects.equals(this.percentileValue, metricRep.percentileValue) &&
        Objects.equals(this.eventDefault, metricRep.eventDefault) &&
        Objects.equals(this.experiments, metricRep.experiments) &&
        Objects.equals(this.metricGroups, metricRep.metricGroups) &&
        Objects.equals(this.isActive, metricRep.isActive) &&
        Objects.equals(this.attachedFeatures, metricRep.attachedFeatures) &&
        Objects.equals(this.version, metricRep.version) &&
        Objects.equals(this.selector, metricRep.selector) &&
        Objects.equals(this.urls, metricRep.urls)&&
        Objects.equals(this.additionalProperties, metricRep.additionalProperties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(experimentCount, metricGroupCount, id, versionId, key, name, kind, attachedFlagCount, links, site, access, tags, creationDate, lastModified, maintainerId, maintainer, description, category, isNumeric, successCriteria, unit, eventKey, randomizationUnits, unitAggregationType, analysisType, percentileValue, eventDefault, experiments, metricGroups, isActive, attachedFeatures, version, selector, urls, additionalProperties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MetricRep {\n");
    sb.append("    experimentCount: ").append(toIndentedString(experimentCount)).append("\n");
    sb.append("    metricGroupCount: ").append(toIndentedString(metricGroupCount)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    versionId: ").append(toIndentedString(versionId)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    kind: ").append(toIndentedString(kind)).append("\n");
    sb.append("    attachedFlagCount: ").append(toIndentedString(attachedFlagCount)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    site: ").append(toIndentedString(site)).append("\n");
    sb.append("    access: ").append(toIndentedString(access)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
    sb.append("    lastModified: ").append(toIndentedString(lastModified)).append("\n");
    sb.append("    maintainerId: ").append(toIndentedString(maintainerId)).append("\n");
    sb.append("    maintainer: ").append(toIndentedString(maintainer)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    isNumeric: ").append(toIndentedString(isNumeric)).append("\n");
    sb.append("    successCriteria: ").append(toIndentedString(successCriteria)).append("\n");
    sb.append("    unit: ").append(toIndentedString(unit)).append("\n");
    sb.append("    eventKey: ").append(toIndentedString(eventKey)).append("\n");
    sb.append("    randomizationUnits: ").append(toIndentedString(randomizationUnits)).append("\n");
    sb.append("    unitAggregationType: ").append(toIndentedString(unitAggregationType)).append("\n");
    sb.append("    analysisType: ").append(toIndentedString(analysisType)).append("\n");
    sb.append("    percentileValue: ").append(toIndentedString(percentileValue)).append("\n");
    sb.append("    eventDefault: ").append(toIndentedString(eventDefault)).append("\n");
    sb.append("    experiments: ").append(toIndentedString(experiments)).append("\n");
    sb.append("    metricGroups: ").append(toIndentedString(metricGroups)).append("\n");
    sb.append("    isActive: ").append(toIndentedString(isActive)).append("\n");
    sb.append("    attachedFeatures: ").append(toIndentedString(attachedFeatures)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    selector: ").append(toIndentedString(selector)).append("\n");
    sb.append("    urls: ").append(toIndentedString(urls)).append("\n");
    sb.append("    additionalProperties: ").append(toIndentedString(additionalProperties)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>();
    openapiFields.add("experimentCount");
    openapiFields.add("metricGroupCount");
    openapiFields.add("_id");
    openapiFields.add("_versionId");
    openapiFields.add("key");
    openapiFields.add("name");
    openapiFields.add("kind");
    openapiFields.add("_attachedFlagCount");
    openapiFields.add("_links");
    openapiFields.add("_site");
    openapiFields.add("_access");
    openapiFields.add("tags");
    openapiFields.add("_creationDate");
    openapiFields.add("lastModified");
    openapiFields.add("maintainerId");
    openapiFields.add("_maintainer");
    openapiFields.add("description");
    openapiFields.add("category");
    openapiFields.add("isNumeric");
    openapiFields.add("successCriteria");
    openapiFields.add("unit");
    openapiFields.add("eventKey");
    openapiFields.add("randomizationUnits");
    openapiFields.add("unitAggregationType");
    openapiFields.add("analysisType");
    openapiFields.add("percentileValue");
    openapiFields.add("eventDefault");
    openapiFields.add("experiments");
    openapiFields.add("metricGroups");
    openapiFields.add("isActive");
    openapiFields.add("_attachedFeatures");
    openapiFields.add("_version");
    openapiFields.add("selector");
    openapiFields.add("urls");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("_id");
    openapiRequiredFields.add("_versionId");
    openapiRequiredFields.add("key");
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("kind");
    openapiRequiredFields.add("_links");
    openapiRequiredFields.add("tags");
    openapiRequiredFields.add("_creationDate");
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to MetricRep
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (MetricRep.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in MetricRep is not found in the empty JSON string", MetricRep.openapiRequiredFields.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : MetricRep.openapiRequiredFields) {
        if (jsonObj.get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonObj.toString()));
        }
      }
      if (jsonObj.get("_id") != null && !jsonObj.get("_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("_id").toString()));
      }
      if (jsonObj.get("_versionId") != null && !jsonObj.get("_versionId").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `_versionId` to be a primitive type in the JSON string but got `%s`", jsonObj.get("_versionId").toString()));
      }
      if (jsonObj.get("key") != null && !jsonObj.get("key").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `key` to be a primitive type in the JSON string but got `%s`", jsonObj.get("key").toString()));
      }
      if (jsonObj.get("name") != null && !jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if (jsonObj.get("kind") != null && !jsonObj.get("kind").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `kind` to be a primitive type in the JSON string but got `%s`", jsonObj.get("kind").toString()));
      }
      // validate the optional field `_site`
      if (jsonObj.getAsJsonObject("_site") != null) {
        Link.validateJsonObject(jsonObj.getAsJsonObject("_site"));
      }
      // validate the optional field `_access`
      if (jsonObj.getAsJsonObject("_access") != null) {
        Access.validateJsonObject(jsonObj.getAsJsonObject("_access"));
      }
      // ensure the json data is an array
      if (jsonObj.get("tags") != null && !jsonObj.get("tags").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `tags` to be an array in the JSON string but got `%s`", jsonObj.get("tags").toString()));
      }
      // validate the optional field `lastModified`
      if (jsonObj.getAsJsonObject("lastModified") != null) {
        Modification.validateJsonObject(jsonObj.getAsJsonObject("lastModified"));
      }
      if (jsonObj.get("maintainerId") != null && !jsonObj.get("maintainerId").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `maintainerId` to be a primitive type in the JSON string but got `%s`", jsonObj.get("maintainerId").toString()));
      }
      // validate the optional field `_maintainer`
      if (jsonObj.getAsJsonObject("_maintainer") != null) {
        MemberSummary.validateJsonObject(jsonObj.getAsJsonObject("_maintainer"));
      }
      if (jsonObj.get("description") != null && !jsonObj.get("description").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `description` to be a primitive type in the JSON string but got `%s`", jsonObj.get("description").toString()));
      }
      if (jsonObj.get("category") != null && !jsonObj.get("category").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `category` to be a primitive type in the JSON string but got `%s`", jsonObj.get("category").toString()));
      }
      if (jsonObj.get("successCriteria") != null && !jsonObj.get("successCriteria").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `successCriteria` to be a primitive type in the JSON string but got `%s`", jsonObj.get("successCriteria").toString()));
      }
      if (jsonObj.get("unit") != null && !jsonObj.get("unit").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `unit` to be a primitive type in the JSON string but got `%s`", jsonObj.get("unit").toString()));
      }
      if (jsonObj.get("eventKey") != null && !jsonObj.get("eventKey").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `eventKey` to be a primitive type in the JSON string but got `%s`", jsonObj.get("eventKey").toString()));
      }
      // ensure the json data is an array
      if (jsonObj.get("randomizationUnits") != null && !jsonObj.get("randomizationUnits").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `randomizationUnits` to be an array in the JSON string but got `%s`", jsonObj.get("randomizationUnits").toString()));
      }
      if (jsonObj.get("unitAggregationType") != null && !jsonObj.get("unitAggregationType").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `unitAggregationType` to be a primitive type in the JSON string but got `%s`", jsonObj.get("unitAggregationType").toString()));
      }
      if (jsonObj.get("analysisType") != null && !jsonObj.get("analysisType").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `analysisType` to be a primitive type in the JSON string but got `%s`", jsonObj.get("analysisType").toString()));
      }
      // validate the optional field `eventDefault`
      if (jsonObj.getAsJsonObject("eventDefault") != null) {
        MetricEventDefaultRep.validateJsonObject(jsonObj.getAsJsonObject("eventDefault"));
      }
      JsonArray jsonArrayexperiments = jsonObj.getAsJsonArray("experiments");
      if (jsonArrayexperiments != null) {
        // ensure the json data is an array
        if (!jsonObj.get("experiments").isJsonArray()) {
          throw new IllegalArgumentException(String.format("Expected the field `experiments` to be an array in the JSON string but got `%s`", jsonObj.get("experiments").toString()));
        }

        // validate the optional field `experiments` (array)
        for (int i = 0; i < jsonArrayexperiments.size(); i++) {
          DependentExperimentRep.validateJsonObject(jsonArrayexperiments.get(i).getAsJsonObject());
        };
      }
      JsonArray jsonArraymetricGroups = jsonObj.getAsJsonArray("metricGroups");
      if (jsonArraymetricGroups != null) {
        // ensure the json data is an array
        if (!jsonObj.get("metricGroups").isJsonArray()) {
          throw new IllegalArgumentException(String.format("Expected the field `metricGroups` to be an array in the JSON string but got `%s`", jsonObj.get("metricGroups").toString()));
        }

        // validate the optional field `metricGroups` (array)
        for (int i = 0; i < jsonArraymetricGroups.size(); i++) {
          DependentMetricGroupRep.validateJsonObject(jsonArraymetricGroups.get(i).getAsJsonObject());
        };
      }
      JsonArray jsonArrayattachedFeatures = jsonObj.getAsJsonArray("_attachedFeatures");
      if (jsonArrayattachedFeatures != null) {
        // ensure the json data is an array
        if (!jsonObj.get("_attachedFeatures").isJsonArray()) {
          throw new IllegalArgumentException(String.format("Expected the field `_attachedFeatures` to be an array in the JSON string but got `%s`", jsonObj.get("_attachedFeatures").toString()));
        }

        // validate the optional field `_attachedFeatures` (array)
        for (int i = 0; i < jsonArrayattachedFeatures.size(); i++) {
          FlagListingRep.validateJsonObject(jsonArrayattachedFeatures.get(i).getAsJsonObject());
        };
      }
      if (jsonObj.get("selector") != null && !jsonObj.get("selector").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `selector` to be a primitive type in the JSON string but got `%s`", jsonObj.get("selector").toString()));
      }
      // ensure the json data is an array
      if (jsonObj.get("urls") != null && !jsonObj.get("urls").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `urls` to be an array in the JSON string but got `%s`", jsonObj.get("urls").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!MetricRep.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'MetricRep' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<MetricRep> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(MetricRep.class));

       return (TypeAdapter<T>) new TypeAdapter<MetricRep>() {
           @Override
           public void write(JsonWriter out, MetricRep value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             obj.remove("additionalProperties");
             // serialize additonal properties
             if (value.getAdditionalProperties() != null) {
               for (Map.Entry<String, Object> entry : value.getAdditionalProperties().entrySet()) {
                 if (entry.getValue() instanceof String)
                   obj.addProperty(entry.getKey(), (String) entry.getValue());
                 else if (entry.getValue() instanceof Number)
                   obj.addProperty(entry.getKey(), (Number) entry.getValue());
                 else if (entry.getValue() instanceof Boolean)
                   obj.addProperty(entry.getKey(), (Boolean) entry.getValue());
                 else if (entry.getValue() instanceof Character)
                   obj.addProperty(entry.getKey(), (Character) entry.getValue());
                 else {
                   obj.add(entry.getKey(), gson.toJsonTree(entry.getValue()).getAsJsonObject());
                 }
               }
             }
             elementAdapter.write(out, obj);
           }

           @Override
           public MetricRep read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             // store additional fields in the deserialized instance
             MetricRep instance = thisAdapter.fromJsonTree(jsonObj);
             for (Map.Entry<String, JsonElement> entry : jsonObj.entrySet()) {
               if (!openapiFields.contains(entry.getKey())) {
                 if (entry.getValue().isJsonPrimitive()) { // primitive type
                   if (entry.getValue().getAsJsonPrimitive().isString())
                     instance.putAdditionalProperty(entry.getKey(), entry.getValue().getAsString());
                   else if (entry.getValue().getAsJsonPrimitive().isNumber())
                     instance.putAdditionalProperty(entry.getKey(), entry.getValue().getAsNumber());
                   else if (entry.getValue().getAsJsonPrimitive().isBoolean())
                     instance.putAdditionalProperty(entry.getKey(), entry.getValue().getAsBoolean());
                   else
                     throw new IllegalArgumentException(String.format("The field `%s` has unknown primitive type. Value: %s", entry.getKey(), entry.getValue().toString()));
                 } else { // non-primitive type
                   instance.putAdditionalProperty(entry.getKey(), gson.fromJson(entry.getValue(), HashMap.class));
                 }
               }
             }
             return instance;
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of MetricRep given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of MetricRep
  * @throws IOException if the JSON string is invalid with respect to MetricRep
  */
  public static MetricRep fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, MetricRep.class);
  }

 /**
  * Convert an instance of MetricRep to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

