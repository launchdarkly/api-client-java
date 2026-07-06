/*
 * LaunchDarkly REST API
 * This documentation describes LaunchDarkly's REST API. To access the complete OpenAPI spec directly, use [Get OpenAPI spec](https://launchdarkly.com/docs/api/other/get-openapi-spec).  To learn how to use LaunchDarkly using the user interface (UI) instead, read our [product documentation](https://launchdarkly.com/docs/home).  ## Authentication  LaunchDarkly's REST API uses the HTTPS protocol with a minimum TLS version of 1.2.  All REST API resources are authenticated with either [personal or service access tokens](https://launchdarkly.com/docs/home/account/api), or session cookies. Other authentication mechanisms are not supported. You can manage personal access tokens on your [**Authorization**](https://app.launchdarkly.com/settings/authorization) page in the LaunchDarkly UI.  LaunchDarkly also has SDK keys, mobile keys, and client-side IDs that are used by our server-side SDKs, mobile SDKs, and JavaScript-based SDKs, respectively. **These keys cannot be used to access our REST API**. These keys are environment-specific, and can only perform read-only operations such as fetching feature flag settings.  | Auth mechanism                                                                                  | Allowed resources                                                                                     | Use cases                                          | | ----------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------- | -------------------------------------------------- | | [Personal or service access tokens](https://launchdarkly.com/docs/home/account/api) | Can be customized on a per-token basis                                                                | Building scripts, custom integrations, data export. | | SDK keys                                                                                        | Can only access read-only resources specific to server-side SDKs. Restricted to a single environment. | Server-side SDKs                     | | Mobile keys                                                                                     | Can only access read-only resources specific to mobile SDKs, and only for flags marked available to mobile keys. Restricted to a single environment.           | Mobile SDKs                                        | | Client-side ID                                                                                  | Can only access read-only resources specific to JavaScript-based client-side SDKs, and only for flags marked available to client-side. Restricted to a single environment.           | Client-side JavaScript                             |  > #### Keep your access tokens and SDK keys private > > Access tokens should _never_ be exposed in untrusted contexts. Never put an access token in client-side JavaScript, or embed it in a mobile application. LaunchDarkly has special mobile keys that you can embed in mobile apps. If you accidentally expose an access token or SDK key, you can reset it from your [**Authorization**](https://app.launchdarkly.com/settings/authorization) page. > > The client-side ID is safe to embed in untrusted contexts. It's designed for use in client-side JavaScript.  ### Authentication using request header  The preferred way to authenticate with the API is by adding an `Authorization` header containing your access token to your requests. The value of the `Authorization` header must be your access token.  Manage personal access tokens from the [**Authorization**](https://app.launchdarkly.com/settings/authorization) page.  ### Authentication using session cookie  For testing purposes, you can make API calls directly from your web browser. If you are logged in to the LaunchDarkly application, the API will use your existing session to authenticate calls.  Depending on the permissions granted as part of your [role](https://launchdarkly.com/docs/home/account/roles), you may not have permission to perform some API calls. You will receive a `401` response code in that case.  > ### Modifying the Origin header causes an error > > LaunchDarkly validates that the Origin header for any API request authenticated by a session cookie matches the expected Origin header. The expected Origin header is `https://app.launchdarkly.com`. > > If the Origin header does not match what's expected, LaunchDarkly returns an error. This error can prevent the LaunchDarkly app from working correctly. > > Any browser extension that intentionally changes the Origin header can cause this problem. For example, Cross-Origin Resource Sharing (CORS) extensions used during development can modify the Origin header and cause the app to fail. > > To prevent this error, do not modify your Origin header. > > LaunchDarkly does not require origin matching when authenticating with an access token, so this issue does not affect normal API usage.  ## Representations  All resources expect and return JSON response bodies. Error responses also send a JSON body. To learn more about the error format of the API, read [Errors](https://launchdarkly.com/docs/api#errors).  In practice this means that you always get a response with a `Content-Type` header set to `application/json`.  In addition, request bodies for `PATCH`, `POST`, and `PUT` requests must be encoded as JSON with a `Content-Type` header set to `application/json`.  ### Summary and detailed representations  When you fetch a list of resources, the response includes only the most important attributes of each resource. This is a _summary representation_ of the resource. When you fetch an individual resource, such as a single feature flag, you receive a _detailed representation_ of the resource.  The best way to find a detailed representation is to follow links. Every summary representation includes a link to its detailed representation.  ### Expanding responses  Sometimes the detailed representation of a resource does not include all of the attributes of the resource by default. If this is the case, the request method will clearly document this and describe which attributes you can include in an expanded response.  To include the additional attributes, append the `expand` request parameter to your request and add a comma-separated list of the attributes to include. For example, when you append `?expand=members,maintainers` to the [Get team](https://launchdarkly.com/docs/api/teams/get-team) endpoint, the expanded response includes both of these attributes.  ### Links and addressability  The best way to navigate the API is by following links. These are attributes in representations that link to other resources. The API always uses the same format for links:  - Links to other resources within the API are encapsulated in a `_links` object - If the resource has a corresponding link to HTML content on the site, it is stored in a special `_site` link  Each link has two attributes:  - An `href`, which contains the URL - A `type`, which describes the content type  For example, a feature resource might return the following:  ```json {   \"_links\": {     \"parent\": {       \"href\": \"/api/features\",       \"type\": \"application/json\"     },     \"self\": {       \"href\": \"/api/features/sort.order\",       \"type\": \"application/json\"     }   },   \"_site\": {     \"href\": \"/features/sort.order\",     \"type\": \"text/html\"   } } ```  From this, you can navigate to the parent collection of features by following the `parent` link, or navigate to the site page for the feature by following the `_site` link.  Collections are always represented as a JSON object with an `items` attribute containing an array of representations. Like all other representations, collections have `_links` defined at the top level.  Paginated collections include `first`, `last`, `next`, and `prev` links containing a URL with the respective set of elements in the collection.  ## Updates  Resources that accept partial updates use the `PATCH` verb. Most resources support the [JSON patch](https://launchdarkly.com/docs/api#updates-using-json-patch) format. Some resources also support the [JSON merge patch](https://launchdarkly.com/docs/api#updates-using-json-merge-patch) format, and some resources support the [semantic patch](https://launchdarkly.com/docs/api#updates-using-semantic-patch) format, which is a way to specify the modifications to perform as a set of executable instructions. Each resource supports optional [comments](https://launchdarkly.com/docs/api#updates-with-comments) that you can submit with updates. Comments appear in outgoing webhooks, the audit log, and other integrations.  When a resource supports both JSON patch and semantic patch, we document both in the request method. However, the specific request body fields and descriptions included in our documentation only match one type of patch or the other.  ### Updates using JSON patch  [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) is a way to specify the modifications to perform on a resource. JSON patch uses paths and a limited set of operations to describe how to transform the current state of the resource into a new state. JSON patch documents are always arrays, where each element contains an operation, a path to the field to update, and the new value.  For example, in this feature flag representation:  ```json {     \"name\": \"New recommendations engine\",     \"key\": \"engine.enable\",     \"description\": \"This is the description\",     ... } ``` You can change the feature flag's description with the following patch document:  ```json [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"This is the new description\" }] ```  You can specify multiple modifications to perform in a single request. You can also test that certain preconditions are met before applying the patch:  ```json [   { \"op\": \"test\", \"path\": \"/version\", \"value\": 10 },   { \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" } ] ```  The above patch request tests whether the feature flag's `version` is `10`, and if so, changes the feature flag's description.  Attributes that are not editable, such as a resource's `_links`, have names that start with an underscore.  ### Updates using JSON merge patch  [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) is another format for specifying the modifications to perform on a resource. JSON merge patch is less expressive than JSON patch. However, in many cases it is simpler to construct a merge patch document. For example, you can change a feature flag's description with the following merge patch document:  ```json {   \"description\": \"New flag description\" } ```  ### Updates using semantic patch  Some resources support the semantic patch format. A semantic patch is a way to specify the modifications to perform on a resource as a set of executable instructions.  Semantic patch allows you to be explicit about intent using precise, custom instructions. In many cases, you can define semantic patch instructions independently of the current state of the resource. This can be useful when defining a change that may be applied at a future date.  To make a semantic patch request, you must append `domain-model=launchdarkly.semanticpatch` to your `Content-Type` header.  Here's how:  ``` Content-Type: application/json; domain-model=launchdarkly.semanticpatch ```  If you call a semantic patch resource without this header, you will receive a `400` response because your semantic patch will be interpreted as a JSON patch.  The body of a semantic patch request takes the following properties:  * `comment` (string): (Optional) A description of the update. * `environmentKey` (string): (Required for some resources only) The environment key. * `instructions` (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a `kind` property that indicates the instruction. If the instruction requires parameters, you must include those parameters as additional fields in the object. The documentation for each resource that supports semantic patch includes the available instructions and any additional parameters.  For example:  ```json {   \"comment\": \"optional comment\",   \"instructions\": [ {\"kind\": \"turnFlagOn\"} ] } ```  Semantic patches are not applied partially; either all of the instructions are applied or none of them are. If **any** instruction is invalid, the endpoint returns an error and will not change the resource. If all instructions are valid, the request succeeds and the resources are updated if necessary, or left unchanged if they are already in the state you request.  ### Updates with comments  You can submit optional comments with `PATCH` changes.  To submit a comment along with a JSON patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"patch\": [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" }] } ```  To submit a comment along with a JSON merge patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"merge\": { \"description\": \"New flag description\" } } ```  To submit a comment along with a semantic patch, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"instructions\": [ {\"kind\": \"turnFlagOn\"} ] } ```  ## Errors  The API always returns errors in a common format. Here's an example:  ```json {   \"code\": \"invalid_request\",   \"message\": \"A feature with that key already exists\",   \"id\": \"30ce6058-87da-11e4-b116-123b93f75cba\" } ```  The `code` indicates the general class of error. The `message` is a human-readable explanation of what went wrong. The `id` is a unique identifier. Use it when you're working with LaunchDarkly Support to debug a problem with a specific API call.  ### HTTP status error response codes  | Code | Definition        | Description                                                                                       | Possible Solution                                                | | ---- | ----------------- | ------------------------------------------------------------------------------------------- | ---------------------------------------------------------------- | | 400  | Invalid request       | The request cannot be understood.                                    | Ensure JSON syntax in request body is correct.                   | | 401  | Invalid access token      | Requestor is unauthorized or does not have permission for this API call.                                                | Ensure your API access token is valid and has the appropriate permissions.                                     | | 403  | Forbidden         | Requestor does not have access to this resource.                                                | Ensure that the account member or access token has proper permissions set. | | 404  | Invalid resource identifier | The requested resource is not valid. | Ensure that the resource is correctly identified by ID or key. | | 405  | Method not allowed | The request method is not allowed on this resource. | Ensure that the HTTP verb is correct. | | 409  | Conflict          | The API request can not be completed because it conflicts with a concurrent API request. | Retry your request.                                              | | 422  | Unprocessable entity | The API request can not be completed because the update description can not be understood. | Ensure that the request body is correct for the type of patch you are using, either JSON patch or semantic patch. | 429  | Too many requests | Read [Rate limiting](https://launchdarkly.com/docs/api#rate-limiting).                                               | Wait and try again later.                                        |  ## CORS  The LaunchDarkly API supports Cross Origin Resource Sharing (CORS) for AJAX requests from any origin. If an `Origin` header is given in a request, it will be echoed as an explicitly allowed origin. Otherwise the request returns a wildcard, `Access-Control-Allow-Origin: *`. For more information on CORS, read the [CORS W3C Recommendation](http://www.w3.org/TR/cors). Example CORS headers might look like:  ```http Access-Control-Allow-Headers: Accept, Content-Type, Content-Length, Accept-Encoding, Authorization Access-Control-Allow-Methods: OPTIONS, GET, DELETE, PATCH Access-Control-Allow-Origin: * Access-Control-Max-Age: 300 ```  You can make authenticated CORS calls just as you would make same-origin calls, using either [token or session-based authentication](https://launchdarkly.com/docs/api#authentication). If you are using session authentication, you should set the `withCredentials` property for your `xhr` request to `true`. You should never expose your access tokens to untrusted entities.  ## Rate limiting  We use several rate limiting strategies to ensure the availability of our APIs. Rate-limited calls to our APIs return a `429` status code. Calls to our APIs include headers indicating the current rate limit status. The specific headers returned depend on the API route being called. The limits differ based on the route, authentication mechanism, and other factors. Routes that are not rate limited may not contain any of the headers described below.  > ### Rate limiting and SDKs > > LaunchDarkly SDKs are never rate limited and do not use the API endpoints defined here. LaunchDarkly uses a different set of approaches, including streaming/server-sent events and a global CDN, to ensure availability to the routes used by LaunchDarkly SDKs.  ### Global rate limits  Authenticated requests are subject to a global limit. This is the maximum number of calls that your account can make to the API per ten seconds. All service and personal access tokens on the account share this limit, so exceeding the limit with one access token will impact other tokens. Calls that are subject to global rate limits may return the headers below:  | Header name                    | Description                                                                      | | ------------------------------ | -------------------------------------------------------------------------------- | | `X-Ratelimit-Global-Remaining` | The maximum number of requests the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`            | The time at which the current rate limit window resets in epoch milliseconds.    |  We do not publicly document the specific number of calls that can be made globally. This limit may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limit.  ### Route-level rate limits  Some authenticated routes have custom rate limits. These also reset every ten seconds. Any service or personal access tokens hitting the same route share this limit, so exceeding the limit with one access token may impact other tokens. Calls that are subject to route-level rate limits return the headers below:  | Header name                   | Description                                                                                           | | ----------------------------- | ----------------------------------------------------------------------------------------------------- | | `X-Ratelimit-Route-Remaining` | The maximum number of requests to the current route the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`           | The time at which the current rate limit window resets in epoch milliseconds.                         |  A _route_ represents a specific URL pattern and verb. For example, the [Delete environment](https://launchdarkly.com/docs/api/environments/delete-environment) endpoint is considered a single route, and each call to delete an environment counts against your route-level rate limit for that route.  We do not publicly document the specific number of calls that an account can make to each endpoint per ten seconds. These limits may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limits.  ### IP-based rate limiting  We also employ IP-based rate limiting on some API routes. If you hit an IP-based rate limit, your API response will include a `Retry-After` header indicating how long to wait before re-trying the call. Clients must wait at least `Retry-After` seconds before making additional calls to our API, and should employ jitter and backoff strategies to avoid triggering rate limits again.  ## OpenAPI (Swagger) and client libraries  We have a [complete OpenAPI (Swagger) specification](https://app.launchdarkly.com/api/v2/openapi.json) for our API.  We auto-generate multiple client libraries based on our OpenAPI specification. To learn more, visit the [collection of client libraries on GitHub](https://github.com/search?q=topic%3Alaunchdarkly-api+org%3Alaunchdarkly&type=Repositories). Alternatively, you can use the specification to generate client libraries to interact with our REST API in your language of choice. Or, you can refer to our API endpoints' documentation for guidance on how to make requests with a common HTTP library in your language of choice.  Our OpenAPI specification is supported by several API-based tools such as Postman and Insomnia. In many cases, you can directly import our specification to explore our APIs.  ## Method overriding  Some firewalls and HTTP clients restrict the use of verbs other than `GET` and `POST`. In those environments, our API endpoints that use `DELETE`, `PATCH`, and `PUT` verbs are inaccessible.  To avoid this issue, our API supports the `X-HTTP-Method-Override` header, allowing clients to \"tunnel\" `DELETE`, `PATCH`, and `PUT` requests using a `POST` request.  For example, to call a `PATCH` endpoint using a `POST` request, you can include `X-HTTP-Method-Override:PATCH` as a header.  ## Beta resources  We sometimes release new API resources in **beta** status before we release them with general availability.  Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  We try to promote resources into general availability as quickly as possible. This happens after sufficient testing and when we're satisfied that we no longer need to make backwards-incompatible changes.  We mark beta resources with a \"Beta\" callout in our documentation, pictured below:  > ### This feature is in beta > > To use this feature, pass in a header including the `LD-API-Version` key with value set to `beta`. Use this header with each call. To learn more, read [Beta resources](https://launchdarkly.com/docs/api#beta-resources). > > Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  ### Using beta resources  To use a beta resource, you must include a header in the request. If you call a beta resource without this header, you receive a `403` response.  Use this header:  ``` LD-API-Version: beta ```  ## Federal and EU environments  In addition to the commercial versions, LaunchDarkly offers instances for federal agencies and those based in the European Union (EU).  ### Federal environments  The version of LaunchDarkly that is available on domains controlled by the United States government is different from the version of LaunchDarkly available to the general public. If you are an employee or contractor for a United States federal agency and use LaunchDarkly in your work, you likely use the federal instance of LaunchDarkly.  If you are working in the federal instance of LaunchDarkly, the base URI for each request is `https://app.launchdarkly.us`.  To learn more, read [LaunchDarkly in federal environments](https://launchdarkly.com/docs/home/infrastructure/federal).  ### EU environments  The version of LaunchDarkly that is available in the EU is different from the version of LaunchDarkly available to other regions. If you are based in the EU, you likely use the EU instance of LaunchDarkly. The LaunchDarkly EU instance complies with EU data residency principles, including the protection and confidentiality of EU customer information.  If you are working in the EU instance of LaunchDarkly, the base URI for each request is `https://app.eu.launchdarkly.com`.  To learn more, read [LaunchDarkly in the European Union (EU)](https://launchdarkly.com/docs/home/infrastructure/eu).  ## Versioning  We try hard to keep our REST API backwards compatible, but we occasionally have to make backwards-incompatible changes in the process of shipping new features. These breaking changes can cause unexpected behavior if you don't prepare for them accordingly.  Updates to our REST API include support for the latest features in LaunchDarkly. We also release a new version of our REST API every time we make a breaking change. We provide simultaneous support for multiple API versions so you can migrate from your current API version to a new version at your own pace.  ### Setting the API version per request  You can set the API version on a specific request by sending an `LD-API-Version` header, as shown in the example below:  ``` LD-API-Version: 20240415 ```  The header value is the version number of the API version you would like to request. The number for each version corresponds to the date the version was released in `yyyymmdd` format. In the example above the version `20240415` corresponds to April 15, 2024.  ### Setting the API version per access token  When you create an access token, you must specify a specific version of the API to use. This ensures that integrations using this token cannot be broken by version changes.  Tokens created before versioning was released have their version set to `20160426`, which is the version of the API that existed before the current versioning scheme, so that they continue working the same way they did before versioning.  If you would like to upgrade your integration to use a new API version, you can explicitly set the header described above.  > ### Best practice: Set the header for every client or integration > > We recommend that you set the API version header explicitly in any client or integration you build. > > Only rely on the access token API version during manual testing.  ### API version changelog  <table>   <tr>     <th>Version</th>     <th>Changes</th>     <th>End of life (EOL)</th>   </tr>   <tr>     <td>`20240415`</td>     <td>       <ul><li>Changed several endpoints from unpaginated to paginated. Use the `limit` and `offset` query parameters to page through the results.</li> <li>Changed the [list access tokens](https://launchdarkly.com/docs/api/access-tokens/get-tokens) endpoint: <ul><li>Response is now paginated with a default limit of `25`</li></ul></li> <li>Changed the [list account members](https://launchdarkly.com/docs/api/account-members/get-members) endpoint: <ul><li>The `accessCheck` filter is no longer available</li></ul></li> <li>Changed the [list custom roles](https://launchdarkly.com/docs/api/custom-roles/get-custom-roles) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li></ul></li> <li>Changed the [list feature flags](https://launchdarkly.com/docs/api/feature-flags/get-feature-flags) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li><li>The `environments` field is now only returned if the request is filtered by environment, using the `filterEnv` query parameter</li><li>The `followerId`, `hasDataExport`, `status`, `contextKindTargeted`, and `segmentTargeted` filters are no longer available</li><li>The `compare` query parameter is no longer available</li></ul></li> <li>Changed the [list segments](https://launchdarkly.com/docs/api/segments/get-segments) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li></ul></li> <li>Changed the [list teams](https://launchdarkly.com/docs/api/teams/get-teams) endpoint: <ul><li>The `expand` parameter no longer supports including `projects` or `roles`</li><li>In paginated results, the maximum page size is now 100</li></ul></li> <li>Changed the [get workflows](https://launchdarkly.com/docs/api/workflows/get-workflows) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li><li>The `_conflicts` field in the response is no longer available</li></ul></li> </ul>     </td>     <td>Current</td>   </tr>   <tr>     <td>`20220603`</td>     <td>       <ul><li>Changed the [list projects](https://launchdarkly.com/docs/api/projects/get-projects) return value:<ul><li>Response is now paginated with a default limit of `20`.</li><li>Added support for filter and sort.</li><li>The project `environments` field is now expandable. This field is omitted by default.</li></ul></li><li>Changed the [get project](https://launchdarkly.com/docs/api/projects/get-project) return value:<ul><li>The `environments` field is now expandable. This field is omitted by default.</li></ul></li></ul>     </td>     <td>2025-04-15</td>   </tr>   <tr>     <td>`20210729`</td>     <td>       <ul><li>Changed the [create approval request](https://launchdarkly.com/docs/api/approvals/post-approval-request) return value. It now returns HTTP Status Code `201` instead of `200`.</li><li> Changed the [get user](https://launchdarkly.com/docs/api/users/get-user) return value. It now returns a user record, not a user. </li><li>Added additional optional fields to environment, segments, flags, members, and segments, including the ability to create big segments. </li><li> Added default values for flag variations when new environments are created. </li><li>Added filtering and pagination for getting flags and members, including `limit`, `number`, `filter`, and `sort` query parameters. </li><li>Added endpoints for expiring user targets for flags and segments, scheduled changes, access tokens, Relay Proxy configuration, integrations and subscriptions, and approvals. </li></ul>     </td>     <td>2023-06-03</td>   </tr>   <tr>     <td>`20191212`</td>     <td>       <ul><li>[List feature flags](https://launchdarkly.com/docs/api/feature-flags/get-feature-flags) now defaults to sending summaries of feature flag configurations, equivalent to setting the query parameter `summary=true`. Summaries omit flag targeting rules and individual user targets from the payload. </li><li> Added endpoints for flags, flag status, projects, environments, audit logs, members, users, custom roles, segments, usage, streams, events, and data export. </li></ul>     </td>     <td>2022-07-29</td>   </tr>   <tr>     <td>`20160426`</td>     <td>       <ul><li>Initial versioning of API. Tokens created before versioning have their version set to this.</li></ul>     </td>     <td>2020-12-12</td>   </tr> </table>  To learn more about how EOL is determined, read LaunchDarkly's [End of Life (EOL) Policy](https://launchdarkly.com/policies/end-of-life-policy/). 
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
import java.io.IOException;
import java.util.Arrays;

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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.launchdarkly.api.JSON;

/**
 * WarehouseSetupScriptPostBody
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-07-06T16:21:06.581384Z[Etc/UTC]", comments = "Generator version: 7.18.0")
public class WarehouseSetupScriptPostBody {
  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  @javax.annotation.Nullable
  private String name;

  public static final String SERIALIZED_NAME_SNOWFLAKE_HOST_ADDRESS = "snowflakeHostAddress";
  @SerializedName(SERIALIZED_NAME_SNOWFLAKE_HOST_ADDRESS)
  @javax.annotation.Nullable
  private String snowflakeHostAddress;

  public static final String SERIALIZED_NAME_DATABASE_NAME = "databaseName";
  @SerializedName(SERIALIZED_NAME_DATABASE_NAME)
  @javax.annotation.Nullable
  private String databaseName;

  public static final String SERIALIZED_NAME_WAREHOUSE_NAME = "warehouseName";
  @SerializedName(SERIALIZED_NAME_WAREHOUSE_NAME)
  @javax.annotation.Nullable
  private String warehouseName;

  public static final String SERIALIZED_NAME_ROLE_NAME = "roleName";
  @SerializedName(SERIALIZED_NAME_ROLE_NAME)
  @javax.annotation.Nullable
  private String roleName;

  public static final String SERIALIZED_NAME_SCHEMA_NAME = "schemaName";
  @SerializedName(SERIALIZED_NAME_SCHEMA_NAME)
  @javax.annotation.Nullable
  private String schemaName;

  public static final String SERIALIZED_NAME_USER_NAME = "userName";
  @SerializedName(SERIALIZED_NAME_USER_NAME)
  @javax.annotation.Nullable
  private String userName;

  public static final String SERIALIZED_NAME_INCLUDE_NETWORK_POLICY = "includeNetworkPolicy";
  @SerializedName(SERIALIZED_NAME_INCLUDE_NETWORK_POLICY)
  @javax.annotation.Nullable
  private Boolean includeNetworkPolicy;

  public static final String SERIALIZED_NAME_CLUSTER_IDENTIFIER = "clusterIdentifier";
  @SerializedName(SERIALIZED_NAME_CLUSTER_IDENTIFIER)
  @javax.annotation.Nullable
  private String clusterIdentifier;

  public static final String SERIALIZED_NAME_CLUSTER_REGION = "clusterRegion";
  @SerializedName(SERIALIZED_NAME_CLUSTER_REGION)
  @javax.annotation.Nullable
  private String clusterRegion;

  public static final String SERIALIZED_NAME_CLUSTER_AWS_ACCOUNT_ID = "clusterAwsAccountId";
  @SerializedName(SERIALIZED_NAME_CLUSTER_AWS_ACCOUNT_ID)
  @javax.annotation.Nullable
  private String clusterAwsAccountId;

  public static final String SERIALIZED_NAME_ENDPOINT = "endpoint";
  @SerializedName(SERIALIZED_NAME_ENDPOINT)
  @javax.annotation.Nullable
  private String endpoint;

  public static final String SERIALIZED_NAME_CLICK_HOUSE_DATABASE_NAME = "clickHouseDatabaseName";
  @SerializedName(SERIALIZED_NAME_CLICK_HOUSE_DATABASE_NAME)
  @javax.annotation.Nullable
  private String clickHouseDatabaseName;

  public static final String SERIALIZED_NAME_CLICK_HOUSE_USER_NAME = "clickHouseUserName";
  @SerializedName(SERIALIZED_NAME_CLICK_HOUSE_USER_NAME)
  @javax.annotation.Nullable
  private String clickHouseUserName;

  public static final String SERIALIZED_NAME_CLICK_HOUSE_S3_BUCKET_NAME = "clickHouseS3BucketName";
  @SerializedName(SERIALIZED_NAME_CLICK_HOUSE_S3_BUCKET_NAME)
  @javax.annotation.Nullable
  private String clickHouseS3BucketName;

  public static final String SERIALIZED_NAME_CLICK_HOUSE_INCLUDE_HOST_RESTRICTION = "clickHouseIncludeHostRestriction";
  @SerializedName(SERIALIZED_NAME_CLICK_HOUSE_INCLUDE_HOST_RESTRICTION)
  @javax.annotation.Nullable
  private Boolean clickHouseIncludeHostRestriction;

  public static final String SERIALIZED_NAME_CLICK_HOUSE_SERVICE_ROLE_ARN = "clickHouseServiceRoleArn";
  @SerializedName(SERIALIZED_NAME_CLICK_HOUSE_SERVICE_ROLE_ARN)
  @javax.annotation.Nullable
  private String clickHouseServiceRoleArn;

  public static final String SERIALIZED_NAME_CLICK_HOUSE_PASSWORD = "clickHousePassword";
  @SerializedName(SERIALIZED_NAME_CLICK_HOUSE_PASSWORD)
  @javax.annotation.Nullable
  private String clickHousePassword;

  public WarehouseSetupScriptPostBody() {
  }

  public WarehouseSetupScriptPostBody name(@javax.annotation.Nullable String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  @javax.annotation.Nullable
  public String getName() {
    return name;
  }

  public void setName(@javax.annotation.Nullable String name) {
    this.name = name;
  }


  public WarehouseSetupScriptPostBody snowflakeHostAddress(@javax.annotation.Nullable String snowflakeHostAddress) {
    this.snowflakeHostAddress = snowflakeHostAddress;
    return this;
  }

  /**
   * Get snowflakeHostAddress
   * @return snowflakeHostAddress
   */
  @javax.annotation.Nullable
  public String getSnowflakeHostAddress() {
    return snowflakeHostAddress;
  }

  public void setSnowflakeHostAddress(@javax.annotation.Nullable String snowflakeHostAddress) {
    this.snowflakeHostAddress = snowflakeHostAddress;
  }


  public WarehouseSetupScriptPostBody databaseName(@javax.annotation.Nullable String databaseName) {
    this.databaseName = databaseName;
    return this;
  }

  /**
   * Get databaseName
   * @return databaseName
   */
  @javax.annotation.Nullable
  public String getDatabaseName() {
    return databaseName;
  }

  public void setDatabaseName(@javax.annotation.Nullable String databaseName) {
    this.databaseName = databaseName;
  }


  public WarehouseSetupScriptPostBody warehouseName(@javax.annotation.Nullable String warehouseName) {
    this.warehouseName = warehouseName;
    return this;
  }

  /**
   * Get warehouseName
   * @return warehouseName
   */
  @javax.annotation.Nullable
  public String getWarehouseName() {
    return warehouseName;
  }

  public void setWarehouseName(@javax.annotation.Nullable String warehouseName) {
    this.warehouseName = warehouseName;
  }


  public WarehouseSetupScriptPostBody roleName(@javax.annotation.Nullable String roleName) {
    this.roleName = roleName;
    return this;
  }

  /**
   * Get roleName
   * @return roleName
   */
  @javax.annotation.Nullable
  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(@javax.annotation.Nullable String roleName) {
    this.roleName = roleName;
  }


  public WarehouseSetupScriptPostBody schemaName(@javax.annotation.Nullable String schemaName) {
    this.schemaName = schemaName;
    return this;
  }

  /**
   * Get schemaName
   * @return schemaName
   */
  @javax.annotation.Nullable
  public String getSchemaName() {
    return schemaName;
  }

  public void setSchemaName(@javax.annotation.Nullable String schemaName) {
    this.schemaName = schemaName;
  }


  public WarehouseSetupScriptPostBody userName(@javax.annotation.Nullable String userName) {
    this.userName = userName;
    return this;
  }

  /**
   * Get userName
   * @return userName
   */
  @javax.annotation.Nullable
  public String getUserName() {
    return userName;
  }

  public void setUserName(@javax.annotation.Nullable String userName) {
    this.userName = userName;
  }


  public WarehouseSetupScriptPostBody includeNetworkPolicy(@javax.annotation.Nullable Boolean includeNetworkPolicy) {
    this.includeNetworkPolicy = includeNetworkPolicy;
    return this;
  }

  /**
   * Get includeNetworkPolicy
   * @return includeNetworkPolicy
   */
  @javax.annotation.Nullable
  public Boolean getIncludeNetworkPolicy() {
    return includeNetworkPolicy;
  }

  public void setIncludeNetworkPolicy(@javax.annotation.Nullable Boolean includeNetworkPolicy) {
    this.includeNetworkPolicy = includeNetworkPolicy;
  }


  public WarehouseSetupScriptPostBody clusterIdentifier(@javax.annotation.Nullable String clusterIdentifier) {
    this.clusterIdentifier = clusterIdentifier;
    return this;
  }

  /**
   * Get clusterIdentifier
   * @return clusterIdentifier
   */
  @javax.annotation.Nullable
  public String getClusterIdentifier() {
    return clusterIdentifier;
  }

  public void setClusterIdentifier(@javax.annotation.Nullable String clusterIdentifier) {
    this.clusterIdentifier = clusterIdentifier;
  }


  public WarehouseSetupScriptPostBody clusterRegion(@javax.annotation.Nullable String clusterRegion) {
    this.clusterRegion = clusterRegion;
    return this;
  }

  /**
   * Get clusterRegion
   * @return clusterRegion
   */
  @javax.annotation.Nullable
  public String getClusterRegion() {
    return clusterRegion;
  }

  public void setClusterRegion(@javax.annotation.Nullable String clusterRegion) {
    this.clusterRegion = clusterRegion;
  }


  public WarehouseSetupScriptPostBody clusterAwsAccountId(@javax.annotation.Nullable String clusterAwsAccountId) {
    this.clusterAwsAccountId = clusterAwsAccountId;
    return this;
  }

  /**
   * Get clusterAwsAccountId
   * @return clusterAwsAccountId
   */
  @javax.annotation.Nullable
  public String getClusterAwsAccountId() {
    return clusterAwsAccountId;
  }

  public void setClusterAwsAccountId(@javax.annotation.Nullable String clusterAwsAccountId) {
    this.clusterAwsAccountId = clusterAwsAccountId;
  }


  public WarehouseSetupScriptPostBody endpoint(@javax.annotation.Nullable String endpoint) {
    this.endpoint = endpoint;
    return this;
  }

  /**
   * Get endpoint
   * @return endpoint
   */
  @javax.annotation.Nullable
  public String getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(@javax.annotation.Nullable String endpoint) {
    this.endpoint = endpoint;
  }


  public WarehouseSetupScriptPostBody clickHouseDatabaseName(@javax.annotation.Nullable String clickHouseDatabaseName) {
    this.clickHouseDatabaseName = clickHouseDatabaseName;
    return this;
  }

  /**
   * Get clickHouseDatabaseName
   * @return clickHouseDatabaseName
   */
  @javax.annotation.Nullable
  public String getClickHouseDatabaseName() {
    return clickHouseDatabaseName;
  }

  public void setClickHouseDatabaseName(@javax.annotation.Nullable String clickHouseDatabaseName) {
    this.clickHouseDatabaseName = clickHouseDatabaseName;
  }


  public WarehouseSetupScriptPostBody clickHouseUserName(@javax.annotation.Nullable String clickHouseUserName) {
    this.clickHouseUserName = clickHouseUserName;
    return this;
  }

  /**
   * Get clickHouseUserName
   * @return clickHouseUserName
   */
  @javax.annotation.Nullable
  public String getClickHouseUserName() {
    return clickHouseUserName;
  }

  public void setClickHouseUserName(@javax.annotation.Nullable String clickHouseUserName) {
    this.clickHouseUserName = clickHouseUserName;
  }


  public WarehouseSetupScriptPostBody clickHouseS3BucketName(@javax.annotation.Nullable String clickHouseS3BucketName) {
    this.clickHouseS3BucketName = clickHouseS3BucketName;
    return this;
  }

  /**
   * Get clickHouseS3BucketName
   * @return clickHouseS3BucketName
   */
  @javax.annotation.Nullable
  public String getClickHouseS3BucketName() {
    return clickHouseS3BucketName;
  }

  public void setClickHouseS3BucketName(@javax.annotation.Nullable String clickHouseS3BucketName) {
    this.clickHouseS3BucketName = clickHouseS3BucketName;
  }


  public WarehouseSetupScriptPostBody clickHouseIncludeHostRestriction(@javax.annotation.Nullable Boolean clickHouseIncludeHostRestriction) {
    this.clickHouseIncludeHostRestriction = clickHouseIncludeHostRestriction;
    return this;
  }

  /**
   * Get clickHouseIncludeHostRestriction
   * @return clickHouseIncludeHostRestriction
   */
  @javax.annotation.Nullable
  public Boolean getClickHouseIncludeHostRestriction() {
    return clickHouseIncludeHostRestriction;
  }

  public void setClickHouseIncludeHostRestriction(@javax.annotation.Nullable Boolean clickHouseIncludeHostRestriction) {
    this.clickHouseIncludeHostRestriction = clickHouseIncludeHostRestriction;
  }


  public WarehouseSetupScriptPostBody clickHouseServiceRoleArn(@javax.annotation.Nullable String clickHouseServiceRoleArn) {
    this.clickHouseServiceRoleArn = clickHouseServiceRoleArn;
    return this;
  }

  /**
   * Get clickHouseServiceRoleArn
   * @return clickHouseServiceRoleArn
   */
  @javax.annotation.Nullable
  public String getClickHouseServiceRoleArn() {
    return clickHouseServiceRoleArn;
  }

  public void setClickHouseServiceRoleArn(@javax.annotation.Nullable String clickHouseServiceRoleArn) {
    this.clickHouseServiceRoleArn = clickHouseServiceRoleArn;
  }


  public WarehouseSetupScriptPostBody clickHousePassword(@javax.annotation.Nullable String clickHousePassword) {
    this.clickHousePassword = clickHousePassword;
    return this;
  }

  /**
   * Get clickHousePassword
   * @return clickHousePassword
   */
  @javax.annotation.Nullable
  public String getClickHousePassword() {
    return clickHousePassword;
  }

  public void setClickHousePassword(@javax.annotation.Nullable String clickHousePassword) {
    this.clickHousePassword = clickHousePassword;
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
   * @return the WarehouseSetupScriptPostBody instance itself
   */
  public WarehouseSetupScriptPostBody putAdditionalProperty(String key, Object value) {
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
    WarehouseSetupScriptPostBody warehouseSetupScriptPostBody = (WarehouseSetupScriptPostBody) o;
    return Objects.equals(this.name, warehouseSetupScriptPostBody.name) &&
        Objects.equals(this.snowflakeHostAddress, warehouseSetupScriptPostBody.snowflakeHostAddress) &&
        Objects.equals(this.databaseName, warehouseSetupScriptPostBody.databaseName) &&
        Objects.equals(this.warehouseName, warehouseSetupScriptPostBody.warehouseName) &&
        Objects.equals(this.roleName, warehouseSetupScriptPostBody.roleName) &&
        Objects.equals(this.schemaName, warehouseSetupScriptPostBody.schemaName) &&
        Objects.equals(this.userName, warehouseSetupScriptPostBody.userName) &&
        Objects.equals(this.includeNetworkPolicy, warehouseSetupScriptPostBody.includeNetworkPolicy) &&
        Objects.equals(this.clusterIdentifier, warehouseSetupScriptPostBody.clusterIdentifier) &&
        Objects.equals(this.clusterRegion, warehouseSetupScriptPostBody.clusterRegion) &&
        Objects.equals(this.clusterAwsAccountId, warehouseSetupScriptPostBody.clusterAwsAccountId) &&
        Objects.equals(this.endpoint, warehouseSetupScriptPostBody.endpoint) &&
        Objects.equals(this.clickHouseDatabaseName, warehouseSetupScriptPostBody.clickHouseDatabaseName) &&
        Objects.equals(this.clickHouseUserName, warehouseSetupScriptPostBody.clickHouseUserName) &&
        Objects.equals(this.clickHouseS3BucketName, warehouseSetupScriptPostBody.clickHouseS3BucketName) &&
        Objects.equals(this.clickHouseIncludeHostRestriction, warehouseSetupScriptPostBody.clickHouseIncludeHostRestriction) &&
        Objects.equals(this.clickHouseServiceRoleArn, warehouseSetupScriptPostBody.clickHouseServiceRoleArn) &&
        Objects.equals(this.clickHousePassword, warehouseSetupScriptPostBody.clickHousePassword)&&
        Objects.equals(this.additionalProperties, warehouseSetupScriptPostBody.additionalProperties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, snowflakeHostAddress, databaseName, warehouseName, roleName, schemaName, userName, includeNetworkPolicy, clusterIdentifier, clusterRegion, clusterAwsAccountId, endpoint, clickHouseDatabaseName, clickHouseUserName, clickHouseS3BucketName, clickHouseIncludeHostRestriction, clickHouseServiceRoleArn, clickHousePassword, additionalProperties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WarehouseSetupScriptPostBody {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    snowflakeHostAddress: ").append(toIndentedString(snowflakeHostAddress)).append("\n");
    sb.append("    databaseName: ").append(toIndentedString(databaseName)).append("\n");
    sb.append("    warehouseName: ").append(toIndentedString(warehouseName)).append("\n");
    sb.append("    roleName: ").append(toIndentedString(roleName)).append("\n");
    sb.append("    schemaName: ").append(toIndentedString(schemaName)).append("\n");
    sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
    sb.append("    includeNetworkPolicy: ").append(toIndentedString(includeNetworkPolicy)).append("\n");
    sb.append("    clusterIdentifier: ").append(toIndentedString(clusterIdentifier)).append("\n");
    sb.append("    clusterRegion: ").append(toIndentedString(clusterRegion)).append("\n");
    sb.append("    clusterAwsAccountId: ").append(toIndentedString(clusterAwsAccountId)).append("\n");
    sb.append("    endpoint: ").append(toIndentedString(endpoint)).append("\n");
    sb.append("    clickHouseDatabaseName: ").append(toIndentedString(clickHouseDatabaseName)).append("\n");
    sb.append("    clickHouseUserName: ").append(toIndentedString(clickHouseUserName)).append("\n");
    sb.append("    clickHouseS3BucketName: ").append(toIndentedString(clickHouseS3BucketName)).append("\n");
    sb.append("    clickHouseIncludeHostRestriction: ").append(toIndentedString(clickHouseIncludeHostRestriction)).append("\n");
    sb.append("    clickHouseServiceRoleArn: ").append(toIndentedString(clickHouseServiceRoleArn)).append("\n");
    sb.append("    clickHousePassword: ").append(toIndentedString(clickHousePassword)).append("\n");
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
    openapiFields = new HashSet<String>(Arrays.asList("name", "snowflakeHostAddress", "databaseName", "warehouseName", "roleName", "schemaName", "userName", "includeNetworkPolicy", "clusterIdentifier", "clusterRegion", "clusterAwsAccountId", "endpoint", "clickHouseDatabaseName", "clickHouseUserName", "clickHouseS3BucketName", "clickHouseIncludeHostRestriction", "clickHouseServiceRoleArn", "clickHousePassword"));

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>(0);
  }

  /**
   * Validates the JSON Element and throws an exception if issues found
   *
   * @param jsonElement JSON Element
   * @throws IOException if the JSON Element is invalid with respect to WarehouseSetupScriptPostBody
   */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!WarehouseSetupScriptPostBody.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "The required field(s) %s in WarehouseSetupScriptPostBody is not found in the empty JSON string", WarehouseSetupScriptPostBody.openapiRequiredFields.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("name") != null && !jsonObj.get("name").isJsonNull()) && !jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if ((jsonObj.get("snowflakeHostAddress") != null && !jsonObj.get("snowflakeHostAddress").isJsonNull()) && !jsonObj.get("snowflakeHostAddress").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `snowflakeHostAddress` to be a primitive type in the JSON string but got `%s`", jsonObj.get("snowflakeHostAddress").toString()));
      }
      if ((jsonObj.get("databaseName") != null && !jsonObj.get("databaseName").isJsonNull()) && !jsonObj.get("databaseName").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `databaseName` to be a primitive type in the JSON string but got `%s`", jsonObj.get("databaseName").toString()));
      }
      if ((jsonObj.get("warehouseName") != null && !jsonObj.get("warehouseName").isJsonNull()) && !jsonObj.get("warehouseName").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `warehouseName` to be a primitive type in the JSON string but got `%s`", jsonObj.get("warehouseName").toString()));
      }
      if ((jsonObj.get("roleName") != null && !jsonObj.get("roleName").isJsonNull()) && !jsonObj.get("roleName").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `roleName` to be a primitive type in the JSON string but got `%s`", jsonObj.get("roleName").toString()));
      }
      if ((jsonObj.get("schemaName") != null && !jsonObj.get("schemaName").isJsonNull()) && !jsonObj.get("schemaName").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `schemaName` to be a primitive type in the JSON string but got `%s`", jsonObj.get("schemaName").toString()));
      }
      if ((jsonObj.get("userName") != null && !jsonObj.get("userName").isJsonNull()) && !jsonObj.get("userName").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `userName` to be a primitive type in the JSON string but got `%s`", jsonObj.get("userName").toString()));
      }
      if ((jsonObj.get("clusterIdentifier") != null && !jsonObj.get("clusterIdentifier").isJsonNull()) && !jsonObj.get("clusterIdentifier").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `clusterIdentifier` to be a primitive type in the JSON string but got `%s`", jsonObj.get("clusterIdentifier").toString()));
      }
      if ((jsonObj.get("clusterRegion") != null && !jsonObj.get("clusterRegion").isJsonNull()) && !jsonObj.get("clusterRegion").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `clusterRegion` to be a primitive type in the JSON string but got `%s`", jsonObj.get("clusterRegion").toString()));
      }
      if ((jsonObj.get("clusterAwsAccountId") != null && !jsonObj.get("clusterAwsAccountId").isJsonNull()) && !jsonObj.get("clusterAwsAccountId").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `clusterAwsAccountId` to be a primitive type in the JSON string but got `%s`", jsonObj.get("clusterAwsAccountId").toString()));
      }
      if ((jsonObj.get("endpoint") != null && !jsonObj.get("endpoint").isJsonNull()) && !jsonObj.get("endpoint").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `endpoint` to be a primitive type in the JSON string but got `%s`", jsonObj.get("endpoint").toString()));
      }
      if ((jsonObj.get("clickHouseDatabaseName") != null && !jsonObj.get("clickHouseDatabaseName").isJsonNull()) && !jsonObj.get("clickHouseDatabaseName").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `clickHouseDatabaseName` to be a primitive type in the JSON string but got `%s`", jsonObj.get("clickHouseDatabaseName").toString()));
      }
      if ((jsonObj.get("clickHouseUserName") != null && !jsonObj.get("clickHouseUserName").isJsonNull()) && !jsonObj.get("clickHouseUserName").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `clickHouseUserName` to be a primitive type in the JSON string but got `%s`", jsonObj.get("clickHouseUserName").toString()));
      }
      if ((jsonObj.get("clickHouseS3BucketName") != null && !jsonObj.get("clickHouseS3BucketName").isJsonNull()) && !jsonObj.get("clickHouseS3BucketName").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `clickHouseS3BucketName` to be a primitive type in the JSON string but got `%s`", jsonObj.get("clickHouseS3BucketName").toString()));
      }
      if ((jsonObj.get("clickHouseServiceRoleArn") != null && !jsonObj.get("clickHouseServiceRoleArn").isJsonNull()) && !jsonObj.get("clickHouseServiceRoleArn").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `clickHouseServiceRoleArn` to be a primitive type in the JSON string but got `%s`", jsonObj.get("clickHouseServiceRoleArn").toString()));
      }
      if ((jsonObj.get("clickHousePassword") != null && !jsonObj.get("clickHousePassword").isJsonNull()) && !jsonObj.get("clickHousePassword").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `clickHousePassword` to be a primitive type in the JSON string but got `%s`", jsonObj.get("clickHousePassword").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!WarehouseSetupScriptPostBody.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'WarehouseSetupScriptPostBody' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<WarehouseSetupScriptPostBody> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(WarehouseSetupScriptPostBody.class));

       return (TypeAdapter<T>) new TypeAdapter<WarehouseSetupScriptPostBody>() {
           @Override
           public void write(JsonWriter out, WarehouseSetupScriptPostBody value) throws IOException {
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
           public WarehouseSetupScriptPostBody read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             JsonObject jsonObj = jsonElement.getAsJsonObject();
             // store additional fields in the deserialized instance
             WarehouseSetupScriptPostBody instance = thisAdapter.fromJsonTree(jsonObj);
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
                     throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "The field `%s` has unknown primitive type. Value: %s", entry.getKey(), entry.getValue().toString()));
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
   * Create an instance of WarehouseSetupScriptPostBody given an JSON string
   *
   * @param jsonString JSON string
   * @return An instance of WarehouseSetupScriptPostBody
   * @throws IOException if the JSON string is invalid with respect to WarehouseSetupScriptPostBody
   */
  public static WarehouseSetupScriptPostBody fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, WarehouseSetupScriptPostBody.class);
  }

  /**
   * Convert an instance of WarehouseSetupScriptPostBody to an JSON string
   *
   * @return JSON string
   */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

