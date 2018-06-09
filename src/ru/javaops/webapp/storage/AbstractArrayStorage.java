package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements IStorage {
    protected static final int STORAGE_LIMIT = 100000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Error: resume with uuid: " + uuid + " not found!");
            return null;
        }
        return storage[index];
    }

    public void clear() {
        Arrays.fill(storage, 0, size,  null);
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0){
            System.out.println("Error: resume with uuid: " + resume.getUuid() + " not found!");
        } else {
            updateResumeInStorage(resume);
        }
    }

    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) >= 0) {
            System.out.println("Error: resume with uuid: " + resume.getUuid() + " already exist!");
        } else if (size >= STORAGE_LIMIT) {
            System.out.println("Error: resume storage is full!");
        } else {
            putResumeToStorage(resume);
            size++;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Error: resume with uuid: " + uuid + " not found!");
        } else {
            size--;
            removeResumeFromStorage(index);
        }
    }

    protected abstract int getIndex(String uuid);

    protected abstract void updateResumeInStorage(Resume resume);

    protected abstract void putResumeToStorage(Resume resume);

    protected abstract void removeResumeFromStorage(int index);

}
