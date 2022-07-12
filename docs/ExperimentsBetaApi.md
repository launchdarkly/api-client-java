# ExperimentsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createExperiment**](ExperimentsBetaApi.md#createExperiment) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments | Create experiment |
| [**createIteration**](ExperimentsBetaApi.md#createIteration) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey}/iterations | Create iteration |
| [**getExperiment**](ExperimentsBetaApi.md#getExperiment) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey} | Get experiment |
| [**getExperimentResults**](ExperimentsBetaApi.md#getExperimentResults) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey}/metrics/{metricKey}/results | Get experiment results |
| [**getExperiments**](ExperimentsBetaApi.md#getExperiments) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments | Get experiments |
| [**getLegacyExperimentResults**](ExperimentsBetaApi.md#getLegacyExperimentResults) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey}/experiments/{environmentKey}/{metricKey} | Get legacy experiment results (deprecated) |
| [**patchExperiment**](ExperimentsBetaApi.md#patchExperiment) | **PATCH** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey} | Patch experiment |
| [**resetExperiment**](ExperimentsBetaApi.md#resetExperiment) | **DELETE** /api/v2/flags/{projectKey}/{featureFlagKey}/experiments/{environmentKey}/{metricKey}/results | Reset experiment results |


<a name="createExperiment"></a>
# **createExperiment**
> Experiment createExperiment(projectKey, environmentKey, experimentPost)

Create experiment

Create an experiment. To learn more, read [Creating experiments](https://docs.launchdarkly.com/home/creating-experiments).

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
| **201** | Experiment response JSON |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="createIteration"></a>
# **createIteration**
> IterationRep createIteration(projectKey, environmentKey, experimentKey, iterationInput)

Create iteration

Create an experiment iteration. Experiment iterations let you record experiments in discrete blocks of time. To learn more, read [Starting experiment iterations](https://docs.launchdarkly.com/home/creating-experiments#starting-experiment-iterations).

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
| **200** | Iteration response JSON |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getExperiment"></a>
# **getExperiment**
> Experiment getExperiment(projectKey, environmentKey, experimentKey)

Get experiment

Get details about an experiment.

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
    try {
      Experiment result = apiInstance.getExperiment(projectKey, environmentKey, experimentKey);
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
| **200** | Experiment response JSON |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **405** | Method not allowed |  -  |
| **429** | Rate limited |  -  |

<a name="getExperimentResults"></a>
# **getExperimentResults**
> ExperimentBayesianResultsRep getExperimentResults(projectKey, environmentKey, experimentKey, metricKey)

Get experiment results

Get results from an experiment for a particular metric.

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
    try {
      ExperimentBayesianResultsRep result = apiInstance.getExperimentResults(projectKey, environmentKey, experimentKey, metricKey);
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
| **200** | Experiment results response JSON |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getExperiments"></a>
# **getExperiments**
> ExperimentCollectionRep getExperiments(projectKey, environmentKey, limit, offset, filter, expand)

Get experiments

Get details about all experiments in an environment.  ### Filtering experiments  LaunchDarkly supports the &#x60;filter&#x60; query param for filtering, with the following fields:  - &#x60;flagKey&#x60; filters for only experiments that use the flag with the given key. - &#x60;metricKey&#x60; filters for only experiments that use the metric with the given key. - &#x60;status&#x60; filters for only experiments with an iteration with the given status. An iteration can have the status &#x60;not_started&#x60;, &#x60;running&#x60; or &#x60;stopped&#x60;.  For example, &#x60;filter&#x3D;flagKey:my-flag,status:running,metricKey:page-load-ms&#x60; filters for experiments for the given flag key and the given metric key which have a currently running iteration.  ### Expanding the experiments response LaunchDarkly supports four fields for expanding the \&quot;List experiments\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  - &#x60;previousIterations&#x60; includes all iterations prior to the current iteration.  By default only the current iteration will be included in the response. - &#x60;draftIteration&#x60; includes a draft of an iteration which has not been started yet, if any. - &#x60;secondaryMetrics&#x60; includes secondary metrics.  By default only the primary metric is included in the response. - &#x60;treatments&#x60; includes all treatment and parameter details.  By default treatment data will not be included in the response.  For example, &#x60;expand&#x3D;draftIteration,treatments&#x60; includes the &#x60;draftIteration&#x60; and &#x60;treatments&#x60; fields in the response. 

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
    Long limit = 56L; // Long | The maximum number of experiments to return. Defaults to 20
    Long offset = 56L; // Long | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    String filter = "filter_example"; // String | A comma-separated list of filters. Each filter is of the form `field:value`. Supported fields are explained above.
    String expand = "expand_example"; // String | A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above.
    try {
      ExperimentCollectionRep result = apiInstance.getExperiments(projectKey, environmentKey, limit, offset, filter, expand);
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
| **limit** | **Long**| The maximum number of experiments to return. Defaults to 20 | [optional] |
| **offset** | **Long**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |
| **filter** | **String**| A comma-separated list of filters. Each filter is of the form &#x60;field:value&#x60;. Supported fields are explained above. | [optional] |
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above. | [optional] |

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
| **200** | Experiment response JSON |  -  |
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

Update an experiment. Updating an experiment uses the semantic patch format.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating experiments.  #### updateName  Updates the experiment name.  ##### Parameters  - &#x60;value&#x60;: The new name.  #### updateDescription  Updates the experiment description.  ##### Parameters  - &#x60;value&#x60;: The new description.  #### startIteration  Starts a new iteration for this experiment.  #### stopIteration  Stops the current iteration for this experiment. 

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
| **200** | Experiment response JSON |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="resetExperiment"></a>
# **resetExperiment**
> resetExperiment(projectKey, featureFlagKey, environmentKey, metricKey)

Reset experiment results

Reset all experiment results by deleting all existing data for an experiment.

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
    String metricKey = "metricKey_example"; // String | The metric's key
    try {
      apiInstance.resetExperiment(projectKey, featureFlagKey, environmentKey, metricKey);
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

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **projectKey** | **String**| The project key | |
| **featureFlagKey** | **String**| The feature flag key | |
| **environmentKey** | **String**| The environment key | |
| **metricKey** | **String**| The metric&#39;s key | |

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
| **204** | Experiment results reset successfully |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

