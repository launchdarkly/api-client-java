# MetricsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createMetricGroup**](MetricsBetaApi.md#createMetricGroup) | **POST** /api/v2/projects/{projectKey}/metric-groups | Create metric group |
| [**deleteMetricGroup**](MetricsBetaApi.md#deleteMetricGroup) | **DELETE** /api/v2/projects/{projectKey}/metric-groups/{metricGroupKey} | Delete metric group |
| [**getMetricGroup**](MetricsBetaApi.md#getMetricGroup) | **GET** /api/v2/projects/{projectKey}/metric-groups/{metricGroupKey} | Get metric group |
| [**getMetricGroups**](MetricsBetaApi.md#getMetricGroups) | **GET** /api/v2/projects/{projectKey}/metric-groups | List metric groups |
| [**patchMetricGroup**](MetricsBetaApi.md#patchMetricGroup) | **PATCH** /api/v2/projects/{projectKey}/metric-groups/{metricGroupKey} | Patch metric group |


<a id="createMetricGroup"></a>
# **createMetricGroup**
> MetricGroupRep createMetricGroup(projectKey, metricGroupPost)

Create metric group

Create a new metric group in the specified project

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.MetricsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    MetricsBetaApi apiInstance = new MetricsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    MetricGroupPost metricGroupPost = new MetricGroupPost(); // MetricGroupPost | 
    try {
      MetricGroupRep result = apiInstance.createMetricGroup(projectKey, metricGroupPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsBetaApi#createMetricGroup");
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
| **metricGroupPost** | [**MetricGroupPost**](MetricGroupPost.md)|  | |

### Return type

[**MetricGroupRep**](MetricGroupRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Metric group response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **405** | Method not allowed |  -  |
| **429** | Rate limited |  -  |

<a id="deleteMetricGroup"></a>
# **deleteMetricGroup**
> deleteMetricGroup(projectKey, metricGroupKey)

Delete metric group

Delete a metric group by key.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.MetricsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    MetricsBetaApi apiInstance = new MetricsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String metricGroupKey = "metricGroupKey_example"; // String | The metric group key
    try {
      apiInstance.deleteMetricGroup(projectKey, metricGroupKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsBetaApi#deleteMetricGroup");
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
| **metricGroupKey** | **String**| The metric group key | |

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
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **405** | Method not allowed |  -  |
| **429** | Rate limited |  -  |

<a id="getMetricGroup"></a>
# **getMetricGroup**
> MetricGroupRep getMetricGroup(projectKey, metricGroupKey, expand)

Get metric group

Get information for a single metric group from the specific project.  ### Expanding the metric group response LaunchDarkly supports two fields for expanding the \&quot;Get metric group\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with either or both of the following fields:  - &#x60;experiments&#x60; includes all experiments from the specific project that use the metric group - &#x60;experimentCount&#x60; includes the number of experiments from the specific project that use the metric group  For example, &#x60;expand&#x3D;experiments&#x60; includes the &#x60;experiments&#x60; field in the response. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.MetricsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    MetricsBetaApi apiInstance = new MetricsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String metricGroupKey = "metricGroupKey_example"; // String | The metric group key
    String expand = "expand_example"; // String | A comma-separated list of properties that can reveal additional information in the response.
    try {
      MetricGroupRep result = apiInstance.getMetricGroup(projectKey, metricGroupKey, expand);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsBetaApi#getMetricGroup");
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
| **metricGroupKey** | **String**| The metric group key | |
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. | [optional] |

### Return type

[**MetricGroupRep**](MetricGroupRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Metric group response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **405** | Method not allowed |  -  |
| **429** | Rate limited |  -  |

<a id="getMetricGroups"></a>
# **getMetricGroups**
> MetricGroupCollectionRep getMetricGroups(projectKey, filter, sort, expand, limit, offset)

List metric groups

Get a list of all metric groups for the specified project.  ### Expanding the metric groups response LaunchDarkly supports one field for expanding the \&quot;Get metric groups\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with the following field:  - &#x60;experiments&#x60; includes all experiments from the specific project that use the metric group  For example, &#x60;expand&#x3D;experiments&#x60; includes the &#x60;experiments&#x60; field in the response.  ### Filtering metric groups  The &#x60;filter&#x60; parameter supports the following operators: &#x60;contains&#x60;, &#x60;equals&#x60;, &#x60;anyOf&#x60;.  #### Supported fields and operators  You can only filter certain fields in metrics when using the &#x60;filter&#x60; parameter. Additionally, you can only filter some fields with certain operators.  When you search for metrics, the &#x60;filter&#x60; parameter supports the following fields and operators:  |&lt;div style&#x3D;\&quot;width:120px\&quot;&gt;Field&lt;/div&gt; |Description |Supported operators | |---|---|---| | &#x60;experimentStatus&#x60; | The experiment&#39;s status. One of &#x60;not_started&#x60;, &#x60;running&#x60;, &#x60;stopped&#x60;, &#x60;started&#x60;. | &#x60;equals&#x60; | | &#x60;hasConnections&#x60; | Whether the metric group has connections to experiments or guarded rollouts. One of &#x60;true&#x60;, &#x60;false&#x60;. | &#x60;equals&#x60; | | &#x60;kind&#x60; | The metric group kind. One of &#x60;funnel&#x60;, &#x60;standard&#x60;. | &#x60;equals&#x60; | | &#x60;maintainerIds&#x60; | The metric maintainer IDs. | &#x60;anyOf&#x60; | | &#x60;maintainerTeamKey&#x60; | The metric maintainer team key. | &#x60;equals&#x60; | | &#x60;query&#x60; | A \&quot;fuzzy\&quot; search across metric group key and name. Supply a string or list of strings to the operator. | &#x60;equals&#x60; |  ### Sorting metric groups  LaunchDarkly supports the following fields for sorting:  - &#x60;name&#x60; sorts by metric group name. - &#x60;createdAt&#x60; sorts by the creation date of the metric group. - &#x60;connectionCount&#x60; sorts by the number of connections to experiments the metric group has.  By default, the sort is in ascending order. Use &#x60;-&#x60; to sort in descending order. For example, &#x60;?sort&#x3D;name&#x60; sorts the response by metric group name in ascending order, and &#x60;?sort&#x3D;-name&#x60; sorts in descending order.  #### Sample query  &#x60;filter&#x3D;experimentStatus equals &#39;not_started&#39; and query equals &#39;metric name&#39;&#x60; 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.MetricsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    MetricsBetaApi apiInstance = new MetricsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String filter = "filter_example"; // String | Accepts filter by `experimentStatus`, `query`, `kind`, `hasConnections`, `maintainerIds`, and `maintainerTeamKey`. Example: `filter=experimentStatus equals 'running' and query equals 'test'`.
    String sort = "sort_example"; // String | A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order. Read the endpoint description for a full list of available sort fields.
    String expand = "expand_example"; // String | A comma-separated list of properties that can reveal additional information in the response.
    Long limit = 56L; // Long | The number of metric groups to return in the response. Defaults to 20. Maximum limit is 50.
    Long offset = 56L; // Long | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and returns the next `limit` items.
    try {
      MetricGroupCollectionRep result = apiInstance.getMetricGroups(projectKey, filter, sort, expand, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsBetaApi#getMetricGroups");
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
| **filter** | **String**| Accepts filter by &#x60;experimentStatus&#x60;, &#x60;query&#x60;, &#x60;kind&#x60;, &#x60;hasConnections&#x60;, &#x60;maintainerIds&#x60;, and &#x60;maintainerTeamKey&#x60;. Example: &#x60;filter&#x3D;experimentStatus equals &#39;running&#39; and query equals &#39;test&#39;&#x60;. | [optional] |
| **sort** | **String**| A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order. Read the endpoint description for a full list of available sort fields. | [optional] |
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. | [optional] |
| **limit** | **Long**| The number of metric groups to return in the response. Defaults to 20. Maximum limit is 50. | [optional] |
| **offset** | **Long**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and returns the next &#x60;limit&#x60; items. | [optional] |

### Return type

[**MetricGroupCollectionRep**](MetricGroupCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Metric group collection response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **405** | Method not allowed |  -  |
| **429** | Rate limited |  -  |

<a id="patchMetricGroup"></a>
# **patchMetricGroup**
> MetricGroupRep patchMetricGroup(projectKey, metricGroupKey, patchOperation)

Patch metric group

Patch a metric group by key. Updating a metric group uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.MetricsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    MetricsBetaApi apiInstance = new MetricsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String metricGroupKey = "metricGroupKey_example"; // String | The metric group key
    List<PatchOperation> patchOperation = Arrays.asList(); // List<PatchOperation> | 
    try {
      MetricGroupRep result = apiInstance.patchMetricGroup(projectKey, metricGroupKey, patchOperation);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsBetaApi#patchMetricGroup");
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
| **metricGroupKey** | **String**| The metric group key | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**MetricGroupRep**](MetricGroupRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Metric group response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **405** | Method not allowed |  -  |
| **429** | Rate limited |  -  |

