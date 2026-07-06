

# Experiment


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | The experiment ID |  [optional] |
|**key** | **String** | The experiment key |  |
|**name** | **String** | The experiment name |  |
|**description** | **String** | The experiment description |  [optional] |
|**maintainerId** | **String** | The ID of the member who maintains this experiment. |  |
|**creationDate** | **Long** |  |  |
|**environmentKey** | **String** |  |  |
|**methodology** | [**MethodologyEnum**](#MethodologyEnum) | The results analysis approach. |  [optional] |
|**dataSource** | [**DataSourceEnum**](#DataSourceEnum) | The source of metric data in order to analyze results. Defaults to \&quot;launchdarkly\&quot; when not provided. |  [optional] |
|**archivedDate** | **Long** |  |  [optional] |
|**tags** | **List&lt;String&gt;** | Tags for the experiment |  [optional] |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  |
|**holdoutId** | **String** | The holdout ID |  [optional] |
|**currentIteration** | [**IterationRep**](IterationRep.md) |  |  [optional] |
|**type** | **String** | The experiment type |  [optional] |
|**access** | [**Access**](Access.md) |  |  [optional] |
|**draftIteration** | [**IterationRep**](IterationRep.md) |  |  [optional] |
|**previousIterations** | [**List&lt;IterationRep&gt;**](IterationRep.md) | Details on the previous iterations for this experiment. |  [optional] |
|**analysisConfig** | [**AnalysisConfigRep**](AnalysisConfigRep.md) |  |  [optional] |
|**mutableFieldsByStatus** | [**MutableFieldsByStatusRep**](MutableFieldsByStatusRep.md) |  |  [optional] |



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



