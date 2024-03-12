

# ExperimentBayesianResultsRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  [optional] |
|**treatmentResults** | [**List&lt;TreatmentResultRep&gt;**](TreatmentResultRep.md) | Deprecated, use &lt;code&gt;results&lt;/code&gt; instead. Only populated when response does not contain results sliced by multiple attributes. |  [optional] |
|**metricSeen** | [**MetricSeen**](MetricSeen.md) |  |  [optional] |
|**probabilityOfMismatch** | **BigDecimal** | The probability of a Sample Ratio Mismatch |  [optional] |
|**results** | [**List&lt;SlicedResultsRep&gt;**](SlicedResultsRep.md) | A list of attribute values and their corresponding treatment results |  [optional] |



