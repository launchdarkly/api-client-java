

# FlagStatusRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | [**NameEnum**](#NameEnum) | Status of the flag |  |
|**lastRequested** | **OffsetDateTime** | Timestamp of last time flag was requested |  [optional] |
|**_default** | **Object** | Default value seen from code |  [optional] |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) |  |  |



## Enum: NameEnum

| Name | Value |
|---- | -----|
| NEW | &quot;new&quot; |
| INACTIVE | &quot;inactive&quot; |
| ACTIVE | &quot;active&quot; |
| LAUNCHED | &quot;launched&quot; |



