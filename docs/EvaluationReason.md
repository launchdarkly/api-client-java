

# EvaluationReason


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**kind** | **String** | Describes the general reason that LaunchDarkly selected this variation. |  |
|**ruleIndex** | **Integer** | The positional index of the matching rule if the kind is &#39;RULE_MATCH&#39;. The index is 0-based. |  [optional] |
|**ruleID** | **String** | The unique identifier of the matching rule if the kind is &#39;RULE_MATCH&#39;. |  [optional] |
|**prerequisiteKey** | **String** | The key of the flag that failed if the kind is &#39;PREREQUISITE_FAILED&#39;. |  [optional] |
|**inExperiment** | **Boolean** | Indicates whether the evaluation occurred as part of an experiment. |  [optional] |
|**errorKind** | **String** | The specific error type if the kind is &#39;ERROR&#39;. |  [optional] |



