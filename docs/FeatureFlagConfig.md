

# FeatureFlagConfig


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**on** | **Boolean** |  |  |
|**archived** | **Boolean** |  |  |
|**salt** | **String** |  |  |
|**sel** | **String** |  |  |
|**lastModified** | **Long** |  |  |
|**version** | **Integer** |  |  |
|**targets** | [**List&lt;Target&gt;**](Target.md) |  |  [optional] |
|**rules** | [**List&lt;Rule&gt;**](Rule.md) |  |  [optional] |
|**fallthrough** | [**VariationOrRolloutRep**](VariationOrRolloutRep.md) |  |  [optional] |
|**offVariation** | **Integer** |  |  [optional] |
|**prerequisites** | [**List&lt;Prerequisite&gt;**](Prerequisite.md) |  |  [optional] |
|**site** | [**Link**](Link.md) |  |  |
|**access** | [**Access**](Access.md) |  |  [optional] |
|**environmentName** | **String** |  |  |
|**trackEvents** | **Boolean** |  |  |
|**trackEventsFallthrough** | **Boolean** |  |  |
|**debugEventsUntilDate** | **Long** |  |  [optional] |
|**summary** | [**FlagSummary**](FlagSummary.md) |  |  [optional] |



