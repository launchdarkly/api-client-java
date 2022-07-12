# OAuth2ClientsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createOAuth2Client**](OAuth2ClientsBetaApi.md#createOAuth2Client) | **POST** /api/v2/oauth/clients | Create a LaunchDarkly OAuth 2.0 client |
| [**deleteOAuthClient**](OAuth2ClientsBetaApi.md#deleteOAuthClient) | **DELETE** /api/v2/oauth/clients/{clientId} | Delete OAuth 2.0 client |
| [**getOAuthClientById**](OAuth2ClientsBetaApi.md#getOAuthClientById) | **GET** /api/v2/oauth/clients/{clientId} | Get client by ID |
| [**getOAuthClients**](OAuth2ClientsBetaApi.md#getOAuthClients) | **GET** /api/v2/oauth/clients | Get clients |
| [**patchOAuthClient**](OAuth2ClientsBetaApi.md#patchOAuthClient) | **PATCH** /api/v2/oauth/clients/{clientId} | Patch client by ID |


<a name="createOAuth2Client"></a>
# **createOAuth2Client**
> Client createOAuth2Client(oauthClientPost)

Create a LaunchDarkly OAuth 2.0 client

Create (register) a LaunchDarkly OAuth2 client. OAuth2 clients allow you to build custom integrations using LaunchDarkly as your identity provider.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.OAuth2ClientsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    OAuth2ClientsBetaApi apiInstance = new OAuth2ClientsBetaApi(defaultClient);
    OauthClientPost oauthClientPost = new OauthClientPost(); // OauthClientPost | 
    try {
      Client result = apiInstance.createOAuth2Client(oauthClientPost);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OAuth2ClientsBetaApi#createOAuth2Client");
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
| **oauthClientPost** | [**OauthClientPost**](OauthClientPost.md)|  | |

### Return type

[**Client**](Client.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Successful OAuth 2.0 client creation response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |

<a name="deleteOAuthClient"></a>
# **deleteOAuthClient**
> deleteOAuthClient(clientId)

Delete OAuth 2.0 client

Delete an existing OAuth 2.0 client by unique client ID.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.OAuth2ClientsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    OAuth2ClientsBetaApi apiInstance = new OAuth2ClientsBetaApi(defaultClient);
    String clientId = "clientId_example"; // String | The client ID
    try {
      apiInstance.deleteOAuthClient(clientId);
    } catch (ApiException e) {
      System.err.println("Exception when calling OAuth2ClientsBetaApi#deleteOAuthClient");
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
| **clientId** | **String**| The client ID | |

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

<a name="getOAuthClientById"></a>
# **getOAuthClientById**
> Client getOAuthClientById(clientId)

Get client by ID

Get a registered OAuth 2.0 client by unique client ID.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.OAuth2ClientsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    OAuth2ClientsBetaApi apiInstance = new OAuth2ClientsBetaApi(defaultClient);
    String clientId = "clientId_example"; // String | The client ID
    try {
      Client result = apiInstance.getOAuthClientById(clientId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OAuth2ClientsBetaApi#getOAuthClientById");
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
| **clientId** | **String**| The client ID | |

### Return type

[**Client**](Client.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OAuth 2.0 client response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |

<a name="getOAuthClients"></a>
# **getOAuthClients**
> ClientCollection getOAuthClients()

Get clients

Get all OAuth 2.0 clients registered by your account.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.OAuth2ClientsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    OAuth2ClientsBetaApi apiInstance = new OAuth2ClientsBetaApi(defaultClient);
    try {
      ClientCollection result = apiInstance.getOAuthClients();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OAuth2ClientsBetaApi#getOAuthClients");
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

[**ClientCollection**](ClientCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OAuth 2.0 client collection response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |

<a name="patchOAuthClient"></a>
# **patchOAuthClient**
> Client patchOAuthClient(clientId, patchOperation)

Patch client by ID

Patch an existing OAuth 2.0 client by client ID. Requires a [JSON Patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes to the client. Only &#x60;name&#x60;, &#x60;description&#x60;, and &#x60;redirectUri&#x60; may be patched.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.OAuth2ClientsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    OAuth2ClientsBetaApi apiInstance = new OAuth2ClientsBetaApi(defaultClient);
    String clientId = "clientId_example"; // String | The client ID
    List<PatchOperation> patchOperation = Arrays.asList(); // List<PatchOperation> | 
    try {
      Client result = apiInstance.patchOAuthClient(clientId, patchOperation);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OAuth2ClientsBetaApi#patchOAuthClient");
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
| **clientId** | **String**| The client ID | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**Client**](Client.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |

