package com.ftc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Sort {
    private Configuration config;
    private RowComparator comparator;

    public Sort(Configuration config) {
        this.config = config;
        this.comparator = new RowComparator(config.getDataType(), config.getSortType());
    }

    public void mergeAllFiles() {
        final File reduced = config.getInputFiles().stream().reduce(this::mergeFiles).orElse(config.getInputFiles().get(0));
        try {
            Files.copy(reduced.toPath(), config.getOutputFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Can't copy result file into output file");
            e.printStackTrace();
        }
    }

    private File mergeFiles(File file1, File file2) {
        try {
            File out = File.createTempFile("result", ".tmp");
            out.deleteOnExit();
            try (BufferedReader fileReader1 = new BufferedReader(new FileReader(file1));
                 BufferedReader fileReader2 = new BufferedReader(new FileReader(file2));
                 BufferedWriter fileOut = new BufferedWriter(new FileWriter(out))) {
                String s1 = fileReader1.readLine();
                String s2 = fileReader2.readLine();
                while (s1 != null && s2 != null) {
                    final int compare = comparator.compare(s1, s2);
                    if (compare <= 0) {
                        writeLine(fileOut, s1);
                        s1 = fileReader1.readLine();
                    } else {
                        writeLine(fileOut, s2);
                        s2 = fileReader2.readLine();
                    }
                }
                if (s1 == null) {
                    writeEndOfFile(s2, fileOut, fileReader2);
                } else {
                    writeEndOfFile(s1, fileOut, fileReader1);
                }
                return out;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeEndOfFile(Comparable lastValue, BufferedWriter writer, BufferedReader fileDataReader) throws
            IOException {
        writeLine(writer, lastValue);
        Comparable value = fileDataReader.readLine();
        while (value != null) {
            writeLine(writer, value);
            value = fileDataReader.readLine();
        }
    }

    private void writeLine(BufferedWriter writer, Comparable line) {
        try {
            writer.write(line.toString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("can't write line to file ");
        }
    }
}
