-- Usuario 1 ha respondido algunas preguntas con distintos niveles de acierto
INSERT INTO public.public_user_option (id_public_user, id_option, rate)
VALUES
	(1, 2, 1.0),   -- Correcto completamente
	(1, 6, 0.5),   -- Falló antes, ahora respondió bien
	(1, 10, 0.0);  -- Siempre falla

-- Usuario 2 ha respondido diferente
INSERT INTO public.public_user_option (id_public_user, id_option, rate)
VALUES
	(2, 3, 0.3),   -- Bajo rendimiento
	(2, 8, 1.0),   -- Excelente
	(2, 12, 0.7);  -- Aciertos frecuentes pero no perfecto
