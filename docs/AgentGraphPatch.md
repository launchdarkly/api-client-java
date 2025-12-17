

# AgentGraphPatch

Request body for updating an agent graph. If rootConfigKey or edges are present, both must be present.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | A human-readable name for the agent graph |  [optional] |
|**description** | **String** | A description of the agent graph |  [optional] |
|**rootConfigKey** | **String** | The AI Config key of the root node. If present, edges must also be present. |  [optional] |
|**edges** | [**List&lt;AgentGraphEdge&gt;**](AgentGraphEdge.md) | The edges in the graph. If present, rootConfigKey must also be present. Replaces all existing edges. |  [optional] |



