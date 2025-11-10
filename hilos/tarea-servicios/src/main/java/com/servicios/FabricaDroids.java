package com.servicios;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class FabricaDroids {
    final BlockingQueue<String> ensamblados = new ArrayBlockingQueue<>(10);
    public final int N = 10;
    public final AtomicInteger activados = new AtomicInteger(0);

    class Ensamblador implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i <= N; i++) {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(100, 301));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                String d = "Droid-" + i;
                System.out.println("Ensamblado " + d);
                
                try {
                    ensamblados.put(d);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    class Activador implements Runnable {
        @Override
        public void run() {
            int cuenta = 0;
            while (cuenta < N) {
                try {
                    String d = ensamblados.take();
                    System.out.println("Activado " + d);
                    activados.incrementAndGet();
                    cuenta++;
                    
                    Thread.sleep(ThreadLocalRandom.current().nextInt(50, 151));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    public void iniciar() throws InterruptedException {
        Thread tE = new Thread(new Ensamblador());
        Thread tA = new Thread(new Activador());

        tE.start();
        tA.start();

        tE.join();
        tA.join();
    }

    public static void main(String[] args) throws InterruptedException {
        FabricaDroids fabrica = new FabricaDroids();
        fabrica.iniciar();
    }
}
