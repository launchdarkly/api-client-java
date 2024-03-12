

# PullRequestRep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **UUID** | The pull request internal ID |  |
|**externalId** | **String** | The pull request number |  |
|**title** | **String** | The pull request title |  |
|**status** | **String** | The pull request status |  |
|**author** | **String** | The pull request author |  |
|**createTime** | **Long** |  |  |
|**mergeTime** | **Long** |  |  [optional] |
|**mergeCommitKey** | **String** | The pull request merge commit key |  [optional] |
|**baseCommitKey** | **String** | The pull request base commit key |  |
|**headCommitKey** | **String** | The pull request head commit key |  |
|**filesChanged** | **Integer** | The number of files changed |  |
|**linesAdded** | **Integer** | The number of lines added |  |
|**linesDeleted** | **Integer** | The number of lines deleted |  |
|**url** | **String** | The pull request URL |  |
|**deployments** | [**DeploymentCollectionRep**](DeploymentCollectionRep.md) |  |  [optional] |
|**flagReferences** | [**FlagReferenceCollectionRep**](FlagReferenceCollectionRep.md) |  |  [optional] |
|**leadTime** | [**PullRequestLeadTimeRep**](PullRequestLeadTimeRep.md) |  |  [optional] |



