# AiConfigsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteAIConfig**](AiConfigsApi.md#deleteAIConfig) | **DELETE** /api/v2/projects/{projectKey}/ai-configs/{configKey} | Delete AI Config |
| [**deleteAIConfigVariation**](AiConfigsApi.md#deleteAIConfigVariation) | **DELETE** /api/v2/projects/{projectKey}/ai-configs/{configKey}/variations/{variationKey} | Delete AI Config variation |
| [**deleteAITool**](AiConfigsApi.md#deleteAITool) | **DELETE** /api/v2/projects/{projectKey}/ai-tools/{toolKey} | Delete AI tool |
| [**deleteAgentGraph**](AiConfigsApi.md#deleteAgentGraph) | **DELETE** /api/v2/projects/{projectKey}/agent-graphs/{graphKey} | Delete agent graph |
| [**deleteModelConfig**](AiConfigsApi.md#deleteModelConfig) | **DELETE** /api/v2/projects/{projectKey}/ai-configs/model-configs/{modelConfigKey} | Delete an AI model config |
| [**deleteRestrictedModels**](AiConfigsApi.md#deleteRestrictedModels) | **DELETE** /api/v2/projects/{projectKey}/ai-configs/model-configs/restricted | Remove AI models from the restricted list |
| [**getAIConfig**](AiConfigsApi.md#getAIConfig) | **GET** /api/v2/projects/{projectKey}/ai-configs/{configKey} | Get AI Config |
| [**getAIConfigMetrics**](AiConfigsApi.md#getAIConfigMetrics) | **GET** /api/v2/projects/{projectKey}/ai-configs/{configKey}/metrics | Get AI Config metrics |
| [**getAIConfigMetricsByVariation**](AiConfigsApi.md#getAIConfigMetricsByVariation) | **GET** /api/v2/projects/{projectKey}/ai-configs/{configKey}/metrics-by-variation | Get AI Config metrics by variation |
| [**getAIConfigTargeting**](AiConfigsApi.md#getAIConfigTargeting) | **GET** /api/v2/projects/{projectKey}/ai-configs/{configKey}/targeting | Show an AI Config&#39;s targeting |
| [**getAIConfigVariation**](AiConfigsApi.md#getAIConfigVariation) | **GET** /api/v2/projects/{projectKey}/ai-configs/{configKey}/variations/{variationKey} | Get AI Config variation |
| [**getAIConfigs**](AiConfigsApi.md#getAIConfigs) | **GET** /api/v2/projects/{projectKey}/ai-configs | List AI Configs |
| [**getAITool**](AiConfigsApi.md#getAITool) | **GET** /api/v2/projects/{projectKey}/ai-tools/{toolKey} | Get AI tool |
| [**getAgentGraph**](AiConfigsApi.md#getAgentGraph) | **GET** /api/v2/projects/{projectKey}/agent-graphs/{graphKey} | Get agent graph |
| [**getModelConfig**](AiConfigsApi.md#getModelConfig) | **GET** /api/v2/projects/{projectKey}/ai-configs/model-configs/{modelConfigKey} | Get AI model config |
| [**listAIToolVersions**](AiConfigsApi.md#listAIToolVersions) | **GET** /api/v2/projects/{projectKey}/ai-tools/{toolKey}/versions | List AI tool versions |
| [**listAITools**](AiConfigsApi.md#listAITools) | **GET** /api/v2/projects/{projectKey}/ai-tools | List AI tools |
| [**listAgentGraphs**](AiConfigsApi.md#listAgentGraphs) | **GET** /api/v2/projects/{projectKey}/agent-graphs | List agent graphs |
| [**listModelConfigs**](AiConfigsApi.md#listModelConfigs) | **GET** /api/v2/projects/{projectKey}/ai-configs/model-configs | List AI model configs |
| [**patchAIConfig**](AiConfigsApi.md#patchAIConfig) | **PATCH** /api/v2/projects/{projectKey}/ai-configs/{configKey} | Update AI Config |
| [**patchAIConfigTargeting**](AiConfigsApi.md#patchAIConfigTargeting) | **PATCH** /api/v2/projects/{projectKey}/ai-configs/{configKey}/targeting | Update AI Config targeting |
| [**patchAIConfigVariation**](AiConfigsApi.md#patchAIConfigVariation) | **PATCH** /api/v2/projects/{projectKey}/ai-configs/{configKey}/variations/{variationKey} | Update AI Config variation |
| [**patchAITool**](AiConfigsApi.md#patchAITool) | **PATCH** /api/v2/projects/{projectKey}/ai-tools/{toolKey} | Update AI tool |
| [**patchAgentGraph**](AiConfigsApi.md#patchAgentGraph) | **PATCH** /api/v2/projects/{projectKey}/agent-graphs/{graphKey} | Update agent graph |
| [**postAIConfig**](AiConfigsApi.md#postAIConfig) | **POST** /api/v2/projects/{projectKey}/ai-configs | Create new AI Config |
| [**postAIConfigVariation**](AiConfigsApi.md#postAIConfigVariation) | **POST** /api/v2/projects/{projectKey}/ai-configs/{configKey}/variations | Create AI Config variation |
| [**postAITool**](AiConfigsApi.md#postAITool) | **POST** /api/v2/projects/{projectKey}/ai-tools | Create an AI tool |
| [**postAgentGraph**](AiConfigsApi.md#postAgentGraph) | **POST** /api/v2/projects/{projectKey}/agent-graphs | Create new agent graph |
| [**postModelConfig**](AiConfigsApi.md#postModelConfig) | **POST** /api/v2/projects/{projectKey}/ai-configs/model-configs | Create an AI model config |
| [**postRestrictedModels**](AiConfigsApi.md#postRestrictedModels) | **POST** /api/v2/projects/{projectKey}/ai-configs/model-configs/restricted | Add AI models to the restricted list |


<a id="deleteAIConfig"></a>
# **deleteAIConfig**
> deleteAIConfig(projectKey, configKey)

Delete AI Config

Delete an existing AI Config.

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
    String projectKey = "default"; // String | 
    String configKey = "configKey_example"; // String | 
    try {
      apiInstance.deleteAIConfig(projectKey, configKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#deleteAIConfig");
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
| **configKey** | **String**|  | |

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
| **500** | Internal server error |  -  |

<a id="deleteAIConfigVariation"></a>
# **deleteAIConfigVariation**
> deleteAIConfigVariation(projectKey, configKey, variationKey)

Delete AI Config variation

Delete a specific variation of an AI Config by config key and variation key.

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
    String configKey = "configKey_example"; // String | 
    String variationKey = "variationKey_example"; // String | 
    try {
      apiInstance.deleteAIConfigVariation(projectKey, configKey, variationKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#deleteAIConfigVariation");
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
| **configKey** | **String**|  | |
| **variationKey** | **String**|  | |

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
| **500** | Internal server error |  -  |

<a id="deleteAITool"></a>
# **deleteAITool**
> deleteAITool(projectKey, toolKey)

Delete AI tool

Delete an existing AI tool.

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
    try {
      apiInstance.deleteAITool(projectKey, toolKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#deleteAITool");
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
| **500** | Internal server error |  -  |

<a id="deleteAgentGraph"></a>
# **deleteAgentGraph**
> deleteAgentGraph(ldAPIVersion, projectKey, graphKey)

Delete agent graph

Delete an existing agent graph and all of its edges.

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
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "projectKey_example"; // String | 
    String graphKey = "graphKey_example"; // String | 
    try {
      apiInstance.deleteAgentGraph(ldAPIVersion, projectKey, graphKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#deleteAgentGraph");
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
| **graphKey** | **String**|  | |

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
| **500** | Internal server error |  -  |

<a id="deleteModelConfig"></a>
# **deleteModelConfig**
> deleteModelConfig(projectKey, modelConfigKey)

Delete an AI model config

Delete an AI model config.

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
    String projectKey = "default"; // String | 
    String modelConfigKey = "modelConfigKey_example"; // String | 
    try {
      apiInstance.deleteModelConfig(projectKey, modelConfigKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#deleteModelConfig");
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
| **modelConfigKey** | **String**|  | |

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
| **500** | Internal server error |  -  |

<a id="deleteRestrictedModels"></a>
# **deleteRestrictedModels**
> deleteRestrictedModels(projectKey, restrictedModelsRequest)

Remove AI models from the restricted list

Remove AI models, by key, from the restricted list.

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
    String projectKey = "default"; // String | 
    RestrictedModelsRequest restrictedModelsRequest = new RestrictedModelsRequest(); // RestrictedModelsRequest | List of AI model keys to remove from the restricted list
    try {
      apiInstance.deleteRestrictedModels(projectKey, restrictedModelsRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#deleteRestrictedModels");
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
| **restrictedModelsRequest** | [**RestrictedModelsRequest**](RestrictedModelsRequest.md)| List of AI model keys to remove from the restricted list | |

### Return type

null (empty response body)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | No content |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="getAIConfig"></a>
# **getAIConfig**
> AIConfig getAIConfig(projectKey, configKey)

Get AI Config

Retrieve a specific AI Config by its key.

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
    String configKey = "configKey_example"; // String | 
    try {
      AIConfig result = apiInstance.getAIConfig(projectKey, configKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#getAIConfig");
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
| **configKey** | **String**|  | |

### Return type

[**AIConfig**](AIConfig.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | AI Config found |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="getAIConfigMetrics"></a>
# **getAIConfigMetrics**
> Metrics getAIConfigMetrics(projectKey, configKey, from, to, env)

Get AI Config metrics

Retrieve usage metrics for an AI Config by config key.

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
    String configKey = "configKey_example"; // String | 
    Integer from = 56; // Integer | The starting time, as milliseconds since epoch (inclusive).
    Integer to = 56; // Integer | The ending time, as milliseconds since epoch (exclusive). May not be more than 100 days after `from`.
    String env = "env_example"; // String | An environment key. Only metrics from this environment will be included.
    try {
      Metrics result = apiInstance.getAIConfigMetrics(projectKey, configKey, from, to, env);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#getAIConfigMetrics");
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
| **configKey** | **String**|  | |
| **from** | **Integer**| The starting time, as milliseconds since epoch (inclusive). | |
| **to** | **Integer**| The ending time, as milliseconds since epoch (exclusive). May not be more than 100 days after &#x60;from&#x60;. | |
| **env** | **String**| An environment key. Only metrics from this environment will be included. | |

### Return type

[**Metrics**](Metrics.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Metrics computed |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="getAIConfigMetricsByVariation"></a>
# **getAIConfigMetricsByVariation**
> List&lt;MetricByVariation&gt; getAIConfigMetricsByVariation(projectKey, configKey, from, to, env)

Get AI Config metrics by variation

Retrieve usage metrics for an AI Config by config key, with results split by variation.

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
    String configKey = "configKey_example"; // String | 
    Integer from = 56; // Integer | The starting time, as milliseconds since epoch (inclusive).
    Integer to = 56; // Integer | The ending time, as milliseconds since epoch (exclusive). May not be more than 100 days after `from`.
    String env = "env_example"; // String | An environment key. Only metrics from this environment will be included.
    try {
      List<MetricByVariation> result = apiInstance.getAIConfigMetricsByVariation(projectKey, configKey, from, to, env);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#getAIConfigMetricsByVariation");
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
| **configKey** | **String**|  | |
| **from** | **Integer**| The starting time, as milliseconds since epoch (inclusive). | |
| **to** | **Integer**| The ending time, as milliseconds since epoch (exclusive). May not be more than 100 days after &#x60;from&#x60;. | |
| **env** | **String**| An environment key. Only metrics from this environment will be included. | |

### Return type

[**List&lt;MetricByVariation&gt;**](MetricByVariation.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Metrics computed |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="getAIConfigTargeting"></a>
# **getAIConfigTargeting**
> AIConfigTargeting getAIConfigTargeting(projectKey, configKey)

Show an AI Config&#39;s targeting

Retrieves a specific AI Config&#39;s targeting by its key

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
    String configKey = "configKey_example"; // String | 
    try {
      AIConfigTargeting result = apiInstance.getAIConfigTargeting(projectKey, configKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#getAIConfigTargeting");
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
| **configKey** | **String**|  | |

### Return type

[**AIConfigTargeting**](AIConfigTargeting.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful response |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="getAIConfigVariation"></a>
# **getAIConfigVariation**
> AIConfigVariationsResponse getAIConfigVariation(projectKey, configKey, variationKey)

Get AI Config variation

Get an AI Config variation by key. The response includes all variation versions for the given variation key.

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
    String projectKey = "default"; // String | 
    String configKey = "default"; // String | 
    String variationKey = "default"; // String | 
    try {
      AIConfigVariationsResponse result = apiInstance.getAIConfigVariation(projectKey, configKey, variationKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#getAIConfigVariation");
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
| **configKey** | **String**|  | |
| **variationKey** | **String**|  | |

### Return type

[**AIConfigVariationsResponse**](AIConfigVariationsResponse.md)

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

<a id="getAIConfigs"></a>
# **getAIConfigs**
> AIConfigs getAIConfigs(projectKey, sort, limit, offset, filter)

List AI Configs

Get a list of all AI Configs in the given project.

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
    String projectKey = "default"; // String | 
    String sort = "sort_example"; // String | A sort to apply to the list of AI Configs.
    Integer limit = 56; // Integer | The number of AI Configs to return.
    Integer offset = 56; // Integer | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    String filter = "filter_example"; // String | A filter to apply to the list of AI Configs.
    try {
      AIConfigs result = apiInstance.getAIConfigs(projectKey, sort, limit, offset, filter);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#getAIConfigs");
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
| **sort** | **String**| A sort to apply to the list of AI Configs. | [optional] |
| **limit** | **Integer**| The number of AI Configs to return. | [optional] |
| **offset** | **Integer**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |
| **filter** | **String**| A filter to apply to the list of AI Configs. | [optional] |

### Return type

[**AIConfigs**](AIConfigs.md)

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

<a id="getAITool"></a>
# **getAITool**
> AITool getAITool(projectKey, toolKey)

Get AI tool

Retrieve a specific AI tool by its key.

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
    try {
      AITool result = apiInstance.getAITool(projectKey, toolKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#getAITool");
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

### Return type

[**AITool**](AITool.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | AI tool found |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="getAgentGraph"></a>
# **getAgentGraph**
> AgentGraph getAgentGraph(ldAPIVersion, projectKey, graphKey)

Get agent graph

Retrieve a specific agent graph by its key, including its edges.

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
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "projectKey_example"; // String | 
    String graphKey = "graphKey_example"; // String | 
    try {
      AgentGraph result = apiInstance.getAgentGraph(ldAPIVersion, projectKey, graphKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#getAgentGraph");
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
| **graphKey** | **String**|  | |

### Return type

[**AgentGraph**](AgentGraph.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Agent graph found |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="getModelConfig"></a>
# **getModelConfig**
> ModelConfig getModelConfig(projectKey, modelConfigKey)

Get AI model config

Get an AI model config by key.

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
    String projectKey = "default"; // String | 
    String modelConfigKey = "default"; // String | 
    try {
      ModelConfig result = apiInstance.getModelConfig(projectKey, modelConfigKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#getModelConfig");
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
| **modelConfigKey** | **String**|  | |

### Return type

[**ModelConfig**](ModelConfig.md)

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

<a id="listAIToolVersions"></a>
# **listAIToolVersions**
> AITools listAIToolVersions(projectKey, toolKey, sort, limit, offset)

List AI tool versions

Get a list of all versions of an AI tool in the given project.

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
    String sort = "sort_example"; // String | A sort to apply to the list of AI Configs.
    Integer limit = 56; // Integer | The number of AI Configs to return.
    Integer offset = 56; // Integer | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    try {
      AITools result = apiInstance.listAIToolVersions(projectKey, toolKey, sort, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#listAIToolVersions");
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
| **sort** | **String**| A sort to apply to the list of AI Configs. | [optional] |
| **limit** | **Integer**| The number of AI Configs to return. | [optional] |
| **offset** | **Integer**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |

### Return type

[**AITools**](AITools.md)

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
| **500** | Internal server error |  -  |

<a id="listAITools"></a>
# **listAITools**
> AITools listAITools(projectKey, sort, limit, offset, filter)

List AI tools

Get a list of all AI tools in the given project.

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
    String sort = "sort_example"; // String | A sort to apply to the list of AI Configs.
    Integer limit = 56; // Integer | The number of AI Configs to return.
    Integer offset = 56; // Integer | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    String filter = "filter_example"; // String | A filter to apply to the list of AI Configs.
    try {
      AITools result = apiInstance.listAITools(projectKey, sort, limit, offset, filter);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#listAITools");
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
| **sort** | **String**| A sort to apply to the list of AI Configs. | [optional] |
| **limit** | **Integer**| The number of AI Configs to return. | [optional] |
| **offset** | **Integer**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |
| **filter** | **String**| A filter to apply to the list of AI Configs. | [optional] |

### Return type

[**AITools**](AITools.md)

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
| **500** | Internal server error |  -  |

<a id="listAgentGraphs"></a>
# **listAgentGraphs**
> AgentGraphs listAgentGraphs(ldAPIVersion, projectKey, limit, offset)

List agent graphs

Get a list of all agent graphs in the given project. Returns metadata only, without edge data.

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
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "projectKey_example"; // String | 
    Integer limit = 56; // Integer | The number of AI Configs to return.
    Integer offset = 56; // Integer | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    try {
      AgentGraphs result = apiInstance.listAgentGraphs(ldAPIVersion, projectKey, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#listAgentGraphs");
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
| **limit** | **Integer**| The number of AI Configs to return. | [optional] |
| **offset** | **Integer**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |

### Return type

[**AgentGraphs**](AgentGraphs.md)

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
| **500** | Internal server error |  -  |

<a id="listModelConfigs"></a>
# **listModelConfigs**
> List&lt;ModelConfig&gt; listModelConfigs(projectKey, restricted)

List AI model configs

Get all AI model configs for a project.

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
    String projectKey = "default"; // String | 
    Boolean restricted = true; // Boolean | Whether to return only restricted models
    try {
      List<ModelConfig> result = apiInstance.listModelConfigs(projectKey, restricted);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#listModelConfigs");
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
| **restricted** | **Boolean**| Whether to return only restricted models | [optional] |

### Return type

[**List&lt;ModelConfig&gt;**](ModelConfig.md)

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

<a id="patchAIConfig"></a>
# **patchAIConfig**
> AIConfig patchAIConfig(projectKey, configKey, aiConfigPatch)

Update AI Config

Edit an existing AI Config.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  Here&#39;s an example:   &#x60;&#x60;&#x60;     {       \&quot;description\&quot;: \&quot;Example updated description\&quot;,       \&quot;tags\&quot;: [\&quot;new-tag\&quot;]     }   &#x60;&#x60;&#x60; 

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
    String configKey = "configKey_example"; // String | 
    AIConfigPatch aiConfigPatch = new AIConfigPatch(); // AIConfigPatch | AI Config object to update
    try {
      AIConfig result = apiInstance.patchAIConfig(projectKey, configKey, aiConfigPatch);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#patchAIConfig");
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
| **configKey** | **String**|  | |
| **aiConfigPatch** | [**AIConfigPatch**](AIConfigPatch.md)| AI Config object to update | [optional] |

### Return type

[**AIConfig**](AIConfig.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | AI Config updated |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="patchAIConfigTargeting"></a>
# **patchAIConfigTargeting**
> AIConfigTargeting patchAIConfigTargeting(projectKey, configKey, aiConfigTargetingPatch)

Update AI Config targeting

Perform a partial update to an AI Config&#39;s targeting. The request body must be a valid semantic patch.  ### Using semantic patches on an AI Config  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](https://launchdarkly.com/docs/api#updates-using-semantic-patch).  The body of a semantic patch request for updating an AI Config&#39;s targeting takes the following properties:  * &#x60;comment&#x60; (string): (Optional) A description of the update. * &#x60;environmentKey&#x60; (string): The key of the LaunchDarkly environment. * &#x60;instructions&#x60; (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a &#x60;kind&#x60; property that indicates the instruction. If the action requires parameters, you must include those parameters as additional fields in the object. The body of a single semantic patch can contain many different instructions.  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating AI Configs.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;working with targeting and variations&lt;/strong&gt; for AI Configs&lt;/summary&gt;  #### addClauses  Adds the given clauses to the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the AI Config. - &#x60;clauses&#x60;: Array of clause objects, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addClauses\&quot;,     \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,     \&quot;clauses\&quot;: [{       \&quot;contextKind\&quot;: \&quot;user\&quot;,       \&quot;attribute\&quot;: \&quot;country\&quot;,       \&quot;op\&quot;: \&quot;in\&quot;,       \&quot;negate\&quot;: false,       \&quot;values\&quot;: [\&quot;USA\&quot;, \&quot;Canada\&quot;]     }]   }] } &#x60;&#x60;&#x60;  #### addRule  Adds a new targeting rule to the AI Config. The rule may contain &#x60;clauses&#x60; and serve the variation that &#x60;variationId&#x60; indicates, or serve a percentage rollout that &#x60;rolloutWeights&#x60;, &#x60;rolloutBucketBy&#x60;, and &#x60;rolloutContextKind&#x60; indicate.  If you set &#x60;beforeRuleId&#x60;, this adds the new rule before the indicated rule. Otherwise, adds the new rule to the end of the list.  ##### Parameters  - &#x60;clauses&#x60;: Array of clause objects, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case. - &#x60;beforeRuleId&#x60;: (Optional) ID of a rule. - Either - &#x60;variationId&#x60;: ID of a variation.  or  - &#x60;rolloutWeights&#x60;: (Optional) Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example that uses a &#x60;variationId&#x60;:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addRule\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,   \&quot;clauses\&quot;: [{     \&quot;contextKind\&quot;: \&quot;organization\&quot;,     \&quot;attribute\&quot;: \&quot;located_in\&quot;,     \&quot;op\&quot;: \&quot;in\&quot;,     \&quot;negate\&quot;: false,     \&quot;values\&quot;: [\&quot;Sweden\&quot;, \&quot;Norway\&quot;]   }] }] } &#x60;&#x60;&#x60;  Here&#39;s an example that uses a percentage rollout:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addRule\&quot;,   \&quot;clauses\&quot;: [{     \&quot;contextKind\&quot;: \&quot;organization\&quot;,     \&quot;attribute\&quot;: \&quot;located_in\&quot;,     \&quot;op\&quot;: \&quot;in\&quot;,     \&quot;negate\&quot;: false,     \&quot;values\&quot;: [\&quot;Sweden\&quot;, \&quot;Norway\&quot;]   }],   \&quot;rolloutContextKind\&quot;: \&quot;organization\&quot;,   \&quot;rolloutWeights\&quot;: {     \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;: 15000, // serve 15% this variation     \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;: 85000  // serve 85% this variation   } }] } &#x60;&#x60;&#x60;  #### addTargets  Adds context keys to the individual context targets for the context kind that &#x60;contextKind&#x60; specifies and the variation that &#x60;variationId&#x60; specifies. Returns an error if this causes the AI Config to target the same context key in multiple variations.  ##### Parameters  - &#x60;values&#x60;: List of context keys. - &#x60;contextKind&#x60;: (Optional) Context kind to target, defaults to &#x60;user&#x60; - &#x60;variationId&#x60;: ID of a variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addTargets\&quot;,   \&quot;values\&quot;: [\&quot;context-key-123abc\&quot;, \&quot;context-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; }] } &#x60;&#x60;&#x60;  #### addValuesToClause  Adds &#x60;values&#x60; to the values of the clause that &#x60;ruleId&#x60; and &#x60;clauseId&#x60; indicate. Does not update the context kind, attribute, or operator.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the AI Config. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings, case sensitive.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addValuesToClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;,   \&quot;values\&quot;: [\&quot;beta_testers\&quot;] }] } &#x60;&#x60;&#x60;  #### clearTargets  Removes all individual targets from the variation that &#x60;variationId&#x60; specifies. This includes both user and non-user targets.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;clearTargets\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### removeClauses  Removes the clauses specified by &#x60;clauseIds&#x60; from the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule. - &#x60;clauseIds&#x60;: Array of IDs of clauses in the rule.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeClauses\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseIds\&quot;: [\&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;, \&quot;36a461dc-235e-4b08-97b9-73ce9365873e\&quot;] }] } &#x60;&#x60;&#x60;  #### removeRule  Removes the targeting rule specified by &#x60;ruleId&#x60;. Does nothing if the rule does not exist.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removeRule\&quot;, \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot; } ] } &#x60;&#x60;&#x60;  #### removeTargets  Removes context keys from the individual context targets for the context kind that &#x60;contextKind&#x60; specifies and the variation that &#x60;variationId&#x60; specifies. Does nothing if the flag does not target the context keys.  ##### Parameters  - &#x60;values&#x60;: List of context keys. - &#x60;contextKind&#x60;: (Optional) Context kind to target, defaults to &#x60;user&#x60; - &#x60;variationId&#x60;: ID of a variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeTargets\&quot;,   \&quot;values\&quot;: [\&quot;context-key-123abc\&quot;, \&quot;context-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; }] } &#x60;&#x60;&#x60;  #### removeValuesFromClause  Removes &#x60;values&#x60; from the values of the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60;. Does not update the context kind, attribute, or operator.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings, case sensitive.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeValuesFromClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;,   \&quot;values\&quot;: [\&quot;beta_testers\&quot;] }] } &#x60;&#x60;&#x60;  #### reorderRules  Rearranges the rules to match the order given in &#x60;ruleIds&#x60;. Returns an error if &#x60;ruleIds&#x60; does not match the current set of rules on the AI Config.  ##### Parameters  - &#x60;ruleIds&#x60;: Array of IDs of all rules.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;reorderRules\&quot;,   \&quot;ruleIds\&quot;: [\&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;, \&quot;63c238d1-835d-435e-8f21-c8d5e40b2a3d\&quot;] }] } &#x60;&#x60;&#x60;  #### replaceRules  Removes all targeting rules for the AI Config and replaces them with the list you provide.  ##### Parameters  - &#x60;rules&#x60;: A list of rules.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [   {     \&quot;kind\&quot;: \&quot;replaceRules\&quot;,     \&quot;rules\&quot;: [       {         \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,         \&quot;description\&quot;: \&quot;My new rule\&quot;,         \&quot;clauses\&quot;: [           {             \&quot;contextKind\&quot;: \&quot;user\&quot;,             \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,             \&quot;op\&quot;: \&quot;segmentMatch\&quot;,             \&quot;values\&quot;: [\&quot;test\&quot;]           }         ]       }     ]   } ] } &#x60;&#x60;&#x60;  #### replaceTargets  Removes all existing targeting and replaces it with the list of targets you provide.  ##### Parameters  - &#x60;targets&#x60;: A list of context targeting. Each item in the list includes an optional &#x60;contextKind&#x60; that defaults to &#x60;user&#x60;, a required &#x60;variationId&#x60;, and a required list of &#x60;values&#x60;.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [   {     \&quot;kind\&quot;: \&quot;replaceTargets\&quot;,     \&quot;targets\&quot;: [       {         \&quot;contextKind\&quot;: \&quot;user\&quot;,         \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,         \&quot;values\&quot;: [\&quot;user-key-123abc\&quot;]       },       {         \&quot;contextKind\&quot;: \&quot;device\&quot;,         \&quot;variationId\&quot;: \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;,         \&quot;values\&quot;: [\&quot;device-key-456def\&quot;]       }     ]   } ] } &#x60;&#x60;&#x60;  #### updateClause  Replaces the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60; with &#x60;clause&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;clause&#x60;: New &#x60;clause&#x60; object, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10c7462a-2062-45ba-a8bb-dfb3de0f8af5\&quot;,   \&quot;clause\&quot;: {     \&quot;contextKind\&quot;: \&quot;user\&quot;,     \&quot;attribute\&quot;: \&quot;country\&quot;,     \&quot;op\&quot;: \&quot;in\&quot;,     \&quot;negate\&quot;: false,     \&quot;values\&quot;: [\&quot;Mexico\&quot;, \&quot;Canada\&quot;]   } }] } &#x60;&#x60;&#x60;  #### updateDefaultVariation  Updates the default on or off variation of the AI Config.  ##### Parameters  - &#x60;onVariationValue&#x60;: (Optional) The value of the variation of the new on variation. - &#x60;offVariationValue&#x60;: (Optional) The value of the variation of the new off variation  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateDefaultVariation\&quot;, \&quot;OnVariationValue\&quot;: true, \&quot;OffVariationValue\&quot;: false } ] } &#x60;&#x60;&#x60;  #### updateFallthroughVariationOrRollout  Updates the default or \&quot;fallthrough\&quot; rule for the AI Config, which the AI Config serves when a context matches none of the targeting rules. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percentage rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation.  or  - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example that uses a &#x60;variationId&#x60;:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; }] } &#x60;&#x60;&#x60;  Here&#39;s an example that uses a percentage rollout:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,   \&quot;rolloutContextKind\&quot;: \&quot;user\&quot;,   \&quot;rolloutWeights\&quot;: {     \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;: 15000, // serve 15% this variation     \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;: 85000  // serve 85% this variation   } }] } &#x60;&#x60;&#x60;  #### updateOffVariation  Updates the default off variation to &#x60;variationId&#x60;. The AI Config serves the default off variation when the AI Config&#39;s targeting is **Off**.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateOffVariation\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### updateRuleDescription  Updates the description of the targeting rule.  ##### Parameters  - &#x60;description&#x60;: The new human-readable description for this rule. - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the AI Config.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleDescription\&quot;,   \&quot;description\&quot;: \&quot;New rule description\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot; }] } &#x60;&#x60;&#x60;  #### updateRuleTrackEvents  Updates whether or not LaunchDarkly tracks events for the AI Config associated with this rule.  ##### Parameters  - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the AI Config. - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleTrackEvents\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;trackEvents\&quot;: true }] } &#x60;&#x60;&#x60;  #### updateRuleVariationOrRollout  Updates what &#x60;ruleId&#x60; serves when its clauses evaluate to true. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percent rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule. - &#x60;variationId&#x60;: ID of a variation.  or  - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleVariationOrRollout\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; }] } &#x60;&#x60;&#x60;  #### updateTrackEvents  Updates whether or not LaunchDarkly tracks events for the AI Config, for all rules.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateTrackEvents\&quot;, \&quot;trackEvents\&quot;: true } ] } &#x60;&#x60;&#x60;  #### updateTrackEventsFallthrough  Updates whether or not LaunchDarkly tracks events for the AI Config, for the default rule.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Here&#39;s an example:  &#x60;&#x60;&#x60;json { \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;, \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateTrackEventsFallthrough\&quot;, \&quot;trackEvents\&quot;: true } ] } &#x60;&#x60;&#x60; &lt;/details&gt; 

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
    String configKey = "configKey_example"; // String | 
    AIConfigTargetingPatch aiConfigTargetingPatch = new AIConfigTargetingPatch(); // AIConfigTargetingPatch | AI Config targeting semantic patch instructions
    try {
      AIConfigTargeting result = apiInstance.patchAIConfigTargeting(projectKey, configKey, aiConfigTargetingPatch);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#patchAIConfigTargeting");
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
| **configKey** | **String**|  | |
| **aiConfigTargetingPatch** | [**AIConfigTargetingPatch**](AIConfigTargetingPatch.md)| AI Config targeting semantic patch instructions | [optional] |

### Return type

[**AIConfigTargeting**](AIConfigTargeting.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | AI Config targeting updated |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="patchAIConfigVariation"></a>
# **patchAIConfigVariation**
> AIConfigVariation patchAIConfigVariation(projectKey, configKey, variationKey, aiConfigVariationPatch)

Update AI Config variation

Edit an existing variation of an AI Config. This creates a new version of the variation.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  Here&#39;s an example: &#x60;&#x60;&#x60;   {     \&quot;messages\&quot;: [       {         \&quot;role\&quot;: \&quot;system\&quot;,         \&quot;content\&quot;: \&quot;The new message\&quot;       }     ]   } &#x60;&#x60;&#x60; 

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
    String configKey = "configKey_example"; // String | 
    String variationKey = "variationKey_example"; // String | 
    AIConfigVariationPatch aiConfigVariationPatch = new AIConfigVariationPatch(); // AIConfigVariationPatch | AI Config variation object to update
    try {
      AIConfigVariation result = apiInstance.patchAIConfigVariation(projectKey, configKey, variationKey, aiConfigVariationPatch);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#patchAIConfigVariation");
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
| **configKey** | **String**|  | |
| **variationKey** | **String**|  | |
| **aiConfigVariationPatch** | [**AIConfigVariationPatch**](AIConfigVariationPatch.md)| AI Config variation object to update | [optional] |

### Return type

[**AIConfigVariation**](AIConfigVariation.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | AI Config variation updated |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="patchAITool"></a>
# **patchAITool**
> AITool patchAITool(projectKey, toolKey, aiToolPatch)

Update AI tool

Edit an existing AI tool.

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
    AIToolPatch aiToolPatch = new AIToolPatch(); // AIToolPatch | AI tool object to update
    try {
      AITool result = apiInstance.patchAITool(projectKey, toolKey, aiToolPatch);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#patchAITool");
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
| **aiToolPatch** | [**AIToolPatch**](AIToolPatch.md)| AI tool object to update | [optional] |

### Return type

[**AITool**](AITool.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | AI tool updated |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="patchAgentGraph"></a>
# **patchAgentGraph**
> AgentGraph patchAgentGraph(ldAPIVersion, projectKey, graphKey, agentGraphPatch)

Update agent graph

Edit an existing agent graph.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  If the update includes &#x60;rootConfigKey&#x60; or &#x60;edges&#x60;, both must be present and will be treated as full replacements. 

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
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "projectKey_example"; // String | 
    String graphKey = "graphKey_example"; // String | 
    AgentGraphPatch agentGraphPatch = new AgentGraphPatch(); // AgentGraphPatch | Agent graph object to update
    try {
      AgentGraph result = apiInstance.patchAgentGraph(ldAPIVersion, projectKey, graphKey, agentGraphPatch);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#patchAgentGraph");
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
| **graphKey** | **String**|  | |
| **agentGraphPatch** | [**AgentGraphPatch**](AgentGraphPatch.md)| Agent graph object to update | [optional] |

### Return type

[**AgentGraph**](AgentGraph.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Agent graph updated |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="postAIConfig"></a>
# **postAIConfig**
> AIConfig postAIConfig(projectKey, aiConfigPost)

Create new AI Config

Create a new AI Config within the given project.

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
    AIConfigPost aiConfigPost = new AIConfigPost(); // AIConfigPost | AI Config object to create
    try {
      AIConfig result = apiInstance.postAIConfig(projectKey, aiConfigPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#postAIConfig");
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
| **aiConfigPost** | [**AIConfigPost**](AIConfigPost.md)| AI Config object to create | |

### Return type

[**AIConfig**](AIConfig.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | AI Config created |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **500** | Internal server error |  -  |

<a id="postAIConfigVariation"></a>
# **postAIConfigVariation**
> AIConfigVariation postAIConfigVariation(projectKey, configKey, aiConfigVariationPost)

Create AI Config variation

Create a new variation for a given AI Config.  The &lt;code&gt;model&lt;/code&gt; in the request body requires a &lt;code&gt;modelName&lt;/code&gt; and &lt;code&gt;parameters&lt;/code&gt;, for example:  &#x60;&#x60;&#x60;   \&quot;model\&quot;: {     \&quot;modelName\&quot;: \&quot;claude-3-opus-20240229\&quot;,     \&quot;parameters\&quot;: {       \&quot;max_tokens\&quot;: 1024     }   } &#x60;&#x60;&#x60; 

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
    String configKey = "configKey_example"; // String | 
    AIConfigVariationPost aiConfigVariationPost = new AIConfigVariationPost(); // AIConfigVariationPost | AI Config variation object to create
    try {
      AIConfigVariation result = apiInstance.postAIConfigVariation(projectKey, configKey, aiConfigVariationPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#postAIConfigVariation");
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
| **configKey** | **String**|  | |
| **aiConfigVariationPost** | [**AIConfigVariationPost**](AIConfigVariationPost.md)| AI Config variation object to create | |

### Return type

[**AIConfigVariation**](AIConfigVariation.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | AI Config variation created |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **500** | Internal server error |  -  |

<a id="postAITool"></a>
# **postAITool**
> AITool postAITool(projectKey, aiToolPost)

Create an AI tool

Create an AI tool

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
    AIToolPost aiToolPost = new AIToolPost(); // AIToolPost | AI tool object to create
    try {
      AITool result = apiInstance.postAITool(projectKey, aiToolPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#postAITool");
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
| **aiToolPost** | [**AIToolPost**](AIToolPost.md)| AI tool object to create | |

### Return type

[**AITool**](AITool.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | AI tool created |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **500** | Internal server error |  -  |

<a id="postAgentGraph"></a>
# **postAgentGraph**
> AgentGraph postAgentGraph(ldAPIVersion, projectKey, agentGraphPost)

Create new agent graph

Create a new agent graph within the given project.

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
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "projectKey_example"; // String | 
    AgentGraphPost agentGraphPost = new AgentGraphPost(); // AgentGraphPost | Agent graph object to create
    try {
      AgentGraph result = apiInstance.postAgentGraph(ldAPIVersion, projectKey, agentGraphPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#postAgentGraph");
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
| **agentGraphPost** | [**AgentGraphPost**](AgentGraphPost.md)| Agent graph object to create | |

### Return type

[**AgentGraph**](AgentGraph.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Agent graph created |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **413** | Payload too large |  -  |
| **500** | Internal server error |  -  |

<a id="postModelConfig"></a>
# **postModelConfig**
> ModelConfig postModelConfig(projectKey, modelConfigPost)

Create an AI model config

Create an AI model config. You can use this in any variation for any AI Config in your project.

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
    String projectKey = "default"; // String | 
    ModelConfigPost modelConfigPost = new ModelConfigPost(); // ModelConfigPost | AI model config object to create
    try {
      ModelConfig result = apiInstance.postModelConfig(projectKey, modelConfigPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#postModelConfig");
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
| **modelConfigPost** | [**ModelConfigPost**](ModelConfigPost.md)| AI model config object to create | |

### Return type

[**ModelConfig**](ModelConfig.md)

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
| **500** | Internal server error |  -  |

<a id="postRestrictedModels"></a>
# **postRestrictedModels**
> RestrictedModelsResponse postRestrictedModels(projectKey, restrictedModelsRequest)

Add AI models to the restricted list

Add AI models, by key, to the restricted list. Keys are included in the response from the [List AI model configs](https://launchdarkly.com/docs/api/ai-configs/list-model-configs) endpoint.

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
    String projectKey = "default"; // String | 
    RestrictedModelsRequest restrictedModelsRequest = new RestrictedModelsRequest(); // RestrictedModelsRequest | List of AI model keys to add to the restricted list.
    try {
      RestrictedModelsResponse result = apiInstance.postRestrictedModels(projectKey, restrictedModelsRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsApi#postRestrictedModels");
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
| **restrictedModelsRequest** | [**RestrictedModelsRequest**](RestrictedModelsRequest.md)| List of AI model keys to add to the restricted list. | |

### Return type

[**RestrictedModelsResponse**](RestrictedModelsResponse.md)

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
| **500** | Internal server error |  -  |

