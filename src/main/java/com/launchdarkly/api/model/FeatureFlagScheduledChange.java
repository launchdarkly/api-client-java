/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 3.7.1
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
import com.launchdarkly.api.model.SemanticPatchInstruction;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * FeatureFlagScheduledChange
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-13T17:38:46.164Z")
public class FeatureFlagScheduledChange {
  @SerializedName("executionDate")
  private Integer executionDate = null;

  @SerializedName("_version")
  private Integer version = null;

  @SerializedName("_id")
  private String id = null;

  @SerializedName("instructions")
  private SemanticPatchInstruction instructions = null;

  public FeatureFlagScheduledChange executionDate(Integer executionDate) {
    this.executionDate = executionDate;
    return this;
  }

   /**
   * A unix epoch time in milliseconds specifying the date the scheduled changes will be applied
   * @return executionDate
  **/
  @ApiModelProperty(value = "A unix epoch time in milliseconds specifying the date the scheduled changes will be applied")
  public Integer getExecutionDate() {
    return executionDate;
  }

  public void setExecutionDate(Integer executionDate) {
    this.executionDate = executionDate;
  }

  public FeatureFlagScheduledChange version(Integer version) {
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

  public FeatureFlagScheduledChange id(String id) {
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

  public FeatureFlagScheduledChange instructions(SemanticPatchInstruction instructions) {
    this.instructions = instructions;
    return this;
  }

   /**
   * Get instructions
   * @return instructions
  **/
  @ApiModelProperty(value = "")
  public SemanticPatchInstruction getInstructions() {
    return instructions;
  }

  public void setInstructions(SemanticPatchInstruction instructions) {
    this.instructions = instructions;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FeatureFlagScheduledChange featureFlagScheduledChange = (FeatureFlagScheduledChange) o;
    return Objects.equals(this.executionDate, featureFlagScheduledChange.executionDate) &&
        Objects.equals(this.version, featureFlagScheduledChange.version) &&
        Objects.equals(this.id, featureFlagScheduledChange.id) &&
        Objects.equals(this.instructions, featureFlagScheduledChange.instructions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(executionDate, version, id, instructions);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FeatureFlagScheduledChange {\n");
    
    sb.append("    executionDate: ").append(toIndentedString(executionDate)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    instructions: ").append(toIndentedString(instructions)).append("\n");
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

