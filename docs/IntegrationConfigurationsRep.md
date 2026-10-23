

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
|**snowflakeSetupScript** | **String** | Consolidated SQL script for Snowflake Warehouse Native Experimentation setup. Present only for setup endpoint responses. |  [optional] |
|**redshiftSetupScripts** | **List&lt;String&gt;** | SQL setup scripts (4 parts) for Redshift native Experimentation setup. Present on setup endpoint responses and, rehydrated from the persisted config, on active (finalized) Redshift native Experimentation configurations. |  [optional] |
|**redshiftIAMPermissionsPolicy** | **String** | IAM permissions policy JSON for the customer&#39;s Redshift IAM role. Present only for setup endpoint responses. |  [optional] |
|**redshiftIAMTrustPolicy** | **String** | IAM trust policy JSON for the customer&#39;s Redshift IAM role. Present only for setup endpoint responses. |  [optional] |
|**clickHouseSetupScript** | **String** | SQL setup script for ClickHouse native Experimentation. On setup endpoint responses it also creates the experimentation user with the submitted password. When the two-step setup flow is enabled, it is rehydrated from the persisted config on other responses with only the grants, never the password. |  [optional] |
|**maintainerId** | **String** | The ID of the member who maintains this integration configuration. Defaults to the member who created it. |  [optional] |



