# UserSettingsApi

All URIs are relative to *https://app.launchdarkly.com/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getUserFlagSetting**](UserSettingsApi.md#getUserFlagSetting) | **GET** /users/{projectKey}/{environmentKey}/{userKey}/flags/{featureFlagKey} | Fetch a single flag setting for a user by key.
[**getUserFlagSettings**](UserSettingsApi.md#getUserFlagSettings) | **GET** /users/{projectKey}/{environmentKey}/{userKey}/flags | Fetch a single flag setting for a user by key.
[**putFlagSetting**](UserSettingsApi.md#putFlagSetting) | **PUT** /users/{projectKey}/{environmentKey}/{userKey}/flags/{featureFlagKey} | Specifically enable or disable a feature flag for a user based on their key.


<a name="getUserFlagSetting"></a>
# **getUserFlagSetting**
> UserFlagSetting getUserFlagSetting(projectKey, environmentKey, userKey, featureFlagKey)

Fetch a single flag setting for a user by key.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.UserSettingsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

UserSettingsApi apiInstance = new UserSettingsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
String userKey = "userKey_example"; // String | The user's key.
String featureFlagKey = "featureFlagKey_example"; // String | The feature flag's key. The key identifies the flag in your code.
try {
    UserFlagSetting result = apiInstance.getUserFlagSetting(projectKey, environmentKey, userKey, featureFlagKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserSettingsApi#getUserFlagSetting");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **userKey** | **String**| The user&#39;s key. |
 **featureFlagKey** | **String**| The feature flag&#39;s key. The key identifies the flag in your code. |

### Return type

[**UserFlagSetting**](UserFlagSetting.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUserFlagSettings"></a>
# **getUserFlagSettings**
> UserFlagSettings getUserFlagSettings(projectKey, environmentKey, userKey)

Fetch a single flag setting for a user by key.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.UserSettingsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

UserSettingsApi apiInstance = new UserSettingsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
String userKey = "userKey_example"; // String | The user's key.
try {
    UserFlagSettings result = apiInstance.getUserFlagSettings(projectKey, environmentKey, userKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserSettingsApi#getUserFlagSettings");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **userKey** | **String**| The user&#39;s key. |

### Return type

[**UserFlagSettings**](UserFlagSettings.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="putFlagSetting"></a>
# **putFlagSetting**
> putFlagSetting(projectKey, environmentKey, userKey, featureFlagKey, userSettingsBody)

Specifically enable or disable a feature flag for a user based on their key.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.UserSettingsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

UserSettingsApi apiInstance = new UserSettingsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
String userKey = "userKey_example"; // String | The user's key.
String featureFlagKey = "featureFlagKey_example"; // String | The feature flag's key. The key identifies the flag in your code.
UserSettingsBody userSettingsBody = new UserSettingsBody(); // UserSettingsBody | 
try {
    apiInstance.putFlagSetting(projectKey, environmentKey, userKey, featureFlagKey, userSettingsBody);
} catch (ApiException e) {
    System.err.println("Exception when calling UserSettingsApi#putFlagSetting");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **userKey** | **String**| The user&#39;s key. |
 **featureFlagKey** | **String**| The feature flag&#39;s key. The key identifies the flag in your code. |
 **userSettingsBody** | [**UserSettingsBody**](UserSettingsBody.md)|  |

### Return type

null (empty response body)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

