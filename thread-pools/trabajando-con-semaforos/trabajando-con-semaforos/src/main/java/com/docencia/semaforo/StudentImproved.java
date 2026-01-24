package com.docencia.semaforo;


import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

class StudentImproved extends Thread {
    private final String name;
    private final Semaphore semaphore;

    public StudentImproved(String name, Semaphore semaphore) {
        this.name = name;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();

            int usedComputer = semaphore.availablePermits() + 1;

            System.out.println(name + " ha comenzado a utilizar el equipo " + usedComputer);

            Thread.sleep(ThreadLocalRandom.current().nextInt(3000, 5001));

            System.out.println(name + " ha finalizado con el equipo " + usedComputer);

            semaphore.release();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}