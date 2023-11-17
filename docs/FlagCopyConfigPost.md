

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
| UPDATEON | &quot;updateOn&quot; |
| UPDATERULES | &quot;updateRules&quot; |
| UPDATEFALLTHROUGH | &quot;updateFallthrough&quot; |
| UPDATEOFFVARIATION | &quot;updateOffVariation&quot; |
| UPDATEPREREQUISITES | &quot;updatePrerequisites&quot; |
| UPDATETARGETS | &quot;updateTargets&quot; |
| UPDATEFLAGCONFIGMIGRATIONSETTINGS | &quot;updateFlagConfigMigrationSettings&quot; |



## Enum: List&lt;ExcludedActionsEnum&gt;

| Name | Value |
|---- | -----|
| UPDATEON | &quot;updateOn&quot; |
| UPDATERULES | &quot;updateRules&quot; |
| UPDATEFALLTHROUGH | &quot;updateFallthrough&quot; |
| UPDATEOFFVARIATION | &quot;updateOffVariation&quot; |
| UPDATEPREREQUISITES | &quot;updatePrerequisites&quot; |
| UPDATETARGETS | &quot;updateTargets&quot; |
| UPDATEFLAGCONFIGMIGRATIONSETTINGS | &quot;updateFlagConfigMigrationSettings&quot; |



