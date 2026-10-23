

# AgentOptimization


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**access** | [**AiConfigsAccess**](AiConfigsAccess.md) |  |  [optional] |
|**links** | [**ParentAndSelfLinks**](ParentAndSelfLinks.md) |  |  [optional] |
|**id** | **String** | The agent optimization ID |  |
|**key** | **String** | The agent optimization key |  |
|**aiConfigKey** | **String** | The key of the AI Config to optimize |  |
|**maxAttempts** | **Integer** | The maximum number of attempts |  |
|**modelChoices** | **List&lt;String&gt;** | The models to try |  |
|**judgeModel** | **String** | The model used to judge responses |  |
|**variableChoices** | **List&lt;Map&lt;String, Object&gt;&gt;** | Sets of prompt variable values to try |  |
|**acceptanceStatements** | [**List&lt;AgentOptimizationAcceptanceStatement&gt;**](AgentOptimizationAcceptanceStatement.md) | Statements a response must satisfy to pass |  |
|**judges** | [**List&lt;AgentOptimizationJudge&gt;**](AgentOptimizationJudge.md) | Judges that score each response |  |
|**userInputOptions** | **List&lt;String&gt;** | User inputs to test with |  |
|**groundTruthResponses** | **List&lt;String&gt;** | Expected responses to compare against |  |
|**metricKey** | **String** | The key of the metric to optimize |  [optional] |
|**tokenLimit** | **Integer** | The maximum number of tokens to use |  [optional] |
|**variationKey** | **String** | The key of the variation to start from |  [optional] |
|**label** | **String** | A label for the optimization |  [optional] |
|**latencyOptimization** | **Boolean** | Whether to optimize for lower latency |  [optional] |
|**tokenOptimization** | **Boolean** | Whether to optimize for lower token use |  [optional] |
|**autoCommit** | **Boolean** | Whether to save the best result as a new variation automatically |  [optional] |
|**version** | **Integer** | The version number of this resource |  |
|**createdAt** | **Long** | When this resource was created, in Unix milliseconds |  |



