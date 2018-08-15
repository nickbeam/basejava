package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected void doSave(Resume resume, Object index) {
        storage.add(resume);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++){
            if (storage.get(i).getUuid().equals(uuid)){
                return i;
            }
        }
        return null;
    }

    @Override
    protected Resume doGet(Object index) {
        return storage.get((Integer) index);
    }

    @Override
    protected void doUpdate(Resume resume, Object index) {
        storage.set((Integer) index, resume);
    }

    @Override
    protected void doDelete(Object index) {
        storage.remove(((Integer) index).intValue());
    }

    @Override
    protected boolean isExist(Object index) {
        return (index != null);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> listStorage = storage;
        Collections.sort(listStorage, Resume.COMPARE_RESUMES_BY_FULLNAME);
        return listStorage;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
