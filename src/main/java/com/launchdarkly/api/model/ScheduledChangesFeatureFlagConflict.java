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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * ScheduledChangesFeatureFlagConflict
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-02-09T21:10:42.024Z")
public class ScheduledChangesFeatureFlagConflict {
  @SerializedName("_id")
  private String id = null;

  @SerializedName("reason")
  private String reason = null;

  public ScheduledChangesFeatureFlagConflict id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Feature flag scheduled change id this change will conflict with
   * @return id
  **/
  @ApiModelProperty(value = "Feature flag scheduled change id this change will conflict with")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ScheduledChangesFeatureFlagConflict reason(String reason) {
    this.reason = reason;
    return this;
  }

   /**
   * Feature flag scheduled change conflict reason
   * @return reason
  **/
  @ApiModelProperty(value = "Feature flag scheduled change conflict reason")
  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScheduledChangesFeatureFlagConflict scheduledChangesFeatureFlagConflict = (ScheduledChangesFeatureFlagConflict) o;
    return Objects.equals(this.id, scheduledChangesFeatureFlagConflict.id) &&
        Objects.equals(this.reason, scheduledChangesFeatureFlagConflict.reason);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, reason);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ScheduledChangesFeatureFlagConflict {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
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

