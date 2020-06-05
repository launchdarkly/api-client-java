/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 3.3.1
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
import java.math.BigDecimal;

/**
 * UserTargetingExpirationForSegment
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-05T11:10:44.621Z")
public class UserTargetingExpirationForSegment {
  @SerializedName("expirationDate")
  private BigDecimal expirationDate = null;

  @SerializedName("targetType")
  private String targetType = null;

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

  public UserTargetingExpirationForSegment expirationDate(BigDecimal expirationDate) {
    this.expirationDate = expirationDate;
    return this;
  }

   /**
   * Unix epoch time in milliseconds specifying the expiration date
   * @return expirationDate
  **/
  @ApiModelProperty(example = "1.7356896E12", value = "Unix epoch time in milliseconds specifying the expiration date")
  public BigDecimal getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(BigDecimal expirationDate) {
    this.expirationDate = expirationDate;
  }

  public UserTargetingExpirationForSegment targetType(String targetType) {
    this.targetType = targetType;
    return this;
  }

   /**
   * either the included or excluded variation that the user is targeted on a segment
   * @return targetType
  **/
  @ApiModelProperty(value = "either the included or excluded variation that the user is targeted on a segment")
  public String getTargetType() {
    return targetType;
  }

  public void setTargetType(String targetType) {
    this.targetType = targetType;
  }

  public UserTargetingExpirationForSegment userKey(String userKey) {
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

  public UserTargetingExpirationForSegment id(String id) {
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

  public UserTargetingExpirationForSegment resourceId(UserTargetingExpirationResourceIdForFlag resourceId) {
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

  public UserTargetingExpirationForSegment links(Links links) {
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

  public UserTargetingExpirationForSegment version(Integer version) {
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
    UserTargetingExpirationForSegment userTargetingExpirationForSegment = (UserTargetingExpirationForSegment) o;
    return Objects.equals(this.expirationDate, userTargetingExpirationForSegment.expirationDate) &&
        Objects.equals(this.targetType, userTargetingExpirationForSegment.targetType) &&
        Objects.equals(this.userKey, userTargetingExpirationForSegment.userKey) &&
        Objects.equals(this.id, userTargetingExpirationForSegment.id) &&
        Objects.equals(this.resourceId, userTargetingExpirationForSegment.resourceId) &&
        Objects.equals(this.links, userTargetingExpirationForSegment.links) &&
        Objects.equals(this.version, userTargetingExpirationForSegment.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(expirationDate, targetType, userKey, id, resourceId, links, version);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserTargetingExpirationForSegment {\n");
    
    sb.append("    expirationDate: ").append(toIndentedString(expirationDate)).append("\n");
    sb.append("    targetType: ").append(toIndentedString(targetType)).append("\n");
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

