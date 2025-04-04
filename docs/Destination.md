

# Destination


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | The ID of this Data Export destination |  [optional] |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  [optional] |
|**name** | **String** | A human-readable name for your Data Export destination |  [optional] |
|**kind** | [**KindEnum**](#KindEnum) | The type of Data Export destination |  [optional] |
|**version** | **BigDecimal** |  |  [optional] |
|**config** | **Object** | An object with the configuration parameters required for the destination type |  [optional] |
|**on** | **Boolean** | Whether the export is on, that is, the status of the integration |  [optional] |
|**access** | [**Access**](Access.md) |  |  [optional] |



## Enum: KindEnum

| Name | Value |
|---- | -----|
| GOOGLE_PUBSUB | &quot;google-pubsub&quot; |
| KINESIS | &quot;kinesis&quot; |
| MPARTICLE | &quot;mparticle&quot; |
| SEGMENT | &quot;segment&quot; |
| AZURE_EVENT_HUBS | &quot;azure-event-hubs&quot; |
| SNOWFLAKE_V2 | &quot;snowflake-v2&quot; |



