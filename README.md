This repository contains a client library for LaunchDarkly's REST API. This client was automatically
generated from our [OpenAPI specification](https://app.launchdarkly.com/api/v2/openapi.json) using a [code generation library](https://github.com/launchdarkly/ld-openapi). View our [sample code](#getting-started) for example usage.

This REST API is for custom integrations, data export, or automating your feature flag workflows. *DO NOT* use this client library to include feature flags in your web or mobile application. To integrate feature flags with your application, read the [SDK documentation](https://docs.launchdarkly.com/sdk).

This client library is only compatible with the latest version of our REST API, version `20220603`. Previous versions of this client library, prior to version 10.0.0, are only compatible with earlier versions of our REST API. When you create an access token, you can set the REST API version associated with the token. By default, API requests you send using the token will use the specified API version. To learn more, read [Versioning](https://apidocs.launchdarkly.com/#section/Overview/Versioning).
# api-client

LaunchDarkly REST API
- API version: 2.0
  - Build date: 2022-11-21T23:14:11.074962Z[Etc/UTC]

# Overview

## Authentication

All REST API resources are authenticated with either [personal or service access tokens](https://docs.launchdarkly.com/home/account-security/api-access-tokens), or session cookies. Other authentication mechanisms are not supported. You can manage personal access tokens on your [**Account settings**](https://app.launchdarkly.com/settings/tokens) page.

LaunchDarkly also has SDK keys, mobile keys, and client-side IDs that are used by our server-side SDKs, mobile SDKs, and JavaScript-based SDKs, respectively. **These keys cannot be used to access our REST API**. These keys are environment-specific, and can only perform read-only operations such as fetching feature flag settings.

| Auth mechanism                                                                                  | Allowed resources                                                                                     | Use cases                                          |
| ----------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------- | -------------------------------------------------- |
| [Personal or service access tokens](https://docs.launchdarkly.com/home/account-security/api-access-tokens) | Can be customized on a per-token basis                                                                | Building scripts, custom integrations, data export. |
| SDK keys                                                                                        | Can only access read-only resources specific to server-side SDKs. Restricted to a single environment. | Server-side SDKs                     |
| Mobile keys                                                                                     | Can only access read-only resources specific to mobile SDKs, and only for flags marked available to mobile keys. Restricted to a single environment.           | Mobile SDKs                                        |
| Client-side ID                                                                                  | Can only access read-only resources specific to JavaScript-based client-side SDKs, and only for flags marked available to client-side. Restricted to a single environment.           | Client-side JavaScript                             |

> #### Keep your access tokens and SDK keys private
>
> Access tokens should _never_ be exposed in untrusted contexts. Never put an access token in client-side JavaScript, or embed it in a mobile application. LaunchDarkly has special mobile keys that you can embed in mobile apps. If you accidentally expose an access token or SDK key, you can reset it from your [**Account settings**](https://app.launchdarkly.com/settings/tokens) page.
>
> The client-side ID is safe to embed in untrusted contexts. It's designed for use in client-side JavaScript.

### Authentication using request header

The preferred way to authenticate with the API is by adding an `Authorization` header containing your access token to your requests. The value of the `Authorization` header must be your access token.

Manage personal access tokens from the [**Account settings**](https://app.launchdarkly.com/settings/tokens) page.

### Authentication using session cookie

For testing purposes, you can make API calls directly from your web browser. If you are logged in to the LaunchDarkly application, the API will use your existing session to authenticate calls.

If you have a [role](https://docs.launchdarkly.com/home/team/built-in-roles) other than Admin, or have a [custom role](https://docs.launchdarkly.com/home/team/custom-roles) defined, you may not have permission to perform some API calls. You will receive a `401` response code in that case.

> ### Modifying the Origin header causes an error
>
> LaunchDarkly validates that the Origin header for any API request authenticated by a session cookie matches the expected Origin header. The expected Origin header is `https://app.launchdarkly.com`.
>
> If the Origin header does not match what's expected, LaunchDarkly returns an error. This error can prevent the LaunchDarkly app from working correctly.
>
> Any browser extension that intentionally changes the Origin header can cause this problem. For example, the `Allow-Control-Allow-Origin: *` Chrome extension changes the Origin header to `http://evil.com` and causes the app to fail.
>
> To prevent this error, do not modify your Origin header.
>
> LaunchDarkly does not require origin matching when authenticating with an access token, so this issue does not affect normal API usage.

## Representations

All resources expect and return JSON response bodies. Error responses also send a JSON body. To learn more about the error format of the API, read [Errors](/#section/Overview/Errors).

In practice this means that you always get a response with a `Content-Type` header set to `application/json`.

In addition, request bodies for `PATCH`, `POST`, `PUT`, and `REPORT` requests must be encoded as JSON with a `Content-Type` header set to `application/json`.

### Summary and detailed representations

When you fetch a list of resources, the response includes only the most important attributes of each resource. This is a _summary representation_ of the resource. When you fetch an individual resource, such as a single feature flag, you receive a _detailed representation_ of the resource.

The best way to find a detailed representation is to follow links. Every summary representation includes a link to its detailed representation.

### Expanding responses

Sometimes the detailed representation of a resource does not include all of the attributes of the resource by default. If this is the case, the request method will clearly document this and describe which attributes you can include in an expanded response.

To include the additional attributes, append the `expand` request parameter to your request and add a comma-separated list of the attributes to include. For example, when you append `?expand=members,roles` to the [Get team](/tag/Teams#operation/getTeam) endpoint, the expanded response includes both of these attributes.

### Links and addressability

The best way to navigate the API is by following links. These are attributes in representations that link to other resources. The API always uses the same format for links:

- Links to other resources within the API are encapsulated in a `_links` object
- If the resource has a corresponding link to HTML content on the site, it is stored in a special `_site` link

Each link has two attributes:

- An `href`, which contains the URL
- A `type`, which describes the content type

For example, a feature resource might return the following:

```json
{
  \"_links\": {
    \"parent\": {
      \"href\": \"/api/features\",
      \"type\": \"application/json\"
    },
    \"self\": {
      \"href\": \"/api/features/sort.order\",
      \"type\": \"application/json\"
    }
  },
  \"_site\": {
    \"href\": \"/features/sort.order\",
    \"type\": \"text/html\"
  }
}
```

From this, you can navigate to the parent collection of features by following the `parent` link, or navigate to the site page for the feature by following the `_site` link.

Collections are always represented as a JSON object with an `items` attribute containing an array of representations. Like all other representations, collections have `_links` defined at the top level.

Paginated collections include `first`, `last`, `next`, and `prev` links containing a URL with the respective set of elements in the collection.

## Updates

Resources that accept partial updates use the `PATCH` verb. Most resources support the [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) format. Some resources also support the [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) format, and some resources support the [semantic patch](/reference#updates-using-semantic-patch) format, which is a way to specify the modifications to perform as a set of executable instructions. Each resource supports optional [comments](/reference#updates-with-comments) that you can submit with updates. Comments appear in outgoing webhooks, the audit log, and other integrations.

When a resource supports both JSON patch and semantic patch, we document both in the request method. However, the specific request body fields and descriptions included in our documentation only match one type of patch or the other.

### Updates using JSON patch

[JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) is a way to specify the modifications to perform on a resource. JSON patch uses paths and a limited set of operations to describe how to transform the current state of the resource into a new state. JSON patch documents are always arrays, where each element contains an operation, a path to the field to update, and the new value.

For example, in this feature flag representation:

```json
{
    \"name\": \"New recommendations engine\",
    \"key\": \"engine.enable\",
    \"description\": \"This is the description\",
    ...
}
```
You can change the feature flag's description with the following patch document:

```json
[{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"This is the new description\" }]
```

You can specify multiple modifications to perform in a single request. You can also test that certain preconditions are met before applying the patch:

```json
[
  { \"op\": \"test\", \"path\": \"/version\", \"value\": 10 },
  { \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" }
]
```

The above patch request tests whether the feature flag's `version` is `10`, and if so, changes the feature flag's description.

Attributes that are not editable, such as a resource's `_links`, have names that start with an underscore.

### Updates using JSON merge patch

[JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) is another format for specifying the modifications to perform on a resource. JSON merge patch is less expressive than JSON patch. However, in many cases it is simpler to construct a merge patch document. For example, you can change a feature flag's description with the following merge patch document:

```json
{
  \"description\": \"New flag description\"
}
```

### Updates using semantic patch

The API also supports the semantic patch format. A semantic patch is a way to specify the modifications to perform on a resource as a set of executable instructions.

Semantic patch allows you to be explicit about intent using precise, custom instructions. In many cases, you can define semantic patch instructions independently of the current state of the resource. This can be useful when defining a change that may be applied at a future date.

To make a semantic patch request, you must append `domain-model=launchdarkly.semanticpatch` to your `Content-Type` header.

Here's how:

```
Content-Type: application/json; domain-model=launchdarkly.semanticpatch
```

If you call a semantic patch resource without this header, you will receive a `400` response because your semantic patch will be interpreted as a JSON patch.

The body of a semantic patch request takes the following properties:

* `comment` (string): (Optional) A description of the update.
* `environmentKey` (string): (Required for some resources only) The environment key.
* `instructions` (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a `kind` property that indicates the instruction. If the instruction requires parameters, you must include those parameters as additional fields in the object. The documentation for each resource that supports semantic patch includes the available instructions and any additional parameters.

For example:

```json
{
  \"comment\": \"optional comment\",
  \"instructions\": [ {\"kind\": \"turnFlagOn\"} ]
}
```

If any instruction in the patch encounters an error, the endpoint returns an error and will not change the resource. In general, each instruction silently does nothing if the resource is already in the state you request.

### Updates with comments

You can submit optional comments with `PATCH` changes.

To submit a comment along with a JSON patch document, use the following format:

```json
{
  \"comment\": \"This is a comment string\",
  \"patch\": [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" }]
}
```

To submit a comment along with a JSON merge patch document, use the following format:

```json
{
  \"comment\": \"This is a comment string\",
  \"merge\": { \"description\": \"New flag description\" }
}
```

To submit a comment along with a semantic patch, use the following format:

```json
{
  \"comment\": \"This is a comment string\",
  \"instructions\": [ {\"kind\": \"turnFlagOn\"} ]
}
```

## Errors

The API always returns errors in a common format. Here's an example:

```json
{
  \"code\": \"invalid_request\",
  \"message\": \"A feature with that key already exists\",
  \"id\": \"30ce6058-87da-11e4-b116-123b93f75cba\"
}
```

The `code` indicates the general class of error. The `message` is a human-readable explanation of what went wrong. The `id` is a unique identifier. Use it when you're working with LaunchDarkly Support to debug a problem with a specific API call.

### HTTP status error response codes

| Code | Definition        | Description                                                                                       | Possible Solution                                                |
| ---- | ----------------- | ------------------------------------------------------------------------------------------- | ---------------------------------------------------------------- |
| 400  | Invalid request       | The request cannot be understood.                                    | Ensure JSON syntax in request body is correct.                   |
| 401  | Invalid access token      | Requestor is unauthorized or does not have permission for this API call.                                                | Ensure your API access token is valid and has the appropriate permissions.                                     |
| 403  | Forbidden         | Requestor does not have access to this resource.                                                | Ensure that the account member or access token has proper permissions set. |
| 404  | Invalid resource identifier | The requested resource is not valid. | Ensure that the resource is correctly identified by id or key. |
| 405  | Method not allowed | The request method is not allowed on this resource. | Ensure that the HTTP verb is correct. |
| 409  | Conflict          | The API request can not be completed because it conflicts with a concurrent API request. | Retry your request.                                              |
| 422  | Unprocessable entity | The API request can not be completed because the update description can not be understood. | Ensure that the request body is correct for the type of patch you are using, either JSON patch or semantic patch.
| 429  | Too many requests | Read [Rate limiting](/#section/Overview/Rate-limiting).                                               | Wait and try again later.                                        |

## CORS

The LaunchDarkly API supports Cross Origin Resource Sharing (CORS) for AJAX requests from any origin. If an `Origin` header is given in a request, it will be echoed as an explicitly allowed origin. Otherwise the request returns a wildcard, `Access-Control-Allow-Origin: *`. For more information on CORS, read the [CORS W3C Recommendation](http://www.w3.org/TR/cors). Example CORS headers might look like:

```http
Access-Control-Allow-Headers: Accept, Content-Type, Content-Length, Accept-Encoding, Authorization
Access-Control-Allow-Methods: OPTIONS, GET, DELETE, PATCH
Access-Control-Allow-Origin: *
Access-Control-Max-Age: 300
```

You can make authenticated CORS calls just as you would make same-origin calls, using either [token or session-based authentication](/#section/Overview/Authentication). If you are using session authentication, you should set the `withCredentials` property for your `xhr` request to `true`. You should never expose your access tokens to untrusted users.

## Rate limiting

We use several rate limiting strategies to ensure the availability of our APIs. Rate-limited calls to our APIs return a `429` status code. Calls to our APIs include headers indicating the current rate limit status. The specific headers returned depend on the API route being called. The limits differ based on the route, authentication mechanism, and other factors. Routes that are not rate limited may not contain any of the headers described below.

> ### Rate limiting and SDKs
>
> LaunchDarkly SDKs are never rate limited and do not use the API endpoints defined here. LaunchDarkly uses a different set of approaches, including streaming/server-sent events and a global CDN, to ensure availability to the routes used by LaunchDarkly SDKs.

### Global rate limits

Authenticated requests are subject to a global limit. This is the maximum number of calls that your account can make to the API per ten seconds. All personal access tokens on the account share this limit, so exceeding the limit with one access token will impact other tokens. Calls that are subject to global rate limits return the headers below:

| Header name                    | Description                                                                      |
| ------------------------------ | -------------------------------------------------------------------------------- |
| `X-Ratelimit-Global-Remaining` | The maximum number of requests the account is permitted to make per ten seconds. |
| `X-Ratelimit-Reset`            | The time at which the current rate limit window resets in epoch milliseconds.    |

We do not publicly document the specific number of calls that can be made globally. This limit may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limit.

### Route-level rate limits

Some authenticated routes have custom rate limits. These also reset every ten seconds. Any access tokens hitting the same route share this limit, so exceeding the limit with one access token may impact other tokens. Calls that are subject to route-level rate limits return the headers below:

| Header name                   | Description                                                                                           |
| ----------------------------- | ----------------------------------------------------------------------------------------------------- |
| `X-Ratelimit-Route-Remaining` | The maximum number of requests to the current route the account is permitted to make per ten seconds. |
| `X-Ratelimit-Reset`           | The time at which the current rate limit window resets in epoch milliseconds.                         |

A _route_ represents a specific URL pattern and verb. For example, the [Delete environment](/tag/Environments#operation/deleteEnvironment) endpoint is considered a single route, and each call to delete an environment counts against your route-level rate limit for that route.

We do not publicly document the specific number of calls that an account can make to each endpoint per ten seconds. These limits may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limits.

### IP-based rate limiting

We also employ IP-based rate limiting on some API routes. If you hit an IP-based rate limit, your API response will include a `Retry-After` header indicating how long to wait before re-trying the call. Clients must wait at least `Retry-After` seconds before making additional calls to our API, and should employ jitter and backoff strategies to avoid triggering rate limits again.

## OpenAPI (Swagger) and client libraries

We have a [complete OpenAPI (Swagger) specification](https://app.launchdarkly.com/api/v2/openapi.json) for our API.

We auto-generate multiple client libraries based on our OpenAPI specification. To learn more, visit the [collection of client libraries on GitHub](https://github.com/search?q=topic%3Alaunchdarkly-api+org%3Alaunchdarkly&type=Repositories). You can also use this specification to generate client libraries to interact with our REST API in your language of choice.

Our OpenAPI specification is supported by several API-based tools such as Postman and Insomnia. In many cases, you can directly import our specification to explore our APIs.

## Method overriding

Some firewalls and HTTP clients restrict the use of verbs other than `GET` and `POST`. In those environments, our API endpoints that use `DELETE`, `PATCH`, and `PUT` verbs are inaccessible.

To avoid this issue, our API supports the `X-HTTP-Method-Override` header, allowing clients to \"tunnel\" `DELETE`, `PATCH`, and `PUT` requests using a `POST` request.

For example, to call a `PATCH` endpoint using a `POST` request, you can include `X-HTTP-Method-Override:PATCH` as a header.

## Beta resources

We sometimes release new API resources in **beta** status before we release them with general availability.

Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.

We try to promote resources into general availability as quickly as possible. This happens after sufficient testing and when we're satisfied that we no longer need to make backwards-incompatible changes.

We mark beta resources with a \"Beta\" callout in our documentation, pictured below:

> ### This feature is in beta
>
> To use this feature, pass in a header including the `LD-API-Version` key with value set to `beta`. Use this header with each call. To learn more, read [Beta resources](/#section/Overview/Beta-resources).
>
> Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.

### Using beta resources

To use a beta resource, you must include a header in the request. If you call a beta resource without this header, you receive a `403` response.

Use this header:

```
LD-API-Version: beta
```

## Versioning

We try hard to keep our REST API backwards compatible, but we occasionally have to make backwards-incompatible changes in the process of shipping new features. These breaking changes can cause unexpected behavior if you don't prepare for them accordingly.

Updates to our REST API include support for the latest features in LaunchDarkly. We also release a new version of our REST API every time we make a breaking change. We provide simultaneous support for multiple API versions so you can migrate from your current API version to a new version at your own pace.

### Setting the API version per request

You can set the API version on a specific request by sending an `LD-API-Version` header, as shown in the example below:

```
LD-API-Version: 20220603
```

The header value is the version number of the API version you would like to request. The number for each version corresponds to the date the version was released in `yyyymmdd` format. In the example above the version `20220603` corresponds to June 03, 2022.

### Setting the API version per access token

When you create an access token, you must specify a specific version of the API to use. This ensures that integrations using this token cannot be broken by version changes.

Tokens created before versioning was released have their version set to `20160426`, which is the version of the API that existed before the current versioning scheme, so that they continue working the same way they did before versioning.

If you would like to upgrade your integration to use a new API version, you can explicitly set the header described above.

> ### Best practice: Set the header for every client or integration
>
> We recommend that you set the API version header explicitly in any client or integration you build.
>
> Only rely on the access token API version during manual testing.

### API version changelog

|<div style=\"width:75px\">Version</div> | Changes | End of life (EOL)
|---|---|---|
| `20220603` | <ul><li>Changed the [list projects](/tag/Projects#operation/getProjects) return value:<ul><li>Response is now paginated with a default limit of `20`.</li><li>Added support for filter and sort.</li><li>The project `environments` field is now expandable. This field is omitted by default.</li></ul></li><li>Changed the [get project](/tag/Projects#operation/getProject) return value:<ul><li>The `environments` field is now expandable. This field is omitted by default.</li></ul></li></ul> | Current |
| `20210729` | <ul><li>Changed the [create approval request](/tag/Approvals#operation/postApprovalRequest) return value. It now returns HTTP Status Code `201` instead of `200`.</li><li> Changed the [get users](/tag/Users#operation/getUser) return value. It now returns a user record, not a user. </li><li>Added additional optional fields to environment, segments, flags, members, and segments, including the ability to create Big Segments. </li><li> Added default values for flag variations when new environments are created. </li><li>Added filtering and pagination for getting flags and members, including `limit`, `number`, `filter`, and `sort` query parameters. </li><li>Added endpoints for expiring user targets for flags and segments, scheduled changes, access tokens, Relay Proxy configuration, integrations and subscriptions, and approvals. </li></ul> | 2023-06-03 |
| `20191212` | <ul><li>[List feature flags](/tag/Feature-flags#operation/getFeatureFlags) now defaults to sending summaries of feature flag configurations, equivalent to setting the query parameter `summary=true`. Summaries omit flag targeting rules and individual user targets from the payload. </li><li> Added endpoints for flags, flag status, projects, environments, users, audit logs, members, users, custom roles, segments, usage, streams, events, and data export. </li></ul> | 2022-07-29 |
| `20160426` | <ul><li>Initial versioning of API. Tokens created before versioning have their version set to this.</li></ul> | 2020-12-12 |


  For more information, please visit [https://support.launchdarkly.com](https://support.launchdarkly.com)

*Automatically generated by the [OpenAPI Generator](https://openapi-generator.tech)*


## Requirements

Building the API client library requires:
1. Java 1.8+
2. Maven (3.8.3+)/Gradle (7.2+)

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn clean install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn clean deploy
```

Refer to the [OSSRH Guide](http://central.sonatype.org/pages/ossrh-guide.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>com.launchdarkly</groupId>
  <artifactId>api-client</artifactId>
  <version>11.0.0</version>
  <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
  repositories {
    mavenCentral()     // Needed if the 'api-client' jar has been published to maven central.
    mavenLocal()       // Needed if the 'api-client' jar has been published to the local maven repo.
  }

  dependencies {
     implementation "com.launchdarkly:api-client:11.0.0"
  }
```

### Others

At first generate the JAR by executing:

```shell
mvn clean package
```

Then manually install the following JARs:

* `target/api-client-11.0.0.jar`
* `target/lib/*.jar`

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccessTokensApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccessTokensApi apiInstance = new AccessTokensApi(defaultClient);
    String id = "id_example"; // String | The ID of the access token to update
    try {
      apiInstance.deleteToken(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccessTokensApi#deleteToken");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}

```

## Documentation for API Endpoints

All URIs are relative to *https://app.launchdarkly.com*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*AccessTokensApi* | [**deleteToken**](docs/AccessTokensApi.md#deleteToken) | **DELETE** /api/v2/tokens/{id} | Delete access token
*AccessTokensApi* | [**getToken**](docs/AccessTokensApi.md#getToken) | **GET** /api/v2/tokens/{id} | Get access token
*AccessTokensApi* | [**getTokens**](docs/AccessTokensApi.md#getTokens) | **GET** /api/v2/tokens | List access tokens
*AccessTokensApi* | [**patchToken**](docs/AccessTokensApi.md#patchToken) | **PATCH** /api/v2/tokens/{id} | Patch access token
*AccessTokensApi* | [**postToken**](docs/AccessTokensApi.md#postToken) | **POST** /api/v2/tokens | Create access token
*AccessTokensApi* | [**resetToken**](docs/AccessTokensApi.md#resetToken) | **POST** /api/v2/tokens/{id}/reset | Reset access token
*AccountMembersApi* | [**deleteMember**](docs/AccountMembersApi.md#deleteMember) | **DELETE** /api/v2/members/{id} | Delete account member
*AccountMembersApi* | [**getMember**](docs/AccountMembersApi.md#getMember) | **GET** /api/v2/members/{id} | Get account member
*AccountMembersApi* | [**getMembers**](docs/AccountMembersApi.md#getMembers) | **GET** /api/v2/members | List account members
*AccountMembersApi* | [**patchMember**](docs/AccountMembersApi.md#patchMember) | **PATCH** /api/v2/members/{id} | Modify an account member
*AccountMembersApi* | [**postMemberTeams**](docs/AccountMembersApi.md#postMemberTeams) | **POST** /api/v2/members/{id}/teams | Add a member to teams
*AccountMembersApi* | [**postMembers**](docs/AccountMembersApi.md#postMembers) | **POST** /api/v2/members | Invite new members
*AccountMembersBetaApi* | [**patchMembers**](docs/AccountMembersBetaApi.md#patchMembers) | **PATCH** /api/v2/members | Modify account members
*AccountUsageBetaApi* | [**getEvaluationsUsage**](docs/AccountUsageBetaApi.md#getEvaluationsUsage) | **GET** /api/v2/usage/evaluations/{projectKey}/{environmentKey}/{featureFlagKey} | Get evaluations usage
*AccountUsageBetaApi* | [**getEventsUsage**](docs/AccountUsageBetaApi.md#getEventsUsage) | **GET** /api/v2/usage/events/{type} | Get events usage
*AccountUsageBetaApi* | [**getMauSdksByType**](docs/AccountUsageBetaApi.md#getMauSdksByType) | **GET** /api/v2/usage/mau/sdks | Get MAU SDKs by type
*AccountUsageBetaApi* | [**getMauUsage**](docs/AccountUsageBetaApi.md#getMauUsage) | **GET** /api/v2/usage/mau | Get MAU usage
*AccountUsageBetaApi* | [**getMauUsageByCategory**](docs/AccountUsageBetaApi.md#getMauUsageByCategory) | **GET** /api/v2/usage/mau/bycategory | Get MAU usage by category
*AccountUsageBetaApi* | [**getStreamUsage**](docs/AccountUsageBetaApi.md#getStreamUsage) | **GET** /api/v2/usage/streams/{source} | Get stream usage
*AccountUsageBetaApi* | [**getStreamUsageBySdkVersion**](docs/AccountUsageBetaApi.md#getStreamUsageBySdkVersion) | **GET** /api/v2/usage/streams/{source}/bysdkversion | Get stream usage by SDK version
*AccountUsageBetaApi* | [**getStreamUsageSdkversion**](docs/AccountUsageBetaApi.md#getStreamUsageSdkversion) | **GET** /api/v2/usage/streams/{source}/sdkversions | Get stream usage SDK versions
*ApprovalsApi* | [**deleteApprovalRequest**](docs/ApprovalsApi.md#deleteApprovalRequest) | **DELETE** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests/{id} | Delete approval request
*ApprovalsApi* | [**getApprovalForFlag**](docs/ApprovalsApi.md#getApprovalForFlag) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests/{id} | Get approval request for a flag
*ApprovalsApi* | [**getApprovalsForFlag**](docs/ApprovalsApi.md#getApprovalsForFlag) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests | List approval requests for a flag
*ApprovalsApi* | [**postApprovalRequest**](docs/ApprovalsApi.md#postApprovalRequest) | **POST** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests | Create approval request
*ApprovalsApi* | [**postApprovalRequestApplyRequest**](docs/ApprovalsApi.md#postApprovalRequestApplyRequest) | **POST** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests/{id}/apply | Apply approval request
*ApprovalsApi* | [**postApprovalRequestReview**](docs/ApprovalsApi.md#postApprovalRequestReview) | **POST** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests/{id}/reviews | Review approval request
*ApprovalsApi* | [**postFlagCopyConfigApprovalRequest**](docs/ApprovalsApi.md#postFlagCopyConfigApprovalRequest) | **POST** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests-flag-copy | Create approval request to copy flag configurations across environments
*AuditLogApi* | [**getAuditLogEntries**](docs/AuditLogApi.md#getAuditLogEntries) | **GET** /api/v2/auditlog | List audit log feature flag entries
*AuditLogApi* | [**getAuditLogEntry**](docs/AuditLogApi.md#getAuditLogEntry) | **GET** /api/v2/auditlog/{id} | Get audit log entry
*CodeReferencesApi* | [**deleteBranches**](docs/CodeReferencesApi.md#deleteBranches) | **POST** /api/v2/code-refs/repositories/{repo}/branch-delete-tasks | Delete branches
*CodeReferencesApi* | [**deleteRepository**](docs/CodeReferencesApi.md#deleteRepository) | **DELETE** /api/v2/code-refs/repositories/{repo} | Delete repository
*CodeReferencesApi* | [**getBranch**](docs/CodeReferencesApi.md#getBranch) | **GET** /api/v2/code-refs/repositories/{repo}/branches/{branch} | Get branch
*CodeReferencesApi* | [**getBranches**](docs/CodeReferencesApi.md#getBranches) | **GET** /api/v2/code-refs/repositories/{repo}/branches | List branches
*CodeReferencesApi* | [**getExtinctions**](docs/CodeReferencesApi.md#getExtinctions) | **GET** /api/v2/code-refs/extinctions | List extinctions
*CodeReferencesApi* | [**getRepositories**](docs/CodeReferencesApi.md#getRepositories) | **GET** /api/v2/code-refs/repositories | List repositories
*CodeReferencesApi* | [**getRepository**](docs/CodeReferencesApi.md#getRepository) | **GET** /api/v2/code-refs/repositories/{repo} | Get repository
*CodeReferencesApi* | [**getRootStatistic**](docs/CodeReferencesApi.md#getRootStatistic) | **GET** /api/v2/code-refs/statistics | Get links to code reference repositories for each project
*CodeReferencesApi* | [**getStatistics**](docs/CodeReferencesApi.md#getStatistics) | **GET** /api/v2/code-refs/statistics/{projectKey} | Get code references statistics for flags
*CodeReferencesApi* | [**patchRepository**](docs/CodeReferencesApi.md#patchRepository) | **PATCH** /api/v2/code-refs/repositories/{repo} | Update repository
*CodeReferencesApi* | [**postExtinction**](docs/CodeReferencesApi.md#postExtinction) | **POST** /api/v2/code-refs/repositories/{repo}/branches/{branch}/extinction-events | Create extinction
*CodeReferencesApi* | [**postRepository**](docs/CodeReferencesApi.md#postRepository) | **POST** /api/v2/code-refs/repositories | Create repository
*CodeReferencesApi* | [**putBranch**](docs/CodeReferencesApi.md#putBranch) | **PUT** /api/v2/code-refs/repositories/{repo}/branches/{branch} | Upsert branch
*CustomRolesApi* | [**deleteCustomRole**](docs/CustomRolesApi.md#deleteCustomRole) | **DELETE** /api/v2/roles/{customRoleKey} | Delete custom role
*CustomRolesApi* | [**getCustomRole**](docs/CustomRolesApi.md#getCustomRole) | **GET** /api/v2/roles/{customRoleKey} | Get custom role
*CustomRolesApi* | [**getCustomRoles**](docs/CustomRolesApi.md#getCustomRoles) | **GET** /api/v2/roles | List custom roles
*CustomRolesApi* | [**patchCustomRole**](docs/CustomRolesApi.md#patchCustomRole) | **PATCH** /api/v2/roles/{customRoleKey} | Update custom role
*CustomRolesApi* | [**postCustomRole**](docs/CustomRolesApi.md#postCustomRole) | **POST** /api/v2/roles | Create custom role
*DataExportDestinationsApi* | [**deleteDestination**](docs/DataExportDestinationsApi.md#deleteDestination) | **DELETE** /api/v2/destinations/{projectKey}/{environmentKey}/{id} | Delete Data Export destination
*DataExportDestinationsApi* | [**getDestination**](docs/DataExportDestinationsApi.md#getDestination) | **GET** /api/v2/destinations/{projectKey}/{environmentKey}/{id} | Get destination
*DataExportDestinationsApi* | [**getDestinations**](docs/DataExportDestinationsApi.md#getDestinations) | **GET** /api/v2/destinations | List destinations
*DataExportDestinationsApi* | [**patchDestination**](docs/DataExportDestinationsApi.md#patchDestination) | **PATCH** /api/v2/destinations/{projectKey}/{environmentKey}/{id} | Update Data Export destination
*DataExportDestinationsApi* | [**postDestination**](docs/DataExportDestinationsApi.md#postDestination) | **POST** /api/v2/destinations/{projectKey}/{environmentKey} | Create Data Export destination
*EnvironmentsApi* | [**deleteEnvironment**](docs/EnvironmentsApi.md#deleteEnvironment) | **DELETE** /api/v2/projects/{projectKey}/environments/{environmentKey} | Delete environment
*EnvironmentsApi* | [**getEnvironment**](docs/EnvironmentsApi.md#getEnvironment) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey} | Get environment
*EnvironmentsApi* | [**getEnvironmentsByProject**](docs/EnvironmentsApi.md#getEnvironmentsByProject) | **GET** /api/v2/projects/{projectKey}/environments | List environments
*EnvironmentsApi* | [**patchEnvironment**](docs/EnvironmentsApi.md#patchEnvironment) | **PATCH** /api/v2/projects/{projectKey}/environments/{environmentKey} | Update environment
*EnvironmentsApi* | [**postEnvironment**](docs/EnvironmentsApi.md#postEnvironment) | **POST** /api/v2/projects/{projectKey}/environments | Create environment
*EnvironmentsApi* | [**resetEnvironmentMobileKey**](docs/EnvironmentsApi.md#resetEnvironmentMobileKey) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/mobileKey | Reset environment mobile SDK key
*EnvironmentsApi* | [**resetEnvironmentSDKKey**](docs/EnvironmentsApi.md#resetEnvironmentSDKKey) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/apiKey | Reset environment SDK key
*ExperimentsBetaApi* | [**createExperiment**](docs/ExperimentsBetaApi.md#createExperiment) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments | Create experiment
*ExperimentsBetaApi* | [**createIteration**](docs/ExperimentsBetaApi.md#createIteration) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey}/iterations | Create iteration
*ExperimentsBetaApi* | [**getExperiment**](docs/ExperimentsBetaApi.md#getExperiment) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey} | Get experiment
*ExperimentsBetaApi* | [**getExperimentResults**](docs/ExperimentsBetaApi.md#getExperimentResults) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey}/metrics/{metricKey}/results | Get experiment results
*ExperimentsBetaApi* | [**getExperiments**](docs/ExperimentsBetaApi.md#getExperiments) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments | Get experiments
*ExperimentsBetaApi* | [**getLegacyExperimentResults**](docs/ExperimentsBetaApi.md#getLegacyExperimentResults) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey}/experiments/{environmentKey}/{metricKey} | Get legacy experiment results (deprecated)
*ExperimentsBetaApi* | [**patchExperiment**](docs/ExperimentsBetaApi.md#patchExperiment) | **PATCH** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey} | Patch experiment
*ExperimentsBetaApi* | [**resetExperiment**](docs/ExperimentsBetaApi.md#resetExperiment) | **DELETE** /api/v2/flags/{projectKey}/{featureFlagKey}/experiments/{environmentKey}/{metricKey}/results | Reset experiment results
*FeatureFlagsApi* | [**copyFeatureFlag**](docs/FeatureFlagsApi.md#copyFeatureFlag) | **POST** /api/v2/flags/{projectKey}/{featureFlagKey}/copy | Copy feature flag
*FeatureFlagsApi* | [**deleteFeatureFlag**](docs/FeatureFlagsApi.md#deleteFeatureFlag) | **DELETE** /api/v2/flags/{projectKey}/{featureFlagKey} | Delete feature flag
*FeatureFlagsApi* | [**getExpiringUserTargets**](docs/FeatureFlagsApi.md#getExpiringUserTargets) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey}/expiring-user-targets/{environmentKey} | Get expiring user targets for feature flag
*FeatureFlagsApi* | [**getFeatureFlag**](docs/FeatureFlagsApi.md#getFeatureFlag) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey} | Get feature flag
*FeatureFlagsApi* | [**getFeatureFlagStatus**](docs/FeatureFlagsApi.md#getFeatureFlagStatus) | **GET** /api/v2/flag-statuses/{projectKey}/{environmentKey}/{featureFlagKey} | Get feature flag status
*FeatureFlagsApi* | [**getFeatureFlagStatusAcrossEnvironments**](docs/FeatureFlagsApi.md#getFeatureFlagStatusAcrossEnvironments) | **GET** /api/v2/flag-status/{projectKey}/{featureFlagKey} | Get flag status across environments
*FeatureFlagsApi* | [**getFeatureFlagStatuses**](docs/FeatureFlagsApi.md#getFeatureFlagStatuses) | **GET** /api/v2/flag-statuses/{projectKey}/{environmentKey} | List feature flag statuses
*FeatureFlagsApi* | [**getFeatureFlags**](docs/FeatureFlagsApi.md#getFeatureFlags) | **GET** /api/v2/flags/{projectKey} | List feature flags
*FeatureFlagsApi* | [**patchExpiringUserTargets**](docs/FeatureFlagsApi.md#patchExpiringUserTargets) | **PATCH** /api/v2/flags/{projectKey}/{featureFlagKey}/expiring-user-targets/{environmentKey} | Update expiring user targets on feature flag
*FeatureFlagsApi* | [**patchFeatureFlag**](docs/FeatureFlagsApi.md#patchFeatureFlag) | **PATCH** /api/v2/flags/{projectKey}/{featureFlagKey} | Update feature flag
*FeatureFlagsApi* | [**postFeatureFlag**](docs/FeatureFlagsApi.md#postFeatureFlag) | **POST** /api/v2/flags/{projectKey} | Create a feature flag
*FeatureFlagsBetaApi* | [**getDependentFlags**](docs/FeatureFlagsBetaApi.md#getDependentFlags) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey}/dependent-flags | List dependent feature flags
*FeatureFlagsBetaApi* | [**getDependentFlagsByEnv**](docs/FeatureFlagsBetaApi.md#getDependentFlagsByEnv) | **GET** /api/v2/flags/{projectKey}/{environmentKey}/{featureFlagKey}/dependent-flags | List dependent feature flags by environment
*FlagLinksBetaApi* | [**createFlagLink**](docs/FlagLinksBetaApi.md#createFlagLink) | **POST** /api/v2/flag-links/projects/{projectKey}/flags/{featureFlagKey} | Create flag link
*FlagLinksBetaApi* | [**deleteFlagLink**](docs/FlagLinksBetaApi.md#deleteFlagLink) | **DELETE** /api/v2/flag-links/projects/{projectKey}/flags/{featureFlagKey}/{id} | Delete flag link
*FlagLinksBetaApi* | [**getFlagLinks**](docs/FlagLinksBetaApi.md#getFlagLinks) | **GET** /api/v2/flag-links/projects/{projectKey}/flags/{featureFlagKey} | List flag links
*FlagLinksBetaApi* | [**updateFlagLink**](docs/FlagLinksBetaApi.md#updateFlagLink) | **PATCH** /api/v2/flag-links/projects/{projectKey}/flags/{featureFlagKey}/{id} | Update flag link
*FlagTriggersApi* | [**createTriggerWorkflow**](docs/FlagTriggersApi.md#createTriggerWorkflow) | **POST** /api/v2/flags/{projectKey}/{featureFlagKey}/triggers/{environmentKey} | Create flag trigger
*FlagTriggersApi* | [**deleteTriggerWorkflow**](docs/FlagTriggersApi.md#deleteTriggerWorkflow) | **DELETE** /api/v2/flags/{projectKey}/{featureFlagKey}/triggers/{environmentKey}/{id} | Delete flag trigger
*FlagTriggersApi* | [**getTriggerWorkflowById**](docs/FlagTriggersApi.md#getTriggerWorkflowById) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey}/triggers/{environmentKey}/{id} | Get flag trigger by ID
*FlagTriggersApi* | [**getTriggerWorkflows**](docs/FlagTriggersApi.md#getTriggerWorkflows) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey}/triggers/{environmentKey} | List flag triggers
*FlagTriggersApi* | [**patchTriggerWorkflow**](docs/FlagTriggersApi.md#patchTriggerWorkflow) | **PATCH** /api/v2/flags/{projectKey}/{featureFlagKey}/triggers/{environmentKey}/{id} | Update flag trigger
*FollowFlagsApi* | [**deleteFlagFollowers**](docs/FollowFlagsApi.md#deleteFlagFollowers) | **DELETE** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/followers/{memberId} | Remove a member as a follower of a flag in a project and environment
*FollowFlagsApi* | [**getFlagFollowers**](docs/FollowFlagsApi.md#getFlagFollowers) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/followers | Get followers of a flag in a project and environment
*FollowFlagsApi* | [**getFollowersByProjEnv**](docs/FollowFlagsApi.md#getFollowersByProjEnv) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/followers | Get followers of all flags in a given project and environment
*FollowFlagsApi* | [**putFlagFollowers**](docs/FollowFlagsApi.md#putFlagFollowers) | **PUT** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/followers/{memberId} | Add a member as a follower of a flag in a project and environment
*IntegrationAuditLogSubscriptionsApi* | [**createSubscription**](docs/IntegrationAuditLogSubscriptionsApi.md#createSubscription) | **POST** /api/v2/integrations/{integrationKey} | Create audit log subscription
*IntegrationAuditLogSubscriptionsApi* | [**deleteSubscription**](docs/IntegrationAuditLogSubscriptionsApi.md#deleteSubscription) | **DELETE** /api/v2/integrations/{integrationKey}/{id} | Delete audit log subscription
*IntegrationAuditLogSubscriptionsApi* | [**getSubscriptionByID**](docs/IntegrationAuditLogSubscriptionsApi.md#getSubscriptionByID) | **GET** /api/v2/integrations/{integrationKey}/{id} | Get audit log subscription by ID
*IntegrationAuditLogSubscriptionsApi* | [**getSubscriptions**](docs/IntegrationAuditLogSubscriptionsApi.md#getSubscriptions) | **GET** /api/v2/integrations/{integrationKey} | Get audit log subscriptions by integration
*IntegrationAuditLogSubscriptionsApi* | [**updateSubscription**](docs/IntegrationAuditLogSubscriptionsApi.md#updateSubscription) | **PATCH** /api/v2/integrations/{integrationKey}/{id} | Update audit log subscription
*IntegrationDeliveryConfigurationsBetaApi* | [**createIntegrationDeliveryConfiguration**](docs/IntegrationDeliveryConfigurationsBetaApi.md#createIntegrationDeliveryConfiguration) | **POST** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey}/{integrationKey} | Create delivery configuration
*IntegrationDeliveryConfigurationsBetaApi* | [**deleteIntegrationDeliveryConfiguration**](docs/IntegrationDeliveryConfigurationsBetaApi.md#deleteIntegrationDeliveryConfiguration) | **DELETE** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey}/{integrationKey}/{id} | Delete delivery configuration
*IntegrationDeliveryConfigurationsBetaApi* | [**getIntegrationDeliveryConfigurationByEnvironment**](docs/IntegrationDeliveryConfigurationsBetaApi.md#getIntegrationDeliveryConfigurationByEnvironment) | **GET** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey} | Get delivery configurations by environment
*IntegrationDeliveryConfigurationsBetaApi* | [**getIntegrationDeliveryConfigurationById**](docs/IntegrationDeliveryConfigurationsBetaApi.md#getIntegrationDeliveryConfigurationById) | **GET** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey}/{integrationKey}/{id} | Get delivery configuration by ID
*IntegrationDeliveryConfigurationsBetaApi* | [**getIntegrationDeliveryConfigurations**](docs/IntegrationDeliveryConfigurationsBetaApi.md#getIntegrationDeliveryConfigurations) | **GET** /api/v2/integration-capabilities/featureStore | List all delivery configurations
*IntegrationDeliveryConfigurationsBetaApi* | [**patchIntegrationDeliveryConfiguration**](docs/IntegrationDeliveryConfigurationsBetaApi.md#patchIntegrationDeliveryConfiguration) | **PATCH** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey}/{integrationKey}/{id} | Update delivery configuration
*IntegrationDeliveryConfigurationsBetaApi* | [**validateIntegrationDeliveryConfiguration**](docs/IntegrationDeliveryConfigurationsBetaApi.md#validateIntegrationDeliveryConfiguration) | **POST** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey}/{integrationKey}/{id}/validate | Validate delivery configuration
*MetricsApi* | [**deleteMetric**](docs/MetricsApi.md#deleteMetric) | **DELETE** /api/v2/metrics/{projectKey}/{metricKey} | Delete metric
*MetricsApi* | [**getMetric**](docs/MetricsApi.md#getMetric) | **GET** /api/v2/metrics/{projectKey}/{metricKey} | Get metric
*MetricsApi* | [**getMetrics**](docs/MetricsApi.md#getMetrics) | **GET** /api/v2/metrics/{projectKey} | List metrics
*MetricsApi* | [**patchMetric**](docs/MetricsApi.md#patchMetric) | **PATCH** /api/v2/metrics/{projectKey}/{metricKey} | Update metric
*MetricsApi* | [**postMetric**](docs/MetricsApi.md#postMetric) | **POST** /api/v2/metrics/{projectKey} | Create metric
*OAuth2ClientsBetaApi* | [**createOAuth2Client**](docs/OAuth2ClientsBetaApi.md#createOAuth2Client) | **POST** /api/v2/oauth/clients | Create a LaunchDarkly OAuth 2.0 client
*OAuth2ClientsBetaApi* | [**deleteOAuthClient**](docs/OAuth2ClientsBetaApi.md#deleteOAuthClient) | **DELETE** /api/v2/oauth/clients/{clientId} | Delete OAuth 2.0 client
*OAuth2ClientsBetaApi* | [**getOAuthClientById**](docs/OAuth2ClientsBetaApi.md#getOAuthClientById) | **GET** /api/v2/oauth/clients/{clientId} | Get client by ID
*OAuth2ClientsBetaApi* | [**getOAuthClients**](docs/OAuth2ClientsBetaApi.md#getOAuthClients) | **GET** /api/v2/oauth/clients | Get clients
*OAuth2ClientsBetaApi* | [**patchOAuthClient**](docs/OAuth2ClientsBetaApi.md#patchOAuthClient) | **PATCH** /api/v2/oauth/clients/{clientId} | Patch client by ID
*OtherApi* | [**getIps**](docs/OtherApi.md#getIps) | **GET** /api/v2/public-ip-list | Gets the public IP list
*OtherApi* | [**getOpenapiSpec**](docs/OtherApi.md#getOpenapiSpec) | **GET** /api/v2/openapi.json | Gets the OpenAPI spec in json
*OtherApi* | [**getRoot**](docs/OtherApi.md#getRoot) | **GET** /api/v2 | Root resource
*OtherApi* | [**getVersions**](docs/OtherApi.md#getVersions) | **GET** /api/v2/versions | Get version information
*ProjectsApi* | [**deleteProject**](docs/ProjectsApi.md#deleteProject) | **DELETE** /api/v2/projects/{projectKey} | Delete project
*ProjectsApi* | [**getFlagDefaultsByProject**](docs/ProjectsApi.md#getFlagDefaultsByProject) | **GET** /api/v2/projects/{projectKey}/flag-defaults | Get flag defaults for project
*ProjectsApi* | [**getProject**](docs/ProjectsApi.md#getProject) | **GET** /api/v2/projects/{projectKey} | Get project
*ProjectsApi* | [**getProjects**](docs/ProjectsApi.md#getProjects) | **GET** /api/v2/projects | List projects
*ProjectsApi* | [**patchFlagDefaultsByProject**](docs/ProjectsApi.md#patchFlagDefaultsByProject) | **PATCH** /api/v2/projects/{projectKey}/flag-defaults | Update flag default for project
*ProjectsApi* | [**patchProject**](docs/ProjectsApi.md#patchProject) | **PATCH** /api/v2/projects/{projectKey} | Update project
*ProjectsApi* | [**postProject**](docs/ProjectsApi.md#postProject) | **POST** /api/v2/projects | Create project
*ProjectsApi* | [**putFlagDefaultsByProject**](docs/ProjectsApi.md#putFlagDefaultsByProject) | **PUT** /api/v2/projects/{projectKey}/flag-defaults | Create or update flag defaults for project
*RelayProxyConfigurationsApi* | [**deleteRelayAutoConfig**](docs/RelayProxyConfigurationsApi.md#deleteRelayAutoConfig) | **DELETE** /api/v2/account/relay-auto-configs/{id} | Delete Relay Proxy config by ID
*RelayProxyConfigurationsApi* | [**getRelayProxyConfig**](docs/RelayProxyConfigurationsApi.md#getRelayProxyConfig) | **GET** /api/v2/account/relay-auto-configs/{id} | Get Relay Proxy config
*RelayProxyConfigurationsApi* | [**getRelayProxyConfigs**](docs/RelayProxyConfigurationsApi.md#getRelayProxyConfigs) | **GET** /api/v2/account/relay-auto-configs | List Relay Proxy configs
*RelayProxyConfigurationsApi* | [**patchRelayAutoConfig**](docs/RelayProxyConfigurationsApi.md#patchRelayAutoConfig) | **PATCH** /api/v2/account/relay-auto-configs/{id} | Update a Relay Proxy config
*RelayProxyConfigurationsApi* | [**postRelayAutoConfig**](docs/RelayProxyConfigurationsApi.md#postRelayAutoConfig) | **POST** /api/v2/account/relay-auto-configs | Create a new Relay Proxy config
*RelayProxyConfigurationsApi* | [**resetRelayAutoConfig**](docs/RelayProxyConfigurationsApi.md#resetRelayAutoConfig) | **POST** /api/v2/account/relay-auto-configs/{id}/reset | Reset Relay Proxy configuration key
*ScheduledChangesApi* | [**deleteFlagConfigScheduledChanges**](docs/ScheduledChangesApi.md#deleteFlagConfigScheduledChanges) | **DELETE** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes/{id} | Delete scheduled changes workflow
*ScheduledChangesApi* | [**getFeatureFlagScheduledChange**](docs/ScheduledChangesApi.md#getFeatureFlagScheduledChange) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes/{id} | Get a scheduled change
*ScheduledChangesApi* | [**getFlagConfigScheduledChanges**](docs/ScheduledChangesApi.md#getFlagConfigScheduledChanges) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes | List scheduled changes
*ScheduledChangesApi* | [**patchFlagConfigScheduledChange**](docs/ScheduledChangesApi.md#patchFlagConfigScheduledChange) | **PATCH** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes/{id} | Update scheduled changes workflow
*ScheduledChangesApi* | [**postFlagConfigScheduledChanges**](docs/ScheduledChangesApi.md#postFlagConfigScheduledChanges) | **POST** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes | Create scheduled changes workflow
*SegmentsApi* | [**deleteSegment**](docs/SegmentsApi.md#deleteSegment) | **DELETE** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey} | Delete segment
*SegmentsApi* | [**getExpiringUserTargetsForSegment**](docs/SegmentsApi.md#getExpiringUserTargetsForSegment) | **GET** /api/v2/segments/{projectKey}/{segmentKey}/expiring-user-targets/{environmentKey} | Get expiring user targets for segment
*SegmentsApi* | [**getSegment**](docs/SegmentsApi.md#getSegment) | **GET** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey} | Get segment
*SegmentsApi* | [**getSegmentMembershipForUser**](docs/SegmentsApi.md#getSegmentMembershipForUser) | **GET** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/users/{userKey} | Get Big Segment membership for user
*SegmentsApi* | [**getSegments**](docs/SegmentsApi.md#getSegments) | **GET** /api/v2/segments/{projectKey}/{environmentKey} | List segments
*SegmentsApi* | [**patchExpiringUserTargetsForSegment**](docs/SegmentsApi.md#patchExpiringUserTargetsForSegment) | **PATCH** /api/v2/segments/{projectKey}/{segmentKey}/expiring-user-targets/{environmentKey} | Update expiring user targets for segment
*SegmentsApi* | [**patchSegment**](docs/SegmentsApi.md#patchSegment) | **PATCH** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey} | Patch segment
*SegmentsApi* | [**postSegment**](docs/SegmentsApi.md#postSegment) | **POST** /api/v2/segments/{projectKey}/{environmentKey} | Create segment
*SegmentsApi* | [**updateBigSegmentTargets**](docs/SegmentsApi.md#updateBigSegmentTargets) | **POST** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/users | Update targets on a Big Segment
*SegmentsBetaApi* | [**createBigSegmentExport**](docs/SegmentsBetaApi.md#createBigSegmentExport) | **POST** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/exports | Create Big Segment export
*SegmentsBetaApi* | [**createBigSegmentImport**](docs/SegmentsBetaApi.md#createBigSegmentImport) | **POST** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/imports | Create Big Segment import
*SegmentsBetaApi* | [**getBigSegmentExport**](docs/SegmentsBetaApi.md#getBigSegmentExport) | **GET** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/exports/{exportID} | Get Big Segment export
*SegmentsBetaApi* | [**getBigSegmentImport**](docs/SegmentsBetaApi.md#getBigSegmentImport) | **GET** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/imports/{importID} | Get Big Segment import
*TagsApi* | [**getTags**](docs/TagsApi.md#getTags) | **GET** /api/v2/tags | List tags
*TeamsApi* | [**deleteTeam**](docs/TeamsApi.md#deleteTeam) | **DELETE** /api/v2/teams/{teamKey} | Delete team
*TeamsApi* | [**getTeam**](docs/TeamsApi.md#getTeam) | **GET** /api/v2/teams/{teamKey} | Get team
*TeamsApi* | [**getTeamMaintainers**](docs/TeamsApi.md#getTeamMaintainers) | **GET** /api/v2/teams/{teamKey}/maintainers | Get team maintainers
*TeamsApi* | [**getTeamRoles**](docs/TeamsApi.md#getTeamRoles) | **GET** /api/v2/teams/{teamKey}/roles | Get team custom roles
*TeamsApi* | [**getTeams**](docs/TeamsApi.md#getTeams) | **GET** /api/v2/teams | List teams
*TeamsApi* | [**patchTeam**](docs/TeamsApi.md#patchTeam) | **PATCH** /api/v2/teams/{teamKey} | Update team
*TeamsApi* | [**postTeam**](docs/TeamsApi.md#postTeam) | **POST** /api/v2/teams | Create team
*TeamsApi* | [**postTeamMembers**](docs/TeamsApi.md#postTeamMembers) | **POST** /api/v2/teams/{teamKey}/members | Add multiple members to team
*TeamsBetaApi* | [**patchTeams**](docs/TeamsBetaApi.md#patchTeams) | **PATCH** /api/v2/teams | Update teams
*UserSettingsApi* | [**getExpiringFlagsForUser**](docs/UserSettingsApi.md#getExpiringFlagsForUser) | **GET** /api/v2/users/{projectKey}/{userKey}/expiring-user-targets/{environmentKey} | Get expiring dates on flags for user
*UserSettingsApi* | [**getUserFlagSetting**](docs/UserSettingsApi.md#getUserFlagSetting) | **GET** /api/v2/users/{projectKey}/{environmentKey}/{userKey}/flags/{featureFlagKey} | Get flag setting for user
*UserSettingsApi* | [**getUserFlagSettings**](docs/UserSettingsApi.md#getUserFlagSettings) | **GET** /api/v2/users/{projectKey}/{environmentKey}/{userKey}/flags | List flag settings for user
*UserSettingsApi* | [**patchExpiringFlagsForUser**](docs/UserSettingsApi.md#patchExpiringFlagsForUser) | **PATCH** /api/v2/users/{projectKey}/{userKey}/expiring-user-targets/{environmentKey} | Update expiring user target for flags
*UserSettingsApi* | [**putFlagSetting**](docs/UserSettingsApi.md#putFlagSetting) | **PUT** /api/v2/users/{projectKey}/{environmentKey}/{userKey}/flags/{featureFlagKey} | Update flag settings for user
*UsersApi* | [**deleteUser**](docs/UsersApi.md#deleteUser) | **DELETE** /api/v2/users/{projectKey}/{environmentKey}/{userKey} | Delete user
*UsersApi* | [**getSearchUsers**](docs/UsersApi.md#getSearchUsers) | **GET** /api/v2/user-search/{projectKey}/{environmentKey} | Find users
*UsersApi* | [**getUser**](docs/UsersApi.md#getUser) | **GET** /api/v2/users/{projectKey}/{environmentKey}/{userKey} | Get user
*UsersApi* | [**getUsers**](docs/UsersApi.md#getUsers) | **GET** /api/v2/users/{projectKey}/{environmentKey} | List users
*UsersBetaApi* | [**getUserAttributeNames**](docs/UsersBetaApi.md#getUserAttributeNames) | **GET** /api/v2/user-attributes/{projectKey}/{environmentKey} | Get user attribute names
*WebhooksApi* | [**deleteWebhook**](docs/WebhooksApi.md#deleteWebhook) | **DELETE** /api/v2/webhooks/{id} | Delete webhook
*WebhooksApi* | [**getAllWebhooks**](docs/WebhooksApi.md#getAllWebhooks) | **GET** /api/v2/webhooks | List webhooks
*WebhooksApi* | [**getWebhook**](docs/WebhooksApi.md#getWebhook) | **GET** /api/v2/webhooks/{id} | Get webhook
*WebhooksApi* | [**patchWebhook**](docs/WebhooksApi.md#patchWebhook) | **PATCH** /api/v2/webhooks/{id} | Update webhook
*WebhooksApi* | [**postWebhook**](docs/WebhooksApi.md#postWebhook) | **POST** /api/v2/webhooks | Creates a webhook
*WorkflowTemplatesBetaApi* | [**createWorkflowTemplate**](docs/WorkflowTemplatesBetaApi.md#createWorkflowTemplate) | **POST** /api/v2/templates | Create workflow template
*WorkflowTemplatesBetaApi* | [**deleteWorkflowTemplate**](docs/WorkflowTemplatesBetaApi.md#deleteWorkflowTemplate) | **DELETE** /api/v2/templates/{templateKey} | Delete workflow template
*WorkflowTemplatesBetaApi* | [**getWorkflowTemplates**](docs/WorkflowTemplatesBetaApi.md#getWorkflowTemplates) | **GET** /api/v2/templates | Get workflow templates
*WorkflowsBetaApi* | [**deleteWorkflow**](docs/WorkflowsBetaApi.md#deleteWorkflow) | **DELETE** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/workflows/{workflowId} | Delete workflow
*WorkflowsBetaApi* | [**getCustomWorkflow**](docs/WorkflowsBetaApi.md#getCustomWorkflow) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/workflows/{workflowId} | Get custom workflow
*WorkflowsBetaApi* | [**getWorkflows**](docs/WorkflowsBetaApi.md#getWorkflows) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/workflows | Get workflows
*WorkflowsBetaApi* | [**postWorkflow**](docs/WorkflowsBetaApi.md#postWorkflow) | **POST** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/workflows | Create workflow


## Documentation for Models

 - [Access](docs/Access.md)
 - [AccessAllowedReason](docs/AccessAllowedReason.md)
 - [AccessAllowedRep](docs/AccessAllowedRep.md)
 - [AccessDenied](docs/AccessDenied.md)
 - [AccessDeniedReason](docs/AccessDeniedReason.md)
 - [AccessTokenPost](docs/AccessTokenPost.md)
 - [ActionInput](docs/ActionInput.md)
 - [ActionOutput](docs/ActionOutput.md)
 - [ApprovalConditionInput](docs/ApprovalConditionInput.md)
 - [ApprovalConditionOutput](docs/ApprovalConditionOutput.md)
 - [ApprovalSettings](docs/ApprovalSettings.md)
 - [AuditLogEntryListingRep](docs/AuditLogEntryListingRep.md)
 - [AuditLogEntryListingRepCollection](docs/AuditLogEntryListingRepCollection.md)
 - [AuditLogEntryRep](docs/AuditLogEntryRep.md)
 - [AuthorizedAppDataRep](docs/AuthorizedAppDataRep.md)
 - [BigSegmentTarget](docs/BigSegmentTarget.md)
 - [BooleanDefaults](docs/BooleanDefaults.md)
 - [BooleanFlagDefaults](docs/BooleanFlagDefaults.md)
 - [BranchCollectionRep](docs/BranchCollectionRep.md)
 - [BranchRep](docs/BranchRep.md)
 - [BulkEditMembersRep](docs/BulkEditMembersRep.md)
 - [BulkEditTeamsRep](docs/BulkEditTeamsRep.md)
 - [Clause](docs/Clause.md)
 - [Client](docs/Client.md)
 - [ClientCollection](docs/ClientCollection.md)
 - [ClientSideAvailability](docs/ClientSideAvailability.md)
 - [ClientSideAvailabilityPost](docs/ClientSideAvailabilityPost.md)
 - [ConditionBaseOutput](docs/ConditionBaseOutput.md)
 - [ConditionInput](docs/ConditionInput.md)
 - [ConditionOutput](docs/ConditionOutput.md)
 - [ConfidenceIntervalRep](docs/ConfidenceIntervalRep.md)
 - [Conflict](docs/Conflict.md)
 - [ConflictOutput](docs/ConflictOutput.md)
 - [CopiedFromEnv](docs/CopiedFromEnv.md)
 - [CreateCopyFlagConfigApprovalRequestRequest](docs/CreateCopyFlagConfigApprovalRequestRequest.md)
 - [CreateFlagConfigApprovalRequestRequest](docs/CreateFlagConfigApprovalRequestRequest.md)
 - [CreateWorkflowTemplateInput](docs/CreateWorkflowTemplateInput.md)
 - [CredibleIntervalRep](docs/CredibleIntervalRep.md)
 - [CustomProperty](docs/CustomProperty.md)
 - [CustomRole](docs/CustomRole.md)
 - [CustomRolePost](docs/CustomRolePost.md)
 - [CustomRolePostData](docs/CustomRolePostData.md)
 - [CustomRoles](docs/CustomRoles.md)
 - [CustomWorkflowInput](docs/CustomWorkflowInput.md)
 - [CustomWorkflowMeta](docs/CustomWorkflowMeta.md)
 - [CustomWorkflowOutput](docs/CustomWorkflowOutput.md)
 - [CustomWorkflowStageMeta](docs/CustomWorkflowStageMeta.md)
 - [CustomWorkflowsListingOutput](docs/CustomWorkflowsListingOutput.md)
 - [Database](docs/Database.md)
 - [DefaultClientSideAvailability](docs/DefaultClientSideAvailability.md)
 - [DefaultClientSideAvailabilityPost](docs/DefaultClientSideAvailabilityPost.md)
 - [Defaults](docs/Defaults.md)
 - [DependentExperimentRep](docs/DependentExperimentRep.md)
 - [DependentFlag](docs/DependentFlag.md)
 - [DependentFlagEnvironment](docs/DependentFlagEnvironment.md)
 - [DependentFlagsByEnvironment](docs/DependentFlagsByEnvironment.md)
 - [Destination](docs/Destination.md)
 - [DestinationPost](docs/DestinationPost.md)
 - [Destinations](docs/Destinations.md)
 - [Environment](docs/Environment.md)
 - [EnvironmentPost](docs/EnvironmentPost.md)
 - [Environments](docs/Environments.md)
 - [EvaluationReason](docs/EvaluationReason.md)
 - [ExecutionOutput](docs/ExecutionOutput.md)
 - [Experiment](docs/Experiment.md)
 - [ExperimentAllocationRep](docs/ExperimentAllocationRep.md)
 - [ExperimentBayesianResultsRep](docs/ExperimentBayesianResultsRep.md)
 - [ExperimentCollectionRep](docs/ExperimentCollectionRep.md)
 - [ExperimentEnabledPeriodRep](docs/ExperimentEnabledPeriodRep.md)
 - [ExperimentEnvironmentSettingRep](docs/ExperimentEnvironmentSettingRep.md)
 - [ExperimentExpandableProperties](docs/ExperimentExpandableProperties.md)
 - [ExperimentInfoRep](docs/ExperimentInfoRep.md)
 - [ExperimentMetadataRep](docs/ExperimentMetadataRep.md)
 - [ExperimentPatchInput](docs/ExperimentPatchInput.md)
 - [ExperimentPost](docs/ExperimentPost.md)
 - [ExperimentResults](docs/ExperimentResults.md)
 - [ExperimentStatsRep](docs/ExperimentStatsRep.md)
 - [ExperimentTimeSeriesSlice](docs/ExperimentTimeSeriesSlice.md)
 - [ExperimentTimeSeriesVariationSlice](docs/ExperimentTimeSeriesVariationSlice.md)
 - [ExperimentTotalsRep](docs/ExperimentTotalsRep.md)
 - [ExpiringTargetError](docs/ExpiringTargetError.md)
 - [ExpiringUserTargetGetResponse](docs/ExpiringUserTargetGetResponse.md)
 - [ExpiringUserTargetItem](docs/ExpiringUserTargetItem.md)
 - [ExpiringUserTargetPatchResponse](docs/ExpiringUserTargetPatchResponse.md)
 - [Export](docs/Export.md)
 - [Extinction](docs/Extinction.md)
 - [ExtinctionCollectionRep](docs/ExtinctionCollectionRep.md)
 - [FeatureFlag](docs/FeatureFlag.md)
 - [FeatureFlagBody](docs/FeatureFlagBody.md)
 - [FeatureFlagConfig](docs/FeatureFlagConfig.md)
 - [FeatureFlagScheduledChange](docs/FeatureFlagScheduledChange.md)
 - [FeatureFlagScheduledChanges](docs/FeatureFlagScheduledChanges.md)
 - [FeatureFlagStatus](docs/FeatureFlagStatus.md)
 - [FeatureFlagStatusAcrossEnvironments](docs/FeatureFlagStatusAcrossEnvironments.md)
 - [FeatureFlagStatuses](docs/FeatureFlagStatuses.md)
 - [FeatureFlags](docs/FeatureFlags.md)
 - [FileRep](docs/FileRep.md)
 - [FlagConfigApprovalRequestResponse](docs/FlagConfigApprovalRequestResponse.md)
 - [FlagConfigApprovalRequestsResponse](docs/FlagConfigApprovalRequestsResponse.md)
 - [FlagCopyConfigEnvironment](docs/FlagCopyConfigEnvironment.md)
 - [FlagCopyConfigPost](docs/FlagCopyConfigPost.md)
 - [FlagDefaults](docs/FlagDefaults.md)
 - [FlagDefaultsApiBaseRep](docs/FlagDefaultsApiBaseRep.md)
 - [FlagDefaultsRep](docs/FlagDefaultsRep.md)
 - [FlagFollowersByProjEnvGetRep](docs/FlagFollowersByProjEnvGetRep.md)
 - [FlagFollowersGetRep](docs/FlagFollowersGetRep.md)
 - [FlagGlobalAttributesRep](docs/FlagGlobalAttributesRep.md)
 - [FlagInput](docs/FlagInput.md)
 - [FlagLinkCollectionRep](docs/FlagLinkCollectionRep.md)
 - [FlagLinkMember](docs/FlagLinkMember.md)
 - [FlagLinkPost](docs/FlagLinkPost.md)
 - [FlagLinkRep](docs/FlagLinkRep.md)
 - [FlagListingRep](docs/FlagListingRep.md)
 - [FlagRep](docs/FlagRep.md)
 - [FlagScheduledChangesInput](docs/FlagScheduledChangesInput.md)
 - [FlagStatusRep](docs/FlagStatusRep.md)
 - [FlagSummary](docs/FlagSummary.md)
 - [FlagTriggerInput](docs/FlagTriggerInput.md)
 - [FollowFlagMember](docs/FollowFlagMember.md)
 - [FollowersPerFlag](docs/FollowersPerFlag.md)
 - [ForbiddenErrorRep](docs/ForbiddenErrorRep.md)
 - [HunkRep](docs/HunkRep.md)
 - [InitiatorRep](docs/InitiatorRep.md)
 - [InstructionUserRequest](docs/InstructionUserRequest.md)
 - [Integration](docs/Integration.md)
 - [IntegrationDeliveryConfiguration](docs/IntegrationDeliveryConfiguration.md)
 - [IntegrationDeliveryConfigurationCollection](docs/IntegrationDeliveryConfigurationCollection.md)
 - [IntegrationDeliveryConfigurationCollectionLinks](docs/IntegrationDeliveryConfigurationCollectionLinks.md)
 - [IntegrationDeliveryConfigurationLinks](docs/IntegrationDeliveryConfigurationLinks.md)
 - [IntegrationDeliveryConfigurationPost](docs/IntegrationDeliveryConfigurationPost.md)
 - [IntegrationDeliveryConfigurationResponse](docs/IntegrationDeliveryConfigurationResponse.md)
 - [IntegrationMetadata](docs/IntegrationMetadata.md)
 - [IntegrationStatus](docs/IntegrationStatus.md)
 - [IntegrationStatusRep](docs/IntegrationStatusRep.md)
 - [IntegrationSubscriptionStatusRep](docs/IntegrationSubscriptionStatusRep.md)
 - [Integrations](docs/Integrations.md)
 - [InvalidRequestErrorRep](docs/InvalidRequestErrorRep.md)
 - [IpList](docs/IpList.md)
 - [IterationExpandableProperties](docs/IterationExpandableProperties.md)
 - [IterationInput](docs/IterationInput.md)
 - [IterationRep](docs/IterationRep.md)
 - [LastSeenMetadata](docs/LastSeenMetadata.md)
 - [LegacyExperimentRep](docs/LegacyExperimentRep.md)
 - [Link](docs/Link.md)
 - [MaintainerTeam](docs/MaintainerTeam.md)
 - [Member](docs/Member.md)
 - [MemberDataRep](docs/MemberDataRep.md)
 - [MemberImportItem](docs/MemberImportItem.md)
 - [MemberPermissionGrantSummaryRep](docs/MemberPermissionGrantSummaryRep.md)
 - [MemberSummary](docs/MemberSummary.md)
 - [MemberTeamSummaryRep](docs/MemberTeamSummaryRep.md)
 - [MemberTeamsPostInput](docs/MemberTeamsPostInput.md)
 - [Members](docs/Members.md)
 - [MembersPatchInput](docs/MembersPatchInput.md)
 - [MethodNotAllowedErrorRep](docs/MethodNotAllowedErrorRep.md)
 - [MetricCollectionRep](docs/MetricCollectionRep.md)
 - [MetricInput](docs/MetricInput.md)
 - [MetricListingRep](docs/MetricListingRep.md)
 - [MetricListingRepExpandableProperties](docs/MetricListingRepExpandableProperties.md)
 - [MetricPost](docs/MetricPost.md)
 - [MetricRep](docs/MetricRep.md)
 - [MetricRepExpandableProperties](docs/MetricRepExpandableProperties.md)
 - [MetricSeen](docs/MetricSeen.md)
 - [MetricV2Rep](docs/MetricV2Rep.md)
 - [ModelImport](docs/ModelImport.md)
 - [Modification](docs/Modification.md)
 - [MultiEnvironmentDependentFlag](docs/MultiEnvironmentDependentFlag.md)
 - [MultiEnvironmentDependentFlags](docs/MultiEnvironmentDependentFlags.md)
 - [NewMemberForm](docs/NewMemberForm.md)
 - [NotFoundErrorRep](docs/NotFoundErrorRep.md)
 - [OauthClientPost](docs/OauthClientPost.md)
 - [ParameterDefault](docs/ParameterDefault.md)
 - [ParameterDefaultInput](docs/ParameterDefaultInput.md)
 - [ParameterRep](docs/ParameterRep.md)
 - [ParentResourceRep](docs/ParentResourceRep.md)
 - [PatchFailedErrorRep](docs/PatchFailedErrorRep.md)
 - [PatchFlagsRequest](docs/PatchFlagsRequest.md)
 - [PatchOperation](docs/PatchOperation.md)
 - [PatchSegmentInstruction](docs/PatchSegmentInstruction.md)
 - [PatchSegmentRequest](docs/PatchSegmentRequest.md)
 - [PatchUsersRequest](docs/PatchUsersRequest.md)
 - [PatchWithComment](docs/PatchWithComment.md)
 - [PermissionGrantInput](docs/PermissionGrantInput.md)
 - [PostApprovalRequestApplyRequest](docs/PostApprovalRequestApplyRequest.md)
 - [PostApprovalRequestReviewRequest](docs/PostApprovalRequestReviewRequest.md)
 - [PostFlagScheduledChangesInput](docs/PostFlagScheduledChangesInput.md)
 - [Prerequisite](docs/Prerequisite.md)
 - [Project](docs/Project.md)
 - [ProjectListingRep](docs/ProjectListingRep.md)
 - [ProjectPost](docs/ProjectPost.md)
 - [ProjectRep](docs/ProjectRep.md)
 - [ProjectSummary](docs/ProjectSummary.md)
 - [Projects](docs/Projects.md)
 - [PubNubDetailRep](docs/PubNubDetailRep.md)
 - [PutBranch](docs/PutBranch.md)
 - [RateLimitedErrorRep](docs/RateLimitedErrorRep.md)
 - [RecentTriggerBody](docs/RecentTriggerBody.md)
 - [ReferenceRep](docs/ReferenceRep.md)
 - [RelativeDifferenceRep](docs/RelativeDifferenceRep.md)
 - [RelayAutoConfigCollectionRep](docs/RelayAutoConfigCollectionRep.md)
 - [RelayAutoConfigPost](docs/RelayAutoConfigPost.md)
 - [RelayAutoConfigRep](docs/RelayAutoConfigRep.md)
 - [RepositoryCollectionRep](docs/RepositoryCollectionRep.md)
 - [RepositoryPost](docs/RepositoryPost.md)
 - [RepositoryRep](docs/RepositoryRep.md)
 - [ResolvedContext](docs/ResolvedContext.md)
 - [ResolvedImage](docs/ResolvedImage.md)
 - [ResolvedTitle](docs/ResolvedTitle.md)
 - [ResolvedUIBlockElement](docs/ResolvedUIBlockElement.md)
 - [ResolvedUIBlocks](docs/ResolvedUIBlocks.md)
 - [ResourceAccess](docs/ResourceAccess.md)
 - [ResourceIDResponse](docs/ResourceIDResponse.md)
 - [ReviewOutput](docs/ReviewOutput.md)
 - [ReviewResponse](docs/ReviewResponse.md)
 - [Rollout](docs/Rollout.md)
 - [Rule](docs/Rule.md)
 - [RuleClause](docs/RuleClause.md)
 - [ScheduleConditionInput](docs/ScheduleConditionInput.md)
 - [ScheduleConditionOutput](docs/ScheduleConditionOutput.md)
 - [SdkListRep](docs/SdkListRep.md)
 - [SdkVersionListRep](docs/SdkVersionListRep.md)
 - [SdkVersionRep](docs/SdkVersionRep.md)
 - [SegmentBody](docs/SegmentBody.md)
 - [SegmentMetadata](docs/SegmentMetadata.md)
 - [SegmentUserList](docs/SegmentUserList.md)
 - [SegmentUserState](docs/SegmentUserState.md)
 - [SeriesListRep](docs/SeriesListRep.md)
 - [SourceEnv](docs/SourceEnv.md)
 - [SourceFlag](docs/SourceFlag.md)
 - [StageInput](docs/StageInput.md)
 - [StageOutput](docs/StageOutput.md)
 - [Statement](docs/Statement.md)
 - [StatementPost](docs/StatementPost.md)
 - [StatementPostData](docs/StatementPostData.md)
 - [StatisticCollectionRep](docs/StatisticCollectionRep.md)
 - [StatisticRep](docs/StatisticRep.md)
 - [StatisticsRep](docs/StatisticsRep.md)
 - [StatisticsRoot](docs/StatisticsRoot.md)
 - [StatusConflictErrorRep](docs/StatusConflictErrorRep.md)
 - [SubjectDataRep](docs/SubjectDataRep.md)
 - [SubscriptionPost](docs/SubscriptionPost.md)
 - [TagCollection](docs/TagCollection.md)
 - [Target](docs/Target.md)
 - [TargetResourceRep](docs/TargetResourceRep.md)
 - [Team](docs/Team.md)
 - [TeamCustomRole](docs/TeamCustomRole.md)
 - [TeamCustomRoles](docs/TeamCustomRoles.md)
 - [TeamImportsRep](docs/TeamImportsRep.md)
 - [TeamMaintainers](docs/TeamMaintainers.md)
 - [TeamMembers](docs/TeamMembers.md)
 - [TeamPatchInput](docs/TeamPatchInput.md)
 - [TeamPostInput](docs/TeamPostInput.md)
 - [TeamProjects](docs/TeamProjects.md)
 - [TeamRepExpandableProperties](docs/TeamRepExpandableProperties.md)
 - [Teams](docs/Teams.md)
 - [TeamsPatchInput](docs/TeamsPatchInput.md)
 - [TimestampRep](docs/TimestampRep.md)
 - [TitleRep](docs/TitleRep.md)
 - [Token](docs/Token.md)
 - [TokenDataRep](docs/TokenDataRep.md)
 - [Tokens](docs/Tokens.md)
 - [TreatmentInput](docs/TreatmentInput.md)
 - [TreatmentParameterInput](docs/TreatmentParameterInput.md)
 - [TreatmentRep](docs/TreatmentRep.md)
 - [TreatmentResultRep](docs/TreatmentResultRep.md)
 - [TriggerPost](docs/TriggerPost.md)
 - [TriggerWorkflowCollectionRep](docs/TriggerWorkflowCollectionRep.md)
 - [TriggerWorkflowRep](docs/TriggerWorkflowRep.md)
 - [UnauthorizedErrorRep](docs/UnauthorizedErrorRep.md)
 - [UpsertFlagDefaultsPayload](docs/UpsertFlagDefaultsPayload.md)
 - [UpsertPayloadRep](docs/UpsertPayloadRep.md)
 - [UrlPost](docs/UrlPost.md)
 - [User](docs/User.md)
 - [UserAttributeNamesRep](docs/UserAttributeNamesRep.md)
 - [UserFlagSetting](docs/UserFlagSetting.md)
 - [UserFlagSettings](docs/UserFlagSettings.md)
 - [UserRecord](docs/UserRecord.md)
 - [UserRecordRep](docs/UserRecordRep.md)
 - [UserSegment](docs/UserSegment.md)
 - [UserSegmentRule](docs/UserSegmentRule.md)
 - [UserSegments](docs/UserSegments.md)
 - [Users](docs/Users.md)
 - [UsersRep](docs/UsersRep.md)
 - [ValuePut](docs/ValuePut.md)
 - [Variate](docs/Variate.md)
 - [Variation](docs/Variation.md)
 - [VariationOrRolloutRep](docs/VariationOrRolloutRep.md)
 - [VariationSummary](docs/VariationSummary.md)
 - [VersionsRep](docs/VersionsRep.md)
 - [Webhook](docs/Webhook.md)
 - [WebhookPost](docs/WebhookPost.md)
 - [Webhooks](docs/Webhooks.md)
 - [WeightedVariation](docs/WeightedVariation.md)
 - [WorkflowTemplateMetadata](docs/WorkflowTemplateMetadata.md)
 - [WorkflowTemplateOutput](docs/WorkflowTemplateOutput.md)
 - [WorkflowTemplateParameter](docs/WorkflowTemplateParameter.md)
 - [WorkflowTemplateParameterInput](docs/WorkflowTemplateParameterInput.md)
 - [WorkflowTemplatesListingOutputRep](docs/WorkflowTemplatesListingOutputRep.md)


## Documentation for Authorization

Authentication schemes defined for the API:
### ApiKey

- **Type**: API key
- **API key parameter name**: Authorization
- **Location**: HTTP header


## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author

support@launchdarkly.com

