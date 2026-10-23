

# RestrictedModelError


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**key** | **String** | The model config key that was not added |  |
|**message** | **String** | Why the key was not added. A key that a config variation uses cannot be restricted. Move those variations to a different model first. |  |
|**code** | **Integer** | HTTP status code for this key. &#x60;400&#x60; means a config variation uses the key. &#x60;404&#x60; means the key does not exist in the project. |  |



