package com.ftc;

public class Main {

    public static void main(String[] args) {
        final Configuration appConfig = new Configuration(args);
        new Sort(appConfig).mergeAllFiles();
    }
}
