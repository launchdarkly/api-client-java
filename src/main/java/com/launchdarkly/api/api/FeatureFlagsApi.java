/*
 * LaunchDarkly REST API
 * # Overview  ## Authentication  All REST API resources are authenticated with either [personal or service access tokens](https://docs.launchdarkly.com/home/account-security/api-access-tokens), or session cookies. Other authentication mechanisms are not supported. You can manage personal access tokens on your [**Account settings**](https://app.launchdarkly.com/settings/tokens) page.  LaunchDarkly also has SDK keys, mobile keys, and client-side IDs that are used by our server-side SDKs, mobile SDKs, and JavaScript-based SDKs, respectively. **These keys cannot be used to access our REST API**. These keys are environment-specific, and can only perform read-only operations such as fetching feature flag settings.  | Auth mechanism                                                                                  | Allowed resources                                                                                     | Use cases                                          | | ----------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------- | -------------------------------------------------- | | [Personal or service access tokens](https://docs.launchdarkly.com/home/account-security/api-access-tokens) | Can be customized on a per-token basis                                                                | Building scripts, custom integrations, data export. | | SDK keys                                                                                        | Can only access read-only resources specific to server-side SDKs. Restricted to a single environment. | Server-side SDKs                     | | Mobile keys                                                                                     | Can only access read-only resources specific to mobile SDKs, and only for flags marked available to mobile keys. Restricted to a single environment.           | Mobile SDKs                                        | | Client-side ID                                                                                  | Can only access read-only resources specific to JavaScript-based client-side SDKs, and only for flags marked available to client-side. Restricted to a single environment.           | Client-side JavaScript                             |  > #### Keep your access tokens and SDK keys private > > Access tokens should _never_ be exposed in untrusted contexts. Never put an access token in client-side JavaScript, or embed it in a mobile application. LaunchDarkly has special mobile keys that you can embed in mobile apps. If you accidentally expose an access token or SDK key, you can reset it from your [**Account settings**](https://app.launchdarkly.com/settings/tokens) page. > > The client-side ID is safe to embed in untrusted contexts. It's designed for use in client-side JavaScript.  ### Authentication using request header  The preferred way to authenticate with the API is by adding an `Authorization` header containing your access token to your requests. The value of the `Authorization` header must be your access token.  Manage personal access tokens from the [**Account settings**](https://app.launchdarkly.com/settings/tokens) page.  ### Authentication using session cookie  For testing purposes, you can make API calls directly from your web browser. If you are logged in to the LaunchDarkly application, the API will use your existing session to authenticate calls.  If you have a [role](https://docs.launchdarkly.com/home/team/built-in-roles) other than Admin, or have a [custom role](https://docs.launchdarkly.com/home/team/custom-roles) defined, you may not have permission to perform some API calls. You will receive a `401` response code in that case.  > ### Modifying the Origin header causes an error > > LaunchDarkly validates that the Origin header for any API request authenticated by a session cookie matches the expected Origin header. The expected Origin header is `https://app.launchdarkly.com`. > > If the Origin header does not match what's expected, LaunchDarkly returns an error. This error can prevent the LaunchDarkly app from working correctly. > > Any browser extension that intentionally changes the Origin header can cause this problem. For example, the `Allow-Control-Allow-Origin: *` Chrome extension changes the Origin header to `http://evil.com` and causes the app to fail. > > To prevent this error, do not modify your Origin header. > > LaunchDarkly does not require origin matching when authenticating with an access token, so this issue does not affect normal API usage.  ## Representations  All resources expect and return JSON response bodies. Error responses also send a JSON body. To learn more about the error format of the API, read [Errors](/#section/Overview/Errors).  In practice this means that you always get a response with a `Content-Type` header set to `application/json`.  In addition, request bodies for `PATCH`, `POST`, and `PUT` requests must be encoded as JSON with a `Content-Type` header set to `application/json`.  ### Summary and detailed representations  When you fetch a list of resources, the response includes only the most important attributes of each resource. This is a _summary representation_ of the resource. When you fetch an individual resource, such as a single feature flag, you receive a _detailed representation_ of the resource.  The best way to find a detailed representation is to follow links. Every summary representation includes a link to its detailed representation.  ### Expanding responses  Sometimes the detailed representation of a resource does not include all of the attributes of the resource by default. If this is the case, the request method will clearly document this and describe which attributes you can include in an expanded response.  To include the additional attributes, append the `expand` request parameter to your request and add a comma-separated list of the attributes to include. For example, when you append `?expand=members,roles` to the [Get team](/tag/Teams#operation/getTeam) endpoint, the expanded response includes both of these attributes.  ### Links and addressability  The best way to navigate the API is by following links. These are attributes in representations that link to other resources. The API always uses the same format for links:  - Links to other resources within the API are encapsulated in a `_links` object - If the resource has a corresponding link to HTML content on the site, it is stored in a special `_site` link  Each link has two attributes:  - An `href`, which contains the URL - A `type`, which describes the content type  For example, a feature resource might return the following:  ```json {   \"_links\": {     \"parent\": {       \"href\": \"/api/features\",       \"type\": \"application/json\"     },     \"self\": {       \"href\": \"/api/features/sort.order\",       \"type\": \"application/json\"     }   },   \"_site\": {     \"href\": \"/features/sort.order\",     \"type\": \"text/html\"   } } ```  From this, you can navigate to the parent collection of features by following the `parent` link, or navigate to the site page for the feature by following the `_site` link.  Collections are always represented as a JSON object with an `items` attribute containing an array of representations. Like all other representations, collections have `_links` defined at the top level.  Paginated collections include `first`, `last`, `next`, and `prev` links containing a URL with the respective set of elements in the collection.  ## Updates  Resources that accept partial updates use the `PATCH` verb. Most resources support the [JSON patch](/reference#updates-using-json-patch) format. Some resources also support the [JSON merge patch](/reference#updates-using-json-merge-patch) format, and some resources support the [semantic patch](/reference#updates-using-semantic-patch) format, which is a way to specify the modifications to perform as a set of executable instructions. Each resource supports optional [comments](/reference#updates-with-comments) that you can submit with updates. Comments appear in outgoing webhooks, the audit log, and other integrations.  When a resource supports both JSON patch and semantic patch, we document both in the request method. However, the specific request body fields and descriptions included in our documentation only match one type of patch or the other.  ### Updates using JSON patch  [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) is a way to specify the modifications to perform on a resource. JSON patch uses paths and a limited set of operations to describe how to transform the current state of the resource into a new state. JSON patch documents are always arrays, where each element contains an operation, a path to the field to update, and the new value.  For example, in this feature flag representation:  ```json {     \"name\": \"New recommendations engine\",     \"key\": \"engine.enable\",     \"description\": \"This is the description\",     ... } ``` You can change the feature flag's description with the following patch document:  ```json [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"This is the new description\" }] ```  You can specify multiple modifications to perform in a single request. You can also test that certain preconditions are met before applying the patch:  ```json [   { \"op\": \"test\", \"path\": \"/version\", \"value\": 10 },   { \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" } ] ```  The above patch request tests whether the feature flag's `version` is `10`, and if so, changes the feature flag's description.  Attributes that are not editable, such as a resource's `_links`, have names that start with an underscore.  ### Updates using JSON merge patch  [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) is another format for specifying the modifications to perform on a resource. JSON merge patch is less expressive than JSON patch. However, in many cases it is simpler to construct a merge patch document. For example, you can change a feature flag's description with the following merge patch document:  ```json {   \"description\": \"New flag description\" } ```  ### Updates using semantic patch  Some resources support the semantic patch format. A semantic patch is a way to specify the modifications to perform on a resource as a set of executable instructions.  Semantic patch allows you to be explicit about intent using precise, custom instructions. In many cases, you can define semantic patch instructions independently of the current state of the resource. This can be useful when defining a change that may be applied at a future date.  To make a semantic patch request, you must append `domain-model=launchdarkly.semanticpatch` to your `Content-Type` header.  Here's how:  ``` Content-Type: application/json; domain-model=launchdarkly.semanticpatch ```  If you call a semantic patch resource without this header, you will receive a `400` response because your semantic patch will be interpreted as a JSON patch.  The body of a semantic patch request takes the following properties:  * `comment` (string): (Optional) A description of the update. * `environmentKey` (string): (Required for some resources only) The environment key. * `instructions` (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a `kind` property that indicates the instruction. If the instruction requires parameters, you must include those parameters as additional fields in the object. The documentation for each resource that supports semantic patch includes the available instructions and any additional parameters.  For example:  ```json {   \"comment\": \"optional comment\",   \"instructions\": [ {\"kind\": \"turnFlagOn\"} ] } ```  If any instruction in the patch encounters an error, the endpoint returns an error and will not change the resource. In general, each instruction silently does nothing if the resource is already in the state you request.  ### Updates with comments  You can submit optional comments with `PATCH` changes.  To submit a comment along with a JSON patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"patch\": [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" }] } ```  To submit a comment along with a JSON merge patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"merge\": { \"description\": \"New flag description\" } } ```  To submit a comment along with a semantic patch, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"instructions\": [ {\"kind\": \"turnFlagOn\"} ] } ```  ## Errors  The API always returns errors in a common format. Here's an example:  ```json {   \"code\": \"invalid_request\",   \"message\": \"A feature with that key already exists\",   \"id\": \"30ce6058-87da-11e4-b116-123b93f75cba\" } ```  The `code` indicates the general class of error. The `message` is a human-readable explanation of what went wrong. The `id` is a unique identifier. Use it when you're working with LaunchDarkly Support to debug a problem with a specific API call.  ### HTTP status error response codes  | Code | Definition        | Description                                                                                       | Possible Solution                                                | | ---- | ----------------- | ------------------------------------------------------------------------------------------- | ---------------------------------------------------------------- | | 400  | Invalid request       | The request cannot be understood.                                    | Ensure JSON syntax in request body is correct.                   | | 401  | Invalid access token      | Requestor is unauthorized or does not have permission for this API call.                                                | Ensure your API access token is valid and has the appropriate permissions.                                     | | 403  | Forbidden         | Requestor does not have access to this resource.                                                | Ensure that the account member or access token has proper permissions set. | | 404  | Invalid resource identifier | The requested resource is not valid. | Ensure that the resource is correctly identified by ID or key. | | 405  | Method not allowed | The request method is not allowed on this resource. | Ensure that the HTTP verb is correct. | | 409  | Conflict          | The API request can not be completed because it conflicts with a concurrent API request. | Retry your request.                                              | | 422  | Unprocessable entity | The API request can not be completed because the update description can not be understood. | Ensure that the request body is correct for the type of patch you are using, either JSON patch or semantic patch. | 429  | Too many requests | Read [Rate limiting](/#section/Overview/Rate-limiting).                                               | Wait and try again later.                                        |  ## CORS  The LaunchDarkly API supports Cross Origin Resource Sharing (CORS) for AJAX requests from any origin. If an `Origin` header is given in a request, it will be echoed as an explicitly allowed origin. Otherwise the request returns a wildcard, `Access-Control-Allow-Origin: *`. For more information on CORS, read the [CORS W3C Recommendation](http://www.w3.org/TR/cors). Example CORS headers might look like:  ```http Access-Control-Allow-Headers: Accept, Content-Type, Content-Length, Accept-Encoding, Authorization Access-Control-Allow-Methods: OPTIONS, GET, DELETE, PATCH Access-Control-Allow-Origin: * Access-Control-Max-Age: 300 ```  You can make authenticated CORS calls just as you would make same-origin calls, using either [token or session-based authentication](/#section/Overview/Authentication). If you are using session authentication, you should set the `withCredentials` property for your `xhr` request to `true`. You should never expose your access tokens to untrusted entities.  ## Rate limiting  We use several rate limiting strategies to ensure the availability of our APIs. Rate-limited calls to our APIs return a `429` status code. Calls to our APIs include headers indicating the current rate limit status. The specific headers returned depend on the API route being called. The limits differ based on the route, authentication mechanism, and other factors. Routes that are not rate limited may not contain any of the headers described below.  > ### Rate limiting and SDKs > > LaunchDarkly SDKs are never rate limited and do not use the API endpoints defined here. LaunchDarkly uses a different set of approaches, including streaming/server-sent events and a global CDN, to ensure availability to the routes used by LaunchDarkly SDKs.  ### Global rate limits  Authenticated requests are subject to a global limit. This is the maximum number of calls that your account can make to the API per ten seconds. All service and personal access tokens on the account share this limit, so exceeding the limit with one access token will impact other tokens. Calls that are subject to global rate limits may return the headers below:  | Header name                    | Description                                                                      | | ------------------------------ | -------------------------------------------------------------------------------- | | `X-Ratelimit-Global-Remaining` | The maximum number of requests the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`            | The time at which the current rate limit window resets in epoch milliseconds.    |  We do not publicly document the specific number of calls that can be made globally. This limit may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limit.  ### Route-level rate limits  Some authenticated routes have custom rate limits. These also reset every ten seconds. Any service or personal access tokens hitting the same route share this limit, so exceeding the limit with one access token may impact other tokens. Calls that are subject to route-level rate limits return the headers below:  | Header name                   | Description                                                                                           | | ----------------------------- | ----------------------------------------------------------------------------------------------------- | | `X-Ratelimit-Route-Remaining` | The maximum number of requests to the current route the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`           | The time at which the current rate limit window resets in epoch milliseconds.                         |  A _route_ represents a specific URL pattern and verb. For example, the [Delete environment](/tag/Environments#operation/deleteEnvironment) endpoint is considered a single route, and each call to delete an environment counts against your route-level rate limit for that route.  We do not publicly document the specific number of calls that an account can make to each endpoint per ten seconds. These limits may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limits.  ### IP-based rate limiting  We also employ IP-based rate limiting on some API routes. If you hit an IP-based rate limit, your API response will include a `Retry-After` header indicating how long to wait before re-trying the call. Clients must wait at least `Retry-After` seconds before making additional calls to our API, and should employ jitter and backoff strategies to avoid triggering rate limits again.  ## OpenAPI (Swagger) and client libraries  We have a [complete OpenAPI (Swagger) specification](https://app.launchdarkly.com/api/v2/openapi.json) for our API.  We auto-generate multiple client libraries based on our OpenAPI specification. To learn more, visit the [collection of client libraries on GitHub](https://github.com/search?q=topic%3Alaunchdarkly-api+org%3Alaunchdarkly&type=Repositories). You can also use this specification to generate client libraries to interact with our REST API in your language of choice.  Our OpenAPI specification is supported by several API-based tools such as Postman and Insomnia. In many cases, you can directly import our specification to explore our APIs.  ## Method overriding  Some firewalls and HTTP clients restrict the use of verbs other than `GET` and `POST`. In those environments, our API endpoints that use `DELETE`, `PATCH`, and `PUT` verbs are inaccessible.  To avoid this issue, our API supports the `X-HTTP-Method-Override` header, allowing clients to \"tunnel\" `DELETE`, `PATCH`, and `PUT` requests using a `POST` request.  For example, to call a `PATCH` endpoint using a `POST` request, you can include `X-HTTP-Method-Override:PATCH` as a header.  ## Beta resources  We sometimes release new API resources in **beta** status before we release them with general availability.  Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  We try to promote resources into general availability as quickly as possible. This happens after sufficient testing and when we're satisfied that we no longer need to make backwards-incompatible changes.  We mark beta resources with a \"Beta\" callout in our documentation, pictured below:  > ### This feature is in beta > > To use this feature, pass in a header including the `LD-API-Version` key with value set to `beta`. Use this header with each call. To learn more, read [Beta resources](/#section/Overview/Beta-resources). > > Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  ### Using beta resources  To use a beta resource, you must include a header in the request. If you call a beta resource without this header, you receive a `403` response.  Use this header:  ``` LD-API-Version: beta ```  ## Federal environments  The version of LaunchDarkly that is available on domains controlled by the United States government is different from the version of LaunchDarkly available to the general public. If you are an employee or contractor for a United States federal agency and use LaunchDarkly in your work, you likely use the federal instance of LaunchDarkly.  If you are working in the federal instance of LaunchDarkly, the base URI for each request is `https://app.launchdarkly.us`. In the \"Try it\" sandbox for each request, click the request path to view the complete resource path for the federal environment.  To learn more, read [LaunchDarkly in federal environments](https://docs.launchdarkly.com/home/advanced/federal).  ## Versioning  We try hard to keep our REST API backwards compatible, but we occasionally have to make backwards-incompatible changes in the process of shipping new features. These breaking changes can cause unexpected behavior if you don't prepare for them accordingly.  Updates to our REST API include support for the latest features in LaunchDarkly. We also release a new version of our REST API every time we make a breaking change. We provide simultaneous support for multiple API versions so you can migrate from your current API version to a new version at your own pace.  ### Setting the API version per request  You can set the API version on a specific request by sending an `LD-API-Version` header, as shown in the example below:  ``` LD-API-Version: 20220603 ```  The header value is the version number of the API version you would like to request. The number for each version corresponds to the date the version was released in `yyyymmdd` format. In the example above the version `20220603` corresponds to June 03, 2022.  ### Setting the API version per access token  When you create an access token, you must specify a specific version of the API to use. This ensures that integrations using this token cannot be broken by version changes.  Tokens created before versioning was released have their version set to `20160426`, which is the version of the API that existed before the current versioning scheme, so that they continue working the same way they did before versioning.  If you would like to upgrade your integration to use a new API version, you can explicitly set the header described above.  > ### Best practice: Set the header for every client or integration > > We recommend that you set the API version header explicitly in any client or integration you build. > > Only rely on the access token API version during manual testing.  ### API version changelog  |<div style=\"width:75px\">Version</div> | Changes | End of life (EOL) |---|---|---| | `20220603` | <ul><li>Changed the [list projects](/tag/Projects#operation/getProjects) return value:<ul><li>Response is now paginated with a default limit of `20`.</li><li>Added support for filter and sort.</li><li>The project `environments` field is now expandable. This field is omitted by default.</li></ul></li><li>Changed the [get project](/tag/Projects#operation/getProject) return value:<ul><li>The `environments` field is now expandable. This field is omitted by default.</li></ul></li></ul> | Current | | `20210729` | <ul><li>Changed the [create approval request](/tag/Approvals#operation/postApprovalRequest) return value. It now returns HTTP Status Code `201` instead of `200`.</li><li> Changed the [get users](/tag/Users#operation/getUser) return value. It now returns a user record, not a user. </li><li>Added additional optional fields to environment, segments, flags, members, and segments, including the ability to create Big Segments. </li><li> Added default values for flag variations when new environments are created. </li><li>Added filtering and pagination for getting flags and members, including `limit`, `number`, `filter`, and `sort` query parameters. </li><li>Added endpoints for expiring user targets for flags and segments, scheduled changes, access tokens, Relay Proxy configuration, integrations and subscriptions, and approvals. </li></ul> | 2023-06-03 | | `20191212` | <ul><li>[List feature flags](/tag/Feature-flags#operation/getFeatureFlags) now defaults to sending summaries of feature flag configurations, equivalent to setting the query parameter `summary=true`. Summaries omit flag targeting rules and individual user targets from the payload. </li><li> Added endpoints for flags, flag status, projects, environments, audit logs, members, users, custom roles, segments, usage, streams, events, and data export. </li></ul> | 2022-07-29 | | `20160426` | <ul><li>Initial versioning of API. Tokens created before versioning have their version set to this.</li></ul> | 2020-12-12 | 
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


import com.launchdarkly.api.model.ExpiringTargetGetResponse;
import com.launchdarkly.api.model.ExpiringTargetPatchResponse;
import com.launchdarkly.api.model.ExpiringUserTargetGetResponse;
import com.launchdarkly.api.model.ExpiringUserTargetPatchResponse;
import com.launchdarkly.api.model.FeatureFlag;
import com.launchdarkly.api.model.FeatureFlagBody;
import com.launchdarkly.api.model.FeatureFlagStatusAcrossEnvironments;
import com.launchdarkly.api.model.FeatureFlagStatuses;
import com.launchdarkly.api.model.FeatureFlags;
import com.launchdarkly.api.model.FlagCopyConfigPost;
import com.launchdarkly.api.model.FlagStatusRep;
import com.launchdarkly.api.model.ForbiddenErrorRep;
import com.launchdarkly.api.model.InvalidRequestErrorRep;
import com.launchdarkly.api.model.MethodNotAllowedErrorRep;
import com.launchdarkly.api.model.NotFoundErrorRep;
import com.launchdarkly.api.model.PatchFlagsRequest;
import com.launchdarkly.api.model.PatchWithComment;
import com.launchdarkly.api.model.RateLimitedErrorRep;
import com.launchdarkly.api.model.StatusConflictErrorRep;
import com.launchdarkly.api.model.UnauthorizedErrorRep;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;

public class FeatureFlagsApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public FeatureFlagsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public FeatureFlagsApi(ApiClient apiClient) {
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
     * Build call for copyFeatureFlag
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key. The key identifies the flag in your code. (required)
     * @param flagCopyConfigPost  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Global flag response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call copyFeatureFlagCall(String projectKey, String featureFlagKey, FlagCopyConfigPost flagCopyConfigPost, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = flagCopyConfigPost;

        // create path and map variables
        String localVarPath = "/api/v2/flags/{projectKey}/{featureFlagKey}/copy"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()))
            .replaceAll("\\{" + "featureFlagKey" + "\\}", localVarApiClient.escapeString(featureFlagKey.toString()));

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
    private okhttp3.Call copyFeatureFlagValidateBeforeCall(String projectKey, String featureFlagKey, FlagCopyConfigPost flagCopyConfigPost, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling copyFeatureFlag(Async)");
        }
        
        // verify the required parameter 'featureFlagKey' is set
        if (featureFlagKey == null) {
            throw new ApiException("Missing the required parameter 'featureFlagKey' when calling copyFeatureFlag(Async)");
        }
        
        // verify the required parameter 'flagCopyConfigPost' is set
        if (flagCopyConfigPost == null) {
            throw new ApiException("Missing the required parameter 'flagCopyConfigPost' when calling copyFeatureFlag(Async)");
        }
        

        okhttp3.Call localVarCall = copyFeatureFlagCall(projectKey, featureFlagKey, flagCopyConfigPost, _callback);
        return localVarCall;

    }

    /**
     * Copy feature flag
     *  &gt; ### Copying flag settings is an Enterprise feature &gt; &gt; Copying flag settings is available to customers on an Enterprise plan. To learn more, [read about our pricing](https://launchdarkly.com/pricing/). To upgrade your plan, [contact Sales](https://launchdarkly.com/contact-sales/).  Copy flag settings from a source environment to a target environment.  By default, this operation copies the entire flag configuration. You can use the &#x60;includedActions&#x60; or &#x60;excludedActions&#x60; to specify that only part of the flag configuration is copied.  If you provide the optional &#x60;currentVersion&#x60; of a flag, this operation tests to ensure that the current flag version in the environment matches the version you&#39;ve specified. The operation rejects attempts to copy flag settings if the environment&#39;s current version  of the flag does not match the version you&#39;ve specified. You can use this to enforce optimistic locking on copy attempts. 
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key. The key identifies the flag in your code. (required)
     * @param flagCopyConfigPost  (required)
     * @return FeatureFlag
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Global flag response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public FeatureFlag copyFeatureFlag(String projectKey, String featureFlagKey, FlagCopyConfigPost flagCopyConfigPost) throws ApiException {
        ApiResponse<FeatureFlag> localVarResp = copyFeatureFlagWithHttpInfo(projectKey, featureFlagKey, flagCopyConfigPost);
        return localVarResp.getData();
    }

    /**
     * Copy feature flag
     *  &gt; ### Copying flag settings is an Enterprise feature &gt; &gt; Copying flag settings is available to customers on an Enterprise plan. To learn more, [read about our pricing](https://launchdarkly.com/pricing/). To upgrade your plan, [contact Sales](https://launchdarkly.com/contact-sales/).  Copy flag settings from a source environment to a target environment.  By default, this operation copies the entire flag configuration. You can use the &#x60;includedActions&#x60; or &#x60;excludedActions&#x60; to specify that only part of the flag configuration is copied.  If you provide the optional &#x60;currentVersion&#x60; of a flag, this operation tests to ensure that the current flag version in the environment matches the version you&#39;ve specified. The operation rejects attempts to copy flag settings if the environment&#39;s current version  of the flag does not match the version you&#39;ve specified. You can use this to enforce optimistic locking on copy attempts. 
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key. The key identifies the flag in your code. (required)
     * @param flagCopyConfigPost  (required)
     * @return ApiResponse&lt;FeatureFlag&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Global flag response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<FeatureFlag> copyFeatureFlagWithHttpInfo(String projectKey, String featureFlagKey, FlagCopyConfigPost flagCopyConfigPost) throws ApiException {
        okhttp3.Call localVarCall = copyFeatureFlagValidateBeforeCall(projectKey, featureFlagKey, flagCopyConfigPost, null);
        Type localVarReturnType = new TypeToken<FeatureFlag>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Copy feature flag (asynchronously)
     *  &gt; ### Copying flag settings is an Enterprise feature &gt; &gt; Copying flag settings is available to customers on an Enterprise plan. To learn more, [read about our pricing](https://launchdarkly.com/pricing/). To upgrade your plan, [contact Sales](https://launchdarkly.com/contact-sales/).  Copy flag settings from a source environment to a target environment.  By default, this operation copies the entire flag configuration. You can use the &#x60;includedActions&#x60; or &#x60;excludedActions&#x60; to specify that only part of the flag configuration is copied.  If you provide the optional &#x60;currentVersion&#x60; of a flag, this operation tests to ensure that the current flag version in the environment matches the version you&#39;ve specified. The operation rejects attempts to copy flag settings if the environment&#39;s current version  of the flag does not match the version you&#39;ve specified. You can use this to enforce optimistic locking on copy attempts. 
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key. The key identifies the flag in your code. (required)
     * @param flagCopyConfigPost  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Global flag response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call copyFeatureFlagAsync(String projectKey, String featureFlagKey, FlagCopyConfigPost flagCopyConfigPost, final ApiCallback<FeatureFlag> _callback) throws ApiException {

        okhttp3.Call localVarCall = copyFeatureFlagValidateBeforeCall(projectKey, featureFlagKey, flagCopyConfigPost, _callback);
        Type localVarReturnType = new TypeToken<FeatureFlag>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteFeatureFlag
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key. The key identifies the flag in your code. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteFeatureFlagCall(String projectKey, String featureFlagKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/flags/{projectKey}/{featureFlagKey}"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()))
            .replaceAll("\\{" + "featureFlagKey" + "\\}", localVarApiClient.escapeString(featureFlagKey.toString()));

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
    private okhttp3.Call deleteFeatureFlagValidateBeforeCall(String projectKey, String featureFlagKey, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling deleteFeatureFlag(Async)");
        }
        
        // verify the required parameter 'featureFlagKey' is set
        if (featureFlagKey == null) {
            throw new ApiException("Missing the required parameter 'featureFlagKey' when calling deleteFeatureFlag(Async)");
        }
        

        okhttp3.Call localVarCall = deleteFeatureFlagCall(projectKey, featureFlagKey, _callback);
        return localVarCall;

    }

    /**
     * Delete feature flag
     * Delete a feature flag in all environments. Use with caution: only delete feature flags your application no longer uses.
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key. The key identifies the flag in your code. (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public void deleteFeatureFlag(String projectKey, String featureFlagKey) throws ApiException {
        deleteFeatureFlagWithHttpInfo(projectKey, featureFlagKey);
    }

    /**
     * Delete feature flag
     * Delete a feature flag in all environments. Use with caution: only delete feature flags your application no longer uses.
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key. The key identifies the flag in your code. (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteFeatureFlagWithHttpInfo(String projectKey, String featureFlagKey) throws ApiException {
        okhttp3.Call localVarCall = deleteFeatureFlagValidateBeforeCall(projectKey, featureFlagKey, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete feature flag (asynchronously)
     * Delete a feature flag in all environments. Use with caution: only delete feature flags your application no longer uses.
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key. The key identifies the flag in your code. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteFeatureFlagAsync(String projectKey, String featureFlagKey, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteFeatureFlagValidateBeforeCall(projectKey, featureFlagKey, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getExpiringContextTargets
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring target response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getExpiringContextTargetsCall(String projectKey, String environmentKey, String featureFlagKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/flags/{projectKey}/{featureFlagKey}/expiring-targets/{environmentKey}"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()))
            .replaceAll("\\{" + "environmentKey" + "\\}", localVarApiClient.escapeString(environmentKey.toString()))
            .replaceAll("\\{" + "featureFlagKey" + "\\}", localVarApiClient.escapeString(featureFlagKey.toString()));

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
    private okhttp3.Call getExpiringContextTargetsValidateBeforeCall(String projectKey, String environmentKey, String featureFlagKey, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getExpiringContextTargets(Async)");
        }
        
        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling getExpiringContextTargets(Async)");
        }
        
        // verify the required parameter 'featureFlagKey' is set
        if (featureFlagKey == null) {
            throw new ApiException("Missing the required parameter 'featureFlagKey' when calling getExpiringContextTargets(Async)");
        }
        

        okhttp3.Call localVarCall = getExpiringContextTargetsCall(projectKey, environmentKey, featureFlagKey, _callback);
        return localVarCall;

    }

    /**
     * Get expiring context targets for feature flag
     * Get a list of context targets on a feature flag that are scheduled for removal.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @return ExpiringTargetGetResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring target response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ExpiringTargetGetResponse getExpiringContextTargets(String projectKey, String environmentKey, String featureFlagKey) throws ApiException {
        ApiResponse<ExpiringTargetGetResponse> localVarResp = getExpiringContextTargetsWithHttpInfo(projectKey, environmentKey, featureFlagKey);
        return localVarResp.getData();
    }

    /**
     * Get expiring context targets for feature flag
     * Get a list of context targets on a feature flag that are scheduled for removal.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @return ApiResponse&lt;ExpiringTargetGetResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring target response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ExpiringTargetGetResponse> getExpiringContextTargetsWithHttpInfo(String projectKey, String environmentKey, String featureFlagKey) throws ApiException {
        okhttp3.Call localVarCall = getExpiringContextTargetsValidateBeforeCall(projectKey, environmentKey, featureFlagKey, null);
        Type localVarReturnType = new TypeToken<ExpiringTargetGetResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get expiring context targets for feature flag (asynchronously)
     * Get a list of context targets on a feature flag that are scheduled for removal.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring target response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getExpiringContextTargetsAsync(String projectKey, String environmentKey, String featureFlagKey, final ApiCallback<ExpiringTargetGetResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = getExpiringContextTargetsValidateBeforeCall(projectKey, environmentKey, featureFlagKey, _callback);
        Type localVarReturnType = new TypeToken<ExpiringTargetGetResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getExpiringUserTargets
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getExpiringUserTargetsCall(String projectKey, String environmentKey, String featureFlagKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/flags/{projectKey}/{featureFlagKey}/expiring-user-targets/{environmentKey}"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()))
            .replaceAll("\\{" + "environmentKey" + "\\}", localVarApiClient.escapeString(environmentKey.toString()))
            .replaceAll("\\{" + "featureFlagKey" + "\\}", localVarApiClient.escapeString(featureFlagKey.toString()));

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
    private okhttp3.Call getExpiringUserTargetsValidateBeforeCall(String projectKey, String environmentKey, String featureFlagKey, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getExpiringUserTargets(Async)");
        }
        
        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling getExpiringUserTargets(Async)");
        }
        
        // verify the required parameter 'featureFlagKey' is set
        if (featureFlagKey == null) {
            throw new ApiException("Missing the required parameter 'featureFlagKey' when calling getExpiringUserTargets(Async)");
        }
        

        okhttp3.Call localVarCall = getExpiringUserTargetsCall(projectKey, environmentKey, featureFlagKey, _callback);
        return localVarCall;

    }

    /**
     * Get expiring user targets for feature flag
     *  &gt; ### Contexts are now available &gt; &gt; After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should use [Get expiring context targets for feature flag](/tag/Feature-flags#operation/getExpiringContextTargets) instead of this endpoint. To learn more, read [Contexts](https://docs.launchdarkly.com/home/contexts).  Get a list of user targets on a feature flag that are scheduled for removal. 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @return ExpiringUserTargetGetResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ExpiringUserTargetGetResponse getExpiringUserTargets(String projectKey, String environmentKey, String featureFlagKey) throws ApiException {
        ApiResponse<ExpiringUserTargetGetResponse> localVarResp = getExpiringUserTargetsWithHttpInfo(projectKey, environmentKey, featureFlagKey);
        return localVarResp.getData();
    }

    /**
     * Get expiring user targets for feature flag
     *  &gt; ### Contexts are now available &gt; &gt; After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should use [Get expiring context targets for feature flag](/tag/Feature-flags#operation/getExpiringContextTargets) instead of this endpoint. To learn more, read [Contexts](https://docs.launchdarkly.com/home/contexts).  Get a list of user targets on a feature flag that are scheduled for removal. 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @return ApiResponse&lt;ExpiringUserTargetGetResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ExpiringUserTargetGetResponse> getExpiringUserTargetsWithHttpInfo(String projectKey, String environmentKey, String featureFlagKey) throws ApiException {
        okhttp3.Call localVarCall = getExpiringUserTargetsValidateBeforeCall(projectKey, environmentKey, featureFlagKey, null);
        Type localVarReturnType = new TypeToken<ExpiringUserTargetGetResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get expiring user targets for feature flag (asynchronously)
     *  &gt; ### Contexts are now available &gt; &gt; After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should use [Get expiring context targets for feature flag](/tag/Feature-flags#operation/getExpiringContextTargets) instead of this endpoint. To learn more, read [Contexts](https://docs.launchdarkly.com/home/contexts).  Get a list of user targets on a feature flag that are scheduled for removal. 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getExpiringUserTargetsAsync(String projectKey, String environmentKey, String featureFlagKey, final ApiCallback<ExpiringUserTargetGetResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = getExpiringUserTargetsValidateBeforeCall(projectKey, environmentKey, featureFlagKey, _callback);
        Type localVarReturnType = new TypeToken<ExpiringUserTargetGetResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getFeatureFlag
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param env Filter configurations by environment (optional)
     * @param expand A comma-separated list of fields to expand in the response. Supported fields are explained above. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Global flag response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getFeatureFlagCall(String projectKey, String featureFlagKey, String env, String expand, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/flags/{projectKey}/{featureFlagKey}"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()))
            .replaceAll("\\{" + "featureFlagKey" + "\\}", localVarApiClient.escapeString(featureFlagKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (env != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("env", env));
        }

        if (expand != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("expand", expand));
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
    private okhttp3.Call getFeatureFlagValidateBeforeCall(String projectKey, String featureFlagKey, String env, String expand, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getFeatureFlag(Async)");
        }
        
        // verify the required parameter 'featureFlagKey' is set
        if (featureFlagKey == null) {
            throw new ApiException("Missing the required parameter 'featureFlagKey' when calling getFeatureFlag(Async)");
        }
        

        okhttp3.Call localVarCall = getFeatureFlagCall(projectKey, featureFlagKey, env, expand, _callback);
        return localVarCall;

    }

    /**
     * Get feature flag
     * Get a single feature flag by key. By default, this returns the configurations for all environments. You can filter environments with the &#x60;env&#x60; query parameter. For example, setting &#x60;env&#x3D;production&#x60; restricts the returned configurations to just the &#x60;production&#x60; environment.  &gt; #### Recommended use &gt; &gt; This endpoint can return a large amount of information. Specifying one or multiple environments with the &#x60;env&#x60; parameter can decrease response time and overall payload size. We recommend using this parameter to return only the environments relevant to your query.  ### Expanding response  LaunchDarkly supports the &#x60;expand&#x60; query param to include additional fields in the response, with the following fields:  - &#x60;evaluation&#x60; includes evaluation information within returned environments, including which context kinds the flag has been evaluated for in the past 30 days  - &#x60;migrationSettings&#x60; includes migration settings information within the flag and within returned environments. These settings are only included for migration flags, that is, where &#x60;purpose&#x60; is &#x60;migration&#x60;.  For example, &#x60;expand&#x3D;evaluation&#x60; includes the &#x60;evaluation&#x60; field in the response. 
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param env Filter configurations by environment (optional)
     * @param expand A comma-separated list of fields to expand in the response. Supported fields are explained above. (optional)
     * @return FeatureFlag
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Global flag response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public FeatureFlag getFeatureFlag(String projectKey, String featureFlagKey, String env, String expand) throws ApiException {
        ApiResponse<FeatureFlag> localVarResp = getFeatureFlagWithHttpInfo(projectKey, featureFlagKey, env, expand);
        return localVarResp.getData();
    }

    /**
     * Get feature flag
     * Get a single feature flag by key. By default, this returns the configurations for all environments. You can filter environments with the &#x60;env&#x60; query parameter. For example, setting &#x60;env&#x3D;production&#x60; restricts the returned configurations to just the &#x60;production&#x60; environment.  &gt; #### Recommended use &gt; &gt; This endpoint can return a large amount of information. Specifying one or multiple environments with the &#x60;env&#x60; parameter can decrease response time and overall payload size. We recommend using this parameter to return only the environments relevant to your query.  ### Expanding response  LaunchDarkly supports the &#x60;expand&#x60; query param to include additional fields in the response, with the following fields:  - &#x60;evaluation&#x60; includes evaluation information within returned environments, including which context kinds the flag has been evaluated for in the past 30 days  - &#x60;migrationSettings&#x60; includes migration settings information within the flag and within returned environments. These settings are only included for migration flags, that is, where &#x60;purpose&#x60; is &#x60;migration&#x60;.  For example, &#x60;expand&#x3D;evaluation&#x60; includes the &#x60;evaluation&#x60; field in the response. 
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param env Filter configurations by environment (optional)
     * @param expand A comma-separated list of fields to expand in the response. Supported fields are explained above. (optional)
     * @return ApiResponse&lt;FeatureFlag&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Global flag response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<FeatureFlag> getFeatureFlagWithHttpInfo(String projectKey, String featureFlagKey, String env, String expand) throws ApiException {
        okhttp3.Call localVarCall = getFeatureFlagValidateBeforeCall(projectKey, featureFlagKey, env, expand, null);
        Type localVarReturnType = new TypeToken<FeatureFlag>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get feature flag (asynchronously)
     * Get a single feature flag by key. By default, this returns the configurations for all environments. You can filter environments with the &#x60;env&#x60; query parameter. For example, setting &#x60;env&#x3D;production&#x60; restricts the returned configurations to just the &#x60;production&#x60; environment.  &gt; #### Recommended use &gt; &gt; This endpoint can return a large amount of information. Specifying one or multiple environments with the &#x60;env&#x60; parameter can decrease response time and overall payload size. We recommend using this parameter to return only the environments relevant to your query.  ### Expanding response  LaunchDarkly supports the &#x60;expand&#x60; query param to include additional fields in the response, with the following fields:  - &#x60;evaluation&#x60; includes evaluation information within returned environments, including which context kinds the flag has been evaluated for in the past 30 days  - &#x60;migrationSettings&#x60; includes migration settings information within the flag and within returned environments. These settings are only included for migration flags, that is, where &#x60;purpose&#x60; is &#x60;migration&#x60;.  For example, &#x60;expand&#x3D;evaluation&#x60; includes the &#x60;evaluation&#x60; field in the response. 
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param env Filter configurations by environment (optional)
     * @param expand A comma-separated list of fields to expand in the response. Supported fields are explained above. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Global flag response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getFeatureFlagAsync(String projectKey, String featureFlagKey, String env, String expand, final ApiCallback<FeatureFlag> _callback) throws ApiException {

        okhttp3.Call localVarCall = getFeatureFlagValidateBeforeCall(projectKey, featureFlagKey, env, expand, _callback);
        Type localVarReturnType = new TypeToken<FeatureFlag>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getFeatureFlagStatus
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Flag status response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getFeatureFlagStatusCall(String projectKey, String environmentKey, String featureFlagKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/flag-statuses/{projectKey}/{environmentKey}/{featureFlagKey}"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()))
            .replaceAll("\\{" + "environmentKey" + "\\}", localVarApiClient.escapeString(environmentKey.toString()))
            .replaceAll("\\{" + "featureFlagKey" + "\\}", localVarApiClient.escapeString(featureFlagKey.toString()));

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
    private okhttp3.Call getFeatureFlagStatusValidateBeforeCall(String projectKey, String environmentKey, String featureFlagKey, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getFeatureFlagStatus(Async)");
        }
        
        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling getFeatureFlagStatus(Async)");
        }
        
        // verify the required parameter 'featureFlagKey' is set
        if (featureFlagKey == null) {
            throw new ApiException("Missing the required parameter 'featureFlagKey' when calling getFeatureFlagStatus(Async)");
        }
        

        okhttp3.Call localVarCall = getFeatureFlagStatusCall(projectKey, environmentKey, featureFlagKey, _callback);
        return localVarCall;

    }

    /**
     * Get feature flag status
     * Get the status for a particular feature flag.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @return FlagStatusRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Flag status response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public FlagStatusRep getFeatureFlagStatus(String projectKey, String environmentKey, String featureFlagKey) throws ApiException {
        ApiResponse<FlagStatusRep> localVarResp = getFeatureFlagStatusWithHttpInfo(projectKey, environmentKey, featureFlagKey);
        return localVarResp.getData();
    }

    /**
     * Get feature flag status
     * Get the status for a particular feature flag.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @return ApiResponse&lt;FlagStatusRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Flag status response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<FlagStatusRep> getFeatureFlagStatusWithHttpInfo(String projectKey, String environmentKey, String featureFlagKey) throws ApiException {
        okhttp3.Call localVarCall = getFeatureFlagStatusValidateBeforeCall(projectKey, environmentKey, featureFlagKey, null);
        Type localVarReturnType = new TypeToken<FlagStatusRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get feature flag status (asynchronously)
     * Get the status for a particular feature flag.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Flag status response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getFeatureFlagStatusAsync(String projectKey, String environmentKey, String featureFlagKey, final ApiCallback<FlagStatusRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getFeatureFlagStatusValidateBeforeCall(projectKey, environmentKey, featureFlagKey, _callback);
        Type localVarReturnType = new TypeToken<FlagStatusRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getFeatureFlagStatusAcrossEnvironments
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param env Optional environment filter (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Flag status across environments response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getFeatureFlagStatusAcrossEnvironmentsCall(String projectKey, String featureFlagKey, String env, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/flag-status/{projectKey}/{featureFlagKey}"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()))
            .replaceAll("\\{" + "featureFlagKey" + "\\}", localVarApiClient.escapeString(featureFlagKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

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
    private okhttp3.Call getFeatureFlagStatusAcrossEnvironmentsValidateBeforeCall(String projectKey, String featureFlagKey, String env, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getFeatureFlagStatusAcrossEnvironments(Async)");
        }
        
        // verify the required parameter 'featureFlagKey' is set
        if (featureFlagKey == null) {
            throw new ApiException("Missing the required parameter 'featureFlagKey' when calling getFeatureFlagStatusAcrossEnvironments(Async)");
        }
        

        okhttp3.Call localVarCall = getFeatureFlagStatusAcrossEnvironmentsCall(projectKey, featureFlagKey, env, _callback);
        return localVarCall;

    }

    /**
     * Get flag status across environments
     * Get the status for a particular feature flag across environments.
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param env Optional environment filter (optional)
     * @return FeatureFlagStatusAcrossEnvironments
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Flag status across environments response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public FeatureFlagStatusAcrossEnvironments getFeatureFlagStatusAcrossEnvironments(String projectKey, String featureFlagKey, String env) throws ApiException {
        ApiResponse<FeatureFlagStatusAcrossEnvironments> localVarResp = getFeatureFlagStatusAcrossEnvironmentsWithHttpInfo(projectKey, featureFlagKey, env);
        return localVarResp.getData();
    }

    /**
     * Get flag status across environments
     * Get the status for a particular feature flag across environments.
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param env Optional environment filter (optional)
     * @return ApiResponse&lt;FeatureFlagStatusAcrossEnvironments&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Flag status across environments response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<FeatureFlagStatusAcrossEnvironments> getFeatureFlagStatusAcrossEnvironmentsWithHttpInfo(String projectKey, String featureFlagKey, String env) throws ApiException {
        okhttp3.Call localVarCall = getFeatureFlagStatusAcrossEnvironmentsValidateBeforeCall(projectKey, featureFlagKey, env, null);
        Type localVarReturnType = new TypeToken<FeatureFlagStatusAcrossEnvironments>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get flag status across environments (asynchronously)
     * Get the status for a particular feature flag across environments.
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param env Optional environment filter (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Flag status across environments response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getFeatureFlagStatusAcrossEnvironmentsAsync(String projectKey, String featureFlagKey, String env, final ApiCallback<FeatureFlagStatusAcrossEnvironments> _callback) throws ApiException {

        okhttp3.Call localVarCall = getFeatureFlagStatusAcrossEnvironmentsValidateBeforeCall(projectKey, featureFlagKey, env, _callback);
        Type localVarReturnType = new TypeToken<FeatureFlagStatusAcrossEnvironments>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getFeatureFlagStatuses
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Flag Statuses collection response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getFeatureFlagStatusesCall(String projectKey, String environmentKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/flag-statuses/{projectKey}/{environmentKey}"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()))
            .replaceAll("\\{" + "environmentKey" + "\\}", localVarApiClient.escapeString(environmentKey.toString()));

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
    private okhttp3.Call getFeatureFlagStatusesValidateBeforeCall(String projectKey, String environmentKey, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getFeatureFlagStatuses(Async)");
        }
        
        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling getFeatureFlagStatuses(Async)");
        }
        

        okhttp3.Call localVarCall = getFeatureFlagStatusesCall(projectKey, environmentKey, _callback);
        return localVarCall;

    }

    /**
     * List feature flag statuses
     * Get a list of statuses for all feature flags. The status includes the last time the feature flag was requested, as well as a state, which is one of the following:  - &#x60;new&#x60;: You created the flag fewer than seven days ago and it has never been requested. - &#x60;active&#x60;: LaunchDarkly is receiving requests for this flag, but there are either multiple variations configured, or it is toggled off, or there have been changes to configuration in the past seven days. - &#x60;inactive&#x60;: You created the feature flag more than seven days ago, and hasn&#39;t been requested within the past seven days. - &#x60;launched&#x60;: LaunchDarkly is receiving requests for this flag, it is toggled on, there is only one variation configured, and there have been no changes to configuration in the past seven days.  To learn more, read [Flag statuses](https://docs.launchdarkly.com/home/code/flag-status). 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @return FeatureFlagStatuses
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Flag Statuses collection response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public FeatureFlagStatuses getFeatureFlagStatuses(String projectKey, String environmentKey) throws ApiException {
        ApiResponse<FeatureFlagStatuses> localVarResp = getFeatureFlagStatusesWithHttpInfo(projectKey, environmentKey);
        return localVarResp.getData();
    }

    /**
     * List feature flag statuses
     * Get a list of statuses for all feature flags. The status includes the last time the feature flag was requested, as well as a state, which is one of the following:  - &#x60;new&#x60;: You created the flag fewer than seven days ago and it has never been requested. - &#x60;active&#x60;: LaunchDarkly is receiving requests for this flag, but there are either multiple variations configured, or it is toggled off, or there have been changes to configuration in the past seven days. - &#x60;inactive&#x60;: You created the feature flag more than seven days ago, and hasn&#39;t been requested within the past seven days. - &#x60;launched&#x60;: LaunchDarkly is receiving requests for this flag, it is toggled on, there is only one variation configured, and there have been no changes to configuration in the past seven days.  To learn more, read [Flag statuses](https://docs.launchdarkly.com/home/code/flag-status). 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @return ApiResponse&lt;FeatureFlagStatuses&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Flag Statuses collection response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<FeatureFlagStatuses> getFeatureFlagStatusesWithHttpInfo(String projectKey, String environmentKey) throws ApiException {
        okhttp3.Call localVarCall = getFeatureFlagStatusesValidateBeforeCall(projectKey, environmentKey, null);
        Type localVarReturnType = new TypeToken<FeatureFlagStatuses>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List feature flag statuses (asynchronously)
     * Get a list of statuses for all feature flags. The status includes the last time the feature flag was requested, as well as a state, which is one of the following:  - &#x60;new&#x60;: You created the flag fewer than seven days ago and it has never been requested. - &#x60;active&#x60;: LaunchDarkly is receiving requests for this flag, but there are either multiple variations configured, or it is toggled off, or there have been changes to configuration in the past seven days. - &#x60;inactive&#x60;: You created the feature flag more than seven days ago, and hasn&#39;t been requested within the past seven days. - &#x60;launched&#x60;: LaunchDarkly is receiving requests for this flag, it is toggled on, there is only one variation configured, and there have been no changes to configuration in the past seven days.  To learn more, read [Flag statuses](https://docs.launchdarkly.com/home/code/flag-status). 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Flag Statuses collection response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getFeatureFlagStatusesAsync(String projectKey, String environmentKey, final ApiCallback<FeatureFlagStatuses> _callback) throws ApiException {

        okhttp3.Call localVarCall = getFeatureFlagStatusesValidateBeforeCall(projectKey, environmentKey, _callback);
        Type localVarReturnType = new TypeToken<FeatureFlagStatuses>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getFeatureFlags
     * @param projectKey The project key (required)
     * @param env Filter configurations by environment (optional)
     * @param tag Filter feature flags by tag (optional)
     * @param limit The number of feature flags to return. Defaults to -1, which returns all flags (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param archived Deprecated, use &#x60;filter&#x3D;archived:true&#x60; instead. A boolean to filter the list to archived flags. When this is absent, only unarchived flags will be returned (optional)
     * @param summary By default, flags do _not_ include their lists of prerequisites, targets, or rules for each environment. Set &#x60;summary&#x3D;0&#x60; to include these fields for each flag returned. (optional)
     * @param filter A comma-separated list of filters. Each filter is of the form field:value. Read the endpoint description for a full list of available filter fields. (optional)
     * @param sort A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order. Read the endpoint description for a full list of available sort fields. (optional)
     * @param compare A boolean to filter results by only flags that have differences between environments (optional)
     * @param expand A comma-separated list of fields to expand in the response. Supported fields are explained above. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Global flags collection response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getFeatureFlagsCall(String projectKey, String env, String tag, Long limit, Long offset, Boolean archived, Boolean summary, String filter, String sort, Boolean compare, String expand, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/flags/{projectKey}"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (env != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("env", env));
        }

        if (tag != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("tag", tag));
        }

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (offset != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("offset", offset));
        }

        if (archived != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("archived", archived));
        }

        if (summary != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("summary", summary));
        }

        if (filter != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("filter", filter));
        }

        if (sort != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sort", sort));
        }

        if (compare != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("compare", compare));
        }

        if (expand != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("expand", expand));
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
    private okhttp3.Call getFeatureFlagsValidateBeforeCall(String projectKey, String env, String tag, Long limit, Long offset, Boolean archived, Boolean summary, String filter, String sort, Boolean compare, String expand, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getFeatureFlags(Async)");
        }
        

        okhttp3.Call localVarCall = getFeatureFlagsCall(projectKey, env, tag, limit, offset, archived, summary, filter, sort, compare, expand, _callback);
        return localVarCall;

    }

    /**
     * List feature flags
     * Get a list of all feature flags in the given project. By default, each flag includes configurations for each environment. You can filter environments with the &#x60;env&#x60; query parameter. For example, setting &#x60;env&#x3D;production&#x60; restricts the returned configurations to just your production environment. You can also filter feature flags by tag with the &#x60;tag&#x60; query parameter.  &gt; #### Recommended use &gt; &gt; This endpoint can return a large amount of information. We recommend using some or all of these query parameters to decrease response time and overall payload size: &#x60;limit&#x60;, &#x60;env&#x60;, &#x60;query&#x60;, and &#x60;filter&#x3D;creationDate&#x60;.  ### Filtering flags  You can filter on certain fields using the &#x60;filter&#x60; query parameter. For example, setting &#x60;filter&#x3D;query:dark-mode,tags:beta+test&#x60; matches flags with the string &#x60;dark-mode&#x60; in their key or name, ignoring case, which also have the tags &#x60;beta&#x60; and &#x60;test&#x60;.  The &#x60;filter&#x60; query parameter supports the following arguments:  | Filter argument       | Description | Example              | |-----------------------|-------------|----------------------| | &#x60;archived&#x60;              | A boolean value. It filters the list to archived flags. Setting the value to &#x60;true&#x60; returns only archived flags. When this is absent, only unarchived flags are returned. | &#x60;filter&#x3D;archived:true&#x60; | | &#x60;contextKindsEvaluated&#x60; | A &#x60;+&#x60;-separated list of context kind keys. It filters the list to flags which have been evaluated in the past 30 days for all of the context kinds in the list. | &#x60;filter&#x3D;contextKindsEvaluated:user+application&#x60; | | &#x60;contextKindTargeted&#x60;   | A string. It filters the list to flags that are targeting the given context kind key. | &#x60;filter&#x3D;contextKindTargeted:user&#x60; | | &#x60;codeReferences.max&#x60;    | An integer value. Use &#x60;0&#x60; to return flags that do not have code references. | &#x60;filter&#x3D;codeReferences.max:0&#x60; | | &#x60;codeReferences.min&#x60;    | An integer value. Use &#x60;1&#x60; to return flags that do have code references. | &#x60;filter&#x3D;codeReferences.min:1&#x60; | | &#x60;creationDate&#x60;          | An object with optional &#x60;before&#x60; and &#x60;after&#x60; fields whose values are Unix time in milliseconds. It filters the list to flags created in the specified range. | &#x60;filter&#x3D;creationDate:{\&quot;before\&quot;:1690527600000}&#x60; | | &#x60;evaluated&#x60;             | An object that contains a key of &#x60;after&#x60; and a value in Unix time in milliseconds. It filters the list to all flags that have been evaluated since the time you specify, in the environment provided. This filter requires the &#x60;filterEnv&#x60; filter. | &#x60;filter&#x3D;evaluation:{\&quot;after\&quot;:1690527600000}&#x60; | | &#x60;filterEnv&#x60;             | A string with the key of a valid environment. You must use this field for filters that are environment-specific. If there are multiple environment-specific filters, you only need to include this field once. | &#x60;filter&#x3D;evaluated:{\&quot;after\&quot;: 1590768455282},filterEnv:production,status:active&#x60; | | &#x60;followerId&#x60;            | A valid member ID. It filters the list to flags that are being followed by this member. |  &#x60;filter&#x3D;followerId:12ab3c45de678910abc12345&#x60; | | &#x60;hasDataExport&#x60;         | A boolean value. It filters the list to flags that are exporting data in the specified environment. This includes flags that are exporting data from Experimentation. This filter requires the &#x60;filterEnv&#x60; filter. | &#x60;filter&#x3D;hasDataExport:true,filterEnv:production&#x60; | | &#x60;hasExperiment&#x60;         | A boolean value. It filters the list to flags that are used in an experiment. | &#x60;filter&#x3D;hasExperiment:true&#x60; | | &#x60;maintainerId&#x60;          | A valid member ID. It filters the list to flags that are maintained by this member. | &#x60;filter&#x3D;maintainerId:12ab3c45de678910abc12345&#x60; | | &#x60;maintainerTeamKey&#x60;     | A string. It filters the list to flags that are maintained by the team with this key. | &#x60;filter&#x3D;maintainerTeamKey:example-team-key&#x60; | | &#x60;query&#x60;                 | A string. It filters the list to flags that include the specified string in their key or name. It is not case sensitive. | &#x60;filter&#x3D;query:example&#x60; | | &#x60;sdkAvailability&#x60;       | A string, one of &#x60;client&#x60;, &#x60;mobile&#x60;, &#x60;anyClient&#x60;, &#x60;server&#x60;. Using &#x60;client&#x60; filters the list to flags whose client-side SDK availability is set to use the client-side ID. Using &#x60;mobile&#x60; filters to flags set to use the mobile key. Using &#x60;anyClient&#x60; filters to flags set to use either the client-side ID or the mobile key. Using &#x60;server&#x60; filters to flags set to use neither, that is, to flags only available in server-side SDKs.  | &#x60;filter&#x3D;sdkAvailability:client&#x60; | | &#x60;segmentTargeted&#x60;       | A string. It filters the list to flags that target the segment with this key. This filter requires the &#x60;filterEnv&#x60; filter. | &#x60;filter&#x3D;segmentTargeted:example-segment-key,filterEnv:production&#x60; | | &#x60;status&#x60;                | A string, either &#x60;new&#x60;, &#x60;inactive&#x60;, &#x60;active&#x60;, or &#x60;launched&#x60;. It filters the list to flags with the specified status in the specified environment. This filter requires the &#x60;filterEnv&#x60; filter. | &#x60;filter&#x3D;status:active,filterEnv:production&#x60; | | &#x60;tags&#x60;                  | A &#x60;+&#x60;-separated list of tags. It filters the list to flags that have all of the tags in the list. | &#x60;filter&#x3D;tags:beta+test&#x60; | | &#x60;type&#x60;                  | A string, either &#x60;temporary&#x60; or &#x60;permanent&#x60;. It filters the list to flags with the specified type. | &#x60;filter&#x3D;type:permanent&#x60; |  The documented values for the &#x60;filter&#x60; query are prior to URL encoding. For example, the &#x60;+&#x60; in &#x60;filter&#x3D;tags:beta+test&#x60; must be encoded to &#x60;%2B&#x60;.  By default, this endpoint returns all flags. You can page through the list with the &#x60;limit&#x60; parameter and by following the &#x60;first&#x60;, &#x60;prev&#x60;, &#x60;next&#x60;, and &#x60;last&#x60; links in the returned &#x60;_links&#x60; field. These links will not be present if the pages they refer to don&#39;t exist. For example, the &#x60;first&#x60; and &#x60;prev&#x60; links will be missing from the response on the first page.  ### Sorting flags  You can sort flags based on the following fields:  - &#x60;creationDate&#x60; sorts by the creation date of the flag. - &#x60;key&#x60; sorts by the key of the flag. - &#x60;maintainerId&#x60; sorts by the flag maintainer. - &#x60;name&#x60; sorts by flag name. - &#x60;tags&#x60; sorts by tags. - &#x60;targetingModifiedDate&#x60; sorts by the date that the flag&#39;s targeting rules were last modified in a given environment. It must be used with &#x60;env&#x60; parameter and it can not be combined with any other sort. If multiple &#x60;env&#x60; values are provided, it will perform sort using the first one. For example, &#x60;sort&#x3D;-targetingModifiedDate&amp;env&#x3D;production&amp;env&#x3D;staging&#x60; returns results sorted by &#x60;targetingModifiedDate&#x60; for the &#x60;production&#x60; environment. - &#x60;type&#x60; sorts by flag type  All fields are sorted in ascending order by default. To sort in descending order, prefix the field with a dash ( - ). For example, &#x60;sort&#x3D;-name&#x60; sorts the response by flag name in descending order.  ### Expanding response  LaunchDarkly supports the &#x60;expand&#x60; query param to include additional fields in the response, with the following fields:  - &#x60;codeReferences&#x60; includes code references for the feature flag - &#x60;evaluation&#x60; includes evaluation information within returned environments, including which context kinds the flag has been evaluated for in the past 30 days - &#x60;migrationSettings&#x60; includes migration settings information within the flag and within returned environments. These settings are only included for migration flags, that is, where &#x60;purpose&#x60; is &#x60;migration&#x60;.  For example, &#x60;expand&#x3D;evaluation&#x60; includes the &#x60;evaluation&#x60; field in the response.  ### Migration flags For migration flags, the cohort information is included in the &#x60;rules&#x60; property of a flag&#39;s response, and default cohort information is included in the &#x60;fallthrough&#x60; property of a flag&#39;s response. To learn more, read [Migration Flags](https://docs.launchdarkly.com/home/flag-types/migration-flags). 
     * @param projectKey The project key (required)
     * @param env Filter configurations by environment (optional)
     * @param tag Filter feature flags by tag (optional)
     * @param limit The number of feature flags to return. Defaults to -1, which returns all flags (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param archived Deprecated, use &#x60;filter&#x3D;archived:true&#x60; instead. A boolean to filter the list to archived flags. When this is absent, only unarchived flags will be returned (optional)
     * @param summary By default, flags do _not_ include their lists of prerequisites, targets, or rules for each environment. Set &#x60;summary&#x3D;0&#x60; to include these fields for each flag returned. (optional)
     * @param filter A comma-separated list of filters. Each filter is of the form field:value. Read the endpoint description for a full list of available filter fields. (optional)
     * @param sort A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order. Read the endpoint description for a full list of available sort fields. (optional)
     * @param compare A boolean to filter results by only flags that have differences between environments (optional)
     * @param expand A comma-separated list of fields to expand in the response. Supported fields are explained above. (optional)
     * @return FeatureFlags
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Global flags collection response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public FeatureFlags getFeatureFlags(String projectKey, String env, String tag, Long limit, Long offset, Boolean archived, Boolean summary, String filter, String sort, Boolean compare, String expand) throws ApiException {
        ApiResponse<FeatureFlags> localVarResp = getFeatureFlagsWithHttpInfo(projectKey, env, tag, limit, offset, archived, summary, filter, sort, compare, expand);
        return localVarResp.getData();
    }

    /**
     * List feature flags
     * Get a list of all feature flags in the given project. By default, each flag includes configurations for each environment. You can filter environments with the &#x60;env&#x60; query parameter. For example, setting &#x60;env&#x3D;production&#x60; restricts the returned configurations to just your production environment. You can also filter feature flags by tag with the &#x60;tag&#x60; query parameter.  &gt; #### Recommended use &gt; &gt; This endpoint can return a large amount of information. We recommend using some or all of these query parameters to decrease response time and overall payload size: &#x60;limit&#x60;, &#x60;env&#x60;, &#x60;query&#x60;, and &#x60;filter&#x3D;creationDate&#x60;.  ### Filtering flags  You can filter on certain fields using the &#x60;filter&#x60; query parameter. For example, setting &#x60;filter&#x3D;query:dark-mode,tags:beta+test&#x60; matches flags with the string &#x60;dark-mode&#x60; in their key or name, ignoring case, which also have the tags &#x60;beta&#x60; and &#x60;test&#x60;.  The &#x60;filter&#x60; query parameter supports the following arguments:  | Filter argument       | Description | Example              | |-----------------------|-------------|----------------------| | &#x60;archived&#x60;              | A boolean value. It filters the list to archived flags. Setting the value to &#x60;true&#x60; returns only archived flags. When this is absent, only unarchived flags are returned. | &#x60;filter&#x3D;archived:true&#x60; | | &#x60;contextKindsEvaluated&#x60; | A &#x60;+&#x60;-separated list of context kind keys. It filters the list to flags which have been evaluated in the past 30 days for all of the context kinds in the list. | &#x60;filter&#x3D;contextKindsEvaluated:user+application&#x60; | | &#x60;contextKindTargeted&#x60;   | A string. It filters the list to flags that are targeting the given context kind key. | &#x60;filter&#x3D;contextKindTargeted:user&#x60; | | &#x60;codeReferences.max&#x60;    | An integer value. Use &#x60;0&#x60; to return flags that do not have code references. | &#x60;filter&#x3D;codeReferences.max:0&#x60; | | &#x60;codeReferences.min&#x60;    | An integer value. Use &#x60;1&#x60; to return flags that do have code references. | &#x60;filter&#x3D;codeReferences.min:1&#x60; | | &#x60;creationDate&#x60;          | An object with optional &#x60;before&#x60; and &#x60;after&#x60; fields whose values are Unix time in milliseconds. It filters the list to flags created in the specified range. | &#x60;filter&#x3D;creationDate:{\&quot;before\&quot;:1690527600000}&#x60; | | &#x60;evaluated&#x60;             | An object that contains a key of &#x60;after&#x60; and a value in Unix time in milliseconds. It filters the list to all flags that have been evaluated since the time you specify, in the environment provided. This filter requires the &#x60;filterEnv&#x60; filter. | &#x60;filter&#x3D;evaluation:{\&quot;after\&quot;:1690527600000}&#x60; | | &#x60;filterEnv&#x60;             | A string with the key of a valid environment. You must use this field for filters that are environment-specific. If there are multiple environment-specific filters, you only need to include this field once. | &#x60;filter&#x3D;evaluated:{\&quot;after\&quot;: 1590768455282},filterEnv:production,status:active&#x60; | | &#x60;followerId&#x60;            | A valid member ID. It filters the list to flags that are being followed by this member. |  &#x60;filter&#x3D;followerId:12ab3c45de678910abc12345&#x60; | | &#x60;hasDataExport&#x60;         | A boolean value. It filters the list to flags that are exporting data in the specified environment. This includes flags that are exporting data from Experimentation. This filter requires the &#x60;filterEnv&#x60; filter. | &#x60;filter&#x3D;hasDataExport:true,filterEnv:production&#x60; | | &#x60;hasExperiment&#x60;         | A boolean value. It filters the list to flags that are used in an experiment. | &#x60;filter&#x3D;hasExperiment:true&#x60; | | &#x60;maintainerId&#x60;          | A valid member ID. It filters the list to flags that are maintained by this member. | &#x60;filter&#x3D;maintainerId:12ab3c45de678910abc12345&#x60; | | &#x60;maintainerTeamKey&#x60;     | A string. It filters the list to flags that are maintained by the team with this key. | &#x60;filter&#x3D;maintainerTeamKey:example-team-key&#x60; | | &#x60;query&#x60;                 | A string. It filters the list to flags that include the specified string in their key or name. It is not case sensitive. | &#x60;filter&#x3D;query:example&#x60; | | &#x60;sdkAvailability&#x60;       | A string, one of &#x60;client&#x60;, &#x60;mobile&#x60;, &#x60;anyClient&#x60;, &#x60;server&#x60;. Using &#x60;client&#x60; filters the list to flags whose client-side SDK availability is set to use the client-side ID. Using &#x60;mobile&#x60; filters to flags set to use the mobile key. Using &#x60;anyClient&#x60; filters to flags set to use either the client-side ID or the mobile key. Using &#x60;server&#x60; filters to flags set to use neither, that is, to flags only available in server-side SDKs.  | &#x60;filter&#x3D;sdkAvailability:client&#x60; | | &#x60;segmentTargeted&#x60;       | A string. It filters the list to flags that target the segment with this key. This filter requires the &#x60;filterEnv&#x60; filter. | &#x60;filter&#x3D;segmentTargeted:example-segment-key,filterEnv:production&#x60; | | &#x60;status&#x60;                | A string, either &#x60;new&#x60;, &#x60;inactive&#x60;, &#x60;active&#x60;, or &#x60;launched&#x60;. It filters the list to flags with the specified status in the specified environment. This filter requires the &#x60;filterEnv&#x60; filter. | &#x60;filter&#x3D;status:active,filterEnv:production&#x60; | | &#x60;tags&#x60;                  | A &#x60;+&#x60;-separated list of tags. It filters the list to flags that have all of the tags in the list. | &#x60;filter&#x3D;tags:beta+test&#x60; | | &#x60;type&#x60;                  | A string, either &#x60;temporary&#x60; or &#x60;permanent&#x60;. It filters the list to flags with the specified type. | &#x60;filter&#x3D;type:permanent&#x60; |  The documented values for the &#x60;filter&#x60; query are prior to URL encoding. For example, the &#x60;+&#x60; in &#x60;filter&#x3D;tags:beta+test&#x60; must be encoded to &#x60;%2B&#x60;.  By default, this endpoint returns all flags. You can page through the list with the &#x60;limit&#x60; parameter and by following the &#x60;first&#x60;, &#x60;prev&#x60;, &#x60;next&#x60;, and &#x60;last&#x60; links in the returned &#x60;_links&#x60; field. These links will not be present if the pages they refer to don&#39;t exist. For example, the &#x60;first&#x60; and &#x60;prev&#x60; links will be missing from the response on the first page.  ### Sorting flags  You can sort flags based on the following fields:  - &#x60;creationDate&#x60; sorts by the creation date of the flag. - &#x60;key&#x60; sorts by the key of the flag. - &#x60;maintainerId&#x60; sorts by the flag maintainer. - &#x60;name&#x60; sorts by flag name. - &#x60;tags&#x60; sorts by tags. - &#x60;targetingModifiedDate&#x60; sorts by the date that the flag&#39;s targeting rules were last modified in a given environment. It must be used with &#x60;env&#x60; parameter and it can not be combined with any other sort. If multiple &#x60;env&#x60; values are provided, it will perform sort using the first one. For example, &#x60;sort&#x3D;-targetingModifiedDate&amp;env&#x3D;production&amp;env&#x3D;staging&#x60; returns results sorted by &#x60;targetingModifiedDate&#x60; for the &#x60;production&#x60; environment. - &#x60;type&#x60; sorts by flag type  All fields are sorted in ascending order by default. To sort in descending order, prefix the field with a dash ( - ). For example, &#x60;sort&#x3D;-name&#x60; sorts the response by flag name in descending order.  ### Expanding response  LaunchDarkly supports the &#x60;expand&#x60; query param to include additional fields in the response, with the following fields:  - &#x60;codeReferences&#x60; includes code references for the feature flag - &#x60;evaluation&#x60; includes evaluation information within returned environments, including which context kinds the flag has been evaluated for in the past 30 days - &#x60;migrationSettings&#x60; includes migration settings information within the flag and within returned environments. These settings are only included for migration flags, that is, where &#x60;purpose&#x60; is &#x60;migration&#x60;.  For example, &#x60;expand&#x3D;evaluation&#x60; includes the &#x60;evaluation&#x60; field in the response.  ### Migration flags For migration flags, the cohort information is included in the &#x60;rules&#x60; property of a flag&#39;s response, and default cohort information is included in the &#x60;fallthrough&#x60; property of a flag&#39;s response. To learn more, read [Migration Flags](https://docs.launchdarkly.com/home/flag-types/migration-flags). 
     * @param projectKey The project key (required)
     * @param env Filter configurations by environment (optional)
     * @param tag Filter feature flags by tag (optional)
     * @param limit The number of feature flags to return. Defaults to -1, which returns all flags (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param archived Deprecated, use &#x60;filter&#x3D;archived:true&#x60; instead. A boolean to filter the list to archived flags. When this is absent, only unarchived flags will be returned (optional)
     * @param summary By default, flags do _not_ include their lists of prerequisites, targets, or rules for each environment. Set &#x60;summary&#x3D;0&#x60; to include these fields for each flag returned. (optional)
     * @param filter A comma-separated list of filters. Each filter is of the form field:value. Read the endpoint description for a full list of available filter fields. (optional)
     * @param sort A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order. Read the endpoint description for a full list of available sort fields. (optional)
     * @param compare A boolean to filter results by only flags that have differences between environments (optional)
     * @param expand A comma-separated list of fields to expand in the response. Supported fields are explained above. (optional)
     * @return ApiResponse&lt;FeatureFlags&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Global flags collection response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<FeatureFlags> getFeatureFlagsWithHttpInfo(String projectKey, String env, String tag, Long limit, Long offset, Boolean archived, Boolean summary, String filter, String sort, Boolean compare, String expand) throws ApiException {
        okhttp3.Call localVarCall = getFeatureFlagsValidateBeforeCall(projectKey, env, tag, limit, offset, archived, summary, filter, sort, compare, expand, null);
        Type localVarReturnType = new TypeToken<FeatureFlags>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List feature flags (asynchronously)
     * Get a list of all feature flags in the given project. By default, each flag includes configurations for each environment. You can filter environments with the &#x60;env&#x60; query parameter. For example, setting &#x60;env&#x3D;production&#x60; restricts the returned configurations to just your production environment. You can also filter feature flags by tag with the &#x60;tag&#x60; query parameter.  &gt; #### Recommended use &gt; &gt; This endpoint can return a large amount of information. We recommend using some or all of these query parameters to decrease response time and overall payload size: &#x60;limit&#x60;, &#x60;env&#x60;, &#x60;query&#x60;, and &#x60;filter&#x3D;creationDate&#x60;.  ### Filtering flags  You can filter on certain fields using the &#x60;filter&#x60; query parameter. For example, setting &#x60;filter&#x3D;query:dark-mode,tags:beta+test&#x60; matches flags with the string &#x60;dark-mode&#x60; in their key or name, ignoring case, which also have the tags &#x60;beta&#x60; and &#x60;test&#x60;.  The &#x60;filter&#x60; query parameter supports the following arguments:  | Filter argument       | Description | Example              | |-----------------------|-------------|----------------------| | &#x60;archived&#x60;              | A boolean value. It filters the list to archived flags. Setting the value to &#x60;true&#x60; returns only archived flags. When this is absent, only unarchived flags are returned. | &#x60;filter&#x3D;archived:true&#x60; | | &#x60;contextKindsEvaluated&#x60; | A &#x60;+&#x60;-separated list of context kind keys. It filters the list to flags which have been evaluated in the past 30 days for all of the context kinds in the list. | &#x60;filter&#x3D;contextKindsEvaluated:user+application&#x60; | | &#x60;contextKindTargeted&#x60;   | A string. It filters the list to flags that are targeting the given context kind key. | &#x60;filter&#x3D;contextKindTargeted:user&#x60; | | &#x60;codeReferences.max&#x60;    | An integer value. Use &#x60;0&#x60; to return flags that do not have code references. | &#x60;filter&#x3D;codeReferences.max:0&#x60; | | &#x60;codeReferences.min&#x60;    | An integer value. Use &#x60;1&#x60; to return flags that do have code references. | &#x60;filter&#x3D;codeReferences.min:1&#x60; | | &#x60;creationDate&#x60;          | An object with optional &#x60;before&#x60; and &#x60;after&#x60; fields whose values are Unix time in milliseconds. It filters the list to flags created in the specified range. | &#x60;filter&#x3D;creationDate:{\&quot;before\&quot;:1690527600000}&#x60; | | &#x60;evaluated&#x60;             | An object that contains a key of &#x60;after&#x60; and a value in Unix time in milliseconds. It filters the list to all flags that have been evaluated since the time you specify, in the environment provided. This filter requires the &#x60;filterEnv&#x60; filter. | &#x60;filter&#x3D;evaluation:{\&quot;after\&quot;:1690527600000}&#x60; | | &#x60;filterEnv&#x60;             | A string with the key of a valid environment. You must use this field for filters that are environment-specific. If there are multiple environment-specific filters, you only need to include this field once. | &#x60;filter&#x3D;evaluated:{\&quot;after\&quot;: 1590768455282},filterEnv:production,status:active&#x60; | | &#x60;followerId&#x60;            | A valid member ID. It filters the list to flags that are being followed by this member. |  &#x60;filter&#x3D;followerId:12ab3c45de678910abc12345&#x60; | | &#x60;hasDataExport&#x60;         | A boolean value. It filters the list to flags that are exporting data in the specified environment. This includes flags that are exporting data from Experimentation. This filter requires the &#x60;filterEnv&#x60; filter. | &#x60;filter&#x3D;hasDataExport:true,filterEnv:production&#x60; | | &#x60;hasExperiment&#x60;         | A boolean value. It filters the list to flags that are used in an experiment. | &#x60;filter&#x3D;hasExperiment:true&#x60; | | &#x60;maintainerId&#x60;          | A valid member ID. It filters the list to flags that are maintained by this member. | &#x60;filter&#x3D;maintainerId:12ab3c45de678910abc12345&#x60; | | &#x60;maintainerTeamKey&#x60;     | A string. It filters the list to flags that are maintained by the team with this key. | &#x60;filter&#x3D;maintainerTeamKey:example-team-key&#x60; | | &#x60;query&#x60;                 | A string. It filters the list to flags that include the specified string in their key or name. It is not case sensitive. | &#x60;filter&#x3D;query:example&#x60; | | &#x60;sdkAvailability&#x60;       | A string, one of &#x60;client&#x60;, &#x60;mobile&#x60;, &#x60;anyClient&#x60;, &#x60;server&#x60;. Using &#x60;client&#x60; filters the list to flags whose client-side SDK availability is set to use the client-side ID. Using &#x60;mobile&#x60; filters to flags set to use the mobile key. Using &#x60;anyClient&#x60; filters to flags set to use either the client-side ID or the mobile key. Using &#x60;server&#x60; filters to flags set to use neither, that is, to flags only available in server-side SDKs.  | &#x60;filter&#x3D;sdkAvailability:client&#x60; | | &#x60;segmentTargeted&#x60;       | A string. It filters the list to flags that target the segment with this key. This filter requires the &#x60;filterEnv&#x60; filter. | &#x60;filter&#x3D;segmentTargeted:example-segment-key,filterEnv:production&#x60; | | &#x60;status&#x60;                | A string, either &#x60;new&#x60;, &#x60;inactive&#x60;, &#x60;active&#x60;, or &#x60;launched&#x60;. It filters the list to flags with the specified status in the specified environment. This filter requires the &#x60;filterEnv&#x60; filter. | &#x60;filter&#x3D;status:active,filterEnv:production&#x60; | | &#x60;tags&#x60;                  | A &#x60;+&#x60;-separated list of tags. It filters the list to flags that have all of the tags in the list. | &#x60;filter&#x3D;tags:beta+test&#x60; | | &#x60;type&#x60;                  | A string, either &#x60;temporary&#x60; or &#x60;permanent&#x60;. It filters the list to flags with the specified type. | &#x60;filter&#x3D;type:permanent&#x60; |  The documented values for the &#x60;filter&#x60; query are prior to URL encoding. For example, the &#x60;+&#x60; in &#x60;filter&#x3D;tags:beta+test&#x60; must be encoded to &#x60;%2B&#x60;.  By default, this endpoint returns all flags. You can page through the list with the &#x60;limit&#x60; parameter and by following the &#x60;first&#x60;, &#x60;prev&#x60;, &#x60;next&#x60;, and &#x60;last&#x60; links in the returned &#x60;_links&#x60; field. These links will not be present if the pages they refer to don&#39;t exist. For example, the &#x60;first&#x60; and &#x60;prev&#x60; links will be missing from the response on the first page.  ### Sorting flags  You can sort flags based on the following fields:  - &#x60;creationDate&#x60; sorts by the creation date of the flag. - &#x60;key&#x60; sorts by the key of the flag. - &#x60;maintainerId&#x60; sorts by the flag maintainer. - &#x60;name&#x60; sorts by flag name. - &#x60;tags&#x60; sorts by tags. - &#x60;targetingModifiedDate&#x60; sorts by the date that the flag&#39;s targeting rules were last modified in a given environment. It must be used with &#x60;env&#x60; parameter and it can not be combined with any other sort. If multiple &#x60;env&#x60; values are provided, it will perform sort using the first one. For example, &#x60;sort&#x3D;-targetingModifiedDate&amp;env&#x3D;production&amp;env&#x3D;staging&#x60; returns results sorted by &#x60;targetingModifiedDate&#x60; for the &#x60;production&#x60; environment. - &#x60;type&#x60; sorts by flag type  All fields are sorted in ascending order by default. To sort in descending order, prefix the field with a dash ( - ). For example, &#x60;sort&#x3D;-name&#x60; sorts the response by flag name in descending order.  ### Expanding response  LaunchDarkly supports the &#x60;expand&#x60; query param to include additional fields in the response, with the following fields:  - &#x60;codeReferences&#x60; includes code references for the feature flag - &#x60;evaluation&#x60; includes evaluation information within returned environments, including which context kinds the flag has been evaluated for in the past 30 days - &#x60;migrationSettings&#x60; includes migration settings information within the flag and within returned environments. These settings are only included for migration flags, that is, where &#x60;purpose&#x60; is &#x60;migration&#x60;.  For example, &#x60;expand&#x3D;evaluation&#x60; includes the &#x60;evaluation&#x60; field in the response.  ### Migration flags For migration flags, the cohort information is included in the &#x60;rules&#x60; property of a flag&#39;s response, and default cohort information is included in the &#x60;fallthrough&#x60; property of a flag&#39;s response. To learn more, read [Migration Flags](https://docs.launchdarkly.com/home/flag-types/migration-flags). 
     * @param projectKey The project key (required)
     * @param env Filter configurations by environment (optional)
     * @param tag Filter feature flags by tag (optional)
     * @param limit The number of feature flags to return. Defaults to -1, which returns all flags (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param archived Deprecated, use &#x60;filter&#x3D;archived:true&#x60; instead. A boolean to filter the list to archived flags. When this is absent, only unarchived flags will be returned (optional)
     * @param summary By default, flags do _not_ include their lists of prerequisites, targets, or rules for each environment. Set &#x60;summary&#x3D;0&#x60; to include these fields for each flag returned. (optional)
     * @param filter A comma-separated list of filters. Each filter is of the form field:value. Read the endpoint description for a full list of available filter fields. (optional)
     * @param sort A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order. Read the endpoint description for a full list of available sort fields. (optional)
     * @param compare A boolean to filter results by only flags that have differences between environments (optional)
     * @param expand A comma-separated list of fields to expand in the response. Supported fields are explained above. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Global flags collection response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getFeatureFlagsAsync(String projectKey, String env, String tag, Long limit, Long offset, Boolean archived, Boolean summary, String filter, String sort, Boolean compare, String expand, final ApiCallback<FeatureFlags> _callback) throws ApiException {

        okhttp3.Call localVarCall = getFeatureFlagsValidateBeforeCall(projectKey, env, tag, limit, offset, archived, summary, filter, sort, compare, expand, _callback);
        Type localVarReturnType = new TypeToken<FeatureFlags>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for patchExpiringTargets
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param patchFlagsRequest  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring target response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchExpiringTargetsCall(String projectKey, String environmentKey, String featureFlagKey, PatchFlagsRequest patchFlagsRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = patchFlagsRequest;

        // create path and map variables
        String localVarPath = "/api/v2/flags/{projectKey}/{featureFlagKey}/expiring-targets/{environmentKey}"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()))
            .replaceAll("\\{" + "environmentKey" + "\\}", localVarApiClient.escapeString(environmentKey.toString()))
            .replaceAll("\\{" + "featureFlagKey" + "\\}", localVarApiClient.escapeString(featureFlagKey.toString()));

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
    private okhttp3.Call patchExpiringTargetsValidateBeforeCall(String projectKey, String environmentKey, String featureFlagKey, PatchFlagsRequest patchFlagsRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling patchExpiringTargets(Async)");
        }
        
        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling patchExpiringTargets(Async)");
        }
        
        // verify the required parameter 'featureFlagKey' is set
        if (featureFlagKey == null) {
            throw new ApiException("Missing the required parameter 'featureFlagKey' when calling patchExpiringTargets(Async)");
        }
        
        // verify the required parameter 'patchFlagsRequest' is set
        if (patchFlagsRequest == null) {
            throw new ApiException("Missing the required parameter 'patchFlagsRequest' when calling patchExpiringTargets(Async)");
        }
        

        okhttp3.Call localVarCall = patchExpiringTargetsCall(projectKey, environmentKey, featureFlagKey, patchFlagsRequest, _callback);
        return localVarCall;

    }

    /**
     * Update expiring context targets on feature flag
     * Schedule a context for removal from individual targeting on a feature flag. The flag must already individually target the context.  You can add, update, or remove a scheduled removal date. You can only schedule a context for removal on a single variation per flag.  Updating an expiring target uses the semantic patch format. To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating expiring targets.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating expiring targets&lt;/strong&gt;&lt;/summary&gt;  #### addExpiringTarget  Adds a date and time that LaunchDarkly will remove the context from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the context from individual targeting for this flag * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;contextKey&#x60;: The context key for the context to remove from individual targeting * &#x60;contextKind&#x60;: The kind of context represented by the &#x60;contextKey&#x60;  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addExpiringTarget\&quot;,     \&quot;value\&quot;: 1754006460000,     \&quot;variationId\&quot;: \&quot;4254742c-71ae-411f-a992-43b18a51afe0\&quot;,     \&quot;contextKey\&quot;: \&quot;user-key-123abc\&quot;,     \&quot;contextKind\&quot;: \&quot;user\&quot;   }] } &#x60;&#x60;&#x60;  #### updateExpiringTarget  Updates the date and time that LaunchDarkly will remove the context from the flag&#39;s individual targeting  ##### Parameters  * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the context from individual targeting for this flag * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;contextKey&#x60;: The context key for the context to remove from individual targeting * &#x60;contextKind&#x60;: The kind of context represented by the &#x60;contextKey&#x60; * &#x60;version&#x60;: (Optional) The version of the expiring target to update. If included, update will fail if version doesn&#39;t match current version of the expiring target.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;updateExpiringTarget\&quot;,     \&quot;value\&quot;: 1754006460000,     \&quot;variationId\&quot;: \&quot;4254742c-71ae-411f-a992-43b18a51afe0\&quot;,     \&quot;contextKey\&quot;: \&quot;user-key-123abc\&quot;,     \&quot;contextKind\&quot;: \&quot;user\&quot;   }] } &#x60;&#x60;&#x60;  #### removeExpiringTarget  Removes the scheduled removal of the context from the flag&#39;s individual targeting. The context will remain part of the flag&#39;s individual targeting until you explicitly remove it, or until you schedule another removal.  ##### Parameters  * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;contextKey&#x60;: The context key for the context to remove from individual targeting * &#x60;contextKind&#x60;: The kind of context represented by the &#x60;contextKey&#x60;  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;removeExpiringTarget\&quot;,     \&quot;variationId\&quot;: \&quot;4254742c-71ae-411f-a992-43b18a51afe0\&quot;,     \&quot;contextKey\&quot;: \&quot;user-key-123abc\&quot;,     \&quot;contextKind\&quot;: \&quot;user\&quot;   }] } &#x60;&#x60;&#x60;  &lt;/details&gt; 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param patchFlagsRequest  (required)
     * @return ExpiringTargetPatchResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring target response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ExpiringTargetPatchResponse patchExpiringTargets(String projectKey, String environmentKey, String featureFlagKey, PatchFlagsRequest patchFlagsRequest) throws ApiException {
        ApiResponse<ExpiringTargetPatchResponse> localVarResp = patchExpiringTargetsWithHttpInfo(projectKey, environmentKey, featureFlagKey, patchFlagsRequest);
        return localVarResp.getData();
    }

    /**
     * Update expiring context targets on feature flag
     * Schedule a context for removal from individual targeting on a feature flag. The flag must already individually target the context.  You can add, update, or remove a scheduled removal date. You can only schedule a context for removal on a single variation per flag.  Updating an expiring target uses the semantic patch format. To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating expiring targets.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating expiring targets&lt;/strong&gt;&lt;/summary&gt;  #### addExpiringTarget  Adds a date and time that LaunchDarkly will remove the context from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the context from individual targeting for this flag * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;contextKey&#x60;: The context key for the context to remove from individual targeting * &#x60;contextKind&#x60;: The kind of context represented by the &#x60;contextKey&#x60;  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addExpiringTarget\&quot;,     \&quot;value\&quot;: 1754006460000,     \&quot;variationId\&quot;: \&quot;4254742c-71ae-411f-a992-43b18a51afe0\&quot;,     \&quot;contextKey\&quot;: \&quot;user-key-123abc\&quot;,     \&quot;contextKind\&quot;: \&quot;user\&quot;   }] } &#x60;&#x60;&#x60;  #### updateExpiringTarget  Updates the date and time that LaunchDarkly will remove the context from the flag&#39;s individual targeting  ##### Parameters  * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the context from individual targeting for this flag * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;contextKey&#x60;: The context key for the context to remove from individual targeting * &#x60;contextKind&#x60;: The kind of context represented by the &#x60;contextKey&#x60; * &#x60;version&#x60;: (Optional) The version of the expiring target to update. If included, update will fail if version doesn&#39;t match current version of the expiring target.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;updateExpiringTarget\&quot;,     \&quot;value\&quot;: 1754006460000,     \&quot;variationId\&quot;: \&quot;4254742c-71ae-411f-a992-43b18a51afe0\&quot;,     \&quot;contextKey\&quot;: \&quot;user-key-123abc\&quot;,     \&quot;contextKind\&quot;: \&quot;user\&quot;   }] } &#x60;&#x60;&#x60;  #### removeExpiringTarget  Removes the scheduled removal of the context from the flag&#39;s individual targeting. The context will remain part of the flag&#39;s individual targeting until you explicitly remove it, or until you schedule another removal.  ##### Parameters  * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;contextKey&#x60;: The context key for the context to remove from individual targeting * &#x60;contextKind&#x60;: The kind of context represented by the &#x60;contextKey&#x60;  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;removeExpiringTarget\&quot;,     \&quot;variationId\&quot;: \&quot;4254742c-71ae-411f-a992-43b18a51afe0\&quot;,     \&quot;contextKey\&quot;: \&quot;user-key-123abc\&quot;,     \&quot;contextKind\&quot;: \&quot;user\&quot;   }] } &#x60;&#x60;&#x60;  &lt;/details&gt; 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param patchFlagsRequest  (required)
     * @return ApiResponse&lt;ExpiringTargetPatchResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring target response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ExpiringTargetPatchResponse> patchExpiringTargetsWithHttpInfo(String projectKey, String environmentKey, String featureFlagKey, PatchFlagsRequest patchFlagsRequest) throws ApiException {
        okhttp3.Call localVarCall = patchExpiringTargetsValidateBeforeCall(projectKey, environmentKey, featureFlagKey, patchFlagsRequest, null);
        Type localVarReturnType = new TypeToken<ExpiringTargetPatchResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update expiring context targets on feature flag (asynchronously)
     * Schedule a context for removal from individual targeting on a feature flag. The flag must already individually target the context.  You can add, update, or remove a scheduled removal date. You can only schedule a context for removal on a single variation per flag.  Updating an expiring target uses the semantic patch format. To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating expiring targets.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating expiring targets&lt;/strong&gt;&lt;/summary&gt;  #### addExpiringTarget  Adds a date and time that LaunchDarkly will remove the context from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the context from individual targeting for this flag * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;contextKey&#x60;: The context key for the context to remove from individual targeting * &#x60;contextKind&#x60;: The kind of context represented by the &#x60;contextKey&#x60;  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addExpiringTarget\&quot;,     \&quot;value\&quot;: 1754006460000,     \&quot;variationId\&quot;: \&quot;4254742c-71ae-411f-a992-43b18a51afe0\&quot;,     \&quot;contextKey\&quot;: \&quot;user-key-123abc\&quot;,     \&quot;contextKind\&quot;: \&quot;user\&quot;   }] } &#x60;&#x60;&#x60;  #### updateExpiringTarget  Updates the date and time that LaunchDarkly will remove the context from the flag&#39;s individual targeting  ##### Parameters  * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the context from individual targeting for this flag * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;contextKey&#x60;: The context key for the context to remove from individual targeting * &#x60;contextKind&#x60;: The kind of context represented by the &#x60;contextKey&#x60; * &#x60;version&#x60;: (Optional) The version of the expiring target to update. If included, update will fail if version doesn&#39;t match current version of the expiring target.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;updateExpiringTarget\&quot;,     \&quot;value\&quot;: 1754006460000,     \&quot;variationId\&quot;: \&quot;4254742c-71ae-411f-a992-43b18a51afe0\&quot;,     \&quot;contextKey\&quot;: \&quot;user-key-123abc\&quot;,     \&quot;contextKind\&quot;: \&quot;user\&quot;   }] } &#x60;&#x60;&#x60;  #### removeExpiringTarget  Removes the scheduled removal of the context from the flag&#39;s individual targeting. The context will remain part of the flag&#39;s individual targeting until you explicitly remove it, or until you schedule another removal.  ##### Parameters  * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;contextKey&#x60;: The context key for the context to remove from individual targeting * &#x60;contextKind&#x60;: The kind of context represented by the &#x60;contextKey&#x60;  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;removeExpiringTarget\&quot;,     \&quot;variationId\&quot;: \&quot;4254742c-71ae-411f-a992-43b18a51afe0\&quot;,     \&quot;contextKey\&quot;: \&quot;user-key-123abc\&quot;,     \&quot;contextKind\&quot;: \&quot;user\&quot;   }] } &#x60;&#x60;&#x60;  &lt;/details&gt; 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param patchFlagsRequest  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring target response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchExpiringTargetsAsync(String projectKey, String environmentKey, String featureFlagKey, PatchFlagsRequest patchFlagsRequest, final ApiCallback<ExpiringTargetPatchResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = patchExpiringTargetsValidateBeforeCall(projectKey, environmentKey, featureFlagKey, patchFlagsRequest, _callback);
        Type localVarReturnType = new TypeToken<ExpiringTargetPatchResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for patchExpiringUserTargets
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param patchFlagsRequest  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchExpiringUserTargetsCall(String projectKey, String environmentKey, String featureFlagKey, PatchFlagsRequest patchFlagsRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = patchFlagsRequest;

        // create path and map variables
        String localVarPath = "/api/v2/flags/{projectKey}/{featureFlagKey}/expiring-user-targets/{environmentKey}"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()))
            .replaceAll("\\{" + "environmentKey" + "\\}", localVarApiClient.escapeString(environmentKey.toString()))
            .replaceAll("\\{" + "featureFlagKey" + "\\}", localVarApiClient.escapeString(featureFlagKey.toString()));

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
    private okhttp3.Call patchExpiringUserTargetsValidateBeforeCall(String projectKey, String environmentKey, String featureFlagKey, PatchFlagsRequest patchFlagsRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling patchExpiringUserTargets(Async)");
        }
        
        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling patchExpiringUserTargets(Async)");
        }
        
        // verify the required parameter 'featureFlagKey' is set
        if (featureFlagKey == null) {
            throw new ApiException("Missing the required parameter 'featureFlagKey' when calling patchExpiringUserTargets(Async)");
        }
        
        // verify the required parameter 'patchFlagsRequest' is set
        if (patchFlagsRequest == null) {
            throw new ApiException("Missing the required parameter 'patchFlagsRequest' when calling patchExpiringUserTargets(Async)");
        }
        

        okhttp3.Call localVarCall = patchExpiringUserTargetsCall(projectKey, environmentKey, featureFlagKey, patchFlagsRequest, _callback);
        return localVarCall;

    }

    /**
     * Update expiring user targets on feature flag
     * &gt; ### Contexts are now available &gt; &gt; After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should use [Update expiring context targets on feature flag](/tag/Feature-flags#operation/patchExpiringTargets) instead of this endpoint. To learn more, read [Contexts](https://docs.launchdarkly.com/home/contexts).  Schedule a target for removal from individual targeting on a feature flag. The flag must already serve a variation to specific targets based on their key.  You can add, update, or remove a scheduled removal date. You can only schedule a target for removal on a single variation per flag.  Updating an expiring target uses the semantic patch format. To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating expiring user targets.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating expiring user targets&lt;/strong&gt;&lt;/summary&gt;  #### addExpireUserTargetDate  Adds a date and time that LaunchDarkly will remove the user from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the user from individual targeting for this flag * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;userKey&#x60;: The user key for the user to remove from individual targeting  #### updateExpireUserTargetDate  Updates the date and time that LaunchDarkly will remove the user from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the user from individual targeting for this flag * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;userKey&#x60;: The user key for the user to remove from individual targeting * &#x60;version&#x60;: (Optional) The version of the expiring user target to update. If included, update will fail if version doesn&#39;t match current version of the expiring user target.  #### removeExpireUserTargetDate  Removes the scheduled removal of the user from the flag&#39;s individual targeting. The user will remain part of the flag&#39;s individual targeting until you explicitly remove them, or until you schedule another removal.  ##### Parameters  * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;userKey&#x60;: The user key for the user to remove from individual targeting  &lt;/details&gt; 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param patchFlagsRequest  (required)
     * @return ExpiringUserTargetPatchResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ExpiringUserTargetPatchResponse patchExpiringUserTargets(String projectKey, String environmentKey, String featureFlagKey, PatchFlagsRequest patchFlagsRequest) throws ApiException {
        ApiResponse<ExpiringUserTargetPatchResponse> localVarResp = patchExpiringUserTargetsWithHttpInfo(projectKey, environmentKey, featureFlagKey, patchFlagsRequest);
        return localVarResp.getData();
    }

    /**
     * Update expiring user targets on feature flag
     * &gt; ### Contexts are now available &gt; &gt; After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should use [Update expiring context targets on feature flag](/tag/Feature-flags#operation/patchExpiringTargets) instead of this endpoint. To learn more, read [Contexts](https://docs.launchdarkly.com/home/contexts).  Schedule a target for removal from individual targeting on a feature flag. The flag must already serve a variation to specific targets based on their key.  You can add, update, or remove a scheduled removal date. You can only schedule a target for removal on a single variation per flag.  Updating an expiring target uses the semantic patch format. To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating expiring user targets.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating expiring user targets&lt;/strong&gt;&lt;/summary&gt;  #### addExpireUserTargetDate  Adds a date and time that LaunchDarkly will remove the user from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the user from individual targeting for this flag * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;userKey&#x60;: The user key for the user to remove from individual targeting  #### updateExpireUserTargetDate  Updates the date and time that LaunchDarkly will remove the user from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the user from individual targeting for this flag * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;userKey&#x60;: The user key for the user to remove from individual targeting * &#x60;version&#x60;: (Optional) The version of the expiring user target to update. If included, update will fail if version doesn&#39;t match current version of the expiring user target.  #### removeExpireUserTargetDate  Removes the scheduled removal of the user from the flag&#39;s individual targeting. The user will remain part of the flag&#39;s individual targeting until you explicitly remove them, or until you schedule another removal.  ##### Parameters  * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;userKey&#x60;: The user key for the user to remove from individual targeting  &lt;/details&gt; 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param patchFlagsRequest  (required)
     * @return ApiResponse&lt;ExpiringUserTargetPatchResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ExpiringUserTargetPatchResponse> patchExpiringUserTargetsWithHttpInfo(String projectKey, String environmentKey, String featureFlagKey, PatchFlagsRequest patchFlagsRequest) throws ApiException {
        okhttp3.Call localVarCall = patchExpiringUserTargetsValidateBeforeCall(projectKey, environmentKey, featureFlagKey, patchFlagsRequest, null);
        Type localVarReturnType = new TypeToken<ExpiringUserTargetPatchResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update expiring user targets on feature flag (asynchronously)
     * &gt; ### Contexts are now available &gt; &gt; After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should use [Update expiring context targets on feature flag](/tag/Feature-flags#operation/patchExpiringTargets) instead of this endpoint. To learn more, read [Contexts](https://docs.launchdarkly.com/home/contexts).  Schedule a target for removal from individual targeting on a feature flag. The flag must already serve a variation to specific targets based on their key.  You can add, update, or remove a scheduled removal date. You can only schedule a target for removal on a single variation per flag.  Updating an expiring target uses the semantic patch format. To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating expiring user targets.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating expiring user targets&lt;/strong&gt;&lt;/summary&gt;  #### addExpireUserTargetDate  Adds a date and time that LaunchDarkly will remove the user from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the user from individual targeting for this flag * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;userKey&#x60;: The user key for the user to remove from individual targeting  #### updateExpireUserTargetDate  Updates the date and time that LaunchDarkly will remove the user from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the user from individual targeting for this flag * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;userKey&#x60;: The user key for the user to remove from individual targeting * &#x60;version&#x60;: (Optional) The version of the expiring user target to update. If included, update will fail if version doesn&#39;t match current version of the expiring user target.  #### removeExpireUserTargetDate  Removes the scheduled removal of the user from the flag&#39;s individual targeting. The user will remain part of the flag&#39;s individual targeting until you explicitly remove them, or until you schedule another removal.  ##### Parameters  * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;userKey&#x60;: The user key for the user to remove from individual targeting  &lt;/details&gt; 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param patchFlagsRequest  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchExpiringUserTargetsAsync(String projectKey, String environmentKey, String featureFlagKey, PatchFlagsRequest patchFlagsRequest, final ApiCallback<ExpiringUserTargetPatchResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = patchExpiringUserTargetsValidateBeforeCall(projectKey, environmentKey, featureFlagKey, patchFlagsRequest, _callback);
        Type localVarReturnType = new TypeToken<ExpiringUserTargetPatchResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for patchFeatureFlag
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key. The key identifies the flag in your code. (required)
     * @param patchWithComment  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Global flag response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Approval is required to make this request </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchFeatureFlagCall(String projectKey, String featureFlagKey, PatchWithComment patchWithComment, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = patchWithComment;

        // create path and map variables
        String localVarPath = "/api/v2/flags/{projectKey}/{featureFlagKey}"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()))
            .replaceAll("\\{" + "featureFlagKey" + "\\}", localVarApiClient.escapeString(featureFlagKey.toString()));

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
    private okhttp3.Call patchFeatureFlagValidateBeforeCall(String projectKey, String featureFlagKey, PatchWithComment patchWithComment, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling patchFeatureFlag(Async)");
        }
        
        // verify the required parameter 'featureFlagKey' is set
        if (featureFlagKey == null) {
            throw new ApiException("Missing the required parameter 'featureFlagKey' when calling patchFeatureFlag(Async)");
        }
        
        // verify the required parameter 'patchWithComment' is set
        if (patchWithComment == null) {
            throw new ApiException("Missing the required parameter 'patchWithComment' when calling patchFeatureFlag(Async)");
        }
        

        okhttp3.Call localVarCall = patchFeatureFlagCall(projectKey, featureFlagKey, patchWithComment, _callback);
        return localVarCall;

    }

    /**
     * Update feature flag
     * Perform a partial update to a feature flag. The request body must be a valid semantic patch, JSON patch, or JSON merge patch. To learn more the different formats, read [Updates](/#section/Overview/Updates).  ### Using semantic patches on a feature flag  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  The body of a semantic patch request for updating feature flags takes the following properties:  * &#x60;comment&#x60; (string): (Optional) A description of the update. * &#x60;environmentKey&#x60; (string): (Required for some instructions only) The key of the LaunchDarkly environment. * &#x60;instructions&#x60; (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a &#x60;kind&#x60; property that indicates the instruction. If the action requires parameters, you must include those parameters as additional fields in the object. The body of a single semantic patch can contain many different instructions.  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating feature flags.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;turning flags on and off&lt;/strong&gt;&lt;/summary&gt;  These instructions require the &#x60;environmentKey&#x60; parameter.  #### turnFlagOff  Sets the flag&#39;s targeting state to **Off**.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnFlagOff\&quot; } ] } &#x60;&#x60;&#x60;  #### turnFlagOn  Sets the flag&#39;s targeting state to **On**.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnFlagOn\&quot; } ] } &#x60;&#x60;&#x60;  &lt;/details&gt;&lt;br /&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;working with targeting and variations&lt;/strong&gt;&lt;/summary&gt;  These instructions require the &#x60;environmentKey&#x60; parameter.  Several of the instructions for working with targeting and variations require flag rule IDs, variation IDs, or clause IDs as parameters. Each of these are returned as part of the [Get feature flag](/tag/Feature-flags#operation/getFeatureFlag) response. The flag rule ID is the &#x60;_id&#x60; field of each element in the &#x60;rules&#x60; array within each environment listed in the &#x60;environments&#x60; object. The variation ID is the &#x60;_id&#x60; field in each element of the &#x60;variations&#x60; array. The clause ID is the &#x60;_id&#x60; field of each element of the &#x60;clauses&#x60; array within the &#x60;rules&#x60; array within each environment listed in the &#x60;environments&#x60; object.  #### addClauses  Adds the given clauses to the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauses&#x60;: Array of clause objects, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addClauses\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauses\&quot;: [{    \&quot;contextKind\&quot;: \&quot;user\&quot;,    \&quot;attribute\&quot;: \&quot;country\&quot;,    \&quot;op\&quot;: \&quot;in\&quot;,    \&quot;negate\&quot;: false,    \&quot;values\&quot;: [\&quot;USA\&quot;, \&quot;Canada\&quot;]   }]  }] } &#x60;&#x60;&#x60;  #### addPrerequisite  Adds the flag indicated by &#x60;key&#x60; with variation &#x60;variationId&#x60; as a prerequisite to the flag in the path parameter.  ##### Parameters  - &#x60;key&#x60;: Flag key of the prerequisite flag. - &#x60;variationId&#x60;: ID of a variation of the prerequisite flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addPrerequisite\&quot;,   \&quot;key\&quot;: \&quot;example-prereq-flag-key\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### addRule  Adds a new targeting rule to the flag. The rule may contain &#x60;clauses&#x60; and serve the variation that &#x60;variationId&#x60; indicates, or serve a percentage rollout that &#x60;rolloutWeights&#x60;, &#x60;rolloutBucketBy&#x60;, and &#x60;rolloutContextKind&#x60; indicate.  If you set &#x60;beforeRuleId&#x60;, this adds the new rule before the indicated rule. Otherwise, adds the new rule to the end of the list.  ##### Parameters  - &#x60;clauses&#x60;: Array of clause objects, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case. - &#x60;beforeRuleId&#x60;: (Optional) ID of a flag rule. - Either   - &#x60;variationId&#x60;: ID of a variation of the flag.    or    - &#x60;rolloutWeights&#x60;: (Optional) Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000).   - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;.   - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example that uses a &#x60;variationId&#x60;:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addRule\&quot;,     \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,     \&quot;clauses\&quot;: [{       \&quot;contextKind\&quot;: \&quot;organization\&quot;,       \&quot;attribute\&quot;: \&quot;located_in\&quot;,       \&quot;op\&quot;: \&quot;in\&quot;,       \&quot;negate\&quot;: false,       \&quot;values\&quot;: [\&quot;Sweden\&quot;, \&quot;Norway\&quot;]     }]   }] } &#x60;&#x60;&#x60;  Here&#39;s an example that uses a percentage rollout:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addRule\&quot;,     \&quot;clauses\&quot;: [{       \&quot;contextKind\&quot;: \&quot;organization\&quot;,       \&quot;attribute\&quot;: \&quot;located_in\&quot;,       \&quot;op\&quot;: \&quot;in\&quot;,       \&quot;negate\&quot;: false,       \&quot;values\&quot;: [\&quot;Sweden\&quot;, \&quot;Norway\&quot;]     }],     \&quot;rolloutContextKind\&quot;: \&quot;organization\&quot;,     \&quot;rolloutWeights\&quot;: {       \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;: 15000, // serve 15% this variation       \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;: 85000  // serve 85% this variation     }   }] } &#x60;&#x60;&#x60;  #### addTargets  Adds context keys to the individual context targets for the context kind that &#x60;contextKind&#x60; specifies and the variation that &#x60;variationId&#x60; specifies. Returns an error if this causes the flag to target the same context key in multiple variations.  ##### Parameters  - &#x60;values&#x60;: List of context keys. - &#x60;contextKind&#x60;: (Optional) Context kind to target, defaults to &#x60;user&#x60; - &#x60;variationId&#x60;: ID of a variation on the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addTargets\&quot;,   \&quot;values\&quot;: [\&quot;context-key-123abc\&quot;, \&quot;context-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### addUserTargets  Adds user keys to the individual user targets for the variation that &#x60;variationId&#x60; specifies. Returns an error if this causes the flag to target the same user key in multiple variations. If you are working with contexts, use &#x60;addTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;values&#x60;: List of user keys. - &#x60;variationId&#x60;: ID of a variation on the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addUserTargets\&quot;,   \&quot;values\&quot;: [\&quot;user-key-123abc\&quot;, \&quot;user-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### addValuesToClause  Adds &#x60;values&#x60; to the values of the clause that &#x60;ruleId&#x60; and &#x60;clauseId&#x60; indicate. Does not update the context kind, attribute, or operator.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings, case sensitive.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addValuesToClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;,   \&quot;values\&quot;: [\&quot;beta_testers\&quot;]  }] } &#x60;&#x60;&#x60;  #### addVariation  Adds a variation to the flag.  ##### Parameters  - &#x60;value&#x60;: The variation value. - &#x60;name&#x60;: (Optional) The variation name. - &#x60;description&#x60;: (Optional) A description for the variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;addVariation\&quot;, \&quot;value\&quot;: 20, \&quot;name\&quot;: \&quot;New variation\&quot; } ] } &#x60;&#x60;&#x60;  #### clearTargets  Removes all individual targets from the variation that &#x60;variationId&#x60; specifies. This includes both user and non-user targets.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation on the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;clearTargets\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### clearUserTargets  Removes all individual user targets from the variation that &#x60;variationId&#x60; specifies. If you are working with contexts, use &#x60;clearTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation on the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;clearUserTargets\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### removeClauses  Removes the clauses specified by &#x60;clauseIds&#x60; from the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseIds&#x60;: Array of IDs of clauses in the rule.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeClauses\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseIds\&quot;: [\&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;, \&quot;36a461dc-235e-4b08-97b9-73ce9365873e\&quot;]  }] } &#x60;&#x60;&#x60;  #### removePrerequisite  Removes the prerequisite flag indicated by &#x60;key&#x60;. Does nothing if this prerequisite does not exist.  ##### Parameters  - &#x60;key&#x60;: Flag key of an existing prerequisite flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removePrerequisite\&quot;, \&quot;key\&quot;: \&quot;prereq-flag-key-123abc\&quot; } ] } &#x60;&#x60;&#x60;  #### removeRule  Removes the targeting rule specified by &#x60;ruleId&#x60;. Does nothing if the rule does not exist.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removeRule\&quot;, \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot; } ] } &#x60;&#x60;&#x60;  #### removeTargets  Removes context keys from the individual context targets for the context kind that &#x60;contextKind&#x60; specifies and the variation that &#x60;variationId&#x60; specifies. Does nothing if the flag does not target the context keys.  ##### Parameters  - &#x60;values&#x60;: List of context keys. - &#x60;contextKind&#x60;: (Optional) Context kind to target, defaults to &#x60;user&#x60; - &#x60;variationId&#x60;: ID of a flag variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeTargets\&quot;,   \&quot;values\&quot;: [\&quot;context-key-123abc\&quot;, \&quot;context-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### removeUserTargets  Removes user keys from the individual user targets for the variation that &#x60;variationId&#x60; specifies. Does nothing if the flag does not target the user keys. If you are working with contexts, use &#x60;removeTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;values&#x60;: List of user keys. - &#x60;variationId&#x60;: ID of a flag variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeUserTargets\&quot;,   \&quot;values\&quot;: [\&quot;user-key-123abc\&quot;, \&quot;user-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### removeValuesFromClause  Removes &#x60;values&#x60; from the values of the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60;. Does not update the context kind, attribute, or operator.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings, case sensitive.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeValuesFromClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;,   \&quot;values\&quot;: [\&quot;beta_testers\&quot;]  }] } &#x60;&#x60;&#x60;  #### removeVariation  Removes a variation from the flag.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation of the flag to remove.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removeVariation\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### reorderRules  Rearranges the rules to match the order given in &#x60;ruleIds&#x60;. Returns an error if &#x60;ruleIds&#x60; does not match the current set of rules on the flag.  ##### Parameters  - &#x60;ruleIds&#x60;: Array of IDs of all rules in the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;reorderRules\&quot;,   \&quot;ruleIds\&quot;: [\&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;, \&quot;63c238d1-835d-435e-8f21-c8d5e40b2a3d\&quot;]  }] } &#x60;&#x60;&#x60;  #### replacePrerequisites  Removes all existing prerequisites and replaces them with the list you provide.  ##### Parameters  - &#x60;prerequisites&#x60;: A list of prerequisites. Each item in the list must include a flag &#x60;key&#x60; and &#x60;variationId&#x60;.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replacePrerequisites\&quot;,       \&quot;prerequisites\&quot;: [         {           \&quot;key\&quot;: \&quot;prereq-flag-key-123abc\&quot;,           \&quot;variationId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;         },         {           \&quot;key\&quot;: \&quot;another-prereq-flag-key-456def\&quot;,           \&quot;variationId\&quot;: \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;         }       ]     }   ] } &#x60;&#x60;&#x60;  #### replaceRules  Removes all targeting rules for the flag and replaces them with the list you provide.  ##### Parameters  - &#x60;rules&#x60;: A list of rules.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replaceRules\&quot;,       \&quot;rules\&quot;: [         {           \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,           \&quot;description\&quot;: \&quot;My new rule\&quot;,           \&quot;clauses\&quot;: [             {               \&quot;contextKind\&quot;: \&quot;user\&quot;,               \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,               \&quot;op\&quot;: \&quot;segmentMatch\&quot;,               \&quot;values\&quot;: [\&quot;test\&quot;]             }           ],           \&quot;trackEvents\&quot;: true         }       ]     }   ] } &#x60;&#x60;&#x60;  #### replaceTargets  Removes all existing targeting and replaces it with the list of targets you provide.  ##### Parameters  - &#x60;targets&#x60;: A list of context targeting. Each item in the list includes an optional &#x60;contextKind&#x60; that defaults to &#x60;user&#x60;, a required &#x60;variationId&#x60;, and a required list of &#x60;values&#x60;.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replaceTargets\&quot;,       \&quot;targets\&quot;: [         {           \&quot;contextKind\&quot;: \&quot;user\&quot;,           \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,           \&quot;values\&quot;: [\&quot;user-key-123abc\&quot;]         },         {           \&quot;contextKind\&quot;: \&quot;device\&quot;,           \&quot;variationId\&quot;: \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;,           \&quot;values\&quot;: [\&quot;device-key-456def\&quot;]         }       ]     }       ] } &#x60;&#x60;&#x60;  #### replaceUserTargets  Removes all existing user targeting and replaces it with the list of targets you provide. In the list of targets, you must include a target for each of the flag&#39;s variations. If you are working with contexts, use &#x60;replaceTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;targets&#x60;: A list of user targeting. Each item in the list must include a &#x60;variationId&#x60; and a list of &#x60;values&#x60;.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replaceUserTargets\&quot;,       \&quot;targets\&quot;: [         {           \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,           \&quot;values\&quot;: [\&quot;user-key-123abc\&quot;, \&quot;user-key-456def\&quot;]         },         {           \&quot;variationId\&quot;: \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;,           \&quot;values\&quot;: [\&quot;user-key-789ghi\&quot;]         }       ]     }   ] } &#x60;&#x60;&#x60;  #### updateClause  Replaces the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60; with &#x60;clause&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;clause&#x60;: New &#x60;clause&#x60; object, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;updateClause\&quot;,     \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,     \&quot;clauseId\&quot;: \&quot;10c7462a-2062-45ba-a8bb-dfb3de0f8af5\&quot;,     \&quot;clause\&quot;: {       \&quot;contextKind\&quot;: \&quot;user\&quot;,       \&quot;attribute\&quot;: \&quot;country\&quot;,       \&quot;op\&quot;: \&quot;in\&quot;,       \&quot;negate\&quot;: false,       \&quot;values\&quot;: [\&quot;Mexico\&quot;, \&quot;Canada\&quot;]     }   }] } &#x60;&#x60;&#x60;  #### updateDefaultVariation  Updates the default on or off variation of the flag.  ##### Parameters  - &#x60;onVariationValue&#x60;: (Optional) The value of the variation of the new on variation. - &#x60;offVariationValue&#x60;: (Optional) The value of the variation of the new off variation  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateDefaultVariation\&quot;, \&quot;OnVariationValue\&quot;: true, \&quot;OffVariationValue\&quot;: false } ] } &#x60;&#x60;&#x60;  #### updateFallthroughVariationOrRollout  Updates the default or \&quot;fallthrough\&quot; rule for the flag, which the flag serves when a context matches none of the targeting rules. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percentage rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation of the flag.  or  - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example that uses a &#x60;variationId&#x60;:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  Here&#39;s an example that uses a percentage rollout:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,   \&quot;rolloutContextKind\&quot;: \&quot;user\&quot;,   \&quot;rolloutWeights\&quot;: {    \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;: 15000, // serve 15% this variation    \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;: 85000  // serve 85% this variation   }  }] } &#x60;&#x60;&#x60;  #### updateOffVariation  Updates the default off variation to &#x60;variationId&#x60;. The flag serves the default off variation when the flag&#39;s targeting is **Off**.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation of the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateOffVariation\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### updatePrerequisite  Changes the prerequisite flag that &#x60;key&#x60; indicates to use the variation that &#x60;variationId&#x60; indicates. Returns an error if this prerequisite does not exist.  ##### Parameters  - &#x60;key&#x60;: Flag key of an existing prerequisite flag. - &#x60;variationId&#x60;: ID of a variation of the prerequisite flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updatePrerequisite\&quot;,   \&quot;key\&quot;: \&quot;example-prereq-flag-key\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### updateRuleDescription  Updates the description of the feature flag rule.  ##### Parameters  - &#x60;description&#x60;: The new human-readable description for this rule. - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleDescription\&quot;,   \&quot;description\&quot;: \&quot;New rule description\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;  }] } &#x60;&#x60;&#x60;  #### updateRuleTrackEvents  Updates whether or not LaunchDarkly tracks events for the feature flag associated with this rule.  ##### Parameters  - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the flag. - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleTrackEvents\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;trackEvents\&quot;: true  }] } &#x60;&#x60;&#x60;  #### updateRuleVariationOrRollout  Updates what &#x60;ruleId&#x60; serves when its clauses evaluate to true. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percent rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;variationId&#x60;: ID of a variation of the flag.    or  - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleVariationOrRollout\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### updateTrackEvents  Updates whether or not LaunchDarkly tracks events for the feature flag, for all rules.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateTrackEvents\&quot;, \&quot;trackEvents\&quot;: true } ] } &#x60;&#x60;&#x60;  #### updateTrackEventsFallthrough  Updates whether or not LaunchDarkly tracks events for the feature flag, for the default rule.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateTrackEventsFallthrough\&quot;, \&quot;trackEvents\&quot;: true } ] } &#x60;&#x60;&#x60;  #### updateVariation  Updates a variation of the flag.  ##### Parameters  - &#x60;variationId&#x60;: The ID of the variation to update. - &#x60;name&#x60;: (Optional) The updated variation name. - &#x60;value&#x60;: (Optional) The updated variation value. - &#x60;description&#x60;: (Optional) The updated variation description.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateVariation\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;, \&quot;value\&quot;: 20 } ] } &#x60;&#x60;&#x60;  &lt;/details&gt;&lt;br /&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating flag settings&lt;/strong&gt;&lt;/summary&gt;  These instructions do not require the &#x60;environmentKey&#x60; parameter. They make changes that apply to the flag across all environments.  #### addCustomProperties  Adds a new custom property to the feature flag. Custom properties are used to associate feature flags with LaunchDarkly integrations. For example, if you create an integration with an issue tracking service, you may want to associate a flag with a list of issues related to a feature&#39;s development.  ##### Parameters   - &#x60;key&#x60;: The custom property key.  - &#x60;name&#x60;: The custom property name.  - &#x60;values&#x60;: A list of the associated values for the custom property.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addCustomProperties\&quot;,   \&quot;key\&quot;: \&quot;example-custom-property\&quot;,   \&quot;name\&quot;: \&quot;Example custom property\&quot;,   \&quot;values\&quot;: [\&quot;value1\&quot;, \&quot;value2\&quot;]  }] } &#x60;&#x60;&#x60;  #### addTags  Adds tags to the feature flag.  ##### Parameters  - &#x60;values&#x60;: A list of tags to add.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;addTags\&quot;, \&quot;values\&quot;: [\&quot;tag1\&quot;, \&quot;tag2\&quot;] } ] } &#x60;&#x60;&#x60;  #### makeFlagPermanent  Marks the feature flag as permanent. LaunchDarkly does not prompt you to remove permanent flags, even if one variation is rolled out to all your customers.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;makeFlagPermanent\&quot; } ] } &#x60;&#x60;&#x60;  #### makeFlagTemporary  Marks the feature flag as temporary.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;makeFlagTemporary\&quot; } ] } &#x60;&#x60;&#x60;  #### removeCustomProperties  Removes the associated values from a custom property. If all the associated values are removed, this instruction also removes the custom property.  ##### Parameters   - &#x60;key&#x60;: The custom property key.  - &#x60;values&#x60;: A list of the associated values to remove from the custom property.  &#x60;&#x60;&#x60;json {  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;replaceCustomProperties\&quot;,   \&quot;key\&quot;: \&quot;example-custom-property\&quot;,   \&quot;values\&quot;: [\&quot;value1\&quot;, \&quot;value2\&quot;]  }] } &#x60;&#x60;&#x60;  #### removeMaintainer  Removes the flag&#39;s maintainer. To set a new maintainer, use the flag&#39;s **Settings** tab in the LaunchDarkly user interface.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removeMaintainer\&quot; } ] } &#x60;&#x60;&#x60;  #### removeTags  Removes tags from the feature flag.  ##### Parameters  - &#x60;values&#x60;: A list of tags to remove.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removeTags\&quot;, \&quot;values\&quot;: [\&quot;tag1\&quot;, \&quot;tag2\&quot;] } ] } &#x60;&#x60;&#x60;  #### replaceCustomProperties  Replaces the existing associated values for a custom property with the new values.  ##### Parameters   - &#x60;key&#x60;: The custom property key.  - &#x60;name&#x60;: The custom property name.  - &#x60;values&#x60;: A list of the new associated values for the custom property.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;instructions\&quot;: [{    \&quot;kind\&quot;: \&quot;replaceCustomProperties\&quot;,    \&quot;key\&quot;: \&quot;example-custom-property\&quot;,    \&quot;name\&quot;: \&quot;Example custom property\&quot;,    \&quot;values\&quot;: [\&quot;value1\&quot;, \&quot;value2\&quot;]  }] } &#x60;&#x60;&#x60;  #### turnOffClientSideAvailability  Turns off client-side SDK availability for the flag. This is equivalent to unchecking the **SDKs using Mobile Key** and/or **SDKs using client-side ID** boxes for the flag. If you&#39;re using a client-side or mobile SDK, you must expose your feature flags in order for the client-side or mobile SDKs to evaluate them.  ##### Parameters  - &#x60;value&#x60;: Use \&quot;usingMobileKey\&quot; to turn off availability for mobile SDKs. Use \&quot;usingEnvironmentId\&quot; to turn on availability for client-side SDKs.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnOffClientSideAvailability\&quot;, \&quot;value\&quot;: \&quot;usingMobileKey\&quot; } ] } &#x60;&#x60;&#x60;  #### turnOnClientSideAvailability  Turns on client-side SDK availability for the flag. This is equivalent to unchecking the **SDKs using Mobile Key** and/or **SDKs using client-side ID** boxes for the flag. If you&#39;re using a client-side or mobile SDK, you must expose your feature flags in order for the client-side or mobile SDKs to evaluate them.  ##### Parameters  - &#x60;value&#x60;: Use \&quot;usingMobileKey\&quot; to turn on availability for mobile SDKs. Use \&quot;usingEnvironmentId\&quot; to turn on availability for client-side SDKs.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnOnClientSideAvailability\&quot;, \&quot;value\&quot;: \&quot;usingMobileKey\&quot; } ] } &#x60;&#x60;&#x60;  #### updateDescription  Updates the feature flag description.  ##### Parameters  - &#x60;value&#x60;: The new description.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateDescription\&quot;, \&quot;value\&quot;: \&quot;Updated flag description\&quot; } ] } &#x60;&#x60;&#x60; #### updateMaintainerMember  Updates the maintainer of the flag to an existing member and removes the existing maintainer.  ##### Parameters  - &#x60;value&#x60;: The ID of the member.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateMaintainerMember\&quot;, \&quot;value\&quot;: \&quot;61e9b714fd47591727db558a\&quot; } ] } &#x60;&#x60;&#x60;  #### updateMaintainerTeam  Updates the maintainer of the flag to an existing team and removes the existing maintainer.  ##### Parameters  - &#x60;value&#x60;: The key of the team.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateMaintainerTeam\&quot;, \&quot;value\&quot;: \&quot;example-team-key\&quot; } ] } &#x60;&#x60;&#x60;  #### updateName  Updates the feature flag name.  ##### Parameters  - &#x60;value&#x60;: The new name.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateName\&quot;, \&quot;value\&quot;: \&quot;Updated flag name\&quot; } ] } &#x60;&#x60;&#x60;  &lt;/details&gt;&lt;br /&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating the flag lifecycle&lt;/strong&gt;&lt;/summary&gt;  These instructions do not require the &#x60;environmentKey&#x60; parameter. They make changes that apply to the flag across all environments.  #### archiveFlag  Archives the feature flag. This retires it from LaunchDarkly without deleting it. You cannot archive a flag that is a prerequisite of other flags.  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;archiveFlag\&quot; } ] } &#x60;&#x60;&#x60;  #### deleteFlag  Deletes the feature flag and its rules. You cannot restore a deleted flag. If this flag is requested again, the flag value defined in code will be returned for all contexts.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;deleteFlag\&quot; } ] } &#x60;&#x60;&#x60;  #### deprecateFlag  Deprecates the feature flag. This hides it from the live flags list without archiving or deleting it.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;deprecateFlag\&quot; } ] } &#x60;&#x60;&#x60;  #### restoreDeprecatedFlag  Restores the feature flag if it was previously deprecated.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;restoreDeprecatedFlag\&quot; } ] } &#x60;&#x60;&#x60;  #### restoreFlag  Restores the feature flag if it was previously archived.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;restoreFlag\&quot; } ] } &#x60;&#x60;&#x60;  &lt;/details&gt;  ### Using JSON patches on a feature flag  If you do not include the header described above, you can use a [JSON patch](/reference#updates-using-json-patch) or [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) representation of the desired changes.  When you use the update feature flag endpoint to add individual targets to a specific variation, there are two different patch documents, depending on whether there are already individual targets for the variation.  If a flag variation already has individual targets, the path for the JSON patch operation is:  &#x60;&#x60;&#x60;json {   \&quot;op\&quot;: \&quot;add\&quot;,   \&quot;path\&quot;: \&quot;/environments/devint/targets/0/values/-\&quot;,   \&quot;value\&quot;: \&quot;TestClient10\&quot; } &#x60;&#x60;&#x60;  If a flag variation does not already have individual targets, the path for the JSON patch operation is:  &#x60;&#x60;&#x60;json [   {     \&quot;op\&quot;: \&quot;add\&quot;,     \&quot;path\&quot;: \&quot;/environments/devint/targets/-\&quot;,     \&quot;value\&quot;: { \&quot;variation\&quot;: 0, \&quot;values\&quot;: [\&quot;TestClient10\&quot;] }   } ] &#x60;&#x60;&#x60;   ### Required approvals If a request attempts to alter a flag configuration in an environment where approvals are required for the flag, the request will fail with a 405. Changes to the flag configuration in that environment will require creating an [approval request](/tag/Approvals) or a [workflow](/tag/Workflows).  ### Conflicts If a flag configuration change made through this endpoint would cause a pending scheduled change or approval request to fail, this endpoint will return a 400. You can ignore this check by adding an &#x60;ignoreConflicts&#x60; query parameter set to &#x60;true&#x60;.  ### Migration flags For migration flags, the cohort information is included in the &#x60;rules&#x60; property of a flag&#39;s response. You can update cohorts by updating &#x60;rules&#x60;. Default cohort information is included in the &#x60;fallthrough&#x60; property of a flag&#39;s response. You can update the default cohort by updating &#x60;fallthrough&#x60;. When you update the rollout for a cohort or the default cohort through the API, provide a rollout instead of a single &#x60;variationId&#x60;. To learn more, read [Migration Flags](https://docs.launchdarkly.com/home/flag-types/migration-flags). 
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key. The key identifies the flag in your code. (required)
     * @param patchWithComment  (required)
     * @return FeatureFlag
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Global flag response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Approval is required to make this request </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public FeatureFlag patchFeatureFlag(String projectKey, String featureFlagKey, PatchWithComment patchWithComment) throws ApiException {
        ApiResponse<FeatureFlag> localVarResp = patchFeatureFlagWithHttpInfo(projectKey, featureFlagKey, patchWithComment);
        return localVarResp.getData();
    }

    /**
     * Update feature flag
     * Perform a partial update to a feature flag. The request body must be a valid semantic patch, JSON patch, or JSON merge patch. To learn more the different formats, read [Updates](/#section/Overview/Updates).  ### Using semantic patches on a feature flag  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  The body of a semantic patch request for updating feature flags takes the following properties:  * &#x60;comment&#x60; (string): (Optional) A description of the update. * &#x60;environmentKey&#x60; (string): (Required for some instructions only) The key of the LaunchDarkly environment. * &#x60;instructions&#x60; (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a &#x60;kind&#x60; property that indicates the instruction. If the action requires parameters, you must include those parameters as additional fields in the object. The body of a single semantic patch can contain many different instructions.  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating feature flags.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;turning flags on and off&lt;/strong&gt;&lt;/summary&gt;  These instructions require the &#x60;environmentKey&#x60; parameter.  #### turnFlagOff  Sets the flag&#39;s targeting state to **Off**.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnFlagOff\&quot; } ] } &#x60;&#x60;&#x60;  #### turnFlagOn  Sets the flag&#39;s targeting state to **On**.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnFlagOn\&quot; } ] } &#x60;&#x60;&#x60;  &lt;/details&gt;&lt;br /&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;working with targeting and variations&lt;/strong&gt;&lt;/summary&gt;  These instructions require the &#x60;environmentKey&#x60; parameter.  Several of the instructions for working with targeting and variations require flag rule IDs, variation IDs, or clause IDs as parameters. Each of these are returned as part of the [Get feature flag](/tag/Feature-flags#operation/getFeatureFlag) response. The flag rule ID is the &#x60;_id&#x60; field of each element in the &#x60;rules&#x60; array within each environment listed in the &#x60;environments&#x60; object. The variation ID is the &#x60;_id&#x60; field in each element of the &#x60;variations&#x60; array. The clause ID is the &#x60;_id&#x60; field of each element of the &#x60;clauses&#x60; array within the &#x60;rules&#x60; array within each environment listed in the &#x60;environments&#x60; object.  #### addClauses  Adds the given clauses to the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauses&#x60;: Array of clause objects, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addClauses\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauses\&quot;: [{    \&quot;contextKind\&quot;: \&quot;user\&quot;,    \&quot;attribute\&quot;: \&quot;country\&quot;,    \&quot;op\&quot;: \&quot;in\&quot;,    \&quot;negate\&quot;: false,    \&quot;values\&quot;: [\&quot;USA\&quot;, \&quot;Canada\&quot;]   }]  }] } &#x60;&#x60;&#x60;  #### addPrerequisite  Adds the flag indicated by &#x60;key&#x60; with variation &#x60;variationId&#x60; as a prerequisite to the flag in the path parameter.  ##### Parameters  - &#x60;key&#x60;: Flag key of the prerequisite flag. - &#x60;variationId&#x60;: ID of a variation of the prerequisite flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addPrerequisite\&quot;,   \&quot;key\&quot;: \&quot;example-prereq-flag-key\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### addRule  Adds a new targeting rule to the flag. The rule may contain &#x60;clauses&#x60; and serve the variation that &#x60;variationId&#x60; indicates, or serve a percentage rollout that &#x60;rolloutWeights&#x60;, &#x60;rolloutBucketBy&#x60;, and &#x60;rolloutContextKind&#x60; indicate.  If you set &#x60;beforeRuleId&#x60;, this adds the new rule before the indicated rule. Otherwise, adds the new rule to the end of the list.  ##### Parameters  - &#x60;clauses&#x60;: Array of clause objects, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case. - &#x60;beforeRuleId&#x60;: (Optional) ID of a flag rule. - Either   - &#x60;variationId&#x60;: ID of a variation of the flag.    or    - &#x60;rolloutWeights&#x60;: (Optional) Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000).   - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;.   - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example that uses a &#x60;variationId&#x60;:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addRule\&quot;,     \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,     \&quot;clauses\&quot;: [{       \&quot;contextKind\&quot;: \&quot;organization\&quot;,       \&quot;attribute\&quot;: \&quot;located_in\&quot;,       \&quot;op\&quot;: \&quot;in\&quot;,       \&quot;negate\&quot;: false,       \&quot;values\&quot;: [\&quot;Sweden\&quot;, \&quot;Norway\&quot;]     }]   }] } &#x60;&#x60;&#x60;  Here&#39;s an example that uses a percentage rollout:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addRule\&quot;,     \&quot;clauses\&quot;: [{       \&quot;contextKind\&quot;: \&quot;organization\&quot;,       \&quot;attribute\&quot;: \&quot;located_in\&quot;,       \&quot;op\&quot;: \&quot;in\&quot;,       \&quot;negate\&quot;: false,       \&quot;values\&quot;: [\&quot;Sweden\&quot;, \&quot;Norway\&quot;]     }],     \&quot;rolloutContextKind\&quot;: \&quot;organization\&quot;,     \&quot;rolloutWeights\&quot;: {       \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;: 15000, // serve 15% this variation       \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;: 85000  // serve 85% this variation     }   }] } &#x60;&#x60;&#x60;  #### addTargets  Adds context keys to the individual context targets for the context kind that &#x60;contextKind&#x60; specifies and the variation that &#x60;variationId&#x60; specifies. Returns an error if this causes the flag to target the same context key in multiple variations.  ##### Parameters  - &#x60;values&#x60;: List of context keys. - &#x60;contextKind&#x60;: (Optional) Context kind to target, defaults to &#x60;user&#x60; - &#x60;variationId&#x60;: ID of a variation on the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addTargets\&quot;,   \&quot;values\&quot;: [\&quot;context-key-123abc\&quot;, \&quot;context-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### addUserTargets  Adds user keys to the individual user targets for the variation that &#x60;variationId&#x60; specifies. Returns an error if this causes the flag to target the same user key in multiple variations. If you are working with contexts, use &#x60;addTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;values&#x60;: List of user keys. - &#x60;variationId&#x60;: ID of a variation on the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addUserTargets\&quot;,   \&quot;values\&quot;: [\&quot;user-key-123abc\&quot;, \&quot;user-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### addValuesToClause  Adds &#x60;values&#x60; to the values of the clause that &#x60;ruleId&#x60; and &#x60;clauseId&#x60; indicate. Does not update the context kind, attribute, or operator.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings, case sensitive.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addValuesToClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;,   \&quot;values\&quot;: [\&quot;beta_testers\&quot;]  }] } &#x60;&#x60;&#x60;  #### addVariation  Adds a variation to the flag.  ##### Parameters  - &#x60;value&#x60;: The variation value. - &#x60;name&#x60;: (Optional) The variation name. - &#x60;description&#x60;: (Optional) A description for the variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;addVariation\&quot;, \&quot;value\&quot;: 20, \&quot;name\&quot;: \&quot;New variation\&quot; } ] } &#x60;&#x60;&#x60;  #### clearTargets  Removes all individual targets from the variation that &#x60;variationId&#x60; specifies. This includes both user and non-user targets.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation on the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;clearTargets\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### clearUserTargets  Removes all individual user targets from the variation that &#x60;variationId&#x60; specifies. If you are working with contexts, use &#x60;clearTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation on the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;clearUserTargets\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### removeClauses  Removes the clauses specified by &#x60;clauseIds&#x60; from the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseIds&#x60;: Array of IDs of clauses in the rule.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeClauses\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseIds\&quot;: [\&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;, \&quot;36a461dc-235e-4b08-97b9-73ce9365873e\&quot;]  }] } &#x60;&#x60;&#x60;  #### removePrerequisite  Removes the prerequisite flag indicated by &#x60;key&#x60;. Does nothing if this prerequisite does not exist.  ##### Parameters  - &#x60;key&#x60;: Flag key of an existing prerequisite flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removePrerequisite\&quot;, \&quot;key\&quot;: \&quot;prereq-flag-key-123abc\&quot; } ] } &#x60;&#x60;&#x60;  #### removeRule  Removes the targeting rule specified by &#x60;ruleId&#x60;. Does nothing if the rule does not exist.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removeRule\&quot;, \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot; } ] } &#x60;&#x60;&#x60;  #### removeTargets  Removes context keys from the individual context targets for the context kind that &#x60;contextKind&#x60; specifies and the variation that &#x60;variationId&#x60; specifies. Does nothing if the flag does not target the context keys.  ##### Parameters  - &#x60;values&#x60;: List of context keys. - &#x60;contextKind&#x60;: (Optional) Context kind to target, defaults to &#x60;user&#x60; - &#x60;variationId&#x60;: ID of a flag variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeTargets\&quot;,   \&quot;values\&quot;: [\&quot;context-key-123abc\&quot;, \&quot;context-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### removeUserTargets  Removes user keys from the individual user targets for the variation that &#x60;variationId&#x60; specifies. Does nothing if the flag does not target the user keys. If you are working with contexts, use &#x60;removeTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;values&#x60;: List of user keys. - &#x60;variationId&#x60;: ID of a flag variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeUserTargets\&quot;,   \&quot;values\&quot;: [\&quot;user-key-123abc\&quot;, \&quot;user-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### removeValuesFromClause  Removes &#x60;values&#x60; from the values of the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60;. Does not update the context kind, attribute, or operator.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings, case sensitive.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeValuesFromClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;,   \&quot;values\&quot;: [\&quot;beta_testers\&quot;]  }] } &#x60;&#x60;&#x60;  #### removeVariation  Removes a variation from the flag.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation of the flag to remove.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removeVariation\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### reorderRules  Rearranges the rules to match the order given in &#x60;ruleIds&#x60;. Returns an error if &#x60;ruleIds&#x60; does not match the current set of rules on the flag.  ##### Parameters  - &#x60;ruleIds&#x60;: Array of IDs of all rules in the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;reorderRules\&quot;,   \&quot;ruleIds\&quot;: [\&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;, \&quot;63c238d1-835d-435e-8f21-c8d5e40b2a3d\&quot;]  }] } &#x60;&#x60;&#x60;  #### replacePrerequisites  Removes all existing prerequisites and replaces them with the list you provide.  ##### Parameters  - &#x60;prerequisites&#x60;: A list of prerequisites. Each item in the list must include a flag &#x60;key&#x60; and &#x60;variationId&#x60;.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replacePrerequisites\&quot;,       \&quot;prerequisites\&quot;: [         {           \&quot;key\&quot;: \&quot;prereq-flag-key-123abc\&quot;,           \&quot;variationId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;         },         {           \&quot;key\&quot;: \&quot;another-prereq-flag-key-456def\&quot;,           \&quot;variationId\&quot;: \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;         }       ]     }   ] } &#x60;&#x60;&#x60;  #### replaceRules  Removes all targeting rules for the flag and replaces them with the list you provide.  ##### Parameters  - &#x60;rules&#x60;: A list of rules.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replaceRules\&quot;,       \&quot;rules\&quot;: [         {           \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,           \&quot;description\&quot;: \&quot;My new rule\&quot;,           \&quot;clauses\&quot;: [             {               \&quot;contextKind\&quot;: \&quot;user\&quot;,               \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,               \&quot;op\&quot;: \&quot;segmentMatch\&quot;,               \&quot;values\&quot;: [\&quot;test\&quot;]             }           ],           \&quot;trackEvents\&quot;: true         }       ]     }   ] } &#x60;&#x60;&#x60;  #### replaceTargets  Removes all existing targeting and replaces it with the list of targets you provide.  ##### Parameters  - &#x60;targets&#x60;: A list of context targeting. Each item in the list includes an optional &#x60;contextKind&#x60; that defaults to &#x60;user&#x60;, a required &#x60;variationId&#x60;, and a required list of &#x60;values&#x60;.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replaceTargets\&quot;,       \&quot;targets\&quot;: [         {           \&quot;contextKind\&quot;: \&quot;user\&quot;,           \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,           \&quot;values\&quot;: [\&quot;user-key-123abc\&quot;]         },         {           \&quot;contextKind\&quot;: \&quot;device\&quot;,           \&quot;variationId\&quot;: \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;,           \&quot;values\&quot;: [\&quot;device-key-456def\&quot;]         }       ]     }       ] } &#x60;&#x60;&#x60;  #### replaceUserTargets  Removes all existing user targeting and replaces it with the list of targets you provide. In the list of targets, you must include a target for each of the flag&#39;s variations. If you are working with contexts, use &#x60;replaceTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;targets&#x60;: A list of user targeting. Each item in the list must include a &#x60;variationId&#x60; and a list of &#x60;values&#x60;.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replaceUserTargets\&quot;,       \&quot;targets\&quot;: [         {           \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,           \&quot;values\&quot;: [\&quot;user-key-123abc\&quot;, \&quot;user-key-456def\&quot;]         },         {           \&quot;variationId\&quot;: \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;,           \&quot;values\&quot;: [\&quot;user-key-789ghi\&quot;]         }       ]     }   ] } &#x60;&#x60;&#x60;  #### updateClause  Replaces the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60; with &#x60;clause&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;clause&#x60;: New &#x60;clause&#x60; object, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;updateClause\&quot;,     \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,     \&quot;clauseId\&quot;: \&quot;10c7462a-2062-45ba-a8bb-dfb3de0f8af5\&quot;,     \&quot;clause\&quot;: {       \&quot;contextKind\&quot;: \&quot;user\&quot;,       \&quot;attribute\&quot;: \&quot;country\&quot;,       \&quot;op\&quot;: \&quot;in\&quot;,       \&quot;negate\&quot;: false,       \&quot;values\&quot;: [\&quot;Mexico\&quot;, \&quot;Canada\&quot;]     }   }] } &#x60;&#x60;&#x60;  #### updateDefaultVariation  Updates the default on or off variation of the flag.  ##### Parameters  - &#x60;onVariationValue&#x60;: (Optional) The value of the variation of the new on variation. - &#x60;offVariationValue&#x60;: (Optional) The value of the variation of the new off variation  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateDefaultVariation\&quot;, \&quot;OnVariationValue\&quot;: true, \&quot;OffVariationValue\&quot;: false } ] } &#x60;&#x60;&#x60;  #### updateFallthroughVariationOrRollout  Updates the default or \&quot;fallthrough\&quot; rule for the flag, which the flag serves when a context matches none of the targeting rules. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percentage rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation of the flag.  or  - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example that uses a &#x60;variationId&#x60;:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  Here&#39;s an example that uses a percentage rollout:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,   \&quot;rolloutContextKind\&quot;: \&quot;user\&quot;,   \&quot;rolloutWeights\&quot;: {    \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;: 15000, // serve 15% this variation    \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;: 85000  // serve 85% this variation   }  }] } &#x60;&#x60;&#x60;  #### updateOffVariation  Updates the default off variation to &#x60;variationId&#x60;. The flag serves the default off variation when the flag&#39;s targeting is **Off**.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation of the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateOffVariation\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### updatePrerequisite  Changes the prerequisite flag that &#x60;key&#x60; indicates to use the variation that &#x60;variationId&#x60; indicates. Returns an error if this prerequisite does not exist.  ##### Parameters  - &#x60;key&#x60;: Flag key of an existing prerequisite flag. - &#x60;variationId&#x60;: ID of a variation of the prerequisite flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updatePrerequisite\&quot;,   \&quot;key\&quot;: \&quot;example-prereq-flag-key\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### updateRuleDescription  Updates the description of the feature flag rule.  ##### Parameters  - &#x60;description&#x60;: The new human-readable description for this rule. - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleDescription\&quot;,   \&quot;description\&quot;: \&quot;New rule description\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;  }] } &#x60;&#x60;&#x60;  #### updateRuleTrackEvents  Updates whether or not LaunchDarkly tracks events for the feature flag associated with this rule.  ##### Parameters  - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the flag. - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleTrackEvents\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;trackEvents\&quot;: true  }] } &#x60;&#x60;&#x60;  #### updateRuleVariationOrRollout  Updates what &#x60;ruleId&#x60; serves when its clauses evaluate to true. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percent rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;variationId&#x60;: ID of a variation of the flag.    or  - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleVariationOrRollout\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### updateTrackEvents  Updates whether or not LaunchDarkly tracks events for the feature flag, for all rules.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateTrackEvents\&quot;, \&quot;trackEvents\&quot;: true } ] } &#x60;&#x60;&#x60;  #### updateTrackEventsFallthrough  Updates whether or not LaunchDarkly tracks events for the feature flag, for the default rule.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateTrackEventsFallthrough\&quot;, \&quot;trackEvents\&quot;: true } ] } &#x60;&#x60;&#x60;  #### updateVariation  Updates a variation of the flag.  ##### Parameters  - &#x60;variationId&#x60;: The ID of the variation to update. - &#x60;name&#x60;: (Optional) The updated variation name. - &#x60;value&#x60;: (Optional) The updated variation value. - &#x60;description&#x60;: (Optional) The updated variation description.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateVariation\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;, \&quot;value\&quot;: 20 } ] } &#x60;&#x60;&#x60;  &lt;/details&gt;&lt;br /&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating flag settings&lt;/strong&gt;&lt;/summary&gt;  These instructions do not require the &#x60;environmentKey&#x60; parameter. They make changes that apply to the flag across all environments.  #### addCustomProperties  Adds a new custom property to the feature flag. Custom properties are used to associate feature flags with LaunchDarkly integrations. For example, if you create an integration with an issue tracking service, you may want to associate a flag with a list of issues related to a feature&#39;s development.  ##### Parameters   - &#x60;key&#x60;: The custom property key.  - &#x60;name&#x60;: The custom property name.  - &#x60;values&#x60;: A list of the associated values for the custom property.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addCustomProperties\&quot;,   \&quot;key\&quot;: \&quot;example-custom-property\&quot;,   \&quot;name\&quot;: \&quot;Example custom property\&quot;,   \&quot;values\&quot;: [\&quot;value1\&quot;, \&quot;value2\&quot;]  }] } &#x60;&#x60;&#x60;  #### addTags  Adds tags to the feature flag.  ##### Parameters  - &#x60;values&#x60;: A list of tags to add.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;addTags\&quot;, \&quot;values\&quot;: [\&quot;tag1\&quot;, \&quot;tag2\&quot;] } ] } &#x60;&#x60;&#x60;  #### makeFlagPermanent  Marks the feature flag as permanent. LaunchDarkly does not prompt you to remove permanent flags, even if one variation is rolled out to all your customers.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;makeFlagPermanent\&quot; } ] } &#x60;&#x60;&#x60;  #### makeFlagTemporary  Marks the feature flag as temporary.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;makeFlagTemporary\&quot; } ] } &#x60;&#x60;&#x60;  #### removeCustomProperties  Removes the associated values from a custom property. If all the associated values are removed, this instruction also removes the custom property.  ##### Parameters   - &#x60;key&#x60;: The custom property key.  - &#x60;values&#x60;: A list of the associated values to remove from the custom property.  &#x60;&#x60;&#x60;json {  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;replaceCustomProperties\&quot;,   \&quot;key\&quot;: \&quot;example-custom-property\&quot;,   \&quot;values\&quot;: [\&quot;value1\&quot;, \&quot;value2\&quot;]  }] } &#x60;&#x60;&#x60;  #### removeMaintainer  Removes the flag&#39;s maintainer. To set a new maintainer, use the flag&#39;s **Settings** tab in the LaunchDarkly user interface.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removeMaintainer\&quot; } ] } &#x60;&#x60;&#x60;  #### removeTags  Removes tags from the feature flag.  ##### Parameters  - &#x60;values&#x60;: A list of tags to remove.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removeTags\&quot;, \&quot;values\&quot;: [\&quot;tag1\&quot;, \&quot;tag2\&quot;] } ] } &#x60;&#x60;&#x60;  #### replaceCustomProperties  Replaces the existing associated values for a custom property with the new values.  ##### Parameters   - &#x60;key&#x60;: The custom property key.  - &#x60;name&#x60;: The custom property name.  - &#x60;values&#x60;: A list of the new associated values for the custom property.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;instructions\&quot;: [{    \&quot;kind\&quot;: \&quot;replaceCustomProperties\&quot;,    \&quot;key\&quot;: \&quot;example-custom-property\&quot;,    \&quot;name\&quot;: \&quot;Example custom property\&quot;,    \&quot;values\&quot;: [\&quot;value1\&quot;, \&quot;value2\&quot;]  }] } &#x60;&#x60;&#x60;  #### turnOffClientSideAvailability  Turns off client-side SDK availability for the flag. This is equivalent to unchecking the **SDKs using Mobile Key** and/or **SDKs using client-side ID** boxes for the flag. If you&#39;re using a client-side or mobile SDK, you must expose your feature flags in order for the client-side or mobile SDKs to evaluate them.  ##### Parameters  - &#x60;value&#x60;: Use \&quot;usingMobileKey\&quot; to turn off availability for mobile SDKs. Use \&quot;usingEnvironmentId\&quot; to turn on availability for client-side SDKs.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnOffClientSideAvailability\&quot;, \&quot;value\&quot;: \&quot;usingMobileKey\&quot; } ] } &#x60;&#x60;&#x60;  #### turnOnClientSideAvailability  Turns on client-side SDK availability for the flag. This is equivalent to unchecking the **SDKs using Mobile Key** and/or **SDKs using client-side ID** boxes for the flag. If you&#39;re using a client-side or mobile SDK, you must expose your feature flags in order for the client-side or mobile SDKs to evaluate them.  ##### Parameters  - &#x60;value&#x60;: Use \&quot;usingMobileKey\&quot; to turn on availability for mobile SDKs. Use \&quot;usingEnvironmentId\&quot; to turn on availability for client-side SDKs.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnOnClientSideAvailability\&quot;, \&quot;value\&quot;: \&quot;usingMobileKey\&quot; } ] } &#x60;&#x60;&#x60;  #### updateDescription  Updates the feature flag description.  ##### Parameters  - &#x60;value&#x60;: The new description.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateDescription\&quot;, \&quot;value\&quot;: \&quot;Updated flag description\&quot; } ] } &#x60;&#x60;&#x60; #### updateMaintainerMember  Updates the maintainer of the flag to an existing member and removes the existing maintainer.  ##### Parameters  - &#x60;value&#x60;: The ID of the member.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateMaintainerMember\&quot;, \&quot;value\&quot;: \&quot;61e9b714fd47591727db558a\&quot; } ] } &#x60;&#x60;&#x60;  #### updateMaintainerTeam  Updates the maintainer of the flag to an existing team and removes the existing maintainer.  ##### Parameters  - &#x60;value&#x60;: The key of the team.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateMaintainerTeam\&quot;, \&quot;value\&quot;: \&quot;example-team-key\&quot; } ] } &#x60;&#x60;&#x60;  #### updateName  Updates the feature flag name.  ##### Parameters  - &#x60;value&#x60;: The new name.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateName\&quot;, \&quot;value\&quot;: \&quot;Updated flag name\&quot; } ] } &#x60;&#x60;&#x60;  &lt;/details&gt;&lt;br /&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating the flag lifecycle&lt;/strong&gt;&lt;/summary&gt;  These instructions do not require the &#x60;environmentKey&#x60; parameter. They make changes that apply to the flag across all environments.  #### archiveFlag  Archives the feature flag. This retires it from LaunchDarkly without deleting it. You cannot archive a flag that is a prerequisite of other flags.  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;archiveFlag\&quot; } ] } &#x60;&#x60;&#x60;  #### deleteFlag  Deletes the feature flag and its rules. You cannot restore a deleted flag. If this flag is requested again, the flag value defined in code will be returned for all contexts.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;deleteFlag\&quot; } ] } &#x60;&#x60;&#x60;  #### deprecateFlag  Deprecates the feature flag. This hides it from the live flags list without archiving or deleting it.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;deprecateFlag\&quot; } ] } &#x60;&#x60;&#x60;  #### restoreDeprecatedFlag  Restores the feature flag if it was previously deprecated.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;restoreDeprecatedFlag\&quot; } ] } &#x60;&#x60;&#x60;  #### restoreFlag  Restores the feature flag if it was previously archived.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;restoreFlag\&quot; } ] } &#x60;&#x60;&#x60;  &lt;/details&gt;  ### Using JSON patches on a feature flag  If you do not include the header described above, you can use a [JSON patch](/reference#updates-using-json-patch) or [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) representation of the desired changes.  When you use the update feature flag endpoint to add individual targets to a specific variation, there are two different patch documents, depending on whether there are already individual targets for the variation.  If a flag variation already has individual targets, the path for the JSON patch operation is:  &#x60;&#x60;&#x60;json {   \&quot;op\&quot;: \&quot;add\&quot;,   \&quot;path\&quot;: \&quot;/environments/devint/targets/0/values/-\&quot;,   \&quot;value\&quot;: \&quot;TestClient10\&quot; } &#x60;&#x60;&#x60;  If a flag variation does not already have individual targets, the path for the JSON patch operation is:  &#x60;&#x60;&#x60;json [   {     \&quot;op\&quot;: \&quot;add\&quot;,     \&quot;path\&quot;: \&quot;/environments/devint/targets/-\&quot;,     \&quot;value\&quot;: { \&quot;variation\&quot;: 0, \&quot;values\&quot;: [\&quot;TestClient10\&quot;] }   } ] &#x60;&#x60;&#x60;   ### Required approvals If a request attempts to alter a flag configuration in an environment where approvals are required for the flag, the request will fail with a 405. Changes to the flag configuration in that environment will require creating an [approval request](/tag/Approvals) or a [workflow](/tag/Workflows).  ### Conflicts If a flag configuration change made through this endpoint would cause a pending scheduled change or approval request to fail, this endpoint will return a 400. You can ignore this check by adding an &#x60;ignoreConflicts&#x60; query parameter set to &#x60;true&#x60;.  ### Migration flags For migration flags, the cohort information is included in the &#x60;rules&#x60; property of a flag&#39;s response. You can update cohorts by updating &#x60;rules&#x60;. Default cohort information is included in the &#x60;fallthrough&#x60; property of a flag&#39;s response. You can update the default cohort by updating &#x60;fallthrough&#x60;. When you update the rollout for a cohort or the default cohort through the API, provide a rollout instead of a single &#x60;variationId&#x60;. To learn more, read [Migration Flags](https://docs.launchdarkly.com/home/flag-types/migration-flags). 
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key. The key identifies the flag in your code. (required)
     * @param patchWithComment  (required)
     * @return ApiResponse&lt;FeatureFlag&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Global flag response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Approval is required to make this request </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<FeatureFlag> patchFeatureFlagWithHttpInfo(String projectKey, String featureFlagKey, PatchWithComment patchWithComment) throws ApiException {
        okhttp3.Call localVarCall = patchFeatureFlagValidateBeforeCall(projectKey, featureFlagKey, patchWithComment, null);
        Type localVarReturnType = new TypeToken<FeatureFlag>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update feature flag (asynchronously)
     * Perform a partial update to a feature flag. The request body must be a valid semantic patch, JSON patch, or JSON merge patch. To learn more the different formats, read [Updates](/#section/Overview/Updates).  ### Using semantic patches on a feature flag  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  The body of a semantic patch request for updating feature flags takes the following properties:  * &#x60;comment&#x60; (string): (Optional) A description of the update. * &#x60;environmentKey&#x60; (string): (Required for some instructions only) The key of the LaunchDarkly environment. * &#x60;instructions&#x60; (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a &#x60;kind&#x60; property that indicates the instruction. If the action requires parameters, you must include those parameters as additional fields in the object. The body of a single semantic patch can contain many different instructions.  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating feature flags.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;turning flags on and off&lt;/strong&gt;&lt;/summary&gt;  These instructions require the &#x60;environmentKey&#x60; parameter.  #### turnFlagOff  Sets the flag&#39;s targeting state to **Off**.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnFlagOff\&quot; } ] } &#x60;&#x60;&#x60;  #### turnFlagOn  Sets the flag&#39;s targeting state to **On**.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnFlagOn\&quot; } ] } &#x60;&#x60;&#x60;  &lt;/details&gt;&lt;br /&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;working with targeting and variations&lt;/strong&gt;&lt;/summary&gt;  These instructions require the &#x60;environmentKey&#x60; parameter.  Several of the instructions for working with targeting and variations require flag rule IDs, variation IDs, or clause IDs as parameters. Each of these are returned as part of the [Get feature flag](/tag/Feature-flags#operation/getFeatureFlag) response. The flag rule ID is the &#x60;_id&#x60; field of each element in the &#x60;rules&#x60; array within each environment listed in the &#x60;environments&#x60; object. The variation ID is the &#x60;_id&#x60; field in each element of the &#x60;variations&#x60; array. The clause ID is the &#x60;_id&#x60; field of each element of the &#x60;clauses&#x60; array within the &#x60;rules&#x60; array within each environment listed in the &#x60;environments&#x60; object.  #### addClauses  Adds the given clauses to the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauses&#x60;: Array of clause objects, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addClauses\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauses\&quot;: [{    \&quot;contextKind\&quot;: \&quot;user\&quot;,    \&quot;attribute\&quot;: \&quot;country\&quot;,    \&quot;op\&quot;: \&quot;in\&quot;,    \&quot;negate\&quot;: false,    \&quot;values\&quot;: [\&quot;USA\&quot;, \&quot;Canada\&quot;]   }]  }] } &#x60;&#x60;&#x60;  #### addPrerequisite  Adds the flag indicated by &#x60;key&#x60; with variation &#x60;variationId&#x60; as a prerequisite to the flag in the path parameter.  ##### Parameters  - &#x60;key&#x60;: Flag key of the prerequisite flag. - &#x60;variationId&#x60;: ID of a variation of the prerequisite flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addPrerequisite\&quot;,   \&quot;key\&quot;: \&quot;example-prereq-flag-key\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### addRule  Adds a new targeting rule to the flag. The rule may contain &#x60;clauses&#x60; and serve the variation that &#x60;variationId&#x60; indicates, or serve a percentage rollout that &#x60;rolloutWeights&#x60;, &#x60;rolloutBucketBy&#x60;, and &#x60;rolloutContextKind&#x60; indicate.  If you set &#x60;beforeRuleId&#x60;, this adds the new rule before the indicated rule. Otherwise, adds the new rule to the end of the list.  ##### Parameters  - &#x60;clauses&#x60;: Array of clause objects, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case. - &#x60;beforeRuleId&#x60;: (Optional) ID of a flag rule. - Either   - &#x60;variationId&#x60;: ID of a variation of the flag.    or    - &#x60;rolloutWeights&#x60;: (Optional) Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000).   - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;.   - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example that uses a &#x60;variationId&#x60;:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addRule\&quot;,     \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,     \&quot;clauses\&quot;: [{       \&quot;contextKind\&quot;: \&quot;organization\&quot;,       \&quot;attribute\&quot;: \&quot;located_in\&quot;,       \&quot;op\&quot;: \&quot;in\&quot;,       \&quot;negate\&quot;: false,       \&quot;values\&quot;: [\&quot;Sweden\&quot;, \&quot;Norway\&quot;]     }]   }] } &#x60;&#x60;&#x60;  Here&#39;s an example that uses a percentage rollout:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addRule\&quot;,     \&quot;clauses\&quot;: [{       \&quot;contextKind\&quot;: \&quot;organization\&quot;,       \&quot;attribute\&quot;: \&quot;located_in\&quot;,       \&quot;op\&quot;: \&quot;in\&quot;,       \&quot;negate\&quot;: false,       \&quot;values\&quot;: [\&quot;Sweden\&quot;, \&quot;Norway\&quot;]     }],     \&quot;rolloutContextKind\&quot;: \&quot;organization\&quot;,     \&quot;rolloutWeights\&quot;: {       \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;: 15000, // serve 15% this variation       \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;: 85000  // serve 85% this variation     }   }] } &#x60;&#x60;&#x60;  #### addTargets  Adds context keys to the individual context targets for the context kind that &#x60;contextKind&#x60; specifies and the variation that &#x60;variationId&#x60; specifies. Returns an error if this causes the flag to target the same context key in multiple variations.  ##### Parameters  - &#x60;values&#x60;: List of context keys. - &#x60;contextKind&#x60;: (Optional) Context kind to target, defaults to &#x60;user&#x60; - &#x60;variationId&#x60;: ID of a variation on the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addTargets\&quot;,   \&quot;values\&quot;: [\&quot;context-key-123abc\&quot;, \&quot;context-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### addUserTargets  Adds user keys to the individual user targets for the variation that &#x60;variationId&#x60; specifies. Returns an error if this causes the flag to target the same user key in multiple variations. If you are working with contexts, use &#x60;addTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;values&#x60;: List of user keys. - &#x60;variationId&#x60;: ID of a variation on the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addUserTargets\&quot;,   \&quot;values\&quot;: [\&quot;user-key-123abc\&quot;, \&quot;user-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### addValuesToClause  Adds &#x60;values&#x60; to the values of the clause that &#x60;ruleId&#x60; and &#x60;clauseId&#x60; indicate. Does not update the context kind, attribute, or operator.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings, case sensitive.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addValuesToClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;,   \&quot;values\&quot;: [\&quot;beta_testers\&quot;]  }] } &#x60;&#x60;&#x60;  #### addVariation  Adds a variation to the flag.  ##### Parameters  - &#x60;value&#x60;: The variation value. - &#x60;name&#x60;: (Optional) The variation name. - &#x60;description&#x60;: (Optional) A description for the variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;addVariation\&quot;, \&quot;value\&quot;: 20, \&quot;name\&quot;: \&quot;New variation\&quot; } ] } &#x60;&#x60;&#x60;  #### clearTargets  Removes all individual targets from the variation that &#x60;variationId&#x60; specifies. This includes both user and non-user targets.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation on the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;clearTargets\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### clearUserTargets  Removes all individual user targets from the variation that &#x60;variationId&#x60; specifies. If you are working with contexts, use &#x60;clearTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation on the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;clearUserTargets\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### removeClauses  Removes the clauses specified by &#x60;clauseIds&#x60; from the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseIds&#x60;: Array of IDs of clauses in the rule.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeClauses\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseIds\&quot;: [\&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;, \&quot;36a461dc-235e-4b08-97b9-73ce9365873e\&quot;]  }] } &#x60;&#x60;&#x60;  #### removePrerequisite  Removes the prerequisite flag indicated by &#x60;key&#x60;. Does nothing if this prerequisite does not exist.  ##### Parameters  - &#x60;key&#x60;: Flag key of an existing prerequisite flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removePrerequisite\&quot;, \&quot;key\&quot;: \&quot;prereq-flag-key-123abc\&quot; } ] } &#x60;&#x60;&#x60;  #### removeRule  Removes the targeting rule specified by &#x60;ruleId&#x60;. Does nothing if the rule does not exist.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removeRule\&quot;, \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot; } ] } &#x60;&#x60;&#x60;  #### removeTargets  Removes context keys from the individual context targets for the context kind that &#x60;contextKind&#x60; specifies and the variation that &#x60;variationId&#x60; specifies. Does nothing if the flag does not target the context keys.  ##### Parameters  - &#x60;values&#x60;: List of context keys. - &#x60;contextKind&#x60;: (Optional) Context kind to target, defaults to &#x60;user&#x60; - &#x60;variationId&#x60;: ID of a flag variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeTargets\&quot;,   \&quot;values\&quot;: [\&quot;context-key-123abc\&quot;, \&quot;context-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### removeUserTargets  Removes user keys from the individual user targets for the variation that &#x60;variationId&#x60; specifies. Does nothing if the flag does not target the user keys. If you are working with contexts, use &#x60;removeTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;values&#x60;: List of user keys. - &#x60;variationId&#x60;: ID of a flag variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeUserTargets\&quot;,   \&quot;values\&quot;: [\&quot;user-key-123abc\&quot;, \&quot;user-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### removeValuesFromClause  Removes &#x60;values&#x60; from the values of the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60;. Does not update the context kind, attribute, or operator.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings, case sensitive.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeValuesFromClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;,   \&quot;values\&quot;: [\&quot;beta_testers\&quot;]  }] } &#x60;&#x60;&#x60;  #### removeVariation  Removes a variation from the flag.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation of the flag to remove.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removeVariation\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### reorderRules  Rearranges the rules to match the order given in &#x60;ruleIds&#x60;. Returns an error if &#x60;ruleIds&#x60; does not match the current set of rules on the flag.  ##### Parameters  - &#x60;ruleIds&#x60;: Array of IDs of all rules in the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;reorderRules\&quot;,   \&quot;ruleIds\&quot;: [\&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;, \&quot;63c238d1-835d-435e-8f21-c8d5e40b2a3d\&quot;]  }] } &#x60;&#x60;&#x60;  #### replacePrerequisites  Removes all existing prerequisites and replaces them with the list you provide.  ##### Parameters  - &#x60;prerequisites&#x60;: A list of prerequisites. Each item in the list must include a flag &#x60;key&#x60; and &#x60;variationId&#x60;.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replacePrerequisites\&quot;,       \&quot;prerequisites\&quot;: [         {           \&quot;key\&quot;: \&quot;prereq-flag-key-123abc\&quot;,           \&quot;variationId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;         },         {           \&quot;key\&quot;: \&quot;another-prereq-flag-key-456def\&quot;,           \&quot;variationId\&quot;: \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;         }       ]     }   ] } &#x60;&#x60;&#x60;  #### replaceRules  Removes all targeting rules for the flag and replaces them with the list you provide.  ##### Parameters  - &#x60;rules&#x60;: A list of rules.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replaceRules\&quot;,       \&quot;rules\&quot;: [         {           \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,           \&quot;description\&quot;: \&quot;My new rule\&quot;,           \&quot;clauses\&quot;: [             {               \&quot;contextKind\&quot;: \&quot;user\&quot;,               \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,               \&quot;op\&quot;: \&quot;segmentMatch\&quot;,               \&quot;values\&quot;: [\&quot;test\&quot;]             }           ],           \&quot;trackEvents\&quot;: true         }       ]     }   ] } &#x60;&#x60;&#x60;  #### replaceTargets  Removes all existing targeting and replaces it with the list of targets you provide.  ##### Parameters  - &#x60;targets&#x60;: A list of context targeting. Each item in the list includes an optional &#x60;contextKind&#x60; that defaults to &#x60;user&#x60;, a required &#x60;variationId&#x60;, and a required list of &#x60;values&#x60;.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replaceTargets\&quot;,       \&quot;targets\&quot;: [         {           \&quot;contextKind\&quot;: \&quot;user\&quot;,           \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,           \&quot;values\&quot;: [\&quot;user-key-123abc\&quot;]         },         {           \&quot;contextKind\&quot;: \&quot;device\&quot;,           \&quot;variationId\&quot;: \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;,           \&quot;values\&quot;: [\&quot;device-key-456def\&quot;]         }       ]     }       ] } &#x60;&#x60;&#x60;  #### replaceUserTargets  Removes all existing user targeting and replaces it with the list of targets you provide. In the list of targets, you must include a target for each of the flag&#39;s variations. If you are working with contexts, use &#x60;replaceTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;targets&#x60;: A list of user targeting. Each item in the list must include a &#x60;variationId&#x60; and a list of &#x60;values&#x60;.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replaceUserTargets\&quot;,       \&quot;targets\&quot;: [         {           \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,           \&quot;values\&quot;: [\&quot;user-key-123abc\&quot;, \&quot;user-key-456def\&quot;]         },         {           \&quot;variationId\&quot;: \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;,           \&quot;values\&quot;: [\&quot;user-key-789ghi\&quot;]         }       ]     }   ] } &#x60;&#x60;&#x60;  #### updateClause  Replaces the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60; with &#x60;clause&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;clause&#x60;: New &#x60;clause&#x60; object, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;updateClause\&quot;,     \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,     \&quot;clauseId\&quot;: \&quot;10c7462a-2062-45ba-a8bb-dfb3de0f8af5\&quot;,     \&quot;clause\&quot;: {       \&quot;contextKind\&quot;: \&quot;user\&quot;,       \&quot;attribute\&quot;: \&quot;country\&quot;,       \&quot;op\&quot;: \&quot;in\&quot;,       \&quot;negate\&quot;: false,       \&quot;values\&quot;: [\&quot;Mexico\&quot;, \&quot;Canada\&quot;]     }   }] } &#x60;&#x60;&#x60;  #### updateDefaultVariation  Updates the default on or off variation of the flag.  ##### Parameters  - &#x60;onVariationValue&#x60;: (Optional) The value of the variation of the new on variation. - &#x60;offVariationValue&#x60;: (Optional) The value of the variation of the new off variation  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateDefaultVariation\&quot;, \&quot;OnVariationValue\&quot;: true, \&quot;OffVariationValue\&quot;: false } ] } &#x60;&#x60;&#x60;  #### updateFallthroughVariationOrRollout  Updates the default or \&quot;fallthrough\&quot; rule for the flag, which the flag serves when a context matches none of the targeting rules. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percentage rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation of the flag.  or  - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example that uses a &#x60;variationId&#x60;:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  Here&#39;s an example that uses a percentage rollout:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,   \&quot;rolloutContextKind\&quot;: \&quot;user\&quot;,   \&quot;rolloutWeights\&quot;: {    \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;: 15000, // serve 15% this variation    \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;: 85000  // serve 85% this variation   }  }] } &#x60;&#x60;&#x60;  #### updateOffVariation  Updates the default off variation to &#x60;variationId&#x60;. The flag serves the default off variation when the flag&#39;s targeting is **Off**.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation of the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateOffVariation\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### updatePrerequisite  Changes the prerequisite flag that &#x60;key&#x60; indicates to use the variation that &#x60;variationId&#x60; indicates. Returns an error if this prerequisite does not exist.  ##### Parameters  - &#x60;key&#x60;: Flag key of an existing prerequisite flag. - &#x60;variationId&#x60;: ID of a variation of the prerequisite flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updatePrerequisite\&quot;,   \&quot;key\&quot;: \&quot;example-prereq-flag-key\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### updateRuleDescription  Updates the description of the feature flag rule.  ##### Parameters  - &#x60;description&#x60;: The new human-readable description for this rule. - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the flag.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleDescription\&quot;,   \&quot;description\&quot;: \&quot;New rule description\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;  }] } &#x60;&#x60;&#x60;  #### updateRuleTrackEvents  Updates whether or not LaunchDarkly tracks events for the feature flag associated with this rule.  ##### Parameters  - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the flag. - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleTrackEvents\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;trackEvents\&quot;: true  }] } &#x60;&#x60;&#x60;  #### updateRuleVariationOrRollout  Updates what &#x60;ruleId&#x60; serves when its clauses evaluate to true. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percent rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;variationId&#x60;: ID of a variation of the flag.    or  - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleVariationOrRollout\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### updateTrackEvents  Updates whether or not LaunchDarkly tracks events for the feature flag, for all rules.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateTrackEvents\&quot;, \&quot;trackEvents\&quot;: true } ] } &#x60;&#x60;&#x60;  #### updateTrackEventsFallthrough  Updates whether or not LaunchDarkly tracks events for the feature flag, for the default rule.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateTrackEventsFallthrough\&quot;, \&quot;trackEvents\&quot;: true } ] } &#x60;&#x60;&#x60;  #### updateVariation  Updates a variation of the flag.  ##### Parameters  - &#x60;variationId&#x60;: The ID of the variation to update. - &#x60;name&#x60;: (Optional) The updated variation name. - &#x60;value&#x60;: (Optional) The updated variation value. - &#x60;description&#x60;: (Optional) The updated variation description.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateVariation\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;, \&quot;value\&quot;: 20 } ] } &#x60;&#x60;&#x60;  &lt;/details&gt;&lt;br /&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating flag settings&lt;/strong&gt;&lt;/summary&gt;  These instructions do not require the &#x60;environmentKey&#x60; parameter. They make changes that apply to the flag across all environments.  #### addCustomProperties  Adds a new custom property to the feature flag. Custom properties are used to associate feature flags with LaunchDarkly integrations. For example, if you create an integration with an issue tracking service, you may want to associate a flag with a list of issues related to a feature&#39;s development.  ##### Parameters   - &#x60;key&#x60;: The custom property key.  - &#x60;name&#x60;: The custom property name.  - &#x60;values&#x60;: A list of the associated values for the custom property.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addCustomProperties\&quot;,   \&quot;key\&quot;: \&quot;example-custom-property\&quot;,   \&quot;name\&quot;: \&quot;Example custom property\&quot;,   \&quot;values\&quot;: [\&quot;value1\&quot;, \&quot;value2\&quot;]  }] } &#x60;&#x60;&#x60;  #### addTags  Adds tags to the feature flag.  ##### Parameters  - &#x60;values&#x60;: A list of tags to add.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;addTags\&quot;, \&quot;values\&quot;: [\&quot;tag1\&quot;, \&quot;tag2\&quot;] } ] } &#x60;&#x60;&#x60;  #### makeFlagPermanent  Marks the feature flag as permanent. LaunchDarkly does not prompt you to remove permanent flags, even if one variation is rolled out to all your customers.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;makeFlagPermanent\&quot; } ] } &#x60;&#x60;&#x60;  #### makeFlagTemporary  Marks the feature flag as temporary.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;makeFlagTemporary\&quot; } ] } &#x60;&#x60;&#x60;  #### removeCustomProperties  Removes the associated values from a custom property. If all the associated values are removed, this instruction also removes the custom property.  ##### Parameters   - &#x60;key&#x60;: The custom property key.  - &#x60;values&#x60;: A list of the associated values to remove from the custom property.  &#x60;&#x60;&#x60;json {  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;replaceCustomProperties\&quot;,   \&quot;key\&quot;: \&quot;example-custom-property\&quot;,   \&quot;values\&quot;: [\&quot;value1\&quot;, \&quot;value2\&quot;]  }] } &#x60;&#x60;&#x60;  #### removeMaintainer  Removes the flag&#39;s maintainer. To set a new maintainer, use the flag&#39;s **Settings** tab in the LaunchDarkly user interface.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removeMaintainer\&quot; } ] } &#x60;&#x60;&#x60;  #### removeTags  Removes tags from the feature flag.  ##### Parameters  - &#x60;values&#x60;: A list of tags to remove.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removeTags\&quot;, \&quot;values\&quot;: [\&quot;tag1\&quot;, \&quot;tag2\&quot;] } ] } &#x60;&#x60;&#x60;  #### replaceCustomProperties  Replaces the existing associated values for a custom property with the new values.  ##### Parameters   - &#x60;key&#x60;: The custom property key.  - &#x60;name&#x60;: The custom property name.  - &#x60;values&#x60;: A list of the new associated values for the custom property.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {  \&quot;instructions\&quot;: [{    \&quot;kind\&quot;: \&quot;replaceCustomProperties\&quot;,    \&quot;key\&quot;: \&quot;example-custom-property\&quot;,    \&quot;name\&quot;: \&quot;Example custom property\&quot;,    \&quot;values\&quot;: [\&quot;value1\&quot;, \&quot;value2\&quot;]  }] } &#x60;&#x60;&#x60;  #### turnOffClientSideAvailability  Turns off client-side SDK availability for the flag. This is equivalent to unchecking the **SDKs using Mobile Key** and/or **SDKs using client-side ID** boxes for the flag. If you&#39;re using a client-side or mobile SDK, you must expose your feature flags in order for the client-side or mobile SDKs to evaluate them.  ##### Parameters  - &#x60;value&#x60;: Use \&quot;usingMobileKey\&quot; to turn off availability for mobile SDKs. Use \&quot;usingEnvironmentId\&quot; to turn on availability for client-side SDKs.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnOffClientSideAvailability\&quot;, \&quot;value\&quot;: \&quot;usingMobileKey\&quot; } ] } &#x60;&#x60;&#x60;  #### turnOnClientSideAvailability  Turns on client-side SDK availability for the flag. This is equivalent to unchecking the **SDKs using Mobile Key** and/or **SDKs using client-side ID** boxes for the flag. If you&#39;re using a client-side or mobile SDK, you must expose your feature flags in order for the client-side or mobile SDKs to evaluate them.  ##### Parameters  - &#x60;value&#x60;: Use \&quot;usingMobileKey\&quot; to turn on availability for mobile SDKs. Use \&quot;usingEnvironmentId\&quot; to turn on availability for client-side SDKs.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnOnClientSideAvailability\&quot;, \&quot;value\&quot;: \&quot;usingMobileKey\&quot; } ] } &#x60;&#x60;&#x60;  #### updateDescription  Updates the feature flag description.  ##### Parameters  - &#x60;value&#x60;: The new description.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateDescription\&quot;, \&quot;value\&quot;: \&quot;Updated flag description\&quot; } ] } &#x60;&#x60;&#x60; #### updateMaintainerMember  Updates the maintainer of the flag to an existing member and removes the existing maintainer.  ##### Parameters  - &#x60;value&#x60;: The ID of the member.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateMaintainerMember\&quot;, \&quot;value\&quot;: \&quot;61e9b714fd47591727db558a\&quot; } ] } &#x60;&#x60;&#x60;  #### updateMaintainerTeam  Updates the maintainer of the flag to an existing team and removes the existing maintainer.  ##### Parameters  - &#x60;value&#x60;: The key of the team.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateMaintainerTeam\&quot;, \&quot;value\&quot;: \&quot;example-team-key\&quot; } ] } &#x60;&#x60;&#x60;  #### updateName  Updates the feature flag name.  ##### Parameters  - &#x60;value&#x60;: The new name.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateName\&quot;, \&quot;value\&quot;: \&quot;Updated flag name\&quot; } ] } &#x60;&#x60;&#x60;  &lt;/details&gt;&lt;br /&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating the flag lifecycle&lt;/strong&gt;&lt;/summary&gt;  These instructions do not require the &#x60;environmentKey&#x60; parameter. They make changes that apply to the flag across all environments.  #### archiveFlag  Archives the feature flag. This retires it from LaunchDarkly without deleting it. You cannot archive a flag that is a prerequisite of other flags.  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;archiveFlag\&quot; } ] } &#x60;&#x60;&#x60;  #### deleteFlag  Deletes the feature flag and its rules. You cannot restore a deleted flag. If this flag is requested again, the flag value defined in code will be returned for all contexts.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;deleteFlag\&quot; } ] } &#x60;&#x60;&#x60;  #### deprecateFlag  Deprecates the feature flag. This hides it from the live flags list without archiving or deleting it.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;deprecateFlag\&quot; } ] } &#x60;&#x60;&#x60;  #### restoreDeprecatedFlag  Restores the feature flag if it was previously deprecated.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;restoreDeprecatedFlag\&quot; } ] } &#x60;&#x60;&#x60;  #### restoreFlag  Restores the feature flag if it was previously archived.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;restoreFlag\&quot; } ] } &#x60;&#x60;&#x60;  &lt;/details&gt;  ### Using JSON patches on a feature flag  If you do not include the header described above, you can use a [JSON patch](/reference#updates-using-json-patch) or [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) representation of the desired changes.  When you use the update feature flag endpoint to add individual targets to a specific variation, there are two different patch documents, depending on whether there are already individual targets for the variation.  If a flag variation already has individual targets, the path for the JSON patch operation is:  &#x60;&#x60;&#x60;json {   \&quot;op\&quot;: \&quot;add\&quot;,   \&quot;path\&quot;: \&quot;/environments/devint/targets/0/values/-\&quot;,   \&quot;value\&quot;: \&quot;TestClient10\&quot; } &#x60;&#x60;&#x60;  If a flag variation does not already have individual targets, the path for the JSON patch operation is:  &#x60;&#x60;&#x60;json [   {     \&quot;op\&quot;: \&quot;add\&quot;,     \&quot;path\&quot;: \&quot;/environments/devint/targets/-\&quot;,     \&quot;value\&quot;: { \&quot;variation\&quot;: 0, \&quot;values\&quot;: [\&quot;TestClient10\&quot;] }   } ] &#x60;&#x60;&#x60;   ### Required approvals If a request attempts to alter a flag configuration in an environment where approvals are required for the flag, the request will fail with a 405. Changes to the flag configuration in that environment will require creating an [approval request](/tag/Approvals) or a [workflow](/tag/Workflows).  ### Conflicts If a flag configuration change made through this endpoint would cause a pending scheduled change or approval request to fail, this endpoint will return a 400. You can ignore this check by adding an &#x60;ignoreConflicts&#x60; query parameter set to &#x60;true&#x60;.  ### Migration flags For migration flags, the cohort information is included in the &#x60;rules&#x60; property of a flag&#39;s response. You can update cohorts by updating &#x60;rules&#x60;. Default cohort information is included in the &#x60;fallthrough&#x60; property of a flag&#39;s response. You can update the default cohort by updating &#x60;fallthrough&#x60;. When you update the rollout for a cohort or the default cohort through the API, provide a rollout instead of a single &#x60;variationId&#x60;. To learn more, read [Migration Flags](https://docs.launchdarkly.com/home/flag-types/migration-flags). 
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key. The key identifies the flag in your code. (required)
     * @param patchWithComment  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Global flag response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Approval is required to make this request </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchFeatureFlagAsync(String projectKey, String featureFlagKey, PatchWithComment patchWithComment, final ApiCallback<FeatureFlag> _callback) throws ApiException {

        okhttp3.Call localVarCall = patchFeatureFlagValidateBeforeCall(projectKey, featureFlagKey, patchWithComment, _callback);
        Type localVarReturnType = new TypeToken<FeatureFlag>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for postFeatureFlag
     * @param projectKey The project key (required)
     * @param featureFlagBody  (required)
     * @param clone The key of the feature flag to be cloned. The key identifies the flag in your code. For example, setting &#x60;clone&#x3D;flagKey&#x60; copies the full targeting configuration for all environments, including &#x60;on/off&#x60; state, from the original flag to the new flag. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Global flag response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postFeatureFlagCall(String projectKey, FeatureFlagBody featureFlagBody, String clone, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = featureFlagBody;

        // create path and map variables
        String localVarPath = "/api/v2/flags/{projectKey}"
            .replaceAll("\\{" + "projectKey" + "\\}", localVarApiClient.escapeString(projectKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (clone != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("clone", clone));
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
    private okhttp3.Call postFeatureFlagValidateBeforeCall(String projectKey, FeatureFlagBody featureFlagBody, String clone, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling postFeatureFlag(Async)");
        }
        
        // verify the required parameter 'featureFlagBody' is set
        if (featureFlagBody == null) {
            throw new ApiException("Missing the required parameter 'featureFlagBody' when calling postFeatureFlag(Async)");
        }
        

        okhttp3.Call localVarCall = postFeatureFlagCall(projectKey, featureFlagBody, clone, _callback);
        return localVarCall;

    }

    /**
     * Create a feature flag
     * Create a feature flag with the given name, key, and variations.  ### Creating a migration flag  When you create a migration flag, the variations are pre-determined based on the number of stages in the migration. To create a migration flag, omit the &#x60;variations&#x60; and &#x60;defaults&#x60; information. Instead, provide a &#x60;purpose&#x60; of &#x60;migration&#x60;, and &#x60;migrationSettings&#x60;. If you create a migration flag with six stages, &#x60;contextKind&#x60; is required. Otherwise, it should be omitted.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;key\&quot;: \&quot;flag-key-123\&quot;,   \&quot;purpose\&quot;: \&quot;migration\&quot;,   \&quot;migrationSettings\&quot;: {     \&quot;stageCount\&quot;: 6,     \&quot;contextKind\&quot;: \&quot;account\&quot;   } } &#x60;&#x60;&#x60;  To learn more, read [Migration Flags](https://docs.launchdarkly.com/home/flag-types/migration-flags). 
     * @param projectKey The project key (required)
     * @param featureFlagBody  (required)
     * @param clone The key of the feature flag to be cloned. The key identifies the flag in your code. For example, setting &#x60;clone&#x3D;flagKey&#x60; copies the full targeting configuration for all environments, including &#x60;on/off&#x60; state, from the original flag to the new flag. (optional)
     * @return FeatureFlag
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Global flag response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public FeatureFlag postFeatureFlag(String projectKey, FeatureFlagBody featureFlagBody, String clone) throws ApiException {
        ApiResponse<FeatureFlag> localVarResp = postFeatureFlagWithHttpInfo(projectKey, featureFlagBody, clone);
        return localVarResp.getData();
    }

    /**
     * Create a feature flag
     * Create a feature flag with the given name, key, and variations.  ### Creating a migration flag  When you create a migration flag, the variations are pre-determined based on the number of stages in the migration. To create a migration flag, omit the &#x60;variations&#x60; and &#x60;defaults&#x60; information. Instead, provide a &#x60;purpose&#x60; of &#x60;migration&#x60;, and &#x60;migrationSettings&#x60;. If you create a migration flag with six stages, &#x60;contextKind&#x60; is required. Otherwise, it should be omitted.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;key\&quot;: \&quot;flag-key-123\&quot;,   \&quot;purpose\&quot;: \&quot;migration\&quot;,   \&quot;migrationSettings\&quot;: {     \&quot;stageCount\&quot;: 6,     \&quot;contextKind\&quot;: \&quot;account\&quot;   } } &#x60;&#x60;&#x60;  To learn more, read [Migration Flags](https://docs.launchdarkly.com/home/flag-types/migration-flags). 
     * @param projectKey The project key (required)
     * @param featureFlagBody  (required)
     * @param clone The key of the feature flag to be cloned. The key identifies the flag in your code. For example, setting &#x60;clone&#x3D;flagKey&#x60; copies the full targeting configuration for all environments, including &#x60;on/off&#x60; state, from the original flag to the new flag. (optional)
     * @return ApiResponse&lt;FeatureFlag&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Global flag response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<FeatureFlag> postFeatureFlagWithHttpInfo(String projectKey, FeatureFlagBody featureFlagBody, String clone) throws ApiException {
        okhttp3.Call localVarCall = postFeatureFlagValidateBeforeCall(projectKey, featureFlagBody, clone, null);
        Type localVarReturnType = new TypeToken<FeatureFlag>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create a feature flag (asynchronously)
     * Create a feature flag with the given name, key, and variations.  ### Creating a migration flag  When you create a migration flag, the variations are pre-determined based on the number of stages in the migration. To create a migration flag, omit the &#x60;variations&#x60; and &#x60;defaults&#x60; information. Instead, provide a &#x60;purpose&#x60; of &#x60;migration&#x60;, and &#x60;migrationSettings&#x60;. If you create a migration flag with six stages, &#x60;contextKind&#x60; is required. Otherwise, it should be omitted.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;key\&quot;: \&quot;flag-key-123\&quot;,   \&quot;purpose\&quot;: \&quot;migration\&quot;,   \&quot;migrationSettings\&quot;: {     \&quot;stageCount\&quot;: 6,     \&quot;contextKind\&quot;: \&quot;account\&quot;   } } &#x60;&#x60;&#x60;  To learn more, read [Migration Flags](https://docs.launchdarkly.com/home/flag-types/migration-flags). 
     * @param projectKey The project key (required)
     * @param featureFlagBody  (required)
     * @param clone The key of the feature flag to be cloned. The key identifies the flag in your code. For example, setting &#x60;clone&#x3D;flagKey&#x60; copies the full targeting configuration for all environments, including &#x60;on/off&#x60; state, from the original flag to the new flag. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Global flag response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postFeatureFlagAsync(String projectKey, FeatureFlagBody featureFlagBody, String clone, final ApiCallback<FeatureFlag> _callback) throws ApiException {

        okhttp3.Call localVarCall = postFeatureFlagValidateBeforeCall(projectKey, featureFlagBody, clone, _callback);
        Type localVarReturnType = new TypeToken<FeatureFlag>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
