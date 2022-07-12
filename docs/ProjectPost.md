

# ProjectPost


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | A human-friendly name for the project. |  |
|**key** | **String** | A unique key used to reference the project in your code. |  |
|**includeInSnippetByDefault** | **Boolean** | Whether or not flags created in this project are made available to the client-side JavaScript SDK by default. |  [optional] |
|**defaultClientSideAvailability** | [**DefaultClientSideAvailabilityPost**](DefaultClientSideAvailabilityPost.md) |  |  [optional] |
|**tags** | **List&lt;String&gt;** | Tags for the project |  [optional] |
|**environments** | [**List&lt;EnvironmentPost&gt;**](EnvironmentPost.md) | Creates the provided environments for this project. If omitted default environments will be created instead. |  [optional] |



