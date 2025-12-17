

# Rule


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | The flag rule ID |  [optional] |
|**disabled** | **Boolean** | Whether the rule is disabled |  [optional] |
|**variation** | **Integer** | The index of the variation, from the array of variations for this flag |  [optional] |
|**rollout** | [**Rollout**](Rollout.md) |  |  [optional] |
|**clauses** | [**List&lt;Clause&gt;**](Clause.md) | An array of clauses used for individual targeting based on attributes |  |
|**trackEvents** | **Boolean** | Whether LaunchDarkly tracks events for this rule |  |
|**description** | **String** | The rule description |  [optional] |
|**ref** | **String** |  |  [optional] |



