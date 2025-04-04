

# FeatureFlagBody


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | A human-friendly name for the feature flag |  |
|**key** | **String** | A unique key used to reference the flag in your code |  |
|**description** | **String** | Description of the feature flag. Defaults to an empty string. |  [optional] |
|**includeInSnippet** | **Boolean** | Deprecated, use &lt;code&gt;clientSideAvailability&lt;/code&gt;. Whether this flag should be made available to the client-side JavaScript SDK. Defaults to &lt;code&gt;false&lt;/code&gt;. |  [optional] |
|**clientSideAvailability** | [**ClientSideAvailabilityPost**](ClientSideAvailabilityPost.md) |  |  [optional] |
|**variations** | [**List&lt;Variation&gt;**](Variation.md) | An array of possible variations for the flag. The variation values must be unique. If omitted, two boolean variations of &lt;code&gt;true&lt;/code&gt; and &lt;code&gt;false&lt;/code&gt; will be used. |  [optional] |
|**temporary** | **Boolean** | Whether the flag is a temporary flag. Defaults to &lt;code&gt;true&lt;/code&gt;. |  [optional] |
|**tags** | **List&lt;String&gt;** | Tags for the feature flag. Defaults to an empty array. |  [optional] |
|**customProperties** | [**Map&lt;String, CustomProperty&gt;**](CustomProperty.md) |  |  [optional] |
|**defaults** | [**Defaults**](Defaults.md) |  |  [optional] |
|**purpose** | [**PurposeEnum**](#PurposeEnum) | Purpose of the flag |  [optional] |
|**migrationSettings** | [**MigrationSettingsPost**](MigrationSettingsPost.md) |  |  [optional] |
|**maintainerId** | **String** | The ID of the member who maintains this feature flag |  [optional] |
|**maintainerTeamKey** | **String** | The key of the team that maintains this feature flag |  [optional] |
|**initialPrerequisites** | [**List&lt;FlagPrerequisitePost&gt;**](FlagPrerequisitePost.md) | Initial set of prerequisite flags for all environments |  [optional] |



## Enum: PurposeEnum

| Name | Value |
|---- | -----|
| MIGRATION | &quot;migration&quot; |
| HOLDOUT | &quot;holdout&quot; |



