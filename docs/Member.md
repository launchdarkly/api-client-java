

# Member


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  |
|**id** | **String** | The member&#39;s ID |  |
|**firstName** | **String** | The member&#39;s first name |  [optional] |
|**lastName** | **String** | The member&#39;s last name |  [optional] |
|**role** | **String** | The member&#39;s built-in role. If the member has no custom roles, this role will be in effect. |  |
|**email** | **String** | The member&#39;s email address |  |
|**pendingInvite** | **Boolean** | Whether the member has a pending invitation |  |
|**verified** | **Boolean** | Whether the member&#39;s email address has been verified |  |
|**pendingEmail** | **String** | The member&#39;s email address before it has been verified, for accounts where email verification is required |  [optional] |
|**customRoles** | **List&lt;String&gt;** | The set of custom roles (as keys) assigned to the member |  |
|**mfa** | **String** | Whether multi-factor authentication is enabled for this member |  |
|**excludedDashboards** | **List&lt;String&gt;** | Default dashboards that the member has chosen to ignore |  [optional] |
|**lastSeen** | **Long** |  |  |
|**lastSeenMetadata** | [**LastSeenMetadata**](LastSeenMetadata.md) |  |  [optional] |
|**integrationMetadata** | [**IntegrationMetadata**](IntegrationMetadata.md) |  |  [optional] |
|**teams** | [**List&lt;MemberTeamSummaryRep&gt;**](MemberTeamSummaryRep.md) | Details on the teams this member is assigned to |  [optional] |
|**permissionGrants** | [**List&lt;MemberPermissionGrantSummaryRep&gt;**](MemberPermissionGrantSummaryRep.md) | A list of permission grants. Permission grants allow a member to have access to a specific action, without having to create or update a custom role. |  [optional] |
|**creationDate** | **Long** |  |  |
|**oauthProviders** | **List&lt;String&gt;** | A list of OAuth providers |  [optional] |



