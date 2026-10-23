

# ReleaseStep

One step in a phase's rollout schedule.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**stepIndex** | **Integer** | Zero-based index of this step. |  [readonly] |
|**allocation** | **Integer** | Thousandths of a percent. &#x60;100000&#x60; is 100%, and &#x60;10000&#x60; is 10%. Guarded releases cannot exceed &#x60;50000&#x60;, preserving a control group.  |  |
|**durationMillis** | **Long** |  |  |
|**startedAt** | **Long** | Start of the step in Unix milliseconds. |  [optional] [readonly] |
|**endedAt** | **Long** | End of the step in Unix milliseconds. |  [optional] [readonly] |



