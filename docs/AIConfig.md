

# AIConfig


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**access** | [**AiConfigsAccess**](AiConfigsAccess.md) |  |  [optional] |
|**links** | [**ParentAndSelfLinks**](ParentAndSelfLinks.md) |  |  [optional] |
|**description** | **String** |  |  |
|**key** | **String** |  |  |
|**maintainer** | [**AIConfigMaintainer**](AIConfigMaintainer.md) |  |  [optional] |
|**mode** | [**ModeEnum**](#ModeEnum) |  |  [optional] |
|**name** | **String** |  |  |
|**tags** | **List&lt;String&gt;** |  |  |
|**version** | **Integer** |  |  |
|**variations** | [**List&lt;AIConfigVariation&gt;**](AIConfigVariation.md) |  |  |
|**createdAt** | **Long** |  |  |
|**updatedAt** | **Long** |  |  |
|**evaluationMetricKey** | **String** | Evaluation metric key for this AI Config |  [optional] |
|**evaluationMetricKeys** | **List&lt;String&gt;** | List of evaluation metric keys for this AI Config |  [optional] |
|**isInverted** | **Boolean** | Whether the evaluation metric is inverted, meaning a lower value is better if set as true |  [optional] |



## Enum: ModeEnum

| Name | Value |
|---- | -----|
| AGENT | &quot;agent&quot; |
| COMPLETION | &quot;completion&quot; |
| JUDGE | &quot;judge&quot; |



