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


package com.launchdarkly.api.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.launchdarkly.api.model.ClientSideAvailability;
import com.launchdarkly.api.model.Environment;
import com.launchdarkly.api.model.Links;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Project
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-04-08T01:21:45.269Z")



public class Project {
  @SerializedName("_links")
  private Links links = null;

  @SerializedName("_id")
  private String id = null;

  @SerializedName("key")
  private String key = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("includeInSnippetByDefault")
  private Boolean includeInSnippetByDefault = null;

  @SerializedName("environments")
  private List<Environment> environments = null;

  @SerializedName("tags")
  private List<String> tags = null;

  @SerializedName("defaultClientSideAvailability")
  private ClientSideAvailability defaultClientSideAvailability = null;

  public Project links(Links links) {
    this.links = links;
    return this;
  }

   /**
   * Get links
   * @return links
  **/
  @ApiModelProperty(value = "")
  public Links getLinks() {
    return links;
  }

  public void setLinks(Links links) {
    this.links = links;
  }

  public Project id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Project key(String key) {
    this.key = key;
    return this;
  }

   /**
   * Get key
   * @return key
  **/
  @ApiModelProperty(example = "zentasks", value = "")
  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public Project name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(example = "Zentasks", value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Project includeInSnippetByDefault(Boolean includeInSnippetByDefault) {
    this.includeInSnippetByDefault = includeInSnippetByDefault;
    return this;
  }

   /**
   * Get includeInSnippetByDefault
   * @return includeInSnippetByDefault
  **/
  @ApiModelProperty(example = "true", value = "")
  public Boolean isIncludeInSnippetByDefault() {
    return includeInSnippetByDefault;
  }

  public void setIncludeInSnippetByDefault(Boolean includeInSnippetByDefault) {
    this.includeInSnippetByDefault = includeInSnippetByDefault;
  }

  public Project environments(List<Environment> environments) {
    this.environments = environments;
    return this;
  }

  public Project addEnvironmentsItem(Environment environmentsItem) {
    if (this.environments == null) {
      this.environments = new ArrayList<Environment>();
    }
    this.environments.add(environmentsItem);
    return this;
  }

   /**
   * Get environments
   * @return environments
  **/
  @ApiModelProperty(value = "")
  public List<Environment> getEnvironments() {
    return environments;
  }

  public void setEnvironments(List<Environment> environments) {
    this.environments = environments;
  }

  public Project tags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public Project addTagsItem(String tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<String>();
    }
    this.tags.add(tagsItem);
    return this;
  }

   /**
   * An array of tags for this project.
   * @return tags
  **/
  @ApiModelProperty(value = "An array of tags for this project.")
  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public Project defaultClientSideAvailability(ClientSideAvailability defaultClientSideAvailability) {
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
    Project project = (Project) o;
    return Objects.equals(this.links, project.links) &&
        Objects.equals(this.id, project.id) &&
        Objects.equals(this.key, project.key) &&
        Objects.equals(this.name, project.name) &&
        Objects.equals(this.includeInSnippetByDefault, project.includeInSnippetByDefault) &&
        Objects.equals(this.environments, project.environments) &&
        Objects.equals(this.tags, project.tags) &&
        Objects.equals(this.defaultClientSideAvailability, project.defaultClientSideAvailability);
  }

  @Override
  public int hashCode() {
    return Objects.hash(links, id, key, name, includeInSnippetByDefault, environments, tags, defaultClientSideAvailability);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Project {\n");
    
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    includeInSnippetByDefault: ").append(toIndentedString(includeInSnippetByDefault)).append("\n");
    sb.append("    environments: ").append(toIndentedString(environments)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
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

