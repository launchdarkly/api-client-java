# UsersApi

All URIs are relative to *https://app.launchdarkly.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteUser**](UsersApi.md#deleteUser) | **DELETE** /api/v2/users/{projKey}/{envKey}/{key} | Delete user
[**getSearchUsers**](UsersApi.md#getSearchUsers) | **GET** /api/v2/user-search/{projKey}/{envKey} | Find users
[**getUser**](UsersApi.md#getUser) | **GET** /api/v2/users/{projKey}/{envKey}/{key} | Get user
[**getUsers**](UsersApi.md#getUsers) | **GET** /api/v2/users/{projKey}/{envKey} | List users


<a name="deleteUser"></a>
# **deleteUser**
> deleteUser(projKey, envKey, key)

Delete user

Delete a user by key

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.UsersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    UsersApi apiInstance = new UsersApi(defaultClient);
    String projKey = "projKey_example"; // String | The project key
    String envKey = "envKey_example"; // String | The environment key
    String key = "key_example"; // String | The user key
    try {
      apiInstance.deleteUser(projKey, envKey, key);
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#deleteUser");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projKey** | **String**| The project key |
 **envKey** | **String**| The environment key |
 **key** | **String**| The user key |

### Return type

null (empty response body)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**204** | Action completed successfully |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**409** | Status conflict |  -  |
**429** | Rate limited |  -  |

<a name="getSearchUsers"></a>
# **getSearchUsers**
> Users getSearchUsers(projKey, envKey, q, limit, offset, after, searchAfter)

Find users

Search users in LaunchDarkly based on their last active date, or a search query. Do not use to enumerate all users in LaunchDarkly. Instead use the [List users](getUsers) API resource.  &gt; ### &#x60;offset&#x60; is deprecated &gt; &gt; &#x60;offset&#x60; is deprecated and will be removed in a future API version. You can still use &#x60;offset&#x60; and &#x60;limit&#x60; for pagination, but we recommend you use &#x60;sort&#x60; and &#x60;searchAfter&#x60; instead. &#x60;searchAfter&#x60; allows you to page through more than 10,000 users, but &#x60;offset&#x60; and &#x60;limit&#x60; do not. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.UsersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    UsersApi apiInstance = new UsersApi(defaultClient);
    String projKey = "projKey_example"; // String | The project key
    String envKey = "envKey_example"; // String | The environment key
    String q = "q_example"; // String | Full-text search for users based on name, first name, last name, e-mail address, or key
    Long limit = 56L; // Long | Specifies the maximum number of items in the collection to return (max: 50, default: 20)
    Long offset = 56L; // Long | Specifies the first item to return in the collection
    Long after = 56L; // Long | A unix epoch time in milliseconds specifying the maximum last time a user requested a feature flag from LaunchDarkly
    String searchAfter = "searchAfter_example"; // String | Limits results to users with sort values after the value you specify. You can use this for pagination, but we recommend using the `next` link we provide instead.
    try {
      Users result = apiInstance.getSearchUsers(projKey, envKey, q, limit, offset, after, searchAfter);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#getSearchUsers");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projKey** | **String**| The project key |
 **envKey** | **String**| The environment key |
 **q** | **String**| Full-text search for users based on name, first name, last name, e-mail address, or key | [optional]
 **limit** | **Long**| Specifies the maximum number of items in the collection to return (max: 50, default: 20) | [optional]
 **offset** | **Long**| Specifies the first item to return in the collection | [optional]
 **after** | **Long**| A unix epoch time in milliseconds specifying the maximum last time a user requested a feature flag from LaunchDarkly | [optional]
 **searchAfter** | **String**| Limits results to users with sort values after the value you specify. You can use this for pagination, but we recommend using the &#x60;next&#x60; link we provide instead. | [optional]

### Return type

[**Users**](Users.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Users collection response |  -  |
**400** | Bad request |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

<a name="getUser"></a>
# **getUser**
> User getUser(projKey, envKey, key)

Get user

Get a user by key. The &#x60;user&#x60; object contains all attributes sent in &#x60;variation&#x60; calls for that key.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.UsersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    UsersApi apiInstance = new UsersApi(defaultClient);
    String projKey = "projKey_example"; // String | The project key
    String envKey = "envKey_example"; // String | The environment key
    String key = "key_example"; // String | The user key
    try {
      User result = apiInstance.getUser(projKey, envKey, key);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#getUser");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projKey** | **String**| The project key |
 **envKey** | **String**| The environment key |
 **key** | **String**| The user key |

### Return type

[**User**](User.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | User response |  -  |
**400** | Bad request |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

<a name="getUsers"></a>
# **getUsers**
> Users getUsers(projKey, envKey, limit, searchAfter)

List users

List all users in the environment. Includes the total count of users. In each page, there is up to &#x60;limit&#x60; users returned. The default is 20. This is useful for exporting all users in the system for further analysis. To paginate through, follow the &#x60;next&#x60; link in the &#x60;_links&#x60; object, as [described in Representations](/#section/Representations). 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.UsersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    UsersApi apiInstance = new UsersApi(defaultClient);
    String projKey = "projKey_example"; // String | The project key
    String envKey = "envKey_example"; // String | The environment key
    Long limit = 56L; // Long | The number of elements to return per page
    String searchAfter = "searchAfter_example"; // String | Limits results to users with sort values after the value you specify. You can use this for pagination, but we recommend using the `next` link we provide instead.
    try {
      Users result = apiInstance.getUsers(projKey, envKey, limit, searchAfter);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#getUsers");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projKey** | **String**| The project key |
 **envKey** | **String**| The environment key |
 **limit** | **Long**| The number of elements to return per page | [optional]
 **searchAfter** | **String**| Limits results to users with sort values after the value you specify. You can use this for pagination, but we recommend using the &#x60;next&#x60; link we provide instead. | [optional]

### Return type

[**Users**](Users.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Users collection response |  -  |
**400** | Bad request |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

