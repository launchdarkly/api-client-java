

# Destination


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  |  [optional]
**links** | [**Map&lt;String, Link&gt;**](Link.md) |  |  [optional]
**name** | **String** |  |  [optional]
**kind** | [**KindEnum**](#KindEnum) |  |  [optional]
**version** | **BigDecimal** |  |  [optional]
**config** | **Object** |  |  [optional]
**on** | **Boolean** |  |  [optional]
**access** | [**Access**](Access.md) |  |  [optional]



## Enum: KindEnum

Name | Value
---- | -----
GOOGLE_PUBSUB | &quot;google-pubsub&quot;
KINESIS | &quot;kinesis&quot;
MPARTICLE | &quot;mparticle&quot;
SEGMENT | &quot;segment&quot;
AZURE_EVENT_HUBS | &quot;azure-event-hubs&quot;



