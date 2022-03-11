

# FlagGlobalAttributesRep


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** | A human-friendly name for the feature flag | 
**kind** | [**KindEnum**](#KindEnum) | Kind of feature flag | 
**description** | **String** | Description of the feature flag |  [optional]
**key** | **String** | A unique key used to reference the flag in your code | 
**version** | **Integer** | Version of the feature flag | 
**creationDate** | **Long** |  | 
**includeInSnippet** | **Boolean** | Deprecated, use clientSideAvailability. Whether or not this flag should be made available to the client-side JavaScript SDK |  [optional]
**clientSideAvailability** | [**ClientSideAvailability**](ClientSideAvailability.md) |  |  [optional]
**variations** | [**List&lt;Variation&gt;**](Variation.md) | An array of possible variations for the flag | 
**variationJsonSchema** | **Object** |  |  [optional]
**temporary** | **Boolean** | Whether or not the flag is a temporary flag | 
**tags** | **List&lt;String&gt;** | Tags for the feature flag | 
**links** | [**Map&lt;String, Link&gt;**](Link.md) |  | 
**maintainerId** | **String** | Associated maintainerId for the feature flag |  [optional]
**maintainer** | [**MemberSummary**](MemberSummary.md) |  |  [optional]
**goalIds** | **List&lt;String&gt;** |  |  [optional]
**experiments** | [**ExperimentInfoRep**](ExperimentInfoRep.md) |  | 
**customProperties** | [**Map&lt;String, CustomProperty&gt;**](CustomProperty.md) |  | 
**archived** | **Boolean** | Boolean indicating if the feature flag is archived | 
**archivedDate** | **Long** |  |  [optional]
**defaults** | [**Defaults**](Defaults.md) |  |  [optional]



## Enum: KindEnum

Name | Value
---- | -----
BOOLEAN | &quot;boolean&quot;
MULTIVARIATE | &quot;multivariate&quot;



