# AccessTokensApi

All URIs are relative to *https://app.launchdarkly.com/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteToken**](AccessTokensApi.md#deleteToken) | **DELETE** /tokens/{tokenId} | Delete an access token by ID.
[**getToken**](AccessTokensApi.md#getToken) | **GET** /tokens/{tokenId} | Get a single access token by ID.
[**getTokens**](AccessTokensApi.md#getTokens) | **GET** /tokens | Returns a list of tokens in the account.
[**patchToken**](AccessTokensApi.md#patchToken) | **PATCH** /tokens/{tokenId} | Modify an access token by ID.
[**postToken**](AccessTokensApi.md#postToken) | **POST** /tokens | Create a new token.
[**resetToken**](AccessTokensApi.md#resetToken) | **POST** /tokens/{tokenId}/reset | Reset an access token&#39;s secret key with an optional expiry time for the old key.


<a name="deleteToken"></a>
# **deleteToken**
> deleteToken(tokenId)

Delete an access token by ID.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.AccessTokensApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

AccessTokensApi apiInstance = new AccessTokensApi();
String tokenId = "tokenId_example"; // String | The access token ID.
try {
    apiInstance.deleteToken(tokenId);
} catch (ApiException e) {
    System.err.println("Exception when calling AccessTokensApi#deleteToken");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **tokenId** | **String**| The access token ID. |

### Return type

null (empty response body)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getToken"></a>
# **getToken**
> Token getToken(tokenId)

Get a single access token by ID.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.AccessTokensApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

AccessTokensApi apiInstance = new AccessTokensApi();
String tokenId = "tokenId_example"; // String | The access token ID.
try {
    Token result = apiInstance.getToken(tokenId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AccessTokensApi#getToken");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **tokenId** | **String**| The access token ID. |

### Return type

[**Token**](Token.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getTokens"></a>
# **getTokens**
> Tokens getTokens(showAll)

Returns a list of tokens in the account.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.AccessTokensApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

AccessTokensApi apiInstance = new AccessTokensApi();
Boolean showAll = true; // Boolean | If set to true, and the authentication access token has the \"Admin\" role, personal access tokens for all members will be retrieved.
try {
    Tokens result = apiInstance.getTokens(showAll);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AccessTokensApi#getTokens");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **showAll** | **Boolean**| If set to true, and the authentication access token has the \&quot;Admin\&quot; role, personal access tokens for all members will be retrieved. | [optional]

### Return type

[**Tokens**](Tokens.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="patchToken"></a>
# **patchToken**
> Token patchToken(tokenId, patchDelta)

Modify an access token by ID.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.AccessTokensApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

AccessTokensApi apiInstance = new AccessTokensApi();
String tokenId = "tokenId_example"; // String | The access token ID.
List<PatchOperation> patchDelta = Arrays.asList(new PatchOperation()); // List<PatchOperation> | Requires a JSON Patch representation of the desired changes to the project. 'http://jsonpatch.com/'
try {
    Token result = apiInstance.patchToken(tokenId, patchDelta);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AccessTokensApi#patchToken");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **tokenId** | **String**| The access token ID. |
 **patchDelta** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)| Requires a JSON Patch representation of the desired changes to the project. &#39;http://jsonpatch.com/&#39; |

### Return type

[**Token**](Token.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="postToken"></a>
# **postToken**
> Token postToken(tokenBody)

Create a new token.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.AccessTokensApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

AccessTokensApi apiInstance = new AccessTokensApi();
TokenBody tokenBody = new TokenBody(); // TokenBody | Create a new access token.
try {
    Token result = apiInstance.postToken(tokenBody);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AccessTokensApi#postToken");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **tokenBody** | [**TokenBody**](TokenBody.md)| Create a new access token. |

### Return type

[**Token**](Token.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="resetToken"></a>
# **resetToken**
> Token resetToken(tokenId, expiry)

Reset an access token&#39;s secret key with an optional expiry time for the old key.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.AccessTokensApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

AccessTokensApi apiInstance = new AccessTokensApi();
String tokenId = "tokenId_example"; // String | The access token ID.
Long expiry = 789L; // Long | An expiration time for the old token key, expressed as a Unix epoch time in milliseconds. By default, the token will expire immediately.
try {
    Token result = apiInstance.resetToken(tokenId, expiry);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AccessTokensApi#resetToken");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **tokenId** | **String**| The access token ID. |
 **expiry** | **Long**| An expiration time for the old token key, expressed as a Unix epoch time in milliseconds. By default, the token will expire immediately. | [optional]

### Return type

[**Token**](Token.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

