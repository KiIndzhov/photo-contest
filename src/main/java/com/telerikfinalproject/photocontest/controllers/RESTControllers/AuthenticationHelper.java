package com.telerikfinalproject.photocontest.controllers.RESTControllers;

import com.telerikfinalproject.photocontest.exceptions.EntityNotFoundException;
import com.telerikfinalproject.photocontest.exceptions.UnauthorisedException;
import com.telerikfinalproject.photocontest.models.Credential;
import com.telerikfinalproject.photocontest.models.User;
import com.telerikfinalproject.photocontest.services.contracts.CredentialService;
import com.telerikfinalproject.photocontest.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHelper {

    public static final String AUTHORIZATION_USERNAME = "username";
    public static final String AUTHORIZATION_PASSWORD = "password";

    private final CredentialService credentialService;
    private final UserService userService;

    @Autowired
    public AuthenticationHelper(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    public Credential getUserCredentials(HttpHeaders headers) throws EntityNotFoundException {
        if (!headers.containsKey(AUTHORIZATION_USERNAME) || !headers.containsKey(AUTHORIZATION_PASSWORD)) {
            throw new UnauthorisedException("The request status requires authentication");
        }

        String username = headers.getFirst(AUTHORIZATION_USERNAME);
        String password = headers.getFirst(AUTHORIZATION_PASSWORD);
        return credentialService.getUserCredentials(username, password);

    }

    public User getUserByCredentials(Credential credential) {
        return userService.getUserByUsername(credential.getUsername());
    }
}
