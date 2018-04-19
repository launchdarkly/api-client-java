
# WebhookBody

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**url** | **String** | The URL of the remote webhook. | 
**secret** | **String** | If sign is true, and the secret attribute is omitted, LaunchDarkly will automatically generate a secret for you. |  [optional]
**sign** | **Boolean** | If sign is false, the webhook will not include a signature header, and the secret can be omitted. | 
**on** | **Boolean** | Whether this webhook is enabled or not. | 
**name** | **String** | The name of the webhook. |  [optional]



