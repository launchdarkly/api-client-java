

# Classifier


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**key** | **String** |  |  |
|**type** | [**TypeEnum**](#TypeEnum) |  |  |
|**instructions** | **String** |  |  |
|**criteria** | **Object** | Type-specific criteria. An enum classifier uses a map of option keys to descriptions. A float classifier uses an ordered list of level labels. A bool classifier uses optional true and false descriptions.  |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| BOOL | &quot;bool&quot; |
| FLOAT | &quot;float&quot; |
| ENUM | &quot;enum&quot; |



