# LayersApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createLayer**](LayersApi.md#createLayer) | **POST** /api/v2/projects/{projectKey}/layers | Create layer |
| [**getLayers**](LayersApi.md#getLayers) | **GET** /api/v2/projects/{projectKey}/layers | Get layers |
| [**updateLayer**](LayersApi.md#updateLayer) | **PATCH** /api/v2/projects/{projectKey}/layers/{layerKey} | Update layer |


<a name="createLayer"></a>
# **createLayer**
> LayerRep createLayer(projectKey, layerPost)

Create layer

Create a layer. Experiments running in the same layer are granted mutually-exclusive traffic. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.LayersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    LayersApi apiInstance = new LayersApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    LayerPost layerPost = new LayerPost(); // LayerPost | 
    try {
      LayerRep result = apiInstance.createLayer(projectKey, layerPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling LayersApi#createLayer");
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
| **layerPost** | [**LayerPost**](LayerPost.md)|  | |

### Return type

[**LayerRep**](LayerRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Layer response |  -  |
| **400** | Invalid request |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getLayers"></a>
# **getLayers**
> LayerCollectionRep getLayers(projectKey, filter)

Get layers

Get a collection of all layers for a project

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.LayersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    LayersApi apiInstance = new LayersApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String filter = "filter_example"; // String | A comma-separated list of filters. This endpoint only accepts filtering by `experimentKey`. The filter returns layers which include that experiment for the selected environment(s). For example: `filter=reservations.experimentKey contains expKey`.
    try {
      LayerCollectionRep result = apiInstance.getLayers(projectKey, filter);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling LayersApi#getLayers");
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
| **filter** | **String**| A comma-separated list of filters. This endpoint only accepts filtering by &#x60;experimentKey&#x60;. The filter returns layers which include that experiment for the selected environment(s). For example: &#x60;filter&#x3D;reservations.experimentKey contains expKey&#x60;. | [optional] |

### Return type

[**LayerCollectionRep**](LayerCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Layer Collection response |  -  |
| **400** | Invalid request |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="updateLayer"></a>
# **updateLayer**
> LayerRep updateLayer(projectKey, layerKey, layerPatchInput)

Update layer

Update a layer by adding, changing, or removing traffic reservations for experiments, or by changing layer name or description. Updating a layer uses the semantic patch format.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating layers.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating layers&lt;/strong&gt;&lt;/summary&gt;  #### updateName  Updates the layer name.  ##### Parameters  - &#x60;name&#x60;: The new layer name.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{       \&quot;kind\&quot;: \&quot;updateName\&quot;,       \&quot;name\&quot;: \&quot;New name\&quot;   }] } &#x60;&#x60;&#x60;  #### updateDescription  Updates the layer description.  ##### Parameters  - &#x60;description&#x60;: The new description.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{       \&quot;kind\&quot;: \&quot;updateDescription\&quot;,       \&quot;description\&quot;: \&quot;New description\&quot;   }] } &#x60;&#x60;&#x60;  #### updateExperimentReservation  Adds or updates a traffic reservation for an experiment in a layer.  ##### Parameters  - &#x60;experimentKey&#x60;: The key of the experiment whose reservation you are adding to or updating in the layer. - &#x60;reservationPercent&#x60;: The amount of traffic in the layer to reserve. Must be an integer. Zero is allowed until iteration start.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;production\&quot;,   \&quot;instructions\&quot;: [{       \&quot;kind\&quot;: \&quot;updateExperimentReservation\&quot;,       \&quot;experimentKey\&quot;: \&quot;exp-key\&quot;,       \&quot;reservationPercent\&quot;: 10   }] } &#x60;&#x60;&#x60;  #### removeExperiment  Removes a traffic reservation for an experiment from a layer.  ##### Parameters  - &#x60;experimentKey&#x60;: The key of the experiment whose reservation you want to remove from the layer.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;environmentKey\&quot;: \&quot;production\&quot;,   \&quot;instructions\&quot;: [{       \&quot;kind\&quot;: \&quot;removeExperiment\&quot;,       \&quot;experimentKey\&quot;: \&quot;exp-key\&quot;   }] } &#x60;&#x60;&#x60;  &lt;/details&gt; 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.LayersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    LayersApi apiInstance = new LayersApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String layerKey = "layerKey_example"; // String | The layer key
    LayerPatchInput layerPatchInput = new LayerPatchInput(); // LayerPatchInput | 
    try {
      LayerRep result = apiInstance.updateLayer(projectKey, layerKey, layerPatchInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling LayersApi#updateLayer");
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
| **layerKey** | **String**| The layer key | |
| **layerPatchInput** | [**LayerPatchInput**](LayerPatchInput.md)|  | |

### Return type

[**LayerRep**](LayerRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Layer response |  -  |
| **400** | Invalid request |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

