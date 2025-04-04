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
import com.launchdarkly.api.model.ClientSideAvailability;
import com.launchdarkly.api.model.CustomProperty;
import com.launchdarkly.api.model.Defaults;
import com.launchdarkly.api.model.ExperimentInfoRep;
import com.launchdarkly.api.model.FeatureFlagConfig;
import com.launchdarkly.api.model.FlagMigrationSettingsRep;
import com.launchdarkly.api.model.Link;
import com.launchdarkly.api.model.MaintainerTeam;
import com.launchdarkly.api.model.MemberSummary;
import com.launchdarkly.api.model.Variation;
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
 * FeatureFlag
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-04-04T21:33:11.424117Z[Etc/UTC]")
public class FeatureFlag {
  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  /**
   * Kind of feature flag
   */
  @JsonAdapter(KindEnum.Adapter.class)
  public enum KindEnum {
    BOOLEAN("boolean"),
    
    MULTIVARIATE("multivariate");

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

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_KEY = "key";
  @SerializedName(SERIALIZED_NAME_KEY)
  private String key;

  public static final String SERIALIZED_NAME_VERSION = "_version";
  @SerializedName(SERIALIZED_NAME_VERSION)
  private Integer version;

  public static final String SERIALIZED_NAME_CREATION_DATE = "creationDate";
  @SerializedName(SERIALIZED_NAME_CREATION_DATE)
  private Long creationDate;

  public static final String SERIALIZED_NAME_INCLUDE_IN_SNIPPET = "includeInSnippet";
  @SerializedName(SERIALIZED_NAME_INCLUDE_IN_SNIPPET)
  private Boolean includeInSnippet;

  public static final String SERIALIZED_NAME_CLIENT_SIDE_AVAILABILITY = "clientSideAvailability";
  @SerializedName(SERIALIZED_NAME_CLIENT_SIDE_AVAILABILITY)
  private ClientSideAvailability clientSideAvailability;

  public static final String SERIALIZED_NAME_VARIATIONS = "variations";
  @SerializedName(SERIALIZED_NAME_VARIATIONS)
  private List<Variation> variations = new ArrayList<>();

  public static final String SERIALIZED_NAME_TEMPORARY = "temporary";
  @SerializedName(SERIALIZED_NAME_TEMPORARY)
  private Boolean temporary;

  public static final String SERIALIZED_NAME_TAGS = "tags";
  @SerializedName(SERIALIZED_NAME_TAGS)
  private List<String> tags = new ArrayList<>();

  public static final String SERIALIZED_NAME_LINKS = "_links";
  @SerializedName(SERIALIZED_NAME_LINKS)
  private Map<String, Link> links = new HashMap<>();

  public static final String SERIALIZED_NAME_MAINTAINER_ID = "maintainerId";
  @SerializedName(SERIALIZED_NAME_MAINTAINER_ID)
  private String maintainerId;

  public static final String SERIALIZED_NAME_MAINTAINER = "_maintainer";
  @SerializedName(SERIALIZED_NAME_MAINTAINER)
  private MemberSummary maintainer;

  public static final String SERIALIZED_NAME_MAINTAINER_TEAM_KEY = "maintainerTeamKey";
  @SerializedName(SERIALIZED_NAME_MAINTAINER_TEAM_KEY)
  private String maintainerTeamKey;

  public static final String SERIALIZED_NAME_MAINTAINER_TEAM = "_maintainerTeam";
  @SerializedName(SERIALIZED_NAME_MAINTAINER_TEAM)
  private MaintainerTeam maintainerTeam;

  public static final String SERIALIZED_NAME_GOAL_IDS = "goalIds";
  @SerializedName(SERIALIZED_NAME_GOAL_IDS)
  private List<String> goalIds = null;

  public static final String SERIALIZED_NAME_EXPERIMENTS = "experiments";
  @SerializedName(SERIALIZED_NAME_EXPERIMENTS)
  private ExperimentInfoRep experiments;

  public static final String SERIALIZED_NAME_CUSTOM_PROPERTIES = "customProperties";
  @SerializedName(SERIALIZED_NAME_CUSTOM_PROPERTIES)
  private Map<String, CustomProperty> customProperties = new HashMap<>();

