
# Statement

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**resources** | **List&lt;String&gt;** |  |  [optional]
**notResources** | **List&lt;String&gt;** | Targeted resource will be those resources NOT in this list. The \&quot;resources&#x60;\&quot; field must be empty to use this field. |  [optional]
**actions** | **List&lt;String&gt;** |  |  [optional]
**notActions** | **List&lt;String&gt;** | Targeted actions will be those actions NOT in this list. The \&quot;actions\&quot; field must be empty to use this field. |  [optional]
**effect** | [**EffectEnum**](#EffectEnum) |  |  [optional]


<a name="EffectEnum"></a>
## Enum: EffectEnum
Name | Value
---- | -----
ALLOW | &quot;allow&quot;
DENY | &quot;deny&quot;



