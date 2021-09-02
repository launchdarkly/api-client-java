# CustomRolesApi

All URIs are relative to *https://app.launchdarkly.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteCustomRole**](CustomRolesApi.md#deleteCustomRole) | **DELETE** /api/v2/roles/{key} | Delete custom role
[**getCustomRole**](CustomRolesApi.md#getCustomRole) | **GET** /api/v2/roles/{key} | Get custom role
[**getCustomRoles**](CustomRolesApi.md#getCustomRoles) | **GET** /api/v2/roles | List custom roles
[**patchCustomRole**](CustomRolesApi.md#patchCustomRole) | **PATCH** /api/v2/roles/{key} | Update custom role
[**postCustomRole**](CustomRolesApi.md#postCustomRole) | **POST** /api/v2/roles | Create custom role


<a name="deleteCustomRole"></a>
# **deleteCustomRole**
> deleteCustomRole(key)

Delete custom role

Delete a custom role by key

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.CustomRolesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    CustomRolesApi apiInstance = new CustomRolesApi(defaultClient);
    String key = "key_example"; // String | The key of the custom role to delete
    try {
      apiInstance.deleteCustomRole(key);
    } catch (ApiException e) {
      System.err.println("Exception when calling CustomRolesApi#deleteCustomRole");
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
 **key** | **String**| The key of the custom role to delete |

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
**404** | Invalid resource specifier |  -  |
**429** | Rate limited |  -  |

<a name="getCustomRole"></a>
# **getCustomRole**
> CustomRolePost getCustomRole(key)

Get custom role

Get a single custom role by key or ID

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.CustomRolesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    CustomRolesApi apiInstance = new CustomRolesApi(defaultClient);
    String key = "key_example"; // String | The custom role's key or ID
    try {
      CustomRolePost result = apiInstance.getCustomRole(key);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CustomRolesApi#getCustomRole");
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
 **key** | **String**| The custom role&#39;s key or ID |

### Return type

[**CustomRolePost**](CustomRolePost.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Custom role response |  -  |
**401** | Invalid access token |  -  |
**403** | Access to the requested resource was denied |  -  |
**404** | Invalid resource specifier |  -  |
**429** | Rate limited |  -  |

<a name="getCustomRoles"></a>
# **getCustomRoles**
> CustomRoles getCustomRoles()

List custom roles

Get a complete list of custom roles. Custom roles let you create flexible policies providing fine-grained access control to everything in LaunchDarkly, from feature flags to goals, environments, and teams. With custom roles, it&#39;s possible to enforce access policies that meet your exact workflow needs. Custom roles are available to customers on our enterprise plans. If you&#39;re interested in learning more about our enterprise plans, contact sales@launchdarkly.com.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.CustomRolesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    CustomRolesApi apiInstance = new CustomRolesApi(defaultClient);
    try {
      CustomRoles result = apiInstance.getCustomRoles();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CustomRolesApi#getCustomRoles");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**CustomRoles**](CustomRoles.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Custom roles collection response. |  -  |
**401** | Invalid access token. |  -  |
**403** | Access to the requested resource was denied |  -  |
**429** | Rate limited |  -  |

<a name="patchCustomRole"></a>
# **patchCustomRole**
> CustomRole patchCustomRole(key, patchWithComment)

Update custom role

Update a single custom role. The request must be a valid JSON Patch document describing the changes to be made to the custom role.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.CustomRolesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    CustomRolesApi apiInstance = new CustomRolesApi(defaultClient);
    String key = "key_example"; // String | The key of the custom role to update
    PatchWithComment patchWithComment = new PatchWithComment(); // PatchWithComment | 
    try {
      CustomRole result = apiInstance.patchCustomRole(key, patchWithComment);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CustomRolesApi#patchCustomRole");
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
 **key** | **String**| The key of the custom role to update |
 **patchWithComment** | [**PatchWithComment**](PatchWithComment.md)|  |

### Return type

[**CustomRole**](CustomRole.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Custom role response |  -  |
**400** | Invalid request body |  -  |
**401** | Invalid access token |  -  |
**404** | Invalid resource specifier |  -  |
**409** | Status conflict |  -  |
**429** | Rate limited |  -  |

<a name="postCustomRole"></a>
# **postCustomRole**
> CustomRole postCustomRole(statementPost)

Create custom role

Create a new custom role

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.CustomRolesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    CustomRolesApi apiInstance = new CustomRolesApi(defaultClient);
    List<StatementPost> statementPost = Arrays.asList(); // List<StatementPost> | 
    try {
      CustomRole result = apiInstance.postCustomRole(statementPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CustomRolesApi#postCustomRole");
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
 **statementPost** | [**List&lt;StatementPost&gt;**](StatementPost.md)|  |

### Return type

[**CustomRole**](CustomRole.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | Custom role response |  -  |
**400** | Invalid request body |  -  |
**401** | Invalid access token |  -  |
**403** | Access to the requested resource was denied |  -  |
**409** | Status conflict |  -  |
**429** | Rate limited |  -  |

