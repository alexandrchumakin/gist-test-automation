package com.github.alex_chumakin_test;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetGistTest extends AbstractTest {

    @Test
    void getCreatedGist() {
        var createdGist = gistClient.createGistWithToken(null);
        createdGists.add(createdGist.getId());
        var getResponse = gistClient.getGist(createdGist.getId());

        assertEquals(createdGist, getResponse);
    }

    @Test
    void getRemovedGistReturnsError() {
        var createdGist = gistClient.createGistWithToken(null);
        gistClient.removeGist(createdGist.getId());
        gistClient
                .getAnonymousClient()
                .getCall(createdGist.getId())
                .extract()
                .response()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

}
