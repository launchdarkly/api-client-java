

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
|**isActive** | **Boolean** | Whether the metric is active. Set to &lt;code&gt;true&lt;/code&gt; to record click or pageview metrics. Not applicable for custom metrics. |  [optional] |
|**isNumeric** | **Boolean** | Whether to track numeric changes in value against a baseline (&lt;code&gt;true&lt;/code&gt;) or to track a conversion when an end user takes an action (&lt;code&gt;false&lt;/code&gt;). Required for custom metrics only. |  [optional] |
|**unit** | **String** | The unit of measure. Applicable for numeric custom metrics only. |  [optional] |
|**eventKey** | **String** | The event key to use in your code. Required for custom conversion/binary and custom numeric metrics only. |  [optional] |
|**successCriteria** | [**SuccessCriteriaEnum**](#SuccessCriteriaEnum) | Success criteria. Required for custom numeric metrics, optional for custom conversion metrics. |  [optional] |
|**tags** | **List&lt;String&gt;** | Tags for the metric |  [optional] |
|**randomizationUnits** | **List&lt;String&gt;** | An array of randomization units allowed for this metric |  [optional] |
|**unitAggregationType** | [**UnitAggregationTypeEnum**](#UnitAggregationTypeEnum) | The method in which multiple unit event values are aggregated |  [optional] |



## Enum: KindEnum

| Name | Value |
|---- | -----|
| PAGEVIEW | &quot;pageview&quot; |
| CLICK | &quot;click&quot; |
| CUSTOM | &quot;custom&quot; |



## Enum: SuccessCriteriaEnum

| Name | Value |
|---- | -----|
| HIGHERTHANBASELINE | &quot;HigherThanBaseline&quot; |
| LOWERTHANBASELINE | &quot;LowerThanBaseline&quot; |



## Enum: UnitAggregationTypeEnum

| Name | Value |
|---- | -----|
| AVERAGE | &quot;average&quot; |
| SUM | &quot;sum&quot; |



