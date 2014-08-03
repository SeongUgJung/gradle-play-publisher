package com.nobrain.gradle.play.publisher

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by jsuch2362 on 2014. 8. 3..
 */
class PlayPublisher implements Plugin<Project> {


    @Override
    void apply(Project project) {

        def extension = project.extensions.create('publisher', PublisherConfig)
        def task = project.task("playPublish", type: BasicUploadTask)
        task.doFirst {
            task.applicationName = project.publisher.applicationName
            task.packageName = project.publisher.packageName
            task.apkFile = project.publisher.apkFile
            task.secretFile = project.publisher.secretFile
            task.productType = project.publisher.productType
            task.authStore = project.publisher.authStore
        }

    }
}
