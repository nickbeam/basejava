package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.*;

import java.time.Month;
import java.util.Arrays;

public class ResumeTestData {

    protected static Resume resume1 = new Resume("uuid1", "Name1");
    protected static Resume resume2 = new Resume("uuid2", "Name2");

    public static Resume getResume1() {
        return resume1;
    }
    public static Resume getResume2() {
        return resume2;
    }

    /*static {
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
                new Organisation("Yandex", "yandex.ru",
                        new Organisation.Position(2008, Month.MAY, "Синьйор", null)
                ),
                new Organisation("JetBrains", "jetbrains.com",
                        new Organisation.Position(2005, Month.DECEMBER, 2008, Month.MAY, "Миддл", "Работал миддлом"),
                        new Organisation.Position(2004, Month.APRIL, 2005, Month.DECEMBER, "Джуниор", "Работал джуном")
                )
        ));

        resume1.addSection(SectionType.EDUCATION, new OrganisationSection(
                new Organisation("Study 1", "urlStudy1",
                        new Organisation.Position(2000, Month.SEPTEMBER, 2004, Month.JUNE, "Student", "Was a student")
                )
        ));

        resume2.addContact(ContactType.PHONE, "+79999999999");
        resume2.addContact(ContactType.MOBILE_PHONE, "+79119999991");
        resume2.addContact(ContactType.EMAIL, "name2@mail.ru");
        resume2.addContact(ContactType.TELEGRAM, "telegram2");
        resume2.addContact(ContactType.SKYPE, "skype2");
        resume2.addContact(ContactType.LINKEDIN, "LinkedIn/name2");
        resume2.addContact(ContactType.GITHUB, "GitHub/name2");
        resume2.addContact(ContactType.STACKOVERFLOW, "Stackoverflow/name2");
        resume2.addContact(ContactType.HOME_PAGE, "homepage/name2");

        resume2.addSection(SectionType.PERSONAL, new TextSection("Личные качества resume 2"));
        resume2.addSection(SectionType.OBJECTIVE, new TextSection("Позиция для resume 2"));

        resume2.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("achievment 1", "achievment 2", "achievment 3")));
        resume2.addSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("qualification 1", "qualification 2", "qualification 3")));

        resume2.addSection(SectionType.EXPERIENCE, new OrganisationSection(
                new Organisation("Google", "google.com",
                        new Organisation.Position(2009, Month.MAY, "Старший уборщик", "Работаю старшим уборщиком")
                ),
                new Organisation("JetBrains", null,
                        new Organisation.Position(2005, Month.DECEMBER, 2008, Month.MAY, "Заместитель старшего уборщика", null),
                        new Organisation.Position(2004, Month.APRIL, 2005, Month.DECEMBER, "Младший убощик", null)
                )
        ));

        resume2.addSection(SectionType.EDUCATION, new OrganisationSection(
                new Organisation("Study 2", "urlStudy2",
                        new Organisation.Position(2000, Month.SEPTEMBER, 2004, Month.JUNE, "Student", null)
                )
        ));
    }*/
}
