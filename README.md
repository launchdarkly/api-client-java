# api-client-java

## Requirements

Building the API client library requires [Maven](https://maven.apache.org/) to be installed.

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn deploy
```

Refer to the [official documentation](https://maven.apache.org/plugins/maven-deploy-plugin/usage.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>com.launchdarkly</groupId>
    <artifactId>api-client-java</artifactId>
    <version>1.0.0</version>
    <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "com.launchdarkly:api-client-java:1.0.0"
```

### Others

At first generate the JAR by executing:

    mvn package

Then manually install the following JARs:

* target/api-client-java-1.0.0.jar
* target/lib/*.jar

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

import io.swagger.client.*;
import io.swagger.client.auth.*;
import io.swagger.client.model.*;
import io.swagger.client.api.AuditLogApi;

import java.io.File;
import java.util.*;

public class AuditLogApiExample {

    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        
        // Configure API key authorization: Token
        ApiKeyAuth Token = (ApiKeyAuth) defaultClient.getAuthentication("Token");
        Token.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //Token.setApiKeyPrefix("Token");

        AuditLogApi apiInstance = new AuditLogApi();
        try {
            AuditLogEntries result = apiInstance.getAuditLogEntries();
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling AuditLogApi#getAuditLogEntries");
            e.printStackTrace();
        }
    }
}

```

## Documentation for API Endpoints

All URIs are relative to *https://app.launchdarkly.com/api/v2*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*AuditLogApi* | [**getAuditLogEntries**](docs/AuditLogApi.md#getAuditLogEntries) | **GET** /auditlog | Fetch a list of all webhooks
*AuditLogApi* | [**getAuditLogEntry**](docs/AuditLogApi.md#getAuditLogEntry) | **GET** /auditlog/{resourceId} | Get a webhook by ID
*EnvironmentsApi* | [**deleteEnvironment**](docs/EnvironmentsApi.md#deleteEnvironment) | **DELETE** /environments/{projectKey}/{environmentKey} | Delete an environment by ID
*EnvironmentsApi* | [**getEnvironment**](docs/EnvironmentsApi.md#getEnvironment) | **GET** /environments/{projectKey}/{environmentKey} | Get an environment by key.
*EnvironmentsApi* | [**patchEnvironment**](docs/EnvironmentsApi.md#patchEnvironment) | **PATCH** /environments/{projectKey}/{environmentKey} | Modify an environment by ID
*EnvironmentsApi* | [**postEnvironment**](docs/EnvironmentsApi.md#postEnvironment) | **POST** /environments/{projectKey} | Create an environment
*FlagsApi* | [**deleteFeatureFlag**](docs/FlagsApi.md#deleteFeatureFlag) | **DELETE** /flags/{projectKey}/{featureFlagKey} | Delete a feature flag by ID
*FlagsApi* | [**getFeatureFlag**](docs/FlagsApi.md#getFeatureFlag) | **GET** /flags/{projectKey}/{featureFlagKey} | Get a single feature flag by key.
*FlagsApi* | [**getFeatureFlagStatus**](docs/FlagsApi.md#getFeatureFlagStatus) | **GET** /flag-statuses/{projectKey}/{environmentKey} | Get a list of statuses for all feature flags
*FlagsApi* | [**getFeatureFlagStatuses**](docs/FlagsApi.md#getFeatureFlagStatuses) | **GET** /flag-statuses/{projectKey}/{environmentKey}/{featureFlagKey} | Get a list of statuses for all feature flags
*FlagsApi* | [**getFeatureFlags**](docs/FlagsApi.md#getFeatureFlags) | **GET** /flags/{projectKey} | Get a list of all features in the given project.
*FlagsApi* | [**patchFeatureFlag**](docs/FlagsApi.md#patchFeatureFlag) | **PATCH** /flags/{projectKey}/{featureFlagKey} | Modify a feature flag by ID
*FlagsApi* | [**postFeatureFlag**](docs/FlagsApi.md#postFeatureFlag) | **POST** /flags/{projectKey} | Create a feature flag
*ProjectsApi* | [**deleteProject**](docs/ProjectsApi.md#deleteProject) | **DELETE** /projects/{projectKey} | Delete a project by ID
*ProjectsApi* | [**getProject**](docs/ProjectsApi.md#getProject) | **GET** /projects/{projectKey} | Get a project by key.
*ProjectsApi* | [**getProjects**](docs/ProjectsApi.md#getProjects) | **GET** /projects | Returns a list of all projects in the account.
*ProjectsApi* | [**patchProject**](docs/ProjectsApi.md#patchProject) | **PATCH** /projects/{projectKey} | Modify a project by ID
*ProjectsApi* | [**postProject**](docs/ProjectsApi.md#postProject) | **POST** /projects | Create a project
*RootApi* | [**getRoot**](docs/RootApi.md#getRoot) | **GET** / | Get the root resource
*UserSettingsApi* | [**getUserFlagSetting**](docs/UserSettingsApi.md#getUserFlagSetting) | **GET** /users/{projectKey}/{environmentKey}/{userKey}/flags/{featureFlagKey} | Get a user by key.
*UserSettingsApi* | [**getUserFlagSettings**](docs/UserSettingsApi.md#getUserFlagSettings) | **GET** /users/{projectKey}/{environmentKey}/{userKey}/flags | Lists the current flag settings for a given user.
*UserSettingsApi* | [**putFlagSetting**](docs/UserSettingsApi.md#putFlagSetting) | **PUT** /users/{projectKey}/{environmentKey}/{userKey}/flags/{featureFlagKey} | Specifically enable or disable a feature flag for a user based on their key.
*UsersApi* | [**deleteUser**](docs/UsersApi.md#deleteUser) | **DELETE** /users/{projectKey}/{environmentKey}/{userKey} | Delete a user by ID
*UsersApi* | [**getSearchUsers**](docs/UsersApi.md#getSearchUsers) | **GET** /user-search/{projectKey}/{environmentKey} | Search users in LaunchDarkly based on their last active date, or a search query.
*UsersApi* | [**getUser**](docs/UsersApi.md#getUser) | **GET** /users/{projectKey}/{environmentKey}/{userKey} | Get a user by key.
*UsersApi* | [**getUsers**](docs/UsersApi.md#getUsers) | **GET** /users/{projectKey}/{environmentKey} | List all users in the environment.
*WebhooksApi* | [**deleteWebhook**](docs/WebhooksApi.md#deleteWebhook) | **DELETE** /webhooks/{resourceId} | Delete a webhook by ID
*WebhooksApi* | [**getWebhook**](docs/WebhooksApi.md#getWebhook) | **GET** /webhooks/{resourceId} | Get a webhook by ID
*WebhooksApi* | [**getWebhooks**](docs/WebhooksApi.md#getWebhooks) | **GET** /webhooks | Fetch a list of all webhooks
*WebhooksApi* | [**patchWebhook**](docs/WebhooksApi.md#patchWebhook) | **PATCH** /webhooks/{resourceId} | Modify a webhook by ID
*WebhooksApi* | [**postWebhook**](docs/WebhooksApi.md#postWebhook) | **POST** /webhooks | Create a webhook


## Documentation for Models

 - [AuditLogEntries](docs/AuditLogEntries.md)
 - [AuditLogEntry](docs/AuditLogEntry.md)
 - [AuditLogEntryTarget](docs/AuditLogEntryTarget.md)
 - [Clause](docs/Clause.md)
 - [Environment](docs/Environment.md)
 - [EnvironmentBody](docs/EnvironmentBody.md)
 - [FeatureFlag](docs/FeatureFlag.md)
 - [FeatureFlagBody](docs/FeatureFlagBody.md)
 - [FeatureFlagConfig](docs/FeatureFlagConfig.md)
 - [FeatureFlagConfigFallthrough](docs/FeatureFlagConfigFallthrough.md)
 - [FeatureFlagStatus](docs/FeatureFlagStatus.md)
 - [FeatureFlagStatuses](docs/FeatureFlagStatuses.md)
 - [FeatureFlags](docs/FeatureFlags.md)
 - [Link](docs/Link.md)
 - [Links](docs/Links.md)
 - [Member](docs/Member.md)
 - [PatchDelta](docs/PatchDelta.md)
 - [Project](docs/Project.md)
 - [ProjectBody](docs/ProjectBody.md)
 - [Projects](docs/Projects.md)
 - [Rollout](docs/Rollout.md)
 - [Rule](docs/Rule.md)
 - [Target](docs/Target.md)
 - [User](docs/User.md)
 - [UserFlagSetting](docs/UserFlagSetting.md)
 - [UserFlagSettings](docs/UserFlagSettings.md)
 - [UserSettingsBody](docs/UserSettingsBody.md)
 - [Users](docs/Users.md)
 - [Variation](docs/Variation.md)
 - [Webhook](docs/Webhook.md)
 - [WebhookBody](docs/WebhookBody.md)
 - [Webhooks](docs/Webhooks.md)
 - [WeightedVariation](docs/WeightedVariation.md)


## Documentation for Authorization

Authentication schemes defined for the API:
### Token

- **Type**: API key
- **API key parameter name**: Authorization
- **Location**: HTTP header


## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author

support@launchdarkly.com

