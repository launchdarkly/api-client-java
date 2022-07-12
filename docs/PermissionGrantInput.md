

# PermissionGrantInput


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**actionSet** | [**ActionSetEnum**](#ActionSetEnum) | A group of related actions to allow. Specify either &lt;code&gt;actionSet&lt;/code&gt; or &lt;code&gt;actions&lt;/code&gt;. Use &lt;code&gt;maintainTeam&lt;/code&gt; to add team maintainers. |  [optional] |
|**actions** | **List&lt;String&gt;** | A list of actions to allow. Specify either &lt;code&gt;actionSet&lt;/code&gt; or &lt;code&gt;actions&lt;/code&gt;. To learn more, read [Role actions](https://docs.launchdarkly.com/home/members/role-actions). |  [optional] |
|**memberIDs** | **List&lt;String&gt;** | A list of member IDs who receive the permission grant. |  [optional] |



## Enum: ActionSetEnum

| Name | Value |
|---- | -----|
| MAINTAINTEAM | &quot;maintainTeam&quot; |



