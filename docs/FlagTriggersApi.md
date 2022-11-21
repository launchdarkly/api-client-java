# FlagTriggersApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createTriggerWorkflow**](FlagTriggersApi.md#createTriggerWorkflow) | **POST** /api/v2/flags/{projectKey}/{featureFlagKey}/triggers/{environmentKey} | Create flag trigger |
| [**deleteTriggerWorkflow**](FlagTriggersApi.md#deleteTriggerWorkflow) | **DELETE** /api/v2/flags/{projectKey}/{featureFlagKey}/triggers/{environmentKey}/{id} | Delete flag trigger |
| [**getTriggerWorkflowById**](FlagTriggersApi.md#getTriggerWorkflowById) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey}/triggers/{environmentKey}/{id} | Get flag trigger by ID |
| [**getTriggerWorkflows**](FlagTriggersApi.md#getTriggerWorkflows) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey}/triggers/{environmentKey} | List flag triggers |
| [**patchTriggerWorkflow**](FlagTriggersApi.md#patchTriggerWorkflow) | **PATCH** /api/v2/flags/{projectKey}/{featureFlagKey}/triggers/{environmentKey}/{id} | Update flag trigger |


<a name="createTriggerWorkflow"></a>
# **createTriggerWorkflow**
> TriggerWorkflowRep createTriggerWorkflow(projectKey, environmentKey, featureFlagKey, triggerPost)

Create flag trigger

Create a new flag trigger.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FlagTriggersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FlagTriggersApi apiInstance = new FlagTriggersApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    TriggerPost triggerPost = new TriggerPost(); // TriggerPost | 
    try {
      TriggerWorkflowRep result = apiInstance.createTriggerWorkflow(projectKey, environmentKey, featureFlagKey, triggerPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagTriggersApi#createTriggerWorkflow");
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
| **featureFlagKey** | **String**| The feature flag key | |
| **triggerPost** | [**TriggerPost**](TriggerPost.md)|  | |

### Return type

[**TriggerWorkflowRep**](TriggerWorkflowRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Flag trigger response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="deleteTriggerWorkflow"></a>
# **deleteTriggerWorkflow**
> deleteTriggerWorkflow(projectKey, environmentKey, featureFlagKey, id)

Delete flag trigger

Delete a flag trigger by ID.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FlagTriggersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FlagTriggersApi apiInstance = new FlagTriggersApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    String id = "id_example"; // String | The flag trigger ID
    try {
      apiInstance.deleteTriggerWorkflow(projectKey, environmentKey, featureFlagKey, id);
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagTriggersApi#deleteTriggerWorkflow");
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
| **featureFlagKey** | **String**| The feature flag key | |
| **id** | **String**| The flag trigger ID | |

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

<a name="getTriggerWorkflowById"></a>
# **getTriggerWorkflowById**
> TriggerWorkflowRep getTriggerWorkflowById(projectKey, featureFlagKey, environmentKey, id)

Get flag trigger by ID

Get a flag trigger by ID.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FlagTriggersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FlagTriggersApi apiInstance = new FlagTriggersApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String id = "id_example"; // String | The flag trigger ID
    try {
      TriggerWorkflowRep result = apiInstance.getTriggerWorkflowById(projectKey, featureFlagKey, environmentKey, id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagTriggersApi#getTriggerWorkflowById");
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
| **id** | **String**| The flag trigger ID | |

### Return type

[**TriggerWorkflowRep**](TriggerWorkflowRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag trigger response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getTriggerWorkflows"></a>
# **getTriggerWorkflows**
> TriggerWorkflowCollectionRep getTriggerWorkflows(projectKey, environmentKey, featureFlagKey)

List flag triggers

Get a list of all flag triggers.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FlagTriggersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FlagTriggersApi apiInstance = new FlagTriggersApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    try {
      TriggerWorkflowCollectionRep result = apiInstance.getTriggerWorkflows(projectKey, environmentKey, featureFlagKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagTriggersApi#getTriggerWorkflows");
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
| **featureFlagKey** | **String**| The feature flag key | |

### Return type

[**TriggerWorkflowCollectionRep**](TriggerWorkflowCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag trigger collection response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |

<a name="patchTriggerWorkflow"></a>
# **patchTriggerWorkflow**
> TriggerWorkflowRep patchTriggerWorkflow(projectKey, environmentKey, featureFlagKey, id, flagTriggerInput)

Update flag trigger

Update a flag trigger. Updating a flag trigger uses the semantic patch format.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating flag triggers.  #### replaceTriggerActionInstructions  Removes the existing trigger action and replaces it with the new instructions.  ##### Parameters  - &#x60;value&#x60;: An array of the new &#x60;kind&#x60;s of actions to perform when triggering. Supported flag actions are &#x60;turnFlagOn&#x60; and &#x60;turnFlagOff&#x60;.  For example, to replace the trigger action instructions, use this request body:  &#x60;&#x60;&#x60;json {   \&quot;comment\&quot;: \&quot;optional comment\&quot;,   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replaceTriggerActionInstructions\&quot;,       \&quot;value\&quot;: [ {\&quot;kind\&quot;: \&quot;turnFlagOff\&quot;} ]     }   ] } &#x60;&#x60;&#x60;  #### cycleTriggerUrl  Generates a new URL for this trigger. You must update any clients using the trigger to use this new URL.  #### disableTrigger  Disables the trigger. This saves the trigger configuration, but the trigger stops running. To re-enable, use &#x60;enableTrigger&#x60;.  #### enableTrigger  Enables the trigger. If you previously disabled the trigger, it begins running again. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FlagTriggersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FlagTriggersApi apiInstance = new FlagTriggersApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String featureFlagKey = "featureFlagKey_example"; // String | The feature flag key
    String id = "id_example"; // String | The flag trigger ID
    FlagTriggerInput flagTriggerInput = new FlagTriggerInput(); // FlagTriggerInput | 
    try {
      TriggerWorkflowRep result = apiInstance.patchTriggerWorkflow(projectKey, environmentKey, featureFlagKey, id, flagTriggerInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagTriggersApi#patchTriggerWorkflow");
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
| **featureFlagKey** | **String**| The feature flag key | |
| **id** | **String**| The flag trigger ID | |
| **flagTriggerInput** | [**FlagTriggerInput**](FlagTriggerInput.md)|  | |

### Return type

[**TriggerWorkflowRep**](TriggerWorkflowRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag trigger response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

