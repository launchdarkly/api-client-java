

# ReviewResponse


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | The approval request ID |  |
|**kind** | [**KindEnum**](#KindEnum) | The type of review action to take |  |
|**creationDate** | **Long** |  |  [optional] |
|**comment** | **String** | A comment describing the approval response |  [optional] |
|**memberId** | **String** | ID of account member that reviewed request |  [optional] |
|**serviceTokenId** | **String** | ID of account service token that reviewed request |  [optional] |



## Enum: KindEnum

| Name | Value |
|---- | -----|
| APPROVE | &quot;approve&quot; |
| DECLINE | &quot;decline&quot; |
| COMMENT | &quot;comment&quot; |



