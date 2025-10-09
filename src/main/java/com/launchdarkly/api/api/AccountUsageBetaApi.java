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
import com.launchdarkly.api.model.NotFoundErrorRep;
import com.launchdarkly.api.model.RateLimitedErrorRep;
import com.launchdarkly.api.model.SdkListRep;
import com.launchdarkly.api.model.SdkVersionListRep;
import com.launchdarkly.api.model.SeriesListRep;
import com.launchdarkly.api.model.SeriesListRepFloat;
import com.launchdarkly.api.model.StatusServiceUnavailable;
import com.launchdarkly.api.model.UnauthorizedErrorRep;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountUsageBetaApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public AccountUsageBetaApi() {
        this(Configuration.getDefaultApiClient());
    }

    public AccountUsageBetaApi(ApiClient apiClient) {
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
     * Build call for getContextsClientsideUsage
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param contextKind A context kind to filter results by. Can be specified multiple times, one query parameter per context kind. (optional)
     * @param sdkName An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name. (optional)
     * @param anonymous An anonymous value to filter results by. Can be specified multiple times, one query parameter per anonymous value.&lt;br/&gt;Valid values: &#x60;true&#x60;, &#x60;false&#x60;. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. &#x60;contextKind&#x60; is always included as a grouping dimension. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;sdkName&#x60;, &#x60;sdkAppId&#x60;, &#x60;anonymousV2&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getContextsClientsideUsageCall(String from, String to, String projectKey, String environmentKey, String contextKind, String sdkName, String anonymous, String groupBy, String aggregationType, String granularity, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/usage/clientside-contexts";

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

        if (projectKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("projectKey", projectKey));
        }

        if (environmentKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("environmentKey", environmentKey));
        }

        if (contextKind != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("contextKind", contextKind));
        }

        if (sdkName != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sdkName", sdkName));
        }

        if (anonymous != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("anonymous", anonymous));
        }

        if (groupBy != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("groupBy", groupBy));
        }

        if (aggregationType != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("aggregationType", aggregationType));
        }

        if (granularity != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("granularity", granularity));
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
    private okhttp3.Call getContextsClientsideUsageValidateBeforeCall(String from, String to, String projectKey, String environmentKey, String contextKind, String sdkName, String anonymous, String groupBy, String aggregationType, String granularity, final ApiCallback _callback) throws ApiException {
        return getContextsClientsideUsageCall(from, to, projectKey, environmentKey, contextKind, sdkName, anonymous, groupBy, aggregationType, granularity, _callback);

    }

    /**
     * Get contexts clientside usage
     * Get a detailed time series of the number of context key usages observed by LaunchDarkly in your account, including non-primary context kinds. Use this for breakdowns that go beyond the primary-only aggregation of MAU endpoints. The counts reflect data reported by client-side SDKs.&lt;br/&gt;&lt;br/&gt;The supported granularity varies by aggregation type. The maximum time range is 365 days.
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param contextKind A context kind to filter results by. Can be specified multiple times, one query parameter per context kind. (optional)
     * @param sdkName An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name. (optional)
     * @param anonymous An anonymous value to filter results by. Can be specified multiple times, one query parameter per anonymous value.&lt;br/&gt;Valid values: &#x60;true&#x60;, &#x60;false&#x60;. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. &#x60;contextKind&#x60; is always included as a grouping dimension. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;sdkName&#x60;, &#x60;sdkAppId&#x60;, &#x60;anonymousV2&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @return SeriesListRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public SeriesListRep getContextsClientsideUsage(String from, String to, String projectKey, String environmentKey, String contextKind, String sdkName, String anonymous, String groupBy, String aggregationType, String granularity) throws ApiException {
        ApiResponse<SeriesListRep> localVarResp = getContextsClientsideUsageWithHttpInfo(from, to, projectKey, environmentKey, contextKind, sdkName, anonymous, groupBy, aggregationType, granularity);
        return localVarResp.getData();
    }

    /**
     * Get contexts clientside usage
     * Get a detailed time series of the number of context key usages observed by LaunchDarkly in your account, including non-primary context kinds. Use this for breakdowns that go beyond the primary-only aggregation of MAU endpoints. The counts reflect data reported by client-side SDKs.&lt;br/&gt;&lt;br/&gt;The supported granularity varies by aggregation type. The maximum time range is 365 days.
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param contextKind A context kind to filter results by. Can be specified multiple times, one query parameter per context kind. (optional)
     * @param sdkName An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name. (optional)
     * @param anonymous An anonymous value to filter results by. Can be specified multiple times, one query parameter per anonymous value.&lt;br/&gt;Valid values: &#x60;true&#x60;, &#x60;false&#x60;. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. &#x60;contextKind&#x60; is always included as a grouping dimension. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;sdkName&#x60;, &#x60;sdkAppId&#x60;, &#x60;anonymousV2&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @return ApiResponse&lt;SeriesListRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SeriesListRep> getContextsClientsideUsageWithHttpInfo(String from, String to, String projectKey, String environmentKey, String contextKind, String sdkName, String anonymous, String groupBy, String aggregationType, String granularity) throws ApiException {
        okhttp3.Call localVarCall = getContextsClientsideUsageValidateBeforeCall(from, to, projectKey, environmentKey, contextKind, sdkName, anonymous, groupBy, aggregationType, granularity, null);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get contexts clientside usage (asynchronously)
     * Get a detailed time series of the number of context key usages observed by LaunchDarkly in your account, including non-primary context kinds. Use this for breakdowns that go beyond the primary-only aggregation of MAU endpoints. The counts reflect data reported by client-side SDKs.&lt;br/&gt;&lt;br/&gt;The supported granularity varies by aggregation type. The maximum time range is 365 days.
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param contextKind A context kind to filter results by. Can be specified multiple times, one query parameter per context kind. (optional)
     * @param sdkName An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name. (optional)
     * @param anonymous An anonymous value to filter results by. Can be specified multiple times, one query parameter per anonymous value.&lt;br/&gt;Valid values: &#x60;true&#x60;, &#x60;false&#x60;. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. &#x60;contextKind&#x60; is always included as a grouping dimension. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;sdkName&#x60;, &#x60;sdkAppId&#x60;, &#x60;anonymousV2&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getContextsClientsideUsageAsync(String from, String to, String projectKey, String environmentKey, String contextKind, String sdkName, String anonymous, String groupBy, String aggregationType, String granularity, final ApiCallback<SeriesListRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getContextsClientsideUsageValidateBeforeCall(from, to, projectKey, environmentKey, contextKind, sdkName, anonymous, groupBy, aggregationType, granularity, _callback);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getContextsServersideUsage
     * @param from The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param contextKind A context kind to filter results by. Can be specified multiple times, one query parameter per context kind. (optional)
     * @param sdkName An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name. (optional)
     * @param anonymous An anonymous value to filter results by. Can be specified multiple times, one query parameter per anonymous value.&lt;br/&gt;Valid values: &#x60;true&#x60;, &#x60;false&#x60;. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. &#x60;contextKind&#x60; is always included as a grouping dimension. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;sdkName&#x60;, &#x60;sdkAppId&#x60;, &#x60;anonymousV2&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getContextsServersideUsageCall(String from, String to, String projectKey, String environmentKey, String contextKind, String sdkName, String anonymous, String groupBy, String aggregationType, String granularity, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/usage/serverside-contexts";

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

        if (projectKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("projectKey", projectKey));
        }

        if (environmentKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("environmentKey", environmentKey));
        }

        if (contextKind != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("contextKind", contextKind));
        }

        if (sdkName != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sdkName", sdkName));
        }

        if (anonymous != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("anonymous", anonymous));
        }

        if (groupBy != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("groupBy", groupBy));
        }

        if (aggregationType != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("aggregationType", aggregationType));
        }

        if (granularity != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("granularity", granularity));
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
    private okhttp3.Call getContextsServersideUsageValidateBeforeCall(String from, String to, String projectKey, String environmentKey, String contextKind, String sdkName, String anonymous, String groupBy, String aggregationType, String granularity, final ApiCallback _callback) throws ApiException {
        return getContextsServersideUsageCall(from, to, projectKey, environmentKey, contextKind, sdkName, anonymous, groupBy, aggregationType, granularity, _callback);

    }

    /**
     * Get contexts serverside usage
     * Get a detailed time series of the number of context key usages observed by LaunchDarkly in your account, including non-primary context kinds. Use this for breakdowns that go beyond the primary-only aggregation of MAU endpoints. The counts reflect data reported by server-side SDKs.&lt;br/&gt;&lt;br/&gt;The supported granularity varies by aggregation type. The maximum time range is 365 days.
     * @param from The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param contextKind A context kind to filter results by. Can be specified multiple times, one query parameter per context kind. (optional)
     * @param sdkName An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name. (optional)
     * @param anonymous An anonymous value to filter results by. Can be specified multiple times, one query parameter per anonymous value.&lt;br/&gt;Valid values: &#x60;true&#x60;, &#x60;false&#x60;. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. &#x60;contextKind&#x60; is always included as a grouping dimension. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;sdkName&#x60;, &#x60;sdkAppId&#x60;, &#x60;anonymousV2&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @return SeriesListRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public SeriesListRep getContextsServersideUsage(String from, String to, String projectKey, String environmentKey, String contextKind, String sdkName, String anonymous, String groupBy, String aggregationType, String granularity) throws ApiException {
        ApiResponse<SeriesListRep> localVarResp = getContextsServersideUsageWithHttpInfo(from, to, projectKey, environmentKey, contextKind, sdkName, anonymous, groupBy, aggregationType, granularity);
        return localVarResp.getData();
    }

    /**
     * Get contexts serverside usage
     * Get a detailed time series of the number of context key usages observed by LaunchDarkly in your account, including non-primary context kinds. Use this for breakdowns that go beyond the primary-only aggregation of MAU endpoints. The counts reflect data reported by server-side SDKs.&lt;br/&gt;&lt;br/&gt;The supported granularity varies by aggregation type. The maximum time range is 365 days.
     * @param from The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param contextKind A context kind to filter results by. Can be specified multiple times, one query parameter per context kind. (optional)
     * @param sdkName An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name. (optional)
     * @param anonymous An anonymous value to filter results by. Can be specified multiple times, one query parameter per anonymous value.&lt;br/&gt;Valid values: &#x60;true&#x60;, &#x60;false&#x60;. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. &#x60;contextKind&#x60; is always included as a grouping dimension. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;sdkName&#x60;, &#x60;sdkAppId&#x60;, &#x60;anonymousV2&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @return ApiResponse&lt;SeriesListRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SeriesListRep> getContextsServersideUsageWithHttpInfo(String from, String to, String projectKey, String environmentKey, String contextKind, String sdkName, String anonymous, String groupBy, String aggregationType, String granularity) throws ApiException {
        okhttp3.Call localVarCall = getContextsServersideUsageValidateBeforeCall(from, to, projectKey, environmentKey, contextKind, sdkName, anonymous, groupBy, aggregationType, granularity, null);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get contexts serverside usage (asynchronously)
     * Get a detailed time series of the number of context key usages observed by LaunchDarkly in your account, including non-primary context kinds. Use this for breakdowns that go beyond the primary-only aggregation of MAU endpoints. The counts reflect data reported by server-side SDKs.&lt;br/&gt;&lt;br/&gt;The supported granularity varies by aggregation type. The maximum time range is 365 days.
     * @param from The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param contextKind A context kind to filter results by. Can be specified multiple times, one query parameter per context kind. (optional)
     * @param sdkName An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name. (optional)
     * @param anonymous An anonymous value to filter results by. Can be specified multiple times, one query parameter per anonymous value.&lt;br/&gt;Valid values: &#x60;true&#x60;, &#x60;false&#x60;. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. &#x60;contextKind&#x60; is always included as a grouping dimension. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;sdkName&#x60;, &#x60;sdkAppId&#x60;, &#x60;anonymousV2&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getContextsServersideUsageAsync(String from, String to, String projectKey, String environmentKey, String contextKind, String sdkName, String anonymous, String groupBy, String aggregationType, String granularity, final ApiCallback<SeriesListRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getContextsServersideUsageValidateBeforeCall(from, to, projectKey, environmentKey, contextKind, sdkName, anonymous, groupBy, aggregationType, granularity, _callback);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getContextsTotalUsage
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param contextKind A context kind to filter results by. Can be specified multiple times, one query parameter per context kind. (optional)
     * @param sdkName An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name. (optional)
     * @param sdkType An SDK type to filter results by. Can be specified multiple times, one query parameter per SDK type. (optional)
     * @param anonymous An anonymous value to filter results by. Can be specified multiple times, one query parameter per anonymous value.&lt;br/&gt;Valid values: &#x60;true&#x60;, &#x60;false&#x60;. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. &#x60;contextKind&#x60; is always included as a grouping dimension. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;sdkName&#x60;, &#x60;sdkType&#x60;, &#x60;sdkAppId&#x60;, &#x60;anonymousV2&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getContextsTotalUsageCall(String from, String to, String projectKey, String environmentKey, String contextKind, String sdkName, String sdkType, String anonymous, String groupBy, String aggregationType, String granularity, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/usage/total-contexts";

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

        if (projectKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("projectKey", projectKey));
        }

        if (environmentKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("environmentKey", environmentKey));
        }

        if (contextKind != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("contextKind", contextKind));
        }

        if (sdkName != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sdkName", sdkName));
        }

        if (sdkType != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sdkType", sdkType));
        }

        if (anonymous != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("anonymous", anonymous));
        }

        if (groupBy != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("groupBy", groupBy));
        }

        if (aggregationType != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("aggregationType", aggregationType));
        }

        if (granularity != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("granularity", granularity));
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
    private okhttp3.Call getContextsTotalUsageValidateBeforeCall(String from, String to, String projectKey, String environmentKey, String contextKind, String sdkName, String sdkType, String anonymous, String groupBy, String aggregationType, String granularity, final ApiCallback _callback) throws ApiException {
        return getContextsTotalUsageCall(from, to, projectKey, environmentKey, contextKind, sdkName, sdkType, anonymous, groupBy, aggregationType, granularity, _callback);

    }

    /**
     * Get contexts total usage
     * Get a detailed time series of the number of context key usages observed by LaunchDarkly in your account, including non-primary context kinds. Use this for breakdowns that go beyond the primary-only aggregation of MAU endpoints.&lt;br/&gt;&lt;br/&gt;The supported granularity varies by aggregation type. The maximum time range is 365 days.
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param contextKind A context kind to filter results by. Can be specified multiple times, one query parameter per context kind. (optional)
     * @param sdkName An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name. (optional)
     * @param sdkType An SDK type to filter results by. Can be specified multiple times, one query parameter per SDK type. (optional)
     * @param anonymous An anonymous value to filter results by. Can be specified multiple times, one query parameter per anonymous value.&lt;br/&gt;Valid values: &#x60;true&#x60;, &#x60;false&#x60;. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. &#x60;contextKind&#x60; is always included as a grouping dimension. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;sdkName&#x60;, &#x60;sdkType&#x60;, &#x60;sdkAppId&#x60;, &#x60;anonymousV2&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @return SeriesListRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public SeriesListRep getContextsTotalUsage(String from, String to, String projectKey, String environmentKey, String contextKind, String sdkName, String sdkType, String anonymous, String groupBy, String aggregationType, String granularity) throws ApiException {
        ApiResponse<SeriesListRep> localVarResp = getContextsTotalUsageWithHttpInfo(from, to, projectKey, environmentKey, contextKind, sdkName, sdkType, anonymous, groupBy, aggregationType, granularity);
        return localVarResp.getData();
    }

    /**
     * Get contexts total usage
     * Get a detailed time series of the number of context key usages observed by LaunchDarkly in your account, including non-primary context kinds. Use this for breakdowns that go beyond the primary-only aggregation of MAU endpoints.&lt;br/&gt;&lt;br/&gt;The supported granularity varies by aggregation type. The maximum time range is 365 days.
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param contextKind A context kind to filter results by. Can be specified multiple times, one query parameter per context kind. (optional)
     * @param sdkName An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name. (optional)
     * @param sdkType An SDK type to filter results by. Can be specified multiple times, one query parameter per SDK type. (optional)
     * @param anonymous An anonymous value to filter results by. Can be specified multiple times, one query parameter per anonymous value.&lt;br/&gt;Valid values: &#x60;true&#x60;, &#x60;false&#x60;. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. &#x60;contextKind&#x60; is always included as a grouping dimension. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;sdkName&#x60;, &#x60;sdkType&#x60;, &#x60;sdkAppId&#x60;, &#x60;anonymousV2&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @return ApiResponse&lt;SeriesListRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SeriesListRep> getContextsTotalUsageWithHttpInfo(String from, String to, String projectKey, String environmentKey, String contextKind, String sdkName, String sdkType, String anonymous, String groupBy, String aggregationType, String granularity) throws ApiException {
        okhttp3.Call localVarCall = getContextsTotalUsageValidateBeforeCall(from, to, projectKey, environmentKey, contextKind, sdkName, sdkType, anonymous, groupBy, aggregationType, granularity, null);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get contexts total usage (asynchronously)
     * Get a detailed time series of the number of context key usages observed by LaunchDarkly in your account, including non-primary context kinds. Use this for breakdowns that go beyond the primary-only aggregation of MAU endpoints.&lt;br/&gt;&lt;br/&gt;The supported granularity varies by aggregation type. The maximum time range is 365 days.
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param contextKind A context kind to filter results by. Can be specified multiple times, one query parameter per context kind. (optional)
     * @param sdkName An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name. (optional)
     * @param sdkType An SDK type to filter results by. Can be specified multiple times, one query parameter per SDK type. (optional)
     * @param anonymous An anonymous value to filter results by. Can be specified multiple times, one query parameter per anonymous value.&lt;br/&gt;Valid values: &#x60;true&#x60;, &#x60;false&#x60;. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. &#x60;contextKind&#x60; is always included as a grouping dimension. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;sdkName&#x60;, &#x60;sdkType&#x60;, &#x60;sdkAppId&#x60;, &#x60;anonymousV2&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getContextsTotalUsageAsync(String from, String to, String projectKey, String environmentKey, String contextKind, String sdkName, String sdkType, String anonymous, String groupBy, String aggregationType, String granularity, final ApiCallback<SeriesListRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getContextsTotalUsageValidateBeforeCall(from, to, projectKey, environmentKey, contextKind, sdkName, sdkType, anonymous, groupBy, aggregationType, granularity, _callback);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDataExportEventsUsage
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param eventKind An event kind to filter results by. Can be specified multiple times, one query parameter per event kind. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;environmentId&#x60;, &#x60;eventKind&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. &#x60;monthly&#x60; granularity is only supported with the **month_to_date** aggregation type.&lt;br/&gt;Valid values: &#x60;daily&#x60;, &#x60;hourly&#x60;, &#x60;monthly&#x60;. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDataExportEventsUsageCall(String from, String to, String projectKey, String environmentKey, String eventKind, String groupBy, String aggregationType, String granularity, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/usage/data-export-events";

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

        if (projectKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("projectKey", projectKey));
        }

        if (environmentKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("environmentKey", environmentKey));
        }

        if (eventKind != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("eventKind", eventKind));
        }

        if (groupBy != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("groupBy", groupBy));
        }

        if (aggregationType != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("aggregationType", aggregationType));
        }

        if (granularity != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("granularity", granularity));
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
    private okhttp3.Call getDataExportEventsUsageValidateBeforeCall(String from, String to, String projectKey, String environmentKey, String eventKind, String groupBy, String aggregationType, String granularity, final ApiCallback _callback) throws ApiException {
        return getDataExportEventsUsageCall(from, to, projectKey, environmentKey, eventKind, groupBy, aggregationType, granularity, _callback);

    }

    /**
     * Get data export events usage
     * Get a time series array showing the number of data export events from your account. The supported granularity varies by aggregation type. The maximum time range is 365 days.
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param eventKind An event kind to filter results by. Can be specified multiple times, one query parameter per event kind. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;environmentId&#x60;, &#x60;eventKind&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. &#x60;monthly&#x60; granularity is only supported with the **month_to_date** aggregation type.&lt;br/&gt;Valid values: &#x60;daily&#x60;, &#x60;hourly&#x60;, &#x60;monthly&#x60;. (optional)
     * @return SeriesListRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public SeriesListRep getDataExportEventsUsage(String from, String to, String projectKey, String environmentKey, String eventKind, String groupBy, String aggregationType, String granularity) throws ApiException {
        ApiResponse<SeriesListRep> localVarResp = getDataExportEventsUsageWithHttpInfo(from, to, projectKey, environmentKey, eventKind, groupBy, aggregationType, granularity);
        return localVarResp.getData();
    }

    /**
     * Get data export events usage
     * Get a time series array showing the number of data export events from your account. The supported granularity varies by aggregation type. The maximum time range is 365 days.
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param eventKind An event kind to filter results by. Can be specified multiple times, one query parameter per event kind. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;environmentId&#x60;, &#x60;eventKind&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. &#x60;monthly&#x60; granularity is only supported with the **month_to_date** aggregation type.&lt;br/&gt;Valid values: &#x60;daily&#x60;, &#x60;hourly&#x60;, &#x60;monthly&#x60;. (optional)
     * @return ApiResponse&lt;SeriesListRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SeriesListRep> getDataExportEventsUsageWithHttpInfo(String from, String to, String projectKey, String environmentKey, String eventKind, String groupBy, String aggregationType, String granularity) throws ApiException {
        okhttp3.Call localVarCall = getDataExportEventsUsageValidateBeforeCall(from, to, projectKey, environmentKey, eventKind, groupBy, aggregationType, granularity, null);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get data export events usage (asynchronously)
     * Get a time series array showing the number of data export events from your account. The supported granularity varies by aggregation type. The maximum time range is 365 days.
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param eventKind An event kind to filter results by. Can be specified multiple times, one query parameter per event kind. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;environmentId&#x60;, &#x60;eventKind&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. &#x60;monthly&#x60; granularity is only supported with the **month_to_date** aggregation type.&lt;br/&gt;Valid values: &#x60;daily&#x60;, &#x60;hourly&#x60;, &#x60;monthly&#x60;. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDataExportEventsUsageAsync(String from, String to, String projectKey, String environmentKey, String eventKind, String groupBy, String aggregationType, String granularity, final ApiCallback<SeriesListRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDataExportEventsUsageValidateBeforeCall(from, to, projectKey, environmentKey, eventKind, groupBy, aggregationType, granularity, _callback);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getEvaluationsUsage
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param from The series of data returned starts from this timestamp. Defaults to 30 days ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @param tz The timezone to use for breaks between days when returning daily data. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getEvaluationsUsageCall(String projectKey, String environmentKey, String featureFlagKey, String from, String to, String tz, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/usage/evaluations/{projectKey}/{environmentKey}/{featureFlagKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()))
            .replace("{" + "featureFlagKey" + "}", localVarApiClient.escapeString(featureFlagKey.toString()));

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

        if (tz != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("tz", tz));
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
    private okhttp3.Call getEvaluationsUsageValidateBeforeCall(String projectKey, String environmentKey, String featureFlagKey, String from, String to, String tz, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getEvaluationsUsage(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling getEvaluationsUsage(Async)");
        }

        // verify the required parameter 'featureFlagKey' is set
        if (featureFlagKey == null) {
            throw new ApiException("Missing the required parameter 'featureFlagKey' when calling getEvaluationsUsage(Async)");
        }

        return getEvaluationsUsageCall(projectKey, environmentKey, featureFlagKey, from, to, tz, _callback);

    }

    /**
     * Get evaluations usage
     * Get time-series arrays of the number of times a flag is evaluated, broken down by the variation that resulted from that evaluation. The granularity of the data depends on the age of the data requested. If the requested range is within the past two hours, minutely data is returned. If it is within the last two days, hourly data is returned. Otherwise, daily data is returned.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param from The series of data returned starts from this timestamp. Defaults to 30 days ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @param tz The timezone to use for breaks between days when returning daily data. (optional)
     * @return SeriesListRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public SeriesListRep getEvaluationsUsage(String projectKey, String environmentKey, String featureFlagKey, String from, String to, String tz) throws ApiException {
        ApiResponse<SeriesListRep> localVarResp = getEvaluationsUsageWithHttpInfo(projectKey, environmentKey, featureFlagKey, from, to, tz);
        return localVarResp.getData();
    }

    /**
     * Get evaluations usage
     * Get time-series arrays of the number of times a flag is evaluated, broken down by the variation that resulted from that evaluation. The granularity of the data depends on the age of the data requested. If the requested range is within the past two hours, minutely data is returned. If it is within the last two days, hourly data is returned. Otherwise, daily data is returned.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param from The series of data returned starts from this timestamp. Defaults to 30 days ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @param tz The timezone to use for breaks between days when returning daily data. (optional)
     * @return ApiResponse&lt;SeriesListRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SeriesListRep> getEvaluationsUsageWithHttpInfo(String projectKey, String environmentKey, String featureFlagKey, String from, String to, String tz) throws ApiException {
        okhttp3.Call localVarCall = getEvaluationsUsageValidateBeforeCall(projectKey, environmentKey, featureFlagKey, from, to, tz, null);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get evaluations usage (asynchronously)
     * Get time-series arrays of the number of times a flag is evaluated, broken down by the variation that resulted from that evaluation. The granularity of the data depends on the age of the data requested. If the requested range is within the past two hours, minutely data is returned. If it is within the last two days, hourly data is returned. Otherwise, daily data is returned.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param from The series of data returned starts from this timestamp. Defaults to 30 days ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @param tz The timezone to use for breaks between days when returning daily data. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getEvaluationsUsageAsync(String projectKey, String environmentKey, String featureFlagKey, String from, String to, String tz, final ApiCallback<SeriesListRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getEvaluationsUsageValidateBeforeCall(projectKey, environmentKey, featureFlagKey, from, to, tz, _callback);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getEventsUsage
     * @param type The type of event to retrieve. Must be either &#x60;received&#x60; or &#x60;published&#x60;. (required)
     * @param from The series of data returned starts from this timestamp. Defaults to 24 hours ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getEventsUsageCall(String type, String from, String to, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/usage/events/{type}"
            .replace("{" + "type" + "}", localVarApiClient.escapeString(type.toString()));

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
    private okhttp3.Call getEventsUsageValidateBeforeCall(String type, String from, String to, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'type' is set
        if (type == null) {
            throw new ApiException("Missing the required parameter 'type' when calling getEventsUsage(Async)");
        }

        return getEventsUsageCall(type, from, to, _callback);

    }

    /**
     * Get events usage
     * Get time-series arrays of the number of times a flag is evaluated, broken down by the variation that resulted from that evaluation. The granularity of the data depends on the age of the data requested. If the requested range is within the past two hours, minutely data is returned. If it is within the last two days, hourly data is returned. Otherwise, daily data is returned.
     * @param type The type of event to retrieve. Must be either &#x60;received&#x60; or &#x60;published&#x60;. (required)
     * @param from The series of data returned starts from this timestamp. Defaults to 24 hours ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @return SeriesListRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public SeriesListRep getEventsUsage(String type, String from, String to) throws ApiException {
        ApiResponse<SeriesListRep> localVarResp = getEventsUsageWithHttpInfo(type, from, to);
        return localVarResp.getData();
    }

    /**
     * Get events usage
     * Get time-series arrays of the number of times a flag is evaluated, broken down by the variation that resulted from that evaluation. The granularity of the data depends on the age of the data requested. If the requested range is within the past two hours, minutely data is returned. If it is within the last two days, hourly data is returned. Otherwise, daily data is returned.
     * @param type The type of event to retrieve. Must be either &#x60;received&#x60; or &#x60;published&#x60;. (required)
     * @param from The series of data returned starts from this timestamp. Defaults to 24 hours ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @return ApiResponse&lt;SeriesListRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SeriesListRep> getEventsUsageWithHttpInfo(String type, String from, String to) throws ApiException {
        okhttp3.Call localVarCall = getEventsUsageValidateBeforeCall(type, from, to, null);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get events usage (asynchronously)
     * Get time-series arrays of the number of times a flag is evaluated, broken down by the variation that resulted from that evaluation. The granularity of the data depends on the age of the data requested. If the requested range is within the past two hours, minutely data is returned. If it is within the last two days, hourly data is returned. Otherwise, daily data is returned.
     * @param type The type of event to retrieve. Must be either &#x60;received&#x60; or &#x60;published&#x60;. (required)
     * @param from The series of data returned starts from this timestamp. Defaults to 24 hours ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getEventsUsageAsync(String type, String from, String to, final ApiCallback<SeriesListRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getEventsUsageValidateBeforeCall(type, from, to, _callback);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getExperimentationEventsUsage
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param eventKey An event key to filter results by. Can be specified multiple times, one query parameter per event key. (optional)
     * @param eventKind An event kind to filter results by. Can be specified multiple times, one query parameter per event kind. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;environmentId&#x60;, &#x60;eventKey&#x60;, &#x60;eventKind&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. &#x60;monthly&#x60; granularity is only supported with the **month_to_date** aggregation type.&lt;br/&gt;Valid values: &#x60;daily&#x60;, &#x60;hourly&#x60;, &#x60;monthly&#x60;. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getExperimentationEventsUsageCall(String from, String to, String projectKey, String environmentKey, String eventKey, String eventKind, String groupBy, String aggregationType, String granularity, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/usage/experimentation-events";

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

        if (projectKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("projectKey", projectKey));
        }

        if (environmentKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("environmentKey", environmentKey));
        }

        if (eventKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("eventKey", eventKey));
        }

        if (eventKind != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("eventKind", eventKind));
        }

        if (groupBy != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("groupBy", groupBy));
        }

        if (aggregationType != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("aggregationType", aggregationType));
        }

        if (granularity != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("granularity", granularity));
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
    private okhttp3.Call getExperimentationEventsUsageValidateBeforeCall(String from, String to, String projectKey, String environmentKey, String eventKey, String eventKind, String groupBy, String aggregationType, String granularity, final ApiCallback _callback) throws ApiException {
        return getExperimentationEventsUsageCall(from, to, projectKey, environmentKey, eventKey, eventKind, groupBy, aggregationType, granularity, _callback);

    }

    /**
     * Get experimentation events usage
     * Get a time series array showing the number of experimentation events from your account. The supported granularity varies by aggregation type. The maximum time range is 365 days.
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param eventKey An event key to filter results by. Can be specified multiple times, one query parameter per event key. (optional)
     * @param eventKind An event kind to filter results by. Can be specified multiple times, one query parameter per event kind. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;environmentId&#x60;, &#x60;eventKey&#x60;, &#x60;eventKind&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. &#x60;monthly&#x60; granularity is only supported with the **month_to_date** aggregation type.&lt;br/&gt;Valid values: &#x60;daily&#x60;, &#x60;hourly&#x60;, &#x60;monthly&#x60;. (optional)
     * @return SeriesListRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public SeriesListRep getExperimentationEventsUsage(String from, String to, String projectKey, String environmentKey, String eventKey, String eventKind, String groupBy, String aggregationType, String granularity) throws ApiException {
        ApiResponse<SeriesListRep> localVarResp = getExperimentationEventsUsageWithHttpInfo(from, to, projectKey, environmentKey, eventKey, eventKind, groupBy, aggregationType, granularity);
        return localVarResp.getData();
    }

    /**
     * Get experimentation events usage
     * Get a time series array showing the number of experimentation events from your account. The supported granularity varies by aggregation type. The maximum time range is 365 days.
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param eventKey An event key to filter results by. Can be specified multiple times, one query parameter per event key. (optional)
     * @param eventKind An event kind to filter results by. Can be specified multiple times, one query parameter per event kind. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;environmentId&#x60;, &#x60;eventKey&#x60;, &#x60;eventKind&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. &#x60;monthly&#x60; granularity is only supported with the **month_to_date** aggregation type.&lt;br/&gt;Valid values: &#x60;daily&#x60;, &#x60;hourly&#x60;, &#x60;monthly&#x60;. (optional)
     * @return ApiResponse&lt;SeriesListRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SeriesListRep> getExperimentationEventsUsageWithHttpInfo(String from, String to, String projectKey, String environmentKey, String eventKey, String eventKind, String groupBy, String aggregationType, String granularity) throws ApiException {
        okhttp3.Call localVarCall = getExperimentationEventsUsageValidateBeforeCall(from, to, projectKey, environmentKey, eventKey, eventKind, groupBy, aggregationType, granularity, null);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get experimentation events usage (asynchronously)
     * Get a time series array showing the number of experimentation events from your account. The supported granularity varies by aggregation type. The maximum time range is 365 days.
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param eventKey An event key to filter results by. Can be specified multiple times, one query parameter per event key. (optional)
     * @param eventKind An event kind to filter results by. Can be specified multiple times, one query parameter per event kind. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;environmentId&#x60;, &#x60;eventKey&#x60;, &#x60;eventKind&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. &#x60;monthly&#x60; granularity is only supported with the **month_to_date** aggregation type.&lt;br/&gt;Valid values: &#x60;daily&#x60;, &#x60;hourly&#x60;, &#x60;monthly&#x60;. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getExperimentationEventsUsageAsync(String from, String to, String projectKey, String environmentKey, String eventKey, String eventKind, String groupBy, String aggregationType, String granularity, final ApiCallback<SeriesListRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getExperimentationEventsUsageValidateBeforeCall(from, to, projectKey, environmentKey, eventKey, eventKind, groupBy, aggregationType, granularity, _callback);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getExperimentationKeysUsage
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param experimentId An experiment ID to filter results by. Can be specified multiple times, one query parameter per experiment ID. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;experimentId&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. &#x60;monthly&#x60; granularity is only supported with the **month_to_date** aggregation type.&lt;br/&gt;Valid values: &#x60;daily&#x60;, &#x60;hourly&#x60;, &#x60;monthly&#x60;. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getExperimentationKeysUsageCall(String from, String to, String projectKey, String environmentKey, String experimentId, String groupBy, String aggregationType, String granularity, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/usage/experimentation-keys";

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

        if (projectKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("projectKey", projectKey));
        }

        if (environmentKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("environmentKey", environmentKey));
        }

        if (experimentId != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("experimentId", experimentId));
        }

        if (groupBy != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("groupBy", groupBy));
        }

        if (aggregationType != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("aggregationType", aggregationType));
        }

        if (granularity != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("granularity", granularity));
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
    private okhttp3.Call getExperimentationKeysUsageValidateBeforeCall(String from, String to, String projectKey, String environmentKey, String experimentId, String groupBy, String aggregationType, String granularity, final ApiCallback _callback) throws ApiException {
        return getExperimentationKeysUsageCall(from, to, projectKey, environmentKey, experimentId, groupBy, aggregationType, granularity, _callback);

    }

    /**
     * Get experimentation keys usage
     * Get a time series array showing the number of experimentation keys from your account. The supported granularity varies by aggregation type. The maximum time range is 365 days.
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param experimentId An experiment ID to filter results by. Can be specified multiple times, one query parameter per experiment ID. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;experimentId&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. &#x60;monthly&#x60; granularity is only supported with the **month_to_date** aggregation type.&lt;br/&gt;Valid values: &#x60;daily&#x60;, &#x60;hourly&#x60;, &#x60;monthly&#x60;. (optional)
     * @return SeriesListRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public SeriesListRep getExperimentationKeysUsage(String from, String to, String projectKey, String environmentKey, String experimentId, String groupBy, String aggregationType, String granularity) throws ApiException {
        ApiResponse<SeriesListRep> localVarResp = getExperimentationKeysUsageWithHttpInfo(from, to, projectKey, environmentKey, experimentId, groupBy, aggregationType, granularity);
        return localVarResp.getData();
    }

    /**
     * Get experimentation keys usage
     * Get a time series array showing the number of experimentation keys from your account. The supported granularity varies by aggregation type. The maximum time range is 365 days.
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param experimentId An experiment ID to filter results by. Can be specified multiple times, one query parameter per experiment ID. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;experimentId&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. &#x60;monthly&#x60; granularity is only supported with the **month_to_date** aggregation type.&lt;br/&gt;Valid values: &#x60;daily&#x60;, &#x60;hourly&#x60;, &#x60;monthly&#x60;. (optional)
     * @return ApiResponse&lt;SeriesListRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SeriesListRep> getExperimentationKeysUsageWithHttpInfo(String from, String to, String projectKey, String environmentKey, String experimentId, String groupBy, String aggregationType, String granularity) throws ApiException {
        okhttp3.Call localVarCall = getExperimentationKeysUsageValidateBeforeCall(from, to, projectKey, environmentKey, experimentId, groupBy, aggregationType, granularity, null);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get experimentation keys usage (asynchronously)
     * Get a time series array showing the number of experimentation keys from your account. The supported granularity varies by aggregation type. The maximum time range is 365 days.
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param experimentId An experiment ID to filter results by. Can be specified multiple times, one query parameter per experiment ID. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;experimentId&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. &#x60;monthly&#x60; granularity is only supported with the **month_to_date** aggregation type.&lt;br/&gt;Valid values: &#x60;daily&#x60;, &#x60;hourly&#x60;, &#x60;monthly&#x60;. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getExperimentationKeysUsageAsync(String from, String to, String projectKey, String environmentKey, String experimentId, String groupBy, String aggregationType, String granularity, final ApiCallback<SeriesListRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getExperimentationKeysUsageValidateBeforeCall(from, to, projectKey, environmentKey, experimentId, groupBy, aggregationType, granularity, _callback);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getMauSdksByType
     * @param from The data returned starts from this timestamp. Defaults to seven days ago. The timestamp is in Unix milliseconds, for example, 1656694800000. (optional)
     * @param to The data returned ends at this timestamp. Defaults to the current time. The timestamp is in Unix milliseconds, for example, 1657904400000. (optional)
     * @param sdktype The type of SDK with monthly active users (MAU) to list. Must be either &#x60;client&#x60; or &#x60;server&#x60;. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> MAU SDKs response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getMauSdksByTypeCall(String from, String to, String sdktype, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/usage/mau/sdks";

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

        if (sdktype != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sdktype", sdktype));
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
    private okhttp3.Call getMauSdksByTypeValidateBeforeCall(String from, String to, String sdktype, final ApiCallback _callback) throws ApiException {
        return getMauSdksByTypeCall(from, to, sdktype, _callback);

    }

    /**
     * Get MAU SDKs by type
     * Get a list of SDKs. These are all of the SDKs that have connected to LaunchDarkly by monthly active users (MAU) in the requested time period.&lt;br/&gt;&lt;br/&gt;Endpoints for retrieving monthly active users (MAU) do not return information about active context instances. After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should not rely on this endpoint. To learn more, read [Account usage metrics](https://launchdarkly.com/docs/home/account/metrics).
     * @param from The data returned starts from this timestamp. Defaults to seven days ago. The timestamp is in Unix milliseconds, for example, 1656694800000. (optional)
     * @param to The data returned ends at this timestamp. Defaults to the current time. The timestamp is in Unix milliseconds, for example, 1657904400000. (optional)
     * @param sdktype The type of SDK with monthly active users (MAU) to list. Must be either &#x60;client&#x60; or &#x60;server&#x60;. (optional)
     * @return SdkListRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> MAU SDKs response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public SdkListRep getMauSdksByType(String from, String to, String sdktype) throws ApiException {
        ApiResponse<SdkListRep> localVarResp = getMauSdksByTypeWithHttpInfo(from, to, sdktype);
        return localVarResp.getData();
    }

    /**
     * Get MAU SDKs by type
     * Get a list of SDKs. These are all of the SDKs that have connected to LaunchDarkly by monthly active users (MAU) in the requested time period.&lt;br/&gt;&lt;br/&gt;Endpoints for retrieving monthly active users (MAU) do not return information about active context instances. After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should not rely on this endpoint. To learn more, read [Account usage metrics](https://launchdarkly.com/docs/home/account/metrics).
     * @param from The data returned starts from this timestamp. Defaults to seven days ago. The timestamp is in Unix milliseconds, for example, 1656694800000. (optional)
     * @param to The data returned ends at this timestamp. Defaults to the current time. The timestamp is in Unix milliseconds, for example, 1657904400000. (optional)
     * @param sdktype The type of SDK with monthly active users (MAU) to list. Must be either &#x60;client&#x60; or &#x60;server&#x60;. (optional)
     * @return ApiResponse&lt;SdkListRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> MAU SDKs response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SdkListRep> getMauSdksByTypeWithHttpInfo(String from, String to, String sdktype) throws ApiException {
        okhttp3.Call localVarCall = getMauSdksByTypeValidateBeforeCall(from, to, sdktype, null);
        Type localVarReturnType = new TypeToken<SdkListRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get MAU SDKs by type (asynchronously)
     * Get a list of SDKs. These are all of the SDKs that have connected to LaunchDarkly by monthly active users (MAU) in the requested time period.&lt;br/&gt;&lt;br/&gt;Endpoints for retrieving monthly active users (MAU) do not return information about active context instances. After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should not rely on this endpoint. To learn more, read [Account usage metrics](https://launchdarkly.com/docs/home/account/metrics).
     * @param from The data returned starts from this timestamp. Defaults to seven days ago. The timestamp is in Unix milliseconds, for example, 1656694800000. (optional)
     * @param to The data returned ends at this timestamp. Defaults to the current time. The timestamp is in Unix milliseconds, for example, 1657904400000. (optional)
     * @param sdktype The type of SDK with monthly active users (MAU) to list. Must be either &#x60;client&#x60; or &#x60;server&#x60;. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> MAU SDKs response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getMauSdksByTypeAsync(String from, String to, String sdktype, final ApiCallback<SdkListRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getMauSdksByTypeValidateBeforeCall(from, to, sdktype, _callback);
        Type localVarReturnType = new TypeToken<SdkListRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getMauUsage
     * @param from The series of data returned starts from this timestamp. Defaults to 30 days ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @param project A project key to filter results to. Can be specified multiple times, one query parameter per project key, to view data for multiple projects. (optional)
     * @param environment An environment key to filter results to. When using this parameter, exactly one project key must also be set. Can be specified multiple times as separate query parameters to view data for multiple environments within a single project. (optional)
     * @param sdktype An SDK type to filter results to. Can be specified multiple times, one query parameter per SDK type. Valid values: client, server (optional)
     * @param sdk An SDK name to filter results to. Can be specified multiple times, one query parameter per SDK. (optional)
     * @param anonymous If specified, filters results to either anonymous or nonanonymous users. (optional)
     * @param groupby If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions (for example, to group by both project and SDK). Valid values: project, environment, sdktype, sdk, anonymous, contextKind, sdkAppId (optional)
     * @param aggregationType If specified, queries for rolling 30-day, month-to-date, or daily incremental counts. Default is rolling 30-day. Valid values: rolling_30d, month_to_date, daily_incremental (optional)
     * @param contextKind Filters results to the specified context kinds. Can be specified multiple times, one query parameter per context kind. If not set, queries for the user context kind. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getMauUsageCall(String from, String to, String project, String environment, String sdktype, String sdk, String anonymous, String groupby, String aggregationType, String contextKind, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/usage/mau";

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

        if (project != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("project", project));
        }

        if (environment != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("environment", environment));
        }

        if (sdktype != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sdktype", sdktype));
        }

        if (sdk != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sdk", sdk));
        }

        if (anonymous != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("anonymous", anonymous));
        }

        if (groupby != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("groupby", groupby));
        }

        if (aggregationType != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("aggregationType", aggregationType));
        }

        if (contextKind != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("contextKind", contextKind));
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
    private okhttp3.Call getMauUsageValidateBeforeCall(String from, String to, String project, String environment, String sdktype, String sdk, String anonymous, String groupby, String aggregationType, String contextKind, final ApiCallback _callback) throws ApiException {
        return getMauUsageCall(from, to, project, environment, sdktype, sdk, anonymous, groupby, aggregationType, contextKind, _callback);

    }

    /**
     * Get MAU usage
     * Get a time-series array of the number of monthly active users (MAU) seen by LaunchDarkly from your account. The granularity is always daily.&lt;br/&gt;&lt;br/&gt;Endpoints for retrieving monthly active users (MAU) do not return information about active context instances. After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should not rely on this endpoint. To learn more, read [Account usage metrics](https://launchdarkly.com/docs/home/account/metrics).
     * @param from The series of data returned starts from this timestamp. Defaults to 30 days ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @param project A project key to filter results to. Can be specified multiple times, one query parameter per project key, to view data for multiple projects. (optional)
     * @param environment An environment key to filter results to. When using this parameter, exactly one project key must also be set. Can be specified multiple times as separate query parameters to view data for multiple environments within a single project. (optional)
     * @param sdktype An SDK type to filter results to. Can be specified multiple times, one query parameter per SDK type. Valid values: client, server (optional)
     * @param sdk An SDK name to filter results to. Can be specified multiple times, one query parameter per SDK. (optional)
     * @param anonymous If specified, filters results to either anonymous or nonanonymous users. (optional)
     * @param groupby If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions (for example, to group by both project and SDK). Valid values: project, environment, sdktype, sdk, anonymous, contextKind, sdkAppId (optional)
     * @param aggregationType If specified, queries for rolling 30-day, month-to-date, or daily incremental counts. Default is rolling 30-day. Valid values: rolling_30d, month_to_date, daily_incremental (optional)
     * @param contextKind Filters results to the specified context kinds. Can be specified multiple times, one query parameter per context kind. If not set, queries for the user context kind. (optional)
     * @return SeriesListRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public SeriesListRep getMauUsage(String from, String to, String project, String environment, String sdktype, String sdk, String anonymous, String groupby, String aggregationType, String contextKind) throws ApiException {
        ApiResponse<SeriesListRep> localVarResp = getMauUsageWithHttpInfo(from, to, project, environment, sdktype, sdk, anonymous, groupby, aggregationType, contextKind);
        return localVarResp.getData();
    }

    /**
     * Get MAU usage
     * Get a time-series array of the number of monthly active users (MAU) seen by LaunchDarkly from your account. The granularity is always daily.&lt;br/&gt;&lt;br/&gt;Endpoints for retrieving monthly active users (MAU) do not return information about active context instances. After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should not rely on this endpoint. To learn more, read [Account usage metrics](https://launchdarkly.com/docs/home/account/metrics).
     * @param from The series of data returned starts from this timestamp. Defaults to 30 days ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @param project A project key to filter results to. Can be specified multiple times, one query parameter per project key, to view data for multiple projects. (optional)
     * @param environment An environment key to filter results to. When using this parameter, exactly one project key must also be set. Can be specified multiple times as separate query parameters to view data for multiple environments within a single project. (optional)
     * @param sdktype An SDK type to filter results to. Can be specified multiple times, one query parameter per SDK type. Valid values: client, server (optional)
     * @param sdk An SDK name to filter results to. Can be specified multiple times, one query parameter per SDK. (optional)
     * @param anonymous If specified, filters results to either anonymous or nonanonymous users. (optional)
     * @param groupby If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions (for example, to group by both project and SDK). Valid values: project, environment, sdktype, sdk, anonymous, contextKind, sdkAppId (optional)
     * @param aggregationType If specified, queries for rolling 30-day, month-to-date, or daily incremental counts. Default is rolling 30-day. Valid values: rolling_30d, month_to_date, daily_incremental (optional)
     * @param contextKind Filters results to the specified context kinds. Can be specified multiple times, one query parameter per context kind. If not set, queries for the user context kind. (optional)
     * @return ApiResponse&lt;SeriesListRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SeriesListRep> getMauUsageWithHttpInfo(String from, String to, String project, String environment, String sdktype, String sdk, String anonymous, String groupby, String aggregationType, String contextKind) throws ApiException {
        okhttp3.Call localVarCall = getMauUsageValidateBeforeCall(from, to, project, environment, sdktype, sdk, anonymous, groupby, aggregationType, contextKind, null);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get MAU usage (asynchronously)
     * Get a time-series array of the number of monthly active users (MAU) seen by LaunchDarkly from your account. The granularity is always daily.&lt;br/&gt;&lt;br/&gt;Endpoints for retrieving monthly active users (MAU) do not return information about active context instances. After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should not rely on this endpoint. To learn more, read [Account usage metrics](https://launchdarkly.com/docs/home/account/metrics).
     * @param from The series of data returned starts from this timestamp. Defaults to 30 days ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @param project A project key to filter results to. Can be specified multiple times, one query parameter per project key, to view data for multiple projects. (optional)
     * @param environment An environment key to filter results to. When using this parameter, exactly one project key must also be set. Can be specified multiple times as separate query parameters to view data for multiple environments within a single project. (optional)
     * @param sdktype An SDK type to filter results to. Can be specified multiple times, one query parameter per SDK type. Valid values: client, server (optional)
     * @param sdk An SDK name to filter results to. Can be specified multiple times, one query parameter per SDK. (optional)
     * @param anonymous If specified, filters results to either anonymous or nonanonymous users. (optional)
     * @param groupby If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions (for example, to group by both project and SDK). Valid values: project, environment, sdktype, sdk, anonymous, contextKind, sdkAppId (optional)
     * @param aggregationType If specified, queries for rolling 30-day, month-to-date, or daily incremental counts. Default is rolling 30-day. Valid values: rolling_30d, month_to_date, daily_incremental (optional)
     * @param contextKind Filters results to the specified context kinds. Can be specified multiple times, one query parameter per context kind. If not set, queries for the user context kind. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getMauUsageAsync(String from, String to, String project, String environment, String sdktype, String sdk, String anonymous, String groupby, String aggregationType, String contextKind, final ApiCallback<SeriesListRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getMauUsageValidateBeforeCall(from, to, project, environment, sdktype, sdk, anonymous, groupby, aggregationType, contextKind, _callback);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getMauUsageByCategory
     * @param from The series of data returned starts from this timestamp. Defaults to 30 days ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getMauUsageByCategoryCall(String from, String to, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/usage/mau/bycategory";

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
    private okhttp3.Call getMauUsageByCategoryValidateBeforeCall(String from, String to, final ApiCallback _callback) throws ApiException {
        return getMauUsageByCategoryCall(from, to, _callback);

    }

    /**
     * Get MAU usage by category
     * Get time-series arrays of the number of monthly active users (MAU) seen by LaunchDarkly from your account, broken down by the category of users. The category is either &#x60;browser&#x60;, &#x60;mobile&#x60;, or &#x60;backend&#x60;.&lt;br/&gt;&lt;br/&gt;Endpoints for retrieving monthly active users (MAU) do not return information about active context instances. After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should not rely on this endpoint. To learn more, read [Account usage metrics](https://launchdarkly.com/docs/home/account/metrics).
     * @param from The series of data returned starts from this timestamp. Defaults to 30 days ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @return SeriesListRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public SeriesListRep getMauUsageByCategory(String from, String to) throws ApiException {
        ApiResponse<SeriesListRep> localVarResp = getMauUsageByCategoryWithHttpInfo(from, to);
        return localVarResp.getData();
    }

    /**
     * Get MAU usage by category
     * Get time-series arrays of the number of monthly active users (MAU) seen by LaunchDarkly from your account, broken down by the category of users. The category is either &#x60;browser&#x60;, &#x60;mobile&#x60;, or &#x60;backend&#x60;.&lt;br/&gt;&lt;br/&gt;Endpoints for retrieving monthly active users (MAU) do not return information about active context instances. After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should not rely on this endpoint. To learn more, read [Account usage metrics](https://launchdarkly.com/docs/home/account/metrics).
     * @param from The series of data returned starts from this timestamp. Defaults to 30 days ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @return ApiResponse&lt;SeriesListRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SeriesListRep> getMauUsageByCategoryWithHttpInfo(String from, String to) throws ApiException {
        okhttp3.Call localVarCall = getMauUsageByCategoryValidateBeforeCall(from, to, null);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get MAU usage by category (asynchronously)
     * Get time-series arrays of the number of monthly active users (MAU) seen by LaunchDarkly from your account, broken down by the category of users. The category is either &#x60;browser&#x60;, &#x60;mobile&#x60;, or &#x60;backend&#x60;.&lt;br/&gt;&lt;br/&gt;Endpoints for retrieving monthly active users (MAU) do not return information about active context instances. After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should not rely on this endpoint. To learn more, read [Account usage metrics](https://launchdarkly.com/docs/home/account/metrics).
     * @param from The series of data returned starts from this timestamp. Defaults to 30 days ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getMauUsageByCategoryAsync(String from, String to, final ApiCallback<SeriesListRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getMauUsageByCategoryValidateBeforeCall(from, to, _callback);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getObservabilityErrorsUsage
     * @param from The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getObservabilityErrorsUsageCall(String from, String to, String projectKey, String granularity, String aggregationType, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/usage/observability/errors";

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

        if (projectKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("projectKey", projectKey));
        }

        if (granularity != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("granularity", granularity));
        }

        if (aggregationType != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("aggregationType", aggregationType));
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
    private okhttp3.Call getObservabilityErrorsUsageValidateBeforeCall(String from, String to, String projectKey, String granularity, String aggregationType, final ApiCallback _callback) throws ApiException {
        return getObservabilityErrorsUsageCall(from, to, projectKey, granularity, aggregationType, _callback);

    }

    /**
     * Get observability errors usage
     * Get time-series arrays of the number of observability errors. Supports &#x60;daily&#x60; and &#x60;monthly&#x60; granularity.
     * @param from The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @return SeriesListRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public SeriesListRep getObservabilityErrorsUsage(String from, String to, String projectKey, String granularity, String aggregationType) throws ApiException {
        ApiResponse<SeriesListRep> localVarResp = getObservabilityErrorsUsageWithHttpInfo(from, to, projectKey, granularity, aggregationType);
        return localVarResp.getData();
    }

    /**
     * Get observability errors usage
     * Get time-series arrays of the number of observability errors. Supports &#x60;daily&#x60; and &#x60;monthly&#x60; granularity.
     * @param from The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @return ApiResponse&lt;SeriesListRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SeriesListRep> getObservabilityErrorsUsageWithHttpInfo(String from, String to, String projectKey, String granularity, String aggregationType) throws ApiException {
        okhttp3.Call localVarCall = getObservabilityErrorsUsageValidateBeforeCall(from, to, projectKey, granularity, aggregationType, null);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get observability errors usage (asynchronously)
     * Get time-series arrays of the number of observability errors. Supports &#x60;daily&#x60; and &#x60;monthly&#x60; granularity.
     * @param from The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getObservabilityErrorsUsageAsync(String from, String to, String projectKey, String granularity, String aggregationType, final ApiCallback<SeriesListRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getObservabilityErrorsUsageValidateBeforeCall(from, to, projectKey, granularity, aggregationType, _callback);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getObservabilityLogsUsage
     * @param from The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getObservabilityLogsUsageCall(String from, String to, String projectKey, String granularity, String aggregationType, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/usage/observability/logs";

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

        if (projectKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("projectKey", projectKey));
        }

        if (granularity != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("granularity", granularity));
        }

        if (aggregationType != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("aggregationType", aggregationType));
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
    private okhttp3.Call getObservabilityLogsUsageValidateBeforeCall(String from, String to, String projectKey, String granularity, String aggregationType, final ApiCallback _callback) throws ApiException {
        return getObservabilityLogsUsageCall(from, to, projectKey, granularity, aggregationType, _callback);

    }

    /**
     * Get observability logs usage
     * Get time-series arrays of the number of observability logs. Supports &#x60;daily&#x60; and &#x60;monthly&#x60; granularity.
     * @param from The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @return SeriesListRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public SeriesListRep getObservabilityLogsUsage(String from, String to, String projectKey, String granularity, String aggregationType) throws ApiException {
        ApiResponse<SeriesListRep> localVarResp = getObservabilityLogsUsageWithHttpInfo(from, to, projectKey, granularity, aggregationType);
        return localVarResp.getData();
    }

    /**
     * Get observability logs usage
     * Get time-series arrays of the number of observability logs. Supports &#x60;daily&#x60; and &#x60;monthly&#x60; granularity.
     * @param from The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @return ApiResponse&lt;SeriesListRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SeriesListRep> getObservabilityLogsUsageWithHttpInfo(String from, String to, String projectKey, String granularity, String aggregationType) throws ApiException {
        okhttp3.Call localVarCall = getObservabilityLogsUsageValidateBeforeCall(from, to, projectKey, granularity, aggregationType, null);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get observability logs usage (asynchronously)
     * Get time-series arrays of the number of observability logs. Supports &#x60;daily&#x60; and &#x60;monthly&#x60; granularity.
     * @param from The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getObservabilityLogsUsageAsync(String from, String to, String projectKey, String granularity, String aggregationType, final ApiCallback<SeriesListRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getObservabilityLogsUsageValidateBeforeCall(from, to, projectKey, granularity, aggregationType, _callback);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getObservabilitySessionsUsage
     * @param from The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getObservabilitySessionsUsageCall(String from, String to, String projectKey, String granularity, String aggregationType, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/usage/observability/sessions";

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

        if (projectKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("projectKey", projectKey));
        }

        if (granularity != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("granularity", granularity));
        }

        if (aggregationType != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("aggregationType", aggregationType));
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
    private okhttp3.Call getObservabilitySessionsUsageValidateBeforeCall(String from, String to, String projectKey, String granularity, String aggregationType, final ApiCallback _callback) throws ApiException {
        return getObservabilitySessionsUsageCall(from, to, projectKey, granularity, aggregationType, _callback);

    }

    /**
     * Get observability sessions usage
     * Get time-series arrays of the number of observability sessions. Supports &#x60;daily&#x60; and &#x60;monthly&#x60; granularity.
     * @param from The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @return SeriesListRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public SeriesListRep getObservabilitySessionsUsage(String from, String to, String projectKey, String granularity, String aggregationType) throws ApiException {
        ApiResponse<SeriesListRep> localVarResp = getObservabilitySessionsUsageWithHttpInfo(from, to, projectKey, granularity, aggregationType);
        return localVarResp.getData();
    }

    /**
     * Get observability sessions usage
     * Get time-series arrays of the number of observability sessions. Supports &#x60;daily&#x60; and &#x60;monthly&#x60; granularity.
     * @param from The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @return ApiResponse&lt;SeriesListRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SeriesListRep> getObservabilitySessionsUsageWithHttpInfo(String from, String to, String projectKey, String granularity, String aggregationType) throws ApiException {
        okhttp3.Call localVarCall = getObservabilitySessionsUsageValidateBeforeCall(from, to, projectKey, granularity, aggregationType, null);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get observability sessions usage (asynchronously)
     * Get time-series arrays of the number of observability sessions. Supports &#x60;daily&#x60; and &#x60;monthly&#x60; granularity.
     * @param from The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getObservabilitySessionsUsageAsync(String from, String to, String projectKey, String granularity, String aggregationType, final ApiCallback<SeriesListRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getObservabilitySessionsUsageValidateBeforeCall(from, to, projectKey, granularity, aggregationType, _callback);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getObservabilityTracesUsage
     * @param from The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getObservabilityTracesUsageCall(String from, String to, String projectKey, String granularity, String aggregationType, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/usage/observability/traces";

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

        if (projectKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("projectKey", projectKey));
        }

        if (granularity != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("granularity", granularity));
        }

        if (aggregationType != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("aggregationType", aggregationType));
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
    private okhttp3.Call getObservabilityTracesUsageValidateBeforeCall(String from, String to, String projectKey, String granularity, String aggregationType, final ApiCallback _callback) throws ApiException {
        return getObservabilityTracesUsageCall(from, to, projectKey, granularity, aggregationType, _callback);

    }

    /**
     * Get observability traces usage
     * Get time-series arrays of the number of observability traces. Supports &#x60;daily&#x60; and &#x60;monthly&#x60; granularity.
     * @param from The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @return SeriesListRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public SeriesListRep getObservabilityTracesUsage(String from, String to, String projectKey, String granularity, String aggregationType) throws ApiException {
        ApiResponse<SeriesListRep> localVarResp = getObservabilityTracesUsageWithHttpInfo(from, to, projectKey, granularity, aggregationType);
        return localVarResp.getData();
    }

    /**
     * Get observability traces usage
     * Get time-series arrays of the number of observability traces. Supports &#x60;daily&#x60; and &#x60;monthly&#x60; granularity.
     * @param from The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @return ApiResponse&lt;SeriesListRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SeriesListRep> getObservabilityTracesUsageWithHttpInfo(String from, String to, String projectKey, String granularity, String aggregationType) throws ApiException {
        okhttp3.Call localVarCall = getObservabilityTracesUsageValidateBeforeCall(from, to, projectKey, granularity, aggregationType, null);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get observability traces usage (asynchronously)
     * Get time-series arrays of the number of observability traces. Supports &#x60;daily&#x60; and &#x60;monthly&#x60; granularity.
     * @param from The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getObservabilityTracesUsageAsync(String from, String to, String projectKey, String granularity, String aggregationType, final ApiCallback<SeriesListRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getObservabilityTracesUsageValidateBeforeCall(from, to, projectKey, granularity, aggregationType, _callback);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getServiceConnectionsUsage
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param connectionType A connection type to filter results by. Can be specified multiple times, one query parameter per connection type. (optional)
     * @param relayVersion A relay version to filter results by. Can be specified multiple times, one query parameter per relay version. (optional)
     * @param sdkName An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name. (optional)
     * @param sdkVersion An SDK version to filter results by. Can be specified multiple times, one query parameter per SDK version. (optional)
     * @param sdkType An SDK type to filter results by. Can be specified multiple times, one query parameter per SDK type. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;connectionType&#x60;, &#x60;relayVersion&#x60;, &#x60;sdkName&#x60;, &#x60;sdkVersion&#x60;, &#x60;sdkType&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. &#x60;monthly&#x60; granularity is only supported with the **month_to_date** aggregation type.&lt;br/&gt;Valid values: &#x60;daily&#x60;, &#x60;hourly&#x60;, &#x60;monthly&#x60;. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getServiceConnectionsUsageCall(String from, String to, String projectKey, String environmentKey, String connectionType, String relayVersion, String sdkName, String sdkVersion, String sdkType, String groupBy, String aggregationType, String granularity, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/usage/service-connections";

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

        if (projectKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("projectKey", projectKey));
        }

        if (environmentKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("environmentKey", environmentKey));
        }

        if (connectionType != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("connectionType", connectionType));
        }

        if (relayVersion != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("relayVersion", relayVersion));
        }

        if (sdkName != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sdkName", sdkName));
        }

        if (sdkVersion != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sdkVersion", sdkVersion));
        }

        if (sdkType != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sdkType", sdkType));
        }

        if (groupBy != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("groupBy", groupBy));
        }

        if (aggregationType != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("aggregationType", aggregationType));
        }

        if (granularity != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("granularity", granularity));
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
    private okhttp3.Call getServiceConnectionsUsageValidateBeforeCall(String from, String to, String projectKey, String environmentKey, String connectionType, String relayVersion, String sdkName, String sdkVersion, String sdkType, String groupBy, String aggregationType, String granularity, final ApiCallback _callback) throws ApiException {
        return getServiceConnectionsUsageCall(from, to, projectKey, environmentKey, connectionType, relayVersion, sdkName, sdkVersion, sdkType, groupBy, aggregationType, granularity, _callback);

    }

    /**
     * Get service connections usage
     * Get a time series array showing the number of service connection minutes from your account. The supported granularity varies by aggregation type. The maximum time range is 365 days.
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param connectionType A connection type to filter results by. Can be specified multiple times, one query parameter per connection type. (optional)
     * @param relayVersion A relay version to filter results by. Can be specified multiple times, one query parameter per relay version. (optional)
     * @param sdkName An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name. (optional)
     * @param sdkVersion An SDK version to filter results by. Can be specified multiple times, one query parameter per SDK version. (optional)
     * @param sdkType An SDK type to filter results by. Can be specified multiple times, one query parameter per SDK type. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;connectionType&#x60;, &#x60;relayVersion&#x60;, &#x60;sdkName&#x60;, &#x60;sdkVersion&#x60;, &#x60;sdkType&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. &#x60;monthly&#x60; granularity is only supported with the **month_to_date** aggregation type.&lt;br/&gt;Valid values: &#x60;daily&#x60;, &#x60;hourly&#x60;, &#x60;monthly&#x60;. (optional)
     * @return SeriesListRepFloat
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public SeriesListRepFloat getServiceConnectionsUsage(String from, String to, String projectKey, String environmentKey, String connectionType, String relayVersion, String sdkName, String sdkVersion, String sdkType, String groupBy, String aggregationType, String granularity) throws ApiException {
        ApiResponse<SeriesListRepFloat> localVarResp = getServiceConnectionsUsageWithHttpInfo(from, to, projectKey, environmentKey, connectionType, relayVersion, sdkName, sdkVersion, sdkType, groupBy, aggregationType, granularity);
        return localVarResp.getData();
    }

    /**
     * Get service connections usage
     * Get a time series array showing the number of service connection minutes from your account. The supported granularity varies by aggregation type. The maximum time range is 365 days.
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param connectionType A connection type to filter results by. Can be specified multiple times, one query parameter per connection type. (optional)
     * @param relayVersion A relay version to filter results by. Can be specified multiple times, one query parameter per relay version. (optional)
     * @param sdkName An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name. (optional)
     * @param sdkVersion An SDK version to filter results by. Can be specified multiple times, one query parameter per SDK version. (optional)
     * @param sdkType An SDK type to filter results by. Can be specified multiple times, one query parameter per SDK type. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;connectionType&#x60;, &#x60;relayVersion&#x60;, &#x60;sdkName&#x60;, &#x60;sdkVersion&#x60;, &#x60;sdkType&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. &#x60;monthly&#x60; granularity is only supported with the **month_to_date** aggregation type.&lt;br/&gt;Valid values: &#x60;daily&#x60;, &#x60;hourly&#x60;, &#x60;monthly&#x60;. (optional)
     * @return ApiResponse&lt;SeriesListRepFloat&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SeriesListRepFloat> getServiceConnectionsUsageWithHttpInfo(String from, String to, String projectKey, String environmentKey, String connectionType, String relayVersion, String sdkName, String sdkVersion, String sdkType, String groupBy, String aggregationType, String granularity) throws ApiException {
        okhttp3.Call localVarCall = getServiceConnectionsUsageValidateBeforeCall(from, to, projectKey, environmentKey, connectionType, relayVersion, sdkName, sdkVersion, sdkType, groupBy, aggregationType, granularity, null);
        Type localVarReturnType = new TypeToken<SeriesListRepFloat>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get service connections usage (asynchronously)
     * Get a time series array showing the number of service connection minutes from your account. The supported granularity varies by aggregation type. The maximum time range is 365 days.
     * @param from The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. (optional)
     * @param to The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. (optional)
     * @param projectKey A project key to filter results by. Can be specified multiple times, one query parameter per project key. (optional)
     * @param environmentKey An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. (optional)
     * @param connectionType A connection type to filter results by. Can be specified multiple times, one query parameter per connection type. (optional)
     * @param relayVersion A relay version to filter results by. Can be specified multiple times, one query parameter per relay version. (optional)
     * @param sdkName An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name. (optional)
     * @param sdkVersion An SDK version to filter results by. Can be specified multiple times, one query parameter per SDK version. (optional)
     * @param sdkType An SDK type to filter results by. Can be specified multiple times, one query parameter per SDK type. (optional)
     * @param groupBy If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;connectionType&#x60;, &#x60;relayVersion&#x60;, &#x60;sdkName&#x60;, &#x60;sdkVersion&#x60;, &#x60;sdkType&#x60;. (optional)
     * @param aggregationType Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;. (optional)
     * @param granularity Specifies the data granularity. Defaults to &#x60;daily&#x60;. &#x60;monthly&#x60; granularity is only supported with the **month_to_date** aggregation type.&lt;br/&gt;Valid values: &#x60;daily&#x60;, &#x60;hourly&#x60;, &#x60;monthly&#x60;. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Service unavailable </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getServiceConnectionsUsageAsync(String from, String to, String projectKey, String environmentKey, String connectionType, String relayVersion, String sdkName, String sdkVersion, String sdkType, String groupBy, String aggregationType, String granularity, final ApiCallback<SeriesListRepFloat> _callback) throws ApiException {

        okhttp3.Call localVarCall = getServiceConnectionsUsageValidateBeforeCall(from, to, projectKey, environmentKey, connectionType, relayVersion, sdkName, sdkVersion, sdkType, groupBy, aggregationType, granularity, _callback);
        Type localVarReturnType = new TypeToken<SeriesListRepFloat>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getStreamUsage
     * @param source The source of streaming connections to describe. Must be either &#x60;client&#x60; or &#x60;server&#x60;. (required)
     * @param from The series of data returned starts from this timestamp. Defaults to 30 days ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @param tz The timezone to use for breaks between days when returning daily data. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getStreamUsageCall(String source, String from, String to, String tz, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/usage/streams/{source}"
            .replace("{" + "source" + "}", localVarApiClient.escapeString(source.toString()));

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

        if (tz != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("tz", tz));
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
    private okhttp3.Call getStreamUsageValidateBeforeCall(String source, String from, String to, String tz, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'source' is set
        if (source == null) {
            throw new ApiException("Missing the required parameter 'source' when calling getStreamUsage(Async)");
        }

        return getStreamUsageCall(source, from, to, tz, _callback);

    }

    /**
     * Get stream usage
     * Get a time-series array of the number of streaming connections to LaunchDarkly in each time period. The granularity of the data depends on the age of the data requested. If the requested range is within the past two hours, minutely data is returned. If it is within the last two days, hourly data is returned. Otherwise, daily data is returned.
     * @param source The source of streaming connections to describe. Must be either &#x60;client&#x60; or &#x60;server&#x60;. (required)
     * @param from The series of data returned starts from this timestamp. Defaults to 30 days ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @param tz The timezone to use for breaks between days when returning daily data. (optional)
     * @return SeriesListRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public SeriesListRep getStreamUsage(String source, String from, String to, String tz) throws ApiException {
        ApiResponse<SeriesListRep> localVarResp = getStreamUsageWithHttpInfo(source, from, to, tz);
        return localVarResp.getData();
    }

    /**
     * Get stream usage
     * Get a time-series array of the number of streaming connections to LaunchDarkly in each time period. The granularity of the data depends on the age of the data requested. If the requested range is within the past two hours, minutely data is returned. If it is within the last two days, hourly data is returned. Otherwise, daily data is returned.
     * @param source The source of streaming connections to describe. Must be either &#x60;client&#x60; or &#x60;server&#x60;. (required)
     * @param from The series of data returned starts from this timestamp. Defaults to 30 days ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @param tz The timezone to use for breaks between days when returning daily data. (optional)
     * @return ApiResponse&lt;SeriesListRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SeriesListRep> getStreamUsageWithHttpInfo(String source, String from, String to, String tz) throws ApiException {
        okhttp3.Call localVarCall = getStreamUsageValidateBeforeCall(source, from, to, tz, null);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get stream usage (asynchronously)
     * Get a time-series array of the number of streaming connections to LaunchDarkly in each time period. The granularity of the data depends on the age of the data requested. If the requested range is within the past two hours, minutely data is returned. If it is within the last two days, hourly data is returned. Otherwise, daily data is returned.
     * @param source The source of streaming connections to describe. Must be either &#x60;client&#x60; or &#x60;server&#x60;. (required)
     * @param from The series of data returned starts from this timestamp. Defaults to 30 days ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @param tz The timezone to use for breaks between days when returning daily data. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getStreamUsageAsync(String source, String from, String to, String tz, final ApiCallback<SeriesListRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getStreamUsageValidateBeforeCall(source, from, to, tz, _callback);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getStreamUsageBySdkVersion
     * @param source The source of streaming connections to describe. Must be either &#x60;client&#x60; or &#x60;server&#x60;. (required)
     * @param from The series of data returned starts from this timestamp. Defaults to 24 hours ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @param tz The timezone to use for breaks between days when returning daily data. (optional)
     * @param sdk If included, this filters the returned series to only those that match this SDK name. (optional)
     * @param version If included, this filters the returned series to only those that match this SDK version. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getStreamUsageBySdkVersionCall(String source, String from, String to, String tz, String sdk, String version, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/usage/streams/{source}/bysdkversion"
            .replace("{" + "source" + "}", localVarApiClient.escapeString(source.toString()));

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

        if (tz != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("tz", tz));
        }

        if (sdk != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sdk", sdk));
        }

        if (version != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("version", version));
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
    private okhttp3.Call getStreamUsageBySdkVersionValidateBeforeCall(String source, String from, String to, String tz, String sdk, String version, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'source' is set
        if (source == null) {
            throw new ApiException("Missing the required parameter 'source' when calling getStreamUsageBySdkVersion(Async)");
        }

        return getStreamUsageBySdkVersionCall(source, from, to, tz, sdk, version, _callback);

    }

    /**
     * Get stream usage by SDK version
     * Get multiple series of the number of streaming connections to LaunchDarkly in each time period, separated by SDK type and version. Information about each series is in the metadata array. The granularity of the data depends on the age of the data requested. If the requested range is within the past 2 hours, minutely data is returned. If it is within the last two days, hourly data is returned. Otherwise, daily data is returned.
     * @param source The source of streaming connections to describe. Must be either &#x60;client&#x60; or &#x60;server&#x60;. (required)
     * @param from The series of data returned starts from this timestamp. Defaults to 24 hours ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @param tz The timezone to use for breaks between days when returning daily data. (optional)
     * @param sdk If included, this filters the returned series to only those that match this SDK name. (optional)
     * @param version If included, this filters the returned series to only those that match this SDK version. (optional)
     * @return SeriesListRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public SeriesListRep getStreamUsageBySdkVersion(String source, String from, String to, String tz, String sdk, String version) throws ApiException {
        ApiResponse<SeriesListRep> localVarResp = getStreamUsageBySdkVersionWithHttpInfo(source, from, to, tz, sdk, version);
        return localVarResp.getData();
    }

    /**
     * Get stream usage by SDK version
     * Get multiple series of the number of streaming connections to LaunchDarkly in each time period, separated by SDK type and version. Information about each series is in the metadata array. The granularity of the data depends on the age of the data requested. If the requested range is within the past 2 hours, minutely data is returned. If it is within the last two days, hourly data is returned. Otherwise, daily data is returned.
     * @param source The source of streaming connections to describe. Must be either &#x60;client&#x60; or &#x60;server&#x60;. (required)
     * @param from The series of data returned starts from this timestamp. Defaults to 24 hours ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @param tz The timezone to use for breaks between days when returning daily data. (optional)
     * @param sdk If included, this filters the returned series to only those that match this SDK name. (optional)
     * @param version If included, this filters the returned series to only those that match this SDK version. (optional)
     * @return ApiResponse&lt;SeriesListRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SeriesListRep> getStreamUsageBySdkVersionWithHttpInfo(String source, String from, String to, String tz, String sdk, String version) throws ApiException {
        okhttp3.Call localVarCall = getStreamUsageBySdkVersionValidateBeforeCall(source, from, to, tz, sdk, version, null);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get stream usage by SDK version (asynchronously)
     * Get multiple series of the number of streaming connections to LaunchDarkly in each time period, separated by SDK type and version. Information about each series is in the metadata array. The granularity of the data depends on the age of the data requested. If the requested range is within the past 2 hours, minutely data is returned. If it is within the last two days, hourly data is returned. Otherwise, daily data is returned.
     * @param source The source of streaming connections to describe. Must be either &#x60;client&#x60; or &#x60;server&#x60;. (required)
     * @param from The series of data returned starts from this timestamp. Defaults to 24 hours ago. (optional)
     * @param to The series of data returned ends at this timestamp. Defaults to the current time. (optional)
     * @param tz The timezone to use for breaks between days when returning daily data. (optional)
     * @param sdk If included, this filters the returned series to only those that match this SDK name. (optional)
     * @param version If included, this filters the returned series to only those that match this SDK version. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Usage response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getStreamUsageBySdkVersionAsync(String source, String from, String to, String tz, String sdk, String version, final ApiCallback<SeriesListRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getStreamUsageBySdkVersionValidateBeforeCall(source, from, to, tz, sdk, version, _callback);
        Type localVarReturnType = new TypeToken<SeriesListRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getStreamUsageSdkversion
     * @param source The source of streaming connections to describe. Must be either &#x60;client&#x60; or &#x60;server&#x60;. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> SDK Versions response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getStreamUsageSdkversionCall(String source, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/usage/streams/{source}/sdkversions"
            .replace("{" + "source" + "}", localVarApiClient.escapeString(source.toString()));

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
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getStreamUsageSdkversionValidateBeforeCall(String source, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'source' is set
        if (source == null) {
            throw new ApiException("Missing the required parameter 'source' when calling getStreamUsageSdkversion(Async)");
        }

        return getStreamUsageSdkversionCall(source, _callback);

    }

    /**
     * Get stream usage SDK versions
     * Get a list of SDK version objects, which contain an SDK name and version. These are all of the SDKs that have connected to LaunchDarkly from your account in the past 60 days.
     * @param source The source of streaming connections to describe. Must be either &#x60;client&#x60; or &#x60;server&#x60;. (required)
     * @return SdkVersionListRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> SDK Versions response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public SdkVersionListRep getStreamUsageSdkversion(String source) throws ApiException {
        ApiResponse<SdkVersionListRep> localVarResp = getStreamUsageSdkversionWithHttpInfo(source);
        return localVarResp.getData();
    }

    /**
     * Get stream usage SDK versions
     * Get a list of SDK version objects, which contain an SDK name and version. These are all of the SDKs that have connected to LaunchDarkly from your account in the past 60 days.
     * @param source The source of streaming connections to describe. Must be either &#x60;client&#x60; or &#x60;server&#x60;. (required)
     * @return ApiResponse&lt;SdkVersionListRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> SDK Versions response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SdkVersionListRep> getStreamUsageSdkversionWithHttpInfo(String source) throws ApiException {
        okhttp3.Call localVarCall = getStreamUsageSdkversionValidateBeforeCall(source, null);
        Type localVarReturnType = new TypeToken<SdkVersionListRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get stream usage SDK versions (asynchronously)
     * Get a list of SDK version objects, which contain an SDK name and version. These are all of the SDKs that have connected to LaunchDarkly from your account in the past 60 days.
     * @param source The source of streaming connections to describe. Must be either &#x60;client&#x60; or &#x60;server&#x60;. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> SDK Versions response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getStreamUsageSdkversionAsync(String source, final ApiCallback<SdkVersionListRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getStreamUsageSdkversionValidateBeforeCall(source, _callback);
        Type localVarReturnType = new TypeToken<SdkVersionListRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
