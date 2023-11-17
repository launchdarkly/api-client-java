

# ContextInstanceSegmentMembership


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | A human-friendly name for the segment |  |
|**key** | **String** | A unique key used to reference the segment |  |
|**description** | **String** | A description of the segment&#39;s purpose |  |
|**unbounded** | **Boolean** | Whether this is an unbounded segment. Unbounded segments, also called Big Segments, may be list-based segments with more than 15,000 entries, or synced segments. |  |
|**external** | **String** | If the segment is a synced segment, the name of the external source |  |
|**isMember** | **Boolean** | Whether the context is a member of this segment, either by explicit inclusion or by rule matching |  |
|**isIndividuallyTargeted** | **Boolean** | Whether the context is explicitly included in this segment |  |
|**isRuleTargeted** | **Boolean** | Whether the context is captured by this segment&#39;s rules. The value of this field is undefined if the context is also explicitly included (&lt;code&gt;isIndividuallyTargeted&lt;/code&gt; is &lt;code&gt;true&lt;/code&gt;). |  |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  |



