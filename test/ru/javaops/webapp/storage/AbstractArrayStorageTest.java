package ru.javaops.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

import static org.junit.Assert.*;
import static ru.javaops.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest {
    private IStorage storage;

    public AbstractArrayStorageTest(IStorage storage){
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final Resume resume1 = new Resume(UUID_1);
    private static final Resume resume2 = new Resume(UUID_2);
    private static final Resume resume3 = new Resume(UUID_3);

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        Assert.assertEquals(resume1, storage.get(UUID_1));
    }

    @Test
    public void getAll() {
        Resume[] all = new Resume[]{resume1, resume2, resume3};
        Assert.assertArrayEquals(all, storage.getAll());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void update() {
        Resume resume = storage.get(UUID_3);
        storage.update(resume);
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test
    public void save() {
        storage.save(new Resume("uuid9"));
        Assert.assertEquals("uuid9", storage.get("uuid9").toString());
    }

    @Test(expected = ExistStorageException.class)
    public void saveStorageException() {
        storage.save(resume1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("uuid3");
        storage.get("uuid3");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteStorageException() {
        storage.delete(new Resume().getUuid());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test(expected = StorageException.class)
    public void storageOverflow() {
        for (int i = storage.size(); i < STORAGE_LIMIT + 1; i++) {
            storage.save(new Resume());
        }
        fail("Exception not thrown");
    }
}