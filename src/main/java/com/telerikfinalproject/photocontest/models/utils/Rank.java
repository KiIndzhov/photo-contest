package com.telerikfinalproject.photocontest.models.utils;

public enum Rank {

    JUNKIE,
    ENTHUSIAST,
    MASTER,
    DICTATOR,
    ORGANIZER;

    @Override
    public String toString() {
        switch (this) {
            case JUNKIE:
                return "Junkie";
            case ENTHUSIAST:
                return "Enthusiast";
            case MASTER:
                return "Master";
            case DICTATOR:
                return "Wise and Benevolent Photo Dictator";
            case ORGANIZER:
                return "Organizer";
            default:
                return "";
        }
    }
}
