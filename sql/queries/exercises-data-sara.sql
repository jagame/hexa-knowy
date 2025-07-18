INSERT INTO public.exercise (id_lesson, question)
VALUES
	(5, '¿La interfaz List en Java permite elementos duplicados?'),
	(5, '¿La interfaz Set en Java garantiza el orden de inserción?');

INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
-- Ejercicio 1
(10, 'Verdadero', true),
(10, 'Falso', false),
-- Ejercicio 2
(11, 'Verdadero', false),
(11, 'Falso', true);

INSERT INTO public.exercise (id_lesson, question)
VALUES
	(6, '¿Las expresiones lambda en Java permiten pasar funciones como parámetros?'),
	(6, '¿La programación funcional promueve el uso de estado mutable?');

INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
-- Ejercicio 3
(12, 'Verdadero', true),
(12, 'Falso', false),
-- Ejercicio 4
(13, 'Verdadero', false),
(13, 'Falso', true);

INSERT INTO public.exercise (id_lesson, question)
VALUES
	(7, '¿El uso de la palabra clave "synchronized" en Java ayuda a prevenir condiciones de carrera?'),
	(7, '¿Los hilos en Java comparten el mismo espacio de memoria por defecto?');

INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
-- Ejercicio 5
(14, 'Verdadero', true),
(14, 'Falso', false),
-- Ejercicio 6
(15, 'Verdadero', true),
(15, 'Falso', false);

INSERT INTO public.exercise (id_lesson, question)
VALUES
	(8, '¿El patrón Singleton asegura que solo haya una instancia de una clase?'),
	(8, '¿Es una buena práctica acoplar fuertemente las clases en un sistema?');

INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
-- Ejercicio 7
(16, 'Verdadero', true),
(16, 'Falso', false),
-- Ejercicio 8
(17, 'Verdadero', false),
(17, 'Falso', true);

INSERT INTO  public.public_user_exercise (id_public_user, id_exercise, next_review, rate)
VALUES
	(2, 10, NOW(), 50),
	(2, 11, NOW(), 0)
