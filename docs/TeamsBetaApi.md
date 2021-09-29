# TeamsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteTeam**](TeamsBetaApi.md#deleteTeam) | **DELETE** /api/v2/teams/{key} | Delete team by key
[**getTeam**](TeamsBetaApi.md#getTeam) | **GET** /api/v2/teams/{key} | Get team by key
[**getTeams**](TeamsBetaApi.md#getTeams) | **GET** /api/v2/teams | Get all teams
[**patchTeam**](TeamsBetaApi.md#patchTeam) | **PATCH** /api/v2/teams/{key} | Patch team by key
[**postTeam**](TeamsBetaApi.md#postTeam) | **POST** /api/v2/teams | Create team


<a name="deleteTeam"></a>
# **deleteTeam**
> deleteTeam(key)

Delete team by key

Delete a team by key.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.TeamsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    TeamsBetaApi apiInstance = new TeamsBetaApi(defaultClient);
    String key = "key_example"; // String | The team key
    try {
      apiInstance.deleteTeam(key);
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsBetaApi#deleteTeam");
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
 **key** | **String**| The team key |

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
**404** | Unknown team key |  -  |
**429** | Rate limited |  -  |

<a name="getTeam"></a>
# **getTeam**
> TeamRep getTeam(key)

Get team by key

Fetch a team by key.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.TeamsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    TeamsBetaApi apiInstance = new TeamsBetaApi(defaultClient);
    String key = "key_example"; // String | The team key
    try {
      TeamRep result = apiInstance.getTeam(key);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsBetaApi#getTeam");
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
 **key** | **String**| The team key |

### Return type

[**TeamRep**](TeamRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Team response JSON |  -  |
**400** | Invalid request body |  -  |
**401** | Invalid access token |  -  |
**404** | Unknown team key |  -  |
**405** | Method not allowed |  -  |
**429** | Rate limited |  -  |

<a name="getTeams"></a>
# **getTeams**
> TeamCollectionRep getTeams()

Get all teams

Fetch all teams.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.TeamsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    TeamsBetaApi apiInstance = new TeamsBetaApi(defaultClient);
    try {
      TeamCollectionRep result = apiInstance.getTeams();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsBetaApi#getTeams");
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

[**TeamCollectionRep**](TeamCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Teams collection response JSON |  -  |
**401** | Invalid access token |  -  |
**405** | Method not allowed |  -  |
**429** | Rate limited |  -  |

<a name="patchTeam"></a>
# **patchTeam**
> TeamCollectionRep patchTeam(key, teamPatchInput)

Patch team by key

Perform a partial update to a team.  The body of a semantic patch request takes the following three properties:  1. comment &#x60;string&#x60;: (Optional) A description of the update. 1. environmentKey &#x60;string&#x60;: (Required) The key of the LaunchDarkly environment. 1. instructions &#x60;array&#x60;: (Required) The action or list of actions to be performed by the update. Each update action in the list must be an object/hash table with a &#x60;kind&#x60; property, although depending on the action, other properties may be necessary. Read below for more information on the specific supported semantic patch instructions.  If any instruction in the patch encounters an error, the error will be returned and the flag will not be changed. In general, instructions will silently do nothing if the flag is already in the state requested by the patch instruction. They will generally error if a parameter refers to something that does not exist. Other specific error conditions are noted in the instruction descriptions.  ### Instructions  #### &#x60;addCustomRoles&#x60;  Adds custom roles to the team. Team members will have these custom roles granted to them.  ##### Parameters  - &#x60;values&#x60;: list of custom role keys  #### &#x60;removeCustomRoles&#x60;  Removes the custom roles on the team. Team members will no longer have these custom roles granted to them.  ##### Parameters  - &#x60;values&#x60;: list of custom role keys  #### &#x60;addMembers&#x60;  Adds members to the team.  ##### Parameters  - &#x60;values&#x60;: list of member IDs  #### &#x60;removeMembers&#x60;  Removes members from the team.  ##### Parameters  - &#x60;values&#x60;: list of member IDs  #### &#x60;addPermissionGrants&#x60;  Adds permission grants to members for the team, allowing them to, for example, act as a team maintainer. A permission grant may have either an &#x60;actionSet&#x60; or a list of &#x60;actions&#x60; but not both at the same time. The members do not have to be team members to have a permission grant for the team.  ##### Parameters  - &#x60;actionSet&#x60;: name of the action set - &#x60;actions&#x60;: list of actions - &#x60;memberIDs&#x60;: list of member IDs  #### &#x60;removePermissionGrants&#x60;  Removes permission grants from members for the team. The &#x60;actionSet&#x60; and &#x60;actions&#x60; must match an existing permission grant.  ##### Parameters  - &#x60;actionSet&#x60;: name of the action set - &#x60;actions&#x60;: list of actions - &#x60;memberIDs&#x60;: list of member IDs  #### &#x60;updateDescription&#x60;  Updates the team&#39;s description.  ##### Parameters  - &#x60;value&#x60;: the team&#39;s new description  #### &#x60;updateName&#x60;  Updates the team&#39;s name.  ##### Parameters  - &#x60;value&#x60;: the team&#39;s new name 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.TeamsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    TeamsBetaApi apiInstance = new TeamsBetaApi(defaultClient);
    String key = "key_example"; // String | The team key
    TeamPatchInput teamPatchInput = new TeamPatchInput(); // TeamPatchInput | 
    try {
      TeamCollectionRep result = apiInstance.patchTeam(key, teamPatchInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsBetaApi#patchTeam");
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
 **key** | **String**| The team key |
 **teamPatchInput** | [**TeamPatchInput**](TeamPatchInput.md)|  |

### Return type

[**TeamCollectionRep**](TeamCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Teams collection response JSON |  -  |
**400** | Invalid request body |  -  |
**401** | Invalid access token |  -  |
**404** | Unknown team key |  -  |
**405** | Method not allowed |  -  |
**409** | Status conflict |  -  |
**429** | Rate limited |  -  |

<a name="postTeam"></a>
# **postTeam**
> TeamRep postTeam(teamPostInput)

Create team

Create a team.

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.TeamsBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    TeamsBetaApi apiInstance = new TeamsBetaApi(defaultClient);
    TeamPostInput teamPostInput = new TeamPostInput(); // TeamPostInput | 
    try {
      TeamRep result = apiInstance.postTeam(teamPostInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsBetaApi#postTeam");
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
 **teamPostInput** | [**TeamPostInput**](TeamPostInput.md)|  |

### Return type

[**TeamRep**](TeamRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | Successful teams response |  -  |
**400** | Invalid request body |  -  |
**401** | Invalid access token |  -  |
**405** | Method not allowed |  -  |
**429** | Rate limited |  -  |

