package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void putResume(int index, Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void removeResume(int index) {
        storage[index] = storage[size];
    }
}
