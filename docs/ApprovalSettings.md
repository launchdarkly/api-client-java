

# ApprovalSettings


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**required** | **Boolean** | If approvals are required for this environment |  |
|**bypassApprovalsForPendingChanges** | **Boolean** | Whether to skip approvals for pending changes |  |
|**minNumApprovals** | **Integer** | Sets the amount of approvals required before a member can apply a change. The minimum is one and the maximum is five. |  |
|**canReviewOwnRequest** | **Boolean** | Allow someone who makes an approval request to apply their own change |  |
|**canApplyDeclinedChanges** | **Boolean** | Allow applying the change as long as at least one person has approved |  |
|**serviceKind** | **String** | Which service to use for managing approvals |  |
|**serviceConfig** | **Map&lt;String, Object&gt;** |  |  |
|**requiredApprovalTags** | **List&lt;String&gt;** | Require approval only on flags with the provided tags. Otherwise all flags will require approval. |  |



