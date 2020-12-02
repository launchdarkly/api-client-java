/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 3.9.1
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

/**
 * Default values to be used when a new environment is created.
 */
@ApiModel(description = "Default values to be used when a new environment is created.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-12-02T21:06:34.811Z")
public class Defaults {
  @SerializedName("onVariation")
  private Integer onVariation = null;

  @SerializedName("offVariation")
  private Integer offVariation = null;

  public Defaults onVariation(Integer onVariation) {
    this.onVariation = onVariation;
    return this;
  }

   /**
   * The index of the variation to be served when a flag&#39;s targeting is on (default variation).
   * @return onVariation
  **/
  @ApiModelProperty(required = true, value = "The index of the variation to be served when a flag's targeting is on (default variation).")
  public Integer getOnVariation() {
    return onVariation;
  }

  public void setOnVariation(Integer onVariation) {
    this.onVariation = onVariation;
  }

  public Defaults offVariation(Integer offVariation) {
    this.offVariation = offVariation;
    return this;
  }

   /**
   * The index of the variation to be served when a flag is off.
   * @return offVariation
  **/
  @ApiModelProperty(required = true, value = "The index of the variation to be served when a flag is off.")
  public Integer getOffVariation() {
    return offVariation;
  }

  public void setOffVariation(Integer offVariation) {
    this.offVariation = offVariation;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Defaults defaults = (Defaults) o;
    return Objects.equals(this.onVariation, defaults.onVariation) &&
        Objects.equals(this.offVariation, defaults.offVariation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(onVariation, offVariation);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Defaults {\n");
    
    sb.append("    onVariation: ").append(toIndentedString(onVariation)).append("\n");
    sb.append("    offVariation: ").append(toIndentedString(offVariation)).append("\n");
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

