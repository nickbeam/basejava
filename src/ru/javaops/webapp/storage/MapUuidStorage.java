package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.*;


public class MapUuidStorage extends AbstractStorage<String> {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void doSave(Resume resume, String uuid) {
        storage.put(uuid, resume);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected Resume doGet(String uuid) {
        return  storage.get(uuid);
    }

    @Override
    protected void doUpdate(Resume resume, String uuid) {
        storage.replace(uuid, resume);
    }

    @Override
    protected void doDelete(String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected boolean isExist(String uuid) {
        return storage.containsKey(uuid);
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
