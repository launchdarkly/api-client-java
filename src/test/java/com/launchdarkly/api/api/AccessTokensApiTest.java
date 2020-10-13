/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 3.7.1
 * Contact: support@launchdarkly.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.launchdarkly.api.api;

import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.model.PatchOperation;
import com.launchdarkly.api.model.Token;
import com.launchdarkly.api.model.TokenBody;
import com.launchdarkly.api.model.Tokens;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for AccessTokensApi
 */
@Ignore
public class AccessTokensApiTest {

    private final AccessTokensApi api = new AccessTokensApi();

    
    /**
     * Delete an access token by ID.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void deleteTokenTest() throws ApiException {
        String tokenId = null;
        api.deleteToken(tokenId);

        // TODO: test validations
    }
    
    /**
     * Get a single access token by ID.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getTokenTest() throws ApiException {
        String tokenId = null;
        Token response = api.getToken(tokenId);

        // TODO: test validations
    }
    
    /**
     * Returns a list of tokens in the account.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getTokensTest() throws ApiException {
        Boolean showAll = null;
        Tokens response = api.getTokens(showAll);

        // TODO: test validations
    }
    
    /**
     * Modify an access token by ID.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void patchTokenTest() throws ApiException {
        String tokenId = null;
        List<PatchOperation> patchDelta = null;
        Token response = api.patchToken(tokenId, patchDelta);

        // TODO: test validations
    }
    
    /**
     * Create a new token.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void postTokenTest() throws ApiException {
        TokenBody tokenBody = null;
        Token response = api.postToken(tokenBody);

        // TODO: test validations
    }
    
    /**
     * Reset an access token&#39;s secret key with an optional expiry time for the old key.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void resetTokenTest() throws ApiException {
        String tokenId = null;
        Long expiry = null;
        Token response = api.resetToken(tokenId, expiry);

        // TODO: test validations
    }
    
}
