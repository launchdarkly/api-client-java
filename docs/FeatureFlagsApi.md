# FeatureFlagsApi

All URIs are relative to *https://app.launchdarkly.com/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**copyFeatureFlag**](FeatureFlagsApi.md#copyFeatureFlag) | **POST** /flags/{projectKey}/{featureFlagKey}/copy | Copies the feature flag configuration from one environment to the same feature flag in another environment.
[**deleteFeatureFlag**](FeatureFlagsApi.md#deleteFeatureFlag) | **DELETE** /flags/{projectKey}/{featureFlagKey} | Delete a feature flag in all environments. Be careful-- only delete feature flags that are no longer being used by your application.
[**getExpiringUserTargets**](FeatureFlagsApi.md#getExpiringUserTargets) | **GET** /flags/{projectKey}/{featureFlagKey}/expiring-user-targets/{environmentKey} | Get expiring user targets for feature flag
[**getFeatureFlag**](FeatureFlagsApi.md#getFeatureFlag) | **GET** /flags/{projectKey}/{featureFlagKey} | Get a single feature flag by key.
[**getFeatureFlagStatus**](FeatureFlagsApi.md#getFeatureFlagStatus) | **GET** /flag-statuses/{projectKey}/{environmentKey}/{featureFlagKey} | Get the status for a particular feature flag.
[**getFeatureFlagStatusAcrossEnvironments**](FeatureFlagsApi.md#getFeatureFlagStatusAcrossEnvironments) | **GET** /flag-status/{projectKey}/{featureFlagKey} | Get the status for a particular feature flag across environments
[**getFeatureFlagStatuses**](FeatureFlagsApi.md#getFeatureFlagStatuses) | **GET** /flag-statuses/{projectKey}/{environmentKey} | Get a list of statuses for all feature flags. The status includes the last time the feature flag was requested, as well as the state of the flag.
[**getFeatureFlags**](FeatureFlagsApi.md#getFeatureFlags) | **GET** /flags/{projectKey} | Get a list of all features in the given project.
[**patchExpiringUserTargets**](FeatureFlagsApi.md#patchExpiringUserTargets) | **PATCH** /flags/{projectKey}/{featureFlagKey}/expiring-user-targets/{environmentKey} | Update, add, or delete expiring user targets on feature flag
[**patchFeatureFlag**](FeatureFlagsApi.md#patchFeatureFlag) | **PATCH** /flags/{projectKey}/{featureFlagKey} | Perform a partial update to a feature.
[**postFeatureFlag**](FeatureFlagsApi.md#postFeatureFlag) | **POST** /flags/{projectKey} | Creates a new feature flag.


<a name="copyFeatureFlag"></a>
# **copyFeatureFlag**
> FeatureFlag copyFeatureFlag(projectKey, featureFlagKey, featureFlagCopyBody)

Copies the feature flag configuration from one environment to the same feature flag in another environment.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.FeatureFlagsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

FeatureFlagsApi apiInstance = new FeatureFlagsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String featureFlagKey = "featureFlagKey_example"; // String | The feature flag's key. The key identifies the flag in your code.
FeatureFlagCopyBody featureFlagCopyBody = new FeatureFlagCopyBody(); // FeatureFlagCopyBody | Copy feature flag configurations between environments.
try {
    FeatureFlag result = apiInstance.copyFeatureFlag(projectKey, featureFlagKey, featureFlagCopyBody);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FeatureFlagsApi#copyFeatureFlag");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **featureFlagKey** | **String**| The feature flag&#39;s key. The key identifies the flag in your code. |
 **featureFlagCopyBody** | [**FeatureFlagCopyBody**](FeatureFlagCopyBody.md)| Copy feature flag configurations between environments. |

### Return type

[**FeatureFlag**](FeatureFlag.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteFeatureFlag"></a>
# **deleteFeatureFlag**
> deleteFeatureFlag(projectKey, featureFlagKey)

Delete a feature flag in all environments. Be careful-- only delete feature flags that are no longer being used by your application.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.FeatureFlagsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

FeatureFlagsApi apiInstance = new FeatureFlagsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String featureFlagKey = "featureFlagKey_example"; // String | The feature flag's key. The key identifies the flag in your code.
try {
    apiInstance.deleteFeatureFlag(projectKey, featureFlagKey);
} catch (ApiException e) {
    System.err.println("Exception when calling FeatureFlagsApi#deleteFeatureFlag");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **featureFlagKey** | **String**| The feature flag&#39;s key. The key identifies the flag in your code. |

### Return type

null (empty response body)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getExpiringUserTargets"></a>
# **getExpiringUserTargets**
> UserTargetingExpirationForFlags getExpiringUserTargets(projectKey, environmentKey, featureFlagKey)

Get expiring user targets for feature flag

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.FeatureFlagsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

FeatureFlagsApi apiInstance = new FeatureFlagsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
String featureFlagKey = "featureFlagKey_example"; // String | The feature flag's key. The key identifies the flag in your code.
try {
    UserTargetingExpirationForFlags result = apiInstance.getExpiringUserTargets(projectKey, environmentKey, featureFlagKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FeatureFlagsApi#getExpiringUserTargets");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **featureFlagKey** | **String**| The feature flag&#39;s key. The key identifies the flag in your code. |

### Return type

[**UserTargetingExpirationForFlags**](UserTargetingExpirationForFlags.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getFeatureFlag"></a>
# **getFeatureFlag**
> FeatureFlag getFeatureFlag(projectKey, featureFlagKey, env)

Get a single feature flag by key.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.FeatureFlagsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

FeatureFlagsApi apiInstance = new FeatureFlagsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String featureFlagKey = "featureFlagKey_example"; // String | The feature flag's key. The key identifies the flag in your code.
List<String> env = Arrays.asList("env_example"); // List<String> | By default, each feature will include configurations for each environment. You can filter environments with the env query parameter. For example, setting env=[\"production\"] will restrict the returned configurations to just your production environment.
try {
    FeatureFlag result = apiInstance.getFeatureFlag(projectKey, featureFlagKey, env);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FeatureFlagsApi#getFeatureFlag");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **featureFlagKey** | **String**| The feature flag&#39;s key. The key identifies the flag in your code. |
 **env** | [**List&lt;String&gt;**](String.md)| By default, each feature will include configurations for each environment. You can filter environments with the env query parameter. For example, setting env&#x3D;[\&quot;production\&quot;] will restrict the returned configurations to just your production environment. | [optional]

### Return type

[**FeatureFlag**](FeatureFlag.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getFeatureFlagStatus"></a>
# **getFeatureFlagStatus**
> FeatureFlagStatus getFeatureFlagStatus(projectKey, environmentKey, featureFlagKey)

Get the status for a particular feature flag.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.FeatureFlagsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

FeatureFlagsApi apiInstance = new FeatureFlagsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
String featureFlagKey = "featureFlagKey_example"; // String | The feature flag's key. The key identifies the flag in your code.
try {
    FeatureFlagStatus result = apiInstance.getFeatureFlagStatus(projectKey, environmentKey, featureFlagKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FeatureFlagsApi#getFeatureFlagStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **featureFlagKey** | **String**| The feature flag&#39;s key. The key identifies the flag in your code. |

### Return type

[**FeatureFlagStatus**](FeatureFlagStatus.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getFeatureFlagStatusAcrossEnvironments"></a>
# **getFeatureFlagStatusAcrossEnvironments**
> FeatureFlagStatusAcrossEnvironments getFeatureFlagStatusAcrossEnvironments(projectKey, featureFlagKey)

Get the status for a particular feature flag across environments

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.FeatureFlagsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

FeatureFlagsApi apiInstance = new FeatureFlagsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String featureFlagKey = "featureFlagKey_example"; // String | The feature flag's key. The key identifies the flag in your code.
try {
    FeatureFlagStatusAcrossEnvironments result = apiInstance.getFeatureFlagStatusAcrossEnvironments(projectKey, featureFlagKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FeatureFlagsApi#getFeatureFlagStatusAcrossEnvironments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **featureFlagKey** | **String**| The feature flag&#39;s key. The key identifies the flag in your code. |

### Return type

[**FeatureFlagStatusAcrossEnvironments**](FeatureFlagStatusAcrossEnvironments.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getFeatureFlagStatuses"></a>
# **getFeatureFlagStatuses**
> FeatureFlagStatuses getFeatureFlagStatuses(projectKey, environmentKey)

Get a list of statuses for all feature flags. The status includes the last time the feature flag was requested, as well as the state of the flag.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.FeatureFlagsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

FeatureFlagsApi apiInstance = new FeatureFlagsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
try {
    FeatureFlagStatuses result = apiInstance.getFeatureFlagStatuses(projectKey, environmentKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FeatureFlagsApi#getFeatureFlagStatuses");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |

### Return type

[**FeatureFlagStatuses**](FeatureFlagStatuses.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getFeatureFlags"></a>
# **getFeatureFlags**
> FeatureFlags getFeatureFlags(projectKey, env, summary, archived, limit, offset, filter, sort, tag)

Get a list of all features in the given project.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.FeatureFlagsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

FeatureFlagsApi apiInstance = new FeatureFlagsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
List<String> env = Arrays.asList("env_example"); // List<String> | By default, each feature will include configurations for each environment. You can filter environments with the env query parameter. For example, setting env=[\"production\"] will restrict the returned configurations to just your production environment.
Boolean summary = true; // Boolean | By default in api version >= 1, flags will _not_ include their list of prerequisites, targets or rules.  Set summary=0 to include these fields for each flag returned.
Boolean archived = true; // Boolean | When set to 1, archived flags will be included in the list of flags returned.  By default, archived flags are not included in the list of flags.
BigDecimal limit = new BigDecimal(); // BigDecimal | The number of objects to return. Defaults to -1, which returns everything.
BigDecimal offset = new BigDecimal(); // BigDecimal | Where to start in the list. This is for use with pagination. For example, an offset of 10 would skip the first 10 items and then return the next limit items.
String filter = "filter_example"; // String | A comma-separated list of filters. Each filter is of the form field:value.
String sort = "sort_example"; // String | A comma-separated list of fields to sort by. A field prefixed by a - will be sorted in descending order.
String tag = "tag_example"; // String | Filter by tag. A tag can be used to group flags across projects.
try {
    FeatureFlags result = apiInstance.getFeatureFlags(projectKey, env, summary, archived, limit, offset, filter, sort, tag);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FeatureFlagsApi#getFeatureFlags");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **env** | [**List&lt;String&gt;**](String.md)| By default, each feature will include configurations for each environment. You can filter environments with the env query parameter. For example, setting env&#x3D;[\&quot;production\&quot;] will restrict the returned configurations to just your production environment. | [optional]
 **summary** | **Boolean**| By default in api version &gt;&#x3D; 1, flags will _not_ include their list of prerequisites, targets or rules.  Set summary&#x3D;0 to include these fields for each flag returned. | [optional]
 **archived** | **Boolean**| When set to 1, archived flags will be included in the list of flags returned.  By default, archived flags are not included in the list of flags. | [optional]
 **limit** | **BigDecimal**| The number of objects to return. Defaults to -1, which returns everything. | [optional]
 **offset** | **BigDecimal**| Where to start in the list. This is for use with pagination. For example, an offset of 10 would skip the first 10 items and then return the next limit items. | [optional]
 **filter** | **String**| A comma-separated list of filters. Each filter is of the form field:value. | [optional]
 **sort** | **String**| A comma-separated list of fields to sort by. A field prefixed by a - will be sorted in descending order. | [optional]
 **tag** | **String**| Filter by tag. A tag can be used to group flags across projects. | [optional]

### Return type

[**FeatureFlags**](FeatureFlags.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="patchExpiringUserTargets"></a>
# **patchExpiringUserTargets**
> UserTargetingExpirationForFlags patchExpiringUserTargets(projectKey, environmentKey, featureFlagKey, semanticPatchWithComment)

Update, add, or delete expiring user targets on feature flag

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.FeatureFlagsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

FeatureFlagsApi apiInstance = new FeatureFlagsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
String featureFlagKey = "featureFlagKey_example"; // String | The feature flag's key. The key identifies the flag in your code.
Object semanticPatchWithComment = null; // Object | Requires a Semantic Patch representation of the desired changes to the resource. 'https://apidocs.launchdarkly.com/reference#updates-via-semantic-patches'. The addition of comments is also supported.
try {
    UserTargetingExpirationForFlags result = apiInstance.patchExpiringUserTargets(projectKey, environmentKey, featureFlagKey, semanticPatchWithComment);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FeatureFlagsApi#patchExpiringUserTargets");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **featureFlagKey** | **String**| The feature flag&#39;s key. The key identifies the flag in your code. |
 **semanticPatchWithComment** | **Object**| Requires a Semantic Patch representation of the desired changes to the resource. &#39;https://apidocs.launchdarkly.com/reference#updates-via-semantic-patches&#39;. The addition of comments is also supported. |

### Return type

[**UserTargetingExpirationForFlags**](UserTargetingExpirationForFlags.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="patchFeatureFlag"></a>
# **patchFeatureFlag**
> FeatureFlag patchFeatureFlag(projectKey, featureFlagKey, patchComment)

Perform a partial update to a feature.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.FeatureFlagsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

FeatureFlagsApi apiInstance = new FeatureFlagsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String featureFlagKey = "featureFlagKey_example"; // String | The feature flag's key. The key identifies the flag in your code.
PatchComment patchComment = new PatchComment(); // PatchComment | Requires a JSON Patch representation of the desired changes to the project, and an optional comment. 'http://jsonpatch.com/' Feature flag patches also support JSON Merge Patch format. 'https://tools.ietf.org/html/rfc7386' The addition of comments is also supported.
try {
    FeatureFlag result = apiInstance.patchFeatureFlag(projectKey, featureFlagKey, patchComment);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FeatureFlagsApi#patchFeatureFlag");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **featureFlagKey** | **String**| The feature flag&#39;s key. The key identifies the flag in your code. |
 **patchComment** | [**PatchComment**](PatchComment.md)| Requires a JSON Patch representation of the desired changes to the project, and an optional comment. &#39;http://jsonpatch.com/&#39; Feature flag patches also support JSON Merge Patch format. &#39;https://tools.ietf.org/html/rfc7386&#39; The addition of comments is also supported. |

### Return type

[**FeatureFlag**](FeatureFlag.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="postFeatureFlag"></a>
# **postFeatureFlag**
> FeatureFlag postFeatureFlag(projectKey, featureFlagBody, clone)

Creates a new feature flag.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.FeatureFlagsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

FeatureFlagsApi apiInstance = new FeatureFlagsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
FeatureFlagBody featureFlagBody = new FeatureFlagBody(); // FeatureFlagBody | Create a new feature flag.
String clone = "clone_example"; // String | The key of the feature flag to be cloned. The key identifies the flag in your code.  For example, setting clone=flagKey will copy the full targeting configuration for all environments (including on/off state) from the original flag to the new flag.
try {
    FeatureFlag result = apiInstance.postFeatureFlag(projectKey, featureFlagBody, clone);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FeatureFlagsApi#postFeatureFlag");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **featureFlagBody** | [**FeatureFlagBody**](FeatureFlagBody.md)| Create a new feature flag. |
 **clone** | **String**| The key of the feature flag to be cloned. The key identifies the flag in your code.  For example, setting clone&#x3D;flagKey will copy the full targeting configuration for all environments (including on/off state) from the original flag to the new flag. | [optional]

### Return type

[**FeatureFlag**](FeatureFlag.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

