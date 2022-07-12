

# SubscriptionPost


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | A human-friendly name for your audit log subscription. |  |
|**statements** | [**List&lt;StatementPost&gt;**](StatementPost.md) |  |  [optional] |
|**on** | **Boolean** | Whether or not you want your subscription to actively send events. |  [optional] |
|**tags** | **List&lt;String&gt;** | An array of tags for this subscription. |  [optional] |
|**config** | **Map&lt;String, Object&gt;** | The unique set of fields required to configure an audit log subscription integration of this type. Refer to the &lt;code&gt;formVariables&lt;/code&gt; field in the corresponding &lt;code&gt;manifest.json&lt;/code&gt; at https://github.com/launchdarkly/integration-framework/tree/main/integrations for a full list of fields for the integration you wish to configure. |  |
|**url** | **String** | Slack webhook receiver URL. Only necessary for legacy Slack webhook integrations. |  [optional] |
|**apiKey** | **String** | Datadog API key. Only necessary for legacy Datadog webhook integrations. |  [optional] |



