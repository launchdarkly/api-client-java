

# DeploymentRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **UUID** | The deployment ID |  |
|**applicationKey** | **String** | The application key |  |
|**applicationVersion** | **String** | The application version |  |
|**startedAt** | **Long** |  |  |
|**endedAt** | **Long** |  |  [optional] |
|**durationMs** | **Long** | The duration of the deployment in milliseconds |  [optional] |
|**status** | **String** |  |  |
|**kind** | **String** |  |  |
|**active** | **Boolean** | Whether the deployment is active |  |
|**metadata** | **Map&lt;String, Object&gt;** | The metadata associated with the deployment |  [optional] |
|**archived** | **Boolean** | Whether the deployment is archived |  |
|**environmentKey** | **String** | The environment key |  |
|**numberOfContributors** | **Integer** | The number of contributors |  |
|**numberOfPullRequests** | **Integer** | The number of pull requests |  |
|**linesAdded** | **Long** | The number of lines added |  |
|**linesDeleted** | **Long** | The number of lines deleted |  |
|**leadTime** | **Long** | The total lead time from first commit to deployment end in milliseconds |  |
|**pullRequests** | [**PullRequestCollectionRep**](PullRequestCollectionRep.md) |  |  [optional] |
|**flagReferences** | [**FlagReferenceCollectionRep**](FlagReferenceCollectionRep.md) |  |  [optional] |
|**leadTimeStages** | [**LeadTimeStagesRep**](LeadTimeStagesRep.md) |  |  [optional] |



