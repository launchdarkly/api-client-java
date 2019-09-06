/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 2.0.15
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
import com.launchdarkly.api.model.Role;
import com.launchdarkly.api.model.Statements;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * MembersBody
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-09-06T20:16:25.962Z")
public class MembersBody {
  @SerializedName("email")
  private String email = null;

  @SerializedName("firstName")
  private String firstName = null;

  @SerializedName("lastName")
  private String lastName = null;

  @SerializedName("role")
  private Role role = null;

  @SerializedName("customRoles")
  private List<String> customRoles = null;

  @SerializedName("inlineRole")
  private Statements inlineRole = null;

  public MembersBody email(String email) {
    this.email = email;
    return this;
  }

   /**
   * Get email
   * @return email
  **/
  @ApiModelProperty(example = "exampleuser@email.com", required = true, value = "")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public MembersBody firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

   /**
   * Get firstName
   * @return firstName
  **/
  @ApiModelProperty(example = "Bob", value = "")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public MembersBody lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

   /**
   * Get lastName
   * @return lastName
  **/
  @ApiModelProperty(example = "Loblaw", value = "")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public MembersBody role(Role role) {
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

  public MembersBody customRoles(List<String> customRoles) {
    this.customRoles = customRoles;
    return this;
  }

  public MembersBody addCustomRolesItem(String customRolesItem) {
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

  public MembersBody inlineRole(Statements inlineRole) {
    this.inlineRole = inlineRole;
    return this;
  }

   /**
   * Get inlineRole
   * @return inlineRole
  **/
  @ApiModelProperty(value = "")
  public Statements getInlineRole() {
    return inlineRole;
  }

  public void setInlineRole(Statements inlineRole) {
    this.inlineRole = inlineRole;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MembersBody membersBody = (MembersBody) o;
    return Objects.equals(this.email, membersBody.email) &&
        Objects.equals(this.firstName, membersBody.firstName) &&
        Objects.equals(this.lastName, membersBody.lastName) &&
        Objects.equals(this.role, membersBody.role) &&
        Objects.equals(this.customRoles, membersBody.customRoles) &&
        Objects.equals(this.inlineRole, membersBody.inlineRole);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, firstName, lastName, role, customRoles, inlineRole);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MembersBody {\n");
    
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    customRoles: ").append(toIndentedString(customRoles)).append("\n");
    sb.append("    inlineRole: ").append(toIndentedString(inlineRole)).append("\n");
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

