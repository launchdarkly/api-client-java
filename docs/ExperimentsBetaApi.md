# ExperimentsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getExperiment**](ExperimentsBetaApi.md#getExperiment) | **GET** /api/v2/flags/{projKey}/{flagKey}/experiments/{envKey}/{metricKey} | Get experiment results
[**resetExperiment**](ExperimentsBetaApi.md#resetExperiment) | **DELETE** /api/v2/flags/{projKey}/{flagKey}/experiments/{envKey}/{metricKey}/results | Reset experiment results


<a name="getExperiment"></a>
# **getExperiment**
> ExperimentResultsRep getExperiment(projKey, flagKey, envKey, metricKey, from, to)

Get experiment results

Get detailed experiment result data

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ExperimentsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ExperimentsBetaApi apiInstance = new ExperimentsBetaApi(defaultClient);
    String projKey = "projKey_example"; // String | The project key
    String flagKey = "flagKey_example"; // String | The flag key
    String envKey = "envKey_example"; // String | The environment key
    String metricKey = "metricKey_example"; // String | The metric key
    Long from = 56L; // Long | A timestamp denoting the start of the data collection period, expressed as a Unix epoch time in milliseconds.
    Long to = 56L; // Long | A timestamp denoting the end of the data collection period, expressed as a Unix epoch time in milliseconds.
    try {
      ExperimentResultsRep result = apiInstance.getExperiment(projKey, flagKey, envKey, metricKey, from, to);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#getExperiment");
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
 **projKey** | **String**| The project key |
 **flagKey** | **String**| The flag key |
 **envKey** | **String**| The environment key |
 **metricKey** | **String**| The metric key |
 **from** | **Long**| A timestamp denoting the start of the data collection period, expressed as a Unix epoch time in milliseconds. | [optional]
 **to** | **Long**| A timestamp denoting the end of the data collection period, expressed as a Unix epoch time in milliseconds. | [optional]

### Return type

[**ExperimentResultsRep**](ExperimentResultsRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Experiment results response |  -  |
**400** | Invalid request |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

<a name="resetExperiment"></a>
# **resetExperiment**
> resetExperiment(projKey, flagKey, envKey, metricKey)

Reset experiment results

Reset all experiment results by deleting all existing data for an experiment

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ExperimentsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ExperimentsBetaApi apiInstance = new ExperimentsBetaApi(defaultClient);
    String projKey = "projKey_example"; // String | The project key
    String flagKey = "flagKey_example"; // String | The feature flag's key
    String envKey = "envKey_example"; // String | The environment key
    String metricKey = "metricKey_example"; // String | The metric's key
    try {
      apiInstance.resetExperiment(projKey, flagKey, envKey, metricKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#resetExperiment");
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
 **projKey** | **String**| The project key |
 **flagKey** | **String**| The feature flag&#39;s key |
 **envKey** | **String**| The environment key |
 **metricKey** | **String**| The metric&#39;s key |

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
**204** | Experiment results reset successfully |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

