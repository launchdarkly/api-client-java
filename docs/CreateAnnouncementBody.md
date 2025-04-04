

# CreateAnnouncementBody

Create announcement request body

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**isDismissible** | **Boolean** | true if the announcement is dismissible |  |
|**title** | **String** | The title of the announcement |  |
|**message** | **String** | The message of the announcement |  |
|**startTime** | **Long** | The start time of the announcement. This is a Unix timestamp in milliseconds. |  |
|**endTime** | **Long** | The end time of the announcement. This is a Unix timestamp in milliseconds. |  [optional] |
|**severity** | [**SeverityEnum**](#SeverityEnum) | The severity of the announcement |  |



## Enum: SeverityEnum

| Name | Value |
|---- | -----|
| INFO | &quot;info&quot; |
| WARNING | &quot;warning&quot; |
| CRITICAL | &quot;critical&quot; |



