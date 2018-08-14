package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.List;

public interface IStorage {

    void clear();

    Resume get(String uuid);

    void update(Resume resume);

    void save(Resume resume);

    void delete(String uuid);

    //Resume[] getAll();
    //return sorted by fullName List
    List<Resume> getAllSorted();

    int size();
}
