package com.servicios;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CazaHorrocruxes {
    public final AtomicBoolean encontrado = new AtomicBoolean(false);
    public final AtomicReference<String> ganador = new AtomicReference<>(null);

    class Buscador implements Runnable {
         final String nombre;
         final String ubicacion;

        public Buscador(String nombre, String ubicacion) {
            this.nombre = nombre;
            this.ubicacion = ubicacion;
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

            if (encontrado.compareAndSet(false, true)) {
                ganador.set(nombre);
                System.out.println(nombre + " encontró un Horrocrux en " + ubicacion + ". ¡Búsqueda terminada!");
            }
        }
    }

    public void iniciar() throws InterruptedException {
        Thread t1 = new Thread(new Buscador("Harry", "Bosque Prohibido"));
        Thread t2 = new Thread(new Buscador("Hermione", "Biblioteca Antigua"));
        Thread t3 = new Thread(new Buscador("Ron", "Mazmorras del castillo"));

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
    }

    public static void main(String[] args) throws InterruptedException {
        CazaHorrocruxes caza = new CazaHorrocruxes();
        caza.iniciar();
    }
}

