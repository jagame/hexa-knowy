INSERT INTO public.public_user_mission (id_public_user, id_mission, init_date)
VALUES (1, 1, '2025-06-21');

INSERT INTO public.public_user_mission (id_public_user, id_mission, init_date)
VALUES (1, 2, '2025-06-21');

UPDATE public.public_user_mission
SET current_progress = 4
WHERE id_public_user = 1 AND id_mission = 2;

INSERT INTO public.public_user_mission (id_public_user, id_mission, init_date)
VALUES (2, 1, '2025-06-21');

UPDATE public.public_user_mission
SET current_progress = 1
WHERE id_public_user = 2 AND id_mission = 1;
