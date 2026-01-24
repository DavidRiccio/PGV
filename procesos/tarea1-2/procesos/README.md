# Análisis del Proyecto de Comandos

Este proyecto es una aplicación de consola (posiblemente Spring Boot) diseñada para ejecutar y gestionar procesos del sistema operativo.

---

## 📂 Estructura y Explicación de Archivos

A continuación se detallan los archivos clave de este proyecto y su propósito.

### Archivos Raíz

* `pom.xml`: *(Descripción pendiente: Define las dependencias de Maven, plugins y la configuración general del build del proyecto)*.
* `mis_procesos.txt`: *(Descripción pendiente: Propósito de este archivo de texto en la raíz del proyecto)*.

---

### Código Fuente (`src/main/java`)

Ruta base del paquete: `com.comandos`

#### Punto de Entrada

`ProcesosServiciosApplication.java`: main es el boton de arranque universal en java. 
+ La linea de ``SpringApplication.run(...)`` inicia el motor de Springboot.


+ ``CommandLineRunner`` esto significa cuando termines de arrancar llama a este bloque de codigo.
* Llama al metodo procesos.menuConsola().

#### Domain

`domain/Job.java`: 
+ Es un archivo `enum` , es la caja de opciones predefinidas y limitadas. 

Sirve basicamente para decirle al programa solo existen estos tipos de 'Job.

#### Controllers

`controllers/CliControllers.java`:
Muestra las opciones al usuario, escucha la peticion y envia el Job adecuado a atenderla.

    @Service : indica que contiene logica
    @Autowired: Inyecta las dependencias


#### Repositories

* `repositories/interfaces/IJobRepository.java`: *(Descripción pendiente: Interfaz que define el contrato (métodos) para las operaciones de persistencia de datos (CRUD) relacionadas con los `Job`)*.
* `repositories/file/FileErrorRepository.java`: *(Descripción pendiente: Implementación de repositorio para gestionar la persistencia de errores, probablemente guardándolos en un archivo de texto)*.
* `repositories/file/FileJobRepository.java`: *(Descripción pendiente: Implementación de repositorio para gestionar la persistencia de `Job`, probablemente guardándolos en un archivo)*.

#### Services

* `services/abstracts/ComandoServiceAbstract.java`: *(Descripción pendiente: Clase abstracta que provee la lógica base (plantilla) para todos los servicios que ejecutan comandos del sistema. Probablemente define métodos comunes)*.
* `services/impl/LsofServiceImpl.java`: *(Descripción pendiente: Implementación de servicio específica para ejecutar el comando `lsof` (List Open Files))*.
* `services/impl/PsHeadServiceImpl.java`: *(Descripción pendiente: Implementación de servicio específica para ejecutar el comando `ps` (Process Status) combinado con `head`)*.
* `services/impl/TopServiceImpl.java`: *(Descripción pendiente: Implementación de servicio específica para ejecutar el comando `top` (Table of Processes))*.

---

### Recursos (`src/main/resources`)

Archivos de configuración o datos estáticos que utiliza la aplicación.

* `mis_procesos.txt`: *(Descripción pendiente: Archivo de recursos usado por la aplicación, quizás como plantilla o log)*.
* `stderr.txt`: *(Descripción pendiente: Archivo destinado a capturar o leer la salida de error estándar (standard error) de los procesos ejecutados)*.