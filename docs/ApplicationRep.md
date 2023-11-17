

# ApplicationRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**flags** | [**ApplicationFlagCollectionRep**](ApplicationFlagCollectionRep.md) |  |  [optional] |
|**access** | [**Access**](Access.md) |  |  [optional] |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  [optional] |
|**version** | **Integer** | Version of the application |  [optional] |
|**autoAdded** | **Boolean** | Whether the application was automatically created because it was included in a context when a LaunchDarkly SDK evaluated a feature flag, or was created through the LaunchDarkly UI or REST API. |  |
|**creationDate** | **Long** |  |  [optional] |
|**description** | **String** | The application description |  [optional] |
|**key** | **String** | The unique identifier of this application |  |
|**kind** | [**KindEnum**](#KindEnum) | To distinguish the kind of application |  |
|**maintainer** | [**MaintainerRep**](MaintainerRep.md) |  |  [optional] |
|**name** | **String** | The name of the application |  |



## Enum: KindEnum

| Name | Value |
|---- | -----|
| BROWSER | &quot;browser&quot; |
| MOBILE | &quot;mobile&quot; |
| SERVER | &quot;server&quot; |



