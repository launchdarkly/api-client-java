

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
|**isActive** | **Boolean** | Whether to track a conversion when users take an action. Required for custom metrics. Either &lt;code&gt;isActive&lt;/code&gt; or &lt;code&gt;isNumeric&lt;/code&gt; may be true, but not both. |  [optional] |
|**isNumeric** | **Boolean** | Whether to track numeric changes in value against a baseline. Required for custom metrics. Either &lt;code&gt;isActive&lt;/code&gt; or &lt;code&gt;isNumeric&lt;/code&gt; may be true, but not both. |  [optional] |
|**unit** | **String** | The unit of measure. Only for numeric custom metrics. |  [optional] |
|**eventKey** | **String** | The event name to use in your code. Required for custom metrics. |  [optional] |
|**successCriteria** | [**SuccessCriteriaEnum**](#SuccessCriteriaEnum) | Success criteria. Required for numeric custom metrics. |  [optional] |
|**tags** | **List&lt;String&gt;** | Tags for the metric |  [optional] |



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



