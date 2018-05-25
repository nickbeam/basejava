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
        Arrays.fill(storage, 0, size,  null);
        size = 0;
    }

    private int getIndexResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void update(Resume resume) {
        int index = getIndexResume(resume.getUuid());
        if (index >= 0){
            storage[index] = resume;
        } else {
            System.out.println("Error: resume with uuid: " + resume.getUuid() + " not found!");
        }
    }

    public void save(Resume resume) {
        if (size < storage.length) {
            if (getIndexResume(resume.getUuid()) < 0) {
                storage[size] = resume;
                size++;
            } else {
                System.out.println("Error: resume with uuid: " + resume.getUuid() + " already exist!");
            }
        } else {
            System.out.println("Error: resume storage is full!");
        }
    }

    public Resume get(String uuid) {
        int index = getIndexResume(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.println("Error: resume with uuid: " + uuid + " not found!");
        return null;
    }

    public void delete(String uuid) {
        int index = getIndexResume(uuid);
        if (index >= 0) {
            size--;
            storage[index] = storage[size];
            storage[size] = null;

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
