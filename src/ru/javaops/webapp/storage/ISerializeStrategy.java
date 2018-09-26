package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.io.InputStream;
import java.io.OutputStream;

public interface ISerializeStrategy {
    void doWrite(Resume resume, OutputStream outputStream);

    Resume doRead(InputStream inputStream);
}
