# ApprovalsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getApprovalRequestSettings**](ApprovalsBetaApi.md#getApprovalRequestSettings) | **GET** /api/v2/approval-requests/projects/{projectKey}/settings | Get approval request settings |
| [**patchApprovalRequest**](ApprovalsBetaApi.md#patchApprovalRequest) | **PATCH** /api/v2/approval-requests/{id} | Update approval request |
| [**patchApprovalRequestSettings**](ApprovalsBetaApi.md#patchApprovalRequestSettings) | **PATCH** /api/v2/approval-requests/projects/{projectKey}/settings | Update approval request settings |
| [**patchFlagConfigApprovalRequest**](ApprovalsBetaApi.md#patchFlagConfigApprovalRequest) | **PATCH** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests/{id} | Update flag approval request |


<a id="getApprovalRequestSettings"></a>
# **getApprovalRequestSettings**
> Map&lt;String, ApprovalRequestSettingWithEnvs&gt; getApprovalRequestSettings(ldAPIVersion, projectKey, environmentKey, resourceKind, expand)

Get approval request settings

Get the approval request settings for the given project, optionally filtered by environment and resource kind.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ApprovalsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ApprovalsBetaApi apiInstance = new ApprovalsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | 
    String environmentKey = "environmentKey_example"; // String | An environment key filter to apply to the approval request settings.
    String resourceKind = "resourceKind_example"; // String | A resource kind filter to apply to the approval request settings.
    String expand = "default,strict"; // String | A comma-separated list of fields to expand in the response. Options include 'default' and 'strict'.
    try {
      Map<String, ApprovalRequestSettingWithEnvs> result = apiInstance.getApprovalRequestSettings(ldAPIVersion, projectKey, environmentKey, resourceKind, expand);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsBetaApi#getApprovalRequestSettings");
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
| **ldAPIVersion** | **String**| Version of the endpoint. | [enum: beta] |
| **projectKey** | **String**|  | |
| **environmentKey** | **String**| An environment key filter to apply to the approval request settings. | [optional] |
| **resourceKind** | **String**| A resource kind filter to apply to the approval request settings. | [optional] |
| **expand** | **String**| A comma-separated list of fields to expand in the response. Options include &#39;default&#39; and &#39;strict&#39;. | [optional] |

### Return type

[**Map&lt;String, ApprovalRequestSettingWithEnvs&gt;**](ApprovalRequestSettingWithEnvs.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful response |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="patchApprovalRequest"></a>
# **patchApprovalRequest**
> FlagConfigApprovalRequestResponse patchApprovalRequest(id)

Update approval request

Perform a partial update to an approval request. Updating an approval request uses the semantic patch format. This endpoint works with any approval requests.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](https://launchdarkly.com/docs/api#updates-using-semantic-patch).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instruction for updating an approval request.  #### addReviewers  Adds the specified members and teams to the existing list of reviewers. You must include at least one of &#x60;notifyMemberIds&#x60; and &#x60;notifyTeamKeys&#x60;.  ##### Parameters  - &#x60;notifyMemberIds&#x60;: (Optional) List of member IDs. - &#x60;notifyTeamKeys&#x60;: (Optional) List of team keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addReviewers\&quot;,     \&quot;notifyMemberIds\&quot;: [ \&quot;user-key-123abc\&quot;, \&quot;user-key-456def\&quot; ],     \&quot;notifyTeamKeys\&quot;: [ \&quot;team-key-789abc\&quot;]   }] } &#x60;&#x60;&#x60; 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ApprovalsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ApprovalsBetaApi apiInstance = new ApprovalsBetaApi(defaultClient);
    String id = "id_example"; // String | The approval ID
    try {
      FlagConfigApprovalRequestResponse result = apiInstance.patchApprovalRequest(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsBetaApi#patchApprovalRequest");
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
| **id** | **String**| The approval ID | |

### Return type

[**FlagConfigApprovalRequestResponse**](FlagConfigApprovalRequestResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Approval request response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="patchApprovalRequestSettings"></a>
# **patchApprovalRequestSettings**
> Map&lt;String, ApprovalRequestSettingWithEnvs&gt; patchApprovalRequestSettings(ldAPIVersion, projectKey, approvalRequestSettingsPatch)

Update approval request settings

Perform a partial update to approval request settings

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ApprovalsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ApprovalsBetaApi apiInstance = new ApprovalsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | 
    ApprovalRequestSettingsPatch approvalRequestSettingsPatch = new ApprovalRequestSettingsPatch(); // ApprovalRequestSettingsPatch | Approval request settings to update
    try {
      Map<String, ApprovalRequestSettingWithEnvs> result = apiInstance.patchApprovalRequestSettings(ldAPIVersion, projectKey, approvalRequestSettingsPatch);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsBetaApi#patchApprovalRequestSettings");
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
| **ldAPIVersion** | **String**| Version of the endpoint. | [enum: beta] |
| **projectKey** | **String**|  | |
| **approvalRequestSettingsPatch** | [**ApprovalRequestSettingsPatch**](ApprovalRequestSettingsPatch.md)| Approval request settings to update | [optional] |

### Return type

[**Map&lt;String, ApprovalRequestSettingWithEnvs&gt;**](ApprovalRequestSettingWithEnvs.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful response |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="patchFlagConfigApprovalRequest"></a>
# **patchFlagConfigApprovalRequest**
> FlagConfigApprovalRequestResponse patchFlagConfigApprovalRequest(projectKey, featureFlagKey, environmentKey, id)

Update flag approval request

Perform a partial update to an approval request. Updating an approval request uses the semantic patch format. This endpoint requires a feature flag key, and can only be used for updating approval requests for flags.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](https://launchdarkly.com/docs/api#updates-using-semantic-patch).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instruction for updating an approval request.  #### addReviewers  Adds the specified members and teams to the existing list of reviewers. You must include at least one of &#x60;notifyMemberIds&#x60; and &#x60;notifyTeamKeys&#x60;.  ##### Parameters  - &#x60;notifyMemberIds&#x60;: (Optional) List of member IDs. - &#x60;notifyTeamKeys&#x60;: (Optional) List of team keys. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ApprovalsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ApprovalsBetaApi apiInstance = new ApprovalsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String id = "id_example"; // String | The approval ID
    try {
      FlagConfigApprovalRequestResponse result = apiInstance.patchFlagConfigApprovalRequest(projectKey, featureFlagKey, environmentKey, id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsBetaApi#patchFlagConfigApprovalRequest");
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
| **environmentKey** | **String**| The environment key | |
| **id** | **String**| The approval ID | |

### Return type

[**FlagConfigApprovalRequestResponse**](FlagConfigApprovalRequestResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Approval request response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

