package ru.javaops.webapp;

import ru.javaops.webapp.util.LazySingleton;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrancy {
    private static int counter;
    private static final Object LOCK = new Object();
    private static final int THREADS_COUNT = 10000;

    public static void main(String[] args) throws InterruptedException {

        Thread thread0 = new Thread() {
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };
        thread0.start();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread currentThred = Thread.currentThread();
                System.out.println(currentThred.getName() + ", " + currentThred.getState());

                throw new IllegalStateException();
            }
        });
        thread1.start();


        new Thread(() -> {
            Thread currentThread = Thread.currentThread();
            System.out.println(currentThread.getName() + ", " + currentThread.getState());
        }).start();

        System.out.println(thread0.getName() + ", " + thread0.getState());
        System.out.println(thread1.getName() + ", " + thread1.getState());

        //Thread.currentThread().sleep(500);
        //Thread.currentThread().join();
        //System.out.println(counter);

        //final Object lock2 = new Object();
        final MainConcurrancy mainConcurancy = new MainConcurrancy();
        List<Thread> threads = new ArrayList<>(THREADS_COUNT);
        for (int i = 0; i < THREADS_COUNT; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        mainConcurancy.inc();
                    }
                }
            });
            thread.start();
            //thread.join();
            threads.add(thread);
        }

        System.out.println(counter);

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        //Thread.sleep(500);
        LazySingleton.getInstance();
        System.out.println(counter);
    }

    private void inc() {
        double sin = Math.sin(123);
        synchronized (this) {
            counter++;
        }
    }
}
