/*
 * LaunchDarkly REST API
 * # Overview  ## Authentication  All REST API resources are authenticated with either [personal or service access tokens](https://docs.launchdarkly.com/home/account-security/api-access-tokens), or session cookies. Other authentication mechanisms are not supported. You can manage personal access tokens on your [Account settings](https://app.launchdarkly.com/settings/tokens) page.  LaunchDarkly also has SDK keys, mobile keys, and client-side IDs that are used by our server-side SDKs, mobile SDKs, and client-side SDKs, respectively. **These keys cannot be used to access our REST API**. These keys are environment-specific, and can only perform read-only operations (fetching feature flag settings).  | Auth mechanism                                                                                  | Allowed resources                                                                                     | Use cases                                          | | ----------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------- | -------------------------------------------------- | | [Personal access tokens](https://docs.launchdarkly.com/home/account-security/api-access-tokens) | Can be customized on a per-token basis                                                                | Building scripts, custom integrations, data export | | SDK keys                                                                                        | Can only access read-only SDK-specific resources and the firehose, restricted to a single environment | Server-side SDKs, Firehose API                     | | Mobile keys                                                                                     | Can only access read-only mobile SDK-specific resources, restricted to a single environment           | Mobile SDKs                                        | | Client-side ID                                                                                  | Single environment, only flags marked available to client-side                                        | Client-side JavaScript                             |  > #### Keep your access tokens and SDK keys private > > Access tokens should _never_ be exposed in untrusted contexts. Never put an access token in client-side JavaScript, or embed it in a mobile application. LaunchDarkly has special mobile keys that you can embed in mobile apps. If you accidentally expose an access token or SDK key, you can reset it from your [Account Settings](https://app.launchdarkly.com/settings#/tokens) page. > > The client-side ID is safe to embed in untrusted contexts. It's designed for use in client-side JavaScript.  ### Via request header  The preferred way to authenticate with the API is by adding an `Authorization` header containing your access token to your requests. The value of the `Authorization` header must be your access token.  Manage personal access tokens from the [Account Settings](https://app.launchdarkly.com/settings/tokens) page.  ### Via session cookie  For testing purposes, you can make API calls directly from your web browser. If you're logged in to the application, the API will use your existing session to authenticate calls.  If you have a [role](https://docs.launchdarkly.com/home/team/built-in-roles) other than Admin, or have a [custom role](https://docs.launchdarkly.com/home/team/custom-roles) defined, you may not have permission to perform some API calls. You will receive a `401` response code in that case.  > ### Modifying the Origin header causes an error > > LaunchDarkly validates that the Origin header for any API request authenticated by a session cookie matches the expected Origin header. The expected Origin header is `https://app.launchdarkly.com`. > > If the Origin header does not match what's expected, LaunchDarkly returns an error. This error can prevent the LaunchDarkly app from working correctly. > > Any browser extension that intentionally changes the Origin header can cause this problem. For example, the `Allow-Control-Allow-Origin: *` Chrome extension changes the Origin header to `http://evil.com` and causes the app to fail. > > To prevent this error, do not modify your Origin header. > > LaunchDarkly does not require origin matching when authenticating with an access token, so this issue does not affect normal API usage.  ## Representations  All resources expect and return JSON response bodies. Error responses will also send a JSON body. Read [Errors](#section/Errors) for a more detailed description of the error format used by the API.  In practice this means that you always get a response with a `Content-Type` header set to `application/json`.  In addition, request bodies for `PUT`, `POST`, `REPORT` and `PATCH` requests must be encoded as JSON with a `Content-Type` header set to `application/json`.  ### Summary and detailed representations  When you fetch a list of resources, the response includes only the most important attributes of each resource. This is a _summary representation_ of the resource. When you fetch an individual resource (for example, a single feature flag), you receive a _detailed representation_ containing all of the attributes of the resource.  The best way to find a detailed representation is to follow links. Every summary representation includes a link to its detailed representation.  ### Links and addressability  The best way to navigate the API is by following links. These are attributes in representations that link to other resources. The API always uses the same format for links:  - Links to other resources within the API are encapsulated in a `_links` object. - If the resource has a corresponding link to HTML content on the site, it is stored in a special `_site` link.  Each link has two attributes: an href (the URL) and a type (the content type). For example, a feature resource might return the following:  ```json {   \"_links\": {     \"parent\": {       \"href\": \"/api/features\",       \"type\": \"application/json\"     },     \"self\": {       \"href\": \"/api/features/sort.order\",       \"type\": \"application/json\"     }   },   \"_site\": {     \"href\": \"/features/sort.order\",     \"type\": \"text/html\"   } } ```  From this, you can navigate to the parent collection of features by following the `parent` link, or navigate to the site page for the feature by following the `_site` link.  Collections are always represented as a JSON object with an `items` attribute containing an array of representations. Like all other representations, collections have `_links` defined at the top level.  Paginated collections include `first`, `last`, `next`, and `prev` links containing a URL with the respective set of elements in the collection.  ## Updates  Resources that accept partial updates use the `PATCH` verb, and support the [JSON Patch](http://tools.ietf.org/html/rfc6902) format. Some resources also support the [JSON Merge Patch](https://tools.ietf.org/html/rfc7386) format. In addition, some resources support optional comments that can be submitted with updates. Comments appear in outgoing webhooks, the audit log, and other integrations.  ### Updates via JSON Patch  [JSON Patch](http://tools.ietf.org/html/rfc6902) is a way to specify the modifications to perform on a resource. For example, in this feature flag representation:  ```json {     \"name\": \"New recommendations engine\",     \"key\": \"engine.enable\",     \"description\": \"This is the description\",     ... } ```  You can change the feature flag's description with the following patch document:  ```json [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"This is the new description\" }] ```  JSON Patch documents are always arrays. You can specify multiple modifications to perform in a single request. You can also test that certain preconditions are met before applying the patch:  ```json [   { \"op\": \"test\", \"path\": \"/version\", \"value\": 10 },   { \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" } ] ```  The above patch request tests whether the feature flag's `version` is `10`, and if so, changes the feature flag's description.  Attributes that aren't editable, like a resource's `_links`, have names that start with an underscore.  ### Updates via JSON Merge Patch  The API also supports the [JSON Merge Patch](https://tools.ietf.org/html/rfc7386) format, as well as the [Update feature flag](/tag/Feature-flags#operation/patchFeatureFlag) resource.  JSON Merge Patch is less expressive than JSON Patch but in many cases, it is simpler to construct a merge patch document. For example, you can change a feature flag's description with the following merge patch document:  ```json {   \"description\": \"New flag description\" } ```  ### Updates with comments  You can submit optional comments with `PATCH` changes. The [Update feature flag](/tag/Feature-flags#operation/patchFeatureFlag) resource supports comments.  To submit a comment along with a JSON Patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"patch\": [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" }] } ```  To submit a comment along with a JSON Merge Patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"merge\": { \"description\": \"New flag description\" } } ```  ### Updates via semantic patches  The API also supports the Semantic patch format. A semantic `PATCH` is a way to specify the modifications to perform on a resource as a set of executable instructions.  JSON Patch uses paths and a limited set of operations to describe how to transform the current state of the resource into a new state. Semantic patch allows you to be explicit about intent using precise, custom instructions. In many cases, semantic patch instructions can also be defined independently of the current state of the resource. This can be useful when defining a change that may be applied at a future date.  For example, in this feature flag configuration in environment Production:  ```json {     \"name\": \"Alternate sort order\",     \"kind\": \"boolean\",     \"key\": \"sort.order\",    ...     \"environments\": {         \"production\": {             \"on\": true,             \"archived\": false,             \"salt\": \"c29ydC5vcmRlcg==\",             \"sel\": \"8de1085cb7354b0ab41c0e778376dfd3\",             \"lastModified\": 1469131558260,             \"version\": 81,             \"targets\": [                 {                     \"values\": [                         \"Gerhard.Little@yahoo.com\"                     ],                     \"variation\": 0                 },                 {                     \"values\": [                         \"1461797806429-33-861961230\",                         \"438580d8-02ee-418d-9eec-0085cab2bdf0\"                     ],                     \"variation\": 1                 }             ],             \"rules\": [],             \"fallthrough\": {                 \"variation\": 0             },             \"offVariation\": 1,             \"prerequisites\": [],             \"_site\": {                 \"href\": \"/default/production/features/sort.order\",                 \"type\": \"text/html\"             }        }     } } ```  You can add a date you want a user to be removed from the feature flag's user targets. For example, “remove user 1461797806429-33-861961230 from the user target for variation 0 on the Alternate sort order flag in the production environment on Wed Jul 08 2020 at 15:27:41 pm”. This is done using the following:  ```json {   \"comment\": \"update expiring user targets\",   \"instructions\": [     {       \"kind\": \"removeExpireUserTargetDate\",       \"userKey\": \"userKey\",       \"variationId\": \"978d53f9-7fe3-4a63-992d-97bcb4535dc8\"     },     {       \"kind\": \"updateExpireUserTargetDate\",       \"userKey\": \"userKey2\",       \"variationId\": \"978d53f9-7fe3-4a63-992d-97bcb4535dc8\",       \"value\": 1587582000000     },     {       \"kind\": \"addExpireUserTargetDate\",       \"userKey\": \"userKey3\",       \"variationId\": \"978d53f9-7fe3-4a63-992d-97bcb4535dc8\",       \"value\": 1594247266386     }   ] } ```  Here is another example. In this feature flag configuration:  ```json {   \"name\": \"New recommendations engine\",   \"key\": \"engine.enable\",   \"environments\": {     \"test\": {       \"on\": true     }   } } ```  You can change the feature flag's description with the following patch document as a set of executable instructions. For example, “add user X to targets for variation Y and remove user A from targets for variation B for test flag”:  ```json {   \"comment\": \"\",   \"instructions\": [     {       \"kind\": \"removeUserTargets\",       \"values\": [\"438580d8-02ee-418d-9eec-0085cab2bdf0\"],       \"variationId\": \"852cb784-54ff-46b9-8c35-5498d2e4f270\"     },     {       \"kind\": \"addUserTargets\",       \"values\": [\"438580d8-02ee-418d-9eec-0085cab2bdf0\"],       \"variationId\": \"1bb18465-33b6-49aa-a3bd-eeb6650b33ad\"     }   ] } ```  > ### Supported semantic patch API endpoints > > - [Update feature flag](/tag/Feature-flags#operation/patchFeatureFlag) > - [Update expiring user targets on feature flag](/tag/Feature-flags#operation/patchExpiringUserTargets) > - [Update expiring user target for flags](/tag/User-settings#operation/patchExpiringFlagsForUser) > - [Update expiring user targets on segment](/tag/Segments#operation/patchExpiringUserTargetsForSegment)  ## Errors  The API always returns errors in a common format. Here's an example:  ```json {   \"code\": \"invalid_request\",   \"message\": \"A feature with that key already exists\",   \"id\": \"30ce6058-87da-11e4-b116-123b93f75cba\" } ```  The general class of error is indicated by the `code`. The `message` is a human-readable explanation of what went wrong. The `id` is a unique identifier. Use it when you're working with LaunchDarkly support to debug a problem with a specific API call.  ### HTTP Status - Error Response Codes  | Code | Definition        | Desc.                                                                                       | Possible Solution                                                | | ---- | ----------------- | ------------------------------------------------------------------------------------------- | ---------------------------------------------------------------- | | 400  | Bad Request       | A request that fails may return this HTTP response code.                                    | Ensure JSON syntax in request body is correct.                   | | 401  | Unauthorized      | User doesn't have permission to an API call.                                                | Ensure your SDK key is good.                                     | | 403  | Forbidden         | User does not have permission for operation.                                                | Ensure that the user or access token has proper permissions set. | | 409  | Conflict          | The API request could not be completed because it conflicted with a concurrent API request. | Retry your request.                                              | | 429  | Too many requests | See [Rate limiting](/#section/Rate-limiting).                                               | Wait and try again later.                                        |  ## CORS  The LaunchDarkly API supports Cross Origin Resource Sharing (CORS) for AJAX requests from any origin. If an `Origin` header is given in a request, it will be echoed as an explicitly allowed origin. Otherwise, a wildcard is returned: `Access-Control-Allow-Origin: *`. For more information on CORS, see the [CORS W3C Recommendation](http://www.w3.org/TR/cors). Example CORS headers might look like:  ```http Access-Control-Allow-Headers: Accept, Content-Type, Content-Length, Accept-Encoding, Authorization Access-Control-Allow-Methods: OPTIONS, GET, DELETE, PATCH Access-Control-Allow-Origin: * Access-Control-Max-Age: 300 ```  You can make authenticated CORS calls just as you would make same-origin calls, using either [token or session-based authentication](#section/Authentication). If you’re using session auth, you should set the `withCredentials` property for your `xhr` request to `true`. You should never expose your access tokens to untrusted users.  ## Rate limiting  We use several rate limiting strategies to ensure the availability of our APIs. Rate-limited calls to our APIs will return a `429` status code. Calls to our APIs will include headers indicating the current rate limit status. The specific headers returned depend on the API route being called. The limits differ based on the route, authentication mechanism, and other factors. Routes that are not rate limited may not contain any of the headers described below.  > ### Rate limiting and SDKs > > LaunchDarkly SDKs are never rate limited and do not use the API endpoints defined here. LaunchDarkly uses a different set of approaches, including streaming/server-sent events and a global CDN, to ensure availability to the routes used by LaunchDarkly SDKs. > > The client-side ID is safe to embed in untrusted contexts. It's designed for use in client-side JavaScript.  ### Global rate limits  Authenticated requests are subject to a global limit. This is the maximum number of calls that can be made to the API per ten seconds. All personal access tokens on the account share this limit, so exceeding the limit with one access token will impact other tokens. Calls that are subject to global rate limits will return the headers below:  | Header name                    | Description                                                                      | | ------------------------------ | -------------------------------------------------------------------------------- | | `X-Ratelimit-Global-Remaining` | The maximum number of requests the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`            | The time at which the current rate limit window resets in epoch milliseconds.    |  We do not publicly document the specific number of calls that can be made globally. This limit may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limit.  ### Route-level rate limits  Some authenticated routes have custom rate limits. These also reset every ten seconds. Any access tokens hitting the same route share this limit, so exceeding the limit with one access token may impact other tokens. Calls that are subject to route-level rate limits will return the headers below:  | Header name                   | Description                                                                                           | | ----------------------------- | ----------------------------------------------------------------------------------------------------- | | `X-Ratelimit-Route-Remaining` | The maximum number of requests to the current route the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`           | The time at which the current rate limit window resets in epoch milliseconds.                         |  A _route_ represents a specific URL pattern and verb. For example, the [Delete environment](/tag/Environments#operation/deleteEnvironment) endpoint is considered a single route, and each call to delete an environment counts against your route-level rate limit for that route.  We do not publicly document the specific number of calls that can be made to each endpoint per ten seconds. These limits may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limits.  ### IP-based rate limiting  We also employ IP-based rate limiting on some API routes. If you hit an IP-based rate limit, your API response will include a `Retry-After` header indicating how long to wait before re-trying the call. Clients must wait at least `Retry-After` seconds before making additional calls to our API, and should employ jitter and backoff strategies to avoid triggering rate limits again.  ## OpenAPI (Swagger)  We have a [complete OpenAPI (Swagger) specification](https://app.launchdarkly.com/api/v2/openapi.json) for our API.  You can use this specification to generate client libraries to interact with our REST API in your language of choice.  This specification is supported by several API-based tools such as Postman and Insomnia. In many cases, you can directly import our specification to ease use in navigating the APIs in the tooling.  ## Client libraries  We auto-generate multiple client libraries based on our OpenAPI specification. To learn more, visit [GitHub](https://github.com/search?q=topic%3Alaunchdarkly-api+org%3Alaunchdarkly&type=Repositories).  ## Method Overriding  Some firewalls and HTTP clients restrict the use of verbs other than `GET` and `POST`. In those environments, our API endpoints that use `PUT`, `PATCH`, and `DELETE` verbs will be inaccessible.  To avoid this issue, our API supports the `X-HTTP-Method-Override` header, allowing clients to \"tunnel\" `PUT`, `PATCH`, and `DELETE` requests via a `POST` request.  For example, if you wish to call one of our `PATCH` resources via a `POST` request, you can include `X-HTTP-Method-Override:PATCH` as a header.  ## Beta resources  We sometimes release new API resources in **beta** status before we release them with general availability.  Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  We try to promote resources into general availability as quickly as possible. This happens after sufficient testing and when we're satisfied that we no longer need to make backwards-incompatible changes.  We mark beta resources with a \"Beta\" callout in our documentation, pictured below:  > ### This feature is in beta > > To use this feature, pass in a header including the `LD-API-Version` key with value set to `beta`. Use this header with each call. To learn more, read [Beta resources](/#section/Beta-resources).  ### Using beta resources  To use a beta resource, you must include a header in the request. If you call a beta resource without this header, you'll receive a `403` response.  Use this header:  ``` LD-API-Version: beta ```  ## Versioning  We try hard to keep our REST API backwards compatible, but we occasionally have to make backwards-incompatible changes in the process of shipping new features. These breaking changes can cause unexpected behavior if you don't prepare for them accordingly.  Updates to our REST API include support for the latest features in LaunchDarkly. We also release a new version of our REST API every time we make a breaking change. We provide simultaneous support for multiple API versions so you can migrate from your current API version to a new version at your own pace.  ### Setting the API version per request  You can set the API version on a specific request by sending an `LD-API-Version` header, as shown in the example below:  ``` LD-API-Version: 20191212 ```  The header value is the version number of the API version you'd like to request. The number for each version corresponds to the date the version was released. In the example above the version `20191212` corresponds to December 12, 2019.  ### Setting the API version per access token  When creating an access token, you must specify a specific version of the API to use. This ensures that integrations using this token cannot be broken by version changes.  Tokens created before versioning was released have their version set to `20160426` (the version of the API that existed before versioning) so that they continue working the same way they did before versioning.  If you would like to upgrade your integration to use a new API version, you can explicitly set the header described above.  > ### Best practice: Set the header for every client or integration > > We recommend that you set the API version header explicitly in any client or integration you build. > > Only rely on the access token API version during manual testing. 
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
import com.launchdarkly.api.model.PatchWithComment;
import com.launchdarkly.api.model.RateLimitedErrorRep;
import com.launchdarkly.api.model.StatusConflictErrorRep;
import com.launchdarkly.api.model.UnauthorizedErrorRep;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeatureFlagsApi {
    private ApiClient localVarApiClient;

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

    /**
     * Build call for copyFeatureFlag
     * @param projKey The project key. (required)
     * @param featureFlagKey The feature flag&#39;s key. The key identifies the flag in your code. (required)
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
    public okhttp3.Call copyFeatureFlagCall(String projKey, String featureFlagKey, FlagCopyConfigPost flagCopyConfigPost, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = flagCopyConfigPost;

        // create path and map variables
        String localVarPath = "/api/v2/flags/{projKey}/{featureFlagKey}/copy"
            .replaceAll("\\{" + "projKey" + "\\}", localVarApiClient.escapeString(projKey.toString()))
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
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call copyFeatureFlagValidateBeforeCall(String projKey, String featureFlagKey, FlagCopyConfigPost flagCopyConfigPost, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projKey' is set
        if (projKey == null) {
            throw new ApiException("Missing the required parameter 'projKey' when calling copyFeatureFlag(Async)");
        }
        
        // verify the required parameter 'featureFlagKey' is set
        if (featureFlagKey == null) {
            throw new ApiException("Missing the required parameter 'featureFlagKey' when calling copyFeatureFlag(Async)");
        }
        
        // verify the required parameter 'flagCopyConfigPost' is set
        if (flagCopyConfigPost == null) {
            throw new ApiException("Missing the required parameter 'flagCopyConfigPost' when calling copyFeatureFlag(Async)");
        }
        

        okhttp3.Call localVarCall = copyFeatureFlagCall(projKey, featureFlagKey, flagCopyConfigPost, _callback);
        return localVarCall;

    }

    /**
     * Copy feature flag
     * The includedActions and excludedActions define the parts of the flag configuration that are copied or not copied. By default, the entire flag configuration is copied.  You can have either &#x60;includedActions&#x60; or &#x60;excludedActions&#x60; but not both.  Valid &#x60;includedActions&#x60; and &#x60;excludedActions&#x60; include:  - &#x60;updateOn&#x60; - &#x60;updatePrerequisites&#x60; - &#x60;updateTargets&#x60; - &#x60;updateRules&#x60; - &#x60;updateFallthrough&#x60; - &#x60;updateOffVariation&#x60;    The &#x60;source&#x60; and &#x60;target&#x60; must be JSON objects if using curl, specifying the environment key and (optional) current flag configuration version in that environment. For example:  &#x60;&#x60;&#x60;json {   \&quot;key\&quot;: \&quot;production\&quot;,   \&quot;currentVersion\&quot;: 3 } &#x60;&#x60;&#x60;  If target is specified as above, the API will test to ensure that the current flag version in the &#x60;production&#x60; environment is &#x60;3&#x60;, and reject attempts to copy settings to &#x60;production&#x60; otherwise. You can use this to enforce optimistic locking on copy attempts. 
     * @param projKey The project key. (required)
     * @param featureFlagKey The feature flag&#39;s key. The key identifies the flag in your code. (required)
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
    public FeatureFlag copyFeatureFlag(String projKey, String featureFlagKey, FlagCopyConfigPost flagCopyConfigPost) throws ApiException {
        ApiResponse<FeatureFlag> localVarResp = copyFeatureFlagWithHttpInfo(projKey, featureFlagKey, flagCopyConfigPost);
        return localVarResp.getData();
    }

    /**
     * Copy feature flag
     * The includedActions and excludedActions define the parts of the flag configuration that are copied or not copied. By default, the entire flag configuration is copied.  You can have either &#x60;includedActions&#x60; or &#x60;excludedActions&#x60; but not both.  Valid &#x60;includedActions&#x60; and &#x60;excludedActions&#x60; include:  - &#x60;updateOn&#x60; - &#x60;updatePrerequisites&#x60; - &#x60;updateTargets&#x60; - &#x60;updateRules&#x60; - &#x60;updateFallthrough&#x60; - &#x60;updateOffVariation&#x60;    The &#x60;source&#x60; and &#x60;target&#x60; must be JSON objects if using curl, specifying the environment key and (optional) current flag configuration version in that environment. For example:  &#x60;&#x60;&#x60;json {   \&quot;key\&quot;: \&quot;production\&quot;,   \&quot;currentVersion\&quot;: 3 } &#x60;&#x60;&#x60;  If target is specified as above, the API will test to ensure that the current flag version in the &#x60;production&#x60; environment is &#x60;3&#x60;, and reject attempts to copy settings to &#x60;production&#x60; otherwise. You can use this to enforce optimistic locking on copy attempts. 
     * @param projKey The project key. (required)
     * @param featureFlagKey The feature flag&#39;s key. The key identifies the flag in your code. (required)
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
    public ApiResponse<FeatureFlag> copyFeatureFlagWithHttpInfo(String projKey, String featureFlagKey, FlagCopyConfigPost flagCopyConfigPost) throws ApiException {
        okhttp3.Call localVarCall = copyFeatureFlagValidateBeforeCall(projKey, featureFlagKey, flagCopyConfigPost, null);
        Type localVarReturnType = new TypeToken<FeatureFlag>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Copy feature flag (asynchronously)
     * The includedActions and excludedActions define the parts of the flag configuration that are copied or not copied. By default, the entire flag configuration is copied.  You can have either &#x60;includedActions&#x60; or &#x60;excludedActions&#x60; but not both.  Valid &#x60;includedActions&#x60; and &#x60;excludedActions&#x60; include:  - &#x60;updateOn&#x60; - &#x60;updatePrerequisites&#x60; - &#x60;updateTargets&#x60; - &#x60;updateRules&#x60; - &#x60;updateFallthrough&#x60; - &#x60;updateOffVariation&#x60;    The &#x60;source&#x60; and &#x60;target&#x60; must be JSON objects if using curl, specifying the environment key and (optional) current flag configuration version in that environment. For example:  &#x60;&#x60;&#x60;json {   \&quot;key\&quot;: \&quot;production\&quot;,   \&quot;currentVersion\&quot;: 3 } &#x60;&#x60;&#x60;  If target is specified as above, the API will test to ensure that the current flag version in the &#x60;production&#x60; environment is &#x60;3&#x60;, and reject attempts to copy settings to &#x60;production&#x60; otherwise. You can use this to enforce optimistic locking on copy attempts. 
     * @param projKey The project key. (required)
     * @param featureFlagKey The feature flag&#39;s key. The key identifies the flag in your code. (required)
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
    public okhttp3.Call copyFeatureFlagAsync(String projKey, String featureFlagKey, FlagCopyConfigPost flagCopyConfigPost, final ApiCallback<FeatureFlag> _callback) throws ApiException {

        okhttp3.Call localVarCall = copyFeatureFlagValidateBeforeCall(projKey, featureFlagKey, flagCopyConfigPost, _callback);
        Type localVarReturnType = new TypeToken<FeatureFlag>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteFeatureFlag
     * @param projKey The project key. (required)
     * @param key The feature flag&#39;s key. The key identifies the flag in your code. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteFeatureFlagCall(String projKey, String key, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/flags/{projKey}/{key}"
            .replaceAll("\\{" + "projKey" + "\\}", localVarApiClient.escapeString(projKey.toString()))
            .replaceAll("\\{" + "key" + "\\}", localVarApiClient.escapeString(key.toString()));

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
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteFeatureFlagValidateBeforeCall(String projKey, String key, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projKey' is set
        if (projKey == null) {
            throw new ApiException("Missing the required parameter 'projKey' when calling deleteFeatureFlag(Async)");
        }
        
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException("Missing the required parameter 'key' when calling deleteFeatureFlag(Async)");
        }
        

        okhttp3.Call localVarCall = deleteFeatureFlagCall(projKey, key, _callback);
        return localVarCall;

    }

    /**
     * Delete feature flag
     * Delete a feature flag in all environments. Use with caution: only delete feature flags your application no longer uses.
     * @param projKey The project key. (required)
     * @param key The feature flag&#39;s key. The key identifies the flag in your code. (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public void deleteFeatureFlag(String projKey, String key) throws ApiException {
        deleteFeatureFlagWithHttpInfo(projKey, key);
    }

    /**
     * Delete feature flag
     * Delete a feature flag in all environments. Use with caution: only delete feature flags your application no longer uses.
     * @param projKey The project key. (required)
     * @param key The feature flag&#39;s key. The key identifies the flag in your code. (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteFeatureFlagWithHttpInfo(String projKey, String key) throws ApiException {
        okhttp3.Call localVarCall = deleteFeatureFlagValidateBeforeCall(projKey, key, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete feature flag (asynchronously)
     * Delete a feature flag in all environments. Use with caution: only delete feature flags your application no longer uses.
     * @param projKey The project key. (required)
     * @param key The feature flag&#39;s key. The key identifies the flag in your code. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteFeatureFlagAsync(String projKey, String key, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteFeatureFlagValidateBeforeCall(projKey, key, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getExpiringUserTargets
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param flagKey The feature flag key. (required)
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
    public okhttp3.Call getExpiringUserTargetsCall(String projKey, String envKey, String flagKey, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/flags/{projKey}/{flagKey}/expiring-user-targets/{envKey}"
            .replaceAll("\\{" + "projKey" + "\\}", localVarApiClient.escapeString(projKey.toString()))
            .replaceAll("\\{" + "envKey" + "\\}", localVarApiClient.escapeString(envKey.toString()))
            .replaceAll("\\{" + "flagKey" + "\\}", localVarApiClient.escapeString(flagKey.toString()));

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
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getExpiringUserTargetsValidateBeforeCall(String projKey, String envKey, String flagKey, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projKey' is set
        if (projKey == null) {
            throw new ApiException("Missing the required parameter 'projKey' when calling getExpiringUserTargets(Async)");
        }
        
        // verify the required parameter 'envKey' is set
        if (envKey == null) {
            throw new ApiException("Missing the required parameter 'envKey' when calling getExpiringUserTargets(Async)");
        }
        
        // verify the required parameter 'flagKey' is set
        if (flagKey == null) {
            throw new ApiException("Missing the required parameter 'flagKey' when calling getExpiringUserTargets(Async)");
        }
        

        okhttp3.Call localVarCall = getExpiringUserTargetsCall(projKey, envKey, flagKey, _callback);
        return localVarCall;

    }

    /**
     * Get expiring user targets for feature flag
     * Get a list of user targets on a feature flag that are scheduled for removal.
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param flagKey The feature flag key. (required)
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
    public ExpiringUserTargetGetResponse getExpiringUserTargets(String projKey, String envKey, String flagKey) throws ApiException {
        ApiResponse<ExpiringUserTargetGetResponse> localVarResp = getExpiringUserTargetsWithHttpInfo(projKey, envKey, flagKey);
        return localVarResp.getData();
    }

    /**
     * Get expiring user targets for feature flag
     * Get a list of user targets on a feature flag that are scheduled for removal.
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param flagKey The feature flag key. (required)
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
    public ApiResponse<ExpiringUserTargetGetResponse> getExpiringUserTargetsWithHttpInfo(String projKey, String envKey, String flagKey) throws ApiException {
        okhttp3.Call localVarCall = getExpiringUserTargetsValidateBeforeCall(projKey, envKey, flagKey, null);
        Type localVarReturnType = new TypeToken<ExpiringUserTargetGetResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get expiring user targets for feature flag (asynchronously)
     * Get a list of user targets on a feature flag that are scheduled for removal.
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param flagKey The feature flag key. (required)
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
    public okhttp3.Call getExpiringUserTargetsAsync(String projKey, String envKey, String flagKey, final ApiCallback<ExpiringUserTargetGetResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = getExpiringUserTargetsValidateBeforeCall(projKey, envKey, flagKey, _callback);
        Type localVarReturnType = new TypeToken<ExpiringUserTargetGetResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getFeatureFlag
     * @param projKey The project key (required)
     * @param key The feature flag key (required)
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
    public okhttp3.Call getFeatureFlagCall(String projKey, String key, String env, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/flags/{projKey}/{key}"
            .replaceAll("\\{" + "projKey" + "\\}", localVarApiClient.escapeString(projKey.toString()))
            .replaceAll("\\{" + "key" + "\\}", localVarApiClient.escapeString(key.toString()));

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
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getFeatureFlagValidateBeforeCall(String projKey, String key, String env, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projKey' is set
        if (projKey == null) {
            throw new ApiException("Missing the required parameter 'projKey' when calling getFeatureFlag(Async)");
        }
        
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException("Missing the required parameter 'key' when calling getFeatureFlag(Async)");
        }
        

        okhttp3.Call localVarCall = getFeatureFlagCall(projKey, key, env, _callback);
        return localVarCall;

    }

    /**
     * Get feature flag
     * Get a single feature flag by key. By default, this returns the configurations for all environments. You can filter environments with the &#x60;env&#x60; query parameter. For example, setting &#x60;env&#x3D;production&#x60; restricts the returned configurations to just the &#x60;production&#x60; environment.
     * @param projKey The project key (required)
     * @param key The feature flag key (required)
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
    public FeatureFlag getFeatureFlag(String projKey, String key, String env) throws ApiException {
        ApiResponse<FeatureFlag> localVarResp = getFeatureFlagWithHttpInfo(projKey, key, env);
        return localVarResp.getData();
    }

    /**
     * Get feature flag
     * Get a single feature flag by key. By default, this returns the configurations for all environments. You can filter environments with the &#x60;env&#x60; query parameter. For example, setting &#x60;env&#x3D;production&#x60; restricts the returned configurations to just the &#x60;production&#x60; environment.
     * @param projKey The project key (required)
     * @param key The feature flag key (required)
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
    public ApiResponse<FeatureFlag> getFeatureFlagWithHttpInfo(String projKey, String key, String env) throws ApiException {
        okhttp3.Call localVarCall = getFeatureFlagValidateBeforeCall(projKey, key, env, null);
        Type localVarReturnType = new TypeToken<FeatureFlag>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get feature flag (asynchronously)
     * Get a single feature flag by key. By default, this returns the configurations for all environments. You can filter environments with the &#x60;env&#x60; query parameter. For example, setting &#x60;env&#x3D;production&#x60; restricts the returned configurations to just the &#x60;production&#x60; environment.
     * @param projKey The project key (required)
     * @param key The feature flag key (required)
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
    public okhttp3.Call getFeatureFlagAsync(String projKey, String key, String env, final ApiCallback<FeatureFlag> _callback) throws ApiException {

        okhttp3.Call localVarCall = getFeatureFlagValidateBeforeCall(projKey, key, env, _callback);
        Type localVarReturnType = new TypeToken<FeatureFlag>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getFeatureFlagStatus
     * @param projKey The project key (required)
     * @param envKey The environment key (required)
     * @param key The feature flag key (required)
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
    public okhttp3.Call getFeatureFlagStatusCall(String projKey, String envKey, String key, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/flag-statuses/{projKey}/{envKey}/{key}"
            .replaceAll("\\{" + "projKey" + "\\}", localVarApiClient.escapeString(projKey.toString()))
            .replaceAll("\\{" + "envKey" + "\\}", localVarApiClient.escapeString(envKey.toString()))
            .replaceAll("\\{" + "key" + "\\}", localVarApiClient.escapeString(key.toString()));

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
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getFeatureFlagStatusValidateBeforeCall(String projKey, String envKey, String key, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projKey' is set
        if (projKey == null) {
            throw new ApiException("Missing the required parameter 'projKey' when calling getFeatureFlagStatus(Async)");
        }
        
        // verify the required parameter 'envKey' is set
        if (envKey == null) {
            throw new ApiException("Missing the required parameter 'envKey' when calling getFeatureFlagStatus(Async)");
        }
        
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException("Missing the required parameter 'key' when calling getFeatureFlagStatus(Async)");
        }
        

        okhttp3.Call localVarCall = getFeatureFlagStatusCall(projKey, envKey, key, _callback);
        return localVarCall;

    }

    /**
     * Get feature flag status
     * Get the status for a particular feature flag.
     * @param projKey The project key (required)
     * @param envKey The environment key (required)
     * @param key The feature flag key (required)
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
    public FlagStatusRep getFeatureFlagStatus(String projKey, String envKey, String key) throws ApiException {
        ApiResponse<FlagStatusRep> localVarResp = getFeatureFlagStatusWithHttpInfo(projKey, envKey, key);
        return localVarResp.getData();
    }

    /**
     * Get feature flag status
     * Get the status for a particular feature flag.
     * @param projKey The project key (required)
     * @param envKey The environment key (required)
     * @param key The feature flag key (required)
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
    public ApiResponse<FlagStatusRep> getFeatureFlagStatusWithHttpInfo(String projKey, String envKey, String key) throws ApiException {
        okhttp3.Call localVarCall = getFeatureFlagStatusValidateBeforeCall(projKey, envKey, key, null);
        Type localVarReturnType = new TypeToken<FlagStatusRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get feature flag status (asynchronously)
     * Get the status for a particular feature flag.
     * @param projKey The project key (required)
     * @param envKey The environment key (required)
     * @param key The feature flag key (required)
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
    public okhttp3.Call getFeatureFlagStatusAsync(String projKey, String envKey, String key, final ApiCallback<FlagStatusRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getFeatureFlagStatusValidateBeforeCall(projKey, envKey, key, _callback);
        Type localVarReturnType = new TypeToken<FlagStatusRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getFeatureFlagStatusAcrossEnvironments
     * @param projKey The project key (required)
     * @param key The feature flag key (required)
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
    public okhttp3.Call getFeatureFlagStatusAcrossEnvironmentsCall(String projKey, String key, String env, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/flag-status/{projKey}/{key}"
            .replaceAll("\\{" + "projKey" + "\\}", localVarApiClient.escapeString(projKey.toString()))
            .replaceAll("\\{" + "key" + "\\}", localVarApiClient.escapeString(key.toString()));

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
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getFeatureFlagStatusAcrossEnvironmentsValidateBeforeCall(String projKey, String key, String env, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projKey' is set
        if (projKey == null) {
            throw new ApiException("Missing the required parameter 'projKey' when calling getFeatureFlagStatusAcrossEnvironments(Async)");
        }
        
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException("Missing the required parameter 'key' when calling getFeatureFlagStatusAcrossEnvironments(Async)");
        }
        

        okhttp3.Call localVarCall = getFeatureFlagStatusAcrossEnvironmentsCall(projKey, key, env, _callback);
        return localVarCall;

    }

    /**
     * Get flag status across environments
     * Get the status for a particular feature flag across environments.
     * @param projKey The project key (required)
     * @param key The feature flag key (required)
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
    public FeatureFlagStatusAcrossEnvironments getFeatureFlagStatusAcrossEnvironments(String projKey, String key, String env) throws ApiException {
        ApiResponse<FeatureFlagStatusAcrossEnvironments> localVarResp = getFeatureFlagStatusAcrossEnvironmentsWithHttpInfo(projKey, key, env);
        return localVarResp.getData();
    }

    /**
     * Get flag status across environments
     * Get the status for a particular feature flag across environments.
     * @param projKey The project key (required)
     * @param key The feature flag key (required)
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
    public ApiResponse<FeatureFlagStatusAcrossEnvironments> getFeatureFlagStatusAcrossEnvironmentsWithHttpInfo(String projKey, String key, String env) throws ApiException {
        okhttp3.Call localVarCall = getFeatureFlagStatusAcrossEnvironmentsValidateBeforeCall(projKey, key, env, null);
        Type localVarReturnType = new TypeToken<FeatureFlagStatusAcrossEnvironments>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get flag status across environments (asynchronously)
     * Get the status for a particular feature flag across environments.
     * @param projKey The project key (required)
     * @param key The feature flag key (required)
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
    public okhttp3.Call getFeatureFlagStatusAcrossEnvironmentsAsync(String projKey, String key, String env, final ApiCallback<FeatureFlagStatusAcrossEnvironments> _callback) throws ApiException {

        okhttp3.Call localVarCall = getFeatureFlagStatusAcrossEnvironmentsValidateBeforeCall(projKey, key, env, _callback);
        Type localVarReturnType = new TypeToken<FeatureFlagStatusAcrossEnvironments>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getFeatureFlagStatuses
     * @param projKey The project key (required)
     * @param envKey Filter configurations by environment (required)
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
    public okhttp3.Call getFeatureFlagStatusesCall(String projKey, String envKey, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/flag-statuses/{projKey}/{envKey}"
            .replaceAll("\\{" + "projKey" + "\\}", localVarApiClient.escapeString(projKey.toString()))
            .replaceAll("\\{" + "envKey" + "\\}", localVarApiClient.escapeString(envKey.toString()));

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
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getFeatureFlagStatusesValidateBeforeCall(String projKey, String envKey, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projKey' is set
        if (projKey == null) {
            throw new ApiException("Missing the required parameter 'projKey' when calling getFeatureFlagStatuses(Async)");
        }
        
        // verify the required parameter 'envKey' is set
        if (envKey == null) {
            throw new ApiException("Missing the required parameter 'envKey' when calling getFeatureFlagStatuses(Async)");
        }
        

        okhttp3.Call localVarCall = getFeatureFlagStatusesCall(projKey, envKey, _callback);
        return localVarCall;

    }

    /**
     * List feature flag statuses
     * Get a list of statuses for all feature flags. The status includes the last time the feature flag was requested, as well as a state, which is one of the following:  - &#x60;new&#x60;: the feature flag was created within the last seven days, and has not been requested yet - &#x60;active&#x60;: the feature flag was requested by your servers or clients within the last seven days - &#x60;inactive&#x60;: the feature flag was created more than seven days ago, and hasn&#39;t been requested by your servers or clients within the past seven days - &#x60;launched&#x60;: one variation of the feature flag has been rolled out to all your users for at least 7 days 
     * @param projKey The project key (required)
     * @param envKey Filter configurations by environment (required)
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
    public FeatureFlagStatuses getFeatureFlagStatuses(String projKey, String envKey) throws ApiException {
        ApiResponse<FeatureFlagStatuses> localVarResp = getFeatureFlagStatusesWithHttpInfo(projKey, envKey);
        return localVarResp.getData();
    }

    /**
     * List feature flag statuses
     * Get a list of statuses for all feature flags. The status includes the last time the feature flag was requested, as well as a state, which is one of the following:  - &#x60;new&#x60;: the feature flag was created within the last seven days, and has not been requested yet - &#x60;active&#x60;: the feature flag was requested by your servers or clients within the last seven days - &#x60;inactive&#x60;: the feature flag was created more than seven days ago, and hasn&#39;t been requested by your servers or clients within the past seven days - &#x60;launched&#x60;: one variation of the feature flag has been rolled out to all your users for at least 7 days 
     * @param projKey The project key (required)
     * @param envKey Filter configurations by environment (required)
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
    public ApiResponse<FeatureFlagStatuses> getFeatureFlagStatusesWithHttpInfo(String projKey, String envKey) throws ApiException {
        okhttp3.Call localVarCall = getFeatureFlagStatusesValidateBeforeCall(projKey, envKey, null);
        Type localVarReturnType = new TypeToken<FeatureFlagStatuses>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List feature flag statuses (asynchronously)
     * Get a list of statuses for all feature flags. The status includes the last time the feature flag was requested, as well as a state, which is one of the following:  - &#x60;new&#x60;: the feature flag was created within the last seven days, and has not been requested yet - &#x60;active&#x60;: the feature flag was requested by your servers or clients within the last seven days - &#x60;inactive&#x60;: the feature flag was created more than seven days ago, and hasn&#39;t been requested by your servers or clients within the past seven days - &#x60;launched&#x60;: one variation of the feature flag has been rolled out to all your users for at least 7 days 
     * @param projKey The project key (required)
     * @param envKey Filter configurations by environment (required)
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
    public okhttp3.Call getFeatureFlagStatusesAsync(String projKey, String envKey, final ApiCallback<FeatureFlagStatuses> _callback) throws ApiException {

        okhttp3.Call localVarCall = getFeatureFlagStatusesValidateBeforeCall(projKey, envKey, _callback);
        Type localVarReturnType = new TypeToken<FeatureFlagStatuses>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getFeatureFlags
     * @param projKey The project key (required)
     * @param env Filter configurations by environment (optional)
     * @param tag Filter feature flags by tag (optional)
     * @param limit The number of feature flags to return. Defaults to -1, which returns all flags (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next limit items (optional)
     * @param query A string that matches against the flags&#39; keys and names. It is not case sensitive (optional)
     * @param archived A boolean to filter the list to archived flags. When this is absent, only unarchived flags will be returned (optional)
     * @param summary By default in API version &gt;&#x3D; 1, flags will _not_ include their list of prerequisites, targets or rules.  Set summary&#x3D;0 to include these fields for each flag returned (optional)
     * @param filter A comma-separated list of filters. Each filter is of the form field:value (optional)
     * @param sort A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order (optional)
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
    public okhttp3.Call getFeatureFlagsCall(String projKey, String env, String tag, Long limit, Long offset, String query, Boolean archived, Boolean summary, String filter, String sort, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/flags/{projKey}"
            .replaceAll("\\{" + "projKey" + "\\}", localVarApiClient.escapeString(projKey.toString()));

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

        if (query != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("query", query));
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
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getFeatureFlagsValidateBeforeCall(String projKey, String env, String tag, Long limit, Long offset, String query, Boolean archived, Boolean summary, String filter, String sort, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projKey' is set
        if (projKey == null) {
            throw new ApiException("Missing the required parameter 'projKey' when calling getFeatureFlags(Async)");
        }
        

        okhttp3.Call localVarCall = getFeatureFlagsCall(projKey, env, tag, limit, offset, query, archived, summary, filter, sort, _callback);
        return localVarCall;

    }

    /**
     * List feature flags
     * Get a list of all features in the given project. By default, each feature includes configurations for each environment. You can filter environments with the env query parameter. For example, setting &#x60;env&#x3D;production&#x60; restricts the returned configurations to just your production environment. You can also filter feature flags by tag with the tag query parameter.  We support the following fields for filters:  - &#x60;query&#x60; is a string that matches against the flags&#39; keys and names. It is not case sensitive. - &#x60;archived&#x60; is a boolean to filter the list to archived flags. When this is absent, only unarchived flags are returned. - &#x60;type&#x60; is a string allowing filtering to &#x60;temporary&#x60; or &#x60;permanent&#x60; flags. - &#x60;status&#x60; is a string allowing filtering to &#x60;new&#x60;, &#x60;inactive&#x60;, &#x60;active&#x60;, or &#x60;launched&#x60; flags in the specified environment. This filter also requires a &#x60;filterEnv&#x60; field to be set to a valid environment. For example: &#x60;filter&#x3D;status:active,filterEnv:production&#x60;. - &#x60;tags&#x60; is a + separated list of tags. It filters the list to members who have all of the tags in the list. - &#x60;hasExperiment&#x60; is a boolean with values of true or false and returns any flags that have an attached metric. - &#x60;hasDataExport&#x60; is a boolean with values of true or false and returns any flags that are currently exporting data in the specified environment. This includes flags that are exporting data via Experimentation. This filter also requires a &#x60;filterEnv&#x60; field to be set to a valid environment key. e.g. &#x60;filter&#x3D;hasExperiment:true,filterEnv:production&#x60; - &#x60;evaluated&#x60; is an object that contains a key of &#x60;after&#x60; and a value in Unix time in milliseconds. This returns all flags that have been evaluated since the time you specify in the environment provided. This filter also requires a &#x60;filterEnv&#x60; field to be set to a valid environment. For example: &#x60;filter&#x3D;evaluated:{\&quot;after\&quot;: 1590768455282},filterEnv:production&#x60;. - &#x60;filterEnv&#x60; is a string with the key of a valid environment. The filterEnv field is used for filters that are environment specific. If there are multiple environment specific filters you should only declare this parameter once. For example: &#x60;filter&#x3D;evaluated:{\&quot;after\&quot;: 1590768455282},filterEnv:production,status:active&#x60;.  An example filter is &#x60;query:abc,tags:foo+bar&#x60;. This matches flags with the string &#x60;abc&#x60; in their key or name, ignoring case, which also have the tags &#x60;foo&#x60; and &#x60;bar&#x60;.  By default, this returns all flags. You can page through the list with the &#x60;limit&#x60; parameter and by following the &#x60;first&#x60;, &#x60;prev&#x60;, &#x60;next&#x60;, and &#x60;last&#x60; links in the returned &#x60;_links&#x60; field. These links will not be present if the pages they refer to don&#39;t exist. For example, the &#x60;first&#x60; and &#x60;prev&#x60; links will be missing from the response on the first page. 
     * @param projKey The project key (required)
     * @param env Filter configurations by environment (optional)
     * @param tag Filter feature flags by tag (optional)
     * @param limit The number of feature flags to return. Defaults to -1, which returns all flags (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next limit items (optional)
     * @param query A string that matches against the flags&#39; keys and names. It is not case sensitive (optional)
     * @param archived A boolean to filter the list to archived flags. When this is absent, only unarchived flags will be returned (optional)
     * @param summary By default in API version &gt;&#x3D; 1, flags will _not_ include their list of prerequisites, targets or rules.  Set summary&#x3D;0 to include these fields for each flag returned (optional)
     * @param filter A comma-separated list of filters. Each filter is of the form field:value (optional)
     * @param sort A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order (optional)
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
    public FeatureFlags getFeatureFlags(String projKey, String env, String tag, Long limit, Long offset, String query, Boolean archived, Boolean summary, String filter, String sort) throws ApiException {
        ApiResponse<FeatureFlags> localVarResp = getFeatureFlagsWithHttpInfo(projKey, env, tag, limit, offset, query, archived, summary, filter, sort);
        return localVarResp.getData();
    }

    /**
     * List feature flags
     * Get a list of all features in the given project. By default, each feature includes configurations for each environment. You can filter environments with the env query parameter. For example, setting &#x60;env&#x3D;production&#x60; restricts the returned configurations to just your production environment. You can also filter feature flags by tag with the tag query parameter.  We support the following fields for filters:  - &#x60;query&#x60; is a string that matches against the flags&#39; keys and names. It is not case sensitive. - &#x60;archived&#x60; is a boolean to filter the list to archived flags. When this is absent, only unarchived flags are returned. - &#x60;type&#x60; is a string allowing filtering to &#x60;temporary&#x60; or &#x60;permanent&#x60; flags. - &#x60;status&#x60; is a string allowing filtering to &#x60;new&#x60;, &#x60;inactive&#x60;, &#x60;active&#x60;, or &#x60;launched&#x60; flags in the specified environment. This filter also requires a &#x60;filterEnv&#x60; field to be set to a valid environment. For example: &#x60;filter&#x3D;status:active,filterEnv:production&#x60;. - &#x60;tags&#x60; is a + separated list of tags. It filters the list to members who have all of the tags in the list. - &#x60;hasExperiment&#x60; is a boolean with values of true or false and returns any flags that have an attached metric. - &#x60;hasDataExport&#x60; is a boolean with values of true or false and returns any flags that are currently exporting data in the specified environment. This includes flags that are exporting data via Experimentation. This filter also requires a &#x60;filterEnv&#x60; field to be set to a valid environment key. e.g. &#x60;filter&#x3D;hasExperiment:true,filterEnv:production&#x60; - &#x60;evaluated&#x60; is an object that contains a key of &#x60;after&#x60; and a value in Unix time in milliseconds. This returns all flags that have been evaluated since the time you specify in the environment provided. This filter also requires a &#x60;filterEnv&#x60; field to be set to a valid environment. For example: &#x60;filter&#x3D;evaluated:{\&quot;after\&quot;: 1590768455282},filterEnv:production&#x60;. - &#x60;filterEnv&#x60; is a string with the key of a valid environment. The filterEnv field is used for filters that are environment specific. If there are multiple environment specific filters you should only declare this parameter once. For example: &#x60;filter&#x3D;evaluated:{\&quot;after\&quot;: 1590768455282},filterEnv:production,status:active&#x60;.  An example filter is &#x60;query:abc,tags:foo+bar&#x60;. This matches flags with the string &#x60;abc&#x60; in their key or name, ignoring case, which also have the tags &#x60;foo&#x60; and &#x60;bar&#x60;.  By default, this returns all flags. You can page through the list with the &#x60;limit&#x60; parameter and by following the &#x60;first&#x60;, &#x60;prev&#x60;, &#x60;next&#x60;, and &#x60;last&#x60; links in the returned &#x60;_links&#x60; field. These links will not be present if the pages they refer to don&#39;t exist. For example, the &#x60;first&#x60; and &#x60;prev&#x60; links will be missing from the response on the first page. 
     * @param projKey The project key (required)
     * @param env Filter configurations by environment (optional)
     * @param tag Filter feature flags by tag (optional)
     * @param limit The number of feature flags to return. Defaults to -1, which returns all flags (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next limit items (optional)
     * @param query A string that matches against the flags&#39; keys and names. It is not case sensitive (optional)
     * @param archived A boolean to filter the list to archived flags. When this is absent, only unarchived flags will be returned (optional)
     * @param summary By default in API version &gt;&#x3D; 1, flags will _not_ include their list of prerequisites, targets or rules.  Set summary&#x3D;0 to include these fields for each flag returned (optional)
     * @param filter A comma-separated list of filters. Each filter is of the form field:value (optional)
     * @param sort A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order (optional)
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
    public ApiResponse<FeatureFlags> getFeatureFlagsWithHttpInfo(String projKey, String env, String tag, Long limit, Long offset, String query, Boolean archived, Boolean summary, String filter, String sort) throws ApiException {
        okhttp3.Call localVarCall = getFeatureFlagsValidateBeforeCall(projKey, env, tag, limit, offset, query, archived, summary, filter, sort, null);
        Type localVarReturnType = new TypeToken<FeatureFlags>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List feature flags (asynchronously)
     * Get a list of all features in the given project. By default, each feature includes configurations for each environment. You can filter environments with the env query parameter. For example, setting &#x60;env&#x3D;production&#x60; restricts the returned configurations to just your production environment. You can also filter feature flags by tag with the tag query parameter.  We support the following fields for filters:  - &#x60;query&#x60; is a string that matches against the flags&#39; keys and names. It is not case sensitive. - &#x60;archived&#x60; is a boolean to filter the list to archived flags. When this is absent, only unarchived flags are returned. - &#x60;type&#x60; is a string allowing filtering to &#x60;temporary&#x60; or &#x60;permanent&#x60; flags. - &#x60;status&#x60; is a string allowing filtering to &#x60;new&#x60;, &#x60;inactive&#x60;, &#x60;active&#x60;, or &#x60;launched&#x60; flags in the specified environment. This filter also requires a &#x60;filterEnv&#x60; field to be set to a valid environment. For example: &#x60;filter&#x3D;status:active,filterEnv:production&#x60;. - &#x60;tags&#x60; is a + separated list of tags. It filters the list to members who have all of the tags in the list. - &#x60;hasExperiment&#x60; is a boolean with values of true or false and returns any flags that have an attached metric. - &#x60;hasDataExport&#x60; is a boolean with values of true or false and returns any flags that are currently exporting data in the specified environment. This includes flags that are exporting data via Experimentation. This filter also requires a &#x60;filterEnv&#x60; field to be set to a valid environment key. e.g. &#x60;filter&#x3D;hasExperiment:true,filterEnv:production&#x60; - &#x60;evaluated&#x60; is an object that contains a key of &#x60;after&#x60; and a value in Unix time in milliseconds. This returns all flags that have been evaluated since the time you specify in the environment provided. This filter also requires a &#x60;filterEnv&#x60; field to be set to a valid environment. For example: &#x60;filter&#x3D;evaluated:{\&quot;after\&quot;: 1590768455282},filterEnv:production&#x60;. - &#x60;filterEnv&#x60; is a string with the key of a valid environment. The filterEnv field is used for filters that are environment specific. If there are multiple environment specific filters you should only declare this parameter once. For example: &#x60;filter&#x3D;evaluated:{\&quot;after\&quot;: 1590768455282},filterEnv:production,status:active&#x60;.  An example filter is &#x60;query:abc,tags:foo+bar&#x60;. This matches flags with the string &#x60;abc&#x60; in their key or name, ignoring case, which also have the tags &#x60;foo&#x60; and &#x60;bar&#x60;.  By default, this returns all flags. You can page through the list with the &#x60;limit&#x60; parameter and by following the &#x60;first&#x60;, &#x60;prev&#x60;, &#x60;next&#x60;, and &#x60;last&#x60; links in the returned &#x60;_links&#x60; field. These links will not be present if the pages they refer to don&#39;t exist. For example, the &#x60;first&#x60; and &#x60;prev&#x60; links will be missing from the response on the first page. 
     * @param projKey The project key (required)
     * @param env Filter configurations by environment (optional)
     * @param tag Filter feature flags by tag (optional)
     * @param limit The number of feature flags to return. Defaults to -1, which returns all flags (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next limit items (optional)
     * @param query A string that matches against the flags&#39; keys and names. It is not case sensitive (optional)
     * @param archived A boolean to filter the list to archived flags. When this is absent, only unarchived flags will be returned (optional)
     * @param summary By default in API version &gt;&#x3D; 1, flags will _not_ include their list of prerequisites, targets or rules.  Set summary&#x3D;0 to include these fields for each flag returned (optional)
     * @param filter A comma-separated list of filters. Each filter is of the form field:value (optional)
     * @param sort A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order (optional)
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
    public okhttp3.Call getFeatureFlagsAsync(String projKey, String env, String tag, Long limit, Long offset, String query, Boolean archived, Boolean summary, String filter, String sort, final ApiCallback<FeatureFlags> _callback) throws ApiException {

        okhttp3.Call localVarCall = getFeatureFlagsValidateBeforeCall(projKey, env, tag, limit, offset, query, archived, summary, filter, sort, _callback);
        Type localVarReturnType = new TypeToken<FeatureFlags>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for patchExpiringUserTargets
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param flagKey The feature flag key. (required)
     * @param patchWithComment  (required)
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
    public okhttp3.Call patchExpiringUserTargetsCall(String projKey, String envKey, String flagKey, PatchWithComment patchWithComment, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = patchWithComment;

        // create path and map variables
        String localVarPath = "/api/v2/flags/{projKey}/{flagKey}/expiring-user-targets/{envKey}"
            .replaceAll("\\{" + "projKey" + "\\}", localVarApiClient.escapeString(projKey.toString()))
            .replaceAll("\\{" + "envKey" + "\\}", localVarApiClient.escapeString(envKey.toString()))
            .replaceAll("\\{" + "flagKey" + "\\}", localVarApiClient.escapeString(flagKey.toString()));

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
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(localVarPath, "PATCH", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call patchExpiringUserTargetsValidateBeforeCall(String projKey, String envKey, String flagKey, PatchWithComment patchWithComment, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projKey' is set
        if (projKey == null) {
            throw new ApiException("Missing the required parameter 'projKey' when calling patchExpiringUserTargets(Async)");
        }
        
        // verify the required parameter 'envKey' is set
        if (envKey == null) {
            throw new ApiException("Missing the required parameter 'envKey' when calling patchExpiringUserTargets(Async)");
        }
        
        // verify the required parameter 'flagKey' is set
        if (flagKey == null) {
            throw new ApiException("Missing the required parameter 'flagKey' when calling patchExpiringUserTargets(Async)");
        }
        
        // verify the required parameter 'patchWithComment' is set
        if (patchWithComment == null) {
            throw new ApiException("Missing the required parameter 'patchWithComment' when calling patchExpiringUserTargets(Async)");
        }
        

        okhttp3.Call localVarCall = patchExpiringUserTargetsCall(projKey, envKey, flagKey, patchWithComment, _callback);
        return localVarCall;

    }

    /**
     * Update expiring user targets on feature flag
     * Update the list of user targets on a feature flag that are scheduled for removal.
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param flagKey The feature flag key. (required)
     * @param patchWithComment  (required)
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
    public ExpiringUserTargetPatchResponse patchExpiringUserTargets(String projKey, String envKey, String flagKey, PatchWithComment patchWithComment) throws ApiException {
        ApiResponse<ExpiringUserTargetPatchResponse> localVarResp = patchExpiringUserTargetsWithHttpInfo(projKey, envKey, flagKey, patchWithComment);
        return localVarResp.getData();
    }

    /**
     * Update expiring user targets on feature flag
     * Update the list of user targets on a feature flag that are scheduled for removal.
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param flagKey The feature flag key. (required)
     * @param patchWithComment  (required)
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
    public ApiResponse<ExpiringUserTargetPatchResponse> patchExpiringUserTargetsWithHttpInfo(String projKey, String envKey, String flagKey, PatchWithComment patchWithComment) throws ApiException {
        okhttp3.Call localVarCall = patchExpiringUserTargetsValidateBeforeCall(projKey, envKey, flagKey, patchWithComment, null);
        Type localVarReturnType = new TypeToken<ExpiringUserTargetPatchResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update expiring user targets on feature flag (asynchronously)
     * Update the list of user targets on a feature flag that are scheduled for removal.
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param flagKey The feature flag key. (required)
     * @param patchWithComment  (required)
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
    public okhttp3.Call patchExpiringUserTargetsAsync(String projKey, String envKey, String flagKey, PatchWithComment patchWithComment, final ApiCallback<ExpiringUserTargetPatchResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = patchExpiringUserTargetsValidateBeforeCall(projKey, envKey, flagKey, patchWithComment, _callback);
        Type localVarReturnType = new TypeToken<ExpiringUserTargetPatchResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for patchFeatureFlag
     * @param projKey The project key. (required)
     * @param key The feature flag&#39;s key. The key identifies the flag in your code. (required)
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
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchFeatureFlagCall(String projKey, String key, PatchWithComment patchWithComment, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = patchWithComment;

        // create path and map variables
        String localVarPath = "/api/v2/flags/{projKey}/{key}"
            .replaceAll("\\{" + "projKey" + "\\}", localVarApiClient.escapeString(projKey.toString()))
            .replaceAll("\\{" + "key" + "\\}", localVarApiClient.escapeString(key.toString()));

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
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(localVarPath, "PATCH", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call patchFeatureFlagValidateBeforeCall(String projKey, String key, PatchWithComment patchWithComment, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projKey' is set
        if (projKey == null) {
            throw new ApiException("Missing the required parameter 'projKey' when calling patchFeatureFlag(Async)");
        }
        
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException("Missing the required parameter 'key' when calling patchFeatureFlag(Async)");
        }
        
        // verify the required parameter 'patchWithComment' is set
        if (patchWithComment == null) {
            throw new ApiException("Missing the required parameter 'patchWithComment' when calling patchFeatureFlag(Async)");
        }
        

        okhttp3.Call localVarCall = patchFeatureFlagCall(projKey, key, patchWithComment, _callback);
        return localVarCall;

    }

    /**
     * Update feature flag
     * Perform a partial update to a feature flag.  ## Using JSON Patches on a feature flag  When using the update feature flag endpoint to add individual users to a specific variation, there are two different patch documents, depending on whether users are already being individually targeted for the variation.  If a flag variation already has users individually targeted, the path for the JSON Patch operation is:  &#x60;&#x60;&#x60;json {   \&quot;op\&quot;: \&quot;add\&quot;,   \&quot;path\&quot;: \&quot;/environments/devint/targets/0/values/-\&quot;,   \&quot;value\&quot;: \&quot;TestClient10\&quot; } &#x60;&#x60;&#x60;  If a flag variation does not already have users individually targeted, the path for the JSON Patch operation is:  &#x60;&#x60;&#x60;json [   {     \&quot;op\&quot;: \&quot;add\&quot;,     \&quot;path\&quot;: \&quot;/environments/devint/targets/-\&quot;,     \&quot;value\&quot;: { \&quot;variation\&quot;: 0, \&quot;values\&quot;: [\&quot;TestClient10\&quot;] }   } ] &#x60;&#x60;&#x60;  ## Using semantic patches on a feature flag  To use a [semantic patch](/#section/Updates/Updates-via-semantic-patches) on a feature flag resource, you must include a header in the request. If you call a semantic patch resource without this header, you receive a &#x60;400&#x60; response as your semantic patch will be interpreted as a JSON patch.  Use this header:  &#x60;&#x60;&#x60; Content-Type: application/json; domain-model&#x3D;launchdarkly.semanticpatch &#x60;&#x60;&#x60;  The body of a semantic patch request takes the following three properties:  1. comment &#x60;string&#x60;: (Optional) A description of the update. 1. environmentKey &#x60;string&#x60;: (Required) The key of the LaunchDarkly environment. 1. instructions &#x60;array&#x60;: (Required) The action or list of actions to be performed by the update. Each update action in the list must be an object/hash table with a &#x60;kind&#x60; property, although depending on the action, other properties may be necessary. Read below for more information on the specific supported semantic patch instructions.  If any instruction in the patch encounters an error, the error will be returned and the flag will not be changed. In general, instructions will silently do nothing if the flag is already in the state requested by the patch instruction. For example, &#x60;removeUserTargets&#x60; does nothing when the targets have already been removed). They will generally error if a parameter refers to something that does not exist, like a variation ID that doesn&#39;t correspond to a variation on the flag or a rule ID that doesn&#39;t belong to a rule on the flag. Other specific error conditions are noted in the instruction descriptions.  ### Instructions  #### &#x60;turnFlagOn&#x60;  Sets the flag&#39;s targeting state to on.  #### &#x60;turnFlagOff&#x60;  Sets the flag&#39;s targeting state to off.  #### &#x60;addUserTargets&#x60;  Adds the user keys in &#x60;values&#x60; to the individual user targets for the variation specified by &#x60;variationId&#x60;. Returns an error if this causes the same user key to be targeted in multiple variations.  ##### Parameters  - &#x60;values&#x60;: list of user keys - &#x60;variationId&#x60;: ID of a variation on the flag  #### &#x60;removeUserTargets&#x60;  Removes the user keys in &#x60;values&#x60; to the individual user targets for the variation specified by &#x60;variationId&#x60;. Does nothing if the user keys are not targeted.  ##### Parameters  - &#x60;values&#x60;: list of user keys - &#x60;variationId&#x60;: ID of a variation on the flag  #### &#x60;replaceUserTargets&#x60;  Completely replaces the existing set of user targeting. All variations must be provided. Example:  &#x60;&#x60;&#x60;json {   \&quot;kind\&quot;: \&quot;replaceUserTargets\&quot;,   \&quot;targets\&quot;: [     {       \&quot;variationId\&quot;: \&quot;variation-1\&quot;,       \&quot;values\&quot;: [\&quot;blah\&quot;, \&quot;foo\&quot;, \&quot;bar\&quot;]     },     {       \&quot;variationId\&quot;: \&quot;variation-2\&quot;,       \&quot;values\&quot;: [\&quot;abc\&quot;, \&quot;def\&quot;]     }   ] } &#x60;&#x60;&#x60;  ##### Parameters  - &#x60;targets&#x60;: a list of user targeting  #### &#x60;clearUserTargets&#x60;  Removes all individual user targets from the variation specified by &#x60;variationId&#x60;  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation on the flag  #### &#x60;addPrerequisite&#x60;  Adds the flag indicated by &#x60;key&#x60; with variation &#x60;variationId&#x60; as a prerequisite to the flag.  ##### Parameters  - &#x60;key&#x60;: flag key of another flag - &#x60;variationId&#x60;: ID of a variation of the flag with key &#x60;key&#x60;  #### &#x60;removePrerequisite&#x60;  Removes the prerequisite indicated by &#x60;key&#x60;. Does nothing if this prerequisite does not exist.  ##### Parameters  - &#x60;key&#x60;: flag key of an existing prerequisite  #### &#x60;updatePrerequisite&#x60;  Changes the prerequisite with flag key &#x60;key&#x60; to the variation indicated by &#x60;variationId&#x60;. Returns an error if this prerequisite does not exist.  ##### Parameters  - &#x60;key&#x60;: flag key of an existing prerequisite - &#x60;variationId&#x60;: ID of a variation of the flag with key &#x60;key&#x60;  #### &#x60;replacePrerequisites&#x60;  Completely replaces the existing set of prerequisites for a given flag. Example:  &#x60;&#x60;&#x60;json {   \&quot;kind\&quot;: \&quot;replacePrerequisites\&quot;,   \&quot;prerequisites\&quot;: [     {       \&quot;key\&quot;: \&quot;flag-key\&quot;,       \&quot;variationId\&quot;: \&quot;variation-1\&quot;     },     {       \&quot;key\&quot;: \&quot;another-flag\&quot;,       \&quot;variationId\&quot;: \&quot;variation-2\&quot;     }   ] } &#x60;&#x60;&#x60;  ##### Parameters  - &#x60;prerequisites&#x60;: a list of prerequisites  #### &#x60;addRule&#x60;  Adds a new rule to the flag with the given &#x60;clauses&#x60; which serves the variation indicated by &#x60;variationId&#x60; or the percent rollout indicated by &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60;. If &#x60;beforeRuleId&#x60; is set, the rule will be added in the list of rules before the indicated rule. Otherwise, the rule will be added to the end of the list.  ##### Parameters  - &#x60;clauses&#x60;: Array of clauses (see &#x60;addClauses&#x60;) - &#x60;beforeRuleId&#x60;: Optional ID of a rule in the flag - &#x60;variationId&#x60;: ID of a variation of the flag - &#x60;rolloutWeights&#x60;: Map of variationId to weight in thousandths of a percent (0-100000) - &#x60;rolloutBucketBy&#x60;: Optional user attribute  #### &#x60;removeRule&#x60;  Removes the targeting rule specified by &#x60;ruleId&#x60;. Does nothing if the rule does not exist.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag  #### &#x60;replaceRules&#x60;  Completely replaces the existing rules for a given flag. Example:  &#x60;&#x60;&#x60;json {   \&quot;kind\&quot;: \&quot;replaceRules\&quot;,   \&quot;rules\&quot;: [     {       \&quot;variationId\&quot;: \&quot;variation-1\&quot;,       \&quot;description\&quot;: \&quot;myRule\&quot;,       \&quot;clauses\&quot;: [         {           \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,           \&quot;op\&quot;: \&quot;segmentMatch\&quot;,           \&quot;values\&quot;: [\&quot;test\&quot;]         }       ],       \&quot;trackEvents\&quot;: true     }   ] } &#x60;&#x60;&#x60;  ##### Parameters  - &#x60;rules&#x60;: a list of rules  #### &#x60;addClauses&#x60;  Adds the given clauses to the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag - &#x60;clauses&#x60;: Array of clause objects, with &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), and &#x60;values&#x60; (array of strings, numbers, or dates) properties.  #### &#x60;removeClauses&#x60;  Removes the clauses specified by &#x60;clauseIds&#x60; from the rule indicated by &#x60;ruleId&#x60;.  #### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag - &#x60;clauseIds&#x60;: Array of IDs of clauses in the rule  #### &#x60;updateClause&#x60;  Replaces the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60; with &#x60;clause&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag - &#x60;clauseId&#x60;: ID of a clause in that rule - &#x60;clause&#x60;: Clause object  #### &#x60;addValuesToClause&#x60;  Adds &#x60;values&#x60; to the values of the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag - &#x60;clauseId&#x60;: ID of a clause in that rule - &#x60;values&#x60;: Array of strings  #### &#x60;removeValuesFromClause&#x60;  Removes &#x60;values&#x60; from the values of the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60;.  ##### Parameters  &#x60;ruleId&#x60;: ID of a rule in the flag &#x60;clauseId&#x60;: ID of a clause in that rule &#x60;values&#x60;: Array of strings  #### &#x60;reorderRules&#x60;  Rearranges the rules to match the order given in &#x60;ruleIds&#x60;. Will return an error if &#x60;ruleIds&#x60; does not match the current set of rules on the flag.  ##### Parameters  - &#x60;ruleIds&#x60;: Array of IDs of all rules in the flag  #### &#x60;updateRuleVariationOrRollout&#x60;  Updates what the rule indicated by &#x60;ruleId&#x60; serves if its clauses evaluate to true. Can either be a fixed variation indicated by &#x60;variationId&#x60; or a percent rollout indicated by &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag - &#x60;variationId&#x60;: ID of a variation of the flag   or - &#x60;rolloutWeights&#x60;: Map of variationId to weight in thousandths of a percent (0-100000) - &#x60;rolloutBucketBy&#x60;: Optional user attribute  #### &#x60;updateFallthroughVariationOrRollout&#x60;  Updates the flag&#39;s fallthrough, which is served if none of the targeting rules match. Can either be a fixed variation indicated by &#x60;variationId&#x60; or a percent rollout indicated by &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60;.  ##### Parameters  &#x60;variationId&#x60;: ID of a variation of the flag or &#x60;rolloutWeights&#x60;: Map of variationId to weight in thousandths of a percent (0-100000) &#x60;rolloutBucketBy&#x60;: Optional user attribute  #### &#x60;updateOffVariation&#x60;  Updates the variation served when the flag&#39;s targeting is off to the variation indicated by &#x60;variationId&#x60;.  ##### Parameters  &#x60;variationId&#x60;: ID of a variation of the flag  ### Example  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;production\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;turnFlagOn\&quot;     },     {       \&quot;kind\&quot;: \&quot;turnFlagOff\&quot;     },     {       \&quot;kind\&quot;: \&quot;addUserTargets\&quot;,       \&quot;variationId\&quot;: \&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;,       \&quot;values\&quot;: [\&quot;userId\&quot;, \&quot;userId2\&quot;]     },     {       \&quot;kind\&quot;: \&quot;removeUserTargets\&quot;,       \&quot;variationId\&quot;: \&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;,       \&quot;values\&quot;: [\&quot;userId3\&quot;, \&quot;userId4\&quot;]     },     {       \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,       \&quot;rolloutWeights\&quot;: {         \&quot;variationId\&quot;: 50000,         \&quot;variationId2\&quot;: 50000       },       \&quot;rolloutBucketBy\&quot;: null     },     {       \&quot;kind\&quot;: \&quot;addRule\&quot;,       \&quot;clauses\&quot;: [         {           \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,           \&quot;negate\&quot;: false,           \&quot;values\&quot;: [\&quot;test-segment\&quot;]         }       ],       \&quot;variationId\&quot;: null,       \&quot;rolloutWeights\&quot;: {         \&quot;variationId\&quot;: 50000,         \&quot;variationId2\&quot;: 50000       },       \&quot;rolloutBucketBy\&quot;: \&quot;key\&quot;     },     {       \&quot;kind\&quot;: \&quot;removeRule\&quot;,       \&quot;ruleId\&quot;: \&quot;99f12464-a429-40fc-86cc-b27612188955\&quot;     },     {       \&quot;kind\&quot;: \&quot;reorderRules\&quot;,       \&quot;ruleIds\&quot;: [\&quot;2f72974e-de68-4243-8dd3-739582147a1f\&quot;, \&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;]     },     {       \&quot;kind\&quot;: \&quot;addClauses\&quot;,       \&quot;ruleId\&quot;: \&quot;1134\&quot;,       \&quot;clauses\&quot;: [         {           \&quot;attribute\&quot;: \&quot;email\&quot;,           \&quot;op\&quot;: \&quot;in\&quot;,           \&quot;negate\&quot;: false,           \&quot;values\&quot;: [\&quot;test@test.com\&quot;]         }       ]     },     {       \&quot;kind\&quot;: \&quot;removeClauses\&quot;,       \&quot;ruleId\&quot;: \&quot;1242529\&quot;,       \&quot;clauseIds\&quot;: [\&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;]     },     {       \&quot;kind\&quot;: \&quot;updateClause\&quot;,       \&quot;ruleId\&quot;: \&quot;2f72974e-de68-4243-8dd3-739582147a1f\&quot;,       \&quot;clauseId\&quot;: \&quot;309845\&quot;,       \&quot;clause\&quot;: {         \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,         \&quot;negate\&quot;: false,         \&quot;values\&quot;: [\&quot;test-segment\&quot;]       }     },     {       \&quot;kind\&quot;: \&quot;updateRuleVariationOrRollout\&quot;,       \&quot;ruleId\&quot;: \&quot;2342\&quot;,       \&quot;rolloutWeights\&quot;: null,       \&quot;rolloutBucketBy\&quot;: null     },     {       \&quot;kind\&quot;: \&quot;updateOffVariation\&quot;,       \&quot;variationId\&quot;: \&quot;3242453\&quot;     },     {       \&quot;kind\&quot;: \&quot;addPrerequisite\&quot;,       \&quot;variationId\&quot;: \&quot;234235\&quot;,       \&quot;key\&quot;: \&quot;flagKey2\&quot;     },     {       \&quot;kind\&quot;: \&quot;updatePrerequisite\&quot;,       \&quot;variationId\&quot;: \&quot;234235\&quot;,       \&quot;key\&quot;: \&quot;flagKey2\&quot;     },     {       \&quot;kind\&quot;: \&quot;removePrerequisite\&quot;,       \&quot;key\&quot;: \&quot;flagKey\&quot;     }   ] } &#x60;&#x60;&#x60;  ## Using JSON patches on a feature flag  If you do not include the header described above, you can use [JSON patch](/#section/Updates/Updates-via-JSON-Patch). 
     * @param projKey The project key. (required)
     * @param key The feature flag&#39;s key. The key identifies the flag in your code. (required)
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
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public FeatureFlag patchFeatureFlag(String projKey, String key, PatchWithComment patchWithComment) throws ApiException {
        ApiResponse<FeatureFlag> localVarResp = patchFeatureFlagWithHttpInfo(projKey, key, patchWithComment);
        return localVarResp.getData();
    }

    /**
     * Update feature flag
     * Perform a partial update to a feature flag.  ## Using JSON Patches on a feature flag  When using the update feature flag endpoint to add individual users to a specific variation, there are two different patch documents, depending on whether users are already being individually targeted for the variation.  If a flag variation already has users individually targeted, the path for the JSON Patch operation is:  &#x60;&#x60;&#x60;json {   \&quot;op\&quot;: \&quot;add\&quot;,   \&quot;path\&quot;: \&quot;/environments/devint/targets/0/values/-\&quot;,   \&quot;value\&quot;: \&quot;TestClient10\&quot; } &#x60;&#x60;&#x60;  If a flag variation does not already have users individually targeted, the path for the JSON Patch operation is:  &#x60;&#x60;&#x60;json [   {     \&quot;op\&quot;: \&quot;add\&quot;,     \&quot;path\&quot;: \&quot;/environments/devint/targets/-\&quot;,     \&quot;value\&quot;: { \&quot;variation\&quot;: 0, \&quot;values\&quot;: [\&quot;TestClient10\&quot;] }   } ] &#x60;&#x60;&#x60;  ## Using semantic patches on a feature flag  To use a [semantic patch](/#section/Updates/Updates-via-semantic-patches) on a feature flag resource, you must include a header in the request. If you call a semantic patch resource without this header, you receive a &#x60;400&#x60; response as your semantic patch will be interpreted as a JSON patch.  Use this header:  &#x60;&#x60;&#x60; Content-Type: application/json; domain-model&#x3D;launchdarkly.semanticpatch &#x60;&#x60;&#x60;  The body of a semantic patch request takes the following three properties:  1. comment &#x60;string&#x60;: (Optional) A description of the update. 1. environmentKey &#x60;string&#x60;: (Required) The key of the LaunchDarkly environment. 1. instructions &#x60;array&#x60;: (Required) The action or list of actions to be performed by the update. Each update action in the list must be an object/hash table with a &#x60;kind&#x60; property, although depending on the action, other properties may be necessary. Read below for more information on the specific supported semantic patch instructions.  If any instruction in the patch encounters an error, the error will be returned and the flag will not be changed. In general, instructions will silently do nothing if the flag is already in the state requested by the patch instruction. For example, &#x60;removeUserTargets&#x60; does nothing when the targets have already been removed). They will generally error if a parameter refers to something that does not exist, like a variation ID that doesn&#39;t correspond to a variation on the flag or a rule ID that doesn&#39;t belong to a rule on the flag. Other specific error conditions are noted in the instruction descriptions.  ### Instructions  #### &#x60;turnFlagOn&#x60;  Sets the flag&#39;s targeting state to on.  #### &#x60;turnFlagOff&#x60;  Sets the flag&#39;s targeting state to off.  #### &#x60;addUserTargets&#x60;  Adds the user keys in &#x60;values&#x60; to the individual user targets for the variation specified by &#x60;variationId&#x60;. Returns an error if this causes the same user key to be targeted in multiple variations.  ##### Parameters  - &#x60;values&#x60;: list of user keys - &#x60;variationId&#x60;: ID of a variation on the flag  #### &#x60;removeUserTargets&#x60;  Removes the user keys in &#x60;values&#x60; to the individual user targets for the variation specified by &#x60;variationId&#x60;. Does nothing if the user keys are not targeted.  ##### Parameters  - &#x60;values&#x60;: list of user keys - &#x60;variationId&#x60;: ID of a variation on the flag  #### &#x60;replaceUserTargets&#x60;  Completely replaces the existing set of user targeting. All variations must be provided. Example:  &#x60;&#x60;&#x60;json {   \&quot;kind\&quot;: \&quot;replaceUserTargets\&quot;,   \&quot;targets\&quot;: [     {       \&quot;variationId\&quot;: \&quot;variation-1\&quot;,       \&quot;values\&quot;: [\&quot;blah\&quot;, \&quot;foo\&quot;, \&quot;bar\&quot;]     },     {       \&quot;variationId\&quot;: \&quot;variation-2\&quot;,       \&quot;values\&quot;: [\&quot;abc\&quot;, \&quot;def\&quot;]     }   ] } &#x60;&#x60;&#x60;  ##### Parameters  - &#x60;targets&#x60;: a list of user targeting  #### &#x60;clearUserTargets&#x60;  Removes all individual user targets from the variation specified by &#x60;variationId&#x60;  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation on the flag  #### &#x60;addPrerequisite&#x60;  Adds the flag indicated by &#x60;key&#x60; with variation &#x60;variationId&#x60; as a prerequisite to the flag.  ##### Parameters  - &#x60;key&#x60;: flag key of another flag - &#x60;variationId&#x60;: ID of a variation of the flag with key &#x60;key&#x60;  #### &#x60;removePrerequisite&#x60;  Removes the prerequisite indicated by &#x60;key&#x60;. Does nothing if this prerequisite does not exist.  ##### Parameters  - &#x60;key&#x60;: flag key of an existing prerequisite  #### &#x60;updatePrerequisite&#x60;  Changes the prerequisite with flag key &#x60;key&#x60; to the variation indicated by &#x60;variationId&#x60;. Returns an error if this prerequisite does not exist.  ##### Parameters  - &#x60;key&#x60;: flag key of an existing prerequisite - &#x60;variationId&#x60;: ID of a variation of the flag with key &#x60;key&#x60;  #### &#x60;replacePrerequisites&#x60;  Completely replaces the existing set of prerequisites for a given flag. Example:  &#x60;&#x60;&#x60;json {   \&quot;kind\&quot;: \&quot;replacePrerequisites\&quot;,   \&quot;prerequisites\&quot;: [     {       \&quot;key\&quot;: \&quot;flag-key\&quot;,       \&quot;variationId\&quot;: \&quot;variation-1\&quot;     },     {       \&quot;key\&quot;: \&quot;another-flag\&quot;,       \&quot;variationId\&quot;: \&quot;variation-2\&quot;     }   ] } &#x60;&#x60;&#x60;  ##### Parameters  - &#x60;prerequisites&#x60;: a list of prerequisites  #### &#x60;addRule&#x60;  Adds a new rule to the flag with the given &#x60;clauses&#x60; which serves the variation indicated by &#x60;variationId&#x60; or the percent rollout indicated by &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60;. If &#x60;beforeRuleId&#x60; is set, the rule will be added in the list of rules before the indicated rule. Otherwise, the rule will be added to the end of the list.  ##### Parameters  - &#x60;clauses&#x60;: Array of clauses (see &#x60;addClauses&#x60;) - &#x60;beforeRuleId&#x60;: Optional ID of a rule in the flag - &#x60;variationId&#x60;: ID of a variation of the flag - &#x60;rolloutWeights&#x60;: Map of variationId to weight in thousandths of a percent (0-100000) - &#x60;rolloutBucketBy&#x60;: Optional user attribute  #### &#x60;removeRule&#x60;  Removes the targeting rule specified by &#x60;ruleId&#x60;. Does nothing if the rule does not exist.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag  #### &#x60;replaceRules&#x60;  Completely replaces the existing rules for a given flag. Example:  &#x60;&#x60;&#x60;json {   \&quot;kind\&quot;: \&quot;replaceRules\&quot;,   \&quot;rules\&quot;: [     {       \&quot;variationId\&quot;: \&quot;variation-1\&quot;,       \&quot;description\&quot;: \&quot;myRule\&quot;,       \&quot;clauses\&quot;: [         {           \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,           \&quot;op\&quot;: \&quot;segmentMatch\&quot;,           \&quot;values\&quot;: [\&quot;test\&quot;]         }       ],       \&quot;trackEvents\&quot;: true     }   ] } &#x60;&#x60;&#x60;  ##### Parameters  - &#x60;rules&#x60;: a list of rules  #### &#x60;addClauses&#x60;  Adds the given clauses to the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag - &#x60;clauses&#x60;: Array of clause objects, with &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), and &#x60;values&#x60; (array of strings, numbers, or dates) properties.  #### &#x60;removeClauses&#x60;  Removes the clauses specified by &#x60;clauseIds&#x60; from the rule indicated by &#x60;ruleId&#x60;.  #### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag - &#x60;clauseIds&#x60;: Array of IDs of clauses in the rule  #### &#x60;updateClause&#x60;  Replaces the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60; with &#x60;clause&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag - &#x60;clauseId&#x60;: ID of a clause in that rule - &#x60;clause&#x60;: Clause object  #### &#x60;addValuesToClause&#x60;  Adds &#x60;values&#x60; to the values of the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag - &#x60;clauseId&#x60;: ID of a clause in that rule - &#x60;values&#x60;: Array of strings  #### &#x60;removeValuesFromClause&#x60;  Removes &#x60;values&#x60; from the values of the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60;.  ##### Parameters  &#x60;ruleId&#x60;: ID of a rule in the flag &#x60;clauseId&#x60;: ID of a clause in that rule &#x60;values&#x60;: Array of strings  #### &#x60;reorderRules&#x60;  Rearranges the rules to match the order given in &#x60;ruleIds&#x60;. Will return an error if &#x60;ruleIds&#x60; does not match the current set of rules on the flag.  ##### Parameters  - &#x60;ruleIds&#x60;: Array of IDs of all rules in the flag  #### &#x60;updateRuleVariationOrRollout&#x60;  Updates what the rule indicated by &#x60;ruleId&#x60; serves if its clauses evaluate to true. Can either be a fixed variation indicated by &#x60;variationId&#x60; or a percent rollout indicated by &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag - &#x60;variationId&#x60;: ID of a variation of the flag   or - &#x60;rolloutWeights&#x60;: Map of variationId to weight in thousandths of a percent (0-100000) - &#x60;rolloutBucketBy&#x60;: Optional user attribute  #### &#x60;updateFallthroughVariationOrRollout&#x60;  Updates the flag&#39;s fallthrough, which is served if none of the targeting rules match. Can either be a fixed variation indicated by &#x60;variationId&#x60; or a percent rollout indicated by &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60;.  ##### Parameters  &#x60;variationId&#x60;: ID of a variation of the flag or &#x60;rolloutWeights&#x60;: Map of variationId to weight in thousandths of a percent (0-100000) &#x60;rolloutBucketBy&#x60;: Optional user attribute  #### &#x60;updateOffVariation&#x60;  Updates the variation served when the flag&#39;s targeting is off to the variation indicated by &#x60;variationId&#x60;.  ##### Parameters  &#x60;variationId&#x60;: ID of a variation of the flag  ### Example  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;production\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;turnFlagOn\&quot;     },     {       \&quot;kind\&quot;: \&quot;turnFlagOff\&quot;     },     {       \&quot;kind\&quot;: \&quot;addUserTargets\&quot;,       \&quot;variationId\&quot;: \&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;,       \&quot;values\&quot;: [\&quot;userId\&quot;, \&quot;userId2\&quot;]     },     {       \&quot;kind\&quot;: \&quot;removeUserTargets\&quot;,       \&quot;variationId\&quot;: \&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;,       \&quot;values\&quot;: [\&quot;userId3\&quot;, \&quot;userId4\&quot;]     },     {       \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,       \&quot;rolloutWeights\&quot;: {         \&quot;variationId\&quot;: 50000,         \&quot;variationId2\&quot;: 50000       },       \&quot;rolloutBucketBy\&quot;: null     },     {       \&quot;kind\&quot;: \&quot;addRule\&quot;,       \&quot;clauses\&quot;: [         {           \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,           \&quot;negate\&quot;: false,           \&quot;values\&quot;: [\&quot;test-segment\&quot;]         }       ],       \&quot;variationId\&quot;: null,       \&quot;rolloutWeights\&quot;: {         \&quot;variationId\&quot;: 50000,         \&quot;variationId2\&quot;: 50000       },       \&quot;rolloutBucketBy\&quot;: \&quot;key\&quot;     },     {       \&quot;kind\&quot;: \&quot;removeRule\&quot;,       \&quot;ruleId\&quot;: \&quot;99f12464-a429-40fc-86cc-b27612188955\&quot;     },     {       \&quot;kind\&quot;: \&quot;reorderRules\&quot;,       \&quot;ruleIds\&quot;: [\&quot;2f72974e-de68-4243-8dd3-739582147a1f\&quot;, \&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;]     },     {       \&quot;kind\&quot;: \&quot;addClauses\&quot;,       \&quot;ruleId\&quot;: \&quot;1134\&quot;,       \&quot;clauses\&quot;: [         {           \&quot;attribute\&quot;: \&quot;email\&quot;,           \&quot;op\&quot;: \&quot;in\&quot;,           \&quot;negate\&quot;: false,           \&quot;values\&quot;: [\&quot;test@test.com\&quot;]         }       ]     },     {       \&quot;kind\&quot;: \&quot;removeClauses\&quot;,       \&quot;ruleId\&quot;: \&quot;1242529\&quot;,       \&quot;clauseIds\&quot;: [\&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;]     },     {       \&quot;kind\&quot;: \&quot;updateClause\&quot;,       \&quot;ruleId\&quot;: \&quot;2f72974e-de68-4243-8dd3-739582147a1f\&quot;,       \&quot;clauseId\&quot;: \&quot;309845\&quot;,       \&quot;clause\&quot;: {         \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,         \&quot;negate\&quot;: false,         \&quot;values\&quot;: [\&quot;test-segment\&quot;]       }     },     {       \&quot;kind\&quot;: \&quot;updateRuleVariationOrRollout\&quot;,       \&quot;ruleId\&quot;: \&quot;2342\&quot;,       \&quot;rolloutWeights\&quot;: null,       \&quot;rolloutBucketBy\&quot;: null     },     {       \&quot;kind\&quot;: \&quot;updateOffVariation\&quot;,       \&quot;variationId\&quot;: \&quot;3242453\&quot;     },     {       \&quot;kind\&quot;: \&quot;addPrerequisite\&quot;,       \&quot;variationId\&quot;: \&quot;234235\&quot;,       \&quot;key\&quot;: \&quot;flagKey2\&quot;     },     {       \&quot;kind\&quot;: \&quot;updatePrerequisite\&quot;,       \&quot;variationId\&quot;: \&quot;234235\&quot;,       \&quot;key\&quot;: \&quot;flagKey2\&quot;     },     {       \&quot;kind\&quot;: \&quot;removePrerequisite\&quot;,       \&quot;key\&quot;: \&quot;flagKey\&quot;     }   ] } &#x60;&#x60;&#x60;  ## Using JSON patches on a feature flag  If you do not include the header described above, you can use [JSON patch](/#section/Updates/Updates-via-JSON-Patch). 
     * @param projKey The project key. (required)
     * @param key The feature flag&#39;s key. The key identifies the flag in your code. (required)
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
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<FeatureFlag> patchFeatureFlagWithHttpInfo(String projKey, String key, PatchWithComment patchWithComment) throws ApiException {
        okhttp3.Call localVarCall = patchFeatureFlagValidateBeforeCall(projKey, key, patchWithComment, null);
        Type localVarReturnType = new TypeToken<FeatureFlag>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update feature flag (asynchronously)
     * Perform a partial update to a feature flag.  ## Using JSON Patches on a feature flag  When using the update feature flag endpoint to add individual users to a specific variation, there are two different patch documents, depending on whether users are already being individually targeted for the variation.  If a flag variation already has users individually targeted, the path for the JSON Patch operation is:  &#x60;&#x60;&#x60;json {   \&quot;op\&quot;: \&quot;add\&quot;,   \&quot;path\&quot;: \&quot;/environments/devint/targets/0/values/-\&quot;,   \&quot;value\&quot;: \&quot;TestClient10\&quot; } &#x60;&#x60;&#x60;  If a flag variation does not already have users individually targeted, the path for the JSON Patch operation is:  &#x60;&#x60;&#x60;json [   {     \&quot;op\&quot;: \&quot;add\&quot;,     \&quot;path\&quot;: \&quot;/environments/devint/targets/-\&quot;,     \&quot;value\&quot;: { \&quot;variation\&quot;: 0, \&quot;values\&quot;: [\&quot;TestClient10\&quot;] }   } ] &#x60;&#x60;&#x60;  ## Using semantic patches on a feature flag  To use a [semantic patch](/#section/Updates/Updates-via-semantic-patches) on a feature flag resource, you must include a header in the request. If you call a semantic patch resource without this header, you receive a &#x60;400&#x60; response as your semantic patch will be interpreted as a JSON patch.  Use this header:  &#x60;&#x60;&#x60; Content-Type: application/json; domain-model&#x3D;launchdarkly.semanticpatch &#x60;&#x60;&#x60;  The body of a semantic patch request takes the following three properties:  1. comment &#x60;string&#x60;: (Optional) A description of the update. 1. environmentKey &#x60;string&#x60;: (Required) The key of the LaunchDarkly environment. 1. instructions &#x60;array&#x60;: (Required) The action or list of actions to be performed by the update. Each update action in the list must be an object/hash table with a &#x60;kind&#x60; property, although depending on the action, other properties may be necessary. Read below for more information on the specific supported semantic patch instructions.  If any instruction in the patch encounters an error, the error will be returned and the flag will not be changed. In general, instructions will silently do nothing if the flag is already in the state requested by the patch instruction. For example, &#x60;removeUserTargets&#x60; does nothing when the targets have already been removed). They will generally error if a parameter refers to something that does not exist, like a variation ID that doesn&#39;t correspond to a variation on the flag or a rule ID that doesn&#39;t belong to a rule on the flag. Other specific error conditions are noted in the instruction descriptions.  ### Instructions  #### &#x60;turnFlagOn&#x60;  Sets the flag&#39;s targeting state to on.  #### &#x60;turnFlagOff&#x60;  Sets the flag&#39;s targeting state to off.  #### &#x60;addUserTargets&#x60;  Adds the user keys in &#x60;values&#x60; to the individual user targets for the variation specified by &#x60;variationId&#x60;. Returns an error if this causes the same user key to be targeted in multiple variations.  ##### Parameters  - &#x60;values&#x60;: list of user keys - &#x60;variationId&#x60;: ID of a variation on the flag  #### &#x60;removeUserTargets&#x60;  Removes the user keys in &#x60;values&#x60; to the individual user targets for the variation specified by &#x60;variationId&#x60;. Does nothing if the user keys are not targeted.  ##### Parameters  - &#x60;values&#x60;: list of user keys - &#x60;variationId&#x60;: ID of a variation on the flag  #### &#x60;replaceUserTargets&#x60;  Completely replaces the existing set of user targeting. All variations must be provided. Example:  &#x60;&#x60;&#x60;json {   \&quot;kind\&quot;: \&quot;replaceUserTargets\&quot;,   \&quot;targets\&quot;: [     {       \&quot;variationId\&quot;: \&quot;variation-1\&quot;,       \&quot;values\&quot;: [\&quot;blah\&quot;, \&quot;foo\&quot;, \&quot;bar\&quot;]     },     {       \&quot;variationId\&quot;: \&quot;variation-2\&quot;,       \&quot;values\&quot;: [\&quot;abc\&quot;, \&quot;def\&quot;]     }   ] } &#x60;&#x60;&#x60;  ##### Parameters  - &#x60;targets&#x60;: a list of user targeting  #### &#x60;clearUserTargets&#x60;  Removes all individual user targets from the variation specified by &#x60;variationId&#x60;  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation on the flag  #### &#x60;addPrerequisite&#x60;  Adds the flag indicated by &#x60;key&#x60; with variation &#x60;variationId&#x60; as a prerequisite to the flag.  ##### Parameters  - &#x60;key&#x60;: flag key of another flag - &#x60;variationId&#x60;: ID of a variation of the flag with key &#x60;key&#x60;  #### &#x60;removePrerequisite&#x60;  Removes the prerequisite indicated by &#x60;key&#x60;. Does nothing if this prerequisite does not exist.  ##### Parameters  - &#x60;key&#x60;: flag key of an existing prerequisite  #### &#x60;updatePrerequisite&#x60;  Changes the prerequisite with flag key &#x60;key&#x60; to the variation indicated by &#x60;variationId&#x60;. Returns an error if this prerequisite does not exist.  ##### Parameters  - &#x60;key&#x60;: flag key of an existing prerequisite - &#x60;variationId&#x60;: ID of a variation of the flag with key &#x60;key&#x60;  #### &#x60;replacePrerequisites&#x60;  Completely replaces the existing set of prerequisites for a given flag. Example:  &#x60;&#x60;&#x60;json {   \&quot;kind\&quot;: \&quot;replacePrerequisites\&quot;,   \&quot;prerequisites\&quot;: [     {       \&quot;key\&quot;: \&quot;flag-key\&quot;,       \&quot;variationId\&quot;: \&quot;variation-1\&quot;     },     {       \&quot;key\&quot;: \&quot;another-flag\&quot;,       \&quot;variationId\&quot;: \&quot;variation-2\&quot;     }   ] } &#x60;&#x60;&#x60;  ##### Parameters  - &#x60;prerequisites&#x60;: a list of prerequisites  #### &#x60;addRule&#x60;  Adds a new rule to the flag with the given &#x60;clauses&#x60; which serves the variation indicated by &#x60;variationId&#x60; or the percent rollout indicated by &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60;. If &#x60;beforeRuleId&#x60; is set, the rule will be added in the list of rules before the indicated rule. Otherwise, the rule will be added to the end of the list.  ##### Parameters  - &#x60;clauses&#x60;: Array of clauses (see &#x60;addClauses&#x60;) - &#x60;beforeRuleId&#x60;: Optional ID of a rule in the flag - &#x60;variationId&#x60;: ID of a variation of the flag - &#x60;rolloutWeights&#x60;: Map of variationId to weight in thousandths of a percent (0-100000) - &#x60;rolloutBucketBy&#x60;: Optional user attribute  #### &#x60;removeRule&#x60;  Removes the targeting rule specified by &#x60;ruleId&#x60;. Does nothing if the rule does not exist.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag  #### &#x60;replaceRules&#x60;  Completely replaces the existing rules for a given flag. Example:  &#x60;&#x60;&#x60;json {   \&quot;kind\&quot;: \&quot;replaceRules\&quot;,   \&quot;rules\&quot;: [     {       \&quot;variationId\&quot;: \&quot;variation-1\&quot;,       \&quot;description\&quot;: \&quot;myRule\&quot;,       \&quot;clauses\&quot;: [         {           \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,           \&quot;op\&quot;: \&quot;segmentMatch\&quot;,           \&quot;values\&quot;: [\&quot;test\&quot;]         }       ],       \&quot;trackEvents\&quot;: true     }   ] } &#x60;&#x60;&#x60;  ##### Parameters  - &#x60;rules&#x60;: a list of rules  #### &#x60;addClauses&#x60;  Adds the given clauses to the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag - &#x60;clauses&#x60;: Array of clause objects, with &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), and &#x60;values&#x60; (array of strings, numbers, or dates) properties.  #### &#x60;removeClauses&#x60;  Removes the clauses specified by &#x60;clauseIds&#x60; from the rule indicated by &#x60;ruleId&#x60;.  #### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag - &#x60;clauseIds&#x60;: Array of IDs of clauses in the rule  #### &#x60;updateClause&#x60;  Replaces the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60; with &#x60;clause&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag - &#x60;clauseId&#x60;: ID of a clause in that rule - &#x60;clause&#x60;: Clause object  #### &#x60;addValuesToClause&#x60;  Adds &#x60;values&#x60; to the values of the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag - &#x60;clauseId&#x60;: ID of a clause in that rule - &#x60;values&#x60;: Array of strings  #### &#x60;removeValuesFromClause&#x60;  Removes &#x60;values&#x60; from the values of the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60;.  ##### Parameters  &#x60;ruleId&#x60;: ID of a rule in the flag &#x60;clauseId&#x60;: ID of a clause in that rule &#x60;values&#x60;: Array of strings  #### &#x60;reorderRules&#x60;  Rearranges the rules to match the order given in &#x60;ruleIds&#x60;. Will return an error if &#x60;ruleIds&#x60; does not match the current set of rules on the flag.  ##### Parameters  - &#x60;ruleIds&#x60;: Array of IDs of all rules in the flag  #### &#x60;updateRuleVariationOrRollout&#x60;  Updates what the rule indicated by &#x60;ruleId&#x60; serves if its clauses evaluate to true. Can either be a fixed variation indicated by &#x60;variationId&#x60; or a percent rollout indicated by &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag - &#x60;variationId&#x60;: ID of a variation of the flag   or - &#x60;rolloutWeights&#x60;: Map of variationId to weight in thousandths of a percent (0-100000) - &#x60;rolloutBucketBy&#x60;: Optional user attribute  #### &#x60;updateFallthroughVariationOrRollout&#x60;  Updates the flag&#39;s fallthrough, which is served if none of the targeting rules match. Can either be a fixed variation indicated by &#x60;variationId&#x60; or a percent rollout indicated by &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60;.  ##### Parameters  &#x60;variationId&#x60;: ID of a variation of the flag or &#x60;rolloutWeights&#x60;: Map of variationId to weight in thousandths of a percent (0-100000) &#x60;rolloutBucketBy&#x60;: Optional user attribute  #### &#x60;updateOffVariation&#x60;  Updates the variation served when the flag&#39;s targeting is off to the variation indicated by &#x60;variationId&#x60;.  ##### Parameters  &#x60;variationId&#x60;: ID of a variation of the flag  ### Example  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;production\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;turnFlagOn\&quot;     },     {       \&quot;kind\&quot;: \&quot;turnFlagOff\&quot;     },     {       \&quot;kind\&quot;: \&quot;addUserTargets\&quot;,       \&quot;variationId\&quot;: \&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;,       \&quot;values\&quot;: [\&quot;userId\&quot;, \&quot;userId2\&quot;]     },     {       \&quot;kind\&quot;: \&quot;removeUserTargets\&quot;,       \&quot;variationId\&quot;: \&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;,       \&quot;values\&quot;: [\&quot;userId3\&quot;, \&quot;userId4\&quot;]     },     {       \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,       \&quot;rolloutWeights\&quot;: {         \&quot;variationId\&quot;: 50000,         \&quot;variationId2\&quot;: 50000       },       \&quot;rolloutBucketBy\&quot;: null     },     {       \&quot;kind\&quot;: \&quot;addRule\&quot;,       \&quot;clauses\&quot;: [         {           \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,           \&quot;negate\&quot;: false,           \&quot;values\&quot;: [\&quot;test-segment\&quot;]         }       ],       \&quot;variationId\&quot;: null,       \&quot;rolloutWeights\&quot;: {         \&quot;variationId\&quot;: 50000,         \&quot;variationId2\&quot;: 50000       },       \&quot;rolloutBucketBy\&quot;: \&quot;key\&quot;     },     {       \&quot;kind\&quot;: \&quot;removeRule\&quot;,       \&quot;ruleId\&quot;: \&quot;99f12464-a429-40fc-86cc-b27612188955\&quot;     },     {       \&quot;kind\&quot;: \&quot;reorderRules\&quot;,       \&quot;ruleIds\&quot;: [\&quot;2f72974e-de68-4243-8dd3-739582147a1f\&quot;, \&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;]     },     {       \&quot;kind\&quot;: \&quot;addClauses\&quot;,       \&quot;ruleId\&quot;: \&quot;1134\&quot;,       \&quot;clauses\&quot;: [         {           \&quot;attribute\&quot;: \&quot;email\&quot;,           \&quot;op\&quot;: \&quot;in\&quot;,           \&quot;negate\&quot;: false,           \&quot;values\&quot;: [\&quot;test@test.com\&quot;]         }       ]     },     {       \&quot;kind\&quot;: \&quot;removeClauses\&quot;,       \&quot;ruleId\&quot;: \&quot;1242529\&quot;,       \&quot;clauseIds\&quot;: [\&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;]     },     {       \&quot;kind\&quot;: \&quot;updateClause\&quot;,       \&quot;ruleId\&quot;: \&quot;2f72974e-de68-4243-8dd3-739582147a1f\&quot;,       \&quot;clauseId\&quot;: \&quot;309845\&quot;,       \&quot;clause\&quot;: {         \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,         \&quot;negate\&quot;: false,         \&quot;values\&quot;: [\&quot;test-segment\&quot;]       }     },     {       \&quot;kind\&quot;: \&quot;updateRuleVariationOrRollout\&quot;,       \&quot;ruleId\&quot;: \&quot;2342\&quot;,       \&quot;rolloutWeights\&quot;: null,       \&quot;rolloutBucketBy\&quot;: null     },     {       \&quot;kind\&quot;: \&quot;updateOffVariation\&quot;,       \&quot;variationId\&quot;: \&quot;3242453\&quot;     },     {       \&quot;kind\&quot;: \&quot;addPrerequisite\&quot;,       \&quot;variationId\&quot;: \&quot;234235\&quot;,       \&quot;key\&quot;: \&quot;flagKey2\&quot;     },     {       \&quot;kind\&quot;: \&quot;updatePrerequisite\&quot;,       \&quot;variationId\&quot;: \&quot;234235\&quot;,       \&quot;key\&quot;: \&quot;flagKey2\&quot;     },     {       \&quot;kind\&quot;: \&quot;removePrerequisite\&quot;,       \&quot;key\&quot;: \&quot;flagKey\&quot;     }   ] } &#x60;&#x60;&#x60;  ## Using JSON patches on a feature flag  If you do not include the header described above, you can use [JSON patch](/#section/Updates/Updates-via-JSON-Patch). 
     * @param projKey The project key. (required)
     * @param key The feature flag&#39;s key. The key identifies the flag in your code. (required)
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
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchFeatureFlagAsync(String projKey, String key, PatchWithComment patchWithComment, final ApiCallback<FeatureFlag> _callback) throws ApiException {

        okhttp3.Call localVarCall = patchFeatureFlagValidateBeforeCall(projKey, key, patchWithComment, _callback);
        Type localVarReturnType = new TypeToken<FeatureFlag>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for postFeatureFlag
     * @param projKey The project key. (required)
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
    public okhttp3.Call postFeatureFlagCall(String projKey, FeatureFlagBody featureFlagBody, String clone, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = featureFlagBody;

        // create path and map variables
        String localVarPath = "/api/v2/flags/{projKey}"
            .replaceAll("\\{" + "projKey" + "\\}", localVarApiClient.escapeString(projKey.toString()));

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
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call postFeatureFlagValidateBeforeCall(String projKey, FeatureFlagBody featureFlagBody, String clone, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projKey' is set
        if (projKey == null) {
            throw new ApiException("Missing the required parameter 'projKey' when calling postFeatureFlag(Async)");
        }
        
        // verify the required parameter 'featureFlagBody' is set
        if (featureFlagBody == null) {
            throw new ApiException("Missing the required parameter 'featureFlagBody' when calling postFeatureFlag(Async)");
        }
        

        okhttp3.Call localVarCall = postFeatureFlagCall(projKey, featureFlagBody, clone, _callback);
        return localVarCall;

    }

    /**
     * Create a feature flag
     * Create a feature flag with the given name, key, and variations
     * @param projKey The project key. (required)
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
    public FeatureFlag postFeatureFlag(String projKey, FeatureFlagBody featureFlagBody, String clone) throws ApiException {
        ApiResponse<FeatureFlag> localVarResp = postFeatureFlagWithHttpInfo(projKey, featureFlagBody, clone);
        return localVarResp.getData();
    }

    /**
     * Create a feature flag
     * Create a feature flag with the given name, key, and variations
     * @param projKey The project key. (required)
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
    public ApiResponse<FeatureFlag> postFeatureFlagWithHttpInfo(String projKey, FeatureFlagBody featureFlagBody, String clone) throws ApiException {
        okhttp3.Call localVarCall = postFeatureFlagValidateBeforeCall(projKey, featureFlagBody, clone, null);
        Type localVarReturnType = new TypeToken<FeatureFlag>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create a feature flag (asynchronously)
     * Create a feature flag with the given name, key, and variations
     * @param projKey The project key. (required)
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
    public okhttp3.Call postFeatureFlagAsync(String projKey, FeatureFlagBody featureFlagBody, String clone, final ApiCallback<FeatureFlag> _callback) throws ApiException {

        okhttp3.Call localVarCall = postFeatureFlagValidateBeforeCall(projKey, featureFlagBody, clone, _callback);
        Type localVarReturnType = new TypeToken<FeatureFlag>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