  public static final String SERIALIZED_NAME_ARCHIVED = "archived";
  @SerializedName(SERIALIZED_NAME_ARCHIVED)
  private Boolean archived;

  public static final String SERIALIZED_NAME_ARCHIVED_DATE = "archivedDate";
  @SerializedName(SERIALIZED_NAME_ARCHIVED_DATE)
  private Long archivedDate;

  public static final String SERIALIZED_NAME_DEPRECATED = "deprecated";
  @SerializedName(SERIALIZED_NAME_DEPRECATED)
  private Boolean deprecated;

  public static final String SERIALIZED_NAME_DEPRECATED_DATE = "deprecatedDate";
  @SerializedName(SERIALIZED_NAME_DEPRECATED_DATE)
  private Long deprecatedDate;

  public static final String SERIALIZED_NAME_DEFAULTS = "defaults";
  @SerializedName(SERIALIZED_NAME_DEFAULTS)
  private Defaults defaults;

  public static final String SERIALIZED_NAME_PURPOSE = "_purpose";
  @SerializedName(SERIALIZED_NAME_PURPOSE)
  private String purpose;

  public static final String SERIALIZED_NAME_MIGRATION_SETTINGS = "migrationSettings";
  @SerializedName(SERIALIZED_NAME_MIGRATION_SETTINGS)
  private FlagMigrationSettingsRep migrationSettings;

  public static final String SERIALIZED_NAME_ENVIRONMENTS = "environments";
  @SerializedName(SERIALIZED_NAME_ENVIRONMENTS)
  private Map<String, FeatureFlagConfig> environments = null;

  public FeatureFlag() { 
  }

  public FeatureFlag name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * A human-friendly name for the feature flag
   * @return name
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "My Flag", required = true, value = "A human-friendly name for the feature flag")

  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public FeatureFlag kind(KindEnum kind) {
    
    this.kind = kind;
    return this;
  }

   /**
   * Kind of feature flag
   * @return kind
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "boolean", required = true, value = "Kind of feature flag")

  public KindEnum getKind() {
    return kind;
  }


  public void setKind(KindEnum kind) {
    this.kind = kind;
  }


  public FeatureFlag description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * Description of the feature flag
   * @return description
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "This flag controls the example widgets", value = "Description of the feature flag")

  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public FeatureFlag key(String key) {
    
    this.key = key;
    return this;
  }

   /**
   * A unique key used to reference the flag in your code
   * @return key
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "flag-key-123abc", required = true, value = "A unique key used to reference the flag in your code")

  public String getKey() {
    return key;
  }


  public void setKey(String key) {
    this.key = key;
  }


  public FeatureFlag version(Integer version) {
    
    this.version = version;
    return this;
  }

   /**
   * Version of the feature flag
   * @return version
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "1", required = true, value = "Version of the feature flag")

  public Integer getVersion() {
    return version;
  }


  public void setVersion(Integer version) {
    this.version = version;
  }


  public FeatureFlag creationDate(Long creationDate) {
    
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


  public FeatureFlag includeInSnippet(Boolean includeInSnippet) {
    
    this.includeInSnippet = includeInSnippet;
    return this;
  }

   /**
   * Deprecated, use &lt;code&gt;clientSideAvailability&lt;/code&gt;. Whether this flag should be made available to the client-side JavaScript SDK
   * @return includeInSnippet
   * @deprecated
  **/
  @Deprecated
  @javax.annotation.Nullable
  @ApiModelProperty(example = "true", value = "Deprecated, use <code>clientSideAvailability</code>. Whether this flag should be made available to the client-side JavaScript SDK")

  public Boolean getIncludeInSnippet() {
    return includeInSnippet;
  }


  public void setIncludeInSnippet(Boolean includeInSnippet) {
    this.includeInSnippet = includeInSnippet;
  }


  public FeatureFlag clientSideAvailability(ClientSideAvailability clientSideAvailability) {
    
    this.clientSideAvailability = clientSideAvailability;
    return this;
  }

