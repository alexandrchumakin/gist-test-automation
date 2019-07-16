package com.github.alex_chumakin_test.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder(builderClassName = "GistResponseModelBuilder", toBuilder = true)
@JsonDeserialize(builder = GistResponseModel.GistResponseModelBuilder.class)
/*
 * Custom representation of Gist model with some set of fields for verification.
 * All fields in Json that is not defined in this class will be ignored during unmarshalling.
 * Customized POJO builder is needed to deserialize json correctly.
 */
public class GistResponseModel {

    private String url;
    private String id;
    private Map<String, Object> files;
    @JsonProperty("public")
    private Boolean isPublic;
    private String description;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    @SuppressWarnings({ "unused", "WeakerAccess" })
    public static final class GistResponseModelBuilder {

        @JsonProperty("public")
        private Boolean isPublic;

    }

}
