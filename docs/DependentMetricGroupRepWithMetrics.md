

# DependentMetricGroupRepWithMetrics


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**key** | **String** | A unique key to reference the metric group |  |
|**name** | **String** | A human-friendly name for the metric group |  |
|**kind** | [**KindEnum**](#KindEnum) | The type of the metric group |  |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  |
|**metrics** | [**List&lt;MetricInGroupRep&gt;**](MetricInGroupRep.md) | The metrics in the metric group |  [optional] |



## Enum: KindEnum

| Name | Value |
|---- | -----|
| FUNNEL | &quot;funnel&quot; |



