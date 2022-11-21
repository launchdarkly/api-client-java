

# RelayAutoConfigRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** |  |  |
|**creator** | [**MemberSummary**](MemberSummary.md) |  |  [optional] |
|**access** | [**Access**](Access.md) |  |  [optional] |
|**name** | **String** | A human-friendly name for the Relay Proxy configuration |  |
|**policy** | [**List&lt;Statement&gt;**](Statement.md) | A description of what environments and projects the Relay Proxy should include or exclude |  |
|**fullKey** | **String** | The Relay Proxy configuration key |  |
|**displayKey** | **String** | The last few characters of the Relay Proxy configuration key, displayed in the LaunchDarkly UI |  |
|**creationDate** | **Long** |  |  |
|**lastModified** | **Long** |  |  |



