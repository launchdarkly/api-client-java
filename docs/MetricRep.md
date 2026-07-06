

# MetricRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**experimentCount** | **Integer** | The number of experiments using this metric |  [optional] |
|**metricGroupCount** | **Integer** | The number of metric groups using this metric |  [optional] |
|**activeExperimentCount** | **Integer** | The number of active experiments using this metric |  [optional] |
|**activeGuardedRolloutCount** | **Integer** | The number of active guarded rollouts using this metric |  [optional] |
|**id** | **String** | The ID of this metric |  |
|**versionId** | **String** | The version ID of the metric |  |
|**version** | **Integer** | Version of the metric |  [optional] |
|**key** | **String** | A unique key to reference the metric |  |
|**name** | **String** | A human-friendly name for the metric |  |
|**kind** | [**KindEnum**](#KindEnum) | The kind of event the metric tracks |  |
|**attachedFlagCount** | **Integer** | The number of feature flags currently attached to this metric |  [optional] |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  |
|**site** | [**Link**](Link.md) |  |  [optional] |
|**access** | [**Access**](Access.md) |  |  [optional] |
|**tags** | **List&lt;String&gt;** | Tags for the metric |  |
|**creationDate** | **Long** |  |  |
|**lastModified** | [**Modification**](Modification.md) |  |  [optional] |
|**maintainerId** | **String** | The ID of the member who maintains this metric |  [optional] |
|**maintainer** | [**MemberSummary**](MemberSummary.md) |  |  [optional] |
|**description** | **String** | Description of the metric |  [optional] |
|**category** | **String** | The category of the metric |  [optional] |
|**isNumeric** | **Boolean** | For custom and trace metrics, whether to track numeric changes in value against a baseline (&lt;code&gt;true&lt;/code&gt;) or to track a conversion when an end user takes an action (&lt;code&gt;false&lt;/code&gt;). |  [optional] |
|**successCriteria** | [**SuccessCriteriaEnum**](#SuccessCriteriaEnum) | For custom and trace metrics, the success criteria |  [optional] |
|**unit** | **String** | For numeric custom and trace metrics, the unit of measure |  [optional] |
|**eventKey** | **String** | For custom metrics, the event key to use in your code |  [optional] |
|**randomizationUnits** | **List&lt;String&gt;** | Deprecated, use &lt;code&gt;analysisUnits&lt;/code&gt; instead. |  [optional] |
|**analysisUnits** | **List&lt;String&gt;** | An array of analysis units allowed for this metric. |  [optional] |
|**filters** | [**Filter**](Filter.md) |  |  [optional] |
|**unitAggregationType** | [**UnitAggregationTypeEnum**](#UnitAggregationTypeEnum) | The method by which multiple unit event values are aggregated |  [optional] |
|**analysisType** | [**AnalysisTypeEnum**](#AnalysisTypeEnum) | The method for analyzing metric events |  [optional] |
|**percentileValue** | **Integer** | The percentile for the analysis method. An integer denoting the target percentile between 0 and 100. Required when &lt;code&gt;analysisType&lt;/code&gt; is &lt;code&gt;percentile&lt;/code&gt;. |  [optional] |
|**eventDefault** | [**MetricEventDefaultRep**](MetricEventDefaultRep.md) |  |  [optional] |
|**dataSource** | [**MetricDataSourceRefRep**](MetricDataSourceRefRep.md) |  |  |
|**lastSeen** | **Long** |  |  [optional] |
|**archived** | **Boolean** | Whether the metric version is archived |  [optional] |
|**archivedAt** | **Long** |  |  [optional] |
|**selector** | **String** | For click metrics, the CSS selectors |  [optional] |
|**urls** | **List&lt;Map&lt;String, Object&gt;&gt;** |  |  [optional] |
|**windowStartOffset** | **Long** | Not yet implemented - The start of the measurement window, in milliseconds relative to the unit&#39;s first exposure to a flag variation |  [optional] |
|**windowEndOffset** | **Long** | Not yet implemented - The end of the measurement window, in milliseconds relative to the unit&#39;s first exposure to a flag variation |  [optional] |
|**winsorLowerPercentile** | **BigDecimal** | Lower winsorization percentile, expressed as a percent in the open interval (0, 100). When both bounds are set, defines a two-sided clamp range. Otherwise lower-only winsorization. |  [optional] |
|**winsorUpperPercentile** | **BigDecimal** | Upper winsorization percentile, expressed as a percent in the open interval (0, 100). When both bounds are set, must be greater than winsorLowerPercentile. |  [optional] |
|**winsorExcludeImputed** | **Boolean** | Deprecated and ignored. Use winsorIncludeImputed instead. |  [optional] |
|**winsorIncludeImputed** | **Boolean** | When true, the percentile bound calculation includes imputed zeros. Only meaningful when at least one bound is set and the metric includes units that didn&#39;t send events. |  [optional] |
|**traceQuery** | **String** | For trace metrics, the trace query to use for the metric. |  [optional] |
|**traceValueLocation** | **String** | For trace metrics, the location in the trace to use for numeric values. |  [optional] |
|**denominator** | [**MetricDenominatorRep**](MetricDenominatorRep.md) |  |  [optional] |
|**experiments** | [**List&lt;DependentExperimentRep&gt;**](DependentExperimentRep.md) |  |  [optional] |
|**metricGroups** | [**List&lt;DependentMetricGroupRep&gt;**](DependentMetricGroupRep.md) | Metric groups that use this metric |  [optional] |
|**lastUsedInExperiment** | [**DependentExperimentRep**](DependentExperimentRep.md) |  |  [optional] |
|**lastUsedInGuardedRollout** | [**DependentMeasuredRolloutRep**](DependentMeasuredRolloutRep.md) |  |  [optional] |
|**isActive** | **Boolean** | Whether the metric is active |  [optional] |
|**attachedFeatures** | [**List&lt;FlagListingRep&gt;**](FlagListingRep.md) | Details on the flags attached to this metric |  [optional] |



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



## Enum: AnalysisTypeEnum

| Name | Value |
|---- | -----|
| MEAN | &quot;mean&quot; |
| PERCENTILE | &quot;percentile&quot; |



