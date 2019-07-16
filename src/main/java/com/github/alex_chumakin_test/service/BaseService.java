package com.github.alex_chumakin_test.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.alex_chumakin_test.data.ConfigReader;
import com.github.alex_chumakin_test.service.filters.CustomRequestLoggingFilter;
import com.github.alex_chumakin_test.service.filters.CustomResponseLoggingFilter;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import org.apache.http.HttpHeaders;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Getter
public class BaseService {

    private String basePath;
    private String token;
    private RequestSpecification reqSpec;
    private ConfigReader configReader;

    public BaseService() {
        configReader = new ConfigReader();
        this.basePath = configReader.getValueByKey("basePath");
        prepareSpec();
    }

    public BaseService authorize(String token) {
        reqSpec.header(HttpHeaders.AUTHORIZATION, configReader.getBaseAuth(token));
        return this;
    }

    public BaseService addGistToken(boolean isValidToken) {
        String validToken = configReader.getValueByKey("gistToken");
        String invalidToken = configReader.getValueByKey("readPackagesToken");
        token = isValidToken ? validToken : invalidToken;
        reqSpec.header(HttpHeaders.AUTHORIZATION, String.format("token %s", token));
        return this;
    }

    public ValidatableResponse postCall(String path, Object body) {
        return reqSpec
                .when()
                .body(body)
                .post(path)
                .then();
    }

    public ValidatableResponse patchCall(String path, Object body) {
        return reqSpec
                .when()
                .body(body)
                .patch(path)
                .then();
    }

    public ValidatableResponse putCall(String path) {
        return reqSpec
                .when()
                .put(path)
                .then();
    }

    public ValidatableResponse getCall(String path) {
        return reqSpec
                .when()
                .get(path)
                .then();
    }

    public ValidatableResponse deleteCall(String path) {
        return reqSpec
                .when()
                .delete(path)
                .then();
    }

    private ObjectMapper buildJacksonMapper() {
        return new ObjectMapper()
                .findAndRegisterModules()
                .setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY)
                .enable(SerializationFeature.INDENT_OUTPUT);
    }

    private void prepareSpec() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.config = RestAssured.config().objectMapperConfig(
                new ObjectMapperConfig().jackson2ObjectMapperFactory((clazz, charset) -> buildJacksonMapper())
        );
        RestAssured.defaultParser = Parser.JSON;
        var initSpec = new RequestSpecBuilder()
                .setBaseUri(basePath)
                .build();
        reqSpec = RestAssured
                .given(initSpec)
                .filters(Arrays.asList(new CustomRequestLoggingFilter(), new CustomResponseLoggingFilter()))
                .request()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

}
