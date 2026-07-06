

# TriggerWorkflowRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** |  |  [optional] |
|**version** | **Integer** | The flag trigger version |  [optional] |
|**creationDate** | **Long** |  |  [optional] |
|**maintainerId** | **String** | The ID of the flag trigger maintainer |  [optional] |
|**maintainer** | [**MemberSummary**](MemberSummary.md) |  |  [optional] |
|**enabled** | **Boolean** | Whether the flag trigger is currently enabled |  [optional] |
|**status** | [**StatusEnum**](#StatusEnum) | The trigger state: active, inactive, or failed |  [optional] |
|**lastFailedAt** | **Long** |  |  [optional] |
|**integrationKey** | **String** | The unique identifier of the integration for your trigger |  [optional] |
|**instructions** | **List&lt;Map&lt;String, Object&gt;&gt;** |  |  [optional] |
|**lastTriggeredAt** | **Long** |  |  [optional] |
|**recentTriggerBodies** | [**List&lt;RecentTriggerBody&gt;**](RecentTriggerBody.md) | Details on recent flag trigger requests. |  [optional] |
|**triggerCount** | **Integer** | Number of times the trigger has been executed |  [optional] |
|**triggerURL** | **String** | The unguessable URL for this flag trigger |  [optional] |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  [optional] |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| ACTIVE | &quot;active&quot; |
| INACTIVE | &quot;inactive&quot; |
| FAILED | &quot;failed&quot; |



