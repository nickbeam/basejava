import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.IStorage;
import ru.javaops.webapp.storage.SortedArrayStorage;

/**
 * Test for com.urise.webapp.storage.ru.javaops.webapp.storage.ArrayStorage
 */
public class MainTestArrayStorage {
    private static final IStorage ARRAY_STORAGE = new SortedArrayStorage();//ArrayStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume();
        r1.setUuid("uuid3");
        final Resume r2 = new Resume();
        r2.setUuid("uuid1");
        final Resume r3 = new Resume();
        r3.setUuid("uuid2");


        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        //Test update method
        printAll();
        r3.setUuid("uuid4");
        ARRAY_STORAGE.update(r3);

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());

        //Test storage overflow
        for (int i = 0; i < 100001; i++){
            Resume resume = new Resume();
            resume.setUuid("uuid" + i);
            ARRAY_STORAGE.save(resume);
        }
    }

    private static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
