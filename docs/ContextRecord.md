

# ContextRecord


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**lastSeen** | **OffsetDateTime** | Timestamp of the last time an evaluation occurred for this context |  [optional] |
|**applicationId** | **String** | An identifier representing the application where the LaunchDarkly SDK is running. Populated when you get contexts by kind and key. Empty in search results, which return each context&#39;s most recently reported data. |  [optional] |
|**context** | **Object** | The context, including its kind and attributes |  |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  [optional] |
|**access** | [**Access**](Access.md) |  |  [optional] |
|**associatedContexts** | **Integer** | Deprecated. This value is always 1. |  [optional] |



