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
import java.util.ArrayList;
import java.util.List;

/**
 * ApprovalRequestConfigBody
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-02-22T22:04:52.396Z")



public class ApprovalRequestConfigBody {
  @SerializedName("description")
  private String description = null;

  @SerializedName("instructions")
  private SemanticPatchInstruction instructions = null;

  @SerializedName("notifyMemberIds")
  private List<String> notifyMemberIds = new ArrayList<String>();

  @SerializedName("comment")
  private String comment = null;

  public ApprovalRequestConfigBody description(String description) {
    this.description = description;
    return this;
  }

   /**
   * A name that describes the changes you would like to apply to a feature flag configuration
   * @return description
  **/
  @ApiModelProperty(required = true, value = "A name that describes the changes you would like to apply to a feature flag configuration")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ApprovalRequestConfigBody instructions(SemanticPatchInstruction instructions) {
    this.instructions = instructions;
    return this;
  }

   /**
   * Get instructions
   * @return instructions
  **/
  @ApiModelProperty(required = true, value = "")
  public SemanticPatchInstruction getInstructions() {
    return instructions;
  }

  public void setInstructions(SemanticPatchInstruction instructions) {
    this.instructions = instructions;
  }

  public ApprovalRequestConfigBody notifyMemberIds(List<String> notifyMemberIds) {
    this.notifyMemberIds = notifyMemberIds;
    return this;
  }

  public ApprovalRequestConfigBody addNotifyMemberIdsItem(String notifyMemberIdsItem) {
    this.notifyMemberIds.add(notifyMemberIdsItem);
    return this;
  }

   /**
   * Get notifyMemberIds
   * @return notifyMemberIds
  **/
  @ApiModelProperty(example = "[\"memberId\",\"memberId2\"]", required = true, value = "")
  public List<String> getNotifyMemberIds() {
    return notifyMemberIds;
  }

  public void setNotifyMemberIds(List<String> notifyMemberIds) {
    this.notifyMemberIds = notifyMemberIds;
  }

  public ApprovalRequestConfigBody comment(String comment) {
    this.comment = comment;
    return this;
  }

   /**
   * comment will be included in audit log item for change.
   * @return comment
  **/
  @ApiModelProperty(value = "comment will be included in audit log item for change.")
  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApprovalRequestConfigBody approvalRequestConfigBody = (ApprovalRequestConfigBody) o;
    return Objects.equals(this.description, approvalRequestConfigBody.description) &&
        Objects.equals(this.instructions, approvalRequestConfigBody.instructions) &&
        Objects.equals(this.notifyMemberIds, approvalRequestConfigBody.notifyMemberIds) &&
        Objects.equals(this.comment, approvalRequestConfigBody.comment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, instructions, notifyMemberIds, comment);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApprovalRequestConfigBody {\n");
    
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    instructions: ").append(toIndentedString(instructions)).append("\n");
    sb.append("    notifyMemberIds: ").append(toIndentedString(notifyMemberIds)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
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
