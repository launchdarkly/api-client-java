

# FlagConfigApprovalRequestResponse


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | The ID of this approval request |  |
|**version** | **Integer** | Version of the approval request |  |
|**creationDate** | **Long** |  |  |
|**serviceKind** | **String** |  |  |
|**requestorId** | **String** | The ID of the member who requested the approval |  [optional] |
|**description** | **String** | A human-friendly name for the approval request |  [optional] |
|**reviewStatus** | [**ReviewStatusEnum**](#ReviewStatusEnum) | Current status of the review of this approval request |  |
|**allReviews** | [**List&lt;ReviewResponse&gt;**](ReviewResponse.md) | An array of individual reviews of this approval request |  |
|**notifyMemberIds** | **List&lt;String&gt;** | An array of member IDs. These members are notified to review the approval request. |  |
|**appliedDate** | **Long** |  |  [optional] |
|**appliedByMemberId** | **String** | The member ID of the member who applied the approval request |  [optional] |
|**status** | [**StatusEnum**](#StatusEnum) | Current status of the approval request |  |
|**instructions** | **List&lt;Map&lt;String, Object&gt;&gt;** |  |  |
|**conflicts** | [**List&lt;Conflict&gt;**](Conflict.md) | Details on any conflicting approval requests |  |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  |
|**executionDate** | **Long** |  |  [optional] |
|**operatingOnId** | **String** | ID of scheduled change to edit or delete |  [optional] |
|**integrationMetadata** | [**IntegrationMetadata**](IntegrationMetadata.md) |  |  [optional] |
|**source** | [**CopiedFromEnv**](CopiedFromEnv.md) |  |  [optional] |
|**customWorkflowMetadata** | [**CustomWorkflowMeta**](CustomWorkflowMeta.md) |  |  [optional] |



## Enum: ReviewStatusEnum

| Name | Value |
|---- | -----|
| APPROVED | &quot;approved&quot; |
| DECLINED | &quot;declined&quot; |
| PENDING | &quot;pending&quot; |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| PENDING | &quot;pending&quot; |
| COMPLETED | &quot;completed&quot; |
| FAILED | &quot;failed&quot; |
| SCHEDULED | &quot;scheduled&quot; |



