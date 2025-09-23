# Code, Learn & Practice  
**Procesos y Servicios (modo alumno, sin root) — Trabajo en `$HOME/dam` con *user units* de systemd**

> **Importante:** Esta guía está adaptada para **usuarios sin privilegios de root**.  
> Todo se hace **en tu carpeta** `~/dam` usando **systemd --user** (un administrador por usuario), sin tocar `/etc` ni `/usr/local`.  
> Pega **salidas reales** y responde a las preguntas cortas.

---

## Preparación

Crea tu área de trabajo y variables útiles:

```bash
mkdir -p ~/dam/{bin,logs,units}
export DAM=~/dam
echo 'export DAM=~/dam' >> ~/.bashrc
```

Comprobar versión de systemd y que el *user manager* está activo:

```bash
systemctl --user --version | head -n1
systemctl --user status --no-pager | head -n5
```
**Pega salida aquí:**

```bash
 systemctl --user --version | head -n1
systemctl --user status --no-pager | head -n5
systemd 255 (255.4-1ubuntu8.6)
● a108pc03
    State: running
    Units: 261 loaded (incl. loaded aliases)
     Jobs: 0 queued
   Failed: 0 units
```

**Reflexiona la salida:**

```bash

```

---

## Bloque 1 — Conceptos (breve + fuentes)

1) ¿Qué es **systemd** y en qué se diferencia de SysV init?  

**Respuesta:**  
Es un gestor del sistema para linux, es el sistema de inicializacion por defecto, se inicia mediante el arraque. La diferencia es que SysV init es el sistema antigüo, de diseño secuencial y utilizaba scripts almacenados.


[Fuentes](https://www.maxizamorano.com/entrada/19/proceso-de-arranque-en-linux-systemd-vs-sysv-init)

2) **Servicio** vs **proceso** (ejemplos).  

**Respuesta:**  

_Fuentes:_

3) ¿Qué son los **cgroups** y para qué sirven?  

**Respuesta:**  

Los cgroyps son grupos de control, es una herramienta utilizada para asignar los recursos a los procesos, le da permisos, que operaciones permite y acotar los recursos.


[Fuentes](https://elpuig.xeill.net/Members/vcarceler/articulos/introduccion-a-los-grupos-de-control-cgroups-de-linux)

4) ¿Qué es un **unit file** y tipos (`service`, `timer`, `socket`, `target`)?  

**Respuesta:**  
Un archivo unit es un fichero de texto que describe un recurso o un servicio en un sistema que utiliza systemd, un sistema de inicio y gestor de procesos para Linux. Define cómo systemd debe manejar distintos tipos de elementos, como los demonios (.service), los eventos programados (.timer), las conexiones de red (.socket) y las agrupaciones lógicas (.target), permitiendo la gestión centralizada de los servicios del sistema

[Fuentes](https://manuais.iessanclemente.net/index.php/Gesti%C3%B3n_del_Sistema_con_Systemd)

5) ¿Qué hace `journalctl` y cómo ver logs **de usuario**?  

**Respuesta:**  

Es una herramienta que se encarga de recopilar y administrar los registros de SystemD, con esta herramienta puedes acceder a los mensajes del kernel, de servicios etc.

el comando para ver los logs del user es journalctl --user

