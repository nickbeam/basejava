package ru.javaops.webapp.storage;

import org.junit.*;
import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;


public abstract class AbstractStorageTest {
    protected final IStorage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final Resume resume1 = new Resume(UUID_1, "Name1");
    private static final Resume resume2 = new Resume(UUID_2, "Name2");
    private static final Resume resume3 = new Resume(UUID_3, "Name3");

    public AbstractStorageTest(IStorage storage) {
        this.storage = storage;
    }

    static {
        resume1.addContact(ContactType.PHONE, "+71231231212");
        resume1.addContact(ContactType.MOBILE_PHONE, "+79217340000");
        resume1.addContact(ContactType.EMAIL, "name1@mail.ru");
        resume1.addContact(ContactType.TELEGRAM, "telegram1");
        resume1.addContact(ContactType.SKYPE, "skype1");
        resume1.addContact(ContactType.LINKEDIN, "LinkedIn/name1");
        resume1.addContact(ContactType.GITHUB, "GitHub/name1");
        resume1.addContact(ContactType.STACKOVERFLOW, "Stackoverflow/name1");
        resume1.addContact(ContactType.HOME_PAGE, "homepage/name1");

        resume1.addSection(SectionType.PERSONAL, new TextSection("Личные качества resume 1"));
        resume1.addSection(SectionType.OBJECTIVE, new TextSection("Позиция для resume 1"));

        resume1.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("achievment 1", "achievment 2", "achievment 3")));
        resume1.addSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("qualification 1", "qualification 2", "qualification 3")));

        resume1.addSection(SectionType.EXPERIENCE, new OrganisationSection(
                new Organisation("Yandex", "url3", LocalDate.of(2008, Month.MAY, 15), LocalDate.now(), "Синьйор", "Работаю синьйором"),
                new Organisation("JetBrains", "url2", LocalDate.of(2005, Month.DECEMBER, 3), LocalDate.of(2008, Month.MAY, 14), "Миддл", "Работал миддлом"),
                new Organisation("JetBrains", "url1", LocalDate.of(2004, Month.APRIL, 1), LocalDate.of(2005, Month.DECEMBER, 2), "Джуниор", "Работал джуном")
        ));

        resume1.addSection(SectionType.EDUCATION, new OrganisationSection(
                new Organisation("Study 1", "urlStudy1", LocalDate.of(2000, Month.SEPTEMBER, 1), LocalDate.of(2004, Month.JUNE, 15), "Student", "Was a student")
        ));
        System.out.println(resume1);
        System.out.println(resume1.getContacts());
        System.out.println(resume1.getSections());
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
        Assert.assertEquals(listStorage, Arrays.asList(resume1, resume2, resume3));
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