

# RandomizationUnitInput


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**randomizationUnit** | **String** | The unit of randomization. Must match the key of an existing context kind in this project. |  |
|**_default** | **Boolean** | If true, any experiment iterations created within this project will default to using this randomization unit. A project can only have one default randomization unit. |  |
|**standardRandomizationUnit** | [**StandardRandomizationUnitEnum**](#StandardRandomizationUnitEnum) | One of LaunchDarkly&#39;s fixed set of standard randomization units. |  |



## Enum: StandardRandomizationUnitEnum

| Name | Value |
|---- | -----|
| GUEST | &quot;guest&quot; |
| GUESTTIME | &quot;guestTime&quot; |
| ORGANIZATION | &quot;organization&quot; |
| REQUEST | &quot;request&quot; |
| USER | &quot;user&quot; |
| USERTIME | &quot;userTime&quot; |



