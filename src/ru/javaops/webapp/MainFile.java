package ru.javaops.webapp;

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
        File dir = new File("D:/JAVA/basejava/src/ru/javaops/webapp");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null){
            for (String name:dir.list()) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
