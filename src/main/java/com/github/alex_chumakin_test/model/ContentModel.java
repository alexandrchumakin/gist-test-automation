package com.github.alex_chumakin_test.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Value
@Builder(builderClassName = "ContentModelBuilder", toBuilder = true)
@JsonDeserialize(builder = ContentModel.ContentModelBuilder.class)
public class ContentModel {

    private String content;

    @JsonPOJOBuilder(withPrefix = "")
    @SuppressWarnings({ "unused", "WeakerAccess" })
    public static final class ContentModelBuilder {
    }

}
