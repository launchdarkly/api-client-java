

# EnvironmentRelease

A flag release in one environment, made up of one or more ordered phases. The top-level status is derived from the phases: it reflects the active phase, reports `waiting` between successfully completed phases, and reports the terminal outcome of the release. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **UUID** |  |  |
|**status** | **ReleaseStatus** |  |  |
|**statusReason** | **ReleaseStatusReason** |  |  [optional] |
|**projectKey** | **String** |  |  |
|**environmentKey** | **String** |  |  |
|**flagKey** | **String** |  |  |
|**originalVariationId** | **UUID** | ID of the variation the release started from. |  |
|**originalVariationValue** | **Object** | The value of the variation. This may be a string, boolean, number, JSON object, or array. |  |
|**targetVariationId** | **UUID** | ID of the variation being released. |  |
|**targetVariationValue** | **Object** | The value of the variation. This may be a string, boolean, number, JSON object, or array. |  |
|**randomizationUnit** | **String** | Key of the context kind the release randomizes on. |  |
|**cleanupBehavior** | **CleanupBehavior** |  |  |
|**kind** | **ReleaseType** |  |  |
|**metrics** | [**List&lt;ReleaseMetric&gt;**](ReleaseMetric.md) |  |  |
|**phases** | [**List&lt;EnvironmentReleasePhase&gt;**](EnvironmentReleasePhase.md) |  |  |
|**currentPhaseIndex** | **Integer** | Zero-based index of the active phase. Null before the first phase, between phases, and after the release reaches a terminal status. |  |
|**advancementMode** | **EnvironmentReleaseAdvancementMode** |  |  |
|**createdAt** | **Long** | Creation time in Unix milliseconds. |  |
|**startedAt** | **Long** | Start of the first phase in Unix milliseconds. Absent until a phase has started. |  [optional] [readonly] |
|**endedAt** | **Long** | End of the last started phase in Unix milliseconds. Present only after the release reaches a terminal status of &#x60;completed&#x60; or &#x60;reverted&#x60;. |  [optional] [readonly] |
|**links** | [**Map&lt;String, EnvironmentReleaseLink&gt;**](EnvironmentReleaseLink.md) |  |  [optional] |



