package ru.javaops.webapp.model;

import java.util.Comparator;
import java.util.UUID;

public class Resume {

    // Unique identifier
    private final String uuid;

    private String fullName;

    public Resume(){
        this.uuid = (UUID.randomUUID().toString());
    }

    public Resume(String uuid, String fullName){
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

    public static Comparator<Resume> COMPARE_RESUMES_BY_FULLNAME = (o1, o2) -> o1.fullName.compareToIgnoreCase(o2.fullName);

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
        return uuid;
    }
}
