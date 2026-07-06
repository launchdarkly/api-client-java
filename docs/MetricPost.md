

# MetricPost


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**key** | **String** | A unique key to reference the metric |  |
|**name** | **String** | A human-friendly name for the metric |  [optional] |
|**description** | **String** | Description of the metric |  [optional] |
|**kind** | [**KindEnum**](#KindEnum) | The kind of event your metric will track |  |
|**selector** | **String** | One or more CSS selectors. Required for click metrics only. |  [optional] |
|**urls** | [**List&lt;UrlPost&gt;**](UrlPost.md) | One or more target URLs. Required for click and pageview metrics only. |  [optional] |
|**isNumeric** | **Boolean** | Whether to track numeric changes in value against a baseline (&lt;code&gt;true&lt;/code&gt;) or to track a conversion when an end user takes an action (&lt;code&gt;false&lt;/code&gt;). Required for custom and trace metrics only. |  [optional] |
|**unit** | **String** | The unit of measure. Applicable for numeric custom and trace metrics only. |  [optional] |
|**eventKey** | **String** | The event key to use in your code. Required for custom conversion/binary and custom numeric metrics only. |  [optional] |
|**successCriteria** | [**SuccessCriteriaEnum**](#SuccessCriteriaEnum) | Success criteria. Required for custom and trace numeric metrics, optional for custom and trace conversion metrics. |  [optional] |
|**tags** | **List&lt;String&gt;** | Tags for the metric |  [optional] |
|**randomizationUnits** | **List&lt;String&gt;** | Deprecated, use &lt;code&gt;analysisUnits&lt;/code&gt; instead. |  [optional] |
|**analysisUnits** | **List&lt;String&gt;** | An array of analysis units allowed for this metric. |  [optional] |
|**maintainerId** | **String** | The ID of the member who maintains this metric |  [optional] |
|**unitAggregationType** | [**UnitAggregationTypeEnum**](#UnitAggregationTypeEnum) | The method by which multiple unit event values are aggregated |  [optional] |
|**analysisType** | **String** | The method for analyzing metric events |  [optional] |
|**percentileValue** | **Integer** | The percentile for the analysis method. An integer denoting the target percentile between 0 and 100. Required when &lt;code&gt;analysisType&lt;/code&gt; is &lt;code&gt;percentile&lt;/code&gt;. |  [optional] |
|**eventDefault** | [**MetricEventDefaultRep**](MetricEventDefaultRep.md) |  |  [optional] |
|**dataSource** | [**MetricDataSourceRefRep**](MetricDataSourceRefRep.md) |  |  [optional] |
|**filters** | [**EventFilter**](EventFilter.md) |  |  [optional] |
|**windowStartOffset** | **Long** | Not yet implemented - The start of the measurement window, in milliseconds relative to the unit&#39;s first exposure to a flag variation |  [optional] |
|**windowEndOffset** | **Long** | Not yet implemented - The end of the measurement window, in milliseconds relative to the unit&#39;s first exposure to a flag variation |  [optional] |
|**winsorLowerPercentile** | **BigDecimal** | Lower winsorization percentile, expressed as a percent in the open interval (0, 100). When both bounds are set, defines a two-sided clamp range. Otherwise lower-only winsorization. |  [optional] |
|**winsorUpperPercentile** | **BigDecimal** | Upper winsorization percentile, expressed as a percent in the open interval (0, 100). When both bounds are set, must be greater than winsorLowerPercentile. |  [optional] |
|**winsorExcludeImputed** | **Boolean** | Deprecated and ignored. Use winsorIncludeImputed instead. |  [optional] |
|**winsorIncludeImputed** | **Boolean** | When true, the percentile bound calculation includes imputed zeros. Only meaningful when at least one bound is set and the metric includes units that didn&#39;t send events. |  [optional] |
|**traceQuery** | **String** | The trace query to use for the metric. Required for trace metrics. |  [optional] |
|**traceValueLocation** | **String** | The location in the trace to use for numeric values. Required for numeric trace metrics. |  [optional] |
|**unitAggregationField** | **String** | The warehouse column to use for counting distinct values. Required when the unitAggregationType is count_distinct. |  [optional] |
|**denominator** | [**DenominatorPost**](DenominatorPost.md) |  |  [optional] |



## Enum: KindEnum

| Name | Value |
|---- | -----|
| PAGEVIEW | &quot;pageview&quot; |
| CLICK | &quot;click&quot; |
| CUSTOM | &quot;custom&quot; |
| TRACE | &quot;trace&quot; |



## Enum: SuccessCriteriaEnum

| Name | Value |
|---- | -----|
| HIGHER_THAN_BASELINE | &quot;HigherThanBaseline&quot; |
| LOWER_THAN_BASELINE | &quot;LowerThanBaseline&quot; |



## Enum: UnitAggregationTypeEnum

| Name | Value |
|---- | -----|
| AVERAGE | &quot;average&quot; |
| SUM | &quot;sum&quot; |
| COUNT_DISTINCT | &quot;count_distinct&quot; |



