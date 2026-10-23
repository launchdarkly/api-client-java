

# AdaptiveTriggerInstruction


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**kind** | [**KindEnum**](#KindEnum) |  |  |
|**variationId** | **UUID** |  |  |
|**ruleId** | **UUID** | The targeting rule this adaptive trigger acts on. Required for updateRuleVariationOrRollout instructions. |  [optional] |



## Enum: KindEnum

| Name | Value |
|---- | -----|
| UPDATE_FALLTHROUGH_VARIATION_OR_ROLLOUT | &quot;updateFallthroughVariationOrRollout&quot; |
| UPDATE_RULE_VARIATION_OR_ROLLOUT | &quot;updateRuleVariationOrRollout&quot; |



