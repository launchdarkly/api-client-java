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


import com.launchdarkly.api.model.ForbiddenErrorRep;
import com.launchdarkly.api.model.InvalidRequestErrorRep;
import com.launchdarkly.api.model.MetricCollectionRep;
import com.launchdarkly.api.model.MetricPost;
import com.launchdarkly.api.model.MetricRep;
import com.launchdarkly.api.model.NotFoundErrorRep;
import com.launchdarkly.api.model.PatchOperation;
import com.launchdarkly.api.model.RateLimitedErrorRep;
import com.launchdarkly.api.model.StatusConflictErrorRep;
import com.launchdarkly.api.model.UnauthorizedErrorRep;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetricsApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public MetricsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public MetricsApi(ApiClient apiClient) {
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
     * Build call for deleteMetric
     * @param projectKey The project key (required)
     * @param metricKey The metric key (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteMetricCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String metricKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/metrics/{projectKey}/{metricKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "metricKey" + "}", localVarApiClient.escapeString(metricKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

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
    private okhttp3.Call deleteMetricValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String metricKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling deleteMetric(Async)");
        }

        // verify the required parameter 'metricKey' is set
        if (metricKey == null) {
            throw new ApiException("Missing the required parameter 'metricKey' when calling deleteMetric(Async)");
        }

        return deleteMetricCall(projectKey, metricKey, _callback);

    }

    /**
     * Delete metric
     * Delete a metric by key.
     * @param projectKey The project key (required)
     * @param metricKey The metric key (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public void deleteMetric(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String metricKey) throws ApiException {
        deleteMetricWithHttpInfo(projectKey, metricKey);
    }

    /**
     * Delete metric
     * Delete a metric by key.
     * @param projectKey The project key (required)
     * @param metricKey The metric key (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteMetricWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String metricKey) throws ApiException {
        okhttp3.Call localVarCall = deleteMetricValidateBeforeCall(projectKey, metricKey, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete metric (asynchronously)
     * Delete a metric by key.
     * @param projectKey The project key (required)
     * @param metricKey The metric key (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteMetricAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String metricKey, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteMetricValidateBeforeCall(projectKey, metricKey, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getMetric
     * @param projectKey The project key (required)
     * @param metricKey The metric key (required)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. Supported fields are &#x60;experiments&#x60;, &#x60;experimentCount&#x60;, &#x60;metricGroups&#x60;, &#x60;metricGroupCount&#x60;, &#x60;eventSources&#x60;, &#x60;guardedRollouts&#x60;, &#x60;guardedRolloutCount&#x60;, &#x60;lastUsedInExperiment&#x60;, and &#x60;lastUsedInGuardedRollout&#x60;. (optional)
     * @param versionId The specific version ID of the metric (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metric response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getMetricCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String metricKey, @javax.annotation.Nullable String expand, @javax.annotation.Nullable String versionId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/metrics/{projectKey}/{metricKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "metricKey" + "}", localVarApiClient.escapeString(metricKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (expand != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("expand", expand));
        }

        if (versionId != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("versionId", versionId));
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
    private okhttp3.Call getMetricValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String metricKey, @javax.annotation.Nullable String expand, @javax.annotation.Nullable String versionId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getMetric(Async)");
        }

        // verify the required parameter 'metricKey' is set
        if (metricKey == null) {
            throw new ApiException("Missing the required parameter 'metricKey' when calling getMetric(Async)");
        }

        return getMetricCall(projectKey, metricKey, expand, versionId, _callback);

    }

    /**
     * Get metric
     * Get information for a single metric from the specific project.  ### Expanding the metric response LaunchDarkly supports four fields for expanding the \&quot;Get metric\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  - &#x60;experiments&#x60; includes all experiments from the specific project that use the metric - &#x60;experimentCount&#x60; includes the number of experiments from the specific project that use the metric - &#x60;metricGroups&#x60; includes all metric groups from the specific project that use the metric - &#x60;metricGroupCount&#x60; includes the number of metric groups from the specific project that use the metric  For example, &#x60;expand&#x3D;experiments&#x60; includes the &#x60;experiments&#x60; field in the response. 
     * @param projectKey The project key (required)
     * @param metricKey The metric key (required)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. Supported fields are &#x60;experiments&#x60;, &#x60;experimentCount&#x60;, &#x60;metricGroups&#x60;, &#x60;metricGroupCount&#x60;, &#x60;eventSources&#x60;, &#x60;guardedRollouts&#x60;, &#x60;guardedRolloutCount&#x60;, &#x60;lastUsedInExperiment&#x60;, and &#x60;lastUsedInGuardedRollout&#x60;. (optional)
     * @param versionId The specific version ID of the metric (optional)
     * @return MetricRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metric response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public MetricRep getMetric(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String metricKey, @javax.annotation.Nullable String expand, @javax.annotation.Nullable String versionId) throws ApiException {
        ApiResponse<MetricRep> localVarResp = getMetricWithHttpInfo(projectKey, metricKey, expand, versionId);
        return localVarResp.getData();
    }

    /**
     * Get metric
     * Get information for a single metric from the specific project.  ### Expanding the metric response LaunchDarkly supports four fields for expanding the \&quot;Get metric\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  - &#x60;experiments&#x60; includes all experiments from the specific project that use the metric - &#x60;experimentCount&#x60; includes the number of experiments from the specific project that use the metric - &#x60;metricGroups&#x60; includes all metric groups from the specific project that use the metric - &#x60;metricGroupCount&#x60; includes the number of metric groups from the specific project that use the metric  For example, &#x60;expand&#x3D;experiments&#x60; includes the &#x60;experiments&#x60; field in the response. 
     * @param projectKey The project key (required)
     * @param metricKey The metric key (required)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. Supported fields are &#x60;experiments&#x60;, &#x60;experimentCount&#x60;, &#x60;metricGroups&#x60;, &#x60;metricGroupCount&#x60;, &#x60;eventSources&#x60;, &#x60;guardedRollouts&#x60;, &#x60;guardedRolloutCount&#x60;, &#x60;lastUsedInExperiment&#x60;, and &#x60;lastUsedInGuardedRollout&#x60;. (optional)
     * @param versionId The specific version ID of the metric (optional)
     * @return ApiResponse&lt;MetricRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metric response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<MetricRep> getMetricWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String metricKey, @javax.annotation.Nullable String expand, @javax.annotation.Nullable String versionId) throws ApiException {
        okhttp3.Call localVarCall = getMetricValidateBeforeCall(projectKey, metricKey, expand, versionId, null);
        Type localVarReturnType = new TypeToken<MetricRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get metric (asynchronously)
     * Get information for a single metric from the specific project.  ### Expanding the metric response LaunchDarkly supports four fields for expanding the \&quot;Get metric\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  - &#x60;experiments&#x60; includes all experiments from the specific project that use the metric - &#x60;experimentCount&#x60; includes the number of experiments from the specific project that use the metric - &#x60;metricGroups&#x60; includes all metric groups from the specific project that use the metric - &#x60;metricGroupCount&#x60; includes the number of metric groups from the specific project that use the metric  For example, &#x60;expand&#x3D;experiments&#x60; includes the &#x60;experiments&#x60; field in the response. 
     * @param projectKey The project key (required)
     * @param metricKey The metric key (required)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. Supported fields are &#x60;experiments&#x60;, &#x60;experimentCount&#x60;, &#x60;metricGroups&#x60;, &#x60;metricGroupCount&#x60;, &#x60;eventSources&#x60;, &#x60;guardedRollouts&#x60;, &#x60;guardedRolloutCount&#x60;, &#x60;lastUsedInExperiment&#x60;, and &#x60;lastUsedInGuardedRollout&#x60;. (optional)
     * @param versionId The specific version ID of the metric (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metric response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getMetricAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String metricKey, @javax.annotation.Nullable String expand, @javax.annotation.Nullable String versionId, final ApiCallback<MetricRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getMetricValidateBeforeCall(projectKey, metricKey, expand, versionId, _callback);
        Type localVarReturnType = new TypeToken<MetricRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getMetrics
     * @param projectKey The project key (required)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. (optional)
     * @param limit The number of metrics to return in the response. Defaults to 20. Maximum limit is 50. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and returns the next &#x60;limit&#x60; items. (optional)
     * @param sort A field to sort the items by. Prefix field by a dash ( - ) to sort in descending order. This endpoint supports sorting by &#x60;createdAt&#x60; or &#x60;name&#x60;. (optional)
     * @param filter A comma-separated list of filters. This endpoint accepts filtering by &#x60;query&#x60;, &#x60;tags&#x60;, &#39;eventKind&#39;, &#39;isNumeric&#39;, &#39;unitAggregationType&#x60;, &#x60;hasConnections&#x60;, &#x60;maintainerIds&#x60;, &#x60;maintainerTeamKey&#x60;, &#x60;view&#x60;, and &#x60;dataSourceKeys&#x60;. To learn more about the filter syntax, read the &#39;Filtering metrics&#39; section above. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metrics collection response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getMetricsCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable String expand, @javax.annotation.Nullable Long limit, @javax.annotation.Nullable Long offset, @javax.annotation.Nullable String sort, @javax.annotation.Nullable String filter, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/metrics/{projectKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (expand != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("expand", expand));
        }

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (offset != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("offset", offset));
        }

        if (sort != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sort", sort));
        }

        if (filter != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("filter", filter));
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
    private okhttp3.Call getMetricsValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable String expand, @javax.annotation.Nullable Long limit, @javax.annotation.Nullable Long offset, @javax.annotation.Nullable String sort, @javax.annotation.Nullable String filter, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getMetrics(Async)");
        }

        return getMetricsCall(projectKey, expand, limit, offset, sort, filter, _callback);

    }

    /**
     * List metrics
     * Get a list of all metrics for the specified project.  ### Filtering metrics  The &#x60;filter&#x60; parameter supports the following operators: &#x60;contains&#x60;, &#x60;equals&#x60;, &#x60;anyOf&#x60;.  #### Supported fields and operators  You can only filter certain fields in metrics when using the &#x60;filter&#x60; parameter. Additionally, you can only filter some fields with certain operators.  When you search for metrics, the &#x60;filter&#x60; parameter supports the following fields and operators:  |&lt;div style&#x3D;\&quot;width:120px\&quot;&gt;Field&lt;/div&gt; |Description |Supported operators | |---|---|---| | &#x60;eventKind&#x60; | The metric event kind. One of &#x60;custom&#x60;, &#x60;pageview&#x60;, &#x60;click&#x60;. | &#x60;equals&#x60; | | &#x60;hasConnections&#x60; | Whether the metric has connections to experiments or guarded rollouts. One of &#x60;true&#x60;, &#x60;false&#x60;. | &#x60;equals&#x60; | | &#x60;isNumeric&#x60; | Whether the metric is numeric. One of &#x60;true&#x60;, &#x60;false&#x60;. | &#x60;equals&#x60; | | &#x60;maintainerIds&#x60; | A comma-separated list of metric maintainer IDs. | &#x60;anyOf&#x60; | | &#x60;maintainerTeamKey&#x60; | The metric maintainer team key. | &#x60;equals&#x60; | | &#x60;query&#x60; | A \&quot;fuzzy\&quot; search across metric key and name. Supply a string or list of strings to the operator. | &#x60;equals&#x60; | | &#x60;tags&#x60; | The metric tags. | &#x60;contains&#x60; | | &#x60;unitAggregationType&#x60; | The metric&#39;s unit aggregation type. One of &#x60;sum&#x60;, &#x60;average&#x60;. | &#x60;equals&#x60; |  For example, the filter &#x60;?filter&#x3D;tags contains [\&quot;tag1\&quot;, \&quot;tag2\&quot;, \&quot;tag3\&quot;]&#x60; matches metrics that have all three tags.  The documented values for &#x60;filter&#x60; query parameters are prior to URL encoding. For example, the &#x60;[&#x60; in &#x60;?filter&#x3D;tags contains [\&quot;tag1\&quot;, \&quot;tag2\&quot;, \&quot;tag3\&quot;]&#x60; must be encoded to &#x60;%5B&#x60;.  ### Expanding the metric list response  LaunchDarkly supports expanding the \&quot;List metrics\&quot; response. By default, the expandable field is **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add the following supported field:  - &#x60;experimentCount&#x60; includes the number of experiments from the specific project that use the metric  For example, &#x60;expand&#x3D;experimentCount&#x60; includes the &#x60;experimentCount&#x60; field for each metric in the response. 
     * @param projectKey The project key (required)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. (optional)
     * @param limit The number of metrics to return in the response. Defaults to 20. Maximum limit is 50. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and returns the next &#x60;limit&#x60; items. (optional)
     * @param sort A field to sort the items by. Prefix field by a dash ( - ) to sort in descending order. This endpoint supports sorting by &#x60;createdAt&#x60; or &#x60;name&#x60;. (optional)
     * @param filter A comma-separated list of filters. This endpoint accepts filtering by &#x60;query&#x60;, &#x60;tags&#x60;, &#39;eventKind&#39;, &#39;isNumeric&#39;, &#39;unitAggregationType&#x60;, &#x60;hasConnections&#x60;, &#x60;maintainerIds&#x60;, &#x60;maintainerTeamKey&#x60;, &#x60;view&#x60;, and &#x60;dataSourceKeys&#x60;. To learn more about the filter syntax, read the &#39;Filtering metrics&#39; section above. (optional)
     * @return MetricCollectionRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metrics collection response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
     </table>
     */
    public MetricCollectionRep getMetrics(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable String expand, @javax.annotation.Nullable Long limit, @javax.annotation.Nullable Long offset, @javax.annotation.Nullable String sort, @javax.annotation.Nullable String filter) throws ApiException {
        ApiResponse<MetricCollectionRep> localVarResp = getMetricsWithHttpInfo(projectKey, expand, limit, offset, sort, filter);
        return localVarResp.getData();
    }

    /**
     * List metrics
     * Get a list of all metrics for the specified project.  ### Filtering metrics  The &#x60;filter&#x60; parameter supports the following operators: &#x60;contains&#x60;, &#x60;equals&#x60;, &#x60;anyOf&#x60;.  #### Supported fields and operators  You can only filter certain fields in metrics when using the &#x60;filter&#x60; parameter. Additionally, you can only filter some fields with certain operators.  When you search for metrics, the &#x60;filter&#x60; parameter supports the following fields and operators:  |&lt;div style&#x3D;\&quot;width:120px\&quot;&gt;Field&lt;/div&gt; |Description |Supported operators | |---|---|---| | &#x60;eventKind&#x60; | The metric event kind. One of &#x60;custom&#x60;, &#x60;pageview&#x60;, &#x60;click&#x60;. | &#x60;equals&#x60; | | &#x60;hasConnections&#x60; | Whether the metric has connections to experiments or guarded rollouts. One of &#x60;true&#x60;, &#x60;false&#x60;. | &#x60;equals&#x60; | | &#x60;isNumeric&#x60; | Whether the metric is numeric. One of &#x60;true&#x60;, &#x60;false&#x60;. | &#x60;equals&#x60; | | &#x60;maintainerIds&#x60; | A comma-separated list of metric maintainer IDs. | &#x60;anyOf&#x60; | | &#x60;maintainerTeamKey&#x60; | The metric maintainer team key. | &#x60;equals&#x60; | | &#x60;query&#x60; | A \&quot;fuzzy\&quot; search across metric key and name. Supply a string or list of strings to the operator. | &#x60;equals&#x60; | | &#x60;tags&#x60; | The metric tags. | &#x60;contains&#x60; | | &#x60;unitAggregationType&#x60; | The metric&#39;s unit aggregation type. One of &#x60;sum&#x60;, &#x60;average&#x60;. | &#x60;equals&#x60; |  For example, the filter &#x60;?filter&#x3D;tags contains [\&quot;tag1\&quot;, \&quot;tag2\&quot;, \&quot;tag3\&quot;]&#x60; matches metrics that have all three tags.  The documented values for &#x60;filter&#x60; query parameters are prior to URL encoding. For example, the &#x60;[&#x60; in &#x60;?filter&#x3D;tags contains [\&quot;tag1\&quot;, \&quot;tag2\&quot;, \&quot;tag3\&quot;]&#x60; must be encoded to &#x60;%5B&#x60;.  ### Expanding the metric list response  LaunchDarkly supports expanding the \&quot;List metrics\&quot; response. By default, the expandable field is **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add the following supported field:  - &#x60;experimentCount&#x60; includes the number of experiments from the specific project that use the metric  For example, &#x60;expand&#x3D;experimentCount&#x60; includes the &#x60;experimentCount&#x60; field for each metric in the response. 
     * @param projectKey The project key (required)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. (optional)
     * @param limit The number of metrics to return in the response. Defaults to 20. Maximum limit is 50. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and returns the next &#x60;limit&#x60; items. (optional)
     * @param sort A field to sort the items by. Prefix field by a dash ( - ) to sort in descending order. This endpoint supports sorting by &#x60;createdAt&#x60; or &#x60;name&#x60;. (optional)
     * @param filter A comma-separated list of filters. This endpoint accepts filtering by &#x60;query&#x60;, &#x60;tags&#x60;, &#39;eventKind&#39;, &#39;isNumeric&#39;, &#39;unitAggregationType&#x60;, &#x60;hasConnections&#x60;, &#x60;maintainerIds&#x60;, &#x60;maintainerTeamKey&#x60;, &#x60;view&#x60;, and &#x60;dataSourceKeys&#x60;. To learn more about the filter syntax, read the &#39;Filtering metrics&#39; section above. (optional)
     * @return ApiResponse&lt;MetricCollectionRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metrics collection response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<MetricCollectionRep> getMetricsWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable String expand, @javax.annotation.Nullable Long limit, @javax.annotation.Nullable Long offset, @javax.annotation.Nullable String sort, @javax.annotation.Nullable String filter) throws ApiException {
        okhttp3.Call localVarCall = getMetricsValidateBeforeCall(projectKey, expand, limit, offset, sort, filter, null);
        Type localVarReturnType = new TypeToken<MetricCollectionRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List metrics (asynchronously)
     * Get a list of all metrics for the specified project.  ### Filtering metrics  The &#x60;filter&#x60; parameter supports the following operators: &#x60;contains&#x60;, &#x60;equals&#x60;, &#x60;anyOf&#x60;.  #### Supported fields and operators  You can only filter certain fields in metrics when using the &#x60;filter&#x60; parameter. Additionally, you can only filter some fields with certain operators.  When you search for metrics, the &#x60;filter&#x60; parameter supports the following fields and operators:  |&lt;div style&#x3D;\&quot;width:120px\&quot;&gt;Field&lt;/div&gt; |Description |Supported operators | |---|---|---| | &#x60;eventKind&#x60; | The metric event kind. One of &#x60;custom&#x60;, &#x60;pageview&#x60;, &#x60;click&#x60;. | &#x60;equals&#x60; | | &#x60;hasConnections&#x60; | Whether the metric has connections to experiments or guarded rollouts. One of &#x60;true&#x60;, &#x60;false&#x60;. | &#x60;equals&#x60; | | &#x60;isNumeric&#x60; | Whether the metric is numeric. One of &#x60;true&#x60;, &#x60;false&#x60;. | &#x60;equals&#x60; | | &#x60;maintainerIds&#x60; | A comma-separated list of metric maintainer IDs. | &#x60;anyOf&#x60; | | &#x60;maintainerTeamKey&#x60; | The metric maintainer team key. | &#x60;equals&#x60; | | &#x60;query&#x60; | A \&quot;fuzzy\&quot; search across metric key and name. Supply a string or list of strings to the operator. | &#x60;equals&#x60; | | &#x60;tags&#x60; | The metric tags. | &#x60;contains&#x60; | | &#x60;unitAggregationType&#x60; | The metric&#39;s unit aggregation type. One of &#x60;sum&#x60;, &#x60;average&#x60;. | &#x60;equals&#x60; |  For example, the filter &#x60;?filter&#x3D;tags contains [\&quot;tag1\&quot;, \&quot;tag2\&quot;, \&quot;tag3\&quot;]&#x60; matches metrics that have all three tags.  The documented values for &#x60;filter&#x60; query parameters are prior to URL encoding. For example, the &#x60;[&#x60; in &#x60;?filter&#x3D;tags contains [\&quot;tag1\&quot;, \&quot;tag2\&quot;, \&quot;tag3\&quot;]&#x60; must be encoded to &#x60;%5B&#x60;.  ### Expanding the metric list response  LaunchDarkly supports expanding the \&quot;List metrics\&quot; response. By default, the expandable field is **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add the following supported field:  - &#x60;experimentCount&#x60; includes the number of experiments from the specific project that use the metric  For example, &#x60;expand&#x3D;experimentCount&#x60; includes the &#x60;experimentCount&#x60; field for each metric in the response. 
     * @param projectKey The project key (required)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. (optional)
     * @param limit The number of metrics to return in the response. Defaults to 20. Maximum limit is 50. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and returns the next &#x60;limit&#x60; items. (optional)
     * @param sort A field to sort the items by. Prefix field by a dash ( - ) to sort in descending order. This endpoint supports sorting by &#x60;createdAt&#x60; or &#x60;name&#x60;. (optional)
     * @param filter A comma-separated list of filters. This endpoint accepts filtering by &#x60;query&#x60;, &#x60;tags&#x60;, &#39;eventKind&#39;, &#39;isNumeric&#39;, &#39;unitAggregationType&#x60;, &#x60;hasConnections&#x60;, &#x60;maintainerIds&#x60;, &#x60;maintainerTeamKey&#x60;, &#x60;view&#x60;, and &#x60;dataSourceKeys&#x60;. To learn more about the filter syntax, read the &#39;Filtering metrics&#39; section above. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metrics collection response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getMetricsAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable String expand, @javax.annotation.Nullable Long limit, @javax.annotation.Nullable Long offset, @javax.annotation.Nullable String sort, @javax.annotation.Nullable String filter, final ApiCallback<MetricCollectionRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getMetricsValidateBeforeCall(projectKey, expand, limit, offset, sort, filter, _callback);
        Type localVarReturnType = new TypeToken<MetricCollectionRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for patchMetric
     * @param projectKey The project key (required)
     * @param metricKey The metric key (required)
     * @param patchOperation  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metric response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchMetricCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String metricKey, @javax.annotation.Nonnull List<PatchOperation> patchOperation, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = patchOperation;

        // create path and map variables
        String localVarPath = "/api/v2/metrics/{projectKey}/{metricKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "metricKey" + "}", localVarApiClient.escapeString(metricKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

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
    private okhttp3.Call patchMetricValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String metricKey, @javax.annotation.Nonnull List<PatchOperation> patchOperation, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling patchMetric(Async)");
        }

        // verify the required parameter 'metricKey' is set
        if (metricKey == null) {
            throw new ApiException("Missing the required parameter 'metricKey' when calling patchMetric(Async)");
        }

        // verify the required parameter 'patchOperation' is set
        if (patchOperation == null) {
            throw new ApiException("Missing the required parameter 'patchOperation' when calling patchMetric(Async)");
        }

        return patchMetricCall(projectKey, metricKey, patchOperation, _callback);

    }

    /**
     * Update metric
     * Patch a metric by key. Updating a metric uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](https://launchdarkly.com/docs/api#updates).
     * @param projectKey The project key (required)
     * @param metricKey The metric key (required)
     * @param patchOperation  (required)
     * @return MetricRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metric response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public MetricRep patchMetric(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String metricKey, @javax.annotation.Nonnull List<PatchOperation> patchOperation) throws ApiException {
        ApiResponse<MetricRep> localVarResp = patchMetricWithHttpInfo(projectKey, metricKey, patchOperation);
        return localVarResp.getData();
    }

    /**
     * Update metric
     * Patch a metric by key. Updating a metric uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](https://launchdarkly.com/docs/api#updates).
     * @param projectKey The project key (required)
     * @param metricKey The metric key (required)
     * @param patchOperation  (required)
     * @return ApiResponse&lt;MetricRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metric response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<MetricRep> patchMetricWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String metricKey, @javax.annotation.Nonnull List<PatchOperation> patchOperation) throws ApiException {
        okhttp3.Call localVarCall = patchMetricValidateBeforeCall(projectKey, metricKey, patchOperation, null);
        Type localVarReturnType = new TypeToken<MetricRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update metric (asynchronously)
     * Patch a metric by key. Updating a metric uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](https://launchdarkly.com/docs/api#updates).
     * @param projectKey The project key (required)
     * @param metricKey The metric key (required)
     * @param patchOperation  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metric response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchMetricAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String metricKey, @javax.annotation.Nonnull List<PatchOperation> patchOperation, final ApiCallback<MetricRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = patchMetricValidateBeforeCall(projectKey, metricKey, patchOperation, _callback);
        Type localVarReturnType = new TypeToken<MetricRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for postMetric
     * @param projectKey The project key (required)
     * @param metricPost  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Metric response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postMetricCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull MetricPost metricPost, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = metricPost;

        // create path and map variables
        String localVarPath = "/api/v2/metrics/{projectKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

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
    private okhttp3.Call postMetricValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull MetricPost metricPost, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling postMetric(Async)");
        }

        // verify the required parameter 'metricPost' is set
        if (metricPost == null) {
            throw new ApiException("Missing the required parameter 'metricPost' when calling postMetric(Async)");
        }

        return postMetricCall(projectKey, metricPost, _callback);

    }

    /**
     * Create metric
     * Create a new metric in the specified project. The expected &#x60;POST&#x60; body differs depending on the specified &#x60;kind&#x60; property.
     * @param projectKey The project key (required)
     * @param metricPost  (required)
     * @return MetricRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Metric response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public MetricRep postMetric(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull MetricPost metricPost) throws ApiException {
        ApiResponse<MetricRep> localVarResp = postMetricWithHttpInfo(projectKey, metricPost);
        return localVarResp.getData();
    }

    /**
     * Create metric
     * Create a new metric in the specified project. The expected &#x60;POST&#x60; body differs depending on the specified &#x60;kind&#x60; property.
     * @param projectKey The project key (required)
     * @param metricPost  (required)
     * @return ApiResponse&lt;MetricRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Metric response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<MetricRep> postMetricWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull MetricPost metricPost) throws ApiException {
        okhttp3.Call localVarCall = postMetricValidateBeforeCall(projectKey, metricPost, null);
        Type localVarReturnType = new TypeToken<MetricRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create metric (asynchronously)
     * Create a new metric in the specified project. The expected &#x60;POST&#x60; body differs depending on the specified &#x60;kind&#x60; property.
     * @param projectKey The project key (required)
     * @param metricPost  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Metric response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postMetricAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull MetricPost metricPost, final ApiCallback<MetricRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = postMetricValidateBeforeCall(projectKey, metricPost, _callback);
        Type localVarReturnType = new TypeToken<MetricRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
