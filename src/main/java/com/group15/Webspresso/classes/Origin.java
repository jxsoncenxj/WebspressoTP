package com.group15.Webspresso.classes;

public enum Origin {
    ETHIOPIA("Ethiopia"),
    KENYA("Kenya"),
    BRAZIL("Brazil"),
    COLOMBIA("Colombia"),
    CENTRALAMERICA("Central America");

    private final String origin;

    Origin(String origin) {
        this.origin = origin;
    }

    public String getOrigin() {
        return origin;
    }
}
