package seedu.address.model.google;

import com.google.photos.library.v1.PhotosLibraryClient;

//@@author chivent
public class GoogleClientInstance {

    private PhotosLibraryClient photosLibraryClient;
    private String user;

    public GoogleClientInstance(PhotosLibraryClient client, String email) {
        photosLibraryClient = client;
        user = email;
    }

    public String identifyUser() {
        return user;
    }

}