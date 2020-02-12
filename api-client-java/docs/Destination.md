
# Destination

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**links** | [**Links**](Links.md) |  |  [optional]
**id** | **String** | Unique destination ID. |  [optional]
**name** | **String** | The destination name |  [optional]
**kind** | [**KindEnum**](#KindEnum) | Destination type (\&quot;google-pubsub\&quot;, \&quot;kinesis\&quot;, \&quot;mparticle\&quot;, or \&quot;segment\&quot;) |  [optional]
**config** | **Object** | destination-specific configuration. |  [optional]
**on** | **Boolean** | Whether the data export destination is on or not. |  [optional]
**version** | **Integer** |  |  [optional]


<a name="KindEnum"></a>
## Enum: KindEnum
Name | Value
---- | -----
GOOGLE_PUBSUB | &quot;google-pubsub&quot;
KINESIS | &quot;kinesis&quot;
MPARTICLE | &quot;mparticle&quot;
SEGMENT | &quot;segment&quot;



