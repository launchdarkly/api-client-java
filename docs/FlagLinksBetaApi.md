# FlagLinksBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createFlagLink**](FlagLinksBetaApi.md#createFlagLink) | **POST** /api/v2/flag-links/projects/{projectKey}/flags/{featureFlagKey} | Create flag link |
| [**deleteFlagLink**](FlagLinksBetaApi.md#deleteFlagLink) | **DELETE** /api/v2/flag-links/projects/{projectKey}/flags/{featureFlagKey}/{id} | Delete flag link |
| [**getFlagLinks**](FlagLinksBetaApi.md#getFlagLinks) | **GET** /api/v2/flag-links/projects/{projectKey}/flags/{featureFlagKey} | List flag links |
| [**updateFlagLink**](FlagLinksBetaApi.md#updateFlagLink) | **PATCH** /api/v2/flag-links/projects/{projectKey}/flags/{featureFlagKey}/{id} | Update flag link |


<a name="createFlagLink"></a>
# **createFlagLink**
> FlagLinkRep createFlagLink(projectKey, featureFlagKey, flagLinkPost)

Create flag link

Create a new flag link. Flag links let you reference external resources and associate them with your flags.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FlagLinksBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FlagLinksBetaApi apiInstance = new FlagLinksBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    FlagLinkPost flagLinkPost = new FlagLinkPost(); // FlagLinkPost | 
    try {
      FlagLinkRep result = apiInstance.createFlagLink(projectKey, featureFlagKey, flagLinkPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagLinksBetaApi#createFlagLink");
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
| **featureFlagKey** | **String**| The feature flag key | |
| **flagLinkPost** | [**FlagLinkPost**](FlagLinkPost.md)|  | |

### Return type

[**FlagLinkRep**](FlagLinkRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Flag link response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="deleteFlagLink"></a>
# **deleteFlagLink**
> deleteFlagLink(projectKey, featureFlagKey, id)

Delete flag link

Delete a flag link by ID or key.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FlagLinksBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FlagLinksBetaApi apiInstance = new FlagLinksBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    String id = "id_example"; // String | The flag link ID or Key
    try {
      apiInstance.deleteFlagLink(projectKey, featureFlagKey, id);
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagLinksBetaApi#deleteFlagLink");
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
| **featureFlagKey** | **String**| The feature flag key | |
| **id** | **String**| The flag link ID or Key | |

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
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getFlagLinks"></a>
# **getFlagLinks**
> FlagLinkCollectionRep getFlagLinks(projectKey, featureFlagKey)

List flag links

Get a list of all flag links.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FlagLinksBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FlagLinksBetaApi apiInstance = new FlagLinksBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    try {
      FlagLinkCollectionRep result = apiInstance.getFlagLinks(projectKey, featureFlagKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagLinksBetaApi#getFlagLinks");
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
| **featureFlagKey** | **String**| The feature flag key | |

### Return type

[**FlagLinkCollectionRep**](FlagLinkCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag link collection response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |

<a name="updateFlagLink"></a>
# **updateFlagLink**
> FlagLinkRep updateFlagLink(projectKey, featureFlagKey, id, patchOperation)

Update flag link

Update a flag link. The request body must be a valid JSON patch document. To learn more, read [Updates](/#section/Overview/Updates).

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FlagLinksBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FlagLinksBetaApi apiInstance = new FlagLinksBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    String id = "id_example"; // String | The flag link ID
    List<PatchOperation> patchOperation = Arrays.asList(); // List<PatchOperation> | 
    try {
      FlagLinkRep result = apiInstance.updateFlagLink(projectKey, featureFlagKey, id, patchOperation);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagLinksBetaApi#updateFlagLink");
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
| **featureFlagKey** | **String**| The feature flag key | |
| **id** | **String**| The flag link ID | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**FlagLinkRep**](FlagLinkRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag link response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

