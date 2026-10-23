

# EnvironmentReleasePhaseInput

One phase of a release. Each phase creates a flag rule unless it sets `ruleId`.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**ruleId** | **String** | Existing rule to release on. The rule must already include the &#x60;kind&#x60; clause for &#x60;randomizationUnit&#x60; described in &#x60;startEnvironmentRelease&#x60;.  |  |
|**steps** | [**List&lt;EnvironmentReleaseStepInput&gt;**](EnvironmentReleaseStepInput.md) |  |  |
|**clauses** | [**List&lt;EnvironmentReleaseClause&gt;**](EnvironmentReleaseClause.md) | Clauses for a new rule. Must include the &#x60;kind&#x60; clause for &#x60;randomizationUnit&#x60; described in &#x60;startEnvironmentRelease&#x60;. A new rule that exactly duplicates an existing rule on the flag returns &#x60;400&#x60; with &#x60;new rule is exact duplicate of an existing rule at index &lt;n&gt;&#x60;.  |  |
|**description** | **String** | Description for a new rule. Must differ from the description of every other rule on the flag, including rules an earlier release left in place, or the request returns &#x60;400&#x60; with &#x60;duplicate rule description not allowed&#x60;.  |  [optional] |



