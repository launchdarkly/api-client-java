

# IntegrationConfigurationsRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  |
|**id** | **String** | The unique identifier for this integration configuration |  |
|**name** | **String** | A human-friendly name for the integration |  |
|**createdAt** | **Long** |  |  [optional] |
|**integrationKey** | **String** | The type of integration |  [optional] |
|**tags** | **List&lt;String&gt;** | An array of tags for this integration |  [optional] |
|**enabled** | **Boolean** | Whether the integration is currently active |  [optional] |
|**access** | [**Access**](Access.md) |  |  [optional] |
|**configValues** | **Map&lt;String, Object&gt;** | Details on configuration for an integration of this type. Refer to the &lt;code&gt;formVariables&lt;/code&gt; field in the corresponding &lt;code&gt;manifest.json&lt;/code&gt; for a full list of fields for each integration. |  [optional] |
|**capabilityConfig** | [**CapabilityConfigRep**](CapabilityConfigRep.md) |  |  [optional] |



