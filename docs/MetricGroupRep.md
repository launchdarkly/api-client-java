

# MetricGroupRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | The ID of this metric group |  |
|**key** | **String** | A unique key to reference the metric group |  |
|**name** | **String** | A human-friendly name for the metric group |  |
|**kind** | [**KindEnum**](#KindEnum) | The type of the metric group |  |
|**description** | **String** | Description of the metric group |  [optional] |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  |
|**access** | [**Access**](Access.md) |  |  [optional] |
|**tags** | **List&lt;String&gt;** | Tags for the metric group |  |
|**creationDate** | **Long** |  |  |
|**lastModified** | **Long** |  |  |
|**maintainer** | [**MaintainerRep**](MaintainerRep.md) |  |  |
|**metrics** | [**List&lt;MetricInGroupRep&gt;**](MetricInGroupRep.md) | An ordered list of the metrics in this metric group |  |
|**version** | **Integer** | The version of this metric group |  |
|**experiments** | [**List&lt;DependentExperimentRep&gt;**](DependentExperimentRep.md) |  |  [optional] |
|**experimentCount** | **Integer** | The number of experiments using this metric group |  [optional] |
|**activeExperimentCount** | **Integer** | The number of active experiments using this metric group |  [optional] |
|**activeGuardedRolloutCount** | **Integer** | The number of active guarded rollouts using this metric group |  [optional] |
|**totalConnectionsCount** | **Integer** | The total number of connections using this metric group |  [optional] |
|**totalActiveConnectionsCount** | **Integer** | The total number of active connections using this metric group |  [optional] |



## Enum: KindEnum

| Name | Value |
|---- | -----|
| FUNNEL | &quot;funnel&quot; |
| STANDARD | &quot;standard&quot; |
| GUARDRAIL | &quot;guardrail&quot; |



