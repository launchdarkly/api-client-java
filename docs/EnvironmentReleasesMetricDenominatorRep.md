

# EnvironmentReleasesMetricDenominatorRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**eventName** | **String** | The warehouse event column for the denominator |  [optional] |
|**isNumeric** | **Boolean** | Whether the denominator aggregates a numeric value |  [optional] |
|**unitAggregationType** | **String** | How individual unit values are aggregated for the denominator |  [optional] |
|**unitAggregationField** | **String** | The column to count distinct values of; required when unitAggregationType is count_distinct |  [optional] |
|**valueColumn** | **String** | For a numeric denominator, the column holding the numeric value |  [optional] |
|**dataSource** | [**MetricDataSourceRefRep**](MetricDataSourceRefRep.md) |  |  [optional] |
|**filters** | [**EnvironmentReleasesFilter**](EnvironmentReleasesFilter.md) |  |  [optional] |
|**windowStartOffset** | **Long** | Start of the measurement window in milliseconds |  [optional] |
|**windowEndOffset** | **Long** | End of the measurement window in milliseconds |  [optional] |
|**winsorLowerPercentile** | **BigDecimal** | Lower winsorization percentile in the open interval (0, 100) |  [optional] |
|**winsorUpperPercentile** | **BigDecimal** | Upper winsorization percentile in the open interval (0, 100) |  [optional] |
|**winsorIncludeImputed** | **Boolean** | When true, the percentile bound calculation includes imputed zeros |  [optional] |



