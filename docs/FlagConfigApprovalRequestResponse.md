

# FlagConfigApprovalRequestResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  | 
**version** | **Integer** |  | 
**creationDate** | **Long** |  | 
**serviceKind** | **String** |  | 
**requestorId** | **String** |  |  [optional]
**description** | **String** | A human-friendly name for the approval request |  [optional]
**reviewStatus** | **String** |  | 
**allReviews** | [**List&lt;ReviewResponse&gt;**](ReviewResponse.md) |  | 
**notifyMemberIds** | **List&lt;String&gt;** | An array of member IDs. These members are notified to review the approval request. | 
**appliedDate** | **Long** |  |  [optional]
**appliedByMemberId** | **String** |  |  [optional]
**status** | **String** |  | 
**instructions** | **List&lt;Map&lt;String, Object&gt;&gt;** |  | 
**conflicts** | [**List&lt;Conflict&gt;**](Conflict.md) |  | 
**links** | [**Map&lt;String, Link&gt;**](Link.md) |  | 
**executionDate** | **Long** |  |  [optional]
**operatingOnId** | **String** | ID of scheduled change to edit or delete |  [optional]
**integrationMetadata** | [**IntegrationMetadata**](IntegrationMetadata.md) |  |  [optional]
**source** | [**CopiedFromEnv**](CopiedFromEnv.md) |  |  [optional]



