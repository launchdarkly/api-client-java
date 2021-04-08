/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 5.0.3
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
import com.launchdarkly.api.model.ClientSideAvailability;
import com.launchdarkly.api.model.CustomProperty;
import com.launchdarkly.api.model.Defaults;
import com.launchdarkly.api.model.FeatureFlagConfig;
import com.launchdarkly.api.model.Links;
import com.launchdarkly.api.model.Member;
import com.launchdarkly.api.model.Variation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FeatureFlag
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-04-08T01:21:45.269Z")



public class FeatureFlag {
  @SerializedName("key")
  private String key = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("description")
  private String description = null;

  @SerializedName("kind")
  private String kind = null;

  @SerializedName("creationDate")
  private Long creationDate = null;

  @SerializedName("includeInSnippet")
  private Boolean includeInSnippet = null;

  @SerializedName("temporary")
  private Boolean temporary = null;

  @SerializedName("maintainerId")
  private String maintainerId = null;

  @SerializedName("tags")
  private List<String> tags = null;

  @SerializedName("variations")
  private List<Variation> variations = null;

  @SerializedName("goalIds")
  private List<String> goalIds = null;

  @SerializedName("_version")
  private Integer version = null;

  @SerializedName("customProperties")
  private Map<String, CustomProperty> customProperties = null;

  @SerializedName("_links")
  private Links links = null;

  @SerializedName("_maintainer")
  private Member maintainer = null;

  @SerializedName("environments")
  private Map<String, FeatureFlagConfig> environments = null;

  @SerializedName("archivedDate")
  private Long archivedDate = null;

  @SerializedName("archived")
  private Boolean archived = null;

  @SerializedName("clientSideAvailability")
  private ClientSideAvailability clientSideAvailability = null;

  @SerializedName("defaults")
  private Defaults defaults = null;

  public FeatureFlag key(String key) {
    this.key = key;
    return this;
  }

   /**
   * Get key
   * @return key
  **/
  @ApiModelProperty(example = "test-feature", value = "")
  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public FeatureFlag name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Name of the feature flag.
   * @return name
  **/
  @ApiModelProperty(example = "Test Feature", value = "Name of the feature flag.")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public FeatureFlag description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Description of the feature flag.
   * @return description
  **/
  @ApiModelProperty(example = "This flag controls whether test feature is turned on or not.", value = "Description of the feature flag.")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public FeatureFlag kind(String kind) {
    this.kind = kind;
    return this;
  }

   /**
   * Whether the feature flag is a boolean flag or multivariate.
   * @return kind
  **/
  @ApiModelProperty(example = "boolean", value = "Whether the feature flag is a boolean flag or multivariate.")
  public String getKind() {
    return kind;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }

  public FeatureFlag creationDate(Long creationDate) {
    this.creationDate = creationDate;
    return this;
  }

