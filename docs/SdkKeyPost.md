

# SdkKeyPost


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**kind** | [**KindEnum**](#KindEnum) | The kind of SDK key. Can be either \&quot;sdk\&quot; (server-side) or \&quot;mobile\&quot; (mobile). Defaults to \&quot;sdk\&quot; when not explicitly defined. |  [optional] |
|**key** | **String** | The user-defined key of the SDK key. |  |
|**name** | **String** | The human-readable name of the SDK key. |  |
|**description** | **String** | The optional description of the SDK key. |  [optional] |
|**expiry** | **Long** |  |  [optional] |



## Enum: KindEnum

| Name | Value |
|---- | -----|
| SDK | &quot;sdk&quot; |
| MOBILE | &quot;mobile&quot; |



