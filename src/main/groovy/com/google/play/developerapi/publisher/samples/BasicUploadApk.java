/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.play.developerapi.publisher.samples;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.repackaged.com.google.common.base.Preconditions;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Apks.Upload;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Commit;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Insert;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Tracks.Update;
import com.google.api.services.androidpublisher.model.Apk;
import com.google.api.services.androidpublisher.model.AppEdit;
import com.google.api.services.androidpublisher.model.Track;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * Uploads an apk to the alpha track.
 */
public class BasicUploadApk {

    private static final Log log = LogFactory.getLog(BasicUploadApk.class);

    public void upload(String applicationName, String packageName, File apkFiles, File secretFile, File mappingFile, String productType) throws IOException, GeneralSecurityException {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(packageName),
                "ApplicationConfig.PACKAGE_NAME cannot be null or empty!");

        // Create the API service.
        AndroidPublisher service = AndroidPublisherHelper.init(applicationName, secretFile);
        final Edits edits = service.edits();

        // Create a new edit to make changes to your listing.
        Insert editRequest = edits.insert(packageName, null);
        AppEdit edit = editRequest.execute();
        final String editId = edit.getId();
        log.info(String.format("Created edit with id: %s", editId));

        // Upload new apk to developer console
        final AbstractInputStreamContent apkFile =
                new FileContent(AndroidPublisherHelper.MIME_TYPE_APK, apkFiles);
        Upload uploadRequest = edits
                .apks()
                .upload(packageName,
                        editId,
                        apkFile);
        Apk apk = uploadRequest.execute();
        log.info(String.format("Version code %d has been uploaded",
                apk.getVersionCode()));

        // Assign apk to alpha track.
        List<Integer> apkVersionCodes = new ArrayList<>();
        apkVersionCodes.add(apk.getVersionCode());
        Update updateTrackRequest = edits
                .tracks()
                .update(packageName,
                        editId,
                        productType,
                        new Track().setVersionCodes(apkVersionCodes));
        Track updatedTrack = updateTrackRequest.execute();
        log.info(String.format("Track %s has been updated.", updatedTrack.getTrack()));

        // Upload Proguard mapping.txt if available
        if (mappingFile != null && mappingFile.exists()) {
            FileContent fileStream = new FileContent("application/octet-stream", mappingFile);
            edits.deobfuscationfiles().upload(packageName, editId, apk.getVersionCode(), "proguard", fileStream).execute();
        }

        // Commit changes for edit.
        Commit commitRequest = edits.commit(packageName, editId);
        AppEdit appEdit = commitRequest.execute();
        log.info(String.format("App edit with id %s has been comitted", appEdit.getId()));
    }
}
