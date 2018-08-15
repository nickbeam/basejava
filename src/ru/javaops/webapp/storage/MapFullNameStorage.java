package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.*;

public class MapFullNameStorage extends AbstractStorage {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.put(resume.getFullName(), resume);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Iterator it = storage.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            if (pair.getKey().equals(uuid)){
                return pair.getKey();
            }
        }
        return null;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {

    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = getNotExistSearchKey(resume.getFullName());
        doSave(resume, searchKey);
    }

    @Override
    public void update(Resume resume) {
        Object searchKey = getExistSearchKey(resume.getFullName());
        doUpdate(resume, searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> listStorage = new ArrayList<>(storage.values());
        Collections.sort(listStorage, Resume.COMPARE_RESUMES_BY_FULLNAME);
        return listStorage;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
