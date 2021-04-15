package com.telerikfinalproject.photocontest.exceptions;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException(){
        super("Wrong username or password combination");
    }
}

