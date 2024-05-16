/*
 * LaunchDarkly REST API
 * # Overview  ## Authentication  LaunchDarkly's REST API uses the HTTPS protocol with a minimum TLS version of 1.2.  All REST API resources are authenticated with either [personal or service access tokens](https://docs.launchdarkly.com/home/account/api), or session cookies. Other authentication mechanisms are not supported. You can manage personal access tokens on your [**Account settings**](https://app.launchdarkly.com/settings/tokens) page.  LaunchDarkly also has SDK keys, mobile keys, and client-side IDs that are used by our server-side SDKs, mobile SDKs, and JavaScript-based SDKs, respectively. **These keys cannot be used to access our REST API**. These keys are environment-specific, and can only perform read-only operations such as fetching feature flag settings.  | Auth mechanism                                                                                  | Allowed resources                                                                                     | Use cases                                          | | ----------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------- | -------------------------------------------------- | | [Personal or service access tokens](https://docs.launchdarkly.com/home/account/api) | Can be customized on a per-token basis                                                                | Building scripts, custom integrations, data export. | | SDK keys                                                                                        | Can only access read-only resources specific to server-side SDKs. Restricted to a single environment. | Server-side SDKs                     | | Mobile keys                                                                                     | Can only access read-only resources specific to mobile SDKs, and only for flags marked available to mobile keys. Restricted to a single environment.           | Mobile SDKs                                        | | Client-side ID                                                                                  | Can only access read-only resources specific to JavaScript-based client-side SDKs, and only for flags marked available to client-side. Restricted to a single environment.           | Client-side JavaScript                             |  > #### Keep your access tokens and SDK keys private > > Access tokens should _never_ be exposed in untrusted contexts. Never put an access token in client-side JavaScript, or embed it in a mobile application. LaunchDarkly has special mobile keys that you can embed in mobile apps. If you accidentally expose an access token or SDK key, you can reset it from your [**Account settings**](https://app.launchdarkly.com/settings/tokens) page. > > The client-side ID is safe to embed in untrusted contexts. It's designed for use in client-side JavaScript.  ### Authentication using request header  The preferred way to authenticate with the API is by adding an `Authorization` header containing your access token to your requests. The value of the `Authorization` header must be your access token.  Manage personal access tokens from the [**Account settings**](https://app.launchdarkly.com/settings/tokens) page.  ### Authentication using session cookie  For testing purposes, you can make API calls directly from your web browser. If you are logged in to the LaunchDarkly application, the API will use your existing session to authenticate calls.  If you have a [role](https://docs.launchdarkly.com/home/account/built-in-roles) other than Admin, or have a [custom role](https://docs.launchdarkly.com/home/account/custom-roles) defined, you may not have permission to perform some API calls. You will receive a `401` response code in that case.  > ### Modifying the Origin header causes an error > > LaunchDarkly validates that the Origin header for any API request authenticated by a session cookie matches the expected Origin header. The expected Origin header is `https://app.launchdarkly.com`. > > If the Origin header does not match what's expected, LaunchDarkly returns an error. This error can prevent the LaunchDarkly app from working correctly. > > Any browser extension that intentionally changes the Origin header can cause this problem. For example, the `Allow-Control-Allow-Origin: *` Chrome extension changes the Origin header to `http://evil.com` and causes the app to fail. > > To prevent this error, do not modify your Origin header. > > LaunchDarkly does not require origin matching when authenticating with an access token, so this issue does not affect normal API usage.  ## Representations  All resources expect and return JSON response bodies. Error responses also send a JSON body. To learn more about the error format of the API, read [Errors](/#section/Overview/Errors).  In practice this means that you always get a response with a `Content-Type` header set to `application/json`.  In addition, request bodies for `PATCH`, `POST`, and `PUT` requests must be encoded as JSON with a `Content-Type` header set to `application/json`.  ### Summary and detailed representations  When you fetch a list of resources, the response includes only the most important attributes of each resource. This is a _summary representation_ of the resource. When you fetch an individual resource, such as a single feature flag, you receive a _detailed representation_ of the resource.  The best way to find a detailed representation is to follow links. Every summary representation includes a link to its detailed representation.  ### Expanding responses  Sometimes the detailed representation of a resource does not include all of the attributes of the resource by default. If this is the case, the request method will clearly document this and describe which attributes you can include in an expanded response.  To include the additional attributes, append the `expand` request parameter to your request and add a comma-separated list of the attributes to include. For example, when you append `?expand=members,roles` to the [Get team](/tag/Teams#operation/getTeam) endpoint, the expanded response includes both of these attributes.  ### Links and addressability  The best way to navigate the API is by following links. These are attributes in representations that link to other resources. The API always uses the same format for links:  - Links to other resources within the API are encapsulated in a `_links` object - If the resource has a corresponding link to HTML content on the site, it is stored in a special `_site` link  Each link has two attributes:  - An `href`, which contains the URL - A `type`, which describes the content type  For example, a feature resource might return the following:  ```json {   \"_links\": {     \"parent\": {       \"href\": \"/api/features\",       \"type\": \"application/json\"     },     \"self\": {       \"href\": \"/api/features/sort.order\",       \"type\": \"application/json\"     }   },   \"_site\": {     \"href\": \"/features/sort.order\",     \"type\": \"text/html\"   } } ```  From this, you can navigate to the parent collection of features by following the `parent` link, or navigate to the site page for the feature by following the `_site` link.  Collections are always represented as a JSON object with an `items` attribute containing an array of representations. Like all other representations, collections have `_links` defined at the top level.  Paginated collections include `first`, `last`, `next`, and `prev` links containing a URL with the respective set of elements in the collection.  ## Updates  Resources that accept partial updates use the `PATCH` verb. Most resources support the [JSON patch](/reference#updates-using-json-patch) format. Some resources also support the [JSON merge patch](/reference#updates-using-json-merge-patch) format, and some resources support the [semantic patch](/reference#updates-using-semantic-patch) format, which is a way to specify the modifications to perform as a set of executable instructions. Each resource supports optional [comments](/reference#updates-with-comments) that you can submit with updates. Comments appear in outgoing webhooks, the audit log, and other integrations.  When a resource supports both JSON patch and semantic patch, we document both in the request method. However, the specific request body fields and descriptions included in our documentation only match one type of patch or the other.  ### Updates using JSON patch  [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) is a way to specify the modifications to perform on a resource. JSON patch uses paths and a limited set of operations to describe how to transform the current state of the resource into a new state. JSON patch documents are always arrays, where each element contains an operation, a path to the field to update, and the new value.  For example, in this feature flag representation:  ```json {     \"name\": \"New recommendations engine\",     \"key\": \"engine.enable\",     \"description\": \"This is the description\",     ... } ``` You can change the feature flag's description with the following patch document:  ```json [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"This is the new description\" }] ```  You can specify multiple modifications to perform in a single request. You can also test that certain preconditions are met before applying the patch:  ```json [   { \"op\": \"test\", \"path\": \"/version\", \"value\": 10 },   { \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" } ] ```  The above patch request tests whether the feature flag's `version` is `10`, and if so, changes the feature flag's description.  Attributes that are not editable, such as a resource's `_links`, have names that start with an underscore.  ### Updates using JSON merge patch  [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) is another format for specifying the modifications to perform on a resource. JSON merge patch is less expressive than JSON patch. However, in many cases it is simpler to construct a merge patch document. For example, you can change a feature flag's description with the following merge patch document:  ```json {   \"description\": \"New flag description\" } ```  ### Updates using semantic patch  Some resources support the semantic patch format. A semantic patch is a way to specify the modifications to perform on a resource as a set of executable instructions.  Semantic patch allows you to be explicit about intent using precise, custom instructions. In many cases, you can define semantic patch instructions independently of the current state of the resource. This can be useful when defining a change that may be applied at a future date.  To make a semantic patch request, you must append `domain-model=launchdarkly.semanticpatch` to your `Content-Type` header.  Here's how:  ``` Content-Type: application/json; domain-model=launchdarkly.semanticpatch ```  If you call a semantic patch resource without this header, you will receive a `400` response because your semantic patch will be interpreted as a JSON patch.  The body of a semantic patch request takes the following properties:  * `comment` (string): (Optional) A description of the update. * `environmentKey` (string): (Required for some resources only) The environment key. * `instructions` (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a `kind` property that indicates the instruction. If the instruction requires parameters, you must include those parameters as additional fields in the object. The documentation for each resource that supports semantic patch includes the available instructions and any additional parameters.  For example:  ```json {   \"comment\": \"optional comment\",   \"instructions\": [ {\"kind\": \"turnFlagOn\"} ] } ```  If any instruction in the patch encounters an error, the endpoint returns an error and will not change the resource. In general, each instruction silently does nothing if the resource is already in the state you request.  ### Updates with comments  You can submit optional comments with `PATCH` changes.  To submit a comment along with a JSON patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"patch\": [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" }] } ```  To submit a comment along with a JSON merge patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"merge\": { \"description\": \"New flag description\" } } ```  To submit a comment along with a semantic patch, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"instructions\": [ {\"kind\": \"turnFlagOn\"} ] } ```  ## Errors  The API always returns errors in a common format. Here's an example:  ```json {   \"code\": \"invalid_request\",   \"message\": \"A feature with that key already exists\",   \"id\": \"30ce6058-87da-11e4-b116-123b93f75cba\" } ```  The `code` indicates the general class of error. The `message` is a human-readable explanation of what went wrong. The `id` is a unique identifier. Use it when you're working with LaunchDarkly Support to debug a problem with a specific API call.  ### HTTP status error response codes  | Code | Definition        | Description                                                                                       | Possible Solution                                                | | ---- | ----------------- | ------------------------------------------------------------------------------------------- | ---------------------------------------------------------------- | | 400  | Invalid request       | The request cannot be understood.                                    | Ensure JSON syntax in request body is correct.                   | | 401  | Invalid access token      | Requestor is unauthorized or does not have permission for this API call.                                                | Ensure your API access token is valid and has the appropriate permissions.                                     | | 403  | Forbidden         | Requestor does not have access to this resource.                                                | Ensure that the account member or access token has proper permissions set. | | 404  | Invalid resource identifier | The requested resource is not valid. | Ensure that the resource is correctly identified by ID or key. | | 405  | Method not allowed | The request method is not allowed on this resource. | Ensure that the HTTP verb is correct. | | 409  | Conflict          | The API request can not be completed because it conflicts with a concurrent API request. | Retry your request.                                              | | 422  | Unprocessable entity | The API request can not be completed because the update description can not be understood. | Ensure that the request body is correct for the type of patch you are using, either JSON patch or semantic patch. | 429  | Too many requests | Read [Rate limiting](/#section/Overview/Rate-limiting).                                               | Wait and try again later.                                        |  ## CORS  The LaunchDarkly API supports Cross Origin Resource Sharing (CORS) for AJAX requests from any origin. If an `Origin` header is given in a request, it will be echoed as an explicitly allowed origin. Otherwise the request returns a wildcard, `Access-Control-Allow-Origin: *`. For more information on CORS, read the [CORS W3C Recommendation](http://www.w3.org/TR/cors). Example CORS headers might look like:  ```http Access-Control-Allow-Headers: Accept, Content-Type, Content-Length, Accept-Encoding, Authorization Access-Control-Allow-Methods: OPTIONS, GET, DELETE, PATCH Access-Control-Allow-Origin: * Access-Control-Max-Age: 300 ```  You can make authenticated CORS calls just as you would make same-origin calls, using either [token or session-based authentication](/#section/Overview/Authentication). If you are using session authentication, you should set the `withCredentials` property for your `xhr` request to `true`. You should never expose your access tokens to untrusted entities.  ## Rate limiting  We use several rate limiting strategies to ensure the availability of our APIs. Rate-limited calls to our APIs return a `429` status code. Calls to our APIs include headers indicating the current rate limit status. The specific headers returned depend on the API route being called. The limits differ based on the route, authentication mechanism, and other factors. Routes that are not rate limited may not contain any of the headers described below.  > ### Rate limiting and SDKs > > LaunchDarkly SDKs are never rate limited and do not use the API endpoints defined here. LaunchDarkly uses a different set of approaches, including streaming/server-sent events and a global CDN, to ensure availability to the routes used by LaunchDarkly SDKs.  ### Global rate limits  Authenticated requests are subject to a global limit. This is the maximum number of calls that your account can make to the API per ten seconds. All service and personal access tokens on the account share this limit, so exceeding the limit with one access token will impact other tokens. Calls that are subject to global rate limits may return the headers below:  | Header name                    | Description                                                                      | | ------------------------------ | -------------------------------------------------------------------------------- | | `X-Ratelimit-Global-Remaining` | The maximum number of requests the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`            | The time at which the current rate limit window resets in epoch milliseconds.    |  We do not publicly document the specific number of calls that can be made globally. This limit may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limit.  ### Route-level rate limits  Some authenticated routes have custom rate limits. These also reset every ten seconds. Any service or personal access tokens hitting the same route share this limit, so exceeding the limit with one access token may impact other tokens. Calls that are subject to route-level rate limits return the headers below:  | Header name                   | Description                                                                                           | | ----------------------------- | ----------------------------------------------------------------------------------------------------- | | `X-Ratelimit-Route-Remaining` | The maximum number of requests to the current route the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`           | The time at which the current rate limit window resets in epoch milliseconds.                         |  A _route_ represents a specific URL pattern and verb. For example, the [Delete environment](/tag/Environments#operation/deleteEnvironment) endpoint is considered a single route, and each call to delete an environment counts against your route-level rate limit for that route.  We do not publicly document the specific number of calls that an account can make to each endpoint per ten seconds. These limits may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limits.  ### IP-based rate limiting  We also employ IP-based rate limiting on some API routes. If you hit an IP-based rate limit, your API response will include a `Retry-After` header indicating how long to wait before re-trying the call. Clients must wait at least `Retry-After` seconds before making additional calls to our API, and should employ jitter and backoff strategies to avoid triggering rate limits again.  ## OpenAPI (Swagger) and client libraries  We have a [complete OpenAPI (Swagger) specification](https://app.launchdarkly.com/api/v2/openapi.json) for our API.  We auto-generate multiple client libraries based on our OpenAPI specification. To learn more, visit the [collection of client libraries on GitHub](https://github.com/search?q=topic%3Alaunchdarkly-api+org%3Alaunchdarkly&type=Repositories). You can also use this specification to generate client libraries to interact with our REST API in your language of choice.  Our OpenAPI specification is supported by several API-based tools such as Postman and Insomnia. In many cases, you can directly import our specification to explore our APIs.  ## Method overriding  Some firewalls and HTTP clients restrict the use of verbs other than `GET` and `POST`. In those environments, our API endpoints that use `DELETE`, `PATCH`, and `PUT` verbs are inaccessible.  To avoid this issue, our API supports the `X-HTTP-Method-Override` header, allowing clients to \"tunnel\" `DELETE`, `PATCH`, and `PUT` requests using a `POST` request.  For example, to call a `PATCH` endpoint using a `POST` request, you can include `X-HTTP-Method-Override:PATCH` as a header.  ## Beta resources  We sometimes release new API resources in **beta** status before we release them with general availability.  Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  We try to promote resources into general availability as quickly as possible. This happens after sufficient testing and when we're satisfied that we no longer need to make backwards-incompatible changes.  We mark beta resources with a \"Beta\" callout in our documentation, pictured below:  > ### This feature is in beta > > To use this feature, pass in a header including the `LD-API-Version` key with value set to `beta`. Use this header with each call. To learn more, read [Beta resources](/#section/Overview/Beta-resources). > > Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  ### Using beta resources  To use a beta resource, you must include a header in the request. If you call a beta resource without this header, you receive a `403` response.  Use this header:  ``` LD-API-Version: beta ```  ## Federal environments  The version of LaunchDarkly that is available on domains controlled by the United States government is different from the version of LaunchDarkly available to the general public. If you are an employee or contractor for a United States federal agency and use LaunchDarkly in your work, you likely use the federal instance of LaunchDarkly.  If you are working in the federal instance of LaunchDarkly, the base URI for each request is `https://app.launchdarkly.us`. In the \"Try it\" sandbox for each request, click the request path to view the complete resource path for the federal environment.  To learn more, read [LaunchDarkly in federal environments](https://docs.launchdarkly.com/home/infrastructure/federal).  ## Versioning  We try hard to keep our REST API backwards compatible, but we occasionally have to make backwards-incompatible changes in the process of shipping new features. These breaking changes can cause unexpected behavior if you don't prepare for them accordingly.  Updates to our REST API include support for the latest features in LaunchDarkly. We also release a new version of our REST API every time we make a breaking change. We provide simultaneous support for multiple API versions so you can migrate from your current API version to a new version at your own pace.  ### Setting the API version per request  You can set the API version on a specific request by sending an `LD-API-Version` header, as shown in the example below:  ``` LD-API-Version: 20240415 ```  The header value is the version number of the API version you would like to request. The number for each version corresponds to the date the version was released in `yyyymmdd` format. In the example above the version `20240415` corresponds to April 15, 2024.  ### Setting the API version per access token  When you create an access token, you must specify a specific version of the API to use. This ensures that integrations using this token cannot be broken by version changes.  Tokens created before versioning was released have their version set to `20160426`, which is the version of the API that existed before the current versioning scheme, so that they continue working the same way they did before versioning.  If you would like to upgrade your integration to use a new API version, you can explicitly set the header described above.  > ### Best practice: Set the header for every client or integration > > We recommend that you set the API version header explicitly in any client or integration you build. > > Only rely on the access token API version during manual testing.  ### API version changelog  |<div style=\"width:75px\">Version</div> | Changes | End of life (EOL) |---|---|---| | `20240415` | <ul><li>Changed several endpoints from unpaginated to paginated. Use the `limit` and `offset` query parameters to page through the results.</li> <li>Changed the [list access tokens](/tag/Access-tokens#operation/getTokens) endpoint: <ul><li>Response is now paginated with a default limit of `25`</li></ul></li> <li>Changed the [list account members](/tag/Account-members#operation/getMembers) endpoint: <ul><li>The `accessCheck` filter is no longer available</li></ul></li> <li>Changed the [list custom roles](/tag/Custom-roles#operation/getCustomRoles) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li></ul></li> <li>Changed the [list feature flags](/tag/Feature-flags#operation/getFeatureFlags) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li><li>The `environments` field is now only returned if the request is filtered by environment, using the `filterEnv` query parameter</li><li>The `filterEnv` query parameter supports a maximum of three environments</li><li>The `followerId`, `hasDataExport`, `status`, `contextKindTargeted`, and `segmentTargeted` filters are no longer available</li></ul></li> <li>Changed the [list segments](/tag/Segments#operation/getSegments) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li></ul></li> <li>Changed the [list teams](/tag/Teams#operation/getTeams) endpoint: <ul><li>The `expand` parameter no longer supports including `projects` or `roles`</li><li>In paginated results, the maximum page size is now 100</li></ul></li> <li>Changed the [get workflows](/tag/Workflows#operation/getWorkflows) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li><li>The `_conflicts` field in the response is no longer available</li></ul></li> </ul>  | Current | | `20220603` | <ul><li>Changed the [list projects](/tag/Projects#operation/getProjects) return value:<ul><li>Response is now paginated with a default limit of `20`.</li><li>Added support for filter and sort.</li><li>The project `environments` field is now expandable. This field is omitted by default.</li></ul></li><li>Changed the [get project](/tag/Projects#operation/getProject) return value:<ul><li>The `environments` field is now expandable. This field is omitted by default.</li></ul></li></ul> | 2025-04-15 | | `20210729` | <ul><li>Changed the [create approval request](/tag/Approvals#operation/postApprovalRequest) return value. It now returns HTTP Status Code `201` instead of `200`.</li><li> Changed the [get users](/tag/Users#operation/getUser) return value. It now returns a user record, not a user. </li><li>Added additional optional fields to environment, segments, flags, members, and segments, including the ability to create big segments. </li><li> Added default values for flag variations when new environments are created. </li><li>Added filtering and pagination for getting flags and members, including `limit`, `number`, `filter`, and `sort` query parameters. </li><li>Added endpoints for expiring user targets for flags and segments, scheduled changes, access tokens, Relay Proxy configuration, integrations and subscriptions, and approvals. </li></ul> | 2023-06-03 | | `20191212` | <ul><li>[List feature flags](/tag/Feature-flags#operation/getFeatureFlags) now defaults to sending summaries of feature flag configurations, equivalent to setting the query parameter `summary=true`. Summaries omit flag targeting rules and individual user targets from the payload. </li><li> Added endpoints for flags, flag status, projects, environments, audit logs, members, users, custom roles, segments, usage, streams, events, and data export. </li></ul> | 2022-07-29 | | `20160426` | <ul><li>Initial versioning of API. Tokens created before versioning have their version set to this.</li></ul> | 2020-12-12 |  To learn more about how EOL is determined, read LaunchDarkly's [End of Life (EOL) Policy](https://launchdarkly.com/policies/end-of-life-policy/). 
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
import com.launchdarkly.api.model.Conflict;
import com.launchdarkly.api.model.CopiedFromEnv;
import com.launchdarkly.api.model.CustomWorkflowMeta;
import com.launchdarkly.api.model.IntegrationMetadata;
import com.launchdarkly.api.model.ReviewResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * FlagConfigApprovalRequestResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-05-16T11:29:12.794018Z[Etc/UTC]")
public class FlagConfigApprovalRequestResponse {
  public static final String SERIALIZED_NAME_ID = "_id";
  @SerializedName(SERIALIZED_NAME_ID)
  private String id;

  public static final String SERIALIZED_NAME_VERSION = "_version";
  @SerializedName(SERIALIZED_NAME_VERSION)
  private Integer version;

  public static final String SERIALIZED_NAME_CREATION_DATE = "creationDate";
  @SerializedName(SERIALIZED_NAME_CREATION_DATE)
  private Long creationDate;

  public static final String SERIALIZED_NAME_SERVICE_KIND = "serviceKind";
  @SerializedName(SERIALIZED_NAME_SERVICE_KIND)
  private String serviceKind;

  public static final String SERIALIZED_NAME_REQUESTOR_ID = "requestorId";
  @SerializedName(SERIALIZED_NAME_REQUESTOR_ID)
  private String requestorId;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  /**
   * Current status of the review of this approval request
   */
  @JsonAdapter(ReviewStatusEnum.Adapter.class)
  public enum ReviewStatusEnum {
    APPROVED("approved"),
    
    DECLINED("declined"),
    
    PENDING("pending");

    private String value;

    ReviewStatusEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ReviewStatusEnum fromValue(String value) {
      for (ReviewStatusEnum b : ReviewStatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<ReviewStatusEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ReviewStatusEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ReviewStatusEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return ReviewStatusEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_REVIEW_STATUS = "reviewStatus";
  @SerializedName(SERIALIZED_NAME_REVIEW_STATUS)
  private ReviewStatusEnum reviewStatus;

  public static final String SERIALIZED_NAME_ALL_REVIEWS = "allReviews";
  @SerializedName(SERIALIZED_NAME_ALL_REVIEWS)
  private List<ReviewResponse> allReviews = new ArrayList<>();

  public static final String SERIALIZED_NAME_NOTIFY_MEMBER_IDS = "notifyMemberIds";
  @SerializedName(SERIALIZED_NAME_NOTIFY_MEMBER_IDS)
  private List<String> notifyMemberIds = new ArrayList<>();

  public static final String SERIALIZED_NAME_APPLIED_DATE = "appliedDate";
  @SerializedName(SERIALIZED_NAME_APPLIED_DATE)
  private Long appliedDate;

  public static final String SERIALIZED_NAME_APPLIED_BY_MEMBER_ID = "appliedByMemberId";
  @SerializedName(SERIALIZED_NAME_APPLIED_BY_MEMBER_ID)
  private String appliedByMemberId;

  public static final String SERIALIZED_NAME_APPLIED_BY_SERVICE_TOKEN_ID = "appliedByServiceTokenId";
  @SerializedName(SERIALIZED_NAME_APPLIED_BY_SERVICE_TOKEN_ID)
  private String appliedByServiceTokenId;

  /**
   * Current status of the approval request
   */
  @JsonAdapter(StatusEnum.Adapter.class)
  public enum StatusEnum {
    PENDING("pending"),
    
    COMPLETED("completed"),
    
    FAILED("failed"),
    
    SCHEDULED("scheduled");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<StatusEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final StatusEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public StatusEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return StatusEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_STATUS = "status";
  @SerializedName(SERIALIZED_NAME_STATUS)
  private StatusEnum status;

  public static final String SERIALIZED_NAME_INSTRUCTIONS = "instructions";
  @SerializedName(SERIALIZED_NAME_INSTRUCTIONS)
  private List<Map<String, Object>> instructions = new ArrayList<>();

  public static final String SERIALIZED_NAME_CONFLICTS = "conflicts";
  @SerializedName(SERIALIZED_NAME_CONFLICTS)
  private List<Conflict> conflicts = new ArrayList<>();

  public static final String SERIALIZED_NAME_LINKS = "_links";
  @SerializedName(SERIALIZED_NAME_LINKS)
  private Map<String, Object> links = new HashMap<>();

  public static final String SERIALIZED_NAME_EXECUTION_DATE = "executionDate";
  @SerializedName(SERIALIZED_NAME_EXECUTION_DATE)
  private Long executionDate;

  public static final String SERIALIZED_NAME_OPERATING_ON_ID = "operatingOnId";
  @SerializedName(SERIALIZED_NAME_OPERATING_ON_ID)
  private String operatingOnId;

  public static final String SERIALIZED_NAME_INTEGRATION_METADATA = "integrationMetadata";
  @SerializedName(SERIALIZED_NAME_INTEGRATION_METADATA)
  private IntegrationMetadata integrationMetadata;

  public static final String SERIALIZED_NAME_SOURCE = "source";
  @SerializedName(SERIALIZED_NAME_SOURCE)
  private CopiedFromEnv source;

  public static final String SERIALIZED_NAME_CUSTOM_WORKFLOW_METADATA = "customWorkflowMetadata";
  @SerializedName(SERIALIZED_NAME_CUSTOM_WORKFLOW_METADATA)
  private CustomWorkflowMeta customWorkflowMetadata;

  public FlagConfigApprovalRequestResponse() { 
  }

  public FlagConfigApprovalRequestResponse id(String id) {
    
    this.id = id;
    return this;
  }

   /**
   * The ID of this approval request
   * @return id
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "12ab3c45de678910abc12345", required = true, value = "The ID of this approval request")

  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }


  public FlagConfigApprovalRequestResponse version(Integer version) {
    
    this.version = version;
    return this;
  }

   /**
   * Version of the approval request
   * @return version
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "1", required = true, value = "Version of the approval request")

  public Integer getVersion() {
    return version;
  }


  public void setVersion(Integer version) {
    this.version = version;
  }


  public FlagConfigApprovalRequestResponse creationDate(Long creationDate) {
    
    this.creationDate = creationDate;
    return this;
  }

   /**
   * Get creationDate
   * @return creationDate
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public Long getCreationDate() {
    return creationDate;
  }


  public void setCreationDate(Long creationDate) {
    this.creationDate = creationDate;
  }


  public FlagConfigApprovalRequestResponse serviceKind(String serviceKind) {
    
    this.serviceKind = serviceKind;
    return this;
  }

   /**
   * Get serviceKind
   * @return serviceKind
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public String getServiceKind() {
    return serviceKind;
  }


  public void setServiceKind(String serviceKind) {
    this.serviceKind = serviceKind;
  }


  public FlagConfigApprovalRequestResponse requestorId(String requestorId) {
    
    this.requestorId = requestorId;
    return this;
  }

   /**
   * The ID of the member who requested the approval
   * @return requestorId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "12ab3c45de678910abc12345", value = "The ID of the member who requested the approval")

  public String getRequestorId() {
    return requestorId;
  }


  public void setRequestorId(String requestorId) {
    this.requestorId = requestorId;
  }


  public FlagConfigApprovalRequestResponse description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * A human-friendly name for the approval request
   * @return description
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "example: request approval from someone", value = "A human-friendly name for the approval request")

  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public FlagConfigApprovalRequestResponse reviewStatus(ReviewStatusEnum reviewStatus) {
    
    this.reviewStatus = reviewStatus;
    return this;
  }

   /**
   * Current status of the review of this approval request
   * @return reviewStatus
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "pending", required = true, value = "Current status of the review of this approval request")

  public ReviewStatusEnum getReviewStatus() {
    return reviewStatus;
  }


  public void setReviewStatus(ReviewStatusEnum reviewStatus) {
    this.reviewStatus = reviewStatus;
  }


  public FlagConfigApprovalRequestResponse allReviews(List<ReviewResponse> allReviews) {
    
    this.allReviews = allReviews;
    return this;
  }

  public FlagConfigApprovalRequestResponse addAllReviewsItem(ReviewResponse allReviewsItem) {
    this.allReviews.add(allReviewsItem);
    return this;
  }

   /**
   * An array of individual reviews of this approval request
   * @return allReviews
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "An array of individual reviews of this approval request")

  public List<ReviewResponse> getAllReviews() {
    return allReviews;
  }


  public void setAllReviews(List<ReviewResponse> allReviews) {
    this.allReviews = allReviews;
  }


  public FlagConfigApprovalRequestResponse notifyMemberIds(List<String> notifyMemberIds) {
    
    this.notifyMemberIds = notifyMemberIds;
    return this;
  }

  public FlagConfigApprovalRequestResponse addNotifyMemberIdsItem(String notifyMemberIdsItem) {
    this.notifyMemberIds.add(notifyMemberIdsItem);
    return this;
  }

   /**
   * An array of member IDs. These members are notified to review the approval request.
   * @return notifyMemberIds
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "[\"1234a56b7c89d012345e678f\"]", required = true, value = "An array of member IDs. These members are notified to review the approval request.")

  public List<String> getNotifyMemberIds() {
    return notifyMemberIds;
  }


  public void setNotifyMemberIds(List<String> notifyMemberIds) {
    this.notifyMemberIds = notifyMemberIds;
  }


  public FlagConfigApprovalRequestResponse appliedDate(Long appliedDate) {
    
    this.appliedDate = appliedDate;
    return this;
  }

   /**
   * Get appliedDate
   * @return appliedDate
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getAppliedDate() {
    return appliedDate;
  }


  public void setAppliedDate(Long appliedDate) {
    this.appliedDate = appliedDate;
  }


  public FlagConfigApprovalRequestResponse appliedByMemberId(String appliedByMemberId) {
    
    this.appliedByMemberId = appliedByMemberId;
    return this;
  }

   /**
   * The member ID of the member who applied the approval request
   * @return appliedByMemberId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "1234a56b7c89d012345e678f", value = "The member ID of the member who applied the approval request")

  public String getAppliedByMemberId() {
    return appliedByMemberId;
  }


  public void setAppliedByMemberId(String appliedByMemberId) {
    this.appliedByMemberId = appliedByMemberId;
  }


  public FlagConfigApprovalRequestResponse appliedByServiceTokenId(String appliedByServiceTokenId) {
    
    this.appliedByServiceTokenId = appliedByServiceTokenId;
    return this;
  }

   /**
   * The service token ID of the service token which applied the approval request
   * @return appliedByServiceTokenId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "1234a56b7c89d012345e678f", value = "The service token ID of the service token which applied the approval request")

  public String getAppliedByServiceTokenId() {
    return appliedByServiceTokenId;
  }


  public void setAppliedByServiceTokenId(String appliedByServiceTokenId) {
    this.appliedByServiceTokenId = appliedByServiceTokenId;
  }


  public FlagConfigApprovalRequestResponse status(StatusEnum status) {
    
    this.status = status;
    return this;
  }

   /**
   * Current status of the approval request
   * @return status
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "pending", required = true, value = "Current status of the approval request")

  public StatusEnum getStatus() {
    return status;
  }


  public void setStatus(StatusEnum status) {
    this.status = status;
  }


  public FlagConfigApprovalRequestResponse instructions(List<Map<String, Object>> instructions) {
    
    this.instructions = instructions;
    return this;
  }

  public FlagConfigApprovalRequestResponse addInstructionsItem(Map<String, Object> instructionsItem) {
    this.instructions.add(instructionsItem);
    return this;
  }

   /**
   * Get instructions
   * @return instructions
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public List<Map<String, Object>> getInstructions() {
    return instructions;
  }


  public void setInstructions(List<Map<String, Object>> instructions) {
    this.instructions = instructions;
  }


  public FlagConfigApprovalRequestResponse conflicts(List<Conflict> conflicts) {
    
    this.conflicts = conflicts;
    return this;
  }

  public FlagConfigApprovalRequestResponse addConflictsItem(Conflict conflictsItem) {
    this.conflicts.add(conflictsItem);
    return this;
  }

   /**
   * Details on any conflicting approval requests
   * @return conflicts
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "Details on any conflicting approval requests")

  public List<Conflict> getConflicts() {
    return conflicts;
  }


  public void setConflicts(List<Conflict> conflicts) {
    this.conflicts = conflicts;
  }


  public FlagConfigApprovalRequestResponse links(Map<String, Object> links) {
    
    this.links = links;
    return this;
  }

  public FlagConfigApprovalRequestResponse putLinksItem(String key, Object linksItem) {
    this.links.put(key, linksItem);
    return this;
  }

   /**
   * The location and content type of related resources
   * @return links
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "The location and content type of related resources")

  public Map<String, Object> getLinks() {
    return links;
  }


  public void setLinks(Map<String, Object> links) {
    this.links = links;
  }


  public FlagConfigApprovalRequestResponse executionDate(Long executionDate) {
    
    this.executionDate = executionDate;
    return this;
  }

   /**
   * Get executionDate
   * @return executionDate
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getExecutionDate() {
    return executionDate;
  }


  public void setExecutionDate(Long executionDate) {
    this.executionDate = executionDate;
  }


  public FlagConfigApprovalRequestResponse operatingOnId(String operatingOnId) {
    
    this.operatingOnId = operatingOnId;
    return this;
  }

   /**
   * ID of scheduled change to edit or delete
   * @return operatingOnId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "12ab3c45de678910abc12345", value = "ID of scheduled change to edit or delete")

  public String getOperatingOnId() {
    return operatingOnId;
  }


  public void setOperatingOnId(String operatingOnId) {
    this.operatingOnId = operatingOnId;
  }


  public FlagConfigApprovalRequestResponse integrationMetadata(IntegrationMetadata integrationMetadata) {
    
    this.integrationMetadata = integrationMetadata;
    return this;
  }

   /**
   * Get integrationMetadata
   * @return integrationMetadata
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public IntegrationMetadata getIntegrationMetadata() {
    return integrationMetadata;
  }


  public void setIntegrationMetadata(IntegrationMetadata integrationMetadata) {
    this.integrationMetadata = integrationMetadata;
  }


  public FlagConfigApprovalRequestResponse source(CopiedFromEnv source) {
    
    this.source = source;
    return this;
  }

   /**
   * Get source
   * @return source
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public CopiedFromEnv getSource() {
    return source;
  }


  public void setSource(CopiedFromEnv source) {
    this.source = source;
  }


  public FlagConfigApprovalRequestResponse customWorkflowMetadata(CustomWorkflowMeta customWorkflowMetadata) {
    
    this.customWorkflowMetadata = customWorkflowMetadata;
    return this;
  }

   /**
   * Get customWorkflowMetadata
   * @return customWorkflowMetadata
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public CustomWorkflowMeta getCustomWorkflowMetadata() {
    return customWorkflowMetadata;
  }


  public void setCustomWorkflowMetadata(CustomWorkflowMeta customWorkflowMetadata) {
    this.customWorkflowMetadata = customWorkflowMetadata;
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
  public FlagConfigApprovalRequestResponse putAdditionalProperty(String key, Object value) {
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
    FlagConfigApprovalRequestResponse flagConfigApprovalRequestResponse = (FlagConfigApprovalRequestResponse) o;
    return Objects.equals(this.id, flagConfigApprovalRequestResponse.id) &&
        Objects.equals(this.version, flagConfigApprovalRequestResponse.version) &&
        Objects.equals(this.creationDate, flagConfigApprovalRequestResponse.creationDate) &&
        Objects.equals(this.serviceKind, flagConfigApprovalRequestResponse.serviceKind) &&
        Objects.equals(this.requestorId, flagConfigApprovalRequestResponse.requestorId) &&
        Objects.equals(this.description, flagConfigApprovalRequestResponse.description) &&
        Objects.equals(this.reviewStatus, flagConfigApprovalRequestResponse.reviewStatus) &&
        Objects.equals(this.allReviews, flagConfigApprovalRequestResponse.allReviews) &&
        Objects.equals(this.notifyMemberIds, flagConfigApprovalRequestResponse.notifyMemberIds) &&
        Objects.equals(this.appliedDate, flagConfigApprovalRequestResponse.appliedDate) &&
        Objects.equals(this.appliedByMemberId, flagConfigApprovalRequestResponse.appliedByMemberId) &&
        Objects.equals(this.appliedByServiceTokenId, flagConfigApprovalRequestResponse.appliedByServiceTokenId) &&
        Objects.equals(this.status, flagConfigApprovalRequestResponse.status) &&
        Objects.equals(this.instructions, flagConfigApprovalRequestResponse.instructions) &&
        Objects.equals(this.conflicts, flagConfigApprovalRequestResponse.conflicts) &&
        Objects.equals(this.links, flagConfigApprovalRequestResponse.links) &&
        Objects.equals(this.executionDate, flagConfigApprovalRequestResponse.executionDate) &&
        Objects.equals(this.operatingOnId, flagConfigApprovalRequestResponse.operatingOnId) &&
        Objects.equals(this.integrationMetadata, flagConfigApprovalRequestResponse.integrationMetadata) &&
        Objects.equals(this.source, flagConfigApprovalRequestResponse.source) &&
        Objects.equals(this.customWorkflowMetadata, flagConfigApprovalRequestResponse.customWorkflowMetadata)&&
        Objects.equals(this.additionalProperties, flagConfigApprovalRequestResponse.additionalProperties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, version, creationDate, serviceKind, requestorId, description, reviewStatus, allReviews, notifyMemberIds, appliedDate, appliedByMemberId, appliedByServiceTokenId, status, instructions, conflicts, links, executionDate, operatingOnId, integrationMetadata, source, customWorkflowMetadata, additionalProperties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FlagConfigApprovalRequestResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
    sb.append("    serviceKind: ").append(toIndentedString(serviceKind)).append("\n");
    sb.append("    requestorId: ").append(toIndentedString(requestorId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    reviewStatus: ").append(toIndentedString(reviewStatus)).append("\n");
    sb.append("    allReviews: ").append(toIndentedString(allReviews)).append("\n");
    sb.append("    notifyMemberIds: ").append(toIndentedString(notifyMemberIds)).append("\n");
    sb.append("    appliedDate: ").append(toIndentedString(appliedDate)).append("\n");
    sb.append("    appliedByMemberId: ").append(toIndentedString(appliedByMemberId)).append("\n");
    sb.append("    appliedByServiceTokenId: ").append(toIndentedString(appliedByServiceTokenId)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    instructions: ").append(toIndentedString(instructions)).append("\n");
    sb.append("    conflicts: ").append(toIndentedString(conflicts)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    executionDate: ").append(toIndentedString(executionDate)).append("\n");
    sb.append("    operatingOnId: ").append(toIndentedString(operatingOnId)).append("\n");
    sb.append("    integrationMetadata: ").append(toIndentedString(integrationMetadata)).append("\n");
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
    sb.append("    customWorkflowMetadata: ").append(toIndentedString(customWorkflowMetadata)).append("\n");
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
    openapiFields.add("_id");
    openapiFields.add("_version");
    openapiFields.add("creationDate");
    openapiFields.add("serviceKind");
    openapiFields.add("requestorId");
    openapiFields.add("description");
    openapiFields.add("reviewStatus");
    openapiFields.add("allReviews");
    openapiFields.add("notifyMemberIds");
    openapiFields.add("appliedDate");
    openapiFields.add("appliedByMemberId");
    openapiFields.add("appliedByServiceTokenId");
    openapiFields.add("status");
    openapiFields.add("instructions");
    openapiFields.add("conflicts");
    openapiFields.add("_links");
    openapiFields.add("executionDate");
    openapiFields.add("operatingOnId");
    openapiFields.add("integrationMetadata");
    openapiFields.add("source");
    openapiFields.add("customWorkflowMetadata");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("_id");
    openapiRequiredFields.add("_version");
    openapiRequiredFields.add("creationDate");
    openapiRequiredFields.add("serviceKind");
    openapiRequiredFields.add("reviewStatus");
    openapiRequiredFields.add("allReviews");
    openapiRequiredFields.add("notifyMemberIds");
    openapiRequiredFields.add("status");
    openapiRequiredFields.add("instructions");
    openapiRequiredFields.add("conflicts");
    openapiRequiredFields.add("_links");
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to FlagConfigApprovalRequestResponse
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (FlagConfigApprovalRequestResponse.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in FlagConfigApprovalRequestResponse is not found in the empty JSON string", FlagConfigApprovalRequestResponse.openapiRequiredFields.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : FlagConfigApprovalRequestResponse.openapiRequiredFields) {
        if (jsonObj.get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonObj.toString()));
        }
      }
      if (jsonObj.get("_id") != null && !jsonObj.get("_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("_id").toString()));
      }
      if (jsonObj.get("serviceKind") != null && !jsonObj.get("serviceKind").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `serviceKind` to be a primitive type in the JSON string but got `%s`", jsonObj.get("serviceKind").toString()));
      }
      if (jsonObj.get("requestorId") != null && !jsonObj.get("requestorId").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `requestorId` to be a primitive type in the JSON string but got `%s`", jsonObj.get("requestorId").toString()));
      }
      if (jsonObj.get("description") != null && !jsonObj.get("description").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `description` to be a primitive type in the JSON string but got `%s`", jsonObj.get("description").toString()));
      }
      if (jsonObj.get("reviewStatus") != null && !jsonObj.get("reviewStatus").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `reviewStatus` to be a primitive type in the JSON string but got `%s`", jsonObj.get("reviewStatus").toString()));
      }
      JsonArray jsonArrayallReviews = jsonObj.getAsJsonArray("allReviews");
      if (jsonArrayallReviews != null) {
        // ensure the json data is an array
        if (!jsonObj.get("allReviews").isJsonArray()) {
          throw new IllegalArgumentException(String.format("Expected the field `allReviews` to be an array in the JSON string but got `%s`", jsonObj.get("allReviews").toString()));
        }

        // validate the optional field `allReviews` (array)
        for (int i = 0; i < jsonArrayallReviews.size(); i++) {
          ReviewResponse.validateJsonObject(jsonArrayallReviews.get(i).getAsJsonObject());
        };
      }
      // ensure the json data is an array
      if (jsonObj.get("notifyMemberIds") != null && !jsonObj.get("notifyMemberIds").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `notifyMemberIds` to be an array in the JSON string but got `%s`", jsonObj.get("notifyMemberIds").toString()));
      }
      if (jsonObj.get("appliedByMemberId") != null && !jsonObj.get("appliedByMemberId").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `appliedByMemberId` to be a primitive type in the JSON string but got `%s`", jsonObj.get("appliedByMemberId").toString()));
      }
      if (jsonObj.get("appliedByServiceTokenId") != null && !jsonObj.get("appliedByServiceTokenId").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `appliedByServiceTokenId` to be a primitive type in the JSON string but got `%s`", jsonObj.get("appliedByServiceTokenId").toString()));
      }
      if (jsonObj.get("status") != null && !jsonObj.get("status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("status").toString()));
      }
      // ensure the json data is an array
      if (jsonObj.get("instructions") != null && !jsonObj.get("instructions").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `instructions` to be an array in the JSON string but got `%s`", jsonObj.get("instructions").toString()));
      }
      JsonArray jsonArrayconflicts = jsonObj.getAsJsonArray("conflicts");
      if (jsonArrayconflicts != null) {
        // ensure the json data is an array
        if (!jsonObj.get("conflicts").isJsonArray()) {
          throw new IllegalArgumentException(String.format("Expected the field `conflicts` to be an array in the JSON string but got `%s`", jsonObj.get("conflicts").toString()));
        }

        // validate the optional field `conflicts` (array)
        for (int i = 0; i < jsonArrayconflicts.size(); i++) {
          Conflict.validateJsonObject(jsonArrayconflicts.get(i).getAsJsonObject());
        };
      }
      if (jsonObj.get("operatingOnId") != null && !jsonObj.get("operatingOnId").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `operatingOnId` to be a primitive type in the JSON string but got `%s`", jsonObj.get("operatingOnId").toString()));
      }
      // validate the optional field `integrationMetadata`
      if (jsonObj.getAsJsonObject("integrationMetadata") != null) {
        IntegrationMetadata.validateJsonObject(jsonObj.getAsJsonObject("integrationMetadata"));
      }
      // validate the optional field `source`
      if (jsonObj.getAsJsonObject("source") != null) {
        CopiedFromEnv.validateJsonObject(jsonObj.getAsJsonObject("source"));
      }
      // validate the optional field `customWorkflowMetadata`
      if (jsonObj.getAsJsonObject("customWorkflowMetadata") != null) {
        CustomWorkflowMeta.validateJsonObject(jsonObj.getAsJsonObject("customWorkflowMetadata"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!FlagConfigApprovalRequestResponse.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'FlagConfigApprovalRequestResponse' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<FlagConfigApprovalRequestResponse> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(FlagConfigApprovalRequestResponse.class));

       return (TypeAdapter<T>) new TypeAdapter<FlagConfigApprovalRequestResponse>() {
           @Override
           public void write(JsonWriter out, FlagConfigApprovalRequestResponse value) throws IOException {
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
           public FlagConfigApprovalRequestResponse read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             // store additional fields in the deserialized instance
             FlagConfigApprovalRequestResponse instance = thisAdapter.fromJsonTree(jsonObj);
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
  * Create an instance of FlagConfigApprovalRequestResponse given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of FlagConfigApprovalRequestResponse
  * @throws IOException if the JSON string is invalid with respect to FlagConfigApprovalRequestResponse
  */
  public static FlagConfigApprovalRequestResponse fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, FlagConfigApprovalRequestResponse.class);
  }

 /**
  * Convert an instance of FlagConfigApprovalRequestResponse to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

