

# WarehouseDestinationSetupScriptRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**script** | **String** | The SQL setup script to run in your data warehouse |  [optional] |
|**publicKey** | **String** | The RSA public key (Snowflake only) to store as the destination public_key |  [optional] |
|**publicKeyPkcs8** | **String** | The PKCS8 RSA public key (Snowflake only) |  [optional] |
|**redshiftIAMPermissionsPolicy** | **String** | For Redshift, present only when clusterIdentifier, clusterRegion, and clusterAwsAccountId are supplied in the request body. |  [optional] |
|**redshiftIAMTrustPolicy** | **String** | For Redshift, present only when clusterIdentifier, clusterRegion, and clusterAwsAccountId are supplied in the request body. |  [optional] |
|**s3BucketName** | **String** | The auto-generated S3 staging bucket name (ClickHouse and Redshift) |  [optional] |



