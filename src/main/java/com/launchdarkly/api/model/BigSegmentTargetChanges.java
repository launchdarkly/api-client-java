/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 5.1.0
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
 * BigSegmentTargetChanges
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-04-21T16:19:08.589Z")



public class BigSegmentTargetChanges {
  @SerializedName("add")
  private List<String> add = null;

  @SerializedName("remove")
  private List<String> remove = null;

  public BigSegmentTargetChanges add(List<String> add) {
    this.add = add;
    return this;
  }

  public BigSegmentTargetChanges addAddItem(String addItem) {
    if (this.add == null) {
      this.add = new ArrayList<String>();
    }
    this.add.add(addItem);
    return this;
  }

   /**
   * Users to add to this list of targets
   * @return add
  **/
  @ApiModelProperty(example = "[\"user@launchdarkly.com\"]", value = "Users to add to this list of targets")
  public List<String> getAdd() {
    return add;
  }

  public void setAdd(List<String> add) {
    this.add = add;
  }

  public BigSegmentTargetChanges remove(List<String> remove) {
    this.remove = remove;
    return this;
  }

  public BigSegmentTargetChanges addRemoveItem(String removeItem) {
    if (this.remove == null) {
      this.remove = new ArrayList<String>();
    }
    this.remove.add(removeItem);
    return this;
  }

   /**
   * Users to remove from this list of targets
   * @return remove
  **/
  @ApiModelProperty(example = "[]", value = "Users to remove from this list of targets")
  public List<String> getRemove() {
    return remove;
  }

  public void setRemove(List<String> remove) {
    this.remove = remove;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BigSegmentTargetChanges bigSegmentTargetChanges = (BigSegmentTargetChanges) o;
    return Objects.equals(this.add, bigSegmentTargetChanges.add) &&
        Objects.equals(this.remove, bigSegmentTargetChanges.remove);
  }

  @Override
  public int hashCode() {
    return Objects.hash(add, remove);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BigSegmentTargetChanges {\n");
    
    sb.append("    add: ").append(toIndentedString(add)).append("\n");
    sb.append("    remove: ").append(toIndentedString(remove)).append("\n");
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
