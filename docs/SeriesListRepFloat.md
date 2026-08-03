

# SeriesListRepFloat


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**links** | **Map&lt;String, Object&gt;** | The location and content type of related resources |  |
|**metadata** | **List&lt;Map&lt;String, Object&gt;&gt;** | Metadata about each series |  |
|**series** | **List&lt;Map&lt;String, BigDecimal&gt;&gt;** | An array of data points with timestamps. Each element of the array is an object with a &#39;time&#39; field, whose value is the timestamp, and one or more key fields. If there are multiple key fields, they are labeled &#39;0&#39;, &#39;1&#39;, and so on, and are explained in the &lt;code&gt;metadata&lt;/code&gt;. |  |
|**projectedSeries** | **List&lt;Map&lt;String, BigDecimal&gt;&gt;** | Cumulative projected usage by day for the full billing period, when available. Each element matches the structure of &lt;code&gt;series&lt;/code&gt;: an object with a &#39;time&#39; field and a &#39;0&#39; value field. The projected (non-actual) portion begins where the actual &lt;code&gt;series&lt;/code&gt; ends. |  [optional] |



