package com.nobrain.gradle.play.publisher

import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import static org.testng.Assert.assertTrue

/**
 * Created by jsuch2362 on 2014. 8. 6..
 */
class CleanOAuthTaskTest {

    @Test
    public void testDelete() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'PlayPublisher'

        project.publisher {
            applicationName "application"
            packageName "package"
            apkFile = new File("")
            secretFile = new File("")
            productType "product"
        }

        project.evaluate()

        assertTrue(project.tasks.cleanAuth instanceof Delete)

    }
}
