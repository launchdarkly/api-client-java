

# AccessTokenPost


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | A human-friendly name for the access token |  [optional] |
|**description** | **String** | A description for the access token |  [optional] |
|**role** | [**RoleEnum**](#RoleEnum) | Built-in role for the token |  [optional] |
|**customRoleIds** | **List&lt;String&gt;** | A list of custom role IDs to use as access limits for the access token |  [optional] |
|**inlineRole** | [**List&lt;StatementPost&gt;**](StatementPost.md) | A JSON array of statements represented as JSON objects with three attributes: effect, resources, actions. May be used in place of a built-in or custom role. |  [optional] |
|**serviceToken** | **Boolean** | Whether the token is a service token |  [optional] |
|**defaultApiVersion** | **Integer** | The default API version for this token |  [optional] |



## Enum: RoleEnum

| Name | Value |
|---- | -----|
| READER | &quot;reader&quot; |
| WRITER | &quot;writer&quot; |
| ADMIN | &quot;admin&quot; |



