

# MetricInGroupRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**key** | **String** | The metric key |  |
|**name** | **String** | The metric name |  |
|**kind** | [**KindEnum**](#KindEnum) | The kind of event the metric tracks |  |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  |
|**nameInGroup** | **String** | Name of the metric when used within the associated metric group. Can be different from the original name of the metric |  |
|**randomizationUnits** | **List&lt;String&gt;** | The randomization units for the metric |  [optional] |



## Enum: KindEnum

| Name | Value |
|---- | -----|
| PAGEVIEW | &quot;pageview&quot; |
| CLICK | &quot;click&quot; |
| CUSTOM | &quot;custom&quot; |



