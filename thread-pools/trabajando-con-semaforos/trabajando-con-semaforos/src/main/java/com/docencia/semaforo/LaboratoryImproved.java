package com.docencia.semaforo;


import java.util.concurrent.Semaphore;

public class LaboratoryImproved {
    public static void main(String[] args) {

        Semaphore teamSemaphore = new Semaphore(4);

        Thread[] students = new Thread[6];

        for (int i = 0; i < 6; i++) {
            students[i] = new StudentImproved("Estudiante " + (i + 1), teamSemaphore);
            students[i].start();
        }

        for (Thread t : students) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Todos los estudiantes han terminado.");
    }
}