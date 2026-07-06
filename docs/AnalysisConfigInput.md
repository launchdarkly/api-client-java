

# AnalysisConfigInput


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**bayesianThreshold** | **String** | The threshold for the Probability to Beat Baseline (PBBL) and Probability to Be Best (PBB) comparisons for the Bayesian results analysis approach. |  [optional] |
|**significanceThreshold** | **String** | The significance threshold for the frequentist results analysis approach. |  [optional] |
|**testDirection** | **String** | The test sided direction for the frequentist results analysis approach. |  [optional] |
|**multipleComparisonCorrectionMethod** | [**MultipleComparisonCorrectionMethodEnum**](#MultipleComparisonCorrectionMethodEnum) | The method to use for multiple comparison correction. |  [optional] |
|**multipleComparisonCorrectionScope** | [**MultipleComparisonCorrectionScopeEnum**](#MultipleComparisonCorrectionScopeEnum) | The scope of the multiple comparison correction. |  [optional] |
|**sequentialTestingEnabled** | **Boolean** | Whether sequential testing is enabled for Frequentist analysis |  [optional] |



## Enum: MultipleComparisonCorrectionMethodEnum

| Name | Value |
|---- | -----|
| BONFERRONI | &quot;bonferroni&quot; |
| BENJAMINI_HOCHBERG | &quot;benjamini-hochberg&quot; |



## Enum: MultipleComparisonCorrectionScopeEnum

| Name | Value |
|---- | -----|
| VARIATIONS | &quot;variations&quot; |
| VARIATIONS_AND_METRICS | &quot;variations-and-metrics&quot; |
| METRICS | &quot;metrics&quot; |



