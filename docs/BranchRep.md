

# BranchRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | The branch name |  |
|**head** | **String** | An ID representing the branch HEAD. For example, a commit SHA. |  |
|**updateSequenceId** | **Long** | An optional ID used to prevent older data from overwriting newer data |  [optional] |
|**syncTime** | **Long** |  |  |
|**references** | [**List&lt;ReferenceRep&gt;**](ReferenceRep.md) | An array of flag references found on the branch |  [optional] |
|**links** | **Map&lt;String, Object&gt;** | The location and content type of related resources |  |



