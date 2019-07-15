package com.github.alex_chumakin_test;

import com.github.alex_chumakin_test.data.FileType;
import com.github.alex_chumakin_test.gist.GistController;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class CreateGistTest extends AbstractTest {

    private static Stream<Arguments> gistFiles() {
        return Stream.of(
                Arguments.of("one file", Collections.singletonList(FileType.PYTHON)),
                Arguments.of("multiple files", Arrays.asList(FileType.PYTHON, FileType.PLAIN_TEXT, FileType.XML)));
    }

    @ParameterizedTest
    @MethodSource("gistFiles")
    void createGist(@SuppressWarnings("unused") String testName, List<FileType> files) {
        var request = new GistController().generateRequest(files);
        var response = gistClient.createGistWithToken(request);
        createdGists.add(response.getId());

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getUrl())
                  .isEqualTo(String.format("%s/%s", gistClient.getValidTokenClient().getBasePath(), response.getId()));
            softly.assertThat(response.getIsPublic()).isEqualTo(request.getIsPublic());
            softly.assertThat(response.getDescription()).isEqualTo(request.getDescription());
            softly.assertThat(response.getFiles().keySet()).isEqualTo(request.getFiles().keySet());
        });
    }

}
