# Explicación del Código de Semáforos en Java

Este programa simula un laboratorio con un número limitado de equipos y varios estudiantes que desean utilizarlos. Se utiliza un **Semáforo** para controlar el acceso concurrente a los recursos limitados.

## Cómo Funciona

### Clases Principales

1.  **`Laboratory.java`**: La clase que orquesta la simulación.
    *   Crea un `Semaphore` con un número fijo de "permisos". En este caso, `new Semaphore(4)` simula que hay **4 equipos** disponibles en el laboratorio.
    *   Crea múltiples hilos, cada uno representando a un `Student`. En este ejemplo, se crean **6 estudiantes**.
    *   Inicia todos los hilos de los estudiantes para que se ejecuten de forma concurrente.
    *   Espera a que todos los estudiantes terminen su trabajo antes de finalizar el programa principal.

2.  **`Student.java`**: Representa a un estudiante que necesita usar un equipo del laboratorio.
    *   Cada estudiante es un `Thread` (hilo) independiente.
    *   La lógica principal se encuentra en su método `run()`.

### Flujo de Ejecución Interno

El comportamiento del programa se basa en dos operaciones clave del semáforo: `acquire()` y `release()`.

1.  **Inicio**: El programa `main` crea el semáforo con 4 permisos y lanza los 6 hilos de `Student` simultáneamente.

2.  **Adquisición de Permisos (`acquire()`):**
    *   Cada hilo de `Student` intenta obtener un permiso del semáforo llamando a `semaphore.acquire()`.
    *   Los **primeros 4 estudiantes** que ejecutan esta línea obtienen un permiso con éxito. El contador interno del semáforo se decrementa con cada adquisición, pasando de 4 a 0.
    *   Estos 4 estudiantes entran en la "sección crítica": imprimen que han comenzado a usar el equipo y simulan el trabajo con `Thread.sleep()`.

3.  **Bloqueo de Hilos:**
    *   Cuando los **2 estudiantes restantes** intentan llamar a `semaphore.acquire()`, el semáforo ya no tiene permisos disponibles (su contador es 0).
    *   Estos 2 hilos son **bloqueados** por el sistema operativo. Quedan en una cola de espera, sin consumir CPU, hasta que un permiso sea liberado.

4.  **Liberación de Permisos (`release()`):**
    *   Cuando uno de los 4 estudiantes que estaban trabajando termina su `Thread.sleep()`, ejecuta `semaphore.release()`.
    *   Esta acción **incrementa el contador** de permisos del semáforo (pasa de 0 a 1).
    *   Al mismo tiempo, el semáforo **desbloquea a uno de los hilos** que estaba en la cola de espera.

5.  **Continuación del Ciclo:**
    *   El estudiante recién despertado adquiere el permiso liberado (el contador vuelve a 0) y comienza su trabajo.
    *   Este ciclo se repite: cada vez que un estudiante termina y libera un permiso, otro que estaba esperando puede comenzar.

6.  **Finalización:**
    *   El hilo `main` permanece en espera gracias a `join()` hasta que los 6 hilos de estudiantes han completado su ejecución (han pasado por `acquire`, `sleep` y `release`).
    *   Una vez que todos han terminado, el programa principal imprime el mensaje final.

En resumen, el semáforo actúa como un guardián que asegura que no más de 4 hilos (estudiantes) accedan al recurso (los equipos) al mismo tiempo, gestionando una cola de espera para los que no pueden entrar.
