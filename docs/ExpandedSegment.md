

# ExpandedSegment

Segment representation for Views API - contains only fields actually used by the Views service

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**key** | **String** | A unique key used to reference the segment |  |
|**name** | **String** | A human-friendly name for the segment |  |
|**environmentId** | **String** | Environment ID of the segment |  [optional] |
|**environmentKey** | **String** | Environment key of the segment |  [optional] |
|**description** | **String** | Description of the segment |  [optional] |
|**creationDate** | **Long** | Creation date in milliseconds |  [optional] |
|**lastModifiedDate** | **Long** | Last modification date in milliseconds |  [optional] |
|**deleted** | **Boolean** | Whether the segment is deleted |  [optional] |
|**tags** | **List&lt;String&gt;** | Tags for the segment |  [optional] |
|**unbounded** | **Boolean** | Whether the segment is unbounded |  [optional] |
|**version** | **Integer** | Version of the segment |  [optional] |
|**generation** | **Integer** | Generation of the segment |  [optional] |
|**links** | [**ParentAndSelfLinks**](ParentAndSelfLinks.md) |  |  [optional] |



