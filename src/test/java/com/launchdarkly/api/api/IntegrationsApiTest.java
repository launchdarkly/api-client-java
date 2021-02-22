/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 5.0.2
 * Contact: support@launchdarkly.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.launchdarkly.api.api;

import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.model.Integration;
import com.launchdarkly.api.model.IntegrationSubscription;
import com.launchdarkly.api.model.Integrations;
import com.launchdarkly.api.model.PatchOperation;
import com.launchdarkly.api.model.SubscriptionBody;
import com.launchdarkly.api.model.UsageError;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for IntegrationsApi
 */
@Ignore
public class IntegrationsApiTest {

    private final IntegrationsApi api = new IntegrationsApi();

    
    /**
     * Delete an integration subscription by ID.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void deleteIntegrationSubscriptionTest() throws ApiException {
        String integrationKey = null;
        String integrationId = null;
        api.deleteIntegrationSubscription(integrationKey, integrationId);

        // TODO: test validations
    }
    
    /**
     * Get a single integration subscription by ID.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getIntegrationSubscriptionTest() throws ApiException {
        String integrationKey = null;
        String integrationId = null;
        IntegrationSubscription response = api.getIntegrationSubscription(integrationKey, integrationId);

        // TODO: test validations
    }
    
    /**
     * Get a list of all configured integrations of a given kind.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getIntegrationSubscriptionsTest() throws ApiException {
        String integrationKey = null;
        Integration response = api.getIntegrationSubscriptions(integrationKey);

        // TODO: test validations
    }
    
    /**
     * Get a list of all configured audit log event integrations associated with this account.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getIntegrationsTest() throws ApiException {
        Integrations response = api.getIntegrations();

        // TODO: test validations
    }
    
    /**
     * Modify an integration subscription by ID.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void patchIntegrationSubscriptionTest() throws ApiException {
        String integrationKey = null;
        String integrationId = null;
        List<PatchOperation> patchDelta = null;
        IntegrationSubscription response = api.patchIntegrationSubscription(integrationKey, integrationId, patchDelta);

        // TODO: test validations
    }
    
    /**
     * Create a new integration subscription of a given kind.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void postIntegrationSubscriptionTest() throws ApiException {
        String integrationKey = null;
        SubscriptionBody subscriptionBody = null;
        IntegrationSubscription response = api.postIntegrationSubscription(integrationKey, subscriptionBody);

        // TODO: test validations
    }
    
}
