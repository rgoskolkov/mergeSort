package com.ftc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Configuration {
    private DataType dataType;
    private SortType sortType;
    private List<File> inputFiles = new ArrayList<>();
    private File outputFile;

    public Configuration(DataType dataType, SortType sortType, List<File> inputFiles, File outputFile) {
        this.dataType = dataType;
        this.sortType = sortType;
        this.inputFiles = inputFiles;
        this.outputFile = outputFile;
    }

    public Configuration(String[] args) {
        boolean hasOptionalSort = false;
        try {
            this.sortType = SortType.of(args[0]);
            hasOptionalSort = true;
        } catch (IllegalArgumentException e) {
            this.sortType = SortType.DESC;
        }
        if ((hasOptionalSort && args.length < 4) || (!hasOptionalSort && args.length < 3)) {
            throw new IllegalArgumentException("Не верное количество аргументов");
        }
        this.dataType = DataType.of(hasOptionalSort ? args[1] : args[0]);

        this.outputFile = hasOptionalSort ? new File(args[2]) : new File(args[1]);

        for (int i = hasOptionalSort ? 3 : 2; i < args.length; i++) {
            final File inFile = new File(args[i]);
            if (!inFile.exists()) {
                throw new IllegalArgumentException("input file " + args[i]  + " does not exists");
            }
            inputFiles.add(inFile);
        }
    }

    public DataType getDataType() {
        return dataType;
    }

    public SortType getSortType() {
        return sortType;
    }

    public List<File> getInputFiles() {
        return inputFiles;
    }

    public File getOutputFile() {
        return outputFile;
    }
}
