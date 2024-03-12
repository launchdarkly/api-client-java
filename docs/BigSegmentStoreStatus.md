

# BigSegmentStoreStatus


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**available** | **Boolean** | Whether the persistent store integration is fully synchronized with the LaunchDarkly environment, and the &lt;code&gt;lastSync&lt;/code&gt; occurred within a few minutes |  [optional] |
|**potentiallyStale** | **Boolean** | Whether the persistent store integration may not be fully synchronized with the LaunchDarkly environment. &lt;code&gt;true&lt;/code&gt; if the integration could be stale. |  [optional] |
|**lastSync** | **Long** |  |  [optional] |
|**lastError** | **Long** |  |  [optional] |
|**errors** | [**List&lt;StoreIntegrationError&gt;**](StoreIntegrationError.md) |  |  [optional] |



