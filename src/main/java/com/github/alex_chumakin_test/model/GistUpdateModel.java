package com.github.alex_chumakin_test.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.Builder;
import lombok.Data;

import java.io.IOException;
import java.util.Map;

@Data
@Builder
public class GistUpdateModel {

    private String description;
    // set value in map as Object to be able pass null
    @JsonSerialize(using = JsonMapSerializer.class)
    private Map<String, Object> files;

    static class JsonMapSerializer extends StdSerializer<Map> {

        JsonMapSerializer() {
            super(Map.class);
        }

        @Override
        public void serialize(Map value, JsonGenerator jGen, SerializerProvider provider) throws IOException {
            if (value == null)
                return;
            var rawMapper = new ObjectMapper();
            var notNullMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

            String mapValue;
            if (value.values().toArray()[0] == null) {
                mapValue = rawMapper.writeValueAsString(value);
            } else {
                mapValue = notNullMapper.writeValueAsString(value);
            }
            jGen.writeRawValue(mapValue);
        }

    }
}
