package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.io.*;
import java.nio.file.Path;

public class ObjectStreamPathStorage extends AbstractPathStorage {
    protected ObjectStreamPathStorage(String directory) {
        super(directory);
    }

    @Override
    protected Resume doRead(InputStream inputStream) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Resume read error", null, e);
        }
    }

    @Override
    protected void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
            oos.writeObject(resume);
        }
    }
}
