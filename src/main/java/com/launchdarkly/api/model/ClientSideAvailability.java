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
 * ClientSideAvailability
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-12-02T21:06:34.811Z")
public class ClientSideAvailability {
  @SerializedName("usingEnvironmentId")
  private Boolean usingEnvironmentId = null;

  @SerializedName("usingMobileKey")
  private Boolean usingMobileKey = null;

  public ClientSideAvailability usingEnvironmentId(Boolean usingEnvironmentId) {
    this.usingEnvironmentId = usingEnvironmentId;
    return this;
  }

   /**
   * When set to true, this flag will be available to SDKs using the client-side id.
   * @return usingEnvironmentId
  **/
  @ApiModelProperty(value = "When set to true, this flag will be available to SDKs using the client-side id.")
  public Boolean isUsingEnvironmentId() {
    return usingEnvironmentId;
  }

  public void setUsingEnvironmentId(Boolean usingEnvironmentId) {
    this.usingEnvironmentId = usingEnvironmentId;
  }

  public ClientSideAvailability usingMobileKey(Boolean usingMobileKey) {
    this.usingMobileKey = usingMobileKey;
    return this;
  }

   /**
   * When set to true, this flag will be available to SDKS using a mobile key.
   * @return usingMobileKey
  **/
  @ApiModelProperty(value = "When set to true, this flag will be available to SDKS using a mobile key.")
  public Boolean isUsingMobileKey() {
    return usingMobileKey;
  }

  public void setUsingMobileKey(Boolean usingMobileKey) {
    this.usingMobileKey = usingMobileKey;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClientSideAvailability clientSideAvailability = (ClientSideAvailability) o;
    return Objects.equals(this.usingEnvironmentId, clientSideAvailability.usingEnvironmentId) &&
        Objects.equals(this.usingMobileKey, clientSideAvailability.usingMobileKey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(usingEnvironmentId, usingMobileKey);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClientSideAvailability {\n");
    
    sb.append("    usingEnvironmentId: ").append(toIndentedString(usingEnvironmentId)).append("\n");
    sb.append("    usingMobileKey: ").append(toIndentedString(usingMobileKey)).append("\n");
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

