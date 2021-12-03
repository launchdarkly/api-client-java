

# FeatureFlagBody


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** | A human-friendly name for the feature flag | 
**key** | **String** | A unique key to reference the flag in your code | 
**description** | **String** | Description of the feature flag |  [optional]
**includeInSnippet** | **Boolean** | Deprecated, use clientSideAvailability. Whether or not this flag should be made available to the client-side JavaScript SDK |  [optional]
**clientSideAvailability** | [**ClientSideAvailabilityPost**](ClientSideAvailabilityPost.md) |  |  [optional]
**variations** | [**List&lt;Variation&gt;**](Variation.md) | An array of possible variations for the flag |  [optional]
**variationJsonSchema** | **Object** |  |  [optional]
**temporary** | **Boolean** | Whether or not the flag is a temporary flag |  [optional]
**tags** | **List&lt;String&gt;** | Tags for the feature flag |  [optional]
**customProperties** | [**Map&lt;String, CustomProperty&gt;**](CustomProperty.md) |  |  [optional]
**defaults** | [**Defaults**](Defaults.md) |  |  [optional]



