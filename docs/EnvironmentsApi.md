# EnvironmentsApi

All URIs are relative to *https://app.launchdarkly.com/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteEnvironment**](EnvironmentsApi.md#deleteEnvironment) | **DELETE** /environments/{projectKey}/{environmentKey} | Delete an environment by ID
[**getEnvironment**](EnvironmentsApi.md#getEnvironment) | **GET** /environments/{projectKey}/{environmentKey} | Get an environment given a project and key.
[**patchEnvironment**](EnvironmentsApi.md#patchEnvironment) | **PATCH** /environments/{projectKey}/{environmentKey} | Modify an environment by ID
[**postEnvironment**](EnvironmentsApi.md#postEnvironment) | **POST** /environments/{projectKey} | Create a new environment in a specified project with a given name, key, and swatch color.


<a name="deleteEnvironment"></a>
# **deleteEnvironment**
> deleteEnvironment(projectKey, environmentKey)

Delete an environment by ID

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.EnvironmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

EnvironmentsApi apiInstance = new EnvironmentsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key
try {
    apiInstance.deleteEnvironment(projectKey, environmentKey);
} catch (ApiException e) {
    System.err.println("Exception when calling EnvironmentsApi#deleteEnvironment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key |

### Return type

null (empty response body)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getEnvironment"></a>
# **getEnvironment**
> Environment getEnvironment(projectKey, environmentKey)

Get an environment given a project and key.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.EnvironmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

EnvironmentsApi apiInstance = new EnvironmentsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key
try {
    Environment result = apiInstance.getEnvironment(projectKey, environmentKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EnvironmentsApi#getEnvironment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key |

### Return type

[**Environment**](Environment.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="patchEnvironment"></a>
# **patchEnvironment**
> patchEnvironment(projectKey, environmentKey, patchDelta)

Modify an environment by ID

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.EnvironmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

EnvironmentsApi apiInstance = new EnvironmentsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key
List<PatchDelta> patchDelta = Arrays.asList(new PatchDelta()); // List<PatchDelta> | http://jsonpatch.com/
try {
    apiInstance.patchEnvironment(projectKey, environmentKey, patchDelta);
} catch (ApiException e) {
    System.err.println("Exception when calling EnvironmentsApi#patchEnvironment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key |
 **patchDelta** | [**List&lt;PatchDelta&gt;**](PatchDelta.md)| http://jsonpatch.com/ |

### Return type

null (empty response body)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="postEnvironment"></a>
# **postEnvironment**
> postEnvironment(projectKey, environmentBody)

Create a new environment in a specified project with a given name, key, and swatch color.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.EnvironmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

EnvironmentsApi apiInstance = new EnvironmentsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
EnvironmentBody environmentBody = new EnvironmentBody(); // EnvironmentBody | New environment
try {
    apiInstance.postEnvironment(projectKey, environmentBody);
} catch (ApiException e) {
    System.err.println("Exception when calling EnvironmentsApi#postEnvironment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentBody** | [**EnvironmentBody**](EnvironmentBody.md)| New environment |

### Return type

null (empty response body)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

