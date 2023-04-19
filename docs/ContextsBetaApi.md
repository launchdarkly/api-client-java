# ContextsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getContextKindsByProjectKey**](ContextsBetaApi.md#getContextKindsByProjectKey) | **GET** /api/v2/projects/{projectKey}/context-kinds | Get context kinds |
| [**putContextKind**](ContextsBetaApi.md#putContextKind) | **PUT** /api/v2/projects/{projectKey}/context-kinds/{key} | Create or update context kind |


<a name="getContextKindsByProjectKey"></a>
# **getContextKindsByProjectKey**
> ContextKindsCollectionRep getContextKindsByProjectKey(projectKey)

Get context kinds

Get all context kinds for a given project.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ContextsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ContextsBetaApi apiInstance = new ContextsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    try {
      ContextKindsCollectionRep result = apiInstance.getContextKindsByProjectKey(projectKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsBetaApi#getContextKindsByProjectKey");
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
| **projectKey** | **String**| The project key | |

### Return type

[**ContextKindsCollectionRep**](ContextKindsCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Context kinds collection response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |

<a name="putContextKind"></a>
# **putContextKind**
> UpsertResponseRep putContextKind(projectKey, key, upsertContextKindPayload)

Create or update context kind

Create or update a context kind by key. Only the included fields will be updated.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ContextsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ContextsBetaApi apiInstance = new ContextsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String key = "key_example"; // String | The context kind key
    UpsertContextKindPayload upsertContextKindPayload = new UpsertContextKindPayload(); // UpsertContextKindPayload | 
    try {
      UpsertResponseRep result = apiInstance.putContextKind(projectKey, key, upsertContextKindPayload);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsBetaApi#putContextKind");
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
| **projectKey** | **String**| The project key | |
| **key** | **String**| The context kind key | |
| **upsertContextKindPayload** | [**UpsertContextKindPayload**](UpsertContextKindPayload.md)|  | |

### Return type

[**UpsertResponseRep**](UpsertResponseRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Context kind upsert response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |

