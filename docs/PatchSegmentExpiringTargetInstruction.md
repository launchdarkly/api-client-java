

# PatchSegmentExpiringTargetInstruction


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**kind** | [**KindEnum**](#KindEnum) | The type of change to make to the context&#39;s removal date from this segment |  |
|**contextKey** | **String** | A unique key used to represent the context |  |
|**contextKind** | **String** | The kind of context |  |
|**targetType** | [**TargetTypeEnum**](#TargetTypeEnum) | The segment&#39;s target type |  |
|**value** | **Long** | The time, in Unix milliseconds, when the context should be removed from this segment. Required if &lt;code&gt;kind&lt;/code&gt; is &lt;code&gt;addExpiringTarget&lt;/code&gt; or &lt;code&gt;updateExpiringTarget&lt;/code&gt;. |  [optional] |
|**version** | **Integer** | The version of the expiring target to update. Optional and only used if &lt;code&gt;kind&lt;/code&gt; is &lt;code&gt;updateExpiringTarget&lt;/code&gt;. If included, update will fail if version doesn&#39;t match current version of the expiring target. |  [optional] |



## Enum: KindEnum

| Name | Value |
|---- | -----|
| ADDEXPIRINGTARGET | &quot;addExpiringTarget&quot; |
| UPDATEEXPIRINGTARGET | &quot;updateExpiringTarget&quot; |
| REMOVEEXPIRINGTARGET | &quot;removeExpiringTarget&quot; |



## Enum: TargetTypeEnum

| Name | Value |
|---- | -----|
| INCLUDED | &quot;included&quot; |
| EXCLUDED | &quot;excluded&quot; |



