

# TreatmentResultRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**treatmentId** | **String** | The ID of the treatment |  [optional] |
|**treatmentName** | **String** | The name of the treatment |  [optional] |
|**mean** | **BigDecimal** | The average value of the variation in this sample. It doesn’t capture the uncertainty in the measurement, so it should not be the only measurement you use to make decisions. |  [optional] |
|**credibleInterval** | [**CredibleIntervalRep**](CredibleIntervalRep.md) |  |  [optional] |
|**pBest** | **BigDecimal** | The likelihood that this variation has the biggest effect on the primary metric. The variation with the highest probability is likely the best of the variations you&#39;re testing |  [optional] |
|**relativeDifferences** | [**List&lt;RelativeDifferenceRep&gt;**](RelativeDifferenceRep.md) | Estimates of the relative difference between this treatment&#39;s mean and the mean of each other treatment |  [optional] |
|**units** | **Long** | The number of units exposed to this treatment that have event values, including those that are configured to default to 0 |  [optional] |
|**traffic** | **Long** | The number of units exposed to this treatment. |  [optional] |
|**distribution** | [**Distribution**](Distribution.md) |  |  [optional] |



