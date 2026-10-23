# EnvironmentReleasesBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getEnvironmentRelease**](EnvironmentReleasesBetaApi.md#getEnvironmentRelease) | **GET** /api/v2/projects/{projectKey}/flags/{flagKey}/environments/{environmentKey}/releases/{releaseId} | Get an environment release |
| [**listEnvironmentReleases**](EnvironmentReleasesBetaApi.md#listEnvironmentReleases) | **GET** /api/v2/projects/{projectKey}/flags/{flagKey}/environments/{environmentKey}/releases | List environment releases |
| [**startEnvironmentRelease**](EnvironmentReleasesBetaApi.md#startEnvironmentRelease) | **POST** /api/v2/projects/{projectKey}/flags/{flagKey}/environments/{environmentKey}/releases | Start an environment release |
| [**stopEnvironmentRelease**](EnvironmentReleasesBetaApi.md#stopEnvironmentRelease) | **PUT** /api/v2/projects/{projectKey}/flags/{flagKey}/environments/{environmentKey}/releases/{releaseId}/stop | Stop an environment release |


<a id="getEnvironmentRelease"></a>
# **getEnvironmentRelease**
> EnvironmentRelease getEnvironmentRelease(ldAPIVersion, projectKey, flagKey, environmentKey, releaseId, expand)

Get an environment release

Returns an environment release and all its phases in phase-index order. The release ID must belong to the flag and environment in the path. Returns &#x60;404&#x60; for a release that ended more than 90 days ago. Pass &#x60;expand&#x3D;metrics&#x60; to attach each metric&#39;s definition to the top-level &#x60;metrics&#x60; array. Analysis settings come from the version pinned when the release started (&#x60;metricVersionId&#x60;); name, description, tags, maintainer, and archive state reflect the current metric and may change afterwards. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.EnvironmentReleasesBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    EnvironmentReleasesBetaApi apiInstance = new EnvironmentReleasesBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "projectKey_example"; // String | 
    String flagKey = "flagKey_example"; // String | 
    String environmentKey = "environmentKey_example"; // String | 
    UUID releaseId = UUID.randomUUID(); // UUID | 
    Set<String> expand = Arrays.asList(); // Set<String> | Optional fields to expand.
    try {
      EnvironmentRelease result = apiInstance.getEnvironmentRelease(ldAPIVersion, projectKey, flagKey, environmentKey, releaseId, expand);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling EnvironmentReleasesBetaApi#getEnvironmentRelease");
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
| **ldAPIVersion** | **String**| Version of the endpoint. | [enum: beta] |
| **projectKey** | **String**|  | |
| **flagKey** | **String**|  | |
| **environmentKey** | **String**|  | |
| **releaseId** | **UUID**|  | |
| **expand** | [**Set&lt;String&gt;**](String.md)| Optional fields to expand. | [optional] [enum: metrics] |

### Return type

[**EnvironmentRelease**](EnvironmentRelease.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Environment release. |  -  |
| **400** | Bad request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **429** | Rate limited |  -  |
| **500** | Internal server error |  -  |

<a id="listEnvironmentReleases"></a>
# **listEnvironmentReleases**
> EnvironmentReleaseCollection listEnvironmentReleases(ldAPIVersion, projectKey, flagKey, environmentKey, filter, limit, offset, snapshot)

List environment releases

Returns the environment releases for the flag in the environment, newest first. Each release includes its phases in phase-index order. Metric definitions are not included. To get them, call &#x60;getEnvironmentRelease&#x60; with &#x60;expand&#x3D;metrics&#x60;. Returns an empty &#x60;items&#x60; array when the flag has no releases in the environment, or when no release matches the &#x60;filter&#x60;. Releases that ended more than 90 days ago are not returned.  Results are paginated. By default, the first 25 releases are returned. Use &#x60;limit&#x60; and &#x60;offset&#x60; to page through the rest, and &#x60;totalCount&#x60; to learn how many releases match in total. The &#x60;_links&#x60; object includes &#x60;first&#x60;, &#x60;prev&#x60;, &#x60;next&#x60;, and &#x60;last&#x60; links where they apply. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.EnvironmentReleasesBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    EnvironmentReleasesBetaApi apiInstance = new EnvironmentReleasesBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "projectKey_example"; // String | 
    String flagKey = "flagKey_example"; // String | 
    String environmentKey = "environmentKey_example"; // String | 
    String filter = "kind equals guarded"; // String | A queryfilter expression that narrows the list. Supported fields and operators: `kind` (`equals`) with values `progressive` or `guarded`. 
    Integer limit = 25; // Integer | The number of releases to return. Defaults to 25. Maximum is 100.
    Integer offset = 0; // Integer | Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten releases and then returns the next releases in the list, up to the query `limit`.
    String snapshot = "snapshot_example"; // String | Pins the read to a moment in time (nanoseconds since the Unix epoch) so pages walked in sequence reflect one consistent snapshot. Follow the pagination links rather than constructing this value. Out-of-range values are ignored.
    try {
      EnvironmentReleaseCollection result = apiInstance.listEnvironmentReleases(ldAPIVersion, projectKey, flagKey, environmentKey, filter, limit, offset, snapshot);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling EnvironmentReleasesBetaApi#listEnvironmentReleases");
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
| **ldAPIVersion** | **String**| Version of the endpoint. | [enum: beta] |
| **projectKey** | **String**|  | |
| **flagKey** | **String**|  | |
| **environmentKey** | **String**|  | |
| **filter** | **String**| A queryfilter expression that narrows the list. Supported fields and operators: &#x60;kind&#x60; (&#x60;equals&#x60;) with values &#x60;progressive&#x60; or &#x60;guarded&#x60;.  | [optional] |
| **limit** | **Integer**| The number of releases to return. Defaults to 25. Maximum is 100. | [optional] [default to 25] |
| **offset** | **Integer**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten releases and then returns the next releases in the list, up to the query &#x60;limit&#x60;. | [optional] [default to 0] |
| **snapshot** | **String**| Pins the read to a moment in time (nanoseconds since the Unix epoch) so pages walked in sequence reflect one consistent snapshot. Follow the pagination links rather than constructing this value. Out-of-range values are ignored. | [optional] |

### Return type

[**EnvironmentReleaseCollection**](EnvironmentReleaseCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Environment releases. |  -  |
| **400** | Bad request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **429** | Rate limited |  -  |
| **500** | Internal server error |  -  |

<a id="startEnvironmentRelease"></a>
# **startEnvironmentRelease**
> EnvironmentRelease startEnvironmentRelease(ldAPIVersion, projectKey, flagKey, environmentKey, startEnvironmentReleaseRequest)

Start an environment release

Creates and starts a release on a flag in an environment, either from inline configuration or from an environment release plan. A release has one or more phases. Each phase is an audience, targeted by one flag rule, and has its own steps: the schedule of allocations at which that rule serves the target variation. After all of a phase&#39;s steps complete, the phase serves the target variation to 100% of its contexts.  **Inline configuration.** Each entry in &#x60;phases&#x60; must specify exactly one targeting mode:  - **Existing rule**: set &#x60;ruleId&#x60;. The rule must be enabled. Only a   single-phase release can use an existing rule. - **New rule**: set &#x60;clauses&#x60;, with an optional &#x60;description&#x60;. New   rules are appended to the end of the flag&#39;s rule list. To insert   them before an existing rule instead, set the top-level   &#x60;beforeRuleId&#x60;, which applies to every phase.  Every phase&#39;s rule, including an existing rule passed by &#x60;ruleId&#x60;, must include &#x60;{\&quot;contextKind\&quot;: \&quot;&lt;randomizationUnit&gt;\&quot;, \&quot;attribute\&quot;: \&quot;kind\&quot;, \&quot;op\&quot;: \&quot;in\&quot;, \&quot;values\&quot;: [\&quot;&lt;randomizationUnit&gt;\&quot;]}&#x60;, not negated and with no other values, so the release only reaches contexts of the &#x60;randomizationUnit&#x60; kind, or multi-contexts containing the &#x60;randomizationUnit&#x60; kind. An existing rule needs the clause even if its other clauses only match that context kind. A phase without it returns &#x60;400&#x60;.  For multiple phases, set &#x60;phases&#x60; with at least two entries. Each phase creates a rule. The first rule is enabled and starts immediately. Subsequent rules stay disabled until the release advances to them: automatically when &#x60;advancementMode&#x60; is &#x60;automatic&#x60;, or by an explicit advance when it is &#x60;manual&#x60;. &#x60;advancementMode&#x60; is required for a multi-phase release.  Releases on the flag&#39;s default rule are not supported. A phase that omits both targeting modes is rejected.  The flag must be on in the environment, or the request returns &#x60;409&#x60;. The release only reaches contexts that match a phase&#39;s rule. Other contexts keep getting the flag&#39;s default rule, which may already serve the target variation.  The release type is inferred from &#x60;metrics&#x60;: an omitted or empty array creates a progressive release; one or more metrics creates a guarded release. Metrics must be listed individually; metric groups are not accepted.  Every start runs release health checks, described in &#x60;ReleaseHealthCheckKind&#x60;. A flag that hasn&#39;t recently been evaluated for contexts of the &#x60;randomizationUnit&#x60; kind fails &#x60;flag_evaluations_with_context&#x60;, and one with no recent evaluations at all also fails &#x60;flag_evaluations_any&#x60;. Guarded releases also check that each metric receives events. A failing check returns &#x60;400&#x60; with code &#x60;health_checks_failed&#x60;. To start the release anyway, list the failing kinds in &#x60;bypassHealthChecks&#x60;.  **From a release plan.** Set &#x60;releasePlanKey&#x60; to start from a plan in this environment instead of configuring the release inline; a request that also sets fields the plan supplies is rejected. The plan&#39;s current version runs as stored, with metric groups expanded into their members. Health checks run against the plan&#39;s randomization unit and metrics. Returns &#x60;400&#x60; if the plan is missing or incomplete, and &#x60;422&#x60; until environment release plans are available for the account.  Requires the &#x60;updateRules&#x60; action on the flag in this environment, plus &#x60;updateFallthrough&#x60; when &#x60;cleanupBehavior&#x60; is &#x60;remove_rules_and_update_default_rule&#x60;. A release started from a plan requires both. The start is recorded in the flag&#39;s audit log as a targeting change that carries &#x60;comment&#x60;.  In an environment that requires approvals, this endpoint fails with &#x60;405&#x60;. Tokens granted the &#x60;bypassRequiredApproval&#x60; action can still call it to start the release directly.  This operation is not idempotent. Retrying a request that already started a release returns &#x60;409&#x60; with code &#x60;release_already_active&#x60;. The earlier request may have succeeded; call &#x60;listEnvironmentReleases&#x60; to confirm the running release is yours. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.EnvironmentReleasesBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    EnvironmentReleasesBetaApi apiInstance = new EnvironmentReleasesBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "projectKey_example"; // String | 
    String flagKey = "flagKey_example"; // String | 
    String environmentKey = "environmentKey_example"; // String | 
    StartEnvironmentReleaseRequest startEnvironmentReleaseRequest = new StartEnvironmentReleaseRequest(); // StartEnvironmentReleaseRequest | 
    try {
      EnvironmentRelease result = apiInstance.startEnvironmentRelease(ldAPIVersion, projectKey, flagKey, environmentKey, startEnvironmentReleaseRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling EnvironmentReleasesBetaApi#startEnvironmentRelease");
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
| **ldAPIVersion** | **String**| Version of the endpoint. | [enum: beta] |
| **projectKey** | **String**|  | |
| **flagKey** | **String**|  | |
| **environmentKey** | **String**|  | |
| **startEnvironmentReleaseRequest** | [**StartEnvironmentReleaseRequest**](StartEnvironmentReleaseRequest.md)|  | |

### Return type

[**EnvironmentRelease**](EnvironmentRelease.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Environment release created and started. |  -  |
| **400** | The request is invalid. When &#x60;code&#x60; is &#x60;health_checks_failed&#x60;, &#x60;failingChecks&#x60; describes the release health checks that failed. Fix the failing checks, or list their kinds in &#x60;bypassHealthChecks&#x60; to start the release anyway.  |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **405** | Approval is required to make this change |  -  |
| **409** | The flag&#39;s current state does not allow the start. &#x60;code&#x60; is &#x60;release_already_active&#x60; when a release is already running on the flag or on the targeted rule; if this is a retry, the first request may have succeeded, so call &#x60;listEnvironmentReleases&#x60; to confirm. Any other conflict returns &#x60;optimistic_locking_error&#x60; with the cause in &#x60;message&#x60;.  |  -  |
| **422** | The request set &#x60;releasePlanKey&#x60; before environment release plans are available for the account. Configure the release inline instead.  |  -  |
| **429** | Rate limited |  -  |
| **500** | Internal server error |  -  |

<a id="stopEnvironmentRelease"></a>
# **stopEnvironmentRelease**
> EnvironmentRelease stopEnvironmentRelease(ldAPIVersion, projectKey, flagKey, environmentKey, releaseId, stopEnvironmentReleaseRequest)

Stop an environment release

Stops a release and resolves its phases according to the &#x60;resolution&#x60; you specify. To learn how each resolution treats completed and pending phases, read the &#x60;resolution&#x60; schema.  Requires the &#x60;updateRules&#x60; action on the flag in this environment, or &#x60;updateFallthrough&#x60; for a historical release that ran on the default rule. The stop is recorded in the flag&#39;s audit log as a targeting change that names the resolution and carries &#x60;comment&#x60;.  In an environment that requires approvals, this endpoint fails with &#x60;405&#x60;. Tokens granted the &#x60;bypassRequiredApproval&#x60; action can still call it to stop the release directly.  Stopping a release that has already completed or been stopped returns &#x60;200&#x60; and makes no changes. Returns &#x60;404&#x60; for a release that ended more than 90 days ago. 

### Example
```java
// Import classes:
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.auth.*;
import com.launchdarkly.api.models.*;
import com.launchdarkly.api.api.EnvironmentReleasesBetaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://app.launchdarkly.com");
    
    // Configure API key authorization: ApiKey
    ApiKeyAuth ApiKey = (ApiKeyAuth) defaultClient.getAuthentication("ApiKey");
    ApiKey.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKey.setApiKeyPrefix("Token");

    EnvironmentReleasesBetaApi apiInstance = new EnvironmentReleasesBetaApi(defaultClient);
    String ldAPIVersion = "beta"; // String | Version of the endpoint.
    String projectKey = "projectKey_example"; // String | 
    String flagKey = "flagKey_example"; // String | 
    String environmentKey = "environmentKey_example"; // String | 
    UUID releaseId = UUID.randomUUID(); // UUID | 
    StopEnvironmentReleaseRequest stopEnvironmentReleaseRequest = new StopEnvironmentReleaseRequest(); // StopEnvironmentReleaseRequest | 
    try {
      EnvironmentRelease result = apiInstance.stopEnvironmentRelease(ldAPIVersion, projectKey, flagKey, environmentKey, releaseId, stopEnvironmentReleaseRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling EnvironmentReleasesBetaApi#stopEnvironmentRelease");
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
| **ldAPIVersion** | **String**| Version of the endpoint. | [enum: beta] |
| **projectKey** | **String**|  | |
| **flagKey** | **String**|  | |
| **environmentKey** | **String**|  | |
| **releaseId** | **UUID**|  | |
| **stopEnvironmentReleaseRequest** | [**StopEnvironmentReleaseRequest**](StopEnvironmentReleaseRequest.md)|  | |

### Return type

[**EnvironmentRelease**](EnvironmentRelease.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Environment release stopped, or already in a terminal status. |  -  |
| **400** | Bad request |  -  |
| **401** | Invalid access token |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **405** | Approval is required to make this change |  -  |
| **409** | The flag&#39;s current state does not allow the stop. The response returns &#x60;optimistic_locking_error&#x60; with the cause in &#x60;message&#x60;. Resolve the cause, then retry.  |  -  |
| **429** | Rate limited |  -  |
| **500** | Internal server error |  -  |

