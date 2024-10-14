

# FlagImportStatus


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**status** | [**StatusEnum**](#StatusEnum) | The current status of the import integrations related import job |  [optional] |
|**lastImport** | **Long** |  |  [optional] |
|**lastError** | **Long** |  |  [optional] |
|**errors** | [**List&lt;StatusResponse&gt;**](StatusResponse.md) |  |  [optional] |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| COMPLETE | &quot;complete&quot; |
| IMPORTING | &quot;importing&quot; |
| PENDING | &quot;pending&quot; |
| FAILED | &quot;failed&quot; |
| PARTIAL | &quot;partial&quot; |



