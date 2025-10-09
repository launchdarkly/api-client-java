# ReleasePoliciesBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteReleasePolicy**](ReleasePoliciesBetaApi.md#deleteReleasePolicy) | **DELETE** /api/v2/projects/{projectKey}/release-policies/{policyKey} | Delete a release policy |
| [**getReleasePolicies**](ReleasePoliciesBetaApi.md#getReleasePolicies) | **GET** /api/v2/projects/{projectKey}/release-policies | List release policies |
| [**getReleasePolicy**](ReleasePoliciesBetaApi.md#getReleasePolicy) | **GET** /api/v2/projects/{projectKey}/release-policies/{policyKey} | Get a release policy by key |
| [**postReleasePoliciesOrder**](ReleasePoliciesBetaApi.md#postReleasePoliciesOrder) | **POST** /api/v2/projects/{projectKey}/release-policies/order | Update the order of existing release policies |
| [**postReleasePolicy**](ReleasePoliciesBetaApi.md#postReleasePolicy) | **POST** /api/v2/projects/{projectKey}/release-policies | Create a release policy |
| [**putReleasePolicy**](ReleasePoliciesBetaApi.md#putReleasePolicy) | **PUT** /api/v2/projects/{projectKey}/release-policies/{policyKey} | Update a release policy |


<a id="deleteReleasePolicy"></a>
# **deleteReleasePolicy**
> deleteReleasePolicy(ldAPIVersion, projectKey, policyKey)

Delete a release policy

Delete an existing release policy for the specified project.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ReleasePoliciesBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ReleasePoliciesBetaApi apiInstance = new ReleasePoliciesBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | The project key
    String policyKey = "production-release"; // String | The human-readable key of the release policy
    try {
      apiInstance.deleteReleasePolicy(ldAPIVersion, projectKey, policyKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasePoliciesBetaApi#deleteReleasePolicy");
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
| **ldAPIVersion** | **String**| Version of the endpoint. | [enum: beta] |
| **projectKey** | **String**| The project key | |
| **policyKey** | **String**| The human-readable key of the release policy | |

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
| **204** | Release policy deleted successfully |  -  |
| **400** | Bad request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="getReleasePolicies"></a>
# **getReleasePolicies**
> ReleasePoliciesResponse getReleasePolicies(ldAPIVersion, projectKey, excludeDefault)

List release policies

Get a list of release policies for the specified project with optional filtering.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ReleasePoliciesBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ReleasePoliciesBetaApi apiInstance = new ReleasePoliciesBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | The project key
    Boolean excludeDefault = false; // Boolean | When true, exclude the default release policy from the response. When false or omitted, include the default policy if an environment filter is present.
    try {
      ReleasePoliciesResponse result = apiInstance.getReleasePolicies(ldAPIVersion, projectKey, excludeDefault);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasePoliciesBetaApi#getReleasePolicies");
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
| **ldAPIVersion** | **String**| Version of the endpoint. | [enum: beta] |
| **projectKey** | **String**| The project key | |
| **excludeDefault** | **Boolean**| When true, exclude the default release policy from the response. When false or omitted, include the default policy if an environment filter is present. | [optional] [default to false] |

### Return type

[**ReleasePoliciesResponse**](ReleasePoliciesResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | List of release policies |  -  |
| **400** | Bad request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="getReleasePolicy"></a>
# **getReleasePolicy**
> ReleasePolicy getReleasePolicy(ldAPIVersion, projectKey, policyKey)

Get a release policy by key

Retrieve a single release policy by its key for the specified project.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ReleasePoliciesBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ReleasePoliciesBetaApi apiInstance = new ReleasePoliciesBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | The project key
    String policyKey = "production-release"; // String | The release policy key
    try {
      ReleasePolicy result = apiInstance.getReleasePolicy(ldAPIVersion, projectKey, policyKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasePoliciesBetaApi#getReleasePolicy");
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
| **ldAPIVersion** | **String**| Version of the endpoint. | [enum: beta] |
| **projectKey** | **String**| The project key | |
| **policyKey** | **String**| The release policy key | |

### Return type

[**ReleasePolicy**](ReleasePolicy.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Release policy found |  -  |
| **400** | Bad request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="postReleasePoliciesOrder"></a>
# **postReleasePoliciesOrder**
> List&lt;ReleasePolicy&gt; postReleasePoliciesOrder(ldAPIVersion, projectKey, requestBody)

Update the order of existing release policies

Update the order of existing release policies for the specified project.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ReleasePoliciesBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ReleasePoliciesBetaApi apiInstance = new ReleasePoliciesBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | The project key
    List<String> requestBody = Arrays.asList(); // List<String> | Array of release policy keys in the desired rank order (scoped policies only). These keys must include _all_ of the scoped release policies for the project.
    try {
      List<ReleasePolicy> result = apiInstance.postReleasePoliciesOrder(ldAPIVersion, projectKey, requestBody);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasePoliciesBetaApi#postReleasePoliciesOrder");
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
| **ldAPIVersion** | **String**| Version of the endpoint. | [enum: beta] |
| **projectKey** | **String**| The project key | |
| **requestBody** | [**List&lt;String&gt;**](String.md)| Array of release policy keys in the desired rank order (scoped policies only). These keys must include _all_ of the scoped release policies for the project. | |

### Return type

[**List&lt;ReleasePolicy&gt;**](ReleasePolicy.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Release policies updated successfully |  -  |
| **400** | Bad request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="postReleasePolicy"></a>
# **postReleasePolicy**
> ReleasePolicy postReleasePolicy(ldAPIVersion, projectKey, postReleasePolicyRequest)

Create a release policy

Create a new release policy for the specified project.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ReleasePoliciesBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ReleasePoliciesBetaApi apiInstance = new ReleasePoliciesBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | The project key
    PostReleasePolicyRequest postReleasePolicyRequest = new PostReleasePolicyRequest(); // PostReleasePolicyRequest | Release policy to create
    try {
      ReleasePolicy result = apiInstance.postReleasePolicy(ldAPIVersion, projectKey, postReleasePolicyRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasePoliciesBetaApi#postReleasePolicy");
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
| **ldAPIVersion** | **String**| Version of the endpoint. | [enum: beta] |
| **projectKey** | **String**| The project key | |
| **postReleasePolicyRequest** | [**PostReleasePolicyRequest**](PostReleasePolicyRequest.md)| Release policy to create | |

### Return type

[**ReleasePolicy**](ReleasePolicy.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Release policy created successfully |  -  |
| **400** | Bad request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **409** | Conflict |  -  |
| **500** | Internal server error |  -  |

<a id="putReleasePolicy"></a>
# **putReleasePolicy**
> ReleasePolicy putReleasePolicy(ldAPIVersion, projectKey, policyKey, putReleasePolicyRequest)

Update a release policy

Update an existing release policy for the specified project.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ReleasePoliciesBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ReleasePoliciesBetaApi apiInstance = new ReleasePoliciesBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | The project key
    String policyKey = "production-release"; // String | The human-readable key of the release policy
    PutReleasePolicyRequest putReleasePolicyRequest = new PutReleasePolicyRequest(); // PutReleasePolicyRequest | Release policy data to update
    try {
      ReleasePolicy result = apiInstance.putReleasePolicy(ldAPIVersion, projectKey, policyKey, putReleasePolicyRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasePoliciesBetaApi#putReleasePolicy");
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
| **ldAPIVersion** | **String**| Version of the endpoint. | [enum: beta] |
| **projectKey** | **String**| The project key | |
| **policyKey** | **String**| The human-readable key of the release policy | |
| **putReleasePolicyRequest** | [**PutReleasePolicyRequest**](PutReleasePolicyRequest.md)| Release policy data to update | |

### Return type

[**ReleasePolicy**](ReleasePolicy.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Release policy updated successfully |  -  |
| **400** | Bad request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

