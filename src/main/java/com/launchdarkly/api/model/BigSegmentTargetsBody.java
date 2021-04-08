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
import com.launchdarkly.api.model.BigSegmentTargetChanges;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * BigSegmentTargetsBody
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-04-08T01:21:45.269Z")



public class BigSegmentTargetsBody {
  @SerializedName("included")
  private BigSegmentTargetChanges included = null;

  @SerializedName("excluded")
  private BigSegmentTargetChanges excluded = null;

  public BigSegmentTargetsBody included(BigSegmentTargetChanges included) {
    this.included = included;
    return this;
  }

   /**
   * Get included
   * @return included
  **/
  @ApiModelProperty(value = "")
  public BigSegmentTargetChanges getIncluded() {
    return included;
  }

  public void setIncluded(BigSegmentTargetChanges included) {
    this.included = included;
  }

  public BigSegmentTargetsBody excluded(BigSegmentTargetChanges excluded) {
    this.excluded = excluded;
    return this;
  }

   /**
   * Get excluded
   * @return excluded
  **/
  @ApiModelProperty(value = "")
  public BigSegmentTargetChanges getExcluded() {
    return excluded;
  }

  public void setExcluded(BigSegmentTargetChanges excluded) {
    this.excluded = excluded;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BigSegmentTargetsBody bigSegmentTargetsBody = (BigSegmentTargetsBody) o;
    return Objects.equals(this.included, bigSegmentTargetsBody.included) &&
        Objects.equals(this.excluded, bigSegmentTargetsBody.excluded);
  }

  @Override
  public int hashCode() {
    return Objects.hash(included, excluded);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BigSegmentTargetsBody {\n");
    
    sb.append("    included: ").append(toIndentedString(included)).append("\n");
    sb.append("    excluded: ").append(toIndentedString(excluded)).append("\n");
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