   /**
   * A unix epoch time in milliseconds specifying the creation time of this flag.
   * @return creationDate
  **/
  @ApiModelProperty(example = "1443652232590", value = "A unix epoch time in milliseconds specifying the creation time of this flag.")
  public Long getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Long creationDate) {
    this.creationDate = creationDate;
  }

  public FeatureFlag includeInSnippet(Boolean includeInSnippet) {
    this.includeInSnippet = includeInSnippet;
    return this;
  }

   /**
   * Get includeInSnippet
   * @return includeInSnippet
  **/
  @ApiModelProperty(example = "false", value = "")
  public Boolean isIncludeInSnippet() {
    return includeInSnippet;
  }

  public void setIncludeInSnippet(Boolean includeInSnippet) {
    this.includeInSnippet = includeInSnippet;
  }

  public FeatureFlag temporary(Boolean temporary) {
    this.temporary = temporary;
    return this;
  }

   /**
   * Whether or not this flag is temporary.
   * @return temporary
  **/
  @ApiModelProperty(example = "false", value = "Whether or not this flag is temporary.")
  public Boolean isTemporary() {
    return temporary;
  }

  public void setTemporary(Boolean temporary) {
    this.temporary = temporary;
  }

  public FeatureFlag maintainerId(String maintainerId) {
    this.maintainerId = maintainerId;
    return this;
  }

   /**
   * The ID of the member that should maintain this flag.
   * @return maintainerId
  **/
  @ApiModelProperty(example = "561c579cd8fd5c2704000001", value = "The ID of the member that should maintain this flag.")
  public String getMaintainerId() {
    return maintainerId;
  }

  public void setMaintainerId(String maintainerId) {
    this.maintainerId = maintainerId;
  }

  public FeatureFlag tags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public FeatureFlag addTagsItem(String tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<String>();
    }
    this.tags.add(tagsItem);
    return this;
  }

   /**
   * An array of tags for this feature flag.
   * @return tags
  **/
  @ApiModelProperty(example = "[]", value = "An array of tags for this feature flag.")
  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public FeatureFlag variations(List<Variation> variations) {
    this.variations = variations;
    return this;
  }

  public FeatureFlag addVariationsItem(Variation variationsItem) {
    if (this.variations == null) {
      this.variations = new ArrayList<Variation>();
    }
    this.variations.add(variationsItem);
    return this;
  }

   /**
   * The variations for this feature flag.
   * @return variations
  **/
  @ApiModelProperty(example = "[{\"value\":\"a\"},{\"value\":\"b\"}]", value = "The variations for this feature flag.")
  public List<Variation> getVariations() {
    return variations;
  }

  public void setVariations(List<Variation> variations) {
    this.variations = variations;
  }

  public FeatureFlag goalIds(List<String> goalIds) {
    this.goalIds = goalIds;
    return this;
  }

  public FeatureFlag addGoalIdsItem(String goalIdsItem) {
    if (this.goalIds == null) {
      this.goalIds = new ArrayList<String>();
    }
    this.goalIds.add(goalIdsItem);
    return this;
  }

   /**
   * An array goals from all environments associated with this feature flag
   * @return goalIds
  **/
  @ApiModelProperty(example = "[\"d7239405bd89c930e885aa76\",\"405bc930e88d7239d895aa76\"]", value = "An array goals from all environments associated with this feature flag")
  public List<String> getGoalIds() {
    return goalIds;
  }

  public void setGoalIds(List<String> goalIds) {
    this.goalIds = goalIds;
  }

  public FeatureFlag version(Integer version) {
    this.version = version;
    return this;
  }

   /**
   * Get version
   * @return version
  **/
  @ApiModelProperty(example = "23", value = "")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public FeatureFlag customProperties(Map<String, CustomProperty> customProperties) {
    this.customProperties = customProperties;
    return this;
  }

  public FeatureFlag putCustomPropertiesItem(String key, CustomProperty customPropertiesItem) {
    if (this.customProperties == null) {
      this.customProperties = new HashMap<String, CustomProperty>();
    }
    this.customProperties.put(key, customPropertiesItem);
    return this;
  }

   /**
   * A mapping of keys to CustomProperty entries.
   * @return customProperties
  **/
  @ApiModelProperty(example = "{\"bugs\":{\"name\":\"Issue tracker ids\",\"value\":[\"123\",\"456\"]},\"deprecated\":{\"name\":\"Deprecated Date\",\"value\":[]}}", value = "A mapping of keys to CustomProperty entries.")
  public Map<String, CustomProperty> getCustomProperties() {
    return customProperties;
  }

  public void setCustomProperties(Map<String, CustomProperty> customProperties) {
    this.customProperties = customProperties;
  }

  public FeatureFlag links(Links links) {
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

  public FeatureFlag maintainer(Member maintainer) {
    this.maintainer = maintainer;
    return this;
  }

   /**
   * Get maintainer
   * @return maintainer
  **/
  @ApiModelProperty(value = "")
  public Member getMaintainer() {
    return maintainer;
  }

  public void setMaintainer(Member maintainer) {
    this.maintainer = maintainer;
  }

  public FeatureFlag environments(Map<String, FeatureFlagConfig> environments) {
    this.environments = environments;
    return this;
  }

  public FeatureFlag putEnvironmentsItem(String key, FeatureFlagConfig environmentsItem) {
    if (this.environments == null) {
      this.environments = new HashMap<String, FeatureFlagConfig>();
    }
    this.environments.put(key, environmentsItem);
    return this;
  }

   /**
   * Get environments
   * @return environments
  **/
  @ApiModelProperty(value = "")
  public Map<String, FeatureFlagConfig> getEnvironments() {
    return environments;
  }

  public void setEnvironments(Map<String, FeatureFlagConfig> environments) {
    this.environments = environments;
  }

  public FeatureFlag archivedDate(Long archivedDate) {
    this.archivedDate = archivedDate;
    return this;
  }

   /**
   * A unix epoch time in milliseconds specifying the archived time of this flag.
   * @return archivedDate
  **/
  @ApiModelProperty(example = "1443652232590", value = "A unix epoch time in milliseconds specifying the archived time of this flag.")
  public Long getArchivedDate() {
    return archivedDate;
  }

  public void setArchivedDate(Long archivedDate) {
    this.archivedDate = archivedDate;
  }

  public FeatureFlag archived(Boolean archived) {
    this.archived = archived;
    return this;
  }

   /**
   * Whether or not this flag is archived.
   * @return archived
  **/
  @ApiModelProperty(example = "false", value = "Whether or not this flag is archived.")
  public Boolean isArchived() {
    return archived;
  }

  public void setArchived(Boolean archived) {
    this.archived = archived;
  }

  public FeatureFlag clientSideAvailability(ClientSideAvailability clientSideAvailability) {
    this.clientSideAvailability = clientSideAvailability;
    return this;
  }

   /**
   * Get clientSideAvailability
   * @return clientSideAvailability
  **/
  @ApiModelProperty(value = "")
  public ClientSideAvailability getClientSideAvailability() {
    return clientSideAvailability;
  }

  public void setClientSideAvailability(ClientSideAvailability clientSideAvailability) {
    this.clientSideAvailability = clientSideAvailability;
  }

  public FeatureFlag defaults(Defaults defaults) {
    this.defaults = defaults;
    return this;
  }

   /**
   * Get defaults
   * @return defaults
  **/
  @ApiModelProperty(value = "")
  public Defaults getDefaults() {
    return defaults;
  }

  public void setDefaults(Defaults defaults) {
    this.defaults = defaults;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FeatureFlag featureFlag = (FeatureFlag) o;
    return Objects.equals(this.key, featureFlag.key) &&
        Objects.equals(this.name, featureFlag.name) &&
        Objects.equals(this.description, featureFlag.description) &&
        Objects.equals(this.kind, featureFlag.kind) &&
        Objects.equals(this.creationDate, featureFlag.creationDate) &&
        Objects.equals(this.includeInSnippet, featureFlag.includeInSnippet) &&
        Objects.equals(this.temporary, featureFlag.temporary) &&
        Objects.equals(this.maintainerId, featureFlag.maintainerId) &&
        Objects.equals(this.tags, featureFlag.tags) &&
        Objects.equals(this.variations, featureFlag.variations) &&
        Objects.equals(this.goalIds, featureFlag.goalIds) &&
        Objects.equals(this.version, featureFlag.version) &&
        Objects.equals(this.customProperties, featureFlag.customProperties) &&
        Objects.equals(this.links, featureFlag.links) &&
        Objects.equals(this.maintainer, featureFlag.maintainer) &&
        Objects.equals(this.environments, featureFlag.environments) &&
        Objects.equals(this.archivedDate, featureFlag.archivedDate) &&
        Objects.equals(this.archived, featureFlag.archived) &&
        Objects.equals(this.clientSideAvailability, featureFlag.clientSideAvailability) &&
        Objects.equals(this.defaults, featureFlag.defaults);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, name, description, kind, creationDate, includeInSnippet, temporary, maintainerId, tags, variations, goalIds, version, customProperties, links, maintainer, environments, archivedDate, archived, clientSideAvailability, defaults);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FeatureFlag {\n");
    
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    kind: ").append(toIndentedString(kind)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
    sb.append("    includeInSnippet: ").append(toIndentedString(includeInSnippet)).append("\n");
    sb.append("    temporary: ").append(toIndentedString(temporary)).append("\n");
    sb.append("    maintainerId: ").append(toIndentedString(maintainerId)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    variations: ").append(toIndentedString(variations)).append("\n");
    sb.append("    goalIds: ").append(toIndentedString(goalIds)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    customProperties: ").append(toIndentedString(customProperties)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    maintainer: ").append(toIndentedString(maintainer)).append("\n");
    sb.append("    environments: ").append(toIndentedString(environments)).append("\n");
    sb.append("    archivedDate: ").append(toIndentedString(archivedDate)).append("\n");
    sb.append("    archived: ").append(toIndentedString(archived)).append("\n");
    sb.append("    clientSideAvailability: ").append(toIndentedString(clientSideAvailability)).append("\n");
    sb.append("    defaults: ").append(toIndentedString(defaults)).append("\n");
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

