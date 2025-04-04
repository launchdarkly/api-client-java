

# ModelImport


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | The import ID |  |
|**segmentKey** | **String** | The segment key |  |
|**creationTime** | **Long** |  |  |
|**mode** | **String** | The import mode used, either &lt;code&gt;merge&lt;/code&gt; or &lt;code&gt;replace&lt;/code&gt; |  |
|**status** | [**StatusEnum**](#StatusEnum) | The import status |  |
|**files** | [**List&lt;FileRep&gt;**](FileRep.md) | The imported files and their status |  [optional] |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| PREPARING | &quot;preparing&quot; |
| PENDING_APPROVAL | &quot;pending_approval&quot; |
| READY | &quot;ready&quot; |
| IN_PROGRESS | &quot;in_progress&quot; |
| COMPLETE | &quot;complete&quot; |
| STOPPED | &quot;stopped&quot; |



