# InsightsChartsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getDeploymentFrequencyChart**](InsightsChartsBetaApi.md#getDeploymentFrequencyChart) | **GET** /api/v2/engineering-insights/charts/deployments/frequency | Get deployment frequency chart data |
| [**getFlagStatusChart**](InsightsChartsBetaApi.md#getFlagStatusChart) | **GET** /api/v2/engineering-insights/charts/flags/status | Get flag status chart data |
| [**getLeadTimeChart**](InsightsChartsBetaApi.md#getLeadTimeChart) | **GET** /api/v2/engineering-insights/charts/lead-time | Get lead time chart data |
| [**getReleaseFrequencyChart**](InsightsChartsBetaApi.md#getReleaseFrequencyChart) | **GET** /api/v2/engineering-insights/charts/releases/frequency | Get release frequency chart data |
| [**getStaleFlagsChart**](InsightsChartsBetaApi.md#getStaleFlagsChart) | **GET** /api/v2/engineering-insights/charts/flags/stale | Get stale flags chart data |


<a name="getDeploymentFrequencyChart"></a>
# **getDeploymentFrequencyChart**
> InsightsChart getDeploymentFrequencyChart(projectKey, environmentKey, applicationKey, from, to, bucketType, bucketMs, groupBy, expand)

Get deployment frequency chart data

Get deployment frequency chart data. Engineering insights displays deployment frequency data in the [deployment frequency metric view](https://launchdarkly.com/docs/home/observability/deployments).  ### Expanding the chart response  LaunchDarkly supports expanding the chart response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;metrics&#x60; includes details on the metrics related to deployment frequency  For example, use &#x60;?expand&#x3D;metrics&#x60; to include the &#x60;metrics&#x60; field in the response. By default, this field is **not** included in the response. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.InsightsChartsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    InsightsChartsBetaApi apiInstance = new InsightsChartsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String applicationKey = "applicationKey_example"; // String | Comma separated list of application keys
    OffsetDateTime from = OffsetDateTime.now(); // OffsetDateTime | Unix timestamp in milliseconds. Default value is 7 days ago.
    OffsetDateTime to = OffsetDateTime.now(); // OffsetDateTime | Unix timestamp in milliseconds. Default value is now.
    String bucketType = "bucketType_example"; // String | Specify type of bucket. Options: `rolling`, `hour`, `day`. Default: `rolling`.
    Long bucketMs = 56L; // Long | Duration of intervals for x-axis in milliseconds. Default value is one day (`86400000` milliseconds).
    String groupBy = "groupBy_example"; // String | Options: `application`, `kind`
    String expand = "expand_example"; // String | Options: `metrics`
    try {
      InsightsChart result = apiInstance.getDeploymentFrequencyChart(projectKey, environmentKey, applicationKey, from, to, bucketType, bucketMs, groupBy, expand);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsChartsBetaApi#getDeploymentFrequencyChart");
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
| **projectKey** | **String**| The project key | [optional] |
| **environmentKey** | **String**| The environment key | [optional] |
| **applicationKey** | **String**| Comma separated list of application keys | [optional] |
| **from** | **OffsetDateTime**| Unix timestamp in milliseconds. Default value is 7 days ago. | [optional] |
| **to** | **OffsetDateTime**| Unix timestamp in milliseconds. Default value is now. | [optional] |
| **bucketType** | **String**| Specify type of bucket. Options: &#x60;rolling&#x60;, &#x60;hour&#x60;, &#x60;day&#x60;. Default: &#x60;rolling&#x60;. | [optional] |
| **bucketMs** | **Long**| Duration of intervals for x-axis in milliseconds. Default value is one day (&#x60;86400000&#x60; milliseconds). | [optional] |
| **groupBy** | **String**| Options: &#x60;application&#x60;, &#x60;kind&#x60; | [optional] |
| **expand** | **String**| Options: &#x60;metrics&#x60; | [optional] |

### Return type

[**InsightsChart**](InsightsChart.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Chart response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getFlagStatusChart"></a>
# **getFlagStatusChart**
> InsightsChart getFlagStatusChart(projectKey, environmentKey, applicationKey)

Get flag status chart data

Get flag status chart data. To learn more, read [Flag statuses](https://launchdarkly.com/docs/home/observability/flag-health#flag-statuses).

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.InsightsChartsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    InsightsChartsBetaApi apiInstance = new InsightsChartsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String applicationKey = "applicationKey_example"; // String | Comma separated list of application keys
    try {
      InsightsChart result = apiInstance.getFlagStatusChart(projectKey, environmentKey, applicationKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsChartsBetaApi#getFlagStatusChart");
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
| **environmentKey** | **String**| The environment key | |
| **applicationKey** | **String**| Comma separated list of application keys | [optional] |

### Return type

[**InsightsChart**](InsightsChart.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Chart response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getLeadTimeChart"></a>
# **getLeadTimeChart**
> InsightsChart getLeadTimeChart(projectKey, environmentKey, applicationKey, from, to, bucketType, bucketMs, groupBy, expand)

Get lead time chart data

Get lead time chart data. The engineering insights UI displays lead time data in the [lead time metric view](https://launchdarkly.com/docs/home/observability/lead-time).

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.InsightsChartsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    InsightsChartsBetaApi apiInstance = new InsightsChartsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String applicationKey = "applicationKey_example"; // String | Comma separated list of application keys
    Long from = 56L; // Long | Unix timestamp in milliseconds. Default value is 7 days ago.
    Long to = 56L; // Long | Unix timestamp in milliseconds. Default value is now.
    String bucketType = "bucketType_example"; // String | Specify type of bucket. Options: `rolling`, `hour`, `day`. Default: `rolling`.
    Long bucketMs = 56L; // Long | Duration of intervals for x-axis in milliseconds. Default value is one day (`86400000` milliseconds).
    String groupBy = "groupBy_example"; // String | Options: `application`, `stage`. Default: `stage`.
    String expand = "expand_example"; // String | Options: `metrics`, `percentiles`.
    try {
      InsightsChart result = apiInstance.getLeadTimeChart(projectKey, environmentKey, applicationKey, from, to, bucketType, bucketMs, groupBy, expand);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsChartsBetaApi#getLeadTimeChart");
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
| **environmentKey** | **String**| The environment key | [optional] |
| **applicationKey** | **String**| Comma separated list of application keys | [optional] |
| **from** | **Long**| Unix timestamp in milliseconds. Default value is 7 days ago. | [optional] |
| **to** | **Long**| Unix timestamp in milliseconds. Default value is now. | [optional] |
| **bucketType** | **String**| Specify type of bucket. Options: &#x60;rolling&#x60;, &#x60;hour&#x60;, &#x60;day&#x60;. Default: &#x60;rolling&#x60;. | [optional] |
| **bucketMs** | **Long**| Duration of intervals for x-axis in milliseconds. Default value is one day (&#x60;86400000&#x60; milliseconds). | [optional] |
| **groupBy** | **String**| Options: &#x60;application&#x60;, &#x60;stage&#x60;. Default: &#x60;stage&#x60;. | [optional] |
| **expand** | **String**| Options: &#x60;metrics&#x60;, &#x60;percentiles&#x60;. | [optional] |

### Return type

[**InsightsChart**](InsightsChart.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Chart response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getReleaseFrequencyChart"></a>
# **getReleaseFrequencyChart**
> InsightsChart getReleaseFrequencyChart(projectKey, environmentKey, applicationKey, hasExperiments, global, groupBy, from, to, bucketType, bucketMs, expand)

Get release frequency chart data

Get release frequency chart data. Engineering insights displays release frequency data in the [release frequency metric view](https://launchdarkly.com/docs/home/observability/releases).

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.InsightsChartsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    InsightsChartsBetaApi apiInstance = new InsightsChartsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String applicationKey = "applicationKey_example"; // String | Comma separated list of application keys
    Boolean hasExperiments = true; // Boolean | Filter events to those associated with an experiment (`true`) or without an experiment (`false`)
    String global = "global_example"; // String | Filter to include or exclude global events. Default value is `include`. Options: `include`, `exclude`
    String groupBy = "groupBy_example"; // String | Property to group results by. Options: `impact`
    OffsetDateTime from = OffsetDateTime.now(); // OffsetDateTime | Unix timestamp in milliseconds. Default value is 7 days ago.
    OffsetDateTime to = OffsetDateTime.now(); // OffsetDateTime | Unix timestamp in milliseconds. Default value is now.
    String bucketType = "bucketType_example"; // String | Specify type of bucket. Options: `rolling`, `hour`, `day`. Default: `rolling`.
    Long bucketMs = 56L; // Long | Duration of intervals for x-axis in milliseconds. Default value is one day (`86400000` milliseconds).
    String expand = "expand_example"; // String | Options: `metrics`
    try {
      InsightsChart result = apiInstance.getReleaseFrequencyChart(projectKey, environmentKey, applicationKey, hasExperiments, global, groupBy, from, to, bucketType, bucketMs, expand);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsChartsBetaApi#getReleaseFrequencyChart");
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
| **environmentKey** | **String**| The environment key | |
| **applicationKey** | **String**| Comma separated list of application keys | [optional] |
| **hasExperiments** | **Boolean**| Filter events to those associated with an experiment (&#x60;true&#x60;) or without an experiment (&#x60;false&#x60;) | [optional] |
| **global** | **String**| Filter to include or exclude global events. Default value is &#x60;include&#x60;. Options: &#x60;include&#x60;, &#x60;exclude&#x60; | [optional] |
| **groupBy** | **String**| Property to group results by. Options: &#x60;impact&#x60; | [optional] |
| **from** | **OffsetDateTime**| Unix timestamp in milliseconds. Default value is 7 days ago. | [optional] |
| **to** | **OffsetDateTime**| Unix timestamp in milliseconds. Default value is now. | [optional] |
| **bucketType** | **String**| Specify type of bucket. Options: &#x60;rolling&#x60;, &#x60;hour&#x60;, &#x60;day&#x60;. Default: &#x60;rolling&#x60;. | [optional] |
| **bucketMs** | **Long**| Duration of intervals for x-axis in milliseconds. Default value is one day (&#x60;86400000&#x60; milliseconds). | [optional] |
| **expand** | **String**| Options: &#x60;metrics&#x60; | [optional] |

### Return type

[**InsightsChart**](InsightsChart.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Chart response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getStaleFlagsChart"></a>
# **getStaleFlagsChart**
> InsightsChart getStaleFlagsChart(projectKey, environmentKey, applicationKey, groupBy, maintainerId, maintainerTeamKey, expand)

Get stale flags chart data

Get stale flags chart data. Engineering insights displays stale flags data in the [flag health metric view](https://launchdarkly.com/docs/home/observability/flag-health).  ### Expanding the chart response  LaunchDarkly supports expanding the chart response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;metrics&#x60; includes details on the metrics related to stale flags  For example, use &#x60;?expand&#x3D;metrics&#x60; to include the &#x60;metrics&#x60; field in the response. By default, this field is **not** included in the response. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.InsightsChartsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    InsightsChartsBetaApi apiInstance = new InsightsChartsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String applicationKey = "applicationKey_example"; // String | Comma separated list of application keys
    String groupBy = "groupBy_example"; // String | Property to group results by. Options: `maintainer`
    String maintainerId = "maintainerId_example"; // String | Comma-separated list of individual maintainers to filter results.
    String maintainerTeamKey = "maintainerTeamKey_example"; // String | Comma-separated list of team maintainer keys to filter results.
    String expand = "expand_example"; // String | Options: `metrics`
    try {
      InsightsChart result = apiInstance.getStaleFlagsChart(projectKey, environmentKey, applicationKey, groupBy, maintainerId, maintainerTeamKey, expand);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsChartsBetaApi#getStaleFlagsChart");
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
| **environmentKey** | **String**| The environment key | |
| **applicationKey** | **String**| Comma separated list of application keys | [optional] |
| **groupBy** | **String**| Property to group results by. Options: &#x60;maintainer&#x60; | [optional] |
| **maintainerId** | **String**| Comma-separated list of individual maintainers to filter results. | [optional] |
| **maintainerTeamKey** | **String**| Comma-separated list of team maintainer keys to filter results. | [optional] |
| **expand** | **String**| Options: &#x60;metrics&#x60; | [optional] |

### Return type

[**InsightsChart**](InsightsChart.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Chart response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

