

# RepositoryRep


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** | The repository name | 
**sourceLink** | **String** | A URL to access the repository |  [optional]
**commitUrlTemplate** | **String** | A template for constructing a valid URL to view the commit |  [optional]
**hunkUrlTemplate** | **String** | A template for constructing a valid URL to view the hunk |  [optional]
**type** | [**TypeEnum**](#TypeEnum) | The type of repository | 
**defaultBranch** | **String** | The repository&#39;s default branch | 
**enabled** | **Boolean** | Whether or not a repository is enabled for code reference scanning | 
**version** | **Integer** | The version of the repository&#39;s saved information | 
**branches** | [**List&lt;BranchRep&gt;**](BranchRep.md) | An array of the repository&#39;s branches that have been scanned for code references |  [optional]
**links** | **Map&lt;String, Object&gt;** |  | 
**access** | [**AccessRep**](AccessRep.md) |  |  [optional]



## Enum: TypeEnum

Name | Value
---- | -----
GITHUB | &quot;github&quot;
BITBUCKET | &quot;bitbucket&quot;
CUSTOM | &quot;custom&quot;



