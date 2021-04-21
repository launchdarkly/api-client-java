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
import com.launchdarkly.api.model.DependentFlag;
import com.launchdarkly.api.model.DependentFlagsLinks;
import com.launchdarkly.api.model.Site;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * DependentFlagsByEnvironment
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-04-21T16:19:08.589Z")



public class DependentFlagsByEnvironment {
  @SerializedName("items")
  private List<DependentFlag> items = null;

  @SerializedName("_links")
  private DependentFlagsLinks links = null;

  @SerializedName("_site")
  private Site site = null;

  public DependentFlagsByEnvironment items(List<DependentFlag> items) {
    this.items = items;
    return this;
  }

  public DependentFlagsByEnvironment addItemsItem(DependentFlag itemsItem) {
    if (this.items == null) {
      this.items = new ArrayList<DependentFlag>();
    }
    this.items.add(itemsItem);
    return this;
  }

   /**
   * Get items
   * @return items
  **/
  @ApiModelProperty(value = "")
  public List<DependentFlag> getItems() {
    return items;
  }

  public void setItems(List<DependentFlag> items) {
    this.items = items;
  }

  public DependentFlagsByEnvironment links(DependentFlagsLinks links) {
    this.links = links;
    return this;
  }

   /**
   * Get links
   * @return links
  **/
  @ApiModelProperty(value = "")
  public DependentFlagsLinks getLinks() {
    return links;
  }

  public void setLinks(DependentFlagsLinks links) {
    this.links = links;
  }

  public DependentFlagsByEnvironment site(Site site) {
    this.site = site;
    return this;
  }

   /**
   * Get site
   * @return site
  **/
  @ApiModelProperty(value = "")
  public Site getSite() {
    return site;
  }

  public void setSite(Site site) {
    this.site = site;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DependentFlagsByEnvironment dependentFlagsByEnvironment = (DependentFlagsByEnvironment) o;
    return Objects.equals(this.items, dependentFlagsByEnvironment.items) &&
        Objects.equals(this.links, dependentFlagsByEnvironment.links) &&
        Objects.equals(this.site, dependentFlagsByEnvironment.site);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items, links, site);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DependentFlagsByEnvironment {\n");
    
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    site: ").append(toIndentedString(site)).append("\n");
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

