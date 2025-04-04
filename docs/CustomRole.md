

# CustomRole


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | The ID of the custom role |  |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  |
|**access** | [**Access**](Access.md) |  |  [optional] |
|**description** | **String** | The description of the custom role |  [optional] |
|**key** | **String** | The key of the custom role |  |
|**name** | **String** | The name of the custom role |  |
|**policy** | [**List&lt;Statement&gt;**](Statement.md) | An array of the policies that comprise this custom role |  |
|**basePermissions** | **String** |  |  [optional] |
|**resourceCategory** | **String** |  |  [optional] |
|**assignedTo** | [**AssignedToRep**](AssignedToRep.md) |  |  [optional] |
|**presetBundleVersion** | **Integer** | If created from a preset, the preset bundle version |  [optional] |
|**presetStatements** | [**List&lt;Statement&gt;**](Statement.md) | If created from a preset, the read-only statements copied from the preset |  [optional] |



