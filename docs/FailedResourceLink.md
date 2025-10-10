

# FailedResourceLink


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**resourceKey** | **String** | The key of the resource that failed to link. |  |
|**environmentId** | **String** | Environment ID of the resource (only present for segments) |  [optional] |
|**resourceType** | [**ResourceTypeEnum**](#ResourceTypeEnum) | The type of the resource that failed to link. |  |
|**errorMessage** | **String** | The reason why linking this resource failed. |  |



## Enum: ResourceTypeEnum

| Name | Value |
|---- | -----|
| FLAG | &quot;flag&quot; |
| SEGMENT | &quot;segment&quot; |
| METRIC | &quot;metric&quot; |
| AI_CONFIG | &quot;aiConfig&quot; |



