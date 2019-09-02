package com.ftc;

public enum SortType {
    ASC("-a"),
    DESC("-d");

    private final String code;

    SortType(String code) {
        this.code = code;
    }

    public static SortType of(String code) {
        for (SortType sortType : values()) {
            if (sortType.code.equals(code)) {
                return sortType;
            }
        }
        throw new IllegalArgumentException(code);
    }
}
