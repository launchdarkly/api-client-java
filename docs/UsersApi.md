# UsersApi

All URIs are relative to *https://app.launchdarkly.com/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteUser**](UsersApi.md#deleteUser) | **DELETE** /users/{projectKey}/{environmentKey}/{userKey} | Delete a user by ID.
[**getSearchUsers**](UsersApi.md#getSearchUsers) | **GET** /user-search/{projectKey}/{environmentKey} | Search users in LaunchDarkly based on their last active date, or a search query. It should not be used to enumerate all users in LaunchDarkly-- use the List users API resource.
[**getUser**](UsersApi.md#getUser) | **GET** /users/{projectKey}/{environmentKey}/{userKey} | Get a user by key.
[**getUsers**](UsersApi.md#getUsers) | **GET** /users/{projectKey}/{environmentKey} | List all users in the environment. Includes the total count of users. In each page, there will be up to &#39;limit&#39; users returned (default 20). This is useful for exporting all users in the system for further analysis. Paginated collections will include a next link containing a URL with the next set of elements in the collection.


<a name="deleteUser"></a>
# **deleteUser**
> deleteUser(projectKey, environmentKey, userKey)

Delete a user by ID.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.UsersApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

UsersApi apiInstance = new UsersApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
String userKey = "userKey_example"; // String | The user's key.
try {
    apiInstance.deleteUser(projectKey, environmentKey, userKey);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersApi#deleteUser");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **userKey** | **String**| The user&#39;s key. |

### Return type

null (empty response body)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSearchUsers"></a>
# **getSearchUsers**
> Users getSearchUsers(projectKey, environmentKey, q, limit, offset, after)

Search users in LaunchDarkly based on their last active date, or a search query. It should not be used to enumerate all users in LaunchDarkly-- use the List users API resource.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.UsersApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

UsersApi apiInstance = new UsersApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
String q = "q_example"; // String | Search query.
Integer limit = 56; // Integer | Pagination limit.
Integer offset = 56; // Integer | Specifies the first item to return in the collection.
Long after = 789L; // Long | A timestamp filter, expressed as a Unix epoch time in milliseconds. All entries returned will have occurred after this timestamp.
try {
    Users result = apiInstance.getSearchUsers(projectKey, environmentKey, q, limit, offset, after);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersApi#getSearchUsers");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **q** | **String**| Search query. | [optional]
 **limit** | **Integer**| Pagination limit. | [optional]
 **offset** | **Integer**| Specifies the first item to return in the collection. | [optional]
 **after** | **Long**| A timestamp filter, expressed as a Unix epoch time in milliseconds. All entries returned will have occurred after this timestamp. | [optional]

### Return type

[**Users**](Users.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUser"></a>
# **getUser**
> UserRecord getUser(projectKey, environmentKey, userKey)

Get a user by key.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.UsersApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

UsersApi apiInstance = new UsersApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
String userKey = "userKey_example"; // String | The user's key.
try {
    UserRecord result = apiInstance.getUser(projectKey, environmentKey, userKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersApi#getUser");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **userKey** | **String**| The user&#39;s key. |

### Return type

[**UserRecord**](UserRecord.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUsers"></a>
# **getUsers**
> Users getUsers(projectKey, environmentKey, limit, h, scrollId)

List all users in the environment. Includes the total count of users. In each page, there will be up to &#39;limit&#39; users returned (default 20). This is useful for exporting all users in the system for further analysis. Paginated collections will include a next link containing a URL with the next set of elements in the collection.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.UsersApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

UsersApi apiInstance = new UsersApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
Integer limit = 56; // Integer | Pagination limit.
String h = "h_example"; // String | This parameter is required when following \"next\" links.
String scrollId = "scrollId_example"; // String | This parameter is required when following \"next\" links.
try {
    Users result = apiInstance.getUsers(projectKey, environmentKey, limit, h, scrollId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersApi#getUsers");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **limit** | **Integer**| Pagination limit. | [optional]
 **h** | **String**| This parameter is required when following \&quot;next\&quot; links. | [optional]
 **scrollId** | **String**| This parameter is required when following \&quot;next\&quot; links. | [optional]

### Return type

[**Users**](Users.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

