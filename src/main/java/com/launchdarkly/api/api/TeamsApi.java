/*
 * LaunchDarkly REST API
 * # Overview  ## Authentication  All REST API resources are authenticated with either [personal or service access tokens](https://docs.launchdarkly.com/home/account-security/api-access-tokens), or session cookies. Other authentication mechanisms are not supported. You can manage personal access tokens on your [**Account settings**](https://app.launchdarkly.com/settings/tokens) page.  LaunchDarkly also has SDK keys, mobile keys, and client-side IDs that are used by our server-side SDKs, mobile SDKs, and JavaScript-based SDKs, respectively. **These keys cannot be used to access our REST API**. These keys are environment-specific, and can only perform read-only operations such as fetching feature flag settings.  | Auth mechanism                                                                                  | Allowed resources                                                                                     | Use cases                                          | | ----------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------- | -------------------------------------------------- | | [Personal or service access tokens](https://docs.launchdarkly.com/home/account-security/api-access-tokens) | Can be customized on a per-token basis                                                                | Building scripts, custom integrations, data export. | | SDK keys                                                                                        | Can only access read-only resources specific to server-side SDKs. Restricted to a single environment. | Server-side SDKs                     | | Mobile keys                                                                                     | Can only access read-only resources specific to mobile SDKs, and only for flags marked available to mobile keys. Restricted to a single environment.           | Mobile SDKs                                        | | Client-side ID                                                                                  | Can only access read-only resources specific to JavaScript-based client-side SDKs, and only for flags marked available to client-side. Restricted to a single environment.           | Client-side JavaScript                             |  > #### Keep your access tokens and SDK keys private > > Access tokens should _never_ be exposed in untrusted contexts. Never put an access token in client-side JavaScript, or embed it in a mobile application. LaunchDarkly has special mobile keys that you can embed in mobile apps. If you accidentally expose an access token or SDK key, you can reset it from your [**Account settings**](https://app.launchdarkly.com/settings/tokens) page. > > The client-side ID is safe to embed in untrusted contexts. It's designed for use in client-side JavaScript.  ### Authentication using request header  The preferred way to authenticate with the API is by adding an `Authorization` header containing your access token to your requests. The value of the `Authorization` header must be your access token.  Manage personal access tokens from the [**Account settings**](https://app.launchdarkly.com/settings/tokens) page.  ### Authentication using session cookie  For testing purposes, you can make API calls directly from your web browser. If you are logged in to the LaunchDarkly application, the API will use your existing session to authenticate calls.  If you have a [role](https://docs.launchdarkly.com/home/team/built-in-roles) other than Admin, or have a [custom role](https://docs.launchdarkly.com/home/team/custom-roles) defined, you may not have permission to perform some API calls. You will receive a `401` response code in that case.  > ### Modifying the Origin header causes an error > > LaunchDarkly validates that the Origin header for any API request authenticated by a session cookie matches the expected Origin header. The expected Origin header is `https://app.launchdarkly.com`. > > If the Origin header does not match what's expected, LaunchDarkly returns an error. This error can prevent the LaunchDarkly app from working correctly. > > Any browser extension that intentionally changes the Origin header can cause this problem. For example, the `Allow-Control-Allow-Origin: *` Chrome extension changes the Origin header to `http://evil.com` and causes the app to fail. > > To prevent this error, do not modify your Origin header. > > LaunchDarkly does not require origin matching when authenticating with an access token, so this issue does not affect normal API usage.  ## Representations  All resources expect and return JSON response bodies. Error responses also send a JSON body. To learn more about the error format of the API, read [Errors](/#section/Overview/Errors).  In practice this means that you always get a response with a `Content-Type` header set to `application/json`.  In addition, request bodies for `PATCH`, `POST`, `PUT`, and `REPORT` requests must be encoded as JSON with a `Content-Type` header set to `application/json`.  ### Summary and detailed representations  When you fetch a list of resources, the response includes only the most important attributes of each resource. This is a _summary representation_ of the resource. When you fetch an individual resource, such as a single feature flag, you receive a _detailed representation_ of the resource.  The best way to find a detailed representation is to follow links. Every summary representation includes a link to its detailed representation.  ### Expanding responses  Sometimes the detailed representation of a resource does not include all of the attributes of the resource by default. If this is the case, the request method will clearly document this and describe which attributes you can include in an expanded response.  To include the additional attributes, append the `expand` request parameter to your request and add a comma-separated list of the attributes to include. For example, when you append `?expand=members,roles` to the [Get team](/tag/Teams#operation/getTeam) endpoint, the expanded response includes both of these attributes.  ### Links and addressability  The best way to navigate the API is by following links. These are attributes in representations that link to other resources. The API always uses the same format for links:  - Links to other resources within the API are encapsulated in a `_links` object - If the resource has a corresponding link to HTML content on the site, it is stored in a special `_site` link  Each link has two attributes:  - An `href`, which contains the URL - A `type`, which describes the content type  For example, a feature resource might return the following:  ```json {   \"_links\": {     \"parent\": {       \"href\": \"/api/features\",       \"type\": \"application/json\"     },     \"self\": {       \"href\": \"/api/features/sort.order\",       \"type\": \"application/json\"     }   },   \"_site\": {     \"href\": \"/features/sort.order\",     \"type\": \"text/html\"   } } ```  From this, you can navigate to the parent collection of features by following the `parent` link, or navigate to the site page for the feature by following the `_site` link.  Collections are always represented as a JSON object with an `items` attribute containing an array of representations. Like all other representations, collections have `_links` defined at the top level.  Paginated collections include `first`, `last`, `next`, and `prev` links containing a URL with the respective set of elements in the collection.  ## Updates  Resources that accept partial updates use the `PATCH` verb. Most resources support the [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) format. Some resources also support the [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) format, and some resources support the [semantic patch](/reference#updates-using-semantic-patch) format, which is a way to specify the modifications to perform as a set of executable instructions. Each resource supports optional [comments](/reference#updates-with-comments) that you can submit with updates. Comments appear in outgoing webhooks, the audit log, and other integrations.  When a resource supports both JSON patch and semantic patch, we document both in the request method. However, the specific request body fields and descriptions included in our documentation only match one type of patch or the other.  ### Updates using JSON patch  [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) is a way to specify the modifications to perform on a resource. JSON patch uses paths and a limited set of operations to describe how to transform the current state of the resource into a new state. JSON patch documents are always arrays, where each element contains an operation, a path to the field to update, and the new value.  For example, in this feature flag representation:  ```json {     \"name\": \"New recommendations engine\",     \"key\": \"engine.enable\",     \"description\": \"This is the description\",     ... } ``` You can change the feature flag's description with the following patch document:  ```json [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"This is the new description\" }] ```  You can specify multiple modifications to perform in a single request. You can also test that certain preconditions are met before applying the patch:  ```json [   { \"op\": \"test\", \"path\": \"/version\", \"value\": 10 },   { \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" } ] ```  The above patch request tests whether the feature flag's `version` is `10`, and if so, changes the feature flag's description.  Attributes that are not editable, such as a resource's `_links`, have names that start with an underscore.  ### Updates using JSON merge patch  [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) is another format for specifying the modifications to perform on a resource. JSON merge patch is less expressive than JSON patch. However, in many cases it is simpler to construct a merge patch document. For example, you can change a feature flag's description with the following merge patch document:  ```json {   \"description\": \"New flag description\" } ```  ### Updates using semantic patch  The API also supports the semantic patch format. A semantic patch is a way to specify the modifications to perform on a resource as a set of executable instructions.  Semantic patch allows you to be explicit about intent using precise, custom instructions. In many cases, you can define semantic patch instructions independently of the current state of the resource. This can be useful when defining a change that may be applied at a future date.  To make a semantic patch request, you must append `domain-model=launchdarkly.semanticpatch` to your `Content-Type` header.  Here's how:  ``` Content-Type: application/json; domain-model=launchdarkly.semanticpatch ```  If you call a semantic patch resource without this header, you will receive a `400` response because your semantic patch will be interpreted as a JSON patch.  The body of a semantic patch request takes the following properties:  * `comment` (string): (Optional) A description of the update. * `environmentKey` (string): (Required for some resources only) The environment key. * `instructions` (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a `kind` property that indicates the instruction. If the instruction requires parameters, you must include those parameters as additional fields in the object. The documentation for each resource that supports semantic patch includes the available instructions and any additional parameters.  For example:  ```json {   \"comment\": \"optional comment\",   \"instructions\": [ {\"kind\": \"turnFlagOn\"} ] } ```  If any instruction in the patch encounters an error, the endpoint returns an error and will not change the resource. In general, each instruction silently does nothing if the resource is already in the state you request.  ### Updates with comments  You can submit optional comments with `PATCH` changes.  To submit a comment along with a JSON patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"patch\": [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" }] } ```  To submit a comment along with a JSON merge patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"merge\": { \"description\": \"New flag description\" } } ```  To submit a comment along with a semantic patch, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"instructions\": [ {\"kind\": \"turnFlagOn\"} ] } ```  ## Errors  The API always returns errors in a common format. Here's an example:  ```json {   \"code\": \"invalid_request\",   \"message\": \"A feature with that key already exists\",   \"id\": \"30ce6058-87da-11e4-b116-123b93f75cba\" } ```  The `code` indicates the general class of error. The `message` is a human-readable explanation of what went wrong. The `id` is a unique identifier. Use it when you're working with LaunchDarkly Support to debug a problem with a specific API call.  ### HTTP status error response codes  | Code | Definition        | Description                                                                                       | Possible Solution                                                | | ---- | ----------------- | ------------------------------------------------------------------------------------------- | ---------------------------------------------------------------- | | 400  | Invalid request       | The request cannot be understood.                                    | Ensure JSON syntax in request body is correct.                   | | 401  | Invalid access token      | Requestor is unauthorized or does not have permission for this API call.                                                | Ensure your API access token is valid and has the appropriate permissions.                                     | | 403  | Forbidden         | Requestor does not have access to this resource.                                                | Ensure that the account member or access token has proper permissions set. | | 404  | Invalid resource identifier | The requested resource is not valid. | Ensure that the resource is correctly identified by id or key. | | 405  | Method not allowed | The request method is not allowed on this resource. | Ensure that the HTTP verb is correct. | | 409  | Conflict          | The API request can not be completed because it conflicts with a concurrent API request. | Retry your request.                                              | | 422  | Unprocessable entity | The API request can not be completed because the update description can not be understood. | Ensure that the request body is correct for the type of patch you are using, either JSON patch or semantic patch. | 429  | Too many requests | Read [Rate limiting](/#section/Overview/Rate-limiting).                                               | Wait and try again later.                                        |  ## CORS  The LaunchDarkly API supports Cross Origin Resource Sharing (CORS) for AJAX requests from any origin. If an `Origin` header is given in a request, it will be echoed as an explicitly allowed origin. Otherwise the request returns a wildcard, `Access-Control-Allow-Origin: *`. For more information on CORS, read the [CORS W3C Recommendation](http://www.w3.org/TR/cors). Example CORS headers might look like:  ```http Access-Control-Allow-Headers: Accept, Content-Type, Content-Length, Accept-Encoding, Authorization Access-Control-Allow-Methods: OPTIONS, GET, DELETE, PATCH Access-Control-Allow-Origin: * Access-Control-Max-Age: 300 ```  You can make authenticated CORS calls just as you would make same-origin calls, using either [token or session-based authentication](/#section/Overview/Authentication). If you are using session authentication, you should set the `withCredentials` property for your `xhr` request to `true`. You should never expose your access tokens to untrusted users.  ## Rate limiting  We use several rate limiting strategies to ensure the availability of our APIs. Rate-limited calls to our APIs return a `429` status code. Calls to our APIs include headers indicating the current rate limit status. The specific headers returned depend on the API route being called. The limits differ based on the route, authentication mechanism, and other factors. Routes that are not rate limited may not contain any of the headers described below.  > ### Rate limiting and SDKs > > LaunchDarkly SDKs are never rate limited and do not use the API endpoints defined here. LaunchDarkly uses a different set of approaches, including streaming/server-sent events and a global CDN, to ensure availability to the routes used by LaunchDarkly SDKs.  ### Global rate limits  Authenticated requests are subject to a global limit. This is the maximum number of calls that your account can make to the API per ten seconds. All personal access tokens on the account share this limit, so exceeding the limit with one access token will impact other tokens. Calls that are subject to global rate limits return the headers below:  | Header name                    | Description                                                                      | | ------------------------------ | -------------------------------------------------------------------------------- | | `X-Ratelimit-Global-Remaining` | The maximum number of requests the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`            | The time at which the current rate limit window resets in epoch milliseconds.    |  We do not publicly document the specific number of calls that can be made globally. This limit may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limit.  ### Route-level rate limits  Some authenticated routes have custom rate limits. These also reset every ten seconds. Any access tokens hitting the same route share this limit, so exceeding the limit with one access token may impact other tokens. Calls that are subject to route-level rate limits return the headers below:  | Header name                   | Description                                                                                           | | ----------------------------- | ----------------------------------------------------------------------------------------------------- | | `X-Ratelimit-Route-Remaining` | The maximum number of requests to the current route the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`           | The time at which the current rate limit window resets in epoch milliseconds.                         |  A _route_ represents a specific URL pattern and verb. For example, the [Delete environment](/tag/Environments#operation/deleteEnvironment) endpoint is considered a single route, and each call to delete an environment counts against your route-level rate limit for that route.  We do not publicly document the specific number of calls that an account can make to each endpoint per ten seconds. These limits may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limits.  ### IP-based rate limiting  We also employ IP-based rate limiting on some API routes. If you hit an IP-based rate limit, your API response will include a `Retry-After` header indicating how long to wait before re-trying the call. Clients must wait at least `Retry-After` seconds before making additional calls to our API, and should employ jitter and backoff strategies to avoid triggering rate limits again.  ## OpenAPI (Swagger) and client libraries  We have a [complete OpenAPI (Swagger) specification](https://app.launchdarkly.com/api/v2/openapi.json) for our API.  We auto-generate multiple client libraries based on our OpenAPI specification. To learn more, visit the [collection of client libraries on GitHub](https://github.com/search?q=topic%3Alaunchdarkly-api+org%3Alaunchdarkly&type=Repositories). You can also use this specification to generate client libraries to interact with our REST API in your language of choice.  Our OpenAPI specification is supported by several API-based tools such as Postman and Insomnia. In many cases, you can directly import our specification to explore our APIs.  ## Method overriding  Some firewalls and HTTP clients restrict the use of verbs other than `GET` and `POST`. In those environments, our API endpoints that use `DELETE`, `PATCH`, and `PUT` verbs are inaccessible.  To avoid this issue, our API supports the `X-HTTP-Method-Override` header, allowing clients to \"tunnel\" `DELETE`, `PATCH`, and `PUT` requests using a `POST` request.  For example, to call a `PATCH` endpoint using a `POST` request, you can include `X-HTTP-Method-Override:PATCH` as a header.  ## Beta resources  We sometimes release new API resources in **beta** status before we release them with general availability.  Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  We try to promote resources into general availability as quickly as possible. This happens after sufficient testing and when we're satisfied that we no longer need to make backwards-incompatible changes.  We mark beta resources with a \"Beta\" callout in our documentation, pictured below:  > ### This feature is in beta > > To use this feature, pass in a header including the `LD-API-Version` key with value set to `beta`. Use this header with each call. To learn more, read [Beta resources](/#section/Overview/Beta-resources). > > Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  ### Using beta resources  To use a beta resource, you must include a header in the request. If you call a beta resource without this header, you receive a `403` response.  Use this header:  ``` LD-API-Version: beta ```  ## Versioning  We try hard to keep our REST API backwards compatible, but we occasionally have to make backwards-incompatible changes in the process of shipping new features. These breaking changes can cause unexpected behavior if you don't prepare for them accordingly.  Updates to our REST API include support for the latest features in LaunchDarkly. We also release a new version of our REST API every time we make a breaking change. We provide simultaneous support for multiple API versions so you can migrate from your current API version to a new version at your own pace.  ### Setting the API version per request  You can set the API version on a specific request by sending an `LD-API-Version` header, as shown in the example below:  ``` LD-API-Version: 20220603 ```  The header value is the version number of the API version you would like to request. The number for each version corresponds to the date the version was released in `yyyymmdd` format. In the example above the version `20220603` corresponds to June 03, 2022.  ### Setting the API version per access token  When you create an access token, you must specify a specific version of the API to use. This ensures that integrations using this token cannot be broken by version changes.  Tokens created before versioning was released have their version set to `20160426`, which is the version of the API that existed before the current versioning scheme, so that they continue working the same way they did before versioning.  If you would like to upgrade your integration to use a new API version, you can explicitly set the header described above.  > ### Best practice: Set the header for every client or integration > > We recommend that you set the API version header explicitly in any client or integration you build. > > Only rely on the access token API version during manual testing.  ### API version changelog  |<div style=\"width:75px\">Version</div> | Changes | End of life (EOL) |---|---|---| | `20220603` | <ul><li>Changed the [list projects](/tag/Projects#operation/getProjects) return value:<ul><li>Response is now paginated with a default limit of `20`.</li><li>Added support for filter and sort.</li><li>The project `environments` field is now expandable. This field is omitted by default.</li></ul></li><li>Changed the [get project](/tag/Projects#operation/getProject) return value:<ul><li>The `environments` field is now expandable. This field is omitted by default.</li></ul></li></ul> | Current | | `20210729` | <ul><li>Changed the [create approval request](/tag/Approvals#operation/postApprovalRequest) return value. It now returns HTTP Status Code `201` instead of `200`.</li><li> Changed the [get users](/tag/Users#operation/getUser) return value. It now returns a user record, not a user. </li><li>Added additional optional fields to environment, segments, flags, members, and segments, including the ability to create Big Segments. </li><li> Added default values for flag variations when new environments are created. </li><li>Added filtering and pagination for getting flags and members, including `limit`, `number`, `filter`, and `sort` query parameters. </li><li>Added endpoints for expiring user targets for flags and segments, scheduled changes, access tokens, Relay Proxy configuration, integrations and subscriptions, and approvals. </li></ul> | 2023-06-03 | | `20191212` | <ul><li>[List feature flags](/tag/Feature-flags#operation/getFeatureFlags) now defaults to sending summaries of feature flag configurations, equivalent to setting the query parameter `summary=true`. Summaries omit flag targeting rules and individual user targets from the payload. </li><li> Added endpoints for flags, flag status, projects, environments, users, audit logs, members, users, custom roles, segments, usage, streams, events, and data export. </li></ul> | 2022-07-29 | | `20160426` | <ul><li>Initial versioning of API. Tokens created before versioning have their version set to this.</li></ul> | 2020-12-12 | 
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


import java.io.File;
import com.launchdarkly.api.model.ForbiddenErrorRep;
import com.launchdarkly.api.model.InvalidRequestErrorRep;
import com.launchdarkly.api.model.MethodNotAllowedErrorRep;
import com.launchdarkly.api.model.NotFoundErrorRep;
import com.launchdarkly.api.model.RateLimitedErrorRep;
import com.launchdarkly.api.model.StatusConflictErrorRep;
import com.launchdarkly.api.model.Team;
import com.launchdarkly.api.model.TeamCustomRoles;
import com.launchdarkly.api.model.TeamImportsRep;
import com.launchdarkly.api.model.TeamMaintainers;
import com.launchdarkly.api.model.TeamPatchInput;
import com.launchdarkly.api.model.TeamPostInput;
import com.launchdarkly.api.model.Teams;
import com.launchdarkly.api.model.UnauthorizedErrorRep;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;

public class TeamsApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public TeamsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public TeamsApi(ApiClient apiClient) {
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
     * Build call for deleteTeam
     * @param teamKey The team key (required)
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
    public okhttp3.Call deleteTeamCall(String teamKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/teams/{teamKey}"
            .replaceAll("\\{" + "teamKey" + "\\}", localVarApiClient.escapeString(teamKey.toString()));

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
    private okhttp3.Call deleteTeamValidateBeforeCall(String teamKey, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'teamKey' is set
        if (teamKey == null) {
            throw new ApiException("Missing the required parameter 'teamKey' when calling deleteTeam(Async)");
        }
        

        okhttp3.Call localVarCall = deleteTeamCall(teamKey, _callback);
        return localVarCall;

    }

    /**
     * Delete team
     * Delete a team by key. To learn more, read [Deleting a team](https://docs.launchdarkly.com/home/teams/managing#deleting-a-team).
     * @param teamKey The team key (required)
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
    public void deleteTeam(String teamKey) throws ApiException {
        deleteTeamWithHttpInfo(teamKey);
    }

    /**
     * Delete team
     * Delete a team by key. To learn more, read [Deleting a team](https://docs.launchdarkly.com/home/teams/managing#deleting-a-team).
     * @param teamKey The team key (required)
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
    public ApiResponse<Void> deleteTeamWithHttpInfo(String teamKey) throws ApiException {
        okhttp3.Call localVarCall = deleteTeamValidateBeforeCall(teamKey, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete team (asynchronously)
     * Delete a team by key. To learn more, read [Deleting a team](https://docs.launchdarkly.com/home/teams/managing#deleting-a-team).
     * @param teamKey The team key (required)
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
    public okhttp3.Call deleteTeamAsync(String teamKey, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteTeamValidateBeforeCall(teamKey, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getTeam
     * @param teamKey The team key. (required)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Teams response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getTeamCall(String teamKey, String expand, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/teams/{teamKey}"
            .replaceAll("\\{" + "teamKey" + "\\}", localVarApiClient.escapeString(teamKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

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
    private okhttp3.Call getTeamValidateBeforeCall(String teamKey, String expand, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'teamKey' is set
        if (teamKey == null) {
            throw new ApiException("Missing the required parameter 'teamKey' when calling getTeam(Async)");
        }
        

        okhttp3.Call localVarCall = getTeamCall(teamKey, expand, _callback);
        return localVarCall;

    }

    /**
     * Get team
     * Fetch a team by key.  ### Expanding the teams response LaunchDarkly supports four fields for expanding the \&quot;Get team\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  * &#x60;members&#x60; includes the total count of members that belong to the team. * &#x60;roles&#x60; includes a paginated list of the custom roles that you have assigned to the team. * &#x60;projects&#x60; includes a paginated list of the projects that the team has any write access to. * &#x60;maintainers&#x60; includes a paginated list of the maintainers that you have assigned to the team.  For example, &#x60;expand&#x3D;members,roles&#x60; includes the &#x60;members&#x60; and &#x60;roles&#x60; fields in the response. 
     * @param teamKey The team key. (required)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. (optional)
     * @return Team
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Teams response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public Team getTeam(String teamKey, String expand) throws ApiException {
        ApiResponse<Team> localVarResp = getTeamWithHttpInfo(teamKey, expand);
        return localVarResp.getData();
    }

    /**
     * Get team
     * Fetch a team by key.  ### Expanding the teams response LaunchDarkly supports four fields for expanding the \&quot;Get team\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  * &#x60;members&#x60; includes the total count of members that belong to the team. * &#x60;roles&#x60; includes a paginated list of the custom roles that you have assigned to the team. * &#x60;projects&#x60; includes a paginated list of the projects that the team has any write access to. * &#x60;maintainers&#x60; includes a paginated list of the maintainers that you have assigned to the team.  For example, &#x60;expand&#x3D;members,roles&#x60; includes the &#x60;members&#x60; and &#x60;roles&#x60; fields in the response. 
     * @param teamKey The team key. (required)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. (optional)
     * @return ApiResponse&lt;Team&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Teams response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Team> getTeamWithHttpInfo(String teamKey, String expand) throws ApiException {
        okhttp3.Call localVarCall = getTeamValidateBeforeCall(teamKey, expand, null);
        Type localVarReturnType = new TypeToken<Team>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get team (asynchronously)
     * Fetch a team by key.  ### Expanding the teams response LaunchDarkly supports four fields for expanding the \&quot;Get team\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  * &#x60;members&#x60; includes the total count of members that belong to the team. * &#x60;roles&#x60; includes a paginated list of the custom roles that you have assigned to the team. * &#x60;projects&#x60; includes a paginated list of the projects that the team has any write access to. * &#x60;maintainers&#x60; includes a paginated list of the maintainers that you have assigned to the team.  For example, &#x60;expand&#x3D;members,roles&#x60; includes the &#x60;members&#x60; and &#x60;roles&#x60; fields in the response. 
     * @param teamKey The team key. (required)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Teams response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getTeamAsync(String teamKey, String expand, final ApiCallback<Team> _callback) throws ApiException {

        okhttp3.Call localVarCall = getTeamValidateBeforeCall(teamKey, expand, _callback);
        Type localVarReturnType = new TypeToken<Team>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getTeamMaintainers
     * @param teamKey The team key (required)
     * @param limit The number of maintainers to return in the response. Defaults to 20. (optional)
     * @param offset Where to start in the list. This is for use with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Team maintainers response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getTeamMaintainersCall(String teamKey, Long limit, Long offset, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/teams/{teamKey}/maintainers"
            .replaceAll("\\{" + "teamKey" + "\\}", localVarApiClient.escapeString(teamKey.toString()));

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

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getTeamMaintainersValidateBeforeCall(String teamKey, Long limit, Long offset, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'teamKey' is set
        if (teamKey == null) {
            throw new ApiException("Missing the required parameter 'teamKey' when calling getTeamMaintainers(Async)");
        }
        

        okhttp3.Call localVarCall = getTeamMaintainersCall(teamKey, limit, offset, _callback);
        return localVarCall;

    }

    /**
     * Get team maintainers
     * Fetch the maintainers that have been assigned to the team. To learn more, read [Managing team maintainers](https://docs.launchdarkly.com/home/teams/managing#managing-team-maintainers).
     * @param teamKey The team key (required)
     * @param limit The number of maintainers to return in the response. Defaults to 20. (optional)
     * @param offset Where to start in the list. This is for use with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @return TeamMaintainers
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Team maintainers response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public TeamMaintainers getTeamMaintainers(String teamKey, Long limit, Long offset) throws ApiException {
        ApiResponse<TeamMaintainers> localVarResp = getTeamMaintainersWithHttpInfo(teamKey, limit, offset);
        return localVarResp.getData();
    }

    /**
     * Get team maintainers
     * Fetch the maintainers that have been assigned to the team. To learn more, read [Managing team maintainers](https://docs.launchdarkly.com/home/teams/managing#managing-team-maintainers).
     * @param teamKey The team key (required)
     * @param limit The number of maintainers to return in the response. Defaults to 20. (optional)
     * @param offset Where to start in the list. This is for use with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @return ApiResponse&lt;TeamMaintainers&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Team maintainers response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<TeamMaintainers> getTeamMaintainersWithHttpInfo(String teamKey, Long limit, Long offset) throws ApiException {
        okhttp3.Call localVarCall = getTeamMaintainersValidateBeforeCall(teamKey, limit, offset, null);
        Type localVarReturnType = new TypeToken<TeamMaintainers>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get team maintainers (asynchronously)
     * Fetch the maintainers that have been assigned to the team. To learn more, read [Managing team maintainers](https://docs.launchdarkly.com/home/teams/managing#managing-team-maintainers).
     * @param teamKey The team key (required)
     * @param limit The number of maintainers to return in the response. Defaults to 20. (optional)
     * @param offset Where to start in the list. This is for use with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Team maintainers response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getTeamMaintainersAsync(String teamKey, Long limit, Long offset, final ApiCallback<TeamMaintainers> _callback) throws ApiException {

        okhttp3.Call localVarCall = getTeamMaintainersValidateBeforeCall(teamKey, limit, offset, _callback);
        Type localVarReturnType = new TypeToken<TeamMaintainers>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getTeamRoles
     * @param teamKey The team key (required)
     * @param limit The number of roles to return in the response. Defaults to 20. (optional)
     * @param offset Where to start in the list. This is for use with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Team roles response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getTeamRolesCall(String teamKey, Long limit, Long offset, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/teams/{teamKey}/roles"
            .replaceAll("\\{" + "teamKey" + "\\}", localVarApiClient.escapeString(teamKey.toString()));

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

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getTeamRolesValidateBeforeCall(String teamKey, Long limit, Long offset, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'teamKey' is set
        if (teamKey == null) {
            throw new ApiException("Missing the required parameter 'teamKey' when calling getTeamRoles(Async)");
        }
        

        okhttp3.Call localVarCall = getTeamRolesCall(teamKey, limit, offset, _callback);
        return localVarCall;

    }

    /**
     * Get team custom roles
     * Fetch the custom roles that have been assigned to the team. To learn more, read [Managing team permissions](https://docs.launchdarkly.com/home/teams/managing#managing-team-permissions).
     * @param teamKey The team key (required)
     * @param limit The number of roles to return in the response. Defaults to 20. (optional)
     * @param offset Where to start in the list. This is for use with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @return TeamCustomRoles
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Team roles response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public TeamCustomRoles getTeamRoles(String teamKey, Long limit, Long offset) throws ApiException {
        ApiResponse<TeamCustomRoles> localVarResp = getTeamRolesWithHttpInfo(teamKey, limit, offset);
        return localVarResp.getData();
    }

    /**
     * Get team custom roles
     * Fetch the custom roles that have been assigned to the team. To learn more, read [Managing team permissions](https://docs.launchdarkly.com/home/teams/managing#managing-team-permissions).
     * @param teamKey The team key (required)
     * @param limit The number of roles to return in the response. Defaults to 20. (optional)
     * @param offset Where to start in the list. This is for use with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @return ApiResponse&lt;TeamCustomRoles&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Team roles response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<TeamCustomRoles> getTeamRolesWithHttpInfo(String teamKey, Long limit, Long offset) throws ApiException {
        okhttp3.Call localVarCall = getTeamRolesValidateBeforeCall(teamKey, limit, offset, null);
        Type localVarReturnType = new TypeToken<TeamCustomRoles>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get team custom roles (asynchronously)
     * Fetch the custom roles that have been assigned to the team. To learn more, read [Managing team permissions](https://docs.launchdarkly.com/home/teams/managing#managing-team-permissions).
     * @param teamKey The team key (required)
     * @param limit The number of roles to return in the response. Defaults to 20. (optional)
     * @param offset Where to start in the list. This is for use with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Team roles response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getTeamRolesAsync(String teamKey, Long limit, Long offset, final ApiCallback<TeamCustomRoles> _callback) throws ApiException {

        okhttp3.Call localVarCall = getTeamRolesValidateBeforeCall(teamKey, limit, offset, _callback);
        Type localVarReturnType = new TypeToken<TeamCustomRoles>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getTeams
     * @param limit The number of teams to return in the response. Defaults to 20. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and returns the next &#x60;limit&#x60; items. (optional)
     * @param filter A comma-separated list of filters. Each filter is constructed as &#x60;field:value&#x60;. (optional)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Teams collection response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getTeamsCall(Long limit, Long offset, String filter, String expand, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/teams";

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

        if (filter != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("filter", filter));
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
    private okhttp3.Call getTeamsValidateBeforeCall(Long limit, Long offset, String filter, String expand, final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = getTeamsCall(limit, offset, filter, expand, _callback);
        return localVarCall;

    }

    /**
     * List teams
     * Return a list of teams.  By default, this returns the first 20 teams. Page through this list with the &#x60;limit&#x60; parameter and by following the &#x60;first&#x60;, &#x60;prev&#x60;, &#x60;next&#x60;, and &#x60;last&#x60; links in the &#x60;_links&#x60; field that returns. If those links do not appear, the pages they refer to don&#39;t exist. For example, the &#x60;first&#x60; and &#x60;prev&#x60; links will be missing from the response on the first page, because there is no previous page and you cannot return to the first page when you are already on the first page.  ### Filtering teams  LaunchDarkly supports the following fields for filters:  - &#x60;query&#x60; is a string that matches against the teams&#39; names and keys. It is not case-sensitive.   - A request with &#x60;query:abc&#x60; returns teams with the string &#x60;abc&#x60; in their name or key. - &#x60;nomembers&#x60; is a boolean that filters the list of teams who have 0 members   - A request with &#x60;nomembers:true&#x60; returns teams that have 0 members   - A request with &#x60;nomembers:false&#x60; returns teams that have 1 or more members  ### Expanding the teams response LaunchDarkly supports four fields for expanding the \&quot;List teams\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  * &#x60;members&#x60; includes the total count of members that belong to the team. * &#x60;roles&#x60; includes a paginated list of the custom roles that you have assigned to the team. * &#x60;projects&#x60; includes a paginated list of the projects that the team has any write access to. * &#x60;maintainers&#x60; includes a paginated list of the maintainers that you have assigned to the team.  For example, &#x60;expand&#x3D;members,roles&#x60; includes the &#x60;members&#x60; and &#x60;roles&#x60; fields in the response. 
     * @param limit The number of teams to return in the response. Defaults to 20. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and returns the next &#x60;limit&#x60; items. (optional)
     * @param filter A comma-separated list of filters. Each filter is constructed as &#x60;field:value&#x60;. (optional)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. (optional)
     * @return Teams
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Teams collection response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public Teams getTeams(Long limit, Long offset, String filter, String expand) throws ApiException {
        ApiResponse<Teams> localVarResp = getTeamsWithHttpInfo(limit, offset, filter, expand);
        return localVarResp.getData();
    }

    /**
     * List teams
     * Return a list of teams.  By default, this returns the first 20 teams. Page through this list with the &#x60;limit&#x60; parameter and by following the &#x60;first&#x60;, &#x60;prev&#x60;, &#x60;next&#x60;, and &#x60;last&#x60; links in the &#x60;_links&#x60; field that returns. If those links do not appear, the pages they refer to don&#39;t exist. For example, the &#x60;first&#x60; and &#x60;prev&#x60; links will be missing from the response on the first page, because there is no previous page and you cannot return to the first page when you are already on the first page.  ### Filtering teams  LaunchDarkly supports the following fields for filters:  - &#x60;query&#x60; is a string that matches against the teams&#39; names and keys. It is not case-sensitive.   - A request with &#x60;query:abc&#x60; returns teams with the string &#x60;abc&#x60; in their name or key. - &#x60;nomembers&#x60; is a boolean that filters the list of teams who have 0 members   - A request with &#x60;nomembers:true&#x60; returns teams that have 0 members   - A request with &#x60;nomembers:false&#x60; returns teams that have 1 or more members  ### Expanding the teams response LaunchDarkly supports four fields for expanding the \&quot;List teams\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  * &#x60;members&#x60; includes the total count of members that belong to the team. * &#x60;roles&#x60; includes a paginated list of the custom roles that you have assigned to the team. * &#x60;projects&#x60; includes a paginated list of the projects that the team has any write access to. * &#x60;maintainers&#x60; includes a paginated list of the maintainers that you have assigned to the team.  For example, &#x60;expand&#x3D;members,roles&#x60; includes the &#x60;members&#x60; and &#x60;roles&#x60; fields in the response. 
     * @param limit The number of teams to return in the response. Defaults to 20. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and returns the next &#x60;limit&#x60; items. (optional)
     * @param filter A comma-separated list of filters. Each filter is constructed as &#x60;field:value&#x60;. (optional)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. (optional)
     * @return ApiResponse&lt;Teams&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Teams collection response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Teams> getTeamsWithHttpInfo(Long limit, Long offset, String filter, String expand) throws ApiException {
        okhttp3.Call localVarCall = getTeamsValidateBeforeCall(limit, offset, filter, expand, null);
        Type localVarReturnType = new TypeToken<Teams>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List teams (asynchronously)
     * Return a list of teams.  By default, this returns the first 20 teams. Page through this list with the &#x60;limit&#x60; parameter and by following the &#x60;first&#x60;, &#x60;prev&#x60;, &#x60;next&#x60;, and &#x60;last&#x60; links in the &#x60;_links&#x60; field that returns. If those links do not appear, the pages they refer to don&#39;t exist. For example, the &#x60;first&#x60; and &#x60;prev&#x60; links will be missing from the response on the first page, because there is no previous page and you cannot return to the first page when you are already on the first page.  ### Filtering teams  LaunchDarkly supports the following fields for filters:  - &#x60;query&#x60; is a string that matches against the teams&#39; names and keys. It is not case-sensitive.   - A request with &#x60;query:abc&#x60; returns teams with the string &#x60;abc&#x60; in their name or key. - &#x60;nomembers&#x60; is a boolean that filters the list of teams who have 0 members   - A request with &#x60;nomembers:true&#x60; returns teams that have 0 members   - A request with &#x60;nomembers:false&#x60; returns teams that have 1 or more members  ### Expanding the teams response LaunchDarkly supports four fields for expanding the \&quot;List teams\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  * &#x60;members&#x60; includes the total count of members that belong to the team. * &#x60;roles&#x60; includes a paginated list of the custom roles that you have assigned to the team. * &#x60;projects&#x60; includes a paginated list of the projects that the team has any write access to. * &#x60;maintainers&#x60; includes a paginated list of the maintainers that you have assigned to the team.  For example, &#x60;expand&#x3D;members,roles&#x60; includes the &#x60;members&#x60; and &#x60;roles&#x60; fields in the response. 
     * @param limit The number of teams to return in the response. Defaults to 20. (optional)
     * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and returns the next &#x60;limit&#x60; items. (optional)
     * @param filter A comma-separated list of filters. Each filter is constructed as &#x60;field:value&#x60;. (optional)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Teams collection response </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getTeamsAsync(Long limit, Long offset, String filter, String expand, final ApiCallback<Teams> _callback) throws ApiException {

        okhttp3.Call localVarCall = getTeamsValidateBeforeCall(limit, offset, filter, expand, _callback);
        Type localVarReturnType = new TypeToken<Teams>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for patchTeam
     * @param teamKey The team key (required)
     * @param teamPatchInput  (required)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Teams response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchTeamCall(String teamKey, TeamPatchInput teamPatchInput, String expand, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = teamPatchInput;

        // create path and map variables
        String localVarPath = "/api/v2/teams/{teamKey}"
            .replaceAll("\\{" + "teamKey" + "\\}", localVarApiClient.escapeString(teamKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

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
    private okhttp3.Call patchTeamValidateBeforeCall(String teamKey, TeamPatchInput teamPatchInput, String expand, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'teamKey' is set
        if (teamKey == null) {
            throw new ApiException("Missing the required parameter 'teamKey' when calling patchTeam(Async)");
        }
        
        // verify the required parameter 'teamPatchInput' is set
        if (teamPatchInput == null) {
            throw new ApiException("Missing the required parameter 'teamPatchInput' when calling patchTeam(Async)");
        }
        

        okhttp3.Call localVarCall = patchTeamCall(teamKey, teamPatchInput, expand, _callback);
        return localVarCall;

    }

    /**
     * Update team
     * Perform a partial update to a team. Updating a team uses the semantic patch format.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating teams.  #### addCustomRoles  Adds custom roles to the team. Team members will have these custom roles granted to them.  ##### Parameters  - &#x60;values&#x60;: List of custom role keys.  #### removeCustomRoles  Removes custom roles from the team. The app will no longer grant these custom roles to the team members.  ##### Parameters  - &#x60;values&#x60;: List of custom role keys.  #### addMembers  Adds members to the team.  ##### Parameters  - &#x60;values&#x60;: List of member IDs.  #### removeMembers  Removes members from the team.  ##### Parameters  - &#x60;values&#x60;: List of member IDs.  #### addPermissionGrants  Adds permission grants to members for the team. For example, a permission grant could allow a member to act as a team maintainer. A permission grant may have either an &#x60;actionSet&#x60; or a list of &#x60;actions&#x60; but not both at the same time. The members do not have to be team members to have a permission grant for the team.  ##### Parameters  - &#x60;actionSet&#x60;: Name of the action set. - &#x60;actions&#x60;: List of actions. - &#x60;memberIDs&#x60;: List of member IDs.  #### removePermissionGrants  Removes permission grants from members for the team. The &#x60;actionSet&#x60; and &#x60;actions&#x60; must match an existing permission grant.  ##### Parameters  - &#x60;actionSet&#x60;: Name of the action set. - &#x60;actions&#x60;: List of actions. - &#x60;memberIDs&#x60;: List of member IDs.  #### updateDescription  Updates the description of the team.  ##### Parameters  - &#x60;value&#x60;: The new description.  #### updateName  Updates the name of the team.  ##### Parameters  - &#x60;value&#x60;: The new name.  ### Expanding the teams response LaunchDarkly supports four fields for expanding the \&quot;Update team\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  * &#x60;members&#x60; includes the total count of members that belong to the team. * &#x60;roles&#x60; includes a paginated list of the custom roles that you have assigned to the team. * &#x60;projects&#x60; includes a paginated list of the projects that the team has any write access to. * &#x60;maintainers&#x60; includes a paginated list of the maintainers that you have assigned to the team.  For example, &#x60;expand&#x3D;members,roles&#x60; includes the &#x60;members&#x60; and &#x60;roles&#x60; fields in the response. 
     * @param teamKey The team key (required)
     * @param teamPatchInput  (required)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above. (optional)
     * @return Team
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Teams response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public Team patchTeam(String teamKey, TeamPatchInput teamPatchInput, String expand) throws ApiException {
        ApiResponse<Team> localVarResp = patchTeamWithHttpInfo(teamKey, teamPatchInput, expand);
        return localVarResp.getData();
    }

    /**
     * Update team
     * Perform a partial update to a team. Updating a team uses the semantic patch format.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating teams.  #### addCustomRoles  Adds custom roles to the team. Team members will have these custom roles granted to them.  ##### Parameters  - &#x60;values&#x60;: List of custom role keys.  #### removeCustomRoles  Removes custom roles from the team. The app will no longer grant these custom roles to the team members.  ##### Parameters  - &#x60;values&#x60;: List of custom role keys.  #### addMembers  Adds members to the team.  ##### Parameters  - &#x60;values&#x60;: List of member IDs.  #### removeMembers  Removes members from the team.  ##### Parameters  - &#x60;values&#x60;: List of member IDs.  #### addPermissionGrants  Adds permission grants to members for the team. For example, a permission grant could allow a member to act as a team maintainer. A permission grant may have either an &#x60;actionSet&#x60; or a list of &#x60;actions&#x60; but not both at the same time. The members do not have to be team members to have a permission grant for the team.  ##### Parameters  - &#x60;actionSet&#x60;: Name of the action set. - &#x60;actions&#x60;: List of actions. - &#x60;memberIDs&#x60;: List of member IDs.  #### removePermissionGrants  Removes permission grants from members for the team. The &#x60;actionSet&#x60; and &#x60;actions&#x60; must match an existing permission grant.  ##### Parameters  - &#x60;actionSet&#x60;: Name of the action set. - &#x60;actions&#x60;: List of actions. - &#x60;memberIDs&#x60;: List of member IDs.  #### updateDescription  Updates the description of the team.  ##### Parameters  - &#x60;value&#x60;: The new description.  #### updateName  Updates the name of the team.  ##### Parameters  - &#x60;value&#x60;: The new name.  ### Expanding the teams response LaunchDarkly supports four fields for expanding the \&quot;Update team\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  * &#x60;members&#x60; includes the total count of members that belong to the team. * &#x60;roles&#x60; includes a paginated list of the custom roles that you have assigned to the team. * &#x60;projects&#x60; includes a paginated list of the projects that the team has any write access to. * &#x60;maintainers&#x60; includes a paginated list of the maintainers that you have assigned to the team.  For example, &#x60;expand&#x3D;members,roles&#x60; includes the &#x60;members&#x60; and &#x60;roles&#x60; fields in the response. 
     * @param teamKey The team key (required)
     * @param teamPatchInput  (required)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above. (optional)
     * @return ApiResponse&lt;Team&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Teams response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Team> patchTeamWithHttpInfo(String teamKey, TeamPatchInput teamPatchInput, String expand) throws ApiException {
        okhttp3.Call localVarCall = patchTeamValidateBeforeCall(teamKey, teamPatchInput, expand, null);
        Type localVarReturnType = new TypeToken<Team>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update team (asynchronously)
     * Perform a partial update to a team. Updating a team uses the semantic patch format.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating teams.  #### addCustomRoles  Adds custom roles to the team. Team members will have these custom roles granted to them.  ##### Parameters  - &#x60;values&#x60;: List of custom role keys.  #### removeCustomRoles  Removes custom roles from the team. The app will no longer grant these custom roles to the team members.  ##### Parameters  - &#x60;values&#x60;: List of custom role keys.  #### addMembers  Adds members to the team.  ##### Parameters  - &#x60;values&#x60;: List of member IDs.  #### removeMembers  Removes members from the team.  ##### Parameters  - &#x60;values&#x60;: List of member IDs.  #### addPermissionGrants  Adds permission grants to members for the team. For example, a permission grant could allow a member to act as a team maintainer. A permission grant may have either an &#x60;actionSet&#x60; or a list of &#x60;actions&#x60; but not both at the same time. The members do not have to be team members to have a permission grant for the team.  ##### Parameters  - &#x60;actionSet&#x60;: Name of the action set. - &#x60;actions&#x60;: List of actions. - &#x60;memberIDs&#x60;: List of member IDs.  #### removePermissionGrants  Removes permission grants from members for the team. The &#x60;actionSet&#x60; and &#x60;actions&#x60; must match an existing permission grant.  ##### Parameters  - &#x60;actionSet&#x60;: Name of the action set. - &#x60;actions&#x60;: List of actions. - &#x60;memberIDs&#x60;: List of member IDs.  #### updateDescription  Updates the description of the team.  ##### Parameters  - &#x60;value&#x60;: The new description.  #### updateName  Updates the name of the team.  ##### Parameters  - &#x60;value&#x60;: The new name.  ### Expanding the teams response LaunchDarkly supports four fields for expanding the \&quot;Update team\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  * &#x60;members&#x60; includes the total count of members that belong to the team. * &#x60;roles&#x60; includes a paginated list of the custom roles that you have assigned to the team. * &#x60;projects&#x60; includes a paginated list of the projects that the team has any write access to. * &#x60;maintainers&#x60; includes a paginated list of the maintainers that you have assigned to the team.  For example, &#x60;expand&#x3D;members,roles&#x60; includes the &#x60;members&#x60; and &#x60;roles&#x60; fields in the response. 
     * @param teamKey The team key (required)
     * @param teamPatchInput  (required)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Teams response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Invalid resource identifier </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Status conflict </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call patchTeamAsync(String teamKey, TeamPatchInput teamPatchInput, String expand, final ApiCallback<Team> _callback) throws ApiException {

        okhttp3.Call localVarCall = patchTeamValidateBeforeCall(teamKey, teamPatchInput, expand, _callback);
        Type localVarReturnType = new TypeToken<Team>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for postTeam
     * @param teamPostInput  (required)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Teams response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postTeamCall(TeamPostInput teamPostInput, String expand, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = teamPostInput;

        // create path and map variables
        String localVarPath = "/api/v2/teams";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

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
    private okhttp3.Call postTeamValidateBeforeCall(TeamPostInput teamPostInput, String expand, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'teamPostInput' is set
        if (teamPostInput == null) {
            throw new ApiException("Missing the required parameter 'teamPostInput' when calling postTeam(Async)");
        }
        

        okhttp3.Call localVarCall = postTeamCall(teamPostInput, expand, _callback);
        return localVarCall;

    }

    /**
     * Create team
     * Create a team. To learn more, read [Creating a team](https://docs.launchdarkly.com/home/teams/creating).  ### Expanding the teams response LaunchDarkly supports four fields for expanding the \&quot;Create team\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  * &#x60;members&#x60; includes the total count of members that belong to the team. * &#x60;roles&#x60; includes a paginated list of the custom roles that you have assigned to the team. * &#x60;projects&#x60; includes a paginated list of the projects that the team has any write access to. * &#x60;maintainers&#x60; includes a paginated list of the maintainers that you have assigned to the team.  For example, &#x60;expand&#x3D;members,roles&#x60; includes the &#x60;members&#x60; and &#x60;roles&#x60; fields in the response. 
     * @param teamPostInput  (required)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above. (optional)
     * @return Team
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Teams response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public Team postTeam(TeamPostInput teamPostInput, String expand) throws ApiException {
        ApiResponse<Team> localVarResp = postTeamWithHttpInfo(teamPostInput, expand);
        return localVarResp.getData();
    }

    /**
     * Create team
     * Create a team. To learn more, read [Creating a team](https://docs.launchdarkly.com/home/teams/creating).  ### Expanding the teams response LaunchDarkly supports four fields for expanding the \&quot;Create team\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  * &#x60;members&#x60; includes the total count of members that belong to the team. * &#x60;roles&#x60; includes a paginated list of the custom roles that you have assigned to the team. * &#x60;projects&#x60; includes a paginated list of the projects that the team has any write access to. * &#x60;maintainers&#x60; includes a paginated list of the maintainers that you have assigned to the team.  For example, &#x60;expand&#x3D;members,roles&#x60; includes the &#x60;members&#x60; and &#x60;roles&#x60; fields in the response. 
     * @param teamPostInput  (required)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above. (optional)
     * @return ApiResponse&lt;Team&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Teams response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Team> postTeamWithHttpInfo(TeamPostInput teamPostInput, String expand) throws ApiException {
        okhttp3.Call localVarCall = postTeamValidateBeforeCall(teamPostInput, expand, null);
        Type localVarReturnType = new TypeToken<Team>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create team (asynchronously)
     * Create a team. To learn more, read [Creating a team](https://docs.launchdarkly.com/home/teams/creating).  ### Expanding the teams response LaunchDarkly supports four fields for expanding the \&quot;Create team\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  * &#x60;members&#x60; includes the total count of members that belong to the team. * &#x60;roles&#x60; includes a paginated list of the custom roles that you have assigned to the team. * &#x60;projects&#x60; includes a paginated list of the projects that the team has any write access to. * &#x60;maintainers&#x60; includes a paginated list of the maintainers that you have assigned to the team.  For example, &#x60;expand&#x3D;members,roles&#x60; includes the &#x60;members&#x60; and &#x60;roles&#x60; fields in the response. 
     * @param teamPostInput  (required)
     * @param expand A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Teams response </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postTeamAsync(TeamPostInput teamPostInput, String expand, final ApiCallback<Team> _callback) throws ApiException {

        okhttp3.Call localVarCall = postTeamValidateBeforeCall(teamPostInput, expand, _callback);
        Type localVarReturnType = new TypeToken<Team>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for postTeamMembers
     * @param teamKey The team key (required)
     * @param _file CSV file containing email addresses (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Team member imports response </td><td>  -  </td></tr>
        <tr><td> 207 </td><td> Partial Success </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postTeamMembersCall(String teamKey, File _file, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/teams/{teamKey}/members"
            .replaceAll("\\{" + "teamKey" + "\\}", localVarApiClient.escapeString(teamKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (_file != null) {
            localVarFormParams.put("file", _file);
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "multipart/form-data"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call postTeamMembersValidateBeforeCall(String teamKey, File _file, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'teamKey' is set
        if (teamKey == null) {
            throw new ApiException("Missing the required parameter 'teamKey' when calling postTeamMembers(Async)");
        }
        

        okhttp3.Call localVarCall = postTeamMembersCall(teamKey, _file, _callback);
        return localVarCall;

    }

    /**
     * Add multiple members to team
     * Add multiple members to an existing team by uploading a CSV file of member email addresses. Your CSV file must include email addresses in the first column. You can include data in additional columns, but LaunchDarkly ignores all data outside the first column. Headers are optional. To learn more, read [Managing team members](https://docs.launchdarkly.com/home/teams/managing#managing-team-members).  **Members are only added on a &#x60;201&#x60; response.** A &#x60;207&#x60; indicates the CSV file contains a combination of valid and invalid entries. A &#x60;207&#x60; results in no members being added to the team.  On a &#x60;207&#x60; response, if an entry contains bad user input, the &#x60;message&#x60; field contains the row number as well as the reason for the error. The &#x60;message&#x60; field is omitted if the entry is valid.  Example &#x60;207&#x60; response: &#x60;&#x60;&#x60;json {   \&quot;items\&quot;: [     {       \&quot;status\&quot;: \&quot;success\&quot;,       \&quot;value\&quot;: \&quot;a-valid-email@launchdarkly.com\&quot;     },     {       \&quot;message\&quot;: \&quot;Line 2: empty row\&quot;,       \&quot;status\&quot;: \&quot;error\&quot;,       \&quot;value\&quot;: \&quot;\&quot;     },     {       \&quot;message\&quot;: \&quot;Line 3: email already exists in the specified team\&quot;,       \&quot;status\&quot;: \&quot;error\&quot;,       \&quot;value\&quot;: \&quot;existing-team-member@launchdarkly.com\&quot;     },     {       \&quot;message\&quot;: \&quot;Line 4: invalid email formatting\&quot;,       \&quot;status\&quot;: \&quot;error\&quot;,       \&quot;value\&quot;: \&quot;invalid email format\&quot;     }   ] } &#x60;&#x60;&#x60;  Message | Resolution --- | --- Empty row | This line is blank. Add an email address and try again. Duplicate entry | This email address appears in the file twice. Remove the email from the file and try again. Email already exists in the specified team | This member is already on your team. Remove the email from the file and try again. Invalid formatting | This email address is not formatted correctly. Fix the formatting and try again. Email does not belong to a LaunchDarkly member | The email address doesn&#39;t belong to a LaunchDarkly account member. Invite them to LaunchDarkly, then re-add them to the team.  On a &#x60;400&#x60; response, the &#x60;message&#x60; field may contain errors specific to this endpoint.  Example &#x60;400&#x60; response: &#x60;&#x60;&#x60;json {   \&quot;code\&quot;: \&quot;invalid_request\&quot;,   \&quot;message\&quot;: \&quot;Unable to process file\&quot; } &#x60;&#x60;&#x60;  Message | Resolution --- | --- Unable to process file | LaunchDarkly could not process the file for an unspecified reason. Review your file for errors and try again. File exceeds 25mb | Break up your file into multiple files of less than 25mbs each. All emails have invalid formatting | None of the email addresses in the file are in the correct format. Fix the formatting and try again. All emails belong to existing team members | All listed members are already on this team. Populate the file with member emails that do not belong to the team and try again. File is empty | The CSV file does not contain any email addresses. Populate the file and try again. No emails belong to members of your LaunchDarkly organization | None of the email addresses belong to members of your LaunchDarkly account. Invite these members to LaunchDarkly, then re-add them to the team. 
     * @param teamKey The team key (required)
     * @param _file CSV file containing email addresses (optional)
     * @return TeamImportsRep
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Team member imports response </td><td>  -  </td></tr>
        <tr><td> 207 </td><td> Partial Success </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public TeamImportsRep postTeamMembers(String teamKey, File _file) throws ApiException {
        ApiResponse<TeamImportsRep> localVarResp = postTeamMembersWithHttpInfo(teamKey, _file);
        return localVarResp.getData();
    }

    /**
     * Add multiple members to team
     * Add multiple members to an existing team by uploading a CSV file of member email addresses. Your CSV file must include email addresses in the first column. You can include data in additional columns, but LaunchDarkly ignores all data outside the first column. Headers are optional. To learn more, read [Managing team members](https://docs.launchdarkly.com/home/teams/managing#managing-team-members).  **Members are only added on a &#x60;201&#x60; response.** A &#x60;207&#x60; indicates the CSV file contains a combination of valid and invalid entries. A &#x60;207&#x60; results in no members being added to the team.  On a &#x60;207&#x60; response, if an entry contains bad user input, the &#x60;message&#x60; field contains the row number as well as the reason for the error. The &#x60;message&#x60; field is omitted if the entry is valid.  Example &#x60;207&#x60; response: &#x60;&#x60;&#x60;json {   \&quot;items\&quot;: [     {       \&quot;status\&quot;: \&quot;success\&quot;,       \&quot;value\&quot;: \&quot;a-valid-email@launchdarkly.com\&quot;     },     {       \&quot;message\&quot;: \&quot;Line 2: empty row\&quot;,       \&quot;status\&quot;: \&quot;error\&quot;,       \&quot;value\&quot;: \&quot;\&quot;     },     {       \&quot;message\&quot;: \&quot;Line 3: email already exists in the specified team\&quot;,       \&quot;status\&quot;: \&quot;error\&quot;,       \&quot;value\&quot;: \&quot;existing-team-member@launchdarkly.com\&quot;     },     {       \&quot;message\&quot;: \&quot;Line 4: invalid email formatting\&quot;,       \&quot;status\&quot;: \&quot;error\&quot;,       \&quot;value\&quot;: \&quot;invalid email format\&quot;     }   ] } &#x60;&#x60;&#x60;  Message | Resolution --- | --- Empty row | This line is blank. Add an email address and try again. Duplicate entry | This email address appears in the file twice. Remove the email from the file and try again. Email already exists in the specified team | This member is already on your team. Remove the email from the file and try again. Invalid formatting | This email address is not formatted correctly. Fix the formatting and try again. Email does not belong to a LaunchDarkly member | The email address doesn&#39;t belong to a LaunchDarkly account member. Invite them to LaunchDarkly, then re-add them to the team.  On a &#x60;400&#x60; response, the &#x60;message&#x60; field may contain errors specific to this endpoint.  Example &#x60;400&#x60; response: &#x60;&#x60;&#x60;json {   \&quot;code\&quot;: \&quot;invalid_request\&quot;,   \&quot;message\&quot;: \&quot;Unable to process file\&quot; } &#x60;&#x60;&#x60;  Message | Resolution --- | --- Unable to process file | LaunchDarkly could not process the file for an unspecified reason. Review your file for errors and try again. File exceeds 25mb | Break up your file into multiple files of less than 25mbs each. All emails have invalid formatting | None of the email addresses in the file are in the correct format. Fix the formatting and try again. All emails belong to existing team members | All listed members are already on this team. Populate the file with member emails that do not belong to the team and try again. File is empty | The CSV file does not contain any email addresses. Populate the file and try again. No emails belong to members of your LaunchDarkly organization | None of the email addresses belong to members of your LaunchDarkly account. Invite these members to LaunchDarkly, then re-add them to the team. 
     * @param teamKey The team key (required)
     * @param _file CSV file containing email addresses (optional)
     * @return ApiResponse&lt;TeamImportsRep&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Team member imports response </td><td>  -  </td></tr>
        <tr><td> 207 </td><td> Partial Success </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<TeamImportsRep> postTeamMembersWithHttpInfo(String teamKey, File _file) throws ApiException {
        okhttp3.Call localVarCall = postTeamMembersValidateBeforeCall(teamKey, _file, null);
        Type localVarReturnType = new TypeToken<TeamImportsRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Add multiple members to team (asynchronously)
     * Add multiple members to an existing team by uploading a CSV file of member email addresses. Your CSV file must include email addresses in the first column. You can include data in additional columns, but LaunchDarkly ignores all data outside the first column. Headers are optional. To learn more, read [Managing team members](https://docs.launchdarkly.com/home/teams/managing#managing-team-members).  **Members are only added on a &#x60;201&#x60; response.** A &#x60;207&#x60; indicates the CSV file contains a combination of valid and invalid entries. A &#x60;207&#x60; results in no members being added to the team.  On a &#x60;207&#x60; response, if an entry contains bad user input, the &#x60;message&#x60; field contains the row number as well as the reason for the error. The &#x60;message&#x60; field is omitted if the entry is valid.  Example &#x60;207&#x60; response: &#x60;&#x60;&#x60;json {   \&quot;items\&quot;: [     {       \&quot;status\&quot;: \&quot;success\&quot;,       \&quot;value\&quot;: \&quot;a-valid-email@launchdarkly.com\&quot;     },     {       \&quot;message\&quot;: \&quot;Line 2: empty row\&quot;,       \&quot;status\&quot;: \&quot;error\&quot;,       \&quot;value\&quot;: \&quot;\&quot;     },     {       \&quot;message\&quot;: \&quot;Line 3: email already exists in the specified team\&quot;,       \&quot;status\&quot;: \&quot;error\&quot;,       \&quot;value\&quot;: \&quot;existing-team-member@launchdarkly.com\&quot;     },     {       \&quot;message\&quot;: \&quot;Line 4: invalid email formatting\&quot;,       \&quot;status\&quot;: \&quot;error\&quot;,       \&quot;value\&quot;: \&quot;invalid email format\&quot;     }   ] } &#x60;&#x60;&#x60;  Message | Resolution --- | --- Empty row | This line is blank. Add an email address and try again. Duplicate entry | This email address appears in the file twice. Remove the email from the file and try again. Email already exists in the specified team | This member is already on your team. Remove the email from the file and try again. Invalid formatting | This email address is not formatted correctly. Fix the formatting and try again. Email does not belong to a LaunchDarkly member | The email address doesn&#39;t belong to a LaunchDarkly account member. Invite them to LaunchDarkly, then re-add them to the team.  On a &#x60;400&#x60; response, the &#x60;message&#x60; field may contain errors specific to this endpoint.  Example &#x60;400&#x60; response: &#x60;&#x60;&#x60;json {   \&quot;code\&quot;: \&quot;invalid_request\&quot;,   \&quot;message\&quot;: \&quot;Unable to process file\&quot; } &#x60;&#x60;&#x60;  Message | Resolution --- | --- Unable to process file | LaunchDarkly could not process the file for an unspecified reason. Review your file for errors and try again. File exceeds 25mb | Break up your file into multiple files of less than 25mbs each. All emails have invalid formatting | None of the email addresses in the file are in the correct format. Fix the formatting and try again. All emails belong to existing team members | All listed members are already on this team. Populate the file with member emails that do not belong to the team and try again. File is empty | The CSV file does not contain any email addresses. Populate the file and try again. No emails belong to members of your LaunchDarkly organization | None of the email addresses belong to members of your LaunchDarkly account. Invite these members to LaunchDarkly, then re-add them to the team. 
     * @param teamKey The team key (required)
     * @param _file CSV file containing email addresses (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Team member imports response </td><td>  -  </td></tr>
        <tr><td> 207 </td><td> Partial Success </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Invalid access token </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Method not allowed </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Rate limited </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call postTeamMembersAsync(String teamKey, File _file, final ApiCallback<TeamImportsRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = postTeamMembersValidateBeforeCall(teamKey, _file, _callback);
        Type localVarReturnType = new TypeToken<TeamImportsRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
