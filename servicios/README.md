# PROYECTO DE CONCURRENCIA EN JAVA
Ejercicios Prácticos de Hilos y Sincronización

## QUE ES ESTO

Este proyecto tiene varios ejercicios de concurrencia en Java donde múltiples hilos corren al mismo tiempo y tienen que coordinarse entre ellos. Todos están basados en ejemplos divertidos (batallas Pokemon, Star Wars, Harry Potter, etc.).

## EJEMPLO COMPLETO: BATALLAMAGOS

Gandalf y Saruman se lanzan hechizos al mismo tiempo hasta que uno se queda sin energía. Este es el ejercicio más completo porque combina locks, variables atómicas y sincronización.

### CODIGO COMPLETO
```java
java
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
        
        System.out.println(atacante + " lanza hechizo por " + daño + 
                           ". Energía rival: " + nuevaEnergia);
        
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
```
## 🔧 Qué hace cada parte

### `AtomicInteger` energiaGandalf y energiaSaruman
Almacenan la energía de cada mago (empiezan con 120).

**Métodos principales:**
- `addAndGet(-daño)`: resta el daño y devuelve la nueva energía en una operación atómica
- `get()`: lee el valor actual

Son atómicos para que varios hilos puedan leerlos/modificarlos sin problemas.

---

### `AtomicBoolean` combateTerminado
Indica si el combate terminó.

**Métodos principales:**
- `compareAndSet(false, true)`: cambia a true solo si todavía es false

Esto evita que ambos magos se declaren ganadores al mismo tiempo.

---

### `ReentrantLock` m
Es el "candado" que controla quién puede atacar.

**Métodos principales:**
- `lock()`: toma el candado (espera si otro lo tiene)
- `unlock()`: suelta el candado

El `try-finally` garantiza que siempre se suelta, incluso si hay error.

---

### `ThreadLocalRandom`
Genera números aleatorios rápido en contextos multihilo.

**Métodos principales:**
- `nextInt(8, 26)`: genera daño entre 8 y 25
- `nextInt(200, 601)`: genera tiempo de espera entre 200 y 600 ms


## ⚔️ Cómo funciona

1. **Se crean dos hilos**: Gandalf y Saruman
2. Ambos entran en un **bucle infinito** que verifica si el combate terminó
3. Cada uno intenta tomar el **lock** (solo uno puede tenerlo a la vez)
4. El que lo consigue lanza un **hechizo** que resta energía al rival
5. Si la energía llega a 0, usa `compareAndSet` para declararse ganador
6. Solo el **primero** en ejecutar `compareAndSet` se declara ganador (el otro falla)
7. Suelta el lock y se **duerme** un tiempo aleatorio
8. Vuelve al paso 2 hasta que `combateTerminado` sea `true`

---

## 📋 Ejemplo de salida



EJEMPLO DE SALIDA

```bash
Gandalf lanza hechizo por 15. Energía rival: 105
Saruman lanza hechizo por 22. Energía rival: 98
Gandalf lanza hechizo por 12. Energía rival: 93
Saruman lanza hechizo por 19. Energía rival: 79
...
Gandalf lanza hechizo por 18. Energía rival: -3
Gandalf gana la batalla mágica.
```
## ✅ Test del ejercicio
```java
@Test
public void testBatallaMagos_debeHaberGanadorYTerminar() throws InterruptedException {
    BatallaMagos b = new BatallaMagos();
    b.iniciar();
    
    String salida = outputStream.toString();
    assertTrue(salida.contains("gana la batalla mágica."));
    assertTrue(b.combateTerminado.get());
    assertTrue(b.energiaGandalf.get() <= 0 || b.energiaSaruman.get() <= 0);
}
```
## 📚 Otros ejercicios del proyecto

| Ejercicio | Descripción |
|-----------|-------------|
| **BatallaPokemon** | Combate por turnos estrictos |
| **CazaHorrocruxes** | Carrera entre 3 buscadores |
| **FabricaDroids** | Productor-consumidor con cola |
| **Quidditch** | Juego con múltiples hilos |
| **ExploradoresJedi** | Carrera simple entre 2 Jedis |
| **Tardis** | Carrera entre 4 viajeros temporales |
| **CiudadEnPeligro** | Héroes salvando zonas |
| **FuerzaThorHulk** | Competencia con temporizador |
| **MilleniumFalcon** | Sistema complejo con múltiples condiciones |

---

