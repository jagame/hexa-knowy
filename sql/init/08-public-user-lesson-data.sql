-- Usuario 1: Curso 1 (lecciones 1-4)
INSERT INTO public.public_user_lesson (id_public_user, id_lesson, start_date, status)
VALUES (1, 1, '2025-06-10', 'completed'),
	   (1, 2, '2025-06-10', 'completed'),
	   (1, 3, '2025-06-10', 'in_progress'),
	   (1, 4, '2025-06-10', 'pending');

-- Usuario 2: Curso 2 (lecciones 5-8)
INSERT INTO public.public_user_lesson (id_public_user, id_lesson, start_date, status)
VALUES (2, 5, '2025-06-05', 'completed'),
	   (2, 6, '2025-06-05', 'in_progress'),
	   (2, 7, '2025-06-05', 'pending'),
	   (2, 8, '2025-06-05', 'pending');
