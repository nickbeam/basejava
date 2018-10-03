package ru.javaops.webapp.storage.serialize;

import ru.javaops.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ISerializeStrategy {
    void doWrite(Resume resume, OutputStream outputStream) throws IOException;

    Resume doRead(InputStream inputStream) throws IOException;
}
