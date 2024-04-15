

# FeatureFlag


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | A human-friendly name for the feature flag |  |
|**kind** | [**KindEnum**](#KindEnum) | Kind of feature flag |  |
|**description** | **String** | Description of the feature flag |  [optional] |
|**key** | **String** | A unique key used to reference the flag in your code |  |
|**version** | **Integer** | Version of the feature flag |  |
|**creationDate** | **Long** |  |  |
|**includeInSnippet** | **Boolean** | Deprecated, use &lt;code&gt;clientSideAvailability&lt;/code&gt;. Whether this flag should be made available to the client-side JavaScript SDK |  [optional] |
|**clientSideAvailability** | [**ClientSideAvailability**](ClientSideAvailability.md) |  |  [optional] |
|**variations** | [**List&lt;Variation&gt;**](Variation.md) | An array of possible variations for the flag |  |
|**temporary** | **Boolean** | Whether the flag is a temporary flag |  |
|**tags** | **List&lt;String&gt;** | Tags for the feature flag |  |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  |
|**maintainerId** | **String** | Associated maintainerId for the feature flag |  [optional] |
|**maintainer** | [**MemberSummary**](MemberSummary.md) |  |  [optional] |
|**maintainerTeamKey** | **String** | The key of the associated team that maintains this feature flag |  [optional] |
|**maintainerTeam** | [**MaintainerTeam**](MaintainerTeam.md) |  |  [optional] |
|**goalIds** | **List&lt;String&gt;** | Deprecated, use &lt;code&gt;experiments&lt;/code&gt; instead |  [optional] |
|**experiments** | [**ExperimentInfoRep**](ExperimentInfoRep.md) |  |  |
|**customProperties** | [**Map&lt;String, CustomProperty&gt;**](CustomProperty.md) |  |  |
|**archived** | **Boolean** | Boolean indicating if the feature flag is archived |  |
|**archivedDate** | **Long** |  |  [optional] |
|**deprecated** | **Boolean** | Boolean indicating if the feature flag is deprecated |  |
|**deprecatedDate** | **Long** |  |  [optional] |
|**defaults** | [**Defaults**](Defaults.md) |  |  [optional] |
|**purpose** | **String** |  |  [optional] |
|**migrationSettings** | [**FlagMigrationSettingsRep**](FlagMigrationSettingsRep.md) |  |  [optional] |
|**environments** | [**Map&lt;String, FeatureFlagConfig&gt;**](FeatureFlagConfig.md) | Details on the environments for this flag. Only returned if the request is filtered by environment, using the &lt;code&gt;filterEnv&lt;/code&gt; query parameter. |  |



## Enum: KindEnum

| Name | Value |
|---- | -----|
| BOOLEAN | &quot;boolean&quot; |
| MULTIVARIATE | &quot;multivariate&quot; |



