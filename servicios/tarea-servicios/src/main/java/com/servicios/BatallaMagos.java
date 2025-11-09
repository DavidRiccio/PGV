package com.servicios;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class BatallaMagos {
     final AtomicInteger energiaGandalf = new AtomicInteger(120);
     final AtomicInteger energiaSaruman = new AtomicInteger(120);
     final AtomicBoolean combateTerminado = new AtomicBoolean(false);
     final ReentrantLock m = new ReentrantLock();

    private void lanzarHechizo(String atacante, AtomicInteger energiaRival) {
        int daño = ThreadLocalRandom.current().nextInt(8, 26);
        int nuevaEnergia = energiaRival.addAndGet(-daño);
        
        System.out.println(atacante + " lanza hechizo por " + daño + ". Energía rival: " + nuevaEnergia);
        
        if (nuevaEnergia <= 0 && combateTerminado.compareAndSet(false, true)) {
            System.out.println(atacante + " gana la batalla mágica.");
        }
    }

    class Gandalf implements Runnable {
        @Override
        public void run() {
            while (!combateTerminado.get()) {
                m.lock();
                try {
                    if (!combateTerminado.get()) {
                        lanzarHechizo("Gandalf", energiaSaruman);
                    }
                } finally {
                    m.unlock();
                }
                
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(200, 601));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    class Saruman implements Runnable {
        @Override
        public void run() {
            while (!combateTerminado.get()) {
                m.lock();
                try {
                    if (!combateTerminado.get()) {
                        lanzarHechizo("Saruman", energiaGandalf);
                    }
                } finally {
                    m.unlock();
                }
                
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(200, 601));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    public void iniciar() throws InterruptedException {
        Thread t1 = new Thread(new Gandalf());
        Thread t2 = new Thread(new Saruman());

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    public static void main(String[] args) throws InterruptedException {
        BatallaMagos batalla = new BatallaMagos();
        batalla.iniciar();
    }
}

