INSERT INTO public.lesson (id_course, id_next_lesson, title, explanation)
VALUES (1, 2, 'Fundamentos de Java', 'Introducción a variables, tipos de datos y operadores en Java.'),
	   (1, 3, 'Estructuras de Control', 'Explicación sobre if, switch, while, for y bucles en Java.'),
	   (1, 4, 'Clases y Objetos', 'Conceptos básicos de programación orientada a objetos en Java.'),
	   (1, NULL, 'Manejo de Excepciones', 'Cómo usar try, catch y finalmente para controlar errores.');

INSERT INTO public.lesson (id_course, id_next_lesson, title, explanation)
VALUES (2, 6, 'Colecciones en Java', 'Descripción detallada de List, Set, Map y sus implementaciones.'),
	   (2, 7, 'Programación Funcional', 'Introducción a lambdas y streams en Java 8 y superior.'),
	   (2, 8, 'Concurrency y Multihilo', 'Manejo de threads y sincronización en Java.'),
	   (2, NULL, 'Buenas Prácticas y Patrones', 'Patrones de diseño comunes y mejores prácticas en Java.');

INSERT INTO public.lesson (id_course, id_next_lesson, title, explanation)
VALUES (3, 10, 'Introducción a Servlets', 'Conceptos básicos sobre Servlets y ciclo de vida.'),
	   (3, NULL, 'Frameworks Web', 'Visión general de frameworks como Spring y JSF para desarrollo web.');
