package ru.javaops.webapp.storage;

import static org.junit.Assert.*;

public class ArrayStorageTest extends AbstractArrayStorageTest {
    private final static IStorage ARRAY_STORAGE = new ArrayStorage();

    public ArrayStorageTest() {
        super(ARRAY_STORAGE);
    }
}