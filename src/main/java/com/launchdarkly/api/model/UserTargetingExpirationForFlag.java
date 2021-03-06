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


package com.launchdarkly.api.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.launchdarkly.api.model.Links;
import com.launchdarkly.api.model.UserTargetingExpirationResourceIdForFlag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * UserTargetingExpirationForFlag
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-04-21T16:19:08.589Z")



public class UserTargetingExpirationForFlag {
  @SerializedName("expirationDate")
  private Long expirationDate = null;

  @SerializedName("variationId")
  private String variationId = null;

  @SerializedName("userKey")
  private String userKey = null;

  @SerializedName("_id")
  private String id = null;

  @SerializedName("_resourceId")
  private UserTargetingExpirationResourceIdForFlag resourceId = null;

  @SerializedName("_links")
  private Links links = null;

  @SerializedName("_version")
  private Integer version = null;

  public UserTargetingExpirationForFlag expirationDate(Long expirationDate) {
    this.expirationDate = expirationDate;
    return this;
  }

   /**
   * Unix epoch time in milliseconds specifying the expiration date
   * @return expirationDate
  **/
  @ApiModelProperty(example = "1735689600000", value = "Unix epoch time in milliseconds specifying the expiration date")
  public Long getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(Long expirationDate) {
    this.expirationDate = expirationDate;
  }

  public UserTargetingExpirationForFlag variationId(String variationId) {
    this.variationId = variationId;
    return this;
  }

   /**
   * the ID of the variation that the user is targeted on a flag
   * @return variationId
  **/
  @ApiModelProperty(value = "the ID of the variation that the user is targeted on a flag")
  public String getVariationId() {
    return variationId;
  }

  public void setVariationId(String variationId) {
    this.variationId = variationId;
  }

  public UserTargetingExpirationForFlag userKey(String userKey) {
    this.userKey = userKey;
    return this;
  }

   /**
   * Unique identifier for the user
   * @return userKey
  **/
  @ApiModelProperty(value = "Unique identifier for the user")
  public String getUserKey() {
    return userKey;
  }

  public void setUserKey(String userKey) {
    this.userKey = userKey;
  }

  public UserTargetingExpirationForFlag id(String id) {
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

  public UserTargetingExpirationForFlag resourceId(UserTargetingExpirationResourceIdForFlag resourceId) {
    this.resourceId = resourceId;
    return this;
  }

   /**
   * Get resourceId
   * @return resourceId
  **/
  @ApiModelProperty(value = "")
  public UserTargetingExpirationResourceIdForFlag getResourceId() {
    return resourceId;
  }

  public void setResourceId(UserTargetingExpirationResourceIdForFlag resourceId) {
    this.resourceId = resourceId;
  }

  public UserTargetingExpirationForFlag links(Links links) {
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

  public UserTargetingExpirationForFlag version(Integer version) {
    this.version = version;
    return this;
  }

   /**
   * Get version
   * @return version
  **/
  @ApiModelProperty(value = "")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserTargetingExpirationForFlag userTargetingExpirationForFlag = (UserTargetingExpirationForFlag) o;
    return Objects.equals(this.expirationDate, userTargetingExpirationForFlag.expirationDate) &&
        Objects.equals(this.variationId, userTargetingExpirationForFlag.variationId) &&
        Objects.equals(this.userKey, userTargetingExpirationForFlag.userKey) &&
        Objects.equals(this.id, userTargetingExpirationForFlag.id) &&
        Objects.equals(this.resourceId, userTargetingExpirationForFlag.resourceId) &&
        Objects.equals(this.links, userTargetingExpirationForFlag.links) &&
        Objects.equals(this.version, userTargetingExpirationForFlag.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(expirationDate, variationId, userKey, id, resourceId, links, version);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserTargetingExpirationForFlag {\n");
    
    sb.append("    expirationDate: ").append(toIndentedString(expirationDate)).append("\n");
    sb.append("    variationId: ").append(toIndentedString(variationId)).append("\n");
    sb.append("    userKey: ").append(toIndentedString(userKey)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    resourceId: ").append(toIndentedString(resourceId)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
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

