

# StatisticRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | The repository name |  |
|**type** | [**TypeEnum**](#TypeEnum) | The type of repository |  |
|**sourceLink** | **String** | A URL to access the repository |  |
|**defaultBranch** | **String** | The repository&#39;s default branch |  |
|**enabled** | **Boolean** | Whether or not a repository is enabled for code reference scanning |  |
|**version** | **Integer** | The version of the repository&#39;s saved information |  |
|**hunkCount** | **Integer** | The number of code reference hunks in which the flag appears in this repository |  |
|**fileCount** | **Integer** | The number of files in which the flag appears in this repository |  |
|**links** | [**Map&lt;String, Link&gt;**](Link.md) | The location and content type of related resources |  |
|**latestCommitTime** | **Long** |  |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| BITBUCKET | &quot;bitbucket&quot; |
| CUSTOM | &quot;custom&quot; |
| GITHUB | &quot;github&quot; |
| GITLAB | &quot;gitlab&quot; |



