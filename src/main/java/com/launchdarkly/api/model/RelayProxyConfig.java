/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 3.8.0
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
import com.launchdarkly.api.model.Member;
import com.launchdarkly.api.model.Policy;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * RelayProxyConfig
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-28T14:46:17.158Z")
public class RelayProxyConfig {
  @SerializedName("_id")
  private String id = null;

  @SerializedName("_creator")
  private Member creator = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("policy")
  private List<Policy> policy = new ArrayList<Policy>();

  @SerializedName("fullKey")
  private String fullKey = null;

  @SerializedName("displayKey")
  private String displayKey = null;

  @SerializedName("creationDate")
  private Long creationDate = null;

  @SerializedName("lastModified")
  private Long lastModified = null;

  public RelayProxyConfig id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(required = true, value = "")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public RelayProxyConfig creator(Member creator) {
    this.creator = creator;
    return this;
  }

   /**
   * Get creator
   * @return creator
  **/
  @ApiModelProperty(required = true, value = "")
  public Member getCreator() {
    return creator;
  }

  public void setCreator(Member creator) {
    this.creator = creator;
  }

  public RelayProxyConfig name(String name) {
    this.name = name;
    return this;
  }

   /**
   * A human-friendly name for the relay proxy configuration
   * @return name
  **/
  @ApiModelProperty(example = "My relay proxy config", required = true, value = "A human-friendly name for the relay proxy configuration")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RelayProxyConfig policy(List<Policy> policy) {
    this.policy = policy;
    return this;
  }

  public RelayProxyConfig addPolicyItem(Policy policyItem) {
    this.policy.add(policyItem);
    return this;
  }

   /**
   * Get policy
   * @return policy
  **/
  @ApiModelProperty(required = true, value = "")
  public List<Policy> getPolicy() {
    return policy;
  }

  public void setPolicy(List<Policy> policy) {
    this.policy = policy;
  }

  public RelayProxyConfig fullKey(String fullKey) {
    this.fullKey = fullKey;
    return this;
  }

   /**
   * Full secret key. Only included if creating or resetting the relay proxy configuration
   * @return fullKey
  **/
  @ApiModelProperty(example = "rel-8a3a773d-b75e-48eb-a850-492cda9266eo", value = "Full secret key. Only included if creating or resetting the relay proxy configuration")
  public String getFullKey() {
    return fullKey;
  }

  public void setFullKey(String fullKey) {
    this.fullKey = fullKey;
  }

  public RelayProxyConfig displayKey(String displayKey) {
    this.displayKey = displayKey;
    return this;
  }

   /**
   * The last 4 digits of the unique secret key for this relay proxy configuration
   * @return displayKey
  **/
  @ApiModelProperty(example = "66eo", required = true, value = "The last 4 digits of the unique secret key for this relay proxy configuration")
  public String getDisplayKey() {
    return displayKey;
  }

  public void setDisplayKey(String displayKey) {
    this.displayKey = displayKey;
  }

  public RelayProxyConfig creationDate(Long creationDate) {
    this.creationDate = creationDate;
    return this;
  }

   /**
   * A unix epoch time in milliseconds specifying the creation time of this relay proxy configuration
   * @return creationDate
  **/
  @ApiModelProperty(example = "1443652232590", required = true, value = "A unix epoch time in milliseconds specifying the creation time of this relay proxy configuration")
  public Long getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Long creationDate) {
    this.creationDate = creationDate;
  }

  public RelayProxyConfig lastModified(Long lastModified) {
    this.lastModified = lastModified;
    return this;
  }

   /**
   * A unix epoch time in milliseconds specifying the last time this relay proxy configuration was modified
   * @return lastModified
  **/
  @ApiModelProperty(example = "1469326565348", required = true, value = "A unix epoch time in milliseconds specifying the last time this relay proxy configuration was modified")
  public Long getLastModified() {
    return lastModified;
  }

  public void setLastModified(Long lastModified) {
    this.lastModified = lastModified;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RelayProxyConfig relayProxyConfig = (RelayProxyConfig) o;
    return Objects.equals(this.id, relayProxyConfig.id) &&
        Objects.equals(this.creator, relayProxyConfig.creator) &&
        Objects.equals(this.name, relayProxyConfig.name) &&
        Objects.equals(this.policy, relayProxyConfig.policy) &&
        Objects.equals(this.fullKey, relayProxyConfig.fullKey) &&
        Objects.equals(this.displayKey, relayProxyConfig.displayKey) &&
        Objects.equals(this.creationDate, relayProxyConfig.creationDate) &&
        Objects.equals(this.lastModified, relayProxyConfig.lastModified);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, creator, name, policy, fullKey, displayKey, creationDate, lastModified);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RelayProxyConfig {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    creator: ").append(toIndentedString(creator)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    policy: ").append(toIndentedString(policy)).append("\n");
    sb.append("    fullKey: ").append(toIndentedString(fullKey)).append("\n");
    sb.append("    displayKey: ").append(toIndentedString(displayKey)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
    sb.append("    lastModified: ").append(toIndentedString(lastModified)).append("\n");
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

