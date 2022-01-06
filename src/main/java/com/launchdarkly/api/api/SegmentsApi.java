/*
 * LaunchDarkly REST API
 * # Overview  ## Authentication  All REST API resources are authenticated with either [personal or service access tokens](https://docs.launchdarkly.com/home/account-security/api-access-tokens), or session cookies. Other authentication mechanisms are not supported. You can manage personal access tokens on your [Account settings](https://app.launchdarkly.com/settings/tokens) page.  LaunchDarkly also has SDK keys, mobile keys, and client-side IDs that are used by our server-side SDKs, mobile SDKs, and client-side SDKs, respectively. **These keys cannot be used to access our REST API**. These keys are environment-specific, and can only perform read-only operations (fetching feature flag settings).  | Auth mechanism                                                                                  | Allowed resources                                                                                     | Use cases                                          | | ----------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------- | -------------------------------------------------- | | [Personal access tokens](https://docs.launchdarkly.com/home/account-security/api-access-tokens) | Can be customized on a per-token basis                                                                | Building scripts, custom integrations, data export | | SDK keys                                                                                        | Can only access read-only SDK-specific resources and the firehose, restricted to a single environment | Server-side SDKs, Firehose API                     | | Mobile keys                                                                                     | Can only access read-only mobile SDK-specific resources, restricted to a single environment           | Mobile SDKs                                        | | Client-side ID                                                                                  | Single environment, only flags marked available to client-side                                        | Client-side JavaScript                             |  > #### Keep your access tokens and SDK keys private > > Access tokens should _never_ be exposed in untrusted contexts. Never put an access token in client-side JavaScript, or embed it in a mobile application. LaunchDarkly has special mobile keys that you can embed in mobile apps. If you accidentally expose an access token or SDK key, you can reset it from your [Account Settings](https://app.launchdarkly.com/settings#/tokens) page. > > The client-side ID is safe to embed in untrusted contexts. It's designed for use in client-side JavaScript.  ### Via request header  The preferred way to authenticate with the API is by adding an `Authorization` header containing your access token to your requests. The value of the `Authorization` header must be your access token.  Manage personal access tokens from the [Account Settings](https://app.launchdarkly.com/settings/tokens) page.  ### Via session cookie  For testing purposes, you can make API calls directly from your web browser. If you're logged in to the application, the API will use your existing session to authenticate calls.  If you have a [role](https://docs.launchdarkly.com/home/team/built-in-roles) other than Admin, or have a [custom role](https://docs.launchdarkly.com/home/team/custom-roles) defined, you may not have permission to perform some API calls. You will receive a `401` response code in that case.  > ### Modifying the Origin header causes an error > > LaunchDarkly validates that the Origin header for any API request authenticated by a session cookie matches the expected Origin header. The expected Origin header is `https://app.launchdarkly.com`. > > If the Origin header does not match what's expected, LaunchDarkly returns an error. This error can prevent the LaunchDarkly app from working correctly. > > Any browser extension that intentionally changes the Origin header can cause this problem. For example, the `Allow-Control-Allow-Origin: *` Chrome extension changes the Origin header to `http://evil.com` and causes the app to fail. > > To prevent this error, do not modify your Origin header. > > LaunchDarkly does not require origin matching when authenticating with an access token, so this issue does not affect normal API usage.  ## Representations  All resources expect and return JSON response bodies. Error responses will also send a JSON body. Read [Errors](#section/Errors) for a more detailed description of the error format used by the API.  In practice this means that you always get a response with a `Content-Type` header set to `application/json`.  In addition, request bodies for `PUT`, `POST`, `REPORT` and `PATCH` requests must be encoded as JSON with a `Content-Type` header set to `application/json`.  ### Summary and detailed representations  When you fetch a list of resources, the response includes only the most important attributes of each resource. This is a _summary representation_ of the resource. When you fetch an individual resource (for example, a single feature flag), you receive a _detailed representation_ containing all of the attributes of the resource.  The best way to find a detailed representation is to follow links. Every summary representation includes a link to its detailed representation.  ### Links and addressability  The best way to navigate the API is by following links. These are attributes in representations that link to other resources. The API always uses the same format for links:  - Links to other resources within the API are encapsulated in a `_links` object. - If the resource has a corresponding link to HTML content on the site, it is stored in a special `_site` link.  Each link has two attributes: an href (the URL) and a type (the content type). For example, a feature resource might return the following:  ```json {   \"_links\": {     \"parent\": {       \"href\": \"/api/features\",       \"type\": \"application/json\"     },     \"self\": {       \"href\": \"/api/features/sort.order\",       \"type\": \"application/json\"     }   },   \"_site\": {     \"href\": \"/features/sort.order\",     \"type\": \"text/html\"   } } ```  From this, you can navigate to the parent collection of features by following the `parent` link, or navigate to the site page for the feature by following the `_site` link.  Collections are always represented as a JSON object with an `items` attribute containing an array of representations. Like all other representations, collections have `_links` defined at the top level.  Paginated collections include `first`, `last`, `next`, and `prev` links containing a URL with the respective set of elements in the collection.  ## Updates  Resources that accept partial updates use the `PATCH` verb, and support the [JSON Patch](https://datatracker.ietf.org/doc/html/rfc6902) format. Some resources also support the [JSON Merge Patch](https://datatracker.ietf.org/doc/html/rfc7386) format. In addition, some resources support optional comments that can be submitted with updates. Comments appear in outgoing webhooks, the audit log, and other integrations.  ### Updates via JSON Patch  [JSON Patch](https://datatracker.ietf.org/doc/html/rfc6902) is a way to specify the modifications to perform on a resource. For example, in this feature flag representation:  ```json {     \"name\": \"New recommendations engine\",     \"key\": \"engine.enable\",     \"description\": \"This is the description\",     ... } ```  You can change the feature flag's description with the following patch document:  ```json [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"This is the new description\" }] ```  JSON Patch documents are always arrays. You can specify multiple modifications to perform in a single request. You can also test that certain preconditions are met before applying the patch:  ```json [   { \"op\": \"test\", \"path\": \"/version\", \"value\": 10 },   { \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" } ] ```  The above patch request tests whether the feature flag's `version` is `10`, and if so, changes the feature flag's description.  Attributes that aren't editable, like a resource's `_links`, have names that start with an underscore.  ### Updates via JSON Merge Patch  The API also supports the [JSON Merge Patch](https://datatracker.ietf.org/doc/html/rfc7386) format, as well as the [Update feature flag](/tag/Feature-flags#operation/patchFeatureFlag) resource.  JSON Merge Patch is less expressive than JSON Patch but in many cases, it is simpler to construct a merge patch document. For example, you can change a feature flag's description with the following merge patch document:  ```json {   \"description\": \"New flag description\" } ```  ### Updates with comments  You can submit optional comments with `PATCH` changes. The [Update feature flag](/tag/Feature-flags#operation/patchFeatureFlag) resource supports comments.  To submit a comment along with a JSON Patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"patch\": [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" }] } ```  To submit a comment along with a JSON Merge Patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"merge\": { \"description\": \"New flag description\" } } ```  ### Updates via semantic patches  The API also supports the Semantic patch format. A semantic `PATCH` is a way to specify the modifications to perform on a resource as a set of executable instructions.  JSON Patch uses paths and a limited set of operations to describe how to transform the current state of the resource into a new state. Semantic patch allows you to be explicit about intent using precise, custom instructions. In many cases, semantic patch instructions can also be defined independently of the current state of the resource. This can be useful when defining a change that may be applied at a future date.  For example, in this feature flag configuration in environment Production:  ```json {     \"name\": \"Alternate sort order\",     \"kind\": \"boolean\",     \"key\": \"sort.order\",    ...     \"environments\": {         \"production\": {             \"on\": true,             \"archived\": false,             \"salt\": \"c29ydC5vcmRlcg==\",             \"sel\": \"8de1085cb7354b0ab41c0e778376dfd3\",             \"lastModified\": 1469131558260,             \"version\": 81,             \"targets\": [                 {                     \"values\": [                         \"Gerhard.Little@yahoo.com\"                     ],                     \"variation\": 0                 },                 {                     \"values\": [                         \"1461797806429-33-861961230\",                         \"438580d8-02ee-418d-9eec-0085cab2bdf0\"                     ],                     \"variation\": 1                 }             ],             \"rules\": [],             \"fallthrough\": {                 \"variation\": 0             },             \"offVariation\": 1,             \"prerequisites\": [],             \"_site\": {                 \"href\": \"/default/production/features/sort.order\",                 \"type\": \"text/html\"             }        }     } } ```  You can add a date you want a user to be removed from the feature flag's user targets. For example, “remove user 1461797806429-33-861961230 from the user target for variation 0 on the Alternate sort order flag in the production environment on Wed Jul 08 2020 at 15:27:41 pm”. This is done using the following:  ```json {   \"comment\": \"update expiring user targets\",   \"instructions\": [     {       \"kind\": \"removeExpireUserTargetDate\",       \"userKey\": \"userKey\",       \"variationId\": \"978d53f9-7fe3-4a63-992d-97bcb4535dc8\"     },     {       \"kind\": \"updateExpireUserTargetDate\",       \"userKey\": \"userKey2\",       \"variationId\": \"978d53f9-7fe3-4a63-992d-97bcb4535dc8\",       \"value\": 1587582000000     },     {       \"kind\": \"addExpireUserTargetDate\",       \"userKey\": \"userKey3\",       \"variationId\": \"978d53f9-7fe3-4a63-992d-97bcb4535dc8\",       \"value\": 1594247266386     }   ] } ```  Here is another example. In this feature flag configuration:  ```json {   \"name\": \"New recommendations engine\",   \"key\": \"engine.enable\",   \"environments\": {     \"test\": {       \"on\": true     }   } } ```  You can change the feature flag's description with the following patch document as a set of executable instructions. For example, “add user X to targets for variation Y and remove user A from targets for variation B for test flag”:  ```json {   \"comment\": \"\",   \"instructions\": [     {       \"kind\": \"removeUserTargets\",       \"values\": [\"438580d8-02ee-418d-9eec-0085cab2bdf0\"],       \"variationId\": \"852cb784-54ff-46b9-8c35-5498d2e4f270\"     },     {       \"kind\": \"addUserTargets\",       \"values\": [\"438580d8-02ee-418d-9eec-0085cab2bdf0\"],       \"variationId\": \"1bb18465-33b6-49aa-a3bd-eeb6650b33ad\"     }   ] } ```  > ### Supported semantic patch API endpoints > > - [Update feature flag](/tag/Feature-flags#operation/patchFeatureFlag) > - [Update expiring user targets on feature flag](/tag/Feature-flags#operation/patchExpiringUserTargets) > - [Update expiring user target for flags](/tag/User-settings#operation/patchExpiringFlagsForUser) > - [Update expiring user targets on segment](/tag/Segments#operation/patchExpiringUserTargetsForSegment)  ## Errors  The API always returns errors in a common format. Here's an example:  ```json {   \"code\": \"invalid_request\",   \"message\": \"A feature with that key already exists\",   \"id\": \"30ce6058-87da-11e4-b116-123b93f75cba\" } ```  The general class of error is indicated by the `code`. The `message` is a human-readable explanation of what went wrong. The `id` is a unique identifier. Use it when you're working with LaunchDarkly support to debug a problem with a specific API call.  ### HTTP Status - Error Response Codes  | Code | Definition        | Desc.                                                                                       | Possible Solution                                                | | ---- | ----------------- | ------------------------------------------------------------------------------------------- | ---------------------------------------------------------------- | | 400  | Bad Request       | A request that fails may return this HTTP response code.                                    | Ensure JSON syntax in request body is correct.                   | | 401  | Unauthorized      | User doesn't have permission to an API call.                                                | Ensure your SDK key is good.                                     | | 403  | Forbidden         | User does not have permission for operation.                                                | Ensure that the user or access token has proper permissions set. | | 409  | Conflict          | The API request could not be completed because it conflicted with a concurrent API request. | Retry your request.                                              | | 429  | Too many requests | See [Rate limiting](/#section/Rate-limiting).                                               | Wait and try again later.                                        |  ## CORS  The LaunchDarkly API supports Cross Origin Resource Sharing (CORS) for AJAX requests from any origin. If an `Origin` header is given in a request, it will be echoed as an explicitly allowed origin. Otherwise, a wildcard is returned: `Access-Control-Allow-Origin: *`. For more information on CORS, see the [CORS W3C Recommendation](http://www.w3.org/TR/cors). Example CORS headers might look like:  ```http Access-Control-Allow-Headers: Accept, Content-Type, Content-Length, Accept-Encoding, Authorization Access-Control-Allow-Methods: OPTIONS, GET, DELETE, PATCH Access-Control-Allow-Origin: * Access-Control-Max-Age: 300 ```  You can make authenticated CORS calls just as you would make same-origin calls, using either [token or session-based authentication](#section/Authentication). If you’re using session auth, you should set the `withCredentials` property for your `xhr` request to `true`. You should never expose your access tokens to untrusted users.  ## Rate limiting  We use several rate limiting strategies to ensure the availability of our APIs. Rate-limited calls to our APIs will return a `429` status code. Calls to our APIs will include headers indicating the current rate limit status. The specific headers returned depend on the API route being called. The limits differ based on the route, authentication mechanism, and other factors. Routes that are not rate limited may not contain any of the headers described below.  > ### Rate limiting and SDKs > > LaunchDarkly SDKs are never rate limited and do not use the API endpoints defined here. LaunchDarkly uses a different set of approaches, including streaming/server-sent events and a global CDN, to ensure availability to the routes used by LaunchDarkly SDKs. > > The client-side ID is safe to embed in untrusted contexts. It's designed for use in client-side JavaScript.  ### Global rate limits  Authenticated requests are subject to a global limit. This is the maximum number of calls that can be made to the API per ten seconds. All personal access tokens on the account share this limit, so exceeding the limit with one access token will impact other tokens. Calls that are subject to global rate limits will return the headers below:  | Header name                    | Description                                                                      | | ------------------------------ | -------------------------------------------------------------------------------- | | `X-Ratelimit-Global-Remaining` | The maximum number of requests the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`            | The time at which the current rate limit window resets in epoch milliseconds.    |  We do not publicly document the specific number of calls that can be made globally. This limit may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limit.  ### Route-level rate limits  Some authenticated routes have custom rate limits. These also reset every ten seconds. Any access tokens hitting the same route share this limit, so exceeding the limit with one access token may impact other tokens. Calls that are subject to route-level rate limits will return the headers below:  | Header name                   | Description                                                                                           | | ----------------------------- | ----------------------------------------------------------------------------------------------------- | | `X-Ratelimit-Route-Remaining` | The maximum number of requests to the current route the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`           | The time at which the current rate limit window resets in epoch milliseconds.                         |  A _route_ represents a specific URL pattern and verb. For example, the [Delete environment](/tag/Environments#operation/deleteEnvironment) endpoint is considered a single route, and each call to delete an environment counts against your route-level rate limit for that route.  We do not publicly document the specific number of calls that can be made to each endpoint per ten seconds. These limits may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limits.  ### IP-based rate limiting  We also employ IP-based rate limiting on some API routes. If you hit an IP-based rate limit, your API response will include a `Retry-After` header indicating how long to wait before re-trying the call. Clients must wait at least `Retry-After` seconds before making additional calls to our API, and should employ jitter and backoff strategies to avoid triggering rate limits again.  ## OpenAPI (Swagger)  We have a [complete OpenAPI (Swagger) specification](https://app.launchdarkly.com/api/v2/openapi.json) for our API.  You can use this specification to generate client libraries to interact with our REST API in your language of choice.  This specification is supported by several API-based tools such as Postman and Insomnia. In many cases, you can directly import our specification to ease use in navigating the APIs in the tooling.  ## Client libraries  We auto-generate multiple client libraries based on our OpenAPI specification. To learn more, visit [GitHub](https://github.com/search?q=topic%3Alaunchdarkly-api+org%3Alaunchdarkly&type=Repositories).  ## Method Overriding  Some firewalls and HTTP clients restrict the use of verbs other than `GET` and `POST`. In those environments, our API endpoints that use `PUT`, `PATCH`, and `DELETE` verbs will be inaccessible.  To avoid this issue, our API supports the `X-HTTP-Method-Override` header, allowing clients to \"tunnel\" `PUT`, `PATCH`, and `DELETE` requests via a `POST` request.  For example, if you wish to call one of our `PATCH` resources via a `POST` request, you can include `X-HTTP-Method-Override:PATCH` as a header.  ## Beta resources  We sometimes release new API resources in **beta** status before we release them with general availability.  Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  We try to promote resources into general availability as quickly as possible. This happens after sufficient testing and when we're satisfied that we no longer need to make backwards-incompatible changes.  We mark beta resources with a \"Beta\" callout in our documentation, pictured below:  > ### This feature is in beta > > To use this feature, pass in a header including the `LD-API-Version` key with value set to `beta`. Use this header with each call. To learn more, read [Beta resources](/#section/Beta-resources).  ### Using beta resources  To use a beta resource, you must include a header in the request. If you call a beta resource without this header, you'll receive a `403` response.  Use this header:  ``` LD-API-Version: beta ```  ## Versioning  We try hard to keep our REST API backwards compatible, but we occasionally have to make backwards-incompatible changes in the process of shipping new features. These breaking changes can cause unexpected behavior if you don't prepare for them accordingly.  Updates to our REST API include support for the latest features in LaunchDarkly. We also release a new version of our REST API every time we make a breaking change. We provide simultaneous support for multiple API versions so you can migrate from your current API version to a new version at your own pace.  ### Setting the API version per request  You can set the API version on a specific request by sending an `LD-API-Version` header, as shown in the example below:  ``` LD-API-Version: 20191212 ```  The header value is the version number of the API version you'd like to request. The number for each version corresponds to the date the version was released. In the example above the version `20191212` corresponds to December 12, 2019.  ### Setting the API version per access token  When creating an access token, you must specify a specific version of the API to use. This ensures that integrations using this token cannot be broken by version changes.  Tokens created before versioning was released have their version set to `20160426` (the version of the API that existed before versioning) so that they continue working the same way they did before versioning.  If you would like to upgrade your integration to use a new API version, you can explicitly set the header described above.  > ### Best practice: Set the header for every client or integration > > We recommend that you set the API version header explicitly in any client or integration you build. > > Only rely on the access token API version during manual testing. 
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


import com.launchdarkly.api.model.BigSegmentTarget;
import com.launchdarkly.api.model.ExpiringUserTargetGetResponse;
import com.launchdarkly.api.model.ExpiringUserTargetPatchResponse;
import com.launchdarkly.api.model.ForbiddenErrorRep;
import com.launchdarkly.api.model.InvalidRequestErrorRep;
import com.launchdarkly.api.model.NotFoundErrorRep;
import com.launchdarkly.api.model.PatchSegmentRequest;
import com.launchdarkly.api.model.PatchWithComment;
import com.launchdarkly.api.model.RateLimitedErrorRep;
import com.launchdarkly.api.model.SegmentBody;
import com.launchdarkly.api.model.SegmentUserState;
import com.launchdarkly.api.model.StatusConflictErrorRep;
import com.launchdarkly.api.model.UnauthorizedErrorRep;
import com.launchdarkly.api.model.UserSegment;
import com.launchdarkly.api.model.UserSegments;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SegmentsApi {
    private ApiClient localVarApiClient;

    public SegmentsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public SegmentsApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    /**
     * Build call for deleteSegment
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param key The user segment key. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteSegmentCall(String projKey, String envKey, String key, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/segments/{projKey}/{envKey}/{key}"
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
        return localVarApiClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteSegmentValidateBeforeCall(String projKey, String envKey, String key, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projKey' is set
        if (projKey == null) {
            throw new ApiException("Missing the required parameter 'projKey' when calling deleteSegment(Async)");
        }
        
        // verify the required parameter 'envKey' is set
        if (envKey == null) {
            throw new ApiException("Missing the required parameter 'envKey' when calling deleteSegment(Async)");
        }
        
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException("Missing the required parameter 'key' when calling deleteSegment(Async)");
        }
        

        okhttp3.Call localVarCall = deleteSegmentCall(projKey, envKey, key, _callback);
        return localVarCall;

    }

    /**
     * Delete segment
     * Delete a user segment.
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param key The user segment key. (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public void deleteSegment(String projKey, String envKey, String key) throws ApiException {
        deleteSegmentWithHttpInfo(projKey, envKey, key);
    }

    /**
     * Delete segment
     * Delete a user segment.
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param key The user segment key. (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteSegmentWithHttpInfo(String projKey, String envKey, String key) throws ApiException {
        okhttp3.Call localVarCall = deleteSegmentValidateBeforeCall(projKey, envKey, key, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete segment (asynchronously)
     * Delete a user segment.
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param key The user segment key. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteSegmentAsync(String projKey, String envKey, String key, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteSegmentValidateBeforeCall(projKey, envKey, key, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getExpiringUserTargetsForSegment
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param segmentKey The segment key. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getExpiringUserTargetsForSegmentCall(String projKey, String envKey, String segmentKey, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/segments/{projKey}/{segmentKey}/expiring-user-targets/{envKey}"
            .replaceAll("\\{" + "projKey" + "\\}", localVarApiClient.escapeString(projKey.toString()))
            .replaceAll("\\{" + "envKey" + "\\}", localVarApiClient.escapeString(envKey.toString()))
            .replaceAll("\\{" + "segmentKey" + "\\}", localVarApiClient.escapeString(segmentKey.toString()));

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
    private okhttp3.Call getExpiringUserTargetsForSegmentValidateBeforeCall(String projKey, String envKey, String segmentKey, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projKey' is set
        if (projKey == null) {
            throw new ApiException("Missing the required parameter 'projKey' when calling getExpiringUserTargetsForSegment(Async)");
        }
        
        // verify the required parameter 'envKey' is set
        if (envKey == null) {
            throw new ApiException("Missing the required parameter 'envKey' when calling getExpiringUserTargetsForSegment(Async)");
        }
        
        // verify the required parameter 'segmentKey' is set
        if (segmentKey == null) {
            throw new ApiException("Missing the required parameter 'segmentKey' when calling getExpiringUserTargetsForSegment(Async)");
        }
        

        okhttp3.Call localVarCall = getExpiringUserTargetsForSegmentCall(projKey, envKey, segmentKey, _callback);
        return localVarCall;

    }

    /**
     * Get expiring user targets for segment
     * Get a list of a segment&#39;s user targets that are scheduled for removal
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param segmentKey The segment key. (required)
     * @return ExpiringUserTargetGetResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ExpiringUserTargetGetResponse getExpiringUserTargetsForSegment(String projKey, String envKey, String segmentKey) throws ApiException {
        ApiResponse<ExpiringUserTargetGetResponse> localVarResp = getExpiringUserTargetsForSegmentWithHttpInfo(projKey, envKey, segmentKey);
        return localVarResp.getData();
    }

    /**
     * Get expiring user targets for segment
     * Get a list of a segment&#39;s user targets that are scheduled for removal
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param segmentKey The segment key. (required)
     * @return ApiResponse&lt;ExpiringUserTargetGetResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ExpiringUserTargetGetResponse> getExpiringUserTargetsForSegmentWithHttpInfo(String projKey, String envKey, String segmentKey) throws ApiException {
        okhttp3.Call localVarCall = getExpiringUserTargetsForSegmentValidateBeforeCall(projKey, envKey, segmentKey, null);
        Type localVarReturnType = new TypeToken<ExpiringUserTargetGetResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get expiring user targets for segment (asynchronously)
     * Get a list of a segment&#39;s user targets that are scheduled for removal
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param segmentKey The segment key. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getExpiringUserTargetsForSegmentAsync(String projKey, String envKey, String segmentKey, final ApiCallback<ExpiringUserTargetGetResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = getExpiringUserTargetsForSegmentValidateBeforeCall(projKey, envKey, segmentKey, _callback);
        Type localVarReturnType = new TypeToken<ExpiringUserTargetGetResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getSegment
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param key The segment key (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Segment response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getSegmentCall(String projKey, String envKey, String key, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/segments/{projKey}/{envKey}/{key}"
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
    private okhttp3.Call getSegmentValidateBeforeCall(String projKey, String envKey, String key, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projKey' is set
        if (projKey == null) {
            throw new ApiException("Missing the required parameter 'projKey' when calling getSegment(Async)");
        }
        
        // verify the required parameter 'envKey' is set
        if (envKey == null) {
            throw new ApiException("Missing the required parameter 'envKey' when calling getSegment(Async)");
        }
        
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException("Missing the required parameter 'key' when calling getSegment(Async)");
        }
        

        okhttp3.Call localVarCall = getSegmentCall(projKey, envKey, key, _callback);
        return localVarCall;

    }

    /**
     * Get segment
     * Get a single user segment by key
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param key The segment key (required)
     * @return UserSegment
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Segment response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public UserSegment getSegment(String projKey, String envKey, String key) throws ApiException {
        ApiResponse<UserSegment> localVarResp = getSegmentWithHttpInfo(projKey, envKey, key);
        return localVarResp.getData();
    }

    /**
     * Get segment
     * Get a single user segment by key
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param key The segment key (required)
     * @return ApiResponse&lt;UserSegment&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Segment response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<UserSegment> getSegmentWithHttpInfo(String projKey, String envKey, String key) throws ApiException {
        okhttp3.Call localVarCall = getSegmentValidateBeforeCall(projKey, envKey, key, null);
        Type localVarReturnType = new TypeToken<UserSegment>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get segment (asynchronously)
     * Get a single user segment by key
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param key The segment key (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Segment response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getSegmentAsync(String projKey, String envKey, String key, final ApiCallback<UserSegment> _callback) throws ApiException {

        okhttp3.Call localVarCall = getSegmentValidateBeforeCall(projKey, envKey, key, _callback);
        Type localVarReturnType = new TypeToken<UserSegment>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getSegmentMembershipForUser
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param key The segment key. (required)
     * @param userKey The user key. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Segment membership for user response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getSegmentMembershipForUserCall(String projKey, String envKey, String key, String userKey, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/segments/{projKey}/{envKey}/{key}/users/{userKey}"
            .replaceAll("\\{" + "projKey" + "\\}", localVarApiClient.escapeString(projKey.toString()))
            .replaceAll("\\{" + "envKey" + "\\}", localVarApiClient.escapeString(envKey.toString()))
            .replaceAll("\\{" + "key" + "\\}", localVarApiClient.escapeString(key.toString()))
            .replaceAll("\\{" + "userKey" + "\\}", localVarApiClient.escapeString(userKey.toString()));

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
    private okhttp3.Call getSegmentMembershipForUserValidateBeforeCall(String projKey, String envKey, String key, String userKey, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projKey' is set
        if (projKey == null) {
            throw new ApiException("Missing the required parameter 'projKey' when calling getSegmentMembershipForUser(Async)");
        }
        
        // verify the required parameter 'envKey' is set
        if (envKey == null) {
            throw new ApiException("Missing the required parameter 'envKey' when calling getSegmentMembershipForUser(Async)");
        }
        
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException("Missing the required parameter 'key' when calling getSegmentMembershipForUser(Async)");
        }
        
        // verify the required parameter 'userKey' is set
        if (userKey == null) {
            throw new ApiException("Missing the required parameter 'userKey' when calling getSegmentMembershipForUser(Async)");
        }
        

        okhttp3.Call localVarCall = getSegmentMembershipForUserCall(projKey, envKey, key, userKey, _callback);
        return localVarCall;

    }

    /**
     * Get Big Segment membership for user
     * Returns the membership status (included/excluded) for a given user in this segment. This operation does not support basic Segments.
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param key The segment key. (required)
     * @param userKey The user key. (required)
     * @return BigSegmentTarget
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Segment membership for user response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public BigSegmentTarget getSegmentMembershipForUser(String projKey, String envKey, String key, String userKey) throws ApiException {
        ApiResponse<BigSegmentTarget> localVarResp = getSegmentMembershipForUserWithHttpInfo(projKey, envKey, key, userKey);
        return localVarResp.getData();
    }

    /**
     * Get Big Segment membership for user
     * Returns the membership status (included/excluded) for a given user in this segment. This operation does not support basic Segments.
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param key The segment key. (required)
     * @param userKey The user key. (required)
     * @return ApiResponse&lt;BigSegmentTarget&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Segment membership for user response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<BigSegmentTarget> getSegmentMembershipForUserWithHttpInfo(String projKey, String envKey, String key, String userKey) throws ApiException {
        okhttp3.Call localVarCall = getSegmentMembershipForUserValidateBeforeCall(projKey, envKey, key, userKey, null);
        Type localVarReturnType = new TypeToken<BigSegmentTarget>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Big Segment membership for user (asynchronously)
     * Returns the membership status (included/excluded) for a given user in this segment. This operation does not support basic Segments.
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param key The segment key. (required)
     * @param userKey The user key. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Segment membership for user response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getSegmentMembershipForUserAsync(String projKey, String envKey, String key, String userKey, final ApiCallback<BigSegmentTarget> _callback) throws ApiException {

        okhttp3.Call localVarCall = getSegmentMembershipForUserValidateBeforeCall(projKey, envKey, key, userKey, _callback);
        Type localVarReturnType = new TypeToken<BigSegmentTarget>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getSegments
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Segment collection response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getSegmentsCall(String projKey, String envKey, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/segments/{projKey}/{envKey}"
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
    private okhttp3.Call getSegmentsValidateBeforeCall(String projKey, String envKey, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projKey' is set
        if (projKey == null) {
            throw new ApiException("Missing the required parameter 'projKey' when calling getSegments(Async)");
        }
        
        // verify the required parameter 'envKey' is set
        if (envKey == null) {
            throw new ApiException("Missing the required parameter 'envKey' when calling getSegments(Async)");
        }
        

        okhttp3.Call localVarCall = getSegmentsCall(projKey, envKey, _callback);
        return localVarCall;

    }

    /**
     * List segments
     * Get a list of all user segments in the given project
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @return UserSegments
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Segment collection response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
     </table>
     */
    public UserSegments getSegments(String projKey, String envKey) throws ApiException {
        ApiResponse<UserSegments> localVarResp = getSegmentsWithHttpInfo(projKey, envKey);
        return localVarResp.getData();
    }

    /**
     * List segments
     * Get a list of all user segments in the given project
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @return ApiResponse&lt;UserSegments&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Segment collection response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<UserSegments> getSegmentsWithHttpInfo(String projKey, String envKey) throws ApiException {
        okhttp3.Call localVarCall = getSegmentsValidateBeforeCall(projKey, envKey, null);
        Type localVarReturnType = new TypeToken<UserSegments>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List segments (asynchronously)
     * Get a list of all user segments in the given project
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Segment collection response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getSegmentsAsync(String projKey, String envKey, final ApiCallback<UserSegments> _callback) throws ApiException {

        okhttp3.Call localVarCall = getSegmentsValidateBeforeCall(projKey, envKey, _callback);
        Type localVarReturnType = new TypeToken<UserSegments>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for patchExpiringUserTargetsForSegment
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param segmentKey The user segment key. (required)
     * @param patchSegmentRequest  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring user target response. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchExpiringUserTargetsForSegmentCall(String projKey, String envKey, String segmentKey, PatchSegmentRequest patchSegmentRequest, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = patchSegmentRequest;

        // create path and map variables
        String localVarPath = "/api/v2/segments/{projKey}/{segmentKey}/expiring-user-targets/{envKey}"
            .replaceAll("\\{" + "projKey" + "\\}", localVarApiClient.escapeString(projKey.toString()))
            .replaceAll("\\{" + "envKey" + "\\}", localVarApiClient.escapeString(envKey.toString()))
            .replaceAll("\\{" + "segmentKey" + "\\}", localVarApiClient.escapeString(segmentKey.toString()));

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
    private okhttp3.Call patchExpiringUserTargetsForSegmentValidateBeforeCall(String projKey, String envKey, String segmentKey, PatchSegmentRequest patchSegmentRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projKey' is set
        if (projKey == null) {
            throw new ApiException("Missing the required parameter 'projKey' when calling patchExpiringUserTargetsForSegment(Async)");
        }
        
        // verify the required parameter 'envKey' is set
        if (envKey == null) {
            throw new ApiException("Missing the required parameter 'envKey' when calling patchExpiringUserTargetsForSegment(Async)");
        }
        
        // verify the required parameter 'segmentKey' is set
        if (segmentKey == null) {
            throw new ApiException("Missing the required parameter 'segmentKey' when calling patchExpiringUserTargetsForSegment(Async)");
        }
        
        // verify the required parameter 'patchSegmentRequest' is set
        if (patchSegmentRequest == null) {
            throw new ApiException("Missing the required parameter 'patchSegmentRequest' when calling patchExpiringUserTargetsForSegment(Async)");
        }
        

        okhttp3.Call localVarCall = patchExpiringUserTargetsForSegmentCall(projKey, envKey, segmentKey, patchSegmentRequest, _callback);
        return localVarCall;

    }

    /**
     * Update expiring user targets for segment
     * Update the list of a segment&#39;s user targets that are scheduled for removal&lt;br /&gt;&lt;br /&gt;Requires a semantic patch representation of the desired changes to the resource. To learn more about semantic patches, read [Updates](/reference#updates-via-semantic-patches).&lt;br /&gt;&lt;br /&gt;If the request is well-formed but any of its instructions failed to process, this operation returns status code &#x60;200&#x60;. In this case, the response &#x60;errors&#x60; array will be non-empty.
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param segmentKey The user segment key. (required)
     * @param patchSegmentRequest  (required)
     * @return ExpiringUserTargetPatchResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring user target response. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ExpiringUserTargetPatchResponse patchExpiringUserTargetsForSegment(String projKey, String envKey, String segmentKey, PatchSegmentRequest patchSegmentRequest) throws ApiException {
        ApiResponse<ExpiringUserTargetPatchResponse> localVarResp = patchExpiringUserTargetsForSegmentWithHttpInfo(projKey, envKey, segmentKey, patchSegmentRequest);
        return localVarResp.getData();
    }

    /**
     * Update expiring user targets for segment
     * Update the list of a segment&#39;s user targets that are scheduled for removal&lt;br /&gt;&lt;br /&gt;Requires a semantic patch representation of the desired changes to the resource. To learn more about semantic patches, read [Updates](/reference#updates-via-semantic-patches).&lt;br /&gt;&lt;br /&gt;If the request is well-formed but any of its instructions failed to process, this operation returns status code &#x60;200&#x60;. In this case, the response &#x60;errors&#x60; array will be non-empty.
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param segmentKey The user segment key. (required)
     * @param patchSegmentRequest  (required)
     * @return ApiResponse&lt;ExpiringUserTargetPatchResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring user target response. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ExpiringUserTargetPatchResponse> patchExpiringUserTargetsForSegmentWithHttpInfo(String projKey, String envKey, String segmentKey, PatchSegmentRequest patchSegmentRequest) throws ApiException {
        okhttp3.Call localVarCall = patchExpiringUserTargetsForSegmentValidateBeforeCall(projKey, envKey, segmentKey, patchSegmentRequest, null);
        Type localVarReturnType = new TypeToken<ExpiringUserTargetPatchResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update expiring user targets for segment (asynchronously)
     * Update the list of a segment&#39;s user targets that are scheduled for removal&lt;br /&gt;&lt;br /&gt;Requires a semantic patch representation of the desired changes to the resource. To learn more about semantic patches, read [Updates](/reference#updates-via-semantic-patches).&lt;br /&gt;&lt;br /&gt;If the request is well-formed but any of its instructions failed to process, this operation returns status code &#x60;200&#x60;. In this case, the response &#x60;errors&#x60; array will be non-empty.
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param segmentKey The user segment key. (required)
     * @param patchSegmentRequest  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring user target response. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchExpiringUserTargetsForSegmentAsync(String projKey, String envKey, String segmentKey, PatchSegmentRequest patchSegmentRequest, final ApiCallback<ExpiringUserTargetPatchResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = patchExpiringUserTargetsForSegmentValidateBeforeCall(projKey, envKey, segmentKey, patchSegmentRequest, _callback);
        Type localVarReturnType = new TypeToken<ExpiringUserTargetPatchResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for patchSegment
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param key The user segment key. (required)
     * @param patchWithComment  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Segment response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchSegmentCall(String projKey, String envKey, String key, PatchWithComment patchWithComment, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = patchWithComment;

        // create path and map variables
        String localVarPath = "/api/v2/segments/{projKey}/{envKey}/{key}"
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
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(localVarPath, "PATCH", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call patchSegmentValidateBeforeCall(String projKey, String envKey, String key, PatchWithComment patchWithComment, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projKey' is set
        if (projKey == null) {
            throw new ApiException("Missing the required parameter 'projKey' when calling patchSegment(Async)");
        }
        
        // verify the required parameter 'envKey' is set
        if (envKey == null) {
            throw new ApiException("Missing the required parameter 'envKey' when calling patchSegment(Async)");
        }
        
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException("Missing the required parameter 'key' when calling patchSegment(Async)");
        }
        
        // verify the required parameter 'patchWithComment' is set
        if (patchWithComment == null) {
            throw new ApiException("Missing the required parameter 'patchWithComment' when calling patchSegment(Async)");
        }
        

        okhttp3.Call localVarCall = patchSegmentCall(projKey, envKey, key, patchWithComment, _callback);
        return localVarCall;

    }

    /**
     * Patch segment
     * Update a user segment. The request body must be a valid JSON patch or JSON merge patch document. To learn more about semantic patches, read [Updates](/#section/Overview/Updates).
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param key The user segment key. (required)
     * @param patchWithComment  (required)
     * @return UserSegment
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Segment response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public UserSegment patchSegment(String projKey, String envKey, String key, PatchWithComment patchWithComment) throws ApiException {
        ApiResponse<UserSegment> localVarResp = patchSegmentWithHttpInfo(projKey, envKey, key, patchWithComment);
        return localVarResp.getData();
    }

    /**
     * Patch segment
     * Update a user segment. The request body must be a valid JSON patch or JSON merge patch document. To learn more about semantic patches, read [Updates](/#section/Overview/Updates).
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param key The user segment key. (required)
     * @param patchWithComment  (required)
     * @return ApiResponse&lt;UserSegment&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Segment response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<UserSegment> patchSegmentWithHttpInfo(String projKey, String envKey, String key, PatchWithComment patchWithComment) throws ApiException {
        okhttp3.Call localVarCall = patchSegmentValidateBeforeCall(projKey, envKey, key, patchWithComment, null);
        Type localVarReturnType = new TypeToken<UserSegment>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Patch segment (asynchronously)
     * Update a user segment. The request body must be a valid JSON patch or JSON merge patch document. To learn more about semantic patches, read [Updates](/#section/Overview/Updates).
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param key The user segment key. (required)
     * @param patchWithComment  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Segment response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchSegmentAsync(String projKey, String envKey, String key, PatchWithComment patchWithComment, final ApiCallback<UserSegment> _callback) throws ApiException {

        okhttp3.Call localVarCall = patchSegmentValidateBeforeCall(projKey, envKey, key, patchWithComment, _callback);
        Type localVarReturnType = new TypeToken<UserSegment>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for postSegment
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param segmentBody  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Segment response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postSegmentCall(String projKey, String envKey, SegmentBody segmentBody, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = segmentBody;

        // create path and map variables
        String localVarPath = "/api/v2/segments/{projKey}/{envKey}"
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
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call postSegmentValidateBeforeCall(String projKey, String envKey, SegmentBody segmentBody, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projKey' is set
        if (projKey == null) {
            throw new ApiException("Missing the required parameter 'projKey' when calling postSegment(Async)");
        }
        
        // verify the required parameter 'envKey' is set
        if (envKey == null) {
            throw new ApiException("Missing the required parameter 'envKey' when calling postSegment(Async)");
        }
        
        // verify the required parameter 'segmentBody' is set
        if (segmentBody == null) {
            throw new ApiException("Missing the required parameter 'segmentBody' when calling postSegment(Async)");
        }
        

        okhttp3.Call localVarCall = postSegmentCall(projKey, envKey, segmentBody, _callback);
        return localVarCall;

    }

    /**
     * Create segment
     * Create a new user segment
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param segmentBody  (required)
     * @return UserSegment
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Segment response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public UserSegment postSegment(String projKey, String envKey, SegmentBody segmentBody) throws ApiException {
        ApiResponse<UserSegment> localVarResp = postSegmentWithHttpInfo(projKey, envKey, segmentBody);
        return localVarResp.getData();
    }

    /**
     * Create segment
     * Create a new user segment
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param segmentBody  (required)
     * @return ApiResponse&lt;UserSegment&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Segment response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<UserSegment> postSegmentWithHttpInfo(String projKey, String envKey, SegmentBody segmentBody) throws ApiException {
        okhttp3.Call localVarCall = postSegmentValidateBeforeCall(projKey, envKey, segmentBody, null);
        Type localVarReturnType = new TypeToken<UserSegment>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create segment (asynchronously)
     * Create a new user segment
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param segmentBody  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Segment response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postSegmentAsync(String projKey, String envKey, SegmentBody segmentBody, final ApiCallback<UserSegment> _callback) throws ApiException {

        okhttp3.Call localVarCall = postSegmentValidateBeforeCall(projKey, envKey, segmentBody, _callback);
        Type localVarReturnType = new TypeToken<UserSegment>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateBigSegmentTargets
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param key The segment key. (required)
     * @param segmentUserState  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateBigSegmentTargetsCall(String projKey, String envKey, String key, SegmentUserState segmentUserState, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = segmentUserState;

        // create path and map variables
        String localVarPath = "/api/v2/segments/{projKey}/{envKey}/{key}/users"
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
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateBigSegmentTargetsValidateBeforeCall(String projKey, String envKey, String key, SegmentUserState segmentUserState, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projKey' is set
        if (projKey == null) {
            throw new ApiException("Missing the required parameter 'projKey' when calling updateBigSegmentTargets(Async)");
        }
        
        // verify the required parameter 'envKey' is set
        if (envKey == null) {
            throw new ApiException("Missing the required parameter 'envKey' when calling updateBigSegmentTargets(Async)");
        }
        
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException("Missing the required parameter 'key' when calling updateBigSegmentTargets(Async)");
        }
        
        // verify the required parameter 'segmentUserState' is set
        if (segmentUserState == null) {
            throw new ApiException("Missing the required parameter 'segmentUserState' when calling updateBigSegmentTargets(Async)");
        }
        

        okhttp3.Call localVarCall = updateBigSegmentTargetsCall(projKey, envKey, key, segmentUserState, _callback);
        return localVarCall;

    }

    /**
     * Update targets on a Big Segment
     * Update targets included or excluded in a Big Segment
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param key The segment key. (required)
     * @param segmentUserState  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public void updateBigSegmentTargets(String projKey, String envKey, String key, SegmentUserState segmentUserState) throws ApiException {
        updateBigSegmentTargetsWithHttpInfo(projKey, envKey, key, segmentUserState);
    }

    /**
     * Update targets on a Big Segment
     * Update targets included or excluded in a Big Segment
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param key The segment key. (required)
     * @param segmentUserState  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> updateBigSegmentTargetsWithHttpInfo(String projKey, String envKey, String key, SegmentUserState segmentUserState) throws ApiException {
        okhttp3.Call localVarCall = updateBigSegmentTargetsValidateBeforeCall(projKey, envKey, key, segmentUserState, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Update targets on a Big Segment (asynchronously)
     * Update targets included or excluded in a Big Segment
     * @param projKey The project key. (required)
     * @param envKey The environment key. (required)
     * @param key The segment key. (required)
     * @param segmentUserState  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateBigSegmentTargetsAsync(String projKey, String envKey, String key, SegmentUserState segmentUserState, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateBigSegmentTargetsValidateBeforeCall(projKey, envKey, key, segmentUserState, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
}
