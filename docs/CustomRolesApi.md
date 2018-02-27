# CustomRolesApi

All URIs are relative to *https://app.launchdarkly.com/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteCustomRole**](CustomRolesApi.md#deleteCustomRole) | **DELETE** /roles/{customRoleKey} | Delete a custom role by key.
[**getCustomRole**](CustomRolesApi.md#getCustomRole) | **GET** /roles/{customRoleKey} | Get one custom role by key.
[**getCustomRoles**](CustomRolesApi.md#getCustomRoles) | **GET** /roles | Return a complete list of custom roles.
[**patchCustomRole**](CustomRolesApi.md#patchCustomRole) | **PATCH** /roles/{customRoleKey} | Modify a custom role by key.
[**postCustomRole**](CustomRolesApi.md#postCustomRole) | **POST** /roles | Create a new custom role.


<a name="deleteCustomRole"></a>
# **deleteCustomRole**
> deleteCustomRole(customRoleKey)

Delete a custom role by key.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.CustomRolesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

CustomRolesApi apiInstance = new CustomRolesApi();
String customRoleKey = "customRoleKey_example"; // String | The custom role key.
try {
    apiInstance.deleteCustomRole(customRoleKey);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomRolesApi#deleteCustomRole");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **customRoleKey** | **String**| The custom role key. |

### Return type

null (empty response body)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getCustomRole"></a>
# **getCustomRole**
> CustomRole getCustomRole(customRoleKey)

Get one custom role by key.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.CustomRolesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

CustomRolesApi apiInstance = new CustomRolesApi();
String customRoleKey = "customRoleKey_example"; // String | The custom role key.
try {
    CustomRole result = apiInstance.getCustomRole(customRoleKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomRolesApi#getCustomRole");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **customRoleKey** | **String**| The custom role key. |

### Return type

[**CustomRole**](CustomRole.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getCustomRoles"></a>
# **getCustomRoles**
> CustomRoles getCustomRoles()

Return a complete list of custom roles.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.CustomRolesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

CustomRolesApi apiInstance = new CustomRolesApi();
try {
    CustomRoles result = apiInstance.getCustomRoles();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomRolesApi#getCustomRoles");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**CustomRoles**](CustomRoles.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="patchCustomRole"></a>
# **patchCustomRole**
> CustomRole patchCustomRole(customRoleKey, patchDelta)

Modify a custom role by key.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.CustomRolesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

CustomRolesApi apiInstance = new CustomRolesApi();
String customRoleKey = "customRoleKey_example"; // String | The custom role key.
List<PatchDelta> patchDelta = Arrays.asList(new PatchDelta()); // List<PatchDelta> | Requires a JSON Patch representation of the desired changes to the project. 'http://jsonpatch.com/'
try {
    CustomRole result = apiInstance.patchCustomRole(customRoleKey, patchDelta);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomRolesApi#patchCustomRole");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **customRoleKey** | **String**| The custom role key. |
 **patchDelta** | [**List&lt;PatchDelta&gt;**](PatchDelta.md)| Requires a JSON Patch representation of the desired changes to the project. &#39;http://jsonpatch.com/&#39; |

### Return type

[**CustomRole**](CustomRole.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="postCustomRole"></a>
# **postCustomRole**
> postCustomRole(customRoleBody)

Create a new custom role.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.CustomRolesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

CustomRolesApi apiInstance = new CustomRolesApi();
CustomRoleBody customRoleBody = new CustomRoleBody(); // CustomRoleBody | New role or roles to create.
try {
    apiInstance.postCustomRole(customRoleBody);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomRolesApi#postCustomRole");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **customRoleBody** | [**CustomRoleBody**](CustomRoleBody.md)| New role or roles to create. |

### Return type

null (empty response body)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

