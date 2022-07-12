

# RuleClause


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**attribute** | **String** | The attribute the rule applies to, for example, last name or email address |  [optional] |
|**op** | [**OpEnum**](#OpEnum) | The operator to apply to the given attribute |  [optional] |
|**negate** | **Boolean** | Whether the operator should be negated |  [optional] |



## Enum: OpEnum

| Name | Value |
|---- | -----|
| IN | &quot;in&quot; |
| ENDSWITH | &quot;endsWith&quot; |
| STARTSWITH | &quot;startsWith&quot; |
| MATCHES | &quot;matches&quot; |
| CONTAINS | &quot;contains&quot; |
| LESSTHAN | &quot;lessThan&quot; |
| LESSTHANOREQUAL | &quot;lessThanOrEqual&quot; |
| GREATERTHAN | &quot;greaterThan&quot; |
| GREATERTHANOREQUAL | &quot;greaterThanOrEqual&quot; |
| BEFORE | &quot;before&quot; |
| AFTER | &quot;after&quot; |
| SEGMENTMATCH | &quot;segmentMatch&quot; |
| SEMVEREQUAL | &quot;semVerEqual&quot; |
| SEMVERLESSTHAN | &quot;semVerLessThan&quot; |
| SEMVERGREATERTHAN | &quot;semVerGreaterThan&quot; |