   /**
   * Get clientSideAvailability
   * @return clientSideAvailability
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public ClientSideAvailability getClientSideAvailability() {
    return clientSideAvailability;
  }


  public void setClientSideAvailability(ClientSideAvailability clientSideAvailability) {
    this.clientSideAvailability = clientSideAvailability;
  }


  public FeatureFlag variations(List<Variation> variations) {
    
    this.variations = variations;
    return this;
  }

  public FeatureFlag addVariationsItem(Variation variationsItem) {
    this.variations.add(variationsItem);
    return this;
  }

   /**
   * An array of possible variations for the flag
   * @return variations
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "[{\"_id\":\"e432f62b-55f6-49dd-a02f-eb24acf39d05\",\"value\":true},{\"_id\":\"a00bf58d-d252-476c-b915-15a74becacb4\",\"value\":false}]", required = true, value = "An array of possible variations for the flag")

  public List<Variation> getVariations() {
    return variations;
  }


  public void setVariations(List<Variation> variations) {
    this.variations = variations;
  }


  public FeatureFlag temporary(Boolean temporary) {
    
    this.temporary = temporary;
    return this;
  }

   /**
   * Whether the flag is a temporary flag
   * @return temporary
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "true", required = true, value = "Whether the flag is a temporary flag")

  public Boolean getTemporary() {
    return temporary;
  }


  public void setTemporary(Boolean temporary) {
    this.temporary = temporary;
  }


  public FeatureFlag tags(List<String> tags) {
    
    this.tags = tags;
    return this;
  }

  public FeatureFlag addTagsItem(String tagsItem) {
    this.tags.add(tagsItem);
    return this;
  }

   /**
   * Tags for the feature flag
   * @return tags
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "[\"example-tag\"]", required = true, value = "Tags for the feature flag")

  public List<String> getTags() {
    return tags;
  }


  public void setTags(List<String> tags) {
    this.tags = tags;
  }


  public FeatureFlag links(Map<String, Link> links) {
    
    this.links = links;
    return this;
  }

  public FeatureFlag putLinksItem(String key, Link linksItem) {
    this.links.put(key, linksItem);
    return this;
  }

   /**
   * The location and content type of related resources
   * @return links
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "{\"parent\":{\"href\":\"/api/v2/flags/my-project\",\"type\":\"application/json\"},\"self\":{\"href\":\"/api/v2/flags/my-project/my-flag\",\"type\":\"application/json\"}}", required = true, value = "The location and content type of related resources")

  public Map<String, Link> getLinks() {
    return links;
  }


  public void setLinks(Map<String, Link> links) {
    this.links = links;
  }


  public FeatureFlag maintainerId(String maintainerId) {
    
    this.maintainerId = maintainerId;
    return this;
  }

   /**
   * Associated maintainerId for the feature flag
   * @return maintainerId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "569f183514f4432160000007", value = "Associated maintainerId for the feature flag")

  public String getMaintainerId() {
    return maintainerId;
  }


  public void setMaintainerId(String maintainerId) {
    this.maintainerId = maintainerId;
  }


  public FeatureFlag maintainer(MemberSummary maintainer) {
    
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


  public FeatureFlag maintainerTeamKey(String maintainerTeamKey) {
    
    this.maintainerTeamKey = maintainerTeamKey;
    return this;
  }

   /**
   * The key of the associated team that maintains this feature flag
   * @return maintainerTeamKey
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "team-1", value = "The key of the associated team that maintains this feature flag")

  public String getMaintainerTeamKey() {
    return maintainerTeamKey;
  }


  public void setMaintainerTeamKey(String maintainerTeamKey) {
    this.maintainerTeamKey = maintainerTeamKey;
  }


  public FeatureFlag maintainerTeam(MaintainerTeam maintainerTeam) {
    
    this.maintainerTeam = maintainerTeam;
    return this;
  }

   /**
   * Get maintainerTeam
   * @return maintainerTeam
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public MaintainerTeam getMaintainerTeam() {
    return maintainerTeam;
  }


  public void setMaintainerTeam(MaintainerTeam maintainerTeam) {
    this.maintainerTeam = maintainerTeam;
  }


  public FeatureFlag goalIds(List<String> goalIds) {
    
    this.goalIds = goalIds;
    return this;
  }

  public FeatureFlag addGoalIdsItem(String goalIdsItem) {
    if (this.goalIds == null) {
      this.goalIds = new ArrayList<>();
    }
    this.goalIds.add(goalIdsItem);
    return this;
  }

   /**
   * Deprecated, use &lt;code&gt;experiments&lt;/code&gt; instead
   * @return goalIds
   * @deprecated
  **/
  @Deprecated
  @javax.annotation.Nullable
  @ApiModelProperty(example = "[]", value = "Deprecated, use <code>experiments</code> instead")

