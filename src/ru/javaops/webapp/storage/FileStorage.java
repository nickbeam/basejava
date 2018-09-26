package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;
    private StreamSerialize streamSerialize;

//    protected abstract Resume doRead(InputStream inputStream) throws IOException;
//
//    protected abstract void doWrite(Resume resume, OutputStream outputStream) throws IOException;

    protected FileStorage(File directory, StreamSerialize streamSerialize) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.streamSerialize = streamSerialize;
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            if (file.createNewFile()) {
                doUpdate(resume, file);
            }
        } catch (IOException e) {
            throw new StorageException("File create error " + file.getAbsolutePath(), file.getName(), e);
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return streamSerialize.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            streamSerialize.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> resumes = new ArrayList<>();
        File[] files = getFiles();
        for (File file : files) {
            resumes.add(doGet(file));
        }
        return resumes;
    }

    @Override
    public void clear() {
        File[] files = getFiles();
        for (File file : files) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        File[] files = getFiles();
        return files.length;
    }

    private File[] getFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory error " + directory.getName());
        }
        return files;
    }
}
