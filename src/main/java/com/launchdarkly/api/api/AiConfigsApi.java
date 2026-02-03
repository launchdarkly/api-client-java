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


import com.launchdarkly.api.model.AIConfig;
import com.launchdarkly.api.model.AIConfigPatch;
import com.launchdarkly.api.model.AIConfigPost;
import com.launchdarkly.api.model.AIConfigTargeting;
import com.launchdarkly.api.model.AIConfigTargetingPatch;
import com.launchdarkly.api.model.AIConfigVariation;
import com.launchdarkly.api.model.AIConfigVariationPatch;
import com.launchdarkly.api.model.AIConfigVariationPost;
import com.launchdarkly.api.model.AIConfigVariationsResponse;
import com.launchdarkly.api.model.AIConfigs;
import com.launchdarkly.api.model.AITool;
import com.launchdarkly.api.model.AIToolPatch;
import com.launchdarkly.api.model.AIToolPost;
import com.launchdarkly.api.model.AITools;
import com.launchdarkly.api.model.AgentGraph;
import com.launchdarkly.api.model.AgentGraphPatch;
import com.launchdarkly.api.model.AgentGraphPost;
import com.launchdarkly.api.model.AgentGraphs;
import com.launchdarkly.api.model.Error;
import com.launchdarkly.api.model.MetricByVariation;
import com.launchdarkly.api.model.Metrics;
import com.launchdarkly.api.model.ModelConfig;
import com.launchdarkly.api.model.ModelConfigPost;
import com.launchdarkly.api.model.RestrictedModelsRequest;
import com.launchdarkly.api.model.RestrictedModelsResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AiConfigsApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public AiConfigsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public AiConfigsApi(ApiClient apiClient) {
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
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAIConfigCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, final ApiCallback _callback) throws ApiException {
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
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "configKey" + "}", localVarApiClient.escapeString(configKey.toString()));

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
    private okhttp3.Call deleteAIConfigValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling deleteAIConfig(Async)");
        }

        // verify the required parameter 'configKey' is set
        if (configKey == null) {
            throw new ApiException("Missing the required parameter 'configKey' when calling deleteAIConfig(Async)");
        }

        return deleteAIConfigCall(projectKey, configKey, _callback);

    }

    /**
     * Delete AI Config
     * Delete an existing AI Config.
     * @param projectKey  (required)
     * @param configKey  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public void deleteAIConfig(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey) throws ApiException {
        deleteAIConfigWithHttpInfo(projectKey, configKey);
    }

    /**
     * Delete AI Config
     * Delete an existing AI Config.
     * @param projectKey  (required)
     * @param configKey  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteAIConfigWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey) throws ApiException {
        okhttp3.Call localVarCall = deleteAIConfigValidateBeforeCall(projectKey, configKey, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete AI Config (asynchronously)
     * Delete an existing AI Config.
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAIConfigAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteAIConfigValidateBeforeCall(projectKey, configKey, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteAIConfigVariation
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAIConfigVariationCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull String variationKey, final ApiCallback _callback) throws ApiException {
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
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "configKey" + "}", localVarApiClient.escapeString(configKey.toString()))
            .replace("{" + "variationKey" + "}", localVarApiClient.escapeString(variationKey.toString()));

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
    private okhttp3.Call deleteAIConfigVariationValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull String variationKey, final ApiCallback _callback) throws ApiException {
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

        return deleteAIConfigVariationCall(projectKey, configKey, variationKey, _callback);

    }

    /**
     * Delete AI Config variation
     * Delete a specific variation of an AI Config by config key and variation key.
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public void deleteAIConfigVariation(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull String variationKey) throws ApiException {
        deleteAIConfigVariationWithHttpInfo(projectKey, configKey, variationKey);
    }

    /**
     * Delete AI Config variation
     * Delete a specific variation of an AI Config by config key and variation key.
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteAIConfigVariationWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull String variationKey) throws ApiException {
        okhttp3.Call localVarCall = deleteAIConfigVariationValidateBeforeCall(projectKey, configKey, variationKey, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete AI Config variation (asynchronously)
     * Delete a specific variation of an AI Config by config key and variation key.
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAIConfigVariationAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull String variationKey, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteAIConfigVariationValidateBeforeCall(projectKey, configKey, variationKey, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteAITool
     * @param projectKey  (required)
     * @param toolKey  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAIToolCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String toolKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/ai-tools/{toolKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "toolKey" + "}", localVarApiClient.escapeString(toolKey.toString()));

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
    private okhttp3.Call deleteAIToolValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String toolKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling deleteAITool(Async)");
        }

        // verify the required parameter 'toolKey' is set
        if (toolKey == null) {
            throw new ApiException("Missing the required parameter 'toolKey' when calling deleteAITool(Async)");
        }

        return deleteAIToolCall(projectKey, toolKey, _callback);

    }

    /**
     * Delete AI tool
     * Delete an existing AI tool.
     * @param projectKey  (required)
     * @param toolKey  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public void deleteAITool(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String toolKey) throws ApiException {
        deleteAIToolWithHttpInfo(projectKey, toolKey);
    }

    /**
     * Delete AI tool
     * Delete an existing AI tool.
     * @param projectKey  (required)
     * @param toolKey  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteAIToolWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String toolKey) throws ApiException {
        okhttp3.Call localVarCall = deleteAIToolValidateBeforeCall(projectKey, toolKey, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete AI tool (asynchronously)
     * Delete an existing AI tool.
     * @param projectKey  (required)
     * @param toolKey  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAIToolAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String toolKey, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteAIToolValidateBeforeCall(projectKey, toolKey, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteAgentGraph
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param graphKey  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAgentGraphCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String graphKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/agent-graphs/{graphKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "graphKey" + "}", localVarApiClient.escapeString(graphKey.toString()));

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
    private okhttp3.Call deleteAgentGraphValidateBeforeCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String graphKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling deleteAgentGraph(Async)");
        }

        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling deleteAgentGraph(Async)");
        }

        // verify the required parameter 'graphKey' is set
        if (graphKey == null) {
            throw new ApiException("Missing the required parameter 'graphKey' when calling deleteAgentGraph(Async)");
        }

        return deleteAgentGraphCall(ldAPIVersion, projectKey, graphKey, _callback);

    }

    /**
     * Delete agent graph
     * Delete an existing agent graph and all of its edges.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param graphKey  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public void deleteAgentGraph(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String graphKey) throws ApiException {
        deleteAgentGraphWithHttpInfo(ldAPIVersion, projectKey, graphKey);
    }

    /**
     * Delete agent graph
     * Delete an existing agent graph and all of its edges.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param graphKey  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteAgentGraphWithHttpInfo(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String graphKey) throws ApiException {
        okhttp3.Call localVarCall = deleteAgentGraphValidateBeforeCall(ldAPIVersion, projectKey, graphKey, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete agent graph (asynchronously)
     * Delete an existing agent graph and all of its edges.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param graphKey  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAgentGraphAsync(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String graphKey, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteAgentGraphValidateBeforeCall(ldAPIVersion, projectKey, graphKey, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteModelConfig
     * @param projectKey  (required)
     * @param modelConfigKey  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteModelConfigCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String modelConfigKey, final ApiCallback _callback) throws ApiException {
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
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "modelConfigKey" + "}", localVarApiClient.escapeString(modelConfigKey.toString()));

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
    private okhttp3.Call deleteModelConfigValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String modelConfigKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling deleteModelConfig(Async)");
        }

        // verify the required parameter 'modelConfigKey' is set
        if (modelConfigKey == null) {
            throw new ApiException("Missing the required parameter 'modelConfigKey' when calling deleteModelConfig(Async)");
        }

        return deleteModelConfigCall(projectKey, modelConfigKey, _callback);

    }

    /**
     * Delete an AI model config
     * Delete an AI model config.
     * @param projectKey  (required)
     * @param modelConfigKey  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public void deleteModelConfig(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String modelConfigKey) throws ApiException {
        deleteModelConfigWithHttpInfo(projectKey, modelConfigKey);
    }

    /**
     * Delete an AI model config
     * Delete an AI model config.
     * @param projectKey  (required)
     * @param modelConfigKey  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteModelConfigWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String modelConfigKey) throws ApiException {
        okhttp3.Call localVarCall = deleteModelConfigValidateBeforeCall(projectKey, modelConfigKey, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete an AI model config (asynchronously)
     * Delete an AI model config.
     * @param projectKey  (required)
     * @param modelConfigKey  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteModelConfigAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String modelConfigKey, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteModelConfigValidateBeforeCall(projectKey, modelConfigKey, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteRestrictedModels
     * @param projectKey  (required)
     * @param restrictedModelsRequest List of AI model keys to remove from the restricted list (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteRestrictedModelsCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull RestrictedModelsRequest restrictedModelsRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = restrictedModelsRequest;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/ai-configs/model-configs/restricted"
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
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteRestrictedModelsValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull RestrictedModelsRequest restrictedModelsRequest, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling deleteRestrictedModels(Async)");
        }

        // verify the required parameter 'restrictedModelsRequest' is set
        if (restrictedModelsRequest == null) {
            throw new ApiException("Missing the required parameter 'restrictedModelsRequest' when calling deleteRestrictedModels(Async)");
        }

        return deleteRestrictedModelsCall(projectKey, restrictedModelsRequest, _callback);

    }

    /**
     * Remove AI models from the restricted list
     * Remove AI models, by key, from the restricted list.
     * @param projectKey  (required)
     * @param restrictedModelsRequest List of AI model keys to remove from the restricted list (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public void deleteRestrictedModels(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull RestrictedModelsRequest restrictedModelsRequest) throws ApiException {
        deleteRestrictedModelsWithHttpInfo(projectKey, restrictedModelsRequest);
    }

    /**
     * Remove AI models from the restricted list
     * Remove AI models, by key, from the restricted list.
     * @param projectKey  (required)
     * @param restrictedModelsRequest List of AI model keys to remove from the restricted list (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteRestrictedModelsWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull RestrictedModelsRequest restrictedModelsRequest) throws ApiException {
        okhttp3.Call localVarCall = deleteRestrictedModelsValidateBeforeCall(projectKey, restrictedModelsRequest, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Remove AI models from the restricted list (asynchronously)
     * Remove AI models, by key, from the restricted list.
     * @param projectKey  (required)
     * @param restrictedModelsRequest List of AI model keys to remove from the restricted list (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteRestrictedModelsAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull RestrictedModelsRequest restrictedModelsRequest, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteRestrictedModelsValidateBeforeCall(projectKey, restrictedModelsRequest, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAIConfig
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config found </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAIConfigCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, final ApiCallback _callback) throws ApiException {
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
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "configKey" + "}", localVarApiClient.escapeString(configKey.toString()));

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
    private okhttp3.Call getAIConfigValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getAIConfig(Async)");
        }

        // verify the required parameter 'configKey' is set
        if (configKey == null) {
            throw new ApiException("Missing the required parameter 'configKey' when calling getAIConfig(Async)");
        }

        return getAIConfigCall(projectKey, configKey, _callback);

    }

    /**
     * Get AI Config
     * Retrieve a specific AI Config by its key.
     * @param projectKey  (required)
     * @param configKey  (required)
     * @return AIConfig
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config found </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AIConfig getAIConfig(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey) throws ApiException {
        ApiResponse<AIConfig> localVarResp = getAIConfigWithHttpInfo(projectKey, configKey);
        return localVarResp.getData();
    }

    /**
     * Get AI Config
     * Retrieve a specific AI Config by its key.
     * @param projectKey  (required)
     * @param configKey  (required)
     * @return ApiResponse&lt;AIConfig&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config found </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AIConfig> getAIConfigWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey) throws ApiException {
        okhttp3.Call localVarCall = getAIConfigValidateBeforeCall(projectKey, configKey, null);
        Type localVarReturnType = new TypeToken<AIConfig>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get AI Config (asynchronously)
     * Retrieve a specific AI Config by its key.
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config found </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAIConfigAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, final ApiCallback<AIConfig> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAIConfigValidateBeforeCall(projectKey, configKey, _callback);
        Type localVarReturnType = new TypeToken<AIConfig>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAIConfigMetrics
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param from The starting time, as milliseconds since epoch (inclusive). (required)
     * @param to The ending time, as milliseconds since epoch (exclusive). May not be more than 100 days after &#x60;from&#x60;. (required)
     * @param env An environment key. Only metrics from this environment will be included. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metrics computed </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAIConfigMetricsCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull Integer from, @javax.annotation.Nonnull Integer to, @javax.annotation.Nonnull String env, final ApiCallback _callback) throws ApiException {
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
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "configKey" + "}", localVarApiClient.escapeString(configKey.toString()));

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
    private okhttp3.Call getAIConfigMetricsValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull Integer from, @javax.annotation.Nonnull Integer to, @javax.annotation.Nonnull String env, final ApiCallback _callback) throws ApiException {
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

        return getAIConfigMetricsCall(projectKey, configKey, from, to, env, _callback);

    }

    /**
     * Get AI Config metrics
     * Retrieve usage metrics for an AI Config by config key.
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param from The starting time, as milliseconds since epoch (inclusive). (required)
     * @param to The ending time, as milliseconds since epoch (exclusive). May not be more than 100 days after &#x60;from&#x60;. (required)
     * @param env An environment key. Only metrics from this environment will be included. (required)
     * @return Metrics
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metrics computed </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public Metrics getAIConfigMetrics(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull Integer from, @javax.annotation.Nonnull Integer to, @javax.annotation.Nonnull String env) throws ApiException {
        ApiResponse<Metrics> localVarResp = getAIConfigMetricsWithHttpInfo(projectKey, configKey, from, to, env);
        return localVarResp.getData();
    }

    /**
     * Get AI Config metrics
     * Retrieve usage metrics for an AI Config by config key.
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param from The starting time, as milliseconds since epoch (inclusive). (required)
     * @param to The ending time, as milliseconds since epoch (exclusive). May not be more than 100 days after &#x60;from&#x60;. (required)
     * @param env An environment key. Only metrics from this environment will be included. (required)
     * @return ApiResponse&lt;Metrics&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metrics computed </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Metrics> getAIConfigMetricsWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull Integer from, @javax.annotation.Nonnull Integer to, @javax.annotation.Nonnull String env) throws ApiException {
        okhttp3.Call localVarCall = getAIConfigMetricsValidateBeforeCall(projectKey, configKey, from, to, env, null);
        Type localVarReturnType = new TypeToken<Metrics>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get AI Config metrics (asynchronously)
     * Retrieve usage metrics for an AI Config by config key.
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param from The starting time, as milliseconds since epoch (inclusive). (required)
     * @param to The ending time, as milliseconds since epoch (exclusive). May not be more than 100 days after &#x60;from&#x60;. (required)
     * @param env An environment key. Only metrics from this environment will be included. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metrics computed </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAIConfigMetricsAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull Integer from, @javax.annotation.Nonnull Integer to, @javax.annotation.Nonnull String env, final ApiCallback<Metrics> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAIConfigMetricsValidateBeforeCall(projectKey, configKey, from, to, env, _callback);
        Type localVarReturnType = new TypeToken<Metrics>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAIConfigMetricsByVariation
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param from The starting time, as milliseconds since epoch (inclusive). (required)
     * @param to The ending time, as milliseconds since epoch (exclusive). May not be more than 100 days after &#x60;from&#x60;. (required)
     * @param env An environment key. Only metrics from this environment will be included. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metrics computed </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAIConfigMetricsByVariationCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull Integer from, @javax.annotation.Nonnull Integer to, @javax.annotation.Nonnull String env, final ApiCallback _callback) throws ApiException {
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
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "configKey" + "}", localVarApiClient.escapeString(configKey.toString()));

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
    private okhttp3.Call getAIConfigMetricsByVariationValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull Integer from, @javax.annotation.Nonnull Integer to, @javax.annotation.Nonnull String env, final ApiCallback _callback) throws ApiException {
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

        return getAIConfigMetricsByVariationCall(projectKey, configKey, from, to, env, _callback);

    }

    /**
     * Get AI Config metrics by variation
     * Retrieve usage metrics for an AI Config by config key, with results split by variation.
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param from The starting time, as milliseconds since epoch (inclusive). (required)
     * @param to The ending time, as milliseconds since epoch (exclusive). May not be more than 100 days after &#x60;from&#x60;. (required)
     * @param env An environment key. Only metrics from this environment will be included. (required)
     * @return List&lt;MetricByVariation&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metrics computed </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public List<MetricByVariation> getAIConfigMetricsByVariation(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull Integer from, @javax.annotation.Nonnull Integer to, @javax.annotation.Nonnull String env) throws ApiException {
        ApiResponse<List<MetricByVariation>> localVarResp = getAIConfigMetricsByVariationWithHttpInfo(projectKey, configKey, from, to, env);
        return localVarResp.getData();
    }

    /**
     * Get AI Config metrics by variation
     * Retrieve usage metrics for an AI Config by config key, with results split by variation.
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param from The starting time, as milliseconds since epoch (inclusive). (required)
     * @param to The ending time, as milliseconds since epoch (exclusive). May not be more than 100 days after &#x60;from&#x60;. (required)
     * @param env An environment key. Only metrics from this environment will be included. (required)
     * @return ApiResponse&lt;List&lt;MetricByVariation&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metrics computed </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<List<MetricByVariation>> getAIConfigMetricsByVariationWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull Integer from, @javax.annotation.Nonnull Integer to, @javax.annotation.Nonnull String env) throws ApiException {
        okhttp3.Call localVarCall = getAIConfigMetricsByVariationValidateBeforeCall(projectKey, configKey, from, to, env, null);
        Type localVarReturnType = new TypeToken<List<MetricByVariation>>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get AI Config metrics by variation (asynchronously)
     * Retrieve usage metrics for an AI Config by config key, with results split by variation.
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param from The starting time, as milliseconds since epoch (inclusive). (required)
     * @param to The ending time, as milliseconds since epoch (exclusive). May not be more than 100 days after &#x60;from&#x60;. (required)
     * @param env An environment key. Only metrics from this environment will be included. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Metrics computed </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAIConfigMetricsByVariationAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull Integer from, @javax.annotation.Nonnull Integer to, @javax.annotation.Nonnull String env, final ApiCallback<List<MetricByVariation>> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAIConfigMetricsByVariationValidateBeforeCall(projectKey, configKey, from, to, env, _callback);
        Type localVarReturnType = new TypeToken<List<MetricByVariation>>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAIConfigTargeting
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAIConfigTargetingCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/ai-configs/{configKey}/targeting"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "configKey" + "}", localVarApiClient.escapeString(configKey.toString()));

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
    private okhttp3.Call getAIConfigTargetingValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getAIConfigTargeting(Async)");
        }

        // verify the required parameter 'configKey' is set
        if (configKey == null) {
            throw new ApiException("Missing the required parameter 'configKey' when calling getAIConfigTargeting(Async)");
        }

        return getAIConfigTargetingCall(projectKey, configKey, _callback);

    }

    /**
     * Show an AI Config&#39;s targeting
     * Retrieves a specific AI Config&#39;s targeting by its key
     * @param projectKey  (required)
     * @param configKey  (required)
     * @return AIConfigTargeting
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AIConfigTargeting getAIConfigTargeting(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey) throws ApiException {
        ApiResponse<AIConfigTargeting> localVarResp = getAIConfigTargetingWithHttpInfo(projectKey, configKey);
        return localVarResp.getData();
    }

    /**
     * Show an AI Config&#39;s targeting
     * Retrieves a specific AI Config&#39;s targeting by its key
     * @param projectKey  (required)
     * @param configKey  (required)
     * @return ApiResponse&lt;AIConfigTargeting&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AIConfigTargeting> getAIConfigTargetingWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey) throws ApiException {
        okhttp3.Call localVarCall = getAIConfigTargetingValidateBeforeCall(projectKey, configKey, null);
        Type localVarReturnType = new TypeToken<AIConfigTargeting>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Show an AI Config&#39;s targeting (asynchronously)
     * Retrieves a specific AI Config&#39;s targeting by its key
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAIConfigTargetingAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, final ApiCallback<AIConfigTargeting> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAIConfigTargetingValidateBeforeCall(projectKey, configKey, _callback);
        Type localVarReturnType = new TypeToken<AIConfigTargeting>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAIConfigVariation
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
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
    public okhttp3.Call getAIConfigVariationCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull String variationKey, final ApiCallback _callback) throws ApiException {
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
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "configKey" + "}", localVarApiClient.escapeString(configKey.toString()))
            .replace("{" + "variationKey" + "}", localVarApiClient.escapeString(variationKey.toString()));

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
    private okhttp3.Call getAIConfigVariationValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull String variationKey, final ApiCallback _callback) throws ApiException {
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

        return getAIConfigVariationCall(projectKey, configKey, variationKey, _callback);

    }

    /**
     * Get AI Config variation
     * Get an AI Config variation by key. The response includes all variation versions for the given variation key.
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
     * @return AIConfigVariationsResponse
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
    public AIConfigVariationsResponse getAIConfigVariation(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull String variationKey) throws ApiException {
        ApiResponse<AIConfigVariationsResponse> localVarResp = getAIConfigVariationWithHttpInfo(projectKey, configKey, variationKey);
        return localVarResp.getData();
    }

    /**
     * Get AI Config variation
     * Get an AI Config variation by key. The response includes all variation versions for the given variation key.
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
     * @return ApiResponse&lt;AIConfigVariationsResponse&gt;
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
    public ApiResponse<AIConfigVariationsResponse> getAIConfigVariationWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull String variationKey) throws ApiException {
        okhttp3.Call localVarCall = getAIConfigVariationValidateBeforeCall(projectKey, configKey, variationKey, null);
        Type localVarReturnType = new TypeToken<AIConfigVariationsResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get AI Config variation (asynchronously)
     * Get an AI Config variation by key. The response includes all variation versions for the given variation key.
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
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
    public okhttp3.Call getAIConfigVariationAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull String variationKey, final ApiCallback<AIConfigVariationsResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAIConfigVariationValidateBeforeCall(projectKey, configKey, variationKey, _callback);
        Type localVarReturnType = new TypeToken<AIConfigVariationsResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAIConfigs
     * @param projectKey  (required)
     * @param sort A sort to apply to the list of AI Configs. (optional)
     * @param limit The number of AI Configs to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param filter A filter to apply to the list of AI Configs. (optional)
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
    public okhttp3.Call getAIConfigsCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String filter, final ApiCallback _callback) throws ApiException {
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
    private okhttp3.Call getAIConfigsValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String filter, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getAIConfigs(Async)");
        }

        return getAIConfigsCall(projectKey, sort, limit, offset, filter, _callback);

    }

    /**
     * List AI Configs
     * Get a list of all AI Configs in the given project.
     * @param projectKey  (required)
     * @param sort A sort to apply to the list of AI Configs. (optional)
     * @param limit The number of AI Configs to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param filter A filter to apply to the list of AI Configs. (optional)
     * @return AIConfigs
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
    public AIConfigs getAIConfigs(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String filter) throws ApiException {
        ApiResponse<AIConfigs> localVarResp = getAIConfigsWithHttpInfo(projectKey, sort, limit, offset, filter);
        return localVarResp.getData();
    }

    /**
     * List AI Configs
     * Get a list of all AI Configs in the given project.
     * @param projectKey  (required)
     * @param sort A sort to apply to the list of AI Configs. (optional)
     * @param limit The number of AI Configs to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param filter A filter to apply to the list of AI Configs. (optional)
     * @return ApiResponse&lt;AIConfigs&gt;
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
    public ApiResponse<AIConfigs> getAIConfigsWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String filter) throws ApiException {
        okhttp3.Call localVarCall = getAIConfigsValidateBeforeCall(projectKey, sort, limit, offset, filter, null);
        Type localVarReturnType = new TypeToken<AIConfigs>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List AI Configs (asynchronously)
     * Get a list of all AI Configs in the given project.
     * @param projectKey  (required)
     * @param sort A sort to apply to the list of AI Configs. (optional)
     * @param limit The number of AI Configs to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param filter A filter to apply to the list of AI Configs. (optional)
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
    public okhttp3.Call getAIConfigsAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String filter, final ApiCallback<AIConfigs> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAIConfigsValidateBeforeCall(projectKey, sort, limit, offset, filter, _callback);
        Type localVarReturnType = new TypeToken<AIConfigs>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAITool
     * @param projectKey  (required)
     * @param toolKey  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI tool found </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAIToolCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String toolKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/ai-tools/{toolKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "toolKey" + "}", localVarApiClient.escapeString(toolKey.toString()));

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
    private okhttp3.Call getAIToolValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String toolKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getAITool(Async)");
        }

        // verify the required parameter 'toolKey' is set
        if (toolKey == null) {
            throw new ApiException("Missing the required parameter 'toolKey' when calling getAITool(Async)");
        }

        return getAIToolCall(projectKey, toolKey, _callback);

    }

    /**
     * Get AI tool
     * Retrieve a specific AI tool by its key.
     * @param projectKey  (required)
     * @param toolKey  (required)
     * @return AITool
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI tool found </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AITool getAITool(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String toolKey) throws ApiException {
        ApiResponse<AITool> localVarResp = getAIToolWithHttpInfo(projectKey, toolKey);
        return localVarResp.getData();
    }

    /**
     * Get AI tool
     * Retrieve a specific AI tool by its key.
     * @param projectKey  (required)
     * @param toolKey  (required)
     * @return ApiResponse&lt;AITool&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI tool found </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AITool> getAIToolWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String toolKey) throws ApiException {
        okhttp3.Call localVarCall = getAIToolValidateBeforeCall(projectKey, toolKey, null);
        Type localVarReturnType = new TypeToken<AITool>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get AI tool (asynchronously)
     * Retrieve a specific AI tool by its key.
     * @param projectKey  (required)
     * @param toolKey  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI tool found </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAIToolAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String toolKey, final ApiCallback<AITool> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAIToolValidateBeforeCall(projectKey, toolKey, _callback);
        Type localVarReturnType = new TypeToken<AITool>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentGraph
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param graphKey  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Agent graph found </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentGraphCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String graphKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/agent-graphs/{graphKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "graphKey" + "}", localVarApiClient.escapeString(graphKey.toString()));

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
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getAgentGraphValidateBeforeCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String graphKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling getAgentGraph(Async)");
        }

        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getAgentGraph(Async)");
        }

        // verify the required parameter 'graphKey' is set
        if (graphKey == null) {
            throw new ApiException("Missing the required parameter 'graphKey' when calling getAgentGraph(Async)");
        }

        return getAgentGraphCall(ldAPIVersion, projectKey, graphKey, _callback);

    }

    /**
     * Get agent graph
     * Retrieve a specific agent graph by its key, including its edges.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param graphKey  (required)
     * @return AgentGraph
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Agent graph found </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AgentGraph getAgentGraph(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String graphKey) throws ApiException {
        ApiResponse<AgentGraph> localVarResp = getAgentGraphWithHttpInfo(ldAPIVersion, projectKey, graphKey);
        return localVarResp.getData();
    }

    /**
     * Get agent graph
     * Retrieve a specific agent graph by its key, including its edges.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param graphKey  (required)
     * @return ApiResponse&lt;AgentGraph&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Agent graph found </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AgentGraph> getAgentGraphWithHttpInfo(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String graphKey) throws ApiException {
        okhttp3.Call localVarCall = getAgentGraphValidateBeforeCall(ldAPIVersion, projectKey, graphKey, null);
        Type localVarReturnType = new TypeToken<AgentGraph>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get agent graph (asynchronously)
     * Retrieve a specific agent graph by its key, including its edges.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param graphKey  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Agent graph found </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentGraphAsync(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String graphKey, final ApiCallback<AgentGraph> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentGraphValidateBeforeCall(ldAPIVersion, projectKey, graphKey, _callback);
        Type localVarReturnType = new TypeToken<AgentGraph>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getModelConfig
     * @param projectKey  (required)
     * @param modelConfigKey  (required)
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
    public okhttp3.Call getModelConfigCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String modelConfigKey, final ApiCallback _callback) throws ApiException {
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
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "modelConfigKey" + "}", localVarApiClient.escapeString(modelConfigKey.toString()));

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
    private okhttp3.Call getModelConfigValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String modelConfigKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getModelConfig(Async)");
        }

        // verify the required parameter 'modelConfigKey' is set
        if (modelConfigKey == null) {
            throw new ApiException("Missing the required parameter 'modelConfigKey' when calling getModelConfig(Async)");
        }

        return getModelConfigCall(projectKey, modelConfigKey, _callback);

    }

    /**
     * Get AI model config
     * Get an AI model config by key.
     * @param projectKey  (required)
     * @param modelConfigKey  (required)
     * @return ModelConfig
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
    public ModelConfig getModelConfig(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String modelConfigKey) throws ApiException {
        ApiResponse<ModelConfig> localVarResp = getModelConfigWithHttpInfo(projectKey, modelConfigKey);
        return localVarResp.getData();
    }

    /**
     * Get AI model config
     * Get an AI model config by key.
     * @param projectKey  (required)
     * @param modelConfigKey  (required)
     * @return ApiResponse&lt;ModelConfig&gt;
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
    public ApiResponse<ModelConfig> getModelConfigWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String modelConfigKey) throws ApiException {
        okhttp3.Call localVarCall = getModelConfigValidateBeforeCall(projectKey, modelConfigKey, null);
        Type localVarReturnType = new TypeToken<ModelConfig>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get AI model config (asynchronously)
     * Get an AI model config by key.
     * @param projectKey  (required)
     * @param modelConfigKey  (required)
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
    public okhttp3.Call getModelConfigAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String modelConfigKey, final ApiCallback<ModelConfig> _callback) throws ApiException {

        okhttp3.Call localVarCall = getModelConfigValidateBeforeCall(projectKey, modelConfigKey, _callback);
        Type localVarReturnType = new TypeToken<ModelConfig>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listAIToolVersions
     * @param projectKey  (required)
     * @param toolKey  (required)
     * @param sort A sort to apply to the list of AI Configs. (optional)
     * @param limit The number of AI Configs to return. (optional)
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
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listAIToolVersionsCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String toolKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/ai-tools/{toolKey}/versions"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "toolKey" + "}", localVarApiClient.escapeString(toolKey.toString()));

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
    private okhttp3.Call listAIToolVersionsValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String toolKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling listAIToolVersions(Async)");
        }

        // verify the required parameter 'toolKey' is set
        if (toolKey == null) {
            throw new ApiException("Missing the required parameter 'toolKey' when calling listAIToolVersions(Async)");
        }

        return listAIToolVersionsCall(projectKey, toolKey, sort, limit, offset, _callback);

    }

    /**
     * List AI tool versions
     * Get a list of all versions of an AI tool in the given project.
     * @param projectKey  (required)
     * @param toolKey  (required)
     * @param sort A sort to apply to the list of AI Configs. (optional)
     * @param limit The number of AI Configs to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @return AITools
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AITools listAIToolVersions(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String toolKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset) throws ApiException {
        ApiResponse<AITools> localVarResp = listAIToolVersionsWithHttpInfo(projectKey, toolKey, sort, limit, offset);
        return localVarResp.getData();
    }

    /**
     * List AI tool versions
     * Get a list of all versions of an AI tool in the given project.
     * @param projectKey  (required)
     * @param toolKey  (required)
     * @param sort A sort to apply to the list of AI Configs. (optional)
     * @param limit The number of AI Configs to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @return ApiResponse&lt;AITools&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AITools> listAIToolVersionsWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String toolKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset) throws ApiException {
        okhttp3.Call localVarCall = listAIToolVersionsValidateBeforeCall(projectKey, toolKey, sort, limit, offset, null);
        Type localVarReturnType = new TypeToken<AITools>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List AI tool versions (asynchronously)
     * Get a list of all versions of an AI tool in the given project.
     * @param projectKey  (required)
     * @param toolKey  (required)
     * @param sort A sort to apply to the list of AI Configs. (optional)
     * @param limit The number of AI Configs to return. (optional)
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
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listAIToolVersionsAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String toolKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, final ApiCallback<AITools> _callback) throws ApiException {

        okhttp3.Call localVarCall = listAIToolVersionsValidateBeforeCall(projectKey, toolKey, sort, limit, offset, _callback);
        Type localVarReturnType = new TypeToken<AITools>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listAITools
     * @param projectKey  (required)
     * @param sort A sort to apply to the list of AI Configs. (optional)
     * @param limit The number of AI Configs to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param filter A filter to apply to the list of AI Configs. (optional)
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
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listAIToolsCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String filter, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/ai-tools"
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
    private okhttp3.Call listAIToolsValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String filter, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling listAITools(Async)");
        }

        return listAIToolsCall(projectKey, sort, limit, offset, filter, _callback);

    }

    /**
     * List AI tools
     * Get a list of all AI tools in the given project.
     * @param projectKey  (required)
     * @param sort A sort to apply to the list of AI Configs. (optional)
     * @param limit The number of AI Configs to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param filter A filter to apply to the list of AI Configs. (optional)
     * @return AITools
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AITools listAITools(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String filter) throws ApiException {
        ApiResponse<AITools> localVarResp = listAIToolsWithHttpInfo(projectKey, sort, limit, offset, filter);
        return localVarResp.getData();
    }

    /**
     * List AI tools
     * Get a list of all AI tools in the given project.
     * @param projectKey  (required)
     * @param sort A sort to apply to the list of AI Configs. (optional)
     * @param limit The number of AI Configs to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param filter A filter to apply to the list of AI Configs. (optional)
     * @return ApiResponse&lt;AITools&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AITools> listAIToolsWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String filter) throws ApiException {
        okhttp3.Call localVarCall = listAIToolsValidateBeforeCall(projectKey, sort, limit, offset, filter, null);
        Type localVarReturnType = new TypeToken<AITools>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List AI tools (asynchronously)
     * Get a list of all AI tools in the given project.
     * @param projectKey  (required)
     * @param sort A sort to apply to the list of AI Configs. (optional)
     * @param limit The number of AI Configs to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param filter A filter to apply to the list of AI Configs. (optional)
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
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listAIToolsAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable String sort, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, @javax.annotation.Nullable String filter, final ApiCallback<AITools> _callback) throws ApiException {

        okhttp3.Call localVarCall = listAIToolsValidateBeforeCall(projectKey, sort, limit, offset, filter, _callback);
        Type localVarReturnType = new TypeToken<AITools>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listAgentGraphs
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param limit The number of AI Configs to return. (optional)
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
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listAgentGraphsCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/agent-graphs"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()));

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
    private okhttp3.Call listAgentGraphsValidateBeforeCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling listAgentGraphs(Async)");
        }

        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling listAgentGraphs(Async)");
        }

        return listAgentGraphsCall(ldAPIVersion, projectKey, limit, offset, _callback);

    }

    /**
     * List agent graphs
     * Get a list of all agent graphs in the given project. Returns metadata only, without edge data.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param limit The number of AI Configs to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @return AgentGraphs
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AgentGraphs listAgentGraphs(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset) throws ApiException {
        ApiResponse<AgentGraphs> localVarResp = listAgentGraphsWithHttpInfo(ldAPIVersion, projectKey, limit, offset);
        return localVarResp.getData();
    }

    /**
     * List agent graphs
     * Get a list of all agent graphs in the given project. Returns metadata only, without edge data.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param limit The number of AI Configs to return. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @return ApiResponse&lt;AgentGraphs&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successful response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AgentGraphs> listAgentGraphsWithHttpInfo(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset) throws ApiException {
        okhttp3.Call localVarCall = listAgentGraphsValidateBeforeCall(ldAPIVersion, projectKey, limit, offset, null);
        Type localVarReturnType = new TypeToken<AgentGraphs>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List agent graphs (asynchronously)
     * Get a list of all agent graphs in the given project. Returns metadata only, without edge data.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param limit The number of AI Configs to return. (optional)
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
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listAgentGraphsAsync(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable Integer offset, final ApiCallback<AgentGraphs> _callback) throws ApiException {

        okhttp3.Call localVarCall = listAgentGraphsValidateBeforeCall(ldAPIVersion, projectKey, limit, offset, _callback);
        Type localVarReturnType = new TypeToken<AgentGraphs>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listModelConfigs
     * @param projectKey  (required)
     * @param restricted Whether to return only restricted models (optional)
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
    public okhttp3.Call listModelConfigsCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable Boolean restricted, final ApiCallback _callback) throws ApiException {
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
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (restricted != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("restricted", restricted));
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
    private okhttp3.Call listModelConfigsValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable Boolean restricted, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling listModelConfigs(Async)");
        }

        return listModelConfigsCall(projectKey, restricted, _callback);

    }

    /**
     * List AI model configs
     * Get all AI model configs for a project.
     * @param projectKey  (required)
     * @param restricted Whether to return only restricted models (optional)
     * @return List&lt;ModelConfig&gt;
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
    public List<ModelConfig> listModelConfigs(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable Boolean restricted) throws ApiException {
        ApiResponse<List<ModelConfig>> localVarResp = listModelConfigsWithHttpInfo(projectKey, restricted);
        return localVarResp.getData();
    }

    /**
     * List AI model configs
     * Get all AI model configs for a project.
     * @param projectKey  (required)
     * @param restricted Whether to return only restricted models (optional)
     * @return ApiResponse&lt;List&lt;ModelConfig&gt;&gt;
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
    public ApiResponse<List<ModelConfig>> listModelConfigsWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable Boolean restricted) throws ApiException {
        okhttp3.Call localVarCall = listModelConfigsValidateBeforeCall(projectKey, restricted, null);
        Type localVarReturnType = new TypeToken<List<ModelConfig>>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List AI model configs (asynchronously)
     * Get all AI model configs for a project.
     * @param projectKey  (required)
     * @param restricted Whether to return only restricted models (optional)
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
    public okhttp3.Call listModelConfigsAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nullable Boolean restricted, final ApiCallback<List<ModelConfig>> _callback) throws ApiException {

        okhttp3.Call localVarCall = listModelConfigsValidateBeforeCall(projectKey, restricted, _callback);
        Type localVarReturnType = new TypeToken<List<ModelConfig>>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for patchAIConfig
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param aiConfigPatch AI Config object to update (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchAIConfigCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nullable AIConfigPatch aiConfigPatch, final ApiCallback _callback) throws ApiException {
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
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "configKey" + "}", localVarApiClient.escapeString(configKey.toString()));

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
    private okhttp3.Call patchAIConfigValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nullable AIConfigPatch aiConfigPatch, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling patchAIConfig(Async)");
        }

        // verify the required parameter 'configKey' is set
        if (configKey == null) {
            throw new ApiException("Missing the required parameter 'configKey' when calling patchAIConfig(Async)");
        }

        return patchAIConfigCall(projectKey, configKey, aiConfigPatch, _callback);

    }

    /**
     * Update AI Config
     * Edit an existing AI Config.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  Here&#39;s an example:   &#x60;&#x60;&#x60;     {       \&quot;description\&quot;: \&quot;Example updated description\&quot;,       \&quot;tags\&quot;: [\&quot;new-tag\&quot;]     }   &#x60;&#x60;&#x60; 
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param aiConfigPatch AI Config object to update (optional)
     * @return AIConfig
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AIConfig patchAIConfig(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nullable AIConfigPatch aiConfigPatch) throws ApiException {
        ApiResponse<AIConfig> localVarResp = patchAIConfigWithHttpInfo(projectKey, configKey, aiConfigPatch);
        return localVarResp.getData();
    }

    /**
     * Update AI Config
     * Edit an existing AI Config.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  Here&#39;s an example:   &#x60;&#x60;&#x60;     {       \&quot;description\&quot;: \&quot;Example updated description\&quot;,       \&quot;tags\&quot;: [\&quot;new-tag\&quot;]     }   &#x60;&#x60;&#x60; 
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param aiConfigPatch AI Config object to update (optional)
     * @return ApiResponse&lt;AIConfig&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AIConfig> patchAIConfigWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nullable AIConfigPatch aiConfigPatch) throws ApiException {
        okhttp3.Call localVarCall = patchAIConfigValidateBeforeCall(projectKey, configKey, aiConfigPatch, null);
        Type localVarReturnType = new TypeToken<AIConfig>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update AI Config (asynchronously)
     * Edit an existing AI Config.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  Here&#39;s an example:   &#x60;&#x60;&#x60;     {       \&quot;description\&quot;: \&quot;Example updated description\&quot;,       \&quot;tags\&quot;: [\&quot;new-tag\&quot;]     }   &#x60;&#x60;&#x60; 
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param aiConfigPatch AI Config object to update (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchAIConfigAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nullable AIConfigPatch aiConfigPatch, final ApiCallback<AIConfig> _callback) throws ApiException {

        okhttp3.Call localVarCall = patchAIConfigValidateBeforeCall(projectKey, configKey, aiConfigPatch, _callback);
        Type localVarReturnType = new TypeToken<AIConfig>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for patchAIConfigTargeting
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param aiConfigTargetingPatch AI Config targeting semantic patch instructions (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config targeting updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchAIConfigTargetingCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nullable AIConfigTargetingPatch aiConfigTargetingPatch, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = aiConfigTargetingPatch;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/ai-configs/{configKey}/targeting"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "configKey" + "}", localVarApiClient.escapeString(configKey.toString()));

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
    private okhttp3.Call patchAIConfigTargetingValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nullable AIConfigTargetingPatch aiConfigTargetingPatch, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling patchAIConfigTargeting(Async)");
        }

        // verify the required parameter 'configKey' is set
        if (configKey == null) {
            throw new ApiException("Missing the required parameter 'configKey' when calling patchAIConfigTargeting(Async)");
        }

        return patchAIConfigTargetingCall(projectKey, configKey, aiConfigTargetingPatch, _callback);

    }

    /**
     * Update AI Config targeting
     * Perform a partial update to an AI Config&#39;s targeting. The request body must be a valid semantic patch.  ### Using semantic patches on an AI Config  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](https://launchdarkly.com/docs/api#updates-using-semantic-patch).  The body of a semantic patch request for updating an AI Config&#39;s targeting takes the following properties:  * &#x60;comment&#x60; (string): (Optional) A description of the update. * &#x60;environmentKey&#x60; (string): The key of the LaunchDarkly environment. * &#x60;instructions&#x60; (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a &#x60;kind&#x60; property that indicates the instruction. If the action requires parameters, you must include those parameters as additional fields in the object. The body of a single semantic patch can contain many different instructions.  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating AI Configs.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;working with targeting and variations&lt;/strong&gt; for AI Configs&lt;/summary&gt;  #### addClauses  Adds the given clauses to the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the AI Config. - &#x60;clauses&#x60;: Array of clause objects, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addClauses\&quot;,     \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,     \&quot;clauses\&quot;: [{       \&quot;contextKind\&quot;: \&quot;user\&quot;,       \&quot;attribute\&quot;: \&quot;country\&quot;,       \&quot;op\&quot;: \&quot;in\&quot;,       \&quot;negate\&quot;: false,       \&quot;values\&quot;: [\&quot;USA\&quot;, \&quot;Canada\&quot;]     }]   }] } &#x60;&#x60;&#x60;  #### addRule  Adds a new targeting rule to the AI Config. The rule may contain &#x60;clauses&#x60; and serve the variation that &#x60;variationId&#x60; indicates, or serve a percentage rollout that &#x60;rolloutWeights&#x60;, &#x60;rolloutBucketBy&#x60;, and &#x60;rolloutContextKind&#x60; indicate.  If you set &#x60;beforeRuleId&#x60;, this adds the new rule before the indicated rule. Otherwise, adds the new rule to the end of the list.  ##### Parameters  - &#x60;clauses&#x60;: Array of clause objects, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case. - &#x60;beforeRuleId&#x60;: (Optional) ID of a rule. - Either - &#x60;variationId&#x60;: ID of a variation.  or  - &#x60;rolloutWeights&#x60;: (Optional) Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example that uses a &#x60;variationId&#x60;:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addRule\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,   \&quot;clauses\&quot;: [{     \&quot;contextKind\&quot;: \&quot;organization\&quot;,     \&quot;attribute\&quot;: \&quot;located_in\&quot;,     \&quot;op\&quot;: \&quot;in\&quot;,     \&quot;negate\&quot;: false,     \&quot;values\&quot;: [\&quot;Sweden\&quot;, \&quot;Norway\&quot;]   }] }] } &#x60;&#x60;&#x60;  Here&#39;s an example that uses a percentage rollout:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addRule\&quot;,   \&quot;clauses\&quot;: [{     \&quot;contextKind\&quot;: \&quot;organization\&quot;,     \&quot;attribute\&quot;: \&quot;located_in\&quot;,     \&quot;op\&quot;: \&quot;in\&quot;,     \&quot;negate\&quot;: false,     \&quot;values\&quot;: [\&quot;Sweden\&quot;, \&quot;Norway\&quot;]   }],   \&quot;rolloutContextKind\&quot;: \&quot;organization\&quot;,   \&quot;rolloutWeights\&quot;: {     \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;: 15000, // serve 15% this variation     \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;: 85000  // serve 85% this variation   } }] } &#x60;&#x60;&#x60;  #### addTargets  Adds context keys to the individual context targets for the context kind that &#x60;contextKind&#x60; specifies and the variation that &#x60;variationId&#x60; specifies. Returns an error if this causes the AI Config to target the same context key in multiple variations.  ##### Parameters  - &#x60;values&#x60;: List of context keys. - &#x60;contextKind&#x60;: (Optional) Context kind to target, defaults to &#x60;user&#x60; - &#x60;variationId&#x60;: ID of a variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addTargets\&quot;,   \&quot;values\&quot;: [\&quot;context-key-123abc\&quot;, \&quot;context-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; }] } &#x60;&#x60;&#x60;  #### addValuesToClause  Adds &#x60;values&#x60; to the values of the clause that &#x60;ruleId&#x60; and &#x60;clauseId&#x60; indicate. Does not update the context kind, attribute, or operator.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the AI Config. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings, case sensitive.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addValuesToClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;,   \&quot;values\&quot;: [\&quot;beta_testers\&quot;] }] } &#x60;&#x60;&#x60;  #### clearTargets  Removes all individual targets from the variation that &#x60;variationId&#x60; specifies. This includes both user and non-user targets.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;clearTargets\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### removeClauses  Removes the clauses specified by &#x60;clauseIds&#x60; from the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule. - &#x60;clauseIds&#x60;: Array of IDs of clauses in the rule.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeClauses\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseIds\&quot;: [\&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;, \&quot;36a461dc-235e-4b08-97b9-73ce9365873e\&quot;] }] } &#x60;&#x60;&#x60;  #### removeRule  Removes the targeting rule specified by &#x60;ruleId&#x60;. Does nothing if the rule does not exist.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removeRule\&quot;, \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot; } ] } &#x60;&#x60;&#x60;  #### removeTargets  Removes context keys from the individual context targets for the context kind that &#x60;contextKind&#x60; specifies and the variation that &#x60;variationId&#x60; specifies. Does nothing if the flag does not target the context keys.  ##### Parameters  - &#x60;values&#x60;: List of context keys. - &#x60;contextKind&#x60;: (Optional) Context kind to target, defaults to &#x60;user&#x60; - &#x60;variationId&#x60;: ID of a variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeTargets\&quot;,   \&quot;values\&quot;: [\&quot;context-key-123abc\&quot;, \&quot;context-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; }] } &#x60;&#x60;&#x60;  #### removeValuesFromClause  Removes &#x60;values&#x60; from the values of the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60;. Does not update the context kind, attribute, or operator.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings, case sensitive.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeValuesFromClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;,   \&quot;values\&quot;: [\&quot;beta_testers\&quot;] }] } &#x60;&#x60;&#x60;  #### reorderRules  Rearranges the rules to match the order given in &#x60;ruleIds&#x60;. Returns an error if &#x60;ruleIds&#x60; does not match the current set of rules on the AI Config.  ##### Parameters  - &#x60;ruleIds&#x60;: Array of IDs of all rules.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;reorderRules\&quot;,   \&quot;ruleIds\&quot;: [\&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;, \&quot;63c238d1-835d-435e-8f21-c8d5e40b2a3d\&quot;] }] } &#x60;&#x60;&#x60;  #### replaceRules  Removes all targeting rules for the AI Config and replaces them with the list you provide.  ##### Parameters  - &#x60;rules&#x60;: A list of rules.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [   {     \&quot;kind\&quot;: \&quot;replaceRules\&quot;,     \&quot;rules\&quot;: [       {         \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,         \&quot;description\&quot;: \&quot;My new rule\&quot;,         \&quot;clauses\&quot;: [           {             \&quot;contextKind\&quot;: \&quot;user\&quot;,             \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,             \&quot;op\&quot;: \&quot;segmentMatch\&quot;,             \&quot;values\&quot;: [\&quot;test\&quot;]           }         ]       }     ]   } ] } &#x60;&#x60;&#x60;  #### replaceTargets  Removes all existing targeting and replaces it with the list of targets you provide.  ##### Parameters  - &#x60;targets&#x60;: A list of context targeting. Each item in the list includes an optional &#x60;contextKind&#x60; that defaults to &#x60;user&#x60;, a required &#x60;variationId&#x60;, and a required list of &#x60;values&#x60;.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [   {     \&quot;kind\&quot;: \&quot;replaceTargets\&quot;,     \&quot;targets\&quot;: [       {         \&quot;contextKind\&quot;: \&quot;user\&quot;,         \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,         \&quot;values\&quot;: [\&quot;user-key-123abc\&quot;]       },       {         \&quot;contextKind\&quot;: \&quot;device\&quot;,         \&quot;variationId\&quot;: \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;,         \&quot;values\&quot;: [\&quot;device-key-456def\&quot;]       }     ]   } ] } &#x60;&#x60;&#x60;  #### updateClause  Replaces the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60; with &#x60;clause&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;clause&#x60;: New &#x60;clause&#x60; object, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10c7462a-2062-45ba-a8bb-dfb3de0f8af5\&quot;,   \&quot;clause\&quot;: {     \&quot;contextKind\&quot;: \&quot;user\&quot;,     \&quot;attribute\&quot;: \&quot;country\&quot;,     \&quot;op\&quot;: \&quot;in\&quot;,     \&quot;negate\&quot;: false,     \&quot;values\&quot;: [\&quot;Mexico\&quot;, \&quot;Canada\&quot;]   } }] } &#x60;&#x60;&#x60;  #### updateDefaultVariation  Updates the default on or off variation of the AI Config.  ##### Parameters  - &#x60;onVariationValue&#x60;: (Optional) The value of the variation of the new on variation. - &#x60;offVariationValue&#x60;: (Optional) The value of the variation of the new off variation  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateDefaultVariation\&quot;, \&quot;OnVariationValue\&quot;: true, \&quot;OffVariationValue\&quot;: false } ] } &#x60;&#x60;&#x60;  #### updateFallthroughVariationOrRollout  Updates the default or \&quot;fallthrough\&quot; rule for the AI Config, which the AI Config serves when a context matches none of the targeting rules. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percentage rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation.  or  - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example that uses a &#x60;variationId&#x60;:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; }] } &#x60;&#x60;&#x60;  Here&#39;s an example that uses a percentage rollout:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,   \&quot;rolloutContextKind\&quot;: \&quot;user\&quot;,   \&quot;rolloutWeights\&quot;: {     \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;: 15000, // serve 15% this variation     \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;: 85000  // serve 85% this variation   } }] } &#x60;&#x60;&#x60;  #### updateOffVariation  Updates the default off variation to &#x60;variationId&#x60;. The AI Config serves the default off variation when the AI Config&#39;s targeting is **Off**.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateOffVariation\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### updateRuleDescription  Updates the description of the targeting rule.  ##### Parameters  - &#x60;description&#x60;: The new human-readable description for this rule. - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the AI Config.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleDescription\&quot;,   \&quot;description\&quot;: \&quot;New rule description\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot; }] } &#x60;&#x60;&#x60;  #### updateRuleTrackEvents  Updates whether or not LaunchDarkly tracks events for the AI Config associated with this rule.  ##### Parameters  - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the AI Config. - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleTrackEvents\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;trackEvents\&quot;: true }] } &#x60;&#x60;&#x60;  #### updateRuleVariationOrRollout  Updates what &#x60;ruleId&#x60; serves when its clauses evaluate to true. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percent rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule. - &#x60;variationId&#x60;: ID of a variation.  or  - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleVariationOrRollout\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; }] } &#x60;&#x60;&#x60;  #### updateTrackEvents  Updates whether or not LaunchDarkly tracks events for the AI Config, for all rules.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateTrackEvents\&quot;, \&quot;trackEvents\&quot;: true } ] } &#x60;&#x60;&#x60;  #### updateTrackEventsFallthrough  Updates whether or not LaunchDarkly tracks events for the AI Config, for the default rule.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateTrackEventsFallthrough\&quot;, \&quot;trackEvents\&quot;: true } ] } &#x60;&#x60;&#x60; &lt;/details&gt; 
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param aiConfigTargetingPatch AI Config targeting semantic patch instructions (optional)
     * @return AIConfigTargeting
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config targeting updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AIConfigTargeting patchAIConfigTargeting(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nullable AIConfigTargetingPatch aiConfigTargetingPatch) throws ApiException {
        ApiResponse<AIConfigTargeting> localVarResp = patchAIConfigTargetingWithHttpInfo(projectKey, configKey, aiConfigTargetingPatch);
        return localVarResp.getData();
    }

    /**
     * Update AI Config targeting
     * Perform a partial update to an AI Config&#39;s targeting. The request body must be a valid semantic patch.  ### Using semantic patches on an AI Config  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](https://launchdarkly.com/docs/api#updates-using-semantic-patch).  The body of a semantic patch request for updating an AI Config&#39;s targeting takes the following properties:  * &#x60;comment&#x60; (string): (Optional) A description of the update. * &#x60;environmentKey&#x60; (string): The key of the LaunchDarkly environment. * &#x60;instructions&#x60; (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a &#x60;kind&#x60; property that indicates the instruction. If the action requires parameters, you must include those parameters as additional fields in the object. The body of a single semantic patch can contain many different instructions.  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating AI Configs.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;working with targeting and variations&lt;/strong&gt; for AI Configs&lt;/summary&gt;  #### addClauses  Adds the given clauses to the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the AI Config. - &#x60;clauses&#x60;: Array of clause objects, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addClauses\&quot;,     \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,     \&quot;clauses\&quot;: [{       \&quot;contextKind\&quot;: \&quot;user\&quot;,       \&quot;attribute\&quot;: \&quot;country\&quot;,       \&quot;op\&quot;: \&quot;in\&quot;,       \&quot;negate\&quot;: false,       \&quot;values\&quot;: [\&quot;USA\&quot;, \&quot;Canada\&quot;]     }]   }] } &#x60;&#x60;&#x60;  #### addRule  Adds a new targeting rule to the AI Config. The rule may contain &#x60;clauses&#x60; and serve the variation that &#x60;variationId&#x60; indicates, or serve a percentage rollout that &#x60;rolloutWeights&#x60;, &#x60;rolloutBucketBy&#x60;, and &#x60;rolloutContextKind&#x60; indicate.  If you set &#x60;beforeRuleId&#x60;, this adds the new rule before the indicated rule. Otherwise, adds the new rule to the end of the list.  ##### Parameters  - &#x60;clauses&#x60;: Array of clause objects, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case. - &#x60;beforeRuleId&#x60;: (Optional) ID of a rule. - Either - &#x60;variationId&#x60;: ID of a variation.  or  - &#x60;rolloutWeights&#x60;: (Optional) Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example that uses a &#x60;variationId&#x60;:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addRule\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,   \&quot;clauses\&quot;: [{     \&quot;contextKind\&quot;: \&quot;organization\&quot;,     \&quot;attribute\&quot;: \&quot;located_in\&quot;,     \&quot;op\&quot;: \&quot;in\&quot;,     \&quot;negate\&quot;: false,     \&quot;values\&quot;: [\&quot;Sweden\&quot;, \&quot;Norway\&quot;]   }] }] } &#x60;&#x60;&#x60;  Here&#39;s an example that uses a percentage rollout:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addRule\&quot;,   \&quot;clauses\&quot;: [{     \&quot;contextKind\&quot;: \&quot;organization\&quot;,     \&quot;attribute\&quot;: \&quot;located_in\&quot;,     \&quot;op\&quot;: \&quot;in\&quot;,     \&quot;negate\&quot;: false,     \&quot;values\&quot;: [\&quot;Sweden\&quot;, \&quot;Norway\&quot;]   }],   \&quot;rolloutContextKind\&quot;: \&quot;organization\&quot;,   \&quot;rolloutWeights\&quot;: {     \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;: 15000, // serve 15% this variation     \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;: 85000  // serve 85% this variation   } }] } &#x60;&#x60;&#x60;  #### addTargets  Adds context keys to the individual context targets for the context kind that &#x60;contextKind&#x60; specifies and the variation that &#x60;variationId&#x60; specifies. Returns an error if this causes the AI Config to target the same context key in multiple variations.  ##### Parameters  - &#x60;values&#x60;: List of context keys. - &#x60;contextKind&#x60;: (Optional) Context kind to target, defaults to &#x60;user&#x60; - &#x60;variationId&#x60;: ID of a variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addTargets\&quot;,   \&quot;values\&quot;: [\&quot;context-key-123abc\&quot;, \&quot;context-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; }] } &#x60;&#x60;&#x60;  #### addValuesToClause  Adds &#x60;values&#x60; to the values of the clause that &#x60;ruleId&#x60; and &#x60;clauseId&#x60; indicate. Does not update the context kind, attribute, or operator.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the AI Config. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings, case sensitive.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addValuesToClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;,   \&quot;values\&quot;: [\&quot;beta_testers\&quot;] }] } &#x60;&#x60;&#x60;  #### clearTargets  Removes all individual targets from the variation that &#x60;variationId&#x60; specifies. This includes both user and non-user targets.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;clearTargets\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### removeClauses  Removes the clauses specified by &#x60;clauseIds&#x60; from the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule. - &#x60;clauseIds&#x60;: Array of IDs of clauses in the rule.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeClauses\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseIds\&quot;: [\&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;, \&quot;36a461dc-235e-4b08-97b9-73ce9365873e\&quot;] }] } &#x60;&#x60;&#x60;  #### removeRule  Removes the targeting rule specified by &#x60;ruleId&#x60;. Does nothing if the rule does not exist.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removeRule\&quot;, \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot; } ] } &#x60;&#x60;&#x60;  #### removeTargets  Removes context keys from the individual context targets for the context kind that &#x60;contextKind&#x60; specifies and the variation that &#x60;variationId&#x60; specifies. Does nothing if the flag does not target the context keys.  ##### Parameters  - &#x60;values&#x60;: List of context keys. - &#x60;contextKind&#x60;: (Optional) Context kind to target, defaults to &#x60;user&#x60; - &#x60;variationId&#x60;: ID of a variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeTargets\&quot;,   \&quot;values\&quot;: [\&quot;context-key-123abc\&quot;, \&quot;context-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; }] } &#x60;&#x60;&#x60;  #### removeValuesFromClause  Removes &#x60;values&#x60; from the values of the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60;. Does not update the context kind, attribute, or operator.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings, case sensitive.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeValuesFromClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;,   \&quot;values\&quot;: [\&quot;beta_testers\&quot;] }] } &#x60;&#x60;&#x60;  #### reorderRules  Rearranges the rules to match the order given in &#x60;ruleIds&#x60;. Returns an error if &#x60;ruleIds&#x60; does not match the current set of rules on the AI Config.  ##### Parameters  - &#x60;ruleIds&#x60;: Array of IDs of all rules.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;reorderRules\&quot;,   \&quot;ruleIds\&quot;: [\&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;, \&quot;63c238d1-835d-435e-8f21-c8d5e40b2a3d\&quot;] }] } &#x60;&#x60;&#x60;  #### replaceRules  Removes all targeting rules for the AI Config and replaces them with the list you provide.  ##### Parameters  - &#x60;rules&#x60;: A list of rules.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [   {     \&quot;kind\&quot;: \&quot;replaceRules\&quot;,     \&quot;rules\&quot;: [       {         \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,         \&quot;description\&quot;: \&quot;My new rule\&quot;,         \&quot;clauses\&quot;: [           {             \&quot;contextKind\&quot;: \&quot;user\&quot;,             \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,             \&quot;op\&quot;: \&quot;segmentMatch\&quot;,             \&quot;values\&quot;: [\&quot;test\&quot;]           }         ]       }     ]   } ] } &#x60;&#x60;&#x60;  #### replaceTargets  Removes all existing targeting and replaces it with the list of targets you provide.  ##### Parameters  - &#x60;targets&#x60;: A list of context targeting. Each item in the list includes an optional &#x60;contextKind&#x60; that defaults to &#x60;user&#x60;, a required &#x60;variationId&#x60;, and a required list of &#x60;values&#x60;.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [   {     \&quot;kind\&quot;: \&quot;replaceTargets\&quot;,     \&quot;targets\&quot;: [       {         \&quot;contextKind\&quot;: \&quot;user\&quot;,         \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,         \&quot;values\&quot;: [\&quot;user-key-123abc\&quot;]       },       {         \&quot;contextKind\&quot;: \&quot;device\&quot;,         \&quot;variationId\&quot;: \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;,         \&quot;values\&quot;: [\&quot;device-key-456def\&quot;]       }     ]   } ] } &#x60;&#x60;&#x60;  #### updateClause  Replaces the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60; with &#x60;clause&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;clause&#x60;: New &#x60;clause&#x60; object, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10c7462a-2062-45ba-a8bb-dfb3de0f8af5\&quot;,   \&quot;clause\&quot;: {     \&quot;contextKind\&quot;: \&quot;user\&quot;,     \&quot;attribute\&quot;: \&quot;country\&quot;,     \&quot;op\&quot;: \&quot;in\&quot;,     \&quot;negate\&quot;: false,     \&quot;values\&quot;: [\&quot;Mexico\&quot;, \&quot;Canada\&quot;]   } }] } &#x60;&#x60;&#x60;  #### updateDefaultVariation  Updates the default on or off variation of the AI Config.  ##### Parameters  - &#x60;onVariationValue&#x60;: (Optional) The value of the variation of the new on variation. - &#x60;offVariationValue&#x60;: (Optional) The value of the variation of the new off variation  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateDefaultVariation\&quot;, \&quot;OnVariationValue\&quot;: true, \&quot;OffVariationValue\&quot;: false } ] } &#x60;&#x60;&#x60;  #### updateFallthroughVariationOrRollout  Updates the default or \&quot;fallthrough\&quot; rule for the AI Config, which the AI Config serves when a context matches none of the targeting rules. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percentage rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation.  or  - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example that uses a &#x60;variationId&#x60;:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; }] } &#x60;&#x60;&#x60;  Here&#39;s an example that uses a percentage rollout:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,   \&quot;rolloutContextKind\&quot;: \&quot;user\&quot;,   \&quot;rolloutWeights\&quot;: {     \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;: 15000, // serve 15% this variation     \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;: 85000  // serve 85% this variation   } }] } &#x60;&#x60;&#x60;  #### updateOffVariation  Updates the default off variation to &#x60;variationId&#x60;. The AI Config serves the default off variation when the AI Config&#39;s targeting is **Off**.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateOffVariation\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### updateRuleDescription  Updates the description of the targeting rule.  ##### Parameters  - &#x60;description&#x60;: The new human-readable description for this rule. - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the AI Config.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleDescription\&quot;,   \&quot;description\&quot;: \&quot;New rule description\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot; }] } &#x60;&#x60;&#x60;  #### updateRuleTrackEvents  Updates whether or not LaunchDarkly tracks events for the AI Config associated with this rule.  ##### Parameters  - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the AI Config. - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleTrackEvents\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;trackEvents\&quot;: true }] } &#x60;&#x60;&#x60;  #### updateRuleVariationOrRollout  Updates what &#x60;ruleId&#x60; serves when its clauses evaluate to true. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percent rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule. - &#x60;variationId&#x60;: ID of a variation.  or  - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleVariationOrRollout\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; }] } &#x60;&#x60;&#x60;  #### updateTrackEvents  Updates whether or not LaunchDarkly tracks events for the AI Config, for all rules.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateTrackEvents\&quot;, \&quot;trackEvents\&quot;: true } ] } &#x60;&#x60;&#x60;  #### updateTrackEventsFallthrough  Updates whether or not LaunchDarkly tracks events for the AI Config, for the default rule.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateTrackEventsFallthrough\&quot;, \&quot;trackEvents\&quot;: true } ] } &#x60;&#x60;&#x60; &lt;/details&gt; 
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param aiConfigTargetingPatch AI Config targeting semantic patch instructions (optional)
     * @return ApiResponse&lt;AIConfigTargeting&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config targeting updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AIConfigTargeting> patchAIConfigTargetingWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nullable AIConfigTargetingPatch aiConfigTargetingPatch) throws ApiException {
        okhttp3.Call localVarCall = patchAIConfigTargetingValidateBeforeCall(projectKey, configKey, aiConfigTargetingPatch, null);
        Type localVarReturnType = new TypeToken<AIConfigTargeting>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update AI Config targeting (asynchronously)
     * Perform a partial update to an AI Config&#39;s targeting. The request body must be a valid semantic patch.  ### Using semantic patches on an AI Config  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](https://launchdarkly.com/docs/api#updates-using-semantic-patch).  The body of a semantic patch request for updating an AI Config&#39;s targeting takes the following properties:  * &#x60;comment&#x60; (string): (Optional) A description of the update. * &#x60;environmentKey&#x60; (string): The key of the LaunchDarkly environment. * &#x60;instructions&#x60; (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a &#x60;kind&#x60; property that indicates the instruction. If the action requires parameters, you must include those parameters as additional fields in the object. The body of a single semantic patch can contain many different instructions.  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating AI Configs.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;working with targeting and variations&lt;/strong&gt; for AI Configs&lt;/summary&gt;  #### addClauses  Adds the given clauses to the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the AI Config. - &#x60;clauses&#x60;: Array of clause objects, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addClauses\&quot;,     \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,     \&quot;clauses\&quot;: [{       \&quot;contextKind\&quot;: \&quot;user\&quot;,       \&quot;attribute\&quot;: \&quot;country\&quot;,       \&quot;op\&quot;: \&quot;in\&quot;,       \&quot;negate\&quot;: false,       \&quot;values\&quot;: [\&quot;USA\&quot;, \&quot;Canada\&quot;]     }]   }] } &#x60;&#x60;&#x60;  #### addRule  Adds a new targeting rule to the AI Config. The rule may contain &#x60;clauses&#x60; and serve the variation that &#x60;variationId&#x60; indicates, or serve a percentage rollout that &#x60;rolloutWeights&#x60;, &#x60;rolloutBucketBy&#x60;, and &#x60;rolloutContextKind&#x60; indicate.  If you set &#x60;beforeRuleId&#x60;, this adds the new rule before the indicated rule. Otherwise, adds the new rule to the end of the list.  ##### Parameters  - &#x60;clauses&#x60;: Array of clause objects, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case. - &#x60;beforeRuleId&#x60;: (Optional) ID of a rule. - Either - &#x60;variationId&#x60;: ID of a variation.  or  - &#x60;rolloutWeights&#x60;: (Optional) Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example that uses a &#x60;variationId&#x60;:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addRule\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,   \&quot;clauses\&quot;: [{     \&quot;contextKind\&quot;: \&quot;organization\&quot;,     \&quot;attribute\&quot;: \&quot;located_in\&quot;,     \&quot;op\&quot;: \&quot;in\&quot;,     \&quot;negate\&quot;: false,     \&quot;values\&quot;: [\&quot;Sweden\&quot;, \&quot;Norway\&quot;]   }] }] } &#x60;&#x60;&#x60;  Here&#39;s an example that uses a percentage rollout:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addRule\&quot;,   \&quot;clauses\&quot;: [{     \&quot;contextKind\&quot;: \&quot;organization\&quot;,     \&quot;attribute\&quot;: \&quot;located_in\&quot;,     \&quot;op\&quot;: \&quot;in\&quot;,     \&quot;negate\&quot;: false,     \&quot;values\&quot;: [\&quot;Sweden\&quot;, \&quot;Norway\&quot;]   }],   \&quot;rolloutContextKind\&quot;: \&quot;organization\&quot;,   \&quot;rolloutWeights\&quot;: {     \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;: 15000, // serve 15% this variation     \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;: 85000  // serve 85% this variation   } }] } &#x60;&#x60;&#x60;  #### addTargets  Adds context keys to the individual context targets for the context kind that &#x60;contextKind&#x60; specifies and the variation that &#x60;variationId&#x60; specifies. Returns an error if this causes the AI Config to target the same context key in multiple variations.  ##### Parameters  - &#x60;values&#x60;: List of context keys. - &#x60;contextKind&#x60;: (Optional) Context kind to target, defaults to &#x60;user&#x60; - &#x60;variationId&#x60;: ID of a variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addTargets\&quot;,   \&quot;values\&quot;: [\&quot;context-key-123abc\&quot;, \&quot;context-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; }] } &#x60;&#x60;&#x60;  #### addValuesToClause  Adds &#x60;values&#x60; to the values of the clause that &#x60;ruleId&#x60; and &#x60;clauseId&#x60; indicate. Does not update the context kind, attribute, or operator.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the AI Config. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings, case sensitive.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addValuesToClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;,   \&quot;values\&quot;: [\&quot;beta_testers\&quot;] }] } &#x60;&#x60;&#x60;  #### clearTargets  Removes all individual targets from the variation that &#x60;variationId&#x60; specifies. This includes both user and non-user targets.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;clearTargets\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### removeClauses  Removes the clauses specified by &#x60;clauseIds&#x60; from the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule. - &#x60;clauseIds&#x60;: Array of IDs of clauses in the rule.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeClauses\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseIds\&quot;: [\&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;, \&quot;36a461dc-235e-4b08-97b9-73ce9365873e\&quot;] }] } &#x60;&#x60;&#x60;  #### removeRule  Removes the targeting rule specified by &#x60;ruleId&#x60;. Does nothing if the rule does not exist.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removeRule\&quot;, \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot; } ] } &#x60;&#x60;&#x60;  #### removeTargets  Removes context keys from the individual context targets for the context kind that &#x60;contextKind&#x60; specifies and the variation that &#x60;variationId&#x60; specifies. Does nothing if the flag does not target the context keys.  ##### Parameters  - &#x60;values&#x60;: List of context keys. - &#x60;contextKind&#x60;: (Optional) Context kind to target, defaults to &#x60;user&#x60; - &#x60;variationId&#x60;: ID of a variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeTargets\&quot;,   \&quot;values\&quot;: [\&quot;context-key-123abc\&quot;, \&quot;context-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; }] } &#x60;&#x60;&#x60;  #### removeValuesFromClause  Removes &#x60;values&#x60; from the values of the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60;. Does not update the context kind, attribute, or operator.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings, case sensitive.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeValuesFromClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;,   \&quot;values\&quot;: [\&quot;beta_testers\&quot;] }] } &#x60;&#x60;&#x60;  #### reorderRules  Rearranges the rules to match the order given in &#x60;ruleIds&#x60;. Returns an error if &#x60;ruleIds&#x60; does not match the current set of rules on the AI Config.  ##### Parameters  - &#x60;ruleIds&#x60;: Array of IDs of all rules.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;reorderRules\&quot;,   \&quot;ruleIds\&quot;: [\&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;, \&quot;63c238d1-835d-435e-8f21-c8d5e40b2a3d\&quot;] }] } &#x60;&#x60;&#x60;  #### replaceRules  Removes all targeting rules for the AI Config and replaces them with the list you provide.  ##### Parameters  - &#x60;rules&#x60;: A list of rules.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [   {     \&quot;kind\&quot;: \&quot;replaceRules\&quot;,     \&quot;rules\&quot;: [       {         \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,         \&quot;description\&quot;: \&quot;My new rule\&quot;,         \&quot;clauses\&quot;: [           {             \&quot;contextKind\&quot;: \&quot;user\&quot;,             \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,             \&quot;op\&quot;: \&quot;segmentMatch\&quot;,             \&quot;values\&quot;: [\&quot;test\&quot;]           }         ]       }     ]   } ] } &#x60;&#x60;&#x60;  #### replaceTargets  Removes all existing targeting and replaces it with the list of targets you provide.  ##### Parameters  - &#x60;targets&#x60;: A list of context targeting. Each item in the list includes an optional &#x60;contextKind&#x60; that defaults to &#x60;user&#x60;, a required &#x60;variationId&#x60;, and a required list of &#x60;values&#x60;.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [   {     \&quot;kind\&quot;: \&quot;replaceTargets\&quot;,     \&quot;targets\&quot;: [       {         \&quot;contextKind\&quot;: \&quot;user\&quot;,         \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,         \&quot;values\&quot;: [\&quot;user-key-123abc\&quot;]       },       {         \&quot;contextKind\&quot;: \&quot;device\&quot;,         \&quot;variationId\&quot;: \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;,         \&quot;values\&quot;: [\&quot;device-key-456def\&quot;]       }     ]   } ] } &#x60;&#x60;&#x60;  #### updateClause  Replaces the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60; with &#x60;clause&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;clause&#x60;: New &#x60;clause&#x60; object, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10c7462a-2062-45ba-a8bb-dfb3de0f8af5\&quot;,   \&quot;clause\&quot;: {     \&quot;contextKind\&quot;: \&quot;user\&quot;,     \&quot;attribute\&quot;: \&quot;country\&quot;,     \&quot;op\&quot;: \&quot;in\&quot;,     \&quot;negate\&quot;: false,     \&quot;values\&quot;: [\&quot;Mexico\&quot;, \&quot;Canada\&quot;]   } }] } &#x60;&#x60;&#x60;  #### updateDefaultVariation  Updates the default on or off variation of the AI Config.  ##### Parameters  - &#x60;onVariationValue&#x60;: (Optional) The value of the variation of the new on variation. - &#x60;offVariationValue&#x60;: (Optional) The value of the variation of the new off variation  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateDefaultVariation\&quot;, \&quot;OnVariationValue\&quot;: true, \&quot;OffVariationValue\&quot;: false } ] } &#x60;&#x60;&#x60;  #### updateFallthroughVariationOrRollout  Updates the default or \&quot;fallthrough\&quot; rule for the AI Config, which the AI Config serves when a context matches none of the targeting rules. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percentage rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation.  or  - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example that uses a &#x60;variationId&#x60;:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; }] } &#x60;&#x60;&#x60;  Here&#39;s an example that uses a percentage rollout:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,   \&quot;rolloutContextKind\&quot;: \&quot;user\&quot;,   \&quot;rolloutWeights\&quot;: {     \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;: 15000, // serve 15% this variation     \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;: 85000  // serve 85% this variation   } }] } &#x60;&#x60;&#x60;  #### updateOffVariation  Updates the default off variation to &#x60;variationId&#x60;. The AI Config serves the default off variation when the AI Config&#39;s targeting is **Off**.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateOffVariation\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### updateRuleDescription  Updates the description of the targeting rule.  ##### Parameters  - &#x60;description&#x60;: The new human-readable description for this rule. - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the AI Config.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleDescription\&quot;,   \&quot;description\&quot;: \&quot;New rule description\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot; }] } &#x60;&#x60;&#x60;  #### updateRuleTrackEvents  Updates whether or not LaunchDarkly tracks events for the AI Config associated with this rule.  ##### Parameters  - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the AI Config. - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleTrackEvents\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;trackEvents\&quot;: true }] } &#x60;&#x60;&#x60;  #### updateRuleVariationOrRollout  Updates what &#x60;ruleId&#x60; serves when its clauses evaluate to true. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percent rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule. - &#x60;variationId&#x60;: ID of a variation.  or  - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleVariationOrRollout\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; }] } &#x60;&#x60;&#x60;  #### updateTrackEvents  Updates whether or not LaunchDarkly tracks events for the AI Config, for all rules.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateTrackEvents\&quot;, \&quot;trackEvents\&quot;: true } ] } &#x60;&#x60;&#x60;  #### updateTrackEventsFallthrough  Updates whether or not LaunchDarkly tracks events for the AI Config, for the default rule.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateTrackEventsFallthrough\&quot;, \&quot;trackEvents\&quot;: true } ] } &#x60;&#x60;&#x60; &lt;/details&gt; 
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param aiConfigTargetingPatch AI Config targeting semantic patch instructions (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config targeting updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchAIConfigTargetingAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nullable AIConfigTargetingPatch aiConfigTargetingPatch, final ApiCallback<AIConfigTargeting> _callback) throws ApiException {

        okhttp3.Call localVarCall = patchAIConfigTargetingValidateBeforeCall(projectKey, configKey, aiConfigTargetingPatch, _callback);
        Type localVarReturnType = new TypeToken<AIConfigTargeting>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for patchAIConfigVariation
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
     * @param aiConfigVariationPatch AI Config variation object to update (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config variation updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchAIConfigVariationCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull String variationKey, @javax.annotation.Nullable AIConfigVariationPatch aiConfigVariationPatch, final ApiCallback _callback) throws ApiException {
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
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "configKey" + "}", localVarApiClient.escapeString(configKey.toString()))
            .replace("{" + "variationKey" + "}", localVarApiClient.escapeString(variationKey.toString()));

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
    private okhttp3.Call patchAIConfigVariationValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull String variationKey, @javax.annotation.Nullable AIConfigVariationPatch aiConfigVariationPatch, final ApiCallback _callback) throws ApiException {
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

        return patchAIConfigVariationCall(projectKey, configKey, variationKey, aiConfigVariationPatch, _callback);

    }

    /**
     * Update AI Config variation
     * Edit an existing variation of an AI Config. This creates a new version of the variation.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  Here&#39;s an example: &#x60;&#x60;&#x60;   {     \&quot;messages\&quot;: [       {         \&quot;role\&quot;: \&quot;system\&quot;,         \&quot;content\&quot;: \&quot;The new message\&quot;       }     ]   } &#x60;&#x60;&#x60; 
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
     * @param aiConfigVariationPatch AI Config variation object to update (optional)
     * @return AIConfigVariation
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config variation updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AIConfigVariation patchAIConfigVariation(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull String variationKey, @javax.annotation.Nullable AIConfigVariationPatch aiConfigVariationPatch) throws ApiException {
        ApiResponse<AIConfigVariation> localVarResp = patchAIConfigVariationWithHttpInfo(projectKey, configKey, variationKey, aiConfigVariationPatch);
        return localVarResp.getData();
    }

    /**
     * Update AI Config variation
     * Edit an existing variation of an AI Config. This creates a new version of the variation.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  Here&#39;s an example: &#x60;&#x60;&#x60;   {     \&quot;messages\&quot;: [       {         \&quot;role\&quot;: \&quot;system\&quot;,         \&quot;content\&quot;: \&quot;The new message\&quot;       }     ]   } &#x60;&#x60;&#x60; 
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
     * @param aiConfigVariationPatch AI Config variation object to update (optional)
     * @return ApiResponse&lt;AIConfigVariation&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config variation updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AIConfigVariation> patchAIConfigVariationWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull String variationKey, @javax.annotation.Nullable AIConfigVariationPatch aiConfigVariationPatch) throws ApiException {
        okhttp3.Call localVarCall = patchAIConfigVariationValidateBeforeCall(projectKey, configKey, variationKey, aiConfigVariationPatch, null);
        Type localVarReturnType = new TypeToken<AIConfigVariation>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update AI Config variation (asynchronously)
     * Edit an existing variation of an AI Config. This creates a new version of the variation.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  Here&#39;s an example: &#x60;&#x60;&#x60;   {     \&quot;messages\&quot;: [       {         \&quot;role\&quot;: \&quot;system\&quot;,         \&quot;content\&quot;: \&quot;The new message\&quot;       }     ]   } &#x60;&#x60;&#x60; 
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param variationKey  (required)
     * @param aiConfigVariationPatch AI Config variation object to update (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI Config variation updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchAIConfigVariationAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull String variationKey, @javax.annotation.Nullable AIConfigVariationPatch aiConfigVariationPatch, final ApiCallback<AIConfigVariation> _callback) throws ApiException {

        okhttp3.Call localVarCall = patchAIConfigVariationValidateBeforeCall(projectKey, configKey, variationKey, aiConfigVariationPatch, _callback);
        Type localVarReturnType = new TypeToken<AIConfigVariation>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for patchAITool
     * @param projectKey  (required)
     * @param toolKey  (required)
     * @param aiToolPatch AI tool object to update (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI tool updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchAIToolCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String toolKey, @javax.annotation.Nullable AIToolPatch aiToolPatch, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = aiToolPatch;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/ai-tools/{toolKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "toolKey" + "}", localVarApiClient.escapeString(toolKey.toString()));

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
    private okhttp3.Call patchAIToolValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String toolKey, @javax.annotation.Nullable AIToolPatch aiToolPatch, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling patchAITool(Async)");
        }

        // verify the required parameter 'toolKey' is set
        if (toolKey == null) {
            throw new ApiException("Missing the required parameter 'toolKey' when calling patchAITool(Async)");
        }

        return patchAIToolCall(projectKey, toolKey, aiToolPatch, _callback);

    }

    /**
     * Update AI tool
     * Edit an existing AI tool.
     * @param projectKey  (required)
     * @param toolKey  (required)
     * @param aiToolPatch AI tool object to update (optional)
     * @return AITool
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI tool updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AITool patchAITool(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String toolKey, @javax.annotation.Nullable AIToolPatch aiToolPatch) throws ApiException {
        ApiResponse<AITool> localVarResp = patchAIToolWithHttpInfo(projectKey, toolKey, aiToolPatch);
        return localVarResp.getData();
    }

    /**
     * Update AI tool
     * Edit an existing AI tool.
     * @param projectKey  (required)
     * @param toolKey  (required)
     * @param aiToolPatch AI tool object to update (optional)
     * @return ApiResponse&lt;AITool&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI tool updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AITool> patchAIToolWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String toolKey, @javax.annotation.Nullable AIToolPatch aiToolPatch) throws ApiException {
        okhttp3.Call localVarCall = patchAIToolValidateBeforeCall(projectKey, toolKey, aiToolPatch, null);
        Type localVarReturnType = new TypeToken<AITool>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update AI tool (asynchronously)
     * Edit an existing AI tool.
     * @param projectKey  (required)
     * @param toolKey  (required)
     * @param aiToolPatch AI tool object to update (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> AI tool updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchAIToolAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String toolKey, @javax.annotation.Nullable AIToolPatch aiToolPatch, final ApiCallback<AITool> _callback) throws ApiException {

        okhttp3.Call localVarCall = patchAIToolValidateBeforeCall(projectKey, toolKey, aiToolPatch, _callback);
        Type localVarReturnType = new TypeToken<AITool>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for patchAgentGraph
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param graphKey  (required)
     * @param agentGraphPatch Agent graph object to update (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Agent graph updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchAgentGraphCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String graphKey, @javax.annotation.Nullable AgentGraphPatch agentGraphPatch, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = agentGraphPatch;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/agent-graphs/{graphKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "graphKey" + "}", localVarApiClient.escapeString(graphKey.toString()));

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
    private okhttp3.Call patchAgentGraphValidateBeforeCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String graphKey, @javax.annotation.Nullable AgentGraphPatch agentGraphPatch, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling patchAgentGraph(Async)");
        }

        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling patchAgentGraph(Async)");
        }

        // verify the required parameter 'graphKey' is set
        if (graphKey == null) {
            throw new ApiException("Missing the required parameter 'graphKey' when calling patchAgentGraph(Async)");
        }

        return patchAgentGraphCall(ldAPIVersion, projectKey, graphKey, agentGraphPatch, _callback);

    }

    /**
     * Update agent graph
     * Edit an existing agent graph.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  If the update includes &#x60;rootConfigKey&#x60; or &#x60;edges&#x60;, both must be present and will be treated as full replacements. 
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param graphKey  (required)
     * @param agentGraphPatch Agent graph object to update (optional)
     * @return AgentGraph
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Agent graph updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AgentGraph patchAgentGraph(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String graphKey, @javax.annotation.Nullable AgentGraphPatch agentGraphPatch) throws ApiException {
        ApiResponse<AgentGraph> localVarResp = patchAgentGraphWithHttpInfo(ldAPIVersion, projectKey, graphKey, agentGraphPatch);
        return localVarResp.getData();
    }

    /**
     * Update agent graph
     * Edit an existing agent graph.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  If the update includes &#x60;rootConfigKey&#x60; or &#x60;edges&#x60;, both must be present and will be treated as full replacements. 
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param graphKey  (required)
     * @param agentGraphPatch Agent graph object to update (optional)
     * @return ApiResponse&lt;AgentGraph&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Agent graph updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AgentGraph> patchAgentGraphWithHttpInfo(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String graphKey, @javax.annotation.Nullable AgentGraphPatch agentGraphPatch) throws ApiException {
        okhttp3.Call localVarCall = patchAgentGraphValidateBeforeCall(ldAPIVersion, projectKey, graphKey, agentGraphPatch, null);
        Type localVarReturnType = new TypeToken<AgentGraph>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update agent graph (asynchronously)
     * Edit an existing agent graph.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  If the update includes &#x60;rootConfigKey&#x60; or &#x60;edges&#x60;, both must be present and will be treated as full replacements. 
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param graphKey  (required)
     * @param agentGraphPatch Agent graph object to update (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Agent graph updated </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not found </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchAgentGraphAsync(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String graphKey, @javax.annotation.Nullable AgentGraphPatch agentGraphPatch, final ApiCallback<AgentGraph> _callback) throws ApiException {

        okhttp3.Call localVarCall = patchAgentGraphValidateBeforeCall(ldAPIVersion, projectKey, graphKey, agentGraphPatch, _callback);
        Type localVarReturnType = new TypeToken<AgentGraph>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for postAIConfig
     * @param projectKey  (required)
     * @param aiConfigPost AI Config object to create (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> AI Config created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postAIConfigCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull AIConfigPost aiConfigPost, final ApiCallback _callback) throws ApiException {
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
    private okhttp3.Call postAIConfigValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull AIConfigPost aiConfigPost, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling postAIConfig(Async)");
        }

        // verify the required parameter 'aiConfigPost' is set
        if (aiConfigPost == null) {
            throw new ApiException("Missing the required parameter 'aiConfigPost' when calling postAIConfig(Async)");
        }

        return postAIConfigCall(projectKey, aiConfigPost, _callback);

    }

    /**
     * Create new AI Config
     * Create a new AI Config within the given project.
     * @param projectKey  (required)
     * @param aiConfigPost AI Config object to create (required)
     * @return AIConfig
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> AI Config created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AIConfig postAIConfig(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull AIConfigPost aiConfigPost) throws ApiException {
        ApiResponse<AIConfig> localVarResp = postAIConfigWithHttpInfo(projectKey, aiConfigPost);
        return localVarResp.getData();
    }

    /**
     * Create new AI Config
     * Create a new AI Config within the given project.
     * @param projectKey  (required)
     * @param aiConfigPost AI Config object to create (required)
     * @return ApiResponse&lt;AIConfig&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> AI Config created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AIConfig> postAIConfigWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull AIConfigPost aiConfigPost) throws ApiException {
        okhttp3.Call localVarCall = postAIConfigValidateBeforeCall(projectKey, aiConfigPost, null);
        Type localVarReturnType = new TypeToken<AIConfig>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create new AI Config (asynchronously)
     * Create a new AI Config within the given project.
     * @param projectKey  (required)
     * @param aiConfigPost AI Config object to create (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> AI Config created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postAIConfigAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull AIConfigPost aiConfigPost, final ApiCallback<AIConfig> _callback) throws ApiException {

        okhttp3.Call localVarCall = postAIConfigValidateBeforeCall(projectKey, aiConfigPost, _callback);
        Type localVarReturnType = new TypeToken<AIConfig>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for postAIConfigVariation
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param aiConfigVariationPost AI Config variation object to create (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> AI Config variation created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postAIConfigVariationCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull AIConfigVariationPost aiConfigVariationPost, final ApiCallback _callback) throws ApiException {
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
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "configKey" + "}", localVarApiClient.escapeString(configKey.toString()));

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
    private okhttp3.Call postAIConfigVariationValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull AIConfigVariationPost aiConfigVariationPost, final ApiCallback _callback) throws ApiException {
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

        return postAIConfigVariationCall(projectKey, configKey, aiConfigVariationPost, _callback);

    }

    /**
     * Create AI Config variation
     * Create a new variation for a given AI Config.  The &lt;code&gt;model&lt;/code&gt; in the request body requires a &lt;code&gt;modelName&lt;/code&gt; and &lt;code&gt;parameters&lt;/code&gt;, for example:  &#x60;&#x60;&#x60;   \&quot;model\&quot;: {     \&quot;modelName\&quot;: \&quot;claude-3-opus-20240229\&quot;,     \&quot;parameters\&quot;: {       \&quot;max_tokens\&quot;: 1024     }   } &#x60;&#x60;&#x60; 
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param aiConfigVariationPost AI Config variation object to create (required)
     * @return AIConfigVariation
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> AI Config variation created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AIConfigVariation postAIConfigVariation(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull AIConfigVariationPost aiConfigVariationPost) throws ApiException {
        ApiResponse<AIConfigVariation> localVarResp = postAIConfigVariationWithHttpInfo(projectKey, configKey, aiConfigVariationPost);
        return localVarResp.getData();
    }

    /**
     * Create AI Config variation
     * Create a new variation for a given AI Config.  The &lt;code&gt;model&lt;/code&gt; in the request body requires a &lt;code&gt;modelName&lt;/code&gt; and &lt;code&gt;parameters&lt;/code&gt;, for example:  &#x60;&#x60;&#x60;   \&quot;model\&quot;: {     \&quot;modelName\&quot;: \&quot;claude-3-opus-20240229\&quot;,     \&quot;parameters\&quot;: {       \&quot;max_tokens\&quot;: 1024     }   } &#x60;&#x60;&#x60; 
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param aiConfigVariationPost AI Config variation object to create (required)
     * @return ApiResponse&lt;AIConfigVariation&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> AI Config variation created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AIConfigVariation> postAIConfigVariationWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull AIConfigVariationPost aiConfigVariationPost) throws ApiException {
        okhttp3.Call localVarCall = postAIConfigVariationValidateBeforeCall(projectKey, configKey, aiConfigVariationPost, null);
        Type localVarReturnType = new TypeToken<AIConfigVariation>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create AI Config variation (asynchronously)
     * Create a new variation for a given AI Config.  The &lt;code&gt;model&lt;/code&gt; in the request body requires a &lt;code&gt;modelName&lt;/code&gt; and &lt;code&gt;parameters&lt;/code&gt;, for example:  &#x60;&#x60;&#x60;   \&quot;model\&quot;: {     \&quot;modelName\&quot;: \&quot;claude-3-opus-20240229\&quot;,     \&quot;parameters\&quot;: {       \&quot;max_tokens\&quot;: 1024     }   } &#x60;&#x60;&#x60; 
     * @param projectKey  (required)
     * @param configKey  (required)
     * @param aiConfigVariationPost AI Config variation object to create (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> AI Config variation created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postAIConfigVariationAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull String configKey, @javax.annotation.Nonnull AIConfigVariationPost aiConfigVariationPost, final ApiCallback<AIConfigVariation> _callback) throws ApiException {

        okhttp3.Call localVarCall = postAIConfigVariationValidateBeforeCall(projectKey, configKey, aiConfigVariationPost, _callback);
        Type localVarReturnType = new TypeToken<AIConfigVariation>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for postAITool
     * @param projectKey  (required)
     * @param aiToolPost AI tool object to create (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> AI tool created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postAIToolCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull AIToolPost aiToolPost, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = aiToolPost;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/ai-tools"
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
    private okhttp3.Call postAIToolValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull AIToolPost aiToolPost, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling postAITool(Async)");
        }

        // verify the required parameter 'aiToolPost' is set
        if (aiToolPost == null) {
            throw new ApiException("Missing the required parameter 'aiToolPost' when calling postAITool(Async)");
        }

        return postAIToolCall(projectKey, aiToolPost, _callback);

    }

    /**
     * Create an AI tool
     * Create an AI tool
     * @param projectKey  (required)
     * @param aiToolPost AI tool object to create (required)
     * @return AITool
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> AI tool created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AITool postAITool(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull AIToolPost aiToolPost) throws ApiException {
        ApiResponse<AITool> localVarResp = postAIToolWithHttpInfo(projectKey, aiToolPost);
        return localVarResp.getData();
    }

    /**
     * Create an AI tool
     * Create an AI tool
     * @param projectKey  (required)
     * @param aiToolPost AI tool object to create (required)
     * @return ApiResponse&lt;AITool&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> AI tool created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AITool> postAIToolWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull AIToolPost aiToolPost) throws ApiException {
        okhttp3.Call localVarCall = postAIToolValidateBeforeCall(projectKey, aiToolPost, null);
        Type localVarReturnType = new TypeToken<AITool>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create an AI tool (asynchronously)
     * Create an AI tool
     * @param projectKey  (required)
     * @param aiToolPost AI tool object to create (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> AI tool created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postAIToolAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull AIToolPost aiToolPost, final ApiCallback<AITool> _callback) throws ApiException {

        okhttp3.Call localVarCall = postAIToolValidateBeforeCall(projectKey, aiToolPost, _callback);
        Type localVarReturnType = new TypeToken<AITool>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for postAgentGraph
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param agentGraphPost Agent graph object to create (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Agent graph created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 413 </td><td> Payload too large </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postAgentGraphCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull AgentGraphPost agentGraphPost, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = agentGraphPost;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/agent-graphs"
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
    private okhttp3.Call postAgentGraphValidateBeforeCall(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull AgentGraphPost agentGraphPost, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'ldAPIVersion' is set
        if (ldAPIVersion == null) {
            throw new ApiException("Missing the required parameter 'ldAPIVersion' when calling postAgentGraph(Async)");
        }

        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling postAgentGraph(Async)");
        }

        // verify the required parameter 'agentGraphPost' is set
        if (agentGraphPost == null) {
            throw new ApiException("Missing the required parameter 'agentGraphPost' when calling postAgentGraph(Async)");
        }

        return postAgentGraphCall(ldAPIVersion, projectKey, agentGraphPost, _callback);

    }

    /**
     * Create new agent graph
     * Create a new agent graph within the given project.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param agentGraphPost Agent graph object to create (required)
     * @return AgentGraph
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Agent graph created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 413 </td><td> Payload too large </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public AgentGraph postAgentGraph(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull AgentGraphPost agentGraphPost) throws ApiException {
        ApiResponse<AgentGraph> localVarResp = postAgentGraphWithHttpInfo(ldAPIVersion, projectKey, agentGraphPost);
        return localVarResp.getData();
    }

    /**
     * Create new agent graph
     * Create a new agent graph within the given project.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param agentGraphPost Agent graph object to create (required)
     * @return ApiResponse&lt;AgentGraph&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Agent graph created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 413 </td><td> Payload too large </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AgentGraph> postAgentGraphWithHttpInfo(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull AgentGraphPost agentGraphPost) throws ApiException {
        okhttp3.Call localVarCall = postAgentGraphValidateBeforeCall(ldAPIVersion, projectKey, agentGraphPost, null);
        Type localVarReturnType = new TypeToken<AgentGraph>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create new agent graph (asynchronously)
     * Create a new agent graph within the given project.
     * @param ldAPIVersion Version of the endpoint. (required)
     * @param projectKey  (required)
     * @param agentGraphPost Agent graph object to create (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Agent graph created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 413 </td><td> Payload too large </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Internal server error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postAgentGraphAsync(@javax.annotation.Nonnull String ldAPIVersion, @javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull AgentGraphPost agentGraphPost, final ApiCallback<AgentGraph> _callback) throws ApiException {

        okhttp3.Call localVarCall = postAgentGraphValidateBeforeCall(ldAPIVersion, projectKey, agentGraphPost, _callback);
        Type localVarReturnType = new TypeToken<AgentGraph>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for postModelConfig
     * @param projectKey  (required)
     * @param modelConfigPost AI model config object to create (required)
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
    public okhttp3.Call postModelConfigCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull ModelConfigPost modelConfigPost, final ApiCallback _callback) throws ApiException {
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
    private okhttp3.Call postModelConfigValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull ModelConfigPost modelConfigPost, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling postModelConfig(Async)");
        }

        // verify the required parameter 'modelConfigPost' is set
        if (modelConfigPost == null) {
            throw new ApiException("Missing the required parameter 'modelConfigPost' when calling postModelConfig(Async)");
        }

        return postModelConfigCall(projectKey, modelConfigPost, _callback);

    }

    /**
     * Create an AI model config
     * Create an AI model config. You can use this in any variation for any AI Config in your project.
     * @param projectKey  (required)
     * @param modelConfigPost AI model config object to create (required)
     * @return ModelConfig
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
    public ModelConfig postModelConfig(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull ModelConfigPost modelConfigPost) throws ApiException {
        ApiResponse<ModelConfig> localVarResp = postModelConfigWithHttpInfo(projectKey, modelConfigPost);
        return localVarResp.getData();
    }

    /**
     * Create an AI model config
     * Create an AI model config. You can use this in any variation for any AI Config in your project.
     * @param projectKey  (required)
     * @param modelConfigPost AI model config object to create (required)
     * @return ApiResponse&lt;ModelConfig&gt;
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
    public ApiResponse<ModelConfig> postModelConfigWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull ModelConfigPost modelConfigPost) throws ApiException {
        okhttp3.Call localVarCall = postModelConfigValidateBeforeCall(projectKey, modelConfigPost, null);
        Type localVarReturnType = new TypeToken<ModelConfig>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create an AI model config (asynchronously)
     * Create an AI model config. You can use this in any variation for any AI Config in your project.
     * @param projectKey  (required)
     * @param modelConfigPost AI model config object to create (required)
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
    public okhttp3.Call postModelConfigAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull ModelConfigPost modelConfigPost, final ApiCallback<ModelConfig> _callback) throws ApiException {

        okhttp3.Call localVarCall = postModelConfigValidateBeforeCall(projectKey, modelConfigPost, _callback);
        Type localVarReturnType = new TypeToken<ModelConfig>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for postRestrictedModels
     * @param projectKey  (required)
     * @param restrictedModelsRequest List of AI model keys to add to the restricted list. (required)
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
    public okhttp3.Call postRestrictedModelsCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull RestrictedModelsRequest restrictedModelsRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = restrictedModelsRequest;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/ai-configs/model-configs/restricted"
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
    private okhttp3.Call postRestrictedModelsValidateBeforeCall(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull RestrictedModelsRequest restrictedModelsRequest, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling postRestrictedModels(Async)");
        }

        // verify the required parameter 'restrictedModelsRequest' is set
        if (restrictedModelsRequest == null) {
            throw new ApiException("Missing the required parameter 'restrictedModelsRequest' when calling postRestrictedModels(Async)");
        }

        return postRestrictedModelsCall(projectKey, restrictedModelsRequest, _callback);

    }

    /**
     * Add AI models to the restricted list
     * Add AI models, by key, to the restricted list. Keys are included in the response from the [List AI model configs](https://launchdarkly.com/docs/api/ai-configs/list-model-configs) endpoint.
     * @param projectKey  (required)
     * @param restrictedModelsRequest List of AI model keys to add to the restricted list. (required)
     * @return RestrictedModelsResponse
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
    public RestrictedModelsResponse postRestrictedModels(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull RestrictedModelsRequest restrictedModelsRequest) throws ApiException {
        ApiResponse<RestrictedModelsResponse> localVarResp = postRestrictedModelsWithHttpInfo(projectKey, restrictedModelsRequest);
        return localVarResp.getData();
    }

    /**
     * Add AI models to the restricted list
     * Add AI models, by key, to the restricted list. Keys are included in the response from the [List AI model configs](https://launchdarkly.com/docs/api/ai-configs/list-model-configs) endpoint.
     * @param projectKey  (required)
     * @param restrictedModelsRequest List of AI model keys to add to the restricted list. (required)
     * @return ApiResponse&lt;RestrictedModelsResponse&gt;
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
    public ApiResponse<RestrictedModelsResponse> postRestrictedModelsWithHttpInfo(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull RestrictedModelsRequest restrictedModelsRequest) throws ApiException {
        okhttp3.Call localVarCall = postRestrictedModelsValidateBeforeCall(projectKey, restrictedModelsRequest, null);
        Type localVarReturnType = new TypeToken<RestrictedModelsResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Add AI models to the restricted list (asynchronously)
     * Add AI models, by key, to the restricted list. Keys are included in the response from the [List AI model configs](https://launchdarkly.com/docs/api/ai-configs/list-model-configs) endpoint.
     * @param projectKey  (required)
     * @param restrictedModelsRequest List of AI model keys to add to the restricted list. (required)
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
    public okhttp3.Call postRestrictedModelsAsync(@javax.annotation.Nonnull String projectKey, @javax.annotation.Nonnull RestrictedModelsRequest restrictedModelsRequest, final ApiCallback<RestrictedModelsResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = postRestrictedModelsValidateBeforeCall(projectKey, restrictedModelsRequest, _callback);
        Type localVarReturnType = new TypeToken<RestrictedModelsResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
