package ru.javaops.webapp.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javaops.webapp.model.Resume;

public class MapFullNameStorageTest extends AbstractStorageTest{
    public MapFullNameStorageTest() {
        super(new MapResumeStorage());
    }
}
