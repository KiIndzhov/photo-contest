package com.telerikfinalproject.photocontest.services.contracts;

import com.telerikfinalproject.photocontest.models.Credential;
import com.telerikfinalproject.photocontest.models.User;
import com.telerikfinalproject.photocontest.models.dtomodels.JuryDto;

import java.util.List;

public interface UserService {
    List<User> getAllUsers(Credential credential);

    List<User> getAllUsers();

    User getUserById(int id, Credential credential);

    User getUserById(int id);

    User createUser(User user);

    User getUserByUsername(String username);

    List<JuryDto> getAllJurors();

    List<User> getAllJunkies();

    List<User> getAllOrganisers();

    void updateUser(User user);

    List<User> getTopJunkies(int amountJunkies);
}
