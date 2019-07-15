package com.github.alex_chumakin_test;

import com.github.alex_chumakin_test.gist.GistClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractTest {

    static GistClient gistClient;
    static List<String> createdGists;

    @BeforeAll
    public static void setup() {
        gistClient = new GistClient();
        createdGists = new ArrayList<>();
    }

    @AfterAll
    public static void tearDown() {
        Optional.ofNullable(createdGists).orElse(new ArrayList<>()).forEach(gistId -> gistClient.removeGist(gistId));
    }

}
