

# HoldoutPostRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | A human-friendly name for the holdout |  [optional] |
|**key** | **String** | A key that identifies the holdout |  [optional] |
|**description** | **String** | Description of the holdout |  [optional] |
|**randomizationunit** | **String** | The chosen randomization unit for the holdout base experiment |  [optional] |
|**attributes** | **List&lt;String&gt;** | The attributes that the holdout iteration&#39;s results can be sliced by |  [optional] |
|**holdoutamount** | **String** | Audience allocation for the holdout |  [optional] |
|**primarymetrickey** | **String** | The key of the primary metric for this holdout |  [optional] |
|**metrics** | [**List&lt;MetricInput&gt;**](MetricInput.md) | Details on the metrics for this experiment |  [optional] |
|**prerequisiteflagkey** | **String** | The key of the flag that the holdout is dependent on |  [optional] |
|**maintainerId** | **String** | Maintainer id |  [optional] |



