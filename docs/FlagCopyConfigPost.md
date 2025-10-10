

# FlagCopyConfigPost


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**source** | [**FlagCopyConfigEnvironment**](FlagCopyConfigEnvironment.md) |  |  |
|**target** | [**FlagCopyConfigEnvironment**](FlagCopyConfigEnvironment.md) |  |  |
|**comment** | **String** | Optional comment |  [optional] |
|**includedActions** | [**List&lt;IncludedActionsEnum&gt;**](#List&lt;IncludedActionsEnum&gt;) | Optional list of the flag changes to copy from the source environment to the target environment. You may include either &lt;code&gt;includedActions&lt;/code&gt; or &lt;code&gt;excludedActions&lt;/code&gt;, but not both. If you include neither, then all flag changes will be copied. |  [optional] |
|**excludedActions** | [**List&lt;ExcludedActionsEnum&gt;**](#List&lt;ExcludedActionsEnum&gt;) | Optional list of the flag changes NOT to copy from the source environment to the target environment. You may include either  &lt;code&gt;includedActions&lt;/code&gt; or &lt;code&gt;excludedActions&lt;/code&gt;, but not both. If you include neither, then all flag changes will be copied. |  [optional] |



## Enum: List&lt;IncludedActionsEnum&gt;

| Name | Value |
|---- | -----|
| UPDATE_ON | &quot;updateOn&quot; |
| UPDATE_RULES | &quot;updateRules&quot; |
| UPDATE_FALLTHROUGH | &quot;updateFallthrough&quot; |
| UPDATE_OFF_VARIATION | &quot;updateOffVariation&quot; |
| UPDATE_PREREQUISITES | &quot;updatePrerequisites&quot; |
| UPDATE_TARGETS | &quot;updateTargets&quot; |
| UPDATE_FLAG_CONFIG_MIGRATION_SETTINGS | &quot;updateFlagConfigMigrationSettings&quot; |



## Enum: List&lt;ExcludedActionsEnum&gt;

| Name | Value |
|---- | -----|
| UPDATE_ON | &quot;updateOn&quot; |
| UPDATE_RULES | &quot;updateRules&quot; |
| UPDATE_FALLTHROUGH | &quot;updateFallthrough&quot; |
| UPDATE_OFF_VARIATION | &quot;updateOffVariation&quot; |
| UPDATE_PREREQUISITES | &quot;updatePrerequisites&quot; |
| UPDATE_TARGETS | &quot;updateTargets&quot; |
| UPDATE_FLAG_CONFIG_MIGRATION_SETTINGS | &quot;updateFlagConfigMigrationSettings&quot; |



