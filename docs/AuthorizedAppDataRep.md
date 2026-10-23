

# AuthorizedAppDataRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**links** | [**Map&lt;String, Link&gt;**](Link.md) |  |  [optional] |
|**id** | **String** | The ID of the authorized application |  [optional] |
|**isScim** | **Boolean** | Whether the application is authorized through SCIM |  [optional] |
|**name** | **String** | The authorized application name |  [optional] |
|**iconUrl** | **String** | The icon for this authorized application, if it has one |  [optional] |
|**maintainerName** | **String** | The name of the maintainer for this authorized application |  [optional] |
|**isAgent** | **Boolean** | Whether this application acted on behalf of the member in this entry. When true, the member and the application are co-authors of the change rather than the member acting alone. Omitted for applications that act on their own. |  [optional] |