  public List<String> getGoalIds() {
    return goalIds;
  }


  public void setGoalIds(List<String> goalIds) {
    this.goalIds = goalIds;
  }


  public FeatureFlag experiments(ExperimentInfoRep experiments) {
    
    this.experiments = experiments;
    return this;
  }

   /**
   * Get experiments
   * @return experiments
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public ExperimentInfoRep getExperiments() {
    return experiments;
  }


  public void setExperiments(ExperimentInfoRep experiments) {
    this.experiments = experiments;
  }


  public FeatureFlag customProperties(Map<String, CustomProperty> customProperties) {
    
    this.customProperties = customProperties;
    return this;
  }

  public FeatureFlag putCustomPropertiesItem(String key, CustomProperty customPropertiesItem) {
    this.customProperties.put(key, customPropertiesItem);
    return this;
  }

   /**
   * Get customProperties
   * @return customProperties
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public Map<String, CustomProperty> getCustomProperties() {
    return customProperties;
  }


  public void setCustomProperties(Map<String, CustomProperty> customProperties) {
    this.customProperties = customProperties;
  }


  public FeatureFlag archived(Boolean archived) {
    
    this.archived = archived;
    return this;
  }

   /**
   * Boolean indicating if the feature flag is archived
   * @return archived
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "false", required = true, value = "Boolean indicating if the feature flag is archived")

  public Boolean getArchived() {
    return archived;
  }


  public void setArchived(Boolean archived) {
    this.archived = archived;
  }


  public FeatureFlag archivedDate(Long archivedDate) {
    
    this.archivedDate = archivedDate;
    return this;
  }

   /**
   * Get archivedDate
   * @return archivedDate
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getArchivedDate() {
    return archivedDate;
  }


  public void setArchivedDate(Long archivedDate) {
    this.archivedDate = archivedDate;
  }


  public FeatureFlag deprecated(Boolean deprecated) {
    
    this.deprecated = deprecated;
    return this;
  }

   /**
   * Boolean indicating if the feature flag is deprecated
   * @return deprecated
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "false", value = "Boolean indicating if the feature flag is deprecated")

  public Boolean getDeprecated() {
    return deprecated;
  }


  public void setDeprecated(Boolean deprecated) {
    this.deprecated = deprecated;
  }


  public FeatureFlag deprecatedDate(Long deprecatedDate) {
    
    this.deprecatedDate = deprecatedDate;
    return this;
  }

   /**
   * Get deprecatedDate
   * @return deprecatedDate
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getDeprecatedDate() {
    return deprecatedDate;
  }


  public void setDeprecatedDate(Long deprecatedDate) {
    this.deprecatedDate = deprecatedDate;
  }


  public FeatureFlag defaults(Defaults defaults) {
    
    this.defaults = defaults;
    return this;
  }

   /**
   * Get defaults
   * @return defaults
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Defaults getDefaults() {
    return defaults;
  }


  public void setDefaults(Defaults defaults) {
    this.defaults = defaults;
  }


  public FeatureFlag purpose(String purpose) {
    
    this.purpose = purpose;
    return this;
  }

   /**
   * Get purpose
   * @return purpose
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getPurpose() {
    return purpose;
  }


  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }


  public FeatureFlag migrationSettings(FlagMigrationSettingsRep migrationSettings) {
    
    this.migrationSettings = migrationSettings;
    return this;
  }

   /**
   * Get migrationSettings
   * @return migrationSettings
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public FlagMigrationSettingsRep getMigrationSettings() {
    return migrationSettings;
  }


  public void setMigrationSettings(FlagMigrationSettingsRep migrationSettings) {
    this.migrationSettings = migrationSettings;
  }


  public FeatureFlag environments(Map<String, FeatureFlagConfig> environments) {
    
    this.environments = environments;
    return this;
  }

  public FeatureFlag putEnvironmentsItem(String key, FeatureFlagConfig environmentsItem) {
    if (this.environments == null) {
      this.environments = new HashMap<>();
    }
    this.environments.put(key, environmentsItem);
    return this;
  }

   /**
   * Details on the environments for this flag. Only returned if the request is filtered by environment, using the &lt;code&gt;filterEnv&lt;/code&gt; query parameter.
   * @return environments
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "{\"my-environment\":{\"_environmentName\":\"My Environment\",\"_site\":{\"href\":\"/default/my-environment/features/client-side-flag\",\"type\":\"text/html\"},\"_summary\":{\"prerequisites\":0,\"variations\":{\"0\":{\"contextTargets\":1,\"isFallthrough\":true,\"nullRules\":0,\"rules\":0,\"targets\":1},\"1\":{\"isOff\":true,\"nullRules\":0,\"rules\":0,\"targets\":0}}},\"archived\":false,\"contextTargets\":[{\"contextKind\":\"device\",\"values\":[\"device-key-123abc\"],\"variation\":0}],\"fallthrough\":{\"variation\":0},\"lastModified\":1627071171347,\"offVariation\":1,\"on\":false,\"prerequisites\":[],\"rules\":[],\"salt\":\"61eddeadbeef4da1facecafe3a60a397\",\"sel\":\"810edeadbeef4844facecafe438f2999492\",\"targets\":[{\"contextKind\":\"user\",\"values\":[\"user-key-123abc\"],\"variation\":0}],\"trackEvents\":false,\"trackEventsFallthrough\":false,\"version\":1}}", value = "Details on the environments for this flag. Only returned if the request is filtered by environment, using the <code>filterEnv</code> query parameter.")

  public Map<String, FeatureFlagConfig> getEnvironments() {
    return environments;
  }


  public void setEnvironments(Map<String, FeatureFlagConfig> environments) {
    this.environments = environments;
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
  public FeatureFlag putAdditionalProperty(String key, Object value) {
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
    FeatureFlag featureFlag = (FeatureFlag) o;
    return Objects.equals(this.name, featureFlag.name) &&
        Objects.equals(this.kind, featureFlag.kind) &&
        Objects.equals(this.description, featureFlag.description) &&
        Objects.equals(this.key, featureFlag.key) &&
        Objects.equals(this.version, featureFlag.version) &&
        Objects.equals(this.creationDate, featureFlag.creationDate) &&
        Objects.equals(this.includeInSnippet, featureFlag.includeInSnippet) &&
        Objects.equals(this.clientSideAvailability, featureFlag.clientSideAvailability) &&
        Objects.equals(this.variations, featureFlag.variations) &&
        Objects.equals(this.temporary, featureFlag.temporary) &&
        Objects.equals(this.tags, featureFlag.tags) &&
        Objects.equals(this.links, featureFlag.links) &&
        Objects.equals(this.maintainerId, featureFlag.maintainerId) &&
        Objects.equals(this.maintainer, featureFlag.maintainer) &&
        Objects.equals(this.maintainerTeamKey, featureFlag.maintainerTeamKey) &&
        Objects.equals(this.maintainerTeam, featureFlag.maintainerTeam) &&
        Objects.equals(this.goalIds, featureFlag.goalIds) &&
        Objects.equals(this.experiments, featureFlag.experiments) &&
        Objects.equals(this.customProperties, featureFlag.customProperties) &&
        Objects.equals(this.archived, featureFlag.archived) &&
        Objects.equals(this.archivedDate, featureFlag.archivedDate) &&
        Objects.equals(this.deprecated, featureFlag.deprecated) &&
        Objects.equals(this.deprecatedDate, featureFlag.deprecatedDate) &&
        Objects.equals(this.defaults, featureFlag.defaults) &&
        Objects.equals(this.purpose, featureFlag.purpose) &&
        Objects.equals(this.migrationSettings, featureFlag.migrationSettings) &&
        Objects.equals(this.environments, featureFlag.environments)&&
        Objects.equals(this.additionalProperties, featureFlag.additionalProperties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, kind, description, key, version, creationDate, includeInSnippet, clientSideAvailability, variations, temporary, tags, links, maintainerId, maintainer, maintainerTeamKey, maintainerTeam, goalIds, experiments, customProperties, archived, archivedDate, deprecated, deprecatedDate, defaults, purpose, migrationSettings, environments, additionalProperties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FeatureFlag {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    kind: ").append(toIndentedString(kind)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
    sb.append("    includeInSnippet: ").append(toIndentedString(includeInSnippet)).append("\n");
    sb.append("    clientSideAvailability: ").append(toIndentedString(clientSideAvailability)).append("\n");
    sb.append("    variations: ").append(toIndentedString(variations)).append("\n");
    sb.append("    temporary: ").append(toIndentedString(temporary)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    maintainerId: ").append(toIndentedString(maintainerId)).append("\n");
    sb.append("    maintainer: ").append(toIndentedString(maintainer)).append("\n");
    sb.append("    maintainerTeamKey: ").append(toIndentedString(maintainerTeamKey)).append("\n");
    sb.append("    maintainerTeam: ").append(toIndentedString(maintainerTeam)).append("\n");
    sb.append("    goalIds: ").append(toIndentedString(goalIds)).append("\n");
    sb.append("    experiments: ").append(toIndentedString(experiments)).append("\n");
    sb.append("    customProperties: ").append(toIndentedString(customProperties)).append("\n");
    sb.append("    archived: ").append(toIndentedString(archived)).append("\n");
    sb.append("    archivedDate: ").append(toIndentedString(archivedDate)).append("\n");
    sb.append("    deprecated: ").append(toIndentedString(deprecated)).append("\n");
    sb.append("    deprecatedDate: ").append(toIndentedString(deprecatedDate)).append("\n");
    sb.append("    defaults: ").append(toIndentedString(defaults)).append("\n");
    sb.append("    purpose: ").append(toIndentedString(purpose)).append("\n");
    sb.append("    migrationSettings: ").append(toIndentedString(migrationSettings)).append("\n");
    sb.append("    environments: ").append(toIndentedString(environments)).append("\n");
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
    openapiFields.add("name");
    openapiFields.add("kind");
    openapiFields.add("description");
    openapiFields.add("key");
    openapiFields.add("_version");
    openapiFields.add("creationDate");
    openapiFields.add("includeInSnippet");
    openapiFields.add("clientSideAvailability");
    openapiFields.add("variations");
    openapiFields.add("temporary");
    openapiFields.add("tags");
    openapiFields.add("_links");
    openapiFields.add("maintainerId");
    openapiFields.add("_maintainer");
    openapiFields.add("maintainerTeamKey");
    openapiFields.add("_maintainerTeam");
    openapiFields.add("goalIds");
    openapiFields.add("experiments");
    openapiFields.add("customProperties");
    openapiFields.add("archived");
    openapiFields.add("archivedDate");
    openapiFields.add("deprecated");
    openapiFields.add("deprecatedDate");
    openapiFields.add("defaults");
    openapiFields.add("_purpose");
    openapiFields.add("migrationSettings");
    openapiFields.add("environments");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("kind");
    openapiRequiredFields.add("key");
    openapiRequiredFields.add("_version");
    openapiRequiredFields.add("creationDate");
    openapiRequiredFields.add("variations");
    openapiRequiredFields.add("temporary");
    openapiRequiredFields.add("tags");
    openapiRequiredFields.add("_links");
    openapiRequiredFields.add("experiments");
    openapiRequiredFields.add("customProperties");
    openapiRequiredFields.add("archived");
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to FeatureFlag
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (FeatureFlag.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in FeatureFlag is not found in the empty JSON string", FeatureFlag.openapiRequiredFields.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : FeatureFlag.openapiRequiredFields) {
        if (jsonObj.get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonObj.toString()));
        }
      }
      if (jsonObj.get("name") != null && !jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if (jsonObj.get("kind") != null && !jsonObj.get("kind").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `kind` to be a primitive type in the JSON string but got `%s`", jsonObj.get("kind").toString()));
      }
      if (jsonObj.get("description") != null && !jsonObj.get("description").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `description` to be a primitive type in the JSON string but got `%s`", jsonObj.get("description").toString()));
      }
      if (jsonObj.get("key") != null && !jsonObj.get("key").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `key` to be a primitive type in the JSON string but got `%s`", jsonObj.get("key").toString()));
      }
      // validate the optional field `clientSideAvailability`
      if (jsonObj.getAsJsonObject("clientSideAvailability") != null) {
        ClientSideAvailability.validateJsonObject(jsonObj.getAsJsonObject("clientSideAvailability"));
      }
      JsonArray jsonArrayvariations = jsonObj.getAsJsonArray("variations");
      if (jsonArrayvariations != null) {
        // ensure the json data is an array
        if (!jsonObj.get("variations").isJsonArray()) {
          throw new IllegalArgumentException(String.format("Expected the field `variations` to be an array in the JSON string but got `%s`", jsonObj.get("variations").toString()));
        }

        // validate the optional field `variations` (array)
        for (int i = 0; i < jsonArrayvariations.size(); i++) {
          Variation.validateJsonObject(jsonArrayvariations.get(i).getAsJsonObject());
        };
      }
      // ensure the json data is an array
      if (jsonObj.get("tags") != null && !jsonObj.get("tags").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `tags` to be an array in the JSON string but got `%s`", jsonObj.get("tags").toString()));
      }
      if (jsonObj.get("maintainerId") != null && !jsonObj.get("maintainerId").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `maintainerId` to be a primitive type in the JSON string but got `%s`", jsonObj.get("maintainerId").toString()));
      }
      // validate the optional field `_maintainer`
      if (jsonObj.getAsJsonObject("_maintainer") != null) {
        MemberSummary.validateJsonObject(jsonObj.getAsJsonObject("_maintainer"));
      }
      if (jsonObj.get("maintainerTeamKey") != null && !jsonObj.get("maintainerTeamKey").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `maintainerTeamKey` to be a primitive type in the JSON string but got `%s`", jsonObj.get("maintainerTeamKey").toString()));
      }
      // validate the optional field `_maintainerTeam`
      if (jsonObj.getAsJsonObject("_maintainerTeam") != null) {
        MaintainerTeam.validateJsonObject(jsonObj.getAsJsonObject("_maintainerTeam"));
      }
      // ensure the json data is an array
      if (jsonObj.get("goalIds") != null && !jsonObj.get("goalIds").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `goalIds` to be an array in the JSON string but got `%s`", jsonObj.get("goalIds").toString()));
      }
      // validate the optional field `experiments`
      if (jsonObj.getAsJsonObject("experiments") != null) {
        ExperimentInfoRep.validateJsonObject(jsonObj.getAsJsonObject("experiments"));
      }
      // validate the optional field `defaults`
      if (jsonObj.getAsJsonObject("defaults") != null) {
        Defaults.validateJsonObject(jsonObj.getAsJsonObject("defaults"));
      }
      if (jsonObj.get("_purpose") != null && !jsonObj.get("_purpose").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `_purpose` to be a primitive type in the JSON string but got `%s`", jsonObj.get("_purpose").toString()));
      }
      // validate the optional field `migrationSettings`
      if (jsonObj.getAsJsonObject("migrationSettings") != null) {
        FlagMigrationSettingsRep.validateJsonObject(jsonObj.getAsJsonObject("migrationSettings"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!FeatureFlag.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'FeatureFlag' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<FeatureFlag> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(FeatureFlag.class));

       return (TypeAdapter<T>) new TypeAdapter<FeatureFlag>() {
           @Override
           public void write(JsonWriter out, FeatureFlag value) throws IOException {
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
           public FeatureFlag read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             // store additional fields in the deserialized instance
             FeatureFlag instance = thisAdapter.fromJsonTree(jsonObj);
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
  * Create an instance of FeatureFlag given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of FeatureFlag
  * @throws IOException if the JSON string is invalid with respect to FeatureFlag
  */
  public static FeatureFlag fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, FeatureFlag.class);
  }

 /**
  * Convert an instance of FeatureFlag to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

