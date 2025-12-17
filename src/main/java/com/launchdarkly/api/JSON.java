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


package com.launchdarkly.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.JsonElement;
import io.gsonfire.GsonFireBuilder;
import io.gsonfire.TypeSelector;

import okio.ByteString;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

/*
 * A JSON utility class
 *
 * NOTE: in the future, this class may be converted to static, which may break
 *       backward-compatibility
 */
public class JSON {
    private static Gson gson;
    private static boolean isLenientOnJson = false;
    private static DateTypeAdapter dateTypeAdapter = new DateTypeAdapter();
    private static SqlDateTypeAdapter sqlDateTypeAdapter = new SqlDateTypeAdapter();
    private static OffsetDateTimeTypeAdapter offsetDateTimeTypeAdapter = new OffsetDateTimeTypeAdapter();
    private static LocalDateTypeAdapter localDateTypeAdapter = new LocalDateTypeAdapter();
    private static ByteArrayAdapter byteArrayAdapter = new ByteArrayAdapter();

    @SuppressWarnings("unchecked")
    public static GsonBuilder createGson() {
        GsonFireBuilder fireBuilder = new GsonFireBuilder()
        ;
        GsonBuilder builder = fireBuilder.createGsonBuilder();
        return builder;
    }

    private static String getDiscriminatorValue(JsonElement readElement, String discriminatorField) {
        JsonElement element = readElement.getAsJsonObject().get(discriminatorField);
        if (null == element) {
            throw new IllegalArgumentException("missing discriminator field: <" + discriminatorField + ">");
        }
        return element.getAsString();
    }

    /**
     * Returns the Java class that implements the OpenAPI schema for the specified discriminator value.
     *
     * @param classByDiscriminatorValue The map of discriminator values to Java classes.
     * @param discriminatorValue The value of the OpenAPI discriminator in the input data.
     * @return The Java class that implements the OpenAPI schema
     */
    private static Class getClassByDiscriminator(Map classByDiscriminatorValue, String discriminatorValue) {
        Class clazz = (Class) classByDiscriminatorValue.get(discriminatorValue);
        if (null == clazz) {
            throw new IllegalArgumentException("cannot determine model class of name: <" + discriminatorValue + ">");
        }
        return clazz;
    }

