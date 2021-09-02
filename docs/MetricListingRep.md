

# MetricListingRep


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  | 
**key** | **String** |  | 
**name** | **String** |  | 
**kind** | [**KindEnum**](#KindEnum) |  | 
**attachedFlagCount** | **Integer** |  |  [optional]
**links** | [**Map&lt;String, Link&gt;**](Link.md) |  | 
**site** | [**Link**](Link.md) |  |  [optional]
**access** | [**AccessRep**](AccessRep.md) |  |  [optional]
**tags** | **List&lt;String&gt;** |  | 
**creationDate** | **Long** |  | 
**lastModified** | [**Modification**](Modification.md) |  |  [optional]
**maintainerId** | **String** |  |  [optional]
**maintainer** | [**MemberSummaryRep**](MemberSummaryRep.md) |  |  [optional]
**description** | **String** |  |  [optional]
**isNumeric** | **Boolean** |  |  [optional]
**successCriteria** | **Integer** |  |  [optional]
**unit** | **String** |  |  [optional]
**eventKey** | **String** |  |  [optional]



## Enum: KindEnum

Name | Value
---- | -----
PAGEVIEW | &quot;pageview&quot;
CLICK | &quot;click&quot;
CUSTOM | &quot;custom&quot;



