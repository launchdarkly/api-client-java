

# MetricDenominatorRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**eventName** | **String** | The warehouse event column for the denominator |  [optional] |
|**isNumeric** | **Boolean** | Whether the denominator aggregates a numeric value |  [optional] |
|**unitAggregationType** | **String** | How individual unit values are aggregated for the denominator |  [optional] |
|**unitAggregationField** | **String** | The column to count distinct values of; required when unitAggregationType is count_distinct |  [optional] |
|**dataSource** | [**MetricDataSourceRefRep**](MetricDataSourceRefRep.md) |  |  [optional] |
|**filters** | [**Filter**](Filter.md) |  |  [optional] |
|**windowStartOffset** | **Long** | Start of the measurement window in milliseconds |  [optional] |
|**windowEndOffset** | **Long** | End of the measurement window in milliseconds |  [optional] |
|**winsorLowerPercentile** | **BigDecimal** | Lower winsorization percentile in the open interval (0, 100) |  [optional] |
|**winsorUpperPercentile** | **BigDecimal** | Upper winsorization percentile in the open interval (0, 100) |  [optional] |
|**winsorExcludeImputed** | **Boolean** | Deprecated and ignored. Use winsorIncludeImputed instead. |  [optional] |
|**winsorIncludeImputed** | **Boolean** | When true, the percentile bound calculation includes imputed zeros |  [optional] |



