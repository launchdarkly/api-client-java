

# UserRecord


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**lastPing** | **OffsetDateTime** | Timestamp of the last time this user was seen |  [optional] |
|**environmentId** | **String** |  |  [optional] |
|**ownerId** | **String** |  |  [optional] |
|**user** | [**User**](User.md) |  |  [optional] |
|**sortValue** | **Object** | If this record is returned as part of a list, the value used to sort the list. This is only included when the &lt;code&gt;sort&lt;/code&gt; query parameter is specified. It is a time, in Unix milliseconds, if the sort is by &lt;code&gt;lastSeen&lt;/code&gt;. It is a user key if the sort is by &lt;code&gt;userKey&lt;/code&gt;. |  [optional] |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  [optional] |
|**access** | [**Access**](Access.md) |  |  [optional] |



