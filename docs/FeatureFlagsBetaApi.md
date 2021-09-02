# FeatureFlagsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getDependentFlags**](FeatureFlagsBetaApi.md#getDependentFlags) | **GET** /api/v2/flags/{projKey}/{flagKey}/dependent-flags | List dependent feature flags
[**getDependentFlagsByEnv**](FeatureFlagsBetaApi.md#getDependentFlagsByEnv) | **GET** /api/v2/flags/{projKey}/{envKey}/{flagKey}/dependent-flags | List dependent feature flags by environment


<a name="getDependentFlags"></a>
# **getDependentFlags**
> MultiEnvironmentDependentFlags getDependentFlags(projKey, flagKey)

List dependent feature flags

List dependent flags across all environments for the flag specified in the path parameters. A dependent flag is a flag that uses another flag as a prerequisite.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FeatureFlagsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FeatureFlagsBetaApi apiInstance = new FeatureFlagsBetaApi(defaultClient);
    String projKey = "projKey_example"; // String | The project key
    String flagKey = "flagKey_example"; // String | The flag key
    try {
      MultiEnvironmentDependentFlags result = apiInstance.getDependentFlags(projKey, flagKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FeatureFlagsBetaApi#getDependentFlags");
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
 **flagKey** | **String**| The flag key |

### Return type

[**MultiEnvironmentDependentFlags**](MultiEnvironmentDependentFlags.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Multi environment dependent flags collection response |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

<a name="getDependentFlagsByEnv"></a>
# **getDependentFlagsByEnv**
> DependentFlagsByEnvironment getDependentFlagsByEnv(projKey, envKey, flagKey)

List dependent feature flags by environment

List dependent flags across all environments for the flag specified in the path parameters. A dependent flag is a flag that uses another flag as a prerequisite.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FeatureFlagsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FeatureFlagsBetaApi apiInstance = new FeatureFlagsBetaApi(defaultClient);
    String projKey = "projKey_example"; // String | The project key
    String envKey = "envKey_example"; // String | The environment key
    String flagKey = "flagKey_example"; // String | The flag key
    try {
      DependentFlagsByEnvironment result = apiInstance.getDependentFlagsByEnv(projKey, envKey, flagKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FeatureFlagsBetaApi#getDependentFlagsByEnv");
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
 **flagKey** | **String**| The flag key |

### Return type

[**DependentFlagsByEnvironment**](DependentFlagsByEnvironment.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Dependent flags collection response |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

