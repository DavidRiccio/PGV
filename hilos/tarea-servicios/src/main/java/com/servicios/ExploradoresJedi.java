package com.servicios;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ExploradoresJedi {
    public final AtomicBoolean pistaEncontrada = new AtomicBoolean(false);
    final AtomicReference<String> ganador = new AtomicReference<>(null);

    class Jedi implements Runnable {
        final String nombre;
        final String planeta;

        public Jedi(String nombre, String planeta) {
            this.nombre = nombre;
            this.planeta = planeta;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(400, 1501));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            if (pistaEncontrada.compareAndSet(false, true)) {
                ganador.set(nombre);
                System.out.println(nombre + " halló una pista en " + planeta + ". Fin de búsqueda.");
            }
        }
    }

    public void iniciar() throws InterruptedException {
        Thread t1 = new Thread(new Jedi("Kenobi", "Tatooine"));
        Thread t2 = new Thread(new Jedi("Skywalker", "Dagobah"));

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    public static void main(String[] args) throws InterruptedException {
        ExploradoresJedi exploracion = new ExploradoresJedi();
        exploracion.iniciar();
    }
}
