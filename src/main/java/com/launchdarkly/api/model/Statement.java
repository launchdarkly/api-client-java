/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 2.0.23
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
 * Statement
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-01-30T19:02:05.080Z")
public class Statement {
  @SerializedName("resources")
  private List<String> resources = null;

  @SerializedName("notresources")
  private List<String> notresources = null;

  @SerializedName("actions")
  private List<String> actions = null;

  @SerializedName("notactions")
  private List<String> notactions = null;

  /**
   * Gets or Sets effect
   */
  @JsonAdapter(EffectEnum.Adapter.class)
  public enum EffectEnum {
    ALLOW("allow"),
    
    DENY("deny");

    private String value;

    EffectEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static EffectEnum fromValue(String text) {
      for (EffectEnum b : EffectEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<EffectEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final EffectEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public EffectEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return EffectEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("effect")
  private EffectEnum effect = null;

  public Statement resources(List<String> resources) {
    this.resources = resources;
    return this;
  }

  public Statement addResourcesItem(String resourcesItem) {
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

  public Statement notresources(List<String> notresources) {
    this.notresources = notresources;
    return this;
  }

  public Statement addNotresourcesItem(String notresourcesItem) {
    if (this.notresources == null) {
      this.notresources = new ArrayList<String>();
    }
    this.notresources.add(notresourcesItem);
    return this;
  }

   /**
   * Targeted resource will be those resources NOT in this list. The \&quot;resources&#x60;\&quot; field must be empty to use this field.
   * @return notresources
  **/
  @ApiModelProperty(value = "Targeted resource will be those resources NOT in this list. The \"resources`\" field must be empty to use this field.")
  public List<String> getNotresources() {
    return notresources;
  }

  public void setNotresources(List<String> notresources) {
    this.notresources = notresources;
  }

  public Statement actions(List<String> actions) {
    this.actions = actions;
    return this;
  }

  public Statement addActionsItem(String actionsItem) {
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

  public Statement notactions(List<String> notactions) {
    this.notactions = notactions;
    return this;
  }

  public Statement addNotactionsItem(String notactionsItem) {
    if (this.notactions == null) {
      this.notactions = new ArrayList<String>();
    }
    this.notactions.add(notactionsItem);
    return this;
  }

   /**
   * Targeted actions will be those actions NOT in this list. The \&quot;actions\&quot; field must be empty to use this field.
   * @return notactions
  **/
  @ApiModelProperty(value = "Targeted actions will be those actions NOT in this list. The \"actions\" field must be empty to use this field.")
  public List<String> getNotactions() {
    return notactions;
  }

  public void setNotactions(List<String> notactions) {
    this.notactions = notactions;
  }

  public Statement effect(EffectEnum effect) {
    this.effect = effect;
    return this;
  }

   /**
   * Get effect
   * @return effect
  **/
  @ApiModelProperty(value = "")
  public EffectEnum getEffect() {
    return effect;
  }

  public void setEffect(EffectEnum effect) {
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
    Statement statement = (Statement) o;
    return Objects.equals(this.resources, statement.resources) &&
        Objects.equals(this.notresources, statement.notresources) &&
        Objects.equals(this.actions, statement.actions) &&
        Objects.equals(this.notactions, statement.notactions) &&
        Objects.equals(this.effect, statement.effect);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resources, notresources, actions, notactions, effect);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Statement {\n");
    
    sb.append("    resources: ").append(toIndentedString(resources)).append("\n");
    sb.append("    notresources: ").append(toIndentedString(notresources)).append("\n");
    sb.append("    actions: ").append(toIndentedString(actions)).append("\n");
    sb.append("    notactions: ").append(toIndentedString(notactions)).append("\n");
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

