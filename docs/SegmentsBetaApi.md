# SegmentsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createBigSegmentExport**](SegmentsBetaApi.md#createBigSegmentExport) | **POST** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/exports | Create Big Segment export |
| [**createBigSegmentImport**](SegmentsBetaApi.md#createBigSegmentImport) | **POST** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/imports | Create Big Segment import |
| [**getBigSegmentExport**](SegmentsBetaApi.md#getBigSegmentExport) | **GET** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/exports/{exportID} | Get Big Segment export |
| [**getBigSegmentImport**](SegmentsBetaApi.md#getBigSegmentImport) | **GET** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/imports/{importID} | Get Big Segment import |
| [**getContextInstanceSegmentsMembershipByEnv**](SegmentsBetaApi.md#getContextInstanceSegmentsMembershipByEnv) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/segments/evaluate | List segment memberships for context instance |


<a name="createBigSegmentExport"></a>
# **createBigSegmentExport**
> createBigSegmentExport(projectKey, environmentKey, segmentKey)

Create Big Segment export

Starts a new export process for a Big Segment

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsBetaApi apiInstance = new SegmentsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    try {
      apiInstance.createBigSegmentExport(projectKey, environmentKey, segmentKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsBetaApi#createBigSegmentExport");
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

<a name="createBigSegmentImport"></a>
# **createBigSegmentImport**
> createBigSegmentImport(projectKey, environmentKey, segmentKey, _file, mode)

Create Big Segment import

Start a new import process for a Big Segment.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsBetaApi apiInstance = new SegmentsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    File _file = new File("/path/to/file"); // File | CSV file containing keys
    String mode = "mode_example"; // String | Import mode. Use either `merge` or `replace`
    try {
      apiInstance.createBigSegmentImport(projectKey, environmentKey, segmentKey, _file, mode);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsBetaApi#createBigSegmentImport");
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

<a name="getBigSegmentExport"></a>
# **getBigSegmentExport**
> Export getBigSegmentExport(projectKey, environmentKey, segmentKey, exportID)

Get Big Segment export

Returns info about a Big Segment export process.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsBetaApi apiInstance = new SegmentsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    String exportID = "exportID_example"; // String | The export ID
    try {
      Export result = apiInstance.getBigSegmentExport(projectKey, environmentKey, segmentKey, exportID);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsBetaApi#getBigSegmentExport");
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

<a name="getBigSegmentImport"></a>
# **getBigSegmentImport**
> ModelImport getBigSegmentImport(projectKey, environmentKey, segmentKey, importID)

Get Big Segment import

Returns info about a Big Segment import process.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsBetaApi apiInstance = new SegmentsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    String importID = "importID_example"; // String | The import ID
    try {
      ModelImport result = apiInstance.getBigSegmentImport(projectKey, environmentKey, segmentKey, importID);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsBetaApi#getBigSegmentImport");
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

<a name="getContextInstanceSegmentsMembershipByEnv"></a>
# **getContextInstanceSegmentsMembershipByEnv**
> ContextInstanceSegmentMemberships getContextInstanceSegmentsMembershipByEnv(projectKey, environmentKey, requestBody)

List segment memberships for context instance

For a given context instance with attributes, get membership details for all segments

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsBetaApi apiInstance = new SegmentsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    Map<String, Object> requestBody = {"key":"context-key-123abc","kind":"user","moreComplex":{"morethanone":[1,2,3],"yes":"please"},"name":"Some User","something":true}; // Map<String, Object> | 
    try {
      ContextInstanceSegmentMemberships result = apiInstance.getContextInstanceSegmentsMembershipByEnv(projectKey, environmentKey, requestBody);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsBetaApi#getContextInstanceSegmentsMembershipByEnv");
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

