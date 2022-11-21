

# FlagLinkRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  |
|**key** | **String** | The flag link key |  [optional] |
|**integrationKey** | **String** | The integration key for an integration whose &lt;code&gt;manifest.json&lt;/code&gt; includes the &lt;code&gt;flagLink&lt;/code&gt; capability, if this is a flag link for an existing integration |  [optional] |
|**id** | **String** | The ID of this flag link |  |
|**deepLink** | **String** | The URL for the external resource the flag is linked to |  |
|**timestamp** | [**TimestampRep**](TimestampRep.md) |  |  |
|**title** | **String** | The title of the flag link |  [optional] |
|**description** | **String** | The description of the flag link |  [optional] |
|**metadata** | **Map&lt;String, String&gt;** | The metadata required by this integration in order to create a flag link, if this is a flag link for an existing integration. Defined in the integration&#39;s &lt;code&gt;manifest.json&lt;/code&gt; file under &lt;code&gt;flagLink&lt;/code&gt;. |  [optional] |
|**createdAt** | **Long** |  |  |
|**member** | [**FlagLinkMember**](FlagLinkMember.md) |  |  [optional] |



