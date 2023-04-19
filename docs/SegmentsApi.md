# SegmentsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteSegment**](SegmentsApi.md#deleteSegment) | **DELETE** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey} | Delete segment |
| [**getContextInstanceSegmentsMembershipByEnv**](SegmentsApi.md#getContextInstanceSegmentsMembershipByEnv) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/segments/evaluate | List segment memberships for context instance |
| [**getExpiringTargetsForSegment**](SegmentsApi.md#getExpiringTargetsForSegment) | **GET** /api/v2/segments/{projectKey}/{segmentKey}/expiring-targets/{environmentKey} | Get expiring targets for segment |
| [**getExpiringUserTargetsForSegment**](SegmentsApi.md#getExpiringUserTargetsForSegment) | **GET** /api/v2/segments/{projectKey}/{segmentKey}/expiring-user-targets/{environmentKey} | Get expiring user targets for segment |
| [**getSegment**](SegmentsApi.md#getSegment) | **GET** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey} | Get segment |
| [**getSegmentMembershipForContext**](SegmentsApi.md#getSegmentMembershipForContext) | **GET** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/contexts/{contextKey} | Get Big Segment membership for context |
| [**getSegmentMembershipForUser**](SegmentsApi.md#getSegmentMembershipForUser) | **GET** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/users/{userKey} | Get Big Segment membership for user |
| [**getSegments**](SegmentsApi.md#getSegments) | **GET** /api/v2/segments/{projectKey}/{environmentKey} | List segments |
| [**patchExpiringTargetsForSegment**](SegmentsApi.md#patchExpiringTargetsForSegment) | **PATCH** /api/v2/segments/{projectKey}/{segmentKey}/expiring-targets/{environmentKey} | Update expiring targets for segment |
| [**patchExpiringUserTargetsForSegment**](SegmentsApi.md#patchExpiringUserTargetsForSegment) | **PATCH** /api/v2/segments/{projectKey}/{segmentKey}/expiring-user-targets/{environmentKey} | Update expiring user targets for segment |
| [**patchSegment**](SegmentsApi.md#patchSegment) | **PATCH** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey} | Patch segment |
| [**postSegment**](SegmentsApi.md#postSegment) | **POST** /api/v2/segments/{projectKey}/{environmentKey} | Create segment |
| [**updateBigSegmentContextTargets**](SegmentsApi.md#updateBigSegmentContextTargets) | **POST** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/contexts | Update context targets on a Big Segment |
| [**updateBigSegmentTargets**](SegmentsApi.md#updateBigSegmentTargets) | **POST** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/users | Update user context targets on a Big Segment |


<a name="deleteSegment"></a>
# **deleteSegment**
> deleteSegment(projectKey, environmentKey, segmentKey)

Delete segment

Delete a segment.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    try {
      apiInstance.deleteSegment(projectKey, environmentKey, segmentKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#deleteSegment");
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
| **projectKey** | **String**| The project key | |
| **environmentKey** | **String**| The environment key | |
| **segmentKey** | **String**| The segment key | |

### Return type

null (empty response body)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Action succeeded |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a name="getContextInstanceSegmentsMembershipByEnv"></a>
# **getContextInstanceSegmentsMembershipByEnv**
> ContextInstanceSegmentMemberships getContextInstanceSegmentsMembershipByEnv(projectKey, environmentKey, requestBody)

List segment memberships for context instance

For a given context instance with attributes, get membership details for all segments

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    Map<String, Object> requestBody = {"key":"context-key-123abc","kind":"user","moreComplex":{"morethanone":[1,2,3],"yes":"please"},"name":"Some User","something":true}; // Map<String, Object> | 
    try {
      ContextInstanceSegmentMemberships result = apiInstance.getContextInstanceSegmentsMembershipByEnv(projectKey, environmentKey, requestBody);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#getContextInstanceSegmentsMembershipByEnv");
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
| **projectKey** | **String**| The project key | |
| **environmentKey** | **String**| The environment key | |
| **requestBody** | [**Map&lt;String, Object&gt;**](Object.md)|  | |

### Return type

[**ContextInstanceSegmentMemberships**](ContextInstanceSegmentMemberships.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Context instance segment membership collection response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |

<a name="getExpiringTargetsForSegment"></a>
# **getExpiringTargetsForSegment**
> ExpiringTargetGetResponse getExpiringTargetsForSegment(projectKey, environmentKey, segmentKey)

Get expiring targets for segment

Get a list of a segment&#39;s context targets that are scheduled for removal.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    try {
      ExpiringTargetGetResponse result = apiInstance.getExpiringTargetsForSegment(projectKey, environmentKey, segmentKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#getExpiringTargetsForSegment");
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
| **projectKey** | **String**| The project key | |
| **environmentKey** | **String**| The environment key | |
| **segmentKey** | **String**| The segment key | |

### Return type

[**ExpiringTargetGetResponse**](ExpiringTargetGetResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Expiring context target response |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getExpiringUserTargetsForSegment"></a>
# **getExpiringUserTargetsForSegment**
> ExpiringUserTargetGetResponse getExpiringUserTargetsForSegment(projectKey, environmentKey, segmentKey)

Get expiring user targets for segment

&gt; ### Contexts are now available &gt; &gt; After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should use [Get expiring targets for segment](/tag/Segments#operation/getExpiringTargetsForSegment) instead of this endpoint. To learn more, read [Contexts](https://docs.launchdarkly.com/home/contexts).  Get a list of a segment&#39;s user targets that are scheduled for removal. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    try {
      ExpiringUserTargetGetResponse result = apiInstance.getExpiringUserTargetsForSegment(projectKey, environmentKey, segmentKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#getExpiringUserTargetsForSegment");
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
| **projectKey** | **String**| The project key | |
| **environmentKey** | **String**| The environment key | |
| **segmentKey** | **String**| The segment key | |

### Return type

[**ExpiringUserTargetGetResponse**](ExpiringUserTargetGetResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Expiring user target response |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getSegment"></a>
# **getSegment**
> UserSegment getSegment(projectKey, environmentKey, segmentKey)

Get segment

Get a single segment by key.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    try {
      UserSegment result = apiInstance.getSegment(projectKey, environmentKey, segmentKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#getSegment");
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
| **projectKey** | **String**| The project key | |
| **environmentKey** | **String**| The environment key | |
| **segmentKey** | **String**| The segment key | |

### Return type

[**UserSegment**](UserSegment.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Segment response |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getSegmentMembershipForContext"></a>
# **getSegmentMembershipForContext**
> BigSegmentTarget getSegmentMembershipForContext(projectKey, environmentKey, segmentKey, contextKey)

Get Big Segment membership for context

Get the membership status (included/excluded) for a given context in this Big Segment. This operation does not support standard segments.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    String contextKey = "contextKey_example"; // String | The context key
    try {
      BigSegmentTarget result = apiInstance.getSegmentMembershipForContext(projectKey, environmentKey, segmentKey, contextKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#getSegmentMembershipForContext");
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
| **projectKey** | **String**| The project key | |
| **environmentKey** | **String**| The environment key | |
| **segmentKey** | **String**| The segment key | |
| **contextKey** | **String**| The context key | |

### Return type

[**BigSegmentTarget**](BigSegmentTarget.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Segment membership for context response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getSegmentMembershipForUser"></a>
# **getSegmentMembershipForUser**
> BigSegmentTarget getSegmentMembershipForUser(projectKey, environmentKey, segmentKey, userKey)

Get Big Segment membership for user

&gt; ### Contexts are now available &gt; &gt; After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should use [Get expiring targets for segment](/tag/Segments#operation/getExpiringTargetsForSegment) instead of this endpoint. To learn more, read [Contexts](https://docs.launchdarkly.com/home/contexts).  Get the membership status (included/excluded) for a given user in this Big Segment. This operation does not support standard segments. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    String userKey = "userKey_example"; // String | The user key
    try {
      BigSegmentTarget result = apiInstance.getSegmentMembershipForUser(projectKey, environmentKey, segmentKey, userKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#getSegmentMembershipForUser");
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
| **projectKey** | **String**| The project key | |
| **environmentKey** | **String**| The environment key | |
| **segmentKey** | **String**| The segment key | |
| **userKey** | **String**| The user key | |

### Return type

[**BigSegmentTarget**](BigSegmentTarget.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Segment membership for user response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getSegments"></a>
# **getSegments**
> UserSegments getSegments(projectKey, environmentKey)

List segments

Get a list of all segments in the given project.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    try {
      UserSegments result = apiInstance.getSegments(projectKey, environmentKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#getSegments");
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
| **projectKey** | **String**| The project key | |
| **environmentKey** | **String**| The environment key | |

### Return type

[**UserSegments**](UserSegments.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Segment collection response |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |

<a name="patchExpiringTargetsForSegment"></a>
# **patchExpiringTargetsForSegment**
> ExpiringTargetPatchResponse patchExpiringTargetsForSegment(projectKey, environmentKey, segmentKey, patchSegmentRequest)

Update expiring targets for segment

 Update expiring context targets for a segment. Updating a context target expiration uses the semantic patch format.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  If the request is well-formed but any of its instructions failed to process, this operation returns status code &#x60;200&#x60;. In this case, the response &#x60;errors&#x60; array will be non-empty.  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating context targets.  #### addExpiringTarget  Schedules a date and time when LaunchDarkly will remove a context from segment targeting.  ##### Parameters  - &#x60;targetType&#x60;: A segment&#39;s target type, must be either &#x60;included&#x60; or &#x60;excluded&#x60;. - &#x60;contextKey&#x60;: The context key. - &#x60;contextKind&#x60;: The kind of context being targeted. - &#x60;value&#x60;: The date when the context should expire from the segment targeting, in Unix milliseconds.  #### updateExpiringTarget  Updates the date and time when LaunchDarkly will remove a context from segment targeting.  ##### Parameters  - &#x60;targetType&#x60;: A segment&#39;s target type, must be either &#x60;included&#x60; or &#x60;excluded&#x60;. - &#x60;contextKey&#x60;: The context key. - &#x60;contextKind&#x60;: The kind of context being targeted. - &#x60;value&#x60;: The new date when the context should expire from the segment targeting, in Unix milliseconds. - &#x60;version&#x60;: The segment version.  #### removeExpiringTarget  Removes the scheduled expiration for the context in the segment.  ##### Parameters  - &#x60;targetType&#x60;: A segment&#39;s target type, must be either &#x60;included&#x60; or &#x60;excluded&#x60;. - &#x60;contextKey&#x60;: The context key. - &#x60;contextKind&#x60;: The kind of context being targeted. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    PatchSegmentRequest patchSegmentRequest = new PatchSegmentRequest(); // PatchSegmentRequest | 
    try {
      ExpiringTargetPatchResponse result = apiInstance.patchExpiringTargetsForSegment(projectKey, environmentKey, segmentKey, patchSegmentRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#patchExpiringTargetsForSegment");
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
| **projectKey** | **String**| The project key | |
| **environmentKey** | **String**| The environment key | |
| **segmentKey** | **String**| The segment key | |
| **patchSegmentRequest** | [**PatchSegmentRequest**](PatchSegmentRequest.md)|  | |

### Return type

[**ExpiringTargetPatchResponse**](ExpiringTargetPatchResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Expiring  target response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a name="patchExpiringUserTargetsForSegment"></a>
# **patchExpiringUserTargetsForSegment**
> ExpiringUserTargetPatchResponse patchExpiringUserTargetsForSegment(projectKey, environmentKey, segmentKey, patchSegmentRequest)

Update expiring user targets for segment

 &gt; ### Contexts are now available &gt; &gt; After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should use [Update expiring targets for segment](/tag/Segments#operation/patchExpiringTargetsForSegment) instead of this endpoint. To learn more, read [Contexts](https://docs.launchdarkly.com/home/contexts).  Update expiring user targets for a segment. Updating a user target expiration uses the semantic patch format.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  If the request is well-formed but any of its instructions failed to process, this operation returns status code &#x60;200&#x60;. In this case, the response &#x60;errors&#x60; array will be non-empty.  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating user targets.  #### addExpireUserTargetDate  Schedules a date and time when LaunchDarkly will remove a user from segment targeting.  ##### Parameters  - &#x60;targetType&#x60;: A segment&#39;s target type, must be either &#x60;included&#x60; or &#x60;excluded&#x60;. - &#x60;userKey&#x60;: The user key. - &#x60;value&#x60;: The date when the user should expire from the segment targeting, in Unix milliseconds.  #### updateExpireUserTargetDate  Updates the date and time when LaunchDarkly will remove a user from segment targeting.  ##### Parameters  - &#x60;targetType&#x60;: A segment&#39;s target type, must be either &#x60;included&#x60; or &#x60;excluded&#x60;. - &#x60;userKey&#x60;: The user key. - &#x60;value&#x60;: The new date when the user should expire from the segment targeting, in Unix milliseconds. - &#x60;version&#x60;: The segment version.  #### removeExpireUserTargetDate  Removes the scheduled expiration for the user in the segment.  ##### Parameters  - &#x60;targetType&#x60;: A segment&#39;s target type, must be either &#x60;included&#x60; or &#x60;excluded&#x60;. - &#x60;userKey&#x60;: The user key. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    PatchSegmentRequest patchSegmentRequest = new PatchSegmentRequest(); // PatchSegmentRequest | 
    try {
      ExpiringUserTargetPatchResponse result = apiInstance.patchExpiringUserTargetsForSegment(projectKey, environmentKey, segmentKey, patchSegmentRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#patchExpiringUserTargetsForSegment");
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
| **projectKey** | **String**| The project key | |
| **environmentKey** | **String**| The environment key | |
| **segmentKey** | **String**| The segment key | |
| **patchSegmentRequest** | [**PatchSegmentRequest**](PatchSegmentRequest.md)|  | |

### Return type

[**ExpiringUserTargetPatchResponse**](ExpiringUserTargetPatchResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Expiring user target response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a name="patchSegment"></a>
# **patchSegment**
> UserSegment patchSegment(projectKey, environmentKey, segmentKey, patchWithComment)

Patch segment

Update a segment. The request body must be a valid semantic patch, JSON patch, or JSON merge patch.  ### Using semantic patches on a segment  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  The body of a semantic patch request for updating segments requires an &#x60;environmentKey&#x60; in addition to &#x60;instructions&#x60; and an optional &#x60;comment&#x60;. The body of the request takes the following properties:  * &#x60;comment&#x60; (string): (Optional) A description of the update. * &#x60;environmentKey&#x60; (string): (Required) The key of the LaunchDarkly environment. * &#x60;instructions&#x60; (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a &#x60;kind&#x60; property that indicates the instruction. If the action requires parameters, you must include those parameters as additional fields in the object.  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating segments.  #### addIncludedTargets  Adds context keys to the individual context targets included in the segment for the specified &#x60;contextKind&#x60;. Returns an error if this causes the same context key to be both included and excluded.  ##### Parameters  - &#x60;contextKind&#x60;: The context kind the targets should be added to. - &#x60;values&#x60;: List of keys.  #### addIncludedUsers  Adds user keys to the individual user targets included in the segment. Returns an error if this causes the same user key to be both included and excluded. If you are working with contexts, use &#x60;addIncludedTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;values&#x60;: List of user keys.  #### addExcludedTargets  Adds context keys to the individual context targets excluded in the segment for the specified &#x60;contextKind&#x60;. Returns an error if this causes the same context key to be both included and excluded.  ##### Parameters  - &#x60;contextKind&#x60;: The context kind the targets should be added to. - &#x60;values&#x60;: List of keys.  #### addExcludedUsers  Adds user keys to the individual user targets excluded from the segment. Returns an error if this causes the same user key to be both included and excluded. If you are working with contexts, use &#x60;addExcludedTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;values&#x60;: List of user keys.  #### removeIncludedTargets  Removes context keys from the individual context targets included in the segment for the specified &#x60;contextKind&#x60;.  ##### Parameters  - &#x60;contextKind&#x60;: The context kind the targets should be removed from. - &#x60;values&#x60;: List of keys.  #### removeIncludedUsers  Removes user keys from the individual user targets included in the segment. If you are working with contexts, use &#x60;removeIncludedTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;values&#x60;: List of user keys.  #### removeExcludedTargets  Removes context keys from the individual context targets excluded from the segment for the specified &#x60;contextKind&#x60;.  ##### Parameters  - &#x60;contextKind&#x60;: The context kind the targets should be removed from. - &#x60;values&#x60;: List of keys.  #### removeExcludedUsers  Removes user keys from the individual user targets excluded from the segment. If you are working with contexts, use &#x60;removeExcludedTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;values&#x60;: List of user keys.  #### updateName  Updates the name of the segment.  ##### Parameters  - &#x60;value&#x60;: Name of the segment.  ## Using JSON patches on a segment  You can also use JSON patch. To learn more, read [Updates using JSON patches](/reference#updates-using-json-patch).  For example, to update the description for a segment, use the following request body:  &#x60;&#x60;&#x60;json {   \&quot;patch\&quot;: [     {       \&quot;op\&quot;: \&quot;replace\&quot;,       \&quot;path\&quot;: \&quot;/description\&quot;,       \&quot;value\&quot;: \&quot;new description\&quot;     }   ] } &#x60;&#x60;&#x60;  To update fields in the segment that are arrays, set the &#x60;path&#x60; to the name of the field and then append &#x60;/&lt;array index&gt;&#x60;. Using &#x60;/0&#x60; adds the new entry to the beginning of the array.  For example, to add a rule to a segment, use the following request body:  &#x60;&#x60;&#x60;json {   \&quot;patch\&quot;:[     {       \&quot;op\&quot;: \&quot;add\&quot;,       \&quot;path\&quot;: \&quot;/rules/0\&quot;,       \&quot;value\&quot;: {         \&quot;clauses\&quot;: [{ \&quot;contextKind\&quot;: \&quot;user\&quot;, \&quot;attribute\&quot;: \&quot;email\&quot;, \&quot;op\&quot;: \&quot;endsWith\&quot;, \&quot;values\&quot;: [\&quot;.edu\&quot;], \&quot;negate\&quot;: false }]       }     }   ] } &#x60;&#x60;&#x60;  To add or remove targets from segments, we recommend using semantic patch. Semantic patch for segments includes specific instructions for adding and removing both included and excluded targets. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    PatchWithComment patchWithComment = new PatchWithComment(); // PatchWithComment | 
    try {
      UserSegment result = apiInstance.patchSegment(projectKey, environmentKey, segmentKey, patchWithComment);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#patchSegment");
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
| **projectKey** | **String**| The project key | |
| **environmentKey** | **String**| The environment key | |
| **segmentKey** | **String**| The segment key | |
| **patchWithComment** | [**PatchWithComment**](PatchWithComment.md)|  | |

### Return type

[**UserSegment**](UserSegment.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Segment response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a name="postSegment"></a>
# **postSegment**
> UserSegment postSegment(projectKey, environmentKey, segmentBody)

Create segment

Create a new segment.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    SegmentBody segmentBody = new SegmentBody(); // SegmentBody | 
    try {
      UserSegment result = apiInstance.postSegment(projectKey, environmentKey, segmentBody);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#postSegment");
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
| **projectKey** | **String**| The project key | |
| **environmentKey** | **String**| The environment key | |
| **segmentBody** | [**SegmentBody**](SegmentBody.md)|  | |

### Return type

[**UserSegment**](UserSegment.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Segment response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="updateBigSegmentContextTargets"></a>
# **updateBigSegmentContextTargets**
> updateBigSegmentContextTargets(projectKey, environmentKey, segmentKey, segmentUserState)

Update context targets on a Big Segment

Update context targets included or excluded in a Big Segment.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    SegmentUserState segmentUserState = new SegmentUserState(); // SegmentUserState | 
    try {
      apiInstance.updateBigSegmentContextTargets(projectKey, environmentKey, segmentKey, segmentUserState);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#updateBigSegmentContextTargets");
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
| **projectKey** | **String**| The project key | |
| **environmentKey** | **String**| The environment key | |
| **segmentKey** | **String**| The segment key | |
| **segmentUserState** | [**SegmentUserState**](SegmentUserState.md)|  | |

### Return type

null (empty response body)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Action succeeded |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="updateBigSegmentTargets"></a>
# **updateBigSegmentTargets**
> updateBigSegmentTargets(projectKey, environmentKey, segmentKey, segmentUserState)

Update user context targets on a Big Segment

Update user context targets included or excluded in a Big Segment.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.SegmentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    SegmentsApi apiInstance = new SegmentsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String segmentKey = "segmentKey_example"; // String | The segment key
    SegmentUserState segmentUserState = new SegmentUserState(); // SegmentUserState | 
    try {
      apiInstance.updateBigSegmentTargets(projectKey, environmentKey, segmentKey, segmentUserState);
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsApi#updateBigSegmentTargets");
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
| **projectKey** | **String**| The project key | |
| **environmentKey** | **String**| The environment key | |
| **segmentKey** | **String**| The segment key | |
| **segmentUserState** | [**SegmentUserState**](SegmentUserState.md)|  | |

### Return type

null (empty response body)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Action succeeded |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

