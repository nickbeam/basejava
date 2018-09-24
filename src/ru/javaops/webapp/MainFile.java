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

        File dir = new File("./src/ru/javaops"); // /webapp

        getDirectoryFileNames(dir, 0);

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getDirectoryFileNames(File dir, int level) {
        File[] files = dir.listFiles();
        if (files == null) {
            throw new StorageException(dir.getName() + " is not directory, or IO Error");
        }
        for (File file : files) {
            if (file.isFile()) {
                for (int i = 0; i < level; i++){
                    System.out.print("\t");
                }
                System.out.println(file.getName());
            } else {
                for (int i = 0; i < level; i++){
                    System.out.print("\t");
                }
                System.out.println("[" + file.getName() + "]");
                getDirectoryFileNames(file, level + 1);
            }
        }
    }
}
