package com.github.alex_chumakin_test.data;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Properties;

@Slf4j
public class ConfigReader {

    public String getValueByKey(String key) {
        Properties prop = new Properties();
        try {
            prop.load(new InputStreamReader(
                    new ClassPathResource("config.properties").getInputStream(), StandardCharsets.UTF_8));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String value = prop.getProperty(key);
        return Optional.ofNullable(value).orElseGet(() -> {
            log.error("No property with name '{}'.", key);
            return "";
        });
    }

    public String getBaseAuth(){
        return String.format("Basic %1$s", new String(Base64.encodeBase64(
                String.format("%1$s:%2$s", getValueByKey("userName"), getValueByKey("password")).getBytes()
        )));
    }

}
