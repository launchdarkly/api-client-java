# IntegrationDeliveryConfigurationsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createIntegrationDeliveryConfiguration**](IntegrationDeliveryConfigurationsBetaApi.md#createIntegrationDeliveryConfiguration) | **POST** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey}/{integrationKey} | Create delivery configuration |
| [**deleteIntegrationDeliveryConfiguration**](IntegrationDeliveryConfigurationsBetaApi.md#deleteIntegrationDeliveryConfiguration) | **DELETE** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey}/{integrationKey}/{id} | Delete delivery configuration |
| [**getIntegrationDeliveryConfigurationByEnvironment**](IntegrationDeliveryConfigurationsBetaApi.md#getIntegrationDeliveryConfigurationByEnvironment) | **GET** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey} | Get delivery configurations by environment |
| [**getIntegrationDeliveryConfigurationById**](IntegrationDeliveryConfigurationsBetaApi.md#getIntegrationDeliveryConfigurationById) | **GET** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey}/{integrationKey}/{id} | Get delivery configuration by ID |
| [**getIntegrationDeliveryConfigurations**](IntegrationDeliveryConfigurationsBetaApi.md#getIntegrationDeliveryConfigurations) | **GET** /api/v2/integration-capabilities/featureStore | List all delivery configurations |
| [**patchIntegrationDeliveryConfiguration**](IntegrationDeliveryConfigurationsBetaApi.md#patchIntegrationDeliveryConfiguration) | **PATCH** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey}/{integrationKey}/{id} | Update delivery configuration |
| [**validateIntegrationDeliveryConfiguration**](IntegrationDeliveryConfigurationsBetaApi.md#validateIntegrationDeliveryConfiguration) | **POST** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey}/{integrationKey}/{id}/validate | Validate delivery configuration |


<a name="createIntegrationDeliveryConfiguration"></a>
# **createIntegrationDeliveryConfiguration**
> IntegrationDeliveryConfiguration createIntegrationDeliveryConfiguration(projectKey, environmentKey, integrationKey, integrationDeliveryConfigurationPost)

Create delivery configuration

Create a delivery configuration.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.IntegrationDeliveryConfigurationsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    IntegrationDeliveryConfigurationsBetaApi apiInstance = new IntegrationDeliveryConfigurationsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String integrationKey = "integrationKey_example"; // String | The integration key
    IntegrationDeliveryConfigurationPost integrationDeliveryConfigurationPost = new IntegrationDeliveryConfigurationPost(); // IntegrationDeliveryConfigurationPost | 
    try {
      IntegrationDeliveryConfiguration result = apiInstance.createIntegrationDeliveryConfiguration(projectKey, environmentKey, integrationKey, integrationDeliveryConfigurationPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationDeliveryConfigurationsBetaApi#createIntegrationDeliveryConfiguration");
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
| **integrationKey** | **String**| The integration key | |
| **integrationDeliveryConfigurationPost** | [**IntegrationDeliveryConfigurationPost**](IntegrationDeliveryConfigurationPost.md)|  | |

### Return type

[**IntegrationDeliveryConfiguration**](IntegrationDeliveryConfiguration.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Integration delivery configuration response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="deleteIntegrationDeliveryConfiguration"></a>
# **deleteIntegrationDeliveryConfiguration**
> deleteIntegrationDeliveryConfiguration(projectKey, environmentKey, integrationKey, id)

Delete delivery configuration

Delete a delivery configuration.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.IntegrationDeliveryConfigurationsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    IntegrationDeliveryConfigurationsBetaApi apiInstance = new IntegrationDeliveryConfigurationsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String integrationKey = "integrationKey_example"; // String | The integration key
    String id = "id_example"; // String | The configuration ID
    try {
      apiInstance.deleteIntegrationDeliveryConfiguration(projectKey, environmentKey, integrationKey, id);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationDeliveryConfigurationsBetaApi#deleteIntegrationDeliveryConfiguration");
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
| **integrationKey** | **String**| The integration key | |
| **id** | **String**| The configuration ID | |

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

<a name="getIntegrationDeliveryConfigurationByEnvironment"></a>
# **getIntegrationDeliveryConfigurationByEnvironment**
> IntegrationDeliveryConfigurationCollection getIntegrationDeliveryConfigurationByEnvironment(projectKey, environmentKey)

Get delivery configurations by environment

Get delivery configurations by environment.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.IntegrationDeliveryConfigurationsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    IntegrationDeliveryConfigurationsBetaApi apiInstance = new IntegrationDeliveryConfigurationsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    try {
      IntegrationDeliveryConfigurationCollection result = apiInstance.getIntegrationDeliveryConfigurationByEnvironment(projectKey, environmentKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationDeliveryConfigurationsBetaApi#getIntegrationDeliveryConfigurationByEnvironment");
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

### Return type

[**IntegrationDeliveryConfigurationCollection**](IntegrationDeliveryConfigurationCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Integration delivery configuration collection response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getIntegrationDeliveryConfigurationById"></a>
# **getIntegrationDeliveryConfigurationById**
> IntegrationDeliveryConfiguration getIntegrationDeliveryConfigurationById(projectKey, environmentKey, integrationKey, id)

Get delivery configuration by ID

Get delivery configuration by ID.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.IntegrationDeliveryConfigurationsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    IntegrationDeliveryConfigurationsBetaApi apiInstance = new IntegrationDeliveryConfigurationsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String integrationKey = "integrationKey_example"; // String | The integration key
    String id = "id_example"; // String | The configuration ID
    try {
      IntegrationDeliveryConfiguration result = apiInstance.getIntegrationDeliveryConfigurationById(projectKey, environmentKey, integrationKey, id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationDeliveryConfigurationsBetaApi#getIntegrationDeliveryConfigurationById");
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
| **integrationKey** | **String**| The integration key | |
| **id** | **String**| The configuration ID | |

### Return type

[**IntegrationDeliveryConfiguration**](IntegrationDeliveryConfiguration.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Integration delivery configuration response |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getIntegrationDeliveryConfigurations"></a>
# **getIntegrationDeliveryConfigurations**
> IntegrationDeliveryConfigurationCollection getIntegrationDeliveryConfigurations()

List all delivery configurations

List all delivery configurations.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.IntegrationDeliveryConfigurationsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    IntegrationDeliveryConfigurationsBetaApi apiInstance = new IntegrationDeliveryConfigurationsBetaApi(defaultClient);
    try {
      IntegrationDeliveryConfigurationCollection result = apiInstance.getIntegrationDeliveryConfigurations();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationDeliveryConfigurationsBetaApi#getIntegrationDeliveryConfigurations");
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

[**IntegrationDeliveryConfigurationCollection**](IntegrationDeliveryConfigurationCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Integration delivery configuration collection response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="patchIntegrationDeliveryConfiguration"></a>
# **patchIntegrationDeliveryConfiguration**
> IntegrationDeliveryConfiguration patchIntegrationDeliveryConfiguration(projectKey, environmentKey, integrationKey, id, patchOperation)

Update delivery configuration

Update an integration delivery configuration.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.IntegrationDeliveryConfigurationsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    IntegrationDeliveryConfigurationsBetaApi apiInstance = new IntegrationDeliveryConfigurationsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String integrationKey = "integrationKey_example"; // String | The integration key
    String id = "id_example"; // String | The configuration ID
    List<PatchOperation> patchOperation = Arrays.asList(); // List<PatchOperation> | 
    try {
      IntegrationDeliveryConfiguration result = apiInstance.patchIntegrationDeliveryConfiguration(projectKey, environmentKey, integrationKey, id, patchOperation);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationDeliveryConfigurationsBetaApi#patchIntegrationDeliveryConfiguration");
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
| **integrationKey** | **String**| The integration key | |
| **id** | **String**| The configuration ID | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**IntegrationDeliveryConfiguration**](IntegrationDeliveryConfiguration.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Integration delivery configuration response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **422** | Invalid patch content |  -  |
| **429** | Rate limited |  -  |

<a name="validateIntegrationDeliveryConfiguration"></a>
# **validateIntegrationDeliveryConfiguration**
> IntegrationDeliveryConfigurationResponse validateIntegrationDeliveryConfiguration(projectKey, environmentKey, integrationKey, id)

Validate delivery configuration

Validate the saved delivery configuration, using the &#x60;validationRequest&#x60; in the integration&#39;s &#x60;manifest.json&#x60; file.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.IntegrationDeliveryConfigurationsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    IntegrationDeliveryConfigurationsBetaApi apiInstance = new IntegrationDeliveryConfigurationsBetaApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String integrationKey = "integrationKey_example"; // String | The integration key
    String id = "id_example"; // String | The configuration ID
    try {
      IntegrationDeliveryConfigurationResponse result = apiInstance.validateIntegrationDeliveryConfiguration(projectKey, environmentKey, integrationKey, id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationDeliveryConfigurationsBetaApi#validateIntegrationDeliveryConfiguration");
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
| **integrationKey** | **String**| The integration key | |
| **id** | **String**| The configuration ID | |

### Return type

[**IntegrationDeliveryConfigurationResponse**](IntegrationDeliveryConfigurationResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Integration delivery configuration validation response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

