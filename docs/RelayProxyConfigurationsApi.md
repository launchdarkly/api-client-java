# RelayProxyConfigurationsApi

All URIs are relative to *https://app.launchdarkly.com/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteRelayProxyConfig**](RelayProxyConfigurationsApi.md#deleteRelayProxyConfig) | **DELETE** /account/relay-auto-configs/{id} | Delete a relay proxy configuration by ID.
[**getRelayProxyConfig**](RelayProxyConfigurationsApi.md#getRelayProxyConfig) | **GET** /account/relay-auto-configs/{id} | Get a single relay proxy configuration by ID.
[**getRelayProxyConfigs**](RelayProxyConfigurationsApi.md#getRelayProxyConfigs) | **GET** /account/relay-auto-configs | Returns a list of relay proxy configurations in the account.
[**patchRelayProxyConfig**](RelayProxyConfigurationsApi.md#patchRelayProxyConfig) | **PATCH** /account/relay-auto-configs/{id} | Modify a relay proxy configuration by ID.
[**postRelayAutoConfig**](RelayProxyConfigurationsApi.md#postRelayAutoConfig) | **POST** /account/relay-auto-configs | Create a new relay proxy config.
[**resetRelayProxyConfig**](RelayProxyConfigurationsApi.md#resetRelayProxyConfig) | **POST** /account/relay-auto-configs/{id}/reset | Reset a relay proxy configuration&#39;s secret key with an optional expiry time for the old key.


<a name="deleteRelayProxyConfig"></a>
# **deleteRelayProxyConfig**
> deleteRelayProxyConfig(id)

Delete a relay proxy configuration by ID.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.RelayProxyConfigurationsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

RelayProxyConfigurationsApi apiInstance = new RelayProxyConfigurationsApi();
String id = "id_example"; // String | The relay proxy configuration ID
try {
    apiInstance.deleteRelayProxyConfig(id);
} catch (ApiException e) {
    System.err.println("Exception when calling RelayProxyConfigurationsApi#deleteRelayProxyConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The relay proxy configuration ID |

### Return type

null (empty response body)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getRelayProxyConfig"></a>
# **getRelayProxyConfig**
> RelayProxyConfig getRelayProxyConfig(id)

Get a single relay proxy configuration by ID.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.RelayProxyConfigurationsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

RelayProxyConfigurationsApi apiInstance = new RelayProxyConfigurationsApi();
String id = "id_example"; // String | The relay proxy configuration ID
try {
    RelayProxyConfig result = apiInstance.getRelayProxyConfig(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RelayProxyConfigurationsApi#getRelayProxyConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The relay proxy configuration ID |

### Return type

[**RelayProxyConfig**](RelayProxyConfig.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getRelayProxyConfigs"></a>
# **getRelayProxyConfigs**
> RelayProxyConfigs getRelayProxyConfigs()

Returns a list of relay proxy configurations in the account.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.RelayProxyConfigurationsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

RelayProxyConfigurationsApi apiInstance = new RelayProxyConfigurationsApi();
try {
    RelayProxyConfigs result = apiInstance.getRelayProxyConfigs();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RelayProxyConfigurationsApi#getRelayProxyConfigs");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**RelayProxyConfigs**](RelayProxyConfigs.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="patchRelayProxyConfig"></a>
# **patchRelayProxyConfig**
> RelayProxyConfig patchRelayProxyConfig(id, patchDelta)

Modify a relay proxy configuration by ID.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.RelayProxyConfigurationsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

RelayProxyConfigurationsApi apiInstance = new RelayProxyConfigurationsApi();
String id = "id_example"; // String | The relay proxy configuration ID
List<PatchOperation> patchDelta = Arrays.asList(new PatchOperation()); // List<PatchOperation> | Requires a JSON Patch representation of the desired changes to the project. 'http://jsonpatch.com/'
try {
    RelayProxyConfig result = apiInstance.patchRelayProxyConfig(id, patchDelta);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RelayProxyConfigurationsApi#patchRelayProxyConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The relay proxy configuration ID |
 **patchDelta** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)| Requires a JSON Patch representation of the desired changes to the project. &#39;http://jsonpatch.com/&#39; |

### Return type

[**RelayProxyConfig**](RelayProxyConfig.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="postRelayAutoConfig"></a>
# **postRelayAutoConfig**
> RelayProxyConfig postRelayAutoConfig(relayProxyConfigBody)

Create a new relay proxy config.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.RelayProxyConfigurationsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

RelayProxyConfigurationsApi apiInstance = new RelayProxyConfigurationsApi();
RelayProxyConfigBody relayProxyConfigBody = new RelayProxyConfigBody(); // RelayProxyConfigBody | Create a new relay proxy configuration
try {
    RelayProxyConfig result = apiInstance.postRelayAutoConfig(relayProxyConfigBody);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RelayProxyConfigurationsApi#postRelayAutoConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **relayProxyConfigBody** | [**RelayProxyConfigBody**](RelayProxyConfigBody.md)| Create a new relay proxy configuration |

### Return type

[**RelayProxyConfig**](RelayProxyConfig.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="resetRelayProxyConfig"></a>
# **resetRelayProxyConfig**
> RelayProxyConfig resetRelayProxyConfig(id, expiry)

Reset a relay proxy configuration&#39;s secret key with an optional expiry time for the old key.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.RelayProxyConfigurationsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

RelayProxyConfigurationsApi apiInstance = new RelayProxyConfigurationsApi();
String id = "id_example"; // String | The relay proxy configuration ID
Long expiry = 789L; // Long | An expiration time for the old relay proxy configuration key, expressed as a Unix epoch time in milliseconds. By default, the relay proxy configuration will expire immediately
try {
    RelayProxyConfig result = apiInstance.resetRelayProxyConfig(id, expiry);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RelayProxyConfigurationsApi#resetRelayProxyConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The relay proxy configuration ID |
 **expiry** | **Long**| An expiration time for the old relay proxy configuration key, expressed as a Unix epoch time in milliseconds. By default, the relay proxy configuration will expire immediately | [optional]

### Return type

[**RelayProxyConfig**](RelayProxyConfig.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

