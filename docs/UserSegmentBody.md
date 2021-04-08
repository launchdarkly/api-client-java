
# UserSegmentBody

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** | A human-friendly name for the user segment. | 
**key** | **String** | A unique key that will be used to reference the user segment in feature flags. | 
**description** | **String** | A description for the user segment. |  [optional]
**unbounded** | **Boolean** | Controls whether this is considered a \&quot;big segment\&quot; which can support an unlimited numbers of users. Include/exclude lists sent with this payload are not used in big segments. Contact your account manager for early access to this feature. |  [optional]
**tags** | **List&lt;String&gt;** | Tags for the user segment. |  [optional]



