# SegmentsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createBigSegmentExport**](SegmentsApi.md#createBigSegmentExport) | **POST** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/exports | Create big segment export |
| [**createBigSegmentImport**](SegmentsApi.md#createBigSegmentImport) | **POST** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/imports | Create big segment import |
| [**deleteSegment**](SegmentsApi.md#deleteSegment) | **DELETE** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey} | Delete segment |
| [**getBigSegmentExport**](SegmentsApi.md#getBigSegmentExport) | **GET** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/exports/{exportID} | Get big segment export |
| [**getBigSegmentImport**](SegmentsApi.md#getBigSegmentImport) | **GET** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/imports/{importID} | Get big segment import |
| [**getContextInstanceSegmentsMembershipByEnv**](SegmentsApi.md#getContextInstanceSegmentsMembershipByEnv) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/segments/evaluate | List segment memberships for context instance |
| [**getExpiringTargetsForSegment**](SegmentsApi.md#getExpiringTargetsForSegment) | **GET** /api/v2/segments/{projectKey}/{segmentKey}/expiring-targets/{environmentKey} | Get expiring targets for segment |
| [**getExpiringUserTargetsForSegment**](SegmentsApi.md#getExpiringUserTargetsForSegment) | **GET** /api/v2/segments/{projectKey}/{segmentKey}/expiring-user-targets/{environmentKey} | Get expiring user targets for segment |
| [**getSegment**](SegmentsApi.md#getSegment) | **GET** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey} | Get segment |
| [**getSegmentMembershipForContext**](SegmentsApi.md#getSegmentMembershipForContext) | **GET** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/contexts/{contextKey} | Get big segment membership for context |
| [**getSegmentMembershipForUser**](SegmentsApi.md#getSegmentMembershipForUser) | **GET** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/users/{userKey} | Get big segment membership for user |
| [**getSegments**](SegmentsApi.md#getSegments) | **GET** /api/v2/segments/{projectKey}/{environmentKey} | List segments |
| [**patchExpiringTargetsForSegment**](SegmentsApi.md#patchExpiringTargetsForSegment) | **PATCH** /api/v2/segments/{projectKey}/{segmentKey}/expiring-targets/{environmentKey} | Update expiring targets for segment |
| [**patchExpiringUserTargetsForSegment**](SegmentsApi.md#patchExpiringUserTargetsForSegment) | **PATCH** /api/v2/segments/{projectKey}/{segmentKey}/expiring-user-targets/{environmentKey} | Update expiring user targets for segment |
| [**patchSegment**](SegmentsApi.md#patchSegment) | **PATCH** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey} | Patch segment |
| [**postSegment**](SegmentsApi.md#postSegment) | **POST** /api/v2/segments/{projectKey}/{environmentKey} | Create segment |
| [**updateBigSegmentContextTargets**](SegmentsApi.md#updateBigSegmentContextTargets) | **POST** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/contexts | Update context targets on a big segment |
| [**updateBigSegmentTargets**](SegmentsApi.md#updateBigSegmentTargets) | **POST** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/users | Update user context targets on a big segment |


<a id="createBigSegmentExport"></a>
# **createBigSegmentExport**
> createBigSegmentExport(projectKey, environmentKey, segmentKey)

Create big segment export

Starts a new export process for a big segment. This is an export for a synced segment or a list-based segment that can include more than 15,000 entries.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    try {
      apiInstance.createBigSegmentExport(projectKey, environmentKey, segmentKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#createBigSegmentExport");
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
| **segmentKey** | **String**| The segment key | |

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
| **200** | Action succeeded |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="createBigSegmentImport"></a>
# **createBigSegmentImport**
> createBigSegmentImport(projectKey, environmentKey, segmentKey, _file, mode, waitOnApprovals)

Create big segment import

Start a new import process for a big segment. This is an import for a list-based segment that can include more than 15,000 entries.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    File _file = new File("/path/to/file"); // File | CSV file containing keys
    String mode = "mode_example"; // String | Import mode. Use either `merge` or `replace`
    Boolean waitOnApprovals = true; // Boolean | Whether to wait for approvals before processing the import
    try {
      apiInstance.createBigSegmentImport(projectKey, environmentKey, segmentKey, _file, mode, waitOnApprovals);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#createBigSegmentImport");
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
| **segmentKey** | **String**| The segment key | |
| **_file** | **File**| CSV file containing keys | [optional] |
| **mode** | **String**| Import mode. Use either &#x60;merge&#x60; or &#x60;replace&#x60; | [optional] |
| **waitOnApprovals** | **Boolean**| Whether to wait for approvals before processing the import | [optional] |

### Return type

null (empty response body)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Import request submitted successfully |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |
| **409** | Conflicting process |  -  |
| **429** | Rate limited |  -  |

<a id="deleteSegment"></a>
# **deleteSegment**
> deleteSegment(projectKey, environmentKey, segmentKey)

Delete segment

Delete a segment.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    try {
      apiInstance.deleteSegment(projectKey, environmentKey, segmentKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#deleteSegment");
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
| **segmentKey** | **String**| The segment key | |

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
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a id="getBigSegmentExport"></a>
# **getBigSegmentExport**
> Export getBigSegmentExport(projectKey, environmentKey, segmentKey, exportID)

Get big segment export

Returns information about a big segment export process. This is an export for a synced segment or a list-based segment that can include more than 15,000 entries.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    String exportID = "exportID_example"; // String | The export ID
    try {
      Export result = apiInstance.getBigSegmentExport(projectKey, environmentKey, segmentKey, exportID);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#getBigSegmentExport");
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
| **segmentKey** | **String**| The segment key | |
| **exportID** | **String**| The export ID | |

### Return type

[**Export**](Export.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Segment export response |  -  |
| **400** | Invalid request |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="getBigSegmentImport"></a>
# **getBigSegmentImport**
> ModelImport getBigSegmentImport(projectKey, environmentKey, segmentKey, importID)

Get big segment import

Returns information about a big segment import process. This is the import of a list-based segment that can include more than 15,000 entries.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    String importID = "importID_example"; // String | The import ID
    try {
      ModelImport result = apiInstance.getBigSegmentImport(projectKey, environmentKey, segmentKey, importID);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#getBigSegmentImport");
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
| **segmentKey** | **String**| The segment key | |
| **importID** | **String**| The import ID | |

### Return type

[**ModelImport**](ModelImport.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Segment import response |  -  |
| **400** | Invalid request |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="getContextInstanceSegmentsMembershipByEnv"></a>
# **getContextInstanceSegmentsMembershipByEnv**
> ContextInstanceSegmentMemberships getContextInstanceSegmentsMembershipByEnv(projectKey, environmentKey, requestBody)

List segment memberships for context instance

For a given context instance with attributes, get membership details for all segments. In the request body, pass in the context instance.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    Map<String, Object> requestBody = {"address":{"city":"Springfield","street":"123 Main Street"},"jobFunction":"doctor","key":"context-key-123abc","kind":"user","name":"Sandy"}; // Map<String, Object> | 
    try {
      ContextInstanceSegmentMemberships result = apiInstance.getContextInstanceSegmentsMembershipByEnv(projectKey, environmentKey, requestBody);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#getContextInstanceSegmentsMembershipByEnv");
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
| **requestBody** | [**Map&lt;String, Object&gt;**](Object.md)|  | |

### Return type

[**ContextInstanceSegmentMemberships**](ContextInstanceSegmentMemberships.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Context instance segment membership collection response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |

<a id="getExpiringTargetsForSegment"></a>
# **getExpiringTargetsForSegment**
> ExpiringTargetGetResponse getExpiringTargetsForSegment(projectKey, environmentKey, segmentKey)

Get expiring targets for segment

Get a list of a segment&#39;s context targets that are scheduled for removal.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    try {
      ExpiringTargetGetResponse result = apiInstance.getExpiringTargetsForSegment(projectKey, environmentKey, segmentKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#getExpiringTargetsForSegment");
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
| **segmentKey** | **String**| The segment key | |

### Return type

[**ExpiringTargetGetResponse**](ExpiringTargetGetResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Expiring context target response |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="getExpiringUserTargetsForSegment"></a>
# **getExpiringUserTargetsForSegment**
> ExpiringUserTargetGetResponse getExpiringUserTargetsForSegment(projectKey, environmentKey, segmentKey)

Get expiring user targets for segment

&gt; ### Contexts are now available &gt; &gt; After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should use [Get expiring targets for segment](https://launchdarkly.com/docs/api/segments/get-expiring-targets-for-segment) instead of this endpoint. To learn more, read [Contexts](https://launchdarkly.com/docs/home/observability/contexts).  Get a list of a segment&#39;s user targets that are scheduled for removal. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    try {
      ExpiringUserTargetGetResponse result = apiInstance.getExpiringUserTargetsForSegment(projectKey, environmentKey, segmentKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#getExpiringUserTargetsForSegment");
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
| **segmentKey** | **String**| The segment key | |

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
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="getSegment"></a>
# **getSegment**
> UserSegment getSegment(projectKey, environmentKey, segmentKey)

Get segment

Get a single segment by key.&lt;br/&gt;&lt;br/&gt;Segments can be rule-based, list-based, or synced. Big segments include larger list-based segments and synced segments. Some fields in the response only apply to big segments.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    try {
      UserSegment result = apiInstance.getSegment(projectKey, environmentKey, segmentKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#getSegment");
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
| **segmentKey** | **String**| The segment key | |

### Return type

[**UserSegment**](UserSegment.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Segment response |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="getSegmentMembershipForContext"></a>
# **getSegmentMembershipForContext**
> BigSegmentTarget getSegmentMembershipForContext(projectKey, environmentKey, segmentKey, contextKey)

Get big segment membership for context

Get the membership status (included/excluded) for a given context in this big segment. Big segments include larger list-based segments and synced segments. This operation does not support standard segments.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    String contextKey = "contextKey_example"; // String | The context key
    try {
      BigSegmentTarget result = apiInstance.getSegmentMembershipForContext(projectKey, environmentKey, segmentKey, contextKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#getSegmentMembershipForContext");
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
| **segmentKey** | **String**| The segment key | |
| **contextKey** | **String**| The context key | |

### Return type

[**BigSegmentTarget**](BigSegmentTarget.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Segment membership for context response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="getSegmentMembershipForUser"></a>
# **getSegmentMembershipForUser**
> BigSegmentTarget getSegmentMembershipForUser(projectKey, environmentKey, segmentKey, userKey)

Get big segment membership for user

&gt; ### Contexts are now available &gt; &gt; After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should use [Get expiring targets for segment](https://launchdarkly.com/docs/api/segments/get-expiring-targets-for-segment) instead of this endpoint. To learn more, read [Contexts](https://launchdarkly.com/docs/home/observability/contexts).  Get the membership status (included/excluded) for a given user in this big segment. This operation does not support standard segments. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    String userKey = "userKey_example"; // String | The user key
    try {
      BigSegmentTarget result = apiInstance.getSegmentMembershipForUser(projectKey, environmentKey, segmentKey, userKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#getSegmentMembershipForUser");
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
| **segmentKey** | **String**| The segment key | |
| **userKey** | **String**| The user key | |

### Return type

[**BigSegmentTarget**](BigSegmentTarget.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Segment membership for user response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="getSegments"></a>
# **getSegments**
> UserSegments getSegments(projectKey, environmentKey, limit, offset, sort, filter)

List segments

Get a list of all segments in the given project.  Segments can be rule-based, list-based, or synced. Big segments include larger list-based segments and synced segments. Some fields in the response only apply to big segments.  ### Filtering segments  The &#x60;filter&#x60; parameter supports the following operators: &#x60;equals&#x60;, &#x60;anyOf&#x60;, and &#x60;exists&#x60;.  You can also combine filters in the following ways:  - Use a comma (&#x60;,&#x60;) as an AND operator - Use a vertical bar (&#x60;|&#x60;) as an OR operator - Use parentheses (&#x60;()&#x60;) to group filters  #### Supported fields and operators  You can only filter certain fields in segments when using the &#x60;filter&#x60; parameter. Additionally, you can only filter some fields with certain operators.  When you search for segments, the &#x60;filter&#x60; parameter supports the following fields and operators:  |&lt;div style&#x3D;\&quot;width:120px\&quot;&gt;Field&lt;/div&gt; |Description |Supported operators | |---|---|---| | &#x60;excludedKeys&#x60; | The segment keys of segments to exclude from the results. | &#x60;anyOf&#x60; | | &#x60;external&#x60; | Whether the segment is a synced segment. | &#x60;exists&#x60; | | &#x60;includedKeys&#x60; | The segment keys of segments to include in the results. | &#x60;anyOf&#x60; | | &#x60;query&#x60; | A \&quot;fuzzy\&quot; search across segment key, name, and description. Supply a string or list of strings to the operator. | &#x60;equals&#x60; | | &#x60;tags&#x60; | The segment tags. | &#x60;anyOf&#x60; | | &#x60;unbounded&#x60; | Whether the segment is a standard segment (&#x60;false&#x60;) or a big segment (&#x60;true&#x60;). Standard segments include rule-based segments and smaller list-based segments. Big segments include larger list-based segments and synced segments. | &#x60;equals&#x60; |  Here are a few examples:  * The filter &#x60;?filter&#x3D;tags anyOf [\&quot;enterprise\&quot;, \&quot;beta\&quot;],query equals \&quot;toggle\&quot;&#x60; matches segments with \&quot;toggle\&quot; in their key, name, or description that also have \&quot;enterprise\&quot; or \&quot;beta\&quot; as a tag. * The filter &#x60;?filter&#x3D;excludedKeys anyOf [\&quot;segmentKey1\&quot;, \&quot;segmentKey2\&quot;]&#x60; excludes the segments with those keys from the results. * The filter &#x60;?filter&#x3D;unbounded equals true&#x60; matches larger list-based segments and synced segments.  The documented values for &#x60;filter&#x60; query parameters are prior to URL encoding. For example, the &#x60;[&#x60; in &#x60;?filter&#x3D;tags anyOf [\&quot;enterprise\&quot;, \&quot;beta\&quot;]&#x60; must be encoded to &#x60;%5B&#x60;. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    Long limit = 56L; // Long | The number of segments to return. Defaults to 20.
    Long offset = 56L; // Long | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    String sort = "sort_example"; // String | Accepts sorting order and fields. Fields can be comma separated. Possible fields are 'creationDate', 'name', 'lastModified'. Example: `sort=name` sort by names ascending or `sort=-name,creationDate` sort by names descending and creationDate ascending.
    String filter = "filter_example"; // String | Accepts filter by `excludedKeys`, `external`, `includedKeys`, `query`, `tags`, `unbounded`, `view`. To learn more about the filter syntax, read the  'Filtering segments' section above.
    try {
      UserSegments result = apiInstance.getSegments(projectKey, environmentKey, limit, offset, sort, filter);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#getSegments");
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
| **limit** | **Long**| The number of segments to return. Defaults to 20. | [optional] |
| **offset** | **Long**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |
| **sort** | **String**| Accepts sorting order and fields. Fields can be comma separated. Possible fields are &#39;creationDate&#39;, &#39;name&#39;, &#39;lastModified&#39;. Example: &#x60;sort&#x3D;name&#x60; sort by names ascending or &#x60;sort&#x3D;-name,creationDate&#x60; sort by names descending and creationDate ascending. | [optional] |
| **filter** | **String**| Accepts filter by &#x60;excludedKeys&#x60;, &#x60;external&#x60;, &#x60;includedKeys&#x60;, &#x60;query&#x60;, &#x60;tags&#x60;, &#x60;unbounded&#x60;, &#x60;view&#x60;. To learn more about the filter syntax, read the  &#39;Filtering segments&#39; section above. | [optional] |

### Return type

[**UserSegments**](UserSegments.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Segment collection response |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |

<a id="patchExpiringTargetsForSegment"></a>
# **patchExpiringTargetsForSegment**
> ExpiringTargetPatchResponse patchExpiringTargetsForSegment(projectKey, environmentKey, segmentKey, patchSegmentExpiringTargetInputRep)

Update expiring targets for segment

 Update expiring context targets for a segment. Updating a context target expiration uses the semantic patch format.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](https://launchdarkly.com/docs/api#updates-using-semantic-patch).  If the request is well-formed but any of its instructions failed to process, this operation returns status code &#x60;200&#x60;. In this case, the response &#x60;errors&#x60; array will be non-empty.  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating expiring context targets.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating expiring context targets&lt;/strong&gt;&lt;/summary&gt;  #### addExpiringTarget  Schedules a date and time when LaunchDarkly will remove a context from segment targeting. The segment must already have the context as an individual target.  ##### Parameters  - &#x60;targetType&#x60;: The type of individual target for this context. Must be either &#x60;included&#x60; or &#x60;excluded&#x60;. - &#x60;contextKey&#x60;: The context key. - &#x60;contextKind&#x60;: The kind of context being targeted. - &#x60;value&#x60;: The date when the context should expire from the segment targeting, in Unix milliseconds.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addExpiringTarget\&quot;,     \&quot;targetType\&quot;: \&quot;included\&quot;,     \&quot;contextKey\&quot;: \&quot;user-key-123abc\&quot;,     \&quot;contextKind\&quot;: \&quot;user\&quot;,     \&quot;value\&quot;: 1754092860000   }] } &#x60;&#x60;&#x60;  #### updateExpiringTarget  Updates the date and time when LaunchDarkly will remove a context from segment targeting.  ##### Parameters  - &#x60;targetType&#x60;: The type of individual target for this context. Must be either &#x60;included&#x60; or &#x60;excluded&#x60;. - &#x60;contextKey&#x60;: The context key. - &#x60;contextKind&#x60;: The kind of context being targeted. - &#x60;value&#x60;: The new date when the context should expire from the segment targeting, in Unix milliseconds. - &#x60;version&#x60;: (Optional) The version of the expiring target to update. If included, update will fail if version doesn&#39;t match current version of the expiring target.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;updateExpiringTarget\&quot;,     \&quot;targetType\&quot;: \&quot;included\&quot;,     \&quot;contextKey\&quot;: \&quot;user-key-123abc\&quot;,     \&quot;contextKind\&quot;: \&quot;user\&quot;,     \&quot;value\&quot;: 1754179260000   }] } &#x60;&#x60;&#x60;  #### removeExpiringTarget  Removes the scheduled expiration for the context in the segment.  ##### Parameters  - &#x60;targetType&#x60;: The type of individual target for this context. Must be either &#x60;included&#x60; or &#x60;excluded&#x60;. - &#x60;contextKey&#x60;: The context key. - &#x60;contextKind&#x60;: The kind of context being targeted.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;removeExpiringTarget\&quot;,     \&quot;targetType\&quot;: \&quot;included\&quot;,     \&quot;contextKey\&quot;: \&quot;user-key-123abc\&quot;,     \&quot;contextKind\&quot;: \&quot;user\&quot;,   }] } &#x60;&#x60;&#x60;  &lt;/details&gt; 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    PatchSegmentExpiringTargetInputRep patchSegmentExpiringTargetInputRep = new PatchSegmentExpiringTargetInputRep(); // PatchSegmentExpiringTargetInputRep | 
    try {
      ExpiringTargetPatchResponse result = apiInstance.patchExpiringTargetsForSegment(projectKey, environmentKey, segmentKey, patchSegmentExpiringTargetInputRep);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#patchExpiringTargetsForSegment");
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
| **segmentKey** | **String**| The segment key | |
| **patchSegmentExpiringTargetInputRep** | [**PatchSegmentExpiringTargetInputRep**](PatchSegmentExpiringTargetInputRep.md)|  | |

### Return type

[**ExpiringTargetPatchResponse**](ExpiringTargetPatchResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Expiring  target response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a id="patchExpiringUserTargetsForSegment"></a>
# **patchExpiringUserTargetsForSegment**
> ExpiringUserTargetPatchResponse patchExpiringUserTargetsForSegment(projectKey, environmentKey, segmentKey, patchSegmentRequest)

Update expiring user targets for segment

 &gt; ### Contexts are now available &gt; &gt; After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should use [Update expiring targets for segment](https://launchdarkly.com/docs/api/segments/patch-expiring-targets-for-segment) instead of this endpoint. To learn more, read [Contexts](https://launchdarkly.com/docs/home/observability/contexts).  Update expiring user targets for a segment. Updating a user target expiration uses the semantic patch format.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](https://launchdarkly.com/docs/api#updates-using-semantic-patch).  If the request is well-formed but any of its instructions failed to process, this operation returns status code &#x60;200&#x60;. In this case, the response &#x60;errors&#x60; array will be non-empty.  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating expiring user targets.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating expiring user targets&lt;/strong&gt;&lt;/summary&gt;  #### addExpireUserTargetDate  Schedules a date and time when LaunchDarkly will remove a user from segment targeting.  ##### Parameters  - &#x60;targetType&#x60;: A segment&#39;s target type, must be either &#x60;included&#x60; or &#x60;excluded&#x60;. - &#x60;userKey&#x60;: The user key. - &#x60;value&#x60;: The date when the user should expire from the segment targeting, in Unix milliseconds.  #### updateExpireUserTargetDate  Updates the date and time when LaunchDarkly will remove a user from segment targeting.  ##### Parameters  - &#x60;targetType&#x60;: A segment&#39;s target type, must be either &#x60;included&#x60; or &#x60;excluded&#x60;. - &#x60;userKey&#x60;: The user key. - &#x60;value&#x60;: The new date when the user should expire from the segment targeting, in Unix milliseconds. - &#x60;version&#x60;: The segment version.  #### removeExpireUserTargetDate  Removes the scheduled expiration for the user in the segment.  ##### Parameters  - &#x60;targetType&#x60;: A segment&#39;s target type, must be either &#x60;included&#x60; or &#x60;excluded&#x60;. - &#x60;userKey&#x60;: The user key.  &lt;/details&gt; 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    PatchSegmentRequest patchSegmentRequest = new PatchSegmentRequest(); // PatchSegmentRequest | 
    try {
      ExpiringUserTargetPatchResponse result = apiInstance.patchExpiringUserTargetsForSegment(projectKey, environmentKey, segmentKey, patchSegmentRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#patchExpiringUserTargetsForSegment");
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
| **segmentKey** | **String**| The segment key | |
| **patchSegmentRequest** | [**PatchSegmentRequest**](PatchSegmentRequest.md)|  | |

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
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a id="patchSegment"></a>
# **patchSegment**
> UserSegment patchSegment(projectKey, environmentKey, segmentKey, patchWithComment)

Patch segment

Update a segment. The request body must be a valid semantic patch, JSON patch, or JSON merge patch. To learn more the different formats, read [Updates](https://launchdarkly.com/docs/api#updates).  ### Using semantic patches on a segment  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](https://launchdarkly.com/docs/api#updates-using-semantic-patch).  The body of a semantic patch request for updating segments requires an &#x60;environmentKey&#x60; in addition to &#x60;instructions&#x60; and an optional &#x60;comment&#x60;. The body of the request takes the following properties:  * &#x60;comment&#x60; (string): (Optional) A description of the update. * &#x60;environmentKey&#x60; (string): (Required) The key of the LaunchDarkly environment. * &#x60;instructions&#x60; (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a &#x60;kind&#x60; property that indicates the instruction. If the action requires parameters, you must include those parameters as additional fields in the object.  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating segments.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating segment details and settings&lt;/strong&gt;&lt;/summary&gt;  #### addTags  Adds tags to the segment.  ##### Parameters  - &#x60;values&#x60;: A list of tags to add.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addTags\&quot;,     \&quot;values\&quot;: [\&quot;tag1\&quot;, \&quot;tag2\&quot;]   }] } &#x60;&#x60;&#x60;  #### removeTags  Removes tags from the segment.  ##### Parameters  - &#x60;values&#x60;: A list of tags to remove.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;removeTags\&quot;,     \&quot;values\&quot;: [\&quot;tag1\&quot;, \&quot;tag2\&quot;]   }] } &#x60;&#x60;&#x60;  #### updateName  Updates the name of the segment.  ##### Parameters  - &#x60;value&#x60;: Name of the segment.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;updateName\&quot;,     \&quot;value\&quot;: \&quot;Updated segment name\&quot;   }] } &#x60;&#x60;&#x60;  &lt;/details&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating segment individual targets&lt;/strong&gt;&lt;/summary&gt;  #### addExcludedTargets  Adds context keys to the individual context targets excluded from the segment for the specified &#x60;contextKind&#x60;. Returns an error if this causes the same context key to be both included and excluded.  ##### Parameters  - &#x60;contextKind&#x60;: The context kind the targets should be added to. - &#x60;values&#x60;: List of keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addExcludedTargets\&quot;,     \&quot;contextKind\&quot;: \&quot;org\&quot;,     \&quot;values\&quot;: [ \&quot;org-key-123abc\&quot;, \&quot;org-key-456def\&quot; ]   }] } &#x60;&#x60;&#x60;  #### addExcludedUsers  Adds user keys to the individual user targets excluded from the segment. Returns an error if this causes the same user key to be both included and excluded. If you are working with contexts, use &#x60;addExcludedTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;values&#x60;: List of user keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addExcludedUsers\&quot;,     \&quot;values\&quot;: [ \&quot;user-key-123abc\&quot;, \&quot;user-key-456def\&quot; ]   }] } &#x60;&#x60;&#x60;  #### addIncludedTargets  Adds context keys to the individual context targets included in the segment for the specified &#x60;contextKind&#x60;. Returns an error if this causes the same context key to be both included and excluded.  ##### Parameters  - &#x60;contextKind&#x60;: The context kind the targets should be added to. - &#x60;values&#x60;: List of keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addIncludedTargets\&quot;,     \&quot;contextKind\&quot;: \&quot;org\&quot;,     \&quot;values\&quot;: [ \&quot;org-key-123abc\&quot;, \&quot;org-key-456def\&quot; ]   }] } &#x60;&#x60;&#x60;  #### addIncludedUsers  Adds user keys to the individual user targets included in the segment. Returns an error if this causes the same user key to be both included and excluded. If you are working with contexts, use &#x60;addIncludedTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;values&#x60;: List of user keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addIncludedUsers\&quot;,     \&quot;values\&quot;: [ \&quot;user-key-123abc\&quot;, \&quot;user-key-456def\&quot; ]   }] } &#x60;&#x60;&#x60;  #### removeExcludedTargets  Removes context keys from the individual context targets excluded from the segment for the specified &#x60;contextKind&#x60;.  ##### Parameters  - &#x60;contextKind&#x60;: The context kind the targets should be removed from. - &#x60;values&#x60;: List of keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;removeExcludedTargets\&quot;,     \&quot;contextKind\&quot;: \&quot;org\&quot;,     \&quot;values\&quot;: [ \&quot;org-key-123abc\&quot;, \&quot;org-key-456def\&quot; ]   }] } &#x60;&#x60;&#x60;  #### removeExcludedUsers  Removes user keys from the individual user targets excluded from the segment. If you are working with contexts, use &#x60;removeExcludedTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;values&#x60;: List of user keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;removeExcludedUsers\&quot;,     \&quot;values\&quot;: [ \&quot;user-key-123abc\&quot;, \&quot;user-key-456def\&quot; ]   }] } &#x60;&#x60;&#x60;  #### removeIncludedTargets  Removes context keys from the individual context targets included in the segment for the specified &#x60;contextKind&#x60;.  ##### Parameters  - &#x60;contextKind&#x60;: The context kind the targets should be removed from. - &#x60;values&#x60;: List of keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;removeIncludedTargets\&quot;,     \&quot;contextKind\&quot;: \&quot;org\&quot;,     \&quot;values\&quot;: [ \&quot;org-key-123abc\&quot;, \&quot;org-key-456def\&quot; ]   }] } &#x60;&#x60;&#x60;  #### removeIncludedUsers  Removes user keys from the individual user targets included in the segment. If you are working with contexts, use &#x60;removeIncludedTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;values&#x60;: List of user keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;removeIncludedUsers\&quot;,     \&quot;values\&quot;: [ \&quot;user-key-123abc\&quot;, \&quot;user-key-456def\&quot; ]   }] } &#x60;&#x60;&#x60;  &lt;/details&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating segment targeting rules&lt;/strong&gt;&lt;/summary&gt;  #### addClauses  Adds the given clauses to the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;clauses&#x60;: Array of clause objects, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, if not provided, defaults to &#x60;user&#x60;. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case. - &#x60;ruleId&#x60;: ID of a rule in the segment.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addClauses\&quot;,     \&quot;clauses\&quot;: [       {         \&quot;attribute\&quot;: \&quot;email\&quot;,         \&quot;negate\&quot;: false,         \&quot;op\&quot;: \&quot;contains\&quot;,         \&quot;values\&quot;: [\&quot;value1\&quot;]       }     ],     \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,   }] } &#x60;&#x60;&#x60;  #### addRule  Adds a new targeting rule to the segment. The rule may contain &#x60;clauses&#x60;.  ##### Parameters  - &#x60;clauses&#x60;: Array of clause objects, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, if not provided, defaults to &#x60;user&#x60;. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case. - &#x60;description&#x60;: A description of the rule.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addRule\&quot;,     \&quot;clauses\&quot;: [       {         \&quot;attribute\&quot;: \&quot;email\&quot;,         \&quot;op\&quot;: \&quot;contains\&quot;,         \&quot;negate\&quot;: false,         \&quot;values\&quot;: [\&quot;@launchdarkly.com\&quot;]       }     ],     \&quot;description\&quot;: \&quot;Targeting rule for LaunchDarkly employees\&quot;,   }] } &#x60;&#x60;&#x60;  #### addValuesToClause  Adds &#x60;values&#x60; to the values of the clause that &#x60;ruleId&#x60; and &#x60;clauseId&#x60; indicate. Does not update the context kind, attribute, or operator.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the segment. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings, case sensitive.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addValuesToClause\&quot;,     \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,     \&quot;clauseId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;,     \&quot;values\&quot;: [\&quot;beta_testers\&quot;]   }] } &#x60;&#x60;&#x60;  #### removeClauses  Removes the clauses specified by &#x60;clauseIds&#x60; from the rule indicated by &#x60;ruleId&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the segment. - &#x60;clauseIds&#x60;: Array of IDs of clauses in the rule.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;removeClauses\&quot;,     \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,     \&quot;clauseIds\&quot;: [\&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;, \&quot;36a461dc-235e-4b08-97b9-73ce9365873e\&quot;]   }] } &#x60;&#x60;&#x60;  #### removeRule  Removes the targeting rule specified by &#x60;ruleId&#x60;. Does nothing if the rule does not exist.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the segment.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;removeRule\&quot;,     \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;   }] } &#x60;&#x60;&#x60;  #### removeValuesFromClause  Removes &#x60;values&#x60; from the values of the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60;. Does not update the context kind, attribute, or operator.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the segment. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;values&#x60;: Array of strings, case sensitive.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;removeValuesFromClause\&quot;,     \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,     \&quot;clauseId\&quot;: \&quot;10a58772-3121-400f-846b-b8a04e8944ed\&quot;,     \&quot;values\&quot;: [\&quot;beta_testers\&quot;]   }] } &#x60;&#x60;&#x60;  #### reorderRules  Rearranges the rules to match the order given in &#x60;ruleIds&#x60;. Returns an error if &#x60;ruleIds&#x60; does not match the current set of rules in the segment.  ##### Parameters  - &#x60;ruleIds&#x60;: Array of IDs of all targeting rules in the segment.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;reorderRules\&quot;,     \&quot;ruleIds\&quot;: [\&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;, \&quot;63c238d1-835d-435e-8f21-c8d5e40b2a3d\&quot;]   }] } &#x60;&#x60;&#x60;  #### updateClause  Replaces the clause indicated by &#x60;ruleId&#x60; and &#x60;clauseId&#x60; with &#x60;clause&#x60;.  ##### Parameters  - &#x60;ruleId&#x60;: ID of a rule in the segment. - &#x60;clauseId&#x60;: ID of a clause in that rule. - &#x60;clause&#x60;: New &#x60;clause&#x60; object, with &#x60;contextKind&#x60; (string), &#x60;attribute&#x60; (string), &#x60;op&#x60; (string), &#x60;negate&#x60; (boolean), and &#x60;values&#x60; (array of strings, numbers, or dates) properties. The &#x60;contextKind&#x60;, if not provided, defaults to &#x60;user&#x60;. The &#x60;contextKind&#x60;, &#x60;attribute&#x60;, and &#x60;values&#x60; are case sensitive. The &#x60;op&#x60; must be lower-case.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;updateClause\&quot;,     \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,     \&quot;clauseId\&quot;: \&quot;10c7462a-2062-45ba-a8bb-dfb3de0f8af5\&quot;,     \&quot;clause\&quot;: {       \&quot;contextKind\&quot;: \&quot;user\&quot;,       \&quot;attribute\&quot;: \&quot;country\&quot;,       \&quot;op\&quot;: \&quot;in\&quot;,       \&quot;negate\&quot;: false,       \&quot;values\&quot;: [\&quot;Mexico\&quot;, \&quot;Canada\&quot;]     }   }] } &#x60;&#x60;&#x60;  #### updateRuleDescription  Updates the description of the segment targeting rule.  ##### Parameters  - &#x60;description&#x60;: The new human-readable description for this rule. - &#x60;ruleId&#x60;: The ID of the rule. You can retrieve this by making a GET request for the segment.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;updateRuleDescription\&quot;,     \&quot;description\&quot;: \&quot;New rule description\&quot;,     \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;   }] } &#x60;&#x60;&#x60;  #### updateRuleRolloutAndContextKind  For a rule that includes a percentage of targets, updates the percentage and the context kind of the targets to include.  ##### Parameters  - &#x60;ruleId&#x60;: The ID of a targeting rule in the segment that includes a percentage of targets. - &#x60;weight&#x60;: The weight, in thousandths of a percent (0-100000). - &#x60;contextKind&#x60;: The context kind.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;reorderRules\&quot;,     \&quot;ruleId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;,     \&quot;weight\&quot;: \&quot;20000\&quot;,     \&quot;contextKind\&quot;: \&quot;device\&quot;   }] } &#x60;&#x60;&#x60;  &lt;/details&gt;  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;working with Big Segments&lt;/strong&gt;&lt;/summary&gt;  A \&quot;big segment\&quot; is a segment that is either a synced segment, or a list-based segment with more than 15,000 entries that includes only one targeted context kind. LaunchDarkly uses different implementations for different types of segments so that all of your segments have good performance.  The following semantic patch instructions apply only to these [larger list-based segments](https://launchdarkly.com/docs/home/flags/segments-create#create-larger-list-based-segments).  #### addBigSegmentExcludedTargets  For use with [larger list-based segments](https://launchdarkly.com/docs/home/flags/segments-create#create-larger-list-based-segments) ONLY. Adds context keys to the context targets excluded from the segment. Returns an error if this causes the same context key to be both included and excluded.  ##### Parameters  - &#x60;values&#x60;: List of context keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addBigSegmentExcludedTargets\&quot;,     \&quot;values\&quot;: [ \&quot;org-key-123abc\&quot;, \&quot;org-key-456def\&quot; ]   }] } &#x60;&#x60;&#x60;  #### addBigSegmentIncludedTargets  For use with [larger list-based segments](https://launchdarkly.com/docs/home/flags/segments-create#create-larger-list-based-segments) ONLY. Adds context keys to the context targets included in the segment. Returns an error if this causes the same context key to be both included and excluded.  ##### Parameters  - &#x60;values&#x60;: List of context keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addBigSegmentIncludedTargets\&quot;,     \&quot;values\&quot;: [ \&quot;org-key-123abc\&quot;, \&quot;org-key-456def\&quot; ]   }] } &#x60;&#x60;&#x60;  #### processBigSegmentImport  For use with [larger list-based segments](https://launchdarkly.com/docs/home/flags/segments-create#create-larger-list-based-segments) ONLY. Processes a segment import.  ##### Parameters  - &#x60;importId&#x60;: The ID of the import. The import ID is returned in the &#x60;Location&#x60; header as part of the [Create big segment import](https://launchdarkly.com/docs/api/segments/create-big-segment-import) request.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;processBigSegmentImport\&quot;,     \&quot;importId\&quot;: \&quot;a902ef4a-2faf-4eaf-88e1-ecc356708a29\&quot;   }] } &#x60;&#x60;&#x60;   #### removeBigSegmentExcludedTargets  For use with [larger list-based segments](https://launchdarkly.com/docs/home/flags/segments-create#create-larger-list-based-segments) ONLY. Removes context keys from the context targets excluded from the segment.  ##### Parameters  - &#x60;values&#x60;: List of context keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;removeBigSegmentExcludedTargets\&quot;,     \&quot;values\&quot;: [ \&quot;org-key-123abc\&quot;, \&quot;org-key-456def\&quot; ]   }] } &#x60;&#x60;&#x60;  #### removeBigSegmentIncludedTargets  For use with [larger list-based segments](https://launchdarkly.com/docs/home/flags/segments-create#create-larger-list-based-segments) ONLY. Removes context keys from the context targets included in the segment.  ##### Parameters  - &#x60;values&#x60;: List of context keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;removeBigSegmentIncludedTargets\&quot;,     \&quot;values\&quot;: [ \&quot;org-key-123abc\&quot;, \&quot;org-key-456def\&quot; ]   }] } &#x60;&#x60;&#x60;  &lt;/details&gt;  ### Using JSON patches on a segment  If you do not include the header described above, you can use a [JSON patch](https://launchdarkly.com/docs/api#updates-using-json-patch) or [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) representation of the desired changes.  For example, to update the description for a segment with a JSON patch, use the following request body:  &#x60;&#x60;&#x60;json {   \&quot;patch\&quot;: [     {       \&quot;op\&quot;: \&quot;replace\&quot;,       \&quot;path\&quot;: \&quot;/description\&quot;,       \&quot;value\&quot;: \&quot;new description\&quot;     }   ] } &#x60;&#x60;&#x60;  To update fields in the segment that are arrays, set the &#x60;path&#x60; to the name of the field and then append &#x60;/&lt;array index&gt;&#x60;. Use &#x60;/0&#x60; to add the new entry to the beginning of the array. Use &#x60;/-&#x60; to add the new entry to the end of the array.  For example, to add a rule to a segment, use the following request body:  &#x60;&#x60;&#x60;json {   \&quot;patch\&quot;:[     {       \&quot;op\&quot;: \&quot;add\&quot;,       \&quot;path\&quot;: \&quot;/rules/0\&quot;,       \&quot;value\&quot;: {         \&quot;clauses\&quot;: [{ \&quot;contextKind\&quot;: \&quot;user\&quot;, \&quot;attribute\&quot;: \&quot;email\&quot;, \&quot;op\&quot;: \&quot;endsWith\&quot;, \&quot;values\&quot;: [\&quot;.edu\&quot;], \&quot;negate\&quot;: false }]       }     }   ] } &#x60;&#x60;&#x60;  To add or remove targets from segments, we recommend using semantic patch. Semantic patch for segments includes specific instructions for adding and removing both included and excluded targets. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    PatchWithComment patchWithComment = new PatchWithComment(); // PatchWithComment | 
    try {
      UserSegment result = apiInstance.patchSegment(projectKey, environmentKey, segmentKey, patchWithComment);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#patchSegment");
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
| **segmentKey** | **String**| The segment key | |
| **patchWithComment** | [**PatchWithComment**](PatchWithComment.md)|  | |

### Return type

[**UserSegment**](UserSegment.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Segment response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a id="postSegment"></a>
# **postSegment**
> UserSegment postSegment(projectKey, environmentKey, segmentBody)

Create segment

Create a new segment.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    SegmentBody segmentBody = new SegmentBody(); // SegmentBody | 
    try {
      UserSegment result = apiInstance.postSegment(projectKey, environmentKey, segmentBody);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#postSegment");
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
| **segmentBody** | [**SegmentBody**](SegmentBody.md)|  | |

### Return type

[**UserSegment**](UserSegment.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Segment response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="updateBigSegmentContextTargets"></a>
# **updateBigSegmentContextTargets**
> updateBigSegmentContextTargets(projectKey, environmentKey, segmentKey, segmentUserState)

Update context targets on a big segment

Update context targets included or excluded in a big segment. Big segments include larger list-based segments and synced segments. This operation does not support standard segments.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    SegmentUserState segmentUserState = new SegmentUserState(); // SegmentUserState | 
    try {
      apiInstance.updateBigSegmentContextTargets(projectKey, environmentKey, segmentKey, segmentUserState);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#updateBigSegmentContextTargets");
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
| **segmentKey** | **String**| The segment key | |
| **segmentUserState** | [**SegmentUserState**](SegmentUserState.md)|  | |

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
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="updateBigSegmentTargets"></a>
# **updateBigSegmentTargets**
> updateBigSegmentTargets(projectKey, environmentKey, segmentKey, segmentUserState)

Update user context targets on a big segment

Update user context targets included or excluded in a big segment. Big segments include larger list-based segments and synced segments. This operation does not support standard segments.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    SegmentUserState segmentUserState = new SegmentUserState(); // SegmentUserState | 
    try {
      apiInstance.updateBigSegmentTargets(projectKey, environmentKey, segmentKey, segmentUserState);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#updateBigSegmentTargets");
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
| **segmentKey** | **String**| The segment key | |
| **segmentUserState** | [**SegmentUserState**](SegmentUserState.md)|  | |

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
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

