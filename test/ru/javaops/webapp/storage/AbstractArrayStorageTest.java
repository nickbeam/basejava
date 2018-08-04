package ru.javaops.webapp.storage;

import org.junit.Assume;
import org.junit.Test;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import static org.junit.Assert.fail;
import static ru.javaops.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    public AbstractArrayStorageTest(IStorage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void storageOverflow() {
        Assume.assumeFalse(storage.getClass().getName().contains("ListStorage") || storage.getClass().getName().contains("MapStorage"));
        try {
            for (int i = storage.size(); i < STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (Exception e) {
            fail("Storage is not full filled! " + "Exception: " + e.getMessage());
        }
        storage.save(new Resume());
    }
}
