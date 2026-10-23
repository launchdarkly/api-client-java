

# AIConfig


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**access** | [**AiConfigsAccess**](AiConfigsAccess.md) |  |  [optional] |
|**links** | [**ParentAndSelfLinks**](ParentAndSelfLinks.md) |  |  [optional] |
|**site** | [**CoreLink**](CoreLink.md) |  |  [optional] |
|**description** | **String** | A description of the AI Config |  |
|**key** | **String** | The AI Config key |  |
|**maintainer** | [**AIConfigMaintainer**](AIConfigMaintainer.md) |  |  [optional] |
|**mode** | [**ModeEnum**](#ModeEnum) | The AI Config mode |  [optional] |
|**name** | **String** | The AI Config name |  |
|**tags** | **List&lt;String&gt;** | Tags for this resource |  |
|**version** | **Integer** | The version number of this resource |  |
|**variations** | [**List&lt;AIConfigVariation&gt;**](AIConfigVariation.md) | The variations of this AI Config |  |
|**createdAt** | **Long** |  |  |
|**updatedAt** | **Long** |  |  |
|**evaluationMetricKey** | **String** | Evaluation metric key for this config |  [optional] |
|**evaluationMetricKeys** | **List&lt;String&gt;** | List of evaluation metric keys for this config |  [optional] |
|**isInverted** | **Boolean** | Whether the evaluation metric is inverted, meaning a lower value is better if set as true |  [optional] |
|**dependencies** | [**List&lt;AIConfigDependency&gt;**](AIConfigDependency.md) | Resources that depend on this config, grouped by type |  [optional] |



## Enum: ModeEnum

| Name | Value |
|---- | -----|
| AGENT | &quot;agent&quot; |
| COMPLETION | &quot;completion&quot; |
| JUDGE | &quot;judge&quot; |



