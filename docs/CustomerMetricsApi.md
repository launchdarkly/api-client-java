# CustomerMetricsApi

All URIs are relative to *https://app.launchdarkly.com/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getEvaluations**](CustomerMetricsApi.md#getEvaluations) | **GET** /usage/evaluations/{envId}/{flagKey} | Get events usage by event id and the feature flag key.
[**getEvent**](CustomerMetricsApi.md#getEvent) | **GET** /usage/events/{type} | Get events usage by event type.
[**getEvents**](CustomerMetricsApi.md#getEvents) | **GET** /usage/events | Get events usage endpoints.
[**getMAU**](CustomerMetricsApi.md#getMAU) | **GET** /usage/mau | Get monthly active user data.
[**getMAUByCategory**](CustomerMetricsApi.md#getMAUByCategory) | **GET** /usage/mau/bycategory | Get monthly active user data by category.
[**getStream**](CustomerMetricsApi.md#getStream) | **GET** /usage/streams/{source} | Get a stream endpoint and return timeseries data.
[**getStreamBySDK**](CustomerMetricsApi.md#getStreamBySDK) | **GET** /usage/streams/{source}/bysdkversion | Get a stream timeseries data by source show sdk version metadata.
[**getStreamSDKVersion**](CustomerMetricsApi.md#getStreamSDKVersion) | **GET** /usage/streams/{source}/sdkversions | Get a stream timeseries data by source and show all sdk version associated.
[**getStreams**](CustomerMetricsApi.md#getStreams) | **GET** /usage/streams | Returns a list of all streams.
[**getUsage**](CustomerMetricsApi.md#getUsage) | **GET** /usage | Returns of the usage endpoints available.


<a name="getEvaluations"></a>
# **getEvaluations**
> StreamSDKVersion getEvaluations(envId, flagKey)

Get events usage by event id and the feature flag key.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.CustomerMetricsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

CustomerMetricsApi apiInstance = new CustomerMetricsApi();
String envId = "envId_example"; // String | The environment id for the flag evaluations in question.
String flagKey = "flagKey_example"; // String | The key of the flag we want metrics for.
try {
    StreamSDKVersion result = apiInstance.getEvaluations(envId, flagKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomerMetricsApi#getEvaluations");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **envId** | **String**| The environment id for the flag evaluations in question. |
 **flagKey** | **String**| The key of the flag we want metrics for. |

### Return type

[**StreamSDKVersion**](StreamSDKVersion.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getEvent"></a>
# **getEvent**
> StreamSDKVersion getEvent(type)

Get events usage by event type.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.CustomerMetricsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

CustomerMetricsApi apiInstance = new CustomerMetricsApi();
String type = "type_example"; // String | The type of event we would like to track.
try {
    StreamSDKVersion result = apiInstance.getEvent(type);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomerMetricsApi#getEvent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **type** | **String**| The type of event we would like to track. |

### Return type

[**StreamSDKVersion**](StreamSDKVersion.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getEvents"></a>
# **getEvents**
> Events getEvents()

Get events usage endpoints.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.CustomerMetricsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

CustomerMetricsApi apiInstance = new CustomerMetricsApi();
try {
    Events result = apiInstance.getEvents();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomerMetricsApi#getEvents");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**Events**](Events.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMAU"></a>
# **getMAU**
> MAU getMAU()

Get monthly active user data.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.CustomerMetricsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

CustomerMetricsApi apiInstance = new CustomerMetricsApi();
try {
    MAU result = apiInstance.getMAU();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomerMetricsApi#getMAU");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**MAU**](MAU.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMAUByCategory"></a>
# **getMAUByCategory**
> MAUbyCategory getMAUByCategory()

Get monthly active user data by category.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.CustomerMetricsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

CustomerMetricsApi apiInstance = new CustomerMetricsApi();
try {
    MAUbyCategory result = apiInstance.getMAUByCategory();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomerMetricsApi#getMAUByCategory");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**MAUbyCategory**](MAUbyCategory.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getStream"></a>
# **getStream**
> Stream getStream(source)

Get a stream endpoint and return timeseries data.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.CustomerMetricsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

CustomerMetricsApi apiInstance = new CustomerMetricsApi();
String source = "source_example"; // String | The source of where the stream comes from.
try {
    Stream result = apiInstance.getStream(source);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomerMetricsApi#getStream");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **source** | **String**| The source of where the stream comes from. |

### Return type

[**Stream**](Stream.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getStreamBySDK"></a>
# **getStreamBySDK**
> StreamBySDK getStreamBySDK(source)

Get a stream timeseries data by source show sdk version metadata.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.CustomerMetricsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

CustomerMetricsApi apiInstance = new CustomerMetricsApi();
String source = "source_example"; // String | The source of where the stream comes from.
try {
    StreamBySDK result = apiInstance.getStreamBySDK(source);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomerMetricsApi#getStreamBySDK");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **source** | **String**| The source of where the stream comes from. |

### Return type

[**StreamBySDK**](StreamBySDK.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getStreamSDKVersion"></a>
# **getStreamSDKVersion**
> StreamSDKVersion getStreamSDKVersion(source)

Get a stream timeseries data by source and show all sdk version associated.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.CustomerMetricsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

CustomerMetricsApi apiInstance = new CustomerMetricsApi();
String source = "source_example"; // String | The source of where the stream comes from.
try {
    StreamSDKVersion result = apiInstance.getStreamSDKVersion(source);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomerMetricsApi#getStreamSDKVersion");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **source** | **String**| The source of where the stream comes from. |

### Return type

[**StreamSDKVersion**](StreamSDKVersion.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getStreams"></a>
# **getStreams**
> Streams getStreams()

Returns a list of all streams.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.CustomerMetricsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

CustomerMetricsApi apiInstance = new CustomerMetricsApi();
try {
    Streams result = apiInstance.getStreams();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomerMetricsApi#getStreams");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**Streams**](Streams.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUsage"></a>
# **getUsage**
> Usage getUsage()

Returns of the usage endpoints available.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.CustomerMetricsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

CustomerMetricsApi apiInstance = new CustomerMetricsApi();
try {
    Usage result = apiInstance.getUsage();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomerMetricsApi#getUsage");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**Usage**](Usage.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

