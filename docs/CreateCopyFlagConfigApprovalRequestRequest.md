

# CreateCopyFlagConfigApprovalRequestRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**comment** | **String** | Optional comment describing the approval request |  [optional] |
|**description** | **String** | A brief description of your changes |  |
|**notifyMemberIds** | **List&lt;String&gt;** | An array of member IDs. These members are notified to review the approval request. |  [optional] |
|**notifyTeamKeys** | **List&lt;String&gt;** | An array of team keys. The members of these teams are notified to review the approval request. |  [optional] |
|**source** | [**SourceFlag**](SourceFlag.md) |  |  |
|**includedActions** | [**List&lt;IncludedActionsEnum&gt;**](#List&lt;IncludedActionsEnum&gt;) | Optional list of the flag changes to copy from the source environment to the target environment. You may include either &lt;code&gt;includedActions&lt;/code&gt; or &lt;code&gt;excludedActions&lt;/code&gt;, but not both. If neither are included, then all flag changes will be copied. |  [optional] |
|**excludedActions** | [**List&lt;ExcludedActionsEnum&gt;**](#List&lt;ExcludedActionsEnum&gt;) | Optional list of the flag changes NOT to copy from the source environment to the target environment. You may include either &lt;code&gt;includedActions&lt;/code&gt; or &lt;code&gt;excludedActions&lt;/code&gt;, but not both. If neither are included, then all flag changes will be copied. |  [optional] |



## Enum: List&lt;IncludedActionsEnum&gt;

| Name | Value |
|---- | -----|
| UPDATEON | &quot;updateOn&quot; |
| UPDATEFALLTHROUGH | &quot;updateFallthrough&quot; |
| UPDATEOFFVARIATION | &quot;updateOffVariation&quot; |
| UPDATERULES | &quot;updateRules&quot; |
| UPDATETARGETS | &quot;updateTargets&quot; |
| UPDATEPREREQUISITES | &quot;updatePrerequisites&quot; |



## Enum: List&lt;ExcludedActionsEnum&gt;

| Name | Value |
|---- | -----|
| UPDATEON | &quot;updateOn&quot; |
| UPDATEFALLTHROUGH | &quot;updateFallthrough&quot; |
| UPDATEOFFVARIATION | &quot;updateOffVariation&quot; |
| UPDATERULES | &quot;updateRules&quot; |
| UPDATETARGETS | &quot;updateTargets&quot; |
| UPDATEPREREQUISITES | &quot;updatePrerequisites&quot; |



