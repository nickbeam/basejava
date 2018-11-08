package ru.javaops.webapp;

import ru.javaops.webapp.util.LazySingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

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
        CountDownLatch latch = new CountDownLatch(THREADS_COUNT);
        ExecutorService executorService = Executors.newCachedThreadPool();
//        List<Thread> threads = new ArrayList<>(THREADS_COUNT);
        for (int i = 0; i < THREADS_COUNT; i++) {
            executorService.submit(() -> {
                {
                    for (int j = 0; j < 100; j++) {
                        mainConcurancy.inc();
                    }
                    latch.countDown();
                }
            });
            /*Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        mainConcurancy.inc();
                    }
                    latch.countDown();
                }
            });
            thread.start();*/
            //thread.join();
//            threads.add(thread);
        }

        System.out.println(counter);

//        threads.forEach(thread -> {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
        //Thread.sleep(500);
        LazySingleton.getInstance();
        System.out.println(counter);

        String lock1 = "lock1";
        String lock2 = "lock2";
//        doDeadLock(lock1, lock2);
//        doDeadLock(lock2, lock1);
    }

    private static void doDeadLock(String lock1, String lock2) {
        new Thread(() -> {
            synchronized (lock1){
                System.out.println("Holding lock1");
                System.out.println("Waiting lock2");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2){
                    System.out.println("Holding lock2");
                }
            }
        }).start();
    }

    private void inc() {
        double sin = Math.sin(123);
        synchronized (this) {
            counter++;
        }
    }
}
