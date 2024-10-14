

# CreateReleasePipelineInput


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**description** | **String** | The release pipeline description |  [optional] |
|**key** | **String** | The unique identifier of this release pipeline |  |
|**name** | **String** | The name of the release pipeline |  |
|**phases** | [**List&lt;CreatePhaseInput&gt;**](CreatePhaseInput.md) | A logical grouping of one or more environments that share attributes for rolling out changes |  |
|**tags** | **List&lt;String&gt;** | A list of tags for this release pipeline |  [optional] |
|**isProjectDefault** | **Boolean** | Whether or not the newly created pipeline should be set as the default pipeline for this project |  [optional] |
|**isLegacy** | **Boolean** | Whether or not the pipeline is enabled for Release Automation. |  [optional] |



