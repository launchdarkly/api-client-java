

# ApplicationVersionRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**access** | [**Access**](Access.md) |  |  [optional] |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  [optional] |
|**version** | **Integer** | Version of the application version |  [optional] |
|**autoAdded** | **Boolean** | Whether the application version was automatically created, because it was included in a context when a LaunchDarkly SDK evaluated a feature flag, or if the application version was created through the LaunchDarkly UI or REST API.  |  |
|**creationDate** | **Long** |  |  [optional] |
|**key** | **String** | The unique identifier of this application version |  |
|**name** | **String** | The name of this version |  |
|**supported** | **Boolean** | Whether this version is supported. Only applicable if the application &lt;code&gt;kind&lt;/code&gt; is &lt;code&gt;mobile&lt;/code&gt;. |  [optional] |



