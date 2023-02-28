

# ContextInstances


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  [optional] |
|**totalCount** | **Integer** | The number of unique context instances |  [optional] |
|**environmentId** | **String** | The environment ID |  |
|**continuationToken** | **String** | An obfuscated string that references the last context instance on the previous page of results. You can use this for pagination, however, we recommend using the &lt;code&gt;next&lt;/code&gt; link instead. |  [optional] |
|**items** | [**List&lt;ContextInstanceRecord&gt;**](ContextInstanceRecord.md) | A collection of context instances. Can include multiple versions of context instances that have the same &lt;code&gt;id&lt;/code&gt;, but different &lt;code&gt;applicationId&lt;/code&gt;s. |  |



