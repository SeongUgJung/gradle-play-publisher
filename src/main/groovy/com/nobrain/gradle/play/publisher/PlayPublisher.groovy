package com.nobrain.gradle.play.publisher

import com.nobrain.gradle.play.publisher.task.clean.CleanOAuthTask
import com.nobrain.gradle.play.publisher.task.upload.BasicUploadTask
import com.nobrain.gradle.play.publisher.util.StringUtils
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by jsuch2362 on 2014. 8. 3..
 */
class PlayPublisher implements Plugin<Project> {


    @Override
    void apply(Project project) {

        def extension = project.extensions.create('publisher', PublisherConfig)
        def cleanTask = project.task("cleanAuth", type: CleanOAuthTask)
        cleanTask.doFirst {
            cleanTask.delete '.store'
        }

        def publishTask = project.task("playPublish", type: BasicUploadTask)
        publishTask.doFirst {

            if (StringUtils.isEmpty(project.publisher.applicationName)) {
                throw new GradleException('Application Name must be not EMPTY!!!!')
            }

            if (StringUtils.isEmpty(project.publisher.packageName)) {
                throw new GradleException('Package Name must be not EMPTY!!!!')
            }

            if (project.publisher.apkFile == null) {
                throw new GradleException('APK File must be not NULL!!!!')
            }

            if (StringUtils.isEmpty(project.publisher.productType)) {
                throw new GradleException('Product Type must be not EMPTY!!!!')
            }

            if (!StringUtils.equals(project.publisher.productType, "production")
                 && !StringUtils.equals(project.publisher.productType, "alpha")
                 && !StringUtils.equals(project.publisher.productType, "beta")) {
                throw new GradleException('Product Type must be one of [production, alpha, beta]')
            }

            if (project.publisher.authStore != null) {
                println('Auth Store Path will be deprecated')
                publishTask.authStore = new File('.store')
            }

            publishTask.applicationName project.publisher.applicationName
            publishTask.packageName project.publisher.packageName
            publishTask.apkFile project.publisher.apkFile
            publishTask.secretFile project.publisher.secretFile
            publishTask.productType project.publisher.productType
        }

    }


}
