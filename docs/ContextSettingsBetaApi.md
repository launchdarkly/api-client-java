# ContextSettingsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**putContextFlagSetting**](ContextSettingsBetaApi.md#putContextFlagSetting) | **PUT** /api/v2/projects/{projectKey}/environments/{environmentKey}/contexts/{contextKind}/{contextKey}/flags/{featureFlagKey} | Update flag settings for context |


<a name="putContextFlagSetting"></a>
# **putContextFlagSetting**
> putContextFlagSetting(projectKey, environmentKey, contextKind, contextKey, featureFlagKey, valuePut)

Update flag settings for context

 Enable or disable a feature flag for a context based on its context kind and key.  Omitting the &#x60;setting&#x60; attribute from the request body, or including a &#x60;setting&#x60; of &#x60;null&#x60;, erases the current setting for a context.  If you previously patched the flag, and the patch included the context&#39;s data, LaunchDarkly continues to use that data. If LaunchDarkly has never encountered the combination of the context&#39;s key and kind before, it calculates the flag values based on the context kind and key. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ContextSettingsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ContextSettingsBetaApi apiInstance = new ContextSettingsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String contextKind = "contextKind_example"; // String | The context kind
    String contextKey = "contextKey_example"; // String | The context key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    ValuePut valuePut = new ValuePut(); // ValuePut | 
    try {
      apiInstance.putContextFlagSetting(projectKey, environmentKey, contextKind, contextKey, featureFlagKey, valuePut);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextSettingsBetaApi#putContextFlagSetting");
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
| **contextKind** | **String**| The context kind | |
| **contextKey** | **String**| The context key | |
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

