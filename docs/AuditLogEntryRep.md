

# AuditLogEntryRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  |
|**id** | **String** | The ID of the audit log entry |  |
|**accountId** | **String** | The ID of the account to which this audit log entry belongs |  |
|**date** | **Long** |  |  |
|**accesses** | [**List&lt;ResourceAccess&gt;**](ResourceAccess.md) | Details on the actions performed and resources acted on in this audit log entry |  |
|**kind** | **String** |  |  |
|**name** | **String** | The name of the resource this audit log entry refers to |  |
|**description** | **String** | Description of the change recorded in the audit log entry |  |
|**shortDescription** | **String** | Shorter version of the change recorded in the audit log entry |  |
|**comment** | **String** | Optional comment for the audit log entry |  [optional] |
|**subject** | [**SubjectDataRep**](SubjectDataRep.md) |  |  [optional] |
|**member** | [**MemberDataRep**](MemberDataRep.md) |  |  [optional] |
|**token** | [**TokenDataRep**](TokenDataRep.md) |  |  [optional] |
|**app** | [**AuthorizedAppDataRep**](AuthorizedAppDataRep.md) |  |  [optional] |
|**titleVerb** | **String** | The action and resource recorded in this audit log entry |  [optional] |
|**title** | **String** | A description of what occurred, in the format &lt;code&gt;member&lt;/code&gt; &lt;code&gt;titleVerb&lt;/code&gt; &lt;code&gt;target&lt;/code&gt; |  [optional] |
|**target** | [**TargetResourceRep**](TargetResourceRep.md) |  |  [optional] |
|**parent** | [**ParentResourceRep**](ParentResourceRep.md) |  |  [optional] |
|**delta** | **Object** | If the audit log entry has been updated, this is the JSON patch body that was used in the request to update the entity |  [optional] |
|**triggerBody** | **Object** | A JSON representation of the external trigger for this audit log entry, if any |  [optional] |
|**merge** | **Object** | A JSON representation of the merge information for this audit log entry, if any |  [optional] |
|**previousVersion** | **Object** | If the audit log entry has been updated, this is a JSON representation of the previous version of the entity |  [optional] |
|**currentVersion** | **Object** | If the audit log entry has been updated, this is a JSON representation of the current version of the entity |  [optional] |
|**subentries** | [**List&lt;AuditLogEntryListingRep&gt;**](AuditLogEntryListingRep.md) |  |  [optional] |



