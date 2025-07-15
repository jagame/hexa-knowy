-- All exercises of user.
SELECT pl.id_public_user,
	   c.title,
	   l.id,
	   ex.id,
	   pl.status,
	   pe.next_review,
	   pe.rate,
	   ex.question
FROM exercise ex
		 INNER JOIN lesson l
					ON l.id = ex.id_lesson
		 INNER JOIN public.course c
					ON c.id = l.id_course
		 INNER JOIN public_user_lesson pl
					ON l.id = pl.id_lesson
		 FULL JOIN public_user_exercise pe
				   ON pl.id_public_user = pe.id_public_user
WHERE pl.id_public_user = 2
GROUP BY ex.id, c.title, l.id, pl.id_public_user, pl.status, pe.next_review, pe.rate, ex.question;
-- All exercises by lesson id
SELECT pl.id_public_user,
	   c.title,
	   l.id,
	   ex.id,
	   pl.status,
	   pe.next_review,
	   pe.rate,
	   ex.question
FROM exercise ex
		 INNER JOIN lesson l
					ON l.id = ex.id_lesson
		 INNER JOIN public.course c
					ON c.id = l.id_course
		 INNER JOIN public_user_lesson pl
					ON l.id = pl.id_lesson
		 FULL JOIN public_user_exercise pe
				   ON pl.id_public_user = pe.id_public_user
WHERE pl.id_public_user = 2
  AND l.id = 1;
-- All lessons with user access
SELECT pl.id_public_user,
	   c.title,
	   l.id,
	   ex.id,
	   pl.status,
	   po.next_review,
	   po.rate,
	   ex.question
FROM exercise ex
		 INNER JOIN lesson l
					ON l.id = ex.id_lesson
		 INNER JOIN public.course c
					ON c.id = l.id_course
		 INNER JOIN public_user_lesson pl
					ON l.id = pl.id_lesson
		 FULL JOIN public_user_exercise po
				   ON pl.id_public_user = po.id_public_user
WHERE pl.id_public_user = 1
  AND pl.status != 'pending';

-- All exercises by lesson id V2
SELECT
	pe.id_public_user,
	pe.next_review,
	pe.rate,
	l.title,
	c.title
FROM public_user_exercise pe
	INNER JOIN exercise ex
		ON pe.id_exercise = ex.id
	INNER JOIN lesson l
		ON ex.id_lesson = l.id
	INNER JOIN course c
		ON l.id_course = c.id
WHERE
	pe.id_public_user = 2;

-- All lessons of user by course id
SELECT
	pl.id_public_user,
	pl.status,
	l.id AS lesson_id,
	l.title AS lesson_title,
	c.id AS course_id,
	c.title AS course_title
FROM public_user_lesson pl
	INNER JOIN lesson l
		ON pl.id_lesson = l.id
	INNER JOIN course c
		ON l.id_course = c.id
WHERE
	pl.id_public_user = 2;
-- exercises by course id
SELECT
	ex.id, ex.id_lesson, ex.question
FROM exercise ex
	INNER JOIN lesson l
		ON ex.id_lesson = l.id
WHERE l.id_course = 2;
-- Find the next exercise to do by course
SELECT
	pl.id_public_user,
	pl.status,
	l.id AS lesson_id,
	c.id AS course_id,
	ex.id,
	pex.rate,
	ex.question
FROM public_user_lesson pl
		 INNER JOIN lesson l
					ON pl.id_lesson = l.id
		 INNER JOIN course c
					ON l.id_course = c.id
		 INNER JOIN exercise ex
			ON l.id = ex.id_lesson
		 FULL JOIN public_user_exercise pex
				   ON ex.id = pex.id_exercise
WHERE
	pl.id_public_user = 2 AND
	c.id = 2
ORDER BY
	pex.rate NULLS FIRST,
	pex.next_review NULLS FIRST
LIMIT 1;
-- Find the next exercise to do by lesson
SELECT
	pl.id_public_user,
	pl.status,
	l.id AS lesson_id,
	c.id AS course_id,
	ex.id,
	pex.rate,
	ex.question
FROM public_user_lesson pl
		 INNER JOIN lesson l
					ON pl.id_lesson = l.id
		 INNER JOIN course c
					ON l.id_course = c.id
		 INNER JOIN exercise ex
					ON l.id = ex.id_lesson
		 FULL JOIN public_user_exercise pex
				   ON ex.id = pex.id_exercise
WHERE
	pl.id_public_user = 2 AND
	l.id = 5
ORDER BY
	pex.rate NULLS FIRST,
	pex.next_review NULLS FIRST
LIMIT 1;
-- Find the next exercise to do general
SELECT
	pl.id_public_user,
	pl.status,
	l.id AS lesson_id,
	c.id AS course_id,
	ex.id,
	pex.rate,
	ex.question
FROM public_user_lesson pl
		 INNER JOIN lesson l
					ON pl.id_lesson = l.id
		 INNER JOIN course c
					ON l.id_course = c.id
		 INNER JOIN exercise ex
					ON l.id = ex.id_lesson
		 FULL JOIN public_user_exercise pex
				   ON ex.id = pex.id_exercise
WHERE
	pl.id_public_user = 2
ORDER BY
	pex.rate NULLS FIRST,
	pex.next_review NULLS FIRST;
SELECT
	pl.id_public_user,
	ex.id,
	pex.next_review,
	pex.rate
FROM public_user_lesson pl
		 INNER JOIN lesson l
					ON pl.id_lesson = l.id
		 INNER JOIN course c
					ON l.id_course = c.id
		 INNER JOIN exercise ex
					ON l.id = ex.id_lesson
		 FULL JOIN public_user_exercise pex
				   ON ex.id = pex.id_exercise
WHERE
	pl.id_public_user = 2
ORDER BY
	pex.rate NULLS FIRST,
	pex.next_review NULLS FIRST,
	RANDOM();