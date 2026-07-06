# IpAllowlistBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createIpAllowlistEntry**](IpAllowlistBetaApi.md#createIpAllowlistEntry) | **POST** /api/v2/account/ip-allowlist | Create IP Allowlist Entry |
| [**deleteIpAllowlistEntry**](IpAllowlistBetaApi.md#deleteIpAllowlistEntry) | **DELETE** /api/v2/account/ip-allowlist/{id} | Delete IP Allowlist Entry |
| [**getIpAllowlist**](IpAllowlistBetaApi.md#getIpAllowlist) | **GET** /api/v2/account/ip-allowlist | Get IP Allowlist |
| [**patchIpAllowlistConfig**](IpAllowlistBetaApi.md#patchIpAllowlistConfig) | **PATCH** /api/v2/account/ip-allowlist | Update IP Allowlist Configuration |
| [**patchIpAllowlistEntry**](IpAllowlistBetaApi.md#patchIpAllowlistEntry) | **PATCH** /api/v2/account/ip-allowlist/{id} | Update IP Allowlist Entry Description |


<a id="createIpAllowlistEntry"></a>
# **createIpAllowlistEntry**
> IpAllowlistEntryResponse createIpAllowlistEntry(createIpAllowlistEntryRequest)

Create IP Allowlist Entry

Create a new IP allowlist entry.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.IpAllowlistBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    IpAllowlistBetaApi apiInstance = new IpAllowlistBetaApi(defaultClient);
    CreateIpAllowlistEntryRequest createIpAllowlistEntryRequest = new CreateIpAllowlistEntryRequest(); // CreateIpAllowlistEntryRequest | 
    try {
      IpAllowlistEntryResponse result = apiInstance.createIpAllowlistEntry(createIpAllowlistEntryRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IpAllowlistBetaApi#createIpAllowlistEntry");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **createIpAllowlistEntryRequest** | [**CreateIpAllowlistEntryRequest**](CreateIpAllowlistEntryRequest.md)|  | |

### Return type

[**IpAllowlistEntryResponse**](IpAllowlistEntryResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | IP allowlist entry created |  -  |
| **400** | Bad request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **409** | Conflict |  -  |
| **500** | Internal server error |  -  |

<a id="deleteIpAllowlistEntry"></a>
# **deleteIpAllowlistEntry**
> deleteIpAllowlistEntry(id)

Delete IP Allowlist Entry

Delete an IP allowlist entry by id.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.IpAllowlistBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    IpAllowlistBetaApi apiInstance = new IpAllowlistBetaApi(defaultClient);
    String id = "id_example"; // String | Unique identifier for the allowlist entry
    try {
      apiInstance.deleteIpAllowlistEntry(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling IpAllowlistBetaApi#deleteIpAllowlistEntry");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **String**| Unique identifier for the allowlist entry | |

### Return type

null (empty response body)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | IP allowlist entry deleted successfully |  -  |
| **400** | Bad request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **409** | Conflict |  -  |
| **500** | Internal server error |  -  |

<a id="getIpAllowlist"></a>
# **getIpAllowlist**
> IpAllowlistResponse getIpAllowlist()

Get IP Allowlist

Get the current IP allowlist configuration and entries.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.IpAllowlistBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    IpAllowlistBetaApi apiInstance = new IpAllowlistBetaApi(defaultClient);
    try {
      IpAllowlistResponse result = apiInstance.getIpAllowlist();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IpAllowlistBetaApi#getIpAllowlist");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**IpAllowlistResponse**](IpAllowlistResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | IP allowlist configuration and entries |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **500** | Internal server error |  -  |

<a id="patchIpAllowlistConfig"></a>
# **patchIpAllowlistConfig**
> IpAllowlistResponse patchIpAllowlistConfig(patchIpAllowlistConfigRequest)

Update IP Allowlist Configuration

Update sessionAllowlistEnabled and apiTokenAllowlistEnabled settings.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.IpAllowlistBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    IpAllowlistBetaApi apiInstance = new IpAllowlistBetaApi(defaultClient);
    PatchIpAllowlistConfigRequest patchIpAllowlistConfigRequest = new PatchIpAllowlistConfigRequest(); // PatchIpAllowlistConfigRequest | 
    try {
      IpAllowlistResponse result = apiInstance.patchIpAllowlistConfig(patchIpAllowlistConfigRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IpAllowlistBetaApi#patchIpAllowlistConfig");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **patchIpAllowlistConfigRequest** | [**PatchIpAllowlistConfigRequest**](PatchIpAllowlistConfigRequest.md)|  | |

### Return type

[**IpAllowlistResponse**](IpAllowlistResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Updated IP allowlist configuration and entries |  -  |
| **400** | Bad request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **409** | Conflict |  -  |
| **500** | Internal server error |  -  |

<a id="patchIpAllowlistEntry"></a>
# **patchIpAllowlistEntry**
> IpAllowlistEntryResponse patchIpAllowlistEntry(id, patchIpAllowlistEntryRequest)

Update IP Allowlist Entry Description

Update the description of an IP allowlist entry.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.IpAllowlistBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    IpAllowlistBetaApi apiInstance = new IpAllowlistBetaApi(defaultClient);
    String id = "id_example"; // String | Unique identifier for the allowlist entry
    PatchIpAllowlistEntryRequest patchIpAllowlistEntryRequest = new PatchIpAllowlistEntryRequest(); // PatchIpAllowlistEntryRequest | 
    try {
      IpAllowlistEntryResponse result = apiInstance.patchIpAllowlistEntry(id, patchIpAllowlistEntryRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IpAllowlistBetaApi#patchIpAllowlistEntry");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **String**| Unique identifier for the allowlist entry | |
| **patchIpAllowlistEntryRequest** | [**PatchIpAllowlistEntryRequest**](PatchIpAllowlistEntryRequest.md)|  | |

### Return type

[**IpAllowlistEntryResponse**](IpAllowlistEntryResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Updated IP allowlist entry |  -  |
| **400** | Bad request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

