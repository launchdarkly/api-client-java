# SdkKeysBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteSdkKeyByKey**](SdkKeysBetaApi.md#deleteSdkKeyByKey) | **DELETE** /api/v2/projects/{projectKey}/environments/{environmentKey}/sdk-keys/{sdkKeyKey} | Delete SDK key |
| [**getSdkKeyByKey**](SdkKeysBetaApi.md#getSdkKeyByKey) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/sdk-keys/{sdkKeyKey} | Get SDK key |
| [**patchSdkKeyByKey**](SdkKeysBetaApi.md#patchSdkKeyByKey) | **PATCH** /api/v2/projects/{projectKey}/environments/{environmentKey}/sdk-keys/{sdkKeyKey} | Update SDK key |
| [**postSdkKey**](SdkKeysBetaApi.md#postSdkKey) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/sdk-keys | Create SDK key |


<a id="deleteSdkKeyByKey"></a>
# **deleteSdkKeyByKey**
> deleteSdkKeyByKey(ldAPIVersion, projectKey, environmentKey, sdkKeyKey)

Delete SDK key

Delete a specific SDK key by its key.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SdkKeysBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SdkKeysBetaApi apiInstance = new SdkKeysBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | 
    String environmentKey = "production"; // String | 
    String sdkKeyKey = "my-sdk-key"; // String | The user-defined identifying key of the SDK key. This is used solely to identify an SDK key and is distinct from the value field, which is the actual SDK key value.
    try {
      apiInstance.deleteSdkKeyByKey(ldAPIVersion, projectKey, environmentKey, sdkKeyKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling SdkKeysBetaApi#deleteSdkKeyByKey");
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
| **ldAPIVersion** | **String**| Version of the endpoint. | [enum: beta] |
| **projectKey** | **String**|  | |
| **environmentKey** | **String**|  | |
| **sdkKeyKey** | **String**| The user-defined identifying key of the SDK key. This is used solely to identify an SDK key and is distinct from the value field, which is the actual SDK key value. | |

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
| **204** | SDK key deleted |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **409** | Conflict |  -  |
| **500** | Internal server error |  -  |

<a id="getSdkKeyByKey"></a>
# **getSdkKeyByKey**
> SdkKey getSdkKeyByKey(ldAPIVersion, projectKey, environmentKey, sdkKeyKey)

Get SDK key

Get a specific SDK key by its key.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SdkKeysBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SdkKeysBetaApi apiInstance = new SdkKeysBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | 
    String environmentKey = "production"; // String | 
    String sdkKeyKey = "my-sdk-key"; // String | The user-defined identifying key of the SDK key. This is used solely to identify an SDK key and is distinct from the value field, which is the actual SDK key value.
    try {
      SdkKey result = apiInstance.getSdkKeyByKey(ldAPIVersion, projectKey, environmentKey, sdkKeyKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SdkKeysBetaApi#getSdkKeyByKey");
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
| **ldAPIVersion** | **String**| Version of the endpoint. | [enum: beta] |
| **projectKey** | **String**|  | |
| **environmentKey** | **String**|  | |
| **sdkKeyKey** | **String**| The user-defined identifying key of the SDK key. This is used solely to identify an SDK key and is distinct from the value field, which is the actual SDK key value. | |

### Return type

[**SdkKey**](SdkKey.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful response |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="patchSdkKeyByKey"></a>
# **patchSdkKeyByKey**
> SdkKey patchSdkKeyByKey(ldAPIVersion, projectKey, environmentKey, sdkKeyKey, sdkKeyPatch, version)

Update SDK key

Update a an existing SDK key by its identifying key.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SdkKeysBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SdkKeysBetaApi apiInstance = new SdkKeysBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | 
    String environmentKey = "production"; // String | 
    String sdkKeyKey = "my-sdk-key"; // String | The user-defined identifying key of the SDK key. This is used solely to identify an SDK key and is distinct from the value field, which is the actual SDK key value.
    SdkKeyPatch sdkKeyPatch = new SdkKeyPatch(); // SdkKeyPatch | An array of patches for updating an existing SDK key. The following fields are supported: `name`, `description`, `expiry`.
    Integer version = 56; // Integer | The version of the SDK key for optimistic locking. If provided, the update will only succeed if the current version matches.
    try {
      SdkKey result = apiInstance.patchSdkKeyByKey(ldAPIVersion, projectKey, environmentKey, sdkKeyKey, sdkKeyPatch, version);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SdkKeysBetaApi#patchSdkKeyByKey");
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
| **ldAPIVersion** | **String**| Version of the endpoint. | [enum: beta] |
| **projectKey** | **String**|  | |
| **environmentKey** | **String**|  | |
| **sdkKeyKey** | **String**| The user-defined identifying key of the SDK key. This is used solely to identify an SDK key and is distinct from the value field, which is the actual SDK key value. | |
| **sdkKeyPatch** | [**SdkKeyPatch**](SdkKeyPatch.md)| An array of patches for updating an existing SDK key. The following fields are supported: &#x60;name&#x60;, &#x60;description&#x60;, &#x60;expiry&#x60;. | |
| **version** | **Integer**| The version of the SDK key for optimistic locking. If provided, the update will only succeed if the current version matches. | [optional] |

### Return type

[**SdkKey**](SdkKey.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful response |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **409** | Conflict |  -  |
| **500** | Internal server error |  -  |

<a id="postSdkKey"></a>
# **postSdkKey**
> SdkKey postSdkKey(ldAPIVersion, projectKey, environmentKey, sdkKeyPost)

Create SDK key

Create a new server-side or mobile SDK key.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SdkKeysBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SdkKeysBetaApi apiInstance = new SdkKeysBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | 
    String environmentKey = "production"; // String | 
    SdkKeyPost sdkKeyPost = new SdkKeyPost(); // SdkKeyPost | Parameters for creating a new SDK key
    try {
      SdkKey result = apiInstance.postSdkKey(ldAPIVersion, projectKey, environmentKey, sdkKeyPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SdkKeysBetaApi#postSdkKey");
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
| **ldAPIVersion** | **String**| Version of the endpoint. | [enum: beta] |
| **projectKey** | **String**|  | |
| **environmentKey** | **String**|  | |
| **sdkKeyPost** | [**SdkKeyPost**](SdkKeyPost.md)| Parameters for creating a new SDK key | |

### Return type

[**SdkKey**](SdkKey.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | SDK key created |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **409** | Conflict |  -  |
| **500** | Internal server error |  -  |

