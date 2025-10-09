# OtherApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getCallerIdentity**](OtherApi.md#getCallerIdentity) | **GET** /api/v2/caller-identity | Identify the caller |
| [**getIps**](OtherApi.md#getIps) | **GET** /api/v2/public-ip-list | Gets the public IP list |
| [**getOpenapiSpec**](OtherApi.md#getOpenapiSpec) | **GET** /api/v2/openapi.json | Gets the OpenAPI spec in json |
| [**getRoot**](OtherApi.md#getRoot) | **GET** /api/v2 | Root resource |
| [**getVersions**](OtherApi.md#getVersions) | **GET** /api/v2/versions | Get version information |


<a id="getCallerIdentity"></a>
# **getCallerIdentity**
> CallerIdentityRep getCallerIdentity()

Identify the caller

Get basic information about the identity used (session cookie, API token, SDK keys, etc.) to call the API

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.OtherApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    OtherApi apiInstance = new OtherApi(defaultClient);
    try {
      CallerIdentityRep result = apiInstance.getCallerIdentity();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OtherApi#getCallerIdentity");
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

[**CallerIdentityRep**](CallerIdentityRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Caller Identity |  -  |
| **401** | Invalid access token |  -  |
| **429** | Rate limited |  -  |

<a id="getIps"></a>
# **getIps**
> IpList getIps()

Gets the public IP list

Get a list of IP ranges the LaunchDarkly service uses. You can use this list to allow LaunchDarkly through your firewall. We post upcoming changes to this list in advance on our [status page](https://status.launchdarkly.com/). &lt;br /&gt;&lt;br /&gt;In the sandbox, click &#39;Play&#39; and enter any string in the &#39;Authorization&#39; field to test this endpoint.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.OtherApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    OtherApi apiInstance = new OtherApi(defaultClient);
    try {
      IpList result = apiInstance.getIps();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OtherApi#getIps");
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

[**IpList**](IpList.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Public IP response |  -  |
| **429** | Rate limited |  -  |

<a id="getOpenapiSpec"></a>
# **getOpenapiSpec**
> getOpenapiSpec()

Gets the OpenAPI spec in json

Get the latest version of the OpenAPI specification for LaunchDarkly&#39;s API in JSON format. In the sandbox, click &#39;Play&#39; and enter any string in the &#39;Authorization&#39; field to test this endpoint.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.OtherApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    OtherApi apiInstance = new OtherApi(defaultClient);
    try {
      apiInstance.getOpenapiSpec();
    } catch (ApiException e) {
      System.err.println("Exception when calling OtherApi#getOpenapiSpec");
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

null (empty response body)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OpenAPI Spec |  -  |
| **429** | Rate limited |  -  |

<a id="getRoot"></a>
# **getRoot**
> RootResponse getRoot()

Root resource

Get all of the resource categories the API supports. In the sandbox, click &#39;Play&#39; and enter any string in the &#39;Authorization&#39; field to test this endpoint.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.OtherApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    OtherApi apiInstance = new OtherApi(defaultClient);
    try {
      RootResponse result = apiInstance.getRoot();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OtherApi#getRoot");
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

[**RootResponse**](RootResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Root response |  -  |
| **429** | Rate limited |  -  |

<a id="getVersions"></a>
# **getVersions**
> VersionsRep getVersions()

Get version information

Get the latest API version, the list of valid API versions in ascending order, and the version being used for this request. These are all in the external, date-based format.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.OtherApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    OtherApi apiInstance = new OtherApi(defaultClient);
    try {
      VersionsRep result = apiInstance.getVersions();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OtherApi#getVersions");
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

[**VersionsRep**](VersionsRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Versions information response |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **429** | Rate limited |  -  |

