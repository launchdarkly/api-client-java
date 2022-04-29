# FeatureFlagsApi

All URIs are relative to *https://app.launchdarkly.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**copyFeatureFlag**](FeatureFlagsApi.md#copyFeatureFlag) | **POST** /api/v2/flags/{projectKey}/{featureFlagKey}/copy | Copy feature flag
[**deleteFeatureFlag**](FeatureFlagsApi.md#deleteFeatureFlag) | **DELETE** /api/v2/flags/{projectKey}/{featureFlagKey} | Delete feature flag
[**getExpiringUserTargets**](FeatureFlagsApi.md#getExpiringUserTargets) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey}/expiring-user-targets/{environmentKey} | Get expiring user targets for feature flag
[**getFeatureFlag**](FeatureFlagsApi.md#getFeatureFlag) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey} | Get feature flag
[**getFeatureFlagStatus**](FeatureFlagsApi.md#getFeatureFlagStatus) | **GET** /api/v2/flag-statuses/{projectKey}/{environmentKey}/{featureFlagKey} | Get feature flag status
[**getFeatureFlagStatusAcrossEnvironments**](FeatureFlagsApi.md#getFeatureFlagStatusAcrossEnvironments) | **GET** /api/v2/flag-status/{projectKey}/{featureFlagKey} | Get flag status across environments
[**getFeatureFlagStatuses**](FeatureFlagsApi.md#getFeatureFlagStatuses) | **GET** /api/v2/flag-statuses/{projectKey}/{environmentKey} | List feature flag statuses
[**getFeatureFlags**](FeatureFlagsApi.md#getFeatureFlags) | **GET** /api/v2/flags/{projectKey} | List feature flags
[**patchExpiringUserTargets**](FeatureFlagsApi.md#patchExpiringUserTargets) | **PATCH** /api/v2/flags/{projectKey}/{featureFlagKey}/expiring-user-targets/{environmentKey} | Update expiring user targets on feature flag
[**patchFeatureFlag**](FeatureFlagsApi.md#patchFeatureFlag) | **PATCH** /api/v2/flags/{projectKey}/{featureFlagKey} | Update feature flag
[**postFeatureFlag**](FeatureFlagsApi.md#postFeatureFlag) | **POST** /api/v2/flags/{projectKey} | Create a feature flag


<a name="copyFeatureFlag"></a>
# **copyFeatureFlag**
> FeatureFlag copyFeatureFlag(projectKey, featureFlagKey, flagCopyConfigPost)

Copy feature flag

The includedActions and excludedActions define the parts of the flag configuration that are copied or not copied. By default, the entire flag configuration is copied.  You can have either &#x60;includedActions&#x60; or &#x60;excludedActions&#x60; but not both.  Valid &#x60;includedActions&#x60; and &#x60;excludedActions&#x60; include:  - &#x60;updateOn&#x60; - &#x60;updatePrerequisites&#x60; - &#x60;updateTargets&#x60; - &#x60;updateRules&#x60; - &#x60;updateFallthrough&#x60; - &#x60;updateOffVariation&#x60;    The &#x60;source&#x60; and &#x60;target&#x60; must be JSON objects if using curl, specifying the environment key and (optional) current flag configuration version in that environment. For example:  &#x60;&#x60;&#x60;json {   \&quot;key\&quot;: \&quot;production\&quot;,   \&quot;currentVersion\&quot;: 3 } &#x60;&#x60;&#x60;  If target is specified as above, the API will test to ensure that the current flag version in the &#x60;production&#x60; environment is &#x60;3&#x60;, and reject attempts to copy settings to &#x60;production&#x60; otherwise. You can use this to enforce optimistic locking on copy attempts. 

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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key |
 **featureFlagKey** | **String**| The feature flag key. The key identifies the flag in your code. |
 **flagCopyConfigPost** | [**FlagCopyConfigPost**](FlagCopyConfigPost.md)|  |

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
**201** | Global flag response |  -  |
**400** | Invalid request |  -  |
**401** | Invalid access token |  -  |
**405** | Method not allowed |  -  |
**409** | Status conflict |  -  |
**429** | Rate limited |  -  |

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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key |
 **featureFlagKey** | **String**| The feature flag key. The key identifies the flag in your code. |

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
**204** | Action succeeded |  -  |
**401** | Invalid access token |  -  |
**404** | Invalid resource identifier |  -  |
**409** | Status conflict |  -  |
**429** | Rate limited |  -  |

<a name="getExpiringUserTargets"></a>
# **getExpiringUserTargets**
> ExpiringUserTargetGetResponse getExpiringUserTargets(projectKey, environmentKey, featureFlagKey)

Get expiring user targets for feature flag

Get a list of user targets on a feature flag that are scheduled for removal.

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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key |
 **environmentKey** | **String**| The environment key |
 **featureFlagKey** | **String**| The feature flag key |

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
**200** | User targeting expirations on feature flag response. |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key |
 **featureFlagKey** | **String**| The feature flag key |
 **env** | **String**| Filter configurations by environment | [optional]

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
**200** | Global flag response |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key |
 **environmentKey** | **String**| The environment key |
 **featureFlagKey** | **String**| The feature flag key |

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
**200** | Flag status response |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key |
 **featureFlagKey** | **String**| The feature flag key |
 **env** | **String**| Optional environment filter | [optional]

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
**200** | Flag status across environments response |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

<a name="getFeatureFlagStatuses"></a>
# **getFeatureFlagStatuses**
> FeatureFlagStatuses getFeatureFlagStatuses(projectKey, environmentKey)

List feature flag statuses

Get a list of statuses for all feature flags. The status includes the last time the feature flag was requested, as well as a state, which is one of the following:  - &#x60;new&#x60;: the feature flag was created within the last seven days, and has not been requested yet - &#x60;active&#x60;: the feature flag was requested by your servers or clients within the last seven days - &#x60;inactive&#x60;: the feature flag was created more than seven days ago, and hasn&#39;t been requested by your servers or clients within the past seven days - &#x60;launched&#x60;: one variation of the feature flag has been rolled out to all your users for at least 7 days 

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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key |
 **environmentKey** | **String**| The environment key |

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
**200** | Flag Statuses collection response |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

<a name="getFeatureFlags"></a>
# **getFeatureFlags**
> FeatureFlags getFeatureFlags(projectKey, env, tag, limit, offset, archived, summary, filter, sort, compare)

List feature flags

Get a list of all features in the given project. By default, each feature includes configurations for each environment. You can filter environments with the env query parameter. For example, setting &#x60;env&#x3D;production&#x60; restricts the returned configurations to just your production environment. You can also filter feature flags by tag with the tag query parameter.  We support the following fields for filters:  - &#x60;query&#x60; is a string that matches against the flags&#39; keys and names. It is not case sensitive. - &#x60;archived&#x60; is a boolean to filter the list to archived flags. When this is absent, only unarchived flags are returned. - &#x60;type&#x60; is a string allowing filtering to &#x60;temporary&#x60; or &#x60;permanent&#x60; flags. - &#x60;status&#x60; is a string allowing filtering to &#x60;new&#x60;, &#x60;inactive&#x60;, &#x60;active&#x60;, or &#x60;launched&#x60; flags in the specified environment. This filter also requires a &#x60;filterEnv&#x60; field to be set to a valid environment. For example: &#x60;filter&#x3D;status:active,filterEnv:production&#x60;. - &#x60;tags&#x60; is a + separated list of tags. It filters the list to members who have all of the tags in the list. - &#x60;hasExperiment&#x60; is a boolean with values of true or false and returns any flags that have an attached metric. - &#x60;hasDataExport&#x60; is a boolean with values of true or false and returns any flags that are currently exporting data in the specified environment. This includes flags that are exporting data via Experimentation. This filter also requires a &#x60;filterEnv&#x60; field to be set to a valid environment key. e.g. &#x60;filter&#x3D;hasExperiment:true,filterEnv:production&#x60; - &#x60;evaluated&#x60; is an object that contains a key of &#x60;after&#x60; and a value in Unix time in milliseconds. This returns all flags that have been evaluated since the time you specify in the environment provided. This filter also requires a &#x60;filterEnv&#x60; field to be set to a valid environment. For example: &#x60;filter&#x3D;evaluated:{\&quot;after\&quot;: 1590768455282},filterEnv:production&#x60;. - &#x60;filterEnv&#x60; is a string with the key of a valid environment. The filterEnv field is used for filters that are environment specific. If there are multiple environment specific filters you should only declare this parameter once. For example: &#x60;filter&#x3D;evaluated:{\&quot;after\&quot;: 1590768455282},filterEnv:production,status:active&#x60;.  An example filter is &#x60;query:abc,tags:foo+bar&#x60;. This matches flags with the string &#x60;abc&#x60; in their key or name, ignoring case, which also have the tags &#x60;foo&#x60; and &#x60;bar&#x60;.  By default, this returns all flags. You can page through the list with the &#x60;limit&#x60; parameter and by following the &#x60;first&#x60;, &#x60;prev&#x60;, &#x60;next&#x60;, and &#x60;last&#x60; links in the returned &#x60;_links&#x60; field. These links will not be present if the pages they refer to don&#39;t exist. For example, the &#x60;first&#x60; and &#x60;prev&#x60; links will be missing from the response on the first page. 

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
    Long offset = 56L; // Long | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next limit items
    Boolean archived = true; // Boolean | A boolean to filter the list to archived flags. When this is absent, only unarchived flags will be returned
    Boolean summary = true; // Boolean | By default in API version >= 1, flags will _not_ include their list of prerequisites, targets or rules.  Set summary=0 to include these fields for each flag returned
    String filter = "filter_example"; // String | A comma-separated list of filters. Each filter is of the form field:value
    String sort = "sort_example"; // String | A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order
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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key |
 **env** | **String**| Filter configurations by environment | [optional]
 **tag** | **String**| Filter feature flags by tag | [optional]
 **limit** | **Long**| The number of feature flags to return. Defaults to -1, which returns all flags | [optional]
 **offset** | **Long**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next limit items | [optional]
 **archived** | **Boolean**| A boolean to filter the list to archived flags. When this is absent, only unarchived flags will be returned | [optional]
 **summary** | **Boolean**| By default in API version &gt;&#x3D; 1, flags will _not_ include their list of prerequisites, targets or rules.  Set summary&#x3D;0 to include these fields for each flag returned | [optional]
 **filter** | **String**| A comma-separated list of filters. Each filter is of the form field:value | [optional]
 **sort** | **String**| A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order | [optional]
 **compare** | **Boolean**| A boolean to filter results by only flags that have differences between environments | [optional]

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
**200** | Global flags collection response |  -  |
**400** | Invalid request |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

<a name="patchExpiringUserTargets"></a>
# **patchExpiringUserTargets**
> ExpiringUserTargetPatchResponse patchExpiringUserTargets(projectKey, environmentKey, featureFlagKey, patchWithComment)

Update expiring user targets on feature flag

Update the list of user targets on a feature flag that are scheduled for removal.

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
    PatchWithComment patchWithComment = new PatchWithComment(); // PatchWithComment | 
    try {
      ExpiringUserTargetPatchResponse result = apiInstance.patchExpiringUserTargets(projectKey, environmentKey, featureFlagKey, patchWithComment);
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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key |
 **environmentKey** | **String**| The environment key |
 **featureFlagKey** | **String**| The feature flag key |
 **patchWithComment** | [**PatchWithComment**](PatchWithComment.md)|  |

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
**200** | User targeting expirations on feature flag response. |  -  |
**400** | Invalid request |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

<a name="patchFeatureFlag"></a>
# **patchFeatureFlag**
> FeatureFlag patchFeatureFlag(projectKey, featureFlagKey, patchWithComment)

Update feature flag

Perform a partial update to a feature flag.  ## Using semantic patches on a feature flag  To use a [semantic patch](/reference#updates-via-semantic-patches) on a feature flag resource, you must include a header in the request. If you call a semantic patch resource without this header, you will receive a &#x60;400&#x60; response because your semantic patch will be interpreted as a JSON patch.  Use this header:  &#x60;&#x60;&#x60; Content-Type: application/json; domain-model&#x3D;launchdarkly.semanticpatch &#x60;&#x60;&#x60;  The body of a semantic patch request takes the following three properties:  1. &#x60;comment&#x60; (string): (Optional) A description of the update. 1. &#x60;environmentKey&#x60; (string): (Required) The key of the LaunchDarkly environment. 1. &#x60;instructions&#x60; (array): (Required) The list of actions to be performed by the update. Each action in the list must be an object/hash table with a &#x60;kind&#x60; property that indicates the instruction. Depending on the &#x60;kind&#x60;, the API may require other parameters. When this is the case, add the parameters as additional fields to the instruction object. Read below for more information on the specific supported semantic patch instructions.  If any instruction in the patch encounters an error, the error will be returned and the flag will not be changed. In general, instructions will silently do nothing if the flag is already in the state requested by the patch instruction. For example, &#x60;removeUserTargets&#x60; does nothing when the targets have already been removed. They will generally error if a parameter refers to something that does not exist, like a variation ID that doesn&#39;t correspond to a variation on the flag or a rule ID that doesn&#39;t belong to a rule on the flag. Other specific error conditions are noted in the instruction descriptions.  ### Instructions  #### &#x60;turnFlagOn&#x60;  Sets the flag&#39;s targeting state to on.  For example, to flip a flag on, use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;example-environment-key\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnFlagOn\&quot; } ] } &#x60;&#x60;&#x60;  #### &#x60;turnFlagOff&#x60;  Sets the flag&#39;s targeting state to off.  For example, to flip a flag off, use this request body:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;example-environment-key\&quot;,   \&quot;instructions\&quot;: [ { \&quot;kind\&quot;: \&quot;turnFlagOff\&quot; } ] } &#x60;&#x60;&#x60;  #### &#x60;addUserTargets&#x60;  Adds the user keys in &#x60;values&#x60; to the individual user targets for the variation specified by &#x60;variationId&#x60;. Returns an error if this causes the same user key to be targeted in multiple variations.  ##### Parameters  - &#x60;values&#x60;: list of user keys - &#x60;variationId&#x60;: ID of a variation on the flag  #### &#x60;removeUserTargets&#x60;  Removes the user keys in &#x60;values&#x60; to the individual user targets for the variation specified by &#x60;variationId&#x60;. Does nothing if the user keys are not targeted.  ##### Parameters  - &#x60;values&#x60;: list of user keys - &#x60;variationId&#x60;: ID of a variation on the flag  #### &#x60;replaceUserTargets&#x60;  Completely replaces the existing set of user targeting. All variations must be provided. Example:  &#x60;&#x60;&#x60;json {   \&quot;kind\&quot;: \&quot;replaceUserTargets\&quot;,   \&quot;targets\&quot;: [     {       \&quot;variationId\&quot;: \&quot;variation-1\&quot;,       \&quot;values\&quot;: [\&quot;blah\&quot;, \&quot;foo\&quot;, \&quot;bar\&quot;]     },     {       \&quot;variationId\&quot;: \&quot;variation-2\&quot;,       \&quot;values\&quot;: [\&quot;abc\&quot;, \&quot;def\&quot;]     }   ] } &#x60;&#x60;&#x60;  ##### Parameters  - &#x60;targets&#x60;: a list of user targeting  #### &#x60;clearUserTargets&#x60;  Removes all individual user targets from the variation specified by &#x60;variationId&#x60;  ##### Parameters  - &#x60;variationId&#x60;: ID of a variation on the flag  #### &#x60;addPrerequisite&#x60;  Adds the flag indicated by &#x60;key&#x60; with variation &#x60;variationId&#x60; as a prerequisite to the flag.  ##### Parameters  - &#x60;key&#x60;: flag key of another flag - &#x60;variationId&#x60;: ID of a variation of the flag with key &#x60;key&#x60;  #### &#x60;removePrerequisite&#x60;  Removes the prerequisite indicated by &#x60;key&#x60;. Does nothing if this prerequisite does not exist.  ##### Parameters  - &#x60;key&#x60;: flag key of an existing prerequisite  #### &#x60;updatePrerequisite&#x60;  Changes the prerequisite with flag key &#x60;key&#x60; to the variation indicated by &#x60;variationId&#x60;. Returns an error if this prerequisite does not exist.  ##### Parameters  - &#x60;key&#x60;: flag key of an existing prerequisite - &#x60;variationId&#x60;: ID of a variation of the flag with key &#x60;key&#x60;  #### &#x60;replacePrerequisites&#x60;  Completely replaces the existing set of prerequisites for a given flag. Example:  &#x60;&#x60;&#x60;json {   \&quot;kind\&quot;: \&quot;replacePrerequisites\&quot;,   \&quot;prerequisites\&quot;: [     {       \&quot;key\&quot;: \&quot;flag-key\&quot;,       \&quot;variationId\&quot;: \&quot;variation-1\&quot;     },     {       \&quot;key\&quot;: \&quot;another-flag\&quot;,       \&quot;variationId\&quot;: \&quot;variation-2\&quot;     }   ] } &#x60;&#x60;&#x60;  ##### Parameters  - &#x60;prerequisites&#x60;: a list of prerequisites  #### &#x60;addRule&#x60;  Adds a new rule to the flag with the given &#x60;clauses&#x60; which serves the variation indicated by &#x60;variationId&#x60; or the percent rollout indicated by &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60;. If &#x60;beforeRuleId&#x60; is set, the rule will be added in the list of rules before the indicated rule. Otherwise, the rule will be added to the end of the list.  ##### Parameters  - &#x60;clauses&#x60;: Array of clauses (see &#x60;addClauses&#x60;) - &#x60;beforeRuleId&#x60;: Optional ID of a rule in the flag - &#x60;variationId&#x60;: ID of a variation of the flag - &#x60;rolloutWeights&#x60;: Map of variationId to weight in thousandths of a percent (0-100000) - &#x60;rolloutBucketBy&#x60;: Optional user attribute  #### &#x60;removeRule&#x60;  Removes the targeting rule specified by &#x60;ruleId&#x60;. Does nothing if the rule does not exist.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag  #### &#x60;replaceRules&#x60;  Completely replaces the existing rules for a given flag. Example:  &#x60;&#x60;&#x60;json {   \&quot;kind\&quot;: \&quot;replaceRules\&quot;,   \&quot;rules\&quot;: [     {       \&quot;variationId\&quot;: \&quot;variation-1\&quot;,       \&quot;description\&quot;: \&quot;myRule\&quot;,       \&quot;clauses\&quot;: [         {           \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,           \&quot;op\&quot;: \&quot;segmentMatch\&quot;,           \&quot;values\&quot;: [\&quot;test\&quot;]         }       ],       \&quot;trackEvents\&quot;: true     }   ] } &#x60;&#x60;&#x60;  ##### Parameters  - &#x60;rules&#x60;: a list of rules  #### &#x60;addClauses&#x60;  Adds the given clauses to the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag - &#x60;clauses&#x60;: Array of clause objects, with &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), and &#x60;values&#x60; (array of strings, numbers, or dates) properties.  #### &#x60;removeClauses&#x60;  Removes the clauses specified by &#x60;clauseIds&#x60; from the rule indicated by &#x60;ruleId&#x60;.  #### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag - &#x60;clauseIds&#x60;: Array of IDs of clauses in the rule  #### &#x60;updateClause&#x60;  Replaces the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60; with &#x60;clause&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag - &#x60;clauseId&#x60;: ID of a clause in that rule - &#x60;clause&#x60;: Clause object  #### &#x60;addValuesToClause&#x60;  Adds &#x60;values&#x60; to the values of the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag - &#x60;clauseId&#x60;: ID of a clause in that rule - &#x60;values&#x60;: Array of strings  #### &#x60;removeValuesFromClause&#x60;  Removes &#x60;values&#x60; from the values of the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60;.  ##### Parameters  &#x60;ruleId&#x60;: ID of a rule in the flag &#x60;clauseId&#x60;: ID of a clause in that rule &#x60;values&#x60;: Array of strings  #### &#x60;reorderRules&#x60;  Rearranges the rules to match the order given in &#x60;ruleIds&#x60;. Will return an error if &#x60;ruleIds&#x60; does not match the current set of rules on the flag.  ##### Parameters  - &#x60;ruleIds&#x60;: Array of IDs of all rules in the flag  #### &#x60;updateRuleVariationOrRollout&#x60;  Updates what the rule indicated by &#x60;ruleId&#x60; serves if its clauses evaluate to true. Can either be a fixed variation indicated by &#x60;variationId&#x60; or a percent rollout indicated by &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the flag - &#x60;variationId&#x60;: ID of a variation of the flag   or - &#x60;rolloutWeights&#x60;: Map of variationId to weight in thousandths of a percent (0-100000) - &#x60;rolloutBucketBy&#x60;: Optional user attribute  #### &#x60;updateFallthroughVariationOrRollout&#x60;  Updates the flag&#39;s fallthrough, which is served if none of the targeting rules match. Can either be a fixed variation indicated by &#x60;variationId&#x60; or a percent rollout indicated by &#x60;rolloutWeights&#x60; and &#x60;rolloutBucketBy&#x60;.  ##### Parameters  &#x60;variationId&#x60;: ID of a variation of the flag or &#x60;rolloutWeights&#x60;: Map of variationId to weight in thousandths of a percent (0-100000) &#x60;rolloutBucketBy&#x60;: Optional user attribute  #### &#x60;updateOffVariation&#x60;  Updates the variation served when the flag&#39;s targeting is off to the variation indicated by &#x60;variationId&#x60;.  ##### Parameters  &#x60;variationId&#x60;: ID of a variation of the flag  ### Example  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;production\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;turnFlagOn\&quot;     },     {       \&quot;kind\&quot;: \&quot;turnFlagOff\&quot;     },     {       \&quot;kind\&quot;: \&quot;addUserTargets\&quot;,       \&quot;variationId\&quot;: \&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;,       \&quot;values\&quot;: [\&quot;userId\&quot;, \&quot;userId2\&quot;]     },     {       \&quot;kind\&quot;: \&quot;removeUserTargets\&quot;,       \&quot;variationId\&quot;: \&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;,       \&quot;values\&quot;: [\&quot;userId3\&quot;, \&quot;userId4\&quot;]     },     {       \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,       \&quot;rolloutWeights\&quot;: {         \&quot;variationId\&quot;: 50000,         \&quot;variationId2\&quot;: 50000       },       \&quot;rolloutBucketBy\&quot;: null     },     {       \&quot;kind\&quot;: \&quot;addRule\&quot;,       \&quot;clauses\&quot;: [         {           \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,           \&quot;negate\&quot;: false,           \&quot;values\&quot;: [\&quot;test-segment\&quot;]         }       ],       \&quot;variationId\&quot;: null,       \&quot;rolloutWeights\&quot;: {         \&quot;variationId\&quot;: 50000,         \&quot;variationId2\&quot;: 50000       },       \&quot;rolloutBucketBy\&quot;: \&quot;key\&quot;     },     {       \&quot;kind\&quot;: \&quot;removeRule\&quot;,       \&quot;ruleId\&quot;: \&quot;99f12464-a429-40fc-86cc-b27612188955\&quot;     },     {       \&quot;kind\&quot;: \&quot;reorderRules\&quot;,       \&quot;ruleIds\&quot;: [\&quot;2f72974e-de68-4243-8dd3-739582147a1f\&quot;, \&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;]     },     {       \&quot;kind\&quot;: \&quot;addClauses\&quot;,       \&quot;ruleId\&quot;: \&quot;1134\&quot;,       \&quot;clauses\&quot;: [         {           \&quot;attribute\&quot;: \&quot;email\&quot;,           \&quot;op\&quot;: \&quot;in\&quot;,           \&quot;negate\&quot;: false,           \&quot;values\&quot;: [\&quot;test@test.com\&quot;]         }       ]     },     {       \&quot;kind\&quot;: \&quot;removeClauses\&quot;,       \&quot;ruleId\&quot;: \&quot;1242529\&quot;,       \&quot;clauseIds\&quot;: [\&quot;8bfb304e-d516-47e5-8727-e7f798e8992d\&quot;]     },     {       \&quot;kind\&quot;: \&quot;updateClause\&quot;,       \&quot;ruleId\&quot;: \&quot;2f72974e-de68-4243-8dd3-739582147a1f\&quot;,       \&quot;clauseId\&quot;: \&quot;309845\&quot;,       \&quot;clause\&quot;: {         \&quot;attribute\&quot;: \&quot;segmentMatch\&quot;,         \&quot;negate\&quot;: false,         \&quot;values\&quot;: [\&quot;test-segment\&quot;]       }     },     {       \&quot;kind\&quot;: \&quot;updateRuleVariationOrRollout\&quot;,       \&quot;ruleId\&quot;: \&quot;2342\&quot;,       \&quot;rolloutWeights\&quot;: null,       \&quot;rolloutBucketBy\&quot;: null     },     {       \&quot;kind\&quot;: \&quot;updateOffVariation\&quot;,       \&quot;variationId\&quot;: \&quot;3242453\&quot;     },     {       \&quot;kind\&quot;: \&quot;addPrerequisite\&quot;,       \&quot;variationId\&quot;: \&quot;234235\&quot;,       \&quot;key\&quot;: \&quot;flagKey2\&quot;     },     {       \&quot;kind\&quot;: \&quot;updatePrerequisite\&quot;,       \&quot;variationId\&quot;: \&quot;234235\&quot;,       \&quot;key\&quot;: \&quot;flagKey2\&quot;     },     {       \&quot;kind\&quot;: \&quot;removePrerequisite\&quot;,       \&quot;key\&quot;: \&quot;flagKey\&quot;     }   ] } &#x60;&#x60;&#x60;  ## Using JSON Patches on a feature flag If you do not include the header described above, you can use [JSON patch](/reference#updates-via-json-patch).  When using the update feature flag endpoint to add individual users to a specific variation, there are two different patch documents, depending on whether users are already being individually targeted for the variation.  If a flag variation already has users individually targeted, the path for the JSON Patch operation is:  &#x60;&#x60;&#x60;json {   \&quot;op\&quot;: \&quot;add\&quot;,   \&quot;path\&quot;: \&quot;/environments/devint/targets/0/values/-\&quot;,   \&quot;value\&quot;: \&quot;TestClient10\&quot; } &#x60;&#x60;&#x60;  If a flag variation does not already have users individually targeted, the path for the JSON Patch operation is:  &#x60;&#x60;&#x60;json [   {     \&quot;op\&quot;: \&quot;add\&quot;,     \&quot;path\&quot;: \&quot;/environments/devint/targets/-\&quot;,     \&quot;value\&quot;: { \&quot;variation\&quot;: 0, \&quot;values\&quot;: [\&quot;TestClient10\&quot;] }   } ] &#x60;&#x60;&#x60;   ## Required approvals If a request attempts to alter a flag configuration in an environment where approvals are required for the flag, the request will fail with a 405. Changes to the flag configuration in that environment will required creating an [approval request](/tag/Approvals) or a [workflow](/tag/Workflows-(beta)).  ## Conflicts If a flag configuration change made through this endpoint would cause a pending scheduled change or approval request to fail, this endpoint will return a 400. You can ignore this check by adding an &#x60;ignoreConflicts&#x60; query parameter set to &#x60;true&#x60;. 

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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key |
 **featureFlagKey** | **String**| The feature flag key. The key identifies the flag in your code. |
 **patchWithComment** | [**PatchWithComment**](PatchWithComment.md)|  |

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
**200** | Global flag response |  -  |
**400** | Invalid request |  -  |
**401** | Invalid access token |  -  |
**404** | Invalid resource identifier |  -  |
**405** | Approval is required to make this request |  -  |
**409** | Status conflict |  -  |
**429** | Rate limited |  -  |

<a name="postFeatureFlag"></a>
# **postFeatureFlag**
> FeatureFlag postFeatureFlag(projectKey, featureFlagBody, clone)

Create a feature flag

Create a feature flag with the given name, key, and variations

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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key |
 **featureFlagBody** | [**FeatureFlagBody**](FeatureFlagBody.md)|  |
 **clone** | **String**| The key of the feature flag to be cloned. The key identifies the flag in your code. For example, setting &#x60;clone&#x3D;flagKey&#x60; copies the full targeting configuration for all environments, including &#x60;on/off&#x60; state, from the original flag to the new flag. | [optional]

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
**201** | Global flag response |  -  |
**400** | Invalid request |  -  |
**401** | Invalid access token |  -  |
**409** | Status conflict |  -  |
**429** | Rate limited |  -  |

