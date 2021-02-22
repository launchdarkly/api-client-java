/*
 * LaunchDarkly REST API
 * Build custom integrations with the LaunchDarkly REST API
 *
 * OpenAPI spec version: 4.0.0
 * Contact: support@launchdarkly.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.launchdarkly.api.model;

import java.util.Objects;
import java.util.Arrays;
import io.swagger.annotations.ApiModel;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * | Name     | Description | | --------:| ----------- | | pending  | the feature flag approval request has not been reviewed yet | | approved | the feature flag approval request has been approved and can now be applied | | declined | the feature flag approval request has been declined and cannot be applied | 
 */
@JsonAdapter(FeatureFlagApprovalRequestReviewStatus.Adapter.class)
public enum FeatureFlagApprovalRequestReviewStatus {
  
  PENDING("pending"),
  
  APPROVED("approved"),
  
  DECLINED("declined");

  private String value;

  FeatureFlagApprovalRequestReviewStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  public static FeatureFlagApprovalRequestReviewStatus fromValue(String text) {
    for (FeatureFlagApprovalRequestReviewStatus b : FeatureFlagApprovalRequestReviewStatus.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }

  public static class Adapter extends TypeAdapter<FeatureFlagApprovalRequestReviewStatus> {
    @Override
    public void write(final JsonWriter jsonWriter, final FeatureFlagApprovalRequestReviewStatus enumeration) throws IOException {
      jsonWriter.value(enumeration.getValue());
    }

    @Override
    public FeatureFlagApprovalRequestReviewStatus read(final JsonReader jsonReader) throws IOException {
      String value = jsonReader.nextString();
      return FeatureFlagApprovalRequestReviewStatus.fromValue(String.valueOf(value));
    }
  }
}
