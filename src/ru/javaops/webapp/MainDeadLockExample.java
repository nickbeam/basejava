package ru.javaops.webapp;

public class MainDeadLockExample {

    public static void main(String[] args) {
        Account accountA = new Account(1, "AAA", 100000.);
        Account accountB = new Account(2, "BBB", 50000.);

        for (int i = 1; i < 100000; i++) {
            Thread thread01 = new Thread(() -> {
                doTransfer(accountA, accountB, 1);
                System.out.println(accountA.getAmount() + " " + accountB.getAmount());
            });
            thread01.start();

            Thread thread02 = new Thread(() -> {
                doTransfer(accountB, accountA, 1);
                System.out.println(accountA.getAmount() + " " + accountB.getAmount());
            });
            thread02.start();
        }
    }

    private static void doTransfer(Account fromAcc, Account toAcc, double amount) {
        synchronized (fromAcc) {
            if (fromAcc.getAmount() < amount) {
                System.out.println("Error, not enough amount at account ID: " + fromAcc.getId());
            } else {
                synchronized (toAcc) {
                    fromAcc.credit(amount);
                    toAcc.debit(amount);
                }
            }
        }
    }

    private static class Account {
        private final int id;
        private final String owner;
        private double amount;

        Account(int id, String owner, double amount) {
            this.id = id;
            this.owner = owner;
            this.amount = amount;
        }

        public int getId() {
            return id;
        }

        public String getOwner() {
            return owner;
        }

        public double getAmount() {
            return amount;
        }

        private void debit(double amount) {
            this.amount += amount;
        }

        private void credit(double amount) {
            this.amount -= amount;
        }
    }
}
