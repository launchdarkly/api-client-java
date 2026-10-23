

# ModelConfigPatch

Fields to update. Omitted fields keep their current values. `params`, `customParams`, and `tags` replace the current values. Global model configs cannot be updated.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | Immutable provider model identifier. To use a different model identifier, create a new model config. |  [optional] |
|**provider** | **String** | Immutable model provider. To use a different provider, create a new model config. |  [optional] |
|**name** | **String** | Human-readable name of the model |  [optional] |
|**costPerInputToken** | **Double** | Cost per input token in USD |  [optional] |
|**costPerOutputToken** | **Double** | Cost per output token in USD |  [optional] |
|**costPerCachedInputToken** | **Double** | Cost per cached input token in USD |  [optional] |
|**params** | **Object** | Freeform JSON object. LaunchDarkly does not validate the keys. In a model config, &#x60;params&#x60; and &#x60;customParams&#x60; hold parameters that the provider&#39;s API accepts, for example &#x60;{\&quot;temperature\&quot;: 0.7, \&quot;maxTokens\&quot;: 4096}&#x60;. |  [optional] |
|**customParams** | **Object** | Freeform JSON object. LaunchDarkly does not validate the keys. In a model config, &#x60;params&#x60; and &#x60;customParams&#x60; hold parameters that the provider&#39;s API accepts, for example &#x60;{\&quot;temperature\&quot;: 0.7, \&quot;maxTokens\&quot;: 4096}&#x60;. |  [optional] |
|**tags** | **List&lt;String&gt;** | Tags for the model config. Replaces the current tags. |  [optional] |
|**maintainerId** | **String** | The member ID of the new maintainer. Set &#x60;maintainerId&#x60; or &#x60;maintainerTeamKey&#x60;, not both. An empty string removes the maintainer. |  [optional] |
|**maintainerTeamKey** | **String** | The key of the team to set as the new maintainer. Set &#x60;maintainerId&#x60; or &#x60;maintainerTeamKey&#x60;, not both. |  [optional] |



