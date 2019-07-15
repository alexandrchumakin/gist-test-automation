package com.github.alex_chumakin_test.gist;

import com.github.alex_chumakin_test.data.ContentController;
import com.github.alex_chumakin_test.data.FileType;
import com.github.alex_chumakin_test.model.ContentModel;
import com.github.alex_chumakin_test.model.CreateGistRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GistController {

    public static final String RATE_LIMIT_HEADER = "X-RateLimit-Limit";
    public static final String RATE_RAMAINING_LIMIT_HEADER = "X-RateLimit-Remaining";

    public CreateGistRequest generateRequest(List<FileType> types) {
        Map<String, ContentModel> files = new HashMap<>();
        Optional.ofNullable(types).orElse(Collections.singletonList(FileType.PYTHON)).forEach(x -> {
            var controller = new ContentController(x);
            files.put(controller.getFileName(), ContentModel.builder().content(controller.getContent()).build());
        });
        return CreateGistRequest
                .builder()
                .description("Hello World Examples")
                .isPublic(true)
                .files(files)
                .build();
    }

}
