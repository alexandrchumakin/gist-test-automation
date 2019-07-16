package com.github.alex_chumakin_test;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.hamcrest.core.IsEqual.equalTo;

class AccessibilityTest extends AbstractTest {

    @Test
    void createGistWithWrongToken() {
        gistClient
                .createGistWithWrongToken()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Not Found"));
    }

    @Test
    void createGistWithUnauthorizedUser() {
        gistClient
                .createGistUnauthorized()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("message", equalTo("Requires authentication"));
    }

    @Test
    void starGistWithoutAuthorization() {
        var createdGist = gistClient.createGistWithToken(null);
        createdGists.add(createdGist.getId());
        gistClient.starGistUnauthorized(createdGist.getId())
                  .then()
                  .assertThat()
                  .statusCode(HttpStatus.SC_NOT_FOUND);
    }

}
