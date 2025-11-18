# Explicación Detallada: `ColorSemaphore.java`

Este documento detalla el funcionamiento interno del programa `ColorSemaphore.java`, que simula un semáforo utilizando hilos y la clase `Semaphore` de Java.

## Resumen General

El programa utiliza tres hilos para representar los colores (Rojo, Ámbar, Verde). Un `Semaphore` configurado con **un solo permiso** actúa como un mecanismo de control de turno (un *mutex*), asegurando que solo un color pueda estar "activo" a la vez. La política "justa" del semáforo garantiza que los hilos se turnen en el orden en que lo solicitan.

---

## Flujo de Ejecución Interno (Paso a Paso)

A continuación se describe el flujo de ejecución desde que se inicia el programa hasta que finaliza.

### 1. Inicio en `main`
1.  El hilo principal del programa crea tres objetos `Thread`: `tRojo`, `tAmbar` y `tVerde`.
2.  Cada hilo se asocia con una tarea `ColorSemaphore` que conoce su propio color.
3.  Se invoca el método `.start()` en cada hilo. Esto los deja en estado "listo para ejecutar" (Runnable). El planificador de hilos de la JVM decide quién se ejecuta primero.

### 2. La Competición por el Semáforo
1.  Los tres hilos arrancan su método `run()` casi simultáneamente y llegan a la línea `turn.acquire()`.
2.  El `Semaphore turn` tiene **1 permiso** disponible.
3.  Como el semáforo es **justo** (`new Semaphore(1, true)`), los hilos se forman en una cola de espera invisible en el orden en que llegaron (ej: Rojo -> Ámbar -> Verde).

### 3. El Turno de `tRojo`
1.  `tRojo`, al ser el primero, **adquiere el único permiso**. El contador de permisos del semáforo baja a **0**.
2.  `tAmbar` y `tVerde` intentan hacer lo mismo, pero al no haber permisos, **se bloquean**. Quedan en un estado de espera sin consumir CPU.
3.  `tRojo` continúa, imprime "Color actual: ROJO" y se duerme 3 segundos. **Importante**: mientras duerme, sigue reteniendo el permiso.

### 4. Liberación y Siguiente Turno (`tAmbar`)
1.  `tRojo` se despierta y ejecuta `turn.release()`. El contador de permisos del semáforo vuelve a ser **1**.
2.  El semáforo, que gestiona la cola, despierta a `tAmbar` y le entrega el permiso. El contador vuelve a **0**.
3.  `tRojo` vuelve al inicio de su bucle `while` y ahora es él quien se bloquea en `turn.acquire()`, esperando al final de la cola.
4.  `tAmbar` imprime "Color actual: AMBAR" y se duerme 1 segundo.

### 5. El Ciclo Continúa (`tVerde`)
1.  El proceso se repite: `tAmbar` se despierta, libera el permiso.
2.  El semáforo despierta a `tVerde`, que adquiere el permiso.
3.  `tAmbar` se bloquea al volver a pedir el permiso.
4.  `tVerde` actúa y se duerme.

Este ciclo de **adquirir -> actuar -> liberar -> esperar** se repite, forzando la secuencia ordenada de los colores.

### 6. La Parada del Semáforo
1.  Mientras los hilos se turnan, el hilo `main` ha estado durmiendo 20 segundos.
2.  Al despertar, `main` ejecuta `ColorSemaphore.switchOff()`. La variable `static volatile boolean isActive` se establece en `false`. `volatile` asegura que todos los hilos vean este cambio inmediatamente.
3.  A continuación, `main` llama al método `.interrupt()` de cada uno de los tres hilos.

### 7. Finalización de los Hilos
La interrupción provoca uno de los siguientes escenarios para cada hilo:
-   **Si estaba durmiendo (`Thread.sleep`)**: El sueño se interrumpe y lanza una `InterruptedException`. El bloque `finally` se asegura de que el permiso se libere (`turn.release()`) antes de que el hilo termine.
-   **Si estaba esperando (`turn.acquire`)**: La espera también se interrumpe con una `InterruptedException`, y el hilo finaliza.
-   **Si justo había terminado un ciclo**: Comprobará que `isActive` es `false` y saldrá del bucle `while` de forma natural.

Una vez que los tres hilos han finalizado, el hilo `main` imprime el mensaje final de apagado.
