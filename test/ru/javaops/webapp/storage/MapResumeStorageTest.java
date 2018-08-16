package ru.javaops.webapp.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javaops.webapp.model.Resume;

public class MapResumeStorageTest extends AbstractStorageTest{
    public MapResumeStorageTest() {
        super(new MapResumeStorage());
    }
}
