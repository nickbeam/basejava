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

    private int search(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void update(Resume r) {
        int index = search(r.getUuid());
        if (index >= 0){
            storage[index] = r;
        } else {
            System.out.println("Error: resume with uuid: " + r.getUuid() + " not found!");
        }
    }

    public void save(Resume r) {
        if (size < storage.length) {
            if (search(r.getUuid()) < 0) {
                storage[size] = r;
                size++;
            } else {
                System.out.println("Error: resume with uuid: " + r.getUuid() + " already exist!");
            }
        }
    }

    public Resume get(String uuid) {
        int index = search(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.println("Error: resume with uuid: " + uuid + " not found!");
        return null;
    }

    public void delete(String uuid) {
        int index = search(uuid);
        if (index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, storage.length - index - 1);
            size--;
        } else {
            System.out.println("Error: resume with uuid: " + uuid + " not found!");
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
