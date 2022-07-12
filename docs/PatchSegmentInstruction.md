

# PatchSegmentInstruction


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**kind** | [**KindEnum**](#KindEnum) | The type of change to make to the user&#39;s removal date from this segment |  |
|**userKey** | **String** | A unique key used to represent the user |  |
|**targetType** | [**TargetTypeEnum**](#TargetTypeEnum) | The segment&#39;s target type |  |
|**value** | **Integer** | The time, in Unix milliseconds, when the user should be removed from this segment. Required if &lt;code&gt;kind&lt;/code&gt; is &lt;code&gt;addExpireUserTargetDate&lt;/code&gt; or &lt;code&gt;updateExpireUserTargetDate&lt;/code&gt;. |  [optional] |
|**version** | **Integer** | The version of the segment to update. Required if &lt;code&gt;kind&lt;/code&gt; is &lt;code&gt;updateExpireUserTargetDate&lt;/code&gt;. |  [optional] |



## Enum: KindEnum

| Name | Value |
|---- | -----|
| ADDEXPIREUSERTARGETDATE | &quot;addExpireUserTargetDate&quot; |
| UPDATEEXPIREUSERTARGETDATE | &quot;updateExpireUserTargetDate&quot; |
| REMOVEEXPIREUSERTARGETDATE | &quot;removeExpireUserTargetDate&quot; |



## Enum: TargetTypeEnum

| Name | Value |
|---- | -----|
| INCLUDED | &quot;included&quot; |
| EXCLUDED | &quot;excluded&quot; |



