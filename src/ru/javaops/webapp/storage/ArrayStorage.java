package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        storage = new Resume[10000];
        size = 0;
    }

    public void save(Resume r) {
        if (size < storage.length){
            storage[size] = r;
            size++;
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)){
                System.arraycopy(storage, i + 1, storage, i, storage.length - i - 1);
                size--;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] all = new Resume[size];
        System.arraycopy(storage, 0, all, 0, size);
        return all;
    }

    public int size() {
        return size;
    }
}
