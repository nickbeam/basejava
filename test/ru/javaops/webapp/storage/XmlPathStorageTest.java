package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serialize.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest{
    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new XmlStreamSerializer()));
    }
}
