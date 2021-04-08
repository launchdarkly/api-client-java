/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 5.0.3
 * Contact: support@launchdarkly.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.launchdarkly.api.api;

import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.model.PatchOperation;
import com.launchdarkly.api.model.RelayProxyConfig;
import com.launchdarkly.api.model.RelayProxyConfigBody;
import com.launchdarkly.api.model.RelayProxyConfigs;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for RelayProxyConfigurationsApi
 */
@Ignore
public class RelayProxyConfigurationsApiTest {

    private final RelayProxyConfigurationsApi api = new RelayProxyConfigurationsApi();

    
    /**
     * Delete a relay proxy configuration by ID.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void deleteRelayProxyConfigTest() throws ApiException {
        String id = null;
        api.deleteRelayProxyConfig(id);

        // TODO: test validations
    }
    
    /**
     * Get a single relay proxy configuration by ID.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getRelayProxyConfigTest() throws ApiException {
        String id = null;
        RelayProxyConfig response = api.getRelayProxyConfig(id);

        // TODO: test validations
    }
    
    /**
     * Returns a list of relay proxy configurations in the account.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getRelayProxyConfigsTest() throws ApiException {
        RelayProxyConfigs response = api.getRelayProxyConfigs();

        // TODO: test validations
    }
    
    /**
     * Modify a relay proxy configuration by ID.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void patchRelayProxyConfigTest() throws ApiException {
        String id = null;
        List<PatchOperation> patchDelta = null;
        RelayProxyConfig response = api.patchRelayProxyConfig(id, patchDelta);

        // TODO: test validations
    }
    
    /**
     * Create a new relay proxy config.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void postRelayAutoConfigTest() throws ApiException {
        RelayProxyConfigBody relayProxyConfigBody = null;
        RelayProxyConfig response = api.postRelayAutoConfig(relayProxyConfigBody);

        // TODO: test validations
    }
    
    /**
     * Reset a relay proxy configuration&#39;s secret key with an optional expiry time for the old key.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void resetRelayProxyConfigTest() throws ApiException {
        String id = null;
        Long expiry = null;
        RelayProxyConfig response = api.resetRelayProxyConfig(id, expiry);

        // TODO: test validations
    }
    
}
