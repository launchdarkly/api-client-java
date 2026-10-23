

# FeatureFlagConfig


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**on** | **Boolean** | Whether the flag is on |  |
|**archived** | **Boolean** | Boolean indicating if the feature flag is archived |  |
|**salt** | **String** |  |  |
|**sel** | **String** |  |  |
|**lastModified** | **Long** |  |  |
|**version** | **Integer** | Version of the feature flag |  |
|**targets** | [**List&lt;Target&gt;**](Target.md) | An array of the individual targets that will receive a specific variation based on their key. Individual targets with a context kind of &#39;user&#39; are included here. |  [optional] |
|**contextTargets** | [**List&lt;Target&gt;**](Target.md) | An array of the individual targets that will receive a specific variation based on their key. Individual targets with context kinds other than &#39;user&#39; are included here. |  [optional] |
|**rules** | [**List&lt;Rule&gt;**](Rule.md) | An array of the rules for how to serve a variation to specific targets based on their attributes |  [optional] |
|**fallthrough** | [**VariationOrRolloutRep**](VariationOrRolloutRep.md) |  |  [optional] |
|**offVariation** | **Integer** | The ID of the variation to serve when the flag is off |  [optional] |
|**prerequisites** | [**List&lt;Prerequisite&gt;**](Prerequisite.md) | An array of the prerequisite flags and their variations that are required before this flag takes effect |  [optional] |
|**site** | [**Link**](Link.md) |  |  |
|**access** | [**Access**](Access.md) |  |  [optional] |
|**environmentName** | **String** | The environment name |  |
|**trackEvents** | **Boolean** | Whether the SDK sends a detailed event for every evaluation of this flag, across all rules, instead of only summary counts. Required for Data Export, but doesn&#39;t enable Data Export on its own. |  |
|**trackEventsFallthrough** | **Boolean** | Whether the SDK sends a detailed event for every evaluation of this flag&#39;s default rule, instead of only summary counts. Required for Data Export, but doesn&#39;t enable Data Export on its own. |  |
|**debugEventsUntilDate** | **Long** |  |  [optional] |
|**summary** | [**FlagSummary**](FlagSummary.md) |  |  [optional] |
|**evaluation** | [**FlagConfigEvaluation**](FlagConfigEvaluation.md) |  |  [optional] |
|**migrationSettings** | [**FlagConfigMigrationSettingsRep**](FlagConfigMigrationSettingsRep.md) |  |  [optional] |



