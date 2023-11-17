

# UserSegment


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | A human-friendly name for the segment. |  |
|**description** | **String** | A description of the segment&#39;s purpose. Defaults to &lt;code&gt;null&lt;/code&gt; and is omitted in the response if not provided. |  [optional] |
|**tags** | **List&lt;String&gt;** | Tags for the segment. Defaults to an empty array. |  |
|**creationDate** | **Long** |  |  |
|**lastModifiedDate** | **Long** |  |  |
|**key** | **String** | A unique key used to reference the segment |  |
|**included** | **List&lt;String&gt;** | An array of keys for included targets. Included individual targets are always segment members, regardless of segment rules. For list-based segments over 15,000 entries, also called Big Segments, this array is either empty or omitted. |  [optional] |
|**excluded** | **List&lt;String&gt;** | An array of keys for excluded targets. Segment rules bypass individual excluded targets, so they will never be included based on rules. Excluded targets may still be included explicitly. This value is omitted for list-based segments over 15,000 entries, also called Big Segments. |  [optional] |
|**includedContexts** | [**List&lt;SegmentTarget&gt;**](SegmentTarget.md) |  |  [optional] |
|**excludedContexts** | [**List&lt;SegmentTarget&gt;**](SegmentTarget.md) |  |  [optional] |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  |
|**rules** | [**List&lt;UserSegmentRule&gt;**](UserSegmentRule.md) | An array of the targeting rules for this segment. |  |
|**version** | **Integer** | Version of the segment |  |
|**deleted** | **Boolean** | Whether the segment has been deleted |  |
|**access** | [**Access**](Access.md) |  |  [optional] |
|**flags** | [**List&lt;FlagListingRep&gt;**](FlagListingRep.md) | A list of flags targeting this segment. Only included when getting a single segment, using the &lt;code&gt;getSegment&lt;/code&gt; endpoint. |  [optional] |
|**unbounded** | **Boolean** | Whether this is a standard segment (&lt;code&gt;false&lt;/code&gt;) or a Big Segment (&lt;code&gt;true&lt;/code&gt;). Standard segments include rule-based segments and smaller list-based segments. Big Segments include larger list-based segments and synced segments. If omitted, the segment is a standard segment. |  [optional] |
|**unboundedContextKind** | **String** | For Big Segments, the targeted context kind. |  [optional] |
|**generation** | **Integer** | For Big Segments, how many times this segment has been created. |  |
|**unboundedMetadata** | [**SegmentMetadata**](SegmentMetadata.md) |  |  [optional] |
|**external** | **String** | The external data store backing this segment. Only applies to synced segments. |  [optional] |
|**externalLink** | **String** | The URL for the external data store backing this segment. Only applies to synced segments. |  [optional] |
|**importInProgress** | **Boolean** | Whether an import is currently in progress for the specified segment. Only applies to Big Segments. |  [optional] |



