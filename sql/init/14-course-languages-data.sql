TRUNCATE TABLE public.course_language RESTART IDENTITY CASCADE;
-- Linking courses with languages
INSERT INTO public.course_language (id_course, id_language) VALUES (1 , 2); -- Java
-- Curso 2: Java Avanzado
INSERT INTO public.course_language (id_course, id_language) VALUES (2, 2); -- Java

-- Curso 3: Desarrollo Web con Java
INSERT INTO public.course_language (id_course, id_language) VALUES (3, 2); -- Java
INSERT INTO public.course_language (id_course, id_language) VALUES (3, 3); -- HTML
INSERT INTO public.course_language (id_course, id_language) VALUES (3, 4); -- CSS

-- Curso 4: POO con Java
INSERT INTO public.course_language (id_course, id_language) VALUES (4, 2); -- Java

-- Curso 5: Estructuras de Datos en Java
INSERT INTO public.course_language (id_course, id_language) VALUES (5, 2); -- Java

-- Curso 6: Fundamentos de Spring
INSERT INTO public.course_language (id_course, id_language) VALUES (6, 2); -- Java

-- Curso 7: Spring Boot desde Cero
INSERT INTO public.course_language (id_course, id_language) VALUES (7, 2); -- Java

-- Curso 8: Java para Desarrolladores Web
INSERT INTO public.course_language (id_course, id_language) VALUES (8, 2); -- Java
INSERT INTO public.course_language (id_course, id_language) VALUES (8, 3); -- HTML

-- Curso 9: APIs RESTful con Spring
INSERT INTO public.course_language (id_course, id_language) VALUES (9, 2); -- Java

-- Curso 10: Persistencia con JPA e Hibernate
INSERT INTO public.course_language (id_course, id_language) VALUES (10, 2); -- Java

-- Curso 11: Testing de Aplicaciones Java
INSERT INTO public.course_language (id_course, id_language) VALUES (11, 2); -- Java

-- Curso 12: Curso Rápido de JavaFX
INSERT INTO public.course_language (id_course, id_language) VALUES (12, 2); -- Java

-- Curso 13: Introducción a Java
INSERT INTO public.course_language (id_course, id_language) VALUES (13, 2); -- Java

-- Curso 14: Introducción a JavaScript
INSERT INTO public.course_language (id_course, id_language) VALUES (14, 1); -- JavaScript

-- Curso 15: JavaScript Avanzado
INSERT INTO public.course_language (id_course, id_language) VALUES (15, 1); -- JavaScript

-- Curso 16: JavaScript para Desarrollo Web
INSERT INTO public.course_language (id_course, id_language) VALUES (16, 1); -- JavaScript
INSERT INTO public.course_language (id_course, id_language) VALUES (16, 3); -- HTML

-- Curso 17: Java Intermedio
INSERT INTO public.course_language (id_course, id_language) VALUES (17, 2); -- Java

-- Curso 18: Java para Aplicaciones Web
INSERT INTO public.course_language (id_course, id_language) VALUES (18, 2); -- Java
INSERT INTO public.course_language (id_course, id_language) VALUES (18, 3); -- HTML

-- Curso 19: Introducción a HTML5
INSERT INTO public.course_language (id_course, id_language) VALUES (19, 3); -- HTML

-- Curso 20: HTML y Accesibilidad
INSERT INTO public.course_language (id_course, id_language) VALUES (20, 3); -- HTML

-- Curso 21: CSS Básico
INSERT INTO public.course_language (id_course, id_language) VALUES (21, 4); -- CSS

-- Curso 22: CSS Avanzado
INSERT INTO public.course_language (id_course, id_language) VALUES (22, 4); -- CSS

-- Curso 23: Python para Expertos
INSERT INTO public.course_language (id_course, id_language) VALUES (23, 5); -- Python

-- Curso 24: Python para Análisis de Datos
INSERT INTO public.course_language (id_course, id_language) VALUES (24, 5); -- Python

-- Curso 25: Curso Básico de PHP
INSERT INTO public.course_language (id_course, id_language) VALUES (25, 6); -- PHP

-- Curso 26: PHP y Bases de Datos
INSERT INTO public.course_language (id_course, id_language) VALUES (26, 6); -- PHP

-- Curso 27: C# para Desarrollo de Software
INSERT INTO public.course_language (id_course, id_language) VALUES (27, 7); -- C#

-- Curso 28: C# Avanzado
INSERT INTO public.course_language (id_course, id_language) VALUES (28, 7); -- C#

-- Curso 29: TypeScript desde Cero
INSERT INTO public.course_language (id_course, id_language) VALUES (29, 8); -- TypeScript

-- Curso 30: TypeScript para Angular
INSERT INTO public.course_language (id_course, id_language) VALUES (30, 8); -- TypeScript

-- Curso 31: Ruby para Desarrollo Web
INSERT INTO public.course_language (id_course, id_language) VALUES (31, 9); -- Ruby

-- Curso 32: Swift Básico
INSERT INTO public.course_language (id_course, id_language) VALUES (32, 10); -- Swift