package com.github.alex_chumakin_test.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class GistUpdateModel {

    private String description;
    private Map<Object, UpdateContentModel> files;

}
