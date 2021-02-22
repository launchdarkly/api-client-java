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
import com.launchdarkly.api.model.IntegrationSubscription;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Integrations
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-02-22T22:04:52.396Z")



public class Integrations {
  @SerializedName("_links")
  private Object links = null;

  @SerializedName("items")
  private List<IntegrationSubscription> items = null;

  public Integrations links(Object links) {
    this.links = links;
    return this;
  }

   /**
   * A mapping of integration types to their respective API endpoints.
   * @return links
  **/
  @ApiModelProperty(example = "{\"appdynamics\":{\"href\":\"/api/v2/integrations/appdynamics\",\"type\":\"application/json\"},\"splunk\":{\"href\":\"/api/v2/integrations/splunk\",\"type\":\"application/json\"}}", value = "A mapping of integration types to their respective API endpoints.")
  public Object getLinks() {
    return links;
  }

  public void setLinks(Object links) {
    this.links = links;
  }

  public Integrations items(List<IntegrationSubscription> items) {
    this.items = items;
    return this;
  }

  public Integrations addItemsItem(IntegrationSubscription itemsItem) {
    if (this.items == null) {
      this.items = new ArrayList<IntegrationSubscription>();
    }
    this.items.add(itemsItem);
    return this;
  }

   /**
   * Get items
   * @return items
  **/
  @ApiModelProperty(value = "")
  public List<IntegrationSubscription> getItems() {
    return items;
  }

  public void setItems(List<IntegrationSubscription> items) {
    this.items = items;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Integrations integrations = (Integrations) o;
    return Objects.equals(this.links, integrations.links) &&
        Objects.equals(this.items, integrations.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(links, items);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Integrations {\n");
    
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
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

