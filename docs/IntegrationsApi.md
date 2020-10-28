# IntegrationsApi

All URIs are relative to *https://app.launchdarkly.com/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteIntegrationSubscription**](IntegrationsApi.md#deleteIntegrationSubscription) | **DELETE** /integrations/{integrationKey}/{integrationId} | Delete an integration subscription by ID.
[**getIntegrationSubscription**](IntegrationsApi.md#getIntegrationSubscription) | **GET** /integrations/{integrationKey}/{integrationId} | Get a single integration subscription by ID.
[**getIntegrationSubscriptions**](IntegrationsApi.md#getIntegrationSubscriptions) | **GET** /integrations/{integrationKey} | Get a list of all configured integrations of a given kind.
[**getIntegrations**](IntegrationsApi.md#getIntegrations) | **GET** /integrations | Get a list of all configured audit log event integrations associated with this account.
[**patchIntegrationSubscription**](IntegrationsApi.md#patchIntegrationSubscription) | **PATCH** /integrations/{integrationKey}/{integrationId} | Modify an integration subscription by ID.
[**postIntegrationSubscription**](IntegrationsApi.md#postIntegrationSubscription) | **POST** /integrations/{integrationKey} | Create a new integration subscription of a given kind.


<a name="deleteIntegrationSubscription"></a>
# **deleteIntegrationSubscription**
> deleteIntegrationSubscription(integrationKey, integrationId)

Delete an integration subscription by ID.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.IntegrationsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

IntegrationsApi apiInstance = new IntegrationsApi();
String integrationKey = "integrationKey_example"; // String | The key used to specify the integration kind.
String integrationId = "integrationId_example"; // String | The integration ID.
try {
    apiInstance.deleteIntegrationSubscription(integrationKey, integrationId);
} catch (ApiException e) {
    System.err.println("Exception when calling IntegrationsApi#deleteIntegrationSubscription");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **integrationKey** | **String**| The key used to specify the integration kind. |
 **integrationId** | **String**| The integration ID. |

### Return type

null (empty response body)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getIntegrationSubscription"></a>
# **getIntegrationSubscription**
> IntegrationSubscription getIntegrationSubscription(integrationKey, integrationId)

Get a single integration subscription by ID.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.IntegrationsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

IntegrationsApi apiInstance = new IntegrationsApi();
String integrationKey = "integrationKey_example"; // String | The key used to specify the integration kind.
String integrationId = "integrationId_example"; // String | The integration ID.
try {
    IntegrationSubscription result = apiInstance.getIntegrationSubscription(integrationKey, integrationId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IntegrationsApi#getIntegrationSubscription");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **integrationKey** | **String**| The key used to specify the integration kind. |
 **integrationId** | **String**| The integration ID. |

### Return type

[**IntegrationSubscription**](IntegrationSubscription.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getIntegrationSubscriptions"></a>
# **getIntegrationSubscriptions**
> Integration getIntegrationSubscriptions(integrationKey)

Get a list of all configured integrations of a given kind.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.IntegrationsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

IntegrationsApi apiInstance = new IntegrationsApi();
String integrationKey = "integrationKey_example"; // String | The key used to specify the integration kind.
try {
    Integration result = apiInstance.getIntegrationSubscriptions(integrationKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IntegrationsApi#getIntegrationSubscriptions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **integrationKey** | **String**| The key used to specify the integration kind. |

### Return type

[**Integration**](Integration.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getIntegrations"></a>
# **getIntegrations**
> Integrations getIntegrations()

Get a list of all configured audit log event integrations associated with this account.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.IntegrationsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

IntegrationsApi apiInstance = new IntegrationsApi();
try {
    Integrations result = apiInstance.getIntegrations();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IntegrationsApi#getIntegrations");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**Integrations**](Integrations.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="patchIntegrationSubscription"></a>
# **patchIntegrationSubscription**
> IntegrationSubscription patchIntegrationSubscription(integrationKey, integrationId, patchDelta)

Modify an integration subscription by ID.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.IntegrationsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

IntegrationsApi apiInstance = new IntegrationsApi();
String integrationKey = "integrationKey_example"; // String | The key used to specify the integration kind.
String integrationId = "integrationId_example"; // String | The integration ID.
List<PatchOperation> patchDelta = Arrays.asList(new PatchOperation()); // List<PatchOperation> | Requires a JSON Patch representation of the desired changes to the project. 'http://jsonpatch.com/'
try {
    IntegrationSubscription result = apiInstance.patchIntegrationSubscription(integrationKey, integrationId, patchDelta);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IntegrationsApi#patchIntegrationSubscription");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **integrationKey** | **String**| The key used to specify the integration kind. |
 **integrationId** | **String**| The integration ID. |
 **patchDelta** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)| Requires a JSON Patch representation of the desired changes to the project. &#39;http://jsonpatch.com/&#39; |

### Return type

[**IntegrationSubscription**](IntegrationSubscription.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="postIntegrationSubscription"></a>
# **postIntegrationSubscription**
> IntegrationSubscription postIntegrationSubscription(integrationKey, subscriptionBody)

Create a new integration subscription of a given kind.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.IntegrationsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

IntegrationsApi apiInstance = new IntegrationsApi();
String integrationKey = "integrationKey_example"; // String | The key used to specify the integration kind.
SubscriptionBody subscriptionBody = new SubscriptionBody(); // SubscriptionBody | Create a new integration subscription.
try {
    IntegrationSubscription result = apiInstance.postIntegrationSubscription(integrationKey, subscriptionBody);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IntegrationsApi#postIntegrationSubscription");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **integrationKey** | **String**| The key used to specify the integration kind. |
 **subscriptionBody** | [**SubscriptionBody**](SubscriptionBody.md)| Create a new integration subscription. |

### Return type

[**IntegrationSubscription**](IntegrationSubscription.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

