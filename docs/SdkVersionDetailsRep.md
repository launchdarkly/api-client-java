

# SdkVersionDetailsRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** |  |  [optional] |
|**version** | **String** |  |  [optional] |
|**type** | **String** |  |  [optional] |
|**projectId** | **String** |  |  [optional] |
|**projectKey** | **String** |  |  [optional] |
|**projectName** | **String** |  |  [optional] |
|**environmentId** | **String** |  |  [optional] |
|**environmentKey** | **String** |  |  [optional] |
|**environmentName** | **String** |  |  [optional] |
|**applicationId** | **String** |  |  [optional] |
|**ldLatestVersion** | **String** |  |  [optional] |
|**eolStatus** | **String** | The end of life (EOL) status of the SDK version. Possible values are: &lt;br/&gt;- &lt;code&gt;EolAllClear&lt;/code&gt;: the SDK version is current&lt;br/&gt;- &lt;code&gt;EolNear&lt;/code&gt;: the SDK version is approaching EOL&lt;br/&gt;- &lt;code&gt;EolPast&lt;/code&gt;: the SDK version is past EOL&lt;br/&gt;- &lt;code&gt;MajorVersionAvailable&lt;/code&gt;: a new major version is available but the current version is not near EOL&lt;br/&gt;- &lt;code&gt;EolUnknown&lt;/code&gt;: the EOL status cannot be determined. |  [optional] |
|**latestReleaseUrl** | **String** |  |  [optional] |
|**connectionType** | **String** |  |  [optional] |
|**relayVersion** | **String** |  |  [optional] |
|**relayEolStatus** | **String** | The end of life status of the Relay Proxy version. Only present when the SDK connects through a Relay Proxy. Uses the same values as &lt;code&gt;eolStatus&lt;/code&gt;. |  [optional] |
|**relayLatestVersion** | **String** |  |  [optional] |
|**relayLatestReleaseUrl** | **String** |  |  [optional] |



