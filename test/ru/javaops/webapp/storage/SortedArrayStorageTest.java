package ru.javaops.webapp.storage;

import static org.junit.Assert.*;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    private final static IStorage ARRAY_STORAGE = new SortedArrayStorage();

    public SortedArrayStorageTest() {
        super(ARRAY_STORAGE);
    }
}