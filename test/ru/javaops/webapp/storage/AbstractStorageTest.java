package ru.javaops.webapp.storage;

import org.junit.*;
import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;


public abstract class AbstractStorageTest {
    protected final IStorage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final Resume resume1 = new Resume(UUID_1, UUID_1);
    private static final Resume resume2 = new Resume(UUID_2, UUID_2);
    private static final Resume resume3 = new Resume(UUID_3, UUID_3);

    public AbstractStorageTest(IStorage storage) {
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
        Resume[] resumes = {resume1, resume2, resume3};
        List<Resume> listStorage = Arrays.asList(resumes);
        Assert.assertEquals(listStorage, storage.getAllSorted());
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

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_3);
        compareSize(2);
        storage.get(UUID_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistStorageException() {
        storage.delete("dummy");
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