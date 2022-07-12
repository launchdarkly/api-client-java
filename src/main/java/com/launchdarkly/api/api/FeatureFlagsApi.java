/*
 * LaunchDarkly REST API
 * # Overview  ## Authentication  All REST API resources are authenticated with either [personal or service access tokens](https://docs.launchdarkly.com/home/account-security/api-access-tokens), or session cookies. Other authentication mechanisms are not supported. You can manage personal access tokens on your [Account settings](https://app.launchdarkly.com/settings/tokens) page.  LaunchDarkly also has SDK keys, mobile keys, and client-side IDs that are used by our server-side SDKs, mobile SDKs, and client-side SDKs, respectively. **These keys cannot be used to access our REST API**. These keys are environment-specific, and can only perform read-only operations (fetching feature flag settings).  | Auth mechanism                                                                                  | Allowed resources                                                                                     | Use cases                                          | | ----------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------- | -------------------------------------------------- | | [Personal access tokens](https://docs.launchdarkly.com/home/account-security/api-access-tokens) | Can be customized on a per-token basis                                                                | Building scripts, custom integrations, data export | | SDK keys                                                                                        | Can only access read-only SDK-specific resources and the firehose, restricted to a single environment | Server-side SDKs, Firehose API                     | | Mobile keys                                                                                     | Can only access read-only mobile SDK-specific resources, restricted to a single environment           | Mobile SDKs                                        | | Client-side ID                                                                                  | Single environment, only flags marked available to client-side                                        | Client-side JavaScript                             |  > #### Keep your access tokens and SDK keys private > > Access tokens should _never_ be exposed in untrusted contexts. Never put an access token in client-side JavaScript, or embed it in a mobile application. LaunchDarkly has special mobile keys that you can embed in mobile apps. If you accidentally expose an access token or SDK key, you can reset it from your [Account Settings](https://app.launchdarkly.com/settings#/tokens) page. > > The client-side ID is safe to embed in untrusted contexts. It's designed for use in client-side JavaScript.  ### Via request header  The preferred way to authenticate with the API is by adding an `Authorization` header containing your access token to your requests. The value of the `Authorization` header must be your access token.  Manage personal access tokens from the [Account Settings](https://app.launchdarkly.com/settings/tokens) page.  ### Via session cookie  For testing purposes, you can make API calls directly from your web browser. If you're logged in to the application, the API will use your existing session to authenticate calls.  If you have a [role](https://docs.launchdarkly.com/home/team/built-in-roles) other than Admin, or have a [custom role](https://docs.launchdarkly.com/home/team/custom-roles) defined, you may not have permission to perform some API calls. You will receive a `401` response code in that case.  > ### Modifying the Origin header causes an error > > LaunchDarkly validates that the Origin header for any API request authenticated by a session cookie matches the expected Origin header. The expected Origin header is `https://app.launchdarkly.com`. > > If the Origin header does not match what's expected, LaunchDarkly returns an error. This error can prevent the LaunchDarkly app from working correctly. > > Any browser extension that intentionally changes the Origin header can cause this problem. For example, the `Allow-Control-Allow-Origin: *` Chrome extension changes the Origin header to `http://evil.com` and causes the app to fail. > > To prevent this error, do not modify your Origin header. > > LaunchDarkly does not require origin matching when authenticating with an access token, so this issue does not affect normal API usage.  ## Representations  All resources expect and return JSON response bodies. Error responses will also send a JSON body. Read [Errors](#section/Errors) for a more detailed description of the error format used by the API.  In practice this means that you always get a response with a `Content-Type` header set to `application/json`.  In addition, request bodies for `PUT`, `POST`, `REPORT` and `PATCH` requests must be encoded as JSON with a `Content-Type` header set to `application/json`.  ### Summary and detailed representations  When you fetch a list of resources, the response includes only the most important attributes of each resource. This is a _summary representation_ of the resource. When you fetch an individual resource, such as a single feature flag, you receive a _detailed representation_ of the resource.  The best way to find a detailed representation is to follow links. Every summary representation includes a link to its detailed representation.  In most cases, the detailed representation contains all of the attributes of the resource. In a few cases, the detailed representation contains many, but not all, of the attributes of the resource. Typically this happens when an attribute of the requested resource is itself paginated. You can request that the response include a particular attribute by using the `expand` request parameter.  ### Links and addressability  The best way to navigate the API is by following links. These are attributes in representations that link to other resources. The API always uses the same format for links:  - Links to other resources within the API are encapsulated in a `_links` object. - If the resource has a corresponding link to HTML content on the site, it is stored in a special `_site` link.  Each link has two attributes: an href (the URL) and a type (the content type). For example, a feature resource might return the following:  ```json {   \"_links\": {     \"parent\": {       \"href\": \"/api/features\",       \"type\": \"application/json\"     },     \"self\": {       \"href\": \"/api/features/sort.order\",       \"type\": \"application/json\"     }   },   \"_site\": {     \"href\": \"/features/sort.order\",     \"type\": \"text/html\"   } } ```  From this, you can navigate to the parent collection of features by following the `parent` link, or navigate to the site page for the feature by following the `_site` link.  Collections are always represented as a JSON object with an `items` attribute containing an array of representations. Like all other representations, collections have `_links` defined at the top level.  Paginated collections include `first`, `last`, `next`, and `prev` links containing a URL with the respective set of elements in the collection.  ### Expanding responses  Sometimes the detailed representation of a resource does not include all of the attributes of the resource by default. If this is the case, the request method will clearly document this and describe which attributes you can include in an expanded response.  To include the additional attributes, append the `expand` request parameter to your request and add a comma-separated list of the attributes to include. For example, when you append `?expand=members,roles` to the [Get team](/tag/Teams-(beta)#operation/getTeam) endpoint, the expanded response includes both of these attributes.  ## Updates  Resources that accept partial updates use the `PATCH` verb. Most resources support the [JSON Patch](https://datatracker.ietf.org/doc/html/rfc6902) format. Some resources also support the [JSON Merge Patch](https://datatracker.ietf.org/doc/html/rfc7386) format, and some resources support the [semantic patch](/reference#updates-using-semantic-patch) format, which is a way to specify the modifications to perform as a set of executable instructions. Each resource supports optional [comments](/reference#updates-with-comments) that you can submit with updates. Comments appear in outgoing webhooks, the audit log, and other integrations.  ### Updates using JSON patch  [JSON Patch](https://datatracker.ietf.org/doc/html/rfc6902) is a way to specify the modifications to perform on a resource. JSON patch uses paths and a limited set of operations to describe how to transform the current state of the resource into a new state. JSON patch documents are always arrays, where each element contains an operation, a path to the field to update, and the new value.  For example, in this feature flag representation:  ```json {     \"name\": \"New recommendations engine\",     \"key\": \"engine.enable\",     \"description\": \"This is the description\",     ... } ``` You can change the feature flag's description with the following patch document:  ```json [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"This is the new description\" }] ```  You can specify multiple modifications to perform in a single request. You can also test that certain preconditions are met before applying the patch:  ```json [   { \"op\": \"test\", \"path\": \"/version\", \"value\": 10 },   { \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" } ] ```  The above patch request tests whether the feature flag's `version` is `10`, and if so, changes the feature flag's description.  Attributes that aren't editable, like a resource's `_links`, have names that start with an underscore.  ### Updates using JSON merge patch  [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) is another format for specifying the modifications to perform on a resource. JSON merge patch is less expressive than JSON patch but in many cases, it is simpler to construct a merge patch document. For example, you can change a feature flag's description with the following merge patch document:  ```json {   \"description\": \"New flag description\" } ```  ### Updates using semantic patch  The API also supports the semantic patch format. A semantic `PATCH` is a way to specify the modifications to perform on a resource as a set of executable instructions.  Semantic patch allows you to be explicit about intent using precise, custom instructions. In many cases, you can define semantic patch instructions independently of the current state of the resource. This can be useful when defining a change that may be applied at a future date.  To make a semantic patch request, you must append `domain-model=launchdarkly.semanticpatch` to your `Content-Type` header.  Here's how:  ``` Content-Type: application/json; domain-model=launchdarkly.semanticpatch ```  If you call a semantic patch resource without this header, you will receive a `400` response because your semantic patch will be interpreted as a JSON patch.  The body of a semantic patch request takes the following properties:  * `comment` (string): (Optional) A description of the update. * `environmentKey` (string): (Required for some resources only) The environment key. * `instructions` (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a `kind` property that indicates the instruction. If the action requires parameters, you must include those parameters as additional fields in the object. The documentation for each resource that supports semantic patch includes the available instructions and any additional parameters.  For example:  ```json {   \"comment\": \"optional comment\",   \"instructions\": [ {\"kind\": \"turnFlagOn\"} ] } ```  If any instruction in the patch encounters an error, the endpoint returns an error and will not change the resource. In general, each instruction silently does nothing if the resource is already in the state you request.  > ### Supported semantic patch API endpoints > > - [Patch experiment](/tag/Experiments-(beta)#operation/patchExperiment) > - [Patch segment](/tag/Segments#operation/patchSegment) > - [Update feature flag](/tag/Feature-flags#operation/patchFeatureFlag) > - [Update flag trigger](/tag/Flag-triggers#operation/patchTriggerWorkflow) > - [Update expiring user targets on feature flag](/tag/Feature-flags#operation/patchExpiringUserTargets) > - [Update expiring user target for flags](/tag/User-settings#operation/patchExpiringFlagsForUser) > - [Update expiring user targets for segment](/tag/Segments#operation/patchExpiringUserTargetsForSegment) > - [Update scheduled changes workflow](/tag/Scheduled-changes#operation/patchFlagConfigScheduledChange) > - [Update team](/tag/Teams-(beta)#operation/patchTeam)  ### Updates with comments  You can submit optional comments with `PATCH` changes.  To submit a comment along with a JSON Patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"patch\": [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" }] } ```  To submit a comment along with a JSON merge patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"merge\": { \"description\": \"New flag description\" } } ```  To submit a comment along with a semantic patch, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"instructions\": [ {\"kind\": \"turnFlagOn\"} ] } ```  ## Errors  The API always returns errors in a common format. Here's an example:  ```json {   \"code\": \"invalid_request\",   \"message\": \"A feature with that key already exists\",   \"id\": \"30ce6058-87da-11e4-b116-123b93f75cba\" } ```  The general class of error is indicated by the `code`. The `message` is a human-readable explanation of what went wrong. The `id` is a unique identifier. Use it when you're working with LaunchDarkly support to debug a problem with a specific API call.  ### HTTP Status - Error Response Codes  | Code | Definition        | Description                                                                                       | Possible Solution                                                | | ---- | ----------------- | ------------------------------------------------------------------------------------------- | ---------------------------------------------------------------- | | 400  | Invalid request       | The request cannot be understood.                                    | Ensure JSON syntax in request body is correct.                   | | 401  | Invalid access token      | User is unauthorized or does not have permission for this API call.                                                | Ensure your API access token is valid and has the appropriate permissions.                                     | | 403  | Forbidden         | User does not have access to this resource.                                                | Ensure that the user or access token has proper permissions set. | | 404  | Invalid resource identifier | The requested resource is not valid. | Ensure that the resource is correctly identified by id or key. | | 405  | Method not allowed | The request method is not allowed on this resource. | Ensure that the HTTP verb is correct. | | 409  | Conflict          | The API request can not be completed because it conflicted with a concurrent API request. | Retry your request.                                              | | 422  | Unprocessable entity | The API request can not be completed because the update description can not be understood. | Ensure that the request body is correct for the type of patch you are using (JSON patch or semantic patch). | 429  | Too many requests | Read [Rate limiting](/#section/Rate-limiting).                                               | Wait and try again later.                                        |  ## CORS  The LaunchDarkly API supports Cross Origin Resource Sharing (CORS) for AJAX requests from any origin. If an `Origin` header is given in a request, it will be echoed as an explicitly allowed origin. Otherwise, a wildcard is returned: `Access-Control-Allow-Origin: *`. For more information on CORS, see the [CORS W3C Recommendation](http://www.w3.org/TR/cors). Example CORS headers might look like:  ```http Access-Control-Allow-Headers: Accept, Content-Type, Content-Length, Accept-Encoding, Authorization Access-Control-Allow-Methods: OPTIONS, GET, DELETE, PATCH Access-Control-Allow-Origin: * Access-Control-Max-Age: 300 ```  You can make authenticated CORS calls just as you would make same-origin calls, using either [token or session-based authentication](#section/Authentication). If you’re using session auth, you should set the `withCredentials` property for your `xhr` request to `true`. You should never expose your access tokens to untrusted users.  ## Rate limiting  We use several rate limiting strategies to ensure the availability of our APIs. Rate-limited calls to our APIs will return a `429` status code. Calls to our APIs will include headers indicating the current rate limit status. The specific headers returned depend on the API route being called. The limits differ based on the route, authentication mechanism, and other factors. Routes that are not rate limited may not contain any of the headers described below.  > ### Rate limiting and SDKs > > LaunchDarkly SDKs are never rate limited and do not use the API endpoints defined here. LaunchDarkly uses a different set of approaches, including streaming/server-sent events and a global CDN, to ensure availability to the routes used by LaunchDarkly SDKs.  ### Global rate limits  Authenticated requests are subject to a global limit. This is the maximum number of calls that can be made to the API per ten seconds. All personal access tokens on the account share this limit, so exceeding the limit with one access token will impact other tokens. Calls that are subject to global rate limits will return the headers below:  | Header name                    | Description                                                                      | | ------------------------------ | -------------------------------------------------------------------------------- | | `X-Ratelimit-Global-Remaining` | The maximum number of requests the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`            | The time at which the current rate limit window resets in epoch milliseconds.    |  We do not publicly document the specific number of calls that can be made globally. This limit may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limit.  ### Route-level rate limits  Some authenticated routes have custom rate limits. These also reset every ten seconds. Any access tokens hitting the same route share this limit, so exceeding the limit with one access token may impact other tokens. Calls that are subject to route-level rate limits will return the headers below:  | Header name                   | Description                                                                                           | | ----------------------------- | ----------------------------------------------------------------------------------------------------- | | `X-Ratelimit-Route-Remaining` | The maximum number of requests to the current route the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`           | The time at which the current rate limit window resets in epoch milliseconds.                         |  A _route_ represents a specific URL pattern and verb. For example, the [Delete environment](/tag/Environments#operation/deleteEnvironment) endpoint is considered a single route, and each call to delete an environment counts against your route-level rate limit for that route.  We do not publicly document the specific number of calls that can be made to each endpoint per ten seconds. These limits may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limits.  ### IP-based rate limiting  We also employ IP-based rate limiting on some API routes. If you hit an IP-based rate limit, your API response will include a `Retry-After` header indicating how long to wait before re-trying the call. Clients must wait at least `Retry-After` seconds before making additional calls to our API, and should employ jitter and backoff strategies to avoid triggering rate limits again.  ## OpenAPI (Swagger)  We have a [complete OpenAPI (Swagger) specification](https://app.launchdarkly.com/api/v2/openapi.json) for our API.  You can use this specification to generate client libraries to interact with our REST API in your language of choice.  This specification is supported by several API-based tools such as Postman and Insomnia. In many cases, you can directly import our specification to ease use in navigating the APIs in the tooling.  ## Client libraries  We auto-generate multiple client libraries based on our OpenAPI specification. To learn more, visit [GitHub](https://github.com/search?q=topic%3Alaunchdarkly-api+org%3Alaunchdarkly&type=Repositories).  ## Method Overriding  Some firewalls and HTTP clients restrict the use of verbs other than `GET` and `POST`. In those environments, our API endpoints that use `PUT`, `PATCH`, and `DELETE` verbs will be inaccessible.  To avoid this issue, our API supports the `X-HTTP-Method-Override` header, allowing clients to \"tunnel\" `PUT`, `PATCH`, and `DELETE` requests via a `POST` request.  For example, if you wish to call one of our `PATCH` resources via a `POST` request, you can include `X-HTTP-Method-Override:PATCH` as a header.  ## Beta resources  We sometimes release new API resources in **beta** status before we release them with general availability.  Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  We try to promote resources into general availability as quickly as possible. This happens after sufficient testing and when we're satisfied that we no longer need to make backwards-incompatible changes.  We mark beta resources with a \"Beta\" callout in our documentation, pictured below:  > ### This feature is in beta > > To use this feature, pass in a header including the `LD-API-Version` key with value set to `beta`. Use this header with each call. To learn more, read [Beta resources](/#section/Overview/Beta-resources).  ### Using beta resources  To use a beta resource, you must include a header in the request. If you call a beta resource without this header, you'll receive a `403` response.  Use this header:  ``` LD-API-Version: beta ```  ## Versioning  We try hard to keep our REST API backwards compatible, but we occasionally have to make backwards-incompatible changes in the process of shipping new features. These breaking changes can cause unexpected behavior if you don't prepare for them accordingly.  Updates to our REST API include support for the latest features in LaunchDarkly. We also release a new version of our REST API every time we make a breaking change. We provide simultaneous support for multiple API versions so you can migrate from your current API version to a new version at your own pace.  ### Setting the API version per request  You can set the API version on a specific request by sending an `LD-API-Version` header, as shown in the example below:  ``` LD-API-Version: 20220603 ```  The header value is the version number of the API version you'd like to request. The number for each version corresponds to the date the version was released in yyyymmdd format. In the example above the version `20220603` corresponds to June 03, 2022.  ### Setting the API version per access token  When creating an access token, you must specify a specific version of the API to use. This ensures that integrations using this token cannot be broken by version changes.  Tokens created before versioning was released have their version set to `20160426` (the version of the API that existed before versioning) so that they continue working the same way they did before versioning.  If you would like to upgrade your integration to use a new API version, you can explicitly set the header described above.  > ### Best practice: Set the header for every client or integration > > We recommend that you set the API version header explicitly in any client or integration you build. > > Only rely on the access token API version during manual testing.  ### API version changelog  |<div style=\"width:75px\">Version</div> | Changes | |---|---| | `20220603` | <ul><li>Changed the [list projects](tag/Projects#operation/getProjects) return value:<ul><li>Response is now paginated with a default limit of `20`.</li><li>Added support for filter and sort.</li><li>The project `environments` field is now expandable. This field is omitted by default.</li></ul></li><li>Changed the [get project](tag/Projects#operation/getProject) return value:<ul><li>The `environments` field is now expandable. This field is omitted by default.</li></ul></li></ul> | | `20210729` | <ul><li>Changed the [create approval request](/tag/Approvals#operation/postApprovalRequest) return value. It now returns HTTP Status Code `201` instead of `200`.</li><li> Changed the [get users](/tag/Users#operation/getUser) return value. It now returns a user record, not a user. </li><li> Added additional optional fields to environment, segments, flags, members, and segments, including the ability to create Big Segments. </li><li> Added default values for flag variations when new environments are created. </li><li> Added filtering and pagination for getting flags and members, including `limit`, `number`, `filter`, and `sort` query parameters. </li><li> Added endpoints for expiring user targets for flags and segments, scheduled changes, access tokens, Relay Proxy configuration, integrations and subscriptions, and approvals. </li></ul> | | `20191212` | <ul><li>[List feature flags](/tag/Feature-flags#operation/getFeatureFlags) now defaults to sending summaries of feature flag configurations, equivalent to setting the query parameter `summary=true`. Summaries omit flag targeting rules and individual user targets from the payload. </li><li> Added endpoints for flags, flag status, projects, environments, users, audit logs, members, users, custom roles, segments, usage, streams, events, and data export. </li></ul> | | `20160426` | <ul><li>Initial versioning of API. Tokens created before versioning have their version set to this.</li></ul> | 
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
     *  Copy flag settings from a source environment to a target environment.  By default, this operation copies the entire flag configuration. You can use the &#x60;includedActions&#x60; or &#x60;excludedActions&#x60; to specify that only part of the flag configuration is copied.  If you provide the optional &#x60;currentVersion&#x60; of a flag, this operation tests to ensure that the current flag version in the environment matches the version you&#39;ve specified. The operation rejects attempts to copy flag settings if the environment&#39;s current version  of the flag does not match the version you&#39;ve specified. You can use this to enforce optimistic locking on copy attempts. 
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
     *  Copy flag settings from a source environment to a target environment.  By default, this operation copies the entire flag configuration. You can use the &#x60;includedActions&#x60; or &#x60;excludedActions&#x60; to specify that only part of the flag configuration is copied.  If you provide the optional &#x60;currentVersion&#x60; of a flag, this operation tests to ensure that the current flag version in the environment matches the version you&#39;ve specified. The operation rejects attempts to copy flag settings if the environment&#39;s current version  of the flag does not match the version you&#39;ve specified. You can use this to enforce optimistic locking on copy attempts. 
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
     *  Copy flag settings from a source environment to a target environment.  By default, this operation copies the entire flag configuration. You can use the &#x60;includedActions&#x60; or &#x60;excludedActions&#x60; to specify that only part of the flag configuration is copied.  If you provide the optional &#x60;currentVersion&#x60; of a flag, this operation tests to ensure that the current flag version in the environment matches the version you&#39;ve specified. The operation rejects attempts to copy flag settings if the environment&#39;s current version  of the flag does not match the version you&#39;ve specified. You can use this to enforce optimistic locking on copy attempts. 
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
        <tr><td> 200 </td><td> User targeting expirations on feature flag response. </td><td>  -  </td></tr>
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
     * Get a list of user targets on a feature flag that are scheduled for removal.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @return ExpiringUserTargetGetResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> User targeting expirations on feature flag response. </td><td>  -  </td></tr>
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
     * Get a list of user targets on a feature flag that are scheduled for removal.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @return ApiResponse&lt;ExpiringUserTargetGetResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> User targeting expirations on feature flag response. </td><td>  -  </td></tr>
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
     * Get a list of user targets on a feature flag that are scheduled for removal.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> User targeting expirations on feature flag response. </td><td>  -  </td></tr>
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
    public okhttp3.Call getFeatureFlagCall(String projectKey, String featureFlagKey, String env, final ApiCallback _callback) throws ApiException {
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
    private okhttp3.Call getFeatureFlagValidateBeforeCall(String projectKey, String featureFlagKey, String env, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getFeatureFlag(Async)");
        }
        
        // verify the required parameter 'featureFlagKey' is set
        if (featureFlagKey == null) {
            throw new ApiException("Missing the required parameter 'featureFlagKey' when calling getFeatureFlag(Async)");
        }
        

        okhttp3.Call localVarCall = getFeatureFlagCall(projectKey, featureFlagKey, env, _callback);
        return localVarCall;

    }

    /**
     * Get feature flag
     * Get a single feature flag by key. By default, this returns the configurations for all environments. You can filter environments with the &#x60;env&#x60; query parameter. For example, setting &#x60;env&#x3D;production&#x60; restricts the returned configurations to just the &#x60;production&#x60; environment.
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param env Filter configurations by environment (optional)
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
    public FeatureFlag getFeatureFlag(String projectKey, String featureFlagKey, String env) throws ApiException {
        ApiResponse<FeatureFlag> localVarResp = getFeatureFlagWithHttpInfo(projectKey, featureFlagKey, env);
        return localVarResp.getData();
    }

    /**
     * Get feature flag
     * Get a single feature flag by key. By default, this returns the configurations for all environments. You can filter environments with the &#x60;env&#x60; query parameter. For example, setting &#x60;env&#x3D;production&#x60; restricts the returned configurations to just the &#x60;production&#x60; environment.
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param env Filter configurations by environment (optional)
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
    public ApiResponse<FeatureFlag> getFeatureFlagWithHttpInfo(String projectKey, String featureFlagKey, String env) throws ApiException {
        okhttp3.Call localVarCall = getFeatureFlagValidateBeforeCall(projectKey, featureFlagKey, env, null);
        Type localVarReturnType = new TypeToken<FeatureFlag>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get feature flag (asynchronously)
     * Get a single feature flag by key. By default, this returns the configurations for all environments. You can filter environments with the &#x60;env&#x60; query parameter. For example, setting &#x60;env&#x3D;production&#x60; restricts the returned configurations to just the &#x60;production&#x60; environment.
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param env Filter configurations by environment (optional)
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
    public okhttp3.Call getFeatureFlagAsync(String projectKey, String featureFlagKey, String env, final ApiCallback<FeatureFlag> _callback) throws ApiException {

        okhttp3.Call localVarCall = getFeatureFlagValidateBeforeCall(projectKey, featureFlagKey, env, _callback);
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
     * Get a list of statuses for all feature flags. The status includes the last time the feature flag was requested, as well as a state, which is one of the following:  - &#x60;new&#x60;: the feature flag was created within the last seven days, and has not been requested yet - &#x60;active&#x60;: the feature flag was requested by your servers or clients within the last seven days - &#x60;inactive&#x60;: the feature flag was created more than seven days ago, and hasn&#39;t been requested by your servers or clients within the past seven days - &#x60;launched&#x60;: one variation of the feature flag has been rolled out to all your users for at least 7 days 
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
     * Get a list of statuses for all feature flags. The status includes the last time the feature flag was requested, as well as a state, which is one of the following:  - &#x60;new&#x60;: the feature flag was created within the last seven days, and has not been requested yet - &#x60;active&#x60;: the feature flag was requested by your servers or clients within the last seven days - &#x60;inactive&#x60;: the feature flag was created more than seven days ago, and hasn&#39;t been requested by your servers or clients within the past seven days - &#x60;launched&#x60;: one variation of the feature flag has been rolled out to all your users for at least 7 days 
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
     * Get a list of statuses for all feature flags. The status includes the last time the feature flag was requested, as well as a state, which is one of the following:  - &#x60;new&#x60;: the feature flag was created within the last seven days, and has not been requested yet - &#x60;active&#x60;: the feature flag was requested by your servers or clients within the last seven days - &#x60;inactive&#x60;: the feature flag was created more than seven days ago, and hasn&#39;t been requested by your servers or clients within the past seven days - &#x60;launched&#x60;: one variation of the feature flag has been rolled out to all your users for at least 7 days 
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
     * @param archived A boolean to filter the list to archived flags. When this is absent, only unarchived flags will be returned (optional)
     * @param summary By default in API version &gt;&#x3D; 1, flags will _not_ include their list of prerequisites, targets or rules.  Set summary&#x3D;0 to include these fields for each flag returned (optional)
     * @param filter A comma-separated list of filters. Each filter is of the form field:value (optional)
     * @param sort A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order (optional)
     * @param compare A boolean to filter results by only flags that have differences between environments (optional)
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
    public okhttp3.Call getFeatureFlagsCall(String projectKey, String env, String tag, Long limit, Long offset, Boolean archived, Boolean summary, String filter, String sort, Boolean compare, final ApiCallback _callback) throws ApiException {
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
    private okhttp3.Call getFeatureFlagsValidateBeforeCall(String projectKey, String env, String tag, Long limit, Long offset, Boolean archived, Boolean summary, String filter, String sort, Boolean compare, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getFeatureFlags(Async)");
        }
        

        okhttp3.Call localVarCall = getFeatureFlagsCall(projectKey, env, tag, limit, offset, archived, summary, filter, sort, compare, _callback);
        return localVarCall;

    }

    /**
     * List feature flags
     * Get a list of all features in the given project. By default, each feature includes configurations for each environment. You can filter environments with the env query parameter. For example, setting &#x60;env&#x3D;production&#x60; restricts the returned configurations to just your production environment. You can also filter feature flags by tag with the tag query parameter.  We support the following fields for filters:  - &#x60;query&#x60; is a string that matches against the flags&#39; keys and names. It is not case sensitive. - &#x60;archived&#x60; is a boolean to filter the list to archived flags. When this is absent, only unarchived flags are returned. - &#x60;type&#x60; is a string allowing filtering to &#x60;temporary&#x60; or &#x60;permanent&#x60; flags. - &#x60;status&#x60; is a string allowing filtering to &#x60;new&#x60;, &#x60;inactive&#x60;, &#x60;active&#x60;, or &#x60;launched&#x60; flags in the specified environment. This filter also requires a &#x60;filterEnv&#x60; field to be set to a valid environment. For example: &#x60;filter&#x3D;status:active,filterEnv:production&#x60;. - &#x60;tags&#x60; is a + separated list of tags. It filters the list to members who have all of the tags in the list. - &#x60;hasExperiment&#x60; is a boolean with values of true or false and returns any flags that have an attached metric. - &#x60;hasDataExport&#x60; is a boolean with values of true or false and returns any flags that are exporting data in the specified environment. This includes flags that are exporting data from Experimentation. This filter also requires that you set a &#x60;filterEnv&#x60; field to a valid environment key. For example: &#x60;filter&#x3D;hasDataExport:true,filterEnv:production&#x60; - &#x60;evaluated&#x60; is an object that contains a key of &#x60;after&#x60; and a value in Unix time in milliseconds. This returns all flags that have been evaluated since the time you specify in the environment provided. This filter also requires you to set a &#x60;filterEnv&#x60; field to a valid environment. For example: &#x60;filter&#x3D;evaluated:{\&quot;after\&quot;: 1590768455282},filterEnv:production&#x60;. - &#x60;filterEnv&#x60; is a string with the key of a valid environment. You can use the filterEnv field for filters that are environment-specific. If there are multiple environment-specific filters, you should only declare this parameter once. For example: &#x60;filter&#x3D;evaluated:{\&quot;after\&quot;: 1590768455282},filterEnv:production,status:active&#x60;.  An example filter is &#x60;query:abc,tags:foo+bar&#x60;. This matches flags with the string &#x60;abc&#x60; in their key or name, ignoring case, which also have the tags &#x60;foo&#x60; and &#x60;bar&#x60;.  By default, this returns all flags. You can page through the list with the &#x60;limit&#x60; parameter and by following the &#x60;first&#x60;, &#x60;prev&#x60;, &#x60;next&#x60;, and &#x60;last&#x60; links in the returned &#x60;_links&#x60; field. These links will not be present if the pages they refer to don&#39;t exist. For example, the &#x60;first&#x60; and &#x60;prev&#x60; links will be missing from the response on the first page. 
     * @param projectKey The project key (required)
     * @param env Filter configurations by environment (optional)
     * @param tag Filter feature flags by tag (optional)
     * @param limit The number of feature flags to return. Defaults to -1, which returns all flags (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param archived A boolean to filter the list to archived flags. When this is absent, only unarchived flags will be returned (optional)
     * @param summary By default in API version &gt;&#x3D; 1, flags will _not_ include their list of prerequisites, targets or rules.  Set summary&#x3D;0 to include these fields for each flag returned (optional)
     * @param filter A comma-separated list of filters. Each filter is of the form field:value (optional)
     * @param sort A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order (optional)
     * @param compare A boolean to filter results by only flags that have differences between environments (optional)
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
    public FeatureFlags getFeatureFlags(String projectKey, String env, String tag, Long limit, Long offset, Boolean archived, Boolean summary, String filter, String sort, Boolean compare) throws ApiException {
        ApiResponse<FeatureFlags> localVarResp = getFeatureFlagsWithHttpInfo(projectKey, env, tag, limit, offset, archived, summary, filter, sort, compare);
        return localVarResp.getData();
    }

    /**
     * List feature flags
     * Get a list of all features in the given project. By default, each feature includes configurations for each environment. You can filter environments with the env query parameter. For example, setting &#x60;env&#x3D;production&#x60; restricts the returned configurations to just your production environment. You can also filter feature flags by tag with the tag query parameter.  We support the following fields for filters:  - &#x60;query&#x60; is a string that matches against the flags&#39; keys and names. It is not case sensitive. - &#x60;archived&#x60; is a boolean to filter the list to archived flags. When this is absent, only unarchived flags are returned. - &#x60;type&#x60; is a string allowing filtering to &#x60;temporary&#x60; or &#x60;permanent&#x60; flags. - &#x60;status&#x60; is a string allowing filtering to &#x60;new&#x60;, &#x60;inactive&#x60;, &#x60;active&#x60;, or &#x60;launched&#x60; flags in the specified environment. This filter also requires a &#x60;filterEnv&#x60; field to be set to a valid environment. For example: &#x60;filter&#x3D;status:active,filterEnv:production&#x60;. - &#x60;tags&#x60; is a + separated list of tags. It filters the list to members who have all of the tags in the list. - &#x60;hasExperiment&#x60; is a boolean with values of true or false and returns any flags that have an attached metric. - &#x60;hasDataExport&#x60; is a boolean with values of true or false and returns any flags that are exporting data in the specified environment. This includes flags that are exporting data from Experimentation. This filter also requires that you set a &#x60;filterEnv&#x60; field to a valid environment key. For example: &#x60;filter&#x3D;hasDataExport:true,filterEnv:production&#x60; - &#x60;evaluated&#x60; is an object that contains a key of &#x60;after&#x60; and a value in Unix time in milliseconds. This returns all flags that have been evaluated since the time you specify in the environment provided. This filter also requires you to set a &#x60;filterEnv&#x60; field to a valid environment. For example: &#x60;filter&#x3D;evaluated:{\&quot;after\&quot;: 1590768455282},filterEnv:production&#x60;. - &#x60;filterEnv&#x60; is a string with the key of a valid environment. You can use the filterEnv field for filters that are environment-specific. If there are multiple environment-specific filters, you should only declare this parameter once. For example: &#x60;filter&#x3D;evaluated:{\&quot;after\&quot;: 1590768455282},filterEnv:production,status:active&#x60;.  An example filter is &#x60;query:abc,tags:foo+bar&#x60;. This matches flags with the string &#x60;abc&#x60; in their key or name, ignoring case, which also have the tags &#x60;foo&#x60; and &#x60;bar&#x60;.  By default, this returns all flags. You can page through the list with the &#x60;limit&#x60; parameter and by following the &#x60;first&#x60;, &#x60;prev&#x60;, &#x60;next&#x60;, and &#x60;last&#x60; links in the returned &#x60;_links&#x60; field. These links will not be present if the pages they refer to don&#39;t exist. For example, the &#x60;first&#x60; and &#x60;prev&#x60; links will be missing from the response on the first page. 
     * @param projectKey The project key (required)
     * @param env Filter configurations by environment (optional)
     * @param tag Filter feature flags by tag (optional)
     * @param limit The number of feature flags to return. Defaults to -1, which returns all flags (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param archived A boolean to filter the list to archived flags. When this is absent, only unarchived flags will be returned (optional)
     * @param summary By default in API version &gt;&#x3D; 1, flags will _not_ include their list of prerequisites, targets or rules.  Set summary&#x3D;0 to include these fields for each flag returned (optional)
     * @param filter A comma-separated list of filters. Each filter is of the form field:value (optional)
     * @param sort A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order (optional)
     * @param compare A boolean to filter results by only flags that have differences between environments (optional)
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
    public ApiResponse<FeatureFlags> getFeatureFlagsWithHttpInfo(String projectKey, String env, String tag, Long limit, Long offset, Boolean archived, Boolean summary, String filter, String sort, Boolean compare) throws ApiException {
        okhttp3.Call localVarCall = getFeatureFlagsValidateBeforeCall(projectKey, env, tag, limit, offset, archived, summary, filter, sort, compare, null);
        Type localVarReturnType = new TypeToken<FeatureFlags>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List feature flags (asynchronously)
     * Get a list of all features in the given project. By default, each feature includes configurations for each environment. You can filter environments with the env query parameter. For example, setting &#x60;env&#x3D;production&#x60; restricts the returned configurations to just your production environment. You can also filter feature flags by tag with the tag query parameter.  We support the following fields for filters:  - &#x60;query&#x60; is a string that matches against the flags&#39; keys and names. It is not case sensitive. - &#x60;archived&#x60; is a boolean to filter the list to archived flags. When this is absent, only unarchived flags are returned. - &#x60;type&#x60; is a string allowing filtering to &#x60;temporary&#x60; or &#x60;permanent&#x60; flags. - &#x60;status&#x60; is a string allowing filtering to &#x60;new&#x60;, &#x60;inactive&#x60;, &#x60;active&#x60;, or &#x60;launched&#x60; flags in the specified environment. This filter also requires a &#x60;filterEnv&#x60; field to be set to a valid environment. For example: &#x60;filter&#x3D;status:active,filterEnv:production&#x60;. - &#x60;tags&#x60; is a + separated list of tags. It filters the list to members who have all of the tags in the list. - &#x60;hasExperiment&#x60; is a boolean with values of true or false and returns any flags that have an attached metric. - &#x60;hasDataExport&#x60; is a boolean with values of true or false and returns any flags that are exporting data in the specified environment. This includes flags that are exporting data from Experimentation. This filter also requires that you set a &#x60;filterEnv&#x60; field to a valid environment key. For example: &#x60;filter&#x3D;hasDataExport:true,filterEnv:production&#x60; - &#x60;evaluated&#x60; is an object that contains a key of &#x60;after&#x60; and a value in Unix time in milliseconds. This returns all flags that have been evaluated since the time you specify in the environment provided. This filter also requires you to set a &#x60;filterEnv&#x60; field to a valid environment. For example: &#x60;filter&#x3D;evaluated:{\&quot;after\&quot;: 1590768455282},filterEnv:production&#x60;. - &#x60;filterEnv&#x60; is a string with the key of a valid environment. You can use the filterEnv field for filters that are environment-specific. If there are multiple environment-specific filters, you should only declare this parameter once. For example: &#x60;filter&#x3D;evaluated:{\&quot;after\&quot;: 1590768455282},filterEnv:production,status:active&#x60;.  An example filter is &#x60;query:abc,tags:foo+bar&#x60;. This matches flags with the string &#x60;abc&#x60; in their key or name, ignoring case, which also have the tags &#x60;foo&#x60; and &#x60;bar&#x60;.  By default, this returns all flags. You can page through the list with the &#x60;limit&#x60; parameter and by following the &#x60;first&#x60;, &#x60;prev&#x60;, &#x60;next&#x60;, and &#x60;last&#x60; links in the returned &#x60;_links&#x60; field. These links will not be present if the pages they refer to don&#39;t exist. For example, the &#x60;first&#x60; and &#x60;prev&#x60; links will be missing from the response on the first page. 
     * @param projectKey The project key (required)
     * @param env Filter configurations by environment (optional)
     * @param tag Filter feature flags by tag (optional)
     * @param limit The number of feature flags to return. Defaults to -1, which returns all flags (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param archived A boolean to filter the list to archived flags. When this is absent, only unarchived flags will be returned (optional)
     * @param summary By default in API version &gt;&#x3D; 1, flags will _not_ include their list of prerequisites, targets or rules.  Set summary&#x3D;0 to include these fields for each flag returned (optional)
     * @param filter A comma-separated list of filters. Each filter is of the form field:value (optional)
     * @param sort A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order (optional)
     * @param compare A boolean to filter results by only flags that have differences between environments (optional)
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
    public okhttp3.Call getFeatureFlagsAsync(String projectKey, String env, String tag, Long limit, Long offset, Boolean archived, Boolean summary, String filter, String sort, Boolean compare, final ApiCallback<FeatureFlags> _callback) throws ApiException {

        okhttp3.Call localVarCall = getFeatureFlagsValidateBeforeCall(projectKey, env, tag, limit, offset, archived, summary, filter, sort, compare, _callback);
        Type localVarReturnType = new TypeToken<FeatureFlags>(){}.getType();
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
        <tr><td> 200 </td><td> User targeting expirations on feature flag response. </td><td>  -  </td></tr>
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
     * Schedule a user for removal from individual targeting on a feature flag. The flag must already individually target the user.  You can add, update, or remove a scheduled removal date. You can only schedule a user for removal on a single variation per flag.  This request only supports semantic patches. To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  ### Instructions  #### addExpireUserTargetDate  Adds a date and time that LaunchDarkly will remove the user from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the user from individual targeting for this flag * &#x60;variationId&#x60;: The version of the flag variation to update. You can retrieve this by making a GET request for the flag. * &#x60;userKey&#x60;: The user key for the user to remove from individual targeting  #### updateExpireUserTargetDate  Updates the date and time that LaunchDarkly will remove the user from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the user from individual targeting for this flag * &#x60;variationId&#x60;: The version of the flag variation to update. You can retrieve this by making a GET request for the flag. * &#x60;userKey&#x60;: The user key for the user to remove from individual targeting  #### removeExpireUserTargetDate  Removes the scheduled removal of the user from the flag&#39;s individual targeting. The user will remain part of the flag&#39;s individual targeting until you explicitly remove them, or until you schedule another removal.  ##### Parameters  * &#x60;variationId&#x60;: The version of the flag variation to update. You can retrieve this by making a GET request for the flag. * &#x60;userKey&#x60;: The user key for the user to remove from individual targeting 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param patchFlagsRequest  (required)
     * @return ExpiringUserTargetPatchResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> User targeting expirations on feature flag response. </td><td>  -  </td></tr>
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
     * Schedule a user for removal from individual targeting on a feature flag. The flag must already individually target the user.  You can add, update, or remove a scheduled removal date. You can only schedule a user for removal on a single variation per flag.  This request only supports semantic patches. To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  ### Instructions  #### addExpireUserTargetDate  Adds a date and time that LaunchDarkly will remove the user from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the user from individual targeting for this flag * &#x60;variationId&#x60;: The version of the flag variation to update. You can retrieve this by making a GET request for the flag. * &#x60;userKey&#x60;: The user key for the user to remove from individual targeting  #### updateExpireUserTargetDate  Updates the date and time that LaunchDarkly will remove the user from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the user from individual targeting for this flag * &#x60;variationId&#x60;: The version of the flag variation to update. You can retrieve this by making a GET request for the flag. * &#x60;userKey&#x60;: The user key for the user to remove from individual targeting  #### removeExpireUserTargetDate  Removes the scheduled removal of the user from the flag&#39;s individual targeting. The user will remain part of the flag&#39;s individual targeting until you explicitly remove them, or until you schedule another removal.  ##### Parameters  * &#x60;variationId&#x60;: The version of the flag variation to update. You can retrieve this by making a GET request for the flag. * &#x60;userKey&#x60;: The user key for the user to remove from individual targeting 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param patchFlagsRequest  (required)
     * @return ApiResponse&lt;ExpiringUserTargetPatchResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> User targeting expirations on feature flag response. </td><td>  -  </td></tr>
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
     * Schedule a user for removal from individual targeting on a feature flag. The flag must already individually target the user.  You can add, update, or remove a scheduled removal date. You can only schedule a user for removal on a single variation per flag.  This request only supports semantic patches. To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  ### Instructions  #### addExpireUserTargetDate  Adds a date and time that LaunchDarkly will remove the user from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the user from individual targeting for this flag * &#x60;variationId&#x60;: The version of the flag variation to update. You can retrieve this by making a GET request for the flag. * &#x60;userKey&#x60;: The user key for the user to remove from individual targeting  #### updateExpireUserTargetDate  Updates the date and time that LaunchDarkly will remove the user from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the user from individual targeting for this flag * &#x60;variationId&#x60;: The version of the flag variation to update. You can retrieve this by making a GET request for the flag. * &#x60;userKey&#x60;: The user key for the user to remove from individual targeting  #### removeExpireUserTargetDate  Removes the scheduled removal of the user from the flag&#39;s individual targeting. The user will remain part of the flag&#39;s individual targeting until you explicitly remove them, or until you schedule another removal.  ##### Parameters  * &#x60;variationId&#x60;: The version of the flag variation to update. You can retrieve this by making a GET request for the flag. * &#x60;userKey&#x60;: The user key for the user to remove from individual targeting 
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
        <tr><td> 200 </td><td> User targeting expirations on feature flag response. </td><td>  -  </td></tr>
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
     * Perform a partial update to a feature flag. The request body must be a valid semantic patch or JSON patch.  ## Using semantic patches on a feature flag  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  The body of a semantic patch request for updating feature flags requires an &#x60;environmentKey&#x60; in addition to &#x60;instructions&#x60; and an optional &#x60;comment&#x60;. The body of the request takes the following properties:  * &#x60;comment&#x60; (string): (Optional) A description of the update. * &#x60;environmentKey&#x60; (string): (Required) The key of the LaunchDarkly environment. * &#x60;instructions&#x60; (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a &#x60;kind&#x60; property that indicates the instruction. If the action requires parameters, you must include those parameters as additional fields in the object.  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating feature flags.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for turning flags on and off&lt;/summary&gt;  #### turnFlagOff  Sets the flag&#39;s targeting state to **Off**.  For example, to turn a flag off, use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;example-environment-key\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnFlagOff\&quot; } ] } &#x60;&#x60;&#x60;  #### turnFlagOn  Sets the flag&#39;s targeting state to **On**.  For example, to turn a flag on, use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;example-environment-key\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnFlagOn\&quot; } ] } &#x60;&#x60;&#x60;  &lt;/details&gt;&lt;br /&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for working with targeting and variations&lt;/summary&gt;  #### addClauses  Adds the given clauses to the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauses&#x60;: Array of clause objects, with &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), and &#x60;values&#x60; (array of strings, numbers, or dates) properties.  #### addPrerequisite  Adds the flag indicated by &#x60;key&#x60; with variation &#x60;variationId&#x60; as a prerequisite to the flag in the path parameter.  ##### Parameters  - &#x60;key&#x60;: Flag key of the prerequisite flag. - &#x60;variationId&#x60;: ID of a variation of the prerequisite flag.  #### addRule  Adds a new targeting rule to the flag. The rule may contain &#x60;clauses&#x60; and serve the variation that &#x60;variationId&#x60; indicates, or serve a percentage rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  If you set &#x60;beforeRuleId&#x60;, this adds the new rule before the indicated rule. Otherwise, adds the new rule to the end of the list.  ##### Parameters  - &#x60;clauses&#x60;: Array of clause objects, with &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. - &#x60;beforeRuleId&#x60;: (Optional) ID of a flag rule. - &#x60;variationId&#x60;: ID of a variation of the flag. - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) User attribute.  #### addUserTargets  Adds user keys to the individual user targets for the variation that &#x60;variationId&#x60; specifies. Returns an error if this causes the flag to target the same user key in multiple variations.  ##### Parameters  - &#x60;values&#x60;: List of user keys. - &#x60;variationId&#x60;: ID of a variation on the flag.  #### addValuesToClause  Adds &#x60;values&#x60; to the values of the clause that &#x60;ruleId&#x60; and &#x60;clauseId&#x60; indicate.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings.  #### clearUserTargets  Removes all individual user targets from the variation that &#x60;variationId&#x60; specifies.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation on the flag.  #### removeClauses  Removes the clauses specified by &#x60;clauseIds&#x60; from the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseIds&#x60;: Array of IDs of clauses in the rule.  #### removePrerequisite  Removes the prerequisite flag indicated by &#x60;key&#x60;. Does nothing if this prerequisite does not exist.  ##### Parameters  - &#x60;key&#x60;: Flag key of an existing prerequisite flag.  #### removeRule  Removes the targeting rule specified by &#x60;ruleId&#x60;. Does nothing if the rule does not exist.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag.  #### removeUserTargets  Removes user keys from the individual user targets for the variation that &#x60;variationId&#x60; specifies. Does nothing if the flag does not target the user keys.  ##### Parameters  - &#x60;values&#x60;: List of user keys. - &#x60;variationId&#x60;: ID of a flag variation.  #### removeValuesFromClause  Removes &#x60;values&#x60; from the values of the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings.  #### reorderRules  Rearranges the rules to match the order given in &#x60;ruleIds&#x60;. Returns an error if &#x60;ruleIds&#x60; does not match the current set of rules on the flag.  ##### Parameters  - &#x60;ruleIds&#x60;: Array of IDs of all rules in the flag.  #### replacePrerequisites  Removes all existing prerequisites and replaces them with the list you provide.  ##### Parameters  - &#x60;prerequisites&#x60;: A list of prerequisites. Each item in the list must include a flag &#x60;key&#x60; and &#x60;variationId&#x60;.  For example, to replace prerequisites, use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;example-environment-key\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replacePrerequisites\&quot;,       \&quot;prerequisites\&quot;: [         {           \&quot;key\&quot;: \&quot;prereq-flag-key\&quot;,           \&quot;variationId\&quot;: \&quot;variation-1\&quot;         },         {           \&quot;key\&quot;: \&quot;another-prereq-flag-key\&quot;,           \&quot;variationId\&quot;: \&quot;variation-2\&quot;         }       ]     }   ] } &#x60;&#x60;&#x60;  #### replaceRules  Removes all targeting rules for the flag and replaces them with the list you provide.  ##### Parameters  - &#x60;rules&#x60;: A list of rules.  For example, to replace rules, use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;example-environment-key\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replaceRules\&quot;,       \&quot;rules\&quot;: [         {           \&quot;variationId\&quot;: \&quot;variation-1\&quot;,           \&quot;description\&quot;: \&quot;myRule\&quot;,           \&quot;clauses\&quot;: [             {               \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,               \&quot;op\&quot;: \&quot;segmentMatch\&quot;,               \&quot;values\&quot;: [\&quot;test\&quot;]             }           ],           \&quot;trackEvents\&quot;: true         }       ]     }   ] } &#x60;&#x60;&#x60;  #### replaceUserTargets  Removes all existing user targeting and replaces it with the list of targets you provide.  ##### Parameters  - &#x60;targets&#x60;: A list of user targeting. Each item in the list must include a &#x60;variationId&#x60; and a list of &#x60;values&#x60;.  For example, to replace user targets, use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;example-environment-key\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replaceUserTargets\&quot;,       \&quot;targets\&quot;: [         {           \&quot;variationId\&quot;: \&quot;variation-1\&quot;,           \&quot;values\&quot;: [\&quot;blah\&quot;, \&quot;foo\&quot;, \&quot;bar\&quot;]         },         {           \&quot;variationId\&quot;: \&quot;variation-2\&quot;,           \&quot;values\&quot;: [\&quot;abc\&quot;, \&quot;def\&quot;]         }       ]     }       ] } &#x60;&#x60;&#x60;  #### updateClause  Replaces the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60; with &#x60;clause&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;clause&#x60;: New &#x60;clause&#x60; object, with &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), and &#x60;values&#x60; (array of strings, numbers, or dates) properties.  #### updateFallthroughVariationOrRollout  Updates the default or \&quot;fallthrough\&quot; rule for the flag, which the flag serves when a user matches none of the targeting rules. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percent rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation of the flag. or - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: Optional user attribute.  #### updateOffVariation  Updates the default off variation to &#x60;variationId&#x60;. The flag serves the default off variation when the flag&#39;s targeting is **Off**.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation of the flag.  #### updatePrerequisite  Changes the prerequisite flag that &#x60;key&#x60; indicates to use the variation that &#x60;variationId&#x60; indicates. Returns an error if this prerequisite does not exist.  ##### Parameters  - &#x60;key&#x60;: Flag key of an existing prerequisite flag. - &#x60;variationId&#x60;: ID of a variation of the prerequisite flag.  #### updateRuleDescription  Updates the description of the feature flag rule.  ##### Parameters  - &#x60;description&#x60;: The new human-readable description for this rule. - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the flag.  #### updateRuleTrackEvents  Updates whether or not LaunchDarkly tracks events for the feature flag associated with this rule.  ##### Parameters  - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the flag. - &#x60;trackEvents&#x60;: Whether or not events are tracked.  #### updateRuleVariationOrRollout  Updates what &#x60;ruleId&#x60; serves when its clauses evaluate to true. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percent rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;variationId&#x60;: ID of a variation of the flag.    or  - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: Optional user attribute.  #### updateTrackEvents  Updates whether or not LaunchDarkly tracks events for the feature flag, for all rules.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  #### updateTrackEventsFallthrough  Updates whether or not LaunchDarkly tracks events for the feature flag, for the default rule.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  &lt;/details&gt;&lt;br /&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for updating flag settings&lt;/summary&gt;  #### addTags  Adds tags to the feature flag.  ##### Parameters  - &#x60;values&#x60;: A list of tags to add.  #### makeFlagPermanent  Marks the feature flag as permanent. LaunchDarkly does not prompt you to remove permanent flags, even if one variation is rolled out to all your users.  #### makeFlagTemporary  Marks the feature flag as temporary.  #### removeMaintainer  Removes the flag&#39;s maintainer. To set a new maintainer, use the flag&#39;s **Settings** tab in the LaunchDarkly user interface.  #### removeTags  Removes tags from the feature flag.  ##### Parameters  - &#x60;values&#x60;: A list of tags to remove.  #### turnOffClientSideAvailability  Turns off client-side SDK availability for the flag. This is equivalent to unchecking the **SDKs using Mobile Key** and/or **SDKs using client-side ID** boxes for the flag. If you&#39;re using a client-side or mobile SDK, you must expose your feature flags in order for the client-side or mobile SDKs to evaluate them.  ##### Parameters  - &#x60;value&#x60;: Use \&quot;usingMobileKey\&quot; to turn on availability for mobile SDKs. Use \&quot;usingEnvironmentId\&quot; to turn on availability for client-side SDKs.  #### turnOnClientSideAvailability  Turns on client-side SDK availability for the flag. This is equivalent to unchecking the **SDKs using Mobile Key** and/or **SDKs using client-side ID** boxes for the flag. If you&#39;re using a client-side or mobile SDK, you must expose your feature flags in order for the client-side or mobile SDKs to evaluate them.  ##### Parameters  - &#x60;value&#x60;: Use \&quot;usingMobileKey\&quot; to turn on availability for mobile SDKs. Use \&quot;usingEnvironmentId\&quot; to turn on availability for client-side SDKs.  #### updateDescription  Updates the feature flag description.  ##### Parameters  - &#x60;value&#x60;: The new description.  #### updateName  Updates the feature flag name.  ##### Parameters  - &#x60;value&#x60;: The new name.  &lt;/details&gt;&lt;br /&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for updating the flag lifecycle&lt;/summary&gt;  #### archiveFlag  Archives the feature flag. This retires it from LaunchDarkly without deleting it. You cannot archive a flag that is a prerequisite of other flags.  #### deleteFlag  Deletes the feature flag and its rules. You cannot restore a deleted flag. If this flag is requested again, the flag value defined in code will be returned for all users.  #### restoreFlag  Restores the feature flag if it was previously archived.  &lt;/details&gt;  ### Example  The body of a single semantic patch can contain many different instructions.  &lt;details&gt; &lt;summary&gt;Click to expand example semantic patch request body&lt;/summary&gt;  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;production\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;turnFlagOn\&quot;     },     {       \&quot;kind\&quot;: \&quot;turnFlagOff\&quot;     },     {       \&quot;kind\&quot;: \&quot;addUserTargets\&quot;,       \&quot;variationId\&quot;: \&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;,       \&quot;values\&quot;: [\&quot;userId\&quot;, \&quot;userId2\&quot;]     },     {       \&quot;kind\&quot;: \&quot;removeUserTargets\&quot;,       \&quot;variationId\&quot;: \&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;,       \&quot;values\&quot;: [\&quot;userId3\&quot;, \&quot;userId4\&quot;]     },     {       \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,       \&quot;rolloutWeights\&quot;: {         \&quot;variationId\&quot;: 50000,         \&quot;variationId2\&quot;: 50000       },       \&quot;rolloutBucketBy\&quot;: null     },     {       \&quot;kind\&quot;: \&quot;addRule\&quot;,       \&quot;clauses\&quot;: [         {           \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,           \&quot;negate\&quot;: false,           \&quot;values\&quot;: [\&quot;test-segment\&quot;]         }       ],       \&quot;variationId\&quot;: null,       \&quot;rolloutWeights\&quot;: {         \&quot;variationId\&quot;: 50000,         \&quot;variationId2\&quot;: 50000       },       \&quot;rolloutBucketBy\&quot;: \&quot;key\&quot;     },     {       \&quot;kind\&quot;: \&quot;removeRule\&quot;,       \&quot;ruleId\&quot;: \&quot;99f12464-a429-40fc-86cc-b27612188955\&quot;     },     {       \&quot;kind\&quot;: \&quot;reorderRules\&quot;,       \&quot;ruleIds\&quot;: [\&quot;2f72974e-de68-4243-8dd3-739582147a1f\&quot;, \&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;]     },     {       \&quot;kind\&quot;: \&quot;addClauses\&quot;,       \&quot;ruleId\&quot;: \&quot;1134\&quot;,       \&quot;clauses\&quot;: [         {           \&quot;attribute\&quot;: \&quot;email\&quot;,           \&quot;op\&quot;: \&quot;in\&quot;,           \&quot;negate\&quot;: false,           \&quot;values\&quot;: [\&quot;test@test.com\&quot;]         }       ]     },     {       \&quot;kind\&quot;: \&quot;removeClauses\&quot;,       \&quot;ruleId\&quot;: \&quot;1242529\&quot;,       \&quot;clauseIds\&quot;: [\&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;]     },     {       \&quot;kind\&quot;: \&quot;updateClause\&quot;,       \&quot;ruleId\&quot;: \&quot;2f72974e-de68-4243-8dd3-739582147a1f\&quot;,       \&quot;clauseId\&quot;: \&quot;309845\&quot;,       \&quot;clause\&quot;: {         \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,         \&quot;negate\&quot;: false,         \&quot;values\&quot;: [\&quot;test-segment\&quot;]       }     },     {       \&quot;kind\&quot;: \&quot;updateRuleVariationOrRollout\&quot;,       \&quot;ruleId\&quot;: \&quot;2342\&quot;,       \&quot;rolloutWeights\&quot;: null,       \&quot;rolloutBucketBy\&quot;: null     },     {       \&quot;kind\&quot;: \&quot;updateOffVariation\&quot;,       \&quot;variationId\&quot;: \&quot;3242453\&quot;     },     {       \&quot;kind\&quot;: \&quot;addPrerequisite\&quot;,       \&quot;variationId\&quot;: \&quot;234235\&quot;,       \&quot;key\&quot;: \&quot;flagKey2\&quot;     },     {       \&quot;kind\&quot;: \&quot;updatePrerequisite\&quot;,       \&quot;variationId\&quot;: \&quot;234235\&quot;,       \&quot;key\&quot;: \&quot;flagKey2\&quot;     },     {       \&quot;kind\&quot;: \&quot;removePrerequisite\&quot;,       \&quot;key\&quot;: \&quot;flagKey\&quot;     }   ] } &#x60;&#x60;&#x60; &lt;/details&gt;  ## Using JSON Patches on a feature flag If you do not include the header described above, you can use [JSON patch](/reference#updates-using-json-patch).  When using the update feature flag endpoint to add individual users to a specific variation, there are two different patch documents, depending on whether users are already being individually targeted for the variation.  If a flag variation already has users individually targeted, the path for the JSON Patch operation is:  &#x60;&#x60;&#x60;json {   \&quot;op\&quot;: \&quot;add\&quot;,   \&quot;path\&quot;: \&quot;/environments/devint/targets/0/values/-\&quot;,   \&quot;value\&quot;: \&quot;TestClient10\&quot; } &#x60;&#x60;&#x60;  If a flag variation does not already have users individually targeted, the path for the JSON Patch operation is:  &#x60;&#x60;&#x60;json [   {     \&quot;op\&quot;: \&quot;add\&quot;,     \&quot;path\&quot;: \&quot;/environments/devint/targets/-\&quot;,     \&quot;value\&quot;: { \&quot;variation\&quot;: 0, \&quot;values\&quot;: [\&quot;TestClient10\&quot;] }   } ] &#x60;&#x60;&#x60;   ## Required approvals If a request attempts to alter a flag configuration in an environment where approvals are required for the flag, the request will fail with a 405. Changes to the flag configuration in that environment will require creating an [approval request](/tag/Approvals) or a [workflow](/tag/Workflows-(beta)).  ## Conflicts If a flag configuration change made through this endpoint would cause a pending scheduled change or approval request to fail, this endpoint will return a 400. You can ignore this check by adding an &#x60;ignoreConflicts&#x60; query parameter set to &#x60;true&#x60;. 
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
     * Perform a partial update to a feature flag. The request body must be a valid semantic patch or JSON patch.  ## Using semantic patches on a feature flag  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  The body of a semantic patch request for updating feature flags requires an &#x60;environmentKey&#x60; in addition to &#x60;instructions&#x60; and an optional &#x60;comment&#x60;. The body of the request takes the following properties:  * &#x60;comment&#x60; (string): (Optional) A description of the update. * &#x60;environmentKey&#x60; (string): (Required) The key of the LaunchDarkly environment. * &#x60;instructions&#x60; (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a &#x60;kind&#x60; property that indicates the instruction. If the action requires parameters, you must include those parameters as additional fields in the object.  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating feature flags.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for turning flags on and off&lt;/summary&gt;  #### turnFlagOff  Sets the flag&#39;s targeting state to **Off**.  For example, to turn a flag off, use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;example-environment-key\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnFlagOff\&quot; } ] } &#x60;&#x60;&#x60;  #### turnFlagOn  Sets the flag&#39;s targeting state to **On**.  For example, to turn a flag on, use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;example-environment-key\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnFlagOn\&quot; } ] } &#x60;&#x60;&#x60;  &lt;/details&gt;&lt;br /&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for working with targeting and variations&lt;/summary&gt;  #### addClauses  Adds the given clauses to the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauses&#x60;: Array of clause objects, with &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), and &#x60;values&#x60; (array of strings, numbers, or dates) properties.  #### addPrerequisite  Adds the flag indicated by &#x60;key&#x60; with variation &#x60;variationId&#x60; as a prerequisite to the flag in the path parameter.  ##### Parameters  - &#x60;key&#x60;: Flag key of the prerequisite flag. - &#x60;variationId&#x60;: ID of a variation of the prerequisite flag.  #### addRule  Adds a new targeting rule to the flag. The rule may contain &#x60;clauses&#x60; and serve the variation that &#x60;variationId&#x60; indicates, or serve a percentage rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  If you set &#x60;beforeRuleId&#x60;, this adds the new rule before the indicated rule. Otherwise, adds the new rule to the end of the list.  ##### Parameters  - &#x60;clauses&#x60;: Array of clause objects, with &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. - &#x60;beforeRuleId&#x60;: (Optional) ID of a flag rule. - &#x60;variationId&#x60;: ID of a variation of the flag. - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) User attribute.  #### addUserTargets  Adds user keys to the individual user targets for the variation that &#x60;variationId&#x60; specifies. Returns an error if this causes the flag to target the same user key in multiple variations.  ##### Parameters  - &#x60;values&#x60;: List of user keys. - &#x60;variationId&#x60;: ID of a variation on the flag.  #### addValuesToClause  Adds &#x60;values&#x60; to the values of the clause that &#x60;ruleId&#x60; and &#x60;clauseId&#x60; indicate.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings.  #### clearUserTargets  Removes all individual user targets from the variation that &#x60;variationId&#x60; specifies.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation on the flag.  #### removeClauses  Removes the clauses specified by &#x60;clauseIds&#x60; from the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseIds&#x60;: Array of IDs of clauses in the rule.  #### removePrerequisite  Removes the prerequisite flag indicated by &#x60;key&#x60;. Does nothing if this prerequisite does not exist.  ##### Parameters  - &#x60;key&#x60;: Flag key of an existing prerequisite flag.  #### removeRule  Removes the targeting rule specified by &#x60;ruleId&#x60;. Does nothing if the rule does not exist.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag.  #### removeUserTargets  Removes user keys from the individual user targets for the variation that &#x60;variationId&#x60; specifies. Does nothing if the flag does not target the user keys.  ##### Parameters  - &#x60;values&#x60;: List of user keys. - &#x60;variationId&#x60;: ID of a flag variation.  #### removeValuesFromClause  Removes &#x60;values&#x60; from the values of the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings.  #### reorderRules  Rearranges the rules to match the order given in &#x60;ruleIds&#x60;. Returns an error if &#x60;ruleIds&#x60; does not match the current set of rules on the flag.  ##### Parameters  - &#x60;ruleIds&#x60;: Array of IDs of all rules in the flag.  #### replacePrerequisites  Removes all existing prerequisites and replaces them with the list you provide.  ##### Parameters  - &#x60;prerequisites&#x60;: A list of prerequisites. Each item in the list must include a flag &#x60;key&#x60; and &#x60;variationId&#x60;.  For example, to replace prerequisites, use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;example-environment-key\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replacePrerequisites\&quot;,       \&quot;prerequisites\&quot;: [         {           \&quot;key\&quot;: \&quot;prereq-flag-key\&quot;,           \&quot;variationId\&quot;: \&quot;variation-1\&quot;         },         {           \&quot;key\&quot;: \&quot;another-prereq-flag-key\&quot;,           \&quot;variationId\&quot;: \&quot;variation-2\&quot;         }       ]     }   ] } &#x60;&#x60;&#x60;  #### replaceRules  Removes all targeting rules for the flag and replaces them with the list you provide.  ##### Parameters  - &#x60;rules&#x60;: A list of rules.  For example, to replace rules, use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;example-environment-key\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replaceRules\&quot;,       \&quot;rules\&quot;: [         {           \&quot;variationId\&quot;: \&quot;variation-1\&quot;,           \&quot;description\&quot;: \&quot;myRule\&quot;,           \&quot;clauses\&quot;: [             {               \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,               \&quot;op\&quot;: \&quot;segmentMatch\&quot;,               \&quot;values\&quot;: [\&quot;test\&quot;]             }           ],           \&quot;trackEvents\&quot;: true         }       ]     }   ] } &#x60;&#x60;&#x60;  #### replaceUserTargets  Removes all existing user targeting and replaces it with the list of targets you provide.  ##### Parameters  - &#x60;targets&#x60;: A list of user targeting. Each item in the list must include a &#x60;variationId&#x60; and a list of &#x60;values&#x60;.  For example, to replace user targets, use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;example-environment-key\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replaceUserTargets\&quot;,       \&quot;targets\&quot;: [         {           \&quot;variationId\&quot;: \&quot;variation-1\&quot;,           \&quot;values\&quot;: [\&quot;blah\&quot;, \&quot;foo\&quot;, \&quot;bar\&quot;]         },         {           \&quot;variationId\&quot;: \&quot;variation-2\&quot;,           \&quot;values\&quot;: [\&quot;abc\&quot;, \&quot;def\&quot;]         }       ]     }       ] } &#x60;&#x60;&#x60;  #### updateClause  Replaces the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60; with &#x60;clause&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;clause&#x60;: New &#x60;clause&#x60; object, with &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), and &#x60;values&#x60; (array of strings, numbers, or dates) properties.  #### updateFallthroughVariationOrRollout  Updates the default or \&quot;fallthrough\&quot; rule for the flag, which the flag serves when a user matches none of the targeting rules. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percent rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation of the flag. or - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: Optional user attribute.  #### updateOffVariation  Updates the default off variation to &#x60;variationId&#x60;. The flag serves the default off variation when the flag&#39;s targeting is **Off**.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation of the flag.  #### updatePrerequisite  Changes the prerequisite flag that &#x60;key&#x60; indicates to use the variation that &#x60;variationId&#x60; indicates. Returns an error if this prerequisite does not exist.  ##### Parameters  - &#x60;key&#x60;: Flag key of an existing prerequisite flag. - &#x60;variationId&#x60;: ID of a variation of the prerequisite flag.  #### updateRuleDescription  Updates the description of the feature flag rule.  ##### Parameters  - &#x60;description&#x60;: The new human-readable description for this rule. - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the flag.  #### updateRuleTrackEvents  Updates whether or not LaunchDarkly tracks events for the feature flag associated with this rule.  ##### Parameters  - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the flag. - &#x60;trackEvents&#x60;: Whether or not events are tracked.  #### updateRuleVariationOrRollout  Updates what &#x60;ruleId&#x60; serves when its clauses evaluate to true. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percent rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;variationId&#x60;: ID of a variation of the flag.    or  - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: Optional user attribute.  #### updateTrackEvents  Updates whether or not LaunchDarkly tracks events for the feature flag, for all rules.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  #### updateTrackEventsFallthrough  Updates whether or not LaunchDarkly tracks events for the feature flag, for the default rule.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  &lt;/details&gt;&lt;br /&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for updating flag settings&lt;/summary&gt;  #### addTags  Adds tags to the feature flag.  ##### Parameters  - &#x60;values&#x60;: A list of tags to add.  #### makeFlagPermanent  Marks the feature flag as permanent. LaunchDarkly does not prompt you to remove permanent flags, even if one variation is rolled out to all your users.  #### makeFlagTemporary  Marks the feature flag as temporary.  #### removeMaintainer  Removes the flag&#39;s maintainer. To set a new maintainer, use the flag&#39;s **Settings** tab in the LaunchDarkly user interface.  #### removeTags  Removes tags from the feature flag.  ##### Parameters  - &#x60;values&#x60;: A list of tags to remove.  #### turnOffClientSideAvailability  Turns off client-side SDK availability for the flag. This is equivalent to unchecking the **SDKs using Mobile Key** and/or **SDKs using client-side ID** boxes for the flag. If you&#39;re using a client-side or mobile SDK, you must expose your feature flags in order for the client-side or mobile SDKs to evaluate them.  ##### Parameters  - &#x60;value&#x60;: Use \&quot;usingMobileKey\&quot; to turn on availability for mobile SDKs. Use \&quot;usingEnvironmentId\&quot; to turn on availability for client-side SDKs.  #### turnOnClientSideAvailability  Turns on client-side SDK availability for the flag. This is equivalent to unchecking the **SDKs using Mobile Key** and/or **SDKs using client-side ID** boxes for the flag. If you&#39;re using a client-side or mobile SDK, you must expose your feature flags in order for the client-side or mobile SDKs to evaluate them.  ##### Parameters  - &#x60;value&#x60;: Use \&quot;usingMobileKey\&quot; to turn on availability for mobile SDKs. Use \&quot;usingEnvironmentId\&quot; to turn on availability for client-side SDKs.  #### updateDescription  Updates the feature flag description.  ##### Parameters  - &#x60;value&#x60;: The new description.  #### updateName  Updates the feature flag name.  ##### Parameters  - &#x60;value&#x60;: The new name.  &lt;/details&gt;&lt;br /&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for updating the flag lifecycle&lt;/summary&gt;  #### archiveFlag  Archives the feature flag. This retires it from LaunchDarkly without deleting it. You cannot archive a flag that is a prerequisite of other flags.  #### deleteFlag  Deletes the feature flag and its rules. You cannot restore a deleted flag. If this flag is requested again, the flag value defined in code will be returned for all users.  #### restoreFlag  Restores the feature flag if it was previously archived.  &lt;/details&gt;  ### Example  The body of a single semantic patch can contain many different instructions.  &lt;details&gt; &lt;summary&gt;Click to expand example semantic patch request body&lt;/summary&gt;  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;production\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;turnFlagOn\&quot;     },     {       \&quot;kind\&quot;: \&quot;turnFlagOff\&quot;     },     {       \&quot;kind\&quot;: \&quot;addUserTargets\&quot;,       \&quot;variationId\&quot;: \&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;,       \&quot;values\&quot;: [\&quot;userId\&quot;, \&quot;userId2\&quot;]     },     {       \&quot;kind\&quot;: \&quot;removeUserTargets\&quot;,       \&quot;variationId\&quot;: \&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;,       \&quot;values\&quot;: [\&quot;userId3\&quot;, \&quot;userId4\&quot;]     },     {       \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,       \&quot;rolloutWeights\&quot;: {         \&quot;variationId\&quot;: 50000,         \&quot;variationId2\&quot;: 50000       },       \&quot;rolloutBucketBy\&quot;: null     },     {       \&quot;kind\&quot;: \&quot;addRule\&quot;,       \&quot;clauses\&quot;: [         {           \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,           \&quot;negate\&quot;: false,           \&quot;values\&quot;: [\&quot;test-segment\&quot;]         }       ],       \&quot;variationId\&quot;: null,       \&quot;rolloutWeights\&quot;: {         \&quot;variationId\&quot;: 50000,         \&quot;variationId2\&quot;: 50000       },       \&quot;rolloutBucketBy\&quot;: \&quot;key\&quot;     },     {       \&quot;kind\&quot;: \&quot;removeRule\&quot;,       \&quot;ruleId\&quot;: \&quot;99f12464-a429-40fc-86cc-b27612188955\&quot;     },     {       \&quot;kind\&quot;: \&quot;reorderRules\&quot;,       \&quot;ruleIds\&quot;: [\&quot;2f72974e-de68-4243-8dd3-739582147a1f\&quot;, \&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;]     },     {       \&quot;kind\&quot;: \&quot;addClauses\&quot;,       \&quot;ruleId\&quot;: \&quot;1134\&quot;,       \&quot;clauses\&quot;: [         {           \&quot;attribute\&quot;: \&quot;email\&quot;,           \&quot;op\&quot;: \&quot;in\&quot;,           \&quot;negate\&quot;: false,           \&quot;values\&quot;: [\&quot;test@test.com\&quot;]         }       ]     },     {       \&quot;kind\&quot;: \&quot;removeClauses\&quot;,       \&quot;ruleId\&quot;: \&quot;1242529\&quot;,       \&quot;clauseIds\&quot;: [\&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;]     },     {       \&quot;kind\&quot;: \&quot;updateClause\&quot;,       \&quot;ruleId\&quot;: \&quot;2f72974e-de68-4243-8dd3-739582147a1f\&quot;,       \&quot;clauseId\&quot;: \&quot;309845\&quot;,       \&quot;clause\&quot;: {         \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,         \&quot;negate\&quot;: false,         \&quot;values\&quot;: [\&quot;test-segment\&quot;]       }     },     {       \&quot;kind\&quot;: \&quot;updateRuleVariationOrRollout\&quot;,       \&quot;ruleId\&quot;: \&quot;2342\&quot;,       \&quot;rolloutWeights\&quot;: null,       \&quot;rolloutBucketBy\&quot;: null     },     {       \&quot;kind\&quot;: \&quot;updateOffVariation\&quot;,       \&quot;variationId\&quot;: \&quot;3242453\&quot;     },     {       \&quot;kind\&quot;: \&quot;addPrerequisite\&quot;,       \&quot;variationId\&quot;: \&quot;234235\&quot;,       \&quot;key\&quot;: \&quot;flagKey2\&quot;     },     {       \&quot;kind\&quot;: \&quot;updatePrerequisite\&quot;,       \&quot;variationId\&quot;: \&quot;234235\&quot;,       \&quot;key\&quot;: \&quot;flagKey2\&quot;     },     {       \&quot;kind\&quot;: \&quot;removePrerequisite\&quot;,       \&quot;key\&quot;: \&quot;flagKey\&quot;     }   ] } &#x60;&#x60;&#x60; &lt;/details&gt;  ## Using JSON Patches on a feature flag If you do not include the header described above, you can use [JSON patch](/reference#updates-using-json-patch).  When using the update feature flag endpoint to add individual users to a specific variation, there are two different patch documents, depending on whether users are already being individually targeted for the variation.  If a flag variation already has users individually targeted, the path for the JSON Patch operation is:  &#x60;&#x60;&#x60;json {   \&quot;op\&quot;: \&quot;add\&quot;,   \&quot;path\&quot;: \&quot;/environments/devint/targets/0/values/-\&quot;,   \&quot;value\&quot;: \&quot;TestClient10\&quot; } &#x60;&#x60;&#x60;  If a flag variation does not already have users individually targeted, the path for the JSON Patch operation is:  &#x60;&#x60;&#x60;json [   {     \&quot;op\&quot;: \&quot;add\&quot;,     \&quot;path\&quot;: \&quot;/environments/devint/targets/-\&quot;,     \&quot;value\&quot;: { \&quot;variation\&quot;: 0, \&quot;values\&quot;: [\&quot;TestClient10\&quot;] }   } ] &#x60;&#x60;&#x60;   ## Required approvals If a request attempts to alter a flag configuration in an environment where approvals are required for the flag, the request will fail with a 405. Changes to the flag configuration in that environment will require creating an [approval request](/tag/Approvals) or a [workflow](/tag/Workflows-(beta)).  ## Conflicts If a flag configuration change made through this endpoint would cause a pending scheduled change or approval request to fail, this endpoint will return a 400. You can ignore this check by adding an &#x60;ignoreConflicts&#x60; query parameter set to &#x60;true&#x60;. 
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
     * Perform a partial update to a feature flag. The request body must be a valid semantic patch or JSON patch.  ## Using semantic patches on a feature flag  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  The body of a semantic patch request for updating feature flags requires an &#x60;environmentKey&#x60; in addition to &#x60;instructions&#x60; and an optional &#x60;comment&#x60;. The body of the request takes the following properties:  * &#x60;comment&#x60; (string): (Optional) A description of the update. * &#x60;environmentKey&#x60; (string): (Required) The key of the LaunchDarkly environment. * &#x60;instructions&#x60; (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a &#x60;kind&#x60; property that indicates the instruction. If the action requires parameters, you must include those parameters as additional fields in the object.  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating feature flags.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for turning flags on and off&lt;/summary&gt;  #### turnFlagOff  Sets the flag&#39;s targeting state to **Off**.  For example, to turn a flag off, use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;example-environment-key\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnFlagOff\&quot; } ] } &#x60;&#x60;&#x60;  #### turnFlagOn  Sets the flag&#39;s targeting state to **On**.  For example, to turn a flag on, use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;example-environment-key\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnFlagOn\&quot; } ] } &#x60;&#x60;&#x60;  &lt;/details&gt;&lt;br /&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for working with targeting and variations&lt;/summary&gt;  #### addClauses  Adds the given clauses to the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauses&#x60;: Array of clause objects, with &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), and &#x60;values&#x60; (array of strings, numbers, or dates) properties.  #### addPrerequisite  Adds the flag indicated by &#x60;key&#x60; with variation &#x60;variationId&#x60; as a prerequisite to the flag in the path parameter.  ##### Parameters  - &#x60;key&#x60;: Flag key of the prerequisite flag. - &#x60;variationId&#x60;: ID of a variation of the prerequisite flag.  #### addRule  Adds a new targeting rule to the flag. The rule may contain &#x60;clauses&#x60; and serve the variation that &#x60;variationId&#x60; indicates, or serve a percentage rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  If you set &#x60;beforeRuleId&#x60;, this adds the new rule before the indicated rule. Otherwise, adds the new rule to the end of the list.  ##### Parameters  - &#x60;clauses&#x60;: Array of clause objects, with &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. - &#x60;beforeRuleId&#x60;: (Optional) ID of a flag rule. - &#x60;variationId&#x60;: ID of a variation of the flag. - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) User attribute.  #### addUserTargets  Adds user keys to the individual user targets for the variation that &#x60;variationId&#x60; specifies. Returns an error if this causes the flag to target the same user key in multiple variations.  ##### Parameters  - &#x60;values&#x60;: List of user keys. - &#x60;variationId&#x60;: ID of a variation on the flag.  #### addValuesToClause  Adds &#x60;values&#x60; to the values of the clause that &#x60;ruleId&#x60; and &#x60;clauseId&#x60; indicate.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings.  #### clearUserTargets  Removes all individual user targets from the variation that &#x60;variationId&#x60; specifies.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation on the flag.  #### removeClauses  Removes the clauses specified by &#x60;clauseIds&#x60; from the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseIds&#x60;: Array of IDs of clauses in the rule.  #### removePrerequisite  Removes the prerequisite flag indicated by &#x60;key&#x60;. Does nothing if this prerequisite does not exist.  ##### Parameters  - &#x60;key&#x60;: Flag key of an existing prerequisite flag.  #### removeRule  Removes the targeting rule specified by &#x60;ruleId&#x60;. Does nothing if the rule does not exist.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag.  #### removeUserTargets  Removes user keys from the individual user targets for the variation that &#x60;variationId&#x60; specifies. Does nothing if the flag does not target the user keys.  ##### Parameters  - &#x60;values&#x60;: List of user keys. - &#x60;variationId&#x60;: ID of a flag variation.  #### removeValuesFromClause  Removes &#x60;values&#x60; from the values of the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings.  #### reorderRules  Rearranges the rules to match the order given in &#x60;ruleIds&#x60;. Returns an error if &#x60;ruleIds&#x60; does not match the current set of rules on the flag.  ##### Parameters  - &#x60;ruleIds&#x60;: Array of IDs of all rules in the flag.  #### replacePrerequisites  Removes all existing prerequisites and replaces them with the list you provide.  ##### Parameters  - &#x60;prerequisites&#x60;: A list of prerequisites. Each item in the list must include a flag &#x60;key&#x60; and &#x60;variationId&#x60;.  For example, to replace prerequisites, use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;example-environment-key\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replacePrerequisites\&quot;,       \&quot;prerequisites\&quot;: [         {           \&quot;key\&quot;: \&quot;prereq-flag-key\&quot;,           \&quot;variationId\&quot;: \&quot;variation-1\&quot;         },         {           \&quot;key\&quot;: \&quot;another-prereq-flag-key\&quot;,           \&quot;variationId\&quot;: \&quot;variation-2\&quot;         }       ]     }   ] } &#x60;&#x60;&#x60;  #### replaceRules  Removes all targeting rules for the flag and replaces them with the list you provide.  ##### Parameters  - &#x60;rules&#x60;: A list of rules.  For example, to replace rules, use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;example-environment-key\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replaceRules\&quot;,       \&quot;rules\&quot;: [         {           \&quot;variationId\&quot;: \&quot;variation-1\&quot;,           \&quot;description\&quot;: \&quot;myRule\&quot;,           \&quot;clauses\&quot;: [             {               \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,               \&quot;op\&quot;: \&quot;segmentMatch\&quot;,               \&quot;values\&quot;: [\&quot;test\&quot;]             }           ],           \&quot;trackEvents\&quot;: true         }       ]     }   ] } &#x60;&#x60;&#x60;  #### replaceUserTargets  Removes all existing user targeting and replaces it with the list of targets you provide.  ##### Parameters  - &#x60;targets&#x60;: A list of user targeting. Each item in the list must include a &#x60;variationId&#x60; and a list of &#x60;values&#x60;.  For example, to replace user targets, use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;example-environment-key\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replaceUserTargets\&quot;,       \&quot;targets\&quot;: [         {           \&quot;variationId\&quot;: \&quot;variation-1\&quot;,           \&quot;values\&quot;: [\&quot;blah\&quot;, \&quot;foo\&quot;, \&quot;bar\&quot;]         },         {           \&quot;variationId\&quot;: \&quot;variation-2\&quot;,           \&quot;values\&quot;: [\&quot;abc\&quot;, \&quot;def\&quot;]         }       ]     }       ] } &#x60;&#x60;&#x60;  #### updateClause  Replaces the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60; with &#x60;clause&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;clause&#x60;: New &#x60;clause&#x60; object, with &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), and &#x60;values&#x60; (array of strings, numbers, or dates) properties.  #### updateFallthroughVariationOrRollout  Updates the default or \&quot;fallthrough\&quot; rule for the flag, which the flag serves when a user matches none of the targeting rules. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percent rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation of the flag. or - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: Optional user attribute.  #### updateOffVariation  Updates the default off variation to &#x60;variationId&#x60;. The flag serves the default off variation when the flag&#39;s targeting is **Off**.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation of the flag.  #### updatePrerequisite  Changes the prerequisite flag that &#x60;key&#x60; indicates to use the variation that &#x60;variationId&#x60; indicates. Returns an error if this prerequisite does not exist.  ##### Parameters  - &#x60;key&#x60;: Flag key of an existing prerequisite flag. - &#x60;variationId&#x60;: ID of a variation of the prerequisite flag.  #### updateRuleDescription  Updates the description of the feature flag rule.  ##### Parameters  - &#x60;description&#x60;: The new human-readable description for this rule. - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the flag.  #### updateRuleTrackEvents  Updates whether or not LaunchDarkly tracks events for the feature flag associated with this rule.  ##### Parameters  - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the flag. - &#x60;trackEvents&#x60;: Whether or not events are tracked.  #### updateRuleVariationOrRollout  Updates what &#x60;ruleId&#x60; serves when its clauses evaluate to true. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percent rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;variationId&#x60;: ID of a variation of the flag.    or  - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: Optional user attribute.  #### updateTrackEvents  Updates whether or not LaunchDarkly tracks events for the feature flag, for all rules.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  #### updateTrackEventsFallthrough  Updates whether or not LaunchDarkly tracks events for the feature flag, for the default rule.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  &lt;/details&gt;&lt;br /&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for updating flag settings&lt;/summary&gt;  #### addTags  Adds tags to the feature flag.  ##### Parameters  - &#x60;values&#x60;: A list of tags to add.  #### makeFlagPermanent  Marks the feature flag as permanent. LaunchDarkly does not prompt you to remove permanent flags, even if one variation is rolled out to all your users.  #### makeFlagTemporary  Marks the feature flag as temporary.  #### removeMaintainer  Removes the flag&#39;s maintainer. To set a new maintainer, use the flag&#39;s **Settings** tab in the LaunchDarkly user interface.  #### removeTags  Removes tags from the feature flag.  ##### Parameters  - &#x60;values&#x60;: A list of tags to remove.  #### turnOffClientSideAvailability  Turns off client-side SDK availability for the flag. This is equivalent to unchecking the **SDKs using Mobile Key** and/or **SDKs using client-side ID** boxes for the flag. If you&#39;re using a client-side or mobile SDK, you must expose your feature flags in order for the client-side or mobile SDKs to evaluate them.  ##### Parameters  - &#x60;value&#x60;: Use \&quot;usingMobileKey\&quot; to turn on availability for mobile SDKs. Use \&quot;usingEnvironmentId\&quot; to turn on availability for client-side SDKs.  #### turnOnClientSideAvailability  Turns on client-side SDK availability for the flag. This is equivalent to unchecking the **SDKs using Mobile Key** and/or **SDKs using client-side ID** boxes for the flag. If you&#39;re using a client-side or mobile SDK, you must expose your feature flags in order for the client-side or mobile SDKs to evaluate them.  ##### Parameters  - &#x60;value&#x60;: Use \&quot;usingMobileKey\&quot; to turn on availability for mobile SDKs. Use \&quot;usingEnvironmentId\&quot; to turn on availability for client-side SDKs.  #### updateDescription  Updates the feature flag description.  ##### Parameters  - &#x60;value&#x60;: The new description.  #### updateName  Updates the feature flag name.  ##### Parameters  - &#x60;value&#x60;: The new name.  &lt;/details&gt;&lt;br /&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for updating the flag lifecycle&lt;/summary&gt;  #### archiveFlag  Archives the feature flag. This retires it from LaunchDarkly without deleting it. You cannot archive a flag that is a prerequisite of other flags.  #### deleteFlag  Deletes the feature flag and its rules. You cannot restore a deleted flag. If this flag is requested again, the flag value defined in code will be returned for all users.  #### restoreFlag  Restores the feature flag if it was previously archived.  &lt;/details&gt;  ### Example  The body of a single semantic patch can contain many different instructions.  &lt;details&gt; &lt;summary&gt;Click to expand example semantic patch request body&lt;/summary&gt;  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;production\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;turnFlagOn\&quot;     },     {       \&quot;kind\&quot;: \&quot;turnFlagOff\&quot;     },     {       \&quot;kind\&quot;: \&quot;addUserTargets\&quot;,       \&quot;variationId\&quot;: \&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;,       \&quot;values\&quot;: [\&quot;userId\&quot;, \&quot;userId2\&quot;]     },     {       \&quot;kind\&quot;: \&quot;removeUserTargets\&quot;,       \&quot;variationId\&quot;: \&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;,       \&quot;values\&quot;: [\&quot;userId3\&quot;, \&quot;userId4\&quot;]     },     {       \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,       \&quot;rolloutWeights\&quot;: {         \&quot;variationId\&quot;: 50000,         \&quot;variationId2\&quot;: 50000       },       \&quot;rolloutBucketBy\&quot;: null     },     {       \&quot;kind\&quot;: \&quot;addRule\&quot;,       \&quot;clauses\&quot;: [         {           \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,           \&quot;negate\&quot;: false,           \&quot;values\&quot;: [\&quot;test-segment\&quot;]         }       ],       \&quot;variationId\&quot;: null,       \&quot;rolloutWeights\&quot;: {         \&quot;variationId\&quot;: 50000,         \&quot;variationId2\&quot;: 50000       },       \&quot;rolloutBucketBy\&quot;: \&quot;key\&quot;     },     {       \&quot;kind\&quot;: \&quot;removeRule\&quot;,       \&quot;ruleId\&quot;: \&quot;99f12464-a429-40fc-86cc-b27612188955\&quot;     },     {       \&quot;kind\&quot;: \&quot;reorderRules\&quot;,       \&quot;ruleIds\&quot;: [\&quot;2f72974e-de68-4243-8dd3-739582147a1f\&quot;, \&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;]     },     {       \&quot;kind\&quot;: \&quot;addClauses\&quot;,       \&quot;ruleId\&quot;: \&quot;1134\&quot;,       \&quot;clauses\&quot;: [         {           \&quot;attribute\&quot;: \&quot;email\&quot;,           \&quot;op\&quot;: \&quot;in\&quot;,           \&quot;negate\&quot;: false,           \&quot;values\&quot;: [\&quot;test@test.com\&quot;]         }       ]     },     {       \&quot;kind\&quot;: \&quot;removeClauses\&quot;,       \&quot;ruleId\&quot;: \&quot;1242529\&quot;,       \&quot;clauseIds\&quot;: [\&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;]     },     {       \&quot;kind\&quot;: \&quot;updateClause\&quot;,       \&quot;ruleId\&quot;: \&quot;2f72974e-de68-4243-8dd3-739582147a1f\&quot;,       \&quot;clauseId\&quot;: \&quot;309845\&quot;,       \&quot;clause\&quot;: {         \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,         \&quot;negate\&quot;: false,         \&quot;values\&quot;: [\&quot;test-segment\&quot;]       }     },     {       \&quot;kind\&quot;: \&quot;updateRuleVariationOrRollout\&quot;,       \&quot;ruleId\&quot;: \&quot;2342\&quot;,       \&quot;rolloutWeights\&quot;: null,       \&quot;rolloutBucketBy\&quot;: null     },     {       \&quot;kind\&quot;: \&quot;updateOffVariation\&quot;,       \&quot;variationId\&quot;: \&quot;3242453\&quot;     },     {       \&quot;kind\&quot;: \&quot;addPrerequisite\&quot;,       \&quot;variationId\&quot;: \&quot;234235\&quot;,       \&quot;key\&quot;: \&quot;flagKey2\&quot;     },     {       \&quot;kind\&quot;: \&quot;updatePrerequisite\&quot;,       \&quot;variationId\&quot;: \&quot;234235\&quot;,       \&quot;key\&quot;: \&quot;flagKey2\&quot;     },     {       \&quot;kind\&quot;: \&quot;removePrerequisite\&quot;,       \&quot;key\&quot;: \&quot;flagKey\&quot;     }   ] } &#x60;&#x60;&#x60; &lt;/details&gt;  ## Using JSON Patches on a feature flag If you do not include the header described above, you can use [JSON patch](/reference#updates-using-json-patch).  When using the update feature flag endpoint to add individual users to a specific variation, there are two different patch documents, depending on whether users are already being individually targeted for the variation.  If a flag variation already has users individually targeted, the path for the JSON Patch operation is:  &#x60;&#x60;&#x60;json {   \&quot;op\&quot;: \&quot;add\&quot;,   \&quot;path\&quot;: \&quot;/environments/devint/targets/0/values/-\&quot;,   \&quot;value\&quot;: \&quot;TestClient10\&quot; } &#x60;&#x60;&#x60;  If a flag variation does not already have users individually targeted, the path for the JSON Patch operation is:  &#x60;&#x60;&#x60;json [   {     \&quot;op\&quot;: \&quot;add\&quot;,     \&quot;path\&quot;: \&quot;/environments/devint/targets/-\&quot;,     \&quot;value\&quot;: { \&quot;variation\&quot;: 0, \&quot;values\&quot;: [\&quot;TestClient10\&quot;] }   } ] &#x60;&#x60;&#x60;   ## Required approvals If a request attempts to alter a flag configuration in an environment where approvals are required for the flag, the request will fail with a 405. Changes to the flag configuration in that environment will require creating an [approval request](/tag/Approvals) or a [workflow](/tag/Workflows-(beta)).  ## Conflicts If a flag configuration change made through this endpoint would cause a pending scheduled change or approval request to fail, this endpoint will return a 400. You can ignore this check by adding an &#x60;ignoreConflicts&#x60; query parameter set to &#x60;true&#x60;. 
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
     * Create a feature flag with the given name, key, and variations.
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
     * Create a feature flag with the given name, key, and variations.
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
     * Create a feature flag with the given name, key, and variations.
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
