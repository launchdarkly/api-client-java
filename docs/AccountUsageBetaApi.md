# AccountUsageBetaApi

All URIs are relative to *https://app.launchdarkly.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getEvaluationsUsage**](AccountUsageBetaApi.md#getEvaluationsUsage) | **GET** /api/v2/usage/evaluations/{projKey}/{envKey}/{flagKey} | Get evaluations usage
[**getEventsUsage**](AccountUsageBetaApi.md#getEventsUsage) | **GET** /api/v2/usage/events/{type} | Get events usage
[**getMauSdksByType**](AccountUsageBetaApi.md#getMauSdksByType) | **GET** /api/v2/usage/mau/sdks | Get MAU SDKs by type
[**getMauUsage**](AccountUsageBetaApi.md#getMauUsage) | **GET** /api/v2/usage/mau | Get MAU usage
[**getMauUsageByCategory**](AccountUsageBetaApi.md#getMauUsageByCategory) | **GET** /api/v2/usage/mau/bycategory | Get MAU usage by category
[**getStreamUsage**](AccountUsageBetaApi.md#getStreamUsage) | **GET** /api/v2/usage/streams/{source} | Get stream usage
[**getStreamUsageBySdkVersion**](AccountUsageBetaApi.md#getStreamUsageBySdkVersion) | **GET** /api/v2/usage/streams/{source}/bysdkversion | Get stream usage by SDK version
[**getStreamUsageSdkversion**](AccountUsageBetaApi.md#getStreamUsageSdkversion) | **GET** /api/v2/usage/streams/{source}/sdkversions | Get stream usage SDK versions


<a name="getEvaluationsUsage"></a>
# **getEvaluationsUsage**
> SeriesListRep getEvaluationsUsage(projKey, envKey, flagKey, from, to, tz)

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
    String projKey = "projKey_example"; // String | The project key.
    String envKey = "envKey_example"; // String | The environment key.
    String flagKey = "flagKey_example"; // String | The feature flag's key.
    String from = "from_example"; // String | The series of data returned starts from this timestamp. Defaults to 30 days ago.
    String to = "to_example"; // String | The series of data returned ends at this timestamp. Defaults to the current time.
    String tz = "tz_example"; // String | The timezone to use for breaks between days when returning daily data.
    try {
      SeriesListRep result = apiInstance.getEvaluationsUsage(projKey, envKey, flagKey, from, to, tz);
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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projKey** | **String**| The project key. |
 **envKey** | **String**| The environment key. |
 **flagKey** | **String**| The feature flag&#39;s key. |
 **from** | **String**| The series of data returned starts from this timestamp. Defaults to 30 days ago. | [optional]
 **to** | **String**| The series of data returned ends at this timestamp. Defaults to the current time. | [optional]
 **tz** | **String**| The timezone to use for breaks between days when returning daily data. | [optional]

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
**200** | Usage response |  -  |
**400** | Invalid request |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

<a name="getEventsUsage"></a>
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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **type** | **String**| The type of event to retrieve. Must be either &#x60;received&#x60; or &#x60;published&#x60;. |
 **from** | **String**| The series of data returned starts from this timestamp. Defaults to 24 hours ago. | [optional]
 **to** | **String**| The series of data returned ends at this timestamp. Defaults to the current time. | [optional]

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
**200** | Usage response |  -  |
**400** | Invalid request |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

<a name="getMauSdksByType"></a>
# **getMauSdksByType**
> SdkListRep getMauSdksByType(from, to, sdktype)

Get MAU SDKs by type

Get a list of SDKs. These are all of the SDKs that have connected to LaunchDarkly by monthly active users (MAU) in the requested time period.

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
    String from = "from_example"; // String | The series of data returned starts from this timestamp. Defaults to seven days ago.
    String to = "to_example"; // String | The series of data returned ends at this timestamp. Defaults to the current time.
    String sdktype = "sdktype_example"; // String | The type of SDK with monthly active users (MAU) to list. Must be either `client` or `server`
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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **from** | **String**| The series of data returned starts from this timestamp. Defaults to seven days ago. | [optional]
 **to** | **String**| The series of data returned ends at this timestamp. Defaults to the current time. | [optional]
 **sdktype** | **String**| The type of SDK with monthly active users (MAU) to list. Must be either &#x60;client&#x60; or &#x60;server&#x60; | [optional]

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
**200** | MAU SDKs response |  -  |
**400** | Invalid request |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**429** | Rate limited |  -  |

<a name="getMauUsage"></a>
# **getMauUsage**
> SeriesListRep getMauUsage(from, to, project, environment, sdktype, sdk, anonymous, groupby)

Get MAU usage

Get a time-series array of the number of monthly active users (MAU) seen by LaunchDarkly from your account. The granularity is always daily.

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
    String groupby = "groupby_example"; // String | If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions (for example, to group by both project and SDK). Valid values: project, environment, sdktype, sdk, anonymous
    try {
      SeriesListRep result = apiInstance.getMauUsage(from, to, project, environment, sdktype, sdk, anonymous, groupby);
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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **from** | **String**| The series of data returned starts from this timestamp. Defaults to 30 days ago. | [optional]
 **to** | **String**| The series of data returned ends at this timestamp. Defaults to the current time. | [optional]
 **project** | **String**| A project key to filter results to. Can be specified multiple times, one query parameter per project key, to view data for multiple projects. | [optional]
 **environment** | **String**| An environment key to filter results to. When using this parameter, exactly one project key must also be set. Can be specified multiple times as separate query parameters to view data for multiple environments within a single project. | [optional]
 **sdktype** | **String**| An SDK type to filter results to. Can be specified multiple times, one query parameter per SDK type. Valid values: client, server | [optional]
 **sdk** | **String**| An SDK name to filter results to. Can be specified multiple times, one query parameter per SDK. | [optional]
 **anonymous** | **String**| If specified, filters results to either anonymous or nonanonymous users. | [optional]
 **groupby** | **String**| If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions (for example, to group by both project and SDK). Valid values: project, environment, sdktype, sdk, anonymous | [optional]

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
**200** | Usage response |  -  |
**400** | Invalid request |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**429** | Rate limited |  -  |

<a name="getMauUsageByCategory"></a>
# **getMauUsageByCategory**
> SeriesListRep getMauUsageByCategory(from, to)

Get MAU usage by category

Get time-series arrays of the number of monthly active users (MAU) seen by LaunchDarkly from your account, broken down by the category of users. The category is either &#x60;browser&#x60;, &#x60;mobile&#x60;, or &#x60;backend&#x60;.

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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **from** | **String**| The series of data returned starts from this timestamp. Defaults to 30 days ago. | [optional]
 **to** | **String**| The series of data returned ends at this timestamp. Defaults to the current time. | [optional]

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
**200** | Usage response |  -  |
**400** | Invalid request |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

<a name="getStreamUsage"></a>
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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **source** | **String**| The source of streaming connections to describe. Must be either &#x60;client&#x60; or &#x60;server&#x60;. |
 **from** | **String**| The series of data returned starts from this timestamp. Defaults to 30 days ago. | [optional]
 **to** | **String**| The series of data returned ends at this timestamp. Defaults to the current time. | [optional]
 **tz** | **String**| The timezone to use for breaks between days when returning daily data. | [optional]

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
**200** | Usage response |  -  |
**400** | Invalid request |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

<a name="getStreamUsageBySdkVersion"></a>
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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **source** | **String**| The source of streaming connections to describe. Must be either &#x60;client&#x60; or &#x60;server&#x60;. |
 **from** | **String**| The series of data returned starts from this timestamp. Defaults to 24 hours ago. | [optional]
 **to** | **String**| The series of data returned ends at this timestamp. Defaults to the current time. | [optional]
 **tz** | **String**| The timezone to use for breaks between days when returning daily data. | [optional]
 **sdk** | **String**| If included, this filters the returned series to only those that match this SDK name. | [optional]
 **version** | **String**| If included, this filters the returned series to only those that match this SDK version. | [optional]

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
**200** | Usage response |  -  |
**400** | Invalid request |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

<a name="getStreamUsageSdkversion"></a>
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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **source** | **String**| The source of streaming connections to describe. Must be either &#x60;client&#x60; or &#x60;server&#x60;. |

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
**200** | SDK Versions response |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**429** | Rate limited |  -  |

