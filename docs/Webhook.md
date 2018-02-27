
# Webhook

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**links** | [**Links**](Links.md) |  |  [optional]
**id** | **String** |  |  [optional]
**url** | **String** | The URL of the remote webhook. |  [optional]
**secret** | **String** | If defined, the webhooks post request will include a X-LD-Signature header whose value will contain an HMAC SHA256 hex digest of the webhook payload, using the secret as the key. |  [optional]
**on** | **Boolean** | Whether this webhook is enabled or not. |  [optional]
**tags** | **List&lt;String&gt;** | Tags assigned to this webhook. |  [optional]



