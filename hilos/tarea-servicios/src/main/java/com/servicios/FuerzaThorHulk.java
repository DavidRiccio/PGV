package com.servicios;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class FuerzaThorHulk {
    final int durationMS = 5000;
    final AtomicBoolean tiempoTerminado = new AtomicBoolean(false);
    final AtomicInteger totalThor = new AtomicInteger(0);
    final AtomicInteger totalHulk = new AtomicInteger(0);

    class Temporizador implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(durationMS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            
            tiempoTerminado.set(true);
            System.out.println("¡Tiempo!");
        }
    }

    class Thor implements Runnable {
        @Override
        public void run() {
            while (!tiempoTerminado.get()) {
                int peso = ThreadLocalRandom.current().nextInt(5, 21);
                totalThor.addAndGet(peso);
                
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(50, 121));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    class Hulk implements Runnable {
        @Override
        public void run() {
            while (!tiempoTerminado.get()) {
                int peso = ThreadLocalRandom.current().nextInt(5, 21);
                totalHulk.addAndGet(peso);
                
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(50, 121));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    public void iniciar() throws InterruptedException {
        Thread t0 = new Thread(new Temporizador());
        Thread t1 = new Thread(new Thor());
        Thread t2 = new Thread(new Hulk());

        t0.start();
        t1.start();
        t2.start();

        t0.join();
        t1.join();
        t2.join();

        int valorThor = totalThor.get();
        int valorHulk = totalHulk.get();

        if (valorThor > valorHulk) {
            System.out.println("Thor gana con " + valorThor + " vs " + valorHulk);
        } else if (valorHulk > valorThor) {
            System.out.println("Hulk gana con " + valorHulk + " vs " + valorThor);
        } else {
            System.out.println("Empate: " + valorThor);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        FuerzaThorHulk competencia = new FuerzaThorHulk();
        competencia.iniciar();
    }
}

