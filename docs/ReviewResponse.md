

# ReviewResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | The approval request id | 
**kind** | [**KindEnum**](#KindEnum) | The type of review action to take. Either \&quot;approve\&quot;, \&quot;decline\&quot; or \&quot;comment\&quot; | 
**creationDate** | **Long** |  |  [optional]
**comment** | **String** | A comment describing the approval response |  [optional]
**memberId** | **String** | ID of account member that reviewed request |  [optional]



## Enum: KindEnum

Name | Value
---- | -----
APPROVE | &quot;approve&quot;
DECLINE | &quot;decline&quot;
COMMENT | &quot;comment&quot;



