

# Rule


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | The flag rule ID |  [optional] |
|**disabled** | **Boolean** | Whether the rule is disabled |  [optional] |
|**variation** | **Integer** | The index of the variation, from the array of variations for this flag |  [optional] |
|**rollout** | [**Rollout**](Rollout.md) |  |  [optional] |
|**clauses** | [**List&lt;Clause&gt;**](Clause.md) | An array of clauses used for individual targeting based on attributes |  |
|**trackEvents** | **Boolean** | Whether the SDK sends a detailed event for every evaluation of this rule, instead of only summary counts. Required for Data Export, but doesn&#39;t enable Data Export on its own. |  |
|**description** | **String** | The rule description |  [optional] |
|**ref** | **String** |  |  [optional] |



