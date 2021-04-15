package com.telerikfinalproject.photocontest.services;

import com.telerikfinalproject.photocontest.exceptions.DuplicateEntityException;
import com.telerikfinalproject.photocontest.models.Credential;
import com.telerikfinalproject.photocontest.repositories.contracts.CredentialRepository;
import com.telerikfinalproject.photocontest.services.contracts.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CredentialServiceImpl implements CredentialService {

    private final CredentialRepository credentialRepository;

    @Autowired
    public CredentialServiceImpl(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    @Override
    public boolean usernameExists(String username) {
        return credentialRepository.usernameExists(username);
    }

    @Override
    public Credential createCredential(Credential credential) {
        if (usernameExists(credential.getUsername())) {
            throw new DuplicateEntityException("User", "username", credential.getUsername());
        }
        credentialRepository.createCredential(credential);
        return credential;
    }

    @Override
    public Credential getUserCredentials(String username, String password) {
        return credentialRepository.getUserCredentials(username, password);
    }

    @Override
    public Optional<Credential> getCredentialByUsername(String username) {
        return credentialRepository.getCredentialByUsername(username);
    }
}
