

# ReleaseMetric

A metric shared by every phase of a guarded release.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**metricKey** | **String** |  |  |
|**metricVersionId** | **UUID** |  |  [optional] [readonly] |
|**autoRollback** | **Boolean** | Whether a regression automatically reverts the release. |  |
|**definition** | [**EnvironmentReleasesMetricListingRep**](EnvironmentReleasesMetricListingRep.md) | Present only when the caller requests &#x60;expand&#x3D;metrics&#x60; and the pinned metric version still exists. A metric whose pinned version has been deleted keeps its &#x60;metricKey&#x60; and &#x60;metricVersionId&#x60; but has no definition.  |  [optional] [readonly] |



