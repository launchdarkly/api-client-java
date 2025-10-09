# IntegrationsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createIntegrationConfiguration**](IntegrationsBetaApi.md#createIntegrationConfiguration) | **POST** /api/v2/integration-configurations/keys/{integrationKey} | Create integration configuration |
| [**deleteIntegrationConfiguration**](IntegrationsBetaApi.md#deleteIntegrationConfiguration) | **DELETE** /api/v2/integration-configurations/{integrationConfigurationId} | Delete integration configuration |
| [**getAllIntegrationConfigurations**](IntegrationsBetaApi.md#getAllIntegrationConfigurations) | **GET** /api/v2/integration-configurations/keys/{integrationKey} | Get all configurations for the integration |
| [**getIntegrationConfiguration**](IntegrationsBetaApi.md#getIntegrationConfiguration) | **GET** /api/v2/integration-configurations/{integrationConfigurationId} | Get an integration configuration |
| [**updateIntegrationConfiguration**](IntegrationsBetaApi.md#updateIntegrationConfiguration) | **PATCH** /api/v2/integration-configurations/{integrationConfigurationId} | Update integration configuration |


<a id="createIntegrationConfiguration"></a>
# **createIntegrationConfiguration**
> IntegrationConfigurationsRep createIntegrationConfiguration(integrationKey, integrationConfigurationPost)

Create integration configuration

Create a new integration configuration. (Excludes [persistent store](https://launchdarkly.com/docs/api/persistent-store-integrations-beta) and [flag import configurations](https://launchdarkly.com/docs/api/flag-import-configurations-beta).)

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.IntegrationsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    IntegrationsBetaApi apiInstance = new IntegrationsBetaApi(defaultClient);
    String integrationKey = "integrationKey_example"; // String | The integration key
    IntegrationConfigurationPost integrationConfigurationPost = new IntegrationConfigurationPost(); // IntegrationConfigurationPost | 
    try {
      IntegrationConfigurationsRep result = apiInstance.createIntegrationConfiguration(integrationKey, integrationConfigurationPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#createIntegrationConfiguration");
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
| **integrationKey** | **String**| The integration key | |
| **integrationConfigurationPost** | [**IntegrationConfigurationPost**](IntegrationConfigurationPost.md)|  | |

### Return type

[**IntegrationConfigurationsRep**](IntegrationConfigurationsRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Integration Configuration response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Integration key not found |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a id="deleteIntegrationConfiguration"></a>
# **deleteIntegrationConfiguration**
> deleteIntegrationConfiguration(integrationConfigurationId)

Delete integration configuration

Delete an integration configuration by ID. (Excludes [persistent store](https://launchdarkly.com/docs/api/persistent-store-integrations-beta) and [flag import configurations](https://launchdarkly.com/docs/api/flag-import-configurations-beta).)

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.IntegrationsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    IntegrationsBetaApi apiInstance = new IntegrationsBetaApi(defaultClient);
    String integrationConfigurationId = "integrationConfigurationId_example"; // String | The ID of the integration configuration to be deleted
    try {
      apiInstance.deleteIntegrationConfiguration(integrationConfigurationId);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#deleteIntegrationConfiguration");
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
| **integrationConfigurationId** | **String**| The ID of the integration configuration to be deleted | |

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

<a id="getAllIntegrationConfigurations"></a>
# **getAllIntegrationConfigurations**
> IntegrationConfigurationCollectionRep getAllIntegrationConfigurations(integrationKey)

Get all configurations for the integration

Get all integration configurations with the specified integration key. (Excludes [persistent store](https://launchdarkly.com/docs/api/persistent-store-integrations-beta) and [flag import configurations](https://launchdarkly.com/docs/api/flag-import-configurations-beta).).

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.IntegrationsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    IntegrationsBetaApi apiInstance = new IntegrationsBetaApi(defaultClient);
    String integrationKey = "integrationKey_example"; // String | Integration key
    try {
      IntegrationConfigurationCollectionRep result = apiInstance.getAllIntegrationConfigurations(integrationKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#getAllIntegrationConfigurations");
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
| **integrationKey** | **String**| Integration key | |

### Return type

[**IntegrationConfigurationCollectionRep**](IntegrationConfigurationCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | List of Integration Configurations |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Integration key not found |  -  |
| **429** | Rate limited |  -  |

<a id="getIntegrationConfiguration"></a>
# **getIntegrationConfiguration**
> IntegrationConfigurationsRep getIntegrationConfiguration(integrationConfigurationId)

Get an integration configuration

Get integration configuration with the specified ID. (Excludes [persistent store](https://launchdarkly.com/docs/api/persistent-store-integrations-beta) and [flag import configurations](https://launchdarkly.com/docs/api/flag-import-configurations-beta).)

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.IntegrationsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    IntegrationsBetaApi apiInstance = new IntegrationsBetaApi(defaultClient);
    String integrationConfigurationId = "integrationConfigurationId_example"; // String | Integration configuration ID
    try {
      IntegrationConfigurationsRep result = apiInstance.getIntegrationConfiguration(integrationConfigurationId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#getIntegrationConfiguration");
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
| **integrationConfigurationId** | **String**| Integration configuration ID | |

### Return type

[**IntegrationConfigurationsRep**](IntegrationConfigurationsRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Integration Configuration response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Integration ID not found |  -  |
| **429** | Rate limited |  -  |

<a id="updateIntegrationConfiguration"></a>
# **updateIntegrationConfiguration**
> IntegrationConfigurationsRep updateIntegrationConfiguration(integrationConfigurationId, patchOperation)

Update integration configuration

Update an integration configuration. Updating an integration configuration uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](https://launchdarkly.com/docs/api#updates).

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.IntegrationsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    IntegrationsBetaApi apiInstance = new IntegrationsBetaApi(defaultClient);
    String integrationConfigurationId = "integrationConfigurationId_example"; // String | The ID of the integration configuration
    List<PatchOperation> patchOperation = Arrays.asList(); // List<PatchOperation> | 
    try {
      IntegrationConfigurationsRep result = apiInstance.updateIntegrationConfiguration(integrationConfigurationId, patchOperation);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#updateIntegrationConfiguration");
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
| **integrationConfigurationId** | **String**| The ID of the integration configuration | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**IntegrationConfigurationsRep**](IntegrationConfigurationsRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Integration configuration response |  -  |
| **400** | Invalid request |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

