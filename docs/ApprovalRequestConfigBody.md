
# ApprovalRequestConfigBody

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**description** | **String** | A name that describes the changes you would like to apply to a feature flag configuration | 
**instructions** | [**SemanticPatchInstruction**](SemanticPatchInstruction.md) |  | 
**notifyMemberIds** | **List&lt;String&gt;** |  | 
**comment** | **String** | comment will be included in audit log item for change. |  [optional]
**executionDate** | **Long** | Timestamp for when instructions will be executed |  [optional]
**operatingOnId** | **String** | ID of scheduled change to edit or delete |  [optional]



