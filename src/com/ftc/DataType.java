package com.ftc;

public enum DataType {
    STRING("-s"),
    INTEGER("-i");

    private final String code;

    DataType(String code) {
        this.code = code;
    }

    public static DataType of(String code) {
        for (DataType dataType : values()) {
            if (dataType.code.equals(code)) {
                return dataType;
            }
        }
        throw new IllegalArgumentException(code);
    }
}
