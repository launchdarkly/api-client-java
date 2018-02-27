
# Statement

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**resources** | [**Resources**](Resources.md) |  |  [optional]
**notresources** | [**Resources**](Resources.md) | Targeted resource will be those resources NOT in this list. The \&quot;resources&#x60;\&quot; field must be empty to use this field. |  [optional]
**actions** | [**Actions**](Actions.md) |  |  [optional]
**notactions** | [**Actions**](Actions.md) | Targeted actions will be those actions NOT in this list. The \&quot;actions\&quot; field must be empty to use this field. |  [optional]
**effect** | [**EffectEnum**](#EffectEnum) |  |  [optional]


<a name="EffectEnum"></a>
## Enum: EffectEnum
Name | Value
---- | -----
ALLOW | &quot;allow&quot;
DENY | &quot;deny&quot;



