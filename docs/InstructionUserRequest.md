

# InstructionUserRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**kind** | [**KindEnum**](#KindEnum) | The type of change to make to the removal date for this user from individual targeting for this flag. |  |
|**flagKey** | **String** | The flag key |  |
|**variationId** | **String** | ID of a variation on the flag |  |
|**value** | **Integer** | The time, in Unix milliseconds, when LaunchDarkly should remove the user from individual targeting for this flag. Required if &lt;code&gt;kind&lt;/code&gt; is &lt;code&gt;addExpireUserTargetDate&lt;/code&gt; or &lt;code&gt;updateExpireUserTargetDate&lt;/code&gt;. |  [optional] |
|**version** | **Integer** | The version of the flag variation to update. You can retrieve this by making a GET request for the flag. Required if &lt;code&gt;kind&lt;/code&gt; is &lt;code&gt;updateExpireUserTargetDate&lt;/code&gt;. |  [optional] |



## Enum: KindEnum

| Name | Value |
|---- | -----|
| ADDEXPIREUSERTARGETDATE | &quot;addExpireUserTargetDate&quot; |
| UPDATEEXPIREUSERTARGETDATE | &quot;updateExpireUserTargetDate&quot; |
| REMOVEEXPIREUSERTARGETDATE | &quot;removeExpireUserTargetDate&quot; |



