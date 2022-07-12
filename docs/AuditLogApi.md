# AuditLogApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getAuditLogEntries**](AuditLogApi.md#getAuditLogEntries) | **GET** /api/v2/auditlog | List audit log feature flag entries |
| [**getAuditLogEntry**](AuditLogApi.md#getAuditLogEntry) | **GET** /api/v2/auditlog/{id} | Get audit log entry |


<a name="getAuditLogEntries"></a>
# **getAuditLogEntries**
> AuditLogEntryListingRepCollection getAuditLogEntries(before, after, q, limit, spec)

List audit log feature flag entries

Get a list of all audit log entries. The query parameters let you restrict the results that return by date ranges, resource specifiers, or a full-text search query.

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
    String q = "q_example"; // String | Text to search for. You can search for the full or partial name of the resource, or full or partial email address of the member who made a change.
    Long limit = 56L; // Long | A limit on the number of audit log entries that return. Set between 1 and 20.
    String spec = "spec_example"; // String | A resource specifier that lets you filter audit log listings by resource
    try {
      AuditLogEntryListingRepCollection result = apiInstance.getAuditLogEntries(before, after, q, limit, spec);
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
| **q** | **String**| Text to search for. You can search for the full or partial name of the resource, or full or partial email address of the member who made a change. | [optional] |
| **limit** | **Long**| A limit on the number of audit log entries that return. Set between 1 and 20. | [optional] |
| **spec** | **String**| A resource specifier that lets you filter audit log listings by resource | [optional] |

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

<a name="getAuditLogEntry"></a>
# **getAuditLogEntry**
> AuditLogEntryRep getAuditLogEntry(id)

Get audit log entry

Fetch a detailed audit log entry representation. The detailed representation includes several fields that are not present in the summary representation, including:  - &#x60;delta&#x60;: the JSON patch body that was used in the request to update the entity - &#x60;previousVersion&#x60;: a JSON representation of the previous version of the entity - &#x60;currentVersion&#x60;: a JSON representation of the current version of the entity 

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

