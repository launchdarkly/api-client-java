

# ViewLinkRequestSegmentIdentifiers


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**segmentIdentifiers** | [**List&lt;ViewLinkRequestSegmentIdentifier&gt;**](ViewLinkRequestSegmentIdentifier.md) | Identifiers of the segments to link/unlink (environmentId and segmentKey) |  |
|**filter** | **String** | Optional filter string to determine which resources should be linked. Resources only need to match either the filter or explicitly-listed keys to be linked (union). Uses the same queryfilter syntax as the segments list endpoint.  Supported filters for segments: query, tags, keys, excludedKeys, unbounded, external, view, type  |  [optional] |
|**environmentId** | **String** | Required when using filter for segment resources. Specifies which environment to query for segments matching the filter. Ignored when only using explicit segmentIdentifiers (since each identifier contains its own environmentId).  |  [optional] |
|**comment** | **String** | Optional comment for the link/unlink operation |  [optional] |



