
# RelayProxyConfig

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  | 
**creator** | [**Member**](Member.md) |  | 
**name** | **String** | A human-friendly name for the relay proxy configuration | 
**policy** | [**List&lt;Policy&gt;**](Policy.md) |  | 
**fullKey** | **String** | Full secret key. Only included if creating or resetting the relay proxy configuration |  [optional]
**displayKey** | **String** | The last 4 digits of the unique secret key for this relay proxy configuration | 
**creationDate** | **Long** | A unix epoch time in milliseconds specifying the creation time of this relay proxy configuration | 
**lastModified** | **Long** | A unix epoch time in milliseconds specifying the last time this relay proxy configuration was modified | 



