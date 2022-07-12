

# AuditLogEntryRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**links** | [**Map&lt;String, Link&gt;**](Link.md) |  |  |
|**id** | **String** |  |  |
|**accountId** | **String** |  |  |
|**date** | **Long** |  |  |
|**accesses** | [**List&lt;ResourceAccess&gt;**](ResourceAccess.md) |  |  |
|**kind** | **String** |  |  |
|**name** | **String** |  |  |
|**description** | **String** |  |  |
|**shortDescription** | **String** |  |  |
|**comment** | **String** |  |  [optional] |
|**subject** | [**SubjectDataRep**](SubjectDataRep.md) |  |  [optional] |
|**member** | [**MemberDataRep**](MemberDataRep.md) |  |  [optional] |
|**token** | [**TokenDataRep**](TokenDataRep.md) |  |  [optional] |
|**app** | [**AuthorizedAppDataRep**](AuthorizedAppDataRep.md) |  |  [optional] |
|**titleVerb** | **String** |  |  [optional] |
|**title** | **String** |  |  [optional] |
|**target** | [**TargetResourceRep**](TargetResourceRep.md) |  |  [optional] |
|**parent** | [**ParentResourceRep**](ParentResourceRep.md) |  |  [optional] |
|**delta** | **Object** |  |  [optional] |
|**triggerBody** | **Object** |  |  [optional] |
|**merge** | **Object** |  |  [optional] |
|**previousVersion** | **Object** |  |  [optional] |
|**currentVersion** | **Object** |  |  [optional] |
|**subentries** | [**List&lt;AuditLogEntryListingRep&gt;**](AuditLogEntryListingRep.md) |  |  [optional] |



