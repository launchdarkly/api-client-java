

# NewMemberForm


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**email** | **String** | The member&#39;s email |  |
|**password** | **String** | The member&#39;s password |  [optional] |
|**firstName** | **String** | The member&#39;s first name |  [optional] |
|**lastName** | **String** | The member&#39;s last name |  [optional] |
|**role** | [**RoleEnum**](#RoleEnum) | The member&#39;s built-in role |  [optional] |
|**customRoles** | **List&lt;String&gt;** | An array of the member&#39;s custom roles |  [optional] |
|**teamKeys** | **List&lt;String&gt;** | An array of the member&#39;s teams |  [optional] |



## Enum: RoleEnum

| Name | Value |
|---- | -----|
| READER | &quot;reader&quot; |
| WRITER | &quot;writer&quot; |
| ADMIN | &quot;admin&quot; |
| NO_ACCESS | &quot;no_access&quot; |



