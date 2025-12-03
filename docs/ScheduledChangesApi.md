# ScheduledChangesApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteFlagConfigScheduledChanges**](ScheduledChangesApi.md#deleteFlagConfigScheduledChanges) | **DELETE** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes/{id} | Delete scheduled changes workflow |
| [**getFeatureFlagScheduledChange**](ScheduledChangesApi.md#getFeatureFlagScheduledChange) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes/{id} | Get a scheduled change |
| [**getFlagConfigScheduledChanges**](ScheduledChangesApi.md#getFlagConfigScheduledChanges) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes | List scheduled changes |
| [**patchFlagConfigScheduledChange**](ScheduledChangesApi.md#patchFlagConfigScheduledChange) | **PATCH** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes/{id} | Update scheduled changes workflow |
| [**postFlagConfigScheduledChanges**](ScheduledChangesApi.md#postFlagConfigScheduledChanges) | **POST** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes | Create scheduled changes workflow |


<a id="deleteFlagConfigScheduledChanges"></a>
# **deleteFlagConfigScheduledChanges**
> deleteFlagConfigScheduledChanges(projectKey, featureFlagKey, environmentKey, id)

Delete scheduled changes workflow

Delete a scheduled changes workflow.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ScheduledChangesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ScheduledChangesApi apiInstance = new ScheduledChangesApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String id = "id_example"; // String | The scheduled change id
    try {
      apiInstance.deleteFlagConfigScheduledChanges(projectKey, featureFlagKey, environmentKey, id);
    } catch (ApiException e) {
      System.err.println("Exception when calling ScheduledChangesApi#deleteFlagConfigScheduledChanges");
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
| **id** | **String**| The scheduled change id | |

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
| **405** | Method not allowed |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a id="getFeatureFlagScheduledChange"></a>
# **getFeatureFlagScheduledChange**
> FeatureFlagScheduledChange getFeatureFlagScheduledChange(projectKey, featureFlagKey, environmentKey, id)

Get a scheduled change

Get a scheduled change that will be applied to the feature flag by ID.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ScheduledChangesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ScheduledChangesApi apiInstance = new ScheduledChangesApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String id = "id_example"; // String | The scheduled change id
    try {
      FeatureFlagScheduledChange result = apiInstance.getFeatureFlagScheduledChange(projectKey, featureFlagKey, environmentKey, id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ScheduledChangesApi#getFeatureFlagScheduledChange");
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
| **id** | **String**| The scheduled change id | |

### Return type

[**FeatureFlagScheduledChange**](FeatureFlagScheduledChange.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Scheduled changes response |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="getFlagConfigScheduledChanges"></a>
# **getFlagConfigScheduledChanges**
> FeatureFlagScheduledChanges getFlagConfigScheduledChanges(projectKey, featureFlagKey, environmentKey)

List scheduled changes

Get a list of scheduled changes that will be applied to the feature flag.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ScheduledChangesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ScheduledChangesApi apiInstance = new ScheduledChangesApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    String environmentKey = "environmentKey_example"; // String | The environment key
    try {
      FeatureFlagScheduledChanges result = apiInstance.getFlagConfigScheduledChanges(projectKey, featureFlagKey, environmentKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ScheduledChangesApi#getFlagConfigScheduledChanges");
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

### Return type

[**FeatureFlagScheduledChanges**](FeatureFlagScheduledChanges.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Scheduled changes collection response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |

<a id="patchFlagConfigScheduledChange"></a>
# **patchFlagConfigScheduledChange**
> FeatureFlagScheduledChange patchFlagConfigScheduledChange(projectKey, featureFlagKey, environmentKey, id, flagScheduledChangesInput, ignoreConflicts)

Update scheduled changes workflow

 Update a scheduled change, overriding existing instructions with the new ones. Updating a scheduled change uses the semantic patch format.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](https://launchdarkly.com/docs/api#updates-using-semantic-patch).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating scheduled changes.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating scheduled changes&lt;/strong&gt;&lt;/summary&gt;  #### deleteScheduledChange  Removes the scheduled change.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{ \&quot;kind\&quot;: \&quot;deleteScheduledChange\&quot; }] } &#x60;&#x60;&#x60;  #### replaceScheduledChangesInstructions  Removes the existing scheduled changes and replaces them with the new instructions.  ##### Parameters  - &#x60;value&#x60;: An array of the new actions to perform when the execution date for these scheduled changes arrives. Supported scheduled actions are &#x60;turnFlagOn&#x60; and &#x60;turnFlagOff&#x60;.  Here&#39;s an example that replaces the scheduled changes with new instructions to turn flag targeting off:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replaceScheduledChangesInstructions\&quot;,       \&quot;value\&quot;: [ {\&quot;kind\&quot;: \&quot;turnFlagOff\&quot;} ]     }   ] } &#x60;&#x60;&#x60;  #### updateScheduledChangesExecutionDate  Updates the execution date for the scheduled changes.  ##### Parameters  - &#x60;value&#x60;: the new execution date, in Unix milliseconds.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;updateScheduledChangesExecutionDate\&quot;,       \&quot;value\&quot;: 1754092860000     }   ] } &#x60;&#x60;&#x60;  &lt;/details&gt; 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ScheduledChangesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ScheduledChangesApi apiInstance = new ScheduledChangesApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String id = "id_example"; // String | The scheduled change ID
    FlagScheduledChangesInput flagScheduledChangesInput = new FlagScheduledChangesInput(); // FlagScheduledChangesInput | 
    Boolean ignoreConflicts = true; // Boolean | Whether to succeed (`true`) or fail (`false`) when these new instructions conflict with existing scheduled changes
    try {
      FeatureFlagScheduledChange result = apiInstance.patchFlagConfigScheduledChange(projectKey, featureFlagKey, environmentKey, id, flagScheduledChangesInput, ignoreConflicts);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ScheduledChangesApi#patchFlagConfigScheduledChange");
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
| **id** | **String**| The scheduled change ID | |
| **flagScheduledChangesInput** | [**FlagScheduledChangesInput**](FlagScheduledChangesInput.md)|  | |
| **ignoreConflicts** | **Boolean**| Whether to succeed (&#x60;true&#x60;) or fail (&#x60;false&#x60;) when these new instructions conflict with existing scheduled changes | [optional] |

### Return type

[**FeatureFlagScheduledChange**](FeatureFlagScheduledChange.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Scheduled changes response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **405** | Method not allowed |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a id="postFlagConfigScheduledChanges"></a>
# **postFlagConfigScheduledChanges**
> FeatureFlagScheduledChange postFlagConfigScheduledChanges(projectKey, featureFlagKey, environmentKey, postFlagScheduledChangesInput, ignoreConflicts)

Create scheduled changes workflow

Create scheduled changes for a feature flag. The changes you schedule may include any semantic patch instructions available when [updating a feature flag](https://launchdarkly.com/docs/api/feature-flags/patch-feature-flag#using-semantic-patches-on-a-feature-flag). If the &#x60;ignoreConflicts&#x60; query parameter is false and there are conflicts between these instructions and existing scheduled changes, the request will fail. If the parameter is true and there are conflicts, the request will succeed.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ScheduledChangesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ScheduledChangesApi apiInstance = new ScheduledChangesApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    String environmentKey = "environmentKey_example"; // String | The environment key
    PostFlagScheduledChangesInput postFlagScheduledChangesInput = new PostFlagScheduledChangesInput(); // PostFlagScheduledChangesInput | 
    Boolean ignoreConflicts = true; // Boolean | Whether to succeed (`true`) or fail (`false`) when these instructions conflict with existing scheduled changes
    try {
      FeatureFlagScheduledChange result = apiInstance.postFlagConfigScheduledChanges(projectKey, featureFlagKey, environmentKey, postFlagScheduledChangesInput, ignoreConflicts);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ScheduledChangesApi#postFlagConfigScheduledChanges");
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
| **postFlagScheduledChangesInput** | [**PostFlagScheduledChangesInput**](PostFlagScheduledChangesInput.md)|  | |
| **ignoreConflicts** | **Boolean**| Whether to succeed (&#x60;true&#x60;) or fail (&#x60;false&#x60;) when these instructions conflict with existing scheduled changes | [optional] |

### Return type

[**FeatureFlagScheduledChange**](FeatureFlagScheduledChange.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Scheduled changes response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **405** | Method not allowed |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

