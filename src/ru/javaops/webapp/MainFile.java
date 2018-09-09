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
        //File dir = new File("D:/JAVA/basejava/src/ru/javaops/webapp");
        File dir = new File("/Users/nick/Documents/JAVA/PROJECTS/basejava/src/ru/javaops/webapp");

        if (dir.isDirectory()){
            getDirectoryFileNames(dir);
        } else if (!dir.exists()){
            System.out.println("dir is not exist");
        } else {
            System.out.println("dir is not directory");
        }

//        String[] list = dir.list();
//        if (list != null){
//            for (String name:dir.list()) {
//                System.out.println(name);
//            }
//        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static void getDirectoryFileNames(File dir){
        File filePath;
        for (String name:dir.list()) {
            filePath = new File(dir + "/" + name);
            if (filePath.isDirectory()){
                getDirectoryFileNames(filePath);
            } else {
                System.out.println(name);
            }
        }
    }
}
