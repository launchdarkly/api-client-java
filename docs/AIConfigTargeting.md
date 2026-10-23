

# AIConfigTargeting


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**createdAt** | **Long** | Unix timestamp in milliseconds |  |
|**defaults** | [**AIConfigTargetingDefaults**](AIConfigTargetingDefaults.md) |  |  [optional] |
|**environments** | [**Map&lt;String, AIConfigTargetingEnvironment&gt;**](AIConfigTargetingEnvironment.md) | Targeting for each environment, keyed by environment key |  |
|**experiments** | [**AiConfigsExperimentInfoRep**](AiConfigsExperimentInfoRep.md) |  |  |
|**key** | **String** | The AI Config key |  |
|**name** | **String** | The AI Config name |  |
|**tags** | **List&lt;String&gt;** | Tags for this resource |  |
|**variations** | [**List&lt;AIConfigTargetingVariation&gt;**](AIConfigTargetingVariation.md) | The variations of this AI Config |  |
|**version** | **Integer** | The version number of this resource |  |



