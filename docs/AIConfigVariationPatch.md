

# AIConfigVariationPatch


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**comment** | **String** | Human-readable description of what this patch changes |  [optional] |
|**description** | **String** | Description for agent when AI Config is in agent mode. |  [optional] |
|**instructions** | **String** | Instructions for agent when AI Config is in agent mode. |  [optional] |
|**messages** | [**List&lt;Message&gt;**](Message.md) |  |  [optional] |
|**model** | **Object** |  |  [optional] |
|**modelConfigKey** | **String** |  |  [optional] |
|**name** | **String** |  |  [optional] |
|**published** | **Boolean** |  |  [optional] |
|**state** | **String** | One of &#39;archived&#39;, &#39;published&#39; |  [optional] |
|**tools** | [**List&lt;VariationToolPost&gt;**](VariationToolPost.md) | List of tools to use for this variation. The latest version of the tool will be used. |  [optional] |
|**toolKeys** | **List&lt;String&gt;** | List of tool keys to use for this variation. The latest version of the tool will be used. |  [optional] |
|**judgeConfiguration** | [**JudgeConfiguration**](JudgeConfiguration.md) |  |  [optional] |



