package com.telerikfinalproject.photocontest.repositories.contracts;

import com.telerikfinalproject.photocontest.models.User;

import java.util.List;

public interface UserRepository {

    List<User> getAllUsers();

    User getUserById(int id);

    void createUser(User user);

    User updateUser(User user);

    void deleteUser(int id);

    User getUserByUsername(String username);

    List<User> getAllJunkies();

    List<User> getAllOrganisers();

    List<User> getAllJuries();

    List<User> getTopJunkies(int amount);
}
