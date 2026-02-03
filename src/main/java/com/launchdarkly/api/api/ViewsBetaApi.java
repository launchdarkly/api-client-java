/*
 * LaunchDarkly REST API
 * This documentation describes LaunchDarkly's REST API. To access the complete OpenAPI spec directly, use [Get OpenAPI spec](https://launchdarkly.com/docs/api/other/get-openapi-spec).  To learn how to use LaunchDarkly using the user interface (UI) instead, read our [product documentation](https://launchdarkly.com/docs/home).  ## Authentication  LaunchDarkly's REST API uses the HTTPS protocol with a minimum TLS version of 1.2.  All REST API resources are authenticated with either [personal or service access tokens](https://launchdarkly.com/docs/home/account/api), or session cookies. Other authentication mechanisms are not supported. You can manage personal access tokens on your [**Authorization**](https://app.launchdarkly.com/settings/authorization) page in the LaunchDarkly UI.  LaunchDarkly also has SDK keys, mobile keys, and client-side IDs that are used by our server-side SDKs, mobile SDKs, and JavaScript-based SDKs, respectively. **These keys cannot be used to access our REST API**. These keys are environment-specific, and can only perform read-only operations such as fetching feature flag settings.  | Auth mechanism                                                                                  | Allowed resources                                                                                     | Use cases                                          | | ----------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------- | -------------------------------------------------- | | [Personal or service access tokens](https://launchdarkly.com/docs/home/account/api) | Can be customized on a per-token basis                                                                | Building scripts, custom integrations, data export. | | SDK keys                                                                                        | Can only access read-only resources specific to server-side SDKs. Restricted to a single environment. | Server-side SDKs                     | | Mobile keys                                                                                     | Can only access read-only resources specific to mobile SDKs, and only for flags marked available to mobile keys. Restricted to a single environment.           | Mobile SDKs                                        | | Client-side ID                                                                                  | Can only access read-only resources specific to JavaScript-based client-side SDKs, and only for flags marked available to client-side. Restricted to a single environment.           | Client-side JavaScript                             |  > #### Keep your access tokens and SDK keys private > > Access tokens should _never_ be exposed in untrusted contexts. Never put an access token in client-side JavaScript, or embed it in a mobile application. LaunchDarkly has special mobile keys that you can embed in mobile apps. If you accidentally expose an access token or SDK key, you can reset it from your [**Authorization**](https://app.launchdarkly.com/settings/authorization) page. > > The client-side ID is safe to embed in untrusted contexts. It's designed for use in client-side JavaScript.  ### Authentication using request header  The preferred way to authenticate with the API is by adding an `Authorization` header containing your access token to your requests. The value of the `Authorization` header must be your access token.  Manage personal access tokens from the [**Authorization**](https://app.launchdarkly.com/settings/authorization) page.  ### Authentication using session cookie  For testing purposes, you can make API calls directly from your web browser. If you are logged in to the LaunchDarkly application, the API will use your existing session to authenticate calls.  Depending on the permissions granted as part of your [role](https://launchdarkly.com/docs/home/account/roles), you may not have permission to perform some API calls. You will receive a `401` response code in that case.  > ### Modifying the Origin header causes an error > > LaunchDarkly validates that the Origin header for any API request authenticated by a session cookie matches the expected Origin header. The expected Origin header is `https://app.launchdarkly.com`. > > If the Origin header does not match what's expected, LaunchDarkly returns an error. This error can prevent the LaunchDarkly app from working correctly. > > Any browser extension that intentionally changes the Origin header can cause this problem. For example, the `Allow-Control-Allow-Origin: *` Chrome extension changes the Origin header to `http://evil.com` and causes the app to fail. > > To prevent this error, do not modify your Origin header. > > LaunchDarkly does not require origin matching when authenticating with an access token, so this issue does not affect normal API usage.  ## Representations  All resources expect and return JSON response bodies. Error responses also send a JSON body. To learn more about the error format of the API, read [Errors](https://launchdarkly.com/docs/api#errors).  In practice this means that you always get a response with a `Content-Type` header set to `application/json`.  In addition, request bodies for `PATCH`, `POST`, and `PUT` requests must be encoded as JSON with a `Content-Type` header set to `application/json`.  ### Summary and detailed representations  When you fetch a list of resources, the response includes only the most important attributes of each resource. This is a _summary representation_ of the resource. When you fetch an individual resource, such as a single feature flag, you receive a _detailed representation_ of the resource.  The best way to find a detailed representation is to follow links. Every summary representation includes a link to its detailed representation.  ### Expanding responses  Sometimes the detailed representation of a resource does not include all of the attributes of the resource by default. If this is the case, the request method will clearly document this and describe which attributes you can include in an expanded response.  To include the additional attributes, append the `expand` request parameter to your request and add a comma-separated list of the attributes to include. For example, when you append `?expand=members,maintainers` to the [Get team](https://launchdarkly.com/docs/api/teams/get-team) endpoint, the expanded response includes both of these attributes.  ### Links and addressability  The best way to navigate the API is by following links. These are attributes in representations that link to other resources. The API always uses the same format for links:  - Links to other resources within the API are encapsulated in a `_links` object - If the resource has a corresponding link to HTML content on the site, it is stored in a special `_site` link  Each link has two attributes:  - An `href`, which contains the URL - A `type`, which describes the content type  For example, a feature resource might return the following:  ```json {   \"_links\": {     \"parent\": {       \"href\": \"/api/features\",       \"type\": \"application/json\"     },     \"self\": {       \"href\": \"/api/features/sort.order\",       \"type\": \"application/json\"     }   },   \"_site\": {     \"href\": \"/features/sort.order\",     \"type\": \"text/html\"   } } ```  From this, you can navigate to the parent collection of features by following the `parent` link, or navigate to the site page for the feature by following the `_site` link.  Collections are always represented as a JSON object with an `items` attribute containing an array of representations. Like all other representations, collections have `_links` defined at the top level.  Paginated collections include `first`, `last`, `next`, and `prev` links containing a URL with the respective set of elements in the collection.  ## Updates  Resources that accept partial updates use the `PATCH` verb. Most resources support the [JSON patch](https://launchdarkly.com/docs/api#updates-using-json-patch) format. Some resources also support the [JSON merge patch](https://launchdarkly.com/docs/api#updates-using-json-merge-patch) format, and some resources support the [semantic patch](https://launchdarkly.com/docs/api#updates-using-semantic-patch) format, which is a way to specify the modifications to perform as a set of executable instructions. Each resource supports optional [comments](https://launchdarkly.com/docs/api#updates-with-comments) that you can submit with updates. Comments appear in outgoing webhooks, the audit log, and other integrations.  When a resource supports both JSON patch and semantic patch, we document both in the request method. However, the specific request body fields and descriptions included in our documentation only match one type of patch or the other.  ### Updates using JSON patch  [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) is a way to specify the modifications to perform on a resource. JSON patch uses paths and a limited set of operations to describe how to transform the current state of the resource into a new state. JSON patch documents are always arrays, where each element contains an operation, a path to the field to update, and the new value.  For example, in this feature flag representation:  ```json {     \"name\": \"New recommendations engine\",     \"key\": \"engine.enable\",     \"description\": \"This is the description\",     ... } ``` You can change the feature flag's description with the following patch document:  ```json [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"This is the new description\" }] ```  You can specify multiple modifications to perform in a single request. You can also test that certain preconditions are met before applying the patch:  ```json [   { \"op\": \"test\", \"path\": \"/version\", \"value\": 10 },   { \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" } ] ```  The above patch request tests whether the feature flag's `version` is `10`, and if so, changes the feature flag's description.  Attributes that are not editable, such as a resource's `_links`, have names that start with an underscore.  ### Updates using JSON merge patch  [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) is another format for specifying the modifications to perform on a resource. JSON merge patch is less expressive than JSON patch. However, in many cases it is simpler to construct a merge patch document. For example, you can change a feature flag's description with the following merge patch document:  ```json {   \"description\": \"New flag description\" } ```  ### Updates using semantic patch  Some resources support the semantic patch format. A semantic patch is a way to specify the modifications to perform on a resource as a set of executable instructions.  Semantic patch allows you to be explicit about intent using precise, custom instructions. In many cases, you can define semantic patch instructions independently of the current state of the resource. This can be useful when defining a change that may be applied at a future date.  To make a semantic patch request, you must append `domain-model=launchdarkly.semanticpatch` to your `Content-Type` header.  Here's how:  ``` Content-Type: application/json; domain-model=launchdarkly.semanticpatch ```  If you call a semantic patch resource without this header, you will receive a `400` response because your semantic patch will be interpreted as a JSON patch.  The body of a semantic patch request takes the following properties:  * `comment` (string): (Optional) A description of the update. * `environmentKey` (string): (Required for some resources only) The environment key. * `instructions` (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a `kind` property that indicates the instruction. If the instruction requires parameters, you must include those parameters as additional fields in the object. The documentation for each resource that supports semantic patch includes the available instructions and any additional parameters.  For example:  ```json {   \"comment\": \"optional comment\",   \"instructions\": [ {\"kind\": \"turnFlagOn\"} ] } ```  Semantic patches are not applied partially; either all of the instructions are applied or none of them are. If **any** instruction is invalid, the endpoint returns an error and will not change the resource. If all instructions are valid, the request succeeds and the resources are updated if necessary, or left unchanged if they are already in the state you request.  ### Updates with comments  You can submit optional comments with `PATCH` changes.  To submit a comment along with a JSON patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"patch\": [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" }] } ```  To submit a comment along with a JSON merge patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"merge\": { \"description\": \"New flag description\" } } ```  To submit a comment along with a semantic patch, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"instructions\": [ {\"kind\": \"turnFlagOn\"} ] } ```  ## Errors  The API always returns errors in a common format. Here's an example:  ```json {   \"code\": \"invalid_request\",   \"message\": \"A feature with that key already exists\",   \"id\": \"30ce6058-87da-11e4-b116-123b93f75cba\" } ```  The `code` indicates the general class of error. The `message` is a human-readable explanation of what went wrong. The `id` is a unique identifier. Use it when you're working with LaunchDarkly Support to debug a problem with a specific API call.  ### HTTP status error response codes  | Code | Definition        | Description                                                                                       | Possible Solution                                                | | ---- | ----------------- | ------------------------------------------------------------------------------------------- | ---------------------------------------------------------------- | | 400  | Invalid request       | The request cannot be understood.                                    | Ensure JSON syntax in request body is correct.                   | | 401  | Invalid access token      | Requestor is unauthorized or does not have permission for this API call.                                                | Ensure your API access token is valid and has the appropriate permissions.                                     | | 403  | Forbidden         | Requestor does not have access to this resource.                                                | Ensure that the account member or access token has proper permissions set. | | 404  | Invalid resource identifier | The requested resource is not valid. | Ensure that the resource is correctly identified by ID or key. | | 405  | Method not allowed | The request method is not allowed on this resource. | Ensure that the HTTP verb is correct. | | 409  | Conflict          | The API request can not be completed because it conflicts with a concurrent API request. | Retry your request.                                              | | 422  | Unprocessable entity | The API request can not be completed because the update description can not be understood. | Ensure that the request body is correct for the type of patch you are using, either JSON patch or semantic patch. | 429  | Too many requests | Read [Rate limiting](https://launchdarkly.com/docs/api#rate-limiting).                                               | Wait and try again later.                                        |  ## CORS  The LaunchDarkly API supports Cross Origin Resource Sharing (CORS) for AJAX requests from any origin. If an `Origin` header is given in a request, it will be echoed as an explicitly allowed origin. Otherwise the request returns a wildcard, `Access-Control-Allow-Origin: *`. For more information on CORS, read the [CORS W3C Recommendation](http://www.w3.org/TR/cors). Example CORS headers might look like:  ```http Access-Control-Allow-Headers: Accept, Content-Type, Content-Length, Accept-Encoding, Authorization Access-Control-Allow-Methods: OPTIONS, GET, DELETE, PATCH Access-Control-Allow-Origin: * Access-Control-Max-Age: 300 ```  You can make authenticated CORS calls just as you would make same-origin calls, using either [token or session-based authentication](https://launchdarkly.com/docs/api#authentication). If you are using session authentication, you should set the `withCredentials` property for your `xhr` request to `true`. You should never expose your access tokens to untrusted entities.  ## Rate limiting  We use several rate limiting strategies to ensure the availability of our APIs. Rate-limited calls to our APIs return a `429` status code. Calls to our APIs include headers indicating the current rate limit status. The specific headers returned depend on the API route being called. The limits differ based on the route, authentication mechanism, and other factors. Routes that are not rate limited may not contain any of the headers described below.  > ### Rate limiting and SDKs > > LaunchDarkly SDKs are never rate limited and do not use the API endpoints defined here. LaunchDarkly uses a different set of approaches, including streaming/server-sent events and a global CDN, to ensure availability to the routes used by LaunchDarkly SDKs.  ### Global rate limits  Authenticated requests are subject to a global limit. This is the maximum number of calls that your account can make to the API per ten seconds. All service and personal access tokens on the account share this limit, so exceeding the limit with one access token will impact other tokens. Calls that are subject to global rate limits may return the headers below:  | Header name                    | Description                                                                      | | ------------------------------ | -------------------------------------------------------------------------------- | | `X-Ratelimit-Global-Remaining` | The maximum number of requests the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`            | The time at which the current rate limit window resets in epoch milliseconds.    |  We do not publicly document the specific number of calls that can be made globally. This limit may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limit.  ### Route-level rate limits  Some authenticated routes have custom rate limits. These also reset every ten seconds. Any service or personal access tokens hitting the same route share this limit, so exceeding the limit with one access token may impact other tokens. Calls that are subject to route-level rate limits return the headers below:  | Header name                   | Description                                                                                           | | ----------------------------- | ----------------------------------------------------------------------------------------------------- | | `X-Ratelimit-Route-Remaining` | The maximum number of requests to the current route the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`           | The time at which the current rate limit window resets in epoch milliseconds.                         |  A _route_ represents a specific URL pattern and verb. For example, the [Delete environment](https://launchdarkly.com/docs/api/environments/delete-environment) endpoint is considered a single route, and each call to delete an environment counts against your route-level rate limit for that route.  We do not publicly document the specific number of calls that an account can make to each endpoint per ten seconds. These limits may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limits.  ### IP-based rate limiting  We also employ IP-based rate limiting on some API routes. If you hit an IP-based rate limit, your API response will include a `Retry-After` header indicating how long to wait before re-trying the call. Clients must wait at least `Retry-After` seconds before making additional calls to our API, and should employ jitter and backoff strategies to avoid triggering rate limits again.  ## OpenAPI (Swagger) and client libraries  We have a [complete OpenAPI (Swagger) specification](https://app.launchdarkly.com/api/v2/openapi.json) for our API.  We auto-generate multiple client libraries based on our OpenAPI specification. To learn more, visit the [collection of client libraries on GitHub](https://github.com/search?q=topic%3Alaunchdarkly-api+org%3Alaunchdarkly&type=Repositories). Alternatively, you can use the specification to generate client libraries to interact with our REST API in your language of choice. Or, you can refer to our API endpoints' documentation for guidance on how to make requests with a common HTTP library in your language of choice.  Our OpenAPI specification is supported by several API-based tools such as Postman and Insomnia. In many cases, you can directly import our specification to explore our APIs.  ## Method overriding  Some firewalls and HTTP clients restrict the use of verbs other than `GET` and `POST`. In those environments, our API endpoints that use `DELETE`, `PATCH`, and `PUT` verbs are inaccessible.  To avoid this issue, our API supports the `X-HTTP-Method-Override` header, allowing clients to \"tunnel\" `DELETE`, `PATCH`, and `PUT` requests using a `POST` request.  For example, to call a `PATCH` endpoint using a `POST` request, you can include `X-HTTP-Method-Override:PATCH` as a header.  ## Beta resources  We sometimes release new API resources in **beta** status before we release them with general availability.  Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  We try to promote resources into general availability as quickly as possible. This happens after sufficient testing and when we're satisfied that we no longer need to make backwards-incompatible changes.  We mark beta resources with a \"Beta\" callout in our documentation, pictured below:  > ### This feature is in beta > > To use this feature, pass in a header including the `LD-API-Version` key with value set to `beta`. Use this header with each call. To learn more, read [Beta resources](https://launchdarkly.com/docs/api#beta-resources). > > Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  ### Using beta resources  To use a beta resource, you must include a header in the request. If you call a beta resource without this header, you receive a `403` response.  Use this header:  ``` LD-API-Version: beta ```  ## Federal and EU environments  In addition to the commercial versions, LaunchDarkly offers instances for federal agencies and those based in the European Union (EU).  ### Federal environments  The version of LaunchDarkly that is available on domains controlled by the United States government is different from the version of LaunchDarkly available to the general public. If you are an employee or contractor for a United States federal agency and use LaunchDarkly in your work, you likely use the federal instance of LaunchDarkly.  If you are working in the federal instance of LaunchDarkly, the base URI for each request is `https://app.launchdarkly.us`.  To learn more, read [LaunchDarkly in federal environments](https://launchdarkly.com/docs/home/infrastructure/federal).  ### EU environments  The version of LaunchDarkly that is available in the EU is different from the version of LaunchDarkly available to other regions. If you are based in the EU, you likely use the EU instance of LaunchDarkly. The LaunchDarkly EU instance complies with EU data residency principles, including the protection and confidentiality of EU customer information.  If you are working in the EU instance of LaunchDarkly, the base URI for each request is `https://app.eu.launchdarkly.com`.  To learn more, read [LaunchDarkly in the European Union (EU)](https://launchdarkly.com/docs/home/infrastructure/eu).  ## Versioning  We try hard to keep our REST API backwards compatible, but we occasionally have to make backwards-incompatible changes in the process of shipping new features. These breaking changes can cause unexpected behavior if you don't prepare for them accordingly.  Updates to our REST API include support for the latest features in LaunchDarkly. We also release a new version of our REST API every time we make a breaking change. We provide simultaneous support for multiple API versions so you can migrate from your current API version to a new version at your own pace.  ### Setting the API version per request  You can set the API version on a specific request by sending an `LD-API-Version` header, as shown in the example below:  ``` LD-API-Version: 20240415 ```  The header value is the version number of the API version you would like to request. The number for each version corresponds to the date the version was released in `yyyymmdd` format. In the example above the version `20240415` corresponds to April 15, 2024.  ### Setting the API version per access token  When you create an access token, you must specify a specific version of the API to use. This ensures that integrations using this token cannot be broken by version changes.  Tokens created before versioning was released have their version set to `20160426`, which is the version of the API that existed before the current versioning scheme, so that they continue working the same way they did before versioning.  If you would like to upgrade your integration to use a new API version, you can explicitly set the header described above.  > ### Best practice: Set the header for every client or integration > > We recommend that you set the API version header explicitly in any client or integration you build. > > Only rely on the access token API version during manual testing.  ### API version changelog  <table>   <tr>     <th>Version</th>     <th>Changes</th>     <th>End of life (EOL)</th>   </tr>   <tr>     <td>`20240415`</td>     <td>       <ul><li>Changed several endpoints from unpaginated to paginated. Use the `limit` and `offset` query parameters to page through the results.</li> <li>Changed the [list access tokens](https://launchdarkly.com/docs/api/access-tokens/get-tokens) endpoint: <ul><li>Response is now paginated with a default limit of `25`</li></ul></li> <li>Changed the [list account members](https://launchdarkly.com/docs/api/account-members/get-members) endpoint: <ul><li>The `accessCheck` filter is no longer available</li></ul></li> <li>Changed the [list custom roles](https://launchdarkly.com/docs/api/custom-roles/get-custom-roles) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li></ul></li> <li>Changed the [list feature flags](https://launchdarkly.com/docs/api/feature-flags/get-feature-flags) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li><li>The `environments` field is now only returned if the request is filtered by environment, using the `filterEnv` query parameter</li><li>The `followerId`, `hasDataExport`, `status`, `contextKindTargeted`, and `segmentTargeted` filters are no longer available</li><li>The `compare` query parameter is no longer available</li></ul></li> <li>Changed the [list segments](https://launchdarkly.com/docs/api/segments/get-segments) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li></ul></li> <li>Changed the [list teams](https://launchdarkly.com/docs/api/teams/get-teams) endpoint: <ul><li>The `expand` parameter no longer supports including `projects` or `roles`</li><li>In paginated results, the maximum page size is now 100</li></ul></li> <li>Changed the [get workflows](https://launchdarkly.com/docs/api/workflows/get-workflows) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li><li>The `_conflicts` field in the response is no longer available</li></ul></li> </ul>     </td>     <td>Current</td>   </tr>   <tr>     <td>`20220603`</td>     <td>       <ul><li>Changed the [list projects](https://launchdarkly.com/docs/api/projects/get-projects) return value:<ul><li>Response is now paginated with a default limit of `20`.</li><li>Added support for filter and sort.</li><li>The project `environments` field is now expandable. This field is omitted by default.</li></ul></li><li>Changed the [get project](https://launchdarkly.com/docs/api/projects/get-project) return value:<ul><li>The `environments` field is now expandable. This field is omitted by default.</li></ul></li></ul>     </td>     <td>2025-04-15</td>   </tr>   <tr>     <td>`20210729`</td>     <td>       <ul><li>Changed the [create approval request](https://launchdarkly.com/docs/api/approvals/post-approval-request) return value. It now returns HTTP Status Code `201` instead of `200`.</li><li> Changed the [get user](https://launchdarkly.com/docs/api/users/get-user) return value. It now returns a user record, not a user. </li><li>Added additional optional fields to environment, segments, flags, members, and segments, including the ability to create big segments. </li><li> Added default values for flag variations when new environments are created. </li><li>Added filtering and pagination for getting flags and members, including `limit`, `number`, `filter`, and `sort` query parameters. </li><li>Added endpoints for expiring user targets for flags and segments, scheduled changes, access tokens, Relay Proxy configuration, integrations and subscriptions, and approvals. </li></ul>     </td>     <td>2023-06-03</td>   </tr>   <tr>     <td>`20191212`</td>     <td>       <ul><li>[List feature flags](https://launchdarkly.com/docs/api/feature-flags/get-feature-flags) now defaults to sending summaries of feature flag configurations, equivalent to setting the query parameter `summary=true`. Summaries omit flag targeting rules and individual user targets from the payload. </li><li> Added endpoints for flags, flag status, projects, environments, audit logs, members, users, custom roles, segments, usage, streams, events, and data export. </li></ul>     </td>     <td>2022-07-29</td>   </tr>   <tr>     <td>`20160426`</td>     <td>       <ul><li>Initial versioning of API. Tokens created before versioning have their version set to this.</li></ul>     </td>     <td>2020-12-12</td>   </tr> </table>  To learn more about how EOL is determined, read LaunchDarkly's [End of Life (EOL) Policy](https://launchdarkly.com/policies/end-of-life-policy/). 
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


import com.launchdarkly.api.model.Error;
import com.launchdarkly.api.model.LinkResourceSuccessResponse;
import com.launchdarkly.api.model.UnlinkResourceSuccessResponse;
import com.launchdarkly.api.model.View;
import com.launchdarkly.api.model.ViewLinkRequest;
import com.launchdarkly.api.model.ViewLinkedResources;
import com.launchdarkly.api.model.ViewPatch;
import com.launchdarkly.api.model.ViewPost;
import com.launchdarkly.api.model.Views;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewsBetaApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public ViewsBetaApi() {
        this(Configuration.getDefaultApiClient());
    }

    public ViewsBetaApi(ApiClient apiClient) {
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
     * Build call for createView
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewPost View object to create (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createViewCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull ViewPost viewPost, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = viewPost;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/views"
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

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }


        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createViewValidateBeforeCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull ViewPost viewPost, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling createView(Async)");
        }

        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling createView(Async)");
        }

        // verify the required parameter 'viewPost' is set
        if (viewPost == null) {
            throw new ApiException("Missing the required parameter 'viewPost' when calling createView(Async)");
        }

        return createViewCall(ldAPIVersion, projectKey, viewPost, _callback);

    }

    /**
     * Create view
     * Create a new view in the given project.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewPost View object to create (required)
     * @return View
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public View createView(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull ViewPost viewPost) throws ApiException {
        ApiResponse<View> localVarResp = createViewWithHttpInfo(ldAPIVersion, projectKey, viewPost);
        return localVarResp.getData();
    }

    /**
     * Create view
     * Create a new view in the given project.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewPost View object to create (required)
     * @return ApiResponse&lt;View&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<View> createViewWithHttpInfo(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull ViewPost viewPost) throws ApiException {
        okhttp3.Call localVarCall = createViewValidateBeforeCall(ldAPIVersion, projectKey, viewPost, null);
        Type localVarReturnType = new TypeToken<View>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create view (asynchronously)
     * Create a new view in the given project.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewPost View object to create (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createViewAsync(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull ViewPost viewPost, final ApiCallback<View> _callback) throws ApiException {

        okhttp3.Call localVarCall = createViewValidateBeforeCall(ldAPIVersion, projectKey, viewPost, _callback);
        Type localVarReturnType = new TypeToken<View>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteView
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteViewCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/views/{viewKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "viewKey" + "}", localVarApiClient.escapeString(viewKey.toString()));

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

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }


        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteViewValidateBeforeCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling deleteView(Async)");
        }

        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling deleteView(Async)");
        }

        // verify the required parameter 'viewKey' is set
        if (viewKey == null) {
            throw new ApiException("Missing the required parameter 'viewKey' when calling deleteView(Async)");
        }

        return deleteViewCall(ldAPIVersion, projectKey, viewKey, _callback);

    }

    /**
     * Delete view
     * Delete a specific view by its key.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public void deleteView(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey) throws ApiException {
        deleteViewWithHttpInfo(ldAPIVersion, projectKey, viewKey);
    }

    /**
     * Delete view
     * Delete a specific view by its key.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteViewWithHttpInfo(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey) throws ApiException {
        okhttp3.Call localVarCall = deleteViewValidateBeforeCall(ldAPIVersion, projectKey, viewKey, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete view (asynchronously)
     * Delete a specific view by its key.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteViewAsync(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteViewValidateBeforeCall(ldAPIVersion, projectKey, viewKey, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getLinkedResources
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @param resourceType  (required)
     * @param limit The number of views to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param sort Field to sort by. Default field is &#x60;linkedAt&#x60;, default order is ascending. (optional, default to linkedAt)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getLinkedResourcesCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nonnull String resourceType, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String sort, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/views/{viewKey}/linked/{resourceType}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "viewKey" + "}", localVarApiClient.escapeString(viewKey.toString()))
            .replace("{" + "resourceType" + "}", localVarApiClient.escapeString(resourceType.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (offset != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("offset", offset));
        }

        if (sort != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sort", sort));
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

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }


        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getLinkedResourcesValidateBeforeCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nonnull String resourceType, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String sort, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling getLinkedResources(Async)");
        }

        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getLinkedResources(Async)");
        }

        // verify the required parameter 'viewKey' is set
        if (viewKey == null) {
            throw new ApiException("Missing the required parameter 'viewKey' when calling getLinkedResources(Async)");
        }

        // verify the required parameter 'resourceType' is set
        if (resourceType == null) {
            throw new ApiException("Missing the required parameter 'resourceType' when calling getLinkedResources(Async)");
        }

        return getLinkedResourcesCall(ldAPIVersion, projectKey, viewKey, resourceType, limit, offset, sort, _callback);

    }

    /**
     * Get linked resources
     * Get a list of all linked resources for a given view.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @param resourceType  (required)
     * @param limit The number of views to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param sort Field to sort by. Default field is &#x60;linkedAt&#x60;, default order is ascending. (optional, default to linkedAt)
     * @return ViewLinkedResources
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ViewLinkedResources getLinkedResources(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nonnull String resourceType, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String sort) throws ApiException {
        ApiResponse<ViewLinkedResources> localVarResp = getLinkedResourcesWithHttpInfo(ldAPIVersion, projectKey, viewKey, resourceType, limit, offset, sort);
        return localVarResp.getData();
    }

    /**
     * Get linked resources
     * Get a list of all linked resources for a given view.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @param resourceType  (required)
     * @param limit The number of views to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param sort Field to sort by. Default field is &#x60;linkedAt&#x60;, default order is ascending. (optional, default to linkedAt)
     * @return ApiResponse&lt;ViewLinkedResources&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ViewLinkedResources> getLinkedResourcesWithHttpInfo(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nonnull String resourceType, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String sort) throws ApiException {
        okhttp3.Call localVarCall = getLinkedResourcesValidateBeforeCall(ldAPIVersion, projectKey, viewKey, resourceType, limit, offset, sort, null);
        Type localVarReturnType = new TypeToken<ViewLinkedResources>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get linked resources (asynchronously)
     * Get a list of all linked resources for a given view.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @param resourceType  (required)
     * @param limit The number of views to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param sort Field to sort by. Default field is &#x60;linkedAt&#x60;, default order is ascending. (optional, default to linkedAt)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getLinkedResourcesAsync(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nonnull String resourceType, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String sort, final ApiCallback<ViewLinkedResources> _callback) throws ApiException {

        okhttp3.Call localVarCall = getLinkedResourcesValidateBeforeCall(ldAPIVersion, projectKey, viewKey, resourceType, limit, offset, sort, _callback);
        Type localVarReturnType = new TypeToken<ViewLinkedResources>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getLinkedViews
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param resourceType  (required)
     * @param resourceKey  (required)
     * @param environmentId Environment ID. Required when resourceType is &#39;segments&#39; (optional)
     * @param limit The number of views to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getLinkedViewsCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String resourceType, @javax.annotation.Nonnull String resourceKey, @javax.annotation.Nullable String environmentId, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/view-associations/{resourceType}/{resourceKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "resourceType" + "}", localVarApiClient.escapeString(resourceType.toString()))
            .replace("{" + "resourceKey" + "}", localVarApiClient.escapeString(resourceKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (environmentId != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("environmentId", environmentId));
        }

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (offset != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("offset", offset));
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

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }


        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getLinkedViewsValidateBeforeCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String resourceType, @javax.annotation.Nonnull String resourceKey, @javax.annotation.Nullable String environmentId, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling getLinkedViews(Async)");
        }

        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getLinkedViews(Async)");
        }

        // verify the required parameter 'resourceType' is set
        if (resourceType == null) {
            throw new ApiException("Missing the required parameter 'resourceType' when calling getLinkedViews(Async)");
        }

        // verify the required parameter 'resourceKey' is set
        if (resourceKey == null) {
            throw new ApiException("Missing the required parameter 'resourceKey' when calling getLinkedViews(Async)");
        }

        return getLinkedViewsCall(ldAPIVersion, projectKey, resourceType, resourceKey, environmentId, limit, offset, _callback);

    }

    /**
     * Get linked views for a given resource
     * Get a list of all linked views for a resource. Flags, AI configs and metrics are identified by key. Segments are identified by segment ID.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param resourceType  (required)
     * @param resourceKey  (required)
     * @param environmentId Environment ID. Required when resourceType is &#39;segments&#39; (optional)
     * @param limit The number of views to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @return Views
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public Views getLinkedViews(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String resourceType, @javax.annotation.Nonnull String resourceKey, @javax.annotation.Nullable String environmentId, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset) throws ApiException {
        ApiResponse<Views> localVarResp = getLinkedViewsWithHttpInfo(ldAPIVersion, projectKey, resourceType, resourceKey, environmentId, limit, offset);
        return localVarResp.getData();
    }

    /**
     * Get linked views for a given resource
     * Get a list of all linked views for a resource. Flags, AI configs and metrics are identified by key. Segments are identified by segment ID.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param resourceType  (required)
     * @param resourceKey  (required)
     * @param environmentId Environment ID. Required when resourceType is &#39;segments&#39; (optional)
     * @param limit The number of views to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @return ApiResponse&lt;Views&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Views> getLinkedViewsWithHttpInfo(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String resourceType, @javax.annotation.Nonnull String resourceKey, @javax.annotation.Nullable String environmentId, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset) throws ApiException {
        okhttp3.Call localVarCall = getLinkedViewsValidateBeforeCall(ldAPIVersion, projectKey, resourceType, resourceKey, environmentId, limit, offset, null);
        Type localVarReturnType = new TypeToken<Views>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get linked views for a given resource (asynchronously)
     * Get a list of all linked views for a resource. Flags, AI configs and metrics are identified by key. Segments are identified by segment ID.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param resourceType  (required)
     * @param resourceKey  (required)
     * @param environmentId Environment ID. Required when resourceType is &#39;segments&#39; (optional)
     * @param limit The number of views to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getLinkedViewsAsync(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String resourceType, @javax.annotation.Nonnull String resourceKey, @javax.annotation.Nullable String environmentId, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, final ApiCallback<Views> _callback) throws ApiException {

        okhttp3.Call localVarCall = getLinkedViewsValidateBeforeCall(ldAPIVersion, projectKey, resourceType, resourceKey, environmentId, limit, offset, _callback);
        Type localVarReturnType = new TypeToken<Views>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getView
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @param sort A sort to apply to the list of views. (optional)
     * @param limit The number of views to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param filter A filter to apply to the list of views. (optional)
     * @param expand A comma-separated list of fields to expand. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getViewCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String filter, @javax.annotation.Nullable List<String> expand, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/views/{viewKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "viewKey" + "}", localVarApiClient.escapeString(viewKey.toString()));

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

        if (expand != null) {
            localVarCollectionQueryParams.addAll(localVarApiClient.parameterToPairs("multi", "expand", expand));
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

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }


        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getViewValidateBeforeCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String filter, @javax.annotation.Nullable List<String> expand, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling getView(Async)");
        }

        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getView(Async)");
        }

        // verify the required parameter 'viewKey' is set
        if (viewKey == null) {
            throw new ApiException("Missing the required parameter 'viewKey' when calling getView(Async)");
        }

        return getViewCall(ldAPIVersion, projectKey, viewKey, sort, limit, offset, filter, expand, _callback);

    }

    /**
     * Get view
     * Retrieve a specific view by its key.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @param sort A sort to apply to the list of views. (optional)
     * @param limit The number of views to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param filter A filter to apply to the list of views. (optional)
     * @param expand A comma-separated list of fields to expand. (optional)
     * @return View
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public View getView(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String filter, @javax.annotation.Nullable List<String> expand) throws ApiException {
        ApiResponse<View> localVarResp = getViewWithHttpInfo(ldAPIVersion, projectKey, viewKey, sort, limit, offset, filter, expand);
        return localVarResp.getData();
    }

    /**
     * Get view
     * Retrieve a specific view by its key.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @param sort A sort to apply to the list of views. (optional)
     * @param limit The number of views to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param filter A filter to apply to the list of views. (optional)
     * @param expand A comma-separated list of fields to expand. (optional)
     * @return ApiResponse&lt;View&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<View> getViewWithHttpInfo(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String filter, @javax.annotation.Nullable List<String> expand) throws ApiException {
        okhttp3.Call localVarCall = getViewValidateBeforeCall(ldAPIVersion, projectKey, viewKey, sort, limit, offset, filter, expand, null);
        Type localVarReturnType = new TypeToken<View>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get view (asynchronously)
     * Retrieve a specific view by its key.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @param sort A sort to apply to the list of views. (optional)
     * @param limit The number of views to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param filter A filter to apply to the list of views. (optional)
     * @param expand A comma-separated list of fields to expand. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getViewAsync(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String filter, @javax.annotation.Nullable List<String> expand, final ApiCallback<View> _callback) throws ApiException {

        okhttp3.Call localVarCall = getViewValidateBeforeCall(ldAPIVersion, projectKey, viewKey, sort, limit, offset, filter, expand, _callback);
        Type localVarReturnType = new TypeToken<View>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getViews
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param sort A sort to apply to the list of views. (optional)
     * @param limit The number of views to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param filter A filter to apply to the list of views. (optional)
     * @param expand A comma-separated list of fields to expand. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getViewsCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String filter, @javax.annotation.Nullable List<String> expand, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/views"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()));

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

        if (expand != null) {
            localVarCollectionQueryParams.addAll(localVarApiClient.parameterToPairs("csv", "expand", expand));
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

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }


        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getViewsValidateBeforeCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String filter, @javax.annotation.Nullable List<String> expand, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling getViews(Async)");
        }

        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getViews(Async)");
        }

        return getViewsCall(ldAPIVersion, projectKey, sort, limit, offset, filter, expand, _callback);

    }

    /**
     * List views
     * Get a list of all views in the given project.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param sort A sort to apply to the list of views. (optional)
     * @param limit The number of views to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param filter A filter to apply to the list of views. (optional)
     * @param expand A comma-separated list of fields to expand. (optional)
     * @return Views
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public Views getViews(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String filter, @javax.annotation.Nullable List<String> expand) throws ApiException {
        ApiResponse<Views> localVarResp = getViewsWithHttpInfo(ldAPIVersion, projectKey, sort, limit, offset, filter, expand);
        return localVarResp.getData();
    }

    /**
     * List views
     * Get a list of all views in the given project.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param sort A sort to apply to the list of views. (optional)
     * @param limit The number of views to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param filter A filter to apply to the list of views. (optional)
     * @param expand A comma-separated list of fields to expand. (optional)
     * @return ApiResponse&lt;Views&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Views> getViewsWithHttpInfo(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String filter, @javax.annotation.Nullable List<String> expand) throws ApiException {
        okhttp3.Call localVarCall = getViewsValidateBeforeCall(ldAPIVersion, projectKey, sort, limit, offset, filter, expand, null);
        Type localVarReturnType = new TypeToken<Views>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List views (asynchronously)
     * Get a list of all views in the given project.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param sort A sort to apply to the list of views. (optional)
     * @param limit The number of views to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param filter A filter to apply to the list of views. (optional)
     * @param expand A comma-separated list of fields to expand. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getViewsAsync(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String filter, @javax.annotation.Nullable List<String> expand, final ApiCallback<Views> _callback) throws ApiException {

        okhttp3.Call localVarCall = getViewsValidateBeforeCall(ldAPIVersion, projectKey, sort, limit, offset, filter, expand, _callback);
        Type localVarReturnType = new TypeToken<Views>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for linkResource
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @param resourceType  (required)
     * @param viewLinkRequest The resource to link to the view. Flags are identified by key. Segments are identified by segment ID. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call linkResourceCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nonnull String resourceType, @javax.annotation.Nonnull ViewLinkRequest viewLinkRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = viewLinkRequest;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/views/{viewKey}/link/{resourceType}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "viewKey" + "}", localVarApiClient.escapeString(viewKey.toString()))
            .replace("{" + "resourceType" + "}", localVarApiClient.escapeString(resourceType.toString()));

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

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }


        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call linkResourceValidateBeforeCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nonnull String resourceType, @javax.annotation.Nonnull ViewLinkRequest viewLinkRequest, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling linkResource(Async)");
        }

        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling linkResource(Async)");
        }

        // verify the required parameter 'viewKey' is set
        if (viewKey == null) {
            throw new ApiException("Missing the required parameter 'viewKey' when calling linkResource(Async)");
        }

        // verify the required parameter 'resourceType' is set
        if (resourceType == null) {
            throw new ApiException("Missing the required parameter 'resourceType' when calling linkResource(Async)");
        }

        // verify the required parameter 'viewLinkRequest' is set
        if (viewLinkRequest == null) {
            throw new ApiException("Missing the required parameter 'viewLinkRequest' when calling linkResource(Async)");
        }

        return linkResourceCall(ldAPIVersion, projectKey, viewKey, resourceType, viewLinkRequest, _callback);

    }

    /**
     * Link resource
     * Link one or multiple resources to a view: - Link flags using flag keys - Link AI configs using AI config keys - Link metrics using metric keys - Link segments using segment IDs 
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @param resourceType  (required)
     * @param viewLinkRequest The resource to link to the view. Flags are identified by key. Segments are identified by segment ID. (required)
     * @return LinkResourceSuccessResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public LinkResourceSuccessResponse linkResource(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nonnull String resourceType, @javax.annotation.Nonnull ViewLinkRequest viewLinkRequest) throws ApiException {
        ApiResponse<LinkResourceSuccessResponse> localVarResp = linkResourceWithHttpInfo(ldAPIVersion, projectKey, viewKey, resourceType, viewLinkRequest);
        return localVarResp.getData();
    }

    /**
     * Link resource
     * Link one or multiple resources to a view: - Link flags using flag keys - Link AI configs using AI config keys - Link metrics using metric keys - Link segments using segment IDs 
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @param resourceType  (required)
     * @param viewLinkRequest The resource to link to the view. Flags are identified by key. Segments are identified by segment ID. (required)
     * @return ApiResponse&lt;LinkResourceSuccessResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<LinkResourceSuccessResponse> linkResourceWithHttpInfo(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nonnull String resourceType, @javax.annotation.Nonnull ViewLinkRequest viewLinkRequest) throws ApiException {
        okhttp3.Call localVarCall = linkResourceValidateBeforeCall(ldAPIVersion, projectKey, viewKey, resourceType, viewLinkRequest, null);
        Type localVarReturnType = new TypeToken<LinkResourceSuccessResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Link resource (asynchronously)
     * Link one or multiple resources to a view: - Link flags using flag keys - Link AI configs using AI config keys - Link metrics using metric keys - Link segments using segment IDs 
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @param resourceType  (required)
     * @param viewLinkRequest The resource to link to the view. Flags are identified by key. Segments are identified by segment ID. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call linkResourceAsync(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nonnull String resourceType, @javax.annotation.Nonnull ViewLinkRequest viewLinkRequest, final ApiCallback<LinkResourceSuccessResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = linkResourceValidateBeforeCall(ldAPIVersion, projectKey, viewKey, resourceType, viewLinkRequest, _callback);
        Type localVarReturnType = new TypeToken<LinkResourceSuccessResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for unlinkResource
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @param resourceType  (required)
     * @param viewLinkRequest The resource to link to the view. Flags are identified by key. Segments are identified by segment ID. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response with unlink details </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call unlinkResourceCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nonnull String resourceType, @javax.annotation.Nonnull ViewLinkRequest viewLinkRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = viewLinkRequest;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/views/{viewKey}/link/{resourceType}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "viewKey" + "}", localVarApiClient.escapeString(viewKey.toString()))
            .replace("{" + "resourceType" + "}", localVarApiClient.escapeString(resourceType.toString()));

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

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }


        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call unlinkResourceValidateBeforeCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nonnull String resourceType, @javax.annotation.Nonnull ViewLinkRequest viewLinkRequest, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling unlinkResource(Async)");
        }

        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling unlinkResource(Async)");
        }

        // verify the required parameter 'viewKey' is set
        if (viewKey == null) {
            throw new ApiException("Missing the required parameter 'viewKey' when calling unlinkResource(Async)");
        }

        // verify the required parameter 'resourceType' is set
        if (resourceType == null) {
            throw new ApiException("Missing the required parameter 'resourceType' when calling unlinkResource(Async)");
        }

        // verify the required parameter 'viewLinkRequest' is set
        if (viewLinkRequest == null) {
            throw new ApiException("Missing the required parameter 'viewLinkRequest' when calling unlinkResource(Async)");
        }

        return unlinkResourceCall(ldAPIVersion, projectKey, viewKey, resourceType, viewLinkRequest, _callback);

    }

    /**
     * Unlink resource
     * Unlink one or multiple resources from a view: - Unlink flags using flag keys - Unlink segments using segment IDs - Unlink AI configs using AI config keys - Unlink metrics using metric keys 
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @param resourceType  (required)
     * @param viewLinkRequest The resource to link to the view. Flags are identified by key. Segments are identified by segment ID. (required)
     * @return UnlinkResourceSuccessResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response with unlink details </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public UnlinkResourceSuccessResponse unlinkResource(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nonnull String resourceType, @javax.annotation.Nonnull ViewLinkRequest viewLinkRequest) throws ApiException {
        ApiResponse<UnlinkResourceSuccessResponse> localVarResp = unlinkResourceWithHttpInfo(ldAPIVersion, projectKey, viewKey, resourceType, viewLinkRequest);
        return localVarResp.getData();
    }

    /**
     * Unlink resource
     * Unlink one or multiple resources from a view: - Unlink flags using flag keys - Unlink segments using segment IDs - Unlink AI configs using AI config keys - Unlink metrics using metric keys 
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @param resourceType  (required)
     * @param viewLinkRequest The resource to link to the view. Flags are identified by key. Segments are identified by segment ID. (required)
     * @return ApiResponse&lt;UnlinkResourceSuccessResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response with unlink details </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<UnlinkResourceSuccessResponse> unlinkResourceWithHttpInfo(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nonnull String resourceType, @javax.annotation.Nonnull ViewLinkRequest viewLinkRequest) throws ApiException {
        okhttp3.Call localVarCall = unlinkResourceValidateBeforeCall(ldAPIVersion, projectKey, viewKey, resourceType, viewLinkRequest, null);
        Type localVarReturnType = new TypeToken<UnlinkResourceSuccessResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Unlink resource (asynchronously)
     * Unlink one or multiple resources from a view: - Unlink flags using flag keys - Unlink segments using segment IDs - Unlink AI configs using AI config keys - Unlink metrics using metric keys 
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @param resourceType  (required)
     * @param viewLinkRequest The resource to link to the view. Flags are identified by key. Segments are identified by segment ID. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response with unlink details </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call unlinkResourceAsync(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nonnull String resourceType, @javax.annotation.Nonnull ViewLinkRequest viewLinkRequest, final ApiCallback<UnlinkResourceSuccessResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = unlinkResourceValidateBeforeCall(ldAPIVersion, projectKey, viewKey, resourceType, viewLinkRequest, _callback);
        Type localVarReturnType = new TypeToken<UnlinkResourceSuccessResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateView
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @param viewPatch A JSON representation of the view including only the fields to update. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateViewCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nonnull ViewPatch viewPatch, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = viewPatch;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/views/{viewKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "viewKey" + "}", localVarApiClient.escapeString(viewKey.toString()));

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

        if (ldAPIVersion != null) {
            localVarHeaderParams.put("LD-API-Version", localVarApiClient.parameterToString(ldAPIVersion));
        }


        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "PATCH", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateViewValidateBeforeCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nonnull ViewPatch viewPatch, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling updateView(Async)");
        }

        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling updateView(Async)");
        }

        // verify the required parameter 'viewKey' is set
        if (viewKey == null) {
            throw new ApiException("Missing the required parameter 'viewKey' when calling updateView(Async)");
        }

        // verify the required parameter 'viewPatch' is set
        if (viewPatch == null) {
            throw new ApiException("Missing the required parameter 'viewPatch' when calling updateView(Async)");
        }

        return updateViewCall(ldAPIVersion, projectKey, viewKey, viewPatch, _callback);

    }

    /**
     * Update view
     * Edit an existing view.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  Here&#39;s an example:   &#x60;&#x60;&#x60;     {       \&quot;description\&quot;: \&quot;Example updated description\&quot;,       \&quot;tags\&quot;: [\&quot;new-tag\&quot;]     }   &#x60;&#x60;&#x60; 
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @param viewPatch A JSON representation of the view including only the fields to update. (required)
     * @return View
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public View updateView(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nonnull ViewPatch viewPatch) throws ApiException {
        ApiResponse<View> localVarResp = updateViewWithHttpInfo(ldAPIVersion, projectKey, viewKey, viewPatch);
        return localVarResp.getData();
    }

    /**
     * Update view
     * Edit an existing view.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  Here&#39;s an example:   &#x60;&#x60;&#x60;     {       \&quot;description\&quot;: \&quot;Example updated description\&quot;,       \&quot;tags\&quot;: [\&quot;new-tag\&quot;]     }   &#x60;&#x60;&#x60; 
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @param viewPatch A JSON representation of the view including only the fields to update. (required)
     * @return ApiResponse&lt;View&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<View> updateViewWithHttpInfo(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nonnull ViewPatch viewPatch) throws ApiException {
        okhttp3.Call localVarCall = updateViewValidateBeforeCall(ldAPIVersion, projectKey, viewKey, viewPatch, null);
        Type localVarReturnType = new TypeToken<View>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update view (asynchronously)
     * Edit an existing view.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  Here&#39;s an example:   &#x60;&#x60;&#x60;     {       \&quot;description\&quot;: \&quot;Example updated description\&quot;,       \&quot;tags\&quot;: [\&quot;new-tag\&quot;]     }   &#x60;&#x60;&#x60; 
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param viewKey  (required)
     * @param viewPatch A JSON representation of the view including only the fields to update. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateViewAsync(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String viewKey, @javax.annotation.Nonnull ViewPatch viewPatch, final ApiCallback<View> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateViewValidateBeforeCall(ldAPIVersion, projectKey, viewKey, viewPatch, _callback);
        Type localVarReturnType = new TypeToken<View>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
