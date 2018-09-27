package ru.javaops.webapp.storage.serialize;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.io.*;

public class StreamSerialize implements ISerializeStrategy {
    @Override
    public void doWrite(Resume resume, OutputStream outputStream) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(resume);
        } catch (IOException e) {
            throw new StorageException("Resume write error", resume.getUuid(), e);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return (Resume) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new StorageException("Resume read error", null, e);
        }
    }
}
