

# ViewLinkedResource


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**links** | [**ParentAndSelfLinks**](ParentAndSelfLinks.md) |  |  |
|**resourceKey** | **String** | Key of the resource (flag, segment, AI config or metric) |  |
|**environmentId** | **String** | Environment ID of the resource (only present for segments) |  [optional] |
|**environmentKey** | **String** | Environment Key of the resource (only present for segments) |  [optional] |
|**resourceType** | [**ResourceTypeEnum**](#ResourceTypeEnum) |  |  |
|**linkedAt** | **Long** |  |  |
|**resourceDetails** | [**ViewLinkedResourceDetails**](ViewLinkedResourceDetails.md) |  |  [optional] |



## Enum: ResourceTypeEnum

| Name | Value |
|---- | -----|
| FLAG | &quot;flag&quot; |
| SEGMENT | &quot;segment&quot; |
| METRIC | &quot;metric&quot; |
| AICONFIG | &quot;aiConfig&quot; |



