/*
 * LaunchDarkly REST API
 * This documentation describes LaunchDarkly's REST API. To access the complete OpenAPI spec directly, use [Get OpenAPI spec](https://launchdarkly.com/docs/api/other/get-openapi-spec).  To learn how to use LaunchDarkly using the user interface (UI) instead, read our [product documentation](https://launchdarkly.com/docs/home).  ## Authentication  LaunchDarkly's REST API uses the HTTPS protocol with a minimum TLS version of 1.2.  All REST API resources are authenticated with either [personal or service access tokens](https://launchdarkly.com/docs/home/account/api), or session cookies. Other authentication mechanisms are not supported. You can manage personal access tokens on your [**Authorization**](https://app.launchdarkly.com/settings/authorization) page in the LaunchDarkly UI.  LaunchDarkly also has SDK keys, mobile keys, and client-side IDs that are used by our server-side SDKs, mobile SDKs, and JavaScript-based SDKs, respectively. **These keys cannot be used to access our REST API**. These keys are environment-specific, and can only perform read-only operations such as fetching feature flag settings.  | Auth mechanism                                                                                  | Allowed resources                                                                                     | Use cases                                          | | ----------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------- | -------------------------------------------------- | | [Personal or service access tokens](https://launchdarkly.com/docs/home/account/api) | Can be customized on a per-token basis                                                                | Building scripts, custom integrations, data export. | | SDK keys                                                                                        | Can only access read-only resources specific to server-side SDKs. Restricted to a single environment. | Server-side SDKs                     | | Mobile keys                                                                                     | Can only access read-only resources specific to mobile SDKs, and only for flags marked available to mobile keys. Restricted to a single environment.           | Mobile SDKs                                        | | Client-side ID                                                                                  | Can only access read-only resources specific to JavaScript-based client-side SDKs, and only for flags marked available to client-side. Restricted to a single environment.           | Client-side JavaScript                             |  > #### Keep your access tokens and SDK keys private > > Access tokens should _never_ be exposed in untrusted contexts. Never put an access token in client-side JavaScript, or embed it in a mobile application. LaunchDarkly has special mobile keys that you can embed in mobile apps. If you accidentally expose an access token or SDK key, you can reset it from your [**Authorization**](https://app.launchdarkly.com/settings/authorization) page. > > The client-side ID is safe to embed in untrusted contexts. It's designed for use in client-side JavaScript.  ### Authentication using request header  The preferred way to authenticate with the API is by adding an `Authorization` header containing your access token to your requests. The value of the `Authorization` header must be your access token.  Manage personal access tokens from the [**Authorization**](https://app.launchdarkly.com/settings/authorization) page.  ### Authentication using session cookie  For testing purposes, you can make API calls directly from your web browser. If you are logged in to the LaunchDarkly application, the API will use your existing session to authenticate calls.  Depending on the permissions granted as part of your [role](https://launchdarkly.com/docs/home/account/roles), you may not have permission to perform some API calls. You will receive a `401` response code in that case.  > ### Modifying the Origin header causes an error > > LaunchDarkly validates that the Origin header for any API request authenticated by a session cookie matches the expected Origin header. The expected Origin header is `https://app.launchdarkly.com`. > > If the Origin header does not match what's expected, LaunchDarkly returns an error. This error can prevent the LaunchDarkly app from working correctly. > > Any browser extension that intentionally changes the Origin header can cause this problem. For example, Cross-Origin Resource Sharing (CORS) extensions used during development can modify the Origin header and cause the app to fail. > > To prevent this error, do not modify your Origin header. > > LaunchDarkly does not require origin matching when authenticating with an access token, so this issue does not affect normal API usage.  ## Representations  All resources expect and return JSON response bodies. Error responses also send a JSON body. To learn more about the error format of the API, read [Errors](https://launchdarkly.com/docs/api#errors).  In practice this means that you always get a response with a `Content-Type` header set to `application/json`.  In addition, request bodies for `PATCH`, `POST`, and `PUT` requests must be encoded as JSON with a `Content-Type` header set to `application/json`.  Unless an endpoint's documentation says otherwise, the API ignores request body fields that are not part of that endpoint's request schema, and does not return an error for them. A successful response does not mean that every field you sent was applied. To confirm the result, check the response body.  ### Summary and detailed representations  When you fetch a list of resources, the response includes only the most important attributes of each resource. This is a _summary representation_ of the resource. When you fetch an individual resource, such as a single feature flag, you receive a _detailed representation_ of the resource.  The best way to find a detailed representation is to follow links. Every summary representation includes a link to its detailed representation.  ### Expanding responses  Sometimes the detailed representation of a resource does not include all of the attributes of the resource by default. If this is the case, the request method will clearly document this and describe which attributes you can include in an expanded response.  To include the additional attributes, append the `expand` request parameter to your request and add a comma-separated list of the attributes to include. For example, when you append `?expand=members,maintainers` to the [Get team](https://launchdarkly.com/docs/api/teams/get-team) endpoint, the expanded response includes both of these attributes.  ### Links and addressability  The best way to navigate the API is by following links. These are attributes in representations that link to other resources. The API always uses the same format for links:  - Links to other resources within the API are encapsulated in a `_links` object - If the resource has a corresponding link to HTML content on the site, it is stored in a special `_site` link  Each link has two attributes:  - An `href`, which contains the URL - A `type`, which describes the content type  For example, a feature resource might return the following:  ```json {   \"_links\": {     \"parent\": {       \"href\": \"/api/features\",       \"type\": \"application/json\"     },     \"self\": {       \"href\": \"/api/features/sort.order\",       \"type\": \"application/json\"     }   },   \"_site\": {     \"href\": \"/features/sort.order\",     \"type\": \"text/html\"   } } ```  From this, you can navigate to the parent collection of features by following the `parent` link, or navigate to the site page for the feature by following the `_site` link.  Collections are always represented as a JSON object with an `items` attribute containing an array of representations. Like all other representations, collections have `_links` defined at the top level.  Paginated collections include `first`, `last`, `next`, and `prev` links containing a URL with the respective set of elements in the collection.  ## Updates  Resources that accept partial updates use the `PATCH` verb. Most resources support the [JSON patch](https://launchdarkly.com/docs/api#updates-using-json-patch) format. Some resources also support the [JSON merge patch](https://launchdarkly.com/docs/api#updates-using-json-merge-patch) format, and some resources support the [semantic patch](https://launchdarkly.com/docs/api#updates-using-semantic-patch) format, which is a way to specify the modifications to perform as a set of executable instructions. Each resource supports optional [comments](https://launchdarkly.com/docs/api#updates-with-comments) that you can submit with updates. Comments appear in outgoing webhooks, the audit log, and other integrations.  When a resource supports both JSON patch and semantic patch, we document both in the request method. However, the specific request body fields and descriptions included in our documentation only match one type of patch or the other.  ### Updates using JSON patch  [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) is a way to specify the modifications to perform on a resource. JSON patch uses paths and a limited set of operations to describe how to transform the current state of the resource into a new state. JSON patch documents are always arrays, where each element contains an operation, a path to the field to update, and the new value.  For example, in this feature flag representation:  ```json {     \"name\": \"New recommendations engine\",     \"key\": \"engine.enable\",     \"description\": \"This is the description\",     ... } ``` You can change the feature flag's description with the following patch document:  ```json [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"This is the new description\" }] ```  You can specify multiple modifications to perform in a single request. You can also test that certain preconditions are met before applying the patch:  ```json [   { \"op\": \"test\", \"path\": \"/version\", \"value\": 10 },   { \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" } ] ```  The above patch request tests whether the feature flag's `version` is `10`, and if so, changes the feature flag's description.  Attributes that are not editable, such as a resource's `_links`, have names that start with an underscore.  ### Updates using JSON merge patch  [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) is another format for specifying the modifications to perform on a resource. JSON merge patch is less expressive than JSON patch. However, in many cases it is simpler to construct a merge patch document. For example, you can change a feature flag's description with the following merge patch document:  ```json {   \"description\": \"New flag description\" } ```  ### Updates using semantic patch  Some resources support the semantic patch format. A semantic patch is a way to specify the modifications to perform on a resource as a set of executable instructions.  Semantic patch allows you to be explicit about intent using precise, custom instructions. In many cases, you can define semantic patch instructions independently of the current state of the resource. This can be useful when defining a change that may be applied at a future date.  To make a semantic patch request, you must append `domain-model=launchdarkly.semanticpatch` to your `Content-Type` header.  Here's how:  ``` Content-Type: application/json; domain-model=launchdarkly.semanticpatch ```  If you call a semantic patch resource without this header, you will receive a `400` response because your semantic patch will be interpreted as a JSON patch.  The body of a semantic patch request takes the following properties:  * `comment` (string): (Optional) A description of the update. * `environmentKey` (string): (Required for some resources only) The environment key. * `instructions` (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a `kind` property that indicates the instruction. If the instruction requires parameters, you must include those parameters as additional fields in the object. The documentation for each resource that supports semantic patch includes the available instructions and any additional parameters.  For example:  ```json {   \"comment\": \"optional comment\",   \"instructions\": [ {\"kind\": \"turnFlagOn\"} ] } ```  Semantic patches are not applied partially; either all of the instructions are applied or none of them are. If **any** instruction is invalid, the endpoint returns an error and will not change the resource. If all instructions are valid, the request succeeds and the resources are updated if necessary, or left unchanged if they are already in the state you request.  ### Updates with comments  You can submit optional comments with `PATCH` changes.  To submit a comment along with a JSON patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"patch\": [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" }] } ```  To submit a comment along with a JSON merge patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"merge\": { \"description\": \"New flag description\" } } ```  To submit a comment along with a semantic patch, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"instructions\": [ {\"kind\": \"turnFlagOn\"} ] } ```  ## Errors  The API always returns errors in a common format. Here's an example:  ```json {   \"code\": \"invalid_request\",   \"message\": \"A feature with that key already exists\",   \"id\": \"30ce6058-87da-11e4-b116-123b93f75cba\" } ```  The `code` indicates the general class of error. The `message` is a human-readable explanation of what went wrong. The `id` is a unique identifier. Use it when you're working with LaunchDarkly Support to debug a problem with a specific API call.  ### HTTP status error response codes  | Code | Definition        | Description                                                                                       | Possible Solution                                                | | ---- | ----------------- | ------------------------------------------------------------------------------------------- | ---------------------------------------------------------------- | | 400  | Invalid request       | The request cannot be understood.                                    | Ensure JSON syntax in request body is correct.                   | | 401  | Invalid access token      | Requestor is unauthorized or does not have permission for this API call.                                                | Ensure your API access token is valid and has the appropriate permissions.                                     | | 403  | Forbidden         | Requestor does not have access to this resource.                                                | Ensure that the account member or access token has proper permissions set. | | 404  | Invalid resource identifier | The requested resource is not valid. | Ensure that the resource is correctly identified by ID or key. | | 405  | Method not allowed | The request method is not allowed on this resource. | Ensure that the HTTP verb is correct. | | 409  | Conflict          | The API request can not be completed because it conflicts with a concurrent API request. | Retry your request.                                              | | 422  | Unprocessable entity | The API request can not be completed because the update description can not be understood. | Ensure that the request body is correct for the type of patch you are using, either JSON patch or semantic patch. | 429  | Too many requests | Read [Rate limiting](https://launchdarkly.com/docs/api#rate-limiting).                                               | Wait and try again later.                                        |  ## CORS  The LaunchDarkly API supports Cross Origin Resource Sharing (CORS) for AJAX requests from any origin. If an `Origin` header is given in a request, it will be echoed as an explicitly allowed origin. Otherwise the request returns a wildcard, `Access-Control-Allow-Origin: *`. For more information on CORS, read the [CORS W3C Recommendation](http://www.w3.org/TR/cors). Example CORS headers might look like:  ```http Access-Control-Allow-Headers: Accept, Content-Type, Content-Length, Accept-Encoding, Authorization Access-Control-Allow-Methods: OPTIONS, GET, DELETE, PATCH Access-Control-Allow-Origin: * Access-Control-Max-Age: 300 ```  You can make authenticated CORS calls just as you would make same-origin calls, using either [token or session-based authentication](https://launchdarkly.com/docs/api#authentication). If you are using session authentication, you should set the `withCredentials` property for your `xhr` request to `true`. You should never expose your access tokens to untrusted entities.  ## Rate limiting  We use several rate-limiting strategies to ensure the availability of our APIs. Rate-limited calls to our APIs return a `429` status code and include headers to indicate the current rate limit status. The specific headers returned depend on the API route that was called. Limits differ based on the route, authentication mechanism, and other factors.  Each set of headers below appears only when the corresponding limit is being enforced for your call. A given route may be subject to any combination of these limits, so a response can include one, several, or none of these headers. A missing header indicates that the limit was not applied to this specific call; it does not necessarily indicate that the limit does not exist. To reduce usage before hitting a `429` status, program against whichever rate limit headers are present rather than expecting a specific header.  We do not publicly document the specific number of calls permitted by any of these limits, and these limits may change. We encourage clients to program against the specification and rely on the headers described below, rather than hardcoding the current limits.  > ### Rate limiting and SDKs > > LaunchDarkly SDKs are never rate limited and do not use the API endpoints defined here. LaunchDarkly uses a different set of approaches, including streaming/server-sent events and a global CDN, to ensure availability to the routes used by LaunchDarkly SDKs.  ### Global rate limits  Authenticated requests are subject to a global limit. This is the maximum number of calls that your account can make to the API per ten seconds. All service and personal access tokens on the account share this limit, so exceeding the limit with one access token will impact other tokens. Calls that are subject to global rate limits may return the headers below:  | Header name                    | Description                                                                      | | ------------------------------ | -------------------------------------------------------------------------------- | | `X-Ratelimit-Global-Limit`     | The maximum number of requests the account is permitted to make per ten seconds. | | `X-Ratelimit-Global-Remaining` | The number of requests remaining in the current global rate limit window.        | | `X-Ratelimit-Reset`            | The time at which the current rate limit window resets in epoch milliseconds.    |  ### Route-level rate limits  Some authenticated routes have custom rate limits. These also reset every ten seconds. Any service or personal access tokens hitting the same route share this limit, so exceeding the limit with one access token may impact other tokens. Calls that are subject to route-level rate limits return the headers below:  | Header name                   | Description                                                                                           | | ----------------------------- | ----------------------------------------------------------------------------------------------------- | | `X-Ratelimit-Route-Limit`     | The maximum number of requests to the current route permitted per ten seconds.           | | `X-Ratelimit-Route-Remaining` | The number of requests remaining for the current route in the current rate limit window. | | `X-Ratelimit-Reset`           | The time at which the current rate limit window resets in epoch milliseconds.            |  A _route_ represents a specific URL pattern and verb. For example, the [Delete environment](https://launchdarkly.com/docs/api/environments/delete-environment) endpoint is considered a single route, and each call to delete an environment counts against your route-level rate limit for that route.  ### Access token rate limits  Some calls are rate limited per access token. Unlike the global and route-level limits, this limit applies to a single service or personal access token on its own. Exceeding a limit with one access token does not affect other tokens on the account. Calls that are subject to access token rate limits return these headers:  | Header name                        | Description                                                                             | | ---------------------------------- | --------------------------------------------------------------------------------------- | | `X-Ratelimit-Auth-Token-Limit`     | The maximum number of requests the access token can make per ten seconds.               | | `X-Ratelimit-Auth-Token-Remaining` | The number of requests remaining for the access token in the current rate limit window. | | `X-Ratelimit-Auth-Token-Reset`     | The time at which the current rate limit window resets in epoch milliseconds.           |  Unlike the other rate limits, access token rate limits report their own reset time in the `X-Ratelimit-Auth-Token-Reset` header instead of in `X-Ratelimit-Reset`.  ### IP-based rate limiting  We also employ IP-based rate limiting on some API routes. If you hit an IP-based rate limit, your API response will include a `Retry-After` header indicating how long to wait before re-trying the call. Clients must wait at least `Retry-After` seconds before making additional calls to our API, and should employ jitter and backoff strategies to avoid triggering rate limits again.  ## OpenAPI (Swagger) and client libraries  We have a [complete OpenAPI (Swagger) specification](https://app.launchdarkly.com/api/v2/openapi.json) for our API.  We auto-generate multiple client libraries based on our OpenAPI specification. To learn more, visit the [collection of client libraries on GitHub](https://github.com/search?q=topic%3Alaunchdarkly-api+org%3Alaunchdarkly&type=Repositories). Alternatively, you can use the specification to generate client libraries to interact with our REST API in your language of choice. Or, you can refer to our API endpoints' documentation for guidance on how to make requests with a common HTTP library in your language of choice.  Our OpenAPI specification is supported by several API-based tools such as Postman and Insomnia. In many cases, you can directly import our specification to explore our APIs.  ## Method overriding  Some firewalls and HTTP clients restrict the use of verbs other than `GET` and `POST`. In those environments, our API endpoints that use `DELETE`, `PATCH`, and `PUT` verbs are inaccessible.  To avoid this issue, our API supports the `X-HTTP-Method-Override` header, allowing clients to \"tunnel\" `DELETE`, `PATCH`, and `PUT` requests using a `POST` request.  For example, to call a `PATCH` endpoint using a `POST` request, you can include `X-HTTP-Method-Override:PATCH` as a header.  ## Beta resources  We sometimes release new API resources in **beta** status before we release them with general availability.  Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  We try to promote resources into general availability as quickly as possible. This happens after sufficient testing and when we're satisfied that we no longer need to make backwards-incompatible changes.  We mark beta resources with a \"Beta\" callout in our documentation, pictured below:  > ### This feature is in beta > > To use this feature, pass in a header including the `LD-API-Version` key with value set to `beta`. Use this header with each call. To learn more, read [Beta resources](https://launchdarkly.com/docs/api#beta-resources). > > Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  ### Using beta resources  To use a beta resource, you must include a header in the request. If you call a beta resource without this header, you receive a `403` response.  Use this header:  ``` LD-API-Version: beta ```  ## Federal and EU environments  In addition to the commercial versions, LaunchDarkly offers instances for federal agencies and those based in the European Union (EU).  ### Federal environments  The version of LaunchDarkly that is available on domains controlled by the United States government is different from the version of LaunchDarkly available to the general public. If you are an employee or contractor for a United States federal agency and use LaunchDarkly in your work, you likely use the federal instance of LaunchDarkly.  If you are working in the federal instance of LaunchDarkly, the base URI for each request is `https://app.launchdarkly.us`.  To learn more, read [LaunchDarkly in federal environments](https://launchdarkly.com/docs/home/infrastructure/federal).  ### EU environments  The version of LaunchDarkly that is available in the EU is different from the version of LaunchDarkly available to other regions. If you are based in the EU, you likely use the EU instance of LaunchDarkly. The LaunchDarkly EU instance complies with EU data residency principles, including the protection and confidentiality of EU customer information.  If you are working in the EU instance of LaunchDarkly, the base URI for each request is `https://app.eu.launchdarkly.com`.  To learn more, read [LaunchDarkly in the European Union (EU)](https://launchdarkly.com/docs/home/infrastructure/eu).  ## Versioning  We try hard to keep our REST API backwards compatible, but we occasionally have to make backwards-incompatible changes in the process of shipping new features. These breaking changes can cause unexpected behavior if you don't prepare for them accordingly.  Updates to our REST API include support for the latest features in LaunchDarkly. We also release a new version of our REST API every time we make a breaking change. We provide simultaneous support for multiple API versions so you can migrate from your current API version to a new version at your own pace.  ### Setting the API version per request  You can set the API version on a specific request by sending an `LD-API-Version` header, as shown in the example below:  ``` LD-API-Version: 20240415 ```  The header value is the version number of the API version you would like to request. The number for each version corresponds to the date the version was released in `yyyymmdd` format. In the example above the version `20240415` corresponds to April 15, 2024.  ### Setting the API version per access token  When you create an access token, you must specify a specific version of the API to use. This ensures that integrations using this token cannot be broken by version changes.  Tokens created before versioning was released have their version set to `20160426`, which is the version of the API that existed before the current versioning scheme, so that they continue working the same way they did before versioning.  If you would like to upgrade your integration to use a new API version, you can explicitly set the header described above.  > ### Best practice: Set the header for every client or integration > > We recommend that you set the API version header explicitly in any client or integration you build. > > Only rely on the access token API version during manual testing.  ### API version changelog  <table>   <tr>     <th>Version</th>     <th>Changes</th>     <th>End of life (EOL)</th>   </tr>   <tr>     <td>`20240415`</td>     <td>       <ul><li>Changed several endpoints from unpaginated to paginated. Use the `limit` and `offset` query parameters to page through the results.</li> <li>Changed the [list access tokens](https://launchdarkly.com/docs/api/access-tokens/get-tokens) endpoint: <ul><li>Response is now paginated with a default limit of `25`</li></ul></li> <li>Changed the [list account members](https://launchdarkly.com/docs/api/account-members/get-members) endpoint: <ul><li>The `accessCheck` filter is no longer available</li></ul></li> <li>Changed the [list custom roles](https://launchdarkly.com/docs/api/custom-roles/get-custom-roles) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li></ul></li> <li>Changed the [list feature flags](https://launchdarkly.com/docs/api/feature-flags/get-feature-flags) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li><li>The `environments` field is now only returned if the request is filtered by environment, using the `filterEnv` query parameter</li><li>The `followerId`, `hasDataExport`, `status`, `contextKindTargeted`, and `segmentTargeted` filters are no longer available</li><li>The `compare` query parameter is no longer available</li></ul></li> <li>Changed the [list segments](https://launchdarkly.com/docs/api/segments/get-segments) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li></ul></li> <li>Changed the [list teams](https://launchdarkly.com/docs/api/teams/get-teams) endpoint: <ul><li>The `expand` parameter no longer supports including `projects` or `roles`</li><li>In paginated results, the maximum page size is now 100</li></ul></li> <li>Changed the [get workflows](https://launchdarkly.com/docs/api/workflows/get-workflows) endpoint: <ul><li>Response is now paginated with a default limit of `20`</li><li>The `_conflicts` field in the response is no longer available</li></ul></li> </ul>     </td>     <td>Current</td>   </tr>   <tr>     <td>`20220603`</td>     <td>       <ul><li>Changed the [list projects](https://launchdarkly.com/docs/api/projects/get-projects) return value:<ul><li>Response is now paginated with a default limit of `20`.</li><li>Added support for filter and sort.</li><li>The project `environments` field is now expandable. This field is omitted by default.</li></ul></li><li>Changed the [get project](https://launchdarkly.com/docs/api/projects/get-project) return value:<ul><li>The `environments` field is now expandable. This field is omitted by default.</li></ul></li></ul>     </td>     <td>2025-04-15</td>   </tr>   <tr>     <td>`20210729`</td>     <td>       <ul><li>Changed the [create approval request](https://launchdarkly.com/docs/api/approvals/post-approval-request) return value. It now returns HTTP Status Code `201` instead of `200`.</li><li> Changed the [get user](https://launchdarkly.com/docs/api/users/get-user) return value. It now returns a user record, not a user. </li><li>Added additional optional fields to environment, segments, flags, members, and segments, including the ability to create big segments. </li><li> Added default values for flag variations when new environments are created. </li><li>Added filtering and pagination for getting flags and members, including `limit`, `number`, `filter`, and `sort` query parameters. </li><li>Added endpoints for expiring user targets for flags and segments, scheduled changes, access tokens, Relay Proxy configuration, integrations and subscriptions, and approvals. </li></ul>     </td>     <td>2023-06-03</td>   </tr>   <tr>     <td>`20191212`</td>     <td>       <ul><li>[List feature flags](https://launchdarkly.com/docs/api/feature-flags/get-feature-flags) now defaults to sending summaries of feature flag configurations, equivalent to setting the query parameter `summary=true`. Summaries omit flag targeting rules and individual user targets from the payload. </li><li> Added endpoints for flags, flag status, projects, environments, audit logs, members, users, custom roles, segments, usage, streams, events, and data export. </li></ul>     </td>     <td>2022-07-29</td>   </tr>   <tr>     <td>`20160426`</td>     <td>       <ul><li>Initial versioning of API. Tokens created before versioning have their version set to this.</li></ul>     </td>     <td>2020-12-12</td>   </tr> </table>  To learn more about how EOL is determined, read LaunchDarkly's [End of Life (EOL) Policy](https://launchdarkly.com/policies/end-of-life-policy/). 
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
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.launchdarkly.api.model.CleanupBehavior;
import com.launchdarkly.api.model.EnvironmentReleaseAdvancementMode;
import com.launchdarkly.api.model.EnvironmentReleaseLink;
import com.launchdarkly.api.model.EnvironmentReleasePhase;
import com.launchdarkly.api.model.ReleaseMetric;
import com.launchdarkly.api.model.ReleaseStatus;
import com.launchdarkly.api.model.ReleaseStatusReason;
import com.launchdarkly.api.model.ReleaseType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.launchdarkly.api.JSON;

/**
 * A flag release in one environment, made up of one or more ordered phases. The top-level status is derived from the phases: it reflects the active phase, reports &#x60;waiting&#x60; between successfully completed phases, and reports the terminal outcome of the release. 
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-10-23T17:32:13.796579Z[Etc/UTC]", comments = "Generator version: 7.18.0")
public class EnvironmentRelease {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  @javax.annotation.Nonnull
  private UUID id;

  public static final String SERIALIZED_NAME_STATUS = "status";
  @SerializedName(SERIALIZED_NAME_STATUS)
  @javax.annotation.Nonnull
  private ReleaseStatus status;

  public static final String SERIALIZED_NAME_STATUS_REASON = "statusReason";
  @SerializedName(SERIALIZED_NAME_STATUS_REASON)
  @javax.annotation.Nullable
  private ReleaseStatusReason statusReason;

  public static final String SERIALIZED_NAME_PROJECT_KEY = "projectKey";
  @SerializedName(SERIALIZED_NAME_PROJECT_KEY)
  @javax.annotation.Nonnull
  private String projectKey;

  public static final String SERIALIZED_NAME_ENVIRONMENT_KEY = "environmentKey";
  @SerializedName(SERIALIZED_NAME_ENVIRONMENT_KEY)
  @javax.annotation.Nonnull
  private String environmentKey;

  public static final String SERIALIZED_NAME_FLAG_KEY = "flagKey";
  @SerializedName(SERIALIZED_NAME_FLAG_KEY)
  @javax.annotation.Nonnull
  private String flagKey;

  public static final String SERIALIZED_NAME_ORIGINAL_VARIATION_ID = "originalVariationId";
  @SerializedName(SERIALIZED_NAME_ORIGINAL_VARIATION_ID)
  @javax.annotation.Nonnull
  private UUID originalVariationId;

  public static final String SERIALIZED_NAME_ORIGINAL_VARIATION_VALUE = "originalVariationValue";
  @SerializedName(SERIALIZED_NAME_ORIGINAL_VARIATION_VALUE)
  @javax.annotation.Nullable
  private Object originalVariationValue = null;

  public static final String SERIALIZED_NAME_TARGET_VARIATION_ID = "targetVariationId";
  @SerializedName(SERIALIZED_NAME_TARGET_VARIATION_ID)
  @javax.annotation.Nonnull
  private UUID targetVariationId;

  public static final String SERIALIZED_NAME_TARGET_VARIATION_VALUE = "targetVariationValue";
  @SerializedName(SERIALIZED_NAME_TARGET_VARIATION_VALUE)
  @javax.annotation.Nullable
  private Object targetVariationValue = null;

  public static final String SERIALIZED_NAME_RANDOMIZATION_UNIT = "randomizationUnit";
  @SerializedName(SERIALIZED_NAME_RANDOMIZATION_UNIT)
  @javax.annotation.Nonnull
  private String randomizationUnit;

  public static final String SERIALIZED_NAME_CLEANUP_BEHAVIOR = "cleanupBehavior";
  @SerializedName(SERIALIZED_NAME_CLEANUP_BEHAVIOR)
  @javax.annotation.Nonnull
  private CleanupBehavior cleanupBehavior;

  public static final String SERIALIZED_NAME_KIND = "kind";
  @SerializedName(SERIALIZED_NAME_KIND)
  @javax.annotation.Nonnull
  private ReleaseType kind;

  public static final String SERIALIZED_NAME_METRICS = "metrics";
  @SerializedName(SERIALIZED_NAME_METRICS)
  @javax.annotation.Nonnull
  private List<ReleaseMetric> metrics = new ArrayList<>();

  public static final String SERIALIZED_NAME_PHASES = "phases";
  @SerializedName(SERIALIZED_NAME_PHASES)
  @javax.annotation.Nonnull
  private List<EnvironmentReleasePhase> phases = new ArrayList<>();

  public static final String SERIALIZED_NAME_CURRENT_PHASE_INDEX = "currentPhaseIndex";
  @SerializedName(SERIALIZED_NAME_CURRENT_PHASE_INDEX)
  @javax.annotation.Nullable
  private Integer currentPhaseIndex;

  public static final String SERIALIZED_NAME_ADVANCEMENT_MODE = "advancementMode";
  @SerializedName(SERIALIZED_NAME_ADVANCEMENT_MODE)
  @javax.annotation.Nonnull
  private EnvironmentReleaseAdvancementMode advancementMode;

  public static final String SERIALIZED_NAME_CREATED_AT = "createdAt";
  @SerializedName(SERIALIZED_NAME_CREATED_AT)
  @javax.annotation.Nonnull
  private Long createdAt;

  public static final String SERIALIZED_NAME_STARTED_AT = "startedAt";
  @SerializedName(SERIALIZED_NAME_STARTED_AT)
  @javax.annotation.Nullable
  private Long startedAt;

  public static final String SERIALIZED_NAME_ENDED_AT = "endedAt";
  @SerializedName(SERIALIZED_NAME_ENDED_AT)
  @javax.annotation.Nullable
  private Long endedAt;

  public static final String SERIALIZED_NAME_LINKS = "_links";
  @SerializedName(SERIALIZED_NAME_LINKS)
  @javax.annotation.Nullable
  private Map<String, EnvironmentReleaseLink> links = new HashMap<>();

  public EnvironmentRelease() {
  }

  public EnvironmentRelease(
     Long startedAt, 
     Long endedAt
  ) {
    this();
    this.startedAt = startedAt;
    this.endedAt = endedAt;
  }

  public EnvironmentRelease id(@javax.annotation.Nonnull UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  @javax.annotation.Nonnull
  public UUID getId() {
    return id;
  }

  public void setId(@javax.annotation.Nonnull UUID id) {
    this.id = id;
  }


  public EnvironmentRelease status(@javax.annotation.Nonnull ReleaseStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   */
  @javax.annotation.Nonnull
  public ReleaseStatus getStatus() {
    return status;
  }

  public void setStatus(@javax.annotation.Nonnull ReleaseStatus status) {
    this.status = status;
  }


  public EnvironmentRelease statusReason(@javax.annotation.Nullable ReleaseStatusReason statusReason) {
    this.statusReason = statusReason;
    return this;
  }

  /**
   * Get statusReason
   * @return statusReason
   */
  @javax.annotation.Nullable
  public ReleaseStatusReason getStatusReason() {
    return statusReason;
  }

  public void setStatusReason(@javax.annotation.Nullable ReleaseStatusReason statusReason) {
    this.statusReason = statusReason;
  }


  public EnvironmentRelease projectKey(@javax.annotation.Nonnull String projectKey) {
    this.projectKey = projectKey;
    return this;
  }

  /**
   * Get projectKey
   * @return projectKey
   */
  @javax.annotation.Nonnull
  public String getProjectKey() {
    return projectKey;
  }

  public void setProjectKey(@javax.annotation.Nonnull String projectKey) {
    this.projectKey = projectKey;
  }


  public EnvironmentRelease environmentKey(@javax.annotation.Nonnull String environmentKey) {
    this.environmentKey = environmentKey;
    return this;
  }

  /**
   * Get environmentKey
   * @return environmentKey
   */
  @javax.annotation.Nonnull
  public String getEnvironmentKey() {
    return environmentKey;
  }

  public void setEnvironmentKey(@javax.annotation.Nonnull String environmentKey) {
    this.environmentKey = environmentKey;
  }


  public EnvironmentRelease flagKey(@javax.annotation.Nonnull String flagKey) {
    this.flagKey = flagKey;
    return this;
  }

  /**
   * Get flagKey
   * @return flagKey
   */
  @javax.annotation.Nonnull
  public String getFlagKey() {
    return flagKey;
  }

  public void setFlagKey(@javax.annotation.Nonnull String flagKey) {
    this.flagKey = flagKey;
  }


  public EnvironmentRelease originalVariationId(@javax.annotation.Nonnull UUID originalVariationId) {
    this.originalVariationId = originalVariationId;
    return this;
  }

  /**
   * ID of the variation the release started from.
   * @return originalVariationId
   */
  @javax.annotation.Nonnull
  public UUID getOriginalVariationId() {
    return originalVariationId;
  }

  public void setOriginalVariationId(@javax.annotation.Nonnull UUID originalVariationId) {
    this.originalVariationId = originalVariationId;
  }


  public EnvironmentRelease originalVariationValue(@javax.annotation.Nullable Object originalVariationValue) {
    this.originalVariationValue = originalVariationValue;
    return this;
  }

  /**
   * The value of the variation. This may be a string, boolean, number, JSON object, or array.
   * @return originalVariationValue
   */
  @javax.annotation.Nullable
  public Object getOriginalVariationValue() {
    return originalVariationValue;
  }

  public void setOriginalVariationValue(@javax.annotation.Nullable Object originalVariationValue) {
    this.originalVariationValue = originalVariationValue;
  }


  public EnvironmentRelease targetVariationId(@javax.annotation.Nonnull UUID targetVariationId) {
    this.targetVariationId = targetVariationId;
    return this;
  }

  /**
   * ID of the variation being released.
   * @return targetVariationId
   */
  @javax.annotation.Nonnull
  public UUID getTargetVariationId() {
    return targetVariationId;
  }

  public void setTargetVariationId(@javax.annotation.Nonnull UUID targetVariationId) {
    this.targetVariationId = targetVariationId;
  }


  public EnvironmentRelease targetVariationValue(@javax.annotation.Nullable Object targetVariationValue) {
    this.targetVariationValue = targetVariationValue;
    return this;
  }

  /**
   * The value of the variation. This may be a string, boolean, number, JSON object, or array.
   * @return targetVariationValue
   */
  @javax.annotation.Nullable
  public Object getTargetVariationValue() {
    return targetVariationValue;
  }

  public void setTargetVariationValue(@javax.annotation.Nullable Object targetVariationValue) {
    this.targetVariationValue = targetVariationValue;
  }


  public EnvironmentRelease randomizationUnit(@javax.annotation.Nonnull String randomizationUnit) {
    this.randomizationUnit = randomizationUnit;
    return this;
  }

  /**
   * Key of the context kind the release randomizes on.
   * @return randomizationUnit
   */
  @javax.annotation.Nonnull
  public String getRandomizationUnit() {
    return randomizationUnit;
  }

  public void setRandomizationUnit(@javax.annotation.Nonnull String randomizationUnit) {
    this.randomizationUnit = randomizationUnit;
  }


  public EnvironmentRelease cleanupBehavior(@javax.annotation.Nonnull CleanupBehavior cleanupBehavior) {
    this.cleanupBehavior = cleanupBehavior;
    return this;
  }

  /**
   * Get cleanupBehavior
   * @return cleanupBehavior
   */
  @javax.annotation.Nonnull
  public CleanupBehavior getCleanupBehavior() {
    return cleanupBehavior;
  }

  public void setCleanupBehavior(@javax.annotation.Nonnull CleanupBehavior cleanupBehavior) {
    this.cleanupBehavior = cleanupBehavior;
  }


  public EnvironmentRelease kind(@javax.annotation.Nonnull ReleaseType kind) {
    this.kind = kind;
    return this;
  }

  /**
   * Get kind
   * @return kind
   */
  @javax.annotation.Nonnull
  public ReleaseType getKind() {
    return kind;
  }

  public void setKind(@javax.annotation.Nonnull ReleaseType kind) {
    this.kind = kind;
  }


  public EnvironmentRelease metrics(@javax.annotation.Nonnull List<ReleaseMetric> metrics) {
    this.metrics = metrics;
    return this;
  }

  public EnvironmentRelease addMetricsItem(ReleaseMetric metricsItem) {
    if (this.metrics == null) {
      this.metrics = new ArrayList<>();
    }
    this.metrics.add(metricsItem);
    return this;
  }

  /**
   * Get metrics
   * @return metrics
   */
  @javax.annotation.Nonnull
  public List<ReleaseMetric> getMetrics() {
    return metrics;
  }

  public void setMetrics(@javax.annotation.Nonnull List<ReleaseMetric> metrics) {
    this.metrics = metrics;
  }


  public EnvironmentRelease phases(@javax.annotation.Nonnull List<EnvironmentReleasePhase> phases) {
    this.phases = phases;
    return this;
  }

  public EnvironmentRelease addPhasesItem(EnvironmentReleasePhase phasesItem) {
    if (this.phases == null) {
      this.phases = new ArrayList<>();
    }
    this.phases.add(phasesItem);
    return this;
  }

  /**
   * Get phases
   * @return phases
   */
  @javax.annotation.Nonnull
  public List<EnvironmentReleasePhase> getPhases() {
    return phases;
  }

  public void setPhases(@javax.annotation.Nonnull List<EnvironmentReleasePhase> phases) {
    this.phases = phases;
  }


  public EnvironmentRelease currentPhaseIndex(@javax.annotation.Nullable Integer currentPhaseIndex) {
    this.currentPhaseIndex = currentPhaseIndex;
    return this;
  }

  /**
   * Zero-based index of the active phase. Null before the first phase, between phases, and after the release reaches a terminal status.
   * minimum: 0
   * @return currentPhaseIndex
   */
  @javax.annotation.Nullable
  public Integer getCurrentPhaseIndex() {
    return currentPhaseIndex;
  }

  public void setCurrentPhaseIndex(@javax.annotation.Nullable Integer currentPhaseIndex) {
    this.currentPhaseIndex = currentPhaseIndex;
  }


  public EnvironmentRelease advancementMode(@javax.annotation.Nonnull EnvironmentReleaseAdvancementMode advancementMode) {
    this.advancementMode = advancementMode;
    return this;
  }

  /**
   * Get advancementMode
   * @return advancementMode
   */
  @javax.annotation.Nonnull
  public EnvironmentReleaseAdvancementMode getAdvancementMode() {
    return advancementMode;
  }

  public void setAdvancementMode(@javax.annotation.Nonnull EnvironmentReleaseAdvancementMode advancementMode) {
    this.advancementMode = advancementMode;
  }


  public EnvironmentRelease createdAt(@javax.annotation.Nonnull Long createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Creation time in Unix milliseconds.
   * @return createdAt
   */
  @javax.annotation.Nonnull
  public Long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(@javax.annotation.Nonnull Long createdAt) {
    this.createdAt = createdAt;
  }


  /**
   * Start of the first phase in Unix milliseconds. Absent until a phase has started.
   * @return startedAt
   */
  @javax.annotation.Nullable
  public Long getStartedAt() {
    return startedAt;
  }



  /**
   * End of the last started phase in Unix milliseconds. Present only after the release reaches a terminal status of &#x60;completed&#x60; or &#x60;reverted&#x60;.
   * @return endedAt
   */
  @javax.annotation.Nullable
  public Long getEndedAt() {
    return endedAt;
  }



  public EnvironmentRelease links(@javax.annotation.Nullable Map<String, EnvironmentReleaseLink> links) {
    this.links = links;
    return this;
  }

  public EnvironmentRelease putLinksItem(String key, EnvironmentReleaseLink linksItem) {
    if (this.links == null) {
      this.links = new HashMap<>();
    }
    this.links.put(key, linksItem);
    return this;
  }

  /**
   * Get links
   * @return links
   */
  @javax.annotation.Nullable
  public Map<String, EnvironmentReleaseLink> getLinks() {
    return links;
  }

  public void setLinks(@javax.annotation.Nullable Map<String, EnvironmentReleaseLink> links) {
    this.links = links;
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
   *
   * @param key name of the property
   * @param value value of the property
   * @return the EnvironmentRelease instance itself
   */
  public EnvironmentRelease putAdditionalProperty(String key, Object value) {
    if (this.additionalProperties == null) {
        this.additionalProperties = new HashMap<String, Object>();
    }
    this.additionalProperties.put(key, value);
    return this;
  }

  /**
   * Return the additional (undeclared) property.
   *
   * @return a map of objects
   */
  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }

  /**
   * Return the additional (undeclared) property with the specified name.
   *
   * @param key name of the property
   * @return an object
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
    EnvironmentRelease environmentRelease = (EnvironmentRelease) o;
    return Objects.equals(this.id, environmentRelease.id) &&
        Objects.equals(this.status, environmentRelease.status) &&
        Objects.equals(this.statusReason, environmentRelease.statusReason) &&
        Objects.equals(this.projectKey, environmentRelease.projectKey) &&
        Objects.equals(this.environmentKey, environmentRelease.environmentKey) &&
        Objects.equals(this.flagKey, environmentRelease.flagKey) &&
        Objects.equals(this.originalVariationId, environmentRelease.originalVariationId) &&
        Objects.equals(this.originalVariationValue, environmentRelease.originalVariationValue) &&
        Objects.equals(this.targetVariationId, environmentRelease.targetVariationId) &&
        Objects.equals(this.targetVariationValue, environmentRelease.targetVariationValue) &&
        Objects.equals(this.randomizationUnit, environmentRelease.randomizationUnit) &&
        Objects.equals(this.cleanupBehavior, environmentRelease.cleanupBehavior) &&
        Objects.equals(this.kind, environmentRelease.kind) &&
        Objects.equals(this.metrics, environmentRelease.metrics) &&
        Objects.equals(this.phases, environmentRelease.phases) &&
        Objects.equals(this.currentPhaseIndex, environmentRelease.currentPhaseIndex) &&
        Objects.equals(this.advancementMode, environmentRelease.advancementMode) &&
        Objects.equals(this.createdAt, environmentRelease.createdAt) &&
        Objects.equals(this.startedAt, environmentRelease.startedAt) &&
        Objects.equals(this.endedAt, environmentRelease.endedAt) &&
        Objects.equals(this.links, environmentRelease.links)&&
        Objects.equals(this.additionalProperties, environmentRelease.additionalProperties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, status, statusReason, projectKey, environmentKey, flagKey, originalVariationId, originalVariationValue, targetVariationId, targetVariationValue, randomizationUnit, cleanupBehavior, kind, metrics, phases, currentPhaseIndex, advancementMode, createdAt, startedAt, endedAt, links, additionalProperties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EnvironmentRelease {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    statusReason: ").append(toIndentedString(statusReason)).append("\n");
    sb.append("    projectKey: ").append(toIndentedString(projectKey)).append("\n");
    sb.append("    environmentKey: ").append(toIndentedString(environmentKey)).append("\n");
    sb.append("    flagKey: ").append(toIndentedString(flagKey)).append("\n");
    sb.append("    originalVariationId: ").append(toIndentedString(originalVariationId)).append("\n");
    sb.append("    originalVariationValue: ").append(toIndentedString(originalVariationValue)).append("\n");
    sb.append("    targetVariationId: ").append(toIndentedString(targetVariationId)).append("\n");
    sb.append("    targetVariationValue: ").append(toIndentedString(targetVariationValue)).append("\n");
    sb.append("    randomizationUnit: ").append(toIndentedString(randomizationUnit)).append("\n");
    sb.append("    cleanupBehavior: ").append(toIndentedString(cleanupBehavior)).append("\n");
    sb.append("    kind: ").append(toIndentedString(kind)).append("\n");
    sb.append("    metrics: ").append(toIndentedString(metrics)).append("\n");
    sb.append("    phases: ").append(toIndentedString(phases)).append("\n");
    sb.append("    currentPhaseIndex: ").append(toIndentedString(currentPhaseIndex)).append("\n");
    sb.append("    advancementMode: ").append(toIndentedString(advancementMode)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    startedAt: ").append(toIndentedString(startedAt)).append("\n");
    sb.append("    endedAt: ").append(toIndentedString(endedAt)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
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
    openapiFields = new HashSet<String>(Arrays.asList("id", "status", "statusReason", "projectKey", "environmentKey", "flagKey", "originalVariationId", "originalVariationValue", "targetVariationId", "targetVariationValue", "randomizationUnit", "cleanupBehavior", "kind", "metrics", "phases", "currentPhaseIndex", "advancementMode", "createdAt", "startedAt", "endedAt", "_links"));

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>(Arrays.asList("id", "status", "projectKey", "environmentKey", "flagKey", "originalVariationId", "originalVariationValue", "targetVariationId", "targetVariationValue", "randomizationUnit", "cleanupBehavior", "kind", "metrics", "phases", "currentPhaseIndex", "advancementMode", "createdAt"));
  }

  /**
   * Validates the JSON Element and throws an exception if issues found
   *
   * @param jsonElement JSON Element
   * @throws IOException if the JSON Element is invalid with respect to EnvironmentRelease
   */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!EnvironmentRelease.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "The required field(s) %s in EnvironmentRelease is not found in the empty JSON string", EnvironmentRelease.openapiRequiredFields.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : EnvironmentRelease.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("id").toString()));
      }
      // validate the required field `status`
      ReleaseStatus.validateJsonElement(jsonObj.get("status"));
      // validate the optional field `statusReason`
      if (jsonObj.get("statusReason") != null && !jsonObj.get("statusReason").isJsonNull()) {
        ReleaseStatusReason.validateJsonElement(jsonObj.get("statusReason"));
      }
      if (!jsonObj.get("projectKey").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `projectKey` to be a primitive type in the JSON string but got `%s`", jsonObj.get("projectKey").toString()));
      }
      if (!jsonObj.get("environmentKey").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `environmentKey` to be a primitive type in the JSON string but got `%s`", jsonObj.get("environmentKey").toString()));
      }
      if (!jsonObj.get("flagKey").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `flagKey` to be a primitive type in the JSON string but got `%s`", jsonObj.get("flagKey").toString()));
      }
      if (!jsonObj.get("originalVariationId").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `originalVariationId` to be a primitive type in the JSON string but got `%s`", jsonObj.get("originalVariationId").toString()));
      }
      if (!jsonObj.get("targetVariationId").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `targetVariationId` to be a primitive type in the JSON string but got `%s`", jsonObj.get("targetVariationId").toString()));
      }
      if (!jsonObj.get("randomizationUnit").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `randomizationUnit` to be a primitive type in the JSON string but got `%s`", jsonObj.get("randomizationUnit").toString()));
      }
      // validate the required field `cleanupBehavior`
      CleanupBehavior.validateJsonElement(jsonObj.get("cleanupBehavior"));
      // validate the required field `kind`
      ReleaseType.validateJsonElement(jsonObj.get("kind"));
      // ensure the json data is an array
      if (!jsonObj.get("metrics").isJsonArray()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `metrics` to be an array in the JSON string but got `%s`", jsonObj.get("metrics").toString()));
      }

      JsonArray jsonArraymetrics = jsonObj.getAsJsonArray("metrics");
      // validate the required field `metrics` (array)
      for (int i = 0; i < jsonArraymetrics.size(); i++) {
        ReleaseMetric.validateJsonElement(jsonArraymetrics.get(i));
      };
      // ensure the json data is an array
      if (!jsonObj.get("phases").isJsonArray()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `phases` to be an array in the JSON string but got `%s`", jsonObj.get("phases").toString()));
      }

      JsonArray jsonArrayphases = jsonObj.getAsJsonArray("phases");
      // validate the required field `phases` (array)
      for (int i = 0; i < jsonArrayphases.size(); i++) {
        EnvironmentReleasePhase.validateJsonElement(jsonArrayphases.get(i));
      };
      // validate the required field `advancementMode`
      EnvironmentReleaseAdvancementMode.validateJsonElement(jsonObj.get("advancementMode"));
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!EnvironmentRelease.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'EnvironmentRelease' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<EnvironmentRelease> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(EnvironmentRelease.class));

       return (TypeAdapter<T>) new TypeAdapter<EnvironmentRelease>() {
           @Override
           public void write(JsonWriter out, EnvironmentRelease value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             obj.remove("additionalProperties");
             // serialize additional properties
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
                   JsonElement jsonElement = gson.toJsonTree(entry.getValue());
                   if (jsonElement.isJsonArray()) {
                     obj.add(entry.getKey(), jsonElement.getAsJsonArray());
                   } else {
                     obj.add(entry.getKey(), jsonElement.getAsJsonObject());
                   }
                 }
               }
             }
             elementAdapter.write(out, obj);
           }

           @Override
           public EnvironmentRelease read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             JsonObject jsonObj = jsonElement.getAsJsonObject();
             // store additional fields in the deserialized instance
             EnvironmentRelease instance = thisAdapter.fromJsonTree(jsonObj);
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
                     throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "The field `%s` has unknown primitive type. Value: %s", entry.getKey(), entry.getValue().toString()));
                 } else if (entry.getValue().isJsonArray()) {
                     instance.putAdditionalProperty(entry.getKey(), gson.fromJson(entry.getValue(), List.class));
                 } else { // JSON object
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
   * Create an instance of EnvironmentRelease given an JSON string
   *
   * @param jsonString JSON string
   * @return An instance of EnvironmentRelease
   * @throws IOException if the JSON string is invalid with respect to EnvironmentRelease
   */
  public static EnvironmentRelease fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, EnvironmentRelease.class);
  }

  /**
   * Convert an instance of EnvironmentRelease to an JSON string
   *
   * @return JSON string
   */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

