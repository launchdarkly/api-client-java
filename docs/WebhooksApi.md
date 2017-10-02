# WebhooksApi

All URIs are relative to *https://app.launchdarkly.com/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteWebhook**](WebhooksApi.md#deleteWebhook) | **DELETE** /webhooks/{resourceId} | Delete a webhook by ID
[**getWebhook**](WebhooksApi.md#getWebhook) | **GET** /webhooks/{resourceId} | Get a webhook by ID
[**getWebhooks**](WebhooksApi.md#getWebhooks) | **GET** /webhooks | Fetch a list of all webhooks
[**patchWebhook**](WebhooksApi.md#patchWebhook) | **PATCH** /webhooks/{resourceId} | Modify a webhook by ID
[**postWebhook**](WebhooksApi.md#postWebhook) | **POST** /webhooks | Create a webhook


<a name="deleteWebhook"></a>
# **deleteWebhook**
> deleteWebhook(resourceId)

Delete a webhook by ID

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.WebhooksApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

WebhooksApi apiInstance = new WebhooksApi();
String resourceId = "resourceId_example"; // String | The resource ID
try {
    apiInstance.deleteWebhook(resourceId);
} catch (ApiException e) {
    System.err.println("Exception when calling WebhooksApi#deleteWebhook");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **resourceId** | **String**| The resource ID |

### Return type

null (empty response body)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getWebhook"></a>
# **getWebhook**
> Webhook getWebhook(resourceId)

Get a webhook by ID

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.WebhooksApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

WebhooksApi apiInstance = new WebhooksApi();
String resourceId = "resourceId_example"; // String | The resource ID
try {
    Webhook result = apiInstance.getWebhook(resourceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WebhooksApi#getWebhook");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **resourceId** | **String**| The resource ID |

### Return type

[**Webhook**](Webhook.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getWebhooks"></a>
# **getWebhooks**
> Webhooks getWebhooks()

Fetch a list of all webhooks

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.WebhooksApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

WebhooksApi apiInstance = new WebhooksApi();
try {
    Webhooks result = apiInstance.getWebhooks();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WebhooksApi#getWebhooks");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**Webhooks**](Webhooks.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="patchWebhook"></a>
# **patchWebhook**
> Webhook patchWebhook(resourceId, patchDelta)

Modify a webhook by ID

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.WebhooksApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

WebhooksApi apiInstance = new WebhooksApi();
String resourceId = "resourceId_example"; // String | The resource ID
List<PatchDelta> patchDelta = Arrays.asList(new PatchDelta()); // List<PatchDelta> | http://jsonpatch.com/
try {
    Webhook result = apiInstance.patchWebhook(resourceId, patchDelta);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WebhooksApi#patchWebhook");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **resourceId** | **String**| The resource ID |
 **patchDelta** | [**List&lt;PatchDelta&gt;**](PatchDelta.md)| http://jsonpatch.com/ |

### Return type

[**Webhook**](Webhook.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="postWebhook"></a>
# **postWebhook**
> postWebhook(webhookBody)

Create a webhook

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.WebhooksApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

WebhooksApi apiInstance = new WebhooksApi();
WebhookBody webhookBody = new WebhookBody(); // WebhookBody | New webhook
try {
    apiInstance.postWebhook(webhookBody);
} catch (ApiException e) {
    System.err.println("Exception when calling WebhooksApi#postWebhook");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **webhookBody** | [**WebhookBody**](WebhookBody.md)| New webhook |

### Return type

null (empty response body)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

