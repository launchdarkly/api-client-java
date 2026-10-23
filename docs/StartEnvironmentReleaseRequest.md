

# StartEnvironmentReleaseRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**beforeRuleId** | **String** | Insert the newly created rule or phase rules before this rule. If omitted, append them to the end of the rule list.  |  [optional] |
|**originalVariationId** | **UUID** | ID of the variation the release starts from. The release gradually moves traffic from it to the target variation, and moves traffic back to it if the release reverts.  |  |
|**targetVariationId** | **UUID** | ID of the variation being released. The release gradually moves traffic to it from the original variation, and moves traffic back to the original variation if the release reverts.  |  |
|**releasePlanKey** | **String** | Key of an environment release plan in this environment. The plan supplies &#x60;randomizationUnit&#x60;, &#x60;phases&#x60;, &#x60;metrics&#x60;, &#x60;advancementMode&#x60;, and &#x60;cleanupBehavior&#x60;, so omit them. Omit this field to configure the release inline.  |  [optional] |
|**randomizationUnit** | **String** | Key of an existing, non-archived context kind in the project that the release randomizes on, such as &#x60;user&#x60;. Each phase&#39;s rule must include the clause &#x60;{\&quot;contextKind\&quot;: \&quot;&lt;unit&gt;\&quot;, \&quot;attribute\&quot;: \&quot;kind\&quot;, \&quot;op\&quot;: \&quot;in\&quot;, \&quot;values\&quot;: [\&quot;&lt;unit&gt;\&quot;]}&#x60;. Required unless &#x60;releasePlanKey&#x60; is set.  |  [optional] |
|**advancementMode** | **EnvironmentReleaseAdvancementMode** | How the release moves between phases. Required when &#x60;phases&#x60; has more than one entry; a single-phase release ignores it and may omit it. Omit when &#x60;releasePlanKey&#x60; is set.  |  [optional] |
|**phases** | [**List&lt;EnvironmentReleasePhaseInput&gt;**](EnvironmentReleasePhaseInput.md) | Ordered phases of the release. Required unless &#x60;releasePlanKey&#x60; is set. |  [optional] |
|**metrics** | [**List&lt;EnvironmentReleaseMetricInput&gt;**](EnvironmentReleaseMetricInput.md) | Metrics shared by every phase. Omit or pass an empty array for a progressive release. Metric groups cannot be listed inline; a release plan&#39;s metric groups expand into their member metrics at start. Omit when &#x60;releasePlanKey&#x60; is set.  |  [optional] |
|**bypassHealthChecks** | **Set&lt;ReleaseHealthCheckKind&gt;** | Health check kinds that do not block this request. LaunchDarkly still runs and enforces every health check kind not listed here.  |  [optional] |
|**cleanupBehavior** | **CleanupBehavior** | What happens to the release rules after the release completes. Omit for &#x60;leave_rules_in_place&#x60;. Omit when &#x60;releasePlanKey&#x60; is set.  |  [optional] |
|**comment** | **String** | Optional comment written to the flag&#39;s audit-log entry. |  [optional] |



