

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
| ENDS_WITH | &quot;endsWith&quot; |
| STARTS_WITH | &quot;startsWith&quot; |
| MATCHES | &quot;matches&quot; |
| CONTAINS | &quot;contains&quot; |
| LESS_THAN | &quot;lessThan&quot; |
| LESS_THAN_OR_EQUAL | &quot;lessThanOrEqual&quot; |
| GREATER_THAN | &quot;greaterThan&quot; |
| GREATER_THAN_OR_EQUAL | &quot;greaterThanOrEqual&quot; |
| BEFORE | &quot;before&quot; |
| AFTER | &quot;after&quot; |
| SEGMENT_MATCH | &quot;segmentMatch&quot; |
| SEM_VER_EQUAL | &quot;semVerEqual&quot; |
| SEM_VER_LESS_THAN | &quot;semVerLessThan&quot; |
| SEM_VER_GREATER_THAN | &quot;semVerGreaterThan&quot; |



