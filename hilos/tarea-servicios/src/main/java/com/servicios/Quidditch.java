package com.servicios;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Quidditch {
    final AtomicBoolean snitchAtrapada = new AtomicBoolean(false);
    private int puntosEquipoA = 0;
    private int puntosEquipoB = 0;
    final ReentrantLock m = new ReentrantLock();

    class CazadorA implements Runnable {
        @Override
        public void run() {
            while (!snitchAtrapada.get()) {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(200, 501));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                int g = ThreadLocalRandom.current().nextInt(0, 2) * 10;
                if (g > 0) {
                    m.lock();
                    try {
                        puntosEquipoA += g;
                        System.out.println("Equipo A anota 10. Total A=" + puntosEquipoA);
                    } finally {
                        m.unlock();
                    }
                }
            }
        }
    }

    class CazadorB implements Runnable {
        @Override
        public void run() {
            while (!snitchAtrapada.get()) {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(200, 501));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                int g = ThreadLocalRandom.current().nextInt(0, 2) * 10;
                if (g > 0) {
                    m.lock();
                    try {
                        puntosEquipoB += g;
                        System.out.println("Equipo B anota 10. Total B=" + puntosEquipoB);
                    } finally {
                        m.unlock();
                    }
                }
            }
        }
    }

    class Buscador implements Runnable {
        @Override
        public void run() {
            while (!snitchAtrapada.get()) {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(300, 701));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                if (ThreadLocalRandom.current().nextInt(1, 101) <= 15) {
                    snitchAtrapada.set(true);
                    System.out.println("¡Snitch dorada atrapada!");
                }
            }
        }
    }

    public void iniciar() throws InterruptedException {
        Thread t1 = new Thread(new CazadorA());
        Thread t2 = new Thread(new CazadorB());
        Thread t3 = new Thread(new Buscador());

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Marcador final: A=" + puntosEquipoA + " B=" + puntosEquipoB);
    }

    public static void main(String[] args) throws InterruptedException {
        Quidditch juego = new Quidditch();
        juego.iniciar();
    }
}

