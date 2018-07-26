package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Collection;
import java.util.Iterator;

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
        Iterator<Resume> iterator = storage.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
            Resume resume = iterator.next();
            if (resume.getUuid().equals(uuid)){
                storage.remove(resume);
                break;
            }
        }
    }

    public Resume[] getAll(){
        return storage.toArray(new Resume[storage.size()]);
    }

    public int size(){
        return storage.size();
    }

}
