# AuditLogApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getAuditLogEntries**](AuditLogApi.md#getAuditLogEntries) | **GET** /api/v2/auditlog | List audit log entries |
| [**getAuditLogEntry**](AuditLogApi.md#getAuditLogEntry) | **GET** /api/v2/auditlog/{id} | Get audit log entry |
| [**postAuditLogEntries**](AuditLogApi.md#postAuditLogEntries) | **POST** /api/v2/auditlog | Search audit log entries |
| [**postAuditLogEntryCounts**](AuditLogApi.md#postAuditLogEntryCounts) | **POST** /api/v2/auditlog/counts | Get audit log entry counts |


<a id="getAuditLogEntries"></a>
# **getAuditLogEntries**
> AuditLogEntryListingRepCollection getAuditLogEntries(before, after, q, limit, spec, member, token)

List audit log entries

Get a list of all audit log entries. The query parameters let you restrict the results that return by date ranges, resource specifiers, or a full-text search query.  LaunchDarkly uses a resource specifier syntax to name resources or collections of resources. To learn more, read [Specifying resources in the advanced editor](https://launchdarkly.com/docs/home/account/roles/role-resources#specifying-resources-in-the-advanced-editor). 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AuditLogApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AuditLogApi apiInstance = new AuditLogApi(defaultClient);
    Long before = 56L; // Long | A timestamp filter, expressed as a Unix epoch time in milliseconds.  All entries this returns occurred before the timestamp.
    Long after = 56L; // Long | A timestamp filter, expressed as a Unix epoch time in milliseconds. All entries this returns occurred after the timestamp.
    String q = "q_example"; // String | Text to search for. You can search for the full or partial name of the resource.
    Long limit = 56L; // Long | A limit on the number of audit log entries that return. Set between 1 and 20. The default is 10.
    String spec = "spec_example"; // String | A resource specifier that lets you filter audit log listings by resource
    String member = "member_example"; // String | A member ID that lets you filter audit log listings by the member who made the change. Repeat the parameter to filter by multiple members.
    String token = "token_example"; // String | An access token ID that lets you filter audit log listings by the token that made the change. Repeat the parameter to filter by multiple tokens.
    try {
      AuditLogEntryListingRepCollection result = apiInstance.getAuditLogEntries(before, after, q, limit, spec, member, token);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AuditLogApi#getAuditLogEntries");
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
| **before** | **Long**| A timestamp filter, expressed as a Unix epoch time in milliseconds.  All entries this returns occurred before the timestamp. | [optional] |
| **after** | **Long**| A timestamp filter, expressed as a Unix epoch time in milliseconds. All entries this returns occurred after the timestamp. | [optional] |
| **q** | **String**| Text to search for. You can search for the full or partial name of the resource. | [optional] |
| **limit** | **Long**| A limit on the number of audit log entries that return. Set between 1 and 20. The default is 10. | [optional] |
| **spec** | **String**| A resource specifier that lets you filter audit log listings by resource | [optional] |
| **member** | **String**| A member ID that lets you filter audit log listings by the member who made the change. Repeat the parameter to filter by multiple members. | [optional] |
| **token** | **String**| An access token ID that lets you filter audit log listings by the token that made the change. Repeat the parameter to filter by multiple tokens. | [optional] |

### Return type

[**AuditLogEntryListingRepCollection**](AuditLogEntryListingRepCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Audit log entries response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |

<a id="getAuditLogEntry"></a>
# **getAuditLogEntry**
> AuditLogEntryRep getAuditLogEntry(id)

Get audit log entry

Fetch a detailed audit log entry representation. The detailed representation includes several fields that are not present in the summary representation, including:  - &#x60;previousVersion&#x60;: a JSON representation of the previous version of the entity. - &#x60;currentVersion&#x60;: a JSON representation of the current version of the entity. - &#x60;delta&#x60;: the JSON patch body that was used in the request to update the entity. This is only included if the update was made through a [JSON patch](https://launchdarkly.com/docs/api#updates-using-json-patch). It is null when the update was made using [semantic patch](https://launchdarkly.com/docs/api#updates-using-semantic-patch). Because most [flag updates](https://launchdarkly.com/docs/api/feature-flags/patch-feature-flag) are made using semantic patch, this field is rarely returned. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AuditLogApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AuditLogApi apiInstance = new AuditLogApi(defaultClient);
    String id = "id_example"; // String | The ID of the audit log entry
    try {
      AuditLogEntryRep result = apiInstance.getAuditLogEntry(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AuditLogApi#getAuditLogEntry");
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
| **id** | **String**| The ID of the audit log entry | |

### Return type

[**AuditLogEntryRep**](AuditLogEntryRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Audit log entry response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="postAuditLogEntries"></a>
# **postAuditLogEntries**
> AuditLogEntryListingRepCollection postAuditLogEntries(before, after, q, limit, member, token, statementPost)

Search audit log entries

Search your audit log entries. The query parameters let you restrict the results that return by date ranges, or a full-text search query. The request body lets you restrict the results that return by resource specifiers.  LaunchDarkly uses a resource specifier syntax to name resources or collections of resources. To learn more, read [Specifying resources in the advanced editor](https://launchdarkly.com/docs/home/account/roles/role-resources#specifying-resources-in-the-advanced-editor). 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AuditLogApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AuditLogApi apiInstance = new AuditLogApi(defaultClient);
    Long before = 56L; // Long | A timestamp filter, expressed as a Unix epoch time in milliseconds.  All entries returned occurred before the timestamp.
    Long after = 56L; // Long | A timestamp filter, expressed as a Unix epoch time in milliseconds. All entries returned occurred after the timestamp.
    String q = "q_example"; // String | Text to search for. You can search for the full or partial name of the resource.
    Long limit = 56L; // Long | A limit on the number of audit log entries that return. Set between 1 and 20. The default is 10.
    String member = "member_example"; // String | A member ID that lets you filter audit log listings by the member who made the change. Repeat the parameter to filter by multiple members.
    String token = "token_example"; // String | An access token ID that lets you filter audit log listings by the token that made the change. Repeat the parameter to filter by multiple tokens.
    List<StatementPost> statementPost = Arrays.asList(); // List<StatementPost> | 
    try {
      AuditLogEntryListingRepCollection result = apiInstance.postAuditLogEntries(before, after, q, limit, member, token, statementPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AuditLogApi#postAuditLogEntries");
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
| **before** | **Long**| A timestamp filter, expressed as a Unix epoch time in milliseconds.  All entries returned occurred before the timestamp. | [optional] |
| **after** | **Long**| A timestamp filter, expressed as a Unix epoch time in milliseconds. All entries returned occurred after the timestamp. | [optional] |
| **q** | **String**| Text to search for. You can search for the full or partial name of the resource. | [optional] |
| **limit** | **Long**| A limit on the number of audit log entries that return. Set between 1 and 20. The default is 10. | [optional] |
| **member** | **String**| A member ID that lets you filter audit log listings by the member who made the change. Repeat the parameter to filter by multiple members. | [optional] |
| **token** | **String**| An access token ID that lets you filter audit log listings by the token that made the change. Repeat the parameter to filter by multiple tokens. | [optional] |
| **statementPost** | [**List&lt;StatementPost&gt;**](StatementPost.md)|  | [optional] |

### Return type

[**AuditLogEntryListingRepCollection**](AuditLogEntryListingRepCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Audit log entries response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |

<a id="postAuditLogEntryCounts"></a>
# **postAuditLogEntryCounts**
> CountBucketsResult postAuditLogEntryCounts(after, statementPost, before, buckets, member, token)

Get audit log entry counts

Returns aggregate counts of audit log entries per time bucket. Used for dashboard overlays that show flag targeting changes.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AuditLogApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AuditLogApi apiInstance = new AuditLogApi(defaultClient);
    Long after = 56L; // Long | A timestamp filter, expressed as a Unix epoch time in milliseconds. Required.
    List<StatementPost> statementPost = Arrays.asList(); // List<StatementPost> | 
    Long before = 56L; // Long | A timestamp filter, expressed as a Unix epoch time in milliseconds. Defaults to now.
    Long buckets = 56L; // Long | Number of time buckets to divide the range into. Default 50, max 500.
    String member = "member_example"; // String | A member ID that lets you filter counts to changes made by that member. Repeat the parameter to filter by multiple members.
    String token = "token_example"; // String | An access token ID that lets you filter counts to changes made by that token. Repeat the parameter to filter by multiple tokens.
    try {
      CountBucketsResult result = apiInstance.postAuditLogEntryCounts(after, statementPost, before, buckets, member, token);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AuditLogApi#postAuditLogEntryCounts");
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
| **after** | **Long**| A timestamp filter, expressed as a Unix epoch time in milliseconds. Required. | |
| **statementPost** | [**List&lt;StatementPost&gt;**](StatementPost.md)|  | |
| **before** | **Long**| A timestamp filter, expressed as a Unix epoch time in milliseconds. Defaults to now. | [optional] |
| **buckets** | **Long**| Number of time buckets to divide the range into. Default 50, max 500. | [optional] |
| **member** | **String**| A member ID that lets you filter counts to changes made by that member. Repeat the parameter to filter by multiple members. | [optional] |
| **token** | **String**| An access token ID that lets you filter counts to changes made by that token. Repeat the parameter to filter by multiple tokens. | [optional] |

### Return type

[**CountBucketsResult**](CountBucketsResult.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Audit log entry counts response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |

