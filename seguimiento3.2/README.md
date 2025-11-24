# Apuntes y Preguntas por Archivo

## `CalculadoraDanoCritico.java`

Este programa simula el cálculo de daño de una "raid" de un videojuego donde varios personajes atacan a la vez.

### Diferencia: `execute(Runnable)` vs `submit(Callable<V>)`

-   **`submit(Callable<V>)` → devuelve un `Future<V>` con un resultado.**
    En `CalculadoraDanoCritico`, la tarea `TareaCalcularDano` implementa `Callable<Integer>`, por lo que su método `call()` **devuelve un `Integer`**. Usamos `pool.submit()` que nos da un `Future<Integer>`. Este `Future` es una "promesa" de que tendremos un resultado. Para obtenerlo, hacemos `future.get()`, que espera a que el cálculo termine.
    
    **La salida demuestra esto**: el programa principal espera, recoge cada valor de daño y al final puede imprimir la suma total.
    
    ```
    [pool-1-thread-1] Calculando daño para Mago del Fuego
    ...
    Daño total de la raid: 1458 
    ```
    
-   **`execute(Runnable)` → no hay valor de retorno.**
    Se puede ver en el ejemplo de `ServidorMazmorras`. La tarea `PeticionMazmorra` implementa `Runnable`, y su método `run()` es `void` (no devuelve nada). Se usa `gmBots.execute(...)`. El hilo principal simplemente "dispara" la tarea y sigue adelante, no hay un `Future` ni se puede obtener un resultado de la tarea.
    
    **La salida demuestra esto**: el `main` imprime su mensaje final casi al instante, sin esperar a que las mazmorras estén listas.
    
    ```
    Servidor: todas las peticiones han sido enviadas a los GM bots.
    [pool-1-thread-1] Preparando mazmorra 'Catacumbas de Hyrule' para el jugador Link
    ...
    ```
    

### ¿Cómo se pueden lanzar muchos cálculos de daño en paralelo y luego recogerlos todos?

1.  **Crear una lista de `Future`**: `List<Future<Integer>> futuros = new ArrayList<>();`
2.  **Lanzar todas las tareas**: Se recorren todos los `ataques` y por cada uno se hace `pool.submit()`. El `Future` que devuelve se añade a la lista `futuros`. 
3.  **Recoger todos los resultados**: Se recorre la lista `futuros`. Por cada `future`, se llama a `future.get()`. Esta llamada **bloquea** el hilo principal hasta que el resultado de *esa tarea concreta* está disponible.

### Probar a cambiar la probabilidad de crítico y ver cómo sube/baja el daño total

Si en la clase `Ataque` subes la `probCritico` (ej: a `0.90`), el daño total promedio subirá. Si la bajas (ej: a `0.10`), el daño total promedio bajará.

---

## `ServidorMazmorras.java`

Este programa simula un servidor que gestiona peticiones de jugadores para entrar en mazmorras.

### Solo se usan 3 hilos (3 GM bots) para atender a todos los jugadores, ¿qué está sucediendo?

El `ExecutorService` (`gmBots`) utiliza una **cola de tareas**. Los 3 hilos cogen tareas de esa cola. Si llegan 10 peticiones, 7 esperan en la cola hasta que un hilo se libera.

### Los mismos hilos procesan varias peticiones → reutilización de hilos. ¿Qué significa esto?

En lugar de crear y destruir un hilo por cada petición (lo cual es costoso), el `ExecutorService` **reutiliza** un conjunto fijo de hilos. Cuando un hilo termina una tarea, coge la siguiente de la cola. Es mucho más eficiente.

### ¿Qué pasa si cambias el tamaño del pool a 1? ¿Y a 10?

-   **`newFixedThreadPool(1)`**: Las peticiones se procesan **de forma secuencial**, una tras otra.
-   **`newFixedThreadPool(10)`**: Como hay 10 jugadores, todas las peticiones se podrían atender **en paralelo** desde el principio.

Salida:
```bash
Servidor: todas las peticiones han sido enviadas a los GM bots.
[pool-1-thread-1] Preparando mazmorra 'Catacumbas de Hyrule' para el jugador Link
[pool-1-thread-5] Preparando mazmorra 'Nido de Dragón' para el jugador Gandalf
[pool-1-thread-7] Preparando mazmorra 'Torre Oscura' para el jugador Aragorn
[pool-1-thread-9] Preparando mazmorra 'Estrella de la Muerte' para el jugador Luke
[pool-1-thread-10] Preparando mazmorra 'Nido de Dragón' para el jugador DarthVader
[pool-1-thread-4] Preparando mazmorra 'Estrella de la Muerte' para el jugador Yennefer
[pool-1-thread-8] Preparando mazmorra 'Moria' para el jugador Leia
[pool-1-thread-2] Preparando mazmorra 'Torre Oscura' para el jugador Zelda
[pool-1-thread-3] Preparando mazmorra 'Moria' para el jugador Geralt
[pool-1-thread-6] Preparando mazmorra 'Catacumbas de Hyrule' para el jugador Frodo
[pool-1-thread-10] Mazmorra 'Nido de Dragón' lista para DarthVader 🎮
[pool-1-thread-4] Mazmorra 'Estrella de la Muerte' lista para Yennefer 🎮
[pool-1-thread-1] Mazmorra 'Catacumbas de Hyrule' lista para Link 🎮
[pool-1-thread-9] Mazmorra 'Estrella de la Muerte' lista para Luke 🎮
[pool-1-thread-8] Mazmorra 'Moria' lista para Leia 🎮
[pool-1-thread-2] Mazmorra 'Torre Oscura' lista para Zelda 🎮
[pool-1-thread-5] Mazmorra 'Nido de Dragón' lista para Gandalf 🎮
[pool-1-thread-7] Mazmorra 'Torre Oscura' lista para Aragorn 🎮
[pool-1-thread-6] Mazmorra 'Catacumbas de Hyrule' lista para Frodo 🎮
[pool-1-thread-3] Mazmorra 'Moria' lista para Geralt 🎮
```

