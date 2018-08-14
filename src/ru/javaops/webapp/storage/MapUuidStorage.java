package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.*;


public class MapUuidStorage extends AbstractStorage {

    private HashMap<String, Resume> storage = new HashMap<>();

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
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
        return  null;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return  storage.get(searchKey);
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        storage.replace(searchKey.toString(), resume);
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
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        return new ArrayList<Resume>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
