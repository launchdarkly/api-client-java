

# AccessDeniedReason


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**resources** | **List&lt;String&gt;** | Resource specifier strings |  [optional] |
|**notResources** | **List&lt;String&gt;** | Targeted resources are the resources NOT in this list. The &lt;code&gt;resources&lt;/code&gt; and &lt;code&gt;notActions&lt;/code&gt; fields must be empty to use this field. |  [optional] |
|**actions** | **List&lt;String&gt;** | Actions to perform on a resource |  [optional] |
|**notActions** | **List&lt;String&gt;** | Targeted actions are the actions NOT in this list. The &lt;code&gt;actions&lt;/code&gt; and &lt;code&gt;notResources&lt;/code&gt; fields must be empty to use this field. |  [optional] |
|**effect** | [**EffectEnum**](#EffectEnum) | Whether this statement should allow or deny actions on the resources. |  |
|**roleName** | **String** |  |  [optional] |



## Enum: EffectEnum

| Name | Value |
|---- | -----|
| ALLOW | &quot;allow&quot; |
| DENY | &quot;deny&quot; |



