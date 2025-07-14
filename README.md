# Knowy – Plataforma de Aprendizaje con Repetición Espaciada

Knowy es una aplicación web centrada en el aprendizaje eficaz y sostenible mediante la técnica de repetición
espaciada, inspirada en herramientas como Anki. Knowy tiene como objetivo consolidar el conocimiento de forma más
efectiva y productiva, enfocándose en reforzar aquellos contenidos que resultan más difíciles para cada usuario, y
permitiendo además valorar el grado de dificultad de las actividades propuestas.

## Índice de contenidos del proyecto Knowy

1. [Sobre el proyecto](#sobre-el-proyecto)
	- [Objetivo del proyecto](#objetivo-del-proyecto)
	- [Por qué Knowy](#por-qué-knowy)
	- [Tecnologías y herramientas utilizadas](#️-tecnologías-y-herramientas-utilizadas)
	- [Requisitos](#requisitos)
2. [Primeros pasos](#primeros-pasos)
	- [Requisitos previos](#requisitos-previos)
    - [Instalación y ejecución Docker](#-Instalación-y-ejecución-Docker)
	- [Arranque manual del servidor Java](#-Arranque-manual-del-servidor-Java)
3. [Uso](#uso)
4. [Contribuciones](#contribuciones)
5. [Licencia](#licencia)
6. [Contacto](#contacto)
7. [Agradecimientos](#agradecimientos)

## Sobre el proyecto

### Objetivo del proyecto

Knowy está especialmente diseñado para apoyar el aprendizaje de programación en diferentes áreas, un campo en constante
crecimiento que, sin embargo, aún carece de herramientas específicas centradas en metodologías científicas de estudio
como la repetición espaciada. Nuestra plataforma busca cerrar esa brecha, facilitando el crecimiento profesional y
técnico de sus usuarios.

### Por qué Knowy

El nombre Knowy nace del término anglosajón “Know” (conocer, saber), alineado con el objetivo principal del proyecto:
ampliar el conocimiento y fomentar el crecimiento profesional. Con un enfoque actual y accesible, Knowy combina el
conocimiento con un toque divertido y cercano.

## 🛠️ Tecnologías y herramientas utilizadas

Knowy está construido utilizando una arquitectura moderna dividida en backend, frontend y herramientas de
infraestructura:

### 🔧 Backend

- **Java 21** – Lenguaje principal.
- **Spring Boot 3.4.5** – Framework para construir la API REST y lógica de negocio.
- **Spring Security** – Seguridad de la aplicación.
- **Spring Data JPA** – Acceso a datos mediante ORM.
- **Hibernate** – Implementación de JPA.
- **Thymeleaf** – Motor de plantillas del lado del servidor.
- **Thymeleaf Layout Dialect** – Extensión para diseño de plantillas reutilizables.
- **Spring Mail** – Envío de correos electrónicos.
- **JJWT (JSON Web Token)** – Autenticación basada en tokens (JWT).
- **Lombok** – Reducción de código boilerplate mediante anotaciones.

### 📦 Construcción y gestión de dependencias

- **Maven** – Sistema de construcción y gestión de dependencias.
- **Maven Compiler Plugin** – Compilación y anotaciones (Lombok).
- **Spring Boot Maven Plugin** – Empaquetado de la aplicación.
- **Sass CLI Maven Plugin** – Compilación de estilos Sass a CSS.

### 💾 Bases de datos y almacenamiento

- **PostgreSQL** – Base de datos relacional.

### 🖥️ Frontend

- **Thymeleaf** – Motor de plantillas del lado del servidor.
- **Bootstrap** – Framework CSS para diseño responsivo.
- **SCSS (Sass)** – Preprocesador CSS para estilos personalizados.

### 🐳 Contenedores y despliegue

- **Docker** – Contenedorización de la aplicación.
- **Docker Compose** – Orquestación de servicios backend/frontend/db.
- **Mailpit** – Servidor SMTP para desarrollo y pruebas de envío de correos.

## Requisitos

- **Java 21** (JDK 21 o superior)
- **Docker**
- **Docker Compose**

## 🏁 Primeros pasos

### Requisitos previos

Antes de comenzar, asegúrate de tener instaladas las siguientes herramientas en tu entorno de desarrollo:

- [Java 21 JDK](https://adoptium.net/en-GB/temurin/releases/)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- (Opcional) Un IDE como **IntelliJ IDEA** o **VS Code** con soporte para Java y Spring Boot.

Puedes verificar las versiones instaladas ejecutando:

```bash
java -version
mvn -v
docker -v
docker compose version
```

### 🚀 Instalación y ejecución Docker

#### 1. Clona el repositorio

```bash
git clone https://github.com/Knowy-Learn/knowy.git
cd knowy
```

#### 2. Levanta los servicios con Docker

Tienes dos opciones para iniciar todos los servicios (backend, base de datos, correo, frontend):

- **Opción A:** Desde la terminal
  Regresa al directorio raíz (donde está el archivo docker-compose.yml) y ejecuta:
   ```bash
   docker compose up -d
   ```

- **Opción B:** Usando IntelliJ IDEA
  El proyecto incluye archivos .run configurados para IntelliJ IDEA que permiten iniciar y detener todos los servicios
  Docker con un solo clic desde el IDE.
  Abre el proyecto en IntelliJ, busca la carpeta .run y ejecuta la configuración "Knowy-Deploy" para levantar los
  contenedores.
  Esto facilita el desarrollo y las pruebas sin salir del entorno.

Puedes verificar que los contenedores estén corriendo con:

```bash
docker compose ps
```

Esto iniciará:

- PostgreSQL
- Mailpit
- La aplicación Java Knowy

#### 3. Accede a la aplicación

Abre tu navegador y visita: http://localhost:8080
Si la aplicación está corriendo correctamente, deberías ver la interfaz principal de Knowy.

También puedes acceder a http://localhost:8025
para utilizar la interfaz del cliente SMTP (Mailpit), útil para gestionar y visualizar correos enviados.

### 🔧 Arranque manual del servidor Java

Si prefieres levantar solo la aplicación Java manualmente y usar Docker únicamente para los servicios de soporte (base
de datos, correo), sigue estos pasos:

#### 1. Asegúrate de tener instalado Java (JDK 21 o superior).

#### 2. Inicia los servicios docker de base de datos y correo

Tienes dos opciones para iniciar todos los servicios (base de datos, correo):

- **Opción A:** Desde la terminal
  Regresa al directorio raíz (donde está el archivo compose-dev-onlydb.yaml) y ejecuta:
   ```bash
   docker compose -f compose-dev-onlydb.yaml up -d
   docker compose -f compose-dev-mailpit.yaml up -d
   ```

- **Opción B:** Usando IntelliJ IDEA
  Opción B: Usando IntelliJ IDEA
  Ejecuta las configuraciones "Knowy-Dev-OnlyDB" y "Knowy-Dev-Mailpit" desde la carpeta .run en el IDE.

Esto levantará:

- PostgreSQL
- PgAdmin
- Mailpit

Puedes verificar que los servicios estén corriendo con:

```bash
docker compose ps
```

#### 3. Compila el proyecto con Maven (desde la raíz del proyecto):

```bash
./mvnw clean package
```

#### 4. Ejecuta el archivo JAR generado (normalmente en target/):

```bash
java -jar target/server-server-0.9.0-SNAPSHOT.jar --spring.profiles.active=dev
```

#### 5. Accede a la aplicación

Una vez iniciado el servidor, estará disponible en:
http://localhost:8080<br>
El cliente de correo estará disponible en:
http://localhost:8025<br>

### 🛠️ Solución rápida de problemas comunes

- **Docker Compose no encontrado:** Asegúrate de que Docker Compose esté instalado y en tu PATH. En versiones recientes
  de Docker Desktop viene integrado.
- **Puertos ocupados:** Cambia el puerto en el archivo docker-compose.yml o detén la aplicación que esté usando el
  puerto.
- **Errores en compilación Maven:** Confirma que tu JDK está en versión 21 y que Maven es 3.8 o superior. También revisa
  que tengas conexión a internet para descargar dependencias.
- **No se conecta a la base de datos:** Verifica que el contenedor PostgreSQL esté corriendo y que las credenciales
  coincidan con las configuradas en el backend.

## Uso

_(Por completar)_

## Contribuciones

_(Por completar)_

## Licencia

_(Por completar)_

## Contacto

_(Por completar)_