    static {
        GsonBuilder gsonBuilder = createGson();
        gsonBuilder.registerTypeAdapter(Date.class, dateTypeAdapter);
        gsonBuilder.registerTypeAdapter(java.sql.Date.class, sqlDateTypeAdapter);
        gsonBuilder.registerTypeAdapter(OffsetDateTime.class, offsetDateTimeTypeAdapter);
        gsonBuilder.registerTypeAdapter(LocalDate.class, localDateTypeAdapter);
        gsonBuilder.registerTypeAdapter(byte[].class, byteArrayAdapter);
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfig.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigMaintainer.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigPatch.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigTargeting.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigTargetingDefaults.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigTargetingEnvironment.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigTargetingEnvironmentFallthrough.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigTargetingEnvironmentFallthroughRollout.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigTargetingEnvironmentFallthroughRolloutExperimentationAllocation.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigTargetingEnvironmentFallthroughRolloutVariation.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigTargetingEnvironmentRule.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigTargetingEnvironmentRuleClause.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigTargetingEnvironmentTarget.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigTargetingPatch.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigTargetingVariation.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigTargetingVariationValue.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigVariation.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigVariationPatch.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigVariationPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigVariationsResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigs.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIConfigsSummary.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AITool.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIToolPatch.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AIToolPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AITools.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Access.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AccessAllowedReason.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AccessAllowedRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AccessDenied.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AccessDeniedReason.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AccessTokenPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ActionInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ActionOutput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AgentGraph.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AgentGraphEdge.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AgentGraphEdgePost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AgentGraphPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AgentGraphs.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AiConfigsAccess.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AiConfigsAccessAllowedReason.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AiConfigsAccessAllowedRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AiConfigsAccessDenied.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AiConfigsAccessDeniedReason.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AiConfigsExperimentEnabledPeriodRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AiConfigsExperimentEnvironmentSettingRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AiConfigsExperimentInfoRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AiConfigsFilter.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AiConfigsLegacyExperimentRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AiConfigsLink.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AiConfigsMaintainerTeam.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AiConfigsMemberSummary.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AiConfigsMetricDataSourceRefRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AiConfigsMetricEventDefaultRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AiConfigsMetricListingRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AiConfigsModification.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AnnouncementAccess.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AnnouncementAccessAllowedReason.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AnnouncementAccessAllowedRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AnnouncementAccessDenied.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AnnouncementAccessDeniedReason.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AnnouncementAccessRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AnnouncementLink.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AnnouncementPaginatedLinks.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AnnouncementPatchOperation.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AnnouncementResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AnnouncementResponseLinks.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ApplicationCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ApplicationFlagCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ApplicationRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ApplicationVersionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ApplicationVersionsCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ApprovalRequestResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ApprovalRequestSetting.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ApprovalRequestSettingWithEnvs.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ApprovalRequestSettingsPatch.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ApprovalSettings.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ApprovalsCapabilityConfig.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AssignedToRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Audience.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AudienceConfiguration.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AudiencePost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AuditLogEntryListingRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AuditLogEntryListingRepCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AuditLogEntryRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AuditLogEventsHookCapabilityConfigPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AuditLogEventsHookCapabilityConfigRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.AuthorizedAppDataRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.BigSegmentStoreIntegration.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.BigSegmentStoreIntegrationCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.BigSegmentStoreIntegrationCollectionLinks.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.BigSegmentStoreIntegrationLinks.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.BigSegmentStoreStatus.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.BigSegmentTarget.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.BooleanDefaults.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.BooleanFlagDefaults.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.BranchCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.BranchRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.BulkEditMembersRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.BulkEditTeamsRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CallerIdentityRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CapabilityConfigPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CapabilityConfigRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Clause.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Client.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ClientCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ClientSideAvailability.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ClientSideAvailabilityPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CompletedBy.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ConditionInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ConditionOutput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Conflict.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ConflictOutput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ContextAttributeName.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ContextAttributeNames.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ContextAttributeNamesCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ContextAttributeValue.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ContextAttributeValues.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ContextAttributeValuesCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ContextInstanceEvaluation.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ContextInstanceEvaluationReason.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ContextInstanceEvaluations.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ContextInstanceRecord.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ContextInstanceSearch.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ContextInstanceSegmentMembership.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ContextInstanceSegmentMemberships.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ContextInstances.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ContextKindRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ContextKindsCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ContextRecord.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ContextSearch.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Contexts.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CopiedFromEnv.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CoreLink.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CreateAnnouncementBody.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CreateApprovalRequestRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CreateCopyFlagConfigApprovalRequestRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CreateFlagConfigApprovalRequestRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CreatePhaseInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CreateReleaseInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CreateReleasePipelineInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CreateWorkflowTemplateInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CustomProperty.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CustomRole.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CustomRolePost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CustomRoles.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CustomWorkflowInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CustomWorkflowMeta.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CustomWorkflowOutput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CustomWorkflowStageMeta.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.CustomWorkflowsListingOutput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.DefaultClientSideAvailability.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.DefaultClientSideAvailabilityPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Defaults.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.DependentExperimentRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.DependentFlag.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.DependentFlagEnvironment.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.DependentFlagsByEnvironment.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.DependentMeasuredRolloutRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.DependentMetricGroupRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.DependentMetricGroupRepWithMetrics.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.DependentMetricOrMetricGroupRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.DeploymentCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.DeploymentRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Destination.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.DestinationPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Destinations.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.DynamicOptions.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.DynamicOptionsParser.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Endpoint.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Environment.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.EnvironmentPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.EnvironmentSummary.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Environments.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Error.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.EvaluationReason.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.EvaluationsSummary.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.EventFilter.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExecutionOutput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpandableApprovalRequestResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpandableApprovalRequestsResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpandedAIConfig.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpandedDirectlyLinkedFlag.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpandedDirectlyLinkedFlags.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpandedDirectlyLinkedSegment.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpandedDirectlyLinkedSegments.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpandedFlag.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpandedFlagRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpandedLinkedAIConfigs.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpandedLinkedFlags.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpandedLinkedMetrics.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpandedLinkedResources.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpandedLinkedResourcesAIConfigs.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpandedLinkedResourcesFlags.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpandedLinkedResourcesItems.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpandedLinkedResourcesMetrics.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpandedLinkedResourcesSegments.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpandedLinkedSegments.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpandedMetric.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpandedResourceRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpandedSegment.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Experiment.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExperimentAllocationRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExperimentCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExperimentEnabledPeriodRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExperimentEnvironmentSettingRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExperimentInfoRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExperimentPatchInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExperimentPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpiringTarget.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpiringTargetError.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpiringTargetGetResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpiringTargetPatchResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpiringUserTargetGetResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpiringUserTargetItem.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExpiringUserTargetPatchResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Export.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Extinction.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ExtinctionCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FailedResourceLink.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FailureReasonRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FeatureFlag.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FeatureFlagBody.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FeatureFlagConfig.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FeatureFlagScheduledChange.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FeatureFlagScheduledChanges.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FeatureFlagStatus.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FeatureFlagStatusAcrossEnvironments.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FeatureFlagStatuses.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FeatureFlags.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FileRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Filter.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagConfigApprovalRequestResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagConfigApprovalRequestsResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagConfigEvaluation.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagConfigMigrationSettingsRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagCopyConfigEnvironment.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagCopyConfigPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagDefaultsRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagEventCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagEventExperiment.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagEventExperimentCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagEventExperimentIteration.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagEventImpactRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagEventMemberRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagEventRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagFollowersByProjEnvGetRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagFollowersGetRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagImportConfigurationPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagImportIntegration.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagImportIntegrationCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagImportIntegrationCollectionLinks.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagImportIntegrationLinks.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagImportStatus.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagLinkCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagLinkMember.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagLinkPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagLinkRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagListingRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagMigrationSettingsRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagPrerequisitePost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagReferenceCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagReferenceRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagScheduledChangesInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagSempatch.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagStatusRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagSummary.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagTriggerInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FlagsSummary.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FollowFlagMember.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FollowersPerFlag.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ForbiddenErrorRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.FormVariable.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.GenerateTrustPolicyPostRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.GenerateWarehouseDestinationKeyPairPostRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.GetAnnouncementsPublic200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.GuardedReleaseConfig.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.HMACSignature.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.HeaderItems.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.HoldoutDetailRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.HoldoutPatchInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.HoldoutPostRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.HoldoutRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.HoldoutsCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.HunkRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InitiatorRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightGroup.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightGroupCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightGroupCollectionMetadata.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightGroupCollectionScoreMetadata.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightGroupScores.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightGroupsCountByIndicator.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightPeriod.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightScores.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightsChart.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightsChartBounds.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightsChartMetadata.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightsChartMetric.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightsChartSeries.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightsChartSeriesDataPoint.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightsChartSeriesMetadata.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightsChartSeriesMetadataAxis.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightsMetricIndicatorRange.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightsMetricScore.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightsMetricTierDefinition.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightsRepository.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightsRepositoryCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightsRepositoryProject.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightsRepositoryProjectCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InsightsRepositoryProjectMappings.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InstructionUserRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Integration.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.IntegrationConfigurationCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.IntegrationConfigurationPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.IntegrationConfigurationsRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.IntegrationDeliveryConfiguration.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.IntegrationDeliveryConfigurationCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.IntegrationDeliveryConfigurationCollectionLinks.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.IntegrationDeliveryConfigurationLinks.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.IntegrationDeliveryConfigurationPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.IntegrationDeliveryConfigurationResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.IntegrationMetadata.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.IntegrationStatus.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.IntegrationStatusRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.IntegrationSubscriptionStatusRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Integrations.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.InvalidRequestErrorRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.IpList.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.IterationInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.IterationRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.JudgeAttachment.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.JudgeConfiguration.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.LastSeenMetadata.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.LayerCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.LayerConfigurationRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.LayerPatchInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.LayerPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.LayerRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.LayerReservationRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.LayerSnapshotRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.LeadTimeStagesRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.LegacyExperimentRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Link.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.LinkResourceSuccessResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Maintainer.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MaintainerMember.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MaintainerRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MaintainerTeam.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Member.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MemberDataRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MemberImportItem.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MemberPermissionGrantSummaryRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MemberSummary.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MemberTeamSummaryRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MemberTeamsPostInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Members.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MembersPatchInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Message.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MethodNotAllowedErrorRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MetricByVariation.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MetricCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MetricDataSourceRefRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MetricEventDefaultRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MetricGroupCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MetricGroupPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MetricGroupRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MetricInGroupRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MetricInMetricGroupInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MetricInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MetricListingRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MetricPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MetricRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MetricV2Rep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Metrics.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MetricsSummary.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MigrationSafetyIssueRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MigrationSettingsPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ModelConfig.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ModelConfigPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ModelImport.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Modification.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MultiEnvironmentDependentFlag.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.MultiEnvironmentDependentFlags.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.NamingConvention.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.NewMemberForm.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.NotFoundErrorRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.OauthClientPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.OptionsArray.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PaginatedLinks.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ParameterDefault.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ParameterRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ParentAndSelfLinks.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ParentLink.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ParentResourceRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PatchFailedErrorRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PatchFlagsRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PatchOperation.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PatchSegmentExpiringTargetInputRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PatchSegmentExpiringTargetInstruction.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PatchSegmentInstruction.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PatchSegmentRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PatchUsersRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PatchWithComment.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PermissionGrantInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Phase.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PhaseInfo.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PostApprovalRequestApplyRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PostApprovalRequestReviewRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PostDeploymentEventInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PostFlagScheduledChangesInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PostInsightGroupParams.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PostReleasePolicyRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Prerequisite.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ProgressiveReleaseConfig.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Project.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ProjectPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ProjectRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ProjectSummary.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ProjectSummaryCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Projects.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PullRequestCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PullRequestLeadTimeRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PullRequestRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PutBranch.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.PutReleasePolicyRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.RandomizationSettingsPut.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.RandomizationSettingsRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.RandomizationUnitInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.RandomizationUnitRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.RateLimitedErrorRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.RecentTriggerBody.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ReferenceRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.RelatedExperimentRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.RelayAutoConfigCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.RelayAutoConfigPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.RelayAutoConfigRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Release.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ReleaseAudience.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ReleaseGuardianConfiguration.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ReleaseGuardianConfigurationInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ReleasePhase.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ReleasePipeline.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ReleasePipelineCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ReleasePoliciesAccess.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ReleasePoliciesAccessAllowedReason.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ReleasePoliciesAccessAllowedRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ReleasePoliciesAccessDenied.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ReleasePoliciesAccessDeniedReason.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ReleasePoliciesAccessRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ReleasePoliciesResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ReleasePolicy.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ReleasePolicyScope.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ReleasePolicyStage.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ReleaseProgression.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ReleaseProgressionCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ReleaserAudienceConfigInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.RepositoryCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.RepositoryPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.RepositoryRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ResourceAccess.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ResourceIDResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ResourceId.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ResourceSummary.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.RestrictedModelError.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.RestrictedModelsRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.RestrictedModelsResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ReviewOutput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ReviewResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Rollout.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.RootResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Rule.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.RuleClause.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.SdkListRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.SdkVersionListRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.SdkVersionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.SegmentBody.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.SegmentMetadata.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.SegmentTarget.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.SegmentUserList.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.SegmentUserState.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.SegmentsSummary.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.SeriesListRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.SeriesListRepFloat.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.SimpleHoldoutRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.SourceEnv.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.SourceFlag.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.StageInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.StageOutput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Statement.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.StatementPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.StatisticCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.StatisticRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.StatisticsRoot.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.StatusConflictErrorRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.StatusResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.StatusServiceUnavailable.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.StoreIntegrationError.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.SubjectDataRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.SubscriptionPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.TagsCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.TagsLink.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Target.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.TargetResourceRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Team.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.TeamCustomRole.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.TeamCustomRoles.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.TeamImportsRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.TeamMaintainers.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.TeamMembers.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.TeamPatchInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.TeamPostInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.TeamProjects.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Teams.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.TeamsPatchInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.TimestampRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Token.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.TokenSummary.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Tokens.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.TreatmentInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.TreatmentParameterInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.TreatmentRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.TriggerPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.TriggerWorkflowCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.TriggerWorkflowRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.TrustPolicyDetails.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.TrustPolicyStatement.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.UnauthorizedErrorRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.UnlinkResourceSuccessResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.UpdatePhaseStatusInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.UpdateReleasePipelineInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.UpsertContextKindPayload.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.UpsertFlagDefaultsPayload.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.UpsertPayloadRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.UpsertResponseRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.UrlPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.User.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.UserAttributeNamesRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.UserFlagSetting.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.UserFlagSettings.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.UserRecord.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.UserSegment.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.UserSegmentRule.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.UserSegments.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Users.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.UsersRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ValidationFailedErrorRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ValuePut.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Variation.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.VariationEvalSummary.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.VariationOrRolloutRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.VariationSummary.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.VariationTool.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.VariationToolPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.VersionsRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.View.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ViewLinkRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ViewLinkRequestKeys.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ViewLinkRequestSegmentIdentifier.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ViewLinkRequestSegmentIdentifiers.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ViewLinkedResource.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ViewLinkedResourceDetails.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ViewLinkedResources.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ViewPatch.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ViewPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Views.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ViewsAccess.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ViewsAccessAllowedReason.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ViewsAccessAllowedRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ViewsAccessDenied.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ViewsAccessDeniedReason.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ViewsAccessRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ViewsLink.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ViewsMaintainerMember.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ViewsMaintainerTeam.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ViewsPaginatedLinks.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.ViewsSelfLink.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Webhook.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.WebhookPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.Webhooks.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.WeightedVariation.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.WorkflowTemplateMetadata.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.WorkflowTemplateOutput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.WorkflowTemplateParameter.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.launchdarkly.api.model.WorkflowTemplatesListingOutputRep.CustomTypeAdapterFactory());
        gson = gsonBuilder.create();
    }

    /**
     * Get Gson.
     *
     * @return Gson
     */
    public static Gson getGson() {
        return gson;
    }

    /**
     * Set Gson.
     *
     * @param gson Gson
     */
    public static void setGson(Gson gson) {
        JSON.gson = gson;
    }

    public static void setLenientOnJson(boolean lenientOnJson) {
        isLenientOnJson = lenientOnJson;
    }

    /**
     * Serialize the given Java object into JSON string.
     *
     * @param obj Object
     * @return String representation of the JSON
     */
    public static String serialize(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * Deserialize the given JSON string to Java object.
     *
     * @param <T>        Type
     * @param body       The JSON string
     * @param returnType The type to deserialize into
     * @return The deserialized Java object
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(String body, Type returnType) {
        try {
            if (isLenientOnJson) {
                JsonReader jsonReader = new JsonReader(new StringReader(body));
                // see https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/stream/JsonReader.html#setLenient(boolean)
                jsonReader.setLenient(true);
                return gson.fromJson(jsonReader, returnType);
            } else {
                return gson.fromJson(body, returnType);
            }
        } catch (JsonParseException e) {
            // Fallback processing when failed to parse JSON form response body:
            // return the response body string directly for the String return type;
            if (returnType.equals(String.class)) {
                return (T) body;
            } else {
                throw (e);
            }
        }
    }

    /**
    * Deserialize the given JSON InputStream to a Java object.
    *
    * @param <T>         Type
    * @param inputStream The JSON InputStream
    * @param returnType  The type to deserialize into
    * @return The deserialized Java object
    */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(InputStream inputStream, Type returnType) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
        if (isLenientOnJson) {
            // see https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/stream/JsonReader.html#setLenient(boolean)
            JsonReader jsonReader = new JsonReader(reader);
            jsonReader.setLenient(true);
            return gson.fromJson(jsonReader, returnType);
            } else {
                return gson.fromJson(reader, returnType);
            }
        }
    }

    /**
     * Gson TypeAdapter for Byte Array type
     */
    public static class ByteArrayAdapter extends TypeAdapter<byte[]> {

        @Override
        public void write(JsonWriter out, byte[] value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(ByteString.of(value).base64());
            }
        }

        @Override
        public byte[] read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String bytesAsBase64 = in.nextString();
                    ByteString byteString = ByteString.decodeBase64(bytesAsBase64);
                    return byteString.toByteArray();
            }
        }
    }

    /**
     * Gson TypeAdapter for JSR310 OffsetDateTime type
     */
    public static class OffsetDateTimeTypeAdapter extends TypeAdapter<OffsetDateTime> {

        private DateTimeFormatter formatter;

        public OffsetDateTimeTypeAdapter() {
            this(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }

        public OffsetDateTimeTypeAdapter(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        public void setFormat(DateTimeFormatter dateFormat) {
            this.formatter = dateFormat;
        }

        @Override
        public void write(JsonWriter out, OffsetDateTime date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                out.value(formatter.format(date));
            }
        }

        @Override
        public OffsetDateTime read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String date = in.nextString();
                    if (date.endsWith("+0000")) {
                        date = date.substring(0, date.length()-5) + "Z";
                    }
                    return OffsetDateTime.parse(date, formatter);
            }
        }
    }

    /**
     * Gson TypeAdapter for JSR310 LocalDate type
     */
    public static class LocalDateTypeAdapter extends TypeAdapter<LocalDate> {

        private DateTimeFormatter formatter;

        public LocalDateTypeAdapter() {
            this(DateTimeFormatter.ISO_LOCAL_DATE);
        }

        public LocalDateTypeAdapter(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        public void setFormat(DateTimeFormatter dateFormat) {
            this.formatter = dateFormat;
        }

        @Override
        public void write(JsonWriter out, LocalDate date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                out.value(formatter.format(date));
            }
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String date = in.nextString();
                    return LocalDate.parse(date, formatter);
            }
        }
    }

    public static void setOffsetDateTimeFormat(DateTimeFormatter dateFormat) {
        offsetDateTimeTypeAdapter.setFormat(dateFormat);
    }

    public static void setLocalDateFormat(DateTimeFormatter dateFormat) {
        localDateTypeAdapter.setFormat(dateFormat);
    }

    /**
     * Gson TypeAdapter for java.sql.Date type
     * If the dateFormat is null, a simple "yyyy-MM-dd" format will be used
     * (more efficient than SimpleDateFormat).
     */
    public static class SqlDateTypeAdapter extends TypeAdapter<java.sql.Date> {

        private DateFormat dateFormat;

        public SqlDateTypeAdapter() {}

        public SqlDateTypeAdapter(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        public void setFormat(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        @Override
        public void write(JsonWriter out, java.sql.Date date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                String value;
                if (dateFormat != null) {
                    value = dateFormat.format(date);
                } else {
                    value = date.toString();
                }
                out.value(value);
            }
        }

        @Override
        public java.sql.Date read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String date = in.nextString();
                    try {
                        if (dateFormat != null) {
                            return new java.sql.Date(dateFormat.parse(date).getTime());
                        }
                        return new java.sql.Date(ISO8601Utils.parse(date, new ParsePosition(0)).getTime());
                    } catch (ParseException e) {
                        throw new JsonParseException(e);
                    }
            }
        }
    }

    /**
     * Gson TypeAdapter for java.util.Date type
     * If the dateFormat is null, ISO8601Utils will be used.
     */
    public static class DateTypeAdapter extends TypeAdapter<Date> {

        private DateFormat dateFormat;

        public DateTypeAdapter() {}

        public DateTypeAdapter(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        public void setFormat(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        @Override
        public void write(JsonWriter out, Date date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                String value;
                if (dateFormat != null) {
                    value = dateFormat.format(date);
                } else {
                    value = ISO8601Utils.format(date, true);
                }
                out.value(value);
            }
        }

        @Override
        public Date read(JsonReader in) throws IOException {
            try {
                switch (in.peek()) {
                    case NULL:
                        in.nextNull();
                        return null;
                    default:
                        String date = in.nextString();
                        try {
                            if (dateFormat != null) {
                                return dateFormat.parse(date);
                            }
                            return ISO8601Utils.parse(date, new ParsePosition(0));
                        } catch (ParseException e) {
                            throw new JsonParseException(e);
                        }
                }
            } catch (IllegalArgumentException e) {
                throw new JsonParseException(e);
            }
        }
    }

    public static void setDateFormat(DateFormat dateFormat) {
        dateTypeAdapter.setFormat(dateFormat);
    }

    public static void setSqlDateFormat(DateFormat dateFormat) {
        sqlDateTypeAdapter.setFormat(dateFormat);
    }
}
