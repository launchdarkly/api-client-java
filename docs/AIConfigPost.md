

# AIConfigPost


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**description** | **String** | A description of the AI Config |  [optional] |
|**key** | **String** | A unique key for the AI Config |  |
|**maintainerId** | **String** | The ID of the member who maintains this resource |  [optional] |
|**maintainerTeamKey** | **String** | The key of the team that maintains this resource |  [optional] |
|**mode** | [**ModeEnum**](#ModeEnum) | The AI Config mode |  [optional] |
|**name** | **String** | A human-readable name for the AI Config |  |
|**tags** | **List&lt;String&gt;** | Tags for this resource |  [optional] |
|**viewKeys** | **List&lt;String&gt;** | Keys of the views to add the AI Config to |  [optional] |
|**defaultVariation** | [**AIConfigVariationPost**](AIConfigVariationPost.md) |  |  [optional] |
|**evaluationMetricKey** | **String** | Evaluation metric key for this config |  [optional] |
|**isInverted** | **Boolean** | Whether the evaluation metric is inverted, meaning a lower value is better if set as true |  [optional] |



## Enum: ModeEnum

| Name | Value |
|---- | -----|
| AGENT | &quot;agent&quot; |
| COMPLETION | &quot;completion&quot; |
| JUDGE | &quot;judge&quot; |



