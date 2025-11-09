package com.servicios;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MilleniumFalcon {
    final AtomicBoolean fin = new AtomicBoolean(false);
    final AtomicBoolean destruida = new AtomicBoolean(false);
    final int tiempoMisionMS = 4000;
    private long inicio;

    final AtomicInteger velocidad = new AtomicInteger(0);
    final AtomicInteger escudos = new AtomicInteger(100);

    class HanSolo implements Runnable {
        @Override
        public void run() {
            while (!fin.get()) {
                int incremento = ThreadLocalRandom.current().nextInt(5, 16);
                velocidad.addAndGet(incremento);
                
                if (ThreadLocalRandom.current().nextInt(1, 101) <= 5) {
                    destruida.set(true);
                    fin.set(true);
                    System.out.println("Fallo de hiperimpulsor. ¡La nave se destruye!");
                }
                
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                
                if (System.currentTimeMillis() - inicio >= tiempoMisionMS) {
                    fin.set(true);
                }
            }
        }
    }

    class Chewbacca implements Runnable {
        @Override
        public void run() {
            while (!fin.get()) {
                int cambio = ThreadLocalRandom.current().nextInt(-10, 6);
                escudos.addAndGet(cambio);
                
                if (escudos.get() <= 0) {
                    destruida.set(true);
                    fin.set(true);
                    System.out.println("¡Escudos agotados! La nave se destruye!");
                }
                
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                
                if (System.currentTimeMillis() - inicio >= tiempoMisionMS) {
                    fin.set(true);
                }
            }
        }
    }

    public void iniciar() throws InterruptedException {
        inicio = System.currentTimeMillis();
        
        Thread t1 = new Thread(new HanSolo());
        Thread t2 = new Thread(new Chewbacca());

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        if (!destruida.get()) {
            System.out.println("¡Han y Chewie escapan! Vel=" + velocidad.get() + ", Escudos=" + escudos.get());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MilleniumFalcon nave = new MilleniumFalcon();
        nave.iniciar();
    }
}

