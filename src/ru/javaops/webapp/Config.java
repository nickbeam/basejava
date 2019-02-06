package ru.javaops.webapp;

import ru.javaops.webapp.storage.FileStorage;
import ru.javaops.webapp.storage.IStorage;
import ru.javaops.webapp.storage.SqlStorage;

import java.io.*;
import java.util.Properties;

public class Config {
    private final static String PROPS = "/resumes.properties";
    private static final Config INSTANCE = new Config();

    private final File storageDir;
    private final IStorage storage;

    public static Config getInstance() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = Config.class.getResourceAsStream(PROPS)) {
            Properties prop = new Properties();
            prop.load(is);
            storageDir = new File(prop.getProperty("storage.dir"));
            storage = new SqlStorage(prop.getProperty("db.url"), prop.getProperty("db.user"), prop.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS);
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public IStorage getStorage() {
        return storage;
    }

//    private static File getHomeDir(){
//        String prop = System.getProperty("homeDir");
//        File homeDir = new File(prop == null? "." : prop);
//        if (!homeDir.isDirectory()){
//            throw new IllegalStateException(homeDir + " is not directory.");
//        }
//        return homeDir;
//    }
}
