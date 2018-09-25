package ru.javaops.webapp;

import ru.javaops.webapp.exception.StorageException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainFile {
    public static void main(String[] args) {
        File filePath = new File("./.gitignore");
        try {
            System.out.println(filePath.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File dir = new File("./src/ru/javaops"); // /webapp
        getDirectoryFileNames(dir, 0);
    }

    private static void getDirectoryFileNames(File dir, int level) {
        File[] files = dir.listFiles();
        if (files == null) {
            throw new StorageException(dir.getName() + " is not directory, or IO Error");
        }
        for (File file : files) {
            String sRepeated = IntStream.range(0, level).mapToObj(i -> "\t").collect(Collectors.joining(""));
            if (file.isFile()) {
                System.out.println(sRepeated + file.getName());
            } else {
                System.out.println(sRepeated + "[" + file.getName() + "]");
                getDirectoryFileNames(file, level + 1);
            }
        }
    }
}
