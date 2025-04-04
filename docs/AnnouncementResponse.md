

# AnnouncementResponse

Announcement response

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | The ID of the announcement |  |
|**isDismissible** | **Boolean** | true if the announcement is dismissible |  |
|**title** | **String** | The title of the announcement |  |
|**message** | **String** | The message of the announcement |  |
|**startTime** | **Long** | The start time of the announcement. This is a Unix timestamp in milliseconds. |  |
|**endTime** | **Long** | The end time of the announcement. This is a Unix timestamp in milliseconds. |  [optional] |
|**severity** | [**SeverityEnum**](#SeverityEnum) | The severity of the announcement |  |
|**status** | [**StatusEnum**](#StatusEnum) | The status of the announcement |  |
|**access** | [**AnnouncementAccessRep**](AnnouncementAccessRep.md) |  |  [optional] |
|**links** | [**AnnouncementResponseLinks**](AnnouncementResponseLinks.md) |  |  |



## Enum: SeverityEnum

| Name | Value |
|---- | -----|
| INFO | &quot;info&quot; |
| WARNING | &quot;warning&quot; |
| CRITICAL | &quot;critical&quot; |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| ACTIVE | &quot;active&quot; |
| INACTIVE | &quot;inactive&quot; |
| SCHEDULED | &quot;scheduled&quot; |



