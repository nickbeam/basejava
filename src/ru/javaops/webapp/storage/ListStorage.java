package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {

    ArrayList<Resume> storage = new ArrayList<>();

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.add(resume);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        for (Resume resume : storage){
            if (resume.getUuid().equals(uuid)){
                return resume;
            }
        }
        return null;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get(storage.indexOf(searchKey));
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        storage.set(storage.indexOf(searchKey), resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove(storage.indexOf(searchKey));
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.contains(storage.indexOf(searchKey));
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
