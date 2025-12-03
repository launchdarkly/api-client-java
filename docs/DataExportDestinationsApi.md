# DataExportDestinationsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteDestination**](DataExportDestinationsApi.md#deleteDestination) | **DELETE** /api/v2/destinations/{projectKey}/{environmentKey}/{id} | Delete Data Export destination |
| [**getDestination**](DataExportDestinationsApi.md#getDestination) | **GET** /api/v2/destinations/{projectKey}/{environmentKey}/{id} | Get destination |
| [**getDestinations**](DataExportDestinationsApi.md#getDestinations) | **GET** /api/v2/destinations | List destinations |
| [**patchDestination**](DataExportDestinationsApi.md#patchDestination) | **PATCH** /api/v2/destinations/{projectKey}/{environmentKey}/{id} | Update Data Export destination |
| [**postDestination**](DataExportDestinationsApi.md#postDestination) | **POST** /api/v2/destinations/{projectKey}/{environmentKey} | Create Data Export destination |
| [**postGenerateTrustPolicy**](DataExportDestinationsApi.md#postGenerateTrustPolicy) | **POST** /api/v2/destinations/projects/{projKey}/environments/{envKey}/generate-trust-policy | Generate trust policy |
| [**postGenerateWarehouseDestinationKeyPair**](DataExportDestinationsApi.md#postGenerateWarehouseDestinationKeyPair) | **POST** /api/v2/destinations/generate-warehouse-destination-key-pair | Generate Snowflake destination key pair |


<a id="deleteDestination"></a>
# **deleteDestination**
> deleteDestination(projectKey, environmentKey, id)

Delete Data Export destination

Delete a Data Export destination by ID.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.DataExportDestinationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    DataExportDestinationsApi apiInstance = new DataExportDestinationsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String id = "id_example"; // String | The Data Export destination ID
    try {
      apiInstance.deleteDestination(projectKey, environmentKey, id);
    } catch (ApiException e) {
      System.err.println("Exception when calling DataExportDestinationsApi#deleteDestination");
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
| **id** | **String**| The Data Export destination ID | |

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
| **204** | Destination response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="getDestination"></a>
# **getDestination**
> Destination getDestination(projectKey, environmentKey, id)

Get destination

Get a single Data Export destination by ID.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.DataExportDestinationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    DataExportDestinationsApi apiInstance = new DataExportDestinationsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String id = "id_example"; // String | The Data Export destination ID
    try {
      Destination result = apiInstance.getDestination(projectKey, environmentKey, id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DataExportDestinationsApi#getDestination");
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
| **id** | **String**| The Data Export destination ID | |

### Return type

[**Destination**](Destination.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Destination response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a id="getDestinations"></a>
# **getDestinations**
> Destinations getDestinations()

List destinations

Get a list of Data Export destinations configured across all projects and environments.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.DataExportDestinationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    DataExportDestinationsApi apiInstance = new DataExportDestinationsApi(defaultClient);
    try {
      Destinations result = apiInstance.getDestinations();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DataExportDestinationsApi#getDestinations");
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

[**Destinations**](Destinations.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Destination collection response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |

<a id="patchDestination"></a>
# **patchDestination**
> Destination patchDestination(projectKey, environmentKey, id, patchOperation)

Update Data Export destination

Update a Data Export destination. Updating a destination uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) or [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) representation of the desired changes. To learn more, read [Updates](https://launchdarkly.com/docs/api#updates).

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.DataExportDestinationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    DataExportDestinationsApi apiInstance = new DataExportDestinationsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String id = "id_example"; // String | The Data Export destination ID
    List<PatchOperation> patchOperation = Arrays.asList(); // List<PatchOperation> | 
    try {
      Destination result = apiInstance.patchDestination(projectKey, environmentKey, id, patchOperation);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DataExportDestinationsApi#patchDestination");
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
| **id** | **String**| The Data Export destination ID | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**Destination**](Destination.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Destination response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a id="postDestination"></a>
# **postDestination**
> Destination postDestination(projectKey, environmentKey, destinationPost)

Create Data Export destination

 Create a new Data Export destination.  In the &#x60;config&#x60; request body parameter, the fields required depend on the type of Data Export destination.  &lt;details&gt; &lt;summary&gt;Click to expand &lt;code&gt;config&lt;/code&gt; parameter details&lt;/summary&gt;  #### Azure Event Hubs  To create a Data Export destination with a &#x60;kind&#x60; of &#x60;azure-event-hubs&#x60;, the &#x60;config&#x60; object requires the following fields:  * &#x60;namespace&#x60;: The Event Hub Namespace name * &#x60;name&#x60;: The Event Hub name * &#x60;policyName&#x60;: The shared access signature policy name. You can find your policy name in the settings of your Azure Event Hubs Namespace. * &#x60;policyKey&#x60;: The shared access signature key. You can find your policy key in the settings of your Azure Event Hubs Namespace.  #### Google Cloud Pub/Sub  To create a Data Export destination with a &#x60;kind&#x60; of &#x60;google-pubsub&#x60;, the &#x60;config&#x60; object requires the following fields:  * &#x60;project&#x60;: The Google PubSub project ID for the project to publish to * &#x60;topic&#x60;: The Google PubSub topic ID for the topic to publish to  #### Amazon Kinesis  To create a Data Export destination with a &#x60;kind&#x60; of &#x60;kinesis&#x60;, the &#x60;config&#x60; object requires the following fields:  * &#x60;region&#x60;: The Kinesis stream&#39;s AWS region key * &#x60;roleArn&#x60;: The Amazon Resource Name (ARN) of the AWS role that will be writing to Kinesis * &#x60;streamName&#x60;: The name of the Kinesis stream that LaunchDarkly is sending events to. This is not the ARN of the stream.  #### mParticle  To create a Data Export destination with a &#x60;kind&#x60; of &#x60;mparticle&#x60;, the &#x60;config&#x60; object requires the following fields:  * &#x60;apiKey&#x60;: The mParticle API key * &#x60;secret&#x60;: The mParticle API secret * &#x60;userIdentity&#x60;: The type of identifier you use to identify your end users in mParticle * &#x60;anonymousUserIdentity&#x60;: The type of identifier you use to identify your anonymous end users in mParticle  #### Segment  To create a Data Export destination with a &#x60;kind&#x60; of &#x60;segment&#x60;, the &#x60;config&#x60; object requires the following fields:  * &#x60;writeKey&#x60;: The Segment write key. This is used to authenticate LaunchDarkly&#39;s calls to Segment.  #### Snowflake  To create a Data Export destination with a &#x60;kind&#x60; of &#x60;snowflake-v2&#x60;, the &#x60;config&#x60; object requires the following fields:  * &#x60;publicKey&#x60;: The &#x60;publicKey&#x60; is returned as part of the [Generate Snowflake destination key pair](https://launchdarkly.com/docs/api/data-export-destinations/post-generate-warehouse-destination-key-pair) response. It is the &#x60;public_key&#x60; field. * &#x60;snowflakeHostAddress&#x60;: Your Snowflake account URL.  &lt;/details&gt; 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.DataExportDestinationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    DataExportDestinationsApi apiInstance = new DataExportDestinationsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    DestinationPost destinationPost = new DestinationPost(); // DestinationPost | 
    try {
      Destination result = apiInstance.postDestination(projectKey, environmentKey, destinationPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DataExportDestinationsApi#postDestination");
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
| **destinationPost** | [**DestinationPost**](DestinationPost.md)|  | |

### Return type

[**Destination**](Destination.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Destination response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a id="postGenerateTrustPolicy"></a>
# **postGenerateTrustPolicy**
> GenerateTrustPolicyPostRep postGenerateTrustPolicy(projKey, envKey)

Generate trust policy

Trust policy to allow Data Export to assume the role and perform operations on AWS resources

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.DataExportDestinationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    DataExportDestinationsApi apiInstance = new DataExportDestinationsApi(defaultClient);
    String projKey = "projKey_example"; // String | The project key
    String envKey = "envKey_example"; // String | The environment key
    try {
      GenerateTrustPolicyPostRep result = apiInstance.postGenerateTrustPolicy(projKey, envKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DataExportDestinationsApi#postGenerateTrustPolicy");
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
| **projKey** | **String**| The project key | |
| **envKey** | **String**| The environment key | |

### Return type

[**GenerateTrustPolicyPostRep**](GenerateTrustPolicyPostRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Generate trust policy response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a id="postGenerateWarehouseDestinationKeyPair"></a>
# **postGenerateWarehouseDestinationKeyPair**
> GenerateWarehouseDestinationKeyPairPostRep postGenerateWarehouseDestinationKeyPair()

Generate Snowflake destination key pair

Generate key pair to allow Data Export to authenticate into a Snowflake warehouse destination

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.DataExportDestinationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    DataExportDestinationsApi apiInstance = new DataExportDestinationsApi(defaultClient);
    try {
      GenerateWarehouseDestinationKeyPairPostRep result = apiInstance.postGenerateWarehouseDestinationKeyPair();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DataExportDestinationsApi#postGenerateWarehouseDestinationKeyPair");
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

[**GenerateWarehouseDestinationKeyPairPostRep**](GenerateWarehouseDestinationKeyPairPostRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Generate warehouse destination key pair response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

