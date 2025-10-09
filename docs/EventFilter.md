

# EventFilter


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | **String** | Filter type. One of [contextAttribute, eventProperty, group] |  |
|**attribute** | **String** | If not a group node, the context attribute name or event property name to filter on |  [optional] |
|**op** | **String** |  |  |
|**values** | **List&lt;Object&gt;** | The context attribute / event property values or group member nodes |  |
|**contextKind** | **String** | For context attribute filters, the context kind. |  [optional] |
|**negate** | **Boolean** | If set, then take the inverse of the operator. &#39;in&#39; becomes &#39;not in&#39;. |  |



