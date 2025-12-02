# 🧵 Java Concurrency: Guía Práctica de ExecutorService

Este repositorio contiene ejemplos prácticos para entender la transición de la gestión manual de hilos (`new Thread()`) al uso profesional de **Pools de Hilos** con el framework `java.util.concurrent`.

## 📚 Conceptos Fundamentales

Antes de ejecutar los ejemplos, es crucial entender los componentes principales:

* **ExecutorService (El Pool):** Es el "jefe de obra". Gestiona un número fijo de trabajadores (hilos). Si hay más tareas que hilos, las tareas esperan en una cola.
* **Runnable vs Callable:**
    * `Runnable`: Una tarea que ejecuta una acción pero **no devuelve nada** (*void*).
    * `Callable`: Una tarea que ejecuta una acción y **devuelve un resultado** (o lanza una excepción).
* **Future (El Recibo):** Un objeto que representa el resultado de una operación asíncrona. Sirve para monitorear el estado de la tarea o recuperar su valor una vez completada.

---

## 📂 Descripción de los Ejemplos

### 1. `CalculadoraDanoCritico.java` (Sync/Blocking)
Simulación de cálculos de daño en una batalla RPG.
* **Patrón:** `Request-Response`.
* **Mecanismo:** Usa `Callable<Integer>` y `pool.submit()`.
* **Clave:** El hilo principal ("Main") espera los resultados usando `Future.get()`.
* **Lección:** El método `.get()` es **bloqueante**. El programa se detiene ahí hasta que el cálculo termina.

### 2. `ServidorMazmorras.java` (Fire and Forget)
Simulación de peticiones de entrada de jugadores a mazmorras.
* **Patrón:** `Fire-and-Forget` (Disparar y olvidar).
* **Mecanismo:** Usa `Runnable` y `pool.execute()`.
* **Clave:** El Main lanza las tareas y termina su ejecución inmediatamente. Los hilos del pool siguen trabajando en segundo plano ("Daemon-like behavior" hasta que acaban).
* **Lección:** Ideal para tareas donde no necesitamos confirmar el resultado inmediatamente (ej: logs, notificaciones).

### 3. `SpawnsMundoAbierto.java` (Scheduling)
Simulación de aparición de enemigos en intervalos de tiempo.
* **Patrón:** `Scheduled Task` (Tarea programada).
* **Mecanismo:** Usa `ScheduledExecutorService` y `scheduleAtFixedRate()`.
* **Clave:** Introduce el factor **Tiempo**.
* **Lección (La Trampa del Tiempo):** Si una tarea programada cada 2 segundos tarda 3 segundos en ejecutarse, el cronograma se rompe (deslizamiento) y las tareas se ejecutan seguidas sin descanso, pero nunca en paralelo consigo mismas.

---

## 📊 Tabla Comparativa Resumen

| Característica | Calculadora (Dano) | Servidor (Mazmorras) | Spawns (Mundo) |
| :--- | :--- | :--- | :--- |
| **Interfaz** | `Callable<T>` | `Runnable` | `Runnable` |
| **Retorno** | Devuelve valor (`Integer`) | `void` | `void` |
| **Método Pool** | `submit()` | `execute()` | `scheduleAtFixedRate()` |
| **Control** | `Future` (Recibo) | Ninguno | `ScheduledFuture` |
| **Comportamiento** | El Main espera (`Blocking`) | El Main se va rápido | El Main duerme (`Sleep`) |
| **Tipo de Pool** | `FixedThreadPool` | `FixedThreadPool` | `ScheduledThreadPool` |

---

## ⚠️ Buenas Prácticas y "Gotchas"

1.  **Siempre cierra el Pool:** Si no llamas a `pool.shutdown()`, la JVM seguirá corriendo indefinidamente porque los hilos del pool se quedan esperando nuevas órdenes.
2.  **Cuidado con `.get()`:** Llamar a `future.get()` en el hilo principal de una aplicación gráfica (Swing/JavaFX) o en el hilo principal de un servidor bloqueará toda la aplicación hasta que la tarea termine.
3.  **Colas Infinitas:** `newFixedThreadPool` usa una cola de espera ilimitada. Si las tareas entran más rápido de lo que se procesan, puedes quedarte sin memoria (Out of Memory).
4.  **Shutdown Graceful:** Usa `awaitTermination` (como en el ejemplo 3) para dar tiempo a las tareas activas a terminar antes de matar el proceso.

