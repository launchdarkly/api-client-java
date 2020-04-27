# TeamMembersApi

All URIs are relative to *https://app.launchdarkly.com/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteMember**](TeamMembersApi.md#deleteMember) | **DELETE** /members/{memberId} | Delete a team member by ID.
[**getMe**](TeamMembersApi.md#getMe) | **GET** /members/me | Get the current team member associated with the token
[**getMember**](TeamMembersApi.md#getMember) | **GET** /members/{memberId} | Get a single team member by ID.
[**getMembers**](TeamMembersApi.md#getMembers) | **GET** /members | Returns a list of all members in the account.
[**patchMember**](TeamMembersApi.md#patchMember) | **PATCH** /members/{memberId} | Modify a team member by ID.
[**postMembers**](TeamMembersApi.md#postMembers) | **POST** /members | Invite new members.


<a name="deleteMember"></a>
# **deleteMember**
> deleteMember(memberId)

Delete a team member by ID.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.TeamMembersApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

TeamMembersApi apiInstance = new TeamMembersApi();
String memberId = "memberId_example"; // String | The member ID.
try {
    apiInstance.deleteMember(memberId);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamMembersApi#deleteMember");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **memberId** | **String**| The member ID. |

### Return type

null (empty response body)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMe"></a>
# **getMe**
> Member getMe()

Get the current team member associated with the token

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.TeamMembersApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

TeamMembersApi apiInstance = new TeamMembersApi();
try {
    Member result = apiInstance.getMe();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamMembersApi#getMe");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**Member**](Member.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMember"></a>
# **getMember**
> Member getMember(memberId)

Get a single team member by ID.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.TeamMembersApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

TeamMembersApi apiInstance = new TeamMembersApi();
String memberId = "memberId_example"; // String | The member ID.
try {
    Member result = apiInstance.getMember(memberId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamMembersApi#getMember");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **memberId** | **String**| The member ID. |

### Return type

[**Member**](Member.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMembers"></a>
# **getMembers**
> Members getMembers(limit, number, filter, sort)

Returns a list of all members in the account.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.TeamMembersApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

TeamMembersApi apiInstance = new TeamMembersApi();
BigDecimal limit = new BigDecimal(); // BigDecimal | The number of objects to return. Defaults to -1, which returns everything.
Boolean number = true; // Boolean | Where to start in the list. This is for use with pagination. For example, an offset of 10 would skip the first 10 items and then return the next limit items.
String filter = "filter_example"; // String | A comma-separated list of filters. Each filter is of the form field:value.
String sort = "sort_example"; // String | A comma-separated list of fields to sort by. A field prefixed by a - will be sorted in descending order.
try {
    Members result = apiInstance.getMembers(limit, number, filter, sort);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamMembersApi#getMembers");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **limit** | **BigDecimal**| The number of objects to return. Defaults to -1, which returns everything. | [optional]
 **number** | **Boolean**| Where to start in the list. This is for use with pagination. For example, an offset of 10 would skip the first 10 items and then return the next limit items. | [optional]
 **filter** | **String**| A comma-separated list of filters. Each filter is of the form field:value. | [optional]
 **sort** | **String**| A comma-separated list of fields to sort by. A field prefixed by a - will be sorted in descending order. | [optional]

### Return type

[**Members**](Members.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="patchMember"></a>
# **patchMember**
> Member patchMember(memberId, patchDelta)

Modify a team member by ID.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.TeamMembersApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

TeamMembersApi apiInstance = new TeamMembersApi();
String memberId = "memberId_example"; // String | The member ID.
List<PatchOperation> patchDelta = Arrays.asList(new PatchOperation()); // List<PatchOperation> | Requires a JSON Patch representation of the desired changes to the project. 'http://jsonpatch.com/'
try {
    Member result = apiInstance.patchMember(memberId, patchDelta);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamMembersApi#patchMember");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **memberId** | **String**| The member ID. |
 **patchDelta** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)| Requires a JSON Patch representation of the desired changes to the project. &#39;http://jsonpatch.com/&#39; |

### Return type

[**Member**](Member.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="postMembers"></a>
# **postMembers**
> Members postMembers(membersBody)

Invite new members.

### Example
```java
// Import classes:
//import com.launchdarkly.api.ApiClient;
//import com.launchdarkly.api.ApiException;
//import com.launchdarkly.api.Configuration;
//import com.launchdarkly.api.auth.*;
//import com.launchdarkly.api.api.TeamMembersApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Token
ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Token.setApiKeyPrefix("Token");

TeamMembersApi apiInstance = new TeamMembersApi();
List<MembersBody> membersBody = Arrays.asList(new MembersBody()); // List<MembersBody> | New members to invite.
try {
    Members result = apiInstance.postMembers(membersBody);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamMembersApi#postMembers");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **membersBody** | [**List&lt;MembersBody&gt;**](MembersBody.md)| New members to invite. |

### Return type

[**Members**](Members.md)

### Authorization

[Token](../README.md#Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

