package ru.javaops.webapp.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    private String fullName;

    private Map<ContactType, String> contactMap = new HashMap<>();
    private Map<SectionType, Section> sectionMap = new HashMap<>();

    public Resume(String fullName){
        this((UUID.randomUUID().toString()), fullName);
    }

    public Resume(String uuid, String fullName){
        Objects.requireNonNull(uuid, "UUID can't be null");
        Objects.requireNonNull(fullName, "fullName can't be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid);
    }

    @Override
    public int compareTo(Resume o) {
        int comp = this.fullName.compareTo(o.fullName);
        return comp != 0 ? comp : this.uuid.compareTo(o.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
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
