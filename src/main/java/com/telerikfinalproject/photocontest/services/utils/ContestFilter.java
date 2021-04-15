package com.telerikfinalproject.photocontest.services.utils;

public enum ContestFilter {

    ALL,
    IS_COMPETING,
    IS_JUDGING,
    CAN_COMPETE,
    FINISHED,
    PHASE_ONE,
    PHASE_TWO,
    OPENED,
    CLOSED;

    @Override
    public String toString() {
        switch (this) {
            case ALL:
                return "All";
            case IS_COMPETING:
                return "Is compete";
            case IS_JUDGING:
                return "Is judging";
            case CAN_COMPETE:
                return "Can compete";
            case FINISHED:
                return "Finished";
            case PHASE_ONE:
                return "Phase 1";
            case PHASE_TWO:
                return "Phase 2";
            case OPENED:
                return "Opened";
            case CLOSED:
                return "Closed";
            default:
                return "";
        }
    }
}
