package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.util.*;

import static ru.javaops.webapp.model.Resume.COMPARE_RESUMES_BY_FULLNAME;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 100000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    protected abstract Integer getSearchKey(String uuid);

    protected abstract void putResume(int index, Resume resume);

    protected abstract void removeResume(int index);

//    public Resume[] getAll() {
//        return Arrays.copyOfRange(storage, 0, size);
//    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> listStorage = Arrays.asList(storage);
        Collections.sort(listStorage, COMPARE_RESUMES_BY_FULLNAME);
        return listStorage;
    }

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size,  null);
        size = 0;
    }

    @Override
    protected void doSave(Resume resume, Object index) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Error: resume storage is full!", resume.getUuid());
        } else {
            putResume((Integer) index, resume);
            size++;
        }
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

    @Override
    protected Resume doGet(Object index) {
        return storage[(Integer) index];
    }

    @Override
    protected void doUpdate(Resume resume, Object index) {
        storage[(Integer) index] = resume;
    }

    @Override
    protected void doDelete(Object index) {
        size--;
        removeResume((Integer) index);
        storage[size] = null;
    }
}
