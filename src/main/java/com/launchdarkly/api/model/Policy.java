/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 5.0.1
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
import java.util.ArrayList;
import java.util.List;

/**
 * Policy
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-02-19T19:16:12.901Z")
public class Policy {
  @SerializedName("resources")
  private List<String> resources = null;

  @SerializedName("notResources")
  private List<String> notResources = null;

  @SerializedName("actions")
  private List<String> actions = null;

  @SerializedName("notActions")
  private List<String> notActions = null;

  @SerializedName("effect")
  private String effect = null;

  public Policy resources(List<String> resources) {
    this.resources = resources;
    return this;
  }

  public Policy addResourcesItem(String resourcesItem) {
    if (this.resources == null) {
      this.resources = new ArrayList<String>();
    }
    this.resources.add(resourcesItem);
    return this;
  }

   /**
   * Get resources
   * @return resources
  **/
  @ApiModelProperty(value = "")
  public List<String> getResources() {
    return resources;
  }

  public void setResources(List<String> resources) {
    this.resources = resources;
  }

  public Policy notResources(List<String> notResources) {
    this.notResources = notResources;
    return this;
  }

  public Policy addNotResourcesItem(String notResourcesItem) {
    if (this.notResources == null) {
      this.notResources = new ArrayList<String>();
    }
    this.notResources.add(notResourcesItem);
    return this;
  }

   /**
   * Targeted resource will be those resources NOT in this list. The \&quot;resources&#x60;\&quot; field must be empty to use this field.
   * @return notResources
  **/
  @ApiModelProperty(value = "Targeted resource will be those resources NOT in this list. The \"resources`\" field must be empty to use this field.")
  public List<String> getNotResources() {
    return notResources;
  }

  public void setNotResources(List<String> notResources) {
    this.notResources = notResources;
  }

  public Policy actions(List<String> actions) {
    this.actions = actions;
    return this;
  }

  public Policy addActionsItem(String actionsItem) {
    if (this.actions == null) {
      this.actions = new ArrayList<String>();
    }
    this.actions.add(actionsItem);
    return this;
  }

   /**
   * Get actions
   * @return actions
  **/
  @ApiModelProperty(value = "")
  public List<String> getActions() {
    return actions;
  }

  public void setActions(List<String> actions) {
    this.actions = actions;
  }

  public Policy notActions(List<String> notActions) {
    this.notActions = notActions;
    return this;
  }

  public Policy addNotActionsItem(String notActionsItem) {
    if (this.notActions == null) {
      this.notActions = new ArrayList<String>();
    }
    this.notActions.add(notActionsItem);
    return this;
  }

   /**
   * Targeted actions will be those actions NOT in this list. The \&quot;actions\&quot; field must be empty to use this field.
   * @return notActions
  **/
  @ApiModelProperty(value = "Targeted actions will be those actions NOT in this list. The \"actions\" field must be empty to use this field.")
  public List<String> getNotActions() {
    return notActions;
  }

  public void setNotActions(List<String> notActions) {
    this.notActions = notActions;
  }

  public Policy effect(String effect) {
    this.effect = effect;
    return this;
  }

   /**
   * Effect of the policy - allow or deny.
   * @return effect
  **/
  @ApiModelProperty(example = "deny", value = "Effect of the policy - allow or deny.")
  public String getEffect() {
    return effect;
  }

  public void setEffect(String effect) {
    this.effect = effect;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Policy policy = (Policy) o;
    return Objects.equals(this.resources, policy.resources) &&
        Objects.equals(this.notResources, policy.notResources) &&
        Objects.equals(this.actions, policy.actions) &&
        Objects.equals(this.notActions, policy.notActions) &&
        Objects.equals(this.effect, policy.effect);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resources, notResources, actions, notActions, effect);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Policy {\n");
    
    sb.append("    resources: ").append(toIndentedString(resources)).append("\n");
    sb.append("    notResources: ").append(toIndentedString(notResources)).append("\n");
    sb.append("    actions: ").append(toIndentedString(actions)).append("\n");
    sb.append("    notActions: ").append(toIndentedString(notActions)).append("\n");
    sb.append("    effect: ").append(toIndentedString(effect)).append("\n");
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

