package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serialize.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new JsonStreamSerializer()));
    }
}
