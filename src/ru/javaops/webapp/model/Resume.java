package ru.javaops.webapp.model;

import java.util.Comparator;
import java.util.UUID;

public class Resume {

    // Unique identifier
    private final String uuid;

    private String fullName;

    public Resume(){
        this.uuid = (UUID.randomUUID().toString());
        this.fullName = this.uuid;
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

    public static Comparator<Resume> COMPARE_RESUMES_BY_FULLNAME = new Comparator<Resume>() {
        int compareResult;
        @Override
        public int compare(Resume o1, Resume o2) {
            compareResult =  o1.fullName.compareTo(o2.fullName);
            if (compareResult != 0){
                return compareResult;
            }
            return o1.uuid.compareTo(o2.uuid);
        }
    };

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
