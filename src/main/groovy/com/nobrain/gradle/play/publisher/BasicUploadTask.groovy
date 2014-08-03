package com.nobrain.gradle.play.publisher

import com.google.play.developerapi.publisher.samples.BasicUploadApk
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by jsuch2362 on 2014. 8. 3..
 */
class BasicUploadTask extends DefaultTask{

    def applicationName// = project.publisher.applicationName
    def packageName// = project.publisher.packageName
    File apkFile// = project.publisher.apkFile
    File secretFile// = project.publisher.secretFile
    File authStore
    def productType// = project.publisher.productType

    @TaskAction
    def playUpload() {

        BasicUploadApk basicUploadApk = new BasicUploadApk();
        basicUploadApk.upload(applicationName, packageName, apkFile, secretFile, authStore, productType)
    }

}
