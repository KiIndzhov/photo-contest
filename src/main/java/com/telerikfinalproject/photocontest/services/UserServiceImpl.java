package com.telerikfinalproject.photocontest.services;

import com.telerikfinalproject.photocontest.exceptions.DuplicateEntityException;
import com.telerikfinalproject.photocontest.models.Credential;
import com.telerikfinalproject.photocontest.models.User;
import com.telerikfinalproject.photocontest.repositories.contracts.UserRepository;
import com.telerikfinalproject.photocontest.services.contracts.CredentialService;
import com.telerikfinalproject.photocontest.services.contracts.UserService;
import com.telerikfinalproject.photocontest.services.utils.ValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CredentialService credentialService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CredentialService credentialService) {
        this.userRepository = userRepository;
        this.credentialService = credentialService;

    }

    @Override
    public List<User> getAllUsers(Credential credential) {
        ValidationHelper.verifyOrganizer(credential);
        return getAllUsers();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User getUserById(int id, Credential credential) {
        ValidationHelper.verifyUser(credential);
        return getUserById(id);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    @Override
    public User createUser(User user) {
        if (usernameExists(user.getCredential().getUsername())) {
            throw new DuplicateEntityException("User", "username", user.getCredential().getUsername());
        }
        userRepository.createUser(user);
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public List<User> getAllJurors() {
        return userRepository.getAllJuries();
    }

    @Override
    public List<User> getAllJunkies() {
        return userRepository.getAllJunkies();
    }

    @Override
    public List<User> getAllOrganisers() {
        return userRepository.getAllOrganisers();
    }

    @Override
    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    @Override
    public List<User> getTopJunkies(int amountJunkies) {
        return userRepository.getTopJunkies(amountJunkies);
    }

    @Override
    public boolean usernameExists(String username) {
        return credentialService.usernameExists(username);
    }
}
