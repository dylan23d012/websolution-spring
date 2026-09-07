# WebSolution-Spring

## Módulo de Gestión de Clientes

Proyecto web desarrollado para la evidencia GA7-220501096-AA3-EV01, utilizando Spring Boot, Spring Data JPA, Hibernate, MySQL y Maven.

## Tecnologías utilizadas

- Java 8
- Spring Boot 2.7.18
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- HTML, CSS y JavaScript
- Git y GitHub

## Descripción

El módulo permite gestionar los clientes registrados en la base de datos del proyecto Web Solution.

La aplicación implementa las operaciones CRUD:

- Crear clientes.
- Consultar clientes.
- Actualizar clientes.
- Eliminar clientes.

## Arquitectura

La aplicación utiliza una arquitectura por capas:

```text
Interfaz web
     ↓
ClienteController
     ↓
ClienteService
     ↓
ClienteRepository
     ↓
Spring Data JPA
     ↓
Hibernate
     ↓
MySQL
Estructura del proyectoWebSolution-Spring
├── src
│   └── main
│       ├── java
│       │   └── com.websolution
│       │       ├── controlador
│       │       │   └── ClienteController.java
│       │       ├── modelo
│       │       │   └── Cliente.java
│       │       ├── repositorio
│       │       │   └── ClienteRepository.java
│       │       ├── servicio
│       │       │   └── ClienteService.java
│       │       └── WebSolutionApplication.java
│       └── resources
│           ├── application.properties
│           └── static
│               └── index.html
├── .gitignore
├── pom.xml
├── README.md
└── database.sql
Endpoints de la API
Consultar todos los clientes
GET /api/clientes
Consultar un cliente
GET /api/clientes/{id}
Crear un cliente
POST /api/clientes
Actualizar un cliente
PUT /api/clientes/{id}
Eliminar un cliente
DELETE /api/clientes/{id}
Base de datos

El proyecto utiliza la base de datos MySQL websolution y la tabla clientes.

La conexión se configura mediante la variable de entorno DB_PASSWORD, evitando almacenar la contraseña directamente en el código fuente.

Pruebas realizadas

Se realizaron pruebas de las cuatro operaciones CRUD:

Crear cliente: prueba exitosa.
Consultar clientes: prueba exitosa.
Actualizar cliente: prueba exitosa.
Eliminar cliente: prueba exitosa.

También se verificó la integración entre la interfaz web, Spring Boot, Spring Data JPA, Hibernate y MySQL.

Estándares de codificación

El código utiliza:

Nombres descriptivos para clases, métodos y variables.
Organización por paquetes según responsabilidad.
Inyección de dependencias mediante constructores.
Comentarios y documentación mediante JavaDoc.
Separación de responsabilidades entre controlador, servicio, repositorio y modelo.
Ejecución

Para ejecutar el proyecto se requiere:

Tener Java 8 instalado.
Tener Maven instalado.
Tener MySQL ejecutándose.
Tener creada la base de datos websolution.
Configurar la variable de entorno DB_PASSWORD.

Ejecutar:

mvn spring-boot:run

La aplicación estará disponible en:

http://localhost:8081/
Evidencia

Este proyecto corresponde a la evidencia:

GA7-220501096-AA3-EV01 - Codificación de módulos del software stand-alone, web y móvil.


### Importante

No pongas tu contraseña de MySQL en el README. 👍

Cuando hayas creado y guardado `README.md`, ejecuta:

```powershell
tree /F
