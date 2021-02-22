/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 5.0.2
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
 * FlagConfigScheduledChangesPostBody
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-02-22T22:04:52.396Z")



public class FlagConfigScheduledChangesPostBody {
  @SerializedName("comment")
  private String comment = null;

  @SerializedName("executionDate")
  private Integer executionDate = null;

  @SerializedName("instructions")
  private SemanticPatchInstruction instructions = null;

  public FlagConfigScheduledChangesPostBody comment(String comment) {
    this.comment = comment;
    return this;
  }

   /**
   * Used to describe the scheduled changes.
   * @return comment
  **/
  @ApiModelProperty(value = "Used to describe the scheduled changes.")
  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public FlagConfigScheduledChangesPostBody executionDate(Integer executionDate) {
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

  public FlagConfigScheduledChangesPostBody instructions(SemanticPatchInstruction instructions) {
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
    FlagConfigScheduledChangesPostBody flagConfigScheduledChangesPostBody = (FlagConfigScheduledChangesPostBody) o;
    return Objects.equals(this.comment, flagConfigScheduledChangesPostBody.comment) &&
        Objects.equals(this.executionDate, flagConfigScheduledChangesPostBody.executionDate) &&
        Objects.equals(this.instructions, flagConfigScheduledChangesPostBody.instructions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comment, executionDate, instructions);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FlagConfigScheduledChangesPostBody {\n");
    
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    executionDate: ").append(toIndentedString(executionDate)).append("\n");
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

