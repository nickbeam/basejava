import com.sun.deploy.util.ArrayUtil;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    void clear() {
        storage = new Resume[10000];
        size = 0;
    }

    void save(Resume r) {
        if (size < storage.length){
            storage[size] = r;
            size++;
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)){
                Resume[] tmp = storage;
                System.arraycopy(storage, i + 1, tmp, i, storage.length - i - 1);
                storage = tmp;
                size--;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] all = new Resume[size];
        System.arraycopy(storage, 0, all, 0, size);
        return all;
    }

    int size() {
        return size;
    }
}
