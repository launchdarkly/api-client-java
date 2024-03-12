# ExperimentsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createExperiment**](ExperimentsBetaApi.md#createExperiment) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments | Create experiment |
| [**createIteration**](ExperimentsBetaApi.md#createIteration) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey}/iterations | Create iteration |
| [**getExperiment**](ExperimentsBetaApi.md#getExperiment) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey} | Get experiment |
| [**getExperimentResults**](ExperimentsBetaApi.md#getExperimentResults) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey}/metrics/{metricKey}/results | Get experiment results |
| [**getExperimentResultsForMetricGroup**](ExperimentsBetaApi.md#getExperimentResultsForMetricGroup) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey}/metric-groups/{metricGroupKey}/results | Get experiment results for metric group |
| [**getExperimentationSettings**](ExperimentsBetaApi.md#getExperimentationSettings) | **GET** /api/v2/projects/{projectKey}/experimentation-settings | Get experimentation settings |
| [**getExperiments**](ExperimentsBetaApi.md#getExperiments) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments | Get experiments |
| [**getLegacyExperimentResults**](ExperimentsBetaApi.md#getLegacyExperimentResults) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey}/experiments/{environmentKey}/{metricKey} | Get legacy experiment results (deprecated) |
| [**patchExperiment**](ExperimentsBetaApi.md#patchExperiment) | **PATCH** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey} | Patch experiment |
| [**putExperimentationSettings**](ExperimentsBetaApi.md#putExperimentationSettings) | **PUT** /api/v2/projects/{projectKey}/experimentation-settings | Update experimentation settings |


<a name="createExperiment"></a>
# **createExperiment**
> Experiment createExperiment(projectKey, environmentKey, experimentPost)

Create experiment

Create an experiment.  To run this experiment, you&#39;ll need to [create an iteration](/tag/Experiments-(beta)#operation/createIteration) and then [update the experiment](/tag/Experiments-(beta)#operation/patchExperiment) with the &#x60;startIteration&#x60; instruction.  To learn more, read [Creating experiments](https://docs.launchdarkly.com/home/creating-experiments). 

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
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    ExperimentPost experimentPost = new ExperimentPost(); // ExperimentPost | 
    try {
      Experiment result = apiInstance.createExperiment(projectKey, environmentKey, experimentPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#createExperiment");
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
| **experimentPost** | [**ExperimentPost**](ExperimentPost.md)|  | |

### Return type

[**Experiment**](Experiment.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Experiment response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="createIteration"></a>
# **createIteration**
> IterationRep createIteration(projectKey, environmentKey, experimentKey, iterationInput)

Create iteration

Create an experiment iteration.  Experiment iterations let you record experiments in individual blocks of time. Initially, iterations are created with a status of &#x60;not_started&#x60; and appear in the &#x60;draftIteration&#x60; field of an experiment. To start or stop an iteration, [update the experiment](/tag/Experiments-(beta)#operation/patchExperiment) with the &#x60;startIteration&#x60; or &#x60;stopIteration&#x60; instruction.   To learn more, read [Starting experiment iterations](https://docs.launchdarkly.com/home/creating-experiments#starting-experiment-iterations). 

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
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String experimentKey = "experimentKey_example"; // String | The experiment key
    IterationInput iterationInput = new IterationInput(); // IterationInput | 
    try {
      IterationRep result = apiInstance.createIteration(projectKey, environmentKey, experimentKey, iterationInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#createIteration");
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
| **experimentKey** | **String**| The experiment key | |
| **iterationInput** | [**IterationInput**](IterationInput.md)|  | |

### Return type

[**IterationRep**](IterationRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Iteration response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getExperiment"></a>
# **getExperiment**
> Experiment getExperiment(projectKey, environmentKey, experimentKey, expand)

Get experiment

Get details about an experiment.  ### Expanding the experiment response  LaunchDarkly supports four fields for expanding the \&quot;Get experiment\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  - &#x60;previousIterations&#x60; includes all iterations prior to the current iteration. By default only the current iteration is included in the response. - &#x60;draftIteration&#x60; includes the iteration which has not been started yet, if any. - &#x60;secondaryMetrics&#x60; includes secondary metrics. By default only the primary metric is included in the response. - &#x60;treatments&#x60; includes all treatment and parameter details. By default treatment data is not included in the response.  For example, &#x60;expand&#x3D;draftIteration,treatments&#x60; includes the &#x60;draftIteration&#x60; and &#x60;treatments&#x60; fields in the response. If fields that you request with the &#x60;expand&#x60; query parameter are empty, they are not included in the response. 

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
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String experimentKey = "experimentKey_example"; // String | The experiment key
    String expand = "expand_example"; // String | A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above.
    try {
      Experiment result = apiInstance.getExperiment(projectKey, environmentKey, experimentKey, expand);
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

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **projectKey** | **String**| The project key | |
| **environmentKey** | **String**| The environment key | |
| **experimentKey** | **String**| The experiment key | |
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above. | [optional] |

### Return type

[**Experiment**](Experiment.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Experiment response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **405** | Method not allowed |  -  |
| **429** | Rate limited |  -  |

<a name="getExperimentResults"></a>
# **getExperimentResults**
> ExperimentBayesianResultsRep getExperimentResults(projectKey, environmentKey, experimentKey, metricKey, iterationId, expand)

Get experiment results

Get results from an experiment for a particular metric.  LaunchDarkly supports one field for expanding the \&quot;Get experiment results\&quot; response. By default, this field is **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter with the following field: * &#x60;traffic&#x60; includes the total count of units for each treatment.  For example, &#x60;expand&#x3D;traffic&#x60; includes the &#x60;traffic&#x60; field for the project in the response. 

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
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String experimentKey = "experimentKey_example"; // String | The experiment key
    String metricKey = "metricKey_example"; // String | The metric key
    String iterationId = "iterationId_example"; // String | The iteration ID
    String expand = "expand_example"; // String | A comma-separated list of fields to expand in the response. Supported fields are explained above.
    try {
      ExperimentBayesianResultsRep result = apiInstance.getExperimentResults(projectKey, environmentKey, experimentKey, metricKey, iterationId, expand);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#getExperimentResults");
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
| **experimentKey** | **String**| The experiment key | |
| **metricKey** | **String**| The metric key | |
| **iterationId** | **String**| The iteration ID | [optional] |
| **expand** | **String**| A comma-separated list of fields to expand in the response. Supported fields are explained above. | [optional] |

### Return type

[**ExperimentBayesianResultsRep**](ExperimentBayesianResultsRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Experiment results response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getExperimentResultsForMetricGroup"></a>
# **getExperimentResultsForMetricGroup**
> MetricGroupResultsRep getExperimentResultsForMetricGroup(projectKey, environmentKey, experimentKey, metricGroupKey, iterationId)

Get experiment results for metric group

Get results from an experiment for a particular metric group.

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
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String experimentKey = "experimentKey_example"; // String | The experiment key
    String metricGroupKey = "metricGroupKey_example"; // String | The metric group key
    String iterationId = "iterationId_example"; // String | The iteration ID
    try {
      MetricGroupResultsRep result = apiInstance.getExperimentResultsForMetricGroup(projectKey, environmentKey, experimentKey, metricGroupKey, iterationId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#getExperimentResultsForMetricGroup");
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
| **experimentKey** | **String**| The experiment key | |
| **metricGroupKey** | **String**| The metric group key | |
| **iterationId** | **String**| The iteration ID | [optional] |

### Return type

[**MetricGroupResultsRep**](MetricGroupResultsRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Experiment results response for metric group |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getExperimentationSettings"></a>
# **getExperimentationSettings**
> RandomizationSettingsRep getExperimentationSettings(projectKey)

Get experimentation settings

Get current experimentation settings for the given project

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
    String projectKey = "projectKey_example"; // String | The project key
    try {
      RandomizationSettingsRep result = apiInstance.getExperimentationSettings(projectKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#getExperimentationSettings");
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

[**RandomizationSettingsRep**](RandomizationSettingsRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Experimentation settings response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **405** | Method not allowed |  -  |
| **429** | Rate limited |  -  |

<a name="getExperiments"></a>
# **getExperiments**
> ExperimentCollectionRep getExperiments(projectKey, environmentKey, limit, offset, filter, expand, lifecycleState)

Get experiments

Get details about all experiments in an environment.  ### Filtering experiments  LaunchDarkly supports the &#x60;filter&#x60; query param for filtering, with the following fields:  - &#x60;flagKey&#x60; filters for only experiments that use the flag with the given key. - &#x60;metricKey&#x60; filters for only experiments that use the metric with the given key. - &#x60;status&#x60; filters for only experiments with an iteration with the given status. An iteration can have the status &#x60;not_started&#x60;, &#x60;running&#x60; or &#x60;stopped&#x60;.  For example, &#x60;filter&#x3D;flagKey:my-flag,status:running,metricKey:page-load-ms&#x60; filters for experiments for the given flag key and the given metric key which have a currently running iteration.  ### Expanding the experiments response  LaunchDarkly supports four fields for expanding the \&quot;Get experiments\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  - &#x60;previousIterations&#x60; includes all iterations prior to the current iteration. By default only the current iteration is included in the response. - &#x60;draftIteration&#x60; includes the iteration which has not been started yet, if any. - &#x60;secondaryMetrics&#x60; includes secondary metrics. By default only the primary metric is included in the response. - &#x60;treatments&#x60; includes all treatment and parameter details. By default treatment data is not included in the response.  For example, &#x60;expand&#x3D;draftIteration,treatments&#x60; includes the &#x60;draftIteration&#x60; and &#x60;treatments&#x60; fields in the response. If fields that you request with the &#x60;expand&#x60; query parameter are empty, they are not included in the response. 

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
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    Long limit = 56L; // Long | The maximum number of experiments to return. Defaults to 20.
    Long offset = 56L; // Long | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    String filter = "filter_example"; // String | A comma-separated list of filters. Each filter is of the form `field:value`. Supported fields are explained above.
    String expand = "expand_example"; // String | A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above.
    String lifecycleState = "lifecycleState_example"; // String | A comma-separated list of experiment archived states. Supports `archived`, `active`, or both. Defaults to `active` experiments.
    try {
      ExperimentCollectionRep result = apiInstance.getExperiments(projectKey, environmentKey, limit, offset, filter, expand, lifecycleState);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#getExperiments");
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
| **limit** | **Long**| The maximum number of experiments to return. Defaults to 20. | [optional] |
| **offset** | **Long**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |
| **filter** | **String**| A comma-separated list of filters. Each filter is of the form &#x60;field:value&#x60;. Supported fields are explained above. | [optional] |
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above. | [optional] |
| **lifecycleState** | **String**| A comma-separated list of experiment archived states. Supports &#x60;archived&#x60;, &#x60;active&#x60;, or both. Defaults to &#x60;active&#x60; experiments. | [optional] |

### Return type

[**ExperimentCollectionRep**](ExperimentCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Experiment collection response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **405** | Method not allowed |  -  |
| **429** | Rate limited |  -  |

<a name="getLegacyExperimentResults"></a>
# **getLegacyExperimentResults**
> ExperimentResults getLegacyExperimentResults(projectKey, featureFlagKey, environmentKey, metricKey, from, to)

Get legacy experiment results (deprecated)

Get detailed experiment result data for legacy experiments.

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
    String projectKey = "projectKey_example"; // String | The project key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String metricKey = "metricKey_example"; // String | The metric key
    Long from = 56L; // Long | A timestamp denoting the start of the data collection period, expressed as a Unix epoch time in milliseconds.
    Long to = 56L; // Long | A timestamp denoting the end of the data collection period, expressed as a Unix epoch time in milliseconds.
    try {
      ExperimentResults result = apiInstance.getLegacyExperimentResults(projectKey, featureFlagKey, environmentKey, metricKey, from, to);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#getLegacyExperimentResults");
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
| **featureFlagKey** | **String**| The feature flag key | |
| **environmentKey** | **String**| The environment key | |
| **metricKey** | **String**| The metric key | |
| **from** | **Long**| A timestamp denoting the start of the data collection period, expressed as a Unix epoch time in milliseconds. | [optional] |
| **to** | **Long**| A timestamp denoting the end of the data collection period, expressed as a Unix epoch time in milliseconds. | [optional] |

### Return type

[**ExperimentResults**](ExperimentResults.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Experiment results response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="patchExperiment"></a>
# **patchExperiment**
> Experiment patchExperiment(projectKey, environmentKey, experimentKey, experimentPatchInput)

Patch experiment

Update an experiment. Updating an experiment uses the semantic patch format.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating experiments.  #### updateName  Updates the experiment name.  ##### Parameters  - &#x60;value&#x60;: The new name.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;updateName\&quot;,     \&quot;value\&quot;: \&quot;Example updated experiment name\&quot;   }] } &#x60;&#x60;&#x60;  #### updateDescription  Updates the experiment description.  ##### Parameters  - &#x60;value&#x60;: The new description.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;updateDescription\&quot;,     \&quot;value\&quot;: \&quot;Example updated description\&quot;   }] } &#x60;&#x60;&#x60;  #### startIteration  Starts a new iteration for this experiment. You must [create a new iteration](/tag/Experiments-(beta)#operation/createIteration) before calling this instruction.  An iteration may not be started until it meets the following criteria:  * Its associated flag is toggled on and is not archived * Its &#x60;randomizationUnit&#x60; is set * At least one of its &#x60;treatments&#x60; has a non-zero &#x60;allocationPercent&#x60;  ##### Parameters  - &#x60;changeJustification&#x60;: The reason for starting a new iteration. Required when you call &#x60;startIteration&#x60; on an already running experiment, otherwise optional.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;startIteration\&quot;,     \&quot;changeJustification\&quot;: \&quot;It&#39;s time to start a new iteration\&quot;   }] } &#x60;&#x60;&#x60;  #### stopIteration  Stops the current iteration for this experiment.  ##### Parameters  - &#x60;winningTreatmentId&#x60;: The ID of the winning treatment. Treatment IDs are returned as part of the [Get experiment](/tag/Experiments-(beta)#operation/getExperiment) response. They are the &#x60;_id&#x60; of each element in the &#x60;treatments&#x60; array. - &#x60;winningReason&#x60;: The reason for the winner  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;stopIteration\&quot;,     \&quot;winningTreatmentId\&quot;: \&quot;3a548ec2-72ac-4e59-8518-5c24f5609ccf\&quot;,     \&quot;winningReason\&quot;: \&quot;Example reason to stop the iteration\&quot;   }] } &#x60;&#x60;&#x60;  #### archiveExperiment  Archives this experiment. Archived experiments are hidden by default in the LaunchDarkly user interface. You cannot start new iterations for archived experiments.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{ \&quot;kind\&quot;: \&quot;archiveExperiment\&quot; }] } &#x60;&#x60;&#x60;  #### restoreExperiment  Restores an archived experiment. After restoring an experiment, you can start new iterations for it again.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{ \&quot;kind\&quot;: \&quot;restoreExperiment\&quot; }] } &#x60;&#x60;&#x60; 

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
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String experimentKey = "experimentKey_example"; // String | The experiment key
    ExperimentPatchInput experimentPatchInput = new ExperimentPatchInput(); // ExperimentPatchInput | 
    try {
      Experiment result = apiInstance.patchExperiment(projectKey, environmentKey, experimentKey, experimentPatchInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#patchExperiment");
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
| **experimentKey** | **String**| The experiment key | |
| **experimentPatchInput** | [**ExperimentPatchInput**](ExperimentPatchInput.md)|  | |

### Return type

[**Experiment**](Experiment.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Experiment response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="putExperimentationSettings"></a>
# **putExperimentationSettings**
> RandomizationSettingsRep putExperimentationSettings(projectKey, randomizationSettingsPut)

Update experimentation settings

Update experimentation settings for the given project

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
    String projectKey = "projectKey_example"; // String | The project key
    RandomizationSettingsPut randomizationSettingsPut = new RandomizationSettingsPut(); // RandomizationSettingsPut | 
    try {
      RandomizationSettingsRep result = apiInstance.putExperimentationSettings(projectKey, randomizationSettingsPut);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#putExperimentationSettings");
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
| **randomizationSettingsPut** | [**RandomizationSettingsPut**](RandomizationSettingsPut.md)|  | |

### Return type

[**RandomizationSettingsRep**](RandomizationSettingsRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Experimentation settings response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **405** | Method not allowed |  -  |
| **429** | Rate limited |  -  |

