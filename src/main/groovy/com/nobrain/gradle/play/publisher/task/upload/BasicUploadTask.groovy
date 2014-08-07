package com.nobrain.gradle.play.publisher.task.upload

import com.google.play.developerapi.publisher.samples.BasicUploadApk
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by jsuch2362 on 2014. 8. 3..
 */
class BasicUploadTask extends DefaultTask{

    def applicationName
    def packageName
    File apkFile
    File secretFile
    File authStore
    def productType

    @TaskAction
    def playUpload() {

        BasicUploadApk basicUploadApk = new BasicUploadApk();
        basicUploadApk.upload(applicationName, packageName, apkFile, secretFile, authStore, productType)
    }
}
