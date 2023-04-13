# ApprovalsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteApprovalRequest**](ApprovalsBetaApi.md#deleteApprovalRequest) | **DELETE** /api/v2/approval-requests/{id} | Delete approval request |
| [**getApprovalRequest**](ApprovalsBetaApi.md#getApprovalRequest) | **GET** /api/v2/approval-requests/{id} | Get approval request |
| [**getApprovalRequests**](ApprovalsBetaApi.md#getApprovalRequests) | **GET** /api/v2/approval-requests | List approval requests |
| [**postApprovalRequest**](ApprovalsBetaApi.md#postApprovalRequest) | **POST** /api/v2/approval-requests | Create approval request |
| [**postApprovalRequestApply**](ApprovalsBetaApi.md#postApprovalRequestApply) | **POST** /api/v2/approval-requests/{id}/apply | Apply approval request |
| [**postApprovalRequestReview**](ApprovalsBetaApi.md#postApprovalRequestReview) | **POST** /api/v2/approval-requests/{id}/reviews | Review approval request |


<a name="deleteApprovalRequest"></a>
# **deleteApprovalRequest**
> deleteApprovalRequest(id)

Delete approval request

Delete an approval request.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ApprovalsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ApprovalsBetaApi apiInstance = new ApprovalsBetaApi(defaultClient);
    String id = "id_example"; // String | The approval request ID
    try {
      apiInstance.deleteApprovalRequest(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsBetaApi#deleteApprovalRequest");
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
| **id** | **String**| The approval request ID | |

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
| **429** | Rate limited |  -  |

<a name="getApprovalRequest"></a>
# **getApprovalRequest**
> ExpandableApprovalRequestResponse getApprovalRequest(id, expand)

Get approval request

Get an approval request by approval request ID.  ### Expanding approval response  LaunchDarkly supports the &#x60;expand&#x60; query param to include additional fields in the response, with the following fields:  - &#x60;flag&#x60; includes the flag the approval request belongs to - &#x60;project&#x60; includes the project the approval request belongs to - &#x60;environments&#x60; includes the environments the approval request relates to  For example, &#x60;expand&#x3D;project,flag&#x60; includes the &#x60;project&#x60; and &#x60;flag&#x60; fields in the response. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ApprovalsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ApprovalsBetaApi apiInstance = new ApprovalsBetaApi(defaultClient);
    String id = "id_example"; // String | The approval request ID
    String expand = "expand_example"; // String | A comma-separated list of fields to expand in the response. Supported fields are explained above.
    try {
      ExpandableApprovalRequestResponse result = apiInstance.getApprovalRequest(id, expand);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsBetaApi#getApprovalRequest");
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
| **id** | **String**| The approval request ID | |
| **expand** | **String**| A comma-separated list of fields to expand in the response. Supported fields are explained above. | [optional] |

### Return type

[**ExpandableApprovalRequestResponse**](ExpandableApprovalRequestResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Approval request response |  -  |
| **400** | Invalid Request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Unable to find approval request |  -  |
| **429** | Rate limited |  -  |

<a name="getApprovalRequests"></a>
# **getApprovalRequests**
> ExpandableApprovalRequestsResponse getApprovalRequests(filter, expand, limit, offset)

List approval requests

Get all approval requests.  ### Filtering approvals  LaunchDarkly supports the &#x60;filter&#x60; query param for filtering, with the following fields:  - &#x60;notifyMemberIds&#x60; filters for only approvals that are assigned to a member in the specified list. For example: &#x60;filter&#x3D;notifyMemberIds anyOf [\&quot;memberId1\&quot;, \&quot;memberId2\&quot;]&#x60;. - &#x60;requestorId&#x60; filters for only approvals that correspond to the ID of the member who requested the approval. For example: &#x60;filter&#x3D;requestorId equals 457034721476302714390214&#x60;. - &#x60;resourceId&#x60; filters for only approvals that correspond to the the specified resource identifier. For example: &#x60;filter&#x3D;resourceId equals proj/my-project:env/my-environment:flag/my-flag&#x60;. - &#x60;reviewStatus&#x60; filters for only approvals which correspond to the review status in the specified list. The possible values are &#x60;approved&#x60;, &#x60;declined&#x60;, and &#x60;pending&#x60;. For example: &#x60;filter&#x3D;reviewStatus anyOf [\&quot;pending\&quot;, \&quot;approved\&quot;]&#x60;. - &#x60;status&#x60; filters for only approvals which correspond to the status in the specified list. The possible values are &#x60;pending&#x60;, &#x60;scheduled&#x60;, &#x60;failed&#x60;, and &#x60;completed&#x60;. For example: &#x60;filter&#x3D;status anyOf [\&quot;pending\&quot;, \&quot;scheduled\&quot;]&#x60;.  You can also apply multiple filters at once. For example, setting &#x60;filter&#x3D;projectKey equals my-project, reviewStatus anyOf [\&quot;pending\&quot;,\&quot;approved\&quot;]&#x60; matches approval requests which correspond to the &#x60;my-project&#x60; project key, and a review status of either &#x60;pending&#x60; or &#x60;approved&#x60;.  ### Expanding approval response  LaunchDarkly supports the &#x60;expand&#x60; query param to include additional fields in the response, with the following fields:  - &#x60;flag&#x60; includes the flag the approval request belongs to - &#x60;project&#x60; includes the project the approval request belongs to - &#x60;environments&#x60; includes the environments the approval request relates to  For example, &#x60;expand&#x3D;project,flag&#x60; includes the &#x60;project&#x60; and &#x60;flag&#x60; fields in the response. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ApprovalsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ApprovalsBetaApi apiInstance = new ApprovalsBetaApi(defaultClient);
    String filter = "filter_example"; // String | A comma-separated list of filters. Each filter is of the form `field operator value`. Supported fields are explained above.
    String expand = "expand_example"; // String | A comma-separated list of fields to expand in the response. Supported fields are explained above.
    Long limit = 56L; // Long | The number of approvals to return. Defaults to -1, which returns all approvals.
    Long offset = 56L; // Long | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    try {
      ExpandableApprovalRequestsResponse result = apiInstance.getApprovalRequests(filter, expand, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsBetaApi#getApprovalRequests");
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
| **filter** | **String**| A comma-separated list of filters. Each filter is of the form &#x60;field operator value&#x60;. Supported fields are explained above. | [optional] |
| **expand** | **String**| A comma-separated list of fields to expand in the response. Supported fields are explained above. | [optional] |
| **limit** | **Long**| The number of approvals to return. Defaults to -1, which returns all approvals. | [optional] |
| **offset** | **Long**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |

### Return type

[**ExpandableApprovalRequestsResponse**](ExpandableApprovalRequestsResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Approval request collection response |  -  |
| **400** | Unsupported filter field. Filter field must be one of: requestorId, projectKey, notifyMemberIds, reviewStatus, or status |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |

<a name="postApprovalRequest"></a>
# **postApprovalRequest**
> ApprovalRequestResponse postApprovalRequest(createApprovalRequestRequest)

Create approval request

Create an approval request.  This endpoint currently supports creating an approval request for a flag across all environments with the following instructions:  - &#x60;addVariation&#x60; - &#x60;removeVariation&#x60; - &#x60;updateVariation&#x60; - &#x60;updateDefaultVariation&#x60;  For details on using these instructions, read [Update feature flag](/tag/Feature-flags#operation/patchFeatureFlag).  To create an approval for a flag specific to an environment, use [Create approval request for a flag](/tag/Approvals#operation/postApprovalRequestForFlag). 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ApprovalsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ApprovalsBetaApi apiInstance = new ApprovalsBetaApi(defaultClient);
    CreateApprovalRequestRequest createApprovalRequestRequest = new CreateApprovalRequestRequest(); // CreateApprovalRequestRequest | 
    try {
      ApprovalRequestResponse result = apiInstance.postApprovalRequest(createApprovalRequestRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsBetaApi#postApprovalRequest");
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
| **createApprovalRequestRequest** | [**CreateApprovalRequestRequest**](CreateApprovalRequestRequest.md)|  | |

### Return type

[**ApprovalRequestResponse**](ApprovalRequestResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Approval request response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |

<a name="postApprovalRequestApply"></a>
# **postApprovalRequestApply**
> ApprovalRequestResponse postApprovalRequestApply(id, postApprovalRequestApplyRequest)

Apply approval request

Apply an approval request that has been approved.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ApprovalsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ApprovalsBetaApi apiInstance = new ApprovalsBetaApi(defaultClient);
    String id = "id_example"; // String | The feature flag approval request ID
    PostApprovalRequestApplyRequest postApprovalRequestApplyRequest = new PostApprovalRequestApplyRequest(); // PostApprovalRequestApplyRequest | 
    try {
      ApprovalRequestResponse result = apiInstance.postApprovalRequestApply(id, postApprovalRequestApplyRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsBetaApi#postApprovalRequestApply");
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
| **id** | **String**| The feature flag approval request ID | |
| **postApprovalRequestApplyRequest** | [**PostApprovalRequestApplyRequest**](PostApprovalRequestApplyRequest.md)|  | |

### Return type

[**ApprovalRequestResponse**](ApprovalRequestResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Approval request apply response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="postApprovalRequestReview"></a>
# **postApprovalRequestReview**
> ApprovalRequestResponse postApprovalRequestReview(id, postApprovalRequestReviewRequest)

Review approval request

Review an approval request by approving or denying changes.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ApprovalsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ApprovalsBetaApi apiInstance = new ApprovalsBetaApi(defaultClient);
    String id = "id_example"; // String | The approval request ID
    PostApprovalRequestReviewRequest postApprovalRequestReviewRequest = new PostApprovalRequestReviewRequest(); // PostApprovalRequestReviewRequest | 
    try {
      ApprovalRequestResponse result = apiInstance.postApprovalRequestReview(id, postApprovalRequestReviewRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsBetaApi#postApprovalRequestReview");
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
| **id** | **String**| The approval request ID | |
| **postApprovalRequestReviewRequest** | [**PostApprovalRequestReviewRequest**](PostApprovalRequestReviewRequest.md)|  | |

### Return type

[**ApprovalRequestResponse**](ApprovalRequestResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Approval request review response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

