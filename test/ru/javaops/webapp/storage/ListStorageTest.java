package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorageTest extends AbstractStorageTest {
    public ListStorageTest() {
        super(new ArrayList<Resume>());
    }
}
