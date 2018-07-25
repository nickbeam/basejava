package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Collection;

public abstract class AbstractStorage implements IStorage{

    protected static Collection<Resume> storage;

    protected AbstractStorage(Collection<Resume> newStorage){
        storage = newStorage;
    }

    public void clear(){
        storage.clear();
    }

    public abstract Resume get(String uuid);

    public void update(Resume resume){

    }

    public abstract void save(Resume resume);

    public void delete(String uuid){
//        Resume resume = new Resume();
//        resume = storage.
//        storage.remove();
    }

    public Resume[] getAll(){
        return storage.toArray(new Resume[storage.size()]);
    }

    public int size(){
        return storage.size();
    }

}
