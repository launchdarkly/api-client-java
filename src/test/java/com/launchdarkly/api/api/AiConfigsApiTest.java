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

import com.launchdarkly.api.ApiException;
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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for AiConfigsApi
 */
@Disabled
public class AiConfigsApiTest {

    private final AiConfigsApi api = new AiConfigsApi();

    /**
     * Delete AI Config
     *
     * Delete an existing AI Config.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteAIConfigTest() throws ApiException {
        String projectKey = null;
        String configKey = null;
        api.deleteAIConfig(projectKey, configKey);
        // TODO: test validations
    }

    /**
     * Delete AI Config variation
     *
     * Delete a specific variation of an AI Config by config key and variation key.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteAIConfigVariationTest() throws ApiException {
        String projectKey = null;
        String configKey = null;
        String variationKey = null;
        api.deleteAIConfigVariation(projectKey, configKey, variationKey);
        // TODO: test validations
    }

    /**
     * Delete AI tool
     *
     * Delete an existing AI tool.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteAIToolTest() throws ApiException {
        String projectKey = null;
        String toolKey = null;
        api.deleteAITool(projectKey, toolKey);
        // TODO: test validations
    }

    /**
     * Delete agent graph
     *
     * Delete an existing agent graph and all of its edges.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteAgentGraphTest() throws ApiException {
        String ldAPIVersion = null;
        String projectKey = null;
        String graphKey = null;
        api.deleteAgentGraph(ldAPIVersion, projectKey, graphKey);
        // TODO: test validations
    }

    /**
     * Delete an AI model config
     *
     * Delete an AI model config.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteModelConfigTest() throws ApiException {
        String projectKey = null;
        String modelConfigKey = null;
        api.deleteModelConfig(projectKey, modelConfigKey);
        // TODO: test validations
    }

    /**
     * Remove AI models from the restricted list
     *
     * Remove AI models, by key, from the restricted list.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteRestrictedModelsTest() throws ApiException {
        String projectKey = null;
        RestrictedModelsRequest restrictedModelsRequest = null;
        api.deleteRestrictedModels(projectKey, restrictedModelsRequest);
        // TODO: test validations
    }

    /**
     * Get AI Config
     *
     * Retrieve a specific AI Config by its key.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAIConfigTest() throws ApiException {
        String projectKey = null;
        String configKey = null;
        AIConfig response = api.getAIConfig(projectKey, configKey);
        // TODO: test validations
    }

    /**
     * Get AI Config metrics
     *
     * Retrieve usage metrics for an AI Config by config key.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAIConfigMetricsTest() throws ApiException {
        String projectKey = null;
        String configKey = null;
        Integer from = null;
        Integer to = null;
        String env = null;
        Metrics response = api.getAIConfigMetrics(projectKey, configKey, from, to, env);
        // TODO: test validations
    }

    /**
     * Get AI Config metrics by variation
     *
     * Retrieve usage metrics for an AI Config by config key, with results split by variation.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAIConfigMetricsByVariationTest() throws ApiException {
        String projectKey = null;
        String configKey = null;
        Integer from = null;
        Integer to = null;
        String env = null;
        List<MetricByVariation> response = api.getAIConfigMetricsByVariation(projectKey, configKey, from, to, env);
        // TODO: test validations
    }

    /**
     * Show an AI Config&#39;s targeting
     *
     * Retrieves a specific AI Config&#39;s targeting by its key
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAIConfigTargetingTest() throws ApiException {
        String projectKey = null;
        String configKey = null;
        AIConfigTargeting response = api.getAIConfigTargeting(projectKey, configKey);
        // TODO: test validations
    }

    /**
     * Get AI Config variation
     *
     * Get an AI Config variation by key. The response includes all variation versions for the given variation key.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAIConfigVariationTest() throws ApiException {
        String projectKey = null;
        String configKey = null;
        String variationKey = null;
        AIConfigVariationsResponse response = api.getAIConfigVariation(projectKey, configKey, variationKey);
        // TODO: test validations
    }

    /**
     * List AI Configs
     *
     * Get a list of all AI Configs in the given project.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAIConfigsTest() throws ApiException {
        String projectKey = null;
        String sort = null;
        Integer limit = null;
        Integer offset = null;
        String filter = null;
        AIConfigs response = api.getAIConfigs(projectKey, sort, limit, offset, filter);
        // TODO: test validations
    }

    /**
     * Get AI tool
     *
     * Retrieve a specific AI tool by its key.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAIToolTest() throws ApiException {
        String projectKey = null;
        String toolKey = null;
        AITool response = api.getAITool(projectKey, toolKey);
        // TODO: test validations
    }

    /**
     * Get agent graph
     *
     * Retrieve a specific agent graph by its key, including its edges.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentGraphTest() throws ApiException {
        String ldAPIVersion = null;
        String projectKey = null;
        String graphKey = null;
        AgentGraph response = api.getAgentGraph(ldAPIVersion, projectKey, graphKey);
        // TODO: test validations
    }

    /**
     * Get AI model config
     *
     * Get an AI model config by key.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getModelConfigTest() throws ApiException {
        String projectKey = null;
        String modelConfigKey = null;
        ModelConfig response = api.getModelConfig(projectKey, modelConfigKey);
        // TODO: test validations
    }

    /**
     * List AI tool versions
     *
     * Get a list of all versions of an AI tool in the given project.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listAIToolVersionsTest() throws ApiException {
        String projectKey = null;
        String toolKey = null;
        String sort = null;
        Integer limit = null;
        Integer offset = null;
        AITools response = api.listAIToolVersions(projectKey, toolKey, sort, limit, offset);
        // TODO: test validations
    }

    /**
     * List AI tools
     *
     * Get a list of all AI tools in the given project.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listAIToolsTest() throws ApiException {
        String projectKey = null;
        String sort = null;
        Integer limit = null;
        Integer offset = null;
        String filter = null;
        AITools response = api.listAITools(projectKey, sort, limit, offset, filter);
        // TODO: test validations
    }

    /**
     * List agent graphs
     *
     * Get a list of all agent graphs in the given project. Returns metadata only, without edge data.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listAgentGraphsTest() throws ApiException {
        String ldAPIVersion = null;
        String projectKey = null;
        Integer limit = null;
        Integer offset = null;
        AgentGraphs response = api.listAgentGraphs(ldAPIVersion, projectKey, limit, offset);
        // TODO: test validations
    }

    /**
     * List AI model configs
     *
     * Get all AI model configs for a project.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listModelConfigsTest() throws ApiException {
        String projectKey = null;
        Boolean restricted = null;
        List<ModelConfig> response = api.listModelConfigs(projectKey, restricted);
        // TODO: test validations
    }

    /**
     * Update AI Config
     *
     * Edit an existing AI Config.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  Here&#39;s an example:   &#x60;&#x60;&#x60;     {       \&quot;description\&quot;: \&quot;Example updated description\&quot;,       \&quot;tags\&quot;: [\&quot;new-tag\&quot;]     }   &#x60;&#x60;&#x60; 
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void patchAIConfigTest() throws ApiException {
        String projectKey = null;
        String configKey = null;
        AIConfigPatch aiConfigPatch = null;
        AIConfig response = api.patchAIConfig(projectKey, configKey, aiConfigPatch);
        // TODO: test validations
    }

    /**
     * Update AI Config targeting
     *
     * Perform a partial update to an AI Config&#39;s targeting. The request body must be a valid semantic patch.  ### Using semantic patches on an AI Config  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](https://launchdarkly.com/docs/api#updates-using-semantic-patch).  The body of a semantic patch request for updating an AI Config&#39;s targeting takes the following properties:  * &#x60;comment&#x60; (string): (Optional) A description of the update. * &#x60;environmentKey&#x60; (string): The key of the LaunchDarkly environment. * &#x60;instructions&#x60; (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a &#x60;kind&#x60; property that indicates the instruction. If the action requires parameters, you must include those parameters as additional fields in the object. The body of a single semantic patch can contain many different instructions.  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating AI Configs.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;working with targeting and variations&lt;/strong&gt; for AI Configs&lt;/summary&gt;  #### addClauses  Adds the given clauses to the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the AI Config. - &#x60;clauses&#x60;: Array of clause objects, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addClauses\&quot;,     \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,     \&quot;clauses\&quot;: [{       \&quot;contextKind\&quot;: \&quot;user\&quot;,       \&quot;attribute\&quot;: \&quot;country\&quot;,       \&quot;op\&quot;: \&quot;in\&quot;,       \&quot;negate\&quot;: false,       \&quot;values\&quot;: [\&quot;USA\&quot;, \&quot;Canada\&quot;]     }]   }] } &#x60;&#x60;&#x60;  #### addRule  Adds a new targeting rule to the AI Config. The rule may contain &#x60;clauses&#x60; and serve the variation that &#x60;variationId&#x60; indicates, or serve a percentage rollout that &#x60;rolloutWeights&#x60;, &#x60;rolloutBucketBy&#x60;, and &#x60;rolloutContextKind&#x60; indicate.  If you set &#x60;beforeRuleId&#x60;, this adds the new rule before the indicated rule. Otherwise, adds the new rule to the end of the list.  ##### Parameters  - &#x60;clauses&#x60;: Array of clause objects, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case. - &#x60;beforeRuleId&#x60;: (Optional) ID of a rule. - Either - &#x60;variationId&#x60;: ID of a variation.  or  - &#x60;rolloutWeights&#x60;: (Optional) Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example that uses a &#x60;variationId&#x60;:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addRule\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,   \&quot;clauses\&quot;: [{     \&quot;contextKind\&quot;: \&quot;organization\&quot;,     \&quot;attribute\&quot;: \&quot;located_in\&quot;,     \&quot;op\&quot;: \&quot;in\&quot;,     \&quot;negate\&quot;: false,     \&quot;values\&quot;: [\&quot;Sweden\&quot;, \&quot;Norway\&quot;]   }] }] } &#x60;&#x60;&#x60;  Here&#39;s an example that uses a percentage rollout:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addRule\&quot;,   \&quot;clauses\&quot;: [{     \&quot;contextKind\&quot;: \&quot;organization\&quot;,     \&quot;attribute\&quot;: \&quot;located_in\&quot;,     \&quot;op\&quot;: \&quot;in\&quot;,     \&quot;negate\&quot;: false,     \&quot;values\&quot;: [\&quot;Sweden\&quot;, \&quot;Norway\&quot;]   }],   \&quot;rolloutContextKind\&quot;: \&quot;organization\&quot;,   \&quot;rolloutWeights\&quot;: {     \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;: 15000, // serve 15% this variation     \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;: 85000  // serve 85% this variation   } }] } &#x60;&#x60;&#x60;  #### addTargets  Adds context keys to the individual context targets for the context kind that &#x60;contextKind&#x60; specifies and the variation that &#x60;variationId&#x60; specifies. Returns an error if this causes the AI Config to target the same context key in multiple variations.  ##### Parameters  - &#x60;values&#x60;: List of context keys. - &#x60;contextKind&#x60;: (Optional) Context kind to target, defaults to &#x60;user&#x60; - &#x60;variationId&#x60;: ID of a variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addTargets\&quot;,   \&quot;values\&quot;: [\&quot;context-key-123abc\&quot;, \&quot;context-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; }] } &#x60;&#x60;&#x60;  #### addValuesToClause  Adds &#x60;values&#x60; to the values of the clause that &#x60;ruleId&#x60; and &#x60;clauseId&#x60; indicate. Does not update the context kind, attribute, or operator.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the AI Config. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings, case sensitive.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addValuesToClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;,   \&quot;values\&quot;: [\&quot;beta_testers\&quot;] }] } &#x60;&#x60;&#x60;  #### clearTargets  Removes all individual targets from the variation that &#x60;variationId&#x60; specifies. This includes both user and non-user targets.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;clearTargets\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### removeClauses  Removes the clauses specified by &#x60;clauseIds&#x60; from the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule. - &#x60;clauseIds&#x60;: Array of IDs of clauses in the rule.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeClauses\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseIds\&quot;: [\&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;, \&quot;36a461dc-235e-4b08-97b9-73ce9365873e\&quot;] }] } &#x60;&#x60;&#x60;  #### removeRule  Removes the targeting rule specified by &#x60;ruleId&#x60;. Does nothing if the rule does not exist.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removeRule\&quot;, \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot; } ] } &#x60;&#x60;&#x60;  #### removeTargets  Removes context keys from the individual context targets for the context kind that &#x60;contextKind&#x60; specifies and the variation that &#x60;variationId&#x60; specifies. Does nothing if the flag does not target the context keys.  ##### Parameters  - &#x60;values&#x60;: List of context keys. - &#x60;contextKind&#x60;: (Optional) Context kind to target, defaults to &#x60;user&#x60; - &#x60;variationId&#x60;: ID of a variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeTargets\&quot;,   \&quot;values\&quot;: [\&quot;context-key-123abc\&quot;, \&quot;context-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; }] } &#x60;&#x60;&#x60;  #### removeValuesFromClause  Removes &#x60;values&#x60; from the values of the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60;. Does not update the context kind, attribute, or operator.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings, case sensitive.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeValuesFromClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;,   \&quot;values\&quot;: [\&quot;beta_testers\&quot;] }] } &#x60;&#x60;&#x60;  #### reorderRules  Rearranges the rules to match the order given in &#x60;ruleIds&#x60;. Returns an error if &#x60;ruleIds&#x60; does not match the current set of rules on the AI Config.  ##### Parameters  - &#x60;ruleIds&#x60;: Array of IDs of all rules.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;reorderRules\&quot;,   \&quot;ruleIds\&quot;: [\&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;, \&quot;63c238d1-835d-435e-8f21-c8d5e40b2a3d\&quot;] }] } &#x60;&#x60;&#x60;  #### replaceRules  Removes all targeting rules for the AI Config and replaces them with the list you provide.  ##### Parameters  - &#x60;rules&#x60;: A list of rules.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [   {     \&quot;kind\&quot;: \&quot;replaceRules\&quot;,     \&quot;rules\&quot;: [       {         \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,         \&quot;description\&quot;: \&quot;My new rule\&quot;,         \&quot;clauses\&quot;: [           {             \&quot;contextKind\&quot;: \&quot;user\&quot;,             \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,             \&quot;op\&quot;: \&quot;segmentMatch\&quot;,             \&quot;values\&quot;: [\&quot;test\&quot;]           }         ]       }     ]   } ] } &#x60;&#x60;&#x60;  #### replaceTargets  Removes all existing targeting and replaces it with the list of targets you provide.  ##### Parameters  - &#x60;targets&#x60;: A list of context targeting. Each item in the list includes an optional &#x60;contextKind&#x60; that defaults to &#x60;user&#x60;, a required &#x60;variationId&#x60;, and a required list of &#x60;values&#x60;.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [   {     \&quot;kind\&quot;: \&quot;replaceTargets\&quot;,     \&quot;targets\&quot;: [       {         \&quot;contextKind\&quot;: \&quot;user\&quot;,         \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,         \&quot;values\&quot;: [\&quot;user-key-123abc\&quot;]       },       {         \&quot;contextKind\&quot;: \&quot;device\&quot;,         \&quot;variationId\&quot;: \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;,         \&quot;values\&quot;: [\&quot;device-key-456def\&quot;]       }     ]   } ] } &#x60;&#x60;&#x60;  #### updateClause  Replaces the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60; with &#x60;clause&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;clause&#x60;: New &#x60;clause&#x60; object, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10c7462a-2062-45ba-a8bb-dfb3de0f8af5\&quot;,   \&quot;clause\&quot;: {     \&quot;contextKind\&quot;: \&quot;user\&quot;,     \&quot;attribute\&quot;: \&quot;country\&quot;,     \&quot;op\&quot;: \&quot;in\&quot;,     \&quot;negate\&quot;: false,     \&quot;values\&quot;: [\&quot;Mexico\&quot;, \&quot;Canada\&quot;]   } }] } &#x60;&#x60;&#x60;  #### updateDefaultVariation  Updates the default on or off variation of the AI Config.  ##### Parameters  - &#x60;onVariationValue&#x60;: (Optional) The value of the variation of the new on variation. - &#x60;offVariationValue&#x60;: (Optional) The value of the variation of the new off variation  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateDefaultVariation\&quot;, \&quot;OnVariationValue\&quot;: true, \&quot;OffVariationValue\&quot;: false } ] } &#x60;&#x60;&#x60;  #### updateFallthroughVariationOrRollout  Updates the default or \&quot;fallthrough\&quot; rule for the AI Config, which the AI Config serves when a context matches none of the targeting rules. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percentage rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation.  or  - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example that uses a &#x60;variationId&#x60;:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; }] } &#x60;&#x60;&#x60;  Here&#39;s an example that uses a percentage rollout:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,   \&quot;rolloutContextKind\&quot;: \&quot;user\&quot;,   \&quot;rolloutWeights\&quot;: {     \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;: 15000, // serve 15% this variation     \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;: 85000  // serve 85% this variation   } }] } &#x60;&#x60;&#x60;  #### updateOffVariation  Updates the default off variation to &#x60;variationId&#x60;. The AI Config serves the default off variation when the AI Config&#39;s targeting is **Off**.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateOffVariation\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### updateRuleDescription  Updates the description of the targeting rule.  ##### Parameters  - &#x60;description&#x60;: The new human-readable description for this rule. - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the AI Config.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleDescription\&quot;,   \&quot;description\&quot;: \&quot;New rule description\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot; }] } &#x60;&#x60;&#x60;  #### updateRuleTrackEvents  Updates whether or not LaunchDarkly tracks events for the AI Config associated with this rule.  ##### Parameters  - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the AI Config. - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleTrackEvents\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;trackEvents\&quot;: true }] } &#x60;&#x60;&#x60;  #### updateRuleVariationOrRollout  Updates what &#x60;ruleId&#x60; serves when its clauses evaluate to true. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percent rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule. - &#x60;variationId&#x60;: ID of a variation.  or  - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleVariationOrRollout\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; }] } &#x60;&#x60;&#x60;  #### updateTrackEvents  Updates whether or not LaunchDarkly tracks events for the AI Config, for all rules.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateTrackEvents\&quot;, \&quot;trackEvents\&quot;: true } ] } &#x60;&#x60;&#x60;  #### updateTrackEventsFallthrough  Updates whether or not LaunchDarkly tracks events for the AI Config, for the default rule.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateTrackEventsFallthrough\&quot;, \&quot;trackEvents\&quot;: true } ] } &#x60;&#x60;&#x60; &lt;/details&gt; 
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void patchAIConfigTargetingTest() throws ApiException {
        String projectKey = null;
        String configKey = null;
        AIConfigTargetingPatch aiConfigTargetingPatch = null;
        AIConfigTargeting response = api.patchAIConfigTargeting(projectKey, configKey, aiConfigTargetingPatch);
        // TODO: test validations
    }

    /**
     * Update AI Config variation
     *
     * Edit an existing variation of an AI Config. This creates a new version of the variation.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  Here&#39;s an example: &#x60;&#x60;&#x60;   {     \&quot;messages\&quot;: [       {         \&quot;role\&quot;: \&quot;system\&quot;,         \&quot;content\&quot;: \&quot;The new message\&quot;       }     ]   } &#x60;&#x60;&#x60; 
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void patchAIConfigVariationTest() throws ApiException {
        String projectKey = null;
        String configKey = null;
        String variationKey = null;
        AIConfigVariationPatch aiConfigVariationPatch = null;
        AIConfigVariation response = api.patchAIConfigVariation(projectKey, configKey, variationKey, aiConfigVariationPatch);
        // TODO: test validations
    }

    /**
     * Update AI tool
     *
     * Edit an existing AI tool.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void patchAIToolTest() throws ApiException {
        String projectKey = null;
        String toolKey = null;
        AIToolPatch aiToolPatch = null;
        AITool response = api.patchAITool(projectKey, toolKey, aiToolPatch);
        // TODO: test validations
    }

    /**
     * Update agent graph
     *
     * Edit an existing agent graph.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  If the update includes &#x60;rootConfigKey&#x60; or &#x60;edges&#x60;, both must be present and will be treated as full replacements. 
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void patchAgentGraphTest() throws ApiException {
        String ldAPIVersion = null;
        String projectKey = null;
        String graphKey = null;
        AgentGraphPatch agentGraphPatch = null;
        AgentGraph response = api.patchAgentGraph(ldAPIVersion, projectKey, graphKey, agentGraphPatch);
        // TODO: test validations
    }

    /**
     * Create new AI Config
     *
     * Create a new AI Config within the given project.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void postAIConfigTest() throws ApiException {
        String projectKey = null;
        AIConfigPost aiConfigPost = null;
        AIConfig response = api.postAIConfig(projectKey, aiConfigPost);
        // TODO: test validations
    }

    /**
     * Create AI Config variation
     *
     * Create a new variation for a given AI Config.  The &lt;code&gt;model&lt;/code&gt; in the request body requires a &lt;code&gt;modelName&lt;/code&gt; and &lt;code&gt;parameters&lt;/code&gt;, for example:  &#x60;&#x60;&#x60;   \&quot;model\&quot;: {     \&quot;modelName\&quot;: \&quot;claude-3-opus-20240229\&quot;,     \&quot;parameters\&quot;: {       \&quot;max_tokens\&quot;: 1024     }   } &#x60;&#x60;&#x60; 
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void postAIConfigVariationTest() throws ApiException {
        String projectKey = null;
        String configKey = null;
        AIConfigVariationPost aiConfigVariationPost = null;
        AIConfigVariation response = api.postAIConfigVariation(projectKey, configKey, aiConfigVariationPost);
        // TODO: test validations
    }

    /**
     * Create an AI tool
     *
     * Create an AI tool
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void postAIToolTest() throws ApiException {
        String projectKey = null;
        AIToolPost aiToolPost = null;
        AITool response = api.postAITool(projectKey, aiToolPost);
        // TODO: test validations
    }

    /**
     * Create new agent graph
     *
     * Create a new agent graph within the given project.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void postAgentGraphTest() throws ApiException {
        String ldAPIVersion = null;
        String projectKey = null;
        AgentGraphPost agentGraphPost = null;
        AgentGraph response = api.postAgentGraph(ldAPIVersion, projectKey, agentGraphPost);
        // TODO: test validations
    }

    /**
     * Create an AI model config
     *
     * Create an AI model config. You can use this in any variation for any AI Config in your project.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void postModelConfigTest() throws ApiException {
        String projectKey = null;
        ModelConfigPost modelConfigPost = null;
        ModelConfig response = api.postModelConfig(projectKey, modelConfigPost);
        // TODO: test validations
    }

    /**
     * Add AI models to the restricted list
     *
     * Add AI models, by key, to the restricted list. Keys are included in the response from the [List AI model configs](https://launchdarkly.com/docs/api/ai-configs/list-model-configs) endpoint.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void postRestrictedModelsTest() throws ApiException {
        String projectKey = null;
        RestrictedModelsRequest restrictedModelsRequest = null;
        RestrictedModelsResponse response = api.postRestrictedModels(projectKey, restrictedModelsRequest);
        // TODO: test validations
    }

}
