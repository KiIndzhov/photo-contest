package com.telerikfinalproject.photocontest.services.utils;

import com.telerikfinalproject.photocontest.exceptions.UnauthorisedException;
import com.telerikfinalproject.photocontest.models.Credential;
import com.telerikfinalproject.photocontest.models.utils.UserRole;
import org.springframework.stereotype.Component;

@Component
public class ValidationHelper {

    public static final String AUTHENTICATION_ERROR_MESSAGE = "User is not authorised";

    public static void verifyUser(Credential credential) {
        if (credential == null) {
            throw new UnauthorisedException(AUTHENTICATION_ERROR_MESSAGE);
        }
    }

    public static void verifyOrganizer(Credential credential) {
        if (credential == null) {
            throw new UnauthorisedException(AUTHENTICATION_ERROR_MESSAGE);
        }
        if (!credential.getUserRole().equals(UserRole.ORGANIZER)) {
            throw new UnauthorisedException(AUTHENTICATION_ERROR_MESSAGE);
        }
    }

    public static void verifyPhotoJunkie(Credential credential) {
        if (credential == null) {
            throw new UnauthorisedException(AUTHENTICATION_ERROR_MESSAGE);
        }
        if (!credential.getUserRole().equals(UserRole.PHOTO_JUNKIE)) {
            throw new UnauthorisedException(AUTHENTICATION_ERROR_MESSAGE);
        }
    }
}
