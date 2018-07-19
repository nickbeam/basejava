package ru.javaops.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import static org.junit.Assert.*;
import static ru.javaops.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest {
    private final IStorage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final Resume resume1 = new Resume(UUID_1);
    private static final Resume resume2 = new Resume(UUID_2);
    private static final Resume resume3 = new Resume(UUID_3);

    public AbstractArrayStorageTest(IStorage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void size() {
        compareSize(3);
    }

    @Test
    public void get() {
        compareResume(resume1);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void getAll() {
        Resume[] resumes = new Resume[]{resume1, resume2, resume3};
        Assert.assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    public void update() {
        Resume resume = storage.get(UUID_3);
        storage.update(resume);
        compareSize(3);
        compareResume(resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume());
    }

    @Test
    public void save() {
        Resume resume = new Resume();
        storage.save(resume);
        compareSize(4);
        compareResume(resume);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistStorageException() {
        storage.save(resume1);
    }

    @Test(expected = StorageException.class)
    public void storageOverflow() {
        try {
            for (int i = storage.size(); i < STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (Exception e) {
            fail("Storage is not full filled! " + "Exception: " + e.getMessage());
        }
        storage.save(new Resume());
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_3);
        compareSize(2);
        storage.get(UUID_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistStorageException() {
        storage.delete(new Resume().getUuid());
    }

    @Test
    public void clear() {
        storage.clear();
        compareSize(0);
    }

    private void compareSize(int size){
        Assert.assertEquals(size, storage.size());
    }

    private void compareResume(Resume resume){
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }
}