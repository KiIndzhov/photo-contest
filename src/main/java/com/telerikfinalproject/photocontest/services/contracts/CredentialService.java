package com.telerikfinalproject.photocontest.services.contracts;

import com.telerikfinalproject.photocontest.models.Credential;

import java.util.Optional;

public interface CredentialService {

    boolean usernameExists(String username);

    Credential createCredential(Credential credential);

    Credential getUserCredentials(String username, String password);

    Optional<Credential> getCredentialByUsername(String username);

}
