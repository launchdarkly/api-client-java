

# TreatmentResultRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**treatmentId** | **String** | The ID of the treatment |  [optional] |
|**treatmentName** | **String** | The name of the treatment |  [optional] |
|**mean** | **BigDecimal** | The average value of the variation in this sample. It doesn’t capture the uncertainty in the measurement, so it should not be the only measurement you use to make decisions. |  [optional] |
|**credibleInterval** | [**CredibleIntervalRep**](CredibleIntervalRep.md) |  |  [optional] |
|**pBest** | **BigDecimal** | The likelihood that this variation has the biggest effect on the primary metric. Of all the variations in the experiment, the one with highest probability is likely the best option to choose. |  [optional] |
|**relativeDifferences** | [**List&lt;RelativeDifferenceRep&gt;**](RelativeDifferenceRep.md) | A list of the ranges of the metric that you should have 90% confidence in, for each treatment ID. For example, if the range of the relative differences is [-1%, 4%], you can have 90% confidence that the population difference is a number between 1% lower and 4% higher than the control. |  [optional] |
|**units** | **Long** | The number of experiment users for this variation |  [optional] |



