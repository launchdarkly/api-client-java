# InsightsDeploymentsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createDeploymentEvent**](InsightsDeploymentsBetaApi.md#createDeploymentEvent) | **POST** /api/v2/engineering-insights/deployment-events | Create deployment event |
| [**getDeployment**](InsightsDeploymentsBetaApi.md#getDeployment) | **GET** /api/v2/engineering-insights/deployments/{deploymentID} | Get deployment |
| [**getDeployments**](InsightsDeploymentsBetaApi.md#getDeployments) | **GET** /api/v2/engineering-insights/deployments | List deployments |
| [**updateDeployment**](InsightsDeploymentsBetaApi.md#updateDeployment) | **PATCH** /api/v2/engineering-insights/deployments/{deploymentID} | Update deployment |


<a id="createDeploymentEvent"></a>
# **createDeploymentEvent**
> createDeploymentEvent(postDeploymentEventInput)

Create deployment event

Create deployment event

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.InsightsDeploymentsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    InsightsDeploymentsBetaApi apiInstance = new InsightsDeploymentsBetaApi(defaultClient);
    PostDeploymentEventInput postDeploymentEventInput = new PostDeploymentEventInput(); // PostDeploymentEventInput | 
    try {
      apiInstance.createDeploymentEvent(postDeploymentEventInput);
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsDeploymentsBetaApi#createDeploymentEvent");
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
| **postDeploymentEventInput** | [**PostDeploymentEventInput**](PostDeploymentEventInput.md)|  | |

### Return type

null (empty response body)

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
| **429** | Rate limited |  -  |

<a id="getDeployment"></a>
# **getDeployment**
> DeploymentRep getDeployment(deploymentID, expand)

Get deployment

Get a deployment by ID.  The deployment ID is returned as part of the [List deployments](https://launchdarkly.com/docs/api/insights-deployments-beta/get-deployments) response. It is the &#x60;id&#x60; field of each element in the &#x60;items&#x60; array.  ### Expanding the deployment response  LaunchDarkly supports expanding the deployment response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;pullRequests&#x60; includes details on all of the pull requests associated with each deployment * &#x60;flagReferences&#x60; includes details on all of the references to flags in each deployment  For example, use &#x60;?expand&#x3D;pullRequests&#x60; to include the &#x60;pullRequests&#x60; field in the response. By default, this field is **not** included in the response. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.InsightsDeploymentsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    InsightsDeploymentsBetaApi apiInstance = new InsightsDeploymentsBetaApi(defaultClient);
    String deploymentID = "deploymentID_example"; // String | The deployment ID
    String expand = "expand_example"; // String | Expand properties in response. Options: `pullRequests`, `flagReferences`
    try {
      DeploymentRep result = apiInstance.getDeployment(deploymentID, expand);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsDeploymentsBetaApi#getDeployment");
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
| **deploymentID** | **String**| The deployment ID | |
| **expand** | **String**| Expand properties in response. Options: &#x60;pullRequests&#x60;, &#x60;flagReferences&#x60; | [optional] |

### Return type

[**DeploymentRep**](DeploymentRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Deployment response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="getDeployments"></a>
# **getDeployments**
> DeploymentCollectionRep getDeployments(projectKey, environmentKey, applicationKey, limit, expand, from, to, after, before, kind, status)

List deployments

Get a list of deployments  ### Expanding the deployment collection response  LaunchDarkly supports expanding the deployment collection response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;pullRequests&#x60; includes details on all of the pull requests associated with each deployment * &#x60;flagReferences&#x60; includes details on all of the references to flags in each deployment  For example, use &#x60;?expand&#x3D;pullRequests&#x60; to include the &#x60;pullRequests&#x60; field in the response. By default, this field is **not** included in the response. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.InsightsDeploymentsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    InsightsDeploymentsBetaApi apiInstance = new InsightsDeploymentsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String applicationKey = "applicationKey_example"; // String | Comma separated list of application keys
    Long limit = 56L; // Long | The number of deployments to return. Default is 20. Maximum allowed is 100.
    String expand = "expand_example"; // String | Expand properties in response. Options: `pullRequests`, `flagReferences`
    Long from = 56L; // Long | Unix timestamp in milliseconds. Default value is 7 days ago.
    Long to = 56L; // Long | Unix timestamp in milliseconds. Default value is now.
    String after = "after_example"; // String | Identifier used for pagination
    String before = "before_example"; // String | Identifier used for pagination
    String kind = "kind_example"; // String | The deployment kind
    String status = "status_example"; // String | The deployment status
    try {
      DeploymentCollectionRep result = apiInstance.getDeployments(projectKey, environmentKey, applicationKey, limit, expand, from, to, after, before, kind, status);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsDeploymentsBetaApi#getDeployments");
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
| **limit** | **Long**| The number of deployments to return. Default is 20. Maximum allowed is 100. | [optional] |
| **expand** | **String**| Expand properties in response. Options: &#x60;pullRequests&#x60;, &#x60;flagReferences&#x60; | [optional] |
| **from** | **Long**| Unix timestamp in milliseconds. Default value is 7 days ago. | [optional] |
| **to** | **Long**| Unix timestamp in milliseconds. Default value is now. | [optional] |
| **after** | **String**| Identifier used for pagination | [optional] |
| **before** | **String**| Identifier used for pagination | [optional] |
| **kind** | **String**| The deployment kind | [optional] |
| **status** | **String**| The deployment status | [optional] |

### Return type

[**DeploymentCollectionRep**](DeploymentCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Deployment collection response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="updateDeployment"></a>
# **updateDeployment**
> DeploymentRep updateDeployment(deploymentID, patchOperation)

Update deployment

Update a deployment by ID. Updating a deployment uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](https://launchdarkly.com/docs/api#updates).&lt;br/&gt;&lt;br/&gt;The deployment ID is returned as part of the [List deployments](https://launchdarkly.com/docs/api/insights-deployments-beta/get-deployments) response. It is the &#x60;id&#x60; field of each element in the &#x60;items&#x60; array.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.InsightsDeploymentsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    InsightsDeploymentsBetaApi apiInstance = new InsightsDeploymentsBetaApi(defaultClient);
    String deploymentID = "deploymentID_example"; // String | The deployment ID
    List<PatchOperation> patchOperation = Arrays.asList(); // List<PatchOperation> | 
    try {
      DeploymentRep result = apiInstance.updateDeployment(deploymentID, patchOperation);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsDeploymentsBetaApi#updateDeployment");
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
| **deploymentID** | **String**| The deployment ID | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**DeploymentRep**](DeploymentRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Deployment response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

