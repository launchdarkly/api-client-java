

# MigrationSafetyIssueRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**causingRuleId** | **String** | The ID of the rule which caused this issue |  [optional] |
|**affectedRuleIds** | **List&lt;String&gt;** | A list of the IDs of the rules which are affected by this issue. &lt;code&gt;fallthrough&lt;/code&gt; is a sentinel value for the default rule. |  [optional] |
|**issue** | **String** | A description of the issue that &lt;code&gt;causingRuleId&lt;/code&gt; has caused for &lt;code&gt;affectedRuleIds&lt;/code&gt;. |  [optional] |
|**oldSystemAffected** | **Boolean** | Whether the changes caused by &lt;code&gt;causingRuleId&lt;/code&gt; bring inconsistency to the old system |  [optional] |



