

# HoldoutRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** |  |  |
|**status** | [**StatusEnum**](#StatusEnum) |  |  |
|**description** | **String** |  |  [optional] |
|**holdoutAmount** | **String** | The percentage of traffic allocated to this holdout. |  |
|**createdAt** | **Long** |  |  |
|**updatedAt** | **Long** |  |  |
|**baseExperiment** | [**Experiment**](Experiment.md) |  |  |
|**experiments** | [**List&lt;RelatedExperimentRep&gt;**](RelatedExperimentRep.md) |  |  [optional] |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| CREATED | &quot;created&quot; |
| ENABLED | &quot;enabled&quot; |
| RUNNING | &quot;running&quot; |
| ENDED | &quot;ended&quot; |



