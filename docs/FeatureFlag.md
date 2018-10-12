
# FeatureFlag

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**key** | **String** |  |  [optional]
**name** | **String** | Name of the feature flag. |  [optional]
**description** | **String** | Description of the feature flag. |  [optional]
**kind** | **String** | Whether the feature flag is a boolean flag or multivariate. |  [optional]
**creationDate** | [**BigDecimal**](BigDecimal.md) | A unix epoch time in milliseconds specifying the creation time of this flag. |  [optional]
**includeInSnippet** | **Boolean** |  |  [optional]
**temporary** | **Boolean** | Whether or not this flag is temporary. |  [optional]
**maintainerId** | **String** | The ID of the member that should maintain this flag. |  [optional]
**tags** | **List&lt;String&gt;** | An array of tags for this feature flag. |  [optional]
**variations** | [**List&lt;Variation&gt;**](Variation.md) | The variations for this feature flag. |  [optional]
**customProperties** | [**CustomProperties**](CustomProperties.md) |  |  [optional]
**links** | [**Links**](Links.md) |  |  [optional]
**maintainer** | [**Member**](Member.md) |  |  [optional]
**environments** | [**Map&lt;String, FeatureFlagConfig&gt;**](FeatureFlagConfig.md) |  |  [optional]



