package ru.javaops.webapp.storage;

import org.junit.*;
import ru.javaops.webapp.Config;
import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.*;
import ru.javaops.webapp.storage.serialize.StreamSerializer;

import java.io.File;
import java.util.Arrays;
import java.util.List;


public abstract class AbstractStorageTest {
    protected final static File STORAGE_DIR = Config.getInstance().getStorageDir(); //new File("storage");
    protected final static StreamSerializer STREAM_SERIALIZER = new StreamSerializer();

    protected final IStorage storage;

    private static final String UUID_3 = "uuid3";
    private static final Resume resume1 = ResumeTestData.getResume1();
    private static final Resume resume2 = ResumeTestData.getResume2();
    private static final Resume resume3 = new Resume(UUID_3, "Name3");

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
    public void getAllSorted() {
        List<Resume> listStorage = storage.getAllSorted(); //Arrays.asList(resumes);
        Assert.assertEquals(Arrays.asList(resume1, resume2, resume3), listStorage);
    }

    @Test
    public void update() {
        Resume resume = storage.get(UUID_3);
        resume3.setContact(ContactType.PHONE, "+79898989898");
        resume3.setContact(ContactType.MOBILE_PHONE, "+7555444554");
        resume3.setContact(ContactType.EMAIL, "name33@mail.ru");
        storage.update(resume);
        compareSize(3);
        compareResume(resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("dummy"));
    }

    @Test
    public void save() {
        Resume resume = new Resume("dummy");
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

    protected void compareSize(int size){
        Assert.assertEquals(size, storage.size());
    }

    protected void compareResume(Resume resume){
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }
}