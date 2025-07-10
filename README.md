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
	- [Instalación](#instalación)
3. [Uso](#uso)
4. [Hoja de ruta](#hoja-de-ruta)
5. [Contribuciones](#contribuciones)
6. [Licencia](#licencia)
7. [Contacto](#contacto)
8. [Agradecimientos](#agradecimientos)

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
- **Javax Validation** – Validación de datos.
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

### 🚀 Instalación y ejecución

#### 1. Clona el repositorio

```bash
git clone https://github.com/Knowy-Learn/knowy.git
cd knowy
```

#### 2. Compilación manual del backend (opcional)

Si quieres ejecutar el backend localmente y la base de datos por separado (por ejemplo, solo la BBDD con Docker y el
servidor desde tu IDE), entonces compila el backend manualmente con:
```bash
cd server
mvn clean install
```

Esto descargará las dependencias y compilará el proyecto usando el pom.xml.
> Nota: Si vas a usar la opción Docker para levantar todo el proyecto (backend + base de datos + frontend), no
> necesitas compilar manualmente, ya que el contenedor Docker se encargará de compilar y ejecutar el backend
> automáticamente.

Esto descargará las dependencias y compilará el proyecto usando el pom.xml.

#### 3. Levanta los servicios con Docker

Tienes dos opciones para iniciar todos los servicios (backend, base de datos, correo, frontend):

- **Opción A:** Desde la terminal
  Regresa al directorio raíz (donde está el archivo docker-compose.yml) y ejecuta:
   ```bash
   docker compose up -d
   ```

- **Opción B:** Usando IntelliJ IDEA
  El proyecto incluye archivos .run configurados para IntelliJ IDEA que permiten iniciar y detener todos los servicios
  Docker con un solo clic desde el IDE.
  Simplemente abre el proyecto en IntelliJ, busca en la carpeta .run y ejecuta la configuración llamada "Knowy-Deploy"
  para
  levantar los contenedores.
  Esto facilita el desarrollo y prueba sin salir del entorno.

Puedes verificar que los contenedores estén corriendo con:

```bash
docker compose ps
```

Esto iniciará:

- PostgreSQL
- Mailpit
- La aplicación Knowy

#### 4. Accede a la aplicación

Abre tu navegador web y visita:http://localhost:8080<br>
Si la aplicación está corriendo correctamente, verás la interfaz principal de Knowy.

### 🛠️ Solución rápida de problemas comunes

- **Docker Compose no encontrado:** Asegúrate de que Docker Compose esté instalado y en tu PATH. En versiones recientes
  de Docker Desktop viene integrado.
- **Puerto 8080 ocupado:** Cambia el puerto en el archivo docker-compose.yml o detén la aplicación que esté usando el
  puerto.
- **Errores en compilación Maven:** Confirma que tu JDK está en versión 21 y que Maven es 3.8 o superior. También revisa
  que
  tengas conexión a internet para descargar dependencias.
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
