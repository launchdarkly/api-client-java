

# TreatmentResultRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**treatmentId** | **String** | The ID of the treatment |  [optional] |
|**treatmentName** | **String** | The name of the treatment |  [optional] |
|**mean** | **BigDecimal** | The average value of the variation in this sample. It doesn’t capture the uncertainty in the measurement, so it should not be the only measurement you use to make decisions. |  [optional] |
|**dataMean** | **BigDecimal** | The mean of the data, with no priors effecting the result. |  [optional] |
|**dataStdDev** | **BigDecimal** | The standard deviation of the data, with no priors effecting the result. |  [optional] |
|**credibleInterval** | [**CredibleIntervalRep**](CredibleIntervalRep.md) |  |  [optional] |
|**pBest** | **BigDecimal** | The likelihood that this variation has the biggest effect on the primary metric. The variation with the highest probability is likely the best of the variations you&#39;re testing |  [optional] |
|**relativeDifferences** | [**List&lt;RelativeDifferenceRep&gt;**](RelativeDifferenceRep.md) | Estimates of the relative difference between this treatment&#39;s mean and the mean of each other treatment |  [optional] |
|**units** | **Long** | The number of units exposed to this treatment that have event values, including those that are configured to default to 0 |  [optional] |
|**traffic** | **Long** | The number of units exposed to this treatment. |  [optional] |
|**eventValuesSum** | **BigDecimal** | The sum of the event values for the units exposed to this treatment. |  [optional] |
|**distribution** | [**Distribution**](Distribution.md) |  |  [optional] |
|**correlation** | **BigDecimal** | The outcome-covariate correlation |  [optional] |
|**standardDeviationRatio** | **BigDecimal** | The ratio of the outcome SD to covariate SD |  [optional] |
|**covariateImbalance** | **BigDecimal** | The imbalance between the covariate mean for the arm and the covariate mean for the experiment |  [optional] |
|**varianceReduction** | **BigDecimal** | The reduction in variance resulting from CUPED |  [optional] |
|**model** | [**ModelEnum**](#ModelEnum) | The model used to calculate the results. Parameters specific to this model will be defined under the field under the same name |  [optional] |
|**bayesianNormal** | [**BayesianNormalStatsRep**](BayesianNormalStatsRep.md) |  |  [optional] |
|**bayesianBeta** | [**BayesianBetaBinomialStatsRep**](BayesianBetaBinomialStatsRep.md) |  |  [optional] |



## Enum: ModelEnum

| Name | Value |
|---- | -----|
| BAYESIANNORMAL | &quot;bayesianNormal&quot; |
| BAYESIANBETA | &quot;bayesianBeta&quot; |



