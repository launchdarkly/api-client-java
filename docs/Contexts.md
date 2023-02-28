

# Contexts


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  [optional] |
|**totalCount** | **Integer** | The number of contexts |  [optional] |
|**environmentId** | **String** | The environment ID where the context was evaluated |  |
|**continuationToken** | **String** | An obfuscated string that references the last context instance on the previous page of results. You can use this for pagination, however, we recommend using the &lt;code&gt;next&lt;/code&gt; link instead. |  [optional] |
|**items** | [**List&lt;ContextRecord&gt;**](ContextRecord.md) | A collection of contexts. Can include multiple versions of contexts that have the same &lt;code&gt;kind&lt;/code&gt; and &lt;code&gt;key&lt;/code&gt;, but different &lt;code&gt;applicationId&lt;/code&gt;s. |  |



