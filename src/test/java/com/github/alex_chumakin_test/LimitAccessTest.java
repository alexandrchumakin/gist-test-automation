package com.github.alex_chumakin_test;

import com.github.alex_chumakin_test.gist.GistController;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class LimitAccessTest extends AbstractTest {

    private static Stream<Arguments> getGists() {
        return Stream.of(
                Arguments.of("unauthorized call", gistClient.getGists(), gistClient.getGists()),
                Arguments.of("authorized call", gistClient.getGistsAuthorized(), gistClient.getGistsAuthorized())
        );
    }

    @ParameterizedTest
    @MethodSource("getGists")
    void getGistsLimit(@SuppressWarnings("unused") String testName, Response initResponse, Response newResponse) {
        int initLimit = Integer.parseInt(initResponse.getHeader(GistController.RATE_REMAINING_LIMIT_HEADER));
        int newLimit = Integer.parseInt(newResponse.getHeader(GistController.RATE_REMAINING_LIMIT_HEADER));
        int maxLimit = Integer.parseInt(newResponse.getHeader(GistController.RATE_LIMIT_HEADER));

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(newLimit).isEqualTo(initLimit - 1);
            softly.assertThat(newLimit).isLessThan(maxLimit);
        });
    }

}
