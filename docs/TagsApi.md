# TagsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getTags**](TagsApi.md#getTags) | **GET** /api/v2/tags | List tags |


<a name="getTags"></a>
# **getTags**
> TagsCollection getTags(kind, pre, archived, limit, offset, asOf)

List tags

Get a list of tags.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.TagsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    TagsApi apiInstance = new TagsApi(defaultClient);
    List<String> kind = Arrays.asList(); // List<String> | Fetch tags associated with the specified resource type. Options are `flag`, `project`, `environment`, `segment`, `metric`. Returns all types by default.
    String pre = "pre_example"; // String | Return tags with the specified prefix
    Boolean archived = true; // Boolean | Whether or not to return archived flags
    Integer limit = 56; // Integer | The number of tags to return. Maximum is 1000.
    Integer offset = 56; // Integer | The index of the first tag to return. Default is 0.
    String asOf = "asOf_example"; // String | The time to retrieve tags as of. Default is the current time.
    try {
      TagsCollection result = apiInstance.getTags(kind, pre, archived, limit, offset, asOf);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TagsApi#getTags");
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
| **kind** | [**List&lt;String&gt;**](String.md)| Fetch tags associated with the specified resource type. Options are &#x60;flag&#x60;, &#x60;project&#x60;, &#x60;environment&#x60;, &#x60;segment&#x60;, &#x60;metric&#x60;. Returns all types by default. | [optional] |
| **pre** | **String**| Return tags with the specified prefix | [optional] |
| **archived** | **Boolean**| Whether or not to return archived flags | [optional] |
| **limit** | **Integer**| The number of tags to return. Maximum is 1000. | [optional] |
| **offset** | **Integer**| The index of the first tag to return. Default is 0. | [optional] |
| **asOf** | **String**| The time to retrieve tags as of. Default is the current time. | [optional] |

### Return type

[**TagsCollection**](TagsCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Tag collection response |  -  |
| **400** | Bad request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate Limited |  -  |
| **500** | Internal server error |  -  |

