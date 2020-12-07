
# FeatureFlagConfig

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**on** | **Boolean** |  |  [optional]
**archived** | **Boolean** |  |  [optional]
**salt** | **String** |  |  [optional]
**sel** | **String** |  |  [optional]
**lastModified** | **Long** |  |  [optional]
**version** | **Integer** |  |  [optional]
**targets** | [**List&lt;Target&gt;**](Target.md) |  |  [optional]
**rules** | [**List&lt;Rule&gt;**](Rule.md) |  |  [optional]
**fallthrough** | [**Fallthrough**](Fallthrough.md) |  |  [optional]
**offVariation** | **Integer** |  |  [optional]
**prerequisites** | [**List&lt;Prerequisite&gt;**](Prerequisite.md) |  |  [optional]
**trackEvents** | **Boolean** | Set to true to send detailed event information for this flag. |  [optional]
**trackEventsFallthrough** | **Boolean** | Set to true to send detailed event information when targeting is enabled but no individual targeting rule is matched. |  [optional]
**site** | [**Site**](Site.md) |  |  [optional]
**environmentName** | **String** |  |  [optional]



