package com.github.alex_chumakin_test.service.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomRequestLoggingFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpecification,
            FilterableResponseSpecification responseSpecification, FilterContext ctx) {
        String body = requestSpecification.getBody();
        log.info("[{}] {}", requestSpecification.getMethod(), requestSpecification.getURI());

        if (requestSpecification.getHeaders().hasHeaderWithName("Authorization")) {
            log.info(requestSpecification.getHeaders().get("Authorization").toString());
        }

        // do not log too big request body
        if (body != null && body.length() < 1000) {
            log.info("Body: \n{}", body);
        }

        return ctx.next(requestSpecification, responseSpecification);
    }

}
