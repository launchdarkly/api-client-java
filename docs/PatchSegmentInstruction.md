

# PatchSegmentInstruction


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**kind** | **String** |  | 
**userKey** | **String** | A unique key used to represent the user | 
**targetType** | **String** | A segment&#39;s target type. Must be either &lt;code&gt;included&lt;/code&gt; or &lt;code&gt;excluded&lt;/code&gt; | 
**value** | **Integer** | Schedule user target expiration on a segment by including a timestamp |  [optional]
**version** | **Integer** | Required if &lt;code&gt;kind&lt;/code&gt; is &lt;code&gt;updateExpireUserTargetDate&lt;/code&gt; |  [optional]



