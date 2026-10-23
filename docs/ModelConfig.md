

# ModelConfig


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**access** | [**AiConfigsAccess**](AiConfigsAccess.md) |  |  [optional] |
|**maintainer** | [**AIConfigMaintainer**](AIConfigMaintainer.md) |  |  [optional] |
|**name** | **String** | Human-readable name of the model |  |
|**key** | **String** | Unique key for the model config in the project, in the format &#x60;Provider.model-id&#x60;. Use this value as &#x60;modelConfigKey&#x60; on a config variation. |  |
|**id** | **String** | Model ID that the provider&#39;s API uses. This is different from &#x60;key&#x60;. Use this value as &#x60;modelName&#x60; on a config variation. |  |
|**icon** | **String** | Icon for the model |  [optional] |
|**provider** | **String** | Provider for the model. Matches the provider prefix of &#x60;key&#x60;. |  [optional] |
|**global** | **Boolean** | Whether the model is in the LaunchDarkly global catalog. Global models are read-only. Model configs that you create are always custom, with &#x60;global&#x60; set to &#x60;false&#x60;. |  |
|**params** | **Object** | Freeform JSON object. LaunchDarkly does not validate the keys. In a model config, &#x60;params&#x60; and &#x60;customParams&#x60; hold parameters that the provider&#39;s API accepts, for example &#x60;{\&quot;temperature\&quot;: 0.7, \&quot;maxTokens\&quot;: 4096}&#x60;. |  [optional] |
|**customParams** | **Object** | Freeform JSON object. LaunchDarkly does not validate the keys. In a model config, &#x60;params&#x60; and &#x60;customParams&#x60; hold parameters that the provider&#39;s API accepts, for example &#x60;{\&quot;temperature\&quot;: 0.7, \&quot;maxTokens\&quot;: 4096}&#x60;. |  [optional] |
|**tags** | **List&lt;String&gt;** | Tags for the model config. Tags can be used in custom role policies. |  |
|**version** | **Integer** | Version of the model config. Starts at 1, and each update that changes the model config adds a new version. |  |
|**costPerInputToken** | **Double** | Cost per input token in USD |  [optional] |
|**costPerOutputToken** | **Double** | Cost per output token in USD |  [optional] |
|**costPerCachedInputToken** | **Double** | Cost per cached input token in USD |  [optional] |
|**isRestricted** | **Boolean** | Whether the model is on the project&#39;s restricted list. Config variations cannot select a restricted model. To change this, use the add or remove restricted models endpoints. |  |
|**usedInCount** | **Integer** | Number of distinct AgentControl configs that reference this model |  [optional] |



