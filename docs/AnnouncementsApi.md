# AnnouncementsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createAnnouncementPublic**](AnnouncementsApi.md#createAnnouncementPublic) | **POST** /api/v2/announcements | Create an announcement |
| [**deleteAnnouncementPublic**](AnnouncementsApi.md#deleteAnnouncementPublic) | **DELETE** /api/v2/announcements/{announcementId} | Delete an announcement |
| [**getAnnouncementsPublic**](AnnouncementsApi.md#getAnnouncementsPublic) | **GET** /api/v2/announcements | Get announcements |
| [**updateAnnouncementPublic**](AnnouncementsApi.md#updateAnnouncementPublic) | **PATCH** /api/v2/announcements/{announcementId} | Update an announcement |


<a name="createAnnouncementPublic"></a>
# **createAnnouncementPublic**
> AnnouncementResponse createAnnouncementPublic(createAnnouncementBody)

Create an announcement

Create an announcement

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AnnouncementsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AnnouncementsApi apiInstance = new AnnouncementsApi(defaultClient);
    CreateAnnouncementBody createAnnouncementBody = new CreateAnnouncementBody(); // CreateAnnouncementBody | Announcement request body
    try {
      AnnouncementResponse result = apiInstance.createAnnouncementPublic(createAnnouncementBody);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AnnouncementsApi#createAnnouncementPublic");
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
| **createAnnouncementBody** | [**CreateAnnouncementBody**](CreateAnnouncementBody.md)| Announcement request body | |

### Return type

[**AnnouncementResponse**](AnnouncementResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Created announcement |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **409** | Conflict |  -  |
| **500** | Internal server error |  -  |

<a name="deleteAnnouncementPublic"></a>
# **deleteAnnouncementPublic**
> deleteAnnouncementPublic(announcementId)

Delete an announcement

Delete an announcement

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AnnouncementsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AnnouncementsApi apiInstance = new AnnouncementsApi(defaultClient);
    String announcementId = "1234567890"; // String | 
    try {
      apiInstance.deleteAnnouncementPublic(announcementId);
    } catch (ApiException e) {
      System.err.println("Exception when calling AnnouncementsApi#deleteAnnouncementPublic");
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
| **announcementId** | **String**|  | |

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
| **204** | No content |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a name="getAnnouncementsPublic"></a>
# **getAnnouncementsPublic**
> GetAnnouncementsPublic200Response getAnnouncementsPublic(status, limit, offset)

Get announcements

Get announcements

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AnnouncementsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AnnouncementsApi apiInstance = new AnnouncementsApi(defaultClient);
    String status = "active"; // String | Filter announcements by status.
    Integer limit = 56; // Integer | The number of announcements to return.
    Integer offset = 56; // Integer | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    try {
      GetAnnouncementsPublic200Response result = apiInstance.getAnnouncementsPublic(status, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AnnouncementsApi#getAnnouncementsPublic");
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
| **status** | **String**| Filter announcements by status. | [optional] [enum: active, inactive, scheduled] |
| **limit** | **Integer**| The number of announcements to return. | [optional] |
| **offset** | **Integer**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |

### Return type

[**GetAnnouncementsPublic200Response**](GetAnnouncementsPublic200Response.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Announcement response |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **429** | Rate limit exceeded |  -  |
| **500** | Internal server error |  -  |

<a name="updateAnnouncementPublic"></a>
# **updateAnnouncementPublic**
> AnnouncementResponse updateAnnouncementPublic(announcementId, announcementPatchOperation)

Update an announcement

Update an announcement

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AnnouncementsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AnnouncementsApi apiInstance = new AnnouncementsApi(defaultClient);
    String announcementId = "1234567890"; // String | 
    List<AnnouncementPatchOperation> announcementPatchOperation = Arrays.asList(); // List<AnnouncementPatchOperation> | Update announcement request body
    try {
      AnnouncementResponse result = apiInstance.updateAnnouncementPublic(announcementId, announcementPatchOperation);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AnnouncementsApi#updateAnnouncementPublic");
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
| **announcementId** | **String**|  | |
| **announcementPatchOperation** | [**List&lt;AnnouncementPatchOperation&gt;**](AnnouncementPatchOperation.md)| Update announcement request body | |

### Return type

[**AnnouncementResponse**](AnnouncementResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Updated announcement |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **409** | Conflict |  -  |
| **500** | Internal server error |  -  |

