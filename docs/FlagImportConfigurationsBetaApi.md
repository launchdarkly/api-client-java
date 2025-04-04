# FlagImportConfigurationsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createFlagImportConfiguration**](FlagImportConfigurationsBetaApi.md#createFlagImportConfiguration) | **POST** /api/v2/integration-capabilities/flag-import/{projectKey}/{integrationKey} | Create a flag import configuration |
| [**deleteFlagImportConfiguration**](FlagImportConfigurationsBetaApi.md#deleteFlagImportConfiguration) | **DELETE** /api/v2/integration-capabilities/flag-import/{projectKey}/{integrationKey}/{integrationId} | Delete a flag import configuration |
| [**getFlagImportConfiguration**](FlagImportConfigurationsBetaApi.md#getFlagImportConfiguration) | **GET** /api/v2/integration-capabilities/flag-import/{projectKey}/{integrationKey}/{integrationId} | Get a single flag import configuration |
| [**getFlagImportConfigurations**](FlagImportConfigurationsBetaApi.md#getFlagImportConfigurations) | **GET** /api/v2/integration-capabilities/flag-import | List all flag import configurations |
| [**patchFlagImportConfiguration**](FlagImportConfigurationsBetaApi.md#patchFlagImportConfiguration) | **PATCH** /api/v2/integration-capabilities/flag-import/{projectKey}/{integrationKey}/{integrationId} | Update a flag import configuration |
| [**triggerFlagImportJob**](FlagImportConfigurationsBetaApi.md#triggerFlagImportJob) | **POST** /api/v2/integration-capabilities/flag-import/{projectKey}/{integrationKey}/{integrationId}/trigger | Trigger a single flag import run |


<a name="createFlagImportConfiguration"></a>
# **createFlagImportConfiguration**
> FlagImportIntegration createFlagImportConfiguration(projectKey, integrationKey, flagImportConfigurationPost)

Create a flag import configuration

Create a new flag import configuration. The &#x60;integrationKey&#x60; path parameter identifies the feature management system from which the import occurs, for example, &#x60;split&#x60;. The &#x60;config&#x60; object in the request body schema is described by the global integration settings, as specified by the &lt;code&gt;formVariables&lt;/code&gt; in the &lt;code&gt;manifest.json&lt;/code&gt; for this integration. It varies slightly based on the &#x60;integrationKey&#x60;.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FlagImportConfigurationsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FlagImportConfigurationsBetaApi apiInstance = new FlagImportConfigurationsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String integrationKey = "integrationKey_example"; // String | The integration key
    FlagImportConfigurationPost flagImportConfigurationPost = new FlagImportConfigurationPost(); // FlagImportConfigurationPost | 
    try {
      FlagImportIntegration result = apiInstance.createFlagImportConfiguration(projectKey, integrationKey, flagImportConfigurationPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagImportConfigurationsBetaApi#createFlagImportConfiguration");
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
| **integrationKey** | **String**| The integration key | |
| **flagImportConfigurationPost** | [**FlagImportConfigurationPost**](FlagImportConfigurationPost.md)|  | |

### Return type

[**FlagImportIntegration**](FlagImportIntegration.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag Import Configuration response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Project not found |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a name="deleteFlagImportConfiguration"></a>
# **deleteFlagImportConfiguration**
> deleteFlagImportConfiguration(projectKey, integrationKey, integrationId)

Delete a flag import configuration

Delete a flag import configuration by ID. The &#x60;integrationKey&#x60; path parameter identifies the feature management system from which the import occurs, for example, &#x60;split&#x60;.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FlagImportConfigurationsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FlagImportConfigurationsBetaApi apiInstance = new FlagImportConfigurationsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String integrationKey = "integrationKey_example"; // String | The integration key
    String integrationId = "integrationId_example"; // String | The integration ID
    try {
      apiInstance.deleteFlagImportConfiguration(projectKey, integrationKey, integrationId);
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagImportConfigurationsBetaApi#deleteFlagImportConfiguration");
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
| **integrationKey** | **String**| The integration key | |
| **integrationId** | **String**| The integration ID | |

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
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Project or import configuration not found |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a name="getFlagImportConfiguration"></a>
# **getFlagImportConfiguration**
> FlagImportIntegration getFlagImportConfiguration(projectKey, integrationKey, integrationId)

Get a single flag import configuration

Get a single flag import configuration by ID. The &#x60;integrationKey&#x60; path parameter identifies the feature management system from which the import occurs, for example, &#x60;split&#x60;.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FlagImportConfigurationsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FlagImportConfigurationsBetaApi apiInstance = new FlagImportConfigurationsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String integrationKey = "integrationKey_example"; // String | The integration key, for example, `split`
    String integrationId = "integrationId_example"; // String | The integration ID
    try {
      FlagImportIntegration result = apiInstance.getFlagImportConfiguration(projectKey, integrationKey, integrationId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagImportConfigurationsBetaApi#getFlagImportConfiguration");
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
| **integrationKey** | **String**| The integration key, for example, &#x60;split&#x60; | |
| **integrationId** | **String**| The integration ID | |

### Return type

[**FlagImportIntegration**](FlagImportIntegration.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag import response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Project or import configuration not found |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a name="getFlagImportConfigurations"></a>
# **getFlagImportConfigurations**
> FlagImportIntegrationCollection getFlagImportConfigurations()

List all flag import configurations

List all flag import configurations.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FlagImportConfigurationsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FlagImportConfigurationsBetaApi apiInstance = new FlagImportConfigurationsBetaApi(defaultClient);
    try {
      FlagImportIntegrationCollection result = apiInstance.getFlagImportConfigurations();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagImportConfigurationsBetaApi#getFlagImportConfigurations");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**FlagImportIntegrationCollection**](FlagImportIntegrationCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag Import Configuration response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Project not found |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a name="patchFlagImportConfiguration"></a>
# **patchFlagImportConfiguration**
> FlagImportIntegration patchFlagImportConfiguration(projectKey, integrationKey, integrationId, patchOperation)

Update a flag import configuration

Updating a flag import configuration uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](https://launchdarkly.com/docs/api#updates).&lt;br/&gt;&lt;br/&gt;To add an element to the import configuration fields that are arrays, set the &#x60;path&#x60; to the name of the field and then append &#x60;/&lt;array index&gt;&#x60;. Use &#x60;/0&#x60; to add to the beginning of the array. Use &#x60;/-&#x60; to add to the end of the array.&lt;br/&gt;&lt;br/&gt;You can update the &#x60;config&#x60;, &#x60;tags&#x60;, and &#x60;name&#x60; of the flag import configuration.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FlagImportConfigurationsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FlagImportConfigurationsBetaApi apiInstance = new FlagImportConfigurationsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String integrationKey = "integrationKey_example"; // String | The integration key
    String integrationId = "integrationId_example"; // String | The integration ID
    List<PatchOperation> patchOperation = Arrays.asList(); // List<PatchOperation> | 
    try {
      FlagImportIntegration result = apiInstance.patchFlagImportConfiguration(projectKey, integrationKey, integrationId, patchOperation);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagImportConfigurationsBetaApi#patchFlagImportConfiguration");
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
| **integrationKey** | **String**| The integration key | |
| **integrationId** | **String**| The integration ID | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**FlagImportIntegration**](FlagImportIntegration.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag import response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Project or import configuration not found |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a name="triggerFlagImportJob"></a>
# **triggerFlagImportJob**
> triggerFlagImportJob(projectKey, integrationKey, integrationId)

Trigger a single flag import run

Trigger a single flag import run for an existing flag import configuration. The &#x60;integrationKey&#x60; path parameter identifies the feature management system from which the import occurs, for example, &#x60;split&#x60;.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.FlagImportConfigurationsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    FlagImportConfigurationsBetaApi apiInstance = new FlagImportConfigurationsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String integrationKey = "integrationKey_example"; // String | The integration key
    String integrationId = "integrationId_example"; // String | The integration ID
    try {
      apiInstance.triggerFlagImportJob(projectKey, integrationKey, integrationId);
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagImportConfigurationsBetaApi#triggerFlagImportJob");
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
| **integrationKey** | **String**| The integration key | |
| **integrationId** | **String**| The integration ID | |

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
| **201** | Import job queued successfully |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Project or import configuration not found |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

