package com.servicios;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Tardis {
    public final AtomicBoolean destinoAlcanzado = new AtomicBoolean(false);
    public final AtomicReference<String> eraGanadora = new AtomicReference<>(null);

    class Viaje implements Runnable {
        final String era;

        public Viaje(String era) {
            this.era = era;
        }

        @Override
        public void run() {
            int tiempo = ThreadLocalRandom.current().nextInt(500, 2001);
            
            try {
                Thread.sleep(tiempo);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            if (destinoAlcanzado.compareAndSet(false, true)) {
                eraGanadora.set(era);
                System.out.println("La TARDIS llegó primero a " + era);
            }
        }
    }

    public void iniciar() throws InterruptedException {
        String[] eras = {"Roma Antigua", "Futuro Lejano", "Era Victoriana", "Año 3000"};
        ArrayList<Thread> hilos = new ArrayList<>();

        for (String e : eras) {
            Thread t = new Thread(new Viaje(e));
            hilos.add(t);
            t.start();
        }

        for (Thread t : hilos) {
            t.join();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Tardis tardis = new Tardis();
        tardis.iniciar();
    }
}
