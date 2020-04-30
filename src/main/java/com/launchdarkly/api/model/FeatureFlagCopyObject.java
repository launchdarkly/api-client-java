/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 3.1.0
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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * FeatureFlagCopyObject
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-04-30T22:42:00.060Z")
public class FeatureFlagCopyObject {
  @SerializedName("key")
  private String key = null;

  @SerializedName("currentVersion")
  private Integer currentVersion = null;

  public FeatureFlagCopyObject key(String key) {
    this.key = key;
    return this;
  }

   /**
   * The environment key to be used.
   * @return key
  **/
  @ApiModelProperty(example = "staging", required = true, value = "The environment key to be used.")
  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public FeatureFlagCopyObject currentVersion(Integer currentVersion) {
    this.currentVersion = currentVersion;
    return this;
  }

   /**
   * If the latest version of the flag matches provided version it will copy, otherwise it will return a conflict.
   * @return currentVersion
  **/
  @ApiModelProperty(example = "65", value = "If the latest version of the flag matches provided version it will copy, otherwise it will return a conflict.")
  public Integer getCurrentVersion() {
    return currentVersion;
  }

  public void setCurrentVersion(Integer currentVersion) {
    this.currentVersion = currentVersion;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FeatureFlagCopyObject featureFlagCopyObject = (FeatureFlagCopyObject) o;
    return Objects.equals(this.key, featureFlagCopyObject.key) &&
        Objects.equals(this.currentVersion, featureFlagCopyObject.currentVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, currentVersion);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FeatureFlagCopyObject {\n");
    
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    currentVersion: ").append(toIndentedString(currentVersion)).append("\n");
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

