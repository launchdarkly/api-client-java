

# MetricRep


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
|**isNumeric** | **Boolean** | For custom metrics, whether to track numeric changes in value against a baseline (&lt;code&gt;true&lt;/code&gt;) or to track a conversion when an end user takes an action (&lt;code&gt;false&lt;/code&gt;). |  [optional] |
|**successCriteria** | [**SuccessCriteriaEnum**](#SuccessCriteriaEnum) | For custom metrics, the success criteria |  [optional] |
|**unit** | **String** | For numeric custom metrics, the unit of measure |  [optional] |
|**eventKey** | **String** | For custom metrics, the event name to use in your code |  [optional] |
|**randomizationUnits** | **List&lt;String&gt;** | An array of randomization units allowed for this metric |  [optional] |
|**experiments** | [**List&lt;DependentExperimentRep&gt;**](DependentExperimentRep.md) |  |  [optional] |
|**isActive** | **Boolean** | Whether the metric is active |  [optional] |
|**attachedFeatures** | [**List&lt;FlagListingRep&gt;**](FlagListingRep.md) | Details on the flags attached to this metric |  [optional] |
|**version** | **Integer** | Version of the metric |  [optional] |
|**selector** | **String** | For click metrics, the CSS selectors |  [optional] |
|**urls** | **List&lt;Map&lt;String, Object&gt;&gt;** |  |  [optional] |



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



