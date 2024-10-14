

# FlagImportIntegration


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**links** | [**FlagImportIntegrationLinks**](FlagImportIntegrationLinks.md) |  |  |
|**id** | **String** | The integration ID |  |
|**integrationKey** | [**IntegrationKeyEnum**](#IntegrationKeyEnum) | The integration key |  |
|**projectKey** | **String** | The project key |  |
|**config** | **Map&lt;String, Object&gt;** |  |  |
|**tags** | **List&lt;String&gt;** | List of tags for this configuration |  |
|**name** | **String** | Name of the configuration |  |
|**version** | **Integer** | Version of the current configuration |  |
|**access** | [**Access**](Access.md) |  |  [optional] |
|**status** | [**FlagImportStatus**](FlagImportStatus.md) |  |  |



## Enum: IntegrationKeyEnum

| Name | Value |
|---- | -----|
| SPLIT | &quot;split&quot; |
| UNLEASH | &quot;unleash&quot; |



