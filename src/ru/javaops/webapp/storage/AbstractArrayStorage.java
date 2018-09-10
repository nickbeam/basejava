package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.util.*;


public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 100000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    protected abstract Integer getSearchKey(String uuid);

    protected abstract void putResume(int index, Resume resume);

    protected abstract void removeResume(int index);

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size,  null);
        size = 0;
    }

    @Override
    protected void doSave(Resume resume, Integer index) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Error: resume storage is full!", resume.getUuid());
        } else {
            putResume(index, resume);
            size++;
        }
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    public List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    @Override
    protected Resume doGet(Integer index) {
        return storage[index];
    }

    @Override
    protected void doUpdate(Resume resume, Integer index) {
        storage[index] = resume;
    }

    @Override
    protected void doDelete(Integer index) {
        size--;
        removeResume(index);
        storage[size] = null;
    }
}
