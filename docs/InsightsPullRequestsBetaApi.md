# InsightsPullRequestsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getPullRequests**](InsightsPullRequestsBetaApi.md#getPullRequests) | **GET** /api/v2/engineering-insights/pull-requests | List pull requests |


<a name="getPullRequests"></a>
# **getPullRequests**
> PullRequestCollectionRep getPullRequests(projectKey, environmentKey, applicationKey, status, query, limit, expand, sort, from, to, after, before)

List pull requests

Get a list of pull requests  ### Expanding the pull request collection response  LaunchDarkly supports expanding the pull request collection response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;deployments&#x60; includes details on all of the deployments associated with each pull request * &#x60;flagReferences&#x60; includes details on all of the references to flags in each pull request * &#x60;leadTime&#x60; includes details about the lead time of the pull request for each stage  For example, use &#x60;?expand&#x3D;deployments&#x60; to include the &#x60;deployments&#x60; field in the response. By default, this field is **not** included in the response. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.InsightsPullRequestsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    InsightsPullRequestsBetaApi apiInstance = new InsightsPullRequestsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | Required if you are using the <code>sort</code> parameter's <code>leadTime</code> option to sort pull requests.
    String applicationKey = "applicationKey_example"; // String | Filter the results to pull requests deployed to a comma separated list of applications
    String status = "status_example"; // String | Filter results to pull requests with the given status. Options: `open`, `merged`, `closed`, `deployed`.
    String query = "query_example"; // String | Filter list of pull requests by title or author
    Long limit = 56L; // Long | The number of pull requests to return. Default is 20. Maximum allowed is 100.
    String expand = "expand_example"; // String | Expand properties in response. Options: `deployments`, `flagReferences`, `leadTime`.
    String sort = "sort_example"; // String | Sort results. Requires the `environmentKey` to be set. Options: `leadTime` (asc) and `-leadTime` (desc). When query option is excluded, default sort is by created or merged date.
    OffsetDateTime from = OffsetDateTime.now(); // OffsetDateTime | Unix timestamp in milliseconds. Default value is 7 days ago.
    OffsetDateTime to = OffsetDateTime.now(); // OffsetDateTime | Unix timestamp in milliseconds. Default value is now.
    String after = "after_example"; // String | Identifier used for pagination
    String before = "before_example"; // String | Identifier used for pagination
    try {
      PullRequestCollectionRep result = apiInstance.getPullRequests(projectKey, environmentKey, applicationKey, status, query, limit, expand, sort, from, to, after, before);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsPullRequestsBetaApi#getPullRequests");
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
| **environmentKey** | **String**| Required if you are using the &lt;code&gt;sort&lt;/code&gt; parameter&#39;s &lt;code&gt;leadTime&lt;/code&gt; option to sort pull requests. | [optional] |
| **applicationKey** | **String**| Filter the results to pull requests deployed to a comma separated list of applications | [optional] |
| **status** | **String**| Filter results to pull requests with the given status. Options: &#x60;open&#x60;, &#x60;merged&#x60;, &#x60;closed&#x60;, &#x60;deployed&#x60;. | [optional] |
| **query** | **String**| Filter list of pull requests by title or author | [optional] |
| **limit** | **Long**| The number of pull requests to return. Default is 20. Maximum allowed is 100. | [optional] |
| **expand** | **String**| Expand properties in response. Options: &#x60;deployments&#x60;, &#x60;flagReferences&#x60;, &#x60;leadTime&#x60;. | [optional] |
| **sort** | **String**| Sort results. Requires the &#x60;environmentKey&#x60; to be set. Options: &#x60;leadTime&#x60; (asc) and &#x60;-leadTime&#x60; (desc). When query option is excluded, default sort is by created or merged date. | [optional] |
| **from** | **OffsetDateTime**| Unix timestamp in milliseconds. Default value is 7 days ago. | [optional] |
| **to** | **OffsetDateTime**| Unix timestamp in milliseconds. Default value is now. | [optional] |
| **after** | **String**| Identifier used for pagination | [optional] |
| **before** | **String**| Identifier used for pagination | [optional] |

### Return type

[**PullRequestCollectionRep**](PullRequestCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Pull request collection response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

