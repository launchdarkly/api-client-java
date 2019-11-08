
# DestinationBody

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** | A human-readable name for your data export destination. | 
**kind** | [**KindEnum**](#KindEnum) | The data export destination type. Available choices are kinesis, google-pubsub, mparticle, or segment. | 
**config** | **Object** | destination-specific configuration. | 
**on** | **Boolean** | Whether the data export destination is on or not. |  [optional]


<a name="KindEnum"></a>
## Enum: KindEnum
Name | Value
---- | -----
GOOGLE_PUBSUB | &quot;google-pubsub&quot;
KINESIS | &quot;kinesis&quot;
MPARTICLE | &quot;mparticle&quot;
SEGMENT | &quot;segment&quot;



