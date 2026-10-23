

# AIConfigVariationPost


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**classifiers** | [**List&lt;Classifier&gt;**](Classifier.md) | Classifier rules for a judge variation. When this array is non-empty, messages must be empty.  |  [optional] |
|**comment** | **String** | Human-readable description of this variation |  [optional] |
|**description** | **String** | Returns the description for the agent. This is only returned for agent variations. |  [optional] |
|**instructions** | **String** | Returns the instructions for the agent. This is only returned for agent variations. |  [optional] |
|**key** | **String** | A unique key for the variation |  |
|**messages** | [**List&lt;Message&gt;**](Message.md) | The prompt messages for this variation |  [optional] |
|**model** | **Object** | Freeform JSON object. LaunchDarkly does not validate the keys. In a model config, &#x60;params&#x60; and &#x60;customParams&#x60; hold parameters that the provider&#39;s API accepts, for example &#x60;{\&quot;temperature\&quot;: 0.7, \&quot;maxTokens\&quot;: 4096}&#x60;. |  [optional] |
|**name** | **String** | A human-readable name for the variation |  |
|**modelConfigKey** | **String** | The key that GET /api/v2/projects/{projectKey}/ai-configs/model-configs returns for the model config. Do not use a custom model config&#39;s ID, which is the provider model ID.  |  [optional] |
|**modelConfigVersion** | **Integer** | The exact model config version to set this variation to. Requires modelConfigKey to be set, and the version must exist for that model config. When omitted, the variation records the model config&#39;s current (latest) version.  |  [optional] |
|**outputFormat** | **Map&lt;String, Object&gt;** | JSON Schema defining the structured output format for the variation. |  [optional] |
|**tools** | [**List&lt;VariationToolPost&gt;**](VariationToolPost.md) | List of tools to use for this variation. The latest version of the tool will be used. |  [optional] |
|**toolKeys** | **List&lt;String&gt;** | List of tool keys to use for this variation. The latest version of the tool will be used. |  [optional] |
|**skills** | [**List&lt;VariationSkillPost&gt;**](VariationSkillPost.md) | List of agent skills to attach to this variation. |  [optional] |
|**judgeConfiguration** | [**JudgeConfiguration**](JudgeConfiguration.md) |  |  [optional] |



