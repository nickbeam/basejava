package ru.javaops.webapp;

import ru.javaops.webapp.exception.StorageException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        File filePath = new File("./.gitignore");
        try {
            System.out.println(filePath.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/ru/javaops/webapp");

        getDirectoryFileNames(dir);

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getDirectoryFileNames(File dir) {
        File[] files = dir.listFiles();
        if (files == null) {
            throw new StorageException(dir.getName() + " is not directory, or IO Error");
        }
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("Directory: " + file.getName());
                getDirectoryFileNames(file);
            } else {
                System.out.println("    File: " + file.getName());
            }
        }
    }
}
