package com.github.alex_chumakin_test.gist;

import com.github.alex_chumakin_test.service.BaseService;
import com.github.alex_chumakin_test.data.FileType;
import com.github.alex_chumakin_test.model.CreateGistRequest;
import com.github.alex_chumakin_test.model.GistResponseModel;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import java.util.Collections;
import java.util.Optional;

@Getter
@Slf4j
public class GistClient {

    private BaseService validTokenClient;
    private BaseService invalidTokenClient;
    private BaseService authorizedClient;
    private BaseService anonymousClient;

    public GistClient() {
        validTokenClient = new BaseService().addGistToken(true);
        invalidTokenClient = new BaseService().addGistToken(false);
        authorizedClient = new BaseService().authorize();
        anonymousClient = new BaseService();
    }

    public GistResponseModel createGistWithToken(CreateGistRequest request) {
        return validTokenClient
                .postCall("", Optional.ofNullable(request).orElse(new GistController().generateRequest(null)))
                .extract()
                .response()
                .as(GistResponseModel.class);
    }

    public Response createGistAuthorized() {
        var request = new GistController().generateRequest(Collections.singletonList(FileType.XML));
        return authorizedClient
                .postCall("", request)
                .extract()
                .response();
    }

    public Response createGistUnauthorized() {
        var request = new GistController().generateRequest(Collections.singletonList(FileType.XML));
        return invalidTokenClient
                .postCall("", request)
                .extract()
                .response();
    }

    public Response getGists() {
        return anonymousClient
                .getCall("")
                .extract()
                .response();
    }

    public Response getGistsAuthorized() {
        return authorizedClient
                .getCall("")
                .extract()
                .response();
    }

    public GistResponseModel getGist(String gistId) {
        return anonymousClient
                .getCall(gistId)
                .extract()
                .response()
                .as(GistResponseModel.class);
    }

    public void removeGist(String gistId) {
        log.info("Removing gist with id '{}'.", gistId);
        validTokenClient
                .deleteCall(String.format("/%s", gistId))
                .extract()
                .response()
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

}
