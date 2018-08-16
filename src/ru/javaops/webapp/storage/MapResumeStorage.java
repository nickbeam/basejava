package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void doSave(Resume newResume, Object resume) {
        storage.put(newResume.getUuid(), newResume);
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected Resume doGet(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void doUpdate(Resume newResume, Object resume) {
        storage.put(newResume.getUuid(), newResume);
    }

    @Override
    protected void doDelete(Object resume) {
        storage.remove(((Resume) resume).getUuid());
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    public List<Resume> doCopyAll() {
        return  new ArrayList<>(storage.values());
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
