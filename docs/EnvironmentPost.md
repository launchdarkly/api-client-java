

# EnvironmentPost


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | A human-friendly name for the new environment |  |
|**key** | **String** | A project-unique key for the new environment |  |
|**color** | **String** | A color to indicate this environment in the UI |  |
|**defaultTtl** | **Integer** | The default time (in minutes) that the PHP SDK can cache feature flag rules locally |  [optional] |
|**secureMode** | **Boolean** | Ensures that one end user of the client-side SDK cannot inspect the variations for another end user |  [optional] |
|**defaultTrackEvents** | **Boolean** | The initial value of trackEvents for flags created in this environment. Does not affect existing flags. Required for Data Export, but doesn&#39;t enable Data Export on its own. |  [optional] |
|**confirmChanges** | **Boolean** | Requires confirmation for all flag and segment changes via the UI in this environment |  [optional] |
|**requireComments** | **Boolean** | Requires comments for all flag and segment changes via the UI in this environment |  [optional] |
|**tags** | **List&lt;String&gt;** | Tags to apply to the new environment |  [optional] |
|**source** | [**SourceEnv**](SourceEnv.md) |  |  [optional] |
|**critical** | **Boolean** | Whether the environment is critical |  [optional] |



