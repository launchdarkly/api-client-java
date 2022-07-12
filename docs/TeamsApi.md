# TeamsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteTeam**](TeamsApi.md#deleteTeam) | **DELETE** /api/v2/teams/{teamKey} | Delete team |
| [**getTeam**](TeamsApi.md#getTeam) | **GET** /api/v2/teams/{teamKey} | Get team |
| [**getTeamMaintainers**](TeamsApi.md#getTeamMaintainers) | **GET** /api/v2/teams/{teamKey}/maintainers | Get team maintainers |
| [**getTeamRoles**](TeamsApi.md#getTeamRoles) | **GET** /api/v2/teams/{teamKey}/roles | Get team custom roles |
| [**getTeams**](TeamsApi.md#getTeams) | **GET** /api/v2/teams | List teams |
| [**patchTeam**](TeamsApi.md#patchTeam) | **PATCH** /api/v2/teams/{teamKey} | Update team |
| [**postTeam**](TeamsApi.md#postTeam) | **POST** /api/v2/teams | Create team |
| [**postTeamMembers**](TeamsApi.md#postTeamMembers) | **POST** /api/v2/teams/{teamKey}/members | Add multiple members to team |


<a name="deleteTeam"></a>
# **deleteTeam**
> deleteTeam(teamKey)

Delete team

Delete a team by key. To learn more, read [Deleting a team](https://docs.launchdarkly.com/home/teams/managing#deleting-a-team).

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.TeamsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    TeamsApi apiInstance = new TeamsApi(defaultClient);
    String teamKey = "teamKey_example"; // String | The team key
    try {
      apiInstance.deleteTeam(teamKey);
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#deleteTeam");
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
| **teamKey** | **String**| The team key | |

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
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |
| **429** | Rate limited |  -  |

<a name="getTeam"></a>
# **getTeam**
> Team getTeam(teamKey, expand)

Get team

Fetch a team by key.  ### Expanding the teams response LaunchDarkly supports four fields for expanding the \&quot;Get team\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  * &#x60;members&#x60; includes the total count of members that belong to the team. * &#x60;roles&#x60; includes a paginated list of the custom roles that you have assigned to the team. * &#x60;projects&#x60; includes a paginated list of the projects that the team has any write access to. * &#x60;maintainers&#x60; includes a paginated list of the maintainers that you have assigned to the team.  For example, &#x60;expand&#x3D;members,roles&#x60; includes the &#x60;members&#x60; and &#x60;roles&#x60; fields in the response. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.TeamsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    TeamsApi apiInstance = new TeamsApi(defaultClient);
    String teamKey = "teamKey_example"; // String | The team key.
    String expand = "expand_example"; // String | A comma-separated list of properties that can reveal additional information in the response.
    try {
      Team result = apiInstance.getTeam(teamKey, expand);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#getTeam");
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
| **teamKey** | **String**| The team key. | |
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. | [optional] |

### Return type

[**Team**](Team.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Teams response JSON |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **405** | Method not allowed |  -  |
| **429** | Rate limited |  -  |

<a name="getTeamMaintainers"></a>
# **getTeamMaintainers**
> TeamMaintainers getTeamMaintainers(teamKey, limit, offset)

Get team maintainers

Fetch the maintainers that have been assigned to the team. To learn more, read [Managing team maintainers](https://docs.launchdarkly.com/home/teams/managing#managing-team-maintainers).

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.TeamsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    TeamsApi apiInstance = new TeamsApi(defaultClient);
    String teamKey = "teamKey_example"; // String | The team key
    Long limit = 56L; // Long | The number of maintainers to return in the response. Defaults to 20.
    Long offset = 56L; // Long | Where to start in the list. This is for use with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    try {
      TeamMaintainers result = apiInstance.getTeamMaintainers(teamKey, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#getTeamMaintainers");
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
| **teamKey** | **String**| The team key | |
| **limit** | **Long**| The number of maintainers to return in the response. Defaults to 20. | [optional] |
| **offset** | **Long**| Where to start in the list. This is for use with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |

### Return type

[**TeamMaintainers**](TeamMaintainers.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Team maintainers response JSON |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **405** | Method not allowed |  -  |
| **429** | Rate limited |  -  |

<a name="getTeamRoles"></a>
# **getTeamRoles**
> TeamCustomRoles getTeamRoles(teamKey, limit, offset)

Get team custom roles

Fetch the custom roles that have been assigned to the team. To learn more, read [Managing team permissions](https://docs.launchdarkly.com/home/teams/managing#managing-team-permissions).

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.TeamsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    TeamsApi apiInstance = new TeamsApi(defaultClient);
    String teamKey = "teamKey_example"; // String | The team key
    Long limit = 56L; // Long | The number of roles to return in the response. Defaults to 20.
    Long offset = 56L; // Long | Where to start in the list. This is for use with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    try {
      TeamCustomRoles result = apiInstance.getTeamRoles(teamKey, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#getTeamRoles");
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
| **teamKey** | **String**| The team key | |
| **limit** | **Long**| The number of roles to return in the response. Defaults to 20. | [optional] |
| **offset** | **Long**| Where to start in the list. This is for use with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |

### Return type

[**TeamCustomRoles**](TeamCustomRoles.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Team roles response JSON |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Invalid resource identifier |  -  |
| **405** | Method not allowed |  -  |
| **429** | Rate limited |  -  |

<a name="getTeams"></a>
# **getTeams**
> Teams getTeams(limit, offset, filter, expand)

List teams

Return a list of teams.  By default, this returns the first 20 teams. Page through this list with the &#x60;limit&#x60; parameter and by following the &#x60;first&#x60;, &#x60;prev&#x60;, &#x60;next&#x60;, and &#x60;last&#x60; links in the &#x60;_links&#x60; field that returns. If those links do not appear, the pages they refer to don&#39;t exist. For example, the &#x60;first&#x60; and &#x60;prev&#x60; links will be missing from the response on the first page, because there is no previous page and you cannot return to the first page when you are already on the first page.  ### Filtering teams  LaunchDarkly supports the &#x60;query&#x60; field for filtering. &#x60;query&#x60; is a string that matches against the teams&#39; names and keys. For example, the filter &#x60;query:abc&#x60; matches teams with the string &#x60;abc&#x60; in their name or key. The filter is not case-sensitive.  ### Expanding the teams response LaunchDarkly supports four fields for expanding the \&quot;List teams\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  * &#x60;members&#x60; includes the total count of members that belong to the team. * &#x60;roles&#x60; includes a paginated list of the custom roles that you have assigned to the team. * &#x60;projects&#x60; includes a paginated list of the projects that the team has any write access to. * &#x60;maintainers&#x60; includes a paginated list of the maintainers that you have assigned to the team.  For example, &#x60;expand&#x3D;members,roles&#x60; includes the &#x60;members&#x60; and &#x60;roles&#x60; fields in the response. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.TeamsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    TeamsApi apiInstance = new TeamsApi(defaultClient);
    Long limit = 56L; // Long | The number of teams to return in the response. Defaults to 20.
    Long offset = 56L; // Long | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and returns the next `limit` items.
    String filter = "filter_example"; // String | A comma-separated list of filters. Each filter is constructed as `field:value`.
    String expand = "expand_example"; // String | A comma-separated list of properties that can reveal additional information in the response.
    try {
      Teams result = apiInstance.getTeams(limit, offset, filter, expand);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#getTeams");
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
| **limit** | **Long**| The number of teams to return in the response. Defaults to 20. | [optional] |
| **offset** | **Long**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and returns the next &#x60;limit&#x60; items. | [optional] |
| **filter** | **String**| A comma-separated list of filters. Each filter is constructed as &#x60;field:value&#x60;. | [optional] |
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. | [optional] |

### Return type

[**Teams**](Teams.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Teams collection response JSON |  -  |
| **401** | Invalid access token |  -  |
| **405** | Method not allowed |  -  |
| **429** | Rate limited |  -  |

<a name="patchTeam"></a>
# **patchTeam**
> Team patchTeam(teamKey, teamPatchInput, expand)

Update team

Perform a partial update to a team. Updating a team uses the semantic patch format.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating teams.  #### addCustomRoles  Adds custom roles to the team. Team members will have these custom roles granted to them.  ##### Parameters  - &#x60;values&#x60;: List of custom role keys.  #### removeCustomRoles  Removes custom roles from the team. The app will no longer grant these custom roles to the team members.  ##### Parameters  - &#x60;values&#x60;: List of custom role keys.  #### addMembers  Adds members to the team.  ##### Parameters  - &#x60;values&#x60;: List of member IDs.  #### removeMembers  Removes members from the team.  ##### Parameters  - &#x60;values&#x60;: List of member IDs.  #### addPermissionGrants  Adds permission grants to members for the team. For example, a permission grant could allow a member to act as a team maintainer. A permission grant may have either an &#x60;actionSet&#x60; or a list of &#x60;actions&#x60; but not both at the same time. The members do not have to be team members to have a permission grant for the team.  ##### Parameters  - &#x60;actionSet&#x60;: Name of the action set. - &#x60;actions&#x60;: List of actions. - &#x60;memberIDs&#x60;: List of member IDs.  #### removePermissionGrants  Removes permission grants from members for the team. The &#x60;actionSet&#x60; and &#x60;actions&#x60; must match an existing permission grant.  ##### Parameters  - &#x60;actionSet&#x60;: Name of the action set. - &#x60;actions&#x60;: List of actions. - &#x60;memberIDs&#x60;: List of member IDs.  #### updateDescription  Updates the description of the team.  ##### Parameters  - &#x60;value&#x60;: The new description.  #### updateName  Updates the name of the team.  ##### Parameters  - &#x60;value&#x60;: The new name.  ### Expanding the teams response LaunchDarkly supports four fields for expanding the \&quot;Update team\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  * &#x60;members&#x60; includes the total count of members that belong to the team. * &#x60;roles&#x60; includes a paginated list of the custom roles that you have assigned to the team. * &#x60;projects&#x60; includes a paginated list of the projects that the team has any write access to. * &#x60;maintainers&#x60; includes a paginated list of the maintainers that you have assigned to the team.  For example, &#x60;expand&#x3D;members,roles&#x60; includes the &#x60;members&#x60; and &#x60;roles&#x60; fields in the response. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.TeamsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    TeamsApi apiInstance = new TeamsApi(defaultClient);
    String teamKey = "teamKey_example"; // String | The team key
    TeamPatchInput teamPatchInput = new TeamPatchInput(); // TeamPatchInput | 
    String expand = "expand_example"; // String | A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above.
    try {
      Team result = apiInstance.patchTeam(teamKey, teamPatchInput, expand);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#patchTeam");
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
| **teamKey** | **String**| The team key | |
| **teamPatchInput** | [**TeamPatchInput**](TeamPatchInput.md)|  | |
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above. | [optional] |

### Return type

[**Team**](Team.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Teams response JSON |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **404** | Invalid resource identifier |  -  |
| **405** | Method not allowed |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

<a name="postTeam"></a>
# **postTeam**
> Team postTeam(teamPostInput, expand)

Create team

Create a team. To learn more, read [Creating a team](https://docs.launchdarkly.com/home/teams/creating).  ### Expanding the teams response LaunchDarkly supports four fields for expanding the \&quot;Create team\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  * &#x60;members&#x60; includes the total count of members that belong to the team. * &#x60;roles&#x60; includes a paginated list of the custom roles that you have assigned to the team. * &#x60;projects&#x60; includes a paginated list of the projects that the team has any write access to. * &#x60;maintainers&#x60; includes a paginated list of the maintainers that you have assigned to the team.  For example, &#x60;expand&#x3D;members,roles&#x60; includes the &#x60;members&#x60; and &#x60;roles&#x60; fields in the response. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.TeamsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    TeamsApi apiInstance = new TeamsApi(defaultClient);
    TeamPostInput teamPostInput = new TeamPostInput(); // TeamPostInput | 
    String expand = "expand_example"; // String | A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above.
    try {
      Team result = apiInstance.postTeam(teamPostInput, expand);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#postTeam");
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
| **teamPostInput** | [**TeamPostInput**](TeamPostInput.md)|  | |
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above. | [optional] |

### Return type

[**Team**](Team.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Successful teams response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **405** | Method not allowed |  -  |
| **429** | Rate limited |  -  |

<a name="postTeamMembers"></a>
# **postTeamMembers**
> TeamImportsRep postTeamMembers(teamKey, _file)

Add multiple members to team

Add multiple members to an existing team by uploading a CSV file of member email addresses. Your CSV file must include email addresses in the first column. You can include data in additional columns, but LaunchDarkly ignores all data outside the first column. Headers are optional. To learn more, read [Managing team members](https://docs.launchdarkly.com/home/teams/managing#managing-team-members).  **Members are only added on a &#x60;201&#x60; response.** A &#x60;207&#x60; indicates the CSV file contains a combination of valid and invalid entries. A &#x60;207&#x60; results in no members being added to the team.  On a &#x60;207&#x60; response, if an entry contains bad user input, the &#x60;message&#x60; field contains the row number as well as the reason for the error. The &#x60;message&#x60; field is omitted if the entry is valid.  Example &#x60;207&#x60; response: &#x60;&#x60;&#x60;json {   \&quot;items\&quot;: [     {       \&quot;status\&quot;: \&quot;success\&quot;,       \&quot;value\&quot;: \&quot;a-valid-email@launchdarkly.com\&quot;     },     {       \&quot;message\&quot;: \&quot;Line 2: empty row\&quot;,       \&quot;status\&quot;: \&quot;error\&quot;,       \&quot;value\&quot;: \&quot;\&quot;     },     {       \&quot;message\&quot;: \&quot;Line 3: email already exists in the specified team\&quot;,       \&quot;status\&quot;: \&quot;error\&quot;,       \&quot;value\&quot;: \&quot;existing-team-member@launchdarkly.com\&quot;     },     {       \&quot;message\&quot;: \&quot;Line 4: invalid email formatting\&quot;,       \&quot;status\&quot;: \&quot;error\&quot;,       \&quot;value\&quot;: \&quot;invalid email format\&quot;     }   ] } &#x60;&#x60;&#x60;  Message | Resolution --- | --- Empty row | This line is blank. Add an email address and try again. Duplicate entry | This email address appears in the file twice. Remove the email from the file and try again. Email already exists in the specified team | This member is already on your team. Remove the email from the file and try again. Invalid formatting | This email address is not formatted correctly. Fix the formatting and try again. Email does not belong to a LaunchDarkly member | The email address doesn&#39;t belong to a LaunchDarkly account member. Invite them to LaunchDarkly, then re-add them to the team.  On a &#x60;400&#x60; response, the &#x60;message&#x60; field may contain errors specific to this endpoint.  Example &#x60;400&#x60; response: &#x60;&#x60;&#x60;json {   \&quot;code\&quot;: \&quot;invalid_request\&quot;,   \&quot;message\&quot;: \&quot;Unable to process file\&quot; } &#x60;&#x60;&#x60;  Message | Resolution --- | --- Unable to process file | LaunchDarkly could not process the file for an unspecified reason. Review your file for errors and try again. File exceeds 25mb | Break up your file into multiple files of less than 25mbs each. All emails have invalid formatting | None of the email addresses in the file are in the correct format. Fix the formatting and try again. All emails belong to existing team members | All listed members are already on this team. Populate the file with member emails that do not belong to the team and try again. File is empty | The CSV file does not contain any email addresses. Populate the file and try again. No emails belong to members of your LaunchDarkly organization | None of the email addresses belong to members of your LaunchDarkly account. Invite these members to LaunchDarkly, then re-add them to the team. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.TeamsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    TeamsApi apiInstance = new TeamsApi(defaultClient);
    String teamKey = "teamKey_example"; // String | The team key
    File _file = new File("/path/to/file"); // File | CSV file containing email addresses
    try {
      TeamImportsRep result = apiInstance.postTeamMembers(teamKey, _file);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#postTeamMembers");
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
| **teamKey** | **String**| The team key | |
| **_file** | **File**| CSV file containing email addresses | [optional] |

### Return type

[**TeamImportsRep**](TeamImportsRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Team member imports response JSON |  -  |
| **207** | Partial Success |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **405** | Method not allowed |  -  |
| **429** | Rate limited |  -  |
