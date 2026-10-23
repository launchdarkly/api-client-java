

# EnvironmentReleasePhase

One ordered phase of an environment release.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **UUID** |  |  |
|**phaseIndex** | **Integer** | Zero-based index of this phase. |  [readonly] |
|**ruleId** | **String** | ID of the flag rule for this phase. New public-API releases always have a rule ID. &#x60;null&#x60; is retained only when reading a historical release that ran on the default rule.  |  |
|**description** | **String** | The description of the flag rule when this release was created. Omitted for default-rule phases.  |  [optional] [readonly] |
|**status** | **ReleaseStatus** |  |  |
|**statusReason** | **ReleaseStatusReason** |  |  [optional] |
|**steps** | [**List&lt;ReleaseStep&gt;**](ReleaseStep.md) |  |  |
|**currentStepIndex** | **Integer** | Zero-based index of the step the phase is on. Omitted until the first step starts. Once the phase completes or reverts, it stays at the last step that started, unlike &#x60;currentPhaseIndex&#x60;, which becomes &#x60;null&#x60; when the release reaches a terminal status.  |  [optional] [readonly] |
|**startedAt** | **Long** | Start of the phase in Unix milliseconds. |  [optional] [readonly] |
|**endedAt** | **Long** | End of the phase in Unix milliseconds. |  [optional] [readonly] |
|**metricStatuses** | **Map&lt;String, ReleaseMetricStatus&gt;** | Per-phase metric status keyed by metric key. Empty for progressive releases. Metric definitions and monitoring preferences live once in the parent release&#39;s &#x60;metrics&#x60; array.  |  [readonly] |



