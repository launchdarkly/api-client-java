/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 3.8.0
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
import com.launchdarkly.api.model.Member;
import com.launchdarkly.api.model.Statement;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Token
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-28T14:53:40.380Z")
public class Token {
  @SerializedName("_links")
  private Links links = null;

  @SerializedName("_id")
  private String id = null;

  @SerializedName("ownerId")
  private String ownerId = null;

  @SerializedName("memberId")
  private String memberId = null;

  @SerializedName("_member")
  private Member member = null;

  @SerializedName("creationDate")
  private Long creationDate = null;

  @SerializedName("lastModified")
  private Long lastModified = null;

  @SerializedName("lastUsed")
  private Long lastUsed = null;

  @SerializedName("token")
  private String token = null;

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

  public Token links(Links links) {
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

  public Token id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Token ownerId(String ownerId) {
    this.ownerId = ownerId;
    return this;
  }

   /**
   * Get ownerId
   * @return ownerId
  **/
  @ApiModelProperty(value = "")
  public String getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  public Token memberId(String memberId) {
    this.memberId = memberId;
    return this;
  }

   /**
   * Get memberId
   * @return memberId
  **/
  @ApiModelProperty(value = "")
  public String getMemberId() {
    return memberId;
  }

  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

  public Token member(Member member) {
    this.member = member;
    return this;
  }

   /**
   * Get member
   * @return member
  **/
  @ApiModelProperty(value = "")
  public Member getMember() {
    return member;
  }

  public void setMember(Member member) {
    this.member = member;
  }

  public Token creationDate(Long creationDate) {
    this.creationDate = creationDate;
    return this;
  }

   /**
   * A unix epoch time in milliseconds specifying the creation time of this access token.
   * @return creationDate
  **/
  @ApiModelProperty(example = "1443652232590", value = "A unix epoch time in milliseconds specifying the creation time of this access token.")
  public Long getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Long creationDate) {
    this.creationDate = creationDate;
  }

  public Token lastModified(Long lastModified) {
    this.lastModified = lastModified;
    return this;
  }

   /**
   * A unix epoch time in milliseconds specifying the last time this access token was modified.
   * @return lastModified
  **/
  @ApiModelProperty(example = "1469326565348", value = "A unix epoch time in milliseconds specifying the last time this access token was modified.")
  public Long getLastModified() {
    return lastModified;
  }

  public void setLastModified(Long lastModified) {
    this.lastModified = lastModified;
  }

  public Token lastUsed(Long lastUsed) {
    this.lastUsed = lastUsed;
    return this;
  }

   /**
   * A unix epoch time in milliseconds specifying the last time this access token was used to authorize access to the LaunchDarkly REST API.
   * @return lastUsed
  **/
  @ApiModelProperty(example = "1469326565348", value = "A unix epoch time in milliseconds specifying the last time this access token was used to authorize access to the LaunchDarkly REST API.")
  public Long getLastUsed() {
    return lastUsed;
  }

  public void setLastUsed(Long lastUsed) {
    this.lastUsed = lastUsed;
  }

  public Token token(String token) {
    this.token = token;
    return this;
  }

   /**
   * The last 4 digits of the unique secret key for this access token. If creating or resetting the token, this will be the full token secret.
   * @return token
  **/
  @ApiModelProperty(example = "3243", value = "The last 4 digits of the unique secret key for this access token. If creating or resetting the token, this will be the full token secret.")
  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Token name(String name) {
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

  public Token role(String role) {
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

  public Token customRoleIds(List<String> customRoleIds) {
    this.customRoleIds = customRoleIds;
    return this;
  }

  public Token addCustomRoleIdsItem(String customRoleIdsItem) {
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

  public Token inlineRole(List<Statement> inlineRole) {
    this.inlineRole = inlineRole;
    return this;
  }

  public Token addInlineRoleItem(Statement inlineRoleItem) {
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

  public Token serviceToken(Boolean serviceToken) {
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

  public Token defaultApiVersion(Integer defaultApiVersion) {
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
    Token token = (Token) o;
    return Objects.equals(this.links, token.links) &&
        Objects.equals(this.id, token.id) &&
        Objects.equals(this.ownerId, token.ownerId) &&
        Objects.equals(this.memberId, token.memberId) &&
        Objects.equals(this.member, token.member) &&
        Objects.equals(this.creationDate, token.creationDate) &&
        Objects.equals(this.lastModified, token.lastModified) &&
        Objects.equals(this.lastUsed, token.lastUsed) &&
        Objects.equals(this.token, token.token) &&
        Objects.equals(this.name, token.name) &&
        Objects.equals(this.role, token.role) &&
        Objects.equals(this.customRoleIds, token.customRoleIds) &&
        Objects.equals(this.inlineRole, token.inlineRole) &&
        Objects.equals(this.serviceToken, token.serviceToken) &&
        Objects.equals(this.defaultApiVersion, token.defaultApiVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(links, id, ownerId, memberId, member, creationDate, lastModified, lastUsed, token, name, role, customRoleIds, inlineRole, serviceToken, defaultApiVersion);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Token {\n");
    
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    ownerId: ").append(toIndentedString(ownerId)).append("\n");
    sb.append("    memberId: ").append(toIndentedString(memberId)).append("\n");
    sb.append("    member: ").append(toIndentedString(member)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
    sb.append("    lastModified: ").append(toIndentedString(lastModified)).append("\n");
    sb.append("    lastUsed: ").append(toIndentedString(lastUsed)).append("\n");
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
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

