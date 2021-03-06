/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 5.1.0
 * Contact: support@launchdarkly.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.launchdarkly.api.api;

import com.launchdarkly.api.ApiException;
import com.launchdarkly.api.model.PatchOperation;
import com.launchdarkly.api.model.Project;
import com.launchdarkly.api.model.ProjectBody;
import com.launchdarkly.api.model.Projects;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for ProjectsApi
 */
@Ignore
public class ProjectsApiTest {

    private final ProjectsApi api = new ProjectsApi();

    
    /**
     * Delete a project by key. Caution-- deleting a project will delete all associated environments and feature flags. You cannot delete the last project in an account.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void deleteProjectTest() throws ApiException {
        String projectKey = null;
        api.deleteProject(projectKey);

        // TODO: test validations
    }
    
    /**
     * Fetch a single project by key.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getProjectTest() throws ApiException {
        String projectKey = null;
        Project response = api.getProject(projectKey);

        // TODO: test validations
    }
    
    /**
     * Returns a list of all projects in the account.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getProjectsTest() throws ApiException {
        Projects response = api.getProjects();

        // TODO: test validations
    }
    
    /**
     * Modify a project by ID.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void patchProjectTest() throws ApiException {
        String projectKey = null;
        List<PatchOperation> patchDelta = null;
        Project response = api.patchProject(projectKey, patchDelta);

        // TODO: test validations
    }
    
    /**
     * Create a new project with the given key and name.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void postProjectTest() throws ApiException {
        ProjectBody projectBody = null;
        Project response = api.postProject(projectBody);

        // TODO: test validations
    }
    
}
