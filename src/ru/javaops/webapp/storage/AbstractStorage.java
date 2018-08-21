package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

import java.util.Collections;
import java.util.List;


public abstract class AbstractStorage<SK> implements IStorage{

    protected abstract void doSave(Resume resume, SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    protected abstract Resume doGet(SK searchKey);

    protected abstract void doUpdate(Resume resume, SK searchKey);

    protected abstract void doDelete(SK searchKey);

    protected abstract boolean isExist(SK searchKey);

    protected abstract List<Resume> doCopyAll();

    public void save(Resume resume) {
        SK searchKey = getNotExistSearchKey(resume.getUuid());
        doSave(resume, searchKey);
    }

    public Resume get(String uuid) {
        SK searchKey = getExistSearchKey(uuid);
        return doGet(searchKey);
    }

    public void update(Resume resume) {
        SK searchKey = getExistSearchKey(resume.getUuid());
        doUpdate(resume, searchKey);
    }

    public void delete(String uuid) {
        SK searchKey = getExistSearchKey(uuid);
        doDelete(searchKey);
    }

    protected SK getExistSearchKey(String uuid){
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)){
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = doCopyAll();
        Collections.sort(list);
        return list;
    }

    protected SK getNotExistSearchKey(String uuid){
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)){
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}
