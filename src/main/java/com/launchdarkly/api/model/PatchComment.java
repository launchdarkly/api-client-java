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
import com.launchdarkly.api.model.PatchOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * PatchComment
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-28T14:46:17.158Z")
public class PatchComment {
  @SerializedName("comment")
  private String comment = null;

  @SerializedName("patch")
  private List<PatchOperation> patch = null;

  public PatchComment comment(String comment) {
    this.comment = comment;
    return this;
  }

   /**
   * Get comment
   * @return comment
  **/
  @ApiModelProperty(example = "This is a comment string", value = "")
  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public PatchComment patch(List<PatchOperation> patch) {
    this.patch = patch;
    return this;
  }

  public PatchComment addPatchItem(PatchOperation patchItem) {
    if (this.patch == null) {
      this.patch = new ArrayList<PatchOperation>();
    }
    this.patch.add(patchItem);
    return this;
  }

   /**
   * Get patch
   * @return patch
  **/
  @ApiModelProperty(value = "")
  public List<PatchOperation> getPatch() {
    return patch;
  }

  public void setPatch(List<PatchOperation> patch) {
    this.patch = patch;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PatchComment patchComment = (PatchComment) o;
    return Objects.equals(this.comment, patchComment.comment) &&
        Objects.equals(this.patch, patchComment.patch);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comment, patch);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PatchComment {\n");
    
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    patch: ").append(toIndentedString(patch)).append("\n");
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

