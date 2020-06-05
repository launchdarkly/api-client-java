# AuditLogApi

All URIs are relative to *https://app.launchdarkly.com/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAuditLogEntries**](AuditLogApi.md#getAuditLogEntries) | **GET** /auditlog | Get a list of all audit log entries. The query parameters allow you to restrict the returned results by date ranges, resource specifiers, or a full-text search query.
[**getAuditLogEntry**](AuditLogApi.md#getAuditLogEntry) | **GET** /auditlog/{resourceId} | Use this endpoint to fetch a single audit log entry by its resouce ID.


<a name="getAuditLogEntries"></a>
# **getAuditLogEntries**
> AuditLogEntries getAuditLogEntries(before, after, q, limit, spec)

Get a list of all audit log entries. The query parameters allow you to restrict the returned results by date ranges, resource specifiers, or a full-text search query.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.AuditLogApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

AuditLogApi apiInstance = new AuditLogApi();
Long before = 789L; // Long | A timestamp filter, expressed as a Unix epoch time in milliseconds. All entries returned will have before this timestamp.
Long after = 789L; // Long | A timestamp filter, expressed as a Unix epoch time in milliseconds. All entries returned will have occurred after this timestamp.
String q = "q_example"; // String | Text to search for. You can search for the full or partial name of the resource involved or full or partial email address of the member who made the change.
BigDecimal limit = new BigDecimal(); // BigDecimal | A limit on the number of audit log entries to be returned, between 1 and 20.
String spec = "spec_example"; // String | A resource specifier, allowing you to filter audit log listings by resource.
try {
    AuditLogEntries result = apiInstance.getAuditLogEntries(before, after, q, limit, spec);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AuditLogApi#getAuditLogEntries");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **before** | **Long**| A timestamp filter, expressed as a Unix epoch time in milliseconds. All entries returned will have before this timestamp. | [optional]
 **after** | **Long**| A timestamp filter, expressed as a Unix epoch time in milliseconds. All entries returned will have occurred after this timestamp. | [optional]
 **q** | **String**| Text to search for. You can search for the full or partial name of the resource involved or full or partial email address of the member who made the change. | [optional]
 **limit** | **BigDecimal**| A limit on the number of audit log entries to be returned, between 1 and 20. | [optional]
 **spec** | **String**| A resource specifier, allowing you to filter audit log listings by resource. | [optional]

### Return type

[**AuditLogEntries**](AuditLogEntries.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAuditLogEntry"></a>
# **getAuditLogEntry**
> AuditLogEntry getAuditLogEntry(resourceId)

Use this endpoint to fetch a single audit log entry by its resouce ID.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.AuditLogApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

AuditLogApi apiInstance = new AuditLogApi();
String resourceId = "resourceId_example"; // String | The resource ID.
try {
    AuditLogEntry result = apiInstance.getAuditLogEntry(resourceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AuditLogApi#getAuditLogEntry");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **resourceId** | **String**| The resource ID. |

### Return type

[**AuditLogEntry**](AuditLogEntry.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

