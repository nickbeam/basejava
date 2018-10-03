package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serialize.DateStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest{
    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new DateStreamSerializer()));
    }
}
