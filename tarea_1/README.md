# <img src=../../../../../images/computer.png width="40"> Code, Learn & Practice(Programación de Servicios y Procesos: "Procesos")

## Instrucciones de la práctica

En esta práctica se trabajará en **dos fases complementarias**:

1. **Parte teórica:** Responde a los conceptos solicitados mediante la **búsqueda de información confiable**, citando siempre las **fuentes consultadas** al final de cada respuesta.
2. **Parte práctica:** Ejecuta en Linux los **comandos indicados** y muestra la **salida obtenida** junto con una breve explicación de su significado.

El objetivo es afianzar la comprensión de los **procesos en sistemas operativos**, tanto desde el punto de vista conceptual como práctico.

## Bloque 1: Conceptos básicos (teoría)

### Ejercicio 0 (ejemplo resuelto)

**Pregunta:** Explica la diferencia entre hardware, sistema operativo y aplicación.

**Respuesta:**

- **Hardware**: parte física (CPU, memoria, disco, etc.).
- **Sistema Operativo (SO)**: software que gestiona el hardware y ofrece servicios a las aplicaciones (ejemplo: Linux, Windows).
- **Aplicación**: programas que usa el usuario y que se apoyan en el SO (ejemplo: navegador, editor de texto).

---

#### 1. Define qué es un **proceso** y en qué se diferencia de un **programa**.

