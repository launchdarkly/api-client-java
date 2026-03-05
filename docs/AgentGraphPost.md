

# AgentGraphPost

Request body for creating an agent graph

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**key** | **String** | A unique key for the agent graph |  |
|**name** | **String** | A human-readable name for the agent graph |  |
|**description** | **String** | A description of the agent graph |  [optional] |
|**maintainerId** | **String** | The ID of the member who maintains this agent graph |  [optional] |
|**maintainerTeamKey** | **String** | The key of the team that maintains this agent graph |  [optional] |
|**rootConfigKey** | **String** | The AI Config key of the root node. A missing root implies a newly created graph with metadata only. |  [optional] |
|**edges** | [**List&lt;AgentGraphEdgePost&gt;**](AgentGraphEdgePost.md) | The edges in the graph. If edges or rootConfigKey is present, both must be present. |  [optional] |



