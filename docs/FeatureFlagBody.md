
# FeatureFlagBody

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** | A human-friendly name for the feature flag. Remember to note if this flag is intended to be temporary or permanent. | 
**key** | **String** | A unique key that will be used to reference the flag in your code. | 
**variations** | [**List&lt;Variation&gt;**](Variation.md) | An array of possible variations for the flag. | 
**temporary** | **Boolean** | Whether or not the flag is a temporary flag. |  [optional]
**tags** | **List&lt;String&gt;** | Tags for the feature flag. |  [optional]
**includeInSnippet** | **Boolean** | Whether or not this flag should be made available to the client-side JavaScript SDK. |  [optional]



