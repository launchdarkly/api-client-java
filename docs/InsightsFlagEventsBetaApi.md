# InsightsFlagEventsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getFlagEvents**](InsightsFlagEventsBetaApi.md#getFlagEvents) | **GET** /api/v2/engineering-insights/flag-events | List flag events |


<a name="getFlagEvents"></a>
# **getFlagEvents**
> FlagEventCollectionRep getFlagEvents(projectKey, environmentKey, applicationKey, query, impactSize, hasExperiments, global, expand, limit, from, to, after, before)

List flag events

Get a list of flag events  ### Expanding the flag event collection response  LaunchDarkly supports expanding the flag event collection response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;experiments&#x60; includes details on all of the experiments run on each flag  For example, use &#x60;?expand&#x3D;experiments&#x60; to include the &#x60;experiments&#x60; field in the response. By default, this field is **not** included in the response. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.InsightsFlagEventsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    InsightsFlagEventsBetaApi apiInstance = new InsightsFlagEventsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String applicationKey = "applicationKey_example"; // String | Comma separated list of application keys
    String query = "query_example"; // String | Filter events by flag key
    String impactSize = "impactSize_example"; // String | Filter events by impact size. A small impact created a less than 20% change in the proportion of end users receiving one or more flag variations. A medium impact created between a 20%-80% change. A large impact created a more than 80% change. Options: `none`, `small`, `medium`, `large`
    Boolean hasExperiments = true; // Boolean | Filter events to those associated with an experiment
    String global = "global_example"; // String | Filter to include or exclude global events. Default value is `include`. Options: `include`, `exclude`
    String expand = "expand_example"; // String | Expand properties in response. Options: `experiments`
    Long limit = 56L; // Long | The number of deployments to return. Default is 20. Maximum allowed is 100.
    Long from = 56L; // Long | Unix timestamp in milliseconds. Default value is 7 days ago.
    Long to = 56L; // Long | Unix timestamp in milliseconds. Default value is now.
    String after = "after_example"; // String | Identifier used for pagination
    String before = "before_example"; // String | Identifier used for pagination
    try {
      FlagEventCollectionRep result = apiInstance.getFlagEvents(projectKey, environmentKey, applicationKey, query, impactSize, hasExperiments, global, expand, limit, from, to, after, before);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsFlagEventsBetaApi#getFlagEvents");
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
| **query** | **String**| Filter events by flag key | [optional] |
| **impactSize** | **String**| Filter events by impact size. A small impact created a less than 20% change in the proportion of end users receiving one or more flag variations. A medium impact created between a 20%-80% change. A large impact created a more than 80% change. Options: &#x60;none&#x60;, &#x60;small&#x60;, &#x60;medium&#x60;, &#x60;large&#x60; | [optional] |
| **hasExperiments** | **Boolean**| Filter events to those associated with an experiment | [optional] |
| **global** | **String**| Filter to include or exclude global events. Default value is &#x60;include&#x60;. Options: &#x60;include&#x60;, &#x60;exclude&#x60; | [optional] |
| **expand** | **String**| Expand properties in response. Options: &#x60;experiments&#x60; | [optional] |
| **limit** | **Long**| The number of deployments to return. Default is 20. Maximum allowed is 100. | [optional] |
| **from** | **Long**| Unix timestamp in milliseconds. Default value is 7 days ago. | [optional] |
| **to** | **Long**| Unix timestamp in milliseconds. Default value is now. | [optional] |
| **after** | **String**| Identifier used for pagination | [optional] |
| **before** | **String**| Identifier used for pagination | [optional] |

### Return type

[**FlagEventCollectionRep**](FlagEventCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag event collection response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

