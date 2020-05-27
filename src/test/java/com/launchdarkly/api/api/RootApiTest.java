/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 3.3.0
 * Contact: support@launchdarkly.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.launchdarkly.api.api;

import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.model.Links;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for RootApi
 */
@Ignore
public class RootApiTest {

    private final RootApi api = new RootApi();

    
    /**
     * 
     *
     * You can issue a GET request to the root resource to find all of the resource categories supported by the API.
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getRootTest() throws ApiException {
        Links response = api.getRoot();

        // TODO: test validations
    }
    
}
