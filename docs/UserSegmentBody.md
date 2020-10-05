
# UserSegmentBody

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** | A human-friendly name for the user segment. | 
**key** | **String** | A unique key that will be used to reference the user segment in feature flags. | 
**description** | **String** | A description for the user segment. |  [optional]
**unbounded** | **Boolean** | Controls whether this segment can support unlimited numbers of users. Requires the beta API and additional setup. Include/exclude lists in this payload are not used in unbounded segments. |  [optional]
**tags** | **List&lt;String&gt;** | Tags for the user segment. |  [optional]



