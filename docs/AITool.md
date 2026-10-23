

# AITool


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**key** | **String** | The tool key |  |
|**access** | [**AiConfigsAccess**](AiConfigsAccess.md) |  |  [optional] |
|**links** | [**ParentAndSelfLinks**](ParentAndSelfLinks.md) |  |  [optional] |
|**site** | [**CoreLink**](CoreLink.md) |  |  [optional] |
|**maintainer** | [**AIConfigMaintainer**](AIConfigMaintainer.md) |  |  [optional] |
|**description** | **String** | A description of the tool |  [optional] |
|**schema** | **Object** | JSON Schema defining the tool&#39;s parameters for LLM consumption |  |
|**customParameters** | **Object** | Custom metadata and configuration for application-level use (not sent to LLM) |  [optional] |
|**tags** | **List&lt;String&gt;** | Tags for the tool. Tags can be used in custom role policies. |  [optional] |
|**version** | **Integer** | Version of the tool. Starts at 1, and each update that changes the description, schema, custom parameters, or tags creates a new version. Pass this value with &#x60;key&#x60; in a variation&#39;s &#x60;tools: [{key, version}]&#x60;. |  |
|**createdAt** | **Long** |  |  |



