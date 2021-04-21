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
import com.launchdarkly.api.model.Policy;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * RelayProxyConfigBody
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-04-21T16:19:08.589Z")



public class RelayProxyConfigBody {
  @SerializedName("name")
  private String name = null;

  @SerializedName("policy")
  private List<Policy> policy = null;

  public RelayProxyConfigBody name(String name) {
    this.name = name;
    return this;
  }

   /**
   * A human-friendly name for the relay proxy configuration
   * @return name
  **/
  @ApiModelProperty(example = "My relay proxy config", value = "A human-friendly name for the relay proxy configuration")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RelayProxyConfigBody policy(List<Policy> policy) {
    this.policy = policy;
    return this;
  }

  public RelayProxyConfigBody addPolicyItem(Policy policyItem) {
    if (this.policy == null) {
      this.policy = new ArrayList<Policy>();
    }
    this.policy.add(policyItem);
    return this;
  }

   /**
   * Get policy
   * @return policy
  **/
  @ApiModelProperty(value = "")
  public List<Policy> getPolicy() {
    return policy;
  }

  public void setPolicy(List<Policy> policy) {
    this.policy = policy;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RelayProxyConfigBody relayProxyConfigBody = (RelayProxyConfigBody) o;
    return Objects.equals(this.name, relayProxyConfigBody.name) &&
        Objects.equals(this.policy, relayProxyConfigBody.policy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, policy);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RelayProxyConfigBody {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    policy: ").append(toIndentedString(policy)).append("\n");
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

