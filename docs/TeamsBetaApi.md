# TeamsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**patchTeams**](TeamsBetaApi.md#patchTeams) | **PATCH** /api/v2/teams | Update teams |


<a name="patchTeams"></a>
# **patchTeams**
> BulkEditTeamsRep patchTeams(teamsPatchInput)

Update teams

Perform a partial update to multiple teams. Updating teams uses the semantic patch format.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](/reference#updates-using-semantic-patch).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating teams.  #### addMembersToTeams  Add the members to teams.  ##### Parameters  - &#x60;memberIDs&#x60;: List of member IDs to add. - &#x60;teamKeys&#x60;: List of teams to update.  #### addAllMembersToTeams  Add all members to the team. Members that match any of the filters are excluded from the update.  ##### Parameters  - &#x60;teamKeys&#x60;: List of teams to update. - &#x60;filterLastSeen&#x60;: (Optional) A JSON object with one of the following formats:   - &#x60;{\&quot;never\&quot;: true}&#x60; - Members that have never been active, such as those who have not accepted their invitation to LaunchDarkly, or have not logged in after being provisioned via SCIM.   - &#x60;{\&quot;noData\&quot;: true}&#x60; - Members that have not been active since LaunchDarkly began recording last seen timestamps.   - &#x60;{\&quot;before\&quot;: 1608672063611}&#x60; - Members that have not been active since the provided value, which should be a timestamp in Unix epoch milliseconds. - &#x60;filterQuery&#x60;: (Optional) A string that matches against the members&#39; emails and names. It is not case sensitive. - &#x60;filterRoles&#x60;: (Optional) A &#x60;|&#x60; separated list of roles and custom roles. For the purposes of this filtering, &#x60;Owner&#x60; counts as &#x60;Admin&#x60;. - &#x60;filterTeamKey&#x60;: (Optional) A string that matches against the key of the team the members belong to. It is not case sensitive. - &#x60;ignoredMemberIDs&#x60;: (Optional) A list of member IDs. 

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
    TeamsPatchInput teamsPatchInput = new TeamsPatchInput(); // TeamsPatchInput | 
    try {
      BulkEditTeamsRep result = apiInstance.patchTeams(teamsPatchInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsBetaApi#patchTeams");
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
| **teamsPatchInput** | [**TeamsPatchInput**](TeamsPatchInput.md)|  | |

### Return type

[**BulkEditTeamsRep**](BulkEditTeamsRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Teams response |  -  |
| **400** | Invalid request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **409** | Status conflict |  -  |
| **429** | Rate limited |  -  |

