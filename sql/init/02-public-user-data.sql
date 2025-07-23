TRUNCATE TABLE public.public_user RESTART IDENTITY CASCADE;
INSERT INTO public.public_user (nickname, id_profile_image) VALUES ('knowyuser', 1); -- id=1
INSERT INTO public.public_user (nickname, id_profile_image) VALUES ('knowyuser2', 2);
INSERT INTO public.public_user (nickname, id_profile_image) VALUES ('knowyuser3', 2);  -- id=2