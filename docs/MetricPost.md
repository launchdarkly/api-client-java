

# MetricPost


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**key** | **String** |  | 
**name** | **String** |  |  [optional]
**description** | **String** |  |  [optional]
**kind** | [**KindEnum**](#KindEnum) |  | 
**selector** | **String** | Required for click metrics |  [optional]
**urls** | [**List&lt;UrlPost&gt;**](UrlPost.md) | Required for click and pageview metrics |  [optional]
**isActive** | **Boolean** |  |  [optional]
**isNumeric** | **Boolean** |  |  [optional]
**unit** | **String** |  |  [optional]
**eventKey** | **String** | Required for custom metrics |  [optional]
**successCriteria** | [**SuccessCriteriaEnum**](#SuccessCriteriaEnum) |  |  [optional]
**tags** | **List&lt;String&gt;** |  |  [optional]



## Enum: KindEnum

Name | Value
---- | -----
PAGEVIEW | &quot;pageview&quot;
CLICK | &quot;click&quot;
CUSTOM | &quot;custom&quot;



## Enum: SuccessCriteriaEnum

Name | Value
---- | -----
HIGHERTHANBASELINE | &quot;HigherThanBaseline&quot;
LOWERTHANBASELINE | &quot;LowerThanBaseline&quot;



