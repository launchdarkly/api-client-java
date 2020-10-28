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
import com.launchdarkly.api.model.Clause;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * UserSegmentRule
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-28T14:53:40.380Z")
public class UserSegmentRule {
  @SerializedName("clauses")
  private List<Clause> clauses = null;

  @SerializedName("weight")
  private Integer weight = null;

  @SerializedName("bucketBy")
  private String bucketBy = null;

  public UserSegmentRule clauses(List<Clause> clauses) {
    this.clauses = clauses;
    return this;
  }

  public UserSegmentRule addClausesItem(Clause clausesItem) {
    if (this.clauses == null) {
      this.clauses = new ArrayList<Clause>();
    }
    this.clauses.add(clausesItem);
    return this;
  }

   /**
   * Get clauses
   * @return clauses
  **/
  @ApiModelProperty(value = "")
  public List<Clause> getClauses() {
    return clauses;
  }

  public void setClauses(List<Clause> clauses) {
    this.clauses = clauses;
  }

  public UserSegmentRule weight(Integer weight) {
    this.weight = weight;
    return this;
  }

   /**
   * Get weight
   * @return weight
  **/
  @ApiModelProperty(value = "")
  public Integer getWeight() {
    return weight;
  }

  public void setWeight(Integer weight) {
    this.weight = weight;
  }

  public UserSegmentRule bucketBy(String bucketBy) {
    this.bucketBy = bucketBy;
    return this;
  }

   /**
   * Get bucketBy
   * @return bucketBy
  **/
  @ApiModelProperty(value = "")
  public String getBucketBy() {
    return bucketBy;
  }

  public void setBucketBy(String bucketBy) {
    this.bucketBy = bucketBy;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserSegmentRule userSegmentRule = (UserSegmentRule) o;
    return Objects.equals(this.clauses, userSegmentRule.clauses) &&
        Objects.equals(this.weight, userSegmentRule.weight) &&
        Objects.equals(this.bucketBy, userSegmentRule.bucketBy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clauses, weight, bucketBy);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserSegmentRule {\n");
    
    sb.append("    clauses: ").append(toIndentedString(clauses)).append("\n");
    sb.append("    weight: ").append(toIndentedString(weight)).append("\n");
    sb.append("    bucketBy: ").append(toIndentedString(bucketBy)).append("\n");
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

