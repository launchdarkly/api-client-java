# ContextsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteContextInstances**](ContextsApi.md#deleteContextInstances) | **DELETE** /api/v2/projects/{projectKey}/environments/{environmentKey}/context-instances/{id} | Delete context instances |
| [**evaluateContextInstance**](ContextsApi.md#evaluateContextInstance) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/flags/evaluate | Evaluate flags for context instance |
| [**getContextAttributeNames**](ContextsApi.md#getContextAttributeNames) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/context-attributes | Get context attribute names |
| [**getContextAttributeValues**](ContextsApi.md#getContextAttributeValues) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/context-attributes/{attributeName} | Get context attribute values |
| [**getContextInstances**](ContextsApi.md#getContextInstances) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/context-instances/{id} | Get context instances |
| [**getContextKindsByProjectKey**](ContextsApi.md#getContextKindsByProjectKey) | **GET** /api/v2/projects/{projectKey}/context-kinds | Get context kinds |
| [**getContexts**](ContextsApi.md#getContexts) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/contexts/{kind}/{key} | Get contexts |
| [**putContextKind**](ContextsApi.md#putContextKind) | **PUT** /api/v2/projects/{projectKey}/context-kinds/{key} | Create or update context kind |
| [**searchContextInstances**](ContextsApi.md#searchContextInstances) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/context-instances/search | Search for context instances |
| [**searchContexts**](ContextsApi.md#searchContexts) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/contexts/search | Search for contexts |


<a name="deleteContextInstances"></a>
# **deleteContextInstances**
> deleteContextInstances(projectKey, environmentKey, id)

Delete context instances

Delete context instances by ID.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ContextsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ContextsApi apiInstance = new ContextsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String id = "id_example"; // String | The context instance ID
    try {
      apiInstance.deleteContextInstances(projectKey, environmentKey, id);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#deleteContextInstances");
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
| **id** | **String**| The context instance ID | |

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

<a name="evaluateContextInstance"></a>
# **evaluateContextInstance**
> ContextInstanceEvaluations evaluateContextInstance(projectKey, environmentKey, requestBody, limit, offset, sort, filter)

Evaluate flags for context instance

Evaluate flags for a context instance, for example, to determine the expected flag variation. **Do not use this API instead of an SDK.** The LaunchDarkly SDKs are specialized for the tasks of evaluating feature flags in your application at scale and generating analytics events based on those evaluations. This API is not designed for that use case. Any evaluations you perform with this API will not be reflected in features such as flag statuses and flag insights. Context instances evaluated by this API will not appear in the Contexts list. To learn more, read [Comparing LaunchDarkly&#39;s SDKs and REST API](https://docs.launchdarkly.com/guide/api/comparing-sdk-rest-api).  ### Filtering   LaunchDarkly supports the &#x60;filter&#x60; query param for filtering, with the following fields:  - &#x60;query&#x60; filters for a string that matches against the flags&#39; keys and names. It is not case sensitive. For example: &#x60;filter&#x3D;query equals dark-mode&#x60;. - &#x60;tags&#x60; filters the list to flags that have all of the tags in the list. For example: &#x60;filter&#x3D;tags contains [\&quot;beta\&quot;,\&quot;q1\&quot;]&#x60;.  You can also apply multiple filters at once. For example, setting &#x60;filter&#x3D;query equals dark-mode, tags contains [\&quot;beta\&quot;,\&quot;q1\&quot;]&#x60; matches flags which match the key or name &#x60;dark-mode&#x60; and are tagged &#x60;beta&#x60; and &#x60;q1&#x60;. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ContextsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ContextsApi apiInstance = new ContextsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    Map<String, Object> requestBody = {"key":"user-key-123abc","kind":"user","otherAttribute":"other attribute value"}; // Map<String, Object> | 
    Long limit = 56L; // Long | The number of feature flags to return. Defaults to -1, which returns all flags
    Long offset = 56L; // Long | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    String sort = "sort_example"; // String | A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order
    String filter = "filter_example"; // String | A comma-separated list of filters. Each filter is of the form `field operator value`. Supported fields are explained above.
    try {
      ContextInstanceEvaluations result = apiInstance.evaluateContextInstance(projectKey, environmentKey, requestBody, limit, offset, sort, filter);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#evaluateContextInstance");
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
| **requestBody** | [**Map&lt;String, Object&gt;**](Object.md)|  | |
| **limit** | **Long**| The number of feature flags to return. Defaults to -1, which returns all flags | [optional] |
| **offset** | **Long**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |
| **sort** | **String**| A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order | [optional] |
| **filter** | **String**| A comma-separated list of filters. Each filter is of the form &#x60;field operator value&#x60;. Supported fields are explained above. | [optional] |

### Return type

[**ContextInstanceEvaluations**](ContextInstanceEvaluations.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag evaluation collection response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getContextAttributeNames"></a>
# **getContextAttributeNames**
> ContextAttributeNamesCollection getContextAttributeNames(projectKey, environmentKey, filter)

Get context attribute names

Get context attribute names. Returns only the first 100 attribute names per context.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ContextsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ContextsApi apiInstance = new ContextsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String filter = "filter_example"; // String | A comma-separated list of context filters. This endpoint only accepts `kind` filters, with the `equals` operator, and `name` filters, with the `startsWith` operator. To learn more about the filter syntax, read [Filtering contexts and context instances](/tag/Contexts#filtering-contexts-and-context-instances).
    try {
      ContextAttributeNamesCollection result = apiInstance.getContextAttributeNames(projectKey, environmentKey, filter);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#getContextAttributeNames");
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
| **filter** | **String**| A comma-separated list of context filters. This endpoint only accepts &#x60;kind&#x60; filters, with the &#x60;equals&#x60; operator, and &#x60;name&#x60; filters, with the &#x60;startsWith&#x60; operator. To learn more about the filter syntax, read [Filtering contexts and context instances](/tag/Contexts#filtering-contexts-and-context-instances). | [optional] |

### Return type

[**ContextAttributeNamesCollection**](ContextAttributeNamesCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Context attribute names collection response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |

<a name="getContextAttributeValues"></a>
# **getContextAttributeValues**
> ContextAttributeValuesCollection getContextAttributeValues(projectKey, environmentKey, attributeName, filter)

Get context attribute values

Get context attribute values.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ContextsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ContextsApi apiInstance = new ContextsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String attributeName = "attributeName_example"; // String | The attribute name
    String filter = "filter_example"; // String | A comma-separated list of context filters. This endpoint only accepts `kind` filters, with the `equals` operator, and `value` filters, with the `startsWith` operator. To learn more about the filter syntax, read [Filtering contexts and context instances](/tag/Contexts#filtering-contexts-and-context-instances).
    try {
      ContextAttributeValuesCollection result = apiInstance.getContextAttributeValues(projectKey, environmentKey, attributeName, filter);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#getContextAttributeValues");
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
| **attributeName** | **String**| The attribute name | |
| **filter** | **String**| A comma-separated list of context filters. This endpoint only accepts &#x60;kind&#x60; filters, with the &#x60;equals&#x60; operator, and &#x60;value&#x60; filters, with the &#x60;startsWith&#x60; operator. To learn more about the filter syntax, read [Filtering contexts and context instances](/tag/Contexts#filtering-contexts-and-context-instances). | [optional] |

### Return type

[**ContextAttributeValuesCollection**](ContextAttributeValuesCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Context attribute values collection response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |

<a name="getContextInstances"></a>
# **getContextInstances**
> ContextInstances getContextInstances(projectKey, environmentKey, id, limit, continuationToken, sort, filter, includeTotalCount)

Get context instances

Get context instances by ID.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ContextsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ContextsApi apiInstance = new ContextsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String id = "id_example"; // String | The context instance ID
    Long limit = 56L; // Long | Specifies the maximum number of context instances to return (max: 50, default: 20)
    String continuationToken = "continuationToken_example"; // String | Limits results to context instances with sort values after the value specified. You can use this for pagination, however, we recommend using the `next` link we provide instead.
    String sort = "sort_example"; // String | Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying `ts` for this value, or descending order by specifying `-ts`.
    String filter = "filter_example"; // String | A comma-separated list of context filters. This endpoint only accepts an `applicationId` filter. To learn more about the filter syntax, read [Filtering contexts and context instances](/tag/Contexts#filtering-contexts-and-context-instances).
    Boolean includeTotalCount = true; // Boolean | Specifies whether to include or omit the total count of matching context instances. Defaults to true.
    try {
      ContextInstances result = apiInstance.getContextInstances(projectKey, environmentKey, id, limit, continuationToken, sort, filter, includeTotalCount);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#getContextInstances");
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
| **id** | **String**| The context instance ID | |
| **limit** | **Long**| Specifies the maximum number of context instances to return (max: 50, default: 20) | [optional] |
| **continuationToken** | **String**| Limits results to context instances with sort values after the value specified. You can use this for pagination, however, we recommend using the &#x60;next&#x60; link we provide instead. | [optional] |
| **sort** | **String**| Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying &#x60;ts&#x60; for this value, or descending order by specifying &#x60;-ts&#x60;. | [optional] |
| **filter** | **String**| A comma-separated list of context filters. This endpoint only accepts an &#x60;applicationId&#x60; filter. To learn more about the filter syntax, read [Filtering contexts and context instances](/tag/Contexts#filtering-contexts-and-context-instances). | [optional] |
| **includeTotalCount** | **Boolean**| Specifies whether to include or omit the total count of matching context instances. Defaults to true. | [optional] |

### Return type

[**ContextInstances**](ContextInstances.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Context instances collection response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |

<a name="getContextKindsByProjectKey"></a>
# **getContextKindsByProjectKey**
> ContextKindsCollectionRep getContextKindsByProjectKey(projectKey)

Get context kinds

Get all context kinds for a given project.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ContextsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ContextsApi apiInstance = new ContextsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    try {
      ContextKindsCollectionRep result = apiInstance.getContextKindsByProjectKey(projectKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#getContextKindsByProjectKey");
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

### Return type

[**ContextKindsCollectionRep**](ContextKindsCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Context kinds collection response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |

<a name="getContexts"></a>
# **getContexts**
> Contexts getContexts(projectKey, environmentKey, kind, key, limit, continuationToken, sort, filter, includeTotalCount)

Get contexts

Get contexts based on kind and key.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ContextsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ContextsApi apiInstance = new ContextsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    String kind = "kind_example"; // String | The context kind
    String key = "key_example"; // String | The context key
    Long limit = 56L; // Long | Specifies the maximum number of items in the collection to return (max: 50, default: 20)
    String continuationToken = "continuationToken_example"; // String | Limits results to contexts with sort values after the value specified. You can use this for pagination, however, we recommend using the `next` link we provide instead.
    String sort = "sort_example"; // String | Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying `ts` for this value, or descending order by specifying `-ts`.
    String filter = "filter_example"; // String | A comma-separated list of context filters. This endpoint only accepts an `applicationId` filter. To learn more about the filter syntax, read [Filtering contexts and context instances](/tag/Contexts#filtering-contexts-and-context-instances).
    Boolean includeTotalCount = true; // Boolean | Specifies whether to include or omit the total count of matching contexts. Defaults to true.
    try {
      Contexts result = apiInstance.getContexts(projectKey, environmentKey, kind, key, limit, continuationToken, sort, filter, includeTotalCount);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#getContexts");
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
| **kind** | **String**| The context kind | |
| **key** | **String**| The context key | |
| **limit** | **Long**| Specifies the maximum number of items in the collection to return (max: 50, default: 20) | [optional] |
| **continuationToken** | **String**| Limits results to contexts with sort values after the value specified. You can use this for pagination, however, we recommend using the &#x60;next&#x60; link we provide instead. | [optional] |
| **sort** | **String**| Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying &#x60;ts&#x60; for this value, or descending order by specifying &#x60;-ts&#x60;. | [optional] |
| **filter** | **String**| A comma-separated list of context filters. This endpoint only accepts an &#x60;applicationId&#x60; filter. To learn more about the filter syntax, read [Filtering contexts and context instances](/tag/Contexts#filtering-contexts-and-context-instances). | [optional] |
| **includeTotalCount** | **Boolean**| Specifies whether to include or omit the total count of matching contexts. Defaults to true. | [optional] |

### Return type

[**Contexts**](Contexts.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Contexts collection response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |

<a name="putContextKind"></a>
# **putContextKind**
> UpsertResponseRep putContextKind(projectKey, key, upsertContextKindPayload)

Create or update context kind

Create or update a context kind by key. Only the included fields will be updated.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ContextsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ContextsApi apiInstance = new ContextsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String key = "key_example"; // String | The context kind key
    UpsertContextKindPayload upsertContextKindPayload = new UpsertContextKindPayload(); // UpsertContextKindPayload | 
    try {
      UpsertResponseRep result = apiInstance.putContextKind(projectKey, key, upsertContextKindPayload);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#putContextKind");
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
| **key** | **String**| The context kind key | |
| **upsertContextKindPayload** | [**UpsertContextKindPayload**](UpsertContextKindPayload.md)|  | |

### Return type

[**UpsertResponseRep**](UpsertResponseRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Context kind upsert response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |

<a name="searchContextInstances"></a>
# **searchContextInstances**
> ContextInstances searchContextInstances(projectKey, environmentKey, contextInstanceSearch, limit, continuationToken, sort, filter, includeTotalCount)

Search for context instances

 Search for context instances.  You can use either the query parameters or the request body parameters. If both are provided, there is an error.  To learn more about the filter syntax, read [Filtering contexts and context instances](/tag/Contexts#filtering-contexts-and-context-instances). To learn more about context instances, read [Context instances](https://docs.launchdarkly.com/home/observability/multi-contexts#context-instances). 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ContextsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ContextsApi apiInstance = new ContextsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    ContextInstanceSearch contextInstanceSearch = new ContextInstanceSearch(); // ContextInstanceSearch | 
    Long limit = 56L; // Long | Specifies the maximum number of items in the collection to return (max: 50, default: 20)
    String continuationToken = "continuationToken_example"; // String | Limits results to context instances with sort values after the value specified. You can use this for pagination, however, we recommend using the `next` link we provide instead.
    String sort = "sort_example"; // String | Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying `ts` for this value, or descending order by specifying `-ts`.
    String filter = "filter_example"; // String | A comma-separated list of context filters. This endpoint only accepts an `applicationId` filter. To learn more about the filter syntax, read [Filtering contexts and context instances](/tag/Contexts#filtering-contexts-and-context-instances).
    Boolean includeTotalCount = true; // Boolean | Specifies whether to include or omit the total count of matching context instances. Defaults to true.
    try {
      ContextInstances result = apiInstance.searchContextInstances(projectKey, environmentKey, contextInstanceSearch, limit, continuationToken, sort, filter, includeTotalCount);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#searchContextInstances");
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
| **contextInstanceSearch** | [**ContextInstanceSearch**](ContextInstanceSearch.md)|  | |
| **limit** | **Long**| Specifies the maximum number of items in the collection to return (max: 50, default: 20) | [optional] |
| **continuationToken** | **String**| Limits results to context instances with sort values after the value specified. You can use this for pagination, however, we recommend using the &#x60;next&#x60; link we provide instead. | [optional] |
| **sort** | **String**| Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying &#x60;ts&#x60; for this value, or descending order by specifying &#x60;-ts&#x60;. | [optional] |
| **filter** | **String**| A comma-separated list of context filters. This endpoint only accepts an &#x60;applicationId&#x60; filter. To learn more about the filter syntax, read [Filtering contexts and context instances](/tag/Contexts#filtering-contexts-and-context-instances). | [optional] |
| **includeTotalCount** | **Boolean**| Specifies whether to include or omit the total count of matching context instances. Defaults to true. | [optional] |

### Return type

[**ContextInstances**](ContextInstances.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Context instances collection response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |

<a name="searchContexts"></a>
# **searchContexts**
> Contexts searchContexts(projectKey, environmentKey, contextSearch, limit, continuationToken, sort, filter, includeTotalCount)

Search for contexts

 Search for contexts.  You can use either the query parameters or the request body parameters. If both are provided, there is an error.  To learn more about the filter syntax, read [Filtering contexts and context instances](/tag/Contexts#filtering-contexts-and-context-instances). To learn more about contexts, read [Contexts and context kinds](https://docs.launchdarkly.com/home/observability/contexts#contexts-and-context-kinds). 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.ContextsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    ContextsApi apiInstance = new ContextsApi(defaultClient);
    String projectKey = "projectKey_example"; // String | The project key
    String environmentKey = "environmentKey_example"; // String | The environment key
    ContextSearch contextSearch = new ContextSearch(); // ContextSearch | 
    Long limit = 56L; // Long | Specifies the maximum number of items in the collection to return (max: 50, default: 20)
    String continuationToken = "continuationToken_example"; // String | Limits results to contexts with sort values after the value specified. You can use this for pagination, however, we recommend using the `next` link we provide instead.
    String sort = "sort_example"; // String | Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying `ts` for this value, or descending order by specifying `-ts`.
    String filter = "filter_example"; // String | A comma-separated list of context filters. To learn more about the filter syntax, read [Filtering contexts and context instances](/tag/Contexts#filtering-contexts-and-context-instances).
    Boolean includeTotalCount = true; // Boolean | Specifies whether to include or omit the total count of matching contexts. Defaults to true.
    try {
      Contexts result = apiInstance.searchContexts(projectKey, environmentKey, contextSearch, limit, continuationToken, sort, filter, includeTotalCount);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#searchContexts");
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
| **contextSearch** | [**ContextSearch**](ContextSearch.md)|  | |
| **limit** | **Long**| Specifies the maximum number of items in the collection to return (max: 50, default: 20) | [optional] |
| **continuationToken** | **String**| Limits results to contexts with sort values after the value specified. You can use this for pagination, however, we recommend using the &#x60;next&#x60; link we provide instead. | [optional] |
| **sort** | **String**| Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying &#x60;ts&#x60; for this value, or descending order by specifying &#x60;-ts&#x60;. | [optional] |
| **filter** | **String**| A comma-separated list of context filters. To learn more about the filter syntax, read [Filtering contexts and context instances](/tag/Contexts#filtering-contexts-and-context-instances). | [optional] |
| **includeTotalCount** | **Boolean**| Specifies whether to include or omit the total count of matching contexts. Defaults to true. | [optional] |

### Return type

[**Contexts**](Contexts.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Contexts collection response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |

