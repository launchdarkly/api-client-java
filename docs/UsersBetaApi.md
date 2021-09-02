# UsersBetaApi

All URIs are relative to *https://app.launchdarkly.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getUserAttributeNames**](UsersBetaApi.md#getUserAttributeNames) | **GET** /api/v2/user-attributes/{projectKey}/{environmentKey} | Get user attribute names


<a name="getUserAttributeNames"></a>
# **getUserAttributeNames**
> UserAttributeNamesRep getUserAttributeNames(projectKey, environmentKey)

Get user attribute names

Get all in-use user attributes in the specified environment. The set of in-use attributes typically consists of all attributes seen within the past 30 days.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.UsersBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    UsersBetaApi apiInstance = new UsersBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    try {
      UserAttributeNamesRep result = apiInstance.getUserAttributeNames(projectKey, environmentKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersBetaApi#getUserAttributeNames");
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

[**UserAttributeNamesRep**](UserAttributeNamesRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | User attribute names response |  -  |
**400** | Invalid request body |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |

