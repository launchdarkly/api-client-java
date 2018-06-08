/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 2.0.2
 * Contact: support@launchdarkly.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.launchdarkly.api.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.launchdarkly.api.model.Actions;
import com.launchdarkly.api.model.Resources;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * Statement
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-06-08T11:19:31.647-07:00")
public class Statement {
  @SerializedName("resources")
  private Resources resources = null;

  @SerializedName("notresources")
  private Resources notresources = null;

  @SerializedName("actions")
  private Actions actions = null;

  @SerializedName("notactions")
  private Actions notactions = null;

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

  public Statement resources(Resources resources) {
    this.resources = resources;
    return this;
  }

   /**
   * Get resources
   * @return resources
  **/
  @ApiModelProperty(value = "")
  public Resources getResources() {
    return resources;
  }

  public void setResources(Resources resources) {
    this.resources = resources;
  }

  public Statement notresources(Resources notresources) {
    this.notresources = notresources;
    return this;
  }

   /**
   * Targeted resource will be those resources NOT in this list. The \&quot;resources&#x60;\&quot; field must be empty to use this field.
   * @return notresources
  **/
  @ApiModelProperty(value = "Targeted resource will be those resources NOT in this list. The \"resources`\" field must be empty to use this field.")
  public Resources getNotresources() {
    return notresources;
  }

  public void setNotresources(Resources notresources) {
    this.notresources = notresources;
  }

  public Statement actions(Actions actions) {
    this.actions = actions;
    return this;
  }

   /**
   * Get actions
   * @return actions
  **/
  @ApiModelProperty(value = "")
  public Actions getActions() {
    return actions;
  }

  public void setActions(Actions actions) {
    this.actions = actions;
  }

  public Statement notactions(Actions notactions) {
    this.notactions = notactions;
    return this;
  }

   /**
   * Targeted actions will be those actions NOT in this list. The \&quot;actions\&quot; field must be empty to use this field.
   * @return notactions
  **/
  @ApiModelProperty(value = "Targeted actions will be those actions NOT in this list. The \"actions\" field must be empty to use this field.")
  public Actions getNotactions() {
    return notactions;
  }

  public void setNotactions(Actions notactions) {
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

