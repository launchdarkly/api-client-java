

# Token


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** |  |  |
|**ownerId** | **String** |  |  |
|**memberId** | **String** |  |  |
|**member** | [**MemberSummary**](MemberSummary.md) |  |  [optional] |
|**name** | **String** | A human-friendly name for the access token |  [optional] |
|**description** | **String** | A description for the access token |  [optional] |
|**creationDate** | **Long** |  |  |
|**lastModified** | **Long** |  |  |
|**customRoleIds** | **List&lt;String&gt;** | A list of custom role IDs to use as access limits for the access token |  [optional] |
|**inlineRole** | [**List&lt;Statement&gt;**](Statement.md) | An array of policy statements, with three attributes: effect, resources, actions. May be used in place of a built-in or custom role. |  [optional] |
|**role** | **String** | Built-in role for the token |  [optional] |
|**token** | **String** | Last four characters of the token value |  [optional] |
|**serviceToken** | **Boolean** | Whether this is a service token or a personal token |  [optional] |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  |
|**defaultApiVersion** | **Integer** | The default API version for this token |  [optional] |
|**lastUsed** | **Long** |  |  [optional] |



