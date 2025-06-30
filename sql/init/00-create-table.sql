CREATE TABLE IF NOT EXISTS public.public_user
(
	id       serial      NOT NULL,
	nickname varchar(50) NOT NULL UNIQUE,
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.private_user
(
	id       integer      NOT NULL,
	email    varchar(100) NOT NULL UNIQUE,
	password varchar(100) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.mission
(
	id             serial      NOT NULL,
	description    varchar(60) NOT NULL,
	total_progress integer     NOT NULL CHECK ( total_progress >= 0 ),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.public_user_mission
(
	id_public_user   integer NOT NULL,
	id_mission       integer NOT NULL,
	current_progress integer DEFAULT 0 CHECK ( current_progress >= 0 ),
	init_date        date    NOT NULL,
	PRIMARY KEY (id_public_user, id_mission)
);

CREATE TABLE IF NOT EXISTS public.course
(
	id          serial NOT NULL,
	title       varchar(100),
	description varchar(250),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.lesson
(
	id             serial       NOT NULL,
	id_course      integer      NOT NULL,
	id_next_lesson integer,
	title          varchar(100) NOT NULL,
	explanation    text         NOT NULL DEFAULT '',
	PRIMARY KEY (id),
	UNIQUE (id_next_lesson)
);

CREATE TABLE IF NOT EXISTS public.public_user_lesson
(
	id_public_user integer     NOT NULL,
	id_lesson      integer     NOT NULL,
	start_date     date        NOT NULL DEFAULT current_date,
	status         varchar(20) NOT NULL DEFAULT 'pending', -- 'pending' , 'in_progress', 'completed'
	PRIMARY KEY (id_public_user, id_lesson)
);

CREATE TABLE IF NOT EXISTS public.exercises
(
	id        serial  NOT NULL,
	id_lesson integer NOT NULL,
	question  text    NOT NULL DEFAULT '',
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.option
(
	id          serial  NOT NULL,
	id_exercise integer NOT NULL,
	option_text text    NOT NULL DEFAULT '',
	is_correct  boolean NOT NULL DEFAULT false,
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.public_user_option
(
	id_public_user integer   NOT NULL,
	id_option      integer   NOT NULL,
	answer_date    timestamp NOT NULL DEFAULT current_timestamp,
	rate           numeric   NOT NULL DEFAULT 0,
	PRIMARY KEY (id_public_user, id_option)
);

CREATE TABLE IF NOT EXISTS public.banned_words
(
	id			serial 		NOT NULL,
	word		varchar(40) NOT NULL,
	PRIMARY KEY (id)
);

-- FK private_user
ALTER TABLE IF EXISTS public.private_user
	ADD FOREIGN KEY (id)
		REFERENCES public.public_user (id);

-- FK public_user_mission
ALTER TABLE IF EXISTS public.public_user_mission
	ADD FOREIGN KEY (id_public_user)
		REFERENCES public.public_user (id);

ALTER TABLE IF EXISTS public.public_user_mission
	ADD FOREIGN KEY (id_mission)
		REFERENCES public.mission (id);

-- FK public_user_lesson
ALTER TABLE IF EXISTS public.public_user_lesson
	ADD FOREIGN KEY (id_public_user)
		REFERENCES public.public_user (id);

ALTER TABLE IF EXISTS public.public_user_lesson
	ADD FOREIGN KEY (id_lesson)
		REFERENCES public.lesson (id);

-- FK lesson
ALTER TABLE IF EXISTS public.lesson
	ADD FOREIGN KEY (id_course)
		REFERENCES public.course (id);

ALTER TABLE IF EXISTS public.lesson
	ADD FOREIGN KEY (id_next_lesson)
		REFERENCES public.lesson (id);

-- FK exercises
ALTER TABLE IF EXISTS public.exercises
	ADD FOREIGN KEY (id_lesson)
		REFERENCES public.lesson (id);

-- FK option
ALTER TABLE IF EXISTS public.option
	ADD FOREIGN KEY (id_exercise)
		REFERENCES public.exercises (id);

-- FK public_user_option
ALTER TABLE IF EXISTS public.public_user_option
	ADD FOREIGN KEY (id_public_user)
		REFERENCES public.public_user (id);

ALTER TABLE IF EXISTS public.public_user_option
	ADD FOREIGN KEY (id_option)
		REFERENCES public.option (id);