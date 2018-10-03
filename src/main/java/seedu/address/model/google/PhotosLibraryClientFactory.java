package seedu.address.model.google;

// @author chivent (kaile)
// don't @ me for my messy style I'll do clean up at the end. feedback is welcome though

// creation of authentication instance (O-Auth)

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;
import com.google.auth.Credentials;
import com.google.auth.oauth2.UserCredentials;
import com.google.common.collect.ImmutableList;
import com.google.photos.library.v1.PhotosLibraryClient;
import com.google.photos.library.v1.PhotosLibrarySettings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.List;

//@@author chivent
//TODO: credit --> https://github.com/google/java-photoslibrary/tree/master/sample
//TODO: Remove comments in later future
//TODO: Store files elsewhere [TBD]

public class PhotosLibraryClientFactory {


    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPE_LIST =
            ImmutableList.of(
                    "https://www.googleapis.com/auth/photoslibrary",
                    "https://www.googleapis.com/auth/photoslibrary.sharing",
                    "email");

    //for storing serializable data in key-value form
    private static final File DATA_STORE = new File(PhotosLibraryClientFactory.class.getResource("/").getPath(), "user_credentials");
    private static File credential_file = new File(PhotosLibraryClientFactory.class.getClassLoader().getResource("client_credentials.json").getPath());

    private PhotosLibraryClientFactory() {
    }

    public static GoogleClientInstance createClient() throws IOException, GeneralSecurityException {

        DataStoreFactory dataStoreFactory = new FileDataStoreFactory(DATA_STORE);

        // load designated client secret/id
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(
                        JSON_FACTORY, new InputStreamReader(new FileInputStream(credential_file)));

        String clientSecret = clientSecrets.getDetails().getClientSecret();
        String clientId = clientSecrets.getDetails().getClientId();

        //google standard authorization flow
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets,
                        SCOPE_LIST).setDataStoreFactory(
                        dataStoreFactory).build();

        // Credential is a google construct that wraps the access token and helps you to refresh periodically
        // AuthorizationCodeInstalledApp is another google standard that helps persist user end credentials
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");

        //UserCredentials is a specific credential type that stores user specific credentials
        UserCredentials userCredentials = UserCredentials.newBuilder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRefreshToken(credential.getRefreshToken())
                .build();

        return new GoogleClientInstance(createPhotosLibraryClient(userCredentials), getUserEmail(credential));
    }

    private static PhotosLibraryClient createPhotosLibraryClient(Credentials credentials) throws IOException {
        PhotosLibrarySettings libSettings =
                PhotosLibrarySettings.newBuilder()
                        .setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
        return PhotosLibraryClient.initialize(libSettings);
    }

    private static String getUserEmail(Credential credential) throws GeneralSecurityException, IOException {
        Plus plus = new Plus.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName("Piconso").build();

        List<Person.Emails> emails = plus.people().get("me").execute().getEmails();

        return emails.get(0).getValue();
    }

}


