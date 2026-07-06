# AiConfigsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**listAIToolReferences**](AiConfigsApi.md#listAIToolReferences) | **GET** /api/v2/projects/{projectKey}/ai-tools/{toolKey}/references | List AI tool references |
| [**listAgentOptimizationRuns**](AiConfigsApi.md#listAgentOptimizationRuns) | **GET** /api/v2/projects/{projectKey}/agent-optimizations/{optimizationKey}/runs | List agent optimization runs |


<a id="listAIToolReferences"></a>
# **listAIToolReferences**
> ToolReferences listAIToolReferences(projectKey, toolKey, limit, offset)

List AI tool references

Get all AgentControl config variations that currently reference this tool.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AiConfigsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AiConfigsApi apiInstance = new AiConfigsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | 
    String toolKey = "toolKey_example"; // String | 
    Integer limit = 56; // Integer | The number of resources to return.
    Integer offset = 56; // Integer | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    try {
      ToolReferences result = apiInstance.listAIToolReferences(projectKey, toolKey, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#listAIToolReferences");
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
| **toolKey** | **String**|  | |
| **limit** | **Integer**| The number of resources to return. | [optional] |
| **offset** | **Integer**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |

### Return type

[**ToolReferences**](ToolReferences.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | AI tool references |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="listAgentOptimizationRuns"></a>
# **listAgentOptimizationRuns**
> AgentOptimizationRuns listAgentOptimizationRuns(projectKey, optimizationKey, limit, offset)

List agent optimization runs

Get one run summary per distinct run_id across all versions of an agent optimization config, ordered by created_at DESC.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AiConfigsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AiConfigsApi apiInstance = new AiConfigsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | 
    String optimizationKey = "optimizationKey_example"; // String | 
    Integer limit = 56; // Integer | The number of resources to return.
    Integer offset = 56; // Integer | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    try {
      AgentOptimizationRuns result = apiInstance.listAgentOptimizationRuns(projectKey, optimizationKey, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#listAgentOptimizationRuns");
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
| **optimizationKey** | **String**|  | |
| **limit** | **Integer**| The number of resources to return. | [optional] |
| **offset** | **Integer**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |

### Return type

[**AgentOptimizationRuns**](AgentOptimizationRuns.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Agent optimization run list |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

