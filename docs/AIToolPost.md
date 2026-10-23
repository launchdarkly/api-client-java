

# AIToolPost


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**key** | **String** | A unique key for the tool |  |
|**maintainerId** | **String** | The ID of the member who maintains this resource |  [optional] |
|**maintainerTeamKey** | **String** | The key of the team that maintains this resource |  [optional] |
|**description** | **String** | A description of the tool |  [optional] |
|**schema** | **Object** | JSON Schema defining the tool&#39;s parameters for LLM consumption |  |
|**customParameters** | **Object** | Custom metadata and configuration for application-level use (not sent to LLM) |  [optional] |
|**tags** | **List&lt;String&gt;** | Tags for the tool. Tags can be used in custom role policies. |  [optional] |



