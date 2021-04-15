package com.telerikfinalproject.photocontest.exceptions;

public class DateTimeException extends RuntimeException {

    public DateTimeException() {
        super("You can only give review during phase 2.");
    }
}
