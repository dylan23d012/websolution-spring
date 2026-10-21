# WebSolution-Spring

## 1. Descripción del proyecto

WebSolution es una aplicación para la gestión interna de una empresa dedicada al diseño y desarrollo de páginas web. El sistema permite administrar clientes, proyectos, servicios y tareas mediante una API REST y una interfaz web.

Este proyecto se desarrolla como parte de la evidencia GA7-220501096-AA5-EV03.

## 2. Tecnologías utilizadas

- Java 8
- Spring Boot 2.7.18
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL 8.4
- Maven
- React y Vite
- HTML, CSS y JavaScript
- Git y GitHub

## 3. Arquitectura del sistema

El backend utiliza una arquitectura por capas para separar las responsabilidades:

- **Controlador:** recibe las solicitudes HTTP y devuelve las respuestas.
- **Servicio:** contiene la lógica de negocio.
- **Repositorio:** permite consultar y modificar los datos mediante Spring Data JPA.
- **Modelo:** representa las entidades del sistema.
- **Manejo global de errores:** centraliza respuestas para errores de validación y otras excepciones.

El flujo general es:

Interfaz React → Controlador REST → Servicio → Repositorio → MySQL.

## 4. Módulos y entidades

El proyecto contiene los siguientes módulos:

- **Clientes:** administra la información de los clientes.
- **Proyectos:** administra los proyectos y su relación con los clientes.
- **Servicios:** permite gestionar los servicios ofrecidos por la empresa.
- **Tareas:** permite gestionar las tareas asociadas a los proyectos.

Las entidades implementadas corresponden a las tablas existentes en la base de datos. La estructura y las relaciones se deben consultar en `database.sql` y en la configuración de MySQL.

## 5. Endpoints de la API REST

La API utiliza la dirección base `http://localhost:8081`.

### Clientes

| Método HTTP | Endpoint | Operación |
|---|---|---|
| GET | `/api/clientes` | Consultar todos los clientes |
| GET | `/api/clientes/{id}` | Consultar un cliente por ID |
| POST | `/api/clientes` | Registrar un cliente |
| PUT | `/api/clientes/{id}` | Actualizar un cliente |
| DELETE | `/api/clientes/{id}` | Eliminar un cliente |

### Proyectos

| Método HTTP | Endpoint | Operación |
|---|---|---|
| GET | `/api/proyectos` | Consultar todos los proyectos |
| GET | `/api/proyectos/{id}` | Consultar un proyecto por ID |
| POST | `/api/proyectos` | Registrar un proyecto |
| PUT | `/api/proyectos/{id}` | Actualizar un proyecto |
| DELETE | `/api/proyectos/{id}` | Eliminar un proyecto |

### Servicios

| Método HTTP | Endpoint | Operación |
|---|---|---|
| GET | `/api/servicios` | Consultar todos los servicios |
| GET | `/api/servicios/{id}` | Consultar un servicio por ID |
| POST | `/api/servicios` | Registrar un servicio |
| PUT | `/api/servicios/{id}` | Actualizar un servicio |
| DELETE | `/api/servicios/{id}` | Eliminar un servicio |

### Tareas

| Método HTTP | Endpoint | Operación |
|---|---|---|
| GET | `/api/tareas` | Consultar todas las tareas |
| GET | `/api/tareas/{id}` | Consultar una tarea por ID |
| POST | `/api/tareas` | Registrar una tarea |
| PUT | `/api/tareas/{id}` | Actualizar una tarea |
| DELETE | `/api/tareas/{id}` | Eliminar una tarea |

## 6. Validaciones y manejo de errores

La aplicación incorpora validaciones para los datos recibidos y un manejador global de excepciones.

Entre las respuestas HTTP utilizadas se encuentran:

- `200 OK`: solicitud procesada correctamente.
- `201 Created`: registro creado correctamente.
- `204 No Content`: eliminación realizada correctamente.
- `400 Bad Request`: datos inválidos o solicitud mal formada.
- `404 Not Found`: recurso no encontrado.
- `409 Conflict`: operación que incumple una restricción de integridad.

Las respuestas específicas dependen del endpoint y del tipo de error.

## 7. Base de datos y configuración

La aplicación utiliza la base de datos MySQL `websolution`, configurada para conectarse al puerto `3307` en el entorno local.

La conexión se configura en `src/main/resources/application.properties`. La contraseña se obtiene mediante la variable de entorno `DB_PASSWORD` y no debe escribirse directamente en el código ni publicarse en GitHub.

La propiedad `spring.jpa.hibernate.ddl-auto=validate` permite comprobar la correspondencia entre las entidades y el esquema existente. Por tanto, la base de datos debe estar creada y tener las tablas necesarias antes de iniciar el backend.

## 8. Requisitos previos

- Java 8 instalado.
- Maven instalado.
- MySQL en ejecución.
- Base de datos `websolution` configurada.
- Variable de entorno `DB_PASSWORD` definida.
- Node.js y npm instalados para ejecutar el frontend.

## 9. Ejecución del backend

Desde la carpeta raíz del proyecto, ejecutar:

```bash
mvn clean compile
mvn spring-boot:run
```

El backend estará disponible en:

`http://localhost:8081`

Para consultar los clientes, por ejemplo, se puede acceder a:

`http://localhost:8081/api/clientes`

## 10. Ejecución del frontend

Abrir otra terminal y ejecutar:

```bash
cd frontend
npm install
npm run dev
```

Vite mostrará en la terminal la dirección local en la que se encuentra disponible la interfaz.

## 11. Pruebas realizadas

Se comprobó que los endpoints de clientes, proyectos, servicios y tareas responden a solicitudes GET en el entorno local.

También se compiló el proyecto Java mediante Maven. Antes de entregar la evidencia, se deben completar y documentar las pruebas de creación, consulta individual, actualización, eliminación y respuestas de error de cada módulo.

## 12. Control de versiones

El proyecto se gestiona mediante Git y GitHub. Los cambios deben registrarse en commits con mensajes descriptivos, evitando incluir archivos generados como `target/`, dependencias instaladas como `node_modules/` o contraseñas.

## 13. Repositorio

https://github.com/dylan23d012/websolution-spring

## 14. Evidencia

GA7-220501096-AA5-EV03: Diseño y desarrollo de servicios web para el proyecto de software.
