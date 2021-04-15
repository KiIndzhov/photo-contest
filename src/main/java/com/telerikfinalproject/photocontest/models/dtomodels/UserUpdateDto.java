package com.telerikfinalproject.photocontest.models.dtomodels;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserUpdateDto {

    @NotNull(message = "First name should not be empty")
    @Size(min = 2, max = 20, message = "First name should be between 2 and 20 symbols")
    private String firstName;

    @NotNull(message = "Last name should not be empty")
    @Size(min = 2, max = 20, message = "Last name should be between 2 and 20 symbols")
    private String lastName;


    public UserUpdateDto() {
    }

    public UserUpdateDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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
}
