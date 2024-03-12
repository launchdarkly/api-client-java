

# SegmentBody


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | A human-friendly name for the segment |  |
|**key** | **String** | A unique key used to reference the segment |  |
|**description** | **String** | A description of the segment&#39;s purpose |  [optional] |
|**tags** | **List&lt;String&gt;** | Tags for the segment |  [optional] |
|**unbounded** | **Boolean** | Whether to create a standard segment (&lt;code&gt;false&lt;/code&gt;) or a big segment (&lt;code&gt;true&lt;/code&gt;). Standard segments include rule-based and smaller list-based segments. Big segments include larger list-based segments and synced segments. Only use a big segment if you need to add more than 15,000 individual targets. |  [optional] |
|**unboundedContextKind** | **String** | For big segments, the targeted context kind. |  [optional] |



