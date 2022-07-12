

# Webhook


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | Links to other resources within the API. Includes the URL and content type of those resources. |  |
|**id** | **String** | The ID of this webhook |  |
|**name** | **String** | A human-readable name for this webhook |  [optional] |
|**url** | **String** | The URL to which LaunchDarkly sends an HTTP POST payload for this webhook |  |
|**secret** | **String** | The secret for this webhook |  [optional] |
|**statements** | [**List&lt;Statement&gt;**](Statement.md) | Represents a Custom role policy, defining a resource kinds filter the webhook responds to. |  [optional] |
|**on** | **Boolean** | Whether or not this webhook is enabled |  |
|**tags** | **List&lt;String&gt;** | List of tags for this webhook |  |
|**access** | [**Access**](Access.md) |  |  [optional] |



