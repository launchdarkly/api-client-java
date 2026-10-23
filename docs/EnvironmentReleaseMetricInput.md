

# EnvironmentReleaseMetricInput

A metric to monitor across every phase of a guarded release.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**metricKey** | **String** |  |  |
|**autoRollback** | **Boolean** | Whether a regression on this metric automatically reverts the release. When &#x60;false&#x60;, a detected regression pauses the release instead: it reports &#x60;waiting&#x60; with reason &#x60;waiting_regression_detected&#x60; and does not advance until the regression is dismissed or the release is stopped.  |  [optional] |



