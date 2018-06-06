package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage{

    @Override
    protected int getIndex(String uuid) {
        Resume searchResume = new Resume();
        searchResume.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchResume);
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index == -1){
            System.out.println("Error: resume with uuid: " + resume.getUuid() + " not found!");
        } else {
            storage[index] = resume;
        }
    }

    @Override
    public void save(Resume resume) {
        int insertIndex = ~Arrays.binarySearch(storage, 0, size, resume);
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
        storage[insertIndex] = resume;
        size++;
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Error: resume with uuid: " + uuid + " not found!");
        } else {
            size--;
            System.arraycopy(storage, index + 1, storage, index, size - index);
        }
    }

}
