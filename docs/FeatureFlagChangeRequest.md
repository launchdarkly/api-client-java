
# FeatureFlagChangeRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  |  [optional]
**version** | **Integer** |  |  [optional]
**creationDate** | **Integer** | A unix epoch time in milliseconds specifying the date the change request was requested |  [optional]
**requestorId** | **String** | The id of the member that requested the change |  [optional]
**reviewStatus** | [**FeatureFlagChangeRequestReviewStatus**](FeatureFlagChangeRequestReviewStatus.md) |  |  [optional]
**status** | [**StatusEnum**](#StatusEnum) | | Name     | Description | | --------:| ----------- | | pending  | the feature flag change request has not been applied yet | | completed| the feature flag change request has been applied successfully | | failed   | the feature flag change request has been applied but the changes were not applied successfully |  |  [optional]
**appliedByMemberID** | **String** | The id of the member that applied the change request |  [optional]
**appliedDate** | **Integer** | A unix epoch time in milliseconds specifying the date the change request was applied |  [optional]
**currentReviewsByMemberId** | [**FeatureFlagChangeRequestReview**](FeatureFlagChangeRequestReview.md) |  |  [optional]
**allReviews** | [**List&lt;FeatureFlagChangeRequestReview&gt;**](FeatureFlagChangeRequestReview.md) |  |  [optional]
**notifyMemberIds** | **List&lt;String&gt;** |  |  [optional]
**instructions** | [**SemanticPatchInstruction**](SemanticPatchInstruction.md) |  |  [optional]


<a name="StatusEnum"></a>
## Enum: StatusEnum
Name | Value
---- | -----
PENDING | &quot;pending&quot;
COMPLETED | &quot;completed&quot;
FAILED | &quot;failed&quot;



