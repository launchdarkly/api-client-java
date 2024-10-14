

# HoldoutDetailRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** |  |  |
|**status** | [**StatusEnum**](#StatusEnum) |  |  |
|**description** | **String** |  |  [optional] |
|**holdoutAmount** | **String** | The percentage of traffic allocated to this holdout. |  |
|**isDirty** | **Boolean** | Indicates if the holdout experiment is running and if any related experiments are running. |  [optional] |
|**createdAt** | **Long** |  |  |
|**updatedAt** | **Long** |  |  |
|**baseExperiment** | [**Experiment**](Experiment.md) |  |  |
|**relatedExperiments** | [**List&lt;Experiment&gt;**](Experiment.md) |  |  [optional] |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| CREATED | &quot;created&quot; |
| ENABLED | &quot;enabled&quot; |
| RUNNING | &quot;running&quot; |
| ENDED | &quot;ended&quot; |



