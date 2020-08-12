
# Token

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**links** | [**Links**](Links.md) |  |  [optional]
**id** | **String** |  |  [optional]
**ownerId** | **String** |  |  [optional]
**memberId** | **String** |  |  [optional]
**member** | [**Member**](Member.md) |  |  [optional]
**creationDate** | **Long** | A unix epoch time in milliseconds specifying the creation time of this access token. |  [optional]
**lastModified** | **Long** | A unix epoch time in milliseconds specifying the last time this access token was modified. |  [optional]
**lastUsed** | **Long** | A unix epoch time in milliseconds specifying the last time this access token was used to authorize access to the LaunchDarkly REST API. |  [optional]
**token** | **String** | The last 4 digits of the unique secret key for this access token. If creating or resetting the token, this will be the full token secret. |  [optional]
**name** | **String** | A human-friendly name for the access token |  [optional]
**role** | **String** | The name of a built-in role for the token |  [optional]
**customRoleIds** | **List&lt;String&gt;** | A list of custom role IDs to use as access limits for the access token |  [optional]
**inlineRole** | [**List&lt;Statement&gt;**](Statement.md) |  |  [optional]
**serviceToken** | **Boolean** | Whether the token will be a service token https://docs.launchdarkly.com/home/account-security/api-access-tokens#service-tokens |  [optional]
**defaultApiVersion** | **Integer** | The default API version for this token |  [optional]



