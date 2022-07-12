

# UserFlagSetting


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources. |  |
|**value** | **Object** | The value of the flag variation that the user receives. If there is no defined default rule, this is null. |  |
|**setting** | **Object** | Whether the user is explicitly targeted to receive a particular variation. The setting is false if you have turned off a feature flag for a user. It is null if you haven&#39;t assigned that user to a specific variation. |  |
|**reason** | [**EvaluationReason**](EvaluationReason.md) |  |  [optional] |



