

# AgentOptimizationPatch


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**maxAttempts** | **Integer** | The maximum number of attempts |  [optional] |
|**modelChoices** | **List&lt;String&gt;** | The models to try |  [optional] |
|**judgeModel** | **String** | The model used to judge responses |  [optional] |
|**variableChoices** | **List&lt;Map&lt;String, Object&gt;&gt;** | Sets of prompt variable values to try |  [optional] |
|**acceptanceStatements** | [**List&lt;AgentOptimizationAcceptanceStatement&gt;**](AgentOptimizationAcceptanceStatement.md) | Statements a response must satisfy to pass |  [optional] |
|**judges** | [**List&lt;AgentOptimizationJudge&gt;**](AgentOptimizationJudge.md) | Judges that score each response |  [optional] |
|**userInputOptions** | **List&lt;String&gt;** | User inputs to test with |  [optional] |
|**groundTruthResponses** | **List&lt;String&gt;** | Expected responses to compare against |  [optional] |
|**metricKey** | **String** | The key of the metric to optimize |  [optional] |
|**tokenLimit** | **Integer** | The maximum number of tokens to use |  [optional] |
|**variationKey** | **String** | The key of the variation to start from |  [optional] |
|**label** | **String** | A label for the optimization |  [optional] |
|**latencyOptimization** | **Boolean** | Whether to optimize for lower latency |  [optional] |
|**tokenOptimization** | **Boolean** | Whether to optimize for lower token use |  [optional] |
|**autoCommit** | **Boolean** | Whether to save the best result as a new variation automatically |  [optional] |



