/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 3.9.2
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
import com.launchdarkly.api.model.Rollout;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Rule
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-12-03T00:55:33.026Z")
public class Rule {
  @SerializedName("_id")
  private String id = null;

  @SerializedName("variation")
  private Integer variation = null;

  @SerializedName("trackEvents")
  private Boolean trackEvents = null;

  @SerializedName("rollout")
  private Rollout rollout = null;

  @SerializedName("clauses")
  private List<Clause> clauses = null;

  @SerializedName("description")
  private String description = null;

  public Rule id(String id) {
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

  public Rule variation(Integer variation) {
    this.variation = variation;
    return this;
  }

   /**
   * Get variation
   * @return variation
  **/
  @ApiModelProperty(value = "")
  public Integer getVariation() {
    return variation;
  }

  public void setVariation(Integer variation) {
    this.variation = variation;
  }

  public Rule trackEvents(Boolean trackEvents) {
    this.trackEvents = trackEvents;
    return this;
  }

   /**
   * Get trackEvents
   * @return trackEvents
  **/
  @ApiModelProperty(value = "")
  public Boolean isTrackEvents() {
    return trackEvents;
  }

  public void setTrackEvents(Boolean trackEvents) {
    this.trackEvents = trackEvents;
  }

  public Rule rollout(Rollout rollout) {
    this.rollout = rollout;
    return this;
  }

   /**
   * Get rollout
   * @return rollout
  **/
  @ApiModelProperty(value = "")
  public Rollout getRollout() {
    return rollout;
  }

  public void setRollout(Rollout rollout) {
    this.rollout = rollout;
  }

  public Rule clauses(List<Clause> clauses) {
    this.clauses = clauses;
    return this;
  }

  public Rule addClausesItem(Clause clausesItem) {
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

  public Rule description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(value = "")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Rule rule = (Rule) o;
    return Objects.equals(this.id, rule.id) &&
        Objects.equals(this.variation, rule.variation) &&
        Objects.equals(this.trackEvents, rule.trackEvents) &&
        Objects.equals(this.rollout, rule.rollout) &&
        Objects.equals(this.clauses, rule.clauses) &&
        Objects.equals(this.description, rule.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, variation, trackEvents, rollout, clauses, description);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Rule {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    variation: ").append(toIndentedString(variation)).append("\n");
    sb.append("    trackEvents: ").append(toIndentedString(trackEvents)).append("\n");
    sb.append("    rollout: ").append(toIndentedString(rollout)).append("\n");
    sb.append("    clauses: ").append(toIndentedString(clauses)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

