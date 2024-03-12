

# IterationRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | The iteration ID |  [optional] |
|**hypothesis** | **String** | The expected outcome of this experiment |  |
|**status** | **String** | The status of the iteration: &lt;code&gt;not_started&lt;/code&gt;, &lt;code&gt;running&lt;/code&gt;, &lt;code&gt;stopped&lt;/code&gt; |  |
|**createdAt** | **Long** |  |  |
|**startedAt** | **Long** |  |  [optional] |
|**endedAt** | **Long** |  |  [optional] |
|**winningTreatmentId** | **String** | The ID of the treatment chosen when the experiment stopped |  [optional] |
|**winningReason** | **String** | The reason you stopped the experiment |  [optional] |
|**canReshuffleTraffic** | **Boolean** | Whether the experiment may reassign traffic to different variations when the experiment audience changes (true) or must keep all traffic assigned to its initial variation (false). |  [optional] |
|**flags** | [**Map&lt;String, FlagRep&gt;**](FlagRep.md) | Details on the flag used in this experiment |  [optional] |
|**primaryMetric** | [**DependentMetricOrMetricGroupRep**](DependentMetricOrMetricGroupRep.md) |  |  [optional] |
|**primarySingleMetric** | [**MetricV2Rep**](MetricV2Rep.md) |  |  [optional] |
|**primaryFunnel** | [**DependentMetricGroupRepWithMetrics**](DependentMetricGroupRepWithMetrics.md) |  |  [optional] |
|**randomizationUnit** | **String** | The unit of randomization for this iteration |  [optional] |
|**treatments** | [**List&lt;TreatmentRep&gt;**](TreatmentRep.md) | Details on the variations you are testing in the experiment |  [optional] |
|**secondaryMetrics** | [**List&lt;MetricV2Rep&gt;**](MetricV2Rep.md) | Deprecated, use &lt;code&gt;metrics&lt;/code&gt; instead. Details on the secondary metrics for this experiment. |  [optional] |
|**metrics** | [**List&lt;DependentMetricOrMetricGroupRep&gt;**](DependentMetricOrMetricGroupRep.md) | Details on the metrics for this experiment |  [optional] |



