package ru.javaops.webapp;

import java.io.*;
import java.util.Properties;

public class Config {
    private final static File PROPS = new File("config/resumes.properties");
    private static final Config INSTANCE = new Config();

    private File storageDir;

    public static Config getInstance() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties prop = new Properties();
            prop.load(is);
            storageDir = new File(prop.getProperty("storage.dir"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }
}
