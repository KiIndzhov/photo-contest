package com.telerikfinalproject.photocontest.repositories.contracts;

import com.telerikfinalproject.photocontest.models.Credential;

import java.util.Optional;

public interface CredentialRepository {

    boolean usernameExists(String username);

    void createCredential(Credential credential);

    Credential getUserCredentials(String username, String password);

    Optional<Credential> getCredentialByUsername(String username);

}
