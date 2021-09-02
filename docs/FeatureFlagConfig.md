

# FeatureFlagConfig


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**on** | **Boolean** |  | 
**archived** | **Boolean** |  | 
**salt** | **String** |  | 
**sel** | **String** |  | 
**lastModified** | **Long** |  | 
**version** | **Integer** |  | 
**targets** | [**List&lt;Target&gt;**](Target.md) |  | 
**rules** | [**List&lt;Rule&gt;**](Rule.md) |  | 
**fallthrough** | [**VariationOrRolloutRep**](VariationOrRolloutRep.md) |  | 
**offVariation** | **Integer** |  |  [optional]
**prerequisites** | [**List&lt;Prerequisite&gt;**](Prerequisite.md) |  | 
**site** | [**Link**](Link.md) |  | 
**access** | [**AccessRep**](AccessRep.md) |  |  [optional]
**environmentName** | **String** |  | 
**trackEvents** | **Boolean** |  | 
**trackEventsFallthrough** | **Boolean** |  | 
**debugEventsUntilDate** | **Long** |  |  [optional]
**summary** | [**FlagSummary**](FlagSummary.md) |  |  [optional]



