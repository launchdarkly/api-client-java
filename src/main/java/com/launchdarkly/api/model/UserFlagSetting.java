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
import com.launchdarkly.api.model.Links;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * UserFlagSetting
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-12-02T21:06:34.811Z")
public class UserFlagSetting {
  @SerializedName("_links")
  private Links links = null;

  @SerializedName("_value")
  private Boolean value = null;

  @SerializedName("setting")
  private Boolean setting = null;

  public UserFlagSetting links(Links links) {
    this.links = links;
    return this;
  }

   /**
   * Get links
   * @return links
  **/
  @ApiModelProperty(value = "")
  public Links getLinks() {
    return links;
  }

  public void setLinks(Links links) {
    this.links = links;
  }

  public UserFlagSetting value(Boolean value) {
    this.value = value;
    return this;
  }

   /**
   * The most important attribute in the response. The _value is the current setting for the user. For a boolean feature toggle, this will be true, false, or null if there is no defined fallthrough value.
   * @return value
  **/
  @ApiModelProperty(example = "true", value = "The most important attribute in the response. The _value is the current setting for the user. For a boolean feature toggle, this will be true, false, or null if there is no defined fallthrough value.")
  public Boolean isValue() {
    return value;
  }

  public void setValue(Boolean value) {
    this.value = value;
  }

  public UserFlagSetting setting(Boolean setting) {
    this.setting = setting;
    return this;
  }

   /**
   * The setting attribute indicates whether you&#39;ve explicitly targeted this user to receive a particular variation. For example, if you have explicitly turned off a feature toggle for a user, setting will be false. A setting of null means that you haven&#39;t assigned that user to a specific variation.
   * @return setting
  **/
  @ApiModelProperty(value = "The setting attribute indicates whether you've explicitly targeted this user to receive a particular variation. For example, if you have explicitly turned off a feature toggle for a user, setting will be false. A setting of null means that you haven't assigned that user to a specific variation.")
  public Boolean isSetting() {
    return setting;
  }

  public void setSetting(Boolean setting) {
    this.setting = setting;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserFlagSetting userFlagSetting = (UserFlagSetting) o;
    return Objects.equals(this.links, userFlagSetting.links) &&
        Objects.equals(this.value, userFlagSetting.value) &&
        Objects.equals(this.setting, userFlagSetting.setting);
  }

  @Override
  public int hashCode() {
    return Objects.hash(links, value, setting);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserFlagSetting {\n");
    
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    setting: ").append(toIndentedString(setting)).append("\n");
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

