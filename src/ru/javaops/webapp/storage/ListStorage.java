package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Iterator;

public class ListStorage extends AbstractStorage {

    public ListStorage() {
        super(new ArrayList<Resume>());
    }

    @Override
    public Resume get(String uuid) {
        Iterator<Resume> iterator = storage.iterator();
        while (iterator.hasNext()){
            if (iterator.next().getUuid().equals(uuid)){
                return iterator.next();
            }
        }
        return null;
    }

    @Override
    public void save(Resume resume) {
        storage.add(resume);
    }
}
