TRUNCATE TABLE public.exercises RESTART IDENTITY CASCADE;
-- Lección 1: ¿Qué es Java y cómo funciona?
INSERT INTO public.exercises (id_lesson, question)
VALUES (2, '¿Qué significa que Java sea "multiplataforma"?'),
	   (2, '¿Cuál es el nombre del componente que permite ejecutar el bytecode en cualquier sistema operativo?'),
	   (2, 'Verdadero o falso: En Java, puedes ejecutar directamente el archivo .java sin compilarlo antes.'),
	   (2, '¿Qué papel cumple el JDK (Java Development Kit)?'),
	   (2, '¿Qué archivo se genera tras compilar un programa Java?'),
	   (2, 'Verdadero o falso: Java es un lenguaje débilmente tipado, por eso no es necesario declarar el tipo de las variables.'),
	   (2, '¿Qué ventaja principal ofrece la orientación a objetos en Java?'),
	   (2, 'Verdadero o falso: La JVM traduce el código fuente de Java directamente al lenguaje máquina.'),
	   (2, '¿Qué extensión tiene un archivo que contiene código fuente en Java?'),
	   (2, 'Verdadero o falso: Para programar en Java solo necesitas tener instalada la JVM.');

-- Lección 2: Estructuras de Control
INSERT INTO public.exercises (id_lesson, question)
VALUES (3, '¿Cuál de estas estructuras se usa para repetir un bloque de código un número determinado de veces?'),
	   (3, '¿Qué palabra clave se usa para salir de un bucle en Java?'),
	   (3, '¿Cuál es el resultado lógico de la condición (true && false)?');

-- Lección 3: Clases y Objetos
INSERT INTO public.exercises (id_lesson, question)
VALUES (4, '¿Qué es una clase en Java?'),
	   (4, '¿Cómo se llama al proceso de crear una instancia de una clase?'),
	   (4, '¿Qué palabra clave se utiliza para acceder a los miembros actuales del objeto?');

-- Lección 6: Colecciones en Java
INSERT INTO public.exercises (id_lesson, question)
VALUES (5, '¿Cuál de estas colecciones permite elementos duplicados y mantiene el orden de inserción?'),
	   (5, '¿Qué clase implementarías para una colección que no permite duplicados?'),
	   (5, '¿Cuál es la principal diferencia entre List y Set en Java?');
