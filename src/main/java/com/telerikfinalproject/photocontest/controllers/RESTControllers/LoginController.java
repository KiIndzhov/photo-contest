package com.telerikfinalproject.photocontest.controllers.RESTControllers;

import com.telerikfinalproject.photocontest.models.dtomodels.CredentialDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @PostMapping
    public void login(@Valid @RequestBody CredentialDto credentialDto){

    }
}
