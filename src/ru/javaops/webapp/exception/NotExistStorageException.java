package ru.javaops.webapp.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("Error: resume with uuid: " + uuid + " not found!", uuid);
    }
}
