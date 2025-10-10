

# InstructionUserRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**kind** | [**KindEnum**](#KindEnum) | The type of change to make to the removal date for this user from individual targeting for this flag. |  |
|**flagKey** | **String** | The flag key |  |
|**variationId** | **String** | ID of a variation on the flag |  |
|**value** | **Integer** | The time, in Unix milliseconds, when LaunchDarkly should remove the user from individual targeting for this flag. Required if &lt;code&gt;kind&lt;/code&gt; is &lt;code&gt;addExpireUserTargetDate&lt;/code&gt; or &lt;code&gt;updateExpireUserTargetDate&lt;/code&gt;. |  [optional] |
|**version** | **Integer** | The version of the expiring user target to update. Optional and only used if &lt;code&gt;kind&lt;/code&gt; is &lt;code&gt;updateExpireUserTargetDate&lt;/code&gt;. If included, update will fail if version doesn&#39;t match current version of the expiring user target. |  [optional] |



## Enum: KindEnum

| Name | Value |
|---- | -----|
| ADD_EXPIRE_USER_TARGET_DATE | &quot;addExpireUserTargetDate&quot; |
| UPDATE_EXPIRE_USER_TARGET_DATE | &quot;updateExpireUserTargetDate&quot; |
| REMOVE_EXPIRE_USER_TARGET_DATE | &quot;removeExpireUserTargetDate&quot; |



