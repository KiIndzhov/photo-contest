package com.telerikfinalproject.photocontest.models.dtomodels;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CredentialDto {

    @NotNull(message = "Username should not be null")
    @Size(min = 2, max = 20, message = "Username should be between 2 and 20 symbols")
    private String username;

    @NotNull(message = "Password should not be null")
    @Size(min = 8, max = 20, message = "Password should be between 2 and 20 symbols")
    private String password;

    public CredentialDto() {
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
