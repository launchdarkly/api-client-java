/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 5.3.0
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
 * DestinationAmazonKinesis
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-07-14T18:58:43.744Z")



public class DestinationAmazonKinesis {
  @SerializedName("region")
  private String region = null;

  @SerializedName("roleArn")
  private String roleArn = null;

  @SerializedName("streamName")
  private String streamName = null;

  public DestinationAmazonKinesis region(String region) {
    this.region = region;
    return this;
  }

   /**
   * Get region
   * @return region
  **/
  @ApiModelProperty(example = "us-east-1", value = "")
  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public DestinationAmazonKinesis roleArn(String roleArn) {
    this.roleArn = roleArn;
    return this;
  }

   /**
   * Get roleArn
   * @return roleArn
  **/
  @ApiModelProperty(example = "arn:aws:iam::123456789012:role/marketingadmin", value = "")
  public String getRoleArn() {
    return roleArn;
  }

  public void setRoleArn(String roleArn) {
    this.roleArn = roleArn;
  }

  public DestinationAmazonKinesis streamName(String streamName) {
    this.streamName = streamName;
    return this;
  }

   /**
   * Get streamName
   * @return streamName
  **/
  @ApiModelProperty(example = "cat-stream", value = "")
  public String getStreamName() {
    return streamName;
  }

  public void setStreamName(String streamName) {
    this.streamName = streamName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DestinationAmazonKinesis destinationAmazonKinesis = (DestinationAmazonKinesis) o;
    return Objects.equals(this.region, destinationAmazonKinesis.region) &&
        Objects.equals(this.roleArn, destinationAmazonKinesis.roleArn) &&
        Objects.equals(this.streamName, destinationAmazonKinesis.streamName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(region, roleArn, streamName);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DestinationAmazonKinesis {\n");
    
    sb.append("    region: ").append(toIndentedString(region)).append("\n");
    sb.append("    roleArn: ").append(toIndentedString(roleArn)).append("\n");
    sb.append("    streamName: ").append(toIndentedString(streamName)).append("\n");
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

