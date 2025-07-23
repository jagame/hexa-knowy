TRUNCATE TABLE public.course RESTART IDENTITY CASCADE;
INSERT INTO public.course (title, description, author)
VALUES ('Introducción a Java', 'Curso básico para aprender fundamentos de Java.','Manuel Rodriguez');
-- Tabla de cursos para englobar las lecciones (id_course 2 al 32)
INSERT INTO public.course (id, title, description, author)
VALUES
	(2, 'Java Avanzado', 'Curso avanzado sobre características y mejores prácticas en Java.', 'Sofía Santiago'),
	(3, 'Desarrollo Web con Java', 'Aprende a crear aplicaciones web usando Java y frameworks populares.', 'Penelope Douglas'),
	(4, 'POO con Java', 'Domina la Programación Orientada a Objetos en Java, desde clases hasta herencia y polimorfismo.', 'Roberto Ibáñez'),
	(5, 'Estructuras de Datos en Java', 'Aprende a utilizar listas, pilas, colas y mapas en Java para resolver problemas comunes.', 'Elena Castro'),
	(6, 'Fundamentos de Spring', 'Introducción práctica al framework Spring para crear aplicaciones empresariales.', 'Lucía Méndez'),
	(7, 'Spring Boot desde Cero', 'Aprende a crear aplicaciones web modernas y microservicios con Spring Boot.', 'Mario Ramírez'),
	(8, 'Java para Desarrolladores Web', 'Crea aplicaciones web usando Java EE, JSP y servlets.', 'Ana Morales'),
	(9, 'APIs RESTful con Spring', 'Diseña y desarrolla APIs RESTful profesionales utilizando Spring MVC y Spring Boot.', 'Carlos Pereira'),
	(10, 'Persistencia con JPA e Hibernate', 'Utiliza JPA y Hibernate para la gestión moderna de bases de datos en aplicaciones Java.', 'Monserrat Lara'),
	(11, 'Testing de Aplicaciones Java', 'Aprende técnicas y frameworks para pruebas unitarias y de integración en Java.', 'Enrique Salinas'),
	(12, 'Curso Rápido de JavaFX', 'Construye interfaces gráficas profesionales y aplicaciones de escritorio con JavaFX.', 'Paula González'),
	(13, 'Introducción a Java', 'Curso básico para aprender fundamentos de Java.', 'Manuel Rodriguez'),
	(14, 'Introducción a JavaScript', 'Curso básico para aprender fundamentos y características esenciales de JavaScript.', 'Manuel Rodriguez'),
	(15, 'JavaScript Avanzado', 'Profundiza en JavaScript con temas como objetos, prototipos y programación funcional.', 'Laura Gómez'),
	(16, 'JavaScript para Desarrollo Web', 'Aprende a usar JavaScript para crear páginas web dinámicas e interactivas.', 'Carlos Martínez'),
	(17, 'Java Intermedio', 'Explora la programación orientada a objetos y colecciones en Java.', 'Ana López'),
	(18, 'Java para Aplicaciones Web', 'Desarrollo de aplicaciones web usando Java y frameworks populares.', 'Javier Pérez'),
	(19, 'Introducción a HTML5', 'Aprende a construir la estructura básica de páginas web con HTML5.', 'Sofía Díaz'),
	(20, 'HTML y Accesibilidad', 'Curso para crear sitios web accesibles y optimizados usando buenas prácticas de HTML.', 'David Ramírez'),
	(21, 'CSS Básico', 'Curso para aprender a definir estilos y layouts con CSS3.', 'Marta Sánchez'),
	(22, 'CSS Avanzado', 'Diseño responsivo y animaciones con CSS avanzado.', 'Lucía Fernández'),
	(23, 'Python para Principiantes', 'Introducción a Python para quienes quieren empezar a programar desde cero.', 'Miguel Herrera'),
	(24, 'Python para Análisis de Datos', 'Curso para usar Python en procesamiento y análisis de datos.', 'Elena Torres'),
	(25, 'Curso Básico de PHP', 'Formación introductoria sobre PHP para desarrollo web dinámico.', 'Ricardo Morales'),
	(26, 'PHP y Bases de Datos', 'Aprende a conectar PHP con bases de datos MySQL y manejar datos eficientemente.', 'Valeria Ortiz'),
	(27, 'C# para Desarrollo de Software', 'Aprende los fundamentos de la programación con C# y .NET.', 'Andrés Gómez'),
	(28, 'C# Avanzado', 'Profundiza en el desarrollo de aplicaciones desktop y web con C#.', 'Laura Medina'),
	(29, 'TypeScript desde Cero', 'Conoce TypeScript, un superconjunto de JavaScript para programación tipada.', 'Pablo Sánchez'),
	(30, 'TypeScript para Angular', 'Uso de TypeScript específico para desarrollo con Angular.', 'Sergio Castillo'),
	(31, 'Ruby para Desarrollo Web', 'Aprende Ruby y su uso para crear aplicaciones web con Ruby on Rails.', 'María Jiménez'),
	(32, 'Swift Básico', 'Curso de introducción a Swift para desarrollo de apps iOS.', 'José Ramírez');