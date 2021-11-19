

# RepositoryPost


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** |  | 
**sourceLink** | **String** |  |  [optional]
**commitUrlTemplate** | **String** | A template for constructing a valid URL to view the commit |  [optional]
**hunkUrlTemplate** | **String** | A template for constructing a valid URL to view the hunk |  [optional]
**type** | [**TypeEnum**](#TypeEnum) | Optionally specify a repository type. The default value is &lt;code&gt;custom&lt;/code&gt; |  [optional]
**defaultBranch** | **String** | The default branch, if not specified, is &lt;code&gt;master&lt;/code&gt; |  [optional]



## Enum: TypeEnum

Name | Value
---- | -----
GITHUB | &quot;github&quot;
BITBUCKET | &quot;bitbucket&quot;
CUSTOM | &quot;custom&quot;



