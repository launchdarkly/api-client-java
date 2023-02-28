

# MetricPost


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**key** | **String** | A unique key to reference the metric |  |
|**name** | **String** | A human-friendly name for the metric |  [optional] |
|**description** | **String** | Description of the metric |  [optional] |
|**kind** | [**KindEnum**](#KindEnum) | The kind of event your metric will track |  |
|**selector** | **String** | One or more CSS selectors. Required for click metrics. |  [optional] |
|**urls** | [**List&lt;UrlPost&gt;**](UrlPost.md) | One or more target URLs. Required for click and pageview metrics. |  [optional] |
|**isActive** | **Boolean** | Whether the metric is active |  [optional] |
|**isNumeric** | **Boolean** | Whether to track numeric changes in value against a baseline (&lt;code&gt;true&lt;/code&gt;) or to track a conversion when an end user takes an action (&lt;code&gt;false&lt;/code&gt;). Required for custom metrics. |  [optional] |
|**unit** | **String** | The unit of measure. Only for numeric custom metrics. |  [optional] |
|**eventKey** | **String** | The event name to use in your code. Required for custom metrics. |  [optional] |
|**successCriteria** | [**SuccessCriteriaEnum**](#SuccessCriteriaEnum) | Success criteria. Required for numeric custom metrics. |  [optional] |
|**tags** | **List&lt;String&gt;** | Tags for the metric |  [optional] |
|**randomizationUnits** | **List&lt;String&gt;** | An array of randomization units allowed for this metric |  [optional] |



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



