

# AIConfigTargetingEnvironment


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**contextTargets** | [**List&lt;AIConfigTargetingEnvironmentTarget&gt;**](AIConfigTargetingEnvironmentTarget.md) | Individual context targets for non-user context kinds |  |
|**enabled** | **Boolean** | Whether targeting is on in this environment |  |
|**fallthrough** | [**AIConfigTargetingEnvironmentFallthrough**](AIConfigTargetingEnvironmentFallthrough.md) |  |  |
|**lastModified** | **Long** |  |  |
|**offVariation** | **Integer** | Index of the variation served when targeting is off |  [optional] |
|**rules** | [**List&lt;AIConfigTargetingEnvironmentRule&gt;**](AIConfigTargetingEnvironmentRule.md) | The targeting rules |  |
|**targets** | [**List&lt;AIConfigTargetingEnvironmentTarget&gt;**](AIConfigTargetingEnvironmentTarget.md) | Individual user targets |  |
|**trackEvents** | **Boolean** | Whether to send detailed events for all evaluations |  |
|**trackEventsFallthrough** | **Boolean** | Whether to send detailed events for evaluations that fall through to the default rule |  |
|**environmentName** | **String** | The environment name |  |
|**version** | **Integer** | The version of the targeting in this environment |  |



