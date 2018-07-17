package ru.javaops.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

import java.util.UUID;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private IStorage storage;

    public AbstractArrayStorageTest(IStorage storage){
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        Assert.assertEquals("uuid1", storage.get(UUID_1).toString());
        Assert.assertEquals("uuid2", storage.get(UUID_2).toString());
        Assert.assertEquals("uuid3", storage.get(UUID_3).toString());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void getAll() {
        Resume[] all = storage.getAll();
        Assert.assertArrayEquals(all, storage.getAll());
    }

    @Test
    public void update() {
        Resume resume = storage.get("uuid3");
        storage.update(resume);
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test
    public void save() {
        storage.save(new Resume("uuid9"));
        Assert.assertEquals("uuid9", storage.get("uuid9").toString());
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("uuid3");
        storage.get("uuid3");
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void storageOverflow() {
        try{
            for (int i = 0; i < 100001; i++){
                storage.save(new Resume());
            }
            fail("Exception not thrown");
        } catch(Exception e) {
            assertEquals("Error: resume storage is full!", e.getMessage());
        }
    }
}