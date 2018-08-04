package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

public interface IStorage {

    void clear();

    Resume get(String uuid);

    void update(Resume resume);

    void save(Resume resume);

    void delete(String uuid);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll();

    int size();
}
