# UserSettingsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getExpiringFlagsForUser**](UserSettingsApi.md#getExpiringFlagsForUser) | **GET** /api/v2/users/{projectKey}/{userKey}/expiring-user-targets/{environmentKey} | Get expiring dates on flags for user |
| [**getUserFlagSetting**](UserSettingsApi.md#getUserFlagSetting) | **GET** /api/v2/users/{projectKey}/{environmentKey}/{userKey}/flags/{featureFlagKey} | Get flag setting for user |
| [**getUserFlagSettings**](UserSettingsApi.md#getUserFlagSettings) | **GET** /api/v2/users/{projectKey}/{environmentKey}/{userKey}/flags | List flag settings for user |
| [**patchExpiringFlagsForUser**](UserSettingsApi.md#patchExpiringFlagsForUser) | **PATCH** /api/v2/users/{projectKey}/{userKey}/expiring-user-targets/{environmentKey} | Update expiring user target for flags |
| [**putFlagSetting**](UserSettingsApi.md#putFlagSetting) | **PUT** /api/v2/users/{projectKey}/{environmentKey}/{userKey}/flags/{featureFlagKey} | Update flag settings for user |


<a id="getExpiringFlagsForUser"></a>
# **getExpiringFlagsForUser**
> ExpiringUserTargetGetResponse getExpiringFlagsForUser(projectKey, userKey, environmentKey)

Get expiring dates on flags for user

Get a list of flags for which the given user is scheduled for removal.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.UserSettingsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    UserSettingsApi apiInstance = new UserSettingsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String userKey = "userKey_example"; // String | The user key
    String environmentKey = "environmentKey_example"; // String | The environment key
    try {
      ExpiringUserTargetGetResponse result = apiInstance.getExpiringFlagsForUser(projectKey, userKey, environmentKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserSettingsApi#getExpiringFlagsForUser");
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
| **userKey** | **String**| The user key | |
| **environmentKey** | **String**| The environment key | |

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

<a id="getUserFlagSetting"></a>
# **getUserFlagSetting**
> UserFlagSetting getUserFlagSetting(projectKey, environmentKey, userKey, featureFlagKey)

Get flag setting for user

Get a single flag setting for a user by flag key. &lt;br /&gt;&lt;br /&gt;The &#x60;_value&#x60; is the flag variation that the user receives. The &#x60;setting&#x60; indicates whether you&#39;ve explicitly targeted a user to receive a particular variation. For example, if you have turned off a feature flag for a user, this setting will be &#x60;false&#x60;. The example response indicates that the user &#x60;Abbie_Braun&#x60; has the &#x60;sort.order&#x60; flag enabled.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.UserSettingsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    UserSettingsApi apiInstance = new UserSettingsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String userKey = "userKey_example"; // String | The user key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    try {
      UserFlagSetting result = apiInstance.getUserFlagSetting(projectKey, environmentKey, userKey, featureFlagKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserSettingsApi#getUserFlagSetting");
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
| **userKey** | **String**| The user key | |
| **featureFlagKey** | **String**| The feature flag key | |

### Return type

[**UserFlagSetting**](UserFlagSetting.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | User flag settings response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="getUserFlagSettings"></a>
# **getUserFlagSettings**
> UserFlagSettings getUserFlagSettings(projectKey, environmentKey, userKey)

List flag settings for user

Get the current flag settings for a given user. &lt;br /&gt;&lt;br /&gt;The &#x60;_value&#x60; is the flag variation that the user receives. The &#x60;setting&#x60; indicates whether you&#39;ve explicitly targeted a user to receive a particular variation. For example, if you have turned off a feature flag for a user, this setting will be &#x60;false&#x60;. The example response indicates that the user &#x60;Abbie_Braun&#x60; has the &#x60;sort.order&#x60; flag enabled and the &#x60;alternate.page&#x60; flag disabled, and that the user has not been explicitly targeted to receive a particular variation.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.UserSettingsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    UserSettingsApi apiInstance = new UserSettingsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String userKey = "userKey_example"; // String | The user key
    try {
      UserFlagSettings result = apiInstance.getUserFlagSettings(projectKey, environmentKey, userKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserSettingsApi#getUserFlagSettings");
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
| **userKey** | **String**| The user key | |

### Return type

[**UserFlagSettings**](UserFlagSettings.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | User flag settings collection response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="patchExpiringFlagsForUser"></a>
# **patchExpiringFlagsForUser**
> ExpiringUserTargetPatchResponse patchExpiringFlagsForUser(projectKey, userKey, environmentKey, patchUsersRequest)

Update expiring user target for flags

Schedule the specified user for removal from individual targeting on one or more flags. The user must already be individually targeted for each flag.  You can add, update, or remove a scheduled removal date. You can only schedule a user for removal on a single variation per flag.  Updating an expiring target uses the semantic patch format. To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](https://launchdarkly.com/docs/api#updates-using-semantic-patch).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating expiring user targets.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating expiring user targets&lt;/strong&gt;&lt;/summary&gt;  #### addExpireUserTargetDate  Adds a date and time that LaunchDarkly will remove the user from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;flagKey&#x60;: The flag key * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the user from individual targeting for this flag.  #### updateExpireUserTargetDate  Updates the date and time that LaunchDarkly will remove the user from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;flagKey&#x60;: The flag key * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the user from individual targeting for this flag. * &#x60;version&#x60;: The version of the expiring user target to update. If included, update will fail if version doesn&#39;t match current version of the expiring user target.  #### removeExpireUserTargetDate  Removes the scheduled removal of the user from the flag&#39;s individual targeting. The user will remain part of the flag&#39;s individual targeting until explicitly removed, or until another removal is scheduled.  ##### Parameters  * &#x60;flagKey&#x60;: The flag key * &#x60;variationId&#x60;: ID of a variation on the flag  &lt;/details&gt; 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.UserSettingsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    UserSettingsApi apiInstance = new UserSettingsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String userKey = "userKey_example"; // String | The user key
    String environmentKey = "environmentKey_example"; // String | The environment key
    PatchUsersRequest patchUsersRequest = new PatchUsersRequest(); // PatchUsersRequest | 
    try {
      ExpiringUserTargetPatchResponse result = apiInstance.patchExpiringFlagsForUser(projectKey, userKey, environmentKey, patchUsersRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserSettingsApi#patchExpiringFlagsForUser");
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
| **userKey** | **String**| The user key | |
| **environmentKey** | **String**| The environment key | |
| **patchUsersRequest** | [**PatchUsersRequest**](PatchUsersRequest.md)|  | |

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

<a id="putFlagSetting"></a>
# **putFlagSetting**
> putFlagSetting(projectKey, environmentKey, userKey, featureFlagKey, valuePut)

Update flag settings for user

Enable or disable a feature flag for a user based on their key.  Omitting the &#x60;setting&#x60; attribute from the request body, or including a &#x60;setting&#x60; of &#x60;null&#x60;, erases the current setting for a user.  If you previously patched the flag, and the patch included the user&#39;s data, LaunchDarkly continues to use that data. If LaunchDarkly has never encountered the user&#39;s key before, it calculates the flag values based on the user key alone. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.UserSettingsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    UserSettingsApi apiInstance = new UserSettingsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String userKey = "userKey_example"; // String | The user key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    ValuePut valuePut = new ValuePut(); // ValuePut | 
    try {
      apiInstance.putFlagSetting(projectKey, environmentKey, userKey, featureFlagKey, valuePut);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserSettingsApi#putFlagSetting");
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
| **userKey** | **String**| The user key | |
| **featureFlagKey** | **String**| The feature flag key | |
| **valuePut** | [**ValuePut**](ValuePut.md)|  | |

### Return type

null (empty response body)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Action succeeded |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

