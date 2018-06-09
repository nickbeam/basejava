package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index == -1){
            System.out.println("Error: resume with uuid: " + resume.getUuid() + " not found!");
        } else {
            storage[index] = resume;
        }
    }

    @Override
    protected void putResumeToStorage(Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void removeResumeFromStorage(int index) {
        storage[index] = storage[size];
        storage[size] = null;
    }
}
