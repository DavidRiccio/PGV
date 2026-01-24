

#  Repositorio de PGV (Programación de Servicios y Procesos)

Este repositorio es una colección exhaustiva de proyectos y ejercicios prácticos centrados en la **programación concurrente**, **gestión de procesos** y **desarrollo de servicios web (REST/SOAP)** en Java.



## 📂 Estructura Detallada del Repositorio

A continuación se describen los módulos principales y su contenido:

### 🧵 1. Programación Concurrente (Hilos y Pools)

Ubicado en las carpetas `/hilos` y `/thread-pools`.


**Hilos Explicación**: Documentación teórica sobre el ciclo de vida y gestión de hilos en Java.


**Tarea Servicios**: Ejercicios prácticos con temáticas creativas (Batalla de Magos, Pokémon, Star Wars) para aplicar sincronización y concurrencia.



**Semaforos**: Implementaciones específicas utilizando `Semaphore` para control de acceso a recursos (ej. `Laboratory`, `SaiyanRace`).



**Thread Pools**: Gestión avanzada de hilos mediante pools en el proyecto `seguimiento3.2`, incluyendo simuladores de servidores de mazmorras.



### ⚙️ 2. Gestión de Procesos

Proyectos dedicados a la interacción con el sistema operativo.


**Procesos Servicios Consola**: Aplicación Spring Boot para ejecutar y monitorizar comandos como `ps` y `ls`.



**Tarea 1.2**: Implementación de comandos de sistema más complejos como `lsof`, `top` y `ps head` con persistencia en archivos.



**Monitor de Disco**: Práctica personal para la monitorización de espacio en disco mediante comandos `df` y `du`.



### 🌐 3. Servicios Web (REST y SOAP)

Ubicado principalmente en `/servicios` y `/evaluables`.


**REST Ejercicio**: Creación de APIs funcionales para gestión de tareas y usuarios.



**Spring Tareas REST-SOAP**: Módulo híbrido que expone funcionalidades de gestión de alumnos y tareas a través de ambos protocolos simultáneamente.



**Editorial REST/SOAP**: Proyecto evaluable con integración de Apache CXF para servicios SOAP y controladores REST para la gestión de libros y autores.



### 🛡️ 4. Seguridad y Eventos


**Eventos Ejercicio**: Sistema de gestión de eventos con seguridad basada en **JWT (JSON Web Tokens)**, filtros de autenticación y manejo global de excepciones.



---

## 🛠️ Tecnologías Utilizadas

* **Lenguaje**: Java
* **Framework Principal**: Spring Boot
* **Gestión de Dependencias**: Maven (Proyectos con `pom.xml`)

**Servicios Web**: JAX-WS (SOAP), Spring Web (REST), Apache CXF 



**Pruebas**: JUnit y JaCoCo para cobertura de código 



**Seguridad**: Spring Security y JWT 



---

## 📊 Cobertura y Calidad

Muchos de los proyectos incluyen una suite de pruebas unitarias (`/test`) y reportes de cobertura generados por **JaCoCo**, asegurando la fiabilidad de la lógica de negocio, especialmente en los repositorios y servicios.

