package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {

    @Override
    public void save(Resume resume) {
        storage.add(resume);
    }

    @Override
    public Resume get(String uuid) {
        for (Resume resume : storage){
            if (resume.getUuid().equals(uuid)){
                return resume;
            }
        }
        return null;
    }

}
