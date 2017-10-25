# AuditLogApi

All URIs are relative to *https://app.launchdarkly.com/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAuditLogEntries**](AuditLogApi.md#getAuditLogEntries) | **GET** /auditlog | Fetch a list of all audit log entries
[**getAuditLogEntry**](AuditLogApi.md#getAuditLogEntry) | **GET** /auditlog/{resourceId} | Get an audit log entry by ID


<a name="getAuditLogEntries"></a>
# **getAuditLogEntries**
> AuditLogEntries getAuditLogEntries()

Fetch a list of all audit log entries

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AuditLogApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

AuditLogApi apiInstance = new AuditLogApi();
try {
    AuditLogEntries result = apiInstance.getAuditLogEntries();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AuditLogApi#getAuditLogEntries");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

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

Get an audit log entry by ID

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AuditLogApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

AuditLogApi apiInstance = new AuditLogApi();
String resourceId = "resourceId_example"; // String | The resource ID
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
 **resourceId** | **String**| The resource ID |

### Return type

[**AuditLogEntry**](AuditLogEntry.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

