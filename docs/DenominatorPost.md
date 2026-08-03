

# DenominatorPost


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**eventName** | **String** | The warehouse event column for the denominator. Required. |  [optional] |
|**isNumeric** | **Boolean** | Whether the denominator aggregates a numeric value |  [optional] |
|**dataSource** | [**MetricDataSourceRefRep**](MetricDataSourceRefRep.md) |  |  [optional] |
|**unitAggregationType** | **String** | How individual unit values are aggregated. One of: average, sum, count_distinct |  [optional] |
|**unitAggregationField** | **String** | The warehouse column to use for counting distinct values. Required when the unitAggregationType is count_distinct. |  [optional] |
|**valueColumn** | **String** | For a numeric denominator, the column holding the numeric value. Overrides the value column mapped on the denominator data source. |  [optional] |
|**filters** | [**EventFilter**](EventFilter.md) |  |  [optional] |
|**windowStartOffset** | **Long** | Start of the measurement window in milliseconds |  [optional] |
|**windowEndOffset** | **Long** | End of the measurement window in milliseconds |  [optional] |
|**winsorLowerPercentile** | **BigDecimal** | Lower winsorization percentile in the open interval (0, 100) |  [optional] |
|**winsorUpperPercentile** | **BigDecimal** | Upper winsorization percentile in the open interval (0, 100) |  [optional] |
|**winsorIncludeImputed** | **Boolean** | When true, includes imputed zeros in the percentile bound calculation |  [optional] |



