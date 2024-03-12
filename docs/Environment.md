

# Environment


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  |
|**id** | **String** | The ID for the environment. Use this as the client-side ID for authorization in some client-side SDKs, and to associate LaunchDarkly environments with CDN integrations in edge SDKs. |  |
|**key** | **String** | A project-unique key for the new environment |  |
|**name** | **String** | A human-friendly name for the new environment |  |
|**apiKey** | **String** | The SDK key for the environment. Use this for authorization in server-side SDKs. |  |
|**mobileKey** | **String** | The mobile key for the environment. Use this for authorization in mobile SDKs. |  |
|**color** | **String** | The color used to indicate this environment in the UI |  |
|**defaultTtl** | **Integer** | The default time (in minutes) that the PHP SDK can cache feature flag rules locally |  |
|**secureMode** | **Boolean** | Ensures that one end user of the client-side SDK cannot inspect the variations for another end user |  |
|**defaultTrackEvents** | **Boolean** | Enables tracking detailed information for new flags by default |  |
|**requireComments** | **Boolean** | Whether members who modify flags and segments through the LaunchDarkly user interface are required to add a comment |  |
|**confirmChanges** | **Boolean** | Whether members who modify flags and segments through the LaunchDarkly user interface are required to confirm those changes |  |
|**tags** | **List&lt;String&gt;** | A list of tags for this environment |  |
|**approvalSettings** | [**ApprovalSettings**](ApprovalSettings.md) |  |  [optional] |
|**critical** | **Boolean** | Whether the environment is critical |  |



