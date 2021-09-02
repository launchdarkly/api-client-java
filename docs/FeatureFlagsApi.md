# FeatureFlagsApi

All URIs are relative to *https://app.launchdarkly.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**copyFeatureFlag**](FeatureFlagsApi.md#copyFeatureFlag) | **POST** /api/v2/flags/{projKey}/{featureFlagKey}/copy | Copy feature flag
[**deleteFeatureFlag**](FeatureFlagsApi.md#deleteFeatureFlag) | **DELETE** /api/v2/flags/{projKey}/{key} | Delete feature flag
[**getExpiringUserTargets**](FeatureFlagsApi.md#getExpiringUserTargets) | **GET** /api/v2/flags/{projKey}/{flagKey}/expiring-user-targets/{envKey} | Get expiring user targets for feature flag
[**getFeatureFlag**](FeatureFlagsApi.md#getFeatureFlag) | **GET** /api/v2/flags/{projKey}/{key} | Get feature flag
[**getFeatureFlagStatus**](FeatureFlagsApi.md#getFeatureFlagStatus) | **GET** /api/v2/flag-statuses/{projKey}/{envKey}/{key} | Get feature flag status
[**getFeatureFlagStatusAcrossEnvironments**](FeatureFlagsApi.md#getFeatureFlagStatusAcrossEnvironments) | **GET** /api/v2/flag-status/{projKey}/{key} | Get flag status across environments
[**getFeatureFlagStatuses**](FeatureFlagsApi.md#getFeatureFlagStatuses) | **GET** /api/v2/flag-statuses/{projKey}/{envKey} | List feature flag statuses
[**getFeatureFlags**](FeatureFlagsApi.md#getFeatureFlags) | **GET** /api/v2/flags/{projKey} | List feature flags
[**patchExpiringUserTargets**](FeatureFlagsApi.md#patchExpiringUserTargets) | **PATCH** /api/v2/flags/{projKey}/{flagKey}/expiring-user-targets/{envKey} | Update expiring user targets on feature flag
[**patchFeatureFlag**](FeatureFlagsApi.md#patchFeatureFlag) | **PATCH** /api/v2/flags/{projKey}/{key} | Update feature flag
[**postFeatureFlag**](FeatureFlagsApi.md#postFeatureFlag) | **POST** /api/v2/flags/{projKey} | Create a feature flag


<a name="copyFeatureFlag"></a>
# **copyFeatureFlag**
> FeatureFlag copyFeatureFlag(projKey, featureFlagKey, flagCopyConfigPost)

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
    String projKey = "projKey_example"; // String | The project key.
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag's key. The key identifies the flag in your code.
    FlagCopyConfigPost flagCopyConfigPost = new FlagCopyConfigPost(); // FlagCopyConfigPost | 
    try {
      FeatureFlag result = apiInstance.copyFeatureFlag(projKey, featureFlagKey, flagCopyConfigPost);
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
 **projKey** | **String**| The project key. |
 **featureFlagKey** | **String**| The feature flag&#39;s key. The key identifies the flag in your code. |
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
**400** | Invalid request body |  -  |
**401** | Invalid access token |  -  |
**405** | Method not allowed |  -  |
**409** | Status conflict |  -  |
**429** | Rate limited |  -  |

<a name="deleteFeatureFlag"></a>
# **deleteFeatureFlag**
> deleteFeatureFlag(projKey, key)

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
    String projKey = "projKey_example"; // String | The project key.
    String key = "key_example"; // String | The feature flag's key. The key identifies the flag in your code.
    try {
      apiInstance.deleteFeatureFlag(projKey, key);
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
 **projKey** | **String**| The project key. |
 **key** | **String**| The feature flag&#39;s key. The key identifies the flag in your code. |

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
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

<a name="getExpiringUserTargets"></a>
# **getExpiringUserTargets**
> ExpiringUserTargetGetResponse getExpiringUserTargets(projKey, envKey, flagKey)

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
    String projKey = "projKey_example"; // String | The project key.
    String envKey = "envKey_example"; // String | The environment key.
    String flagKey = "flagKey_example"; // String | The feature flag key.
    try {
      ExpiringUserTargetGetResponse result = apiInstance.getExpiringUserTargets(projKey, envKey, flagKey);
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
 **projKey** | **String**| The project key. |
 **envKey** | **String**| The environment key. |
 **flagKey** | **String**| The feature flag key. |

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
> FeatureFlag getFeatureFlag(projKey, key, env)

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
    String projKey = "projKey_example"; // String | The project key
    String key = "key_example"; // String | The feature flag key
    String env = "env_example"; // String | Filter configurations by environment
    try {
      FeatureFlag result = apiInstance.getFeatureFlag(projKey, key, env);
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
 **projKey** | **String**| The project key |
 **key** | **String**| The feature flag key |
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
> FlagStatusRep getFeatureFlagStatus(projKey, envKey, key)

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
    String projKey = "projKey_example"; // String | The project key
    String envKey = "envKey_example"; // String | The environment key
    String key = "key_example"; // String | The feature flag key
    try {
      FlagStatusRep result = apiInstance.getFeatureFlagStatus(projKey, envKey, key);
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
 **projKey** | **String**| The project key |
 **envKey** | **String**| The environment key |
 **key** | **String**| The feature flag key |

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
> FeatureFlagStatusAcrossEnvironments getFeatureFlagStatusAcrossEnvironments(projKey, key, env)

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
    String projKey = "projKey_example"; // String | The project key
    String key = "key_example"; // String | The feature flag key
    String env = "env_example"; // String | Optional environment filter
    try {
      FeatureFlagStatusAcrossEnvironments result = apiInstance.getFeatureFlagStatusAcrossEnvironments(projKey, key, env);
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
 **projKey** | **String**| The project key |
 **key** | **String**| The feature flag key |
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
> FeatureFlagStatuses getFeatureFlagStatuses(projKey, envKey)

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
    String projKey = "projKey_example"; // String | The project key
    String envKey = "envKey_example"; // String | Filter configurations by environment
    try {
      FeatureFlagStatuses result = apiInstance.getFeatureFlagStatuses(projKey, envKey);
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
 **projKey** | **String**| The project key |
 **envKey** | **String**| Filter configurations by environment |

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
> FeatureFlags getFeatureFlags(projKey, env, tag, limit, offset, query, archived, summary, filter, sort)

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
    String projKey = "projKey_example"; // String | The project key
    String env = "env_example"; // String | Filter configurations by environment
    String tag = "tag_example"; // String | Filter feature flags by tag
    Long limit = 56L; // Long | The number of feature flags to return. Defaults to -1, which returns all flags
    Long offset = 56L; // Long | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next limit items
    String query = "query_example"; // String | A string that matches against the flags' keys and names. It is not case sensitive
    Boolean archived = true; // Boolean | A boolean to filter the list to archived flags. When this is absent, only unarchived flags will be returned
    Boolean summary = true; // Boolean | By default in API version >= 1, flags will _not_ include their list of prerequisites, targets or rules.  Set summary=0 to include these fields for each flag returned
    String filter = "filter_example"; // String | A comma-separated list of filters. Each filter is of the form field:value
    String sort = "sort_example"; // String | A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order
    try {
      FeatureFlags result = apiInstance.getFeatureFlags(projKey, env, tag, limit, offset, query, archived, summary, filter, sort);
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
 **projKey** | **String**| The project key |
 **env** | **String**| Filter configurations by environment | [optional]
 **tag** | **String**| Filter feature flags by tag | [optional]
 **limit** | **Long**| The number of feature flags to return. Defaults to -1, which returns all flags | [optional]
 **offset** | **Long**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next limit items | [optional]
 **query** | **String**| A string that matches against the flags&#39; keys and names. It is not case sensitive | [optional]
 **archived** | **Boolean**| A boolean to filter the list to archived flags. When this is absent, only unarchived flags will be returned | [optional]
 **summary** | **Boolean**| By default in API version &gt;&#x3D; 1, flags will _not_ include their list of prerequisites, targets or rules.  Set summary&#x3D;0 to include these fields for each flag returned | [optional]
 **filter** | **String**| A comma-separated list of filters. Each filter is of the form field:value | [optional]
 **sort** | **String**| A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order | [optional]

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
**400** | Invalid request body |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

<a name="patchExpiringUserTargets"></a>
# **patchExpiringUserTargets**
> ExpiringUserTargetPatchResponse patchExpiringUserTargets(projKey, envKey, flagKey, patchWithComment)

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
    String projKey = "projKey_example"; // String | The project key.
    String envKey = "envKey_example"; // String | The environment key.
    String flagKey = "flagKey_example"; // String | The feature flag key.
    PatchWithComment patchWithComment = new PatchWithComment(); // PatchWithComment | 
    try {
      ExpiringUserTargetPatchResponse result = apiInstance.patchExpiringUserTargets(projKey, envKey, flagKey, patchWithComment);
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
 **projKey** | **String**| The project key. |
 **envKey** | **String**| The environment key. |
 **flagKey** | **String**| The feature flag key. |
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
**400** | Invalid request body |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

<a name="patchFeatureFlag"></a>
# **patchFeatureFlag**
> FeatureFlag patchFeatureFlag(projKey, key, patchWithComment)

Update feature flag

Perform a partial update to a feature

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
    String projKey = "projKey_example"; // String | The project key.
    String key = "key_example"; // String | The feature flag's key. The key identifies the flag in your code.
    PatchWithComment patchWithComment = new PatchWithComment(); // PatchWithComment | 
    try {
      FeatureFlag result = apiInstance.patchFeatureFlag(projKey, key, patchWithComment);
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
 **projKey** | **String**| The project key. |
 **key** | **String**| The feature flag&#39;s key. The key identifies the flag in your code. |
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
**400** | Invalid request body |  -  |
**401** | Invalid access token |  -  |
**404** | Invalid resource identifier |  -  |
**409** | Status conflict |  -  |
**429** | Rate limited |  -  |

<a name="postFeatureFlag"></a>
# **postFeatureFlag**
> FeatureFlag postFeatureFlag(projKey, featureFlagBody, clone)

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
    String projKey = "projKey_example"; // String | The project key.
    FeatureFlagBody featureFlagBody = new FeatureFlagBody(); // FeatureFlagBody | 
    String clone = "clone_example"; // String | The key of the feature flag to be cloned. The key identifies the flag in your code. For example, setting `clone=flagKey` copies the full targeting configuration for all environments, including `on/off` state, from the original flag to the new flag.
    try {
      FeatureFlag result = apiInstance.postFeatureFlag(projKey, featureFlagBody, clone);
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
 **projKey** | **String**| The project key. |
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
**400** | Invalid request body |  -  |
**401** | Invalid access token |  -  |
**409** | Status conflict |  -  |
**429** | Rate limited |  -  |

