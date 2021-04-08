# UserSegmentsApi

All URIs are relative to *https://app.launchdarkly.com/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteUserSegment**](UserSegmentsApi.md#deleteUserSegment) | **DELETE** /segments/{projectKey}/{environmentKey}/{userSegmentKey} | Delete a user segment.
[**getExpiringUserTargetsOnSegment**](UserSegmentsApi.md#getExpiringUserTargetsOnSegment) | **GET** /segments/{projectKey}/{userSegmentKey}/expiring-user-targets/{environmentKey} | Get expiring user targets for user segment
[**getUserSegment**](UserSegmentsApi.md#getUserSegment) | **GET** /segments/{projectKey}/{environmentKey}/{userSegmentKey} | Get a single user segment by key.
[**getUserSegments**](UserSegmentsApi.md#getUserSegments) | **GET** /segments/{projectKey}/{environmentKey} | Get a list of all user segments in the given project.
[**patchExpiringUserTargetsOnSegment**](UserSegmentsApi.md#patchExpiringUserTargetsOnSegment) | **PATCH** /segments/{projectKey}/{userSegmentKey}/expiring-user-targets/{environmentKey} | Update, add, or delete expiring user targets on user segment
[**patchUserSegment**](UserSegmentsApi.md#patchUserSegment) | **PATCH** /segments/{projectKey}/{environmentKey}/{userSegmentKey} | Perform a partial update to a user segment.
[**postUserSegment**](UserSegmentsApi.md#postUserSegment) | **POST** /segments/{projectKey}/{environmentKey} | Creates a new user segment.
[**updatedBigSegmentTargets**](UserSegmentsApi.md#updatedBigSegmentTargets) | **POST** /segments/{projectKey}/{environmentKey}/{userSegmentKey}/users | Update targets included or excluded in a big segment


<a name="deleteUserSegment"></a>
# **deleteUserSegment**
> deleteUserSegment(projectKey, environmentKey, userSegmentKey)

Delete a user segment.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.UserSegmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

UserSegmentsApi apiInstance = new UserSegmentsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
String userSegmentKey = "userSegmentKey_example"; // String | The user segment's key. The key identifies the user segment in your code.
try {
    apiInstance.deleteUserSegment(projectKey, environmentKey, userSegmentKey);
} catch (ApiException e) {
    System.err.println("Exception when calling UserSegmentsApi#deleteUserSegment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **userSegmentKey** | **String**| The user segment&#39;s key. The key identifies the user segment in your code. |

### Return type

null (empty response body)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getExpiringUserTargetsOnSegment"></a>
# **getExpiringUserTargetsOnSegment**
> UserTargetingExpirationForSegment getExpiringUserTargetsOnSegment(projectKey, environmentKey, userSegmentKey)

Get expiring user targets for user segment

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.UserSegmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

UserSegmentsApi apiInstance = new UserSegmentsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
String userSegmentKey = "userSegmentKey_example"; // String | The user segment's key. The key identifies the user segment in your code.
try {
    UserTargetingExpirationForSegment result = apiInstance.getExpiringUserTargetsOnSegment(projectKey, environmentKey, userSegmentKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserSegmentsApi#getExpiringUserTargetsOnSegment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **userSegmentKey** | **String**| The user segment&#39;s key. The key identifies the user segment in your code. |

### Return type

[**UserTargetingExpirationForSegment**](UserTargetingExpirationForSegment.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUserSegment"></a>
# **getUserSegment**
> UserSegment getUserSegment(projectKey, environmentKey, userSegmentKey)

Get a single user segment by key.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.UserSegmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

UserSegmentsApi apiInstance = new UserSegmentsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
String userSegmentKey = "userSegmentKey_example"; // String | The user segment's key. The key identifies the user segment in your code.
try {
    UserSegment result = apiInstance.getUserSegment(projectKey, environmentKey, userSegmentKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserSegmentsApi#getUserSegment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **userSegmentKey** | **String**| The user segment&#39;s key. The key identifies the user segment in your code. |

### Return type

[**UserSegment**](UserSegment.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUserSegments"></a>
# **getUserSegments**
> UserSegments getUserSegments(projectKey, environmentKey, tag)

Get a list of all user segments in the given project.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.UserSegmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

UserSegmentsApi apiInstance = new UserSegmentsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
String tag = "tag_example"; // String | Filter by tag. A tag can be used to group flags across projects.
try {
    UserSegments result = apiInstance.getUserSegments(projectKey, environmentKey, tag);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserSegmentsApi#getUserSegments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **tag** | **String**| Filter by tag. A tag can be used to group flags across projects. | [optional]

### Return type

[**UserSegments**](UserSegments.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="patchExpiringUserTargetsOnSegment"></a>
# **patchExpiringUserTargetsOnSegment**
> UserTargetingExpirationForSegment patchExpiringUserTargetsOnSegment(projectKey, environmentKey, userSegmentKey, semanticPatchWithComment)

Update, add, or delete expiring user targets on user segment

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.UserSegmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

UserSegmentsApi apiInstance = new UserSegmentsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
String userSegmentKey = "userSegmentKey_example"; // String | The user segment's key. The key identifies the user segment in your code.
Object semanticPatchWithComment = null; // Object | Requires a Semantic Patch representation of the desired changes to the resource. 'https://apidocs.launchdarkly.com/reference#updates-via-semantic-patches'. The addition of comments is also supported.
try {
    UserTargetingExpirationForSegment result = apiInstance.patchExpiringUserTargetsOnSegment(projectKey, environmentKey, userSegmentKey, semanticPatchWithComment);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserSegmentsApi#patchExpiringUserTargetsOnSegment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **userSegmentKey** | **String**| The user segment&#39;s key. The key identifies the user segment in your code. |
 **semanticPatchWithComment** | **Object**| Requires a Semantic Patch representation of the desired changes to the resource. &#39;https://apidocs.launchdarkly.com/reference#updates-via-semantic-patches&#39;. The addition of comments is also supported. |

### Return type

[**UserTargetingExpirationForSegment**](UserTargetingExpirationForSegment.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="patchUserSegment"></a>
# **patchUserSegment**
> UserSegment patchUserSegment(projectKey, environmentKey, userSegmentKey, patchOnly)

Perform a partial update to a user segment.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.UserSegmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

UserSegmentsApi apiInstance = new UserSegmentsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
String userSegmentKey = "userSegmentKey_example"; // String | The user segment's key. The key identifies the user segment in your code.
List<PatchOperation> patchOnly = Arrays.asList(new PatchOperation()); // List<PatchOperation> | Requires a JSON Patch representation of the desired changes to the project. 'http://jsonpatch.com/' Feature flag patches also support JSON Merge Patch format. 'https://tools.ietf.org/html/rfc7386' The addition of comments is also supported.
try {
    UserSegment result = apiInstance.patchUserSegment(projectKey, environmentKey, userSegmentKey, patchOnly);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserSegmentsApi#patchUserSegment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **userSegmentKey** | **String**| The user segment&#39;s key. The key identifies the user segment in your code. |
 **patchOnly** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)| Requires a JSON Patch representation of the desired changes to the project. &#39;http://jsonpatch.com/&#39; Feature flag patches also support JSON Merge Patch format. &#39;https://tools.ietf.org/html/rfc7386&#39; The addition of comments is also supported. |

### Return type

[**UserSegment**](UserSegment.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="postUserSegment"></a>
# **postUserSegment**
> UserSegment postUserSegment(projectKey, environmentKey, userSegmentBody)

Creates a new user segment.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.UserSegmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

UserSegmentsApi apiInstance = new UserSegmentsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
UserSegmentBody userSegmentBody = new UserSegmentBody(); // UserSegmentBody | Create a new user segment.
try {
    UserSegment result = apiInstance.postUserSegment(projectKey, environmentKey, userSegmentBody);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserSegmentsApi#postUserSegment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **userSegmentBody** | [**UserSegmentBody**](UserSegmentBody.md)| Create a new user segment. |

### Return type

[**UserSegment**](UserSegment.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updatedBigSegmentTargets"></a>
# **updatedBigSegmentTargets**
> updatedBigSegmentTargets(projectKey, environmentKey, userSegmentKey, bigSegmentTargetsBody)

Update targets included or excluded in a big segment

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.UserSegmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

UserSegmentsApi apiInstance = new UserSegmentsApi();
String projectKey = "projectKey_example"; // String | The project key, used to tie the flags together under one project so they can be managed together.
String environmentKey = "environmentKey_example"; // String | The environment key, used to tie together flag configuration and users under one environment so they can be managed together.
String userSegmentKey = "userSegmentKey_example"; // String | The user segment's key. The key identifies the user segment in your code.
BigSegmentTargetsBody bigSegmentTargetsBody = new BigSegmentTargetsBody(); // BigSegmentTargetsBody | Add or remove user targets to the included or excluded lists on a big segment. Contact your account manager for early access to this feature.
try {
    apiInstance.updatedBigSegmentTargets(projectKey, environmentKey, userSegmentKey, bigSegmentTargetsBody);
} catch (ApiException e) {
    System.err.println("Exception when calling UserSegmentsApi#updatedBigSegmentTargets");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectKey** | **String**| The project key, used to tie the flags together under one project so they can be managed together. |
 **environmentKey** | **String**| The environment key, used to tie together flag configuration and users under one environment so they can be managed together. |
 **userSegmentKey** | **String**| The user segment&#39;s key. The key identifies the user segment in your code. |
 **bigSegmentTargetsBody** | [**BigSegmentTargetsBody**](BigSegmentTargetsBody.md)| Add or remove user targets to the included or excluded lists on a big segment. Contact your account manager for early access to this feature. |

### Return type

null (empty response body)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

