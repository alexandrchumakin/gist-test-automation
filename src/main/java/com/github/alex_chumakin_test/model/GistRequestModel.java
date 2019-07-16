package com.github.alex_chumakin_test.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class GistRequestModel {

    private String description;
    @JsonProperty("public")
    private Boolean isPublic;
    private Map<String, ContentModel> files;

}
