package com.telerikfinalproject.photocontest.services;

import com.telerikfinalproject.photocontest.exceptions.DuplicateEntityException;
import com.telerikfinalproject.photocontest.exceptions.UnauthorisedException;
import com.telerikfinalproject.photocontest.models.User;
import com.telerikfinalproject.photocontest.models.utils.UserRole;
import com.telerikfinalproject.photocontest.repositories.contracts.UserRepository;
import com.telerikfinalproject.photocontest.services.contracts.CredentialService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.telerikfinalproject.photocontest.services.Helpers.createUser;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @Mock
    UserRepository userRepository;

    @Mock
    CredentialService credentialService;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void getAllUsers_Should_Throws_When_User_NotOrganizer() {
        User mockUser = createUser();
        mockUser.getCredential().setUserRole(UserRole.PHOTO_JUNKIE);

        Assertions.assertThrows(UnauthorisedException.class,
                () -> userService.getAllUsers(mockUser.getCredential()));
    }

    @Test
    public void getAllUsers_Should_Call_Repository() {
        User mockUser = createUser();

        userService.getAllUsers(mockUser.getCredential());

        verify(userRepository, times(1)).getAllUsers();
    }

    @Test
    public void getUserById_Should_Call_Repository() {
        User mockUser = createUser();

        userService.getUserById(mockUser.getId(), mockUser.getCredential());

        verify(userRepository, times(1)).getUserById(mockUser.getId());
    }

    @Test
    public void usernameExists_Should_Call_CredentialService() {
        userService.usernameExists(Mockito.anyString());

        verify(credentialService, times(1)).usernameExists(Mockito.anyString());
    }

    @Test
    public void createUser_Should_Throw_When_UsernameExists() {
        User user = createUser();
        User newUser = createUser();
        newUser.setId(2);

        when(userService.usernameExists(newUser.getCredential().getUsername()))
                .thenReturn(true);

        Assertions.assertThrows(DuplicateEntityException.class,
                () -> userService.createUser(newUser));
    }

    @Test
    public void createUser_Should_Call_Repository_When_Valid() {
        User user = createUser();
        User newUser = createUser();
        newUser.setId(2);
        newUser.getCredential().setUsername("somethingNew");


        when(userService.usernameExists(newUser.getCredential().getUsername()))
                .thenReturn(false);

        userService.createUser(newUser);

        verify(userRepository, times(1)).createUser(newUser);
    }

    @Test
    public void getAllJunkies_Should_Call_Repository_WhenValid() {
        userService.getAllJunkies();

        verify(userRepository, times(1)).getAllJunkies();
    }

    @Test
    public void getAllJurors_Should_Call_Repository_WhenValid() {
        userService.getAllJurors();

        verify(userRepository, times(1)).getAllJuries();
    }

    @Test
    public void getUserByUsername_Should_Call_Repository_WhenValid() {
        userService.getUserByUsername(Mockito.anyString());

        verify(userRepository, times(1)).getUserByUsername(Mockito.anyString());
    }

    @Test
    public void getAllOrganisers_Should_Call_Repository_WhenValid() {
        userService.getAllOrganisers();

        verify(userRepository, times(1)).getAllOrganisers();
    }

    @Test
    public void getTopJunkies_Should_Call_Repository_WhenValid() {
        userService.getTopJunkies(Mockito.anyInt());

        verify(userRepository, times(1)).getTopJunkies(Mockito.anyInt());
    }

    @Test
    public void updateUser_Should_Call_Repository_WhenValid() {
        User user = createUser();
        userService.updateUser(user);

        verify(userRepository, times(1)).updateUser(user);
    }
}
