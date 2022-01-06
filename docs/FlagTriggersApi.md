# FlagTriggersApi

All URIs are relative to *https://app.launchdarkly.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createTriggerWorkflow**](FlagTriggersApi.md#createTriggerWorkflow) | **POST** /api/v2/flags/{projKey}/{flagKey}/triggers/{envKey} | Create flag trigger
[**deleteTriggerWorkflow**](FlagTriggersApi.md#deleteTriggerWorkflow) | **DELETE** /api/v2/flags/{projKey}/{flagKey}/triggers/{envKey}/{id} | Delete flag trigger
[**getTriggerWorkflowById**](FlagTriggersApi.md#getTriggerWorkflowById) | **GET** /api/v2/flags/{projKey}/{flagKey}/triggers/{envKey}/{id} | Get flag trigger by ID
[**getTriggerWorkflows**](FlagTriggersApi.md#getTriggerWorkflows) | **GET** /api/v2/flags/{projKey}/{flagKey}/triggers/{envKey} | List flag triggers
[**patchTriggerWorkflow**](FlagTriggersApi.md#patchTriggerWorkflow) | **PATCH** /api/v2/flags/{projKey}/{flagKey}/triggers/{envKey}/{id} | Update flag trigger


<a name="createTriggerWorkflow"></a>
# **createTriggerWorkflow**
> TriggerWorkflowRep createTriggerWorkflow(projKey, envKey, flagKey, triggerPost)

Create flag trigger

Create a new flag trigger. Triggers let you initiate changes to flag targeting remotely using a unique webhook URL.

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
    String projKey = "projKey_example"; // String | The project key
    String envKey = "envKey_example"; // String | The environment key
    String flagKey = "flagKey_example"; // String | The flag key
    TriggerPost triggerPost = new TriggerPost(); // TriggerPost | 
    try {
      TriggerWorkflowRep result = apiInstance.createTriggerWorkflow(projKey, envKey, flagKey, triggerPost);
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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projKey** | **String**| The project key |
 **envKey** | **String**| The environment key |
 **flagKey** | **String**| The flag key |
 **triggerPost** | [**TriggerPost**](TriggerPost.md)|  |

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
**201** | Successful flag trigger response |  -  |
**400** | Invalid request |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

<a name="deleteTriggerWorkflow"></a>
# **deleteTriggerWorkflow**
> deleteTriggerWorkflow(projKey, envKey, flagKey, id)

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
    String projKey = "projKey_example"; // String | The project key
    String envKey = "envKey_example"; // String | The environment key
    String flagKey = "flagKey_example"; // String | The flag key
    String id = "id_example"; // String | The flag trigger ID
    try {
      apiInstance.deleteTriggerWorkflow(projKey, envKey, flagKey, id);
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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projKey** | **String**| The project key |
 **envKey** | **String**| The environment key |
 **flagKey** | **String**| The flag key |
 **id** | **String**| The flag trigger ID |

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
**204** | Action succeeded |  -  |
**400** | Invalid request |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

<a name="getTriggerWorkflowById"></a>
# **getTriggerWorkflowById**
> TriggerWorkflowRep getTriggerWorkflowById(projKey, flagKey, envKey, id)

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
    String projKey = "projKey_example"; // String | The project key
    String flagKey = "flagKey_example"; // String | The flag key
    String envKey = "envKey_example"; // String | The environment key
    String id = "id_example"; // String | The flag trigger ID
    try {
      TriggerWorkflowRep result = apiInstance.getTriggerWorkflowById(projKey, flagKey, envKey, id);
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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projKey** | **String**| The project key |
 **flagKey** | **String**| The flag key |
 **envKey** | **String**| The environment key |
 **id** | **String**| The flag trigger ID |

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
**200** | Flag trigger response |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

<a name="getTriggerWorkflows"></a>
# **getTriggerWorkflows**
> TriggerWorkflowCollectionRep getTriggerWorkflows(projKey, envKey, flagKey)

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
    String projKey = "projKey_example"; // String | The project key
    String envKey = "envKey_example"; // String | The environment key
    String flagKey = "flagKey_example"; // String | The flag key
    try {
      TriggerWorkflowCollectionRep result = apiInstance.getTriggerWorkflows(projKey, envKey, flagKey);
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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projKey** | **String**| The project key |
 **envKey** | **String**| The environment key |
 **flagKey** | **String**| The flag key |

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
**200** | Flag trigger collection response JSON |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**429** | Rate limited |  -  |

<a name="patchTriggerWorkflow"></a>
# **patchTriggerWorkflow**
> TriggerWorkflowRep patchTriggerWorkflow(projKey, envKey, flagKey, id, flagTriggerInput)

Update flag trigger

Update a flag trigger. The request body must be a valid JSON patch or JSON merge patch document. To learn more, read [Updates](/#section/Overview/Updates).

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
    String projKey = "projKey_example"; // String | The project key
    String envKey = "envKey_example"; // String | The environment key
    String flagKey = "flagKey_example"; // String | The flag key
    String id = "id_example"; // String | The flag trigger ID
    FlagTriggerInput flagTriggerInput = new FlagTriggerInput(); // FlagTriggerInput | 
    try {
      TriggerWorkflowRep result = apiInstance.patchTriggerWorkflow(projKey, envKey, flagKey, id, flagTriggerInput);
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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projKey** | **String**| The project key |
 **envKey** | **String**| The environment key |
 **flagKey** | **String**| The flag key |
 **id** | **String**| The flag trigger ID |
 **flagTriggerInput** | [**FlagTriggerInput**](FlagTriggerInput.md)|  |

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
**200** | Flag trigger response |  -  |
**400** | Invalid request |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**409** | Status conflict |  -  |
**429** | Rate limited |  -  |

