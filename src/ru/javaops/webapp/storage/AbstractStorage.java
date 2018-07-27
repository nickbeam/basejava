package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

public abstract class AbstractStorage implements IStorage{

    protected static Collection<Resume> storage;

    protected AbstractStorage(Collection<Resume> newStorage){
        storage = newStorage;
    }

    public Resume[] getAll(){
        return storage.toArray(new Resume[0]);
    }

    public void update(Resume resume){
        if (!storage.contains(resume)){
            throw new NotExistStorageException(resume.getUuid());
        } else {
            Resume findResume = get(resume.getUuid());
            findResume = resume;
        }
    }

    public void delete(String uuid){
        Resume resume = get(uuid);
        if (resume == null) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            storage.remove(resume);
        }
    }

    public int size(){
        return storage.size();
    }

    public void clear(){
        storage.clear();
    }

    public abstract void save(Resume resume);

    public abstract Resume get(String uuid);
}
