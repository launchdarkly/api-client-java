# DataExportDestinationsApi

All URIs are relative to *https://app.launchdarkly.com/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteDestination**](DataExportDestinationsApi.md#deleteDestination) | **DELETE** /destinations/{projectKey}/{environmentKey}/{destinationId} | Get a single data export destination by ID
[**getDestination**](DataExportDestinationsApi.md#getDestination) | **GET** /destinations/{projectKey}/{environmentKey}/{destinationId} | Get a single data export destination by ID
[**getDestinations**](DataExportDestinationsApi.md#getDestinations) | **GET** /destinations | Returns a list of all data export destinations.
[**patchDestination**](DataExportDestinationsApi.md#patchDestination) | **PATCH** /destinations/{projectKey}/{environmentKey}/{destinationId} | Perform a partial update to a data export destination.
[**postDestination**](DataExportDestinationsApi.md#postDestination) | **POST** /destinations/{projectKey}/{environmentKey} | Create a new data export destination


<a name="deleteDestination"></a>
# **deleteDestination**
> deleteDestination(projectKey, environmentKey, destinationId)

Get a single data export destination by ID

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.DataExportDestinationsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

DataExportDestinationsApi apiInstance = new DataExportDestinationsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
String destinationId = "destinationId_example"; // String | The data export destination ID.
try {
    apiInstance.deleteDestination(projectKey, environmentKey, destinationId);
} catch (ApiException e) {
    System.err.println("Exception when calling DataExportDestinationsApi#deleteDestination");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **destinationId** | **String**| The data export destination ID. |

### Return type

null (empty response body)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getDestination"></a>
# **getDestination**
> Destination getDestination(projectKey, environmentKey, destinationId)

Get a single data export destination by ID

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.DataExportDestinationsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

DataExportDestinationsApi apiInstance = new DataExportDestinationsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
String destinationId = "destinationId_example"; // String | The data export destination ID.
try {
    Destination result = apiInstance.getDestination(projectKey, environmentKey, destinationId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DataExportDestinationsApi#getDestination");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **destinationId** | **String**| The data export destination ID. |

### Return type

[**Destination**](Destination.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getDestinations"></a>
# **getDestinations**
> Destinations getDestinations()

Returns a list of all data export destinations.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.DataExportDestinationsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

DataExportDestinationsApi apiInstance = new DataExportDestinationsApi();
try {
    Destinations result = apiInstance.getDestinations();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DataExportDestinationsApi#getDestinations");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**Destinations**](Destinations.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="patchDestination"></a>
# **patchDestination**
> Destination patchDestination(projectKey, environmentKey, destinationId, patchOnly)

Perform a partial update to a data export destination.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.DataExportDestinationsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

DataExportDestinationsApi apiInstance = new DataExportDestinationsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
String destinationId = "destinationId_example"; // String | The data export destination ID.
List<PatchOperation> patchOnly = Arrays.asList(new PatchOperation()); // List<PatchOperation> | Requires a JSON Patch representation of the desired changes to the project. 'http://jsonpatch.com/' Feature flag patches also support JSON Merge Patch format. 'https://tools.ietf.org/html/rfc7386' The addition of comments is also supported.
try {
    Destination result = apiInstance.patchDestination(projectKey, environmentKey, destinationId, patchOnly);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DataExportDestinationsApi#patchDestination");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **destinationId** | **String**| The data export destination ID. |
 **patchOnly** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)| Requires a JSON Patch representation of the desired changes to the project. &#39;http://jsonpatch.com/&#39; Feature flag patches also support JSON Merge Patch format. &#39;https://tools.ietf.org/html/rfc7386&#39; The addition of comments is also supported. |

### Return type

[**Destination**](Destination.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="postDestination"></a>
# **postDestination**
> Destination postDestination(projectKey, environmentKey, destinationBody)

Create a new data export destination

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.DataExportDestinationsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

DataExportDestinationsApi apiInstance = new DataExportDestinationsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
DestinationBody destinationBody = new DestinationBody(); // DestinationBody | Create a new data export destination.
try {
    Destination result = apiInstance.postDestination(projectKey, environmentKey, destinationBody);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DataExportDestinationsApi#postDestination");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **destinationBody** | [**DestinationBody**](DestinationBody.md)| Create a new data export destination. |

### Return type

[**Destination**](Destination.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

