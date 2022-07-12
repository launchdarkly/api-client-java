

# Environment


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | Links to related resources. |  |
|**id** | **String** |  |  |
|**key** | **String** | A project-unique key for the new environment. |  |
|**name** | **String** | A human-friendly name for the new environment. |  |
|**apiKey** | **String** | API key to use with client-side SDKs. |  |
|**mobileKey** | **String** | API key to use with mobile SDKs. |  |
|**color** | **String** | The color used to indicate this environment in the UI. |  |
|**defaultTtl** | **Integer** | The default time (in minutes) that the PHP SDK can cache feature flag rules locally. |  |
|**secureMode** | **Boolean** | Secure mode ensures that a user of the client-side SDK cannot impersonate another user. |  |
|**defaultTrackEvents** | **Boolean** | Enables tracking detailed information for new flags by default. |  |
|**requireComments** | **Boolean** |  |  |
|**confirmChanges** | **Boolean** |  |  |
|**tags** | **List&lt;String&gt;** |  |  |
|**approvalSettings** | [**ApprovalSettings**](ApprovalSettings.md) |  |  [optional] |



