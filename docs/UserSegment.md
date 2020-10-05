
# UserSegment

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**key** | **String** | Unique identifier for the user segment. | 
**name** | **String** | Name of the user segment. | 
**description** | **String** | Description of the user segment. |  [optional]
**tags** | **List&lt;String&gt;** | An array of tags for this user segment. |  [optional]
**creationDate** | **Long** | A unix epoch time in milliseconds specifying the creation time of this flag. | 
**included** | **List&lt;String&gt;** | An array of user keys that are included in this segment. |  [optional]
**excluded** | **List&lt;String&gt;** | An array of user keys that should not be included in this segment, unless they are also listed in \&quot;included\&quot;. |  [optional]
**rules** | [**List&lt;UserSegmentRule&gt;**](UserSegmentRule.md) | An array of rules that can cause a user to be included in this segment. |  [optional]
**unbounded** | **Boolean** | Controls whether this segment can support unlimited numbers of users. Requires the beta API and additional setup. Include/exclude lists in this payload are not used in unbounded segments. |  [optional]
**version** | **Integer** |  |  [optional]
**links** | [**Links**](Links.md) |  |  [optional]
**flags** | [**List&lt;FlagListItem&gt;**](FlagListItem.md) |  |  [optional]



