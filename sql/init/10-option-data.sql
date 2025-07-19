TRUNCATE TABLE public.option RESTART IDENTITY CASCADE;
-- Ejercicio 1: ¿Qué significa que Java sea "multiplataforma"?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (1, 'Que solo se puede usar en Windows', false),
	   (1, 'Que puede ejecutarse en distintos sistemas sin cambiar el código', true),
	   (1, 'Que necesita diferentes versiones para cada sistema operativo', false),
	   (1, 'Que no necesita ser compilado', false);

-- Ejercicio 2: ¿Cuál es el nombre del componente que permite ejecutar el bytecode en cualquier sistema operativo?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (2, 'JDK', false),
	   (2, 'JVM', true),
	   (2, 'JRE', false),
	   (2, 'IDE', false);

-- Ejercicio 3: Verdadero o falso: En Java, puedes ejecutar directamente el archivo .java sin compilarlo antes.
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (3, 'Verdadero', true),
	   (3, 'Falso', false);

-- Ejercicio 4: ¿Qué papel cumple el JDK (Java Development Kit)?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (4, 'Ejecuta los programas Java', false),
	   (4, 'Controla los errores en tiempo de ejecución', false),
	   (4, 'Compila el código fuente en bytecode', true),
	   (4, 'Es un editor de texto para programadores', false);

-- Ejercicio 5: ¿Qué archivo se genera tras compilar un programa Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (5, '.exe', false),
	   (5, '.class', true),
	   (5, '.txt', false),
	   (5, '.jar', false);

-- Ejercicio 6: Verdadero o falso: Java es un lenguaje débilmente tipado, por eso no es necesario declarar el tipo de las variables.
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (6, 'Verdadero', false),
	   (6, 'Falso', true);

-- Ejercicio 7: ¿Qué ventaja principal ofrece la orientación a objetos en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (7, 'Hace que el programa sea más rápido', false),
	   (7, 'Permite usar emojis en el código', false),
	   (7, 'Organiza el código de forma modular y reutilizable', true),
	   (7, 'Elimina la necesidad de usar funciones', false);

-- Ejercicio 8: Verdadero o falso: La JVM traduce el código fuente de Java directamente al lenguaje máquina.
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (8, 'Verdadero', false),
	   (8, 'Falso', true);

-- Ejercicio 9: ¿Qué extensión tiene un archivo que contiene código fuente en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (9, '.class', false),
	   (9, '.xml', false),
	   (9, '.java', true),
	   (9, '.js', false);

-- Ejercicio 10: Verdadero o falso: Para programar en Java solo necesitas tener instalada la JVM.
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (10, 'Falso', true),
	   (10, 'Verdadero', false);
