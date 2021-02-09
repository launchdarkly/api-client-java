
# ApprovalRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  |  [optional]
**version** | **Integer** |  |  [optional]
**creationDate** | **Integer** | A unix epoch time in milliseconds specifying the date the approval request was requested |  [optional]
**requestorId** | **String** | The id of the member that requested the change |  [optional]
**reviewStatus** | [**ApprovalRequestReviewStatus**](ApprovalRequestReviewStatus.md) |  |  [optional]
**status** | [**StatusEnum**](#StatusEnum) | | Name     | Description | | --------:| ----------- | | pending  | the approval request has not been applied yet | | completed| the approval request has been applied successfully | | failed   | the approval request has been applied but the changes were not applied successfully |  |  [optional]
**appliedByMemberID** | **String** | The id of the member that applied the approval request |  [optional]
**appliedDate** | **Integer** | A unix epoch time in milliseconds specifying the date the approval request was applied |  [optional]
**allReviews** | [**List&lt;ApprovalRequestReview&gt;**](ApprovalRequestReview.md) |  |  [optional]
**notifyMemberIds** | **List&lt;String&gt;** |  |  [optional]
**instructions** | [**SemanticPatchInstruction**](SemanticPatchInstruction.md) |  |  [optional]


<a name="StatusEnum"></a>
## Enum: StatusEnum
Name | Value
---- | -----
PENDING | &quot;pending&quot;
COMPLETED | &quot;completed&quot;
FAILED | &quot;failed&quot;



