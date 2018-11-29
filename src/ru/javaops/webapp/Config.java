package ru.javaops.webapp;

import ru.javaops.webapp.storage.IStorage;
import ru.javaops.webapp.storage.SqlStorage;

import java.io.*;
import java.util.Properties;

public class Config {
    private final static File PROPS = new File("config/resumes.properties");
    private static final Config INSTANCE = new Config();

    private final File storageDir;
    private final IStorage storage;

    public static Config getInstance() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties prop = new Properties();
            prop.load(is);
            storageDir = new File(prop.getProperty("storage.dir"));
            storage = new SqlStorage(prop.getProperty("db.url"), prop.getProperty("db.user"), prop.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public IStorage getStorage() {
        return storage;
    }
}
