package com.servicios;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BatallaPokemon {
     final AtomicBoolean juegoTerminado = new AtomicBoolean(false);
     int hpPikachu = 100;
     int hpCharmander = 100;
     String turno = "Pikachu";
     final ReentrantLock m = new ReentrantLock();
     final Condition turnoCambio = m.newCondition();

    private void atacar(String atacante, boolean esPikachu) {
        int daño = ThreadLocalRandom.current().nextInt(5, 21);
        
        if (esPikachu) {
            hpCharmander -= daño;
            System.out.println(atacante + " ataca con " + daño + " de daño. HP rival: " + hpCharmander);
            
            if (hpCharmander <= 0 && !juegoTerminado.get()) {
                juegoTerminado.set(true);
                System.out.println(atacante + " ha ganado la batalla!");
            }
        } else {
            hpPikachu -= daño;
            System.out.println(atacante + " ataca con " + daño + " de daño. HP rival: " + hpPikachu);
            
            if (hpPikachu <= 0 && !juegoTerminado.get()) {
                juegoTerminado.set(true);
                System.out.println(atacante + " ha ganado la batalla!");
            }
        }
        
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(200, 601));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    class HiloPikachu implements Runnable {
        @Override
        public void run() {
            while (!juegoTerminado.get()) {
                m.lock();
                try {
                    while (!turno.equals("Pikachu") && !juegoTerminado.get()) {
                        turnoCambio.await();
                    }
                    if (juegoTerminado.get()) {
                        return;
                    }
                    
                    atacar("Pikachu", true);
                    turno = "Charmander";
                    turnoCambio.signal();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                } finally {
                    m.unlock();
                }
            }
        }
    }

    class HiloCharmander implements Runnable {
        @Override
        public void run() {
            while (!juegoTerminado.get()) {
                m.lock();
                try {
                    while (!turno.equals("Charmander") && !juegoTerminado.get()) {
                        turnoCambio.await();
                    }
                    if (juegoTerminado.get()) {
                        return;
                    }
                    
                    atacar("Charmander", false);
                    turno = "Pikachu";
                    turnoCambio.signal();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                } finally {
                    m.unlock();
                }
            }
        }
    }

    public void iniciar() throws InterruptedException {
        Thread t1 = new Thread(new HiloPikachu());
        Thread t2 = new Thread(new HiloCharmander());
        
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
    }

    public static void main(String[] args) throws InterruptedException {
        BatallaPokemon batalla = new BatallaPokemon();
        batalla.iniciar();
    }
}
