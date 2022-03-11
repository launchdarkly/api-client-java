

# UserSegment


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** | A human-friendly name for the segment | 
**description** | **String** | A description of the segment&#39;s purpose |  [optional]
**tags** | **List&lt;String&gt;** | Tags for the segment | 
**creationDate** | **Long** |  | 
**key** | **String** | A unique key used to reference the segment | 
**included** | **List&lt;String&gt;** | Included users are always segment members, regardless of segment rules. For Big Segments this array is either empty or omitted entirely. |  [optional]
**excluded** | **List&lt;String&gt;** | Segment rules bypass excluded users, so they will never be included based on rules. Excluded users may still be included explicitly. This value is omitted for Big Segments. |  [optional]
**links** | [**Map&lt;String, Link&gt;**](Link.md) |  | 
**rules** | [**List&lt;UserSegmentRule&gt;**](UserSegmentRule.md) |  | 
**version** | **Integer** |  | 
**deleted** | **Boolean** |  | 
**access** | [**Access**](Access.md) |  |  [optional]
**flags** | [**List&lt;FlagListingRep&gt;**](FlagListingRep.md) |  |  [optional]
**unbounded** | **Boolean** |  |  [optional]
**generation** | **Integer** |  | 
**unboundedMetadata** | [**SegmentMetadata**](SegmentMetadata.md) |  |  [optional]
**external** | **String** |  |  [optional]
**externalLink** | **String** |  |  [optional]
**importInProgress** | **Boolean** |  |  [optional]



