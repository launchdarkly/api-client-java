# InsightsRepositoriesBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**associateRepositoriesAndProjects**](InsightsRepositoriesBetaApi.md#associateRepositoriesAndProjects) | **PUT** /api/v2/engineering-insights/repositories/projects | Associate repositories with projects |
| [**deleteRepositoryProject**](InsightsRepositoriesBetaApi.md#deleteRepositoryProject) | **DELETE** /api/v2/engineering-insights/repositories/{repositoryKey}/projects/{projectKey} | Remove repository project association |
| [**getInsightsRepositories**](InsightsRepositoriesBetaApi.md#getInsightsRepositories) | **GET** /api/v2/engineering-insights/repositories | List repositories |


<a id="associateRepositoriesAndProjects"></a>
# **associateRepositoriesAndProjects**
> InsightsRepositoryProjectCollection associateRepositoriesAndProjects(insightsRepositoryProjectMappings)

Associate repositories with projects

Associate repositories with projects

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.InsightsRepositoriesBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    InsightsRepositoriesBetaApi apiInstance = new InsightsRepositoriesBetaApi(defaultClient);
    InsightsRepositoryProjectMappings insightsRepositoryProjectMappings = new InsightsRepositoryProjectMappings(); // InsightsRepositoryProjectMappings | 
    try {
      InsightsRepositoryProjectCollection result = apiInstance.associateRepositoriesAndProjects(insightsRepositoryProjectMappings);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsRepositoriesBetaApi#associateRepositoriesAndProjects");
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
| **insightsRepositoryProjectMappings** | [**InsightsRepositoryProjectMappings**](InsightsRepositoryProjectMappings.md)|  | |

### Return type

[**InsightsRepositoryProjectCollection**](InsightsRepositoryProjectCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Repositories projects response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="deleteRepositoryProject"></a>
# **deleteRepositoryProject**
> deleteRepositoryProject(repositoryKey, projectKey)

Remove repository project association

Remove repository project association

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.InsightsRepositoriesBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    InsightsRepositoriesBetaApi apiInstance = new InsightsRepositoriesBetaApi(defaultClient);
    String repositoryKey = "repositoryKey_example"; // String | The repository key
    String projectKey = "projectKey_example"; // String | The project key
    try {
      apiInstance.deleteRepositoryProject(repositoryKey, projectKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsRepositoriesBetaApi#deleteRepositoryProject");
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
| **repositoryKey** | **String**| The repository key | |
| **projectKey** | **String**| The project key | |

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

<a id="getInsightsRepositories"></a>
# **getInsightsRepositories**
> InsightsRepositoryCollection getInsightsRepositories(expand)

List repositories

Get a list of repositories  ### Expanding the repository collection response  LaunchDarkly supports expanding the repository collection response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;projects&#x60; includes details on all of the LaunchDarkly projects associated with each repository  For example, use &#x60;?expand&#x3D;projects&#x60; to include the &#x60;projects&#x60; field in the response. By default, this field is **not** included in the response. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.InsightsRepositoriesBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    InsightsRepositoriesBetaApi apiInstance = new InsightsRepositoriesBetaApi(defaultClient);
    String expand = "expand_example"; // String | Expand properties in response. Options: `projects`
    try {
      InsightsRepositoryCollection result = apiInstance.getInsightsRepositories(expand);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsRepositoriesBetaApi#getInsightsRepositories");
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
| **expand** | **String**| Expand properties in response. Options: &#x60;projects&#x60; | [optional] |

### Return type

[**InsightsRepositoryCollection**](InsightsRepositoryCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Repository collection response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

