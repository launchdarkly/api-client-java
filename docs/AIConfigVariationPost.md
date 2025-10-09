

# AIConfigVariationPost


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**comment** | **String** | Human-readable description of this variation |  [optional] |
|**description** | **String** | Returns the description for the agent. This is only returned for agent variations. |  [optional] |
|**instructions** | **String** | Returns the instructions for the agent. This is only returned for agent variations. |  [optional] |
|**key** | **String** |  |  |
|**messages** | [**List&lt;Message&gt;**](Message.md) |  |  |
|**model** | **Object** |  |  [optional] |
|**name** | **String** |  |  |
|**modelConfigKey** | **String** |  |  [optional] |
|**tools** | [**List&lt;VariationToolPost&gt;**](VariationToolPost.md) | List of tools to use for this variation. The latest version of the tool will be used. |  [optional] |
|**toolKeys** | **List&lt;String&gt;** | List of tool keys to use for this variation. The latest version of the tool will be used. |  [optional] |
|**judgeConfiguration** | [**JudgeConfiguration**](JudgeConfiguration.md) |  |  [optional] |



