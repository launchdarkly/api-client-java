# AccountMembersApi

All URIs are relative to *https://app.launchdarkly.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteMember**](AccountMembersApi.md#deleteMember) | **DELETE** /api/v2/members/{id} | Delete account member
[**getMember**](AccountMembersApi.md#getMember) | **GET** /api/v2/members/{id} | Get account member
[**getMembers**](AccountMembersApi.md#getMembers) | **GET** /api/v2/members | List account members
[**patchMember**](AccountMembersApi.md#patchMember) | **PATCH** /api/v2/members/{id} | Modify an account member
[**postMembers**](AccountMembersApi.md#postMembers) | **POST** /api/v2/members | Invite new members


<a name="deleteMember"></a>
# **deleteMember**
> deleteMember(id)

Delete account member

Delete a single account member by ID

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountMembersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountMembersApi apiInstance = new AccountMembersApi(defaultClient);
    String id = "id_example"; // String | The member ID
    try {
      apiInstance.deleteMember(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountMembersApi#deleteMember");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The member ID |

### Return type

null (empty response body)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**204** | Action completed successfully |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**409** | Status conflict |  -  |
**429** | Rate limited |  -  |

<a name="getMember"></a>
# **getMember**
> Member getMember(id)

Get account member

Get a single account member by ID

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountMembersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountMembersApi apiInstance = new AccountMembersApi(defaultClient);
    String id = "id_example"; // String | The member ID
    try {
      Member result = apiInstance.getMember(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountMembersApi#getMember");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The member ID |

### Return type

[**Member**](Member.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Member response JSON |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

<a name="getMembers"></a>
# **getMembers**
> Members getMembers(limit, offset, filter, sort)

List account members

Return a list of account members.  By default, this returns the first 20 members. Page through this list with the &#x60;limit&#x60; parameter and by following the &#x60;first&#x60;, &#x60;prev&#x60;, &#x60;next&#x60;, and &#x60;last&#x60; links in the returned &#x60;_links&#x60; field. These links are not present if the pages they refer to don&#39;t exist. For example, the &#x60;first&#x60; and &#x60;prev&#x60; links will be missing from the response on the first page.  ### Filtering members  LaunchDarkly supports three fields for filters: &#x60;query&#x60;, &#x60;role&#x60;, and &#x60;lastSeen&#x60;:  - &#x60;query&#x60; is a string that matches against the members&#39; emails and names. It is not case sensitive. - &#x60;role&#x60; is a &#x60;|&#x60; separated list of roles and custom roles. It filters the list to members who have any of the roles in the list. For the purposes of this filtering, &#x60;Owner&#x60; counts as &#x60;Admin&#x60;. - &#x60;lastSeen&#x60; is a JSON object in one of the following formats:   - &#x60;{\&quot;never\&quot;: true}&#x60; - Members that have never been active, such as those who have not accepted their invitation to LaunchDarkly, or have not logged in after being provisioned via SCIM.   - &#x60;{\&quot;noData\&quot;: true}&#x60; - Members that have not been active since LaunchDarkly began recording last seen timestamps.   - &#x60;{\&quot;before\&quot;: 1608672063611}&#x60; - Members that have not been active since the provided value, which should be a timestamp in Unix epoch milliseconds.  For example, the filter &#x60;query:abc,role:admin|customrole&#x60; matches members with the string &#x60;abc&#x60; in their email or name, ignoring case, who also are either an an &#x60;Owner&#x60; or &#x60;Admin&#x60; or have the custom role &#x60;customrole&#x60;.  ### Sorting members  LaunchDarkly supports two fields for sorting: &#x60;displayName&#x60; and &#x60;lastSeen&#x60;:  - &#x60;displayName&#x60; sorts by first + last name, using the member&#39;s email if no name is set. - &#x60;lastSeen&#x60; sorts by the &#x60;_lastSeen&#x60; property. LaunchDarkly considers members that have never been seen or have no data the oldest. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountMembersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountMembersApi apiInstance = new AccountMembersApi(defaultClient);
    Long limit = 56L; // Long | The number of members to return in the response. Defaults to 20.
    Long offset = 56L; // Long | Where to start in the list. This is for use with pagination. For example, an offset of 10 would skip the first ten items and then return the next `limit` items.
    String filter = "filter_example"; // String | A comma-separated list of filters. Each filter is of the form `field:value`. Supported fields are explained below.
    String sort = "sort_example"; // String | A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order.
    try {
      Members result = apiInstance.getMembers(limit, offset, filter, sort);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountMembersApi#getMembers");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **limit** | **Long**| The number of members to return in the response. Defaults to 20. | [optional]
 **offset** | **Long**| Where to start in the list. This is for use with pagination. For example, an offset of 10 would skip the first ten items and then return the next &#x60;limit&#x60; items. | [optional]
 **filter** | **String**| A comma-separated list of filters. Each filter is of the form &#x60;field:value&#x60;. Supported fields are explained below. | [optional]
 **sort** | **String**| A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order. | [optional]

### Return type

[**Members**](Members.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Member collection response JSON |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**429** | Rate limited |  -  |

<a name="patchMember"></a>
# **patchMember**
> Member patchMember(id, patchOperation)

Modify an account member

Update a single account member. The request should be a valid JSON Patch document describing the changes to be made to the member.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountMembersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountMembersApi apiInstance = new AccountMembersApi(defaultClient);
    String id = "id_example"; // String | The member ID
    List<PatchOperation> patchOperation = Arrays.asList(); // List<PatchOperation> | 
    try {
      Member result = apiInstance.patchMember(id, patchOperation);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountMembersApi#patchMember");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The member ID |
 **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  |

### Return type

[**Member**](Member.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Member response JSON |  -  |
**400** | Invalid request body |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**404** | Invalid resource identifier |  -  |
**409** | Status conflict |  -  |
**429** | Rate limited |  -  |

<a name="postMembers"></a>
# **postMembers**
> Members postMembers(inlineObject1)

Invite new members

&gt; ### Full use of this API resource is only available to accounts with paid subscriptions &gt; &gt; The ability to bulk invite members is a paid feature. Single members may be invited if not on a paid plan.  Invite one or more new members to join an account. Each member is sent an invitation. Members with \&quot;admin\&quot; or \&quot;owner\&quot; roles may create new members, as well as anyone with a \&quot;createMember\&quot; permission for \&quot;member/\\*\&quot;. If a member cannot be invited, the entire request is rejected and no members are invited from that request.  Each member _must_ have an &#x60;email&#x60; field and either a &#x60;role&#x60; or a &#x60;customRoles&#x60; field. If any of the fields are not populated correctly, the request is rejected with the reason specified in the \&quot;message\&quot; field of the response.  _No more than 50 members may be created per request._  A request may also fail because of conflicts with existing members. These conflicts are reported using the additional &#x60;code&#x60; and &#x60;invalid_emails&#x60; response fields with the following possible values for &#x60;code&#x60;:  - **email_already_exists_in_account**: A member with this email address already exists in this account. - **email_taken_in_different_account**: A member with this email address exists in another account. - **duplicate_email**s: This request contains two or more members with the same email address.  A request that fails for one of the above reasons returns an HTTP response code of 400 (Bad Request). 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.AccountMembersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    AccountMembersApi apiInstance = new AccountMembersApi(defaultClient);
    List<InlineObject1> inlineObject1 = Arrays.asList(); // List<InlineObject1> | 
    try {
      Members result = apiInstance.postMembers(inlineObject1);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountMembersApi#postMembers");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **inlineObject1** | [**List&lt;InlineObject1&gt;**](InlineObject1.md)|  |

### Return type

[**Members**](Members.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | Members response |  -  |
**400** | Invalid request body |  -  |
**401** | Invalid access token |  -  |
**403** | Forbidden |  -  |
**409** | Status conflict |  -  |
**429** | Rate limited |  -  |

