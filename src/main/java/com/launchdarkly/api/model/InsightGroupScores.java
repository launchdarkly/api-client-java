/*
 * LaunchDarkly REST API
 * # Overview  ## Authentication  LaunchDarkly's REST API uses the HTTPS protocol with a minimum TLS version of 1.2.  All REST API resources are authenticated with either [personal or service access tokens](https://docs.launchdarkly.com/home/account-security/api-access-tokens), or session cookies. Other authentication mechanisms are not supported. You can manage personal access tokens on your [**Account settings**](https://app.launchdarkly.com/settings/tokens) page.  LaunchDarkly also has SDK keys, mobile keys, and client-side IDs that are used by our server-side SDKs, mobile SDKs, and JavaScript-based SDKs, respectively. **These keys cannot be used to access our REST API**. These keys are environment-specific, and can only perform read-only operations such as fetching feature flag settings.  | Auth mechanism                                                                                  | Allowed resources                                                                                     | Use cases                                          | | ----------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------- | -------------------------------------------------- | | [Personal or service access tokens](https://docs.launchdarkly.com/home/account-security/api-access-tokens) | Can be customized on a per-token basis                                                                | Building scripts, custom integrations, data export. | | SDK keys                                                                                        | Can only access read-only resources specific to server-side SDKs. Restricted to a single environment. | Server-side SDKs                     | | Mobile keys                                                                                     | Can only access read-only resources specific to mobile SDKs, and only for flags marked available to mobile keys. Restricted to a single environment.           | Mobile SDKs                                        | | Client-side ID                                                                                  | Can only access read-only resources specific to JavaScript-based client-side SDKs, and only for flags marked available to client-side. Restricted to a single environment.           | Client-side JavaScript                             |  > #### Keep your access tokens and SDK keys private > > Access tokens should _never_ be exposed in untrusted contexts. Never put an access token in client-side JavaScript, or embed it in a mobile application. LaunchDarkly has special mobile keys that you can embed in mobile apps. If you accidentally expose an access token or SDK key, you can reset it from your [**Account settings**](https://app.launchdarkly.com/settings/tokens) page. > > The client-side ID is safe to embed in untrusted contexts. It's designed for use in client-side JavaScript.  ### Authentication using request header  The preferred way to authenticate with the API is by adding an `Authorization` header containing your access token to your requests. The value of the `Authorization` header must be your access token.  Manage personal access tokens from the [**Account settings**](https://app.launchdarkly.com/settings/tokens) page.  ### Authentication using session cookie  For testing purposes, you can make API calls directly from your web browser. If you are logged in to the LaunchDarkly application, the API will use your existing session to authenticate calls.  If you have a [role](https://docs.launchdarkly.com/home/team/built-in-roles) other than Admin, or have a [custom role](https://docs.launchdarkly.com/home/team/custom-roles) defined, you may not have permission to perform some API calls. You will receive a `401` response code in that case.  > ### Modifying the Origin header causes an error > > LaunchDarkly validates that the Origin header for any API request authenticated by a session cookie matches the expected Origin header. The expected Origin header is `https://app.launchdarkly.com`. > > If the Origin header does not match what's expected, LaunchDarkly returns an error. This error can prevent the LaunchDarkly app from working correctly. > > Any browser extension that intentionally changes the Origin header can cause this problem. For example, the `Allow-Control-Allow-Origin: *` Chrome extension changes the Origin header to `http://evil.com` and causes the app to fail. > > To prevent this error, do not modify your Origin header. > > LaunchDarkly does not require origin matching when authenticating with an access token, so this issue does not affect normal API usage.  ## Representations  All resources expect and return JSON response bodies. Error responses also send a JSON body. To learn more about the error format of the API, read [Errors](/#section/Overview/Errors).  In practice this means that you always get a response with a `Content-Type` header set to `application/json`.  In addition, request bodies for `PATCH`, `POST`, and `PUT` requests must be encoded as JSON with a `Content-Type` header set to `application/json`.  ### Summary and detailed representations  When you fetch a list of resources, the response includes only the most important attributes of each resource. This is a _summary representation_ of the resource. When you fetch an individual resource, such as a single feature flag, you receive a _detailed representation_ of the resource.  The best way to find a detailed representation is to follow links. Every summary representation includes a link to its detailed representation.  ### Expanding responses  Sometimes the detailed representation of a resource does not include all of the attributes of the resource by default. If this is the case, the request method will clearly document this and describe which attributes you can include in an expanded response.  To include the additional attributes, append the `expand` request parameter to your request and add a comma-separated list of the attributes to include. For example, when you append `?expand=members,roles` to the [Get team](/tag/Teams#operation/getTeam) endpoint, the expanded response includes both of these attributes.  ### Links and addressability  The best way to navigate the API is by following links. These are attributes in representations that link to other resources. The API always uses the same format for links:  - Links to other resources within the API are encapsulated in a `_links` object - If the resource has a corresponding link to HTML content on the site, it is stored in a special `_site` link  Each link has two attributes:  - An `href`, which contains the URL - A `type`, which describes the content type  For example, a feature resource might return the following:  ```json {   \"_links\": {     \"parent\": {       \"href\": \"/api/features\",       \"type\": \"application/json\"     },     \"self\": {       \"href\": \"/api/features/sort.order\",       \"type\": \"application/json\"     }   },   \"_site\": {     \"href\": \"/features/sort.order\",     \"type\": \"text/html\"   } } ```  From this, you can navigate to the parent collection of features by following the `parent` link, or navigate to the site page for the feature by following the `_site` link.  Collections are always represented as a JSON object with an `items` attribute containing an array of representations. Like all other representations, collections have `_links` defined at the top level.  Paginated collections include `first`, `last`, `next`, and `prev` links containing a URL with the respective set of elements in the collection.  ## Updates  Resources that accept partial updates use the `PATCH` verb. Most resources support the [JSON patch](/reference#updates-using-json-patch) format. Some resources also support the [JSON merge patch](/reference#updates-using-json-merge-patch) format, and some resources support the [semantic patch](/reference#updates-using-semantic-patch) format, which is a way to specify the modifications to perform as a set of executable instructions. Each resource supports optional [comments](/reference#updates-with-comments) that you can submit with updates. Comments appear in outgoing webhooks, the audit log, and other integrations.  When a resource supports both JSON patch and semantic patch, we document both in the request method. However, the specific request body fields and descriptions included in our documentation only match one type of patch or the other.  ### Updates using JSON patch  [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) is a way to specify the modifications to perform on a resource. JSON patch uses paths and a limited set of operations to describe how to transform the current state of the resource into a new state. JSON patch documents are always arrays, where each element contains an operation, a path to the field to update, and the new value.  For example, in this feature flag representation:  ```json {     \"name\": \"New recommendations engine\",     \"key\": \"engine.enable\",     \"description\": \"This is the description\",     ... } ``` You can change the feature flag's description with the following patch document:  ```json [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"This is the new description\" }] ```  You can specify multiple modifications to perform in a single request. You can also test that certain preconditions are met before applying the patch:  ```json [   { \"op\": \"test\", \"path\": \"/version\", \"value\": 10 },   { \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" } ] ```  The above patch request tests whether the feature flag's `version` is `10`, and if so, changes the feature flag's description.  Attributes that are not editable, such as a resource's `_links`, have names that start with an underscore.  ### Updates using JSON merge patch  [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) is another format for specifying the modifications to perform on a resource. JSON merge patch is less expressive than JSON patch. However, in many cases it is simpler to construct a merge patch document. For example, you can change a feature flag's description with the following merge patch document:  ```json {   \"description\": \"New flag description\" } ```  ### Updates using semantic patch  Some resources support the semantic patch format. A semantic patch is a way to specify the modifications to perform on a resource as a set of executable instructions.  Semantic patch allows you to be explicit about intent using precise, custom instructions. In many cases, you can define semantic patch instructions independently of the current state of the resource. This can be useful when defining a change that may be applied at a future date.  To make a semantic patch request, you must append `domain-model=launchdarkly.semanticpatch` to your `Content-Type` header.  Here's how:  ``` Content-Type: application/json; domain-model=launchdarkly.semanticpatch ```  If you call a semantic patch resource without this header, you will receive a `400` response because your semantic patch will be interpreted as a JSON patch.  The body of a semantic patch request takes the following properties:  * `comment` (string): (Optional) A description of the update. * `environmentKey` (string): (Required for some resources only) The environment key. * `instructions` (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a `kind` property that indicates the instruction. If the instruction requires parameters, you must include those parameters as additional fields in the object. The documentation for each resource that supports semantic patch includes the available instructions and any additional parameters.  For example:  ```json {   \"comment\": \"optional comment\",   \"instructions\": [ {\"kind\": \"turnFlagOn\"} ] } ```  If any instruction in the patch encounters an error, the endpoint returns an error and will not change the resource. In general, each instruction silently does nothing if the resource is already in the state you request.  ### Updates with comments  You can submit optional comments with `PATCH` changes.  To submit a comment along with a JSON patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"patch\": [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" }] } ```  To submit a comment along with a JSON merge patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"merge\": { \"description\": \"New flag description\" } } ```  To submit a comment along with a semantic patch, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"instructions\": [ {\"kind\": \"turnFlagOn\"} ] } ```  ## Errors  The API always returns errors in a common format. Here's an example:  ```json {   \"code\": \"invalid_request\",   \"message\": \"A feature with that key already exists\",   \"id\": \"30ce6058-87da-11e4-b116-123b93f75cba\" } ```  The `code` indicates the general class of error. The `message` is a human-readable explanation of what went wrong. The `id` is a unique identifier. Use it when you're working with LaunchDarkly Support to debug a problem with a specific API call.  ### HTTP status error response codes  | Code | Definition        | Description                                                                                       | Possible Solution                                                | | ---- | ----------------- | ------------------------------------------------------------------------------------------- | ---------------------------------------------------------------- | | 400  | Invalid request       | The request cannot be understood.                                    | Ensure JSON syntax in request body is correct.                   | | 401  | Invalid access token      | Requestor is unauthorized or does not have permission for this API call.                                                | Ensure your API access token is valid and has the appropriate permissions.                                     | | 403  | Forbidden         | Requestor does not have access to this resource.                                                | Ensure that the account member or access token has proper permissions set. | | 404  | Invalid resource identifier | The requested resource is not valid. | Ensure that the resource is correctly identified by ID or key. | | 405  | Method not allowed | The request method is not allowed on this resource. | Ensure that the HTTP verb is correct. | | 409  | Conflict          | The API request can not be completed because it conflicts with a concurrent API request. | Retry your request.                                              | | 422  | Unprocessable entity | The API request can not be completed because the update description can not be understood. | Ensure that the request body is correct for the type of patch you are using, either JSON patch or semantic patch. | 429  | Too many requests | Read [Rate limiting](/#section/Overview/Rate-limiting).                                               | Wait and try again later.                                        |  ## CORS  The LaunchDarkly API supports Cross Origin Resource Sharing (CORS) for AJAX requests from any origin. If an `Origin` header is given in a request, it will be echoed as an explicitly allowed origin. Otherwise the request returns a wildcard, `Access-Control-Allow-Origin: *`. For more information on CORS, read the [CORS W3C Recommendation](http://www.w3.org/TR/cors). Example CORS headers might look like:  ```http Access-Control-Allow-Headers: Accept, Content-Type, Content-Length, Accept-Encoding, Authorization Access-Control-Allow-Methods: OPTIONS, GET, DELETE, PATCH Access-Control-Allow-Origin: * Access-Control-Max-Age: 300 ```  You can make authenticated CORS calls just as you would make same-origin calls, using either [token or session-based authentication](/#section/Overview/Authentication). If you are using session authentication, you should set the `withCredentials` property for your `xhr` request to `true`. You should never expose your access tokens to untrusted entities.  ## Rate limiting  We use several rate limiting strategies to ensure the availability of our APIs. Rate-limited calls to our APIs return a `429` status code. Calls to our APIs include headers indicating the current rate limit status. The specific headers returned depend on the API route being called. The limits differ based on the route, authentication mechanism, and other factors. Routes that are not rate limited may not contain any of the headers described below.  > ### Rate limiting and SDKs > > LaunchDarkly SDKs are never rate limited and do not use the API endpoints defined here. LaunchDarkly uses a different set of approaches, including streaming/server-sent events and a global CDN, to ensure availability to the routes used by LaunchDarkly SDKs.  ### Global rate limits  Authenticated requests are subject to a global limit. This is the maximum number of calls that your account can make to the API per ten seconds. All service and personal access tokens on the account share this limit, so exceeding the limit with one access token will impact other tokens. Calls that are subject to global rate limits may return the headers below:  | Header name                    | Description                                                                      | | ------------------------------ | -------------------------------------------------------------------------------- | | `X-Ratelimit-Global-Remaining` | The maximum number of requests the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`            | The time at which the current rate limit window resets in epoch milliseconds.    |  We do not publicly document the specific number of calls that can be made globally. This limit may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limit.  ### Route-level rate limits  Some authenticated routes have custom rate limits. These also reset every ten seconds. Any service or personal access tokens hitting the same route share this limit, so exceeding the limit with one access token may impact other tokens. Calls that are subject to route-level rate limits return the headers below:  | Header name                   | Description                                                                                           | | ----------------------------- | ----------------------------------------------------------------------------------------------------- | | `X-Ratelimit-Route-Remaining` | The maximum number of requests to the current route the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`           | The time at which the current rate limit window resets in epoch milliseconds.                         |  A _route_ represents a specific URL pattern and verb. For example, the [Delete environment](/tag/Environments#operation/deleteEnvironment) endpoint is considered a single route, and each call to delete an environment counts against your route-level rate limit for that route.  We do not publicly document the specific number of calls that an account can make to each endpoint per ten seconds. These limits may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limits.  ### IP-based rate limiting  We also employ IP-based rate limiting on some API routes. If you hit an IP-based rate limit, your API response will include a `Retry-After` header indicating how long to wait before re-trying the call. Clients must wait at least `Retry-After` seconds before making additional calls to our API, and should employ jitter and backoff strategies to avoid triggering rate limits again.  ## OpenAPI (Swagger) and client libraries  We have a [complete OpenAPI (Swagger) specification](https://app.launchdarkly.com/api/v2/openapi.json) for our API.  We auto-generate multiple client libraries based on our OpenAPI specification. To learn more, visit the [collection of client libraries on GitHub](https://github.com/search?q=topic%3Alaunchdarkly-api+org%3Alaunchdarkly&type=Repositories). You can also use this specification to generate client libraries to interact with our REST API in your language of choice.  Our OpenAPI specification is supported by several API-based tools such as Postman and Insomnia. In many cases, you can directly import our specification to explore our APIs.  ## Method overriding  Some firewalls and HTTP clients restrict the use of verbs other than `GET` and `POST`. In those environments, our API endpoints that use `DELETE`, `PATCH`, and `PUT` verbs are inaccessible.  To avoid this issue, our API supports the `X-HTTP-Method-Override` header, allowing clients to \"tunnel\" `DELETE`, `PATCH`, and `PUT` requests using a `POST` request.  For example, to call a `PATCH` endpoint using a `POST` request, you can include `X-HTTP-Method-Override:PATCH` as a header.  ## Beta resources  We sometimes release new API resources in **beta** status before we release them with general availability.  Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  We try to promote resources into general availability as quickly as possible. This happens after sufficient testing and when we're satisfied that we no longer need to make backwards-incompatible changes.  We mark beta resources with a \"Beta\" callout in our documentation, pictured below:  > ### This feature is in beta > > To use this feature, pass in a header including the `LD-API-Version` key with value set to `beta`. Use this header with each call. To learn more, read [Beta resources](/#section/Overview/Beta-resources). > > Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  ### Using beta resources  To use a beta resource, you must include a header in the request. If you call a beta resource without this header, you receive a `403` response.  Use this header:  ``` LD-API-Version: beta ```  ## Federal environments  The version of LaunchDarkly that is available on domains controlled by the United States government is different from the version of LaunchDarkly available to the general public. If you are an employee or contractor for a United States federal agency and use LaunchDarkly in your work, you likely use the federal instance of LaunchDarkly.  If you are working in the federal instance of LaunchDarkly, the base URI for each request is `https://app.launchdarkly.us`. In the \"Try it\" sandbox for each request, click the request path to view the complete resource path for the federal environment.  To learn more, read [LaunchDarkly in federal environments](https://docs.launchdarkly.com/home/advanced/federal).  ## Versioning  We try hard to keep our REST API backwards compatible, but we occasionally have to make backwards-incompatible changes in the process of shipping new features. These breaking changes can cause unexpected behavior if you don't prepare for them accordingly.  Updates to our REST API include support for the latest features in LaunchDarkly. We also release a new version of our REST API every time we make a breaking change. We provide simultaneous support for multiple API versions so you can migrate from your current API version to a new version at your own pace.  ### Setting the API version per request  You can set the API version on a specific request by sending an `LD-API-Version` header, as shown in the example below:  ``` LD-API-Version: 20240415 ```  The header value is the version number of the API version you would like to request. The number for each version corresponds to the date the version was released in `yyyymmdd` format. In the example above the version `20240415` corresponds to April 15, 2024.  ### Setting the API version per access token  When you create an access token, you must specify a specific version of the API to use. This ensures that integrations using this token cannot be broken by version changes.  Tokens created before versioning was released have their version set to `20160426`, which is the version of the API that existed before the current versioning scheme, so that they continue working the same way they did before versioning.  If you would like to upgrade your integration to use a new API version, you can explicitly set the header described above.  > ### Best practice: Set the header for every client or integration > > We recommend that you set the API version header explicitly in any client or integration you build. > > Only rely on the access token API version during manual testing.  ### API version changelog  |<div style=\"width:75px\">Version</div> | Changes | End of life (EOL) |---|---|---| | `20240415` | <ul><li>Changed several endpoints from unpaginated to paginated. Use the `limit` and `offset` query parameters to page through the results.</li> <li>Changed the [list access tokens](/tag/Access-tokens#operation/getTokens) endpoint: <ul><li>Response is now paginated with a default limit of `25`</li></ul></li> <li>Changed the [list account members](/tag/Account-members#operation/getMembers) endpoint: <ul><li>The `accessCheck` filter is no longer available</li></ul></li> <li>Changed the [list custom roles](/tag/Custom-roles#operation/getCustomRoles) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li></ul></li> <li>Changed the [list feature flags](/tag/Feature-flags#operation/getFeatureFlags) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li><li>The `environments` field is now only returned if the request is filtered by environment, using the `filterEnv` query parameter</li><li>The `filterEnv` query parameter supports a maximum of three environments</li><li>The `followerId`, `hasDataExport`, `status`, `contextKindTargeted`, and `segmentTargeted` filters are no longer available</li></ul></li> <li>Changed the [list segments](/tag/Segments#operation/getSegments) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li></ul></li> <li>Changed the [list teams](/tag/Teams#operation/getTeams) endpoint: <ul><li>The `expand` parameter no longer supports including `projects` or `roles`</li><li>In paginated results, the maximum page size is now 100</li></ul></li> <li>Changed the [get workflows](/tag/Workflows#operation/getWorkflows) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li><li>The `_conflicts` field in the response is no longer available</li></ul></li> </ul>  | Current | | `20220603` | <ul><li>Changed the [list projects](/tag/Projects#operation/getProjects) return value:<ul><li>Response is now paginated with a default limit of `20`.</li><li>Added support for filter and sort.</li><li>The project `environments` field is now expandable. This field is omitted by default.</li></ul></li><li>Changed the [get project](/tag/Projects#operation/getProject) return value:<ul><li>The `environments` field is now expandable. This field is omitted by default.</li></ul></li></ul> | 2025-04-15 | | `20210729` | <ul><li>Changed the [create approval request](/tag/Approvals#operation/postApprovalRequest) return value. It now returns HTTP Status Code `201` instead of `200`.</li><li> Changed the [get users](/tag/Users#operation/getUser) return value. It now returns a user record, not a user. </li><li>Added additional optional fields to environment, segments, flags, members, and segments, including the ability to create big segments. </li><li> Added default values for flag variations when new environments are created. </li><li>Added filtering and pagination for getting flags and members, including `limit`, `number`, `filter`, and `sort` query parameters. </li><li>Added endpoints for expiring user targets for flags and segments, scheduled changes, access tokens, Relay Proxy configuration, integrations and subscriptions, and approvals. </li></ul> | 2023-06-03 | | `20191212` | <ul><li>[List feature flags](/tag/Feature-flags#operation/getFeatureFlags) now defaults to sending summaries of feature flag configurations, equivalent to setting the query parameter `summary=true`. Summaries omit flag targeting rules and individual user targets from the payload. </li><li> Added endpoints for flags, flag status, projects, environments, audit logs, members, users, custom roles, segments, usage, streams, events, and data export. </li></ul> | 2022-07-29 | | `20160426` | <ul><li>Initial versioning of API. Tokens created before versioning have their version set to this.</li></ul> | 2020-12-12 |  To learn more about how EOL is determined, read LaunchDarkly's [End of Life (EOL) Policy](https://launchdarkly.com/policies/end-of-life-policy/). 
 *
 * The version of the OpenAPI document: 2.0
 * Contact: support@launchdarkly.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.launchdarkly.api.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.launchdarkly.api.model.InsightsMetricScore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.launchdarkly.api.JSON;

/**
 * InsightGroupScores
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-04-15T20:28:23.474829Z[Etc/UTC]")
public class InsightGroupScores {
  public static final String SERIALIZED_NAME_OVERALL = "overall";
  @SerializedName(SERIALIZED_NAME_OVERALL)
  private InsightsMetricScore overall;

  public static final String SERIALIZED_NAME_DEPLOYMENT_FREQUENCY = "deploymentFrequency";
  @SerializedName(SERIALIZED_NAME_DEPLOYMENT_FREQUENCY)
  private InsightsMetricScore deploymentFrequency;

  public static final String SERIALIZED_NAME_DEPLOYMENT_FAILURE_RATE = "deploymentFailureRate";
  @SerializedName(SERIALIZED_NAME_DEPLOYMENT_FAILURE_RATE)
  private InsightsMetricScore deploymentFailureRate;

  public static final String SERIALIZED_NAME_LEAD_TIME = "leadTime";
  @SerializedName(SERIALIZED_NAME_LEAD_TIME)
  private InsightsMetricScore leadTime;

  public static final String SERIALIZED_NAME_IMPACT_SIZE = "impactSize";
  @SerializedName(SERIALIZED_NAME_IMPACT_SIZE)
  private InsightsMetricScore impactSize;

  public static final String SERIALIZED_NAME_EXPERIMENTATION_COVERAGE = "experimentationCoverage";
  @SerializedName(SERIALIZED_NAME_EXPERIMENTATION_COVERAGE)
  private InsightsMetricScore experimentationCoverage;

  public static final String SERIALIZED_NAME_FLAG_HEALTH = "flagHealth";
  @SerializedName(SERIALIZED_NAME_FLAG_HEALTH)
  private InsightsMetricScore flagHealth;

  public static final String SERIALIZED_NAME_VELOCITY = "velocity";
  @SerializedName(SERIALIZED_NAME_VELOCITY)
  private InsightsMetricScore velocity;

  public static final String SERIALIZED_NAME_RISK = "risk";
  @SerializedName(SERIALIZED_NAME_RISK)
  private InsightsMetricScore risk;

  public static final String SERIALIZED_NAME_EFFICIENCY = "efficiency";
  @SerializedName(SERIALIZED_NAME_EFFICIENCY)
  private InsightsMetricScore efficiency;

  public static final String SERIALIZED_NAME_CREATION_RATIO = "creationRatio";
  @SerializedName(SERIALIZED_NAME_CREATION_RATIO)
  private InsightsMetricScore creationRatio;

  public InsightGroupScores() { 
  }

  public InsightGroupScores overall(InsightsMetricScore overall) {
    
    this.overall = overall;
    return this;
  }

   /**
   * Get overall
   * @return overall
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public InsightsMetricScore getOverall() {
    return overall;
  }


  public void setOverall(InsightsMetricScore overall) {
    this.overall = overall;
  }


  public InsightGroupScores deploymentFrequency(InsightsMetricScore deploymentFrequency) {
    
    this.deploymentFrequency = deploymentFrequency;
    return this;
  }

   /**
   * Get deploymentFrequency
   * @return deploymentFrequency
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public InsightsMetricScore getDeploymentFrequency() {
    return deploymentFrequency;
  }


  public void setDeploymentFrequency(InsightsMetricScore deploymentFrequency) {
    this.deploymentFrequency = deploymentFrequency;
  }


  public InsightGroupScores deploymentFailureRate(InsightsMetricScore deploymentFailureRate) {
    
    this.deploymentFailureRate = deploymentFailureRate;
    return this;
  }

   /**
   * Get deploymentFailureRate
   * @return deploymentFailureRate
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public InsightsMetricScore getDeploymentFailureRate() {
    return deploymentFailureRate;
  }


  public void setDeploymentFailureRate(InsightsMetricScore deploymentFailureRate) {
    this.deploymentFailureRate = deploymentFailureRate;
  }


  public InsightGroupScores leadTime(InsightsMetricScore leadTime) {
    
    this.leadTime = leadTime;
    return this;
  }

   /**
   * Get leadTime
   * @return leadTime
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public InsightsMetricScore getLeadTime() {
    return leadTime;
  }


  public void setLeadTime(InsightsMetricScore leadTime) {
    this.leadTime = leadTime;
  }


  public InsightGroupScores impactSize(InsightsMetricScore impactSize) {
    
    this.impactSize = impactSize;
    return this;
  }

   /**
   * Get impactSize
   * @return impactSize
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public InsightsMetricScore getImpactSize() {
    return impactSize;
  }


  public void setImpactSize(InsightsMetricScore impactSize) {
    this.impactSize = impactSize;
  }


  public InsightGroupScores experimentationCoverage(InsightsMetricScore experimentationCoverage) {
    
    this.experimentationCoverage = experimentationCoverage;
    return this;
  }

   /**
   * Get experimentationCoverage
   * @return experimentationCoverage
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public InsightsMetricScore getExperimentationCoverage() {
    return experimentationCoverage;
  }


  public void setExperimentationCoverage(InsightsMetricScore experimentationCoverage) {
    this.experimentationCoverage = experimentationCoverage;
  }


  public InsightGroupScores flagHealth(InsightsMetricScore flagHealth) {
    
    this.flagHealth = flagHealth;
    return this;
  }

   /**
   * Get flagHealth
   * @return flagHealth
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public InsightsMetricScore getFlagHealth() {
    return flagHealth;
  }


  public void setFlagHealth(InsightsMetricScore flagHealth) {
    this.flagHealth = flagHealth;
  }


  public InsightGroupScores velocity(InsightsMetricScore velocity) {
    
    this.velocity = velocity;
    return this;
  }

   /**
   * Get velocity
   * @return velocity
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public InsightsMetricScore getVelocity() {
    return velocity;
  }


  public void setVelocity(InsightsMetricScore velocity) {
    this.velocity = velocity;
  }


  public InsightGroupScores risk(InsightsMetricScore risk) {
    
    this.risk = risk;
    return this;
  }

   /**
   * Get risk
   * @return risk
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public InsightsMetricScore getRisk() {
    return risk;
  }


  public void setRisk(InsightsMetricScore risk) {
    this.risk = risk;
  }


  public InsightGroupScores efficiency(InsightsMetricScore efficiency) {
    
    this.efficiency = efficiency;
    return this;
  }

   /**
   * Get efficiency
   * @return efficiency
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public InsightsMetricScore getEfficiency() {
    return efficiency;
  }


  public void setEfficiency(InsightsMetricScore efficiency) {
    this.efficiency = efficiency;
  }


  public InsightGroupScores creationRatio(InsightsMetricScore creationRatio) {
    
    this.creationRatio = creationRatio;
    return this;
  }

   /**
   * Get creationRatio
   * @return creationRatio
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public InsightsMetricScore getCreationRatio() {
    return creationRatio;
  }


  public void setCreationRatio(InsightsMetricScore creationRatio) {
    this.creationRatio = creationRatio;
  }

  /**
   * A container for additional, undeclared properties.
   * This is a holder for any undeclared properties as specified with
   * the 'additionalProperties' keyword in the OAS document.
   */
  private Map<String, Object> additionalProperties;

  /**
   * Set the additional (undeclared) property with the specified name and value.
   * If the property does not already exist, create it otherwise replace it.
   */
  public InsightGroupScores putAdditionalProperty(String key, Object value) {
    if (this.additionalProperties == null) {
        this.additionalProperties = new HashMap<String, Object>();
    }
    this.additionalProperties.put(key, value);
    return this;
  }

  /**
   * Return the additional (undeclared) property.
   */
  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }

  /**
   * Return the additional (undeclared) property with the specified name.
   */
  public Object getAdditionalProperty(String key) {
    if (this.additionalProperties == null) {
        return null;
    }
    return this.additionalProperties.get(key);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InsightGroupScores insightGroupScores = (InsightGroupScores) o;
    return Objects.equals(this.overall, insightGroupScores.overall) &&
        Objects.equals(this.deploymentFrequency, insightGroupScores.deploymentFrequency) &&
        Objects.equals(this.deploymentFailureRate, insightGroupScores.deploymentFailureRate) &&
        Objects.equals(this.leadTime, insightGroupScores.leadTime) &&
        Objects.equals(this.impactSize, insightGroupScores.impactSize) &&
        Objects.equals(this.experimentationCoverage, insightGroupScores.experimentationCoverage) &&
        Objects.equals(this.flagHealth, insightGroupScores.flagHealth) &&
        Objects.equals(this.velocity, insightGroupScores.velocity) &&
        Objects.equals(this.risk, insightGroupScores.risk) &&
        Objects.equals(this.efficiency, insightGroupScores.efficiency) &&
        Objects.equals(this.creationRatio, insightGroupScores.creationRatio)&&
        Objects.equals(this.additionalProperties, insightGroupScores.additionalProperties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(overall, deploymentFrequency, deploymentFailureRate, leadTime, impactSize, experimentationCoverage, flagHealth, velocity, risk, efficiency, creationRatio, additionalProperties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InsightGroupScores {\n");
    sb.append("    overall: ").append(toIndentedString(overall)).append("\n");
    sb.append("    deploymentFrequency: ").append(toIndentedString(deploymentFrequency)).append("\n");
    sb.append("    deploymentFailureRate: ").append(toIndentedString(deploymentFailureRate)).append("\n");
    sb.append("    leadTime: ").append(toIndentedString(leadTime)).append("\n");
    sb.append("    impactSize: ").append(toIndentedString(impactSize)).append("\n");
    sb.append("    experimentationCoverage: ").append(toIndentedString(experimentationCoverage)).append("\n");
    sb.append("    flagHealth: ").append(toIndentedString(flagHealth)).append("\n");
    sb.append("    velocity: ").append(toIndentedString(velocity)).append("\n");
    sb.append("    risk: ").append(toIndentedString(risk)).append("\n");
    sb.append("    efficiency: ").append(toIndentedString(efficiency)).append("\n");
    sb.append("    creationRatio: ").append(toIndentedString(creationRatio)).append("\n");
    sb.append("    additionalProperties: ").append(toIndentedString(additionalProperties)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>();
    openapiFields.add("overall");
    openapiFields.add("deploymentFrequency");
    openapiFields.add("deploymentFailureRate");
    openapiFields.add("leadTime");
    openapiFields.add("impactSize");
    openapiFields.add("experimentationCoverage");
    openapiFields.add("flagHealth");
    openapiFields.add("velocity");
    openapiFields.add("risk");
    openapiFields.add("efficiency");
    openapiFields.add("creationRatio");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("overall");
    openapiRequiredFields.add("deploymentFrequency");
    openapiRequiredFields.add("deploymentFailureRate");
    openapiRequiredFields.add("leadTime");
    openapiRequiredFields.add("impactSize");
    openapiRequiredFields.add("experimentationCoverage");
    openapiRequiredFields.add("flagHealth");
    openapiRequiredFields.add("velocity");
    openapiRequiredFields.add("risk");
    openapiRequiredFields.add("efficiency");
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to InsightGroupScores
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (InsightGroupScores.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in InsightGroupScores is not found in the empty JSON string", InsightGroupScores.openapiRequiredFields.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : InsightGroupScores.openapiRequiredFields) {
        if (jsonObj.get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonObj.toString()));
        }
      }
      // validate the optional field `overall`
      if (jsonObj.getAsJsonObject("overall") != null) {
        InsightsMetricScore.validateJsonObject(jsonObj.getAsJsonObject("overall"));
      }
      // validate the optional field `deploymentFrequency`
      if (jsonObj.getAsJsonObject("deploymentFrequency") != null) {
        InsightsMetricScore.validateJsonObject(jsonObj.getAsJsonObject("deploymentFrequency"));
      }
      // validate the optional field `deploymentFailureRate`
      if (jsonObj.getAsJsonObject("deploymentFailureRate") != null) {
        InsightsMetricScore.validateJsonObject(jsonObj.getAsJsonObject("deploymentFailureRate"));
      }
      // validate the optional field `leadTime`
      if (jsonObj.getAsJsonObject("leadTime") != null) {
        InsightsMetricScore.validateJsonObject(jsonObj.getAsJsonObject("leadTime"));
      }
      // validate the optional field `impactSize`
      if (jsonObj.getAsJsonObject("impactSize") != null) {
        InsightsMetricScore.validateJsonObject(jsonObj.getAsJsonObject("impactSize"));
      }
      // validate the optional field `experimentationCoverage`
      if (jsonObj.getAsJsonObject("experimentationCoverage") != null) {
        InsightsMetricScore.validateJsonObject(jsonObj.getAsJsonObject("experimentationCoverage"));
      }
      // validate the optional field `flagHealth`
      if (jsonObj.getAsJsonObject("flagHealth") != null) {
        InsightsMetricScore.validateJsonObject(jsonObj.getAsJsonObject("flagHealth"));
      }
      // validate the optional field `velocity`
      if (jsonObj.getAsJsonObject("velocity") != null) {
        InsightsMetricScore.validateJsonObject(jsonObj.getAsJsonObject("velocity"));
      }
      // validate the optional field `risk`
      if (jsonObj.getAsJsonObject("risk") != null) {
        InsightsMetricScore.validateJsonObject(jsonObj.getAsJsonObject("risk"));
      }
      // validate the optional field `efficiency`
      if (jsonObj.getAsJsonObject("efficiency") != null) {
        InsightsMetricScore.validateJsonObject(jsonObj.getAsJsonObject("efficiency"));
      }
      // validate the optional field `creationRatio`
      if (jsonObj.getAsJsonObject("creationRatio") != null) {
        InsightsMetricScore.validateJsonObject(jsonObj.getAsJsonObject("creationRatio"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!InsightGroupScores.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'InsightGroupScores' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<InsightGroupScores> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(InsightGroupScores.class));

       return (TypeAdapter<T>) new TypeAdapter<InsightGroupScores>() {
           @Override
           public void write(JsonWriter out, InsightGroupScores value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             obj.remove("additionalProperties");
             // serialize additonal properties
             if (value.getAdditionalProperties() != null) {
               for (Map.Entry<String, Object> entry : value.getAdditionalProperties().entrySet()) {
                 if (entry.getValue() instanceof String)
                   obj.addProperty(entry.getKey(), (String) entry.getValue());
                 else if (entry.getValue() instanceof Number)
                   obj.addProperty(entry.getKey(), (Number) entry.getValue());
                 else if (entry.getValue() instanceof Boolean)
                   obj.addProperty(entry.getKey(), (Boolean) entry.getValue());
                 else if (entry.getValue() instanceof Character)
                   obj.addProperty(entry.getKey(), (Character) entry.getValue());
                 else {
                   obj.add(entry.getKey(), gson.toJsonTree(entry.getValue()).getAsJsonObject());
                 }
               }
             }
             elementAdapter.write(out, obj);
           }

           @Override
           public InsightGroupScores read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             // store additional fields in the deserialized instance
             InsightGroupScores instance = thisAdapter.fromJsonTree(jsonObj);
             for (Map.Entry<String, JsonElement> entry : jsonObj.entrySet()) {
               if (!openapiFields.contains(entry.getKey())) {
                 if (entry.getValue().isJsonPrimitive()) { // primitive type
                   if (entry.getValue().getAsJsonPrimitive().isString())
                     instance.putAdditionalProperty(entry.getKey(), entry.getValue().getAsString());
                   else if (entry.getValue().getAsJsonPrimitive().isNumber())
                     instance.putAdditionalProperty(entry.getKey(), entry.getValue().getAsNumber());
                   else if (entry.getValue().getAsJsonPrimitive().isBoolean())
                     instance.putAdditionalProperty(entry.getKey(), entry.getValue().getAsBoolean());
                   else
                     throw new IllegalArgumentException(String.format("The field `%s` has unknown primitive type. Value: %s", entry.getKey(), entry.getValue().toString()));
                 } else { // non-primitive type
                   instance.putAdditionalProperty(entry.getKey(), gson.fromJson(entry.getValue(), HashMap.class));
                 }
               }
             }
             return instance;
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of InsightGroupScores given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of InsightGroupScores
  * @throws IOException if the JSON string is invalid with respect to InsightGroupScores
  */
  public static InsightGroupScores fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, InsightGroupScores.class);
  }

 /**
  * Convert an instance of InsightGroupScores to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

