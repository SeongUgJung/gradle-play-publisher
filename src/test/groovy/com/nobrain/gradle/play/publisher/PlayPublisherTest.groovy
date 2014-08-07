package com.nobrain.gradle.play.publisher

import com.nobrain.gradle.play.publisher.task.upload.BasicUploadTask
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import static junit.framework.Assert.assertNotNull
import static org.testng.Assert.assertTrue

/**
 * Created by jsuch2362 on 2014. 8. 3..
 */
class PlayPublisherTest {

    @Test
    public void testInitPlayPublisher() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'PlayPublisher'

        project.publisher {
            applicationName "applications"
            packageName "packages"
            apkFile = new File("")
            secretFile = new File("")
            productType "product"
        }


        project.evaluate()

        println project.publisher.applicationName
        println project.publisher.packageName
        println project.publisher.productType

        assertTrue(project.tasks.playPublish instanceof BasicUploadTask);
        assertTrue(project.publisher instanceof  PublisherConfig)
        assertNotNull(project.tasks.playPublish)
        assertNotNull(project.publisher.applicationName)

    }
}
