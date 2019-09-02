package com.ftc;

public class RowComparator {
    private final DataType dataType;
    private final SortType sortType;

    public RowComparator(DataType dataType, SortType sortType) {
        this.dataType = dataType;
        this.sortType = sortType;
    }

    public int compare(String s1, String s2) {
        Comparable v1 = getValue(s1);
        Comparable v2 = getValue(s2);
        switch (sortType) {
            case ASC:
                return v2.compareTo(v1);
            default:
                return v1.compareTo(v2);
        }
    }

    private Comparable getValue(String s) {
        switch (dataType) {
            case INTEGER:
                return Integer.valueOf(s);
            default:
                return s;
        }
    }
}
