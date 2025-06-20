BEGIN;


CREATE TABLE IF NOT EXISTS public.private_user
(
    id serial NOT NULL,
    id_public_user integer NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (id_public_user)
);

CREATE TABLE IF NOT EXISTS public.public_user
(
    id serial NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.public_user_mission
(
    id_public_user integer NOT NULL,
    id_mission integer NOT NULL,
    PRIMARY KEY (id_public_user, id_mission)
);

CREATE TABLE IF NOT EXISTS public.mission
(
    id serial NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.public_user_lesson
(
    id_public_user integer NOT NULL,
    id_lesson integer NOT NULL,
    PRIMARY KEY (id_public_user, id_course)
);

CREATE TABLE IF NOT EXISTS public.course
(
    id serial NOT NULL,
    name character varying(50),
    description character varying(250),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.lesson
(
    id serial NOT NULL,
    id_course integer NOT NULL,
    id_next_lesson integer,
    PRIMARY KEY (id),
    UNIQUE (id_next_lesson)
);

CREATE TABLE IF NOT EXISTS public.exercises
(
    id serial NOT NULL,
    id_lesson integer NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.option
(
    id serial NOT NULL,
    id_excercise integer NOT NULL,
    PRIMARY KEY (id, id_excercise)
);

CREATE TABLE IF NOT EXISTS public.public_user_option
(
    id_public_user integer NOT NULL,
    id_option integer NOT NULL,
    PRIMARY KEY (id_public_user, id_option)
);

ALTER TABLE IF EXISTS public.private_user
    ADD FOREIGN KEY (id_public_user)
    REFERENCES public.public_user (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.public_user
    ADD FOREIGN KEY (id)
    REFERENCES public.public_user_mission (id_public_user) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.public_user_mission
    ADD FOREIGN KEY (id_public_user)
    REFERENCES public.public_user (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.public_user_mission
    ADD FOREIGN KEY (id_mission)
    REFERENCES public.mission (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.public_user_lesson
    ADD FOREIGN KEY (id_public_user)
    REFERENCES public.public_user (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.public_user_lesson
    ADD FOREIGN KEY (id_lesson)
    REFERENCES public.lesson (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.lesson
    ADD FOREIGN KEY (id_course)
    REFERENCES public.course (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.lesson
    ADD FOREIGN KEY (id_next_lesson)
    REFERENCES public.lesson (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.exercises
    ADD FOREIGN KEY (id_lesson)
    REFERENCES public.lesson (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.option
    ADD FOREIGN KEY (id_excercise)
    REFERENCES public.exercises (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.public_user_option
    ADD FOREIGN KEY (id_public_user)
    REFERENCES public.public_user (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.public_user_option
    ADD FOREIGN KEY (id_option)
    REFERENCES public.option (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;

END;