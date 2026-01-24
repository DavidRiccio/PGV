Este documento describe cómo he implementado la lógica específica de mi proyecto, utilizando la estructura base y las clases abstractas proporcionadas. El objetivo era crear servicios funcionales que se integraran con la arquitectura existente.

## Estructura del Proyecto

Esta es la estructura de archivos del proyecto, que separa el código fuente (main), las clases de implementación y los (test).

```bash
.
├── main
│   ├── java
│   │   └── com
│   │       └── comandos
│   │           ├── ProcesosServiciosApplication.java
│   │           ├── controllers
│   │           │   └── CliControllers.java
│   │           ├── domain
│   │           │   └── Job.java
│   │           ├── repositories
│   │           │   ├── file
│   │           │   │   ├── FileErrorRepository.java
│   │           │   │   └── FileJobRepository.java
│   │           │   └── interfaces
│   │           │       └── IJobRepository.java
│   │           └── services
│   │               ├── abstracts
│   │               │   └── ComandoServiceAbstract.java
│   │               └── impl
│   │                   ├── LsofServiceImpl.java
│   │                   ├── PsHeadServiceImpl.java
│   │                   └── TopServiceImpl.java
│   └── resources
│       ├── mis_procesos.txt
│       └── stderr.txt
└── test
    └── java
        └── com
            └── comandos
                ├── repository
                └── services
                    ├── Abstract
                    │   └── ComandoServiceAbstractTest.java
                    ├── LsofServiceTest.java
                    ├── PsHeadServiceTest.java
                    └── TopServiceImpl.java
```
## Arquitectura del Proyecto (Basada en Clases Abstractas)

La arquitectura se centra en la clase ComandoServiceAbstract. Esta clase actúa como una "plantilla" o "molde" que define toda la lógica común (como la validación y la ejecución) que cualquier comando necesitará.

Mi trabajo ha sido crear implementaciones concretas (impl) que "rellenan" los detalles de esta plantilla.

## Componentes Clave y Mi Implementación

### services/abstracts/ComandoServiceAbstract.java

Esta es la "plantilla" proporcionada. Es el cerebro de la lógica de comandos y define cómo deben funcionar todos los servicios.

Campos Clave:

- tipo (Enum Job): El tipo de comando que el servicio representa.

- validation (String): El patrón regex para validar los parámetros.

- errorRepository: La dependencia (@Autowired) para registrar errores.

- Funciones Principales (Heredadas):

- validarComando(): Comprueba si el comando escrito coincide con el Job.TIPO del servicio.

- validar(): El método de validación principal. Llama a validarComando() y luego usa el regex de validation para comprobar los parámetros. (Nota: esta función accede directamente a arrayComando[1], asumiendo que siempre existe un parámetro).

### controllers/CliControllers.java

Aquí es donde he implementado la lógica de la interfaz de usuario.

- menuConsola(): Este método usa un Scanner para mostrar el menú y leer la entrada del usuario. Mediante una serie de if (comando.toUpperCase().startsWith(...)), decide a cuál de mis servicios (impl) debe enviar el comando para que sea procesado.

### services/impl/ (Mi Lógica de Implementación)

Aquí es donde he añadido la lógica específica para cada comando, heredando de ComandoServiceAbstract.

LsofServiceImpl.java y TopServiceImpl.java

Mi Implementación: Mi trabajo aquí ha sido configurar el constructor para que "rellene" la plantilla abstracta. Por ejemplo, en TopServiceImpl, llamo a this.setTipo(Job.TOP) y this.setValidation("^-bn[0-9]$"). La clase abstracta se encarga del resto de la validación.

PsHeadServiceImpl.java (El Alias)

Mi Implementación: Esta es una implementación especial que he creado como un alias.

El Objetivo: Quería que cuando el usuario escribiera ps head, la aplicación ejecutara ps aux | head.

Cómo lo hice:

En el constructor, configuré la plantilla abstracta para que solo acepte esa entrada: setTipo(Job.PS) y setValidation("^head$").

Esto fuerza a que el método validar() de la clase abstracta solo dé por válida la entrada exacta: ps head.

Luego, he sobrescrito el método procesarLinea(). Dentro de este método, ignoro la entrada original (ps head) y en su lugar construyo y ejecuto el comando real: String comandoReal = "ps aux | head";.

### domain/Job.java

Propósito: Un Enum que define la lista de comandos que mi aplicación reconoce (TOP, LSOF, PS).

### repositories/

Propósito: Clases proporcionadas para manejar la escritura en archivos.

FileErrorRepository.java: La clase abstracta lo usa automáticamente. Cuando validar() falla, este repositorio escribe el error en stderr.txt.

## Ficheros de Salida

mis_procesos.txt: El fichero donde se redirige la salida estándar (stdout) de los comandos que se ejecutan correctamente.

stderr.txt: El registro de errores. FileErrorRepository escribe aquí cada vez que una validación falla.