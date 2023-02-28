# WorkflowsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteWorkflow**](WorkflowsBetaApi.md#deleteWorkflow) | **DELETE** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/workflows/{workflowId} | Delete workflow |
| [**getCustomWorkflow**](WorkflowsBetaApi.md#getCustomWorkflow) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/workflows/{workflowId} | Get custom workflow |
| [**getWorkflows**](WorkflowsBetaApi.md#getWorkflows) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/workflows | Get workflows |
| [**postWorkflow**](WorkflowsBetaApi.md#postWorkflow) | **POST** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/workflows | Create workflow |


<a name="deleteWorkflow"></a>
# **deleteWorkflow**
> deleteWorkflow(projectKey, featureFlagKey, environmentKey, workflowId)

Delete workflow

Delete a workflow from a feature flag.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.WorkflowsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    WorkflowsBetaApi apiInstance = new WorkflowsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String workflowId = "workflowId_example"; // String | The workflow id
    try {
      apiInstance.deleteWorkflow(projectKey, featureFlagKey, environmentKey, workflowId);
    } catch (ApiException e) {
      System.err.println("Exception when calling WorkflowsBetaApi#deleteWorkflow");
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
| **environmentKey** | **String**| The environment key | |
| **workflowId** | **String**| The workflow id | |

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

<a name="getCustomWorkflow"></a>
# **getCustomWorkflow**
> CustomWorkflowOutput getCustomWorkflow(projectKey, featureFlagKey, environmentKey, workflowId)

Get custom workflow

Get a specific workflow by ID.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.WorkflowsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    WorkflowsBetaApi apiInstance = new WorkflowsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String workflowId = "workflowId_example"; // String | The workflow ID
    try {
      CustomWorkflowOutput result = apiInstance.getCustomWorkflow(projectKey, featureFlagKey, environmentKey, workflowId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling WorkflowsBetaApi#getCustomWorkflow");
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
| **environmentKey** | **String**| The environment key | |
| **workflowId** | **String**| The workflow ID | |

### Return type

[**CustomWorkflowOutput**](CustomWorkflowOutput.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Workflow response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getWorkflows"></a>
# **getWorkflows**
> CustomWorkflowsListingOutput getWorkflows(projectKey, featureFlagKey, environmentKey, status, sort)

Get workflows

Display workflows associated with a feature flag.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.WorkflowsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    WorkflowsBetaApi apiInstance = new WorkflowsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String status = "status_example"; // String | Filter results by workflow status. Valid status filters are `active`, `completed`, and `failed`.
    String sort = "sort_example"; // String | A field to sort the items by. Prefix field by a dash ( - ) to sort in descending order. This endpoint supports sorting by `creationDate` or `stopDate`.
    try {
      CustomWorkflowsListingOutput result = apiInstance.getWorkflows(projectKey, featureFlagKey, environmentKey, status, sort);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling WorkflowsBetaApi#getWorkflows");
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
| **environmentKey** | **String**| The environment key | |
| **status** | **String**| Filter results by workflow status. Valid status filters are &#x60;active&#x60;, &#x60;completed&#x60;, and &#x60;failed&#x60;. | [optional] |
| **sort** | **String**| A field to sort the items by. Prefix field by a dash ( - ) to sort in descending order. This endpoint supports sorting by &#x60;creationDate&#x60; or &#x60;stopDate&#x60;. | [optional] |

### Return type

[**CustomWorkflowsListingOutput**](CustomWorkflowsListingOutput.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Workflows collection response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="postWorkflow"></a>
# **postWorkflow**
> CustomWorkflowOutput postWorkflow(projectKey, featureFlagKey, environmentKey, customWorkflowInput, templateKey, dryRun)

Create workflow

Create a workflow for a feature flag. You can create a workflow directly, or you can apply a template to create a new workflow.  ### Creating a workflow  You can use the create workflow endpoint to create a workflow directly by adding a &#x60;stages&#x60; array to the request body.  For each stage, define the &#x60;name&#x60;, &#x60;conditions&#x60; when the stage should be executed, and &#x60;action&#x60; that describes the stage.  &lt;details&gt; &lt;summary&gt;Click to expand example&lt;/summary&gt;  _Example request body_ &#x60;&#x60;&#x60;json {   \&quot;name\&quot;: \&quot;Progressive rollout starting in two days\&quot;,   \&quot;description\&quot;: \&quot;Turn flag on for 10% of customers each day\&quot;,   \&quot;stages\&quot;: [     {       \&quot;name\&quot;: \&quot;10% rollout on day 1\&quot;,       \&quot;conditions\&quot;: [         {           \&quot;kind\&quot;: \&quot;schedule\&quot;,           \&quot;scheduleKind\&quot;: \&quot;relative\&quot;, // or \&quot;absolute\&quot;               //  If \&quot;scheduleKind\&quot; is \&quot;absolute\&quot;, set \&quot;executionDate\&quot;;               // \&quot;waitDuration\&quot; and \&quot;waitDurationUnit\&quot; will be ignored           \&quot;waitDuration\&quot;: 2,           \&quot;waitDurationUnit\&quot;: \&quot;calendarDay\&quot;         },         {           \&quot;kind\&quot;: \&quot;ld-approval\&quot;,           \&quot;notifyMemberIds\&quot;: [ \&quot;507f1f77bcf86cd799439011\&quot; ],           \&quot;notifyTeamKeys\&quot;: [ \&quot;example-team\&quot; ]         }       ],       \&quot;action\&quot;: {         \&quot;instructions\&quot;: [           {             \&quot;kind\&quot;: \&quot;turnFlagOn\&quot;           },           {             \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,             \&quot;rolloutWeights\&quot;: {               \&quot;452f5fb5-7320-4ba3-81a1-8f4324f79d49\&quot;: 90000,               \&quot;fc15f6a4-05d3-4aa4-a997-446be461345d\&quot;: 10000             }           }         ]       }     }   ] } &#x60;&#x60;&#x60; &lt;/details&gt;  ### Creating a workflow by applying a workflow template  You can also create a workflow by applying a workflow template. If you pass a valid workflow template key as the &#x60;templateKey&#x60; query parameter with the request, the API will attempt to create a new workflow with the stages defined in the workflow template with the corresponding key.  #### Applicability of stages Templates are created in the context of a particular flag in a particular environment in a particular project. However, because workflows created from a template can be applied to any project, environment, and flag, some steps of the workflow may need to be updated in order to be applicable for the target resource.  You can pass a &#x60;dryRun&#x60; query parameter to tell the API to return a report of which steps of the workflow template are applicable in the target project/environment/flag, and which will need to be updated. When the &#x60;dryRun&#x60; query parameter is present the response body includes a &#x60;meta&#x60; property that holds a list of parameters that could potentially be inapplicable for the target resource. Each of these parameters will include a &#x60;valid&#x60; field. You will need to update any invalid parameters in order to create the new workflow. You can do this using the &#x60;parameters&#x60; property, which overrides the workflow template parameters.  #### Overriding template parameters You can use the &#x60;parameters&#x60; property in the request body to tell the API to override the specified workflow template parameters with new values that are specific to your target project/environment/flag.  &lt;details&gt; &lt;summary&gt;Click to expand example&lt;/summary&gt;  _Example request body_ &#x60;&#x60;&#x60;json {  \&quot;name\&quot;: \&quot;workflow created from my-template\&quot;,  \&quot;description\&quot;: \&quot;description of my workflow\&quot;,  \&quot;parameters\&quot;: [   {    \&quot;_id\&quot;: \&quot;62cf2bc4cadbeb7697943f3b\&quot;,    \&quot;path\&quot;: \&quot;/clauses/0/values\&quot;,    \&quot;default\&quot;: {     \&quot;value\&quot;: [\&quot;updated-segment\&quot;]    }   },   {    \&quot;_id\&quot;: \&quot;62cf2bc4cadbeb7697943f3d\&quot;,    \&quot;path\&quot;: \&quot;/variationId\&quot;,    \&quot;default\&quot;: {     \&quot;value\&quot;: \&quot;abcd1234-abcd-1234-abcd-1234abcd12\&quot;    }   }  ] } &#x60;&#x60;&#x60; &lt;/details&gt;  If there are any steps in the template that are not applicable to the target resource, the workflow will not be created, and the &#x60;meta&#x60; property will be included in the response body detailing which parameters need to be updated. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.WorkflowsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    WorkflowsBetaApi apiInstance = new WorkflowsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    String environmentKey = "environmentKey_example"; // String | The environment key
    CustomWorkflowInput customWorkflowInput = new CustomWorkflowInput(); // CustomWorkflowInput | 
    String templateKey = "templateKey_example"; // String | The template key to apply as a starting point for the new workflow
    Boolean dryRun = true; // Boolean | Whether to call the endpoint in dry-run mode
    try {
      CustomWorkflowOutput result = apiInstance.postWorkflow(projectKey, featureFlagKey, environmentKey, customWorkflowInput, templateKey, dryRun);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling WorkflowsBetaApi#postWorkflow");
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
| **environmentKey** | **String**| The environment key | |
| **customWorkflowInput** | [**CustomWorkflowInput**](CustomWorkflowInput.md)|  | |
| **templateKey** | **String**| The template key to apply as a starting point for the new workflow | [optional] |
| **dryRun** | **Boolean**| Whether to call the endpoint in dry-run mode | [optional] |

### Return type

[**CustomWorkflowOutput**](CustomWorkflowOutput.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Workflow response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

