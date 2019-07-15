package com.github.alex_chumakin_test;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.hamcrest.core.IsEqual.equalTo;

class AccessibilityTest extends AbstractTest {

    @Test
    void createGistWithWrongToken() {
        gistClient
                .createGistUnauthorized()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Not Found"));
    }

    @Test
    void createGistWithAuthorizedUser() {
        // gist can be created by authorized user without gist token in request
        gistClient
                .createGistAuthorized()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("message", equalTo("Not Found"));
    }

}
