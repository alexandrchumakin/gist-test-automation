package com.github.alex_chumakin_test.service.filters;

import com.github.alex_chumakin_test.gist.GistController;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.filter.log.LogDetail;
import io.restassured.internal.print.ResponsePrinter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomResponseLoggingFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpecification,
            FilterableResponseSpecification responseSpecification, FilterContext ctx) {
        LogDetail logDetail;
        Response response = ctx.next(requestSpecification, responseSpecification);
        var body = response.getBody();

        // do not log too big response body
        if (body != null && !body.asString().isEmpty() && body.asString().length() < 4000) {
            log.info(response.getStatusLine());
            logDetail = LogDetail.BODY;
        } else {
            logDetail = LogDetail.STATUS;
        }

        if (response.getHeaders().hasHeaderWithName(GistController.RATE_REMAINING_LIMIT_HEADER)) {
            log.info(response.getHeaders().get(GistController.RATE_REMAINING_LIMIT_HEADER).toString());
        }

        ResponsePrinter.print(response, response, System.out, logDetail, true);

        return response;
    }

}
