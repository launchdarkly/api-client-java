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


package com.launchdarkly.api.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.launchdarkly.api.model.Links;
import com.launchdarkly.api.model.Policy;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CustomRole
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-10-12T02:47:33.078Z")
public class CustomRole {
  @SerializedName("_links")
  private Links links = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("key")
  private String key = null;

  @SerializedName("description")
  private String description = null;

  @SerializedName("_id")
  private String id = null;

  @SerializedName("policy")
  private List<Policy> policy = null;

  public CustomRole links(Links links) {
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

  public CustomRole name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Name of the custom role.
   * @return name
  **/
  @ApiModelProperty(example = "revenue team", value = "Name of the custom role.")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CustomRole key(String key) {
    this.key = key;
    return this;
  }

   /**
   * Get key
   * @return key
  **/
  @ApiModelProperty(value = "")
  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public CustomRole description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Description of the custom role.
   * @return description
  **/
  @ApiModelProperty(example = "Description of revenue team role here", value = "Description of the custom role.")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public CustomRole id(String id) {
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

  public CustomRole policy(List<Policy> policy) {
    this.policy = policy;
    return this;
  }

  public CustomRole addPolicyItem(Policy policyItem) {
    if (this.policy == null) {
      this.policy = new ArrayList<Policy>();
    }
    this.policy.add(policyItem);
    return this;
  }

   /**
   * Get policy
   * @return policy
  **/
  @ApiModelProperty(value = "")
  public List<Policy> getPolicy() {
    return policy;
  }

  public void setPolicy(List<Policy> policy) {
    this.policy = policy;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomRole customRole = (CustomRole) o;
    return Objects.equals(this.links, customRole.links) &&
        Objects.equals(this.name, customRole.name) &&
        Objects.equals(this.key, customRole.key) &&
        Objects.equals(this.description, customRole.description) &&
        Objects.equals(this.id, customRole.id) &&
        Objects.equals(this.policy, customRole.policy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(links, name, key, description, id, policy);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CustomRole {\n");
    
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    policy: ").append(toIndentedString(policy)).append("\n");
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

