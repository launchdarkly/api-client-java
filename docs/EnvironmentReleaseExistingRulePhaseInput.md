

# EnvironmentReleaseExistingRulePhaseInput

A phase that releases on an existing rule. Allowed only on a single-phase release.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**ruleId** | **String** | Existing rule to release on. The rule must already include the &#x60;kind&#x60; clause for &#x60;randomizationUnit&#x60; described in &#x60;startEnvironmentRelease&#x60;.  |  |
|**steps** | [**List&lt;EnvironmentReleaseStepInput&gt;**](EnvironmentReleaseStepInput.md) |  |  |



