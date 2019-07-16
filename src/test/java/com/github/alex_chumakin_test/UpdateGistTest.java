package com.github.alex_chumakin_test;

import com.github.alex_chumakin_test.data.ContentController;
import com.github.alex_chumakin_test.data.FileType;
import com.github.alex_chumakin_test.gist.GistController;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UpdateGistTest extends AbstractTest {

    @Test
    void updateGistDescription() {
        var createdGist = gistClient.createGistWithToken(null);
        createdGists.add(createdGist.getId());

        String newDescription = "updated description";
        var updateGistRequest = new GistController().updateGist(null, newDescription);
        var updateResponse = gistClient.updateGist(createdGist.getId(), updateGistRequest);

        assertEquals(createdGist.toBuilder().description(newDescription).build(), updateResponse);
    }

    @Test
    void updateGistFiles() {
        var createdGist = gistClient.createGistWithToken(null);
        createdGists.add(createdGist.getId());

        var newFiles = Arrays.asList(FileType.XML, FileType.PLAIN_TEXT);
        var updateGistRequest = new GistController().updateGist(newFiles, null);
        var updateResponse = gistClient.updateGist(createdGist.getId(), updateGistRequest);

        var initFiles = createdGist.getFiles().keySet();
        var updatedFiles = newFiles
                .stream().map(x -> new ContentController(x).getFileName()).collect(Collectors.toSet());
        updatedFiles.addAll(initFiles);

        assertAll(
                () -> assertEquals(updatedFiles, updateResponse.getFiles().keySet()),
                () -> assertEquals(createdGist.getDescription(), updateResponse.getDescription())
        );
    }

    @Test
    @Disabled
    /*
    Sent a problem description to GitHub support:
    based on the documentation https://developer.github.com/v3/gists/#edit-a-gist
    > filename	string	The new name for this file. To delete a file, set the value of the filename to null.

    but when passing `"filename": null` it returns 422 error:
        "Invalid request.\n\nFor 'properties/filename', nil is not a string."
     */
    void removeGistFile() {
        var createdGist = gistClient.createGistWithToken(null);
        createdGists.add(createdGist.getId());

        var updateGistRequest = new GistController().updateGistWithRemovingFile(FileType.PYTHON);
        var updateResponse = gistClient.updateGist(createdGist.getId(), updateGistRequest);

        assertAll(
                () -> assertNull(updateResponse.getFiles()),
                () -> assertEquals(createdGist.getDescription(), updateResponse.getDescription())
        );
    }

}