---

## `SpawnsMundoAbierto.java`

Simula la aparición de enemigos (spawns) en diferentes zonas de un mundo abierto.

### `schedule(...)` vs `scheduleAtFixedRate(...)`

-   **`schedule(tarea, 5, TimeUnit.SECONDS)` → una vez en el futuro.**
    Significa que la `tarea` se ejecutará **una sola vez** después de que pasen 5 segundos desde el momento en que se llama. Es como poner una alarma que suena una vez.

-   **`scheduleAtFixedRate(tarea, 0, 2, TimeUnit.SECONDS)` → repetidamente, cada X tiempo.**
    Significa que la `tarea` se ejecutará por primera vez sin retardo (`0`), y a partir de ahí, el `ScheduledExecutorService` **intentará** ejecutarla de nuevo cada `2` segundos. El intervalo se mide desde el **inicio** de una ejecución hasta el **inicio** de la siguiente.

### ¿Cómo se comporta el sistema si la tarea tarda más que el período?

El `ScheduledExecutorService` **no iniciará una nueva ejecución si la anterior no ha terminado**. Si la tarea tarda 3 segundos y el período es de 2 segundos, el comportamiento es el siguiente:
1.  **t=0s**: Empieza la Tarea 1.
2.  **t=2s**: El plan era empezar la Tarea 2, pero la Tarea 1 sigue en ejecución. La Tarea 2 se pone en espera.
3.  **t=3s**: Termina la Tarea 1.
4.  **t=3s**: **Inmediatamente**, empieza la Tarea 2 para intentar "recuperar" el tiempo perdido. No espera otros 2 segundos.

**Modificación y resultado simulado:**
Añadimos un `Thread.sleep(3000)` en el método `run()` de `SpawnTarea`, manteniendo el período en 2 segundos.

```java
// Dentro de run()
System.out.println("[" + LocalTime.now() + "][" + hilo + "] Spawn de " + enemigo + " en " + zona);
try {
    System.out.println("[" + LocalTime.now() + "][" + hilo + "] La tarea durará 3 segundos...");
    Thread.sleep(3000); // Tarea (3s) > Periodo (2s)
} catch (InterruptedException e) { Thread.currentThread().interrupt(); }
```

**Salida simulada:**
Fíjate en las marcas de tiempo. La segunda tarea empieza a las `00:03`, justo cuando acaba la primera, no a las `00:02`.
```bash
[21:06:57.937895948][pool-1-thread-1] Spawn de Mecha-Dragón en Bosque Maldito
[21:06:57.966111279][pool-1-thread-1] La tarea durará 3 segundos...
[21:07:06.248430672][pool-1-thread-1] Tarea finalizada.
[21:07:06.249304382][pool-1-thread-1] Spawn de Slime Mutante en Ruinas Antiguas
[21:07:06.249423294][pool-1-thread-1] La tarea durará 3 segundos...
[21:07:03.475493025][pool-1-thread-1] Tarea finalizada.
[21:07:03.475899395][pool-1-thread-1] Spawn de Esqueleto Guerrero en Ruinas Antiguas
[21:07:03.475991896][pool-1-thread-1] La tarea durará 3 segundos...
[21:07:05.978227321][pool-1-thread-1] Tarea finalizada.
[21:07:05.978500941][pool-1-thread-1] Spawn de Esqueleto Guerrero en Ruinas Antiguas
[21:07:05.978614991][pool-1-thread-1] La tarea durará 3 segundos...
...
```

### Probar a cambiar el período y la duración del `sleep` del `main`

-   **Período 1s, `sleep` del `main` 4s**:
    -   **Cambio**: `scheduleAtFixedRate(..., 0, 1, SECONDS)` y `Thread.sleep(4000)`.
    -   **Resultado**: Se ejecutarán 4 tareas (en t=0, t=1, t=2, t=3) antes de que el `main` inicie el apagado a los 4 segundos.
-   **Período 3s, `sleep` del `main` 8s**:
    -   **Cambio**: `scheduleAtFixedRate(..., 0, 3, SECONDS)` y `Thread.sleep(8000)`.
    -   **Resultado**: Se ejecutarán 3 tareas (en t=0, t=3, t=6) antes de que el `main` inicie el apagado a los 8 segundos. La tarea de t=9 no llegará a ejecutarse.


