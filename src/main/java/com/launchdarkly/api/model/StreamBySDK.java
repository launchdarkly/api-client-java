/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 3.3.1
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
import com.launchdarkly.api.model.StreamBySDKLinks;
import com.launchdarkly.api.model.StreamBySDKLinksMetadata;
import com.launchdarkly.api.model.StreamUsageSeries;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * StreamBySDK
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-05T11:10:44.621Z")
public class StreamBySDK {
  @SerializedName("_links")
  private StreamBySDKLinks links = null;

  @SerializedName("metadata")
  private List<StreamBySDKLinksMetadata> metadata = null;

  @SerializedName("series")
  private List<StreamUsageSeries> series = null;

  public StreamBySDK links(StreamBySDKLinks links) {
    this.links = links;
    return this;
  }

   /**
   * Get links
   * @return links
  **/
  @ApiModelProperty(value = "")
  public StreamBySDKLinks getLinks() {
    return links;
  }

  public void setLinks(StreamBySDKLinks links) {
    this.links = links;
  }

  public StreamBySDK metadata(List<StreamBySDKLinksMetadata> metadata) {
    this.metadata = metadata;
    return this;
  }

  public StreamBySDK addMetadataItem(StreamBySDKLinksMetadata metadataItem) {
    if (this.metadata == null) {
      this.metadata = new ArrayList<StreamBySDKLinksMetadata>();
    }
    this.metadata.add(metadataItem);
    return this;
  }

   /**
   * Get metadata
   * @return metadata
  **/
  @ApiModelProperty(value = "")
  public List<StreamBySDKLinksMetadata> getMetadata() {
    return metadata;
  }

  public void setMetadata(List<StreamBySDKLinksMetadata> metadata) {
    this.metadata = metadata;
  }

  public StreamBySDK series(List<StreamUsageSeries> series) {
    this.series = series;
    return this;
  }

  public StreamBySDK addSeriesItem(StreamUsageSeries seriesItem) {
    if (this.series == null) {
      this.series = new ArrayList<StreamUsageSeries>();
    }
    this.series.add(seriesItem);
    return this;
  }

   /**
   * Get series
   * @return series
  **/
  @ApiModelProperty(value = "")
  public List<StreamUsageSeries> getSeries() {
    return series;
  }

  public void setSeries(List<StreamUsageSeries> series) {
    this.series = series;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StreamBySDK streamBySDK = (StreamBySDK) o;
    return Objects.equals(this.links, streamBySDK.links) &&
        Objects.equals(this.metadata, streamBySDK.metadata) &&
        Objects.equals(this.series, streamBySDK.series);
  }

  @Override
  public int hashCode() {
    return Objects.hash(links, metadata, series);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StreamBySDK {\n");
    
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    series: ").append(toIndentedString(series)).append("\n");
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

