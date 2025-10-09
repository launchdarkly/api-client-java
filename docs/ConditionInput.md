

# ConditionInput


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**scheduleKind** | **String** |  |  [optional] |
|**executionDate** | **Long** |  |  [optional] |
|**waitDuration** | **Integer** | For workflow stages whose scheduled execution is relative, how far in the future the stage should start. |  [optional] |
|**waitDurationUnit** | **String** |  |  [optional] |
|**executeNow** | **Boolean** | Whether the workflow stage should be executed immediately |  [optional] |
|**description** | **String** | A description of the approval required for this stage |  [optional] |
|**notifyMemberIds** | **List&lt;String&gt;** | A list of member IDs for the members to request approval from for this stage |  [optional] |
|**notifyTeamKeys** | **List&lt;String&gt;** | A list of team keys for the teams to request approval from for this stage |  [optional] |
|**integrationConfig** | **Map&lt;String, Object&gt;** |  |  [optional] |
|**kind** | **String** |  |  [optional] |



