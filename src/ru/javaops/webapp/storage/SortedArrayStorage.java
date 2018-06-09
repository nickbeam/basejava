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
        if (index < 0){
            System.out.println("Error: resume with uuid: " + resume.getUuid() + " not found!");
        } else {
            delete(resume.getUuid());
            save(resume);
        }
    }

    @Override
    protected void putResumeToStorage(Resume resume) {
        int index = ~getIndex(resume.getUuid());
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    protected void removeResumeFromStorage(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index);
    }

}
