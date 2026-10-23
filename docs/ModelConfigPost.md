

# ModelConfigPost


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | Human readable name of the model |  |
|**key** | **String** | Unique key for the model config in the project, in the format &#x60;Provider.model-id&#x60;. Config variations reject other formats. Keys of global catalog models are reserved. |  |
|**id** | **String** | Model ID that the provider&#39;s API uses. Cannot be changed after creation. |  |
|**maintainerId** | **String** | The member ID of the maintainer. Not applied on create. To set the maintainer, update the model config after you create it. |  [optional] |
|**maintainerTeamKey** | **String** | The key of the team that maintains the model config. Not applied on create. To set the maintainer, update the model config after you create it. |  [optional] |
|**icon** | **String** | Icon for the model |  [optional] |
|**provider** | **String** | Provider for the model. Use the provider prefix of &#x60;key&#x60;. Cannot be changed after creation. |  [optional] |
|**params** | **Object** | Freeform JSON object. LaunchDarkly does not validate the keys. In a model config, &#x60;params&#x60; and &#x60;customParams&#x60; hold parameters that the provider&#39;s API accepts, for example &#x60;{\&quot;temperature\&quot;: 0.7, \&quot;maxTokens\&quot;: 4096}&#x60;. |  [optional] |
|**customParams** | **Object** | Freeform JSON object. LaunchDarkly does not validate the keys. In a model config, &#x60;params&#x60; and &#x60;customParams&#x60; hold parameters that the provider&#39;s API accepts, for example &#x60;{\&quot;temperature\&quot;: 0.7, \&quot;maxTokens\&quot;: 4096}&#x60;. |  [optional] |
|**tags** | **List&lt;String&gt;** | Tags for the model config |  [optional] |
|**costPerInputToken** | **Double** | Cost per input token in USD |  [optional] |
|**costPerOutputToken** | **Double** | Cost per output token in USD |  [optional] |
|**costPerCachedInputToken** | **Double** | Cost per cached input token in USD |  [optional] |



