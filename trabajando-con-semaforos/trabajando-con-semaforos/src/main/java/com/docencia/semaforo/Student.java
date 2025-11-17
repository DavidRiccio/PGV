package com.docencia.semaforo;


import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

class Student extends Thread {

    private final String name;
    private final Semaphore semaphore;

    public Student(String name, Semaphore semaphore) {
        this.name = name;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();

            System.out.println(name + " ha comenzado a utilizar el equipo.");

            Thread.sleep(ThreadLocalRandom.current().nextInt(3000, 5001));

            System.out.println(name + " ha finalizado con el equipo.");

            semaphore.release();
        } catch (Exception ignored) {
            Thread.currentThread().interrupt();
            semaphore.release();
        }
    }
}
