/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 5.0.0
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
import com.launchdarkly.api.model.ApprovalRequestReviewStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * ApprovalRequestReview
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-02-09T21:10:42.024Z")
public class ApprovalRequestReview {
  @SerializedName("creationDate")
  private Integer creationDate = null;

  @SerializedName("kind")
  private ApprovalRequestReviewStatus kind = null;

  @SerializedName("memberId")
  private String memberId = null;

  @SerializedName("_id")
  private String id = null;

  public ApprovalRequestReview creationDate(Integer creationDate) {
    this.creationDate = creationDate;
    return this;
  }

   /**
   * A unix epoch time in milliseconds specifying the date the approval request was reviewed
   * @return creationDate
  **/
  @ApiModelProperty(value = "A unix epoch time in milliseconds specifying the date the approval request was reviewed")
  public Integer getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Integer creationDate) {
    this.creationDate = creationDate;
  }

  public ApprovalRequestReview kind(ApprovalRequestReviewStatus kind) {
    this.kind = kind;
    return this;
  }

   /**
   * Get kind
   * @return kind
  **/
  @ApiModelProperty(value = "")
  public ApprovalRequestReviewStatus getKind() {
    return kind;
  }

  public void setKind(ApprovalRequestReviewStatus kind) {
    this.kind = kind;
  }

  public ApprovalRequestReview memberId(String memberId) {
    this.memberId = memberId;
    return this;
  }

   /**
   * Get memberId
   * @return memberId
  **/
  @ApiModelProperty(value = "")
  public String getMemberId() {
    return memberId;
  }

  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

  public ApprovalRequestReview id(String id) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApprovalRequestReview approvalRequestReview = (ApprovalRequestReview) o;
    return Objects.equals(this.creationDate, approvalRequestReview.creationDate) &&
        Objects.equals(this.kind, approvalRequestReview.kind) &&
        Objects.equals(this.memberId, approvalRequestReview.memberId) &&
        Objects.equals(this.id, approvalRequestReview.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(creationDate, kind, memberId, id);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApprovalRequestReview {\n");
    
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
    sb.append("    kind: ").append(toIndentedString(kind)).append("\n");
    sb.append("    memberId: ").append(toIndentedString(memberId)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

