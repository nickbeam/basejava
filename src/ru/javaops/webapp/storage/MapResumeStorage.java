package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Object getSearchKey(String uuid) {
//        Iterator it = storage.entrySet().iterator();
//        while (it.hasNext()){
//            Map.Entry pair = (Map.Entry)it.next();
//            if (pair.getKey().equals(uuid)){
//                return pair.getKey();
//            }
//        }
//        return null;
        return storage.get(uuid);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
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
