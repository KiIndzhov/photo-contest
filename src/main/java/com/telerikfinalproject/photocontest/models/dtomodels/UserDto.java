package com.telerikfinalproject.photocontest.models.dtomodels;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto {

    @NotNull(message = "First name should not be empty")
    @Size(min = 2, max = 20, message = "First name should be between 2 and 20 symbols")
    private String firstName;

    @NotNull(message = "Last name should not be empty")
    @Size(min = 2, max = 20, message = "Last name should be between 2 and 20 symbols")
    private String lastName;

    @NotNull(message = "Username should not be empty")
    @Size(min = 2, max = 20, message = "Username should be between 2 and 20 symbols")
    private String username;

    @NotNull(message = "Password should not be empty")
    @Size(min = 8, max = 20, message = "Password should be between 8 and 20 symbols")
    private String password;

    public UserDto() {
    }

    public UserDto(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
