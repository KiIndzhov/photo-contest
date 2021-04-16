package com.telerikfinalproject.photocontest.services;

import com.telerikfinalproject.photocontest.exceptions.DuplicateEntityException;
import com.telerikfinalproject.photocontest.models.Credential;
import com.telerikfinalproject.photocontest.repositories.contracts.CredentialRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.telerikfinalproject.photocontest.services.Helpers.createCredentials;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CredentialServiceImplTests {

    @Mock
    CredentialRepository credentialRepository;

    @InjectMocks
    CredentialServiceImpl credentialService;

    @Test
    public void usernameExists_Should_Call_Repository_WhenValid() {
        credentialService.usernameExists(Mockito.anyString());

        verify(credentialRepository, times(1)).usernameExists(Mockito.anyString());
    }

    @Test
    public void getCredentialByUsername_Should_Call_Repository_WhenValid() {
        credentialService.getCredentialByUsername(Mockito.anyString());

        verify(credentialRepository, times(1)).getCredentialByUsername(Mockito.anyString());
    }

    @Test
    public void getUserCredentials_Should_Call_Repository_WhenValid() {
        credentialService.getUserCredentials(Mockito.anyString(), Mockito.anyString());

        verify(credentialRepository, times(1)).getUserCredentials(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void createCredential_Should_Throw_When_UsernameExists() {
        Credential credential = createCredentials();
        Credential newCredential = createCredentials();
        newCredential.setId(2);

        when(credentialRepository.usernameExists(newCredential.getUsername()))
                .thenReturn(true);

        Assertions.assertThrows(DuplicateEntityException.class,
                () -> credentialService.createCredential(newCredential));
    }

    @Test
    public void createCredential_Should_Call_Repository_When_Valid() {
        Credential credential = createCredentials();
        Credential newCredential = createCredentials();
        newCredential.setUsername("somethingNew");
        newCredential.setId(2);

        when(credentialRepository.usernameExists(newCredential.getUsername()))
                .thenReturn(false);

        credentialService.createCredential(newCredential);

        verify(credentialRepository, times(1)).createCredential(newCredential);
    }
}
