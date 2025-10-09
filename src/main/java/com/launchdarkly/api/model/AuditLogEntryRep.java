/*
 * LaunchDarkly REST API
 * This documentation describes LaunchDarkly's REST API. To access the complete OpenAPI spec directly, use [Get OpenAPI spec](https://launchdarkly.com/docs/api/other/get-openapi-spec).  To learn how to use LaunchDarkly using the user interface (UI) instead, read our [product documentation](https://launchdarkly.com/docs/home).  ## Authentication  LaunchDarkly's REST API uses the HTTPS protocol with a minimum TLS version of 1.2.  All REST API resources are authenticated with either [personal or service access tokens](https://launchdarkly.com/docs/home/account/api), or session cookies. Other authentication mechanisms are not supported. You can manage personal access tokens on your [**Authorization**](https://app.launchdarkly.com/settings/authorization) page in the LaunchDarkly UI.  LaunchDarkly also has SDK keys, mobile keys, and client-side IDs that are used by our server-side SDKs, mobile SDKs, and JavaScript-based SDKs, respectively. **These keys cannot be used to access our REST API**. These keys are environment-specific, and can only perform read-only operations such as fetching feature flag settings.  | Auth mechanism                                                                                  | Allowed resources                                                                                     | Use cases                                          | | ----------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------- | -------------------------------------------------- | | [Personal or service access tokens](https://launchdarkly.com/docs/home/account/api) | Can be customized on a per-token basis                                                                | Building scripts, custom integrations, data export. | | SDK keys                                                                                        | Can only access read-only resources specific to server-side SDKs. Restricted to a single environment. | Server-side SDKs                     | | Mobile keys                                                                                     | Can only access read-only resources specific to mobile SDKs, and only for flags marked available to mobile keys. Restricted to a single environment.           | Mobile SDKs                                        | | Client-side ID                                                                                  | Can only access read-only resources specific to JavaScript-based client-side SDKs, and only for flags marked available to client-side. Restricted to a single environment.           | Client-side JavaScript                             |  > #### Keep your access tokens and SDK keys private > > Access tokens should _never_ be exposed in untrusted contexts. Never put an access token in client-side JavaScript, or embed it in a mobile application. LaunchDarkly has special mobile keys that you can embed in mobile apps. If you accidentally expose an access token or SDK key, you can reset it from your [**Authorization**](https://app.launchdarkly.com/settings/authorization) page. > > The client-side ID is safe to embed in untrusted contexts. It's designed for use in client-side JavaScript.  ### Authentication using request header  The preferred way to authenticate with the API is by adding an `Authorization` header containing your access token to your requests. The value of the `Authorization` header must be your access token.  Manage personal access tokens from the [**Authorization**](https://app.launchdarkly.com/settings/authorization) page.  ### Authentication using session cookie  For testing purposes, you can make API calls directly from your web browser. If you are logged in to the LaunchDarkly application, the API will use your existing session to authenticate calls.  Depending on the permissions granted as part of your [role](https://launchdarkly.com/docs/home/account/roles), you may not have permission to perform some API calls. You will receive a `401` response code in that case.  > ### Modifying the Origin header causes an error > > LaunchDarkly validates that the Origin header for any API request authenticated by a session cookie matches the expected Origin header. The expected Origin header is `https://app.launchdarkly.com`. > > If the Origin header does not match what's expected, LaunchDarkly returns an error. This error can prevent the LaunchDarkly app from working correctly. > > Any browser extension that intentionally changes the Origin header can cause this problem. For example, the `Allow-Control-Allow-Origin: *` Chrome extension changes the Origin header to `http://evil.com` and causes the app to fail. > > To prevent this error, do not modify your Origin header. > > LaunchDarkly does not require origin matching when authenticating with an access token, so this issue does not affect normal API usage.  ## Representations  All resources expect and return JSON response bodies. Error responses also send a JSON body. To learn more about the error format of the API, read [Errors](https://launchdarkly.com/docs/api#errors).  In practice this means that you always get a response with a `Content-Type` header set to `application/json`.  In addition, request bodies for `PATCH`, `POST`, and `PUT` requests must be encoded as JSON with a `Content-Type` header set to `application/json`.  ### Summary and detailed representations  When you fetch a list of resources, the response includes only the most important attributes of each resource. This is a _summary representation_ of the resource. When you fetch an individual resource, such as a single feature flag, you receive a _detailed representation_ of the resource.  The best way to find a detailed representation is to follow links. Every summary representation includes a link to its detailed representation.  ### Expanding responses  Sometimes the detailed representation of a resource does not include all of the attributes of the resource by default. If this is the case, the request method will clearly document this and describe which attributes you can include in an expanded response.  To include the additional attributes, append the `expand` request parameter to your request and add a comma-separated list of the attributes to include. For example, when you append `?expand=members,maintainers` to the [Get team](https://launchdarkly.com/docs/api/teams/get-team) endpoint, the expanded response includes both of these attributes.  ### Links and addressability  The best way to navigate the API is by following links. These are attributes in representations that link to other resources. The API always uses the same format for links:  - Links to other resources within the API are encapsulated in a `_links` object - If the resource has a corresponding link to HTML content on the site, it is stored in a special `_site` link  Each link has two attributes:  - An `href`, which contains the URL - A `type`, which describes the content type  For example, a feature resource might return the following:  ```json {   \"_links\": {     \"parent\": {       \"href\": \"/api/features\",       \"type\": \"application/json\"     },     \"self\": {       \"href\": \"/api/features/sort.order\",       \"type\": \"application/json\"     }   },   \"_site\": {     \"href\": \"/features/sort.order\",     \"type\": \"text/html\"   } } ```  From this, you can navigate to the parent collection of features by following the `parent` link, or navigate to the site page for the feature by following the `_site` link.  Collections are always represented as a JSON object with an `items` attribute containing an array of representations. Like all other representations, collections have `_links` defined at the top level.  Paginated collections include `first`, `last`, `next`, and `prev` links containing a URL with the respective set of elements in the collection.  ## Updates  Resources that accept partial updates use the `PATCH` verb. Most resources support the [JSON patch](https://launchdarkly.com/docs/api#updates-using-json-patch) format. Some resources also support the [JSON merge patch](https://launchdarkly.com/docs/api#updates-using-json-merge-patch) format, and some resources support the [semantic patch](https://launchdarkly.com/docs/api#updates-using-semantic-patch) format, which is a way to specify the modifications to perform as a set of executable instructions. Each resource supports optional [comments](https://launchdarkly.com/docs/api#updates-with-comments) that you can submit with updates. Comments appear in outgoing webhooks, the audit log, and other integrations.  When a resource supports both JSON patch and semantic patch, we document both in the request method. However, the specific request body fields and descriptions included in our documentation only match one type of patch or the other.  ### Updates using JSON patch  [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) is a way to specify the modifications to perform on a resource. JSON patch uses paths and a limited set of operations to describe how to transform the current state of the resource into a new state. JSON patch documents are always arrays, where each element contains an operation, a path to the field to update, and the new value.  For example, in this feature flag representation:  ```json {     \"name\": \"New recommendations engine\",     \"key\": \"engine.enable\",     \"description\": \"This is the description\",     ... } ``` You can change the feature flag's description with the following patch document:  ```json [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"This is the new description\" }] ```  You can specify multiple modifications to perform in a single request. You can also test that certain preconditions are met before applying the patch:  ```json [   { \"op\": \"test\", \"path\": \"/version\", \"value\": 10 },   { \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" } ] ```  The above patch request tests whether the feature flag's `version` is `10`, and if so, changes the feature flag's description.  Attributes that are not editable, such as a resource's `_links`, have names that start with an underscore.  ### Updates using JSON merge patch  [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) is another format for specifying the modifications to perform on a resource. JSON merge patch is less expressive than JSON patch. However, in many cases it is simpler to construct a merge patch document. For example, you can change a feature flag's description with the following merge patch document:  ```json {   \"description\": \"New flag description\" } ```  ### Updates using semantic patch  Some resources support the semantic patch format. A semantic patch is a way to specify the modifications to perform on a resource as a set of executable instructions.  Semantic patch allows you to be explicit about intent using precise, custom instructions. In many cases, you can define semantic patch instructions independently of the current state of the resource. This can be useful when defining a change that may be applied at a future date.  To make a semantic patch request, you must append `domain-model=launchdarkly.semanticpatch` to your `Content-Type` header.  Here's how:  ``` Content-Type: application/json; domain-model=launchdarkly.semanticpatch ```  If you call a semantic patch resource without this header, you will receive a `400` response because your semantic patch will be interpreted as a JSON patch.  The body of a semantic patch request takes the following properties:  * `comment` (string): (Optional) A description of the update. * `environmentKey` (string): (Required for some resources only) The environment key. * `instructions` (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a `kind` property that indicates the instruction. If the instruction requires parameters, you must include those parameters as additional fields in the object. The documentation for each resource that supports semantic patch includes the available instructions and any additional parameters.  For example:  ```json {   \"comment\": \"optional comment\",   \"instructions\": [ {\"kind\": \"turnFlagOn\"} ] } ```  Semantic patches are not applied partially; either all of the instructions are applied or none of them are. If **any** instruction is invalid, the endpoint returns an error and will not change the resource. If all instructions are valid, the request succeeds and the resources are updated if necessary, or left unchanged if they are already in the state you request.  ### Updates with comments  You can submit optional comments with `PATCH` changes.  To submit a comment along with a JSON patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"patch\": [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" }] } ```  To submit a comment along with a JSON merge patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"merge\": { \"description\": \"New flag description\" } } ```  To submit a comment along with a semantic patch, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"instructions\": [ {\"kind\": \"turnFlagOn\"} ] } ```  ## Errors  The API always returns errors in a common format. Here's an example:  ```json {   \"code\": \"invalid_request\",   \"message\": \"A feature with that key already exists\",   \"id\": \"30ce6058-87da-11e4-b116-123b93f75cba\" } ```  The `code` indicates the general class of error. The `message` is a human-readable explanation of what went wrong. The `id` is a unique identifier. Use it when you're working with LaunchDarkly Support to debug a problem with a specific API call.  ### HTTP status error response codes  | Code | Definition        | Description                                                                                       | Possible Solution                                                | | ---- | ----------------- | ------------------------------------------------------------------------------------------- | ---------------------------------------------------------------- | | 400  | Invalid request       | The request cannot be understood.                                    | Ensure JSON syntax in request body is correct.                   | | 401  | Invalid access token      | Requestor is unauthorized or does not have permission for this API call.                                                | Ensure your API access token is valid and has the appropriate permissions.                                     | | 403  | Forbidden         | Requestor does not have access to this resource.                                                | Ensure that the account member or access token has proper permissions set. | | 404  | Invalid resource identifier | The requested resource is not valid. | Ensure that the resource is correctly identified by ID or key. | | 405  | Method not allowed | The request method is not allowed on this resource. | Ensure that the HTTP verb is correct. | | 409  | Conflict          | The API request can not be completed because it conflicts with a concurrent API request. | Retry your request.                                              | | 422  | Unprocessable entity | The API request can not be completed because the update description can not be understood. | Ensure that the request body is correct for the type of patch you are using, either JSON patch or semantic patch. | 429  | Too many requests | Read [Rate limiting](https://launchdarkly.com/docs/api#rate-limiting).                                               | Wait and try again later.                                        |  ## CORS  The LaunchDarkly API supports Cross Origin Resource Sharing (CORS) for AJAX requests from any origin. If an `Origin` header is given in a request, it will be echoed as an explicitly allowed origin. Otherwise the request returns a wildcard, `Access-Control-Allow-Origin: *`. For more information on CORS, read the [CORS W3C Recommendation](http://www.w3.org/TR/cors). Example CORS headers might look like:  ```http Access-Control-Allow-Headers: Accept, Content-Type, Content-Length, Accept-Encoding, Authorization Access-Control-Allow-Methods: OPTIONS, GET, DELETE, PATCH Access-Control-Allow-Origin: * Access-Control-Max-Age: 300 ```  You can make authenticated CORS calls just as you would make same-origin calls, using either [token or session-based authentication](https://launchdarkly.com/docs/api#authentication). If you are using session authentication, you should set the `withCredentials` property for your `xhr` request to `true`. You should never expose your access tokens to untrusted entities.  ## Rate limiting  We use several rate limiting strategies to ensure the availability of our APIs. Rate-limited calls to our APIs return a `429` status code. Calls to our APIs include headers indicating the current rate limit status. The specific headers returned depend on the API route being called. The limits differ based on the route, authentication mechanism, and other factors. Routes that are not rate limited may not contain any of the headers described below.  > ### Rate limiting and SDKs > > LaunchDarkly SDKs are never rate limited and do not use the API endpoints defined here. LaunchDarkly uses a different set of approaches, including streaming/server-sent events and a global CDN, to ensure availability to the routes used by LaunchDarkly SDKs.  ### Global rate limits  Authenticated requests are subject to a global limit. This is the maximum number of calls that your account can make to the API per ten seconds. All service and personal access tokens on the account share this limit, so exceeding the limit with one access token will impact other tokens. Calls that are subject to global rate limits may return the headers below:  | Header name                    | Description                                                                      | | ------------------------------ | -------------------------------------------------------------------------------- | | `X-Ratelimit-Global-Remaining` | The maximum number of requests the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`            | The time at which the current rate limit window resets in epoch milliseconds.    |  We do not publicly document the specific number of calls that can be made globally. This limit may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limit.  ### Route-level rate limits  Some authenticated routes have custom rate limits. These also reset every ten seconds. Any service or personal access tokens hitting the same route share this limit, so exceeding the limit with one access token may impact other tokens. Calls that are subject to route-level rate limits return the headers below:  | Header name                   | Description                                                                                           | | ----------------------------- | ----------------------------------------------------------------------------------------------------- | | `X-Ratelimit-Route-Remaining` | The maximum number of requests to the current route the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`           | The time at which the current rate limit window resets in epoch milliseconds.                         |  A _route_ represents a specific URL pattern and verb. For example, the [Delete environment](https://launchdarkly.com/docs/api/environments/delete-environment) endpoint is considered a single route, and each call to delete an environment counts against your route-level rate limit for that route.  We do not publicly document the specific number of calls that an account can make to each endpoint per ten seconds. These limits may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limits.  ### IP-based rate limiting  We also employ IP-based rate limiting on some API routes. If you hit an IP-based rate limit, your API response will include a `Retry-After` header indicating how long to wait before re-trying the call. Clients must wait at least `Retry-After` seconds before making additional calls to our API, and should employ jitter and backoff strategies to avoid triggering rate limits again.  ## OpenAPI (Swagger) and client libraries  We have a [complete OpenAPI (Swagger) specification](https://app.launchdarkly.com/api/v2/openapi.json) for our API.  We auto-generate multiple client libraries based on our OpenAPI specification. To learn more, visit the [collection of client libraries on GitHub](https://github.com/search?q=topic%3Alaunchdarkly-api+org%3Alaunchdarkly&type=Repositories). You can also use this specification to generate client libraries to interact with our REST API in your language of choice.  Our OpenAPI specification is supported by several API-based tools such as Postman and Insomnia. In many cases, you can directly import our specification to explore our APIs.  ## Method overriding  Some firewalls and HTTP clients restrict the use of verbs other than `GET` and `POST`. In those environments, our API endpoints that use `DELETE`, `PATCH`, and `PUT` verbs are inaccessible.  To avoid this issue, our API supports the `X-HTTP-Method-Override` header, allowing clients to \"tunnel\" `DELETE`, `PATCH`, and `PUT` requests using a `POST` request.  For example, to call a `PATCH` endpoint using a `POST` request, you can include `X-HTTP-Method-Override:PATCH` as a header.  ## Beta resources  We sometimes release new API resources in **beta** status before we release them with general availability.  Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  We try to promote resources into general availability as quickly as possible. This happens after sufficient testing and when we're satisfied that we no longer need to make backwards-incompatible changes.  We mark beta resources with a \"Beta\" callout in our documentation, pictured below:  > ### This feature is in beta > > To use this feature, pass in a header including the `LD-API-Version` key with value set to `beta`. Use this header with each call. To learn more, read [Beta resources](https://launchdarkly.com/docs/api#beta-resources). > > Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  ### Using beta resources  To use a beta resource, you must include a header in the request. If you call a beta resource without this header, you receive a `403` response.  Use this header:  ``` LD-API-Version: beta ```  ## Federal and EU environments  In addition to the commercial versions, LaunchDarkly offers instances for federal agencies and those based in the European Union (EU).  ### Federal environments  The version of LaunchDarkly that is available on domains controlled by the United States government is different from the version of LaunchDarkly available to the general public. If you are an employee or contractor for a United States federal agency and use LaunchDarkly in your work, you likely use the federal instance of LaunchDarkly.  If you are working in the federal instance of LaunchDarkly, the base URI for each request is `https://app.launchdarkly.us`.  To learn more, read [LaunchDarkly in federal environments](https://launchdarkly.com/docs/home/infrastructure/federal).  ### EU environments  The version of LaunchDarkly that is available in the EU is different from the version of LaunchDarkly available to other regions. If you are based in the EU, you likely use the EU instance of LaunchDarkly. The LaunchDarkly EU instance complies with EU data residency principles, including the protection and confidentiality of EU customer information.  If you are working in the EU instance of LaunchDarkly, the base URI for each request is `https://app.eu.launchdarkly.com`.  To learn more, read [LaunchDarkly in the European Union (EU)](https://launchdarkly.com/docs/home/infrastructure/eu).  ## Versioning  We try hard to keep our REST API backwards compatible, but we occasionally have to make backwards-incompatible changes in the process of shipping new features. These breaking changes can cause unexpected behavior if you don't prepare for them accordingly.  Updates to our REST API include support for the latest features in LaunchDarkly. We also release a new version of our REST API every time we make a breaking change. We provide simultaneous support for multiple API versions so you can migrate from your current API version to a new version at your own pace.  ### Setting the API version per request  You can set the API version on a specific request by sending an `LD-API-Version` header, as shown in the example below:  ``` LD-API-Version: 20240415 ```  The header value is the version number of the API version you would like to request. The number for each version corresponds to the date the version was released in `yyyymmdd` format. In the example above the version `20240415` corresponds to April 15, 2024.  ### Setting the API version per access token  When you create an access token, you must specify a specific version of the API to use. This ensures that integrations using this token cannot be broken by version changes.  Tokens created before versioning was released have their version set to `20160426`, which is the version of the API that existed before the current versioning scheme, so that they continue working the same way they did before versioning.  If you would like to upgrade your integration to use a new API version, you can explicitly set the header described above.  > ### Best practice: Set the header for every client or integration > > We recommend that you set the API version header explicitly in any client or integration you build. > > Only rely on the access token API version during manual testing.  ### API version changelog  <table>   <tr>     <th>Version</th>     <th>Changes</th>     <th>End of life (EOL)</th>   </tr>   <tr>     <td>`20240415`</td>     <td>       <ul><li>Changed several endpoints from unpaginated to paginated. Use the `limit` and `offset` query parameters to page through the results.</li> <li>Changed the [list access tokens](https://launchdarkly.com/docs/api/access-tokens/get-tokens) endpoint: <ul><li>Response is now paginated with a default limit of `25`</li></ul></li> <li>Changed the [list account members](https://launchdarkly.com/docs/api/account-members/get-members) endpoint: <ul><li>The `accessCheck` filter is no longer available</li></ul></li> <li>Changed the [list custom roles](https://launchdarkly.com/docs/api/custom-roles/get-custom-roles) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li></ul></li> <li>Changed the [list feature flags](https://launchdarkly.com/docs/api/feature-flags/get-feature-flags) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li><li>The `environments` field is now only returned if the request is filtered by environment, using the `filterEnv` query parameter</li><li>The `followerId`, `hasDataExport`, `status`, `contextKindTargeted`, and `segmentTargeted` filters are no longer available</li><li>The `compare` query parameter is no longer available</li></ul></li> <li>Changed the [list segments](https://launchdarkly.com/docs/api/segments/get-segments) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li></ul></li> <li>Changed the [list teams](https://launchdarkly.com/docs/api/teams/get-teams) endpoint: <ul><li>The `expand` parameter no longer supports including `projects` or `roles`</li><li>In paginated results, the maximum page size is now 100</li></ul></li> <li>Changed the [get workflows](https://launchdarkly.com/docs/api/workflows/get-workflows) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li><li>The `_conflicts` field in the response is no longer available</li></ul></li> </ul>     </td>     <td>Current</td>   </tr>   <tr>     <td>`20220603`</td>     <td>       <ul><li>Changed the [list projects](https://launchdarkly.com/docs/api/projects/get-projects) return value:<ul><li>Response is now paginated with a default limit of `20`.</li><li>Added support for filter and sort.</li><li>The project `environments` field is now expandable. This field is omitted by default.</li></ul></li><li>Changed the [get project](https://launchdarkly.com/docs/api/projects/get-project) return value:<ul><li>The `environments` field is now expandable. This field is omitted by default.</li></ul></li></ul>     </td>     <td>2025-04-15</td>   </tr>   <tr>     <td>`20210729`</td>     <td>       <ul><li>Changed the [create approval request](https://launchdarkly.com/docs/api/approvals/post-approval-request) return value. It now returns HTTP Status Code `201` instead of `200`.</li><li> Changed the [get user](https://launchdarkly.com/docs/api/users/get-user) return value. It now returns a user record, not a user. </li><li>Added additional optional fields to environment, segments, flags, members, and segments, including the ability to create big segments. </li><li> Added default values for flag variations when new environments are created. </li><li>Added filtering and pagination for getting flags and members, including `limit`, `number`, `filter`, and `sort` query parameters. </li><li>Added endpoints for expiring user targets for flags and segments, scheduled changes, access tokens, Relay Proxy configuration, integrations and subscriptions, and approvals. </li></ul>     </td>     <td>2023-06-03</td>   </tr>   <tr>     <td>`20191212`</td>     <td>       <ul><li>[List feature flags](https://launchdarkly.com/docs/api/feature-flags/get-feature-flags) now defaults to sending summaries of feature flag configurations, equivalent to setting the query parameter `summary=true`. Summaries omit flag targeting rules and individual user targets from the payload. </li><li> Added endpoints for flags, flag status, projects, environments, audit logs, members, users, custom roles, segments, usage, streams, events, and data export. </li></ul>     </td>     <td>2022-07-29</td>   </tr>   <tr>     <td>`20160426`</td>     <td>       <ul><li>Initial versioning of API. Tokens created before versioning have their version set to this.</li></ul>     </td>     <td>2020-12-12</td>   </tr> </table>  To learn more about how EOL is determined, read LaunchDarkly's [End of Life (EOL) Policy](https://launchdarkly.com/policies/end-of-life-policy/). 
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
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.launchdarkly.api.model.AuditLogEntryListingRep;
import com.launchdarkly.api.model.AuthorizedAppDataRep;
import com.launchdarkly.api.model.Link;
import com.launchdarkly.api.model.MemberDataRep;
import com.launchdarkly.api.model.ParentResourceRep;
import com.launchdarkly.api.model.ResourceAccess;
import com.launchdarkly.api.model.SubjectDataRep;
import com.launchdarkly.api.model.TargetResourceRep;
import com.launchdarkly.api.model.TokenSummary;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openapitools.jackson.nullable.JsonNullable;

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
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.launchdarkly.api.JSON;

/**
 * AuditLogEntryRep
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-10-09T16:56:19.516161Z[Etc/UTC]", comments = "Generator version: 7.5.0")
public class AuditLogEntryRep {
  public static final String SERIALIZED_NAME_LINKS = "_links";
  @SerializedName(SERIALIZED_NAME_LINKS)
  private Map<String, Link> links = new HashMap<>();

  public static final String SERIALIZED_NAME_ID = "_id";
  @SerializedName(SERIALIZED_NAME_ID)
  private String id;

  public static final String SERIALIZED_NAME_ACCOUNT_ID = "_accountId";
  @SerializedName(SERIALIZED_NAME_ACCOUNT_ID)
  private String accountId;

  public static final String SERIALIZED_NAME_DATE = "date";
  @SerializedName(SERIALIZED_NAME_DATE)
  private Long date;

  public static final String SERIALIZED_NAME_ACCESSES = "accesses";
  @SerializedName(SERIALIZED_NAME_ACCESSES)
  private List<ResourceAccess> accesses = new ArrayList<>();

  public static final String SERIALIZED_NAME_KIND = "kind";
  @SerializedName(SERIALIZED_NAME_KIND)
  private String kind;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_SHORT_DESCRIPTION = "shortDescription";
  @SerializedName(SERIALIZED_NAME_SHORT_DESCRIPTION)
  private String shortDescription;

  public static final String SERIALIZED_NAME_COMMENT = "comment";
  @SerializedName(SERIALIZED_NAME_COMMENT)
  private String comment;

  public static final String SERIALIZED_NAME_SUBJECT = "subject";
  @SerializedName(SERIALIZED_NAME_SUBJECT)
  private SubjectDataRep subject;

  public static final String SERIALIZED_NAME_MEMBER = "member";
  @SerializedName(SERIALIZED_NAME_MEMBER)
  private MemberDataRep member;

  public static final String SERIALIZED_NAME_TOKEN = "token";
  @SerializedName(SERIALIZED_NAME_TOKEN)
  private TokenSummary token;

  public static final String SERIALIZED_NAME_APP = "app";
  @SerializedName(SERIALIZED_NAME_APP)
  private AuthorizedAppDataRep app;

  public static final String SERIALIZED_NAME_TITLE_VERB = "titleVerb";
  @SerializedName(SERIALIZED_NAME_TITLE_VERB)
  private String titleVerb;

  public static final String SERIALIZED_NAME_TITLE = "title";
  @SerializedName(SERIALIZED_NAME_TITLE)
  private String title;

  public static final String SERIALIZED_NAME_TARGET = "target";
  @SerializedName(SERIALIZED_NAME_TARGET)
  private TargetResourceRep target;

  public static final String SERIALIZED_NAME_PARENT = "parent";
  @SerializedName(SERIALIZED_NAME_PARENT)
  private ParentResourceRep parent;

  public static final String SERIALIZED_NAME_DELTA = "delta";
  @SerializedName(SERIALIZED_NAME_DELTA)
  private Object delta = null;

  public static final String SERIALIZED_NAME_TRIGGER_BODY = "triggerBody";
  @SerializedName(SERIALIZED_NAME_TRIGGER_BODY)
  private Object triggerBody = null;

  public static final String SERIALIZED_NAME_MERGE = "merge";
  @SerializedName(SERIALIZED_NAME_MERGE)
  private Object merge = null;

  public static final String SERIALIZED_NAME_PREVIOUS_VERSION = "previousVersion";
  @SerializedName(SERIALIZED_NAME_PREVIOUS_VERSION)
  private Object previousVersion = null;

  public static final String SERIALIZED_NAME_CURRENT_VERSION = "currentVersion";
  @SerializedName(SERIALIZED_NAME_CURRENT_VERSION)
  private Object currentVersion = null;

  public static final String SERIALIZED_NAME_SUBENTRIES = "subentries";
  @SerializedName(SERIALIZED_NAME_SUBENTRIES)
  private List<AuditLogEntryListingRep> subentries = new ArrayList<>();

  public AuditLogEntryRep() {
  }

  public AuditLogEntryRep links(Map<String, Link> links) {
    this.links = links;
    return this;
  }

  public AuditLogEntryRep putLinksItem(String key, Link linksItem) {
    if (this.links == null) {
      this.links = new HashMap<>();
    }
    this.links.put(key, linksItem);
    return this;
  }

   /**
   * The location and content type of related resources
   * @return links
  **/
  @javax.annotation.Nonnull
  public Map<String, Link> getLinks() {
    return links;
  }

  public void setLinks(Map<String, Link> links) {
    this.links = links;
  }


  public AuditLogEntryRep id(String id) {
    this.id = id;
    return this;
  }

   /**
   * The ID of the audit log entry
   * @return id
  **/
  @javax.annotation.Nonnull
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public AuditLogEntryRep accountId(String accountId) {
    this.accountId = accountId;
    return this;
  }

   /**
   * The ID of the account to which this audit log entry belongs
   * @return accountId
  **/
  @javax.annotation.Nonnull
  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }


  public AuditLogEntryRep date(Long date) {
    this.date = date;
    return this;
  }

   /**
   * Get date
   * @return date
  **/
  @javax.annotation.Nonnull
  public Long getDate() {
    return date;
  }

  public void setDate(Long date) {
    this.date = date;
  }


  public AuditLogEntryRep accesses(List<ResourceAccess> accesses) {
    this.accesses = accesses;
    return this;
  }

  public AuditLogEntryRep addAccessesItem(ResourceAccess accessesItem) {
    if (this.accesses == null) {
      this.accesses = new ArrayList<>();
    }
    this.accesses.add(accessesItem);
    return this;
  }

   /**
   * Details on the actions performed and resources acted on in this audit log entry
   * @return accesses
  **/
  @javax.annotation.Nonnull
  public List<ResourceAccess> getAccesses() {
    return accesses;
  }

  public void setAccesses(List<ResourceAccess> accesses) {
    this.accesses = accesses;
  }


  public AuditLogEntryRep kind(String kind) {
    this.kind = kind;
    return this;
  }

   /**
   * Get kind
   * @return kind
  **/
  @javax.annotation.Nonnull
  public String getKind() {
    return kind;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }


  public AuditLogEntryRep name(String name) {
    this.name = name;
    return this;
  }

   /**
   * The name of the resource this audit log entry refers to
   * @return name
  **/
  @javax.annotation.Nonnull
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public AuditLogEntryRep description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Description of the change recorded in the audit log entry
   * @return description
  **/
  @javax.annotation.Nonnull
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public AuditLogEntryRep shortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
    return this;
  }

   /**
   * Shorter version of the change recorded in the audit log entry
   * @return shortDescription
  **/
  @javax.annotation.Nonnull
  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }


  public AuditLogEntryRep comment(String comment) {
    this.comment = comment;
    return this;
  }

   /**
   * Optional comment for the audit log entry
   * @return comment
  **/
  @javax.annotation.Nullable
  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }


  public AuditLogEntryRep subject(SubjectDataRep subject) {
    this.subject = subject;
    return this;
  }

   /**
   * Get subject
   * @return subject
  **/
  @javax.annotation.Nullable
  public SubjectDataRep getSubject() {
    return subject;
  }

  public void setSubject(SubjectDataRep subject) {
    this.subject = subject;
  }


  public AuditLogEntryRep member(MemberDataRep member) {
    this.member = member;
    return this;
  }

   /**
   * Get member
   * @return member
  **/
  @javax.annotation.Nullable
  public MemberDataRep getMember() {
    return member;
  }

  public void setMember(MemberDataRep member) {
    this.member = member;
  }


  public AuditLogEntryRep token(TokenSummary token) {
    this.token = token;
    return this;
  }

   /**
   * Get token
   * @return token
  **/
  @javax.annotation.Nullable
  public TokenSummary getToken() {
    return token;
  }

  public void setToken(TokenSummary token) {
    this.token = token;
  }


  public AuditLogEntryRep app(AuthorizedAppDataRep app) {
    this.app = app;
    return this;
  }

   /**
   * Get app
   * @return app
  **/
  @javax.annotation.Nullable
  public AuthorizedAppDataRep getApp() {
    return app;
  }

  public void setApp(AuthorizedAppDataRep app) {
    this.app = app;
  }


  public AuditLogEntryRep titleVerb(String titleVerb) {
    this.titleVerb = titleVerb;
    return this;
  }

   /**
   * The action and resource recorded in this audit log entry
   * @return titleVerb
  **/
  @javax.annotation.Nullable
  public String getTitleVerb() {
    return titleVerb;
  }

  public void setTitleVerb(String titleVerb) {
    this.titleVerb = titleVerb;
  }


  public AuditLogEntryRep title(String title) {
    this.title = title;
    return this;
  }

   /**
   * A description of what occurred, in the format &lt;code&gt;member&lt;/code&gt; &lt;code&gt;titleVerb&lt;/code&gt; &lt;code&gt;target&lt;/code&gt;
   * @return title
  **/
  @javax.annotation.Nullable
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public AuditLogEntryRep target(TargetResourceRep target) {
    this.target = target;
    return this;
  }

   /**
   * Get target
   * @return target
  **/
  @javax.annotation.Nullable
  public TargetResourceRep getTarget() {
    return target;
  }

  public void setTarget(TargetResourceRep target) {
    this.target = target;
  }


  public AuditLogEntryRep parent(ParentResourceRep parent) {
    this.parent = parent;
    return this;
  }

   /**
   * Get parent
   * @return parent
  **/
  @javax.annotation.Nullable
  public ParentResourceRep getParent() {
    return parent;
  }

  public void setParent(ParentResourceRep parent) {
    this.parent = parent;
  }


  public AuditLogEntryRep delta(Object delta) {
    this.delta = delta;
    return this;
  }

   /**
   * If the audit log entry has been updated, this is the JSON patch body that was used in the request to update the entity
   * @return delta
  **/
  @javax.annotation.Nullable
  public Object getDelta() {
    return delta;
  }

  public void setDelta(Object delta) {
    this.delta = delta;
  }


  public AuditLogEntryRep triggerBody(Object triggerBody) {
    this.triggerBody = triggerBody;
    return this;
  }

   /**
   * A JSON representation of the external trigger for this audit log entry, if any
   * @return triggerBody
  **/
  @javax.annotation.Nullable
  public Object getTriggerBody() {
    return triggerBody;
  }

  public void setTriggerBody(Object triggerBody) {
    this.triggerBody = triggerBody;
  }


  public AuditLogEntryRep merge(Object merge) {
    this.merge = merge;
    return this;
  }

   /**
   * A JSON representation of the merge information for this audit log entry, if any
   * @return merge
  **/
  @javax.annotation.Nullable
  public Object getMerge() {
    return merge;
  }

  public void setMerge(Object merge) {
    this.merge = merge;
  }


  public AuditLogEntryRep previousVersion(Object previousVersion) {
    this.previousVersion = previousVersion;
    return this;
  }

   /**
   * If the audit log entry has been updated, this is a JSON representation of the previous version of the entity
   * @return previousVersion
  **/
  @javax.annotation.Nullable
  public Object getPreviousVersion() {
    return previousVersion;
  }

  public void setPreviousVersion(Object previousVersion) {
    this.previousVersion = previousVersion;
  }


  public AuditLogEntryRep currentVersion(Object currentVersion) {
    this.currentVersion = currentVersion;
    return this;
  }

   /**
   * If the audit log entry has been updated, this is a JSON representation of the current version of the entity
   * @return currentVersion
  **/
  @javax.annotation.Nullable
  public Object getCurrentVersion() {
    return currentVersion;
  }

  public void setCurrentVersion(Object currentVersion) {
    this.currentVersion = currentVersion;
  }


  public AuditLogEntryRep subentries(List<AuditLogEntryListingRep> subentries) {
    this.subentries = subentries;
    return this;
  }

  public AuditLogEntryRep addSubentriesItem(AuditLogEntryListingRep subentriesItem) {
    if (this.subentries == null) {
      this.subentries = new ArrayList<>();
    }
    this.subentries.add(subentriesItem);
    return this;
  }

   /**
   * Get subentries
   * @return subentries
  **/
  @javax.annotation.Nullable
  public List<AuditLogEntryListingRep> getSubentries() {
    return subentries;
  }

  public void setSubentries(List<AuditLogEntryListingRep> subentries) {
    this.subentries = subentries;
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
   *
   * @param key name of the property
   * @param value value of the property
   * @return the AuditLogEntryRep instance itself
   */
  public AuditLogEntryRep putAdditionalProperty(String key, Object value) {
    if (this.additionalProperties == null) {
        this.additionalProperties = new HashMap<String, Object>();
    }
    this.additionalProperties.put(key, value);
    return this;
  }

  /**
   * Return the additional (undeclared) property.
   *
   * @return a map of objects
   */
  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }

  /**
   * Return the additional (undeclared) property with the specified name.
   *
   * @param key name of the property
   * @return an object
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
    AuditLogEntryRep auditLogEntryRep = (AuditLogEntryRep) o;
    return Objects.equals(this.links, auditLogEntryRep.links) &&
        Objects.equals(this.id, auditLogEntryRep.id) &&
        Objects.equals(this.accountId, auditLogEntryRep.accountId) &&
        Objects.equals(this.date, auditLogEntryRep.date) &&
        Objects.equals(this.accesses, auditLogEntryRep.accesses) &&
        Objects.equals(this.kind, auditLogEntryRep.kind) &&
        Objects.equals(this.name, auditLogEntryRep.name) &&
        Objects.equals(this.description, auditLogEntryRep.description) &&
        Objects.equals(this.shortDescription, auditLogEntryRep.shortDescription) &&
        Objects.equals(this.comment, auditLogEntryRep.comment) &&
        Objects.equals(this.subject, auditLogEntryRep.subject) &&
        Objects.equals(this.member, auditLogEntryRep.member) &&
        Objects.equals(this.token, auditLogEntryRep.token) &&
        Objects.equals(this.app, auditLogEntryRep.app) &&
        Objects.equals(this.titleVerb, auditLogEntryRep.titleVerb) &&
        Objects.equals(this.title, auditLogEntryRep.title) &&
        Objects.equals(this.target, auditLogEntryRep.target) &&
        Objects.equals(this.parent, auditLogEntryRep.parent) &&
        Objects.equals(this.delta, auditLogEntryRep.delta) &&
        Objects.equals(this.triggerBody, auditLogEntryRep.triggerBody) &&
        Objects.equals(this.merge, auditLogEntryRep.merge) &&
        Objects.equals(this.previousVersion, auditLogEntryRep.previousVersion) &&
        Objects.equals(this.currentVersion, auditLogEntryRep.currentVersion) &&
        Objects.equals(this.subentries, auditLogEntryRep.subentries)&&
        Objects.equals(this.additionalProperties, auditLogEntryRep.additionalProperties);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(links, id, accountId, date, accesses, kind, name, description, shortDescription, comment, subject, member, token, app, titleVerb, title, target, parent, delta, triggerBody, merge, previousVersion, currentVersion, subentries, additionalProperties);
  }

  private static <T> int hashCodeNullable(JsonNullable<T> a) {
    if (a == null) {
      return 1;
    }
    return a.isPresent() ? Arrays.deepHashCode(new Object[]{a.get()}) : 31;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuditLogEntryRep {\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    accountId: ").append(toIndentedString(accountId)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    accesses: ").append(toIndentedString(accesses)).append("\n");
    sb.append("    kind: ").append(toIndentedString(kind)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    shortDescription: ").append(toIndentedString(shortDescription)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
    sb.append("    member: ").append(toIndentedString(member)).append("\n");
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("    app: ").append(toIndentedString(app)).append("\n");
    sb.append("    titleVerb: ").append(toIndentedString(titleVerb)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    target: ").append(toIndentedString(target)).append("\n");
    sb.append("    parent: ").append(toIndentedString(parent)).append("\n");
    sb.append("    delta: ").append(toIndentedString(delta)).append("\n");
    sb.append("    triggerBody: ").append(toIndentedString(triggerBody)).append("\n");
    sb.append("    merge: ").append(toIndentedString(merge)).append("\n");
    sb.append("    previousVersion: ").append(toIndentedString(previousVersion)).append("\n");
    sb.append("    currentVersion: ").append(toIndentedString(currentVersion)).append("\n");
    sb.append("    subentries: ").append(toIndentedString(subentries)).append("\n");
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
    openapiFields.add("_links");
    openapiFields.add("_id");
    openapiFields.add("_accountId");
    openapiFields.add("date");
    openapiFields.add("accesses");
    openapiFields.add("kind");
    openapiFields.add("name");
    openapiFields.add("description");
    openapiFields.add("shortDescription");
    openapiFields.add("comment");
    openapiFields.add("subject");
    openapiFields.add("member");
    openapiFields.add("token");
    openapiFields.add("app");
    openapiFields.add("titleVerb");
    openapiFields.add("title");
    openapiFields.add("target");
    openapiFields.add("parent");
    openapiFields.add("delta");
    openapiFields.add("triggerBody");
    openapiFields.add("merge");
    openapiFields.add("previousVersion");
    openapiFields.add("currentVersion");
    openapiFields.add("subentries");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("_links");
    openapiRequiredFields.add("_id");
    openapiRequiredFields.add("_accountId");
    openapiRequiredFields.add("date");
    openapiRequiredFields.add("accesses");
    openapiRequiredFields.add("kind");
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("description");
    openapiRequiredFields.add("shortDescription");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to AuditLogEntryRep
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!AuditLogEntryRep.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in AuditLogEntryRep is not found in the empty JSON string", AuditLogEntryRep.openapiRequiredFields.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : AuditLogEntryRep.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("_id").toString()));
      }
      if (!jsonObj.get("_accountId").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `_accountId` to be a primitive type in the JSON string but got `%s`", jsonObj.get("_accountId").toString()));
      }
      // ensure the json data is an array
      if (!jsonObj.get("accesses").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `accesses` to be an array in the JSON string but got `%s`", jsonObj.get("accesses").toString()));
      }

      JsonArray jsonArrayaccesses = jsonObj.getAsJsonArray("accesses");
      // validate the required field `accesses` (array)
      for (int i = 0; i < jsonArrayaccesses.size(); i++) {
        ResourceAccess.validateJsonElement(jsonArrayaccesses.get(i));
      };
      if (!jsonObj.get("kind").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `kind` to be a primitive type in the JSON string but got `%s`", jsonObj.get("kind").toString()));
      }
      if (!jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if (!jsonObj.get("description").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `description` to be a primitive type in the JSON string but got `%s`", jsonObj.get("description").toString()));
      }
      if (!jsonObj.get("shortDescription").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `shortDescription` to be a primitive type in the JSON string but got `%s`", jsonObj.get("shortDescription").toString()));
      }
      if ((jsonObj.get("comment") != null && !jsonObj.get("comment").isJsonNull()) && !jsonObj.get("comment").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `comment` to be a primitive type in the JSON string but got `%s`", jsonObj.get("comment").toString()));
      }
      // validate the optional field `subject`
      if (jsonObj.get("subject") != null && !jsonObj.get("subject").isJsonNull()) {
        SubjectDataRep.validateJsonElement(jsonObj.get("subject"));
      }
      // validate the optional field `member`
      if (jsonObj.get("member") != null && !jsonObj.get("member").isJsonNull()) {
        MemberDataRep.validateJsonElement(jsonObj.get("member"));
      }
      // validate the optional field `token`
      if (jsonObj.get("token") != null && !jsonObj.get("token").isJsonNull()) {
        TokenSummary.validateJsonElement(jsonObj.get("token"));
      }
      // validate the optional field `app`
      if (jsonObj.get("app") != null && !jsonObj.get("app").isJsonNull()) {
        AuthorizedAppDataRep.validateJsonElement(jsonObj.get("app"));
      }
      if ((jsonObj.get("titleVerb") != null && !jsonObj.get("titleVerb").isJsonNull()) && !jsonObj.get("titleVerb").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `titleVerb` to be a primitive type in the JSON string but got `%s`", jsonObj.get("titleVerb").toString()));
      }
      if ((jsonObj.get("title") != null && !jsonObj.get("title").isJsonNull()) && !jsonObj.get("title").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `title` to be a primitive type in the JSON string but got `%s`", jsonObj.get("title").toString()));
      }
      // validate the optional field `target`
      if (jsonObj.get("target") != null && !jsonObj.get("target").isJsonNull()) {
        TargetResourceRep.validateJsonElement(jsonObj.get("target"));
      }
      // validate the optional field `parent`
      if (jsonObj.get("parent") != null && !jsonObj.get("parent").isJsonNull()) {
        ParentResourceRep.validateJsonElement(jsonObj.get("parent"));
      }
      if (jsonObj.get("subentries") != null && !jsonObj.get("subentries").isJsonNull()) {
        JsonArray jsonArraysubentries = jsonObj.getAsJsonArray("subentries");
        if (jsonArraysubentries != null) {
          // ensure the json data is an array
          if (!jsonObj.get("subentries").isJsonArray()) {
            throw new IllegalArgumentException(String.format("Expected the field `subentries` to be an array in the JSON string but got `%s`", jsonObj.get("subentries").toString()));
          }

          // validate the optional field `subentries` (array)
          for (int i = 0; i < jsonArraysubentries.size(); i++) {
            AuditLogEntryListingRep.validateJsonElement(jsonArraysubentries.get(i));
          };
        }
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!AuditLogEntryRep.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'AuditLogEntryRep' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<AuditLogEntryRep> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(AuditLogEntryRep.class));

       return (TypeAdapter<T>) new TypeAdapter<AuditLogEntryRep>() {
           @Override
           public void write(JsonWriter out, AuditLogEntryRep value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             obj.remove("additionalProperties");
             // serialize additional properties
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
                   JsonElement jsonElement = gson.toJsonTree(entry.getValue());
                   if (jsonElement.isJsonArray()) {
                     obj.add(entry.getKey(), jsonElement.getAsJsonArray());
                   } else {
                     obj.add(entry.getKey(), jsonElement.getAsJsonObject());
                   }
                 }
               }
             }
             elementAdapter.write(out, obj);
           }

           @Override
           public AuditLogEntryRep read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             JsonObject jsonObj = jsonElement.getAsJsonObject();
             // store additional fields in the deserialized instance
             AuditLogEntryRep instance = thisAdapter.fromJsonTree(jsonObj);
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
                 } else if (entry.getValue().isJsonArray()) {
                     instance.putAdditionalProperty(entry.getKey(), gson.fromJson(entry.getValue(), List.class));
                 } else { // JSON object
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
  * Create an instance of AuditLogEntryRep given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of AuditLogEntryRep
  * @throws IOException if the JSON string is invalid with respect to AuditLogEntryRep
  */
  public static AuditLogEntryRep fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, AuditLogEntryRep.class);
  }

 /**
  * Convert an instance of AuditLogEntryRep to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

