

# MetricListingRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**experimentCount** | **Integer** | The number of experiments using this metric |  [optional] |
|**id** | **String** | The ID of this metric |  |
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
|**isNumeric** | **Boolean** | For custom metrics, whether to track numeric changes in value against a baseline (&lt;code&gt;true&lt;/code&gt;) or to track a conversion when users taken an action (&lt;code&gt;false&lt;/code&gt;). |  [optional] |
|**successCriteria** | [**SuccessCriteriaEnum**](#SuccessCriteriaEnum) | For numeric custom metrics, the success criteria |  [optional] |
|**unit** | **String** | For numeric custom metrics, the unit of measure |  [optional] |
|**eventKey** | **String** | For custom metrics, the event name to use in your code |  [optional] |



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



