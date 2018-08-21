package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void doSave(Resume newResume, Resume resume) {
        storage.put(newResume.getUuid(), newResume);
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected Resume doGet(Resume resume) {
        return resume;
    }

    @Override
    protected void doUpdate(Resume newResume, Resume resume) {
        storage.put(newResume.getUuid(), newResume);
    }

    @Override
    protected void doDelete(Resume resume) {
        storage.remove(resume.getUuid());
    }

    @Override
    protected boolean isExist(Resume resume) {
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
