package com.github.alex_chumakin_test.gist;

import com.github.alex_chumakin_test.data.ContentController;
import com.github.alex_chumakin_test.data.FileType;
import com.github.alex_chumakin_test.model.ContentModel;
import com.github.alex_chumakin_test.model.GistRequestModel;
import com.github.alex_chumakin_test.model.GistUpdateModel;
import com.github.alex_chumakin_test.model.UpdateContentModel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GistController {

    public static final String RATE_LIMIT_HEADER = "X-RateLimit-Limit";
    public static final String RATE_REMAINING_LIMIT_HEADER = "X-RateLimit-Remaining";

    public GistRequestModel generateRequest(List<FileType> types) {
        Map<Object, ContentModel> files = new HashMap<>();
        Optional.ofNullable(types).orElse(Collections.singletonList(FileType.PYTHON)).forEach(x -> {
            var controller = new ContentController(x);
            files.put(controller.getFileName(), ContentModel.builder().content(controller.getContent()).build());
        });
        return GistRequestModel
                .builder()
                .description("Hello World Examples")
                .isPublic(true)
                .files(files)
                .build();
    }

    public GistUpdateModel updateGist(List<FileType> types, String description) {
        Map<Object, UpdateContentModel> files = null;

        if (types != null) {
            Map<Object, UpdateContentModel> updateFiles = new HashMap<>();
            types.forEach(x -> {
                var controller = new ContentController(x);
                updateFiles.put(controller.getFileName(),
                                UpdateContentModel.builder().content(controller.getContent()).build());
            });
            files = updateFiles;
        }

        return GistUpdateModel
                .builder()
                .description(description)
                .files(files)
                .build();
    }

    public GistUpdateModel updateGistWithRemovingFile(FileType fileType) {
        var controller = new ContentController(fileType);
        var files = new HashMap<Object, UpdateContentModel>() {{
            put(controller.getFileName(),
                UpdateContentModel
                        .builder()
                        .content(controller.getContent())
                        .filename("null")
                        .build());
        }};

        return GistUpdateModel
                .builder()
                .files(files)
                .build();
    }

}
