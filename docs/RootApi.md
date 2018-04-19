# RootApi

All URIs are relative to *https://app.launchdarkly.com/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getRoot**](RootApi.md#getRoot) | **GET** / | 


<a name="getRoot"></a>
# **getRoot**
> Links getRoot()



You can issue a GET request to the root resource to find all of the resource categories supported by the API.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.RootApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

RootApi apiInstance = new RootApi();
try {
    Links result = apiInstance.getRoot();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RootApi#getRoot");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**Links**](Links.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

