package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected abstract Resume doRead(InputStream inputStream) throws IOException;

    protected abstract void doWrite(Resume resume, OutputStream outputStream) throws IOException;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(dir + " is not directory");
        }
        if (!Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not readable/writable");
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            if (path.createNewPath()) {
                doUpdate(resume, file);
            }
        } catch (IOException e) {
            throw new StorageException("File create error " + file.getAbsolutePath(), file.getName(), e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return new Path(directory, uuid);
    }

    @Override
    protected Resume doGet(Path file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(Resume resume, Path file) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(Path file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    protected boolean isExist(Path file) {
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
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        try {
            return Math.toIntExact(Files.list(directory).count());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private File[] getFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory error " + directory.getName());
        }
        return files;
    }
}
