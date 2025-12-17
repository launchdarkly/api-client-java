# AccountUsageBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getContextsClientsideUsage**](AccountUsageBetaApi.md#getContextsClientsideUsage) | **GET** /api/v2/usage/clientside-contexts | Get contexts clientside usage |
| [**getContextsServersideUsage**](AccountUsageBetaApi.md#getContextsServersideUsage) | **GET** /api/v2/usage/serverside-contexts | Get contexts serverside usage |
| [**getContextsTotalUsage**](AccountUsageBetaApi.md#getContextsTotalUsage) | **GET** /api/v2/usage/total-contexts | Get contexts total usage |
| [**getDataExportEventsUsage**](AccountUsageBetaApi.md#getDataExportEventsUsage) | **GET** /api/v2/usage/data-export-events | Get data export events usage |
| [**getEvaluationsUsage**](AccountUsageBetaApi.md#getEvaluationsUsage) | **GET** /api/v2/usage/evaluations/{projectKey}/{environmentKey}/{featureFlagKey} | Get evaluations usage |
| [**getEventsUsage**](AccountUsageBetaApi.md#getEventsUsage) | **GET** /api/v2/usage/events/{type} | Get events usage |
| [**getExperimentationEventsUsage**](AccountUsageBetaApi.md#getExperimentationEventsUsage) | **GET** /api/v2/usage/experimentation-events | Get experimentation events usage |
| [**getExperimentationKeysUsage**](AccountUsageBetaApi.md#getExperimentationKeysUsage) | **GET** /api/v2/usage/experimentation-keys | Get experimentation keys usage |
| [**getMAUClientsideUsage**](AccountUsageBetaApi.md#getMAUClientsideUsage) | **GET** /api/v2/usage/clientside-mau | Get MAU clientside usage |
| [**getMAUTotalUsage**](AccountUsageBetaApi.md#getMAUTotalUsage) | **GET** /api/v2/usage/total-mau | Get MAU total usage |
| [**getMauSdksByType**](AccountUsageBetaApi.md#getMauSdksByType) | **GET** /api/v2/usage/mau/sdks | Get MAU SDKs by type |
| [**getMauUsage**](AccountUsageBetaApi.md#getMauUsage) | **GET** /api/v2/usage/mau | Get MAU usage |
| [**getMauUsageByCategory**](AccountUsageBetaApi.md#getMauUsageByCategory) | **GET** /api/v2/usage/mau/bycategory | Get MAU usage by category |
| [**getObservabilityErrorsUsage**](AccountUsageBetaApi.md#getObservabilityErrorsUsage) | **GET** /api/v2/usage/observability/errors | Get observability errors usage |
| [**getObservabilityLogsUsage**](AccountUsageBetaApi.md#getObservabilityLogsUsage) | **GET** /api/v2/usage/observability/logs | Get observability logs usage |
| [**getObservabilitySessionsUsage**](AccountUsageBetaApi.md#getObservabilitySessionsUsage) | **GET** /api/v2/usage/observability/sessions | Get observability sessions usage |
| [**getObservabilityTracesUsage**](AccountUsageBetaApi.md#getObservabilityTracesUsage) | **GET** /api/v2/usage/observability/traces | Get observability traces usage |
| [**getServiceConnectionsUsage**](AccountUsageBetaApi.md#getServiceConnectionsUsage) | **GET** /api/v2/usage/service-connections | Get service connections usage |
| [**getStreamUsage**](AccountUsageBetaApi.md#getStreamUsage) | **GET** /api/v2/usage/streams/{source} | Get stream usage |
| [**getStreamUsageBySdkVersion**](AccountUsageBetaApi.md#getStreamUsageBySdkVersion) | **GET** /api/v2/usage/streams/{source}/bysdkversion | Get stream usage by SDK version |
| [**getStreamUsageSdkversion**](AccountUsageBetaApi.md#getStreamUsageSdkversion) | **GET** /api/v2/usage/streams/{source}/sdkversions | Get stream usage SDK versions |


<a id="getContextsClientsideUsage"></a>
# **getContextsClientsideUsage**
> SeriesListRep getContextsClientsideUsage(from, to, projectKey, environmentKey, contextKind, sdkName, anonymous, groupBy, aggregationType, granularity)

Get contexts clientside usage

Get a detailed time series of the number of context key usages observed by LaunchDarkly in your account, including non-primary context kinds. Use this for breakdowns that go beyond the primary-only aggregation of MAU endpoints. The counts reflect data reported by client-side SDKs.&lt;br/&gt;&lt;br/&gt;The supported granularity varies by aggregation type. The maximum time range is 365 days.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountUsageBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountUsageBetaApi apiInstance = new AccountUsageBetaApi(defaultClient);
    String from = "from_example"; // String | The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month.
    String to = "to_example"; // String | The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time.
    String projectKey = "projectKey_example"; // String | A project key to filter results by. Can be specified multiple times, one query parameter per project key.
    String environmentKey = "environmentKey_example"; // String | An environment key to filter results by. If specified, exactly one `projectKey` must be provided. Can be specified multiple times, one query parameter per environment key.
    String contextKind = "contextKind_example"; // String | A context kind to filter results by. Can be specified multiple times, one query parameter per context kind.
    String sdkName = "sdkName_example"; // String | An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name.
    String anonymous = "anonymous_example"; // String | An anonymous value to filter results by. Can be specified multiple times, one query parameter per anonymous value.<br/>Valid values: `true`, `false`.
    String groupBy = "groupBy_example"; // String | If specified, returns data for each distinct value of the given field. `contextKind` is always included as a grouping dimension. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.<br/>Valid values: `projectId`, `environmentId`, `sdkName`, `sdkAppId`, `anonymousV2`.
    String aggregationType = "aggregationType_example"; // String | Specifies the aggregation method. Defaults to `month_to_date`.<br/>Valid values: `month_to_date`, `incremental`, `rolling_30d`.
    String granularity = "granularity_example"; // String | Specifies the data granularity. Defaults to `daily`. Valid values depend on `aggregationType`: **month_to_date** supports `daily` and `monthly`; **incremental** and **rolling_30d** support `daily` only.
    try {
      SeriesListRep result = apiInstance.getContextsClientsideUsage(from, to, projectKey, environmentKey, contextKind, sdkName, anonymous, groupBy, aggregationType, granularity);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getContextsClientsideUsage");
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
| **from** | **String**| The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. | [optional] |
| **projectKey** | **String**| A project key to filter results by. Can be specified multiple times, one query parameter per project key. | [optional] |
| **environmentKey** | **String**| An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. | [optional] |
| **contextKind** | **String**| A context kind to filter results by. Can be specified multiple times, one query parameter per context kind. | [optional] |
| **sdkName** | **String**| An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name. | [optional] |
| **anonymous** | **String**| An anonymous value to filter results by. Can be specified multiple times, one query parameter per anonymous value.&lt;br/&gt;Valid values: &#x60;true&#x60;, &#x60;false&#x60;. | [optional] |
| **groupBy** | **String**| If specified, returns data for each distinct value of the given field. &#x60;contextKind&#x60; is always included as a grouping dimension. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;sdkName&#x60;, &#x60;sdkAppId&#x60;, &#x60;anonymousV2&#x60;. | [optional] |
| **aggregationType** | **String**| Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. | [optional] |
| **granularity** | **String**| Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |
| **503** | Service unavailable |  -  |

<a id="getContextsServersideUsage"></a>
# **getContextsServersideUsage**
> SeriesListRep getContextsServersideUsage(from, to, projectKey, environmentKey, contextKind, sdkName, anonymous, groupBy, aggregationType, granularity)

Get contexts serverside usage

Get a detailed time series of the number of context key usages observed by LaunchDarkly in your account, including non-primary context kinds. Use this for breakdowns that go beyond the primary-only aggregation of MAU endpoints. The counts reflect data reported by server-side SDKs.&lt;br/&gt;&lt;br/&gt;The supported granularity varies by aggregation type. The maximum time range is 365 days.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountUsageBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountUsageBetaApi apiInstance = new AccountUsageBetaApi(defaultClient);
    String from = "from_example"; // String | The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month.
    String to = "to_example"; // String | The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time.
    String projectKey = "projectKey_example"; // String | A project key to filter results by. Can be specified multiple times, one query parameter per project key.
    String environmentKey = "environmentKey_example"; // String | An environment key to filter results by. If specified, exactly one `projectKey` must be provided. Can be specified multiple times, one query parameter per environment key.
    String contextKind = "contextKind_example"; // String | A context kind to filter results by. Can be specified multiple times, one query parameter per context kind.
    String sdkName = "sdkName_example"; // String | An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name.
    String anonymous = "anonymous_example"; // String | An anonymous value to filter results by. Can be specified multiple times, one query parameter per anonymous value.<br/>Valid values: `true`, `false`.
    String groupBy = "groupBy_example"; // String | If specified, returns data for each distinct value of the given field. `contextKind` is always included as a grouping dimension. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.<br/>Valid values: `projectId`, `environmentId`, `sdkName`, `sdkAppId`, `anonymousV2`.
    String aggregationType = "aggregationType_example"; // String | Specifies the aggregation method. Defaults to `month_to_date`.<br/>Valid values: `month_to_date`, `incremental`, `rolling_30d`.
    String granularity = "granularity_example"; // String | Specifies the data granularity. Defaults to `daily`. Valid values depend on `aggregationType`: **month_to_date** supports `daily` and `monthly`; **incremental** and **rolling_30d** support `daily` only.
    try {
      SeriesListRep result = apiInstance.getContextsServersideUsage(from, to, projectKey, environmentKey, contextKind, sdkName, anonymous, groupBy, aggregationType, granularity);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getContextsServersideUsage");
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
| **from** | **String**| The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. | [optional] |
| **projectKey** | **String**| A project key to filter results by. Can be specified multiple times, one query parameter per project key. | [optional] |
| **environmentKey** | **String**| An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. | [optional] |
| **contextKind** | **String**| A context kind to filter results by. Can be specified multiple times, one query parameter per context kind. | [optional] |
| **sdkName** | **String**| An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name. | [optional] |
| **anonymous** | **String**| An anonymous value to filter results by. Can be specified multiple times, one query parameter per anonymous value.&lt;br/&gt;Valid values: &#x60;true&#x60;, &#x60;false&#x60;. | [optional] |
| **groupBy** | **String**| If specified, returns data for each distinct value of the given field. &#x60;contextKind&#x60; is always included as a grouping dimension. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;sdkName&#x60;, &#x60;sdkAppId&#x60;, &#x60;anonymousV2&#x60;. | [optional] |
| **aggregationType** | **String**| Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. | [optional] |
| **granularity** | **String**| Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |
| **503** | Service unavailable |  -  |

<a id="getContextsTotalUsage"></a>
# **getContextsTotalUsage**
> SeriesListRep getContextsTotalUsage(from, to, projectKey, environmentKey, contextKind, sdkName, sdkType, anonymous, groupBy, aggregationType, granularity)

Get contexts total usage

Get a detailed time series of the number of context key usages observed by LaunchDarkly in your account, including non-primary context kinds. Use this for breakdowns that go beyond the primary-only aggregation of MAU endpoints.&lt;br/&gt;&lt;br/&gt;The supported granularity varies by aggregation type. The maximum time range is 365 days.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountUsageBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountUsageBetaApi apiInstance = new AccountUsageBetaApi(defaultClient);
    String from = "from_example"; // String | The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month.
    String to = "to_example"; // String | The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time.
    String projectKey = "projectKey_example"; // String | A project key to filter results by. Can be specified multiple times, one query parameter per project key.
    String environmentKey = "environmentKey_example"; // String | An environment key to filter results by. If specified, exactly one `projectKey` must be provided. Can be specified multiple times, one query parameter per environment key.
    String contextKind = "contextKind_example"; // String | A context kind to filter results by. Can be specified multiple times, one query parameter per context kind.
    String sdkName = "sdkName_example"; // String | An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name.
    String sdkType = "sdkType_example"; // String | An SDK type to filter results by. Can be specified multiple times, one query parameter per SDK type.
    String anonymous = "anonymous_example"; // String | An anonymous value to filter results by. Can be specified multiple times, one query parameter per anonymous value.<br/>Valid values: `true`, `false`.
    String groupBy = "groupBy_example"; // String | If specified, returns data for each distinct value of the given field. `contextKind` is always included as a grouping dimension. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.<br/>Valid values: `projectId`, `environmentId`, `sdkName`, `sdkType`, `sdkAppId`, `anonymousV2`.
    String aggregationType = "aggregationType_example"; // String | Specifies the aggregation method. Defaults to `month_to_date`.<br/>Valid values: `month_to_date`, `incremental`, `rolling_30d`.
    String granularity = "granularity_example"; // String | Specifies the data granularity. Defaults to `daily`. Valid values depend on `aggregationType`: **month_to_date** supports `daily` and `monthly`; **incremental** and **rolling_30d** support `daily` only.
    try {
      SeriesListRep result = apiInstance.getContextsTotalUsage(from, to, projectKey, environmentKey, contextKind, sdkName, sdkType, anonymous, groupBy, aggregationType, granularity);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getContextsTotalUsage");
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
| **from** | **String**| The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. | [optional] |
| **projectKey** | **String**| A project key to filter results by. Can be specified multiple times, one query parameter per project key. | [optional] |
| **environmentKey** | **String**| An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. | [optional] |
| **contextKind** | **String**| A context kind to filter results by. Can be specified multiple times, one query parameter per context kind. | [optional] |
| **sdkName** | **String**| An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name. | [optional] |
| **sdkType** | **String**| An SDK type to filter results by. Can be specified multiple times, one query parameter per SDK type. | [optional] |
| **anonymous** | **String**| An anonymous value to filter results by. Can be specified multiple times, one query parameter per anonymous value.&lt;br/&gt;Valid values: &#x60;true&#x60;, &#x60;false&#x60;. | [optional] |
| **groupBy** | **String**| If specified, returns data for each distinct value of the given field. &#x60;contextKind&#x60; is always included as a grouping dimension. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;sdkName&#x60;, &#x60;sdkType&#x60;, &#x60;sdkAppId&#x60;, &#x60;anonymousV2&#x60;. | [optional] |
| **aggregationType** | **String**| Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. | [optional] |
| **granularity** | **String**| Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |
| **503** | Service unavailable |  -  |

<a id="getDataExportEventsUsage"></a>
# **getDataExportEventsUsage**
> SeriesListRep getDataExportEventsUsage(from, to, projectKey, environmentKey, eventKind, groupBy, aggregationType, granularity)

Get data export events usage

Get a time series array showing the number of data export events from your account. The supported granularity varies by aggregation type. The maximum time range is 365 days.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountUsageBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountUsageBetaApi apiInstance = new AccountUsageBetaApi(defaultClient);
    String from = "from_example"; // String | The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month.
    String to = "to_example"; // String | The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time.
    String projectKey = "projectKey_example"; // String | A project key to filter results by. Can be specified multiple times, one query parameter per project key.
    String environmentKey = "environmentKey_example"; // String | An environment key to filter results by. If specified, exactly one `projectKey` must be provided. Can be specified multiple times, one query parameter per environment key.
    String eventKind = "eventKind_example"; // String | An event kind to filter results by. Can be specified multiple times, one query parameter per event kind.
    String groupBy = "groupBy_example"; // String | If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.<br/>Valid values: `environmentId`, `eventKind`.
    String aggregationType = "aggregationType_example"; // String | Specifies the aggregation method. Defaults to `month_to_date`.<br/>Valid values: `month_to_date`, `incremental`.
    String granularity = "granularity_example"; // String | Specifies the data granularity. Defaults to `daily`. `monthly` granularity is only supported with the **month_to_date** aggregation type.<br/>Valid values: `daily`, `hourly`, `monthly`.
    try {
      SeriesListRep result = apiInstance.getDataExportEventsUsage(from, to, projectKey, environmentKey, eventKind, groupBy, aggregationType, granularity);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getDataExportEventsUsage");
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
| **from** | **String**| The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. | [optional] |
| **projectKey** | **String**| A project key to filter results by. Can be specified multiple times, one query parameter per project key. | [optional] |
| **environmentKey** | **String**| An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. | [optional] |
| **eventKind** | **String**| An event kind to filter results by. Can be specified multiple times, one query parameter per event kind. | [optional] |
| **groupBy** | **String**| If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;environmentId&#x60;, &#x60;eventKind&#x60;. | [optional] |
| **aggregationType** | **String**| Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;. | [optional] |
| **granularity** | **String**| Specifies the data granularity. Defaults to &#x60;daily&#x60;. &#x60;monthly&#x60; granularity is only supported with the **month_to_date** aggregation type.&lt;br/&gt;Valid values: &#x60;daily&#x60;, &#x60;hourly&#x60;, &#x60;monthly&#x60;. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |
| **503** | Service unavailable |  -  |

<a id="getEvaluationsUsage"></a>
# **getEvaluationsUsage**
> SeriesListRep getEvaluationsUsage(projectKey, environmentKey, featureFlagKey, from, to, tz)

Get evaluations usage

Get time-series arrays of the number of times a flag is evaluated, broken down by the variation that resulted from that evaluation. The granularity of the data depends on the age of the data requested. If the requested range is within the past two hours, minutely data is returned. If it is within the last two days, hourly data is returned. Otherwise, daily data is returned.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountUsageBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountUsageBetaApi apiInstance = new AccountUsageBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    String from = "from_example"; // String | The series of data returned starts from this timestamp. Defaults to 30 days ago.
    String to = "to_example"; // String | The series of data returned ends at this timestamp. Defaults to the current time.
    String tz = "tz_example"; // String | The timezone to use for breaks between days when returning daily data.
    try {
      SeriesListRep result = apiInstance.getEvaluationsUsage(projectKey, environmentKey, featureFlagKey, from, to, tz);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getEvaluationsUsage");
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
| **featureFlagKey** | **String**| The feature flag key | |
| **from** | **String**| The series of data returned starts from this timestamp. Defaults to 30 days ago. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp. Defaults to the current time. | [optional] |
| **tz** | **String**| The timezone to use for breaks between days when returning daily data. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="getEventsUsage"></a>
# **getEventsUsage**
> SeriesListRep getEventsUsage(type, from, to)

Get events usage

Get time-series arrays of the number of times a flag is evaluated, broken down by the variation that resulted from that evaluation. The granularity of the data depends on the age of the data requested. If the requested range is within the past two hours, minutely data is returned. If it is within the last two days, hourly data is returned. Otherwise, daily data is returned.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountUsageBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountUsageBetaApi apiInstance = new AccountUsageBetaApi(defaultClient);
    String type = "type_example"; // String | The type of event to retrieve. Must be either `received` or `published`.
    String from = "from_example"; // String | The series of data returned starts from this timestamp. Defaults to 24 hours ago.
    String to = "to_example"; // String | The series of data returned ends at this timestamp. Defaults to the current time.
    try {
      SeriesListRep result = apiInstance.getEventsUsage(type, from, to);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getEventsUsage");
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
| **type** | **String**| The type of event to retrieve. Must be either &#x60;received&#x60; or &#x60;published&#x60;. | |
| **from** | **String**| The series of data returned starts from this timestamp. Defaults to 24 hours ago. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp. Defaults to the current time. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="getExperimentationEventsUsage"></a>
# **getExperimentationEventsUsage**
> SeriesListRep getExperimentationEventsUsage(from, to, projectKey, environmentKey, eventKey, eventKind, groupBy, aggregationType, granularity)

Get experimentation events usage

Get a time series array showing the number of experimentation events from your account. The supported granularity varies by aggregation type. The maximum time range is 365 days.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountUsageBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountUsageBetaApi apiInstance = new AccountUsageBetaApi(defaultClient);
    String from = "from_example"; // String | The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month.
    String to = "to_example"; // String | The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time.
    String projectKey = "projectKey_example"; // String | A project key to filter results by. Can be specified multiple times, one query parameter per project key.
    String environmentKey = "environmentKey_example"; // String | An environment key to filter results by. If specified, exactly one `projectKey` must be provided. Can be specified multiple times, one query parameter per environment key.
    String eventKey = "eventKey_example"; // String | An event key to filter results by. Can be specified multiple times, one query parameter per event key.
    String eventKind = "eventKind_example"; // String | An event kind to filter results by. Can be specified multiple times, one query parameter per event kind.
    String groupBy = "groupBy_example"; // String | If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.<br/>Valid values: `environmentId`, `eventKey`, `eventKind`.
    String aggregationType = "aggregationType_example"; // String | Specifies the aggregation method. Defaults to `month_to_date`.<br/>Valid values: `month_to_date`, `incremental`.
    String granularity = "granularity_example"; // String | Specifies the data granularity. Defaults to `daily`. `monthly` granularity is only supported with the **month_to_date** aggregation type.<br/>Valid values: `daily`, `hourly`, `monthly`.
    try {
      SeriesListRep result = apiInstance.getExperimentationEventsUsage(from, to, projectKey, environmentKey, eventKey, eventKind, groupBy, aggregationType, granularity);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getExperimentationEventsUsage");
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
| **from** | **String**| The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. | [optional] |
| **projectKey** | **String**| A project key to filter results by. Can be specified multiple times, one query parameter per project key. | [optional] |
| **environmentKey** | **String**| An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. | [optional] |
| **eventKey** | **String**| An event key to filter results by. Can be specified multiple times, one query parameter per event key. | [optional] |
| **eventKind** | **String**| An event kind to filter results by. Can be specified multiple times, one query parameter per event kind. | [optional] |
| **groupBy** | **String**| If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;environmentId&#x60;, &#x60;eventKey&#x60;, &#x60;eventKind&#x60;. | [optional] |
| **aggregationType** | **String**| Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;. | [optional] |
| **granularity** | **String**| Specifies the data granularity. Defaults to &#x60;daily&#x60;. &#x60;monthly&#x60; granularity is only supported with the **month_to_date** aggregation type.&lt;br/&gt;Valid values: &#x60;daily&#x60;, &#x60;hourly&#x60;, &#x60;monthly&#x60;. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |
| **503** | Service unavailable |  -  |

<a id="getExperimentationKeysUsage"></a>
# **getExperimentationKeysUsage**
> SeriesListRep getExperimentationKeysUsage(from, to, projectKey, environmentKey, experimentId, groupBy, aggregationType, granularity)

Get experimentation keys usage

Get a time series array showing the number of experimentation keys from your account. The supported granularity varies by aggregation type. The maximum time range is 365 days.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountUsageBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountUsageBetaApi apiInstance = new AccountUsageBetaApi(defaultClient);
    String from = "from_example"; // String | The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month.
    String to = "to_example"; // String | The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time.
    String projectKey = "projectKey_example"; // String | A project key to filter results by. Can be specified multiple times, one query parameter per project key.
    String environmentKey = "environmentKey_example"; // String | An environment key to filter results by. If specified, exactly one `projectKey` must be provided. Can be specified multiple times, one query parameter per environment key.
    String experimentId = "experimentId_example"; // String | An experiment ID to filter results by. Can be specified multiple times, one query parameter per experiment ID.
    String groupBy = "groupBy_example"; // String | If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.<br/>Valid values: `projectId`, `environmentId`, `experimentId`.
    String aggregationType = "aggregationType_example"; // String | Specifies the aggregation method. Defaults to `month_to_date`.<br/>Valid values: `month_to_date`, `incremental`.
    String granularity = "granularity_example"; // String | Specifies the data granularity. Defaults to `daily`. `monthly` granularity is only supported with the **month_to_date** aggregation type.<br/>Valid values: `daily`, `hourly`, `monthly`.
    try {
      SeriesListRep result = apiInstance.getExperimentationKeysUsage(from, to, projectKey, environmentKey, experimentId, groupBy, aggregationType, granularity);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getExperimentationKeysUsage");
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
| **from** | **String**| The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. | [optional] |
| **projectKey** | **String**| A project key to filter results by. Can be specified multiple times, one query parameter per project key. | [optional] |
| **environmentKey** | **String**| An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. | [optional] |
| **experimentId** | **String**| An experiment ID to filter results by. Can be specified multiple times, one query parameter per experiment ID. | [optional] |
| **groupBy** | **String**| If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;experimentId&#x60;. | [optional] |
| **aggregationType** | **String**| Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;. | [optional] |
| **granularity** | **String**| Specifies the data granularity. Defaults to &#x60;daily&#x60;. &#x60;monthly&#x60; granularity is only supported with the **month_to_date** aggregation type.&lt;br/&gt;Valid values: &#x60;daily&#x60;, &#x60;hourly&#x60;, &#x60;monthly&#x60;. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |
| **503** | Service unavailable |  -  |

<a id="getMAUClientsideUsage"></a>
# **getMAUClientsideUsage**
> SeriesListRep getMAUClientsideUsage(from, to, projectKey, environmentKey, sdkName, anonymous, groupBy, aggregationType, granularity)

Get MAU clientside usage

Get a time series of the number of context key usages observed by LaunchDarkly in your account, for the primary context kind only. The counts reflect data reported from client-side SDKs.&lt;br/&gt;&lt;br/&gt;For past months, the primary context kind is fixed and reflects the last known primary kind for that month. For the current month, it may vary as new primary context kinds are observed.&lt;br/&gt;&lt;br/&gt;The supported granularity varies by aggregation type. The maximum time range is 365 days.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountUsageBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountUsageBetaApi apiInstance = new AccountUsageBetaApi(defaultClient);
    String from = "from_example"; // String | The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month.
    String to = "to_example"; // String | The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time.
    String projectKey = "projectKey_example"; // String | A project key to filter results by. Can be specified multiple times, one query parameter per project key.
    String environmentKey = "environmentKey_example"; // String | An environment key to filter results by. If specified, exactly one `projectKey` must be provided. Can be specified multiple times, one query parameter per environment key.
    String sdkName = "sdkName_example"; // String | An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name.
    String anonymous = "anonymous_example"; // String | An anonymous value to filter results by. Can be specified multiple times, one query parameter per anonymous value.<br/>Valid values: `true`, `false`.
    String groupBy = "groupBy_example"; // String | If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.<br/>Valid values: `projectId`, `environmentId`, `sdkName`, `sdkAppId`, `anonymousV2`.
    String aggregationType = "aggregationType_example"; // String | Specifies the aggregation method. Defaults to `month_to_date`.<br/>Valid values: `month_to_date`, `incremental`, `rolling_30d`.
    String granularity = "granularity_example"; // String | Specifies the data granularity. Defaults to `daily`. Valid values depend on `aggregationType`: **month_to_date** supports `daily` and `monthly`; **incremental** and **rolling_30d** support `daily` only.
    try {
      SeriesListRep result = apiInstance.getMAUClientsideUsage(from, to, projectKey, environmentKey, sdkName, anonymous, groupBy, aggregationType, granularity);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getMAUClientsideUsage");
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
| **from** | **String**| The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. | [optional] |
| **projectKey** | **String**| A project key to filter results by. Can be specified multiple times, one query parameter per project key. | [optional] |
| **environmentKey** | **String**| An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. | [optional] |
| **sdkName** | **String**| An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name. | [optional] |
| **anonymous** | **String**| An anonymous value to filter results by. Can be specified multiple times, one query parameter per anonymous value.&lt;br/&gt;Valid values: &#x60;true&#x60;, &#x60;false&#x60;. | [optional] |
| **groupBy** | **String**| If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;sdkName&#x60;, &#x60;sdkAppId&#x60;, &#x60;anonymousV2&#x60;. | [optional] |
| **aggregationType** | **String**| Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. | [optional] |
| **granularity** | **String**| Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |
| **503** | Service unavailable |  -  |

<a id="getMAUTotalUsage"></a>
# **getMAUTotalUsage**
> SeriesListRep getMAUTotalUsage(from, to, projectKey, environmentKey, sdkName, sdkType, anonymous, groupBy, aggregationType, granularity)

Get MAU total usage

Get a time series of the number of context key usages observed by LaunchDarkly in your account, for the primary context kind only.&lt;br/&gt;&lt;br/&gt;For past months, this reflects the context kind that was most recently marked as primary for that month. For the current month, the context kind may vary as new primary kinds are observed.&lt;br/&gt;&lt;br/&gt;The supported granularity varies by aggregation type. The maximum time range is 365 days.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountUsageBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountUsageBetaApi apiInstance = new AccountUsageBetaApi(defaultClient);
    String from = "from_example"; // String | The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month.
    String to = "to_example"; // String | The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time.
    String projectKey = "projectKey_example"; // String | A project key to filter results by. Can be specified multiple times, one query parameter per project key.
    String environmentKey = "environmentKey_example"; // String | An environment key to filter results by. If specified, exactly one `projectKey` must be provided. Can be specified multiple times, one query parameter per environment key.
    String sdkName = "sdkName_example"; // String | An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name.
    String sdkType = "sdkType_example"; // String | An SDK type to filter results by. Can be specified multiple times, one query parameter per SDK type.
    String anonymous = "anonymous_example"; // String | An anonymous value to filter results by. Can be specified multiple times, one query parameter per anonymous value.<br/>Valid values: `true`, `false`.
    String groupBy = "groupBy_example"; // String | If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.<br/>Valid values: `projectId`, `environmentId`, `sdkName`, `sdkType`, `sdkAppId`, `anonymousV2`.
    String aggregationType = "aggregationType_example"; // String | Specifies the aggregation method. Defaults to `month_to_date`.<br/>Valid values: `month_to_date`, `incremental`, `rolling_30d`.
    String granularity = "granularity_example"; // String | Specifies the data granularity. Defaults to `daily`. Valid values depend on `aggregationType`: **month_to_date** supports `daily` and `monthly`; **incremental** and **rolling_30d** support `daily` only.
    try {
      SeriesListRep result = apiInstance.getMAUTotalUsage(from, to, projectKey, environmentKey, sdkName, sdkType, anonymous, groupBy, aggregationType, granularity);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getMAUTotalUsage");
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
| **from** | **String**| The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. | [optional] |
| **projectKey** | **String**| A project key to filter results by. Can be specified multiple times, one query parameter per project key. | [optional] |
| **environmentKey** | **String**| An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. | [optional] |
| **sdkName** | **String**| An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name. | [optional] |
| **sdkType** | **String**| An SDK type to filter results by. Can be specified multiple times, one query parameter per SDK type. | [optional] |
| **anonymous** | **String**| An anonymous value to filter results by. Can be specified multiple times, one query parameter per anonymous value.&lt;br/&gt;Valid values: &#x60;true&#x60;, &#x60;false&#x60;. | [optional] |
| **groupBy** | **String**| If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;sdkName&#x60;, &#x60;sdkType&#x60;, &#x60;sdkAppId&#x60;, &#x60;anonymousV2&#x60;. | [optional] |
| **aggregationType** | **String**| Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. | [optional] |
| **granularity** | **String**| Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |
| **503** | Service unavailable |  -  |

<a id="getMauSdksByType"></a>
# **getMauSdksByType**
> SdkListRep getMauSdksByType(from, to, sdktype)

Get MAU SDKs by type

Get a list of SDKs. These are all of the SDKs that have connected to LaunchDarkly by monthly active users (MAU) in the requested time period.&lt;br/&gt;&lt;br/&gt;Endpoints for retrieving monthly active users (MAU) do not return information about active context instances. After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should not rely on this endpoint. To learn more, read [Account usage metrics](https://launchdarkly.com/docs/home/account/metrics).

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountUsageBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountUsageBetaApi apiInstance = new AccountUsageBetaApi(defaultClient);
    String from = "from_example"; // String | The data returned starts from this timestamp. Defaults to seven days ago. The timestamp is in Unix milliseconds, for example, 1656694800000.
    String to = "to_example"; // String | The data returned ends at this timestamp. Defaults to the current time. The timestamp is in Unix milliseconds, for example, 1657904400000.
    String sdktype = "sdktype_example"; // String | The type of SDK with monthly active users (MAU) to list. Must be either `client` or `server`.
    try {
      SdkListRep result = apiInstance.getMauSdksByType(from, to, sdktype);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getMauSdksByType");
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
| **from** | **String**| The data returned starts from this timestamp. Defaults to seven days ago. The timestamp is in Unix milliseconds, for example, 1656694800000. | [optional] |
| **to** | **String**| The data returned ends at this timestamp. Defaults to the current time. The timestamp is in Unix milliseconds, for example, 1657904400000. | [optional] |
| **sdktype** | **String**| The type of SDK with monthly active users (MAU) to list. Must be either &#x60;client&#x60; or &#x60;server&#x60;. | [optional] |

### Return type

[**SdkListRep**](SdkListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | MAU SDKs response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |

<a id="getMauUsage"></a>
# **getMauUsage**
> SeriesListRep getMauUsage(from, to, project, environment, sdktype, sdk, anonymous, groupby, aggregationType, contextKind)

Get MAU usage

Get a time-series array of the number of monthly active users (MAU) seen by LaunchDarkly from your account. The granularity is always daily.&lt;br/&gt;&lt;br/&gt;Endpoints for retrieving monthly active users (MAU) do not return information about active context instances. After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should not rely on this endpoint. To learn more, read [Account usage metrics](https://launchdarkly.com/docs/home/account/metrics).

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountUsageBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountUsageBetaApi apiInstance = new AccountUsageBetaApi(defaultClient);
    String from = "from_example"; // String | The series of data returned starts from this timestamp. Defaults to 30 days ago.
    String to = "to_example"; // String | The series of data returned ends at this timestamp. Defaults to the current time.
    String project = "project_example"; // String | A project key to filter results to. Can be specified multiple times, one query parameter per project key, to view data for multiple projects.
    String environment = "environment_example"; // String | An environment key to filter results to. When using this parameter, exactly one project key must also be set. Can be specified multiple times as separate query parameters to view data for multiple environments within a single project.
    String sdktype = "sdktype_example"; // String | An SDK type to filter results to. Can be specified multiple times, one query parameter per SDK type. Valid values: client, server
    String sdk = "sdk_example"; // String | An SDK name to filter results to. Can be specified multiple times, one query parameter per SDK.
    String anonymous = "anonymous_example"; // String | If specified, filters results to either anonymous or nonanonymous users.
    String groupby = "groupby_example"; // String | If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions (for example, to group by both project and SDK). Valid values: project, environment, sdktype, sdk, anonymous, contextKind, sdkAppId
    String aggregationType = "aggregationType_example"; // String | If specified, queries for rolling 30-day, month-to-date, or daily incremental counts. Default is rolling 30-day. Valid values: rolling_30d, month_to_date, daily_incremental
    String contextKind = "contextKind_example"; // String | Filters results to the specified context kinds. Can be specified multiple times, one query parameter per context kind. If not set, queries for the user context kind.
    try {
      SeriesListRep result = apiInstance.getMauUsage(from, to, project, environment, sdktype, sdk, anonymous, groupby, aggregationType, contextKind);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getMauUsage");
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
| **from** | **String**| The series of data returned starts from this timestamp. Defaults to 30 days ago. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp. Defaults to the current time. | [optional] |
| **project** | **String**| A project key to filter results to. Can be specified multiple times, one query parameter per project key, to view data for multiple projects. | [optional] |
| **environment** | **String**| An environment key to filter results to. When using this parameter, exactly one project key must also be set. Can be specified multiple times as separate query parameters to view data for multiple environments within a single project. | [optional] |
| **sdktype** | **String**| An SDK type to filter results to. Can be specified multiple times, one query parameter per SDK type. Valid values: client, server | [optional] |
| **sdk** | **String**| An SDK name to filter results to. Can be specified multiple times, one query parameter per SDK. | [optional] |
| **anonymous** | **String**| If specified, filters results to either anonymous or nonanonymous users. | [optional] |
| **groupby** | **String**| If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions (for example, to group by both project and SDK). Valid values: project, environment, sdktype, sdk, anonymous, contextKind, sdkAppId | [optional] |
| **aggregationType** | **String**| If specified, queries for rolling 30-day, month-to-date, or daily incremental counts. Default is rolling 30-day. Valid values: rolling_30d, month_to_date, daily_incremental | [optional] |
| **contextKind** | **String**| Filters results to the specified context kinds. Can be specified multiple times, one query parameter per context kind. If not set, queries for the user context kind. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |

<a id="getMauUsageByCategory"></a>
# **getMauUsageByCategory**
> SeriesListRep getMauUsageByCategory(from, to)

Get MAU usage by category

Get time-series arrays of the number of monthly active users (MAU) seen by LaunchDarkly from your account, broken down by the category of users. The category is either &#x60;browser&#x60;, &#x60;mobile&#x60;, or &#x60;backend&#x60;.&lt;br/&gt;&lt;br/&gt;Endpoints for retrieving monthly active users (MAU) do not return information about active context instances. After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should not rely on this endpoint. To learn more, read [Account usage metrics](https://launchdarkly.com/docs/home/account/metrics).

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountUsageBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountUsageBetaApi apiInstance = new AccountUsageBetaApi(defaultClient);
    String from = "from_example"; // String | The series of data returned starts from this timestamp. Defaults to 30 days ago.
    String to = "to_example"; // String | The series of data returned ends at this timestamp. Defaults to the current time.
    try {
      SeriesListRep result = apiInstance.getMauUsageByCategory(from, to);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getMauUsageByCategory");
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
| **from** | **String**| The series of data returned starts from this timestamp. Defaults to 30 days ago. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp. Defaults to the current time. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="getObservabilityErrorsUsage"></a>
# **getObservabilityErrorsUsage**
> SeriesListRep getObservabilityErrorsUsage(from, to, projectKey, granularity, aggregationType)

Get observability errors usage

Get time-series arrays of the number of observability errors. Supports &#x60;daily&#x60; and &#x60;monthly&#x60; granularity.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountUsageBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountUsageBetaApi apiInstance = new AccountUsageBetaApi(defaultClient);
    String from = "from_example"; // String | The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month.
    String to = "to_example"; // String | The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time.
    String projectKey = "projectKey_example"; // String | A project key to filter results by. Can be specified multiple times, one query parameter per project key.
    String granularity = "granularity_example"; // String | Specifies the data granularity. Defaults to `daily`. Valid values depend on `aggregationType`: **month_to_date** supports `daily` and `monthly`; **incremental** and **rolling_30d** support `daily` only.
    String aggregationType = "aggregationType_example"; // String | Specifies the aggregation method. Defaults to `month_to_date`.<br/>Valid values: `month_to_date`, `incremental`, `rolling_30d`.
    try {
      SeriesListRep result = apiInstance.getObservabilityErrorsUsage(from, to, projectKey, granularity, aggregationType);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getObservabilityErrorsUsage");
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
| **from** | **String**| The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. | [optional] |
| **projectKey** | **String**| A project key to filter results by. Can be specified multiple times, one query parameter per project key. | [optional] |
| **granularity** | **String**| Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. | [optional] |
| **aggregationType** | **String**| Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="getObservabilityLogsUsage"></a>
# **getObservabilityLogsUsage**
> SeriesListRep getObservabilityLogsUsage(from, to, projectKey, granularity, aggregationType)

Get observability logs usage

Get time-series arrays of the number of observability logs. Supports &#x60;daily&#x60; and &#x60;monthly&#x60; granularity.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountUsageBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountUsageBetaApi apiInstance = new AccountUsageBetaApi(defaultClient);
    String from = "from_example"; // String | The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month.
    String to = "to_example"; // String | The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time.
    String projectKey = "projectKey_example"; // String | A project key to filter results by. Can be specified multiple times, one query parameter per project key.
    String granularity = "granularity_example"; // String | Specifies the data granularity. Defaults to `daily`. Valid values depend on `aggregationType`: **month_to_date** supports `daily` and `monthly`; **incremental** and **rolling_30d** support `daily` only.
    String aggregationType = "aggregationType_example"; // String | Specifies the aggregation method. Defaults to `month_to_date`.<br/>Valid values: `month_to_date`, `incremental`, `rolling_30d`.
    try {
      SeriesListRep result = apiInstance.getObservabilityLogsUsage(from, to, projectKey, granularity, aggregationType);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getObservabilityLogsUsage");
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
| **from** | **String**| The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. | [optional] |
| **projectKey** | **String**| A project key to filter results by. Can be specified multiple times, one query parameter per project key. | [optional] |
| **granularity** | **String**| Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. | [optional] |
| **aggregationType** | **String**| Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="getObservabilitySessionsUsage"></a>
# **getObservabilitySessionsUsage**
> SeriesListRep getObservabilitySessionsUsage(from, to, projectKey, granularity, aggregationType)

Get observability sessions usage

Get time-series arrays of the number of observability sessions. Supports &#x60;daily&#x60; and &#x60;monthly&#x60; granularity.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountUsageBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountUsageBetaApi apiInstance = new AccountUsageBetaApi(defaultClient);
    String from = "from_example"; // String | The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month.
    String to = "to_example"; // String | The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time.
    String projectKey = "projectKey_example"; // String | A project key to filter results by. Can be specified multiple times, one query parameter per project key.
    String granularity = "granularity_example"; // String | Specifies the data granularity. Defaults to `daily`. Valid values depend on `aggregationType`: **month_to_date** supports `daily` and `monthly`; **incremental** and **rolling_30d** support `daily` only.
    String aggregationType = "aggregationType_example"; // String | Specifies the aggregation method. Defaults to `month_to_date`.<br/>Valid values: `month_to_date`, `incremental`, `rolling_30d`.
    try {
      SeriesListRep result = apiInstance.getObservabilitySessionsUsage(from, to, projectKey, granularity, aggregationType);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getObservabilitySessionsUsage");
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
| **from** | **String**| The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. | [optional] |
| **projectKey** | **String**| A project key to filter results by. Can be specified multiple times, one query parameter per project key. | [optional] |
| **granularity** | **String**| Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. | [optional] |
| **aggregationType** | **String**| Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="getObservabilityTracesUsage"></a>
# **getObservabilityTracesUsage**
> SeriesListRep getObservabilityTracesUsage(from, to, projectKey, granularity, aggregationType)

Get observability traces usage

Get time-series arrays of the number of observability traces. Supports &#x60;daily&#x60; and &#x60;monthly&#x60; granularity.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountUsageBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountUsageBetaApi apiInstance = new AccountUsageBetaApi(defaultClient);
    String from = "from_example"; // String | The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month.
    String to = "to_example"; // String | The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time.
    String projectKey = "projectKey_example"; // String | A project key to filter results by. Can be specified multiple times, one query parameter per project key.
    String granularity = "granularity_example"; // String | Specifies the data granularity. Defaults to `daily`. Valid values depend on `aggregationType`: **month_to_date** supports `daily` and `monthly`; **incremental** and **rolling_30d** support `daily` only.
    String aggregationType = "aggregationType_example"; // String | Specifies the aggregation method. Defaults to `month_to_date`.<br/>Valid values: `month_to_date`, `incremental`, `rolling_30d`.
    try {
      SeriesListRep result = apiInstance.getObservabilityTracesUsage(from, to, projectKey, granularity, aggregationType);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getObservabilityTracesUsage");
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
| **from** | **String**| The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. | [optional] |
| **projectKey** | **String**| A project key to filter results by. Can be specified multiple times, one query parameter per project key. | [optional] |
| **granularity** | **String**| Specifies the data granularity. Defaults to &#x60;daily&#x60;. Valid values depend on &#x60;aggregationType&#x60;: **month_to_date** supports &#x60;daily&#x60; and &#x60;monthly&#x60;; **incremental** and **rolling_30d** support &#x60;daily&#x60; only. | [optional] |
| **aggregationType** | **String**| Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;, &#x60;rolling_30d&#x60;. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="getServiceConnectionsUsage"></a>
# **getServiceConnectionsUsage**
> SeriesListRepFloat getServiceConnectionsUsage(from, to, projectKey, environmentKey, connectionType, relayVersion, sdkName, sdkVersion, sdkType, groupBy, aggregationType, granularity)

Get service connections usage

Get a time series array showing the number of service connection minutes from your account. The supported granularity varies by aggregation type. The maximum time range is 365 days.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountUsageBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountUsageBetaApi apiInstance = new AccountUsageBetaApi(defaultClient);
    String from = "from_example"; // String | The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month.
    String to = "to_example"; // String | The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time.
    String projectKey = "projectKey_example"; // String | A project key to filter results by. Can be specified multiple times, one query parameter per project key.
    String environmentKey = "environmentKey_example"; // String | An environment key to filter results by. If specified, exactly one `projectKey` must be provided. Can be specified multiple times, one query parameter per environment key.
    String connectionType = "connectionType_example"; // String | A connection type to filter results by. Can be specified multiple times, one query parameter per connection type.
    String relayVersion = "relayVersion_example"; // String | A relay version to filter results by. Can be specified multiple times, one query parameter per relay version.
    String sdkName = "sdkName_example"; // String | An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name.
    String sdkVersion = "sdkVersion_example"; // String | An SDK version to filter results by. Can be specified multiple times, one query parameter per SDK version.
    String sdkType = "sdkType_example"; // String | An SDK type to filter results by. Can be specified multiple times, one query parameter per SDK type.
    String groupBy = "groupBy_example"; // String | If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.<br/>Valid values: `projectId`, `environmentId`, `connectionType`, `relayVersion`, `sdkName`, `sdkVersion`, `sdkType`.
    String aggregationType = "aggregationType_example"; // String | Specifies the aggregation method. Defaults to `month_to_date`.<br/>Valid values: `month_to_date`, `incremental`.
    String granularity = "granularity_example"; // String | Specifies the data granularity. Defaults to `daily`. `monthly` granularity is only supported with the **month_to_date** aggregation type.<br/>Valid values: `daily`, `hourly`, `monthly`.
    try {
      SeriesListRepFloat result = apiInstance.getServiceConnectionsUsage(from, to, projectKey, environmentKey, connectionType, relayVersion, sdkName, sdkVersion, sdkType, groupBy, aggregationType, granularity);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getServiceConnectionsUsage");
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
| **from** | **String**| The series of data returned starts from this timestamp (Unix milliseconds). Defaults to the beginning of the current month. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp (Unix milliseconds). Defaults to the current time. | [optional] |
| **projectKey** | **String**| A project key to filter results by. Can be specified multiple times, one query parameter per project key. | [optional] |
| **environmentKey** | **String**| An environment key to filter results by. If specified, exactly one &#x60;projectKey&#x60; must be provided. Can be specified multiple times, one query parameter per environment key. | [optional] |
| **connectionType** | **String**| A connection type to filter results by. Can be specified multiple times, one query parameter per connection type. | [optional] |
| **relayVersion** | **String**| A relay version to filter results by. Can be specified multiple times, one query parameter per relay version. | [optional] |
| **sdkName** | **String**| An SDK name to filter results by. Can be specified multiple times, one query parameter per SDK name. | [optional] |
| **sdkVersion** | **String**| An SDK version to filter results by. Can be specified multiple times, one query parameter per SDK version. | [optional] |
| **sdkType** | **String**| An SDK type to filter results by. Can be specified multiple times, one query parameter per SDK type. | [optional] |
| **groupBy** | **String**| If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions, one query parameter per dimension.&lt;br/&gt;Valid values: &#x60;projectId&#x60;, &#x60;environmentId&#x60;, &#x60;connectionType&#x60;, &#x60;relayVersion&#x60;, &#x60;sdkName&#x60;, &#x60;sdkVersion&#x60;, &#x60;sdkType&#x60;. | [optional] |
| **aggregationType** | **String**| Specifies the aggregation method. Defaults to &#x60;month_to_date&#x60;.&lt;br/&gt;Valid values: &#x60;month_to_date&#x60;, &#x60;incremental&#x60;. | [optional] |
| **granularity** | **String**| Specifies the data granularity. Defaults to &#x60;daily&#x60;. &#x60;monthly&#x60; granularity is only supported with the **month_to_date** aggregation type.&lt;br/&gt;Valid values: &#x60;daily&#x60;, &#x60;hourly&#x60;, &#x60;monthly&#x60;. | [optional] |

### Return type

[**SeriesListRepFloat**](SeriesListRepFloat.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |
| **503** | Service unavailable |  -  |

<a id="getStreamUsage"></a>
# **getStreamUsage**
> SeriesListRep getStreamUsage(source, from, to, tz)

Get stream usage

Get a time-series array of the number of streaming connections to LaunchDarkly in each time period. The granularity of the data depends on the age of the data requested. If the requested range is within the past two hours, minutely data is returned. If it is within the last two days, hourly data is returned. Otherwise, daily data is returned.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountUsageBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountUsageBetaApi apiInstance = new AccountUsageBetaApi(defaultClient);
    String source = "source_example"; // String | The source of streaming connections to describe. Must be either `client` or `server`.
    String from = "from_example"; // String | The series of data returned starts from this timestamp. Defaults to 30 days ago.
    String to = "to_example"; // String | The series of data returned ends at this timestamp. Defaults to the current time.
    String tz = "tz_example"; // String | The timezone to use for breaks between days when returning daily data.
    try {
      SeriesListRep result = apiInstance.getStreamUsage(source, from, to, tz);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getStreamUsage");
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
| **source** | **String**| The source of streaming connections to describe. Must be either &#x60;client&#x60; or &#x60;server&#x60;. | |
| **from** | **String**| The series of data returned starts from this timestamp. Defaults to 30 days ago. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp. Defaults to the current time. | [optional] |
| **tz** | **String**| The timezone to use for breaks between days when returning daily data. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="getStreamUsageBySdkVersion"></a>
# **getStreamUsageBySdkVersion**
> SeriesListRep getStreamUsageBySdkVersion(source, from, to, tz, sdk, version)

Get stream usage by SDK version

Get multiple series of the number of streaming connections to LaunchDarkly in each time period, separated by SDK type and version. Information about each series is in the metadata array. The granularity of the data depends on the age of the data requested. If the requested range is within the past 2 hours, minutely data is returned. If it is within the last two days, hourly data is returned. Otherwise, daily data is returned.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountUsageBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountUsageBetaApi apiInstance = new AccountUsageBetaApi(defaultClient);
    String source = "source_example"; // String | The source of streaming connections to describe. Must be either `client` or `server`.
    String from = "from_example"; // String | The series of data returned starts from this timestamp. Defaults to 24 hours ago.
    String to = "to_example"; // String | The series of data returned ends at this timestamp. Defaults to the current time.
    String tz = "tz_example"; // String | The timezone to use for breaks between days when returning daily data.
    String sdk = "sdk_example"; // String | If included, this filters the returned series to only those that match this SDK name.
    String version = "version_example"; // String | If included, this filters the returned series to only those that match this SDK version.
    try {
      SeriesListRep result = apiInstance.getStreamUsageBySdkVersion(source, from, to, tz, sdk, version);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getStreamUsageBySdkVersion");
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
| **source** | **String**| The source of streaming connections to describe. Must be either &#x60;client&#x60; or &#x60;server&#x60;. | |
| **from** | **String**| The series of data returned starts from this timestamp. Defaults to 24 hours ago. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp. Defaults to the current time. | [optional] |
| **tz** | **String**| The timezone to use for breaks between days when returning daily data. | [optional] |
| **sdk** | **String**| If included, this filters the returned series to only those that match this SDK name. | [optional] |
| **version** | **String**| If included, this filters the returned series to only those that match this SDK version. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="getStreamUsageSdkversion"></a>
# **getStreamUsageSdkversion**
> SdkVersionListRep getStreamUsageSdkversion(source)

Get stream usage SDK versions

Get a list of SDK version objects, which contain an SDK name and version. These are all of the SDKs that have connected to LaunchDarkly from your account in the past 60 days.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountUsageBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountUsageBetaApi apiInstance = new AccountUsageBetaApi(defaultClient);
    String source = "source_example"; // String | The source of streaming connections to describe. Must be either `client` or `server`.
    try {
      SdkVersionListRep result = apiInstance.getStreamUsageSdkversion(source);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getStreamUsageSdkversion");
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
| **source** | **String**| The source of streaming connections to describe. Must be either &#x60;client&#x60; or &#x60;server&#x60;. | |

### Return type

[**SdkVersionListRep**](SdkVersionListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | SDK Versions response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |

