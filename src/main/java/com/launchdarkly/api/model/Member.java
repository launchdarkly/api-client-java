/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 5.0.2
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
import com.launchdarkly.api.model.MemberLastSeenMetadata;
import com.launchdarkly.api.model.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Member
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-02-22T22:04:52.396Z")



public class Member {
  @SerializedName("_links")
  private Links links = null;

  @SerializedName("_id")
  private String id = null;

  @SerializedName("role")
  private Role role = null;

  @SerializedName("email")
  private String email = null;

  @SerializedName("firstName")
  private String firstName = null;

  @SerializedName("lastName")
  private String lastName = null;

  @SerializedName("_verified")
  private Boolean verified = null;

  @SerializedName("_pendingInvite")
  private Boolean pendingInvite = null;

  @SerializedName("isBeta")
  private Boolean isBeta = null;

  @SerializedName("customRoles")
  private List<String> customRoles = null;

  @SerializedName("_lastSeen")
  private Long lastSeen = null;

  @SerializedName("_lastSeenMetadata")
  private MemberLastSeenMetadata lastSeenMetadata = null;

  public Member links(Links links) {
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

  public Member id(String id) {
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

  public Member role(Role role) {
    this.role = role;
    return this;
  }

   /**
   * Get role
   * @return role
  **/
  @ApiModelProperty(value = "")
  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Member email(String email) {
    this.email = email;
    return this;
  }

   /**
   * Get email
   * @return email
  **/
  @ApiModelProperty(example = "user@launchdarkly.com", value = "")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Member firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

   /**
   * Get firstName
   * @return firstName
  **/
  @ApiModelProperty(example = "Alan", value = "")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public Member lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

   /**
   * Get lastName
   * @return lastName
  **/
  @ApiModelProperty(example = "Turing", value = "")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Member verified(Boolean verified) {
    this.verified = verified;
    return this;
  }

   /**
   * Get verified
   * @return verified
  **/
  @ApiModelProperty(value = "")
  public Boolean isVerified() {
    return verified;
  }

  public void setVerified(Boolean verified) {
    this.verified = verified;
  }

  public Member pendingInvite(Boolean pendingInvite) {
    this.pendingInvite = pendingInvite;
    return this;
  }

   /**
   * Get pendingInvite
   * @return pendingInvite
  **/
  @ApiModelProperty(value = "")
  public Boolean isPendingInvite() {
    return pendingInvite;
  }

  public void setPendingInvite(Boolean pendingInvite) {
    this.pendingInvite = pendingInvite;
  }

  public Member isBeta(Boolean isBeta) {
    this.isBeta = isBeta;
    return this;
  }

   /**
   * Get isBeta
   * @return isBeta
  **/
  @ApiModelProperty(value = "")
  public Boolean isIsBeta() {
    return isBeta;
  }

  public void setIsBeta(Boolean isBeta) {
    this.isBeta = isBeta;
  }

  public Member customRoles(List<String> customRoles) {
    this.customRoles = customRoles;
    return this;
  }

  public Member addCustomRolesItem(String customRolesItem) {
    if (this.customRoles == null) {
      this.customRoles = new ArrayList<String>();
    }
    this.customRoles.add(customRolesItem);
    return this;
  }

   /**
   * Get customRoles
   * @return customRoles
  **/
  @ApiModelProperty(value = "")
  public List<String> getCustomRoles() {
    return customRoles;
  }

  public void setCustomRoles(List<String> customRoles) {
    this.customRoles = customRoles;
  }

  public Member lastSeen(Long lastSeen) {
    this.lastSeen = lastSeen;
    return this;
  }

   /**
   * A unix epoch time in milliseconds specifying the last time this member was active in LaunchDarkly.
   * @return lastSeen
  **/
  @ApiModelProperty(example = "1469326565348", value = "A unix epoch time in milliseconds specifying the last time this member was active in LaunchDarkly.")
  public Long getLastSeen() {
    return lastSeen;
  }

  public void setLastSeen(Long lastSeen) {
    this.lastSeen = lastSeen;
  }

  public Member lastSeenMetadata(MemberLastSeenMetadata lastSeenMetadata) {
    this.lastSeenMetadata = lastSeenMetadata;
    return this;
  }

   /**
   * Get lastSeenMetadata
   * @return lastSeenMetadata
  **/
  @ApiModelProperty(value = "")
  public MemberLastSeenMetadata getLastSeenMetadata() {
    return lastSeenMetadata;
  }

  public void setLastSeenMetadata(MemberLastSeenMetadata lastSeenMetadata) {
    this.lastSeenMetadata = lastSeenMetadata;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Member member = (Member) o;
    return Objects.equals(this.links, member.links) &&
        Objects.equals(this.id, member.id) &&
        Objects.equals(this.role, member.role) &&
        Objects.equals(this.email, member.email) &&
        Objects.equals(this.firstName, member.firstName) &&
        Objects.equals(this.lastName, member.lastName) &&
        Objects.equals(this.verified, member.verified) &&
        Objects.equals(this.pendingInvite, member.pendingInvite) &&
        Objects.equals(this.isBeta, member.isBeta) &&
        Objects.equals(this.customRoles, member.customRoles) &&
        Objects.equals(this.lastSeen, member.lastSeen) &&
        Objects.equals(this.lastSeenMetadata, member.lastSeenMetadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(links, id, role, email, firstName, lastName, verified, pendingInvite, isBeta, customRoles, lastSeen, lastSeenMetadata);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Member {\n");
    
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    verified: ").append(toIndentedString(verified)).append("\n");
    sb.append("    pendingInvite: ").append(toIndentedString(pendingInvite)).append("\n");
    sb.append("    isBeta: ").append(toIndentedString(isBeta)).append("\n");
    sb.append("    customRoles: ").append(toIndentedString(customRoles)).append("\n");
    sb.append("    lastSeen: ").append(toIndentedString(lastSeen)).append("\n");
    sb.append("    lastSeenMetadata: ").append(toIndentedString(lastSeenMetadata)).append("\n");
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

