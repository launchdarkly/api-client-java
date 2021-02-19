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
import com.launchdarkly.api.model.Statement;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TokenBody
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-02-19T19:16:12.901Z")
public class TokenBody {
  @SerializedName("name")
  private String name = null;

  @SerializedName("role")
  private String role = null;

  @SerializedName("customRoleIds")
  private List<String> customRoleIds = null;

  @SerializedName("inlineRole")
  private List<Statement> inlineRole = null;

  @SerializedName("serviceToken")
  private Boolean serviceToken = null;

  @SerializedName("defaultApiVersion")
  private Integer defaultApiVersion = null;

  public TokenBody name(String name) {
    this.name = name;
    return this;
  }

   /**
   * A human-friendly name for the access token
   * @return name
  **/
  @ApiModelProperty(example = "My access token", value = "A human-friendly name for the access token")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TokenBody role(String role) {
    this.role = role;
    return this;
  }

   /**
   * The name of a built-in role for the token
   * @return role
  **/
  @ApiModelProperty(example = "writer", value = "The name of a built-in role for the token")
  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public TokenBody customRoleIds(List<String> customRoleIds) {
    this.customRoleIds = customRoleIds;
    return this;
  }

  public TokenBody addCustomRoleIdsItem(String customRoleIdsItem) {
    if (this.customRoleIds == null) {
      this.customRoleIds = new ArrayList<String>();
    }
    this.customRoleIds.add(customRoleIdsItem);
    return this;
  }

   /**
   * A list of custom role IDs to use as access limits for the access token
   * @return customRoleIds
  **/
  @ApiModelProperty(value = "A list of custom role IDs to use as access limits for the access token")
  public List<String> getCustomRoleIds() {
    return customRoleIds;
  }

  public void setCustomRoleIds(List<String> customRoleIds) {
    this.customRoleIds = customRoleIds;
  }

  public TokenBody inlineRole(List<Statement> inlineRole) {
    this.inlineRole = inlineRole;
    return this;
  }

  public TokenBody addInlineRoleItem(Statement inlineRoleItem) {
    if (this.inlineRole == null) {
      this.inlineRole = new ArrayList<Statement>();
    }
    this.inlineRole.add(inlineRoleItem);
    return this;
  }

   /**
   * Get inlineRole
   * @return inlineRole
  **/
  @ApiModelProperty(value = "")
  public List<Statement> getInlineRole() {
    return inlineRole;
  }

  public void setInlineRole(List<Statement> inlineRole) {
    this.inlineRole = inlineRole;
  }

  public TokenBody serviceToken(Boolean serviceToken) {
    this.serviceToken = serviceToken;
    return this;
  }

   /**
   * Whether the token will be a service token https://docs.launchdarkly.com/home/account-security/api-access-tokens#service-tokens
   * @return serviceToken
  **/
  @ApiModelProperty(value = "Whether the token will be a service token https://docs.launchdarkly.com/home/account-security/api-access-tokens#service-tokens")
  public Boolean isServiceToken() {
    return serviceToken;
  }

  public void setServiceToken(Boolean serviceToken) {
    this.serviceToken = serviceToken;
  }

  public TokenBody defaultApiVersion(Integer defaultApiVersion) {
    this.defaultApiVersion = defaultApiVersion;
    return this;
  }

   /**
   * The default API version for this token
   * @return defaultApiVersion
  **/
  @ApiModelProperty(value = "The default API version for this token")
  public Integer getDefaultApiVersion() {
    return defaultApiVersion;
  }

  public void setDefaultApiVersion(Integer defaultApiVersion) {
    this.defaultApiVersion = defaultApiVersion;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TokenBody tokenBody = (TokenBody) o;
    return Objects.equals(this.name, tokenBody.name) &&
        Objects.equals(this.role, tokenBody.role) &&
        Objects.equals(this.customRoleIds, tokenBody.customRoleIds) &&
        Objects.equals(this.inlineRole, tokenBody.inlineRole) &&
        Objects.equals(this.serviceToken, tokenBody.serviceToken) &&
        Objects.equals(this.defaultApiVersion, tokenBody.defaultApiVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, role, customRoleIds, inlineRole, serviceToken, defaultApiVersion);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TokenBody {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    customRoleIds: ").append(toIndentedString(customRoleIds)).append("\n");
    sb.append("    inlineRole: ").append(toIndentedString(inlineRole)).append("\n");
    sb.append("    serviceToken: ").append(toIndentedString(serviceToken)).append("\n");
    sb.append("    defaultApiVersion: ").append(toIndentedString(defaultApiVersion)).append("\n");
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

