# EnvironmentsApi

All URIs are relative to *https://app.launchdarkly.com/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteEnvironment**](EnvironmentsApi.md#deleteEnvironment) | **DELETE** /projects/{projectKey}/environments/{environmentKey} | Delete an environment in a specific project.
[**getEnvironment**](EnvironmentsApi.md#getEnvironment) | **GET** /projects/{projectKey}/environments/{environmentKey} | Get an environment given a project and key.
[**patchEnvironment**](EnvironmentsApi.md#patchEnvironment) | **PATCH** /projects/{projectKey}/environments/{environmentKey} | Modify an environment by ID.
[**postEnvironment**](EnvironmentsApi.md#postEnvironment) | **POST** /projects/{projectKey}/environments | Create a new environment in a specified project with a given name, key, and swatch color.
[**resetEnvironmentMobileKey**](EnvironmentsApi.md#resetEnvironmentMobileKey) | **POST** /projects/{projectKey}/environments/{environmentKey}/mobileKey | Reset an environment&#39;s mobile key with an optional expiry time for the old key.
[**resetEnvironmentSDKKey**](EnvironmentsApi.md#resetEnvironmentSDKKey) | **POST** /projects/{projectKey}/environments/{environmentKey}/apiKey | Reset an environment&#39;s SDK key with an optional expiry time for the old key.


<a name="deleteEnvironment"></a>
# **deleteEnvironment**
> deleteEnvironment(projectKey, environmentKey)

Delete an environment in a specific project.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.EnvironmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

EnvironmentsApi apiInstance = new EnvironmentsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
try {
    apiInstance.deleteEnvironment(projectKey, environmentKey);
} catch (ApiException e) {
    System.err.println("Exception when calling EnvironmentsApi#deleteEnvironment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |

### Return type

null (empty response body)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getEnvironment"></a>
# **getEnvironment**
> Environment getEnvironment(projectKey, environmentKey)

Get an environment given a project and key.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.EnvironmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

EnvironmentsApi apiInstance = new EnvironmentsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
try {
    Environment result = apiInstance.getEnvironment(projectKey, environmentKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EnvironmentsApi#getEnvironment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |

### Return type

[**Environment**](Environment.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="patchEnvironment"></a>
# **patchEnvironment**
> Environment patchEnvironment(projectKey, environmentKey, patchDelta)

Modify an environment by ID.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.EnvironmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

EnvironmentsApi apiInstance = new EnvironmentsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
List<PatchOperation> patchDelta = Arrays.asList(new PatchOperation()); // List<PatchOperation> | Requires a JSON Patch representation of the desired changes to the project. 'http://jsonpatch.com/'
try {
    Environment result = apiInstance.patchEnvironment(projectKey, environmentKey, patchDelta);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EnvironmentsApi#patchEnvironment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **patchDelta** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)| Requires a JSON Patch representation of the desired changes to the project. &#39;http://jsonpatch.com/&#39; |

### Return type

[**Environment**](Environment.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="postEnvironment"></a>
# **postEnvironment**
> Environment postEnvironment(projectKey, environmentBody)

Create a new environment in a specified project with a given name, key, and swatch color.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.EnvironmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

EnvironmentsApi apiInstance = new EnvironmentsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
EnvironmentPost environmentBody = new EnvironmentPost(); // EnvironmentPost | New environment.
try {
    Environment result = apiInstance.postEnvironment(projectKey, environmentBody);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EnvironmentsApi#postEnvironment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentBody** | [**EnvironmentPost**](EnvironmentPost.md)| New environment. |

### Return type

[**Environment**](Environment.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="resetEnvironmentMobileKey"></a>
# **resetEnvironmentMobileKey**
> Environment resetEnvironmentMobileKey(projectKey, environmentKey, expiry)

Reset an environment&#39;s mobile key with an optional expiry time for the old key.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.EnvironmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

EnvironmentsApi apiInstance = new EnvironmentsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
Long expiry = 789L; // Long | An expiration time for the old environment SDK or mobile key, expressed as a Unix epoch time in milliseconds. By default, the key will expire immediately
try {
    Environment result = apiInstance.resetEnvironmentMobileKey(projectKey, environmentKey, expiry);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EnvironmentsApi#resetEnvironmentMobileKey");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **expiry** | **Long**| An expiration time for the old environment SDK or mobile key, expressed as a Unix epoch time in milliseconds. By default, the key will expire immediately | [optional]

### Return type

[**Environment**](Environment.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="resetEnvironmentSDKKey"></a>
# **resetEnvironmentSDKKey**
> Environment resetEnvironmentSDKKey(projectKey, environmentKey, expiry)

Reset an environment&#39;s SDK key with an optional expiry time for the old key.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.EnvironmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

EnvironmentsApi apiInstance = new EnvironmentsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
Long expiry = 789L; // Long | An expiration time for the old environment SDK or mobile key, expressed as a Unix epoch time in milliseconds. By default, the key will expire immediately
try {
    Environment result = apiInstance.resetEnvironmentSDKKey(projectKey, environmentKey, expiry);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EnvironmentsApi#resetEnvironmentSDKKey");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **expiry** | **Long**| An expiration time for the old environment SDK or mobile key, expressed as a Unix epoch time in milliseconds. By default, the key will expire immediately | [optional]

### Return type

[**Environment**](Environment.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

