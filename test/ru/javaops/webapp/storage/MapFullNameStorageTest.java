package ru.javaops.webapp.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javaops.webapp.model.Resume;

public class MapFullNameStorageTest extends AbstractStorageTest{
    public MapFullNameStorageTest() {
        super(new MapFullNameStorage());
    }

    @Override
    @Test
    public void update() {
        Resume resume = storage.get(NAME_3);
        storage.update(resume);
        compareSize(3);
        compareResume(resume);
    }

    @Override
    protected void compareResume(Resume resume){
        Assert.assertEquals(resume, storage.get(resume.getFullName()));
    }
}
