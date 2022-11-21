# WorkflowTemplatesBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createWorkflowTemplate**](WorkflowTemplatesBetaApi.md#createWorkflowTemplate) | **POST** /api/v2/templates | Create workflow template |
| [**deleteWorkflowTemplate**](WorkflowTemplatesBetaApi.md#deleteWorkflowTemplate) | **DELETE** /api/v2/templates/{templateKey} | Delete workflow template |
| [**getWorkflowTemplates**](WorkflowTemplatesBetaApi.md#getWorkflowTemplates) | **GET** /api/v2/templates | Get workflow templates |


<a name="createWorkflowTemplate"></a>
# **createWorkflowTemplate**
> WorkflowTemplateOutput createWorkflowTemplate(createWorkflowTemplateInput)

Create workflow template

Create a template for a feature flag workflow

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.WorkflowTemplatesBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    WorkflowTemplatesBetaApi apiInstance = new WorkflowTemplatesBetaApi(defaultClient);
    CreateWorkflowTemplateInput createWorkflowTemplateInput = new CreateWorkflowTemplateInput(); // CreateWorkflowTemplateInput | 
    try {
      WorkflowTemplateOutput result = apiInstance.createWorkflowTemplate(createWorkflowTemplateInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling WorkflowTemplatesBetaApi#createWorkflowTemplate");
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
| **createWorkflowTemplateInput** | [**CreateWorkflowTemplateInput**](CreateWorkflowTemplateInput.md)|  | |

### Return type

[**WorkflowTemplateOutput**](WorkflowTemplateOutput.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Workflow template response JSON |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |

<a name="deleteWorkflowTemplate"></a>
# **deleteWorkflowTemplate**
> deleteWorkflowTemplate(templateKey)

Delete workflow template

Delete a workflow template

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.WorkflowTemplatesBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    WorkflowTemplatesBetaApi apiInstance = new WorkflowTemplatesBetaApi(defaultClient);
    String templateKey = "templateKey_example"; // String | The template key
    try {
      apiInstance.deleteWorkflowTemplate(templateKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling WorkflowTemplatesBetaApi#deleteWorkflowTemplate");
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
| **templateKey** | **String**| The template key | |

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
| **204** | Action completed successfully |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getWorkflowTemplates"></a>
# **getWorkflowTemplates**
> WorkflowTemplatesListingOutputRep getWorkflowTemplates(search)

Get workflow templates

Get workflow templates belonging to an account, or can optionally return templates_endpoints.workflowTemplateSummariesListingOutputRep when summary query param is true

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.WorkflowTemplatesBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    WorkflowTemplatesBetaApi apiInstance = new WorkflowTemplatesBetaApi(defaultClient);
    String search = "search_example"; // String | The substring in either the name or description of a template
    try {
      WorkflowTemplatesListingOutputRep result = apiInstance.getWorkflowTemplates(search);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling WorkflowTemplatesBetaApi#getWorkflowTemplates");
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
| **search** | **String**| The substring in either the name or description of a template | [optional] |

### Return type

[**WorkflowTemplatesListingOutputRep**](WorkflowTemplatesListingOutputRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Workflow templates list response JSON |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

