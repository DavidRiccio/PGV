# Tarea nivel fácil.

### Enunciado: 
Implementa una aplicación Spring Boot CLI que ejecute el comando df -h, tambien habra que validar el df -i y habra que hacer tambien el du -sh /home capture stdout/stderr en tiempo real con prefijos [OUT]/[ERR], y persista el historial en ficheros .txt.

### Tree 
```bash
ejercicio1-disk-monitor/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── docencia/
│   │   │           └── dam/
│   │   │               ├── DiskMonitorApplication.java
│   │   │               ├── domain/
│   │   │               │   └── Job.java
│   │   │               ├── repositories/
│   │   │               │   ├── interfaces/
│   │   │               │   │   └── JobRepository.java
│   │   │               │   └── file/
│   │   │               │       └── FileJobRepository.java
│   │   │               ├── services/
│   │   │               │   ├── interfaces/
│   │   │               │   │   └── CommandService.java
│   │   │               │   └── impl/
│   │   │               │       └── DfServiceImpl.java
│   │   │               └── controllers/
│   │   │                   └── CliController.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── stdout.txt (generado)
│   │       └── stderr.txt (generado)
│   └── test/
│       └── java/
│           └── com/
│               └── docencia/
│                   └── dam/
│                       ├── services/
│                       │   └── DfServiceImplTest.java
│                       └── repositories/
│                           └── FileJobRepositoryTest.java
└── pom.xml

```