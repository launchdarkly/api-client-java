

# IterationInput


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**hypothesis** | **String** | The expected outcome of this experiment |  |
|**canReshuffleTraffic** | **Boolean** | Whether to allow the experiment to reassign users to different variations (true) or keep users assigned to their initial variation (false). Defaults to true. |  [optional] |
|**metrics** | [**List&lt;MetricInput&gt;**](MetricInput.md) |  |  |
|**treatments** | [**List&lt;TreatmentInput&gt;**](TreatmentInput.md) |  |  |
|**flags** | [**Map&lt;String, FlagInput&gt;**](FlagInput.md) |  |  |



