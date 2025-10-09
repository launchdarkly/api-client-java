

# ApprovalRequestSettingsPatch


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**autoApplyApprovedChanges** | **Boolean** | Automatically apply changes that have been approved by all reviewers. This field is only applicable for approval services other than LaunchDarkly.  |  [optional] |
|**bypassApprovalsForPendingChanges** | **Boolean** | Whether to skip approvals for pending changes |  [optional] |
|**canApplyDeclinedChanges** | **Boolean** | Allow applying the change as long as at least one person has approved |  [optional] |
|**canReviewOwnRequest** | **Boolean** | Allow someone who makes an approval request to apply their own change |  [optional] |
|**environmentKey** | **String** |  |  |
|**minNumApprovals** | **Integer** | Sets the amount of approvals required before a member can apply a change. The minimum is one and the maximum is five.  |  [optional] |
|**required** | **Boolean** | If approvals are required for this environment |  [optional] |
|**requiredApprovalTags** | **List&lt;String&gt;** | Require approval only on flags with the provided tags. Otherwise all flags will require approval.  |  [optional] |
|**resourceKind** | **String** |  |  |
|**serviceConfig** | **Map&lt;String, Object&gt;** | Arbitrary service-specific configuration |  [optional] |
|**serviceKind** | **String** | Which service to use for managing approvals |  [optional] |
|**serviceKindConfigurationId** | **String** | Optional integration configuration ID of a custom approval integration. This is an Enterprise-only feature.  |  [optional] |



