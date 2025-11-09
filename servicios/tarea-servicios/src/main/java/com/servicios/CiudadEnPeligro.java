package com.servicios;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CiudadEnPeligro {
    public final AtomicBoolean amenazaNeutralizada = new AtomicBoolean(false);
    final String[] zonasA = {"Norte", "Centro", "Este"};
    final String[] zonasB = {"Oeste", "Sur"};
    public final AtomicReference<String> ganador = new AtomicReference<>(null);

    class Superman implements Runnable {
        @Override
        public void run() {
            for (String zona : zonasA) {
                if (amenazaNeutralizada.get()) {
                    break;
                }
                
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(200, 501));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                
                System.out.println("Superman salvó " + zona);
            }
            
            if (amenazaNeutralizada.compareAndSet(false, true)) {
                ganador.set("Superman");
                System.out.println("Amenaza neutralizada por Superman");
            }
        }
    }

    class Batman implements Runnable {
    @Override
    public void run() {
        for (String zona : zonasB) {
            if (amenazaNeutralizada.get()) {
                break;
            }
            
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(300, 601));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            
            System.out.println("Batman salvó " + zona);
        }
        
        // CORRECCIÓN: amenazaNeutralizada (no amenazaNeutraliza)
        if (amenazaNeutralizada.compareAndSet(false, true)) {
            ganador.set("Batman");
            System.out.println("Amenaza neutralizada por Batman");
        }
    }
}


    public void iniciar() throws InterruptedException {
        Thread t1 = new Thread(new Superman());
        Thread t2 = new Thread(new Batman());

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    public static void main(String[] args) throws InterruptedException {
        CiudadEnPeligro ciudad = new CiudadEnPeligro();
        ciudad.iniciar();
    }
}
