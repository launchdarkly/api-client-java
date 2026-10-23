

# AgentOptimizationResultPatch


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**status** | **AgentOptimizationResultStatus** |  |  [optional] |
|**activity** | **AgentOptimizationResultActivity** |  |  [optional] |
|**completionResponse** | **String** | The model response |  [optional] |
|**variation** | **Map&lt;String, Object&gt;** | The variation that was tested |  [optional] |
|**scores** | **Map&lt;String, Object&gt;** | The scores for the response |  [optional] |
|**generationTokens** | **Map&lt;String, Object&gt;** | Token usage for generating the response |  [optional] |
|**evaluationTokens** | **Map&lt;String, Object&gt;** | Token usage for judging the response |  [optional] |
|**generationLatency** | **Integer** | How long the response took to generate |  [optional] |
|**evaluationLatencies** | **Map&lt;String, Object&gt;** | How long each evaluation took |  [optional] |
|**createdVariationKey** | **String** | The key of the variation created from this result |  [optional] |



