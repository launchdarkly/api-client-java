

# MetricInGroupRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**key** | **String** | The metric key |  |
|**versionId** | **String** | The version ID of the metric |  [optional] |
|**name** | **String** | The metric name |  |
|**kind** | [**KindEnum**](#KindEnum) | The kind of event the metric tracks |  |
|**isNumeric** | **Boolean** | For custom metrics, whether to track numeric changes in value against a baseline (&lt;code&gt;true&lt;/code&gt;) or to track a conversion when an end user takes an action (&lt;code&gt;false&lt;/code&gt;). |  [optional] |
|**unitAggregationType** | [**UnitAggregationTypeEnum**](#UnitAggregationTypeEnum) | The type of unit aggregation to use for the metric |  [optional] |
|**analysisType** | [**AnalysisTypeEnum**](#AnalysisTypeEnum) | The method for analyzing metric events |  [optional] |
|**eventKey** | **String** | The event key sent with the metric. Only relevant for custom metrics. |  [optional] |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  |
|**nameInGroup** | **String** | Name of the metric when used within the associated metric group. Can be different from the original name of the metric. Required if and only if the metric group is a &lt;code&gt;funnel&lt;/code&gt;. |  [optional] |
|**randomizationUnits** | **List&lt;String&gt;** | Deprecated, use &lt;code&gt;analysisUnits&lt;/code&gt; instead. |  [optional] |
|**analysisUnits** | **List&lt;String&gt;** | An array of analysis units allowed for this metric. |  [optional] |



## Enum: KindEnum

| Name | Value |
|---- | -----|
| PAGEVIEW | &quot;pageview&quot; |
| CLICK | &quot;click&quot; |
| CUSTOM | &quot;custom&quot; |
| TRACE | &quot;trace&quot; |



## Enum: UnitAggregationTypeEnum

| Name | Value |
|---- | -----|
| SUM | &quot;sum&quot; |
| AVERAGE | &quot;average&quot; |
| COUNT_DISTINCT | &quot;count_distinct&quot; |



## Enum: AnalysisTypeEnum

| Name | Value |
|---- | -----|
| MEAN | &quot;mean&quot; |
| PERCENTILE | &quot;percentile&quot; |



