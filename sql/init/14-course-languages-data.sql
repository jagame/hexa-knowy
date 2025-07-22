TRUNCATE TABLE public.course_language RESTART IDENTITY CASCADE;
-- Linking courses with languages
INSERT INTO public.course_language (id_course, id_language) VALUES (1, 2);  -- Introducción a Java → Java

INSERT INTO public.course_language (id_course, id_language) VALUES (2, 1);  -- Introducción a JavaScript → JavaScript
INSERT INTO public.course_language (id_course, id_language) VALUES (3, 1);  -- JavaScript Avanzado → JavaScript
INSERT INTO public.course_language (id_course, id_language) VALUES (4, 1);  -- JavaScript para Desarrollo Web → JavaScript

INSERT INTO public.course_language (id_course, id_language) VALUES (5, 2);  -- Java Intermedio → Java
INSERT INTO public.course_language (id_course, id_language) VALUES (6, 2);  -- Java para Aplicaciones Web → Java

INSERT INTO public.course_language (id_course, id_language) VALUES (7, 3);  -- Introducción a HTML5 → HTML
INSERT INTO public.course_language (id_course, id_language) VALUES (8, 3);  -- HTML y Accesibilidad → HTML

INSERT INTO public.course_language (id_course, id_language) VALUES (9, 4);  -- CSS Básico → CSS
INSERT INTO public.course_language (id_course, id_language) VALUES (10, 4); -- CSS Avanzado → CSS

INSERT INTO public.course_language (id_course, id_language) VALUES (11, 5); -- Python para Principiantes → Python
INSERT INTO public.course_language (id_course, id_language) VALUES (12, 5); -- Python para Análisis de Datos → Python

INSERT INTO public.course_language (id_course, id_language) VALUES (13, 6); -- Curso Básico de PHP → PHP
INSERT INTO public.course_language (id_course, id_language) VALUES (14, 6); -- PHP y Bases de Datos → PHP

INSERT INTO public.course_language (id_course, id_language) VALUES (15, 7); -- C# para Desarrollo de Software → #C
INSERT INTO public.course_language (id_course, id_language) VALUES (16, 7); -- C# Avanzado → #C

INSERT INTO public.course_language (id_course, id_language) VALUES (17, 8); -- TypeScript desde Cero → TypeScript
INSERT INTO public.course_language (id_course, id_language) VALUES (18, 8); -- TypeScript para Angular → TypeScript

INSERT INTO public.course_language (id_course, id_language) VALUES (19, 9); -- Ruby para Desarrollo Web → Ruby
INSERT INTO public.course_language (id_course, id_language) VALUES (20, 10);-- Swift Básico → Swift
