

# AgentOptimizationResult


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**access** | [**AiConfigsAccess**](AiConfigsAccess.md) |  |  [optional] |
|**links** | [**ParentAndSelfLinks**](ParentAndSelfLinks.md) |  |  [optional] |
|**id** | **UUID** | The result ID |  |
|**runId** | **UUID** | The ID of the run this result belongs to |  |
|**agentOptimizationId** | **UUID** | The agent optimization ID |  |
|**agentOptimizationVersion** | **Integer** | The version of the agent optimization used |  |
|**status** | **AgentOptimizationResultStatus** |  |  |
|**activity** | **AgentOptimizationResultActivity** |  |  |
|**iteration** | **Integer** | The iteration number within the run |  |
|**instructions** | **String** | The instructions used for this attempt |  |
|**parameters** | **Map&lt;String, Object&gt;** | The model parameters used for this attempt |  [optional] |
|**userInput** | **String** | The user input used for this attempt |  |
|**completionResponse** | **String** | The model response |  [optional] |
|**variation** | **Map&lt;String, Object&gt;** | The variation that was tested |  [optional] |
|**scores** | **Map&lt;String, Object&gt;** | The scores for the response |  [optional] |
|**generationTokens** | **Map&lt;String, Object&gt;** | Token usage for generating the response |  [optional] |
|**evaluationTokens** | **Map&lt;String, Object&gt;** | Token usage for judging the response |  [optional] |
|**generationLatency** | **Integer** | How long the response took to generate |  [optional] |
|**evaluationLatencies** | **Map&lt;String, Object&gt;** | How long each evaluation took |  [optional] |
|**completedAt** | **Long** | When this finished, in Unix milliseconds |  [optional] |
|**createdVariationKey** | **String** | The key of the variation created from this result |  [optional] |
|**createdAt** | **Long** | When this resource was created, in Unix milliseconds |  |
|**updatedAt** | **Long** | When this resource was last updated, in Unix milliseconds |  |



