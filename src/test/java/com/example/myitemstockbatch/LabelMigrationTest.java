package com.example.myitemstockbatch;

import org.junit.jupiter.api.Test;
import org.kohsuke.github.*;

import java.io.IOException;
import java.util.Arrays;


public class LabelMigrationTest {
    @Test
    public void migrateLabel() {
        try {
            GitHub github = new GitHubBuilder().withOAuthToken("your_github_token").build();
            GHRepository source = github.getRepository("codybuilder-dev/my-item-stock");
            GHRepository target = github.getRepository("codybuilder-dev/my-item-stock-batch");

            PagedIterable<GHLabel> labels = source.listLabels();
            Arrays.stream(labels.toArray()).forEach(ghLabel -> {
                try {
                    target.createLabel(ghLabel.getName(), ghLabel.getColor());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
