-- Ejercicio 1: ¿Cuál de los siguientes es un tipo de dato primitivo en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (1, 'String', false),
	   (1, 'int', true),
	   (1, 'ArrayList', false),
	   (1, 'Scanner', false);

-- Ejercicio 2: ¿Qué palabra clave se utiliza para declarar una variable entera en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (2, 'integer', false),
	   (2, 'int', true),
	   (2, 'var', false),
	   (2, 'num', false);

-- Ejercicio 3: ¿Qué valor tiene una variable int no inicializada en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (3, '0', true),
	   (3, 'null', false),
	   (3, 'undefined', false),
	   (3, 'throw error', false);

-- Ejercicio 4: ¿Cuál de estas estructuras se usa para repetir un bloque de código un número determinado de veces?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (4, 'if', false),
	   (4, 'switch', false),
	   (4, 'for', true),
	   (4, 'break', false);

-- Ejercicio 5: ¿Qué palabra clave se usa para salir de un bucle en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (5, 'exit', false),
	   (5, 'stop', false),
	   (5, 'break', true),
	   (5, 'return', false);

-- Ejercicio 6: ¿Cuál es el resultado lógico de la condición (true && false)?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (6, 'true', false),
	   (6, 'false', true),
	   (6, 'null', false),
	   (6, 'Error de compilación', false);

-- Ejercicio 7: ¿Qué es una clase en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (7, 'Una función que se ejecuta automáticamente', false),
	   (7, 'Una plantilla para crear objetos', true),
	   (7, 'Un método especial del sistema', false),
	   (7, 'Un archivo ejecutable', false);

-- Ejercicio 8: ¿Cómo se llama al proceso de crear una instancia de una clase?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (8, 'Compilación', false),
	   (8, 'Declaración', false),
	   (8, 'Instanciación', true),
	   (8, 'Inicialización de variable', false);

-- Ejercicio 9: ¿Qué palabra clave se utiliza para acceder a los miembros actuales del objeto?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (9, 'super', false),
	   (9, 'this', true),
	   (9, 'self', false),
	   (9, 'object', false);

-- Ejercicio 10: ¿Cuál de estas colecciones permite elementos duplicados y mantiene el orden de inserción?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (10, 'Set', false),
	   (10, 'Map', false),
	   (10, 'List', true),
	   (10, 'HashSet', false);

-- Ejercicio 11: ¿Qué clase implementarías para una colección que no permite duplicados?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (11, 'ArrayList', false),
	   (11, 'LinkedList', false),
	   (11, 'HashSet', true),
	   (11, 'Vector', false);

-- Ejercicio 12: ¿Cuál es la principal diferencia entre List y Set en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (12, 'Set permite duplicados, List no', false),
	   (12, 'List es una clase abstracta, Set no', false),
	   (12, 'List permite duplicados y mantiene el orden, Set no', true),
	   (12, 'No hay diferencia, ambas hacen lo mismo', false);
