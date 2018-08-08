package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage{

    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchResume = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchResume, RESUME_COMPARATOR);
    }

    @Override
    protected void putResume(int index, Resume resume) {
        index = ~index;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    protected void removeResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index);
    }

}