Los procesos son una sucesion de instrucciones que pretender realizar una tarea en concreto, en cambio un programa es la tarea a realizar.
[Fuente](https://www.profesionalreview.com/2019/09/23/proceso-informatico/)

#### 2. Explica qué es el **kernel** y su papel en la gestión de procesos.

El kernel es el encargado de darle acceso al hardware de forma segura al software que lo solicita. Se encarga de determinar que procesos pueden usar la cpu cuando y durante cuanto tiempo.

#### 3. ¿Qué son **PID** y **PPID**? Explica con un ejemplo.

PID (Process ID): es el identificador único que el sistema operativo asigna a cada proceso cuando se ejecuta.
PPID (Parent Process ID): es el PID del proceso padre, es decir, el proceso que creó (lanzó) al actual.
[Fuente](https://www.codigoiot.com/base-de-conocimiento/ejecucion-de-procesos/)

#### 4. Describe qué es un **cambio de contexto** y por qué es costoso.

Un cambio de contexto es el acto de pasar la atención de una tarea, aplicación o proyecto a otro, tanto en informática como en la vida cotidiana, y es costoso porque requiere que el sistema deje de lado la información y el estado de una actividad para cargar los de la siguiente,
[Fuente](https://asana.com/es/resources/context-switching)

#### 5. Explica qué es un **PCB (Process Control Block)** y qué información almacena.

Un PCB (Process Control Block) o Bloque de Control de Proceso es una estructura de datos que el sistema operativo utiliza para gestionar un proceso específico, conteniendo toda la información necesaria sobre él, como su identificación, estado, recursos asignados, y datos de la CPU.
[Fuente](<https://es.scribd.com/document/85273968/2-3-Bloque-de-Control-de-Procesos#:~:text=El%20bloque%20de%20control%20de%20proceso%20(PCB),PCB%20define%20el%20estado%20del%20sistema%20operativo.>)

#### 6. Diferencia entre **proceso padre** y **proceso hijo**.

La principal diferencia es que el
proceso padre es el que crea otro proceso, mientras que el proceso hijo es el proceso recién creado por un proceso padre.
[Fuente](https://en.wikipedia.org/wiki/Parent_process#:~:text=The%20process%20that%20invoked%20fork,process%20by%20its%20process%20identifier.)

#### 7. Explica qué ocurre cuando un proceso queda **huérfano** en Linux.

Un proceso huérfano ocurre cuando un proceso padre finaliza antes que su proceso hijo, dejándolo ejecutándose sin su padre original . En sistemas Linux, los procesos hijos huérfanos son adoptados automáticamente por el proceso init o systemd (normalmente PID 1), lo que les permite continuar ejecutándose en segundo plano.
[Fuente](https://www-greptile-com.translate.goog/bug-wiki/memory-resource-management/orphaned-process?_x_tr_sl=en&_x_tr_tl=es&_x_tr_hl=es&_x_tr_pto=rq)

#### 8. ¿Qué es un proceso **zombie**? Da un ejemplo de cómo puede ocurrir.

Un
proceso zombie es un proceso terminado en un sistema operativo que ya no se ejecuta, pero su entrada en la tabla de procesos aún existe hasta que el proceso padre lo "reconoce" y borra.
[Fuente](https://es.wikipedia.org/wiki/Proceso_zombie)

#### 9. Diferencia entre **concurrencia** y **paralelismo**.

La
concurrencia se enfoca en la estructura lógica y gestión de múltiples tareas de forma que parezcan ocurrir simultáneamente, como un chef que alternar entre platos, y puede ejecutarse incluso con un solo procesador. El paralelismo es la ejecución real y simultánea de múltiples tareas, lo que requiere múltiples procesadores o núcleos, como un equipo de cocineros que preparan platos a la vez
[Fuente](https://blog.thedojo.mx/2019/04/17/la-diferencia-entre-concurrencia-y-paralelismo.html)

#### 10. Explica qué es un **hilo (thread)** y en qué se diferencia de un proceso.

Un
hilo (thread) es la unidad mínima de ejecución que puede ser programada por el sistema operativo, y varias tareas dentro de un mismo programa pueden ejecutar hilos de manera concurrente, compartiendo memoria y recursos del proceso padre. En contraste, un proceso es una instancia independiente de una aplicación en ejecución,
[Fuente](https://www.mentorestech.com/resource-blog-content/process-vs-threads)

## Bloque 2: Práctica con comandos en Linux

### Ejercicio 0 (ejemplo resuelto)

**Pregunta:** ¿Qué comando muestra el directorio actual?

**Resolución:**

```bash
    pwd
```

11. Usa echo $$ para mostrar el PID del proceso actual.

```bash
   alu@daw:~$ echo $$
8624
```

12. Usa echo $PPID para mostrar el PID del proceso padre.

```bash
   alu@daw:~$ echo $PPID
1782
```

13. Ejecuta pidof systemd y explica el resultado.

```bash
    alu@daw:~$ pidof systemd
1305
systemd es el init system más usado en Linux moderno.
```

14. Abre un programa gráfico (ejemplo: gedit) y usa pidof para obtener sus PID.

```bash
    alu@daw:~$ xeyes &
[1] 9732
alu@daw:~$ pidof xeyes
9732
```

15. Ejecuta ps -e y explica qué significan sus columnas.

```bash
    alu@daw:~$ ps -e
    PID TTY          TIME CMD
      1 ?        00:00:01 systemd
      2 ?        00:00:00 kthreadd
      3 ?        00:00:00 rcu_gp
      4 ?        00:00:00 rcu_par_gp
      5 ?        00:00:00 slub_flushwq
      6 ?        00:00:00 netns
      8 ?        00:00:00 kworker/0:0H-events_highpri
     10 ?        00:00:00 mm_percpu_wq
     11 ?        00:00:00 rcu_tasks_kthread
     12 ?        00:00:00 rcu_tasks_rude_kthread
```

- PID

  Process ID: el número único que identifica al proceso.

- TTY

  Teletypewriter: indica el terminal asociado al proceso.

- TIME

  El tiempo total de CPU que el proceso ha consumido desde que empezó.

- CMD

  El comando que lanzó el proceso (nombre del programa).

16. Ejecuta ps -f y observa la relación entre procesos padre e hijo.

```bash
    ps -f
UID          PID    PPID  C STIME TTY          TIME CMD
alu         8624    1782  0 22:01 pts/1    00:00:00 bash
alu         9732    8624  3 22:03 pts/1    00:00:05 xeyes
alu        10892    8624  0 22:06 pts/1    00:00:00 ps -f
```

17. Usa ps -axf o pstree para mostrar el árbol de procesos y dibújalo.

```bash
    alu@daw:~$ ps -axf
    PID TTY      STAT   TIME COMMAND
      2 ?        S      0:00 [kthreadd]
      3 ?        I<     0:00  \_ [rcu_gp]
      4 ?        I<     0:00  \_ [rcu_par_gp]
      5 ?        I<     0:00  \_ [slub_flushwq]
      6 ?        I<     0:00  \_ [netns]
      8 ?        I<     0:00  \_ [kworker/0:0H-events_highpri]
     10 ?        I<     0:00  \_ [mm_percpu_wq]
     11 ?        I      0:00  \_ [rcu_tasks_kthread]
     12 ?        I      0:00  \_ [rcu_tasks_rude_kthread]
     13 ?        I      0:00  \_ [rcu_tasks_trace_kthread]
     14 ?        S      0:16  \_ [ksoftirqd/0]
```

18. Ejecuta top o htop y localiza el proceso con mayor uso de CPU.

```bash
   791 root      20   0  440756 175892  85040 S  25,6   2,2   3:18.36 Xorg
   2316 alu       20   0 1136,1g 344968 122904 S  22,6   4,3   2:10.97 code
   1489 alu       20   0  486004  54740  39536 S   8,3   0,7   0:10.02 xfce4-p+
```

EL 791 19. Ejecuta sleep 100 en segundo plano y busca su PID con ps.

```bash
    sleep 100 &
[2] 11788
```

20. Finaliza un proceso con kill <PID> y comprueba con ps que ya no está.

```bash
    alu@daw:~$ ps -f | grep sleep
alu        11788    8624  0 22:08 pts/1    00:00:00 sleep 100
alu        12042    8624  0 22:08 pts/1    00:00:00 grep --color sleep
alu@daw:~$ kill 11788
[2]+  Terminado               sleep 100
```

## Bloque 3: Procesos y jerarquía

21. Identifica el **PID del proceso init/systemd** y explica su función.

Es el primer proceso que se arranca en el sistema se identifica con pidof systemd

```bash
pidof systemd
1305
```

22. Explica qué ocurre con el **PPID** de un proceso hijo si su padre termina antes.
    Si el proceso padre termina antes que el hijo, el hijo no muere automáticamente.
23. Ejecuta un programa que genere varios procesos hijos y observa sus PIDs con `ps`.

```bash
alu@daw:~$ sudo chmod +x procesos.sh
alu@daw:~$ ./procesos.sh
alu@daw:~$ pidof sleep


UID          PID    PPID  C STIME TTY          TIME CMD
alu        13748   13747  0 22:12 pts/1    00:00:00 sleep 60
alu        13749   13747  0 22:12 pts/1    00:00:00 sleep 60
alu        13750   13747  0 22:12 pts/1    00:00:00 sleep 60
alu        13751   13747  0 22:12 pts/1    00:00:00 ps -f --ppid 13747
```

24. Haz que un proceso quede en **estado suspendido** con `Ctrl+Z` y réanúdalo con `fg`.

```bash
alu@daw:~$ sleep 100
^Z
[1]+  Detenido                sleep 100
alu@daw:~$ fg
sleep 100
```

25. Lanza un proceso en **segundo plano** con `&` y obsérvalo con `jobs`.

```bash
alu@daw:~$ sleep 60 &
[1] 14974
alu@daw:~$ jobs
[1]+  Ejecutando              sleep 60 &
```

26. Explica la diferencia entre los estados de un proceso: **Running, Sleeping, Zombie, Stopped**.

- Running (R):
  Es cuando el proceso se está ejecutando o listo para ejecutarse en CPU.

- Sleeping (S):
  Es cuando el proceso está esperando que pase algo para continuar, como un archivo que se abra o un temporizador.

- Zombie (Z):
  Es un proceso que ya terminó pero aún no ha sido recogido por su padre. No consume CPU ni hace nada, solo queda en memoria un registro mínimo.

- Stopped (T):
  Es cuando el proceso ha sido suspendido, normalmente con Ctrl+Z.

27. Usa `ps -eo pid,ppid,stat,cmd` para mostrar los estados de varios procesos.

```bash
ps -eo pid,ppid,stat,cmd
    PID    PPID STAT CMD
      1       0 Ss   /sbin/init
      2       0 S    [kthreadd]
      3       2 I<   [rcu_gp]
      4       2 I<   [rcu_par_gp]
      5       2 I<   [slub_flushwq]
      6       2 I<   [netns]
      8       2 I<   [kworker/0:0H-events_highpri]
     10       2 I<   [mm_percpu_wq]
     11       2 I    [rcu_tasks_kthread]
     12       2 I    [rcu_tasks_rude_kthread]
     13       2 I    [rcu_tasks_trace_kthread]
     14       2 S    [ksoftirqd/0]
     15       2 I    [rcu_preempt]
     16       2 S    [migration/0]
     18       2 S    [cpuhp/0]

```

28. Ejecuta `watch -n 1 ps -e` y observa cómo cambian los procesos en tiempo real.

```bash

    PID TTY          TIME CMD
      1 ?        00:00:01 systemd
      2 ?        00:00:00 kthreadd
      3 ?        00:00:00 rcu_gp
      4 ?        00:00:00 rcu_par_gp
      5 ?        00:00:00 slub_flushwq
      6 ?        00:00:00 netns
      8 ?        00:00:00 kworker/0:0H-events_highpri
     10 ?        00:00:00 mm_percpu_wq
     11 ?        00:00:00 rcu_tasks_kthread

```

29. Explica la diferencia entre ejecutar un proceso con `&` y con `nohup`.
    - & ejecuta un proceso en segundo plano, liberando la terminal.
      Pero si cierro la terminal, el proceso muere.
    - nohup hace que el proceso ignore el cierre de la terminal.
      Con nohup & puedo dejarlo corriendo aunque cierre la sesión.
30. Usa `ulimit -a` para ver los límites de recursos de procesos en tu sistema.

```bash
ulimit -a
real-time non-blocking time  (microseconds, -R) unlimited
core file size              (blocks, -c) 0
data seg size               (kbytes, -d) unlimited
scheduling priority                 (-e) 0
file size                   (blocks, -f) unlimited
pending signals                     (-i) 30753
max locked memory           (kbytes, -l) 992060
max memory size             (kbytes, -m) unlimited
open files                          (-n) 1024
pipe size                (512 bytes, -p) 8
POSIX message queues         (bytes, -q) 819200
real-time priority                  (-r) 0
stack size                  (kbytes, -s) 8192
cpu time                   (seconds, -t) unlimited
max user processes                  (-u) 30753
virtual memory              (kbytes, -v) unlimited
file locks                          (-x) unlimited

```