[Fuentes](https://keepcoding.io/blog/que-es-journalctl-y-como-usarlo/)

---

## Bloque 2 — Práctica guiada (todo en tu `$DAM`)

> Si un comando pide permisos que no tienes, usa la **versión `--user`** o consulta el **ayuda** con `--help` para alternativas.

### 2.1 — PIDs básicos

**11.** PID de tu shell y su PPID.

```bash
echo "PID=$$  PPID=$PPID"
```
**Salida:**

```bash
echo "PID=$$  PPID=$PPID"
PID=86693  PPID=86684


```

**Pregunta:** ¿Qué proceso es el padre (PPID) de tu shell ahora?  

**Respuesta:**
```bash
echo ${PPID}
86684
```
---

**12.** PID del `systemd --user` (manager de usuario) y explicación.

```bash
pidof systemd  || pgrep -u "$USER" -x systemd
```

**Salida:**

```bash
pidof systemd --user || pgrep -u "$USER" -x systemd

3304

### Fue despues de 
3304
[1]+  Hecho                   gcc "$DAM/bin/zombie.c" -o "$DAM/bin/zombie" && "$DAM/bin/zombie"



```
**Pregunta:** ¿Qué hace el *user manager* de systemd para tu sesión?  

**Respuesta:**
El systemd-user manager es un proceso que Systemd inicia por ti al iniciar sesión, gestionando las "unidades" de Systemd (servicios y tareas) en tu espacio de usuario, y ejecutándolas con tus permisos sin necesidad de ser administrador.
---
[Fuente](https://docs.oracle.com/es/learn/use_systemd/)

### 2.2 — Servicios **de usuario** con systemd

Vamos a crear un servicio sencillo y un timer **en tu carpeta** `~/.config/systemd/user` o en `$DAM/units` (usaremos la primera para que `systemctl --user` lo encuentre).

**13.** Prepara directorios y script de práctica.

```bash
mkdir -p ~/.config/systemd/user "$DAM"/{bin,logs}
cat << 'EOF' > "$DAM/bin/fecha_log.sh"
#!/usr/bin/env bash
mkdir -p "$HOME/dam/logs"
echo "$(date --iso-8601=seconds) :: hello from user timer" >> "$HOME/dam/logs/fecha.log"
EOF
chmod +x "$DAM/bin/fecha_log.sh"
```

**14.** Crea el servicio **de usuario** `fecha-log.service` (**Type=simple**, ejecuta tu script).

```bash
cat << 'EOF' > ~/.config/systemd/user/fecha-log.service
[Unit]
Description=Escribe fecha en $HOME/dam/logs/fecha.log

[Service]
Type=simple
ExecStart=%h/dam/bin/fecha_log.sh
EOF

systemctl --user daemon-reload
systemctl --user start fecha-log.service
systemctl --user status fecha-log.service --no-pager -l | sed -n '1,10p'
```
**Salida (pega un extracto):**

```bash
cat << 'EOF' > ~/.config/systemd/user/fecha-log.service
[Unit]
Description=Escribe fecha en $HOME/dam/logs/fecha.log

[Service]
Type=simple
ExecStart=%h/dam/bin/fecha_log.sh
EOF

systemctl --user daemon-reload
systemctl --user start fecha-log.service
systemctl --user status fecha-log.service --no-pager -l | sed -n '1,10p'
○ fecha-log.service - Escribe fecha en $HOME/dam/logs/fecha.log
     Loaded: loaded (/home/dam/.config/systemd/user/fecha-log.service; static)
     Active: inactive (dead)

sep 23 18:09:13 a108pc03 systemd[3304]: Started fecha-log.service - Escribe fecha en $HOME/dam/logs/fecha.log.


```
**Pregunta:** ¿Se creó/actualizó `~/dam/logs/fecha.log`? Muestra las últimas líneas:

```bash
tail -n 5 "$DAM/logs/fecha.log"
```

**Salida:**

```bash
tail -n 5 "$DAM/logs/fecha.log"
2025-09-23T18:30:45+01:00 :: hello from user timer

```

**Reflexiona la salida:**
El timer esta creando una entrada nueva en los logs cada minuto, pero al no estar habilitado solo creo 1.

**15.** Diferencia **enable** vs **start** (modo usuario). Habilita el **timer**.

```bash
cat << 'EOF' > ~/.config/systemd/user/fecha-log.timer
[Unit]
Description=Timer (usuario): ejecuta fecha-log.service cada minuto

[Timer]
OnCalendar=*:0/1
Unit=fecha-log.service
Persistent=true

[Install]
WantedBy=timers.target
EOF

systemctl --user daemon-reload
systemctl --user enable --now fecha-log.timer
systemctl --user list-timers --all | grep fecha-log || true
```

**Salida (recorta):**

```bash
[Unit]
Description=Timer (usuario): ejecuta fecha-log.service cada minuto

[Timer]
OnCalendar=*:0/1
Unit=fecha-log.service
Persistent=true

[Install]
WantedBy=timers.target
EOF

systemctl --user daemon-reload
systemctl --user enable --now fecha-log.timer
systemctl --user list-timers --all | grep fecha-log || true
Created symlink /home/dam/.config/systemd/user/timers.target.wants/fecha-log.timer → /home/dam/.config/systemd/user/fecha-log.timer.
Tue 2025-09-23 18:11:00 WEST  46s -                                       - fecha-log.timer                fecha-log.service

```
**Pregunta:** ¿Qué diferencia hay entre `enable` y `start` cuando usas `systemctl --user`?  

**Respuesta:**
La principal diferencia es que systemctl --user start inicia un servicio inmediatamente para la sesión actual, mientras que systemctl --user enable lo configura para que se inicie automáticamente cada vez que el usuario inicia sesión

---
[Fuente](https://askubuntu.com/questions/733469/what-is-the-difference-between-systemctl-start-and-systemctl-enable)

**16.** Logs recientes **del servicio de usuario** con `journalctl --user`.

```bash
journalctl --user -u fecha-log.service -n 10 --no-pager
```

**Salida:**

```bash
sep 23 18:09:13 a108pc03 systemd[3304]: Started fecha-log.service - Escribe fecha en $HOME/dam/logs/fecha.log.
```
**Pregunta:** ¿Ves ejecuciones activadas por el timer? ¿Cuándo fue la última?  

**Respuesta:**

---

### 2.3 — Observación de procesos sin root

**17.** Puertos en escucha (lo que puedas ver como usuario).

```bash
lsof -i -P -n | grep LISTEN || ss -lntp
```
**Salida:**

```bash
dart:dart 45297  dam    7u  IPv4  84832      0t0  TCP 127.0.0.1:41345 (LISTEN)
dart      45354  dam    9u  IPv4  94363      0t0  TCP 127.0.0.1:9101 (LISTEN)
dart      45354  dam   10u  IPv6  94365      0t0  TCP [::1]:9101 (LISTEN)
adb       45418  dam   11u  IPv4  93595      0t0  TCP 127.0.0.1:5037 (LISTEN)
```
**Pregunta:** ¿Qué procesos *tuyos* están escuchando? (si no hay, explica por qué)  

**Respuesta:**

---

**18.** Ejecuta un proceso bajo **cgroup de usuario** con límite de memoria.

```bash
systemd-run --user --scope -p MemoryMax=50M sleep 200
ps -eo pid,ppid,cmd,stat | grep "[s]leep 200"
```

**Salida:**

```bash
Running as unit: run-rd57c6b43fc114fa4ad153674eb6a06b4.scope; invocation ID: d51d9bf18c794681b5cfb325bb1cd8dd


```
**Pregunta:** 
¿Qué ventaja tiene lanzar con `systemd-run --user` respecto a ejecutarlo “a pelo”?  

**Respuesta:**
Lanzar con systemd-run --user permite ejecutar programas y servicios como si fueran servicios de usuario, otorgando ventajas como la ejecución sin permisos de root.
[Fuente](https://documentation.suse.com/es-es/sle-micro/6.0/html/Micro-systemd-basics/index.html)
---

**19.** Observa CPU en tiempo real con `top` (si tienes `htop`, también vale).

```bash
top -b -n 1 | head -n 15
```
**Salida (resumen):**

```bash
    PID USUARIO   PR  NI    VIRT    RES    SHR S  %CPU  %MEM     HORA+ ORDEN
  95573 dam       20   0   17224   5760   3584 R   9,1   0,0   0:00.02 top
      1 root      20   0   23240  13672   9064 S   0,0   0,0   0:01.64 systemd
      2 root      20   0       0      0      0 S   0,0   0,0   0:00.01 kthreadd
      3 root      20   0       0      0      0 S   0,0   0,0   0:00.00 pool_wo+
      4 root       0 -20       0      0      0 I   0,0   0,0   0:00.00 kworker+
      5 root       0 -20       0      0      0 I   0,0   0,0   0:00.00 kworker+
      6 root       0 -20       0      0      0 I   0,0   0,0   0:00.00 kworker+
      7 root       0 -20       0      0      0 I   0,0   0,0   0:00.00 kworker+

```
**Pregunta:** ¿Cuál es tu proceso con mayor `%CPU` en ese momento?  

**Respuesta:**
El proceso 95573, que es el comando top.
---

**20.** Traza syscalls de **tu propio proceso** (p. ej., el `sleep` anterior).
> Nota: Sin root, no podrás adjuntarte a procesos de otros usuarios ni a algunos del sistema.

```bash
pid=$(pgrep -u "$USER" -x sleep | head -n1)
strace -p "$pid" -e trace=nanosleep -tt -c -f 2>&1 | sed -n '1,10p'
```

**Salida (fragmento):**

```bash

```
**Pregunta:** Explica brevemente la syscall que observaste.  

**Respuesta:**

---

### 2.4 — Estados y jerarquía (sin root)

**21.** Árbol de procesos con PIDs.

```bash
pstree -p | head -n 50
```

**Salida (recorta):**

```bash
systemd(1)-+-ModemManager(1851)-+-{ModemManager}(1861)
           |                    |-{ModemManager}(1864)
           |                    `-{ModemManager}(1866)
           |-NetworkManager(1819)-+-{NetworkManager}(1856)
           |                      |-{NetworkManager}(1857)
           |                      `-{NetworkManager}(1858)
           |-accounts-daemon(1156)-+-{accounts-daemon}(1190)
           |                       |-{accounts-daemon}(1191)
           |                       `-{accounts-daemon}(1820)
           |-agetty(2231)
           |-apache2(2283)-+-apache2(2295)
           |               |-apache2(2296)
           |               |-apache2(2298)
           |               |-apache2(2299)
           |               `-apache2(2300)
           |-at-spi2-registr(3660)-+-{at-spi2-registr}(3666)
           |                       |-{at-spi2-registr}(3667)
           |                       `-{at-spi2-registr}(3668)
           |-avahi-daemon(1158)---avahi-daemon(1251)
           |-blkmapd(1105)
           |-colord(2015)-+-{colord}(2021)
           |              |-{colord}(2022)
           |              `-{colord}(2025)
           |-containerd(2000)-+-{containerd}(2017)
           |                  |-{containerd}(2018)
           |                  |-{containerd}(2019)
           |                  |-{containerd}(2020)
           |                  |-{containerd}(2024)
           |                  |-{containerd}(2034)
           |                  |-{containerd}(2035)
           |                  |-{containerd}(2042)
           |                  |-{containerd}(2043)
           |                  |-{containerd}(2044)
           |                  |-{containerd}(2052)
           |                  |-{containerd}(2056)
           |                  |-{containerd}(2057)
           |                  |-{containerd}(2058)
           |                  `-{containerd}(2526)


```
**Pregunta:** ¿Bajo qué proceso aparece tu `systemd --user`?  

**Respuesta:**
Bajo la shell del usuario
---

**22.** Estados y relación PID/PPID.

```bash
ps -eo pid,ppid,stat,cmd | head -n 20
```
**Salida:**

```bash
PID    PPID STAT CMD
      1       0 Ss   /sbin/init splash
      2       0 S    [kthreadd]
      3       2 S    [pool_workqueue_release]
      4       2 I<   [kworker/R-rcu_g]
      5       2 I<   [kworker/R-rcu_p]
      6       2 I<   [kworker/R-slub_]
      7       2 I<   [kworker/R-netns]
     10       2 I<   [kworker/0:0H-events_highpri]
     12       2 I<   [kworker/R-mm_pe]
     13       2 I    [rcu_tasks_kthread]
     14       2 I    [rcu_tasks_rude_kthread]
     15       2 I    [rcu_tasks_trace_kthread]
     16       2 S    [ksoftirqd/0]
     17       2 I    [rcu_preempt]
     18       2 S    [migration/0]
     19       2 S    [idle_inject/0]
     20       2 S    [cpuhp/0]
     21       2 S    [cpuhp/1]
     22       2 S    [idle_inject/1]

```
**Pregunta:** Explica 3 flags de `STAT` que veas (ej.: `R`, `S`, `T`, `Z`, `+`).  

**Respuesta:**

- R: Running -> es un proceso que esta en ejecucion
- S: Sleeping -> Es un proceso dormido, está esperando a un evento para activarse
- Z: Zombie -> Es un proceso que ha terminado pero su proceso padre no ha recolectado la informacion sobre su finalizacion.
---

**23.** Suspende y reanuda **uno de tus procesos** (no crítico).

```bash
# Crea un proceso propio en segundo plano
sleep 120 &
pid=$!
# Suspende
kill -STOP "$pid"
# Estado
ps -o pid,stat,cmd -p "$pid"
# Reanuda
kill -CONT "$pid"
# Estado
ps -o pid,stat,cmd -p "$pid"
```
**Pega los dos estados (antes/después):**

```bash
 dam  ~  sleep 120 &
pid=$!
[1] 98961
 dam  ~  1  kill -STOP 98961

[1]+  Detenido                sleep 120

ps -o pid,stat,cmd -p 98961
    PID STAT CMD
  98961 T    sleep 120

 ps -o pid,stat,cmd -p 98961
    PID STAT CMD
  98961 S    sleep 120



```
**Pregunta:** ¿Qué flag indicó la suspensión?  
**Respuesta:**
Sleeping

---

**24. (Opcional)** Genera un **zombie** controlado (no requiere root).

```bash
cat << 'EOF' > "$DAM/bin/zombie.c"
#include <stdlib.h>
#include <unistd.h>
int main() {
  if (fork() == 0) { exit(0); } // hijo termina
  sleep(60); // padre no hace wait(), hijo queda zombie hasta que padre termine
  return 0;
}
EOF
gcc "$DAM/bin/zombie.c" -o "$DAM/bin/zombie" && "$DAM/bin/zombie" &
ps -el | grep ' Z '
```
**Salida (recorta):**

```bash
#include <stdlib.h>
#include <unistd.h>
int main() {
  if (fork() == 0) { exit(0); } // hijo termina
  sleep(60); // padre no hace wait(), hijo queda zombie hasta que padre termine
  return 0;
}
EOF

0 Z  1001   53049   53037  0  80   0 -     0 -      ?        00:00:00 sd_espeak-ng-mb

```
**Pregunta:** ¿Por qué el estado `Z` y qué lo limpia finalmente?  

**Respuesta:**

---

### 2.5 — Limpieza (solo tu usuario)

Detén y deshabilita tu **timer/servicio de usuario** y borra artefactos si quieres.

```bash
systemctl --user disable --now fecha-log.timer
systemctl --user stop fecha-log.service
rm -f ~/.config/systemd/user/fecha-log.{service,timer}
systemctl --user daemon-reload
rm -rf "$DAM/bin" "$DAM/logs" "$DAM/units"
```

---

## Tree de los archivos creados
```bash
dam/
├── bin
│   ├── fecha_log.sh
│   ├── zombie
│   └── zombie.c
├── logs
│   └── fecha.log
└── units
```


## ¿Qué estás prácticando?
- [ ] Pegaste **salidas reales**.  
- [ ] Explicaste **qué significan**.  
- [ ] Usaste **systemd --user** y **journalctl --user**.  
- [ ] Probaste `systemd-run --user` con límites de memoria.  
- [ ] Practicaste señales (`STOP`/`CONT`), `pstree`, `ps` y `strace` **sobre tus procesos**.

---


