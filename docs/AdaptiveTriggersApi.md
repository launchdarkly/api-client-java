# AdaptiveTriggersApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteAdaptiveTrigger**](AdaptiveTriggersApi.md#deleteAdaptiveTrigger) | **DELETE** /api/v2/projects/{projectKey}/environments/{environmentKey}/adaptive-triggers/{triggerID} | Delete an adaptive trigger |
| [**getAdaptiveTrigger**](AdaptiveTriggersApi.md#getAdaptiveTrigger) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/adaptive-triggers/{triggerID} | Get an adaptive trigger |
| [**listAdaptiveTriggers**](AdaptiveTriggersApi.md#listAdaptiveTriggers) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/adaptive-triggers | List adaptive triggers |
| [**patchAdaptiveTrigger**](AdaptiveTriggersApi.md#patchAdaptiveTrigger) | **PATCH** /api/v2/projects/{projectKey}/environments/{environmentKey}/adaptive-triggers/{triggerID} | Update an adaptive trigger |
| [**postAdaptiveTrigger**](AdaptiveTriggersApi.md#postAdaptiveTrigger) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/adaptive-triggers | Create an adaptive trigger |
| [**postDisableAdaptiveTrigger**](AdaptiveTriggersApi.md#postDisableAdaptiveTrigger) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/adaptive-triggers/{triggerID}/disable | Disable an adaptive trigger |
| [**postEnableAdaptiveTrigger**](AdaptiveTriggersApi.md#postEnableAdaptiveTrigger) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/adaptive-triggers/{triggerID}/enable | Enable an adaptive trigger |


<a id="deleteAdaptiveTrigger"></a>
# **deleteAdaptiveTrigger**
> deleteAdaptiveTrigger(projectKey, environmentKey, triggerID)

Delete an adaptive trigger

Delete a single adaptive trigger.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AdaptiveTriggersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AdaptiveTriggersApi apiInstance = new AdaptiveTriggersApi(defaultClient);
    String projectKey = "projectKey_example"; // String | 
    String environmentKey = "environmentKey_example"; // String | 
    UUID triggerID = UUID.randomUUID(); // UUID | 
    try {
      apiInstance.deleteAdaptiveTrigger(projectKey, environmentKey, triggerID);
    } catch (ApiException e) {
      System.err.println("Exception when calling AdaptiveTriggersApi#deleteAdaptiveTrigger");
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
| **projectKey** | **String**|  | |
| **environmentKey** | **String**|  | |
| **triggerID** | **UUID**|  | |

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
| **204** | No content |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **429** | Rate Limited |  -  |
| **500** | Internal server error |  -  |

<a id="getAdaptiveTrigger"></a>
# **getAdaptiveTrigger**
> AdaptiveTrigger getAdaptiveTrigger(projectKey, environmentKey, triggerID)

Get an adaptive trigger

Get a single adaptive trigger.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AdaptiveTriggersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AdaptiveTriggersApi apiInstance = new AdaptiveTriggersApi(defaultClient);
    String projectKey = "projectKey_example"; // String | 
    String environmentKey = "environmentKey_example"; // String | 
    UUID triggerID = UUID.randomUUID(); // UUID | 
    try {
      AdaptiveTrigger result = apiInstance.getAdaptiveTrigger(projectKey, environmentKey, triggerID);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AdaptiveTriggersApi#getAdaptiveTrigger");
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
| **projectKey** | **String**|  | |
| **environmentKey** | **String**|  | |
| **triggerID** | **UUID**|  | |

### Return type

[**AdaptiveTrigger**](AdaptiveTrigger.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Trigger |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **429** | Rate Limited |  -  |
| **500** | Internal server error |  -  |

<a id="listAdaptiveTriggers"></a>
# **listAdaptiveTriggers**
> AdaptiveTriggers listAdaptiveTriggers(projectKey, environmentKey, resourceKind, resourceKey, limit, offset)

List adaptive triggers

List all adaptive triggers for a resource.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AdaptiveTriggersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AdaptiveTriggersApi apiInstance = new AdaptiveTriggersApi(defaultClient);
    String projectKey = "projectKey_example"; // String | 
    String environmentKey = "environmentKey_example"; // String | 
    AdaptiveTriggerResourceKind resourceKind = AdaptiveTriggerResourceKind.fromValue("flag"); // AdaptiveTriggerResourceKind | 
    String resourceKey = "resourceKey_example"; // String | 
    Integer limit = 25; // Integer | The number of items to return.
    Integer offset = 0; // Integer | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    try {
      AdaptiveTriggers result = apiInstance.listAdaptiveTriggers(projectKey, environmentKey, resourceKind, resourceKey, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AdaptiveTriggersApi#listAdaptiveTriggers");
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
| **projectKey** | **String**|  | |
| **environmentKey** | **String**|  | |
| **resourceKind** | [**AdaptiveTriggerResourceKind**](.md)|  | [enum: flag, ai-config] |
| **resourceKey** | **String**|  | |
| **limit** | **Integer**| The number of items to return. | [optional] [default to 25] |
| **offset** | **Integer**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] [default to 0] |

### Return type

[**AdaptiveTriggers**](AdaptiveTriggers.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Adaptive Triggers list |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **429** | Rate Limited |  -  |
| **500** | Internal server error |  -  |

<a id="patchAdaptiveTrigger"></a>
# **patchAdaptiveTrigger**
> AdaptiveTrigger patchAdaptiveTrigger(projectKey, environmentKey, triggerID, updateAdaptiveTriggerRequest)

Update an adaptive trigger

Update a single adaptive trigger.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AdaptiveTriggersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AdaptiveTriggersApi apiInstance = new AdaptiveTriggersApi(defaultClient);
    String projectKey = "projectKey_example"; // String | 
    String environmentKey = "environmentKey_example"; // String | 
    UUID triggerID = UUID.randomUUID(); // UUID | 
    UpdateAdaptiveTriggerRequest updateAdaptiveTriggerRequest = new UpdateAdaptiveTriggerRequest(); // UpdateAdaptiveTriggerRequest | 
    try {
      AdaptiveTrigger result = apiInstance.patchAdaptiveTrigger(projectKey, environmentKey, triggerID, updateAdaptiveTriggerRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AdaptiveTriggersApi#patchAdaptiveTrigger");
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
| **projectKey** | **String**|  | |
| **environmentKey** | **String**|  | |
| **triggerID** | **UUID**|  | |
| **updateAdaptiveTriggerRequest** | [**UpdateAdaptiveTriggerRequest**](UpdateAdaptiveTriggerRequest.md)|  | |

### Return type

[**AdaptiveTrigger**](AdaptiveTrigger.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Adaptive Trigger updated |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **429** | Rate Limited |  -  |
| **500** | Internal server error |  -  |

<a id="postAdaptiveTrigger"></a>
# **postAdaptiveTrigger**
> AdaptiveTrigger postAdaptiveTrigger(projectKey, environmentKey, createAdaptiveTriggerRequest)

Create an adaptive trigger

Create a new adaptive trigger for a resource.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AdaptiveTriggersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AdaptiveTriggersApi apiInstance = new AdaptiveTriggersApi(defaultClient);
    String projectKey = "projectKey_example"; // String | 
    String environmentKey = "environmentKey_example"; // String | 
    CreateAdaptiveTriggerRequest createAdaptiveTriggerRequest = new CreateAdaptiveTriggerRequest(); // CreateAdaptiveTriggerRequest | 
    try {
      AdaptiveTrigger result = apiInstance.postAdaptiveTrigger(projectKey, environmentKey, createAdaptiveTriggerRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AdaptiveTriggersApi#postAdaptiveTrigger");
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
| **projectKey** | **String**|  | |
| **environmentKey** | **String**|  | |
| **createAdaptiveTriggerRequest** | [**CreateAdaptiveTriggerRequest**](CreateAdaptiveTriggerRequest.md)|  | |

### Return type

[**AdaptiveTrigger**](AdaptiveTrigger.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Adaptive Trigger created |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **409** | Conflict |  -  |
| **429** | Rate Limited |  -  |
| **500** | Internal server error |  -  |

<a id="postDisableAdaptiveTrigger"></a>
# **postDisableAdaptiveTrigger**
> postDisableAdaptiveTrigger(projectKey, environmentKey, triggerID)

Disable an adaptive trigger

Disable an adaptive trigger.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AdaptiveTriggersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AdaptiveTriggersApi apiInstance = new AdaptiveTriggersApi(defaultClient);
    String projectKey = "projectKey_example"; // String | 
    String environmentKey = "environmentKey_example"; // String | 
    UUID triggerID = UUID.randomUUID(); // UUID | 
    try {
      apiInstance.postDisableAdaptiveTrigger(projectKey, environmentKey, triggerID);
    } catch (ApiException e) {
      System.err.println("Exception when calling AdaptiveTriggersApi#postDisableAdaptiveTrigger");
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
| **projectKey** | **String**|  | |
| **environmentKey** | **String**|  | |
| **triggerID** | **UUID**|  | |

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
| **204** | No content |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **429** | Rate Limited |  -  |
| **500** | Internal server error |  -  |

<a id="postEnableAdaptiveTrigger"></a>
# **postEnableAdaptiveTrigger**
> postEnableAdaptiveTrigger(projectKey, environmentKey, triggerID)

Enable an adaptive trigger

Enable an adaptive trigger.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AdaptiveTriggersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AdaptiveTriggersApi apiInstance = new AdaptiveTriggersApi(defaultClient);
    String projectKey = "projectKey_example"; // String | 
    String environmentKey = "environmentKey_example"; // String | 
    UUID triggerID = UUID.randomUUID(); // UUID | 
    try {
      apiInstance.postEnableAdaptiveTrigger(projectKey, environmentKey, triggerID);
    } catch (ApiException e) {
      System.err.println("Exception when calling AdaptiveTriggersApi#postEnableAdaptiveTrigger");
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
| **projectKey** | **String**|  | |
| **environmentKey** | **String**|  | |
| **triggerID** | **UUID**|  | |

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
| **204** | No content |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **429** | Rate Limited |  -  |
| **500** | Internal server error |  -  |

