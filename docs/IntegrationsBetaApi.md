# IntegrationsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createBigSegmentStoreIntegration**](IntegrationsBetaApi.md#createBigSegmentStoreIntegration) | **POST** /api/v2/integration-capabilities/big-segment-store/{projectKey}/{environmentKey}/{integrationKey} | Create big segment store integration |
| [**createFlagImportConfiguration**](IntegrationsBetaApi.md#createFlagImportConfiguration) | **POST** /api/v2/integration-capabilities/flag-import/{projectKey}/{integrationKey} | Create a flag import configuration |
| [**deleteBigSegmentStoreIntegration**](IntegrationsBetaApi.md#deleteBigSegmentStoreIntegration) | **DELETE** /api/v2/integration-capabilities/big-segment-store/{projectKey}/{environmentKey}/{integrationKey}/{integrationId} | Delete big segment store integration |
| [**deleteFlagImportConfiguration**](IntegrationsBetaApi.md#deleteFlagImportConfiguration) | **DELETE** /api/v2/integration-capabilities/flag-import/{projectKey}/{integrationKey}/{integrationId} | Delete a flag import configuration |
| [**getBigSegmentStoreIntegration**](IntegrationsBetaApi.md#getBigSegmentStoreIntegration) | **GET** /api/v2/integration-capabilities/big-segment-store/{projectKey}/{environmentKey}/{integrationKey}/{integrationId} | Get big segment store integration by ID |
| [**getBigSegmentStoreIntegrations**](IntegrationsBetaApi.md#getBigSegmentStoreIntegrations) | **GET** /api/v2/integration-capabilities/big-segment-store | List all big segment store integrations |
| [**getFlagImportConfiguration**](IntegrationsBetaApi.md#getFlagImportConfiguration) | **GET** /api/v2/integration-capabilities/flag-import/{projectKey}/{integrationKey}/{integrationId} | Get a single flag import configuration |
| [**getFlagImportConfigurations**](IntegrationsBetaApi.md#getFlagImportConfigurations) | **GET** /api/v2/integration-capabilities/flag-import | List all flag import configurations |
| [**patchBigSegmentStoreIntegration**](IntegrationsBetaApi.md#patchBigSegmentStoreIntegration) | **PATCH** /api/v2/integration-capabilities/big-segment-store/{projectKey}/{environmentKey}/{integrationKey}/{integrationId} | Update big segment store integration |
| [**patchFlagImportConfiguration**](IntegrationsBetaApi.md#patchFlagImportConfiguration) | **PATCH** /api/v2/integration-capabilities/flag-import/{projectKey}/{integrationKey}/{integrationId} | Update a flag import configuration |
| [**triggerFlagImportJob**](IntegrationsBetaApi.md#triggerFlagImportJob) | **POST** /api/v2/integration-capabilities/flag-import/{projectKey}/{integrationKey}/{integrationId}/trigger | Trigger a single flag import run |


<a name="createBigSegmentStoreIntegration"></a>
# **createBigSegmentStoreIntegration**
> BigSegmentStoreIntegration createBigSegmentStoreIntegration(projectKey, environmentKey, integrationKey, integrationDeliveryConfigurationPost)

Create big segment store integration

 Create a persistent store integration.  If you are using server-side SDKs, segments synced from external tools and larger list-based segments require a persistent store within your infrastructure. LaunchDarkly keeps the persistent store up to date and consults it during flag evaluation.  You can use either Redis or DynamoDB as your persistent store. When you create a persistent store integration, the fields in the &#x60;config&#x60; object in the request vary depending on which persistent store you use.  If you are using Redis to create your persistent store integration, you will need to know:  * Your Redis host * Your Redis port * Your Redis username * Your Redis password * Whether or not LaunchDarkly should connect using TLS  If you are using DynamoDB to create your persistent store integration, you will need to know:  * Your DynamoDB table name. The table must have the following schema:   * Partition key: &#x60;namespace&#x60; (string)   * Sort key: &#x60;key&#x60; (string) * Your DynamoDB Amazon Web Services (AWS) region. * Your AWS role Amazon Resource Name (ARN). This is the role that LaunchDarkly will assume to manage your DynamoDB table. * The External ID you specified when creating your Amazon Resource Name (ARN).  To learn more, read [Segment configuration](https://docs.launchdarkly.com/home/flags/segment-config). 

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
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String integrationKey = "integrationKey_example"; // String | The integration key, either `redis` or `dynamodb`
    IntegrationDeliveryConfigurationPost integrationDeliveryConfigurationPost = new IntegrationDeliveryConfigurationPost(); // IntegrationDeliveryConfigurationPost | 
    try {
      BigSegmentStoreIntegration result = apiInstance.createBigSegmentStoreIntegration(projectKey, environmentKey, integrationKey, integrationDeliveryConfigurationPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#createBigSegmentStoreIntegration");
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
| **integrationKey** | **String**| The integration key, either &#x60;redis&#x60; or &#x60;dynamodb&#x60; | |
| **integrationDeliveryConfigurationPost** | [**IntegrationDeliveryConfigurationPost**](IntegrationDeliveryConfigurationPost.md)|  | |

### Return type

[**BigSegmentStoreIntegration**](BigSegmentStoreIntegration.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Big segment store response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Environment or project not found |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

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
    String projectKey = "projectKey_example"; // String | The project key
    String integrationKey = "integrationKey_example"; // String | The integration key
    FlagImportConfigurationPost flagImportConfigurationPost = new FlagImportConfigurationPost(); // FlagImportConfigurationPost | 
    try {
      FlagImportIntegration result = apiInstance.createFlagImportConfiguration(projectKey, integrationKey, flagImportConfigurationPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#createFlagImportConfiguration");
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

<a name="deleteBigSegmentStoreIntegration"></a>
# **deleteBigSegmentStoreIntegration**
> deleteBigSegmentStoreIntegration(projectKey, environmentKey, integrationKey, integrationId)

Delete big segment store integration

Delete a persistent store integration. Each integration uses either Redis or DynamoDB.

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
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String integrationKey = "integrationKey_example"; // String | The integration key, either `redis` or `dynamodb`
    String integrationId = "integrationId_example"; // String | The integration ID
    try {
      apiInstance.deleteBigSegmentStoreIntegration(projectKey, environmentKey, integrationKey, integrationId);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#deleteBigSegmentStoreIntegration");
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
| **integrationKey** | **String**| The integration key, either &#x60;redis&#x60; or &#x60;dynamodb&#x60; | |
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
| **404** | Environment or project not found |  -  |
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
    String projectKey = "projectKey_example"; // String | The project key
    String integrationKey = "integrationKey_example"; // String | The integration key
    String integrationId = "integrationId_example"; // String | The integration ID
    try {
      apiInstance.deleteFlagImportConfiguration(projectKey, integrationKey, integrationId);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#deleteFlagImportConfiguration");
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

<a name="getBigSegmentStoreIntegration"></a>
# **getBigSegmentStoreIntegration**
> BigSegmentStoreIntegration getBigSegmentStoreIntegration(projectKey, environmentKey, integrationKey, integrationId)

Get big segment store integration by ID

Get a big segment store integration by ID.

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
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String integrationKey = "integrationKey_example"; // String | The integration key, either `redis` or `dynamodb`
    String integrationId = "integrationId_example"; // String | The integration ID
    try {
      BigSegmentStoreIntegration result = apiInstance.getBigSegmentStoreIntegration(projectKey, environmentKey, integrationKey, integrationId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#getBigSegmentStoreIntegration");
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
| **integrationKey** | **String**| The integration key, either &#x60;redis&#x60; or &#x60;dynamodb&#x60; | |
| **integrationId** | **String**| The integration ID | |

### Return type

[**BigSegmentStoreIntegration**](BigSegmentStoreIntegration.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Big segment store response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Environment or project not found |  -  |
| **429** | Rate limited |  -  |

<a name="getBigSegmentStoreIntegrations"></a>
# **getBigSegmentStoreIntegrations**
> BigSegmentStoreIntegrationCollection getBigSegmentStoreIntegrations()

List all big segment store integrations

List all big segment store integrations.

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
    try {
      BigSegmentStoreIntegrationCollection result = apiInstance.getBigSegmentStoreIntegrations();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#getBigSegmentStoreIntegrations");
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

[**BigSegmentStoreIntegrationCollection**](BigSegmentStoreIntegrationCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Big segment store collection response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Environment or project not found |  -  |
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
    String projectKey = "projectKey_example"; // String | The project key
    String integrationKey = "integrationKey_example"; // String | The integration key, for example, `split`
    String integrationId = "integrationId_example"; // String | The integration ID
    try {
      FlagImportIntegration result = apiInstance.getFlagImportConfiguration(projectKey, integrationKey, integrationId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#getFlagImportConfiguration");
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
    try {
      FlagImportIntegrationCollection result = apiInstance.getFlagImportConfigurations();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#getFlagImportConfigurations");
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

<a name="patchBigSegmentStoreIntegration"></a>
# **patchBigSegmentStoreIntegration**
> BigSegmentStoreIntegration patchBigSegmentStoreIntegration(projectKey, environmentKey, integrationKey, integrationId, patchOperation)

Update big segment store integration

Update a big segment store integration. Updating a big segment store requires a [JSON Patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](/#section/Overview/Updates).

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
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String integrationKey = "integrationKey_example"; // String | The integration key, either `redis` or `dynamodb`
    String integrationId = "integrationId_example"; // String | The integration ID
    List<PatchOperation> patchOperation = Arrays.asList(); // List<PatchOperation> | 
    try {
      BigSegmentStoreIntegration result = apiInstance.patchBigSegmentStoreIntegration(projectKey, environmentKey, integrationKey, integrationId, patchOperation);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#patchBigSegmentStoreIntegration");
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
| **integrationKey** | **String**| The integration key, either &#x60;redis&#x60; or &#x60;dynamodb&#x60; | |
| **integrationId** | **String**| The integration ID | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**BigSegmentStoreIntegration**](BigSegmentStoreIntegration.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Big segment store response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Environment or project not found |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a name="patchFlagImportConfiguration"></a>
# **patchFlagImportConfiguration**
> FlagImportIntegration patchFlagImportConfiguration(projectKey, integrationKey, integrationId, patchOperation)

Update a flag import configuration

Updating a flag import configuration uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](/#section/Overview/Updates).&lt;br/&gt;&lt;br/&gt;To add an element to the import configuration fields that are arrays, set the &#x60;path&#x60; to the name of the field and then append &#x60;/&lt;array index&gt;&#x60;. Use &#x60;/0&#x60; to add to the beginning of the array. Use &#x60;/-&#x60; to add to the end of the array.&lt;br/&gt;&lt;br/&gt;You can update the &#x60;config&#x60;, &#x60;tags&#x60;, and &#x60;name&#x60; of the flag import configuration.

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
    String projectKey = "projectKey_example"; // String | The project key
    String integrationKey = "integrationKey_example"; // String | The integration key
    String integrationId = "integrationId_example"; // String | The integration ID
    List<PatchOperation> patchOperation = Arrays.asList(); // List<PatchOperation> | 
    try {
      FlagImportIntegration result = apiInstance.patchFlagImportConfiguration(projectKey, integrationKey, integrationId, patchOperation);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#patchFlagImportConfiguration");
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
> Object triggerFlagImportJob(projectKey, integrationKey, integrationId)

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
    String projectKey = "projectKey_example"; // String | The project key
    String integrationKey = "integrationKey_example"; // String | The integration key
    String integrationId = "integrationId_example"; // String | The integration ID
    try {
      Object result = apiInstance.triggerFlagImportJob(projectKey, integrationKey, integrationId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#triggerFlagImportJob");
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

**Object**

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** |  |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Project or import configuration not found |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

