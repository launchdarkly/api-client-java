

# GuardedReleaseConfig

Configuration for guarded releases

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**rolloutContextKindKey** | **String** | Context kind key to use as the randomization unit for the rollout |  [optional] |
|**minSampleSize** | **Integer** | The minimum number of samples required to make a decision |  [optional] |
|**rollbackOnRegression** | **Boolean** | Whether to roll back on regression |  [optional] |
|**metricKeys** | **List&lt;String&gt;** | List of metric keys |  [optional] |
|**metricGroupKeys** | **List&lt;String&gt;** | List of metric group keys |  [optional] |
|**stages** | [**List&lt;ReleasePolicyStage&gt;**](ReleasePolicyStage.md) | List of stages |  [optional] |



