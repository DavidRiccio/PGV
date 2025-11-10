package com.tarea3;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class PokemonBattle {
    AtomicBoolean  juegoTerminado = new AtomicBoolean(false);
    int hpPikachu = 100;
    int hpCharmander = 100;
    String turno = "Pikachu";
    ReentrantLock m = new ReentrantLock();
    Condition turnoCambio;

    
    }
