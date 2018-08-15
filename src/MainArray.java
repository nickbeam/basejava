import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainArray {
    //private final static IStorage ARRAY_STORAGE = new ArrayStorage();
    private final static IStorage ARRAY_STORAGE = new SortedArrayStorage();
    //private final static IStorage ARRAY_STORAGE = new ListStorage();
    //private final static IStorage ARRAY_STORAGE = new MapUuidStorage();
    //private final static IStorage ARRAY_STORAGE = new MapFullNameStorage();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | save uuid | delete uuid | get uuid | clear | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 3) {
                System.out.println("Неверная команда.");
                continue;
            }
            String uuid = null;
            String fullName = null;
            if (params.length == 2) {
                uuid = params[1].intern();
            }
            if (params.length == 3) {
                uuid = params[1].intern();
                fullName = params[2].intern();
            }
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(ARRAY_STORAGE.size());
                    break;
                case "save":
                    r = new Resume(uuid, fullName);
                    ARRAY_STORAGE.save(r);
                    printAll();
                    break;
                case "delete":
                    ARRAY_STORAGE.delete(uuid);
                    printAll();
                    break;
                case "get":
                    System.out.println(ARRAY_STORAGE.get(uuid));
                    break;
                case "clear":
                    ARRAY_STORAGE.clear();
                    printAll();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    private static void printAll() {
        List<Resume> all = ARRAY_STORAGE.getAllSorted();
        System.out.println("----------------------------");
        if (all.size() == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r.getUuid() + " " + r.getFullName());
            }
        }
        System.out.println("----------------------------");
    }
}