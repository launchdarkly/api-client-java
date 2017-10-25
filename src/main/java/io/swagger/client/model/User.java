/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 2.0.0
 * Contact: support@launchdarkly.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.client.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * User
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2017-10-25T12:45:40.194-07:00")
public class User {
  @SerializedName("lastPing")
  private String lastPing = null;

  @SerializedName("environmentId")
  private String environmentId = null;

  @SerializedName("ownerId")
  private String ownerId = null;

  @SerializedName("user")
  private Object user = null;

  @SerializedName("avatar")
  private String avatar = null;

  public User lastPing(String lastPing) {
    this.lastPing = lastPing;
    return this;
  }

   /**
   * Get lastPing
   * @return lastPing
  **/
  @ApiModelProperty(value = "")
  public String getLastPing() {
    return lastPing;
  }

  public void setLastPing(String lastPing) {
    this.lastPing = lastPing;
  }

  public User environmentId(String environmentId) {
    this.environmentId = environmentId;
    return this;
  }

   /**
   * Get environmentId
   * @return environmentId
  **/
  @ApiModelProperty(value = "")
  public String getEnvironmentId() {
    return environmentId;
  }

  public void setEnvironmentId(String environmentId) {
    this.environmentId = environmentId;
  }

  public User ownerId(String ownerId) {
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

  public User user(Object user) {
    this.user = user;
    return this;
  }

   /**
   * Get user
   * @return user
  **/
  @ApiModelProperty(example = "{\"key\":\"a00bea\",\"name\":\"Bob Loblaw\",\"custom\":{\"company\":\"example.com\"}}", value = "")
  public Object getUser() {
    return user;
  }

  public void setUser(Object user) {
    this.user = user;
  }

  public User avatar(String avatar) {
    this.avatar = avatar;
    return this;
  }

   /**
   * Get avatar
   * @return avatar
  **/
  @ApiModelProperty(value = "")
  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(this.lastPing, user.lastPing) &&
        Objects.equals(this.environmentId, user.environmentId) &&
        Objects.equals(this.ownerId, user.ownerId) &&
        Objects.equals(this.user, user.user) &&
        Objects.equals(this.avatar, user.avatar);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lastPing, environmentId, ownerId, user, avatar);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    
    sb.append("    lastPing: ").append(toIndentedString(lastPing)).append("\n");
    sb.append("    environmentId: ").append(toIndentedString(environmentId)).append("\n");
    sb.append("    ownerId: ").append(toIndentedString(ownerId)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    avatar: ").append(toIndentedString(avatar)).append("\n");
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

