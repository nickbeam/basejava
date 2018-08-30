package ru.javaops.webapp.model;

import java.util.*;

public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    private String fullName;

    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName){
        this((UUID.randomUUID().toString()), fullName);
    }

    public Resume(String uuid, String fullName){
        Objects.requireNonNull(uuid, "UUID can't be NULL");
        Objects.requireNonNull(fullName, "fullName can't be NULL");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public void addContact(ContactType type, String value) {
        this.contacts.put(type, value);
    }

    public void addSection(SectionType type, Section value) {
        this.sections.put(type, value);
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public int compareTo(Resume o) {
        int comp = this.fullName.compareTo(o.fullName);
        return comp != 0 ? comp : this.uuid.compareTo(o.uuid);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return uuid + " (" + fullName + ")";
    }
}
