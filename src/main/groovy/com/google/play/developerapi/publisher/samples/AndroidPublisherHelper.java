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

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.repackaged.com.google.common.base.Preconditions;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisherScopes;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * Helper class to initialize the publisher APIs client library.
 * <p>
 * Before making any calls to the API through the client library you need to
 * call the {@link AndroidPublisherHelper#init(String, java.io.File, java.io.File)} method. This will run
 * all precondition checks for for client id and secret setup properly in
 * resources/client_secrets.json and authorize this client against the API.
 * </p>
 */
public class AndroidPublisherHelper {

    private static final Log log = LogFactory.getLog(AndroidPublisherHelper.class);

    static final String MIME_TYPE_APK = "application/vnd.android.package-archive";

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Installed application user ID. */
    private static final String INST_APP_USER_ID = "user";

    /**
     * Global instance of the {@link com.google.api.client.util.store.DataStoreFactory}. The best practice is to
     * make it a single globally shared instance across your application.
     */
    private static FileDataStoreFactory dataStoreFactory;

    /**
     * Authorizes the installed application to access user's protected data.
     *
     * @throws java.io.IOException
     * @throws java.security.GeneralSecurityException
     */
    private static Credential authorizeWithInstalledApplication(File secretFile, File authStore) throws IOException {
        log.info("Authorizing using installed application");

        // load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JSON_FACTORY,
                new FileReader(secretFile));
        // Ensure file has been filled out.
        checkClientSecretsFile(clientSecrets);

        dataStoreFactory = new FileDataStoreFactory(authStore);

        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow
                .Builder(HTTP_TRANSPORT,
                        JSON_FACTORY, clientSecrets,
                        Collections.singleton(AndroidPublisherScopes.ANDROIDPUBLISHER))
                        .setDataStoreFactory(dataStoreFactory).build();
        // authorize
        return new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize(INST_APP_USER_ID);
    }

    /**
     * Ensure the client secrets file has been filled out.
     *
     * @param clientSecrets the GoogleClientSecrets containing data from the
     *            file
     */
    private static void checkClientSecretsFile(GoogleClientSecrets clientSecrets) {
        if (clientSecrets.getDetails().getClientId().startsWith("[[INSERT")
                || clientSecrets.getDetails().getClientSecret().startsWith("[[INSERT")) {
            log.error("Enter Client ID and Secret from "
                    + "APIs console into resources/client_secrets.json.");
            System.exit(1);
        }
    }

    /**
     * Performs all necessary setup steps for running requests against the API.
     *
     * @param applicationName the name of the application: com.example.app
     * @param authStore
     * @return the {@Link AndroidPublisher} service
     * @throws java.security.GeneralSecurityException
     * @throws java.io.IOException
     */
    protected static AndroidPublisher init(String applicationName, File secretFile, File authStore) throws IOException, GeneralSecurityException {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(applicationName),
                "applicationName cannot be null or empty!");

        // Authorization.
        newTrustedTransport();
        Credential credential;
        credential = authorizeWithInstalledApplication(secretFile, authStore);

        // Set up and return API client.
        return new AndroidPublisher.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(applicationName)
                .build();
    }

    private static void newTrustedTransport() throws GeneralSecurityException,
            IOException {
        if (null == HTTP_TRANSPORT) {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        }
    }

}
