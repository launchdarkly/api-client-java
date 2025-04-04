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


package com.launchdarkly.api.api;

import com.launchdarkly.api.ApiCallback;
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.ApiResponse;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.Pair;
import com.launchdarkly.api.ProgressRequestBody;
import com.launchdarkly.api.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import com.launchdarkly.api.model.AIConfig;
import com.launchdarkly.api.model.AIConfigPatch;
import com.launchdarkly.api.model.AIConfigPost;
import com.launchdarkly.api.model.AIConfigVariation;
import com.launchdarkly.api.model.AIConfigVariationPatch;
import com.launchdarkly.api.model.AIConfigVariationPost;
import com.launchdarkly.api.model.AIConfigVariationsResponse;
import com.launchdarkly.api.model.AIConfigs;
import com.launchdarkly.api.model.Error;
import com.launchdarkly.api.model.MetricByVariation;
import com.launchdarkly.api.model.Metrics;
import com.launchdarkly.api.model.ModelConfig;
import com.launchdarkly.api.model.ModelConfigPost;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;

public class AiConfigsBetaApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public AiConfigsBetaApi() {
        this(Configuration.getDefaultApiClient());
    }

    public AiConfigsBetaApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public int getHostIndex() {
        return localHostIndex;
    }

    public void setHostIndex(int hostIndex) {
        this.localHostIndex = hostIndex;
    }

    public String getCustomBaseUrl() {
        return localCustomBaseUrl;
    }

    public void setCustomBaseUrl(String customBaseUrl) {
        this.localCustomBaseUrl = customBaseUrl;
    }

    /**
     * Build call for deleteAIConfig
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAIConfigCall(String ldAPIVersion, String projectKey, String configKey, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/ai-configs/{configKey}"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()))
            .replaceAll("\\{" + "configKey" + "\\}", localVarApiClient.escapeString(configKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteAIConfigValidateBeforeCall(String ldAPIVersion, String projectKey, String configKey, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling deleteAIConfig(Async)");
        }
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling deleteAIConfig(Async)");
        }
        
        // verify the required parameter 'configKey' is set
        if (configKey == null) {
            throw new ApiException("Missing the required parameter 'configKey' when calling deleteAIConfig(Async)");
        }
        

        okhttp3.Call localVarCall = deleteAIConfigCall(ldAPIVersion, projectKey, configKey, _callback);
        return localVarCall;

    }

    /**
     * Delete AI Config
     * Delete an existing AI Config.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public void deleteAIConfig(String ldAPIVersion, String projectKey, String configKey) throws ApiException {
        deleteAIConfigWithHttpInfo(ldAPIVersion, projectKey, configKey);
    }

    /**
     * Delete AI Config
     * Delete an existing AI Config.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteAIConfigWithHttpInfo(String ldAPIVersion, String projectKey, String configKey) throws ApiException {
        okhttp3.Call localVarCall = deleteAIConfigValidateBeforeCall(ldAPIVersion, projectKey, configKey, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete AI Config (asynchronously)
     * Delete an existing AI Config.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAIConfigAsync(String ldAPIVersion, String projectKey, String configKey, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteAIConfigValidateBeforeCall(ldAPIVersion, projectKey, configKey, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteAIConfigVariation
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAIConfigVariationCall(String ldAPIVersion, String projectKey, String configKey, String variationKey, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/ai-configs/{configKey}/variations/{variationKey}"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()))
            .replaceAll("\\{" + "configKey" + "\\}", localVarApiClient.escapeString(configKey.toString()))
            .replaceAll("\\{" + "variationKey" + "\\}", localVarApiClient.escapeString(variationKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteAIConfigVariationValidateBeforeCall(String ldAPIVersion, String projectKey, String configKey, String variationKey, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling deleteAIConfigVariation(Async)");
        }
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling deleteAIConfigVariation(Async)");
        }
        
        // verify the required parameter 'configKey' is set
        if (configKey == null) {
            throw new ApiException("Missing the required parameter 'configKey' when calling deleteAIConfigVariation(Async)");
        }
        
        // verify the required parameter 'variationKey' is set
        if (variationKey == null) {
            throw new ApiException("Missing the required parameter 'variationKey' when calling deleteAIConfigVariation(Async)");
        }
        

        okhttp3.Call localVarCall = deleteAIConfigVariationCall(ldAPIVersion, projectKey, configKey, variationKey, _callback);
        return localVarCall;

    }

    /**
     * Delete AI Config variation
     * Delete a specific variation of an AI Config by config key and variation key.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public void deleteAIConfigVariation(String ldAPIVersion, String projectKey, String configKey, String variationKey) throws ApiException {
        deleteAIConfigVariationWithHttpInfo(ldAPIVersion, projectKey, configKey, variationKey);
    }

    /**
     * Delete AI Config variation
     * Delete a specific variation of an AI Config by config key and variation key.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteAIConfigVariationWithHttpInfo(String ldAPIVersion, String projectKey, String configKey, String variationKey) throws ApiException {
        okhttp3.Call localVarCall = deleteAIConfigVariationValidateBeforeCall(ldAPIVersion, projectKey, configKey, variationKey, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete AI Config variation (asynchronously)
     * Delete a specific variation of an AI Config by config key and variation key.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAIConfigVariationAsync(String ldAPIVersion, String projectKey, String configKey, String variationKey, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteAIConfigVariationValidateBeforeCall(ldAPIVersion, projectKey, configKey, variationKey, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteModelConfig
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param modelConfigKey  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteModelConfigCall(String ldAPIVersion, String projectKey, String modelConfigKey, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/ai-configs/model-configs/{modelConfigKey}"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()))
            .replaceAll("\\{" + "modelConfigKey" + "\\}", localVarApiClient.escapeString(modelConfigKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteModelConfigValidateBeforeCall(String ldAPIVersion, String projectKey, String modelConfigKey, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling deleteModelConfig(Async)");
        }
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling deleteModelConfig(Async)");
        }
        
        // verify the required parameter 'modelConfigKey' is set
        if (modelConfigKey == null) {
            throw new ApiException("Missing the required parameter 'modelConfigKey' when calling deleteModelConfig(Async)");
        }
        

        okhttp3.Call localVarCall = deleteModelConfigCall(ldAPIVersion, projectKey, modelConfigKey, _callback);
        return localVarCall;

    }

    /**
     * Delete an AI model config
     * Delete an AI model config.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param modelConfigKey  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public void deleteModelConfig(String ldAPIVersion, String projectKey, String modelConfigKey) throws ApiException {
        deleteModelConfigWithHttpInfo(ldAPIVersion, projectKey, modelConfigKey);
    }

    /**
     * Delete an AI model config
     * Delete an AI model config.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param modelConfigKey  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteModelConfigWithHttpInfo(String ldAPIVersion, String projectKey, String modelConfigKey) throws ApiException {
        okhttp3.Call localVarCall = deleteModelConfigValidateBeforeCall(ldAPIVersion, projectKey, modelConfigKey, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete an AI model config (asynchronously)
     * Delete an AI model config.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param modelConfigKey  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteModelConfigAsync(String ldAPIVersion, String projectKey, String modelConfigKey, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteModelConfigValidateBeforeCall(ldAPIVersion, projectKey, modelConfigKey, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAIConfig
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config found </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAIConfigCall(String ldAPIVersion, String projectKey, String configKey, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/ai-configs/{configKey}"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()))
            .replaceAll("\\{" + "configKey" + "\\}", localVarApiClient.escapeString(configKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getAIConfigValidateBeforeCall(String ldAPIVersion, String projectKey, String configKey, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling getAIConfig(Async)");
        }
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getAIConfig(Async)");
        }
        
        // verify the required parameter 'configKey' is set
        if (configKey == null) {
            throw new ApiException("Missing the required parameter 'configKey' when calling getAIConfig(Async)");
        }
        

        okhttp3.Call localVarCall = getAIConfigCall(ldAPIVersion, projectKey, configKey, _callback);
        return localVarCall;

    }

    /**
     * Get AI Config
     * Retrieve a specific AI Config by its key.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @return AIConfig
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config found </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AIConfig getAIConfig(String ldAPIVersion, String projectKey, String configKey) throws ApiException {
        ApiResponse<AIConfig> localVarResp = getAIConfigWithHttpInfo(ldAPIVersion, projectKey, configKey);
        return localVarResp.getData();
    }

    /**
     * Get AI Config
     * Retrieve a specific AI Config by its key.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @return ApiResponse&lt;AIConfig&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config found </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AIConfig> getAIConfigWithHttpInfo(String ldAPIVersion, String projectKey, String configKey) throws ApiException {
        okhttp3.Call localVarCall = getAIConfigValidateBeforeCall(ldAPIVersion, projectKey, configKey, null);
        Type localVarReturnType = new TypeToken<AIConfig>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get AI Config (asynchronously)
     * Retrieve a specific AI Config by its key.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config found </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAIConfigAsync(String ldAPIVersion, String projectKey, String configKey, final ApiCallback<AIConfig> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAIConfigValidateBeforeCall(ldAPIVersion, projectKey, configKey, _callback);
        Type localVarReturnType = new TypeToken<AIConfig>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAIConfigMetrics
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param from The starting time, as milliseconds since epoch (inclusive). (required)
     * @param to The ending time, as milliseconds since epoch (exclusive). May not be more than 100 days after &#x60;from&#x60;. (required)
     * @param env An environment key. Only metrics from this environment will be included. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metrics computed </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAIConfigMetricsCall(String ldAPIVersion, String projectKey, String configKey, Integer from, Integer to, String env, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/ai-configs/{configKey}/metrics"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()))
            .replaceAll("\\{" + "configKey" + "\\}", localVarApiClient.escapeString(configKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (from != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("from", from));
        }

        if (to != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("to", to));
        }

        if (env != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("env", env));
        }

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getAIConfigMetricsValidateBeforeCall(String ldAPIVersion, String projectKey, String configKey, Integer from, Integer to, String env, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling getAIConfigMetrics(Async)");
        }
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getAIConfigMetrics(Async)");
        }
        
        // verify the required parameter 'configKey' is set
        if (configKey == null) {
            throw new ApiException("Missing the required parameter 'configKey' when calling getAIConfigMetrics(Async)");
        }
        
        // verify the required parameter 'from' is set
        if (from == null) {
            throw new ApiException("Missing the required parameter 'from' when calling getAIConfigMetrics(Async)");
        }
        
        // verify the required parameter 'to' is set
        if (to == null) {
            throw new ApiException("Missing the required parameter 'to' when calling getAIConfigMetrics(Async)");
        }
        
        // verify the required parameter 'env' is set
        if (env == null) {
            throw new ApiException("Missing the required parameter 'env' when calling getAIConfigMetrics(Async)");
        }
        

        okhttp3.Call localVarCall = getAIConfigMetricsCall(ldAPIVersion, projectKey, configKey, from, to, env, _callback);
        return localVarCall;

    }

    /**
     * Get AI Config metrics
     * Retrieve usage metrics for an AI Config by config key.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param from The starting time, as milliseconds since epoch (inclusive). (required)
     * @param to The ending time, as milliseconds since epoch (exclusive). May not be more than 100 days after &#x60;from&#x60;. (required)
     * @param env An environment key. Only metrics from this environment will be included. (required)
     * @return Metrics
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metrics computed </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public Metrics getAIConfigMetrics(String ldAPIVersion, String projectKey, String configKey, Integer from, Integer to, String env) throws ApiException {
        ApiResponse<Metrics> localVarResp = getAIConfigMetricsWithHttpInfo(ldAPIVersion, projectKey, configKey, from, to, env);
        return localVarResp.getData();
    }

    /**
     * Get AI Config metrics
     * Retrieve usage metrics for an AI Config by config key.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param from The starting time, as milliseconds since epoch (inclusive). (required)
     * @param to The ending time, as milliseconds since epoch (exclusive). May not be more than 100 days after &#x60;from&#x60;. (required)
     * @param env An environment key. Only metrics from this environment will be included. (required)
     * @return ApiResponse&lt;Metrics&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metrics computed </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Metrics> getAIConfigMetricsWithHttpInfo(String ldAPIVersion, String projectKey, String configKey, Integer from, Integer to, String env) throws ApiException {
        okhttp3.Call localVarCall = getAIConfigMetricsValidateBeforeCall(ldAPIVersion, projectKey, configKey, from, to, env, null);
        Type localVarReturnType = new TypeToken<Metrics>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get AI Config metrics (asynchronously)
     * Retrieve usage metrics for an AI Config by config key.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param from The starting time, as milliseconds since epoch (inclusive). (required)
     * @param to The ending time, as milliseconds since epoch (exclusive). May not be more than 100 days after &#x60;from&#x60;. (required)
     * @param env An environment key. Only metrics from this environment will be included. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metrics computed </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAIConfigMetricsAsync(String ldAPIVersion, String projectKey, String configKey, Integer from, Integer to, String env, final ApiCallback<Metrics> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAIConfigMetricsValidateBeforeCall(ldAPIVersion, projectKey, configKey, from, to, env, _callback);
        Type localVarReturnType = new TypeToken<Metrics>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAIConfigMetricsByVariation
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param from The starting time, as milliseconds since epoch (inclusive). (required)
     * @param to The ending time, as milliseconds since epoch (exclusive). May not be more than 100 days after &#x60;from&#x60;. (required)
     * @param env An environment key. Only metrics from this environment will be included. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metrics computed </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAIConfigMetricsByVariationCall(String ldAPIVersion, String projectKey, String configKey, Integer from, Integer to, String env, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/ai-configs/{configKey}/metrics-by-variation"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()))
            .replaceAll("\\{" + "configKey" + "\\}", localVarApiClient.escapeString(configKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (from != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("from", from));
        }

        if (to != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("to", to));
        }

        if (env != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("env", env));
        }

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getAIConfigMetricsByVariationValidateBeforeCall(String ldAPIVersion, String projectKey, String configKey, Integer from, Integer to, String env, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling getAIConfigMetricsByVariation(Async)");
        }
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getAIConfigMetricsByVariation(Async)");
        }
        
        // verify the required parameter 'configKey' is set
        if (configKey == null) {
            throw new ApiException("Missing the required parameter 'configKey' when calling getAIConfigMetricsByVariation(Async)");
        }
        
        // verify the required parameter 'from' is set
        if (from == null) {
            throw new ApiException("Missing the required parameter 'from' when calling getAIConfigMetricsByVariation(Async)");
        }
        
        // verify the required parameter 'to' is set
        if (to == null) {
            throw new ApiException("Missing the required parameter 'to' when calling getAIConfigMetricsByVariation(Async)");
        }
        
        // verify the required parameter 'env' is set
        if (env == null) {
            throw new ApiException("Missing the required parameter 'env' when calling getAIConfigMetricsByVariation(Async)");
        }
        

        okhttp3.Call localVarCall = getAIConfigMetricsByVariationCall(ldAPIVersion, projectKey, configKey, from, to, env, _callback);
        return localVarCall;

    }

    /**
     * Get AI Config metrics by variation
     * Retrieve usage metrics for an AI Config by config key, with results split by variation.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param from The starting time, as milliseconds since epoch (inclusive). (required)
     * @param to The ending time, as milliseconds since epoch (exclusive). May not be more than 100 days after &#x60;from&#x60;. (required)
     * @param env An environment key. Only metrics from this environment will be included. (required)
     * @return List&lt;MetricByVariation&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metrics computed </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public List<MetricByVariation> getAIConfigMetricsByVariation(String ldAPIVersion, String projectKey, String configKey, Integer from, Integer to, String env) throws ApiException {
        ApiResponse<List<MetricByVariation>> localVarResp = getAIConfigMetricsByVariationWithHttpInfo(ldAPIVersion, projectKey, configKey, from, to, env);
        return localVarResp.getData();
    }

    /**
     * Get AI Config metrics by variation
     * Retrieve usage metrics for an AI Config by config key, with results split by variation.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param from The starting time, as milliseconds since epoch (inclusive). (required)
     * @param to The ending time, as milliseconds since epoch (exclusive). May not be more than 100 days after &#x60;from&#x60;. (required)
     * @param env An environment key. Only metrics from this environment will be included. (required)
     * @return ApiResponse&lt;List&lt;MetricByVariation&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metrics computed </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<List<MetricByVariation>> getAIConfigMetricsByVariationWithHttpInfo(String ldAPIVersion, String projectKey, String configKey, Integer from, Integer to, String env) throws ApiException {
        okhttp3.Call localVarCall = getAIConfigMetricsByVariationValidateBeforeCall(ldAPIVersion, projectKey, configKey, from, to, env, null);
        Type localVarReturnType = new TypeToken<List<MetricByVariation>>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get AI Config metrics by variation (asynchronously)
     * Retrieve usage metrics for an AI Config by config key, with results split by variation.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param from The starting time, as milliseconds since epoch (inclusive). (required)
     * @param to The ending time, as milliseconds since epoch (exclusive). May not be more than 100 days after &#x60;from&#x60;. (required)
     * @param env An environment key. Only metrics from this environment will be included. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metrics computed </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAIConfigMetricsByVariationAsync(String ldAPIVersion, String projectKey, String configKey, Integer from, Integer to, String env, final ApiCallback<List<MetricByVariation>> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAIConfigMetricsByVariationValidateBeforeCall(ldAPIVersion, projectKey, configKey, from, to, env, _callback);
        Type localVarReturnType = new TypeToken<List<MetricByVariation>>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAIConfigVariation
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAIConfigVariationCall(String ldAPIVersion, String projectKey, String configKey, String variationKey, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/ai-configs/{configKey}/variations/{variationKey}"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()))
            .replaceAll("\\{" + "configKey" + "\\}", localVarApiClient.escapeString(configKey.toString()))
            .replaceAll("\\{" + "variationKey" + "\\}", localVarApiClient.escapeString(variationKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getAIConfigVariationValidateBeforeCall(String ldAPIVersion, String projectKey, String configKey, String variationKey, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling getAIConfigVariation(Async)");
        }
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getAIConfigVariation(Async)");
        }
        
        // verify the required parameter 'configKey' is set
        if (configKey == null) {
            throw new ApiException("Missing the required parameter 'configKey' when calling getAIConfigVariation(Async)");
        }
        
        // verify the required parameter 'variationKey' is set
        if (variationKey == null) {
            throw new ApiException("Missing the required parameter 'variationKey' when calling getAIConfigVariation(Async)");
        }
        

        okhttp3.Call localVarCall = getAIConfigVariationCall(ldAPIVersion, projectKey, configKey, variationKey, _callback);
        return localVarCall;

    }

    /**
     * Get AI Config variation
     * Get an AI Config variation by key. The response includes all variation versions for the given variation key.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
     * @return AIConfigVariationsResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AIConfigVariationsResponse getAIConfigVariation(String ldAPIVersion, String projectKey, String configKey, String variationKey) throws ApiException {
        ApiResponse<AIConfigVariationsResponse> localVarResp = getAIConfigVariationWithHttpInfo(ldAPIVersion, projectKey, configKey, variationKey);
        return localVarResp.getData();
    }

    /**
     * Get AI Config variation
     * Get an AI Config variation by key. The response includes all variation versions for the given variation key.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
     * @return ApiResponse&lt;AIConfigVariationsResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AIConfigVariationsResponse> getAIConfigVariationWithHttpInfo(String ldAPIVersion, String projectKey, String configKey, String variationKey) throws ApiException {
        okhttp3.Call localVarCall = getAIConfigVariationValidateBeforeCall(ldAPIVersion, projectKey, configKey, variationKey, null);
        Type localVarReturnType = new TypeToken<AIConfigVariationsResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get AI Config variation (asynchronously)
     * Get an AI Config variation by key. The response includes all variation versions for the given variation key.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAIConfigVariationAsync(String ldAPIVersion, String projectKey, String configKey, String variationKey, final ApiCallback<AIConfigVariationsResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAIConfigVariationValidateBeforeCall(ldAPIVersion, projectKey, configKey, variationKey, _callback);
        Type localVarReturnType = new TypeToken<AIConfigVariationsResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAIConfigs
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param sort A sort to apply to the list of AI Configs. (optional)
     * @param limit The number of AI Configs to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param filter A filter to apply to the list of AI Configs. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAIConfigsCall(String ldAPIVersion, String projectKey, String sort, Integer limit, Integer offset, String filter, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/ai-configs"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (sort != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sort", sort));
        }

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (offset != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("offset", offset));
        }

        if (filter != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("filter", filter));
        }

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getAIConfigsValidateBeforeCall(String ldAPIVersion, String projectKey, String sort, Integer limit, Integer offset, String filter, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling getAIConfigs(Async)");
        }
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getAIConfigs(Async)");
        }
        

        okhttp3.Call localVarCall = getAIConfigsCall(ldAPIVersion, projectKey, sort, limit, offset, filter, _callback);
        return localVarCall;

    }

    /**
     * List AI Configs
     * Get a list of all AI Configs in the given project.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param sort A sort to apply to the list of AI Configs. (optional)
     * @param limit The number of AI Configs to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param filter A filter to apply to the list of AI Configs. (optional)
     * @return AIConfigs
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AIConfigs getAIConfigs(String ldAPIVersion, String projectKey, String sort, Integer limit, Integer offset, String filter) throws ApiException {
        ApiResponse<AIConfigs> localVarResp = getAIConfigsWithHttpInfo(ldAPIVersion, projectKey, sort, limit, offset, filter);
        return localVarResp.getData();
    }

    /**
     * List AI Configs
     * Get a list of all AI Configs in the given project.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param sort A sort to apply to the list of AI Configs. (optional)
     * @param limit The number of AI Configs to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param filter A filter to apply to the list of AI Configs. (optional)
     * @return ApiResponse&lt;AIConfigs&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AIConfigs> getAIConfigsWithHttpInfo(String ldAPIVersion, String projectKey, String sort, Integer limit, Integer offset, String filter) throws ApiException {
        okhttp3.Call localVarCall = getAIConfigsValidateBeforeCall(ldAPIVersion, projectKey, sort, limit, offset, filter, null);
        Type localVarReturnType = new TypeToken<AIConfigs>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List AI Configs (asynchronously)
     * Get a list of all AI Configs in the given project.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param sort A sort to apply to the list of AI Configs. (optional)
     * @param limit The number of AI Configs to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param filter A filter to apply to the list of AI Configs. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAIConfigsAsync(String ldAPIVersion, String projectKey, String sort, Integer limit, Integer offset, String filter, final ApiCallback<AIConfigs> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAIConfigsValidateBeforeCall(ldAPIVersion, projectKey, sort, limit, offset, filter, _callback);
        Type localVarReturnType = new TypeToken<AIConfigs>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getModelConfig
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param modelConfigKey  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getModelConfigCall(String ldAPIVersion, String projectKey, String modelConfigKey, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/ai-configs/model-configs/{modelConfigKey}"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()))
            .replaceAll("\\{" + "modelConfigKey" + "\\}", localVarApiClient.escapeString(modelConfigKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getModelConfigValidateBeforeCall(String ldAPIVersion, String projectKey, String modelConfigKey, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling getModelConfig(Async)");
        }
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getModelConfig(Async)");
        }
        
        // verify the required parameter 'modelConfigKey' is set
        if (modelConfigKey == null) {
            throw new ApiException("Missing the required parameter 'modelConfigKey' when calling getModelConfig(Async)");
        }
        

        okhttp3.Call localVarCall = getModelConfigCall(ldAPIVersion, projectKey, modelConfigKey, _callback);
        return localVarCall;

    }

    /**
     * Get AI model config
     * Get an AI model config by key.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param modelConfigKey  (required)
     * @return ModelConfig
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ModelConfig getModelConfig(String ldAPIVersion, String projectKey, String modelConfigKey) throws ApiException {
        ApiResponse<ModelConfig> localVarResp = getModelConfigWithHttpInfo(ldAPIVersion, projectKey, modelConfigKey);
        return localVarResp.getData();
    }

    /**
     * Get AI model config
     * Get an AI model config by key.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param modelConfigKey  (required)
     * @return ApiResponse&lt;ModelConfig&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ModelConfig> getModelConfigWithHttpInfo(String ldAPIVersion, String projectKey, String modelConfigKey) throws ApiException {
        okhttp3.Call localVarCall = getModelConfigValidateBeforeCall(ldAPIVersion, projectKey, modelConfigKey, null);
        Type localVarReturnType = new TypeToken<ModelConfig>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get AI model config (asynchronously)
     * Get an AI model config by key.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param modelConfigKey  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getModelConfigAsync(String ldAPIVersion, String projectKey, String modelConfigKey, final ApiCallback<ModelConfig> _callback) throws ApiException {

        okhttp3.Call localVarCall = getModelConfigValidateBeforeCall(ldAPIVersion, projectKey, modelConfigKey, _callback);
        Type localVarReturnType = new TypeToken<ModelConfig>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listModelConfigs
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listModelConfigsCall(String ldAPIVersion, String projectKey, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/ai-configs/model-configs"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call listModelConfigsValidateBeforeCall(String ldAPIVersion, String projectKey, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling listModelConfigs(Async)");
        }
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling listModelConfigs(Async)");
        }
        

        okhttp3.Call localVarCall = listModelConfigsCall(ldAPIVersion, projectKey, _callback);
        return localVarCall;

    }

    /**
     * List AI model configs
     * Get all AI model configs for a project.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @return List&lt;ModelConfig&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public List<ModelConfig> listModelConfigs(String ldAPIVersion, String projectKey) throws ApiException {
        ApiResponse<List<ModelConfig>> localVarResp = listModelConfigsWithHttpInfo(ldAPIVersion, projectKey);
        return localVarResp.getData();
    }

    /**
     * List AI model configs
     * Get all AI model configs for a project.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @return ApiResponse&lt;List&lt;ModelConfig&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<List<ModelConfig>> listModelConfigsWithHttpInfo(String ldAPIVersion, String projectKey) throws ApiException {
        okhttp3.Call localVarCall = listModelConfigsValidateBeforeCall(ldAPIVersion, projectKey, null);
        Type localVarReturnType = new TypeToken<List<ModelConfig>>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List AI model configs (asynchronously)
     * Get all AI model configs for a project.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listModelConfigsAsync(String ldAPIVersion, String projectKey, final ApiCallback<List<ModelConfig>> _callback) throws ApiException {

        okhttp3.Call localVarCall = listModelConfigsValidateBeforeCall(ldAPIVersion, projectKey, _callback);
        Type localVarReturnType = new TypeToken<List<ModelConfig>>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for patchAIConfig
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param aiConfigPatch AI Config object to update (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchAIConfigCall(String ldAPIVersion, String projectKey, String configKey, AIConfigPatch aiConfigPatch, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = aiConfigPatch;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/ai-configs/{configKey}"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()))
            .replaceAll("\\{" + "configKey" + "\\}", localVarApiClient.escapeString(configKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "PATCH", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call patchAIConfigValidateBeforeCall(String ldAPIVersion, String projectKey, String configKey, AIConfigPatch aiConfigPatch, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling patchAIConfig(Async)");
        }
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling patchAIConfig(Async)");
        }
        
        // verify the required parameter 'configKey' is set
        if (configKey == null) {
            throw new ApiException("Missing the required parameter 'configKey' when calling patchAIConfig(Async)");
        }
        

        okhttp3.Call localVarCall = patchAIConfigCall(ldAPIVersion, projectKey, configKey, aiConfigPatch, _callback);
        return localVarCall;

    }

    /**
     * Update AI Config
     * Edit an existing AI Config.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  Here&#39;s an example:   &#x60;&#x60;&#x60;     {       \&quot;description\&quot;: \&quot;Example updated description\&quot;,       \&quot;tags\&quot;: [\&quot;new-tag\&quot;]     }   &#x60;&#x60;&#x60; 
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param aiConfigPatch AI Config object to update (optional)
     * @return AIConfig
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AIConfig patchAIConfig(String ldAPIVersion, String projectKey, String configKey, AIConfigPatch aiConfigPatch) throws ApiException {
        ApiResponse<AIConfig> localVarResp = patchAIConfigWithHttpInfo(ldAPIVersion, projectKey, configKey, aiConfigPatch);
        return localVarResp.getData();
    }

    /**
     * Update AI Config
     * Edit an existing AI Config.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  Here&#39;s an example:   &#x60;&#x60;&#x60;     {       \&quot;description\&quot;: \&quot;Example updated description\&quot;,       \&quot;tags\&quot;: [\&quot;new-tag\&quot;]     }   &#x60;&#x60;&#x60; 
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param aiConfigPatch AI Config object to update (optional)
     * @return ApiResponse&lt;AIConfig&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AIConfig> patchAIConfigWithHttpInfo(String ldAPIVersion, String projectKey, String configKey, AIConfigPatch aiConfigPatch) throws ApiException {
        okhttp3.Call localVarCall = patchAIConfigValidateBeforeCall(ldAPIVersion, projectKey, configKey, aiConfigPatch, null);
        Type localVarReturnType = new TypeToken<AIConfig>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update AI Config (asynchronously)
     * Edit an existing AI Config.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  Here&#39;s an example:   &#x60;&#x60;&#x60;     {       \&quot;description\&quot;: \&quot;Example updated description\&quot;,       \&quot;tags\&quot;: [\&quot;new-tag\&quot;]     }   &#x60;&#x60;&#x60; 
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param aiConfigPatch AI Config object to update (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchAIConfigAsync(String ldAPIVersion, String projectKey, String configKey, AIConfigPatch aiConfigPatch, final ApiCallback<AIConfig> _callback) throws ApiException {

        okhttp3.Call localVarCall = patchAIConfigValidateBeforeCall(ldAPIVersion, projectKey, configKey, aiConfigPatch, _callback);
        Type localVarReturnType = new TypeToken<AIConfig>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for patchAIConfigVariation
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
     * @param aiConfigVariationPatch AI Config variation object to update (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config variation updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchAIConfigVariationCall(String ldAPIVersion, String projectKey, String configKey, String variationKey, AIConfigVariationPatch aiConfigVariationPatch, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = aiConfigVariationPatch;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/ai-configs/{configKey}/variations/{variationKey}"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()))
            .replaceAll("\\{" + "configKey" + "\\}", localVarApiClient.escapeString(configKey.toString()))
            .replaceAll("\\{" + "variationKey" + "\\}", localVarApiClient.escapeString(variationKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "PATCH", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call patchAIConfigVariationValidateBeforeCall(String ldAPIVersion, String projectKey, String configKey, String variationKey, AIConfigVariationPatch aiConfigVariationPatch, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling patchAIConfigVariation(Async)");
        }
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling patchAIConfigVariation(Async)");
        }
        
        // verify the required parameter 'configKey' is set
        if (configKey == null) {
            throw new ApiException("Missing the required parameter 'configKey' when calling patchAIConfigVariation(Async)");
        }
        
        // verify the required parameter 'variationKey' is set
        if (variationKey == null) {
            throw new ApiException("Missing the required parameter 'variationKey' when calling patchAIConfigVariation(Async)");
        }
        

        okhttp3.Call localVarCall = patchAIConfigVariationCall(ldAPIVersion, projectKey, configKey, variationKey, aiConfigVariationPatch, _callback);
        return localVarCall;

    }

    /**
     * Update AI Config variation
     * Edit an existing variation of an AI Config. This creates a new version of the variation.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  Here&#39;s an example: &#x60;&#x60;&#x60;   {     \&quot;messages\&quot;: [       {         \&quot;role\&quot;: \&quot;system\&quot;,         \&quot;content\&quot;: \&quot;The new message\&quot;       }     ]   } &#x60;&#x60;&#x60; 
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
     * @param aiConfigVariationPatch AI Config variation object to update (optional)
     * @return AIConfigVariation
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config variation updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AIConfigVariation patchAIConfigVariation(String ldAPIVersion, String projectKey, String configKey, String variationKey, AIConfigVariationPatch aiConfigVariationPatch) throws ApiException {
        ApiResponse<AIConfigVariation> localVarResp = patchAIConfigVariationWithHttpInfo(ldAPIVersion, projectKey, configKey, variationKey, aiConfigVariationPatch);
        return localVarResp.getData();
    }

    /**
     * Update AI Config variation
     * Edit an existing variation of an AI Config. This creates a new version of the variation.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  Here&#39;s an example: &#x60;&#x60;&#x60;   {     \&quot;messages\&quot;: [       {         \&quot;role\&quot;: \&quot;system\&quot;,         \&quot;content\&quot;: \&quot;The new message\&quot;       }     ]   } &#x60;&#x60;&#x60; 
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
     * @param aiConfigVariationPatch AI Config variation object to update (optional)
     * @return ApiResponse&lt;AIConfigVariation&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config variation updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AIConfigVariation> patchAIConfigVariationWithHttpInfo(String ldAPIVersion, String projectKey, String configKey, String variationKey, AIConfigVariationPatch aiConfigVariationPatch) throws ApiException {
        okhttp3.Call localVarCall = patchAIConfigVariationValidateBeforeCall(ldAPIVersion, projectKey, configKey, variationKey, aiConfigVariationPatch, null);
        Type localVarReturnType = new TypeToken<AIConfigVariation>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update AI Config variation (asynchronously)
     * Edit an existing variation of an AI Config. This creates a new version of the variation.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  Here&#39;s an example: &#x60;&#x60;&#x60;   {     \&quot;messages\&quot;: [       {         \&quot;role\&quot;: \&quot;system\&quot;,         \&quot;content\&quot;: \&quot;The new message\&quot;       }     ]   } &#x60;&#x60;&#x60; 
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
     * @param aiConfigVariationPatch AI Config variation object to update (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config variation updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchAIConfigVariationAsync(String ldAPIVersion, String projectKey, String configKey, String variationKey, AIConfigVariationPatch aiConfigVariationPatch, final ApiCallback<AIConfigVariation> _callback) throws ApiException {

        okhttp3.Call localVarCall = patchAIConfigVariationValidateBeforeCall(ldAPIVersion, projectKey, configKey, variationKey, aiConfigVariationPatch, _callback);
        Type localVarReturnType = new TypeToken<AIConfigVariation>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for postAIConfig
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param aiConfigPost AI Config object to create (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> AI Config created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postAIConfigCall(String ldAPIVersion, String projectKey, AIConfigPost aiConfigPost, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = aiConfigPost;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/ai-configs"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call postAIConfigValidateBeforeCall(String ldAPIVersion, String projectKey, AIConfigPost aiConfigPost, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling postAIConfig(Async)");
        }
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling postAIConfig(Async)");
        }
        
        // verify the required parameter 'aiConfigPost' is set
        if (aiConfigPost == null) {
            throw new ApiException("Missing the required parameter 'aiConfigPost' when calling postAIConfig(Async)");
        }
        

        okhttp3.Call localVarCall = postAIConfigCall(ldAPIVersion, projectKey, aiConfigPost, _callback);
        return localVarCall;

    }

    /**
     * Create new AI Config
     * Create a new AI Config within the given project.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param aiConfigPost AI Config object to create (required)
     * @return AIConfig
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> AI Config created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AIConfig postAIConfig(String ldAPIVersion, String projectKey, AIConfigPost aiConfigPost) throws ApiException {
        ApiResponse<AIConfig> localVarResp = postAIConfigWithHttpInfo(ldAPIVersion, projectKey, aiConfigPost);
        return localVarResp.getData();
    }

    /**
     * Create new AI Config
     * Create a new AI Config within the given project.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param aiConfigPost AI Config object to create (required)
     * @return ApiResponse&lt;AIConfig&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> AI Config created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AIConfig> postAIConfigWithHttpInfo(String ldAPIVersion, String projectKey, AIConfigPost aiConfigPost) throws ApiException {
        okhttp3.Call localVarCall = postAIConfigValidateBeforeCall(ldAPIVersion, projectKey, aiConfigPost, null);
        Type localVarReturnType = new TypeToken<AIConfig>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create new AI Config (asynchronously)
     * Create a new AI Config within the given project.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param aiConfigPost AI Config object to create (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> AI Config created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postAIConfigAsync(String ldAPIVersion, String projectKey, AIConfigPost aiConfigPost, final ApiCallback<AIConfig> _callback) throws ApiException {

        okhttp3.Call localVarCall = postAIConfigValidateBeforeCall(ldAPIVersion, projectKey, aiConfigPost, _callback);
        Type localVarReturnType = new TypeToken<AIConfig>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for postAIConfigVariation
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param aiConfigVariationPost AI Config variation object to create (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> AI Config variation created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postAIConfigVariationCall(String ldAPIVersion, String projectKey, String configKey, AIConfigVariationPost aiConfigVariationPost, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = aiConfigVariationPost;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/ai-configs/{configKey}/variations"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()))
            .replaceAll("\\{" + "configKey" + "\\}", localVarApiClient.escapeString(configKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call postAIConfigVariationValidateBeforeCall(String ldAPIVersion, String projectKey, String configKey, AIConfigVariationPost aiConfigVariationPost, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling postAIConfigVariation(Async)");
        }
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling postAIConfigVariation(Async)");
        }
        
        // verify the required parameter 'configKey' is set
        if (configKey == null) {
            throw new ApiException("Missing the required parameter 'configKey' when calling postAIConfigVariation(Async)");
        }
        
        // verify the required parameter 'aiConfigVariationPost' is set
        if (aiConfigVariationPost == null) {
            throw new ApiException("Missing the required parameter 'aiConfigVariationPost' when calling postAIConfigVariation(Async)");
        }
        

        okhttp3.Call localVarCall = postAIConfigVariationCall(ldAPIVersion, projectKey, configKey, aiConfigVariationPost, _callback);
        return localVarCall;

    }

    /**
     * Create AI Config variation
     * Create a new variation for a given AI Config.  The &lt;code&gt;model&lt;/code&gt; in the request body requires a &lt;code&gt;modelName&lt;/code&gt; and &lt;code&gt;parameters&lt;/code&gt;, for example:  &#x60;&#x60;&#x60;   \&quot;model\&quot;: {     \&quot;modelName\&quot;: \&quot;claude-3-opus-20240229\&quot;,     \&quot;parameters\&quot;: {       \&quot;max_tokens\&quot;: 1024     }   } &#x60;&#x60;&#x60; 
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param aiConfigVariationPost AI Config variation object to create (required)
     * @return AIConfigVariation
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> AI Config variation created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AIConfigVariation postAIConfigVariation(String ldAPIVersion, String projectKey, String configKey, AIConfigVariationPost aiConfigVariationPost) throws ApiException {
        ApiResponse<AIConfigVariation> localVarResp = postAIConfigVariationWithHttpInfo(ldAPIVersion, projectKey, configKey, aiConfigVariationPost);
        return localVarResp.getData();
    }

    /**
     * Create AI Config variation
     * Create a new variation for a given AI Config.  The &lt;code&gt;model&lt;/code&gt; in the request body requires a &lt;code&gt;modelName&lt;/code&gt; and &lt;code&gt;parameters&lt;/code&gt;, for example:  &#x60;&#x60;&#x60;   \&quot;model\&quot;: {     \&quot;modelName\&quot;: \&quot;claude-3-opus-20240229\&quot;,     \&quot;parameters\&quot;: {       \&quot;max_tokens\&quot;: 1024     }   } &#x60;&#x60;&#x60; 
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param aiConfigVariationPost AI Config variation object to create (required)
     * @return ApiResponse&lt;AIConfigVariation&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> AI Config variation created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AIConfigVariation> postAIConfigVariationWithHttpInfo(String ldAPIVersion, String projectKey, String configKey, AIConfigVariationPost aiConfigVariationPost) throws ApiException {
        okhttp3.Call localVarCall = postAIConfigVariationValidateBeforeCall(ldAPIVersion, projectKey, configKey, aiConfigVariationPost, null);
        Type localVarReturnType = new TypeToken<AIConfigVariation>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create AI Config variation (asynchronously)
     * Create a new variation for a given AI Config.  The &lt;code&gt;model&lt;/code&gt; in the request body requires a &lt;code&gt;modelName&lt;/code&gt; and &lt;code&gt;parameters&lt;/code&gt;, for example:  &#x60;&#x60;&#x60;   \&quot;model\&quot;: {     \&quot;modelName\&quot;: \&quot;claude-3-opus-20240229\&quot;,     \&quot;parameters\&quot;: {       \&quot;max_tokens\&quot;: 1024     }   } &#x60;&#x60;&#x60; 
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param aiConfigVariationPost AI Config variation object to create (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> AI Config variation created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postAIConfigVariationAsync(String ldAPIVersion, String projectKey, String configKey, AIConfigVariationPost aiConfigVariationPost, final ApiCallback<AIConfigVariation> _callback) throws ApiException {

        okhttp3.Call localVarCall = postAIConfigVariationValidateBeforeCall(ldAPIVersion, projectKey, configKey, aiConfigVariationPost, _callback);
        Type localVarReturnType = new TypeToken<AIConfigVariation>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for postModelConfig
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param modelConfigPost AI model config object to create (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postModelConfigCall(String ldAPIVersion, String projectKey, ModelConfigPost modelConfigPost, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = modelConfigPost;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/ai-configs/model-configs"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call postModelConfigValidateBeforeCall(String ldAPIVersion, String projectKey, ModelConfigPost modelConfigPost, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling postModelConfig(Async)");
        }
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling postModelConfig(Async)");
        }
        
        // verify the required parameter 'modelConfigPost' is set
        if (modelConfigPost == null) {
            throw new ApiException("Missing the required parameter 'modelConfigPost' when calling postModelConfig(Async)");
        }
        

        okhttp3.Call localVarCall = postModelConfigCall(ldAPIVersion, projectKey, modelConfigPost, _callback);
        return localVarCall;

    }

    /**
     * Create an AI model config
     * Create an AI model config. You can use this in any variation for any AI Config in your project.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param modelConfigPost AI model config object to create (required)
     * @return ModelConfig
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ModelConfig postModelConfig(String ldAPIVersion, String projectKey, ModelConfigPost modelConfigPost) throws ApiException {
        ApiResponse<ModelConfig> localVarResp = postModelConfigWithHttpInfo(ldAPIVersion, projectKey, modelConfigPost);
        return localVarResp.getData();
    }

    /**
     * Create an AI model config
     * Create an AI model config. You can use this in any variation for any AI Config in your project.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param modelConfigPost AI model config object to create (required)
     * @return ApiResponse&lt;ModelConfig&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ModelConfig> postModelConfigWithHttpInfo(String ldAPIVersion, String projectKey, ModelConfigPost modelConfigPost) throws ApiException {
        okhttp3.Call localVarCall = postModelConfigValidateBeforeCall(ldAPIVersion, projectKey, modelConfigPost, null);
        Type localVarReturnType = new TypeToken<ModelConfig>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create an AI model config (asynchronously)
     * Create an AI model config. You can use this in any variation for any AI Config in your project.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param modelConfigPost AI model config object to create (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postModelConfigAsync(String ldAPIVersion, String projectKey, ModelConfigPost modelConfigPost, final ApiCallback<ModelConfig> _callback) throws ApiException {

        okhttp3.Call localVarCall = postModelConfigValidateBeforeCall(ldAPIVersion, projectKey, modelConfigPost, _callback);
        Type localVarReturnType = new TypeToken<ModelConfig>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
