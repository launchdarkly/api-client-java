

# View


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**access** | [**ViewsAccessRep**](ViewsAccessRep.md) |  |  [optional] |
|**links** | [**ParentAndSelfLinks**](ParentAndSelfLinks.md) |  |  [optional] |
|**id** | **String** | Unique ID of this view |  |
|**accountId** | **String** | ID of the account that owns this view |  |
|**projectId** | **String** | ID of the project this view belongs to |  |
|**projectKey** | **String** | Key of the project this view belongs to |  |
|**key** | **String** | Unique key for the view within the account/project |  |
|**name** | **String** | Human-readable name for the view |  |
|**description** | **String** | Optional detailed description of the view |  |
|**generateSdkKeys** | **Boolean** | Whether to generate SDK keys for this view. Defaults to false. |  |
|**version** | **Integer** | Version number for tracking changes |  |
|**tags** | **List&lt;String&gt;** | Tags associated with this view |  |
|**createdAt** | **Long** |  |  |
|**updatedAt** | **Long** |  |  |
|**archived** | **Boolean** | Whether this view is archived |  |
|**archivedAt** | **Long** |  |  [optional] |
|**deletedAt** | **Long** |  |  [optional] |
|**deleted** | **Boolean** | Whether this view is deleted |  |
|**maintainer** | [**Maintainer**](Maintainer.md) |  |  [optional] |
|**flagsSummary** | [**FlagsSummary**](FlagsSummary.md) |  |  [optional] |
|**segmentsSummary** | [**SegmentsSummary**](SegmentsSummary.md) |  |  [optional] |
|**metricsSummary** | [**MetricsSummary**](MetricsSummary.md) |  |  [optional] |
|**aiConfigsSummary** | [**AIConfigsSummary**](AIConfigsSummary.md) |  |  [optional] |
|**resourceSummary** | [**ResourceSummary**](ResourceSummary.md) |  |  [optional] |
|**flagsExpanded** | [**ExpandedLinkedFlags**](ExpandedLinkedFlags.md) |  |  [optional] |
|**segmentsExpanded** | [**ExpandedLinkedSegments**](ExpandedLinkedSegments.md) |  |  [optional] |
|**metricsExpanded** | [**ExpandedLinkedMetrics**](ExpandedLinkedMetrics.md) |  |  [optional] |
|**aiConfigsExpanded** | [**ExpandedLinkedAIConfigs**](ExpandedLinkedAIConfigs.md) |  |  [optional] |
|**resourcesExpanded** | [**ExpandedLinkedResources**](ExpandedLinkedResources.md) |  |  [optional] |



