# AiConfigsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteAIConfig**](AiConfigsBetaApi.md#deleteAIConfig) | **DELETE** /api/v2/projects/{projectKey}/ai-configs/{configKey} | Delete AI Config |
| [**deleteAIConfigVariation**](AiConfigsBetaApi.md#deleteAIConfigVariation) | **DELETE** /api/v2/projects/{projectKey}/ai-configs/{configKey}/variations/{variationKey} | Delete AI Config variation |
| [**deleteModelConfig**](AiConfigsBetaApi.md#deleteModelConfig) | **DELETE** /api/v2/projects/{projectKey}/ai-configs/model-configs/{modelConfigKey} | Delete an AI model config |
| [**getAIConfig**](AiConfigsBetaApi.md#getAIConfig) | **GET** /api/v2/projects/{projectKey}/ai-configs/{configKey} | Get AI Config |
| [**getAIConfigMetrics**](AiConfigsBetaApi.md#getAIConfigMetrics) | **GET** /api/v2/projects/{projectKey}/ai-configs/{configKey}/metrics | Get AI Config metrics |
| [**getAIConfigMetricsByVariation**](AiConfigsBetaApi.md#getAIConfigMetricsByVariation) | **GET** /api/v2/projects/{projectKey}/ai-configs/{configKey}/metrics-by-variation | Get AI Config metrics by variation |
| [**getAIConfigVariation**](AiConfigsBetaApi.md#getAIConfigVariation) | **GET** /api/v2/projects/{projectKey}/ai-configs/{configKey}/variations/{variationKey} | Get AI Config variation |
| [**getAIConfigs**](AiConfigsBetaApi.md#getAIConfigs) | **GET** /api/v2/projects/{projectKey}/ai-configs | List AI Configs |
| [**getModelConfig**](AiConfigsBetaApi.md#getModelConfig) | **GET** /api/v2/projects/{projectKey}/ai-configs/model-configs/{modelConfigKey} | Get AI model config |
| [**listModelConfigs**](AiConfigsBetaApi.md#listModelConfigs) | **GET** /api/v2/projects/{projectKey}/ai-configs/model-configs | List AI model configs |
| [**patchAIConfig**](AiConfigsBetaApi.md#patchAIConfig) | **PATCH** /api/v2/projects/{projectKey}/ai-configs/{configKey} | Update AI Config |
| [**patchAIConfigVariation**](AiConfigsBetaApi.md#patchAIConfigVariation) | **PATCH** /api/v2/projects/{projectKey}/ai-configs/{configKey}/variations/{variationKey} | Update AI Config variation |
| [**postAIConfig**](AiConfigsBetaApi.md#postAIConfig) | **POST** /api/v2/projects/{projectKey}/ai-configs | Create new AI Config |
| [**postAIConfigVariation**](AiConfigsBetaApi.md#postAIConfigVariation) | **POST** /api/v2/projects/{projectKey}/ai-configs/{configKey}/variations | Create AI Config variation |
| [**postModelConfig**](AiConfigsBetaApi.md#postModelConfig) | **POST** /api/v2/projects/{projectKey}/ai-configs/model-configs | Create an AI model config |


<a name="deleteAIConfig"></a>
# **deleteAIConfig**
> deleteAIConfig(ldAPIVersion, projectKey, configKey)

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
import com.launchdarkly.api.api.AiConfigsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AiConfigsBetaApi apiInstance = new AiConfigsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | 
    String configKey = "configKey_example"; // String | 
    try {
      apiInstance.deleteAIConfig(ldAPIVersion, projectKey, configKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsBetaApi#deleteAIConfig");
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

<a name="deleteAIConfigVariation"></a>
# **deleteAIConfigVariation**
> deleteAIConfigVariation(ldAPIVersion, projectKey, configKey, variationKey)

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
import com.launchdarkly.api.api.AiConfigsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AiConfigsBetaApi apiInstance = new AiConfigsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "projectKey_example"; // String | 
    String configKey = "configKey_example"; // String | 
    String variationKey = "variationKey_example"; // String | 
    try {
      apiInstance.deleteAIConfigVariation(ldAPIVersion, projectKey, configKey, variationKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsBetaApi#deleteAIConfigVariation");
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

<a name="deleteModelConfig"></a>
# **deleteModelConfig**
> deleteModelConfig(ldAPIVersion, projectKey, modelConfigKey)

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
import com.launchdarkly.api.api.AiConfigsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AiConfigsBetaApi apiInstance = new AiConfigsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | 
    String modelConfigKey = "modelConfigKey_example"; // String | 
    try {
      apiInstance.deleteModelConfig(ldAPIVersion, projectKey, modelConfigKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsBetaApi#deleteModelConfig");
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

<a name="getAIConfig"></a>
# **getAIConfig**
> AIConfig getAIConfig(ldAPIVersion, projectKey, configKey)

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
import com.launchdarkly.api.api.AiConfigsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AiConfigsBetaApi apiInstance = new AiConfigsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "projectKey_example"; // String | 
    String configKey = "configKey_example"; // String | 
    try {
      AIConfig result = apiInstance.getAIConfig(ldAPIVersion, projectKey, configKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsBetaApi#getAIConfig");
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

<a name="getAIConfigMetrics"></a>
# **getAIConfigMetrics**
> Metrics getAIConfigMetrics(ldAPIVersion, projectKey, configKey, from, to, env)

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
import com.launchdarkly.api.api.AiConfigsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AiConfigsBetaApi apiInstance = new AiConfigsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "projectKey_example"; // String | 
    String configKey = "configKey_example"; // String | 
    Integer from = 56; // Integer | The starting time, as milliseconds since epoch (inclusive).
    Integer to = 56; // Integer | The ending time, as milliseconds since epoch (exclusive). May not be more than 100 days after `from`.
    String env = "env_example"; // String | An environment key. Only metrics from this environment will be included.
    try {
      Metrics result = apiInstance.getAIConfigMetrics(ldAPIVersion, projectKey, configKey, from, to, env);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsBetaApi#getAIConfigMetrics");
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

<a name="getAIConfigMetricsByVariation"></a>
# **getAIConfigMetricsByVariation**
> List&lt;MetricByVariation&gt; getAIConfigMetricsByVariation(ldAPIVersion, projectKey, configKey, from, to, env)

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
import com.launchdarkly.api.api.AiConfigsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AiConfigsBetaApi apiInstance = new AiConfigsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "projectKey_example"; // String | 
    String configKey = "configKey_example"; // String | 
    Integer from = 56; // Integer | The starting time, as milliseconds since epoch (inclusive).
    Integer to = 56; // Integer | The ending time, as milliseconds since epoch (exclusive). May not be more than 100 days after `from`.
    String env = "env_example"; // String | An environment key. Only metrics from this environment will be included.
    try {
      List<MetricByVariation> result = apiInstance.getAIConfigMetricsByVariation(ldAPIVersion, projectKey, configKey, from, to, env);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsBetaApi#getAIConfigMetricsByVariation");
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

<a name="getAIConfigVariation"></a>
# **getAIConfigVariation**
> AIConfigVariationsResponse getAIConfigVariation(ldAPIVersion, projectKey, configKey, variationKey)

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
import com.launchdarkly.api.api.AiConfigsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AiConfigsBetaApi apiInstance = new AiConfigsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | 
    String configKey = "default"; // String | 
    String variationKey = "default"; // String | 
    try {
      AIConfigVariationsResponse result = apiInstance.getAIConfigVariation(ldAPIVersion, projectKey, configKey, variationKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsBetaApi#getAIConfigVariation");
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

<a name="getAIConfigs"></a>
# **getAIConfigs**
> AIConfigs getAIConfigs(ldAPIVersion, projectKey, sort, limit, offset, filter)

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
import com.launchdarkly.api.api.AiConfigsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AiConfigsBetaApi apiInstance = new AiConfigsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | 
    String sort = "sort_example"; // String | A sort to apply to the list of AI Configs.
    Integer limit = 56; // Integer | The number of AI Configs to return.
    Integer offset = 56; // Integer | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    String filter = "filter_example"; // String | A filter to apply to the list of AI Configs.
    try {
      AIConfigs result = apiInstance.getAIConfigs(ldAPIVersion, projectKey, sort, limit, offset, filter);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsBetaApi#getAIConfigs");
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

<a name="getModelConfig"></a>
# **getModelConfig**
> ModelConfig getModelConfig(ldAPIVersion, projectKey, modelConfigKey)

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
import com.launchdarkly.api.api.AiConfigsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AiConfigsBetaApi apiInstance = new AiConfigsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | 
    String modelConfigKey = "default"; // String | 
    try {
      ModelConfig result = apiInstance.getModelConfig(ldAPIVersion, projectKey, modelConfigKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsBetaApi#getModelConfig");
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

<a name="listModelConfigs"></a>
# **listModelConfigs**
> List&lt;ModelConfig&gt; listModelConfigs(ldAPIVersion, projectKey)

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
import com.launchdarkly.api.api.AiConfigsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AiConfigsBetaApi apiInstance = new AiConfigsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | 
    try {
      List<ModelConfig> result = apiInstance.listModelConfigs(ldAPIVersion, projectKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsBetaApi#listModelConfigs");
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

<a name="patchAIConfig"></a>
# **patchAIConfig**
> AIConfig patchAIConfig(ldAPIVersion, projectKey, configKey, aiConfigPatch)

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
import com.launchdarkly.api.api.AiConfigsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AiConfigsBetaApi apiInstance = new AiConfigsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "projectKey_example"; // String | 
    String configKey = "configKey_example"; // String | 
    AIConfigPatch aiConfigPatch = new AIConfigPatch(); // AIConfigPatch | AI Config object to update
    try {
      AIConfig result = apiInstance.patchAIConfig(ldAPIVersion, projectKey, configKey, aiConfigPatch);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsBetaApi#patchAIConfig");
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

<a name="patchAIConfigVariation"></a>
# **patchAIConfigVariation**
> AIConfigVariation patchAIConfigVariation(ldAPIVersion, projectKey, configKey, variationKey, aiConfigVariationPatch)

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
import com.launchdarkly.api.api.AiConfigsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AiConfigsBetaApi apiInstance = new AiConfigsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "projectKey_example"; // String | 
    String configKey = "configKey_example"; // String | 
    String variationKey = "variationKey_example"; // String | 
    AIConfigVariationPatch aiConfigVariationPatch = new AIConfigVariationPatch(); // AIConfigVariationPatch | AI Config variation object to update
    try {
      AIConfigVariation result = apiInstance.patchAIConfigVariation(ldAPIVersion, projectKey, configKey, variationKey, aiConfigVariationPatch);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsBetaApi#patchAIConfigVariation");
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

<a name="postAIConfig"></a>
# **postAIConfig**
> AIConfig postAIConfig(ldAPIVersion, projectKey, aiConfigPost)

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
import com.launchdarkly.api.api.AiConfigsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AiConfigsBetaApi apiInstance = new AiConfigsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "projectKey_example"; // String | 
    AIConfigPost aiConfigPost = new AIConfigPost(); // AIConfigPost | AI Config object to create
    try {
      AIConfig result = apiInstance.postAIConfig(ldAPIVersion, projectKey, aiConfigPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsBetaApi#postAIConfig");
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

<a name="postAIConfigVariation"></a>
# **postAIConfigVariation**
> AIConfigVariation postAIConfigVariation(ldAPIVersion, projectKey, configKey, aiConfigVariationPost)

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
import com.launchdarkly.api.api.AiConfigsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AiConfigsBetaApi apiInstance = new AiConfigsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "projectKey_example"; // String | 
    String configKey = "configKey_example"; // String | 
    AIConfigVariationPost aiConfigVariationPost = new AIConfigVariationPost(); // AIConfigVariationPost | AI Config variation object to create
    try {
      AIConfigVariation result = apiInstance.postAIConfigVariation(ldAPIVersion, projectKey, configKey, aiConfigVariationPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsBetaApi#postAIConfigVariation");
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

<a name="postModelConfig"></a>
# **postModelConfig**
> ModelConfig postModelConfig(ldAPIVersion, projectKey, modelConfigPost)

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
import com.launchdarkly.api.api.AiConfigsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AiConfigsBetaApi apiInstance = new AiConfigsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | 
    ModelConfigPost modelConfigPost = new ModelConfigPost(); // ModelConfigPost | AI model config object to create
    try {
      ModelConfig result = apiInstance.postModelConfig(ldAPIVersion, projectKey, modelConfigPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiConfigsBetaApi#postModelConfig");
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

