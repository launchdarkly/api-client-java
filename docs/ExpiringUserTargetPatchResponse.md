

# ExpiringUserTargetPatchResponse


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**items** | [**List&lt;ExpiringUserTargetItem&gt;**](ExpiringUserTargetItem.md) | An array of expiring user targets |  |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  [optional] |
|**totalInstructions** | **Integer** | The total count of instructions sent in the PATCH request |  [optional] |
|**successfulInstructions** | **Integer** | The total count of successful instructions sent in the PATCH request |  [optional] |
|**failedInstructions** | **Integer** | The total count of the failed instructions sent in the PATCH request |  [optional] |
|**errors** | [**List&lt;ExpiringTargetError&gt;**](ExpiringTargetError.md) | An array of error messages for the failed instructions |  [optional] |



