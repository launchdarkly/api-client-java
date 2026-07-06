

# SdkKey


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**links** | [**ParentAndSelfLinks**](ParentAndSelfLinks.md) |  |  [optional] |
|**kind** | **SdkKeyKind** |  |  |
|**key** | **String** | The user-defined identifying key of the SDK key. This is used solely to identify an SDK key and is distinct from the value field, which is the actual SDK key value. |  |
|**name** | **String** | The human-readable name of the SDK key. |  |
|**description** | **String** | The optional description of the SDK key. |  [optional] |
|**expiry** | **Long** |  |  [optional] |
|**value** | **String** | The string value of the SDK key. Use this when configuring your SDK. |  |
|**isDefault** | **Boolean** | Indicates if this SDK key is the system-defined default for the environment. There may also be an expiring default SDK key for the environment (not possible with mobile keys). |  |
|**createdByMemberId** | **String** | The ID of the member who created the SDK key. This field is immutable. |  [optional] |
|**createdAt** | **Long** |  |  |
|**updatedAt** | **Long** |  |  |
|**version** | **Integer** | The auto-incremented version number of the SDK key. |  |
|**viewSummaries** | [**List&lt;ViewSummary&gt;**](ViewSummary.md) | Summaries of views associated with the SDK key. |  [optional] |



