# Clases utilizadas en la tarea de hilos.

## Clases

``AtomicBoolean y AtomicInteger``:Estan diseñadas para ser gestionada de forma segura por varios hilos simultaneamente. 

```java
final AtomicInteger energiaGandalf = new AtomicInteger(120);
final AtomicInteger energiaSaruman = new AtomicInteger(120);
final AtomicBoolean combateTerminado = new AtomicBoolean(false);
```




``ReentrantLock``:  Es el candado , garantiza que solo un hilo pueda ejecutar una seccion del codigo a la vez.

``Condition``: Permite que el hilo que tiene el candado se duerma hasta que otro le avise que puede continuar.

```java
final ReentrantLock m = new ReentrantLock();
final Condition turnoCambio = m.newCondition();
```


### Métodos Principales de ``ReentrantLock (m)`` 


``lock``: Bloquea el hilo actual hasta que pueda usarse de forma exlusiva.

``unlock``:  Siempre debe llamase a un bloque **finally** para asegurar quer se libera el candado, permitiendo que otros hilos accedan al recurso.

``trylock``: Intenta adquirir el candado inmediantamente, si lo consigue devuelve **True** si no devuelve **False**.

``newCondition``: Crea y devuelve un objeto Condition ligado al Lock.

### Métodos Principales de ``Condition`` 

``await``: Pausa y espera, pone a domir el hilo hasta que se le notifique que ya puede continuar.

``signal``: Notifica al hilo, Despierta a un solo hilo que se durmió con ``await``.

``signalAll``: Notifica a todos los hilos dormidos por await.


``awaitUninterruptibly``: Parecido a await pero ignora las interrupciones del hilo.

## Ejemplos practicos.



### Ejemplo 1: ``Buffer limitado``.

```java
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class EjemploCondition {
    // La cerradura maestra (el candado)
    private final ReentrantLock lock = new ReentrantLock();
    
    // Condición 1: El consumidor espera aquí si el búfer está vacío
    private final Condition estaVacio = lock.newCondition();
    
    // Condición 2: El productor espera aquí si el búfer está lleno
    private final Condition estaLleno = lock.newCondition();

    private final Queue<Integer> buffer = new LinkedList<>();
    private final int CAPACIDAD = 5;

    public void producir(int valor) throws InterruptedException {
        // 1. Adquirir el candado
        lock.lock();
        try {
            // 2. Esperar si la condición no es adecuada (búfer lleno)
            while (buffer.size() == CAPACIDAD) {
                System.out.println("Buffer lleno. Productor espera...");
                estaLleno.await(); // Libera el lock y espera en la Condition 'estaLleno'
            }
            
            // 3. Modificar el estado compartido
            buffer.add(valor);
            System.out.println("Producido: " + valor + " | Buffer size: " + buffer.size());

            // 4. Notificar a los hilos que esperan en la otra condición (estaVacio)
            estaVacio.signalAll(); 

        } finally {
            // 5. Asegurar la liberación del candado
            lock.unlock();
        }
    }

    public int consumir() throws InterruptedException {
        lock.lock();
        try {
            // 1. Esperar si la condición no es adecuada (búfer vacío)
            while (buffer.isEmpty()) {
                System.out.println("Buffer vacío. Consumidor espera...");
                estaVacio.await(); // Libera el lock y espera en la Condition 'estaVacio'
            }

            // 2. Modificar el estado compartido
            int valor = buffer.poll();
            System.out.println("Consumido: " + valor + " | Buffer size: " + buffer.size());

            // 3. Notificar a los hilos que esperan en la otra condición (estaLleno)
            estaLleno.signalAll(); 
            
            // 4. Devolver el resultado
            return valor;

        } finally {
            // 5. Asegurar la liberación del candado
            lock.unlock();
        }
    }
}

```