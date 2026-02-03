

# AIConfigPost


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**description** | **String** |  |  [optional] |
|**key** | **String** |  |  |
|**maintainerId** | **String** |  |  [optional] |
|**maintainerTeamKey** | **String** |  |  [optional] |
|**mode** | [**ModeEnum**](#ModeEnum) |  |  [optional] |
|**name** | **String** |  |  |
|**tags** | **List&lt;String&gt;** |  |  [optional] |
|**defaultVariation** | [**AIConfigVariationPost**](AIConfigVariationPost.md) |  |  [optional] |
|**evaluationMetricKey** | **String** | Evaluation metric key for this AI Config |  [optional] |
|**isInverted** | **Boolean** | Whether the evaluation metric is inverted, meaning a lower value is better if set as true |  [optional] |



## Enum: ModeEnum

| Name | Value |
|---- | -----|
| AGENT | &quot;agent&quot; |
| COMPLETION | &quot;completion&quot; |
| JUDGE | &quot;judge&quot; |



