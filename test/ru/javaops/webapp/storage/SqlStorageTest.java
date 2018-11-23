package ru.javaops.webapp.storage;

import ru.javaops.webapp.Config;

import java.util.Properties;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        //super(new SqlStorage(Config.getInstance().getStorageDir().toString(), "postgres", "postgres"));
        super(new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "postgres"));
    }
}
