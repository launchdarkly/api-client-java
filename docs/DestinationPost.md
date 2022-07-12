

# DestinationPost


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | A human-readable name for your Data Export destination |  [optional] |
|**kind** | [**KindEnum**](#KindEnum) | The type of Data Export destination |  [optional] |
|**config** | **Object** | An object with the configuration parameters required for the destination type |  [optional] |
|**on** | **Boolean** | Whether the export is on. Displayed as the integration status in the LaunchDarkly UI. |  [optional] |



## Enum: KindEnum

| Name | Value |
|---- | -----|
| GOOGLE_PUBSUB | &quot;google-pubsub&quot; |
| KINESIS | &quot;kinesis&quot; |
| MPARTICLE | &quot;mparticle&quot; |
| SEGMENT | &quot;segment&quot; |
| AZURE_EVENT_HUBS | &quot;azure-event-hubs&quot; |



