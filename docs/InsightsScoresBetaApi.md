# InsightsScoresBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createInsightGroup**](InsightsScoresBetaApi.md#createInsightGroup) | **POST** /api/v2/engineering-insights/insights/group | Create insight group |
| [**deleteInsightGroup**](InsightsScoresBetaApi.md#deleteInsightGroup) | **DELETE** /api/v2/engineering-insights/insights/groups/{insightGroupKey} | Delete insight group |
| [**getInsightGroup**](InsightsScoresBetaApi.md#getInsightGroup) | **GET** /api/v2/engineering-insights/insights/groups/{insightGroupKey} | Get insight group |
| [**getInsightGroups**](InsightsScoresBetaApi.md#getInsightGroups) | **GET** /api/v2/engineering-insights/insights/groups | List insight groups |
| [**getInsightsScores**](InsightsScoresBetaApi.md#getInsightsScores) | **GET** /api/v2/engineering-insights/insights/scores | Get insight scores |
| [**patchInsightGroup**](InsightsScoresBetaApi.md#patchInsightGroup) | **PATCH** /api/v2/engineering-insights/insights/groups/{insightGroupKey} | Patch insight group |


<a id="createInsightGroup"></a>
# **createInsightGroup**
> InsightGroup createInsightGroup(postInsightGroupParams)

Create insight group

Create insight group

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.InsightsScoresBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    InsightsScoresBetaApi apiInstance = new InsightsScoresBetaApi(defaultClient);
    PostInsightGroupParams postInsightGroupParams = new PostInsightGroupParams(); // PostInsightGroupParams | 
    try {
      InsightGroup result = apiInstance.createInsightGroup(postInsightGroupParams);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsScoresBetaApi#createInsightGroup");
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
| **postInsightGroupParams** | [**PostInsightGroupParams**](PostInsightGroupParams.md)|  | |

### Return type

[**InsightGroup**](InsightGroup.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Created |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a id="deleteInsightGroup"></a>
# **deleteInsightGroup**
> deleteInsightGroup(insightGroupKey)

Delete insight group

Delete insight group

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.InsightsScoresBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    InsightsScoresBetaApi apiInstance = new InsightsScoresBetaApi(defaultClient);
    String insightGroupKey = "insightGroupKey_example"; // String | The insight group key
    try {
      apiInstance.deleteInsightGroup(insightGroupKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsScoresBetaApi#deleteInsightGroup");
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
| **insightGroupKey** | **String**| The insight group key | |

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

<a id="getInsightGroup"></a>
# **getInsightGroup**
> InsightGroup getInsightGroup(insightGroupKey, expand)

Get insight group

Get insight group  ### Expanding the insight group response  LaunchDarkly supports expanding the insight group response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;scores&#x60; includes details on all of the scores used in the engineering insights metrics views for this group * &#x60;environment&#x60; includes details on each environment associated with this group  For example, use &#x60;?expand&#x3D;scores&#x60; to include the &#x60;scores&#x60; field in the response. By default, this field is **not** included in the response. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.InsightsScoresBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    InsightsScoresBetaApi apiInstance = new InsightsScoresBetaApi(defaultClient);
    String insightGroupKey = "insightGroupKey_example"; // String | The insight group key
    String expand = "expand_example"; // String | Options: `scores`, `environment`
    try {
      InsightGroup result = apiInstance.getInsightGroup(insightGroupKey, expand);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsScoresBetaApi#getInsightGroup");
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
| **insightGroupKey** | **String**| The insight group key | |
| **expand** | **String**| Options: &#x60;scores&#x60;, &#x60;environment&#x60; | [optional] |

### Return type

[**InsightGroup**](InsightGroup.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Insight group response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="getInsightGroups"></a>
# **getInsightGroups**
> InsightGroupCollection getInsightGroups(limit, offset, sort, query, expand)

List insight groups

List groups for which you are collecting insights  ### Expanding the insight groups collection response  LaunchDarkly supports expanding the insight groups collection response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;scores&#x60; includes details on all of the scores used in the engineering insights metrics views for each group * &#x60;environment&#x60; includes details on each environment associated with each group * &#x60;metadata&#x60; includes counts of the number of insight groups with particular indicators, such as \&quot;excellent,\&quot; \&quot;good,\&quot; \&quot;fair,\&quot; and so on.  For example, use &#x60;?expand&#x3D;scores&#x60; to include the &#x60;scores&#x60; field in the response. By default, this field is **not** included in the response. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.InsightsScoresBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    InsightsScoresBetaApi apiInstance = new InsightsScoresBetaApi(defaultClient);
    Long limit = 56L; // Long | The number of insight groups to return. Default is 20. Must be between 1 and 20 inclusive.
    Long offset = 56L; // Long | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    String sort = "sort_example"; // String | Sort flag list by field. Prefix field with <code>-</code> to sort in descending order. Allowed fields: name
    String query = "query_example"; // String | Filter list of insights groups by name.
    String expand = "expand_example"; // String | Options: `scores`, `environment`, `metadata`
    try {
      InsightGroupCollection result = apiInstance.getInsightGroups(limit, offset, sort, query, expand);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsScoresBetaApi#getInsightGroups");
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
| **limit** | **Long**| The number of insight groups to return. Default is 20. Must be between 1 and 20 inclusive. | [optional] |
| **offset** | **Long**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |
| **sort** | **String**| Sort flag list by field. Prefix field with &lt;code&gt;-&lt;/code&gt; to sort in descending order. Allowed fields: name | [optional] |
| **query** | **String**| Filter list of insights groups by name. | [optional] |
| **expand** | **String**| Options: &#x60;scores&#x60;, &#x60;environment&#x60;, &#x60;metadata&#x60; | [optional] |

### Return type

[**InsightGroupCollection**](InsightGroupCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Insight groups collection response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |

<a id="getInsightsScores"></a>
# **getInsightsScores**
> InsightScores getInsightsScores(projectKey, environmentKey, applicationKey)

Get insight scores

Return insights scores, based on the given parameters. This data is also used in engineering insights metrics views.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.InsightsScoresBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    InsightsScoresBetaApi apiInstance = new InsightsScoresBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String applicationKey = "applicationKey_example"; // String | Comma separated list of application keys
    try {
      InsightScores result = apiInstance.getInsightsScores(projectKey, environmentKey, applicationKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsScoresBetaApi#getInsightsScores");
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
| **applicationKey** | **String**| Comma separated list of application keys | [optional] |

### Return type

[**InsightScores**](InsightScores.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Insight score response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |

<a id="patchInsightGroup"></a>
# **patchInsightGroup**
> InsightGroup patchInsightGroup(insightGroupKey, patchOperation)

Patch insight group

Update an insight group. Updating an insight group uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](https://launchdarkly.com/docs/api#updates).

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.InsightsScoresBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    InsightsScoresBetaApi apiInstance = new InsightsScoresBetaApi(defaultClient);
    String insightGroupKey = "insightGroupKey_example"; // String | The insight group key
    List<PatchOperation> patchOperation = Arrays.asList(); // List<PatchOperation> | 
    try {
      InsightGroup result = apiInstance.patchInsightGroup(insightGroupKey, patchOperation);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsScoresBetaApi#patchInsightGroup");
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
| **insightGroupKey** | **String**| The insight group key | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**InsightGroup**](InsightGroup.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Insight group response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **422** | Invalid patch content |  -  |
| **429** | Rate limited |  -  |

