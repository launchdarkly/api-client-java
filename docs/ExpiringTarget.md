

# ExpiringTarget


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | The ID of this expiring target |  |
|**version** | **Integer** | The version of this expiring target |  |
|**expirationDate** | **Long** |  |  |
|**contextKind** | **String** | The context kind of the context to be removed |  |
|**contextKey** | **String** | A unique key used to represent the context to be removed |  |
|**targetType** | **String** | A segment&#39;s target type, &lt;code&gt;included&lt;/code&gt; or &lt;code&gt;excluded&lt;/code&gt;. Included when expiring targets are updated on a segment. |  [optional] |
|**variationId** | **String** | A unique ID used to represent the flag variation. Included when expiring targets are updated on a feature flag. |  [optional] |
|**resourceId** | [**ResourceId**](ResourceId.md) |  |  |



