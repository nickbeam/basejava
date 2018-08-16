package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.*;


public class MapUuidStorage extends AbstractStorage {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void doSave(Resume resume, Object uuid) {
        storage.put((String) uuid, resume);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected Resume doGet(Object uuid) {
        return  storage.get((String) uuid);
    }

    @Override
    protected void doUpdate(Resume resume, Object uuid) {
        storage.replace((String) uuid, resume);
    }

    @Override
    protected void doDelete(Object uuid) {
        storage.remove((String) uuid);
    }

    @Override
    protected boolean isExist(Object uuid) {
        return storage.containsKey((String) uuid);
    }

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }
}
