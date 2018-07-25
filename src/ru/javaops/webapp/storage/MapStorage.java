package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;


public class MapStorage extends AbstractStorage {

    protected MapStorage(){
        super(null);
    }

    @Override
    public Resume get(String uuid) {
        return null;
    }

    @Override
    public void save(Resume resume) {

    }
}
