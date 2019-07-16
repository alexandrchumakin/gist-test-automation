package com.github.alex_chumakin_test.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.Builder;
import lombok.Data;

import java.io.IOException;

@Data
@Builder
public class UpdateContentModel {

    private String content;
    @JsonSerialize(using = JsonStringSerializer.class, as = String.class)
    private String filename;


    static class JsonStringSerializer extends StdSerializer<String> {

        JsonStringSerializer() {
            super(String.class);
        }

        @Override
        public void serialize(String value, JsonGenerator jGen, SerializerProvider provider) throws IOException {
            if (value == null)
                return;
            if (value.equals("null")) {
                jGen.writeNull();
            } else {
                jGen.writeRawValue(value);
            }
        }

    }

}
