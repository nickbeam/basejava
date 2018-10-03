package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serialize.StreamSerializer;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new StreamSerializer()));
    }
}
