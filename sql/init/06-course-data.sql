TRUNCATE TABLE public.course RESTART IDENTITY CASCADE;

-- Curso inicial (sin ID especificado, usará ID = 1)
INSERT INTO public.course (title, description, author, image)
VALUES ('Introducción a Java', 'Curso básico para aprender fundamentos de Java.','Manuel Rodriguez', '/images/languages/java.png');

-- Cursos con ID específicos
INSERT INTO public.course (id, title, description, author, image)
VALUES
	(2,  'Java Avanzado', 'Curso avanzado sobre características y mejores prácticas en Java.', 'Sofía Santiago', '/images/languages/java.png'),
	(3,  'Desarrollo Web con Java', 'Aprende a crear aplicaciones web usando Java y frameworks populares.', 'Penelope Douglas', '/images/languages/java.png'),
	(4,  'POO con Java', 'Domina la Programación Orientada a Objetos en Java, desde clases hasta herencia y polimorfismo.', 'Roberto Ibáñez', '/images/languages/java.png'),
	(5,  'Estructuras de Datos en Java', 'Aprende a utilizar listas, pilas, colas y mapas en Java para resolver problemas comunes.', 'Elena Castro', '/images/languages/java.png'),
	(6,  'Fundamentos de Spring', 'Introducción práctica al framework Spring para crear aplicaciones empresariales.', 'Lucía Méndez', '/images/languages/java.png'),
	(7,  'Spring Boot desde Cero', 'Aprende a crear aplicaciones web modernas y microservicios con Spring Boot.', 'Mario Ramírez', '/images/languages/java.png'),
	(8,  'Java para Desarrolladores Web', 'Crea aplicaciones web usando Java EE, JSP y servlets.', 'Ana Morales', '/images/languages/java.png'),
	(9,  'APIs RESTful con Spring', 'Diseña y desarrolla APIs RESTful profesionales utilizando Spring MVC y Spring Boot.', 'Carlos Pereira', '/images/languages/java.png'),
	(10, 'Persistencia con JPA e Hibernate', 'Utiliza JPA y Hibernate para la gestión moderna de bases de datos en aplicaciones Java.', 'Monserrat Lara', '/images/languages/java.png'),
	(11, 'Testing de Aplicaciones Java', 'Aprende técnicas y frameworks para pruebas unitarias y de integración en Java.', 'Enrique Salinas', '/images/languages/java.png'),
	(12, 'Curso Rápido de JavaFX', 'Construye interfaces gráficas profesionales y aplicaciones de escritorio con JavaFX.', 'Paula González', '/images/languages/java.png'),
	(13, 'Introducción a Java', 'Curso básico para aprender fundamentos de Java.', 'Manuel Rodriguez', '/images/languages/java.png'),

	(14, 'Introducción a JavaScript', 'Curso básico para aprender fundamentos y características esenciales de JavaScript.', 'Manuel Rodriguez', '/images/languages/javascript.png'),
	(15, 'JavaScript Avanzado', 'Profundiza en JavaScript con temas como objetos, prototipos y programación funcional.', 'Laura Gómez', '/images/languages/javascript.png'),
	(16, 'JavaScript para Desarrollo Web', 'Aprende a usar JavaScript para crear páginas web dinámicas e interactivas.', 'Carlos Martínez', '/images/languages/javascript.png'),

	(17, 'Java Intermedio', 'Explora la programación orientada a objetos y colecciones en Java.', 'Ana López', '/images/languages/java.png'),
	(18, 'Java para Aplicaciones Web', 'Desarrollo de aplicaciones web usando Java y frameworks populares.', 'Javier Pérez', '/images/languages/java.png'),

	(19, 'Introducción a HTML5', 'Aprende a construir la estructura básica de páginas web con HTML5.', 'Sofía Díaz', '/images/languages/html.png'),
	(20, 'HTML y Accesibilidad', 'Curso para crear sitios web accesibles y optimizados usando buenas prácticas de HTML.', 'David Ramírez', '/images/languages/html.png'),

	(21, 'CSS Básico', 'Curso para aprender a definir estilos y layouts con CSS3.', 'Marta Sánchez', '/images/languages/css.png'),
	(22, 'CSS Avanzado', 'Diseño responsivo y animaciones con CSS avanzado.', 'Lucía Fernández', '/images/languages/css.png'),

	(23, 'Python para Principiantes', 'Introducción a Python para quienes quieren empezar a programar desde cero.', 'Miguel Herrera', '/images/languages/python.png'),
	(24, 'Python para Análisis de Datos', 'Curso para usar Python en procesamiento y análisis de datos.', 'Elena Torres', '/images/languages/python.png'),

	(25, 'Curso Básico de PHP', 'Formación introductoria sobre PHP para desarrollo web dinámico.', 'Ricardo Morales', '/images/languages/php.png'),
	(26, 'PHP y Bases de Datos', 'Aprende a conectar PHP con bases de datos MySQL y manejar datos eficientemente.', 'Valeria Ortiz', '/images/languages/php.png'),

	(27, 'C# para Desarrollo de Software', 'Aprende los fundamentos de la programación con C# y .NET.', 'Andrés Gómez', '/images/languages/c-sharp.png'),
	(28, 'C# Avanzado', 'Profundiza en el desarrollo de aplicaciones desktop y web con C#.', 'Laura Medina', '/images/languages/c-sharp.png'),

	(29, 'TypeScript desde Cero', 'Conoce TypeScript, un superconjunto de JavaScript para programación tipada.', 'Pablo Sánchez', 'images/languages/typescript.png'),
	(30, 'TypeScript para Angular', 'Uso de TypeScript específico para desarrollo con Angular.', 'Sergio Castillo', 'images/languages/typescript.png'),

	(31, 'Ruby para Desarrollo Web', 'Aprende Ruby y su uso para crear aplicaciones web con Ruby on Rails.', 'María Jiménez', 'images/languages/ruby.png'),

	(32, 'Swift Básico', 'Curso de introducción a Swift para desarrollo de apps iOS.', 'José Ramírez', 'images/languages/swift.png');
