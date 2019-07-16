package com.github.alex_chumakin_test;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

class StarUnstarGistTest extends AbstractTest {

    @Test
    void starCreatedGist() {
        var createdGist = gistClient.createGistWithToken(null);
        createdGists.add(createdGist.getId());
        gistClient.starGist(createdGist.getId())
                  .then()
                  .assertThat()
                  .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    void starNotExistingGist() {
        gistClient.starGist("wrongGistId")
                  .then()
                  .assertThat()
                  .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    void unstarStarredGist() {
        var createdGist = gistClient.createGistWithToken(null);
        createdGists.add(createdGist.getId());
        gistClient.starGist(createdGist.getId());
        gistClient.unstarGist(createdGist.getId())
                  .then()
                  .assertThat()
                  .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    /*
    unstar returns 204 for any existing gist even if gist is not starred:
    https://developer.github.com/v3/gists/#unstar-a-gist
     */
    void unstarNotStarredGist() {
        var createdGist = gistClient.createGistWithToken(null);
        createdGists.add(createdGist.getId());
        gistClient.unstarGist(createdGist.getId())
                  .then()
                  .assertThat()
                  .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    void checkStarredGist() {
        var createdGist = gistClient.createGistWithToken(null);
        createdGists.add(createdGist.getId());
        gistClient.starGist(createdGist.getId());
        gistClient.checkGistIsStarred(createdGist.getId())
                  .then()
                  .assertThat()
                  .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    void checkUnstarredGist() {
        var createdGist = gistClient.createGistWithToken(null);
        createdGists.add(createdGist.getId());
        gistClient.starGist(createdGist.getId());
        gistClient.unstarGist(createdGist.getId());
        gistClient.checkGistIsStarred(createdGist.getId())
                  .then()
                  .assertThat()
                  .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    void checkStarForNotExistingGist() {
        gistClient.checkGistIsStarred("wrongGistId")
                  .then()
                  .assertThat()
                  .statusCode(HttpStatus.SC_NOT_FOUND);
    }

}
