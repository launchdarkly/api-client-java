

# IterationInput


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**hypothesis** | **String** | The expected outcome of this experiment |  |
|**canReshuffleTraffic** | **Boolean** | Whether to allow the experiment to reassign traffic to different variations when you increase or decrease the traffic in your experiment audience (true) or keep all traffic assigned to its initial variation (false). Defaults to true. |  [optional] |
|**metrics** | [**List&lt;MetricInput&gt;**](MetricInput.md) |  |  |
|**treatments** | [**List&lt;TreatmentInput&gt;**](TreatmentInput.md) |  |  |
|**flags** | [**Map&lt;String, FlagInput&gt;**](FlagInput.md) |  |  |
|**randomizationUnit** | **String** | The unit of randomization for this iteration. Defaults to user. |  [optional] |



