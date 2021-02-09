/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 5.0.0
 * Contact: support@launchdarkly.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.launchdarkly.api.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.launchdarkly.api.model.ClientSideAvailability;
import com.launchdarkly.api.model.EnvironmentPost;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ProjectBody
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-02-09T21:10:42.024Z")
public class ProjectBody {
  @SerializedName("name")
  private String name = null;

  @SerializedName("key")
  private String key = null;

  @SerializedName("includeInSnippetByDefault")
  private Boolean includeInSnippetByDefault = null;

  @SerializedName("tags")
  private List<String> tags = null;

  @SerializedName("environments")
  private List<EnvironmentPost> environments = null;

  @SerializedName("defaultClientSideAvailability")
  private ClientSideAvailability defaultClientSideAvailability = null;

  public ProjectBody name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(example = "New Project", required = true, value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ProjectBody key(String key) {
    this.key = key;
    return this;
  }

   /**
   * Get key
   * @return key
  **/
  @ApiModelProperty(example = "new-project", required = true, value = "")
  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public ProjectBody includeInSnippetByDefault(Boolean includeInSnippetByDefault) {
    this.includeInSnippetByDefault = includeInSnippetByDefault;
    return this;
  }

   /**
   * Get includeInSnippetByDefault
   * @return includeInSnippetByDefault
  **/
  @ApiModelProperty(example = "false", value = "")
  public Boolean isIncludeInSnippetByDefault() {
    return includeInSnippetByDefault;
  }

  public void setIncludeInSnippetByDefault(Boolean includeInSnippetByDefault) {
    this.includeInSnippetByDefault = includeInSnippetByDefault;
  }

  public ProjectBody tags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public ProjectBody addTagsItem(String tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<String>();
    }
    this.tags.add(tagsItem);
    return this;
  }

   /**
   * Get tags
   * @return tags
  **/
  @ApiModelProperty(example = "[\"ops\",\"dev\"]", value = "")
  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public ProjectBody environments(List<EnvironmentPost> environments) {
    this.environments = environments;
    return this;
  }

  public ProjectBody addEnvironmentsItem(EnvironmentPost environmentsItem) {
    if (this.environments == null) {
      this.environments = new ArrayList<EnvironmentPost>();
    }
    this.environments.add(environmentsItem);
    return this;
  }

   /**
   * Get environments
   * @return environments
  **/
  @ApiModelProperty(value = "")
  public List<EnvironmentPost> getEnvironments() {
    return environments;
  }

  public void setEnvironments(List<EnvironmentPost> environments) {
    this.environments = environments;
  }

  public ProjectBody defaultClientSideAvailability(ClientSideAvailability defaultClientSideAvailability) {
    this.defaultClientSideAvailability = defaultClientSideAvailability;
    return this;
  }

   /**
   * Get defaultClientSideAvailability
   * @return defaultClientSideAvailability
  **/
  @ApiModelProperty(value = "")
  public ClientSideAvailability getDefaultClientSideAvailability() {
    return defaultClientSideAvailability;
  }

  public void setDefaultClientSideAvailability(ClientSideAvailability defaultClientSideAvailability) {
    this.defaultClientSideAvailability = defaultClientSideAvailability;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProjectBody projectBody = (ProjectBody) o;
    return Objects.equals(this.name, projectBody.name) &&
        Objects.equals(this.key, projectBody.key) &&
        Objects.equals(this.includeInSnippetByDefault, projectBody.includeInSnippetByDefault) &&
        Objects.equals(this.tags, projectBody.tags) &&
        Objects.equals(this.environments, projectBody.environments) &&
        Objects.equals(this.defaultClientSideAvailability, projectBody.defaultClientSideAvailability);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, key, includeInSnippetByDefault, tags, environments, defaultClientSideAvailability);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProjectBody {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    includeInSnippetByDefault: ").append(toIndentedString(includeInSnippetByDefault)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    environments: ").append(toIndentedString(environments)).append("\n");
    sb.append("    defaultClientSideAvailability: ").append(toIndentedString(defaultClientSideAvailability)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

