# ScheduledChangesApi

All URIs are relative to *https://app.launchdarkly.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteFlagConfigScheduledChanges**](ScheduledChangesApi.md#deleteFlagConfigScheduledChanges) | **DELETE** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes/{id} | Delete scheduled changes workflow
[**getFeatureFlagScheduledChange**](ScheduledChangesApi.md#getFeatureFlagScheduledChange) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes/{id} | Get a scheduled change
[**getFlagConfigScheduledChanges**](ScheduledChangesApi.md#getFlagConfigScheduledChanges) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes | List scheduled changes
[**patchFlagConfigScheduledChange**](ScheduledChangesApi.md#patchFlagConfigScheduledChange) | **PATCH** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes/{id} | Update scheduled changes workflow
[**postFlagConfigScheduledChanges**](ScheduledChangesApi.md#postFlagConfigScheduledChanges) | **POST** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes | Create scheduled changes workflow


<a name="deleteFlagConfigScheduledChanges"></a>
# **deleteFlagConfigScheduledChanges**
> deleteFlagConfigScheduledChanges(projectKey, featureFlagKey, environmentKey, id)

Delete scheduled changes workflow

Delete a scheduled changes workflow

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
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag's key
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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key |
 **featureFlagKey** | **String**| The feature flag&#39;s key |
 **environmentKey** | **String**| The environment key |
 **id** | **String**| The scheduled change id |

### Return type

null (empty response body)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**204** | Action completed successfully |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**405** | Method not allowed |  -  |
**409** | Status conflict |  -  |
**429** | Rate limited |  -  |

<a name="getFeatureFlagScheduledChange"></a>
# **getFeatureFlagScheduledChange**
> FeatureFlagScheduledChange getFeatureFlagScheduledChange(projectKey, featureFlagKey, environmentKey, id)

Get a scheduled change

Get a scheduled change that will be applied to the feature flag by ID

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
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag's key
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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key |
 **featureFlagKey** | **String**| The feature flag&#39;s key |
 **environmentKey** | **String**| The environment key |
 **id** | **String**| The scheduled change id |

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
**200** | Scheduled change response |  -  |
**401** | Invalid access token |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

<a name="getFlagConfigScheduledChanges"></a>
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
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag's key
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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key |
 **featureFlagKey** | **String**| The feature flag&#39;s key |
 **environmentKey** | **String**| The environment key |

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
**200** | Scheduled changes collection response |  -  |
**400** | Invalid request body |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |

<a name="patchFlagConfigScheduledChange"></a>
# **patchFlagConfigScheduledChange**
> FeatureFlagScheduledChange patchFlagConfigScheduledChange(projectKey, featureFlagKey, environmentKey, id, flagScheduledChangesInput, ignoreConflicts)

Update scheduled changes workflow

Update a scheduled change, overriding existing instructions with the new ones.&lt;br /&gt;&lt;br /&gt;Requires a semantic patch representation of the desired changes to the resource. To learn more about semantic patches, read [Updates](/#section/Updates/Updates-via-semantic-patches)

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
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag's key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String id = "id_example"; // String | The scheduled change ID
    FlagScheduledChangesInput flagScheduledChangesInput = new FlagScheduledChangesInput(); // FlagScheduledChangesInput | 
    Boolean ignoreConflicts = true; // Boolean | Whether or not to succeed or fail when the new instructions conflict with existing scheduled changes
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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key |
 **featureFlagKey** | **String**| The feature flag&#39;s key |
 **environmentKey** | **String**| The environment key |
 **id** | **String**| The scheduled change ID |
 **flagScheduledChangesInput** | [**FlagScheduledChangesInput**](FlagScheduledChangesInput.md)|  |
 **ignoreConflicts** | **Boolean**| Whether or not to succeed or fail when the new instructions conflict with existing scheduled changes | [optional]

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
**200** | Successful scheduled changes response |  -  |
**400** | Invalid request body |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource ID |  -  |
**405** | Method not allowed |  -  |
**409** | Status conflict |  -  |
**429** | Rate limited |  -  |

<a name="postFlagConfigScheduledChanges"></a>
# **postFlagConfigScheduledChanges**
> FeatureFlagScheduledChange postFlagConfigScheduledChanges(projectKey, featureFlagKey, environmentKey, postFlagScheduledChangesInput, ignoreConflicts)

Create scheduled changes workflow

Create scheduled changes for a feature flag. If the ignoreConficts query parameter is false and the new instructions would conflict with the current state of the feature flag or any existing scheduled changes, the request will fail. If the parameter is true and there are conflicts, the request will succeed as normal.

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
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag's key
    String environmentKey = "environmentKey_example"; // String | The environment key
    PostFlagScheduledChangesInput postFlagScheduledChangesInput = new PostFlagScheduledChangesInput(); // PostFlagScheduledChangesInput | 
    Boolean ignoreConflicts = true; // Boolean | Whether or not to succeed or fail when the new instructions conflict with existing scheduled changes
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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key |
 **featureFlagKey** | **String**| The feature flag&#39;s key |
 **environmentKey** | **String**| The environment key |
 **postFlagScheduledChangesInput** | [**PostFlagScheduledChangesInput**](PostFlagScheduledChangesInput.md)|  |
 **ignoreConflicts** | **Boolean**| Whether or not to succeed or fail when the new instructions conflict with existing scheduled changes | [optional]

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
**201** | Successful scheduled changes response |  -  |
**400** | Invalid request body |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource ID |  -  |
**405** | Method not allowed |  -  |
**409** | Status conflict |  -  |
**429** | Rate limited |  -  |

