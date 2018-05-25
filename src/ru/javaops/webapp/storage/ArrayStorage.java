package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    private int getUuidIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void update(Resume resume) {
        int index = getUuidIndex(resume.getUuid());
        if (index >= 0){
            storage[index] = resume;
        } else {
            System.out.println("Error: resume with uuid: " + resume.getUuid() + " not found!");
        }
    }

    public void save(Resume resume) {
        if (size < storage.length) {
            if (getUuidIndex(resume.getUuid()) < 0) {
                storage[size] = resume;
                size++;
            } else {
                System.out.println("Error: resume with uuid: " + resume.getUuid() + " already exist!");
            }
        } else {
            System.out.println("Resume storage is full!");
        }
    }

    public Resume get(String uuid) {
        int index = getUuidIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.println("Error: resume with uuid: " + uuid + " not found!");
        return null;
    }

    public void delete(String uuid) {
        int index = getUuidIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Error: resume with uuid: " + uuid + " not found!");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
