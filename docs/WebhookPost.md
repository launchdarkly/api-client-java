

# WebhookPost


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** | A human-readable name for your webhook |  [optional]
**url** | **String** | The URL of the remote webhook | 
**secret** | **String** | If sign is true, and the secret attribute is omitted, LaunchDarkly automatically generates a secret for you. |  [optional]
**statements** | [**List&lt;StatementPost&gt;**](StatementPost.md) |  |  [optional]
**sign** | **Boolean** | If sign is false, the webhook does not include a signature header, and the secret can be omitted. | 
**on** | **Boolean** | Whether or not this webhook is enabled. | 
**tags** | **List&lt;String&gt;** | List of tags for this webhook |  [optional]



