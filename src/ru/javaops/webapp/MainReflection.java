package ru.javaops.webapp;

import ru.javaops.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IllegalAccessException {
        Resume r = new Resume("dummy");
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        System.out.println(r);

        Class resumeClass = Resume.class;
        try {
            Method method = resumeClass.getDeclaredMethod("toString", (Class<?>[]) null);
            System.out.println(method.invoke(r, (Object[]) null));
        } catch (NoSuchMethodException | InvocationTargetException e) {
            System.out.println(e.getMessage());
        }

    }
}
