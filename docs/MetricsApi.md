# MetricsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteMetric**](MetricsApi.md#deleteMetric) | **DELETE** /api/v2/metrics/{projectKey}/{metricKey} | Delete metric |
| [**getMetric**](MetricsApi.md#getMetric) | **GET** /api/v2/metrics/{projectKey}/{metricKey} | Get metric |
| [**getMetrics**](MetricsApi.md#getMetrics) | **GET** /api/v2/metrics/{projectKey} | List metrics |
| [**patchMetric**](MetricsApi.md#patchMetric) | **PATCH** /api/v2/metrics/{projectKey}/{metricKey} | Update metric |
| [**postMetric**](MetricsApi.md#postMetric) | **POST** /api/v2/metrics/{projectKey} | Create metric |


<a name="deleteMetric"></a>
# **deleteMetric**
> deleteMetric(projectKey, metricKey)

Delete metric

Delete a metric by key.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.MetricsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    MetricsApi apiInstance = new MetricsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String metricKey = "metricKey_example"; // String | The metric key
    try {
      apiInstance.deleteMetric(projectKey, metricKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsApi#deleteMetric");
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
| **metricKey** | **String**| The metric key | |

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
| **204** | Action succeeded |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getMetric"></a>
# **getMetric**
> MetricRep getMetric(projectKey, metricKey, expand)

Get metric

Get information for a single metric from the specific project.  ### Expanding the metric response LaunchDarkly supports two fields for expanding the \&quot;Get metric\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  - &#x60;experiments&#x60; includes all experiments from the specific project that use the metric - &#x60;experimentCount&#x60; includes the number of experiments from the specific project that use the metric  For example, &#x60;expand&#x3D;experiments&#x60; includes the &#x60;experiments&#x60; field in the response. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.MetricsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    MetricsApi apiInstance = new MetricsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String metricKey = "metricKey_example"; // String | The metric key
    String expand = "expand_example"; // String | A comma-separated list of properties that can reveal additional information in the response.
    try {
      MetricRep result = apiInstance.getMetric(projectKey, metricKey, expand);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsApi#getMetric");
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
| **metricKey** | **String**| The metric key | |
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. | [optional] |

### Return type

[**MetricRep**](MetricRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Metric response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getMetrics"></a>
# **getMetrics**
> MetricCollectionRep getMetrics(projectKey, expand)

List metrics

Get a list of all metrics for the specified project.  ### Expanding the metric list response LaunchDarkly supports expanding the \&quot;List metrics\&quot; response. By default, the expandable field is **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add the following supported field:  - &#x60;experimentCount&#x60; includes the number of experiments from the specific project that use the metric  For example, &#x60;expand&#x3D;experimentCount&#x60; includes the &#x60;experimentCount&#x60; field for each metric in the response. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.MetricsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    MetricsApi apiInstance = new MetricsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String expand = "expand_example"; // String | A comma-separated list of properties that can reveal additional information in the response.
    try {
      MetricCollectionRep result = apiInstance.getMetrics(projectKey, expand);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsApi#getMetrics");
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
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. | [optional] |

### Return type

[**MetricCollectionRep**](MetricCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Metrics collection response |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |

<a name="patchMetric"></a>
# **patchMetric**
> MetricRep patchMetric(projectKey, metricKey, patchOperation)

Update metric

Patch a metric by key.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.MetricsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    MetricsApi apiInstance = new MetricsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String metricKey = "metricKey_example"; // String | The metric key
    List<PatchOperation> patchOperation = Arrays.asList(); // List<PatchOperation> | 
    try {
      MetricRep result = apiInstance.patchMetric(projectKey, metricKey, patchOperation);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsApi#patchMetric");
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
| **metricKey** | **String**| The metric key | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**MetricRep**](MetricRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Metric response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a name="postMetric"></a>
# **postMetric**
> MetricRep postMetric(projectKey, metricPost)

Create metric

Create a new metric in the specified project. The expected &#x60;POST&#x60; body differs depending on the specified &#x60;kind&#x60; property.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.MetricsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    MetricsApi apiInstance = new MetricsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    MetricPost metricPost = new MetricPost(); // MetricPost | 
    try {
      MetricRep result = apiInstance.postMetric(projectKey, metricPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsApi#postMetric");
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
| **metricPost** | [**MetricPost**](MetricPost.md)|  | |

### Return type

[**MetricRep**](MetricRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Metric response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

