import com.sun.deploy.util.ArrayUtil;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        storage = new Resume[10000];
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length; i++){
            if (storage[i] == null) {
                storage[i] = r;
                break;
            }
        }
    }

    Resume get(String uuid) {
        for (Resume i:storage) {
            if ((i != null) && (i.toString().equals(uuid))) return i;
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i].toString().equals(uuid)) {
                storage[i] = null;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] all = new Resume[0];
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                Resume[] tmp = new Resume[all.length + 1];
                System.arraycopy(all, 0, tmp, 0, all.length);
                System.arraycopy(storage, i, tmp, all.length, 1);
                all = tmp;
            }
        }
        return all;
    }

    int size() {
        int size = 0;
        for (Resume i:storage) {
            if (i != null) size++;
        }
        return size;
    }
}
