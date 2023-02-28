

# ContextInstanceSegmentMembership


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | A human-friendly name for the segment |  |
|**key** | **String** | A unique key used to reference the segment |  |
|**description** | **String** | A description of the segment&#39;s purpose |  |
|**unbounded** | **Boolean** | Whether this is an unbounded/big segment |  |
|**external** | **String** | If the segment is a Synced Segment the name of the external source&#39; |  |
|**isMember** | **Boolean** | Whether the context is a member of this segment, either by explicit inclusion or by rule matching |  |
|**isIndividuallyTargeted** | **Boolean** | Whether the context is explicitly included in this segment |  |
|**isRuleTargeted** | **Boolean** | Whether the context is captured by this segment&#39;s rules. The value of this field is undefined if the context is also explicitly included (isIndividuallyTargeted &#x3D; true). |  |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) |  |  |



