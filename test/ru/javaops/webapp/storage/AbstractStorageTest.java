package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Collection;

public abstract class AbstractStorageTest{
    protected final Collection<Resume> storage;

    public AbstractStorageTest(Collection<Resume> storage){
        this.storage = storage;
    }

}
