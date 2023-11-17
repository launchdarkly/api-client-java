

# CreateApplicationInput


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**description** | **String** | The application description |  [optional] |
|**key** | **String** | The unique identifier of this application |  |
|**kind** | [**KindEnum**](#KindEnum) | To distinguish the kind of application |  |
|**maintainer** | [**MaintainerInput**](MaintainerInput.md) |  |  [optional] |
|**name** | **String** | The name of the application |  |



## Enum: KindEnum

| Name | Value |
|---- | -----|
| BROWSER | &quot;browser&quot; |
| MOBILE | &quot;mobile&quot; |
| SERVER | &quot;server&quot; |



