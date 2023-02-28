# FeatureFlagsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**copyFeatureFlag**](FeatureFlagsApi.md#copyFeatureFlag) | **POST** /api/v2/flags/{projectKey}/{featureFlagKey}/copy | Copy feature flag |
| [**deleteFeatureFlag**](FeatureFlagsApi.md#deleteFeatureFlag) | **DELETE** /api/v2/flags/{projectKey}/{featureFlagKey} | Delete feature flag |
| [**getExpiringContextTargets**](FeatureFlagsApi.md#getExpiringContextTargets) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey}/expiring-targets/{environmentKey} | Get expiring context targets for feature flag |
| [**getExpiringUserTargets**](FeatureFlagsApi.md#getExpiringUserTargets) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey}/expiring-user-targets/{environmentKey} | Get expiring user targets for feature flag |
| [**getFeatureFlag**](FeatureFlagsApi.md#getFeatureFlag) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey} | Get feature flag |
| [**getFeatureFlagStatus**](FeatureFlagsApi.md#getFeatureFlagStatus) | **GET** /api/v2/flag-statuses/{projectKey}/{environmentKey}/{featureFlagKey} | Get feature flag status |
| [**getFeatureFlagStatusAcrossEnvironments**](FeatureFlagsApi.md#getFeatureFlagStatusAcrossEnvironments) | **GET** /api/v2/flag-status/{projectKey}/{featureFlagKey} | Get flag status across environments |
| [**getFeatureFlagStatuses**](FeatureFlagsApi.md#getFeatureFlagStatuses) | **GET** /api/v2/flag-statuses/{projectKey}/{environmentKey} | List feature flag statuses |
| [**getFeatureFlags**](FeatureFlagsApi.md#getFeatureFlags) | **GET** /api/v2/flags/{projectKey} | List feature flags |
| [**patchExpiringTargets**](FeatureFlagsApi.md#patchExpiringTargets) | **PATCH** /api/v2/flags/{projectKey}/{featureFlagKey}/expiring-targets/{environmentKey} | Update expiring context targets on feature flag |
| [**patchExpiringUserTargets**](FeatureFlagsApi.md#patchExpiringUserTargets) | **PATCH** /api/v2/flags/{projectKey}/{featureFlagKey}/expiring-user-targets/{environmentKey} | Update expiring user targets on feature flag |
| [**patchFeatureFlag**](FeatureFlagsApi.md#patchFeatureFlag) | **PATCH** /api/v2/flags/{projectKey}/{featureFlagKey} | Update feature flag |
| [**postFeatureFlag**](FeatureFlagsApi.md#postFeatureFlag) | **POST** /api/v2/flags/{projectKey} | Create a feature flag |


<a name="copyFeatureFlag"></a>
# **copyFeatureFlag**
> FeatureFlag copyFeatureFlag(projectKey, featureFlagKey, flagCopyConfigPost)

Copy feature flag

 &gt; ### Copying flag settings is an Enterprise feature &gt; &gt; Copying flag settings is available to customers on an Enterprise plan. To learn more, [read about our pricing](https://launchdarkly.com/pricing/). To upgrade your plan, [contact Sales](https://launchdarkly.com/contact-sales/).  Copy flag settings from a source environment to a target environment.  By default, this operation copies the entire flag configuration. You can use the &#x60;includedActions&#x60; or &#x60;excludedActions&#x60; to specify that only part of the flag configuration is copied.  If you provide the optional &#x60;currentVersion&#x60; of a flag, this operation tests to ensure that the current flag version in the environment matches the version you&#39;ve specified. The operation rejects attempts to copy flag settings if the environment&#39;s current version  of the flag does not match the version you&#39;ve specified. You can use this to enforce optimistic locking on copy attempts. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FeatureFlagsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FeatureFlagsApi apiInstance = new FeatureFlagsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key. The key identifies the flag in your code.
    FlagCopyConfigPost flagCopyConfigPost = new FlagCopyConfigPost(); // FlagCopyConfigPost | 
    try {
      FeatureFlag result = apiInstance.copyFeatureFlag(projectKey, featureFlagKey, flagCopyConfigPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FeatureFlagsApi#copyFeatureFlag");
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
| **featureFlagKey** | **String**| The feature flag key. The key identifies the flag in your code. | |
| **flagCopyConfigPost** | [**FlagCopyConfigPost**](FlagCopyConfigPost.md)|  | |

### Return type

[**FeatureFlag**](FeatureFlag.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Global flag response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **405** | Method not allowed |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a name="deleteFeatureFlag"></a>
# **deleteFeatureFlag**
> deleteFeatureFlag(projectKey, featureFlagKey)

Delete feature flag

Delete a feature flag in all environments. Use with caution: only delete feature flags your application no longer uses.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FeatureFlagsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FeatureFlagsApi apiInstance = new FeatureFlagsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key. The key identifies the flag in your code.
    try {
      apiInstance.deleteFeatureFlag(projectKey, featureFlagKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling FeatureFlagsApi#deleteFeatureFlag");
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
| **featureFlagKey** | **String**| The feature flag key. The key identifies the flag in your code. | |

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
| **404** | Invalid resource identifier |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a name="getExpiringContextTargets"></a>
# **getExpiringContextTargets**
> ExpiringTargetGetResponse getExpiringContextTargets(projectKey, environmentKey, featureFlagKey)

Get expiring context targets for feature flag

Get a list of context targets on a feature flag that are scheduled for removal.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FeatureFlagsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FeatureFlagsApi apiInstance = new FeatureFlagsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    try {
      ExpiringTargetGetResponse result = apiInstance.getExpiringContextTargets(projectKey, environmentKey, featureFlagKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FeatureFlagsApi#getExpiringContextTargets");
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

### Return type

[**ExpiringTargetGetResponse**](ExpiringTargetGetResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Expiring target response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getExpiringUserTargets"></a>
# **getExpiringUserTargets**
> ExpiringUserTargetGetResponse getExpiringUserTargets(projectKey, environmentKey, featureFlagKey)

Get expiring user targets for feature flag

 &gt; ### Contexts are now available &gt; &gt; After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should use [Get expiring context targets for feature flag](/tag/Feature-flags#operation/getExpiringContextTargets) instead of this endpoint. To learn more, read [Contexts](https://docs.launchdarkly.com/home/contexts).  Get a list of user targets on a feature flag that are scheduled for removal. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FeatureFlagsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FeatureFlagsApi apiInstance = new FeatureFlagsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    try {
      ExpiringUserTargetGetResponse result = apiInstance.getExpiringUserTargets(projectKey, environmentKey, featureFlagKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FeatureFlagsApi#getExpiringUserTargets");
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

### Return type

[**ExpiringUserTargetGetResponse**](ExpiringUserTargetGetResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Expiring user target response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getFeatureFlag"></a>
# **getFeatureFlag**
> FeatureFlag getFeatureFlag(projectKey, featureFlagKey, env)

Get feature flag

Get a single feature flag by key. By default, this returns the configurations for all environments. You can filter environments with the &#x60;env&#x60; query parameter. For example, setting &#x60;env&#x3D;production&#x60; restricts the returned configurations to just the &#x60;production&#x60; environment.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FeatureFlagsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FeatureFlagsApi apiInstance = new FeatureFlagsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    String env = "env_example"; // String | Filter configurations by environment
    try {
      FeatureFlag result = apiInstance.getFeatureFlag(projectKey, featureFlagKey, env);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FeatureFlagsApi#getFeatureFlag");
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
| **env** | **String**| Filter configurations by environment | [optional] |

### Return type

[**FeatureFlag**](FeatureFlag.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Global flag response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getFeatureFlagStatus"></a>
# **getFeatureFlagStatus**
> FlagStatusRep getFeatureFlagStatus(projectKey, environmentKey, featureFlagKey)

Get feature flag status

Get the status for a particular feature flag.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FeatureFlagsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FeatureFlagsApi apiInstance = new FeatureFlagsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    try {
      FlagStatusRep result = apiInstance.getFeatureFlagStatus(projectKey, environmentKey, featureFlagKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FeatureFlagsApi#getFeatureFlagStatus");
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

### Return type

[**FlagStatusRep**](FlagStatusRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag status response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getFeatureFlagStatusAcrossEnvironments"></a>
# **getFeatureFlagStatusAcrossEnvironments**
> FeatureFlagStatusAcrossEnvironments getFeatureFlagStatusAcrossEnvironments(projectKey, featureFlagKey, env)

Get flag status across environments

Get the status for a particular feature flag across environments.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FeatureFlagsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FeatureFlagsApi apiInstance = new FeatureFlagsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    String env = "env_example"; // String | Optional environment filter
    try {
      FeatureFlagStatusAcrossEnvironments result = apiInstance.getFeatureFlagStatusAcrossEnvironments(projectKey, featureFlagKey, env);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FeatureFlagsApi#getFeatureFlagStatusAcrossEnvironments");
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
| **env** | **String**| Optional environment filter | [optional] |

### Return type

[**FeatureFlagStatusAcrossEnvironments**](FeatureFlagStatusAcrossEnvironments.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag status across environments response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getFeatureFlagStatuses"></a>
# **getFeatureFlagStatuses**
> FeatureFlagStatuses getFeatureFlagStatuses(projectKey, environmentKey)

List feature flag statuses

Get a list of statuses for all feature flags. The status includes the last time the feature flag was requested, as well as a state, which is one of the following:  - &#x60;new&#x60;: the feature flag was created within the last seven days, and has not been requested yet - &#x60;active&#x60;: the feature flag was requested within the last seven days - &#x60;inactive&#x60;: the feature flag was created more than seven days ago, and hasn&#39;t been requested within the past seven days - &#x60;launched&#x60;: one variation of the feature flag has been rolled out for at least seven days 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FeatureFlagsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FeatureFlagsApi apiInstance = new FeatureFlagsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    try {
      FeatureFlagStatuses result = apiInstance.getFeatureFlagStatuses(projectKey, environmentKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FeatureFlagsApi#getFeatureFlagStatuses");
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

### Return type

[**FeatureFlagStatuses**](FeatureFlagStatuses.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag Statuses collection response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getFeatureFlags"></a>
# **getFeatureFlags**
> FeatureFlags getFeatureFlags(projectKey, env, tag, limit, offset, archived, summary, filter, sort, compare)

List feature flags

Get a list of all features in the given project. By default, each feature includes configurations for each environment. You can filter environments with the &#x60;env&#x60; query parameter. For example, setting &#x60;env&#x3D;production&#x60; restricts the returned configurations to just your production environment. You can also filter feature flags by tag with the tag query parameter.  ### Filtering flags  You can filter on certain fields using the &#x60;filter&#x60; query parameter. For example, setting &#x60;filter&#x3D;query:dark-mode,tags:beta+test&#x60; matches flags with the string &#x60;dark-mode&#x60; in their key or name, ignoring case, which also have the tags &#x60;beta&#x60; and &#x60;test&#x60;.  The &#x60;filter&#x60; query parameter supports the following arguments:  - &#x60;query&#x60; is a string that matches against the flags&#39; keys and names. It is not case sensitive. - &#x60;archived&#x60; is a boolean with values of &#x60;true&#x60; or &#x60;false&#x60; that filters the list to archived flags. Setting the value to &#x60;true&#x60; returns only archived flags. When this is absent, only unarchived flags are returned. - &#x60;type&#x60; is a string allowing filtering to &#x60;temporary&#x60; or &#x60;permanent&#x60; flags. - &#x60;status&#x60; is a string allowing filtering to &#x60;new&#x60;, &#x60;inactive&#x60;, &#x60;active&#x60;, or &#x60;launched&#x60; flags in the specified environment. This filter also requires a &#x60;filterEnv&#x60; field to be set to a valid environment. For example: &#x60;filter&#x3D;status:active,filterEnv:production&#x60;. - &#x60;tags&#x60; is a &#x60;+&#x60; separated list of tags. It filters the list to members who have all of the tags in the list. For example: &#x60;filter&#x3D;tags:beta+test&#x60;. - &#x60;hasExperiment&#x60; is a boolean with values of &#x60;true&#x60; or &#x60;false&#x60; that returns any flags that are used in an experiment. - &#x60;hasDataExport&#x60; is a boolean with values of &#x60;true&#x60; or &#x60;false&#x60; that returns any flags that are exporting data in the specified environment. This includes flags that are exporting data from Experimentation. This filter also requires that you set a &#x60;filterEnv&#x60; field to a valid environment key. For example: &#x60;filter&#x3D;hasDataExport:true,filterEnv:production&#x60; - &#x60;evaluated&#x60; is an object that contains a key of &#x60;after&#x60; and a value in Unix time in milliseconds. This returns all flags that have been evaluated since the time you specify in the environment provided. This filter also requires you to set a &#x60;filterEnv&#x60; field to a valid environment. For example: &#x60;filter&#x3D;evaluated:{\&quot;after\&quot;: 1590768455282},filterEnv:production&#x60;. - &#x60;filterEnv&#x60; is a string with the key of a valid environment. You can use the &#x60;filterEnv&#x60; field for filters that are environment-specific. If there are multiple environment-specific filters, you should only declare this parameter once. For example: &#x60;filter&#x3D;evaluated:{\&quot;after\&quot;: 1590768455282},filterEnv:production,status:active&#x60;.  By default, this returns all flags. You can page through the list with the &#x60;limit&#x60; parameter and by following the &#x60;first&#x60;, &#x60;prev&#x60;, &#x60;next&#x60;, and &#x60;last&#x60; links in the returned &#x60;_links&#x60; field. These links will not be present if the pages they refer to don&#39;t exist. For example, the &#x60;first&#x60; and &#x60;prev&#x60; links will be missing from the response on the first page.  ### Sorting flags  You can sort flags based on the following fields:  - &#x60;creationDate&#x60; sorts by the creation date of the flag. - &#x60;key&#x60; sorts by the key of the flag. - &#x60;maintainerId&#x60; sorts by the flag maintainer. - &#x60;name&#x60; sorts by flag name. - &#x60;tags&#x60; sorts by tags. - &#x60;targetingModifiedDate&#x60; sorts by the date that the flag&#39;s targeting rules were last modified in a given environment. It must be used with &#x60;env&#x60; parameter and it can not be combined with any other sort. If multiple &#x60;env&#x60; values are provided, it will perform sort using the first one. For example, &#x60;sort&#x3D;-targetingModifiedDate&amp;env&#x3D;production&amp;env&#x3D;staging&#x60; returns results sorted by &#x60;targetingModifiedDate&#x60; for the &#x60;production&#x60; environment. - &#x60;type&#x60; sorts by flag type  All fields are sorted in ascending order by default. To sort in descending order, prefix the field with a dash ( - ). For example, &#x60;sort&#x3D;-name&#x60; sorts the response by flag name in descending order. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FeatureFlagsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FeatureFlagsApi apiInstance = new FeatureFlagsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String env = "env_example"; // String | Filter configurations by environment
    String tag = "tag_example"; // String | Filter feature flags by tag
    Long limit = 56L; // Long | The number of feature flags to return. Defaults to -1, which returns all flags
    Long offset = 56L; // Long | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    Boolean archived = true; // Boolean | A boolean to filter the list to archived flags. When this is absent, only unarchived flags will be returned
    Boolean summary = true; // Boolean | By default in API version >= 1, flags will _not_ include their list of prerequisites, targets or rules.  Set summary=0 to include these fields for each flag returned
    String filter = "filter_example"; // String | A comma-separated list of filters. Each filter is of the form field:value. Read the endpoint description for a full list of available filter fields.
    String sort = "sort_example"; // String | A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order. Read the endpoint description for a full list of available sort fields.
    Boolean compare = true; // Boolean | A boolean to filter results by only flags that have differences between environments
    try {
      FeatureFlags result = apiInstance.getFeatureFlags(projectKey, env, tag, limit, offset, archived, summary, filter, sort, compare);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FeatureFlagsApi#getFeatureFlags");
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
| **env** | **String**| Filter configurations by environment | [optional] |
| **tag** | **String**| Filter feature flags by tag | [optional] |
| **limit** | **Long**| The number of feature flags to return. Defaults to -1, which returns all flags | [optional] |
| **offset** | **Long**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |
| **archived** | **Boolean**| A boolean to filter the list to archived flags. When this is absent, only unarchived flags will be returned | [optional] |
| **summary** | **Boolean**| By default in API version &gt;&#x3D; 1, flags will _not_ include their list of prerequisites, targets or rules.  Set summary&#x3D;0 to include these fields for each flag returned | [optional] |
| **filter** | **String**| A comma-separated list of filters. Each filter is of the form field:value. Read the endpoint description for a full list of available filter fields. | [optional] |
| **sort** | **String**| A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order. Read the endpoint description for a full list of available sort fields. | [optional] |
| **compare** | **Boolean**| A boolean to filter results by only flags that have differences between environments | [optional] |

### Return type

[**FeatureFlags**](FeatureFlags.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Global flags collection response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="patchExpiringTargets"></a>
# **patchExpiringTargets**
> ExpiringTargetPatchResponse patchExpiringTargets(projectKey, environmentKey, featureFlagKey, patchFlagsRequest)

Update expiring context targets on feature flag

Schedule a context for removal from individual targeting on a feature flag. The flag must already individually target the context.  You can add, update, or remove a scheduled removal date. You can only schedule a context for removal on a single variation per flag.  This request only supports semantic patches. To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  ### Instructions  #### addExpiringTarget  Adds a date and time that LaunchDarkly will remove the context from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the context from individual targeting for this flag * &#x60;variationId&#x60;: The version of the flag variation to update. You can retrieve this by making a GET request for the flag * &#x60;contextKey&#x60;: The context key for the context to remove from individual targeting * &#x60;contextKind&#x60;: The kind of context represented by the &#x60;contextKey&#x60;  #### updateExpiringTarget  Updates the date and time that LaunchDarkly will remove the context from the flag&#39;s individual targeting  ##### Parameters  * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the context from individual targeting for this flag * &#x60;variationId&#x60;: The version of the flag variation to update. You can retrieve this by making a GET request for the flag. * &#x60;contextKey&#x60;: The context key for the context to remove from individual targeting * &#x60;contextKind&#x60;: The kind of context represented by the &#x60;contextKey&#x60;  #### removeExpiringTarget  Removes the scheduled removal of the context from the flag&#39;s individual targeting. The context will remain part of the flag&#39;s individual targeting until you explicitly remove them, or until you schedule another removal.  ##### Parameters  * &#x60;variationId&#x60;: The version of the flag variation to update. You can retrieve this by making a GET request for the flag. * &#x60;contextKey&#x60;: The context key for the context to remove from individual targeting * &#x60;contextKind&#x60;: The kind of context represented by the &#x60;contextKey&#x60; 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FeatureFlagsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FeatureFlagsApi apiInstance = new FeatureFlagsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    PatchFlagsRequest patchFlagsRequest = new PatchFlagsRequest(); // PatchFlagsRequest | 
    try {
      ExpiringTargetPatchResponse result = apiInstance.patchExpiringTargets(projectKey, environmentKey, featureFlagKey, patchFlagsRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FeatureFlagsApi#patchExpiringTargets");
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
| **patchFlagsRequest** | [**PatchFlagsRequest**](PatchFlagsRequest.md)|  | |

### Return type

[**ExpiringTargetPatchResponse**](ExpiringTargetPatchResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Expiring target response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="patchExpiringUserTargets"></a>
# **patchExpiringUserTargets**
> ExpiringUserTargetPatchResponse patchExpiringUserTargets(projectKey, environmentKey, featureFlagKey, patchFlagsRequest)

Update expiring user targets on feature flag

&gt; ### Contexts are now available &gt; &gt; After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should use [Update expiring context targets on feature flag](/tag/Feature-flags#operation/patchExpiringTargets) instead of this endpoint. To learn more, read [Contexts](https://docs.launchdarkly.com/home/contexts).  Schedule a target for removal from individual targeting on a feature flag. The flag must already serve a variation to specific targets based on their key.  You can add, update, or remove a scheduled removal date. You can only schedule a target for removal on a single variation per flag.  This request only supports semantic patches. To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  ### Instructions  #### addExpireUserTargetDate  Adds a date and time that LaunchDarkly will remove the user from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the user from individual targeting for this flag * &#x60;variationId&#x60;: The version of the flag variation to update. You can retrieve this by making a GET request for the flag. * &#x60;userKey&#x60;: The user key for the user to remove from individual targeting  #### updateExpireUserTargetDate  Updates the date and time that LaunchDarkly will remove the user from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the user from individual targeting for this flag * &#x60;variationId&#x60;: The version of the flag variation to update. You can retrieve this by making a GET request for the flag. * &#x60;userKey&#x60;: The user key for the user to remove from individual targeting  #### removeExpireUserTargetDate  Removes the scheduled removal of the user from the flag&#39;s individual targeting. The user will remain part of the flag&#39;s individual targeting until you explicitly remove them, or until you schedule another removal.  ##### Parameters  * &#x60;variationId&#x60;: The version of the flag variation to update. You can retrieve this by making a GET request for the flag. * &#x60;userKey&#x60;: The user key for the user to remove from individual targeting 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FeatureFlagsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FeatureFlagsApi apiInstance = new FeatureFlagsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    PatchFlagsRequest patchFlagsRequest = new PatchFlagsRequest(); // PatchFlagsRequest | 
    try {
      ExpiringUserTargetPatchResponse result = apiInstance.patchExpiringUserTargets(projectKey, environmentKey, featureFlagKey, patchFlagsRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FeatureFlagsApi#patchExpiringUserTargets");
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
| **patchFlagsRequest** | [**PatchFlagsRequest**](PatchFlagsRequest.md)|  | |

### Return type

[**ExpiringUserTargetPatchResponse**](ExpiringUserTargetPatchResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Expiring user target response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="patchFeatureFlag"></a>
# **patchFeatureFlag**
> FeatureFlag patchFeatureFlag(projectKey, featureFlagKey, patchWithComment)

Update feature flag

Perform a partial update to a feature flag. The request body must be a valid semantic patch or JSON patch.  ### Using semantic patches on a feature flag  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  The body of a semantic patch request for updating feature flags requires an &#x60;environmentKey&#x60; in addition to &#x60;instructions&#x60; and an optional &#x60;comment&#x60;. The body of the request takes the following properties:  * &#x60;comment&#x60; (string): (Optional) A description of the update. * &#x60;environmentKey&#x60; (string): (Required) The key of the LaunchDarkly environment. * &#x60;instructions&#x60; (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a &#x60;kind&#x60; property that indicates the instruction. If the action requires parameters, you must include those parameters as additional fields in the object. The body of a single semantic patch can contain many different instructions.  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating feature flags.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for turning flags on and off&lt;/summary&gt;  #### turnFlagOff  Sets the flag&#39;s targeting state to **Off**.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;example-environment-key\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnFlagOff\&quot; } ] } &#x60;&#x60;&#x60;  #### turnFlagOn  Sets the flag&#39;s targeting state to **On**.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;example-environment-key\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnFlagOn\&quot; } ] } &#x60;&#x60;&#x60;  &lt;/details&gt;&lt;br /&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for working with targeting and variations&lt;/summary&gt;  Several of the instructions for working with targeting and variations require flag rule IDs, variation IDs, or clause IDs as parameters. Each of these are returned as part of the [Get feature flag](/tag/Feature-flags#operation/getFeatureFlag) response. The flag rule ID is the &#x60;_id&#x60; field of each element in the &#x60;rules&#x60; array within each environment listed in the &#x60;environments&#x60; object. The variation ID is the &#x60;_id&#x60; field in each element of the &#x60;variations&#x60; array. The clause ID is the &#x60;_id&#x60; field of each element of the &#x60;clauses&#x60; array within the &#x60;rules&#x60; array within each environment listed in the &#x60;environments&#x60; object.  #### addClauses  Adds the given clauses to the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauses&#x60;: Array of clause objects, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties.  Use this request body:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addClauses\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauses\&quot;: [{    \&quot;contextKind\&quot;: \&quot;user\&quot;,    \&quot;attribute\&quot;: \&quot;country\&quot;,    \&quot;op\&quot;: \&quot;in\&quot;,    \&quot;negate\&quot;: false,    \&quot;values\&quot;: [\&quot;USA\&quot;, \&quot;Canada\&quot;]   }]  }] } &#x60;&#x60;&#x60;  #### addPrerequisite  Adds the flag indicated by &#x60;key&#x60; with variation &#x60;variationId&#x60; as a prerequisite to the flag in the path parameter.  ##### Parameters  - &#x60;key&#x60;: Flag key of the prerequisite flag. - &#x60;variationId&#x60;: ID of a variation of the prerequisite flag.  Use this request body:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addPrerequisite\&quot;,   \&quot;key\&quot;: \&quot;example-prereq-flag-key\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### addRule  Adds a new targeting rule to the flag. The rule may contain &#x60;clauses&#x60; and serve the variation that &#x60;variationId&#x60; indicates, or serve a percentage rollout that &#x60;rolloutWeights&#x60;, &#x60;rolloutBucketBy&#x60;, and &#x60;rolloutContextKind&#x60; indicate.  If you set &#x60;beforeRuleId&#x60;, this adds the new rule before the indicated rule. Otherwise, adds the new rule to the end of the list.  ##### Parameters  - &#x60;clauses&#x60;: Array of clause objects, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. - &#x60;beforeRuleId&#x60;: (Optional) ID of a flag rule. - &#x60;variationId&#x60;: ID of a variation of the flag. - &#x60;rolloutWeights&#x60;: (Optional) Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addRule\&quot;,     \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,     \&quot;clauses\&quot;: [{       \&quot;contextKind\&quot;: \&quot;organization\&quot;,       \&quot;attribute\&quot;: \&quot;located_in\&quot;,       \&quot;op\&quot;: \&quot;in\&quot;,       \&quot;negate\&quot;: false,       \&quot;values\&quot;: [\&quot;Sweden\&quot;, \&quot;Norway\&quot;]     }]   }] } &#x60;&#x60;&#x60;  #### addTargets  Adds context keys to the individual context targets for the context kind that &#x60;contextKind&#x60; specifies and the variation that &#x60;variationId&#x60; specifies. Returns an error if this causes the flag to target the same context key in multiple variations.  ##### Parameters  - &#x60;values&#x60;: List of context keys. - &#x60;contextKind&#x60;: (Optional) Context kind to target, defaults to &#x60;user&#x60; - &#x60;variationId&#x60;: ID of a variation on the flag.  Use this request body:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addTargets\&quot;,   \&quot;values\&quot;: [\&quot;context-key-123abc\&quot;, \&quot;context-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### addUserTargets  Adds user keys to the individual user targets for the variation that &#x60;variationId&#x60; specifies. Returns an error if this causes the flag to target the same user key in multiple variations.  ##### Parameters  - &#x60;values&#x60;: List of user keys. - &#x60;variationId&#x60;: ID of a variation on the flag.  Use this request body:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addUserTargets\&quot;,   \&quot;values\&quot;: [\&quot;user-key-123abc\&quot;, \&quot;user-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### addValuesToClause  Adds &#x60;values&#x60; to the values of the clause that &#x60;ruleId&#x60; and &#x60;clauseId&#x60; indicate. Does not update the context kind, attribute, or operator.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings.  Use this request body:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addValuesToClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;,   \&quot;values\&quot;: [\&quot;beta_testers\&quot;]  }] } &#x60;&#x60;&#x60;  #### clearTargets  Removes all individual targets from the variation that &#x60;variationId&#x60; specifies. This includes both user and non-user targets.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation on the flag.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;clearTargets\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### clearUserTargets  Removes all individual user targets from the variation that &#x60;variationId&#x60; specifies.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation on the flag.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;clearUserTargets\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### removeClauses  Removes the clauses specified by &#x60;clauseIds&#x60; from the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseIds&#x60;: Array of IDs of clauses in the rule.  Use this request body:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeClauses\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseIds\&quot;: [\&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;, \&quot;36a461dc-235e-4b08-97b9-73ce9365873e\&quot;]  }] } &#x60;&#x60;&#x60;  #### removePrerequisite  Removes the prerequisite flag indicated by &#x60;key&#x60;. Does nothing if this prerequisite does not exist.  ##### Parameters  - &#x60;key&#x60;: Flag key of an existing prerequisite flag.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removePrerequisite\&quot;, \&quot;key\&quot;: \&quot;prereq-flag-key-123abc\&quot; } ] } &#x60;&#x60;&#x60;  #### removeRule  Removes the targeting rule specified by &#x60;ruleId&#x60;. Does nothing if the rule does not exist.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removeRule\&quot;, \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot; } ] } &#x60;&#x60;&#x60;  #### removeTargets  Removes context keys from the individual context targets for the context kind that &#x60;contextKind&#x60; specifies and the variation that &#x60;variationId&#x60; specifies. Does nothing if the flag does not target the context keys.  ##### Parameters  - &#x60;values&#x60;: List of context keys. - &#x60;contextKind&#x60;: (Optional) Context kind to target, defaults to &#x60;user&#x60; - &#x60;variationId&#x60;: ID of a flag variation.  Use this request body:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeTargets\&quot;,   \&quot;values\&quot;: [\&quot;context-key-123abc\&quot;, \&quot;context-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### removeUserTargets  Removes user keys from the individual user targets for the variation that &#x60;variationId&#x60; specifies. Does nothing if the flag does not target the user keys.  ##### Parameters  - &#x60;values&#x60;: List of user keys. - &#x60;variationId&#x60;: ID of a flag variation.  Use this request body:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeUserTargets\&quot;,   \&quot;values\&quot;: [\&quot;user-key-123abc\&quot;, \&quot;user-key-456def\&quot;],   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### removeValuesFromClause  Removes &#x60;values&#x60; from the values of the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60;. Does not update the context kind, attribute, or operator.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings.  Use this request body:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;removeValuesFromClause\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;clauseId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;,   \&quot;values\&quot;: [\&quot;beta_testers\&quot;]  }] } &#x60;&#x60;&#x60;  #### reorderRules  Rearranges the rules to match the order given in &#x60;ruleIds&#x60;. Returns an error if &#x60;ruleIds&#x60; does not match the current set of rules on the flag.  ##### Parameters  - &#x60;ruleIds&#x60;: Array of IDs of all rules in the flag.  Use this request body:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;reorderRules\&quot;,   \&quot;ruleIds\&quot;: [\&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;, \&quot;63c238d1-835d-435e-8f21-c8d5e40b2a3d\&quot;]  }] } &#x60;&#x60;&#x60;  #### replacePrerequisites  Removes all existing prerequisites and replaces them with the list you provide.  ##### Parameters  - &#x60;prerequisites&#x60;: A list of prerequisites. Each item in the list must include a flag &#x60;key&#x60; and &#x60;variationId&#x60;.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;example-environment-key\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replacePrerequisites\&quot;,       \&quot;prerequisites\&quot;: [         {           \&quot;key\&quot;: \&quot;prereq-flag-key\&quot;,           \&quot;variationId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;         },         {           \&quot;key\&quot;: \&quot;another-prereq-flag-key\&quot;,           \&quot;variationId\&quot;: \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;         }       ]     }   ] } &#x60;&#x60;&#x60;  #### replaceRules  Removes all targeting rules for the flag and replaces them with the list you provide.  ##### Parameters  - &#x60;rules&#x60;: A list of rules.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;example-environment-key\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replaceRules\&quot;,       \&quot;rules\&quot;: [         {           \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,           \&quot;description\&quot;: \&quot;My new rule\&quot;,           \&quot;clauses\&quot;: [             {               \&quot;contextKind\&quot;: \&quot;user\&quot;,               \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,               \&quot;op\&quot;: \&quot;segmentMatch\&quot;,               \&quot;values\&quot;: [\&quot;test\&quot;]             }           ],           \&quot;trackEvents\&quot;: true         }       ]     }   ] } &#x60;&#x60;&#x60;  #### replaceTargets  Removes all existing targeting and replaces it with the list of targets you provide.  ##### Parameters  - &#x60;targets&#x60;: A list of context targeting. Each item in the list includes an optional &#x60;contextKind&#x60; that defaults to &#x60;user&#x60;, a required &#x60;variationId&#x60;, and a required list of &#x60;values&#x60;.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replaceTargets\&quot;,       \&quot;targets\&quot;: [         {           \&quot;contextKind\&quot;: \&quot;user\&quot;,           \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,           \&quot;values\&quot;: [\&quot;user-key-123abc\&quot;]         },         {           \&quot;contextKind\&quot;: \&quot;device\&quot;,           \&quot;variationId\&quot;: \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;,           \&quot;values\&quot;: [\&quot;device-key-456def\&quot;]         }       ]     }       ] } &#x60;&#x60;&#x60;  #### replaceUserTargets  Removes all existing user targeting and replaces it with the list of targets you provide. In the list of targets, you must include a target for each of the flag&#39;s variations.  ##### Parameters  - &#x60;targets&#x60;: A list of user targeting. Each item in the list must include a &#x60;variationId&#x60; and a list of &#x60;values&#x60;.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;example-environment-key\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replaceUserTargets\&quot;,       \&quot;targets\&quot;: [         {           \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;,           \&quot;values\&quot;: [\&quot;user-key-123abc\&quot;, \&quot;user-key-456def\&quot;]         },         {           \&quot;variationId\&quot;: \&quot;e5830889-1ec5-4b0c-9cc9-c48790090c43\&quot;,           \&quot;values\&quot;: [\&quot;user-key-789ghi\&quot;]         }       ]     }   ] } &#x60;&#x60;&#x60;  #### updateClause  Replaces the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60; with &#x60;clause&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;clause&#x60;: New &#x60;clause&#x60; object, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;updateClause\&quot;,     \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,     \&quot;clauseId\&quot;: \&quot;10c7462a-2062-45ba-a8bb-dfb3de0f8af5\&quot;,     \&quot;clause\&quot;: {       \&quot;contextKind\&quot;: \&quot;user\&quot;,       \&quot;attribute\&quot;: \&quot;country\&quot;,       \&quot;op\&quot;: \&quot;in\&quot;,       \&quot;negate\&quot;: false,       \&quot;values\&quot;: [\&quot;Mexico\&quot;, \&quot;Canada\&quot;]     }   }] } &#x60;&#x60;&#x60;  #### updateFallthroughVariationOrRollout  Updates the default or \&quot;fallthrough\&quot; rule for the flag, which the flag serves when a context matches none of the targeting rules. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percent rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation of the flag. or - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Use this request body:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### updateOffVariation  Updates the default off variation to &#x60;variationId&#x60;. The flag serves the default off variation when the flag&#39;s targeting is **Off**.  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation of the flag.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateOffVariation\&quot;, \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot; } ] } &#x60;&#x60;&#x60;  #### updatePrerequisite  Changes the prerequisite flag that &#x60;key&#x60; indicates to use the variation that &#x60;variationId&#x60; indicates. Returns an error if this prerequisite does not exist.  ##### Parameters  - &#x60;key&#x60;: Flag key of an existing prerequisite flag. - &#x60;variationId&#x60;: ID of a variation of the prerequisite flag.  Use this request body:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updatePrerequisite\&quot;,   \&quot;key\&quot;: \&quot;example-prereq-flag-key\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### updateRuleDescription  Updates the description of the feature flag rule.  ##### Parameters  - &#x60;description&#x60;: The new human-readable description for this rule. - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the flag.  Use this request body:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleDescription\&quot;,   \&quot;description\&quot;: \&quot;New rule description\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;  }] } &#x60;&#x60;&#x60;  #### updateRuleTrackEvents  Updates whether or not LaunchDarkly tracks events for the feature flag associated with this rule.  ##### Parameters  - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the flag. - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Use this request body:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleTrackEvents\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;trackEvents\&quot;: true  }] } &#x60;&#x60;&#x60;  #### updateRuleVariationOrRollout  Updates what &#x60;ruleId&#x60; serves when its clauses evaluate to true. The rule can serve either the variation that &#x60;variationId&#x60; indicates, or a percent rollout that &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60; indicate.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag. - &#x60;variationId&#x60;: ID of a variation of the flag.    or  - &#x60;rolloutWeights&#x60;: Map of &#x60;variationId&#x60; to weight, in thousandths of a percent (0-100000). - &#x60;rolloutBucketBy&#x60;: (Optional) Context attribute available in the specified &#x60;rolloutContextKind&#x60;. - &#x60;rolloutContextKind&#x60;: (Optional) Context kind, defaults to &#x60;user&#x60;  Use this request body:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;updateRuleVariationOrRollout\&quot;,   \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   \&quot;variationId\&quot;: \&quot;2f43f67c-3e4e-4945-a18a-26559378ca00\&quot;  }] } &#x60;&#x60;&#x60;  #### updateTrackEvents  Updates whether or not LaunchDarkly tracks events for the feature flag, for all rules.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateTrackEvents\&quot;, \&quot;trackEvents\&quot;: true } ] } &#x60;&#x60;&#x60;  #### updateTrackEventsFallthrough  Updates whether or not LaunchDarkly tracks events for the feature flag, for the default rule.  ##### Parameters  - &#x60;trackEvents&#x60;: Whether or not events are tracked.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateTrackEventsFallthrough\&quot;, \&quot;trackEvents\&quot;: true } ] } &#x60;&#x60;&#x60;  &lt;/details&gt;&lt;br /&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for updating flag settings&lt;/summary&gt;  #### addCustomProperties  Adds a new custom property to the feature flag. Custom properties are used to associate feature flags with LaunchDarkly integrations. For example, if you create an integration with an issue tracking service, you may want to associate a flag with a list of issues related to a feature&#39;s development.  ##### Parameters   - &#x60;key&#x60;: The custom property key.  - &#x60;name&#x60;: The custom property name.  - &#x60;values&#x60;: A list of the associated values for the custom property.  Use this request body:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;addCustomProperties\&quot;,   \&quot;key\&quot;: \&quot;example-custom-property\&quot;,   \&quot;name\&quot;: \&quot;Example custom property\&quot;,   \&quot;values\&quot;: [\&quot;value1\&quot;, \&quot;value2\&quot;]  }] } &#x60;&#x60;&#x60;  #### addTags  Adds tags to the feature flag.  ##### Parameters  - &#x60;values&#x60;: A list of tags to add.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;addTags\&quot;, \&quot;values\&quot;: [\&quot;tag1\&quot;, \&quot;tag2\&quot;] } ] } &#x60;&#x60;&#x60;  #### makeFlagPermanent  Marks the feature flag as permanent. LaunchDarkly does not prompt you to remove permanent flags, even if one variation is rolled out to all your customers.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;makeFlagPermanent\&quot; } ] } &#x60;&#x60;&#x60;  #### makeFlagTemporary  Marks the feature flag as temporary.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;makeFlagTemporary\&quot; } ] } &#x60;&#x60;&#x60;  #### removeCustomProperties  Removes the associated values from a custom property. If all the associated values are removed, this instruction also removes the custom property.  ##### Parameters   - &#x60;key&#x60;: The custom property key.  - &#x60;values&#x60;: A list of the associated values to remove from the custom property.  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{   \&quot;kind\&quot;: \&quot;replaceCustomProperties\&quot;,   \&quot;key\&quot;: \&quot;example-custom-property\&quot;,   \&quot;values\&quot;: [\&quot;value1\&quot;, \&quot;value2\&quot;]  }] } &#x60;&#x60;&#x60;  #### removeMaintainer  Removes the flag&#39;s maintainer. To set a new maintainer, use the flag&#39;s **Settings** tab in the LaunchDarkly user interface.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removeMaintainer\&quot; } ] } &#x60;&#x60;&#x60;  #### removeTags  Removes tags from the feature flag.  ##### Parameters  - &#x60;values&#x60;: A list of tags to remove.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;removeTags\&quot;, \&quot;values\&quot;: [\&quot;tag1\&quot;, \&quot;tag2\&quot;] } ] } &#x60;&#x60;&#x60;  #### replaceCustomProperties  Replaces the existing associated values for a custom property with the new values.  ##### Parameters   - &#x60;key&#x60;: The custom property key.  - &#x60;name&#x60;: The custom property name.  - &#x60;values&#x60;: A list of the new associated values for the custom property.  Use this request body:  &#x60;&#x60;&#x60;json {  \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,  \&quot;instructions\&quot;: [{    \&quot;kind\&quot;: \&quot;replaceCustomProperties\&quot;,    \&quot;key\&quot;: \&quot;example-custom-property\&quot;,    \&quot;name\&quot;: \&quot;Example custom property\&quot;,    \&quot;values\&quot;: [\&quot;value1\&quot;, \&quot;value2\&quot;]  }] } &#x60;&#x60;&#x60;  #### turnOffClientSideAvailability  Turns off client-side SDK availability for the flag. This is equivalent to unchecking the **SDKs using Mobile Key** and/or **SDKs using client-side ID** boxes for the flag. If you&#39;re using a client-side or mobile SDK, you must expose your feature flags in order for the client-side or mobile SDKs to evaluate them.  ##### Parameters  - &#x60;value&#x60;: Use \&quot;usingMobileKey\&quot; to turn off availability for mobile SDKs. Use \&quot;usingEnvironmentId\&quot; to turn on availability for client-side SDKs.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnOffClientSideAvailability\&quot;, \&quot;value\&quot;: \&quot;usingMobileKey\&quot; } ] } &#x60;&#x60;&#x60;  #### turnOnClientSideAvailability  Turns on client-side SDK availability for the flag. This is equivalent to unchecking the **SDKs using Mobile Key** and/or **SDKs using client-side ID** boxes for the flag. If you&#39;re using a client-side or mobile SDK, you must expose your feature flags in order for the client-side or mobile SDKs to evaluate them.  ##### Parameters  - &#x60;value&#x60;: Use \&quot;usingMobileKey\&quot; to turn on availability for mobile SDKs. Use \&quot;usingEnvironmentId\&quot; to turn on availability for client-side SDKs.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnOnClientSideAvailability\&quot;, \&quot;value\&quot;: \&quot;usingMobileKey\&quot; } ] } &#x60;&#x60;&#x60;  #### updateDescription  Updates the feature flag description.  ##### Parameters  - &#x60;value&#x60;: The new description.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateDescription\&quot;, \&quot;value\&quot;: \&quot;Updated flag description\&quot; } ] } &#x60;&#x60;&#x60; #### updateMaintainerMember  Updates the maintainer of the flag to an existing member and removes the existing maintainer.  ##### Parameters  - &#x60;value&#x60;: The ID of the member.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateMaintainerMember\&quot;, \&quot;value\&quot;: \&quot;61e9b714fd47591727db558a\&quot; } ] } &#x60;&#x60;&#x60;  #### updateMaintainerTeam  Updates the maintainer of the flag to an existing team and removes the existing maintainer.  ##### Parameters  - &#x60;value&#x60;: The key of the team.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateMaintainerTeam\&quot;, \&quot;value\&quot;: \&quot;example-team-key\&quot; } ] } &#x60;&#x60;&#x60;  #### updateName  Updates the feature flag name.  ##### Parameters  - &#x60;value&#x60;: The new name.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;updateName\&quot;, \&quot;value\&quot;: \&quot;Updated flag name\&quot; } ] } &#x60;&#x60;&#x60;  &lt;/details&gt;&lt;br /&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for updating the flag lifecycle&lt;/summary&gt;  #### archiveFlag  Archives the feature flag. This retires it from LaunchDarkly without deleting it. You cannot archive a flag that is a prerequisite of other flags.  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;archiveFlag\&quot; } ] } &#x60;&#x60;&#x60;  #### deleteFlag  Deletes the feature flag and its rules. You cannot restore a deleted flag. If this flag is requested again, the flag value defined in code will be returned for all contexts.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;deleteFlag\&quot; } ] } &#x60;&#x60;&#x60;  #### restoreFlag  Restores the feature flag if it was previously archived.  Use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;environment-key-123abc\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;restoreFlag\&quot; } ] } &#x60;&#x60;&#x60;  &lt;/details&gt;  ### Using JSON Patches on a feature flag If you do not include the header described above, you can use [JSON patch](/reference#updates-using-json-patch).  When using the update feature flag endpoint to add individual targets to a specific variation, there are two different patch documents, depending on whether there are already individual targets for the variation.  If a flag variation already has individual targets, the path for the JSON Patch operation is:  &#x60;&#x60;&#x60;json {   \&quot;op\&quot;: \&quot;add\&quot;,   \&quot;path\&quot;: \&quot;/environments/devint/targets/0/values/-\&quot;,   \&quot;value\&quot;: \&quot;TestClient10\&quot; } &#x60;&#x60;&#x60;  If a flag variation does not already have individual targets, the path for the JSON Patch operation is:  &#x60;&#x60;&#x60;json [   {     \&quot;op\&quot;: \&quot;add\&quot;,     \&quot;path\&quot;: \&quot;/environments/devint/targets/-\&quot;,     \&quot;value\&quot;: { \&quot;variation\&quot;: 0, \&quot;values\&quot;: [\&quot;TestClient10\&quot;] }   } ] &#x60;&#x60;&#x60;   ### Required approvals If a request attempts to alter a flag configuration in an environment where approvals are required for the flag, the request will fail with a 405. Changes to the flag configuration in that environment will require creating an [approval request](/tag/Approvals) or a [workflow](/tag/Workflows-(beta)).  ### Conflicts If a flag configuration change made through this endpoint would cause a pending scheduled change or approval request to fail, this endpoint will return a 400. You can ignore this check by adding an &#x60;ignoreConflicts&#x60; query parameter set to &#x60;true&#x60;. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FeatureFlagsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FeatureFlagsApi apiInstance = new FeatureFlagsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key. The key identifies the flag in your code.
    PatchWithComment patchWithComment = new PatchWithComment(); // PatchWithComment | 
    try {
      FeatureFlag result = apiInstance.patchFeatureFlag(projectKey, featureFlagKey, patchWithComment);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FeatureFlagsApi#patchFeatureFlag");
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
| **featureFlagKey** | **String**| The feature flag key. The key identifies the flag in your code. | |
| **patchWithComment** | [**PatchWithComment**](PatchWithComment.md)|  | |

### Return type

[**FeatureFlag**](FeatureFlag.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Global flag response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |
| **405** | Approval is required to make this request |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a name="postFeatureFlag"></a>
# **postFeatureFlag**
> FeatureFlag postFeatureFlag(projectKey, featureFlagBody, clone)

Create a feature flag

Create a feature flag with the given name, key, and variations.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FeatureFlagsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FeatureFlagsApi apiInstance = new FeatureFlagsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    FeatureFlagBody featureFlagBody = new FeatureFlagBody(); // FeatureFlagBody | 
    String clone = "clone_example"; // String | The key of the feature flag to be cloned. The key identifies the flag in your code. For example, setting `clone=flagKey` copies the full targeting configuration for all environments, including `on/off` state, from the original flag to the new flag.
    try {
      FeatureFlag result = apiInstance.postFeatureFlag(projectKey, featureFlagBody, clone);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FeatureFlagsApi#postFeatureFlag");
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
| **featureFlagBody** | [**FeatureFlagBody**](FeatureFlagBody.md)|  | |
| **clone** | **String**| The key of the feature flag to be cloned. The key identifies the flag in your code. For example, setting &#x60;clone&#x3D;flagKey&#x60; copies the full targeting configuration for all environments, including &#x60;on/off&#x60; state, from the original flag to the new flag. | [optional] |

### Return type

[**FeatureFlag**](FeatureFlag.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Global flag response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

