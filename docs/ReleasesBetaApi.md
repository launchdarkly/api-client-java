# ReleasesBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createReleaseForFlag**](ReleasesBetaApi.md#createReleaseForFlag) | **PUT** /api/v2/projects/{projectKey}/flags/{flagKey}/release | Create a new release for flag |
| [**deleteReleaseByFlagKey**](ReleasesBetaApi.md#deleteReleaseByFlagKey) | **DELETE** /api/v2/flags/{projectKey}/{flagKey}/release | Delete a release for flag |
| [**getReleaseByFlagKey**](ReleasesBetaApi.md#getReleaseByFlagKey) | **GET** /api/v2/flags/{projectKey}/{flagKey}/release | Get release for flag |
| [**patchReleaseByFlagKey**](ReleasesBetaApi.md#patchReleaseByFlagKey) | **PATCH** /api/v2/flags/{projectKey}/{flagKey}/release | Patch release for flag |
| [**updatePhaseStatus**](ReleasesBetaApi.md#updatePhaseStatus) | **PUT** /api/v2/projects/{projectKey}/flags/{flagKey}/release/phases/{phaseId} | Update phase status for release |


<a name="createReleaseForFlag"></a>
# **createReleaseForFlag**
> Release createReleaseForFlag(projectKey, flagKey, createReleaseInput)

Create a new release for flag

Creates a release by adding a flag to a release pipeline

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ReleasesBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ReleasesBetaApi apiInstance = new ReleasesBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String flagKey = "flagKey_example"; // String | The flag key
    CreateReleaseInput createReleaseInput = new CreateReleaseInput(); // CreateReleaseInput | 
    try {
      Release result = apiInstance.createReleaseForFlag(projectKey, flagKey, createReleaseInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasesBetaApi#createReleaseForFlag");
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
| **flagKey** | **String**| The flag key | |
| **createReleaseInput** | [**CreateReleaseInput**](CreateReleaseInput.md)|  | |

### Return type

[**Release**](Release.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Release response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limit exceeded |  -  |

<a name="deleteReleaseByFlagKey"></a>
# **deleteReleaseByFlagKey**
> deleteReleaseByFlagKey(projectKey, flagKey)

Delete a release for flag

Deletes a release from a flag

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ReleasesBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ReleasesBetaApi apiInstance = new ReleasesBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String flagKey = "flagKey_example"; // String | The flag key
    try {
      apiInstance.deleteReleaseByFlagKey(projectKey, flagKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasesBetaApi#deleteReleaseByFlagKey");
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
| **flagKey** | **String**| The flag key | |

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
| **204** | Action succeeded |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |

<a name="getReleaseByFlagKey"></a>
# **getReleaseByFlagKey**
> Release getReleaseByFlagKey(projectKey, flagKey)

Get release for flag

Get currently active release for a flag

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ReleasesBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ReleasesBetaApi apiInstance = new ReleasesBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String flagKey = "flagKey_example"; // String | The flag key
    try {
      Release result = apiInstance.getReleaseByFlagKey(projectKey, flagKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasesBetaApi#getReleaseByFlagKey");
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
| **flagKey** | **String**| The flag key | |

### Return type

[**Release**](Release.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Release response |  -  |
| **404** | Invalid resource identifier |  -  |

<a name="patchReleaseByFlagKey"></a>
# **patchReleaseByFlagKey**
> Release patchReleaseByFlagKey(projectKey, flagKey, patchOperation)

Patch release for flag

This endpoint is only available for releases that are part of a legacy release pipeline. Releases for new release pipelines should use the [Update phase status for release](/tag/Releases-(beta)#operation/updatePhaseStatus) endpoint. To learn more about migrating from legacy release pipelines to fully automated release pipelines, read the [Release pipeline migration guide](https://docs.launchdarkly.com/guides/flags/release-pipeline-migration).  Update currently active release for a flag. Updating releases requires the [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) format. To learn more, read [Updates](/#section/Overview/Updates).  You can only use this endpoint to mark a release phase complete or incomplete. To indicate which phase to update, use the array index in the &#x60;path&#x60;. For example, to mark the first phase of a release as complete, use the following request body:  &#x60;&#x60;&#x60;   [     {       \&quot;op\&quot;: \&quot;replace\&quot;,       \&quot;path\&quot;: \&quot;/phase/0/complete\&quot;,       \&quot;value\&quot;: true     }   ] &#x60;&#x60;&#x60; 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ReleasesBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ReleasesBetaApi apiInstance = new ReleasesBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String flagKey = "flagKey_example"; // String | The flag key
    List<PatchOperation> patchOperation = Arrays.asList(); // List<PatchOperation> | 
    try {
      Release result = apiInstance.patchReleaseByFlagKey(projectKey, flagKey, patchOperation);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasesBetaApi#patchReleaseByFlagKey");
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
| **flagKey** | **String**| The flag key | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**Release**](Release.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Release response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="updatePhaseStatus"></a>
# **updatePhaseStatus**
> Release updatePhaseStatus(projectKey, flagKey, phaseId, updatePhaseStatusInput)

Update phase status for release

Updates the execution status of a phase of a release

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ReleasesBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ReleasesBetaApi apiInstance = new ReleasesBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String flagKey = "flagKey_example"; // String | The flag key
    String phaseId = "phaseId_example"; // String | The phase ID
    UpdatePhaseStatusInput updatePhaseStatusInput = new UpdatePhaseStatusInput(); // UpdatePhaseStatusInput | 
    try {
      Release result = apiInstance.updatePhaseStatus(projectKey, flagKey, phaseId, updatePhaseStatusInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasesBetaApi#updatePhaseStatus");
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
| **flagKey** | **String**| The flag key | |
| **phaseId** | **String**| The phase ID | |
| **updatePhaseStatusInput** | [**UpdatePhaseStatusInput**](UpdatePhaseStatusInput.md)|  | |

### Return type

[**Release**](Release.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Action succeeded |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **404** | release or phase not found |  -  |
| **429** | Rate limit exceeded |  -  |

