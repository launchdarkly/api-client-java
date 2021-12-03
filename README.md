This repository contains a client library for LaunchDarkly's REST API. This client was automatically
generated from our [OpenAPI specification](https://app.launchdarkly.com/api/v2/openapi.json) using a [code generation library](https://github.com/launchdarkly/ld-openapi). View our [sample code](#sample-code) for example usage.

This REST API is for custom integrations, data export, or automating your feature flag workflows. *DO NOT* use this client library to include feature flags in your web or mobile application. To integrate feature flags with your application, read the [SDK documentation](https://docs.launchdarkly.com/sdk).
# api-client

LaunchDarkly REST API
- API version: 2.0
  - Build date: 2021-12-03T20:08:15.055980Z[Etc/UTC]

# Overview

## Authentication

All REST API resources are authenticated with either [personal or service access tokens](https://docs.launchdarkly.com/home/account-security/api-access-tokens), or session cookies. Other authentication mechanisms are not supported. You can manage personal access tokens on your [Account settings](https://app.launchdarkly.com/settings/tokens) page.

LaunchDarkly also has SDK keys, mobile keys, and client-side IDs that are used by our server-side SDKs, mobile SDKs, and client-side SDKs, respectively. **These keys cannot be used to access our REST API**. These keys are environment-specific, and can only perform read-only operations (fetching feature flag settings).

| Auth mechanism                                                                                  | Allowed resources                                                                                     | Use cases                                          |
| ----------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------- | -------------------------------------------------- |
| [Personal access tokens](https://docs.launchdarkly.com/home/account-security/api-access-tokens) | Can be customized on a per-token basis                                                                | Building scripts, custom integrations, data export |
| SDK keys                                                                                        | Can only access read-only SDK-specific resources and the firehose, restricted to a single environment | Server-side SDKs, Firehose API                     |
| Mobile keys                                                                                     | Can only access read-only mobile SDK-specific resources, restricted to a single environment           | Mobile SDKs                                        |
| Client-side ID                                                                                  | Single environment, only flags marked available to client-side                                        | Client-side JavaScript                             |

> #### Keep your access tokens and SDK keys private
>
> Access tokens should _never_ be exposed in untrusted contexts. Never put an access token in client-side JavaScript, or embed it in a mobile application. LaunchDarkly has special mobile keys that you can embed in mobile apps. If you accidentally expose an access token or SDK key, you can reset it from your [Account Settings](https://app.launchdarkly.com/settings#/tokens) page.
>
> The client-side ID is safe to embed in untrusted contexts. It's designed for use in client-side JavaScript.

### Via request header

The preferred way to authenticate with the API is by adding an `Authorization` header containing your access token to your requests. The value of the `Authorization` header must be your access token.

Manage personal access tokens from the [Account Settings](https://app.launchdarkly.com/settings/tokens) page.

### Via session cookie

For testing purposes, you can make API calls directly from your web browser. If you're logged in to the application, the API will use your existing session to authenticate calls.

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

All resources expect and return JSON response bodies. Error responses will also send a JSON body. Read [Errors](#section/Errors) for a more detailed description of the error format used by the API.

In practice this means that you always get a response with a `Content-Type` header set to `application/json`.

In addition, request bodies for `PUT`, `POST`, `REPORT` and `PATCH` requests must be encoded as JSON with a `Content-Type` header set to `application/json`.

### Summary and detailed representations

When you fetch a list of resources, the response includes only the most important attributes of each resource. This is a _summary representation_ of the resource. When you fetch an individual resource (for example, a single feature flag), you receive a _detailed representation_ containing all of the attributes of the resource.

The best way to find a detailed representation is to follow links. Every summary representation includes a link to its detailed representation.

### Links and addressability

The best way to navigate the API is by following links. These are attributes in representations that link to other resources. The API always uses the same format for links:

- Links to other resources within the API are encapsulated in a `_links` object.
- If the resource has a corresponding link to HTML content on the site, it is stored in a special `_site` link.

Each link has two attributes: an href (the URL) and a type (the content type). For example, a feature resource might return the following:

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

Resources that accept partial updates use the `PATCH` verb, and support the [JSON Patch](https://datatracker.ietf.org/doc/html/rfc6902) format. Some resources also support the [JSON Merge Patch](https://datatracker.ietf.org/doc/html/rfc7386) format. In addition, some resources support optional comments that can be submitted with updates. Comments appear in outgoing webhooks, the audit log, and other integrations.

### Updates via JSON Patch

[JSON Patch](https://datatracker.ietf.org/doc/html/rfc6902) is a way to specify the modifications to perform on a resource. For example, in this feature flag representation:

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

JSON Patch documents are always arrays. You can specify multiple modifications to perform in a single request. You can also test that certain preconditions are met before applying the patch:

```json
[
  { \"op\": \"test\", \"path\": \"/version\", \"value\": 10 },
  { \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" }
]
```

The above patch request tests whether the feature flag's `version` is `10`, and if so, changes the feature flag's description.

Attributes that aren't editable, like a resource's `_links`, have names that start with an underscore.

### Updates via JSON Merge Patch

The API also supports the [JSON Merge Patch](https://datatracker.ietf.org/doc/html/rfc7386) format, as well as the [Update feature flag](/tag/Feature-flags#operation/patchFeatureFlag) resource.

JSON Merge Patch is less expressive than JSON Patch but in many cases, it is simpler to construct a merge patch document. For example, you can change a feature flag's description with the following merge patch document:

```json
{
  \"description\": \"New flag description\"
}
```

### Updates with comments

You can submit optional comments with `PATCH` changes. The [Update feature flag](/tag/Feature-flags#operation/patchFeatureFlag) resource supports comments.

To submit a comment along with a JSON Patch document, use the following format:

```json
{
  \"comment\": \"This is a comment string\",
  \"patch\": [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" }]
}
```

To submit a comment along with a JSON Merge Patch document, use the following format:

```json
{
  \"comment\": \"This is a comment string\",
  \"merge\": { \"description\": \"New flag description\" }
}
```

### Updates via semantic patches

The API also supports the Semantic patch format. A semantic `PATCH` is a way to specify the modifications to perform on a resource as a set of executable instructions.

JSON Patch uses paths and a limited set of operations to describe how to transform the current state of the resource into a new state. Semantic patch allows you to be explicit about intent using precise, custom instructions. In many cases, semantic patch instructions can also be defined independently of the current state of the resource. This can be useful when defining a change that may be applied at a future date.

For example, in this feature flag configuration in environment Production:

```json
{
    \"name\": \"Alternate sort order\",
    \"kind\": \"boolean\",
    \"key\": \"sort.order\",
   ...
    \"environments\": {
        \"production\": {
            \"on\": true,
            \"archived\": false,
            \"salt\": \"c29ydC5vcmRlcg==\",
            \"sel\": \"8de1085cb7354b0ab41c0e778376dfd3\",
            \"lastModified\": 1469131558260,
            \"version\": 81,
            \"targets\": [
                {
                    \"values\": [
                        \"Gerhard.Little@yahoo.com\"
                    ],
                    \"variation\": 0
                },
                {
                    \"values\": [
                        \"1461797806429-33-861961230\",
                        \"438580d8-02ee-418d-9eec-0085cab2bdf0\"
                    ],
                    \"variation\": 1
                }
            ],
            \"rules\": [],
            \"fallthrough\": {
                \"variation\": 0
            },
            \"offVariation\": 1,
            \"prerequisites\": [],
            \"_site\": {
                \"href\": \"/default/production/features/sort.order\",
                \"type\": \"text/html\"
            }
       }
    }
}
```

You can add a date you want a user to be removed from the feature flag's user targets. For example, “remove user 1461797806429-33-861961230 from the user target for variation 0 on the Alternate sort order flag in the production environment on Wed Jul 08 2020 at 15:27:41 pm”. This is done using the following:

```json
{
  \"comment\": \"update expiring user targets\",
  \"instructions\": [
    {
      \"kind\": \"removeExpireUserTargetDate\",
      \"userKey\": \"userKey\",
      \"variationId\": \"978d53f9-7fe3-4a63-992d-97bcb4535dc8\"
    },
    {
      \"kind\": \"updateExpireUserTargetDate\",
      \"userKey\": \"userKey2\",
      \"variationId\": \"978d53f9-7fe3-4a63-992d-97bcb4535dc8\",
      \"value\": 1587582000000
    },
    {
      \"kind\": \"addExpireUserTargetDate\",
      \"userKey\": \"userKey3\",
      \"variationId\": \"978d53f9-7fe3-4a63-992d-97bcb4535dc8\",
      \"value\": 1594247266386
    }
  ]
}
```

Here is another example. In this feature flag configuration:

```json
{
  \"name\": \"New recommendations engine\",
  \"key\": \"engine.enable\",
  \"environments\": {
    \"test\": {
      \"on\": true
    }
  }
}
```

You can change the feature flag's description with the following patch document as a set of executable instructions. For example, “add user X to targets for variation Y and remove user A from targets for variation B for test flag”:

```json
{
  \"comment\": \"\",
  \"instructions\": [
    {
      \"kind\": \"removeUserTargets\",
      \"values\": [\"438580d8-02ee-418d-9eec-0085cab2bdf0\"],
      \"variationId\": \"852cb784-54ff-46b9-8c35-5498d2e4f270\"
    },
    {
      \"kind\": \"addUserTargets\",
      \"values\": [\"438580d8-02ee-418d-9eec-0085cab2bdf0\"],
      \"variationId\": \"1bb18465-33b6-49aa-a3bd-eeb6650b33ad\"
    }
  ]
}
```

> ### Supported semantic patch API endpoints
>
> - [Update feature flag](/tag/Feature-flags#operation/patchFeatureFlag)
> - [Update expiring user targets on feature flag](/tag/Feature-flags#operation/patchExpiringUserTargets)
> - [Update expiring user target for flags](/tag/User-settings#operation/patchExpiringFlagsForUser)
> - [Update expiring user targets on segment](/tag/Segments#operation/patchExpiringUserTargetsForSegment)

## Errors

The API always returns errors in a common format. Here's an example:

```json
{
  \"code\": \"invalid_request\",
  \"message\": \"A feature with that key already exists\",
  \"id\": \"30ce6058-87da-11e4-b116-123b93f75cba\"
}
```

The general class of error is indicated by the `code`. The `message` is a human-readable explanation of what went wrong. The `id` is a unique identifier. Use it when you're working with LaunchDarkly support to debug a problem with a specific API call.

### HTTP Status - Error Response Codes

| Code | Definition        | Desc.                                                                                       | Possible Solution                                                |
| ---- | ----------------- | ------------------------------------------------------------------------------------------- | ---------------------------------------------------------------- |
| 400  | Bad Request       | A request that fails may return this HTTP response code.                                    | Ensure JSON syntax in request body is correct.                   |
| 401  | Unauthorized      | User doesn't have permission to an API call.                                                | Ensure your SDK key is good.                                     |
| 403  | Forbidden         | User does not have permission for operation.                                                | Ensure that the user or access token has proper permissions set. |
| 409  | Conflict          | The API request could not be completed because it conflicted with a concurrent API request. | Retry your request.                                              |
| 429  | Too many requests | See [Rate limiting](/#section/Rate-limiting).                                               | Wait and try again later.                                        |

## CORS

The LaunchDarkly API supports Cross Origin Resource Sharing (CORS) for AJAX requests from any origin. If an `Origin` header is given in a request, it will be echoed as an explicitly allowed origin. Otherwise, a wildcard is returned: `Access-Control-Allow-Origin: *`. For more information on CORS, see the [CORS W3C Recommendation](http://www.w3.org/TR/cors). Example CORS headers might look like:

```http
Access-Control-Allow-Headers: Accept, Content-Type, Content-Length, Accept-Encoding, Authorization
Access-Control-Allow-Methods: OPTIONS, GET, DELETE, PATCH
Access-Control-Allow-Origin: *
Access-Control-Max-Age: 300
```

You can make authenticated CORS calls just as you would make same-origin calls, using either [token or session-based authentication](#section/Authentication). If you’re using session auth, you should set the `withCredentials` property for your `xhr` request to `true`. You should never expose your access tokens to untrusted users.

## Rate limiting

We use several rate limiting strategies to ensure the availability of our APIs. Rate-limited calls to our APIs will return a `429` status code. Calls to our APIs will include headers indicating the current rate limit status. The specific headers returned depend on the API route being called. The limits differ based on the route, authentication mechanism, and other factors. Routes that are not rate limited may not contain any of the headers described below.

> ### Rate limiting and SDKs
>
> LaunchDarkly SDKs are never rate limited and do not use the API endpoints defined here. LaunchDarkly uses a different set of approaches, including streaming/server-sent events and a global CDN, to ensure availability to the routes used by LaunchDarkly SDKs.
>
> The client-side ID is safe to embed in untrusted contexts. It's designed for use in client-side JavaScript.

### Global rate limits

Authenticated requests are subject to a global limit. This is the maximum number of calls that can be made to the API per ten seconds. All personal access tokens on the account share this limit, so exceeding the limit with one access token will impact other tokens. Calls that are subject to global rate limits will return the headers below:

| Header name                    | Description                                                                      |
| ------------------------------ | -------------------------------------------------------------------------------- |
| `X-Ratelimit-Global-Remaining` | The maximum number of requests the account is permitted to make per ten seconds. |
| `X-Ratelimit-Reset`            | The time at which the current rate limit window resets in epoch milliseconds.    |

We do not publicly document the specific number of calls that can be made globally. This limit may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limit.

### Route-level rate limits

Some authenticated routes have custom rate limits. These also reset every ten seconds. Any access tokens hitting the same route share this limit, so exceeding the limit with one access token may impact other tokens. Calls that are subject to route-level rate limits will return the headers below:

| Header name                   | Description                                                                                           |
| ----------------------------- | ----------------------------------------------------------------------------------------------------- |
| `X-Ratelimit-Route-Remaining` | The maximum number of requests to the current route the account is permitted to make per ten seconds. |
| `X-Ratelimit-Reset`           | The time at which the current rate limit window resets in epoch milliseconds.                         |

A _route_ represents a specific URL pattern and verb. For example, the [Delete environment](/tag/Environments#operation/deleteEnvironment) endpoint is considered a single route, and each call to delete an environment counts against your route-level rate limit for that route.

We do not publicly document the specific number of calls that can be made to each endpoint per ten seconds. These limits may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limits.

### IP-based rate limiting

We also employ IP-based rate limiting on some API routes. If you hit an IP-based rate limit, your API response will include a `Retry-After` header indicating how long to wait before re-trying the call. Clients must wait at least `Retry-After` seconds before making additional calls to our API, and should employ jitter and backoff strategies to avoid triggering rate limits again.

## OpenAPI (Swagger)

We have a [complete OpenAPI (Swagger) specification](https://app.launchdarkly.com/api/v2/openapi.json) for our API.

You can use this specification to generate client libraries to interact with our REST API in your language of choice.

This specification is supported by several API-based tools such as Postman and Insomnia. In many cases, you can directly import our specification to ease use in navigating the APIs in the tooling.

## Client libraries

We auto-generate multiple client libraries based on our OpenAPI specification. To learn more, visit [GitHub](https://github.com/search?q=topic%3Alaunchdarkly-api+org%3Alaunchdarkly&type=Repositories).

## Method Overriding

Some firewalls and HTTP clients restrict the use of verbs other than `GET` and `POST`. In those environments, our API endpoints that use `PUT`, `PATCH`, and `DELETE` verbs will be inaccessible.

To avoid this issue, our API supports the `X-HTTP-Method-Override` header, allowing clients to \"tunnel\" `PUT`, `PATCH`, and `DELETE` requests via a `POST` request.

For example, if you wish to call one of our `PATCH` resources via a `POST` request, you can include `X-HTTP-Method-Override:PATCH` as a header.

## Beta resources

We sometimes release new API resources in **beta** status before we release them with general availability.

Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.

We try to promote resources into general availability as quickly as possible. This happens after sufficient testing and when we're satisfied that we no longer need to make backwards-incompatible changes.

We mark beta resources with a \"Beta\" callout in our documentation, pictured below:

> ### This feature is in beta
>
> To use this feature, pass in a header including the `LD-API-Version` key with value set to `beta`. Use this header with each call. To learn more, read [Beta resources](/#section/Beta-resources).

### Using beta resources

To use a beta resource, you must include a header in the request. If you call a beta resource without this header, you'll receive a `403` response.

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
LD-API-Version: 20191212
```

The header value is the version number of the API version you'd like to request. The number for each version corresponds to the date the version was released. In the example above the version `20191212` corresponds to December 12, 2019.

### Setting the API version per access token

When creating an access token, you must specify a specific version of the API to use. This ensures that integrations using this token cannot be broken by version changes.

Tokens created before versioning was released have their version set to `20160426` (the version of the API that existed before versioning) so that they continue working the same way they did before versioning.

If you would like to upgrade your integration to use a new API version, you can explicitly set the header described above.

> ### Best practice: Set the header for every client or integration
>
> We recommend that you set the API version header explicitly in any client or integration you build.
>
> Only rely on the access token API version during manual testing.


  For more information, please visit [https://support.launchdarkly.com](https://support.launchdarkly.com)

*Automatically generated by the [OpenAPI Generator](https://openapi-generator.tech)*


## Requirements

Building the API client library requires:
1. Java 1.7+
2. Maven/Gradle

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
  <version>7.0.0</version>
  <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "com.launchdarkly:api-client:7.0.0"
```

### Others

At first generate the JAR by executing:

```shell
mvn clean package
```

Then manually install the following JARs:

* `target/api-client-7.0.0.jar`
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
*AccountMembersApi* | [**postMembers**](docs/AccountMembersApi.md#postMembers) | **POST** /api/v2/members | Invite new members
*AccountUsageBetaApi* | [**getEvaluationsUsage**](docs/AccountUsageBetaApi.md#getEvaluationsUsage) | **GET** /api/v2/usage/evaluations/{projKey}/{envKey}/{flagKey} | Get evaluations usage
*AccountUsageBetaApi* | [**getEventsUsage**](docs/AccountUsageBetaApi.md#getEventsUsage) | **GET** /api/v2/usage/events/{type} | Get events usage
*AccountUsageBetaApi* | [**getMauSdksByType**](docs/AccountUsageBetaApi.md#getMauSdksByType) | **GET** /api/v2/usage/mau/sdks | Get MAU SDKs by type
*AccountUsageBetaApi* | [**getMauUsage**](docs/AccountUsageBetaApi.md#getMauUsage) | **GET** /api/v2/usage/mau | Get MAU usage
*AccountUsageBetaApi* | [**getMauUsageByCategory**](docs/AccountUsageBetaApi.md#getMauUsageByCategory) | **GET** /api/v2/usage/mau/bycategory | Get MAU usage by category
*AccountUsageBetaApi* | [**getStreamUsage**](docs/AccountUsageBetaApi.md#getStreamUsage) | **GET** /api/v2/usage/streams/{source} | Get stream usage
*AccountUsageBetaApi* | [**getStreamUsageBySdkVersion**](docs/AccountUsageBetaApi.md#getStreamUsageBySdkVersion) | **GET** /api/v2/usage/streams/{source}/bysdkversion | Get stream usage by SDK version
*AccountUsageBetaApi* | [**getStreamUsageSdkversion**](docs/AccountUsageBetaApi.md#getStreamUsageSdkversion) | **GET** /api/v2/usage/streams/{source}/sdkversions | Get stream usage SDK versions
*ApprovalsApi* | [**deleteApprovalRequest**](docs/ApprovalsApi.md#deleteApprovalRequest) | **DELETE** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests/{id} | Delete approval request
*ApprovalsApi* | [**getApproval**](docs/ApprovalsApi.md#getApproval) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests/{id} | Get approval request
*ApprovalsApi* | [**getApprovals**](docs/ApprovalsApi.md#getApprovals) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests | List all approval requests
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
*CodeReferencesApi* | [**getStatistics**](docs/CodeReferencesApi.md#getStatistics) | **GET** /api/v2/code-refs/statistics/{projKey} | Get number of code references for flags
*CodeReferencesApi* | [**patchRepository**](docs/CodeReferencesApi.md#patchRepository) | **PATCH** /api/v2/code-refs/repositories/{repo} | Update repository
*CodeReferencesApi* | [**postExtinction**](docs/CodeReferencesApi.md#postExtinction) | **POST** /api/v2/code-refs/repositories/{repo}/branches/{branch}/extinction-events | Create extinction
*CodeReferencesApi* | [**postRepository**](docs/CodeReferencesApi.md#postRepository) | **POST** /api/v2/code-refs/repositories | Create repository
*CodeReferencesApi* | [**putBranch**](docs/CodeReferencesApi.md#putBranch) | **PUT** /api/v2/code-refs/repositories/{repo}/branches/{branch} | Upsert branch
*CustomRolesApi* | [**deleteCustomRole**](docs/CustomRolesApi.md#deleteCustomRole) | **DELETE** /api/v2/roles/{key} | Delete custom role
*CustomRolesApi* | [**getCustomRole**](docs/CustomRolesApi.md#getCustomRole) | **GET** /api/v2/roles/{key} | Get custom role
*CustomRolesApi* | [**getCustomRoles**](docs/CustomRolesApi.md#getCustomRoles) | **GET** /api/v2/roles | List custom roles
*CustomRolesApi* | [**patchCustomRole**](docs/CustomRolesApi.md#patchCustomRole) | **PATCH** /api/v2/roles/{key} | Update custom role
*CustomRolesApi* | [**postCustomRole**](docs/CustomRolesApi.md#postCustomRole) | **POST** /api/v2/roles | Create custom role
*DataExportDestinationsApi* | [**deleteDestination**](docs/DataExportDestinationsApi.md#deleteDestination) | **DELETE** /api/v2/destinations/{projKey}/{envKey}/{id} | Delete Data Export destination
*DataExportDestinationsApi* | [**getDestination**](docs/DataExportDestinationsApi.md#getDestination) | **GET** /api/v2/destinations/{projKey}/{envKey}/{id} | Get destination
*DataExportDestinationsApi* | [**getDestinations**](docs/DataExportDestinationsApi.md#getDestinations) | **GET** /api/v2/destinations | List destinations
*DataExportDestinationsApi* | [**patchDestination**](docs/DataExportDestinationsApi.md#patchDestination) | **PATCH** /api/v2/destinations/{projKey}/{envKey}/{id} | Update Data Export destination
*DataExportDestinationsApi* | [**postDestination**](docs/DataExportDestinationsApi.md#postDestination) | **POST** /api/v2/destinations/{projKey}/{envKey} | Create data export destination
*EnvironmentsApi* | [**deleteEnvironment**](docs/EnvironmentsApi.md#deleteEnvironment) | **DELETE** /api/v2/projects/{projectKey}/environments/{environmentKey} | Delete environment
*EnvironmentsApi* | [**getEnvironment**](docs/EnvironmentsApi.md#getEnvironment) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey} | Get environment
*EnvironmentsApi* | [**patchEnvironment**](docs/EnvironmentsApi.md#patchEnvironment) | **PATCH** /api/v2/projects/{projectKey}/environments/{environmentKey} | Update environment
*EnvironmentsApi* | [**postEnvironment**](docs/EnvironmentsApi.md#postEnvironment) | **POST** /api/v2/projects/{projectKey}/environments | Create environment
*EnvironmentsApi* | [**resetEnvironmentMobileKey**](docs/EnvironmentsApi.md#resetEnvironmentMobileKey) | **POST** /api/v2/projects/{projectKey}/environments/{envKey}/mobileKey | Reset environment mobile SDK key
*EnvironmentsApi* | [**resetEnvironmentSDKKey**](docs/EnvironmentsApi.md#resetEnvironmentSDKKey) | **POST** /api/v2/projects/{projectKey}/environments/{envKey}/apiKey | Reset environment SDK key
*ExperimentsBetaApi* | [**getExperiment**](docs/ExperimentsBetaApi.md#getExperiment) | **GET** /api/v2/flags/{projKey}/{flagKey}/experiments/{envKey}/{metricKey} | Get experiment results
*ExperimentsBetaApi* | [**resetExperiment**](docs/ExperimentsBetaApi.md#resetExperiment) | **DELETE** /api/v2/flags/{projKey}/{flagKey}/experiments/{envKey}/{metricKey}/results | Reset experiment results
*FeatureFlagsApi* | [**copyFeatureFlag**](docs/FeatureFlagsApi.md#copyFeatureFlag) | **POST** /api/v2/flags/{projKey}/{featureFlagKey}/copy | Copy feature flag
*FeatureFlagsApi* | [**deleteFeatureFlag**](docs/FeatureFlagsApi.md#deleteFeatureFlag) | **DELETE** /api/v2/flags/{projKey}/{key} | Delete feature flag
*FeatureFlagsApi* | [**getExpiringUserTargets**](docs/FeatureFlagsApi.md#getExpiringUserTargets) | **GET** /api/v2/flags/{projKey}/{flagKey}/expiring-user-targets/{envKey} | Get expiring user targets for feature flag
*FeatureFlagsApi* | [**getFeatureFlag**](docs/FeatureFlagsApi.md#getFeatureFlag) | **GET** /api/v2/flags/{projKey}/{key} | Get feature flag
*FeatureFlagsApi* | [**getFeatureFlagStatus**](docs/FeatureFlagsApi.md#getFeatureFlagStatus) | **GET** /api/v2/flag-statuses/{projKey}/{envKey}/{key} | Get feature flag status
*FeatureFlagsApi* | [**getFeatureFlagStatusAcrossEnvironments**](docs/FeatureFlagsApi.md#getFeatureFlagStatusAcrossEnvironments) | **GET** /api/v2/flag-status/{projKey}/{key} | Get flag status across environments
*FeatureFlagsApi* | [**getFeatureFlagStatuses**](docs/FeatureFlagsApi.md#getFeatureFlagStatuses) | **GET** /api/v2/flag-statuses/{projKey}/{envKey} | List feature flag statuses
*FeatureFlagsApi* | [**getFeatureFlags**](docs/FeatureFlagsApi.md#getFeatureFlags) | **GET** /api/v2/flags/{projKey} | List feature flags
*FeatureFlagsApi* | [**patchExpiringUserTargets**](docs/FeatureFlagsApi.md#patchExpiringUserTargets) | **PATCH** /api/v2/flags/{projKey}/{flagKey}/expiring-user-targets/{envKey} | Update expiring user targets on feature flag
*FeatureFlagsApi* | [**patchFeatureFlag**](docs/FeatureFlagsApi.md#patchFeatureFlag) | **PATCH** /api/v2/flags/{projKey}/{key} | Update feature flag
*FeatureFlagsApi* | [**postFeatureFlag**](docs/FeatureFlagsApi.md#postFeatureFlag) | **POST** /api/v2/flags/{projKey} | Create a feature flag
*FeatureFlagsBetaApi* | [**getDependentFlags**](docs/FeatureFlagsBetaApi.md#getDependentFlags) | **GET** /api/v2/flags/{projKey}/{flagKey}/dependent-flags | List dependent feature flags
*FeatureFlagsBetaApi* | [**getDependentFlagsByEnv**](docs/FeatureFlagsBetaApi.md#getDependentFlagsByEnv) | **GET** /api/v2/flags/{projKey}/{envKey}/{flagKey}/dependent-flags | List dependent feature flags by environment
*MetricsApi* | [**deleteMetric**](docs/MetricsApi.md#deleteMetric) | **DELETE** /api/v2/metrics/{projectKey}/{key} | Delete metric
*MetricsApi* | [**getMetric**](docs/MetricsApi.md#getMetric) | **GET** /api/v2/metrics/{projectKey}/{key} | Get metric
*MetricsApi* | [**getMetrics**](docs/MetricsApi.md#getMetrics) | **GET** /api/v2/metrics/{projectKey} | List metrics
*MetricsApi* | [**patchMetric**](docs/MetricsApi.md#patchMetric) | **PATCH** /api/v2/metrics/{projectKey}/{key} | Update metric
*MetricsApi* | [**postMetric**](docs/MetricsApi.md#postMetric) | **POST** /api/v2/metrics/{projectKey} | Create metric
*OtherApi* | [**getIps**](docs/OtherApi.md#getIps) | **GET** /api/v2/public-ip-list | Gets the public IP list
*OtherApi* | [**getOpenapiSpec**](docs/OtherApi.md#getOpenapiSpec) | **GET** /api/v2/openapi.json | Gets the OpenAPI spec in json
*OtherApi* | [**getRoot**](docs/OtherApi.md#getRoot) | **GET** /api/v2 | Root resource
*OtherApi* | [**getVersions**](docs/OtherApi.md#getVersions) | **GET** /api/v2/versions | Get version information
*ProjectsApi* | [**deleteProject**](docs/ProjectsApi.md#deleteProject) | **DELETE** /api/v2/projects/{projectKey} | Delete project
*ProjectsApi* | [**getProject**](docs/ProjectsApi.md#getProject) | **GET** /api/v2/projects/{projectKey} | Get project
*ProjectsApi* | [**getProjects**](docs/ProjectsApi.md#getProjects) | **GET** /api/v2/projects | List projects
*ProjectsApi* | [**patchProject**](docs/ProjectsApi.md#patchProject) | **PATCH** /api/v2/projects/{projectKey} | Update project
*ProjectsApi* | [**postProject**](docs/ProjectsApi.md#postProject) | **POST** /api/v2/projects | Create project
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
*SegmentsApi* | [**deleteSegment**](docs/SegmentsApi.md#deleteSegment) | **DELETE** /api/v2/segments/{projKey}/{envKey}/{key} | Delete segment
*SegmentsApi* | [**getExpiringUserTargetsForSegment**](docs/SegmentsApi.md#getExpiringUserTargetsForSegment) | **GET** /api/v2/segments/{projKey}/{segmentKey}/expiring-user-targets/{envKey} | Get expiring user targets for segment
*SegmentsApi* | [**getSegment**](docs/SegmentsApi.md#getSegment) | **GET** /api/v2/segments/{projKey}/{envKey}/{key} | Get segment
*SegmentsApi* | [**getSegmentMembershipForUser**](docs/SegmentsApi.md#getSegmentMembershipForUser) | **GET** /api/v2/segments/{projKey}/{envKey}/{key}/users/{userKey} | Get Big Segment membership for user
*SegmentsApi* | [**getSegments**](docs/SegmentsApi.md#getSegments) | **GET** /api/v2/segments/{projKey}/{envKey} | List segments
*SegmentsApi* | [**patchExpiringUserTargetsForSegment**](docs/SegmentsApi.md#patchExpiringUserTargetsForSegment) | **PATCH** /api/v2/segments/{projKey}/{segmentKey}/expiring-user-targets/{envKey} | Update expiring user targets for segment
*SegmentsApi* | [**patchSegment**](docs/SegmentsApi.md#patchSegment) | **PATCH** /api/v2/segments/{projKey}/{envKey}/{key} | Patch segment
*SegmentsApi* | [**postSegment**](docs/SegmentsApi.md#postSegment) | **POST** /api/v2/segments/{projKey}/{envKey} | Create segment
*SegmentsApi* | [**updateBigSegmentTargets**](docs/SegmentsApi.md#updateBigSegmentTargets) | **POST** /api/v2/segments/{projKey}/{envKey}/{key}/users | Update targets on a Big Segment
*TeamsBetaApi* | [**deleteTeam**](docs/TeamsBetaApi.md#deleteTeam) | **DELETE** /api/v2/teams/{key} | Delete team
*TeamsBetaApi* | [**getTeam**](docs/TeamsBetaApi.md#getTeam) | **GET** /api/v2/teams/{key} | Get team
*TeamsBetaApi* | [**getTeams**](docs/TeamsBetaApi.md#getTeams) | **GET** /api/v2/teams | List teams
*TeamsBetaApi* | [**patchTeam**](docs/TeamsBetaApi.md#patchTeam) | **PATCH** /api/v2/teams/{key} | Update team
*TeamsBetaApi* | [**postTeam**](docs/TeamsBetaApi.md#postTeam) | **POST** /api/v2/teams | Create team
*UserSettingsApi* | [**getExpiringFlagsForUser**](docs/UserSettingsApi.md#getExpiringFlagsForUser) | **GET** /api/v2/users/{projKey}/{userKey}/expiring-user-targets/{envKey} | Get expiring dates on flags for user
*UserSettingsApi* | [**getUserFlagSetting**](docs/UserSettingsApi.md#getUserFlagSetting) | **GET** /api/v2/users/{projKey}/{envKey}/{key}/flags/{featureKey} | Get flag setting for user
*UserSettingsApi* | [**getUserFlagSettings**](docs/UserSettingsApi.md#getUserFlagSettings) | **GET** /api/v2/users/{projKey}/{envKey}/{key}/flags | List flag settings for user
*UserSettingsApi* | [**patchExpiringFlagsForUser**](docs/UserSettingsApi.md#patchExpiringFlagsForUser) | **PATCH** /api/v2/users/{projKey}/{userKey}/expiring-user-targets/{envKey} | Update expiring user target for flags
*UserSettingsApi* | [**putFlagSetting**](docs/UserSettingsApi.md#putFlagSetting) | **PUT** /api/v2/users/{projKey}/{envKey}/{key}/flags/{featureKey} | Update flag settings for user
*UsersApi* | [**deleteUser**](docs/UsersApi.md#deleteUser) | **DELETE** /api/v2/users/{projKey}/{envKey}/{key} | Delete user
*UsersApi* | [**getSearchUsers**](docs/UsersApi.md#getSearchUsers) | **GET** /api/v2/user-search/{projKey}/{envKey} | Find users
*UsersApi* | [**getUser**](docs/UsersApi.md#getUser) | **GET** /api/v2/users/{projKey}/{envKey}/{key} | Get user
*UsersApi* | [**getUsers**](docs/UsersApi.md#getUsers) | **GET** /api/v2/users/{projKey}/{envKey} | List users
*UsersBetaApi* | [**getUserAttributeNames**](docs/UsersBetaApi.md#getUserAttributeNames) | **GET** /api/v2/user-attributes/{projectKey}/{environmentKey} | Get user attribute names
*WebhooksApi* | [**deleteWebhook**](docs/WebhooksApi.md#deleteWebhook) | **DELETE** /api/v2/webhooks/{id} | Delete webhook
*WebhooksApi* | [**getAllWebhooks**](docs/WebhooksApi.md#getAllWebhooks) | **GET** /api/v2/webhooks | List webhooks
*WebhooksApi* | [**getWebhook**](docs/WebhooksApi.md#getWebhook) | **GET** /api/v2/webhooks/{id} | Get webhook
*WebhooksApi* | [**patchWebhook**](docs/WebhooksApi.md#patchWebhook) | **PATCH** /api/v2/webhooks/{id} | Update webhook
*WebhooksApi* | [**postWebhook**](docs/WebhooksApi.md#postWebhook) | **POST** /api/v2/webhooks | Creates a webhook
*WorkflowsBetaApi* | [**deleteWorkflow**](docs/WorkflowsBetaApi.md#deleteWorkflow) | **DELETE** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/workflows/{workflowId} | Delete workflow
*WorkflowsBetaApi* | [**getCustomWorkflow**](docs/WorkflowsBetaApi.md#getCustomWorkflow) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/workflows/{workflowId} | Get custom workflow
*WorkflowsBetaApi* | [**getWorkflows**](docs/WorkflowsBetaApi.md#getWorkflows) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/workflows | Get workflows
*WorkflowsBetaApi* | [**postWorkflow**](docs/WorkflowsBetaApi.md#postWorkflow) | **POST** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/workflows | Create workflow


## Documentation for Models

 - [AccessDeniedReasonRep](docs/AccessDeniedReasonRep.md)
 - [AccessDeniedRep](docs/AccessDeniedRep.md)
 - [AccessRep](docs/AccessRep.md)
 - [AccessTokenPost](docs/AccessTokenPost.md)
 - [ActionInputRep](docs/ActionInputRep.md)
 - [ActionOutputRep](docs/ActionOutputRep.md)
 - [ApprovalConditionInputRep](docs/ApprovalConditionInputRep.md)
 - [ApprovalConditionOutputRep](docs/ApprovalConditionOutputRep.md)
 - [ApprovalSettings](docs/ApprovalSettings.md)
 - [AuditLogEntryListingRep](docs/AuditLogEntryListingRep.md)
 - [AuditLogEntryListingRepCollection](docs/AuditLogEntryListingRepCollection.md)
 - [AuditLogEntryRep](docs/AuditLogEntryRep.md)
 - [AuthorizedAppDataRep](docs/AuthorizedAppDataRep.md)
 - [BigSegmentTarget](docs/BigSegmentTarget.md)
 - [BranchCollectionRep](docs/BranchCollectionRep.md)
 - [BranchRep](docs/BranchRep.md)
 - [Clause](docs/Clause.md)
 - [ClientSideAvailability](docs/ClientSideAvailability.md)
 - [ClientSideAvailabilityPost](docs/ClientSideAvailabilityPost.md)
 - [ConditionBaseOutputRep](docs/ConditionBaseOutputRep.md)
 - [ConditionInputRep](docs/ConditionInputRep.md)
 - [ConditionOutputRep](docs/ConditionOutputRep.md)
 - [ConfidenceIntervalRep](docs/ConfidenceIntervalRep.md)
 - [Conflict](docs/Conflict.md)
 - [ConflictOutputRep](docs/ConflictOutputRep.md)
 - [CopiedFromEnv](docs/CopiedFromEnv.md)
 - [CreateCopyFlagConfigApprovalRequestRequest](docs/CreateCopyFlagConfigApprovalRequestRequest.md)
 - [CreateFlagConfigApprovalRequestRequest](docs/CreateFlagConfigApprovalRequestRequest.md)
 - [CustomProperty](docs/CustomProperty.md)
 - [CustomRole](docs/CustomRole.md)
 - [CustomRolePost](docs/CustomRolePost.md)
 - [CustomRolePostData](docs/CustomRolePostData.md)
 - [CustomRoles](docs/CustomRoles.md)
 - [CustomWorkflowInputRep](docs/CustomWorkflowInputRep.md)
 - [CustomWorkflowMeta](docs/CustomWorkflowMeta.md)
 - [CustomWorkflowOutputRep](docs/CustomWorkflowOutputRep.md)
 - [CustomWorkflowStageMeta](docs/CustomWorkflowStageMeta.md)
 - [CustomWorkflowsListingOutputRep](docs/CustomWorkflowsListingOutputRep.md)
 - [DefaultClientSideAvailabilityPost](docs/DefaultClientSideAvailabilityPost.md)
 - [Defaults](docs/Defaults.md)
 - [DependentFlag](docs/DependentFlag.md)
 - [DependentFlagEnvironment](docs/DependentFlagEnvironment.md)
 - [DependentFlagsByEnvironment](docs/DependentFlagsByEnvironment.md)
 - [DerivedAttribute](docs/DerivedAttribute.md)
 - [Destination](docs/Destination.md)
 - [DestinationPost](docs/DestinationPost.md)
 - [Destinations](docs/Destinations.md)
 - [Environment](docs/Environment.md)
 - [EnvironmentPost](docs/EnvironmentPost.md)
 - [ExecutionOutputRep](docs/ExecutionOutputRep.md)
 - [ExperimentAllocationRep](docs/ExperimentAllocationRep.md)
 - [ExperimentEnabledPeriodRep](docs/ExperimentEnabledPeriodRep.md)
 - [ExperimentEnvironmentSettingRep](docs/ExperimentEnvironmentSettingRep.md)
 - [ExperimentInfoRep](docs/ExperimentInfoRep.md)
 - [ExperimentMetadataRep](docs/ExperimentMetadataRep.md)
 - [ExperimentRep](docs/ExperimentRep.md)
 - [ExperimentResultsRep](docs/ExperimentResultsRep.md)
 - [ExperimentStatsRep](docs/ExperimentStatsRep.md)
 - [ExperimentTimeSeriesSlice](docs/ExperimentTimeSeriesSlice.md)
 - [ExperimentTimeSeriesVariationSlice](docs/ExperimentTimeSeriesVariationSlice.md)
 - [ExperimentTotalsRep](docs/ExperimentTotalsRep.md)
 - [ExpiringUserTargetError](docs/ExpiringUserTargetError.md)
 - [ExpiringUserTargetGetResponse](docs/ExpiringUserTargetGetResponse.md)
 - [ExpiringUserTargetItem](docs/ExpiringUserTargetItem.md)
 - [ExpiringUserTargetPatchResponse](docs/ExpiringUserTargetPatchResponse.md)
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
 - [FlagConfigApprovalRequestResponse](docs/FlagConfigApprovalRequestResponse.md)
 - [FlagConfigApprovalRequestsResponse](docs/FlagConfigApprovalRequestsResponse.md)
 - [FlagCopyConfigEnvironment](docs/FlagCopyConfigEnvironment.md)
 - [FlagCopyConfigPost](docs/FlagCopyConfigPost.md)
 - [FlagGlobalAttributesRep](docs/FlagGlobalAttributesRep.md)
 - [FlagListingRep](docs/FlagListingRep.md)
 - [FlagScheduledChangesInput](docs/FlagScheduledChangesInput.md)
 - [FlagStatusRep](docs/FlagStatusRep.md)
 - [FlagSummary](docs/FlagSummary.md)
 - [ForbiddenErrorRep](docs/ForbiddenErrorRep.md)
 - [HunkRep](docs/HunkRep.md)
 - [IntegrationMetadata](docs/IntegrationMetadata.md)
 - [IntegrationStatus](docs/IntegrationStatus.md)
 - [InvalidRequestErrorRep](docs/InvalidRequestErrorRep.md)
 - [IpList](docs/IpList.md)
 - [LastSeenMetadata](docs/LastSeenMetadata.md)
 - [Link](docs/Link.md)
 - [Member](docs/Member.md)
 - [MemberDataRep](docs/MemberDataRep.md)
 - [MemberPermissionGrantSummaryRep](docs/MemberPermissionGrantSummaryRep.md)
 - [MemberSummaryRep](docs/MemberSummaryRep.md)
 - [MemberTeamSummaryRep](docs/MemberTeamSummaryRep.md)
 - [Members](docs/Members.md)
 - [MethodNotAllowedErrorRep](docs/MethodNotAllowedErrorRep.md)
 - [MetricCollectionRep](docs/MetricCollectionRep.md)
 - [MetricListingRep](docs/MetricListingRep.md)
 - [MetricPost](docs/MetricPost.md)
 - [MetricRep](docs/MetricRep.md)
 - [MetricSeen](docs/MetricSeen.md)
 - [Modification](docs/Modification.md)
 - [MultiEnvironmentDependentFlag](docs/MultiEnvironmentDependentFlag.md)
 - [MultiEnvironmentDependentFlags](docs/MultiEnvironmentDependentFlags.md)
 - [NewMemberForm](docs/NewMemberForm.md)
 - [NotFoundErrorRep](docs/NotFoundErrorRep.md)
 - [ParentResourceRep](docs/ParentResourceRep.md)
 - [PatchFailedErrorRep](docs/PatchFailedErrorRep.md)
 - [PatchOperation](docs/PatchOperation.md)
 - [PatchSegmentInstruction](docs/PatchSegmentInstruction.md)
 - [PatchSegmentRequest](docs/PatchSegmentRequest.md)
 - [PatchWithComment](docs/PatchWithComment.md)
 - [PermissionGrantInput](docs/PermissionGrantInput.md)
 - [PermissionGrantRep](docs/PermissionGrantRep.md)
 - [PostApprovalRequestApplyRequest](docs/PostApprovalRequestApplyRequest.md)
 - [PostApprovalRequestReviewRequest](docs/PostApprovalRequestReviewRequest.md)
 - [PostFlagScheduledChangesInput](docs/PostFlagScheduledChangesInput.md)
 - [Prerequisite](docs/Prerequisite.md)
 - [Project](docs/Project.md)
 - [ProjectListingRep](docs/ProjectListingRep.md)
 - [ProjectPost](docs/ProjectPost.md)
 - [Projects](docs/Projects.md)
 - [PubNubDetailRep](docs/PubNubDetailRep.md)
 - [PutBranch](docs/PutBranch.md)
 - [RateLimitedErrorRep](docs/RateLimitedErrorRep.md)
 - [ReferenceRep](docs/ReferenceRep.md)
 - [RelayAutoConfigCollectionRep](docs/RelayAutoConfigCollectionRep.md)
 - [RelayAutoConfigPost](docs/RelayAutoConfigPost.md)
 - [RelayAutoConfigRep](docs/RelayAutoConfigRep.md)
 - [RepositoryCollectionRep](docs/RepositoryCollectionRep.md)
 - [RepositoryPost](docs/RepositoryPost.md)
 - [RepositoryRep](docs/RepositoryRep.md)
 - [ResourceAccess](docs/ResourceAccess.md)
 - [ResourceIDResponse](docs/ResourceIDResponse.md)
 - [ReviewOutputRep](docs/ReviewOutputRep.md)
 - [ReviewResponse](docs/ReviewResponse.md)
 - [Rollout](docs/Rollout.md)
 - [Rule](docs/Rule.md)
 - [ScheduleConditionInputRep](docs/ScheduleConditionInputRep.md)
 - [ScheduleConditionOutputRep](docs/ScheduleConditionOutputRep.md)
 - [SdkListRep](docs/SdkListRep.md)
 - [SdkVersionListRep](docs/SdkVersionListRep.md)
 - [SdkVersionRep](docs/SdkVersionRep.md)
 - [SegmentBody](docs/SegmentBody.md)
 - [SegmentMetadata](docs/SegmentMetadata.md)
 - [SegmentUserList](docs/SegmentUserList.md)
 - [SegmentUserState](docs/SegmentUserState.md)
 - [SeriesListRep](docs/SeriesListRep.md)
 - [SourceFlag](docs/SourceFlag.md)
 - [StageInputRep](docs/StageInputRep.md)
 - [StageOutputRep](docs/StageOutputRep.md)
 - [Statement](docs/Statement.md)
 - [StatementPost](docs/StatementPost.md)
 - [StatementPostData](docs/StatementPostData.md)
 - [StatementRep](docs/StatementRep.md)
 - [StatisticCollectionRep](docs/StatisticCollectionRep.md)
 - [StatisticRep](docs/StatisticRep.md)
 - [StatisticsRoot](docs/StatisticsRoot.md)
 - [StatusConflictErrorRep](docs/StatusConflictErrorRep.md)
 - [SubjectDataRep](docs/SubjectDataRep.md)
 - [Target](docs/Target.md)
 - [TargetResourceRep](docs/TargetResourceRep.md)
 - [TeamCollectionRep](docs/TeamCollectionRep.md)
 - [TeamPatchInput](docs/TeamPatchInput.md)
 - [TeamPostInput](docs/TeamPostInput.md)
 - [TeamRep](docs/TeamRep.md)
 - [TitleRep](docs/TitleRep.md)
 - [Token](docs/Token.md)
 - [TokenDataRep](docs/TokenDataRep.md)
 - [Tokens](docs/Tokens.md)
 - [UnauthorizedErrorRep](docs/UnauthorizedErrorRep.md)
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
 - [ValuePut](docs/ValuePut.md)
 - [Variation](docs/Variation.md)
 - [VariationOrRolloutRep](docs/VariationOrRolloutRep.md)
 - [VariationSummary](docs/VariationSummary.md)
 - [VersionsRep](docs/VersionsRep.md)
 - [Webhook](docs/Webhook.md)
 - [WebhookPost](docs/WebhookPost.md)
 - [Webhooks](docs/Webhooks.md)
 - [WeightedVariation](docs/WeightedVariation.md)


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

