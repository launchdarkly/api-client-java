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
import io.swagger.client.model.FeatureFlagConfigFallthrough;
import io.swagger.client.model.Rule;
import io.swagger.client.model.Target;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * FeatureFlagConfig
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2017-10-25T12:45:40.194-07:00")
public class FeatureFlagConfig {
  @SerializedName("on")
  private Boolean on = null;

  @SerializedName("archived")
  private Boolean archived = null;

  @SerializedName("salt")
  private String salt = null;

  @SerializedName("sel")
  private String sel = null;

  @SerializedName("lastModified")
  private Integer lastModified = null;

  @SerializedName("version")
  private Integer version = null;

  @SerializedName("targets")
  private List<Target> targets = null;

  @SerializedName("rules")
  private List<Rule> rules = null;

  @SerializedName("fallthrough")
  private FeatureFlagConfigFallthrough fallthrough = null;

  public FeatureFlagConfig on(Boolean on) {
    this.on = on;
    return this;
  }

   /**
   * Get on
   * @return on
  **/
  @ApiModelProperty(value = "")
  public Boolean getOn() {
    return on;
  }

  public void setOn(Boolean on) {
    this.on = on;
  }

  public FeatureFlagConfig archived(Boolean archived) {
    this.archived = archived;
    return this;
  }

   /**
   * Get archived
   * @return archived
  **/
  @ApiModelProperty(value = "")
  public Boolean getArchived() {
    return archived;
  }

  public void setArchived(Boolean archived) {
    this.archived = archived;
  }

  public FeatureFlagConfig salt(String salt) {
    this.salt = salt;
    return this;
  }

   /**
   * Get salt
   * @return salt
  **/
  @ApiModelProperty(value = "")
  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public FeatureFlagConfig sel(String sel) {
    this.sel = sel;
    return this;
  }

   /**
   * Get sel
   * @return sel
  **/
  @ApiModelProperty(value = "")
  public String getSel() {
    return sel;
  }

  public void setSel(String sel) {
    this.sel = sel;
  }

  public FeatureFlagConfig lastModified(Integer lastModified) {
    this.lastModified = lastModified;
    return this;
  }

   /**
   * Get lastModified
   * @return lastModified
  **/
  @ApiModelProperty(value = "")
  public Integer getLastModified() {
    return lastModified;
  }

  public void setLastModified(Integer lastModified) {
    this.lastModified = lastModified;
  }

  public FeatureFlagConfig version(Integer version) {
    this.version = version;
    return this;
  }

   /**
   * Get version
   * @return version
  **/
  @ApiModelProperty(value = "")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public FeatureFlagConfig targets(List<Target> targets) {
    this.targets = targets;
    return this;
  }

  public FeatureFlagConfig addTargetsItem(Target targetsItem) {
    if (this.targets == null) {
      this.targets = new ArrayList<Target>();
    }
    this.targets.add(targetsItem);
    return this;
  }

   /**
   * Get targets
   * @return targets
  **/
  @ApiModelProperty(value = "")
  public List<Target> getTargets() {
    return targets;
  }

  public void setTargets(List<Target> targets) {
    this.targets = targets;
  }

  public FeatureFlagConfig rules(List<Rule> rules) {
    this.rules = rules;
    return this;
  }

  public FeatureFlagConfig addRulesItem(Rule rulesItem) {
    if (this.rules == null) {
      this.rules = new ArrayList<Rule>();
    }
    this.rules.add(rulesItem);
    return this;
  }

   /**
   * Get rules
   * @return rules
  **/
  @ApiModelProperty(value = "")
  public List<Rule> getRules() {
    return rules;
  }

  public void setRules(List<Rule> rules) {
    this.rules = rules;
  }

  public FeatureFlagConfig fallthrough(FeatureFlagConfigFallthrough fallthrough) {
    this.fallthrough = fallthrough;
    return this;
  }

   /**
   * Get fallthrough
   * @return fallthrough
  **/
  @ApiModelProperty(value = "")
  public FeatureFlagConfigFallthrough getFallthrough() {
    return fallthrough;
  }

  public void setFallthrough(FeatureFlagConfigFallthrough fallthrough) {
    this.fallthrough = fallthrough;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FeatureFlagConfig featureFlagConfig = (FeatureFlagConfig) o;
    return Objects.equals(this.on, featureFlagConfig.on) &&
        Objects.equals(this.archived, featureFlagConfig.archived) &&
        Objects.equals(this.salt, featureFlagConfig.salt) &&
        Objects.equals(this.sel, featureFlagConfig.sel) &&
        Objects.equals(this.lastModified, featureFlagConfig.lastModified) &&
        Objects.equals(this.version, featureFlagConfig.version) &&
        Objects.equals(this.targets, featureFlagConfig.targets) &&
        Objects.equals(this.rules, featureFlagConfig.rules) &&
        Objects.equals(this.fallthrough, featureFlagConfig.fallthrough);
  }

  @Override
  public int hashCode() {
    return Objects.hash(on, archived, salt, sel, lastModified, version, targets, rules, fallthrough);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FeatureFlagConfig {\n");
    
    sb.append("    on: ").append(toIndentedString(on)).append("\n");
    sb.append("    archived: ").append(toIndentedString(archived)).append("\n");
    sb.append("    salt: ").append(toIndentedString(salt)).append("\n");
    sb.append("    sel: ").append(toIndentedString(sel)).append("\n");
    sb.append("    lastModified: ").append(toIndentedString(lastModified)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    targets: ").append(toIndentedString(targets)).append("\n");
    sb.append("    rules: ").append(toIndentedString(rules)).append("\n");
    sb.append("    fallthrough: ").append(toIndentedString(fallthrough)).append("\n");
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

