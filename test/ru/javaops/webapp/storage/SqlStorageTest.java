package ru.javaops.webapp.storage;

import ru.javaops.webapp.Config;

import java.util.Properties;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(Config.getInstance().getStorage());
    }
}
