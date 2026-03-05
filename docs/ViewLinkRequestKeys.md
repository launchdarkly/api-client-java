

# ViewLinkRequestKeys


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**keys** | **List&lt;String&gt;** | Keys of the resources (flags, segments) to link/unlink |  |
|**filter** | **String** | Optional filter string to determine which resources should be linked. Resources only need to match either the filter or explicitly-listed keys to be linked (union). Uses the same syntax as list endpoints: flags use comma-separated field:value filters, segments use queryfilter syntax.  Supported filters by resource type: - flags: query, tags, maintainerId, maintainerTeamKey, type, status, state, staleState, sdkAvailability, targeting, hasExperiment, hasDataExport, evaluated, creationDate, contextKindTargeted, contextKindsEvaluated, filterEnv, segmentTargeted, codeReferences.min, codeReferences.max, excludeSettings, releasePipeline, applicationEvaluated, purpose, guardedRollout, view, key, name, archived, followerId - segments (queryfilter): query, tags, keys, excludedKeys, unbounded, external, view, type Some filters are only available when the corresponding feature is enabled on your account.  |  [optional] |
|**comment** | **String** | Optional comment for the link/unlink operation |  [optional] |



