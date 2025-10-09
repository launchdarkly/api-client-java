# ViewsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createView**](ViewsBetaApi.md#createView) | **POST** /api/v2/projects/{projectKey}/views | Create view |
| [**deleteView**](ViewsBetaApi.md#deleteView) | **DELETE** /api/v2/projects/{projectKey}/views/{viewKey} | Delete view |
| [**getLinkedResources**](ViewsBetaApi.md#getLinkedResources) | **GET** /api/v2/projects/{projectKey}/views/{viewKey}/linked/{resourceType} | Get linked resources |
| [**getLinkedViews**](ViewsBetaApi.md#getLinkedViews) | **GET** /api/v2/projects/{projectKey}/view-associations/{resourceType}/{resourceKey} | Get linked views for a given resource |
| [**getView**](ViewsBetaApi.md#getView) | **GET** /api/v2/projects/{projectKey}/views/{viewKey} | Get view |
| [**getViews**](ViewsBetaApi.md#getViews) | **GET** /api/v2/projects/{projectKey}/views | List views |
| [**linkResource**](ViewsBetaApi.md#linkResource) | **POST** /api/v2/projects/{projectKey}/views/{viewKey}/link/{resourceType} | Link resource |
| [**unlinkResource**](ViewsBetaApi.md#unlinkResource) | **DELETE** /api/v2/projects/{projectKey}/views/{viewKey}/link/{resourceType} | Unlink resource |
| [**updateView**](ViewsBetaApi.md#updateView) | **PATCH** /api/v2/projects/{projectKey}/views/{viewKey} | Update view |


<a id="createView"></a>
# **createView**
> View createView(ldAPIVersion, projectKey, viewPost)

Create view

Create a new view in the given project.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ViewsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ViewsBetaApi apiInstance = new ViewsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | 
    ViewPost viewPost = new ViewPost(); // ViewPost | View object to create
    try {
      View result = apiInstance.createView(ldAPIVersion, projectKey, viewPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ViewsBetaApi#createView");
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
| **projectKey** | **String**|  | |
| **viewPost** | [**ViewPost**](ViewPost.md)| View object to create | |

### Return type

[**View**](View.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful response |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="deleteView"></a>
# **deleteView**
> deleteView(ldAPIVersion, projectKey, viewKey)

Delete view

Delete a specific view by its key.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ViewsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ViewsBetaApi apiInstance = new ViewsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | 
    String viewKey = "my-view"; // String | 
    try {
      apiInstance.deleteView(ldAPIVersion, projectKey, viewKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling ViewsBetaApi#deleteView");
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
| **projectKey** | **String**|  | |
| **viewKey** | **String**|  | |

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
| **204** | No content |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="getLinkedResources"></a>
# **getLinkedResources**
> ViewLinkedResources getLinkedResources(ldAPIVersion, projectKey, viewKey, resourceType, limit, offset, sort)

Get linked resources

Get a list of all linked resources for a given view.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ViewsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ViewsBetaApi apiInstance = new ViewsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | 
    String viewKey = "my-view"; // String | 
    String resourceType = "flags"; // String | 
    Integer limit = 56; // Integer | The number of views to return.
    Integer offset = 56; // Integer | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    String sort = "linkedAt"; // String | Field to sort by. Default field is `linkedAt`, default order is ascending.
    try {
      ViewLinkedResources result = apiInstance.getLinkedResources(ldAPIVersion, projectKey, viewKey, resourceType, limit, offset, sort);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ViewsBetaApi#getLinkedResources");
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
| **projectKey** | **String**|  | |
| **viewKey** | **String**|  | |
| **resourceType** | **String**|  | [enum: flags, segments, aiConfigs, metrics] |
| **limit** | **Integer**| The number of views to return. | [optional] |
| **offset** | **Integer**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |
| **sort** | **String**| Field to sort by. Default field is &#x60;linkedAt&#x60;, default order is ascending. | [optional] [default to linkedAt] [enum: linkedAt, name] |

### Return type

[**ViewLinkedResources**](ViewLinkedResources.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful response |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="getLinkedViews"></a>
# **getLinkedViews**
> Views getLinkedViews(ldAPIVersion, projectKey, resourceType, resourceKey, environmentId, limit, offset)

Get linked views for a given resource

Get a list of all linked views for a resource. Flags, AI configs and metrics are identified by key. Segments are identified by segment ID.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ViewsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ViewsBetaApi apiInstance = new ViewsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | 
    String resourceType = "flags"; // String | 
    String resourceKey = "my-flag"; // String | 
    String environmentId = "6890ff25c3e3830ba1a352e4"; // String | Environment ID. Required when resourceType is 'segments'
    Integer limit = 56; // Integer | The number of views to return.
    Integer offset = 56; // Integer | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    try {
      Views result = apiInstance.getLinkedViews(ldAPIVersion, projectKey, resourceType, resourceKey, environmentId, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ViewsBetaApi#getLinkedViews");
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
| **projectKey** | **String**|  | |
| **resourceType** | **String**|  | [enum: flags, segments, aiConfigs, metrics] |
| **resourceKey** | **String**|  | |
| **environmentId** | **String**| Environment ID. Required when resourceType is &#39;segments&#39; | [optional] |
| **limit** | **Integer**| The number of views to return. | [optional] |
| **offset** | **Integer**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |

### Return type

[**Views**](Views.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful response |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="getView"></a>
# **getView**
> View getView(ldAPIVersion, projectKey, viewKey, sort, limit, offset, filter, expand)

Get view

Retrieve a specific view by its key.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ViewsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ViewsBetaApi apiInstance = new ViewsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | 
    String viewKey = "my-view"; // String | 
    String sort = "key"; // String | A sort to apply to the list of views.
    Integer limit = 56; // Integer | The number of views to return.
    Integer offset = 56; // Integer | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    String filter = "filter_example"; // String | A filter to apply to the list of views.
    List<String> expand = Arrays.asList(); // List<String> | A comma-separated list of fields to expand.
    try {
      View result = apiInstance.getView(ldAPIVersion, projectKey, viewKey, sort, limit, offset, filter, expand);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ViewsBetaApi#getView");
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
| **projectKey** | **String**|  | |
| **viewKey** | **String**|  | |
| **sort** | **String**| A sort to apply to the list of views. | [optional] [enum: key, name, updatedAt] |
| **limit** | **Integer**| The number of views to return. | [optional] |
| **offset** | **Integer**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |
| **filter** | **String**| A filter to apply to the list of views. | [optional] |
| **expand** | [**List&lt;String&gt;**](String.md)| A comma-separated list of fields to expand. | [optional] [enum: allFlags, allSegments, allMetrics, allAIConfigs, allResources, flagsSummary, segmentsSummary, metricsSummary, aiConfigsSummary, resourceSummary] |

### Return type

[**View**](View.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful response |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="getViews"></a>
# **getViews**
> Views getViews(ldAPIVersion, projectKey, sort, limit, offset, filter, expand)

List views

Get a list of all views in the given project.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ViewsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ViewsBetaApi apiInstance = new ViewsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | 
    String sort = "key"; // String | A sort to apply to the list of views.
    Integer limit = 56; // Integer | The number of views to return.
    Integer offset = 56; // Integer | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    String filter = "filter_example"; // String | A filter to apply to the list of views.
    List<String> expand = Arrays.asList(); // List<String> | A comma-separated list of fields to expand.
    try {
      Views result = apiInstance.getViews(ldAPIVersion, projectKey, sort, limit, offset, filter, expand);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ViewsBetaApi#getViews");
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
| **projectKey** | **String**|  | |
| **sort** | **String**| A sort to apply to the list of views. | [optional] [enum: key, name, updatedAt] |
| **limit** | **Integer**| The number of views to return. | [optional] |
| **offset** | **Integer**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |
| **filter** | **String**| A filter to apply to the list of views. | [optional] |
| **expand** | [**List&lt;String&gt;**](String.md)| A comma-separated list of fields to expand. | [optional] [enum: flagsSummary, segmentsSummary, metricsSummary, aiConfigsSummary, resourceSummary] |

### Return type

[**Views**](Views.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful response |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="linkResource"></a>
# **linkResource**
> LinkResourceSuccessResponse linkResource(ldAPIVersion, projectKey, viewKey, resourceType, viewLinkRequest)

Link resource

Link one or multiple resources to a view: - Link flags using flag keys - Link AI configs using AI config keys - Link metrics using metric keys - Link segments using segment IDs 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ViewsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ViewsBetaApi apiInstance = new ViewsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | 
    String viewKey = "my-view"; // String | 
    String resourceType = "flags"; // String | 
    ViewLinkRequest viewLinkRequest = new ViewLinkRequest(); // ViewLinkRequest | The resource to link to the view. Flags are identified by key. Segments are identified by segment ID.
    try {
      LinkResourceSuccessResponse result = apiInstance.linkResource(ldAPIVersion, projectKey, viewKey, resourceType, viewLinkRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ViewsBetaApi#linkResource");
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
| **projectKey** | **String**|  | |
| **viewKey** | **String**|  | |
| **resourceType** | **String**|  | [enum: flags, segments, aiConfigs, metrics] |
| **viewLinkRequest** | [**ViewLinkRequest**](ViewLinkRequest.md)| The resource to link to the view. Flags are identified by key. Segments are identified by segment ID. | |

### Return type

[**LinkResourceSuccessResponse**](LinkResourceSuccessResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful response |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="unlinkResource"></a>
# **unlinkResource**
> UnlinkResourceSuccessResponse unlinkResource(ldAPIVersion, projectKey, viewKey, resourceType, viewLinkRequest)

Unlink resource

Unlink one or multiple resources from a view: - Unlink flags using flag keys - Unlink segments using segment IDs - Unlink AI configs using AI config keys - Unlink metrics using metric keys 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ViewsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ViewsBetaApi apiInstance = new ViewsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | 
    String viewKey = "my-view"; // String | 
    String resourceType = "flags"; // String | 
    ViewLinkRequest viewLinkRequest = new ViewLinkRequest(); // ViewLinkRequest | The resource to link to the view. Flags are identified by key. Segments are identified by segment ID.
    try {
      UnlinkResourceSuccessResponse result = apiInstance.unlinkResource(ldAPIVersion, projectKey, viewKey, resourceType, viewLinkRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ViewsBetaApi#unlinkResource");
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
| **projectKey** | **String**|  | |
| **viewKey** | **String**|  | |
| **resourceType** | **String**|  | [enum: flags, segments, aiConfigs, metrics] |
| **viewLinkRequest** | [**ViewLinkRequest**](ViewLinkRequest.md)| The resource to link to the view. Flags are identified by key. Segments are identified by segment ID. | |

### Return type

[**UnlinkResourceSuccessResponse**](UnlinkResourceSuccessResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful response with unlink details |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

<a id="updateView"></a>
# **updateView**
> View updateView(ldAPIVersion, projectKey, viewKey, viewPatch)

Update view

Edit an existing view.  The request body must be a JSON object of the fields to update. The values you include replace the existing values for the fields.  Here&#39;s an example:   &#x60;&#x60;&#x60;     {       \&quot;description\&quot;: \&quot;Example updated description\&quot;,       \&quot;tags\&quot;: [\&quot;new-tag\&quot;]     }   &#x60;&#x60;&#x60; 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ViewsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ViewsBetaApi apiInstance = new ViewsBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "default"; // String | 
    String viewKey = "my-view"; // String | 
    ViewPatch viewPatch = new ViewPatch(); // ViewPatch | A JSON representation of the view including only the fields to update.
    try {
      View result = apiInstance.updateView(ldAPIVersion, projectKey, viewKey, viewPatch);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ViewsBetaApi#updateView");
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
| **projectKey** | **String**|  | |
| **viewKey** | **String**|  | |
| **viewPatch** | [**ViewPatch**](ViewPatch.md)| A JSON representation of the view including only the fields to update. | |

### Return type

[**View**](View.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful response |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **500** | Internal server error |  -  |

