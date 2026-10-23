

# ProgressiveReleaseConfig

Configuration for progressive releases

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**rolloutContextKindKey** | **String** | Context kind key to use as the randomization unit for the rollout |  [optional] |
|**stages** | [**List&lt;ReleasePolicyStage&gt;**](ReleasePolicyStage.md) | List of stages. At most 10 stages by default; the server enforces the per-account limit, which is never more than 20. |  [optional] |



