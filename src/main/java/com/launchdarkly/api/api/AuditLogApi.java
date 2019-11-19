/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 2.0.22
 * Contact: support@launchdarkly.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.launchdarkly.api.api;

import com.launchdarkly.api.ApiCallback;
import com.launchdarkly.api.ApiClient;
import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.ApiResponse;
import com.launchdarkly.api.Configuration;
import com.launchdarkly.api.Pair;
import com.launchdarkly.api.ProgressRequestBody;
import com.launchdarkly.api.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import com.launchdarkly.api.model.AuditLogEntries;
import com.launchdarkly.api.model.AuditLogEntry;
import java.math.BigDecimal;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuditLogApi {
    private ApiClient apiClient;

    public AuditLogApi() {
        this(Configuration.getDefaultApiClient());
    }

    public AuditLogApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for getAuditLogEntries
     * @param before A timestamp filter, expressed as a Unix epoch time in milliseconds. All entries returned will have before this timestamp. (optional)
     * @param after A timestamp filter, expressed as a Unix epoch time in milliseconds. All entries returned will have occured after this timestamp. (optional)
     * @param q Text to search for. You can search for the full or partial name of the resource involved or fullpartial email address of the member who made the change. (optional)
     * @param limit A limit on the number of audit log entries to be returned, between 1 and 20. (optional)
     * @param spec A resource specifier, allowing you to filter audit log listings by resource. (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getAuditLogEntriesCall(Long before, Long after, String q, BigDecimal limit, String spec, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/auditlog";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (before != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("before", before));
        if (after != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("after", after));
        if (q != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("q", q));
        if (limit != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("limit", limit));
        if (spec != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("spec", spec));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] { "Token" };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getAuditLogEntriesValidateBeforeCall(Long before, Long after, String q, BigDecimal limit, String spec, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        

        com.squareup.okhttp.Call call = getAuditLogEntriesCall(before, after, q, limit, spec, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Get a list of all audit log entries. The query parameters allow you to restrict the returned results by date ranges, resource specifiers, or a full-text search query.
     * 
     * @param before A timestamp filter, expressed as a Unix epoch time in milliseconds. All entries returned will have before this timestamp. (optional)
     * @param after A timestamp filter, expressed as a Unix epoch time in milliseconds. All entries returned will have occured after this timestamp. (optional)
     * @param q Text to search for. You can search for the full or partial name of the resource involved or fullpartial email address of the member who made the change. (optional)
     * @param limit A limit on the number of audit log entries to be returned, between 1 and 20. (optional)
     * @param spec A resource specifier, allowing you to filter audit log listings by resource. (optional)
     * @return AuditLogEntries
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public AuditLogEntries getAuditLogEntries(Long before, Long after, String q, BigDecimal limit, String spec) throws ApiException {
        ApiResponse<AuditLogEntries> resp = getAuditLogEntriesWithHttpInfo(before, after, q, limit, spec);
        return resp.getData();
    }

    /**
     * Get a list of all audit log entries. The query parameters allow you to restrict the returned results by date ranges, resource specifiers, or a full-text search query.
     * 
     * @param before A timestamp filter, expressed as a Unix epoch time in milliseconds. All entries returned will have before this timestamp. (optional)
     * @param after A timestamp filter, expressed as a Unix epoch time in milliseconds. All entries returned will have occured after this timestamp. (optional)
     * @param q Text to search for. You can search for the full or partial name of the resource involved or fullpartial email address of the member who made the change. (optional)
     * @param limit A limit on the number of audit log entries to be returned, between 1 and 20. (optional)
     * @param spec A resource specifier, allowing you to filter audit log listings by resource. (optional)
     * @return ApiResponse&lt;AuditLogEntries&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<AuditLogEntries> getAuditLogEntriesWithHttpInfo(Long before, Long after, String q, BigDecimal limit, String spec) throws ApiException {
        com.squareup.okhttp.Call call = getAuditLogEntriesValidateBeforeCall(before, after, q, limit, spec, null, null);
        Type localVarReturnType = new TypeToken<AuditLogEntries>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get a list of all audit log entries. The query parameters allow you to restrict the returned results by date ranges, resource specifiers, or a full-text search query. (asynchronously)
     * 
     * @param before A timestamp filter, expressed as a Unix epoch time in milliseconds. All entries returned will have before this timestamp. (optional)
     * @param after A timestamp filter, expressed as a Unix epoch time in milliseconds. All entries returned will have occured after this timestamp. (optional)
     * @param q Text to search for. You can search for the full or partial name of the resource involved or fullpartial email address of the member who made the change. (optional)
     * @param limit A limit on the number of audit log entries to be returned, between 1 and 20. (optional)
     * @param spec A resource specifier, allowing you to filter audit log listings by resource. (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getAuditLogEntriesAsync(Long before, Long after, String q, BigDecimal limit, String spec, final ApiCallback<AuditLogEntries> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = getAuditLogEntriesValidateBeforeCall(before, after, q, limit, spec, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<AuditLogEntries>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for getAuditLogEntry
     * @param resourceId The resource ID. (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getAuditLogEntryCall(String resourceId, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/auditlog/{resourceId}"
            .replaceAll("\\{" + "resourceId" + "\\}", apiClient.escapeString(resourceId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] { "Token" };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getAuditLogEntryValidateBeforeCall(String resourceId, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'resourceId' is set
        if (resourceId == null) {
            throw new ApiException("Missing the required parameter 'resourceId' when calling getAuditLogEntry(Async)");
        }
        

        com.squareup.okhttp.Call call = getAuditLogEntryCall(resourceId, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Use this endpoint to fetch a single audit log entry by its resouce ID.
     * 
     * @param resourceId The resource ID. (required)
     * @return AuditLogEntry
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public AuditLogEntry getAuditLogEntry(String resourceId) throws ApiException {
        ApiResponse<AuditLogEntry> resp = getAuditLogEntryWithHttpInfo(resourceId);
        return resp.getData();
    }

    /**
     * Use this endpoint to fetch a single audit log entry by its resouce ID.
     * 
     * @param resourceId The resource ID. (required)
     * @return ApiResponse&lt;AuditLogEntry&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<AuditLogEntry> getAuditLogEntryWithHttpInfo(String resourceId) throws ApiException {
        com.squareup.okhttp.Call call = getAuditLogEntryValidateBeforeCall(resourceId, null, null);
        Type localVarReturnType = new TypeToken<AuditLogEntry>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Use this endpoint to fetch a single audit log entry by its resouce ID. (asynchronously)
     * 
     * @param resourceId The resource ID. (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getAuditLogEntryAsync(String resourceId, final ApiCallback<AuditLogEntry> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = getAuditLogEntryValidateBeforeCall(resourceId, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<AuditLogEntry>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
