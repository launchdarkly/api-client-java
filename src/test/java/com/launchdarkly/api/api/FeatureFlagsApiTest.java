/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 2.0.9
 * Contact: support@launchdarkly.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.launchdarkly.api.api;

import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.model.FeatureFlag;
import com.launchdarkly.api.model.FeatureFlagBody;
import com.launchdarkly.api.model.FeatureFlagStatus;
import com.launchdarkly.api.model.FeatureFlagStatuses;
import com.launchdarkly.api.model.FeatureFlags;
import com.launchdarkly.api.model.PatchComment;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for FeatureFlagsApi
 */
@Ignore
public class FeatureFlagsApiTest {

    private final FeatureFlagsApi api = new FeatureFlagsApi();

    
    /**
     * Delete a feature flag in all environments. Be careful-- only delete feature flags that are no longer being used by your application.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void deleteFeatureFlagTest() throws ApiException {
        String projectKey = null;
        String featureFlagKey = null;
        api.deleteFeatureFlag(projectKey, featureFlagKey);

        // TODO: test validations
    }
    
    /**
     * Get a single feature flag by key.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getFeatureFlagTest() throws ApiException {
        String projectKey = null;
        String featureFlagKey = null;
        String env = null;
        FeatureFlag response = api.getFeatureFlag(projectKey, featureFlagKey, env);

        // TODO: test validations
    }
    
    /**
     * Get the status for a particular feature flag.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getFeatureFlagStatusTest() throws ApiException {
        String projectKey = null;
        String environmentKey = null;
        String featureFlagKey = null;
        FeatureFlagStatus response = api.getFeatureFlagStatus(projectKey, environmentKey, featureFlagKey);

        // TODO: test validations
    }
    
    /**
     * Get a list of statuses for all feature flags. The status includes the last time the feature flag was requested, as well as the state of the flag.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getFeatureFlagStatusesTest() throws ApiException {
        String projectKey = null;
        String environmentKey = null;
        FeatureFlagStatuses response = api.getFeatureFlagStatuses(projectKey, environmentKey);

        // TODO: test validations
    }
    
    /**
     * Get a list of all features in the given project.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getFeatureFlagsTest() throws ApiException {
        String projectKey = null;
        String env = null;
        String tag = null;
        FeatureFlags response = api.getFeatureFlags(projectKey, env, tag);

        // TODO: test validations
    }
    
    /**
     * Perform a partial update to a feature.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void patchFeatureFlagTest() throws ApiException {
        String projectKey = null;
        String featureFlagKey = null;
        PatchComment patchComment = null;
        FeatureFlag response = api.patchFeatureFlag(projectKey, featureFlagKey, patchComment);

        // TODO: test validations
    }
    
    /**
     * Creates a new feature flag.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void postFeatureFlagTest() throws ApiException {
        String projectKey = null;
        FeatureFlagBody featureFlagBody = null;
        api.postFeatureFlag(projectKey, featureFlagBody);

        // TODO: test validations
    }
    
}
