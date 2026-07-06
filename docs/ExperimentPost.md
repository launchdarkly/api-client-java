

# ExperimentPost


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | The experiment name |  |
|**description** | **String** | The experiment description |  [optional] |
|**maintainerId** | **String** | The ID of the member who maintains this experiment |  [optional] |
|**key** | **String** | The experiment key |  |
|**iteration** | [**IterationInput**](IterationInput.md) |  |  |
|**holdoutId** | **String** | The ID of the holdout |  [optional] |
|**tags** | **List&lt;String&gt;** | Tags for the experiment |  [optional] |
|**methodology** | [**MethodologyEnum**](#MethodologyEnum) | The results analysis approach. |  [optional] |
|**analysisConfig** | [**AnalysisConfigInput**](AnalysisConfigInput.md) |  |  [optional] |
|**dataSource** | [**DataSourceEnum**](#DataSourceEnum) | The source of metric data in order to analyze results. Defaults to \&quot;launchdarkly\&quot; when not provided. |  [optional] |
|**type** | [**TypeEnum**](#TypeEnum) | The type of experiment. |  [optional] |



## Enum: MethodologyEnum

| Name | Value |
|---- | -----|
| BAYESIAN | &quot;bayesian&quot; |
| FREQUENTIST | &quot;frequentist&quot; |
| EXPORT_ONLY | &quot;export_only&quot; |



## Enum: DataSourceEnum

| Name | Value |
|---- | -----|
| LAUNCHDARKLY | &quot;launchdarkly&quot; |
| SNOWFLAKE | &quot;snowflake&quot; |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| EXPERIMENT | &quot;experiment&quot; |
| MAB | &quot;mab&quot; |
| HOLDOUT | &quot;holdout&quot; |



