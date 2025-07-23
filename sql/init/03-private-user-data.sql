TRUNCATE TABLE public.private_user RESTART IDENTITY CASCADE;
INSERT INTO public.private_user (id, email, password)
VALUES (1, 'knowy@kn.com', '$2a$12$JN671zrBNZeCUIajo7G/Lullr94nF4uVKY3gUVGM/kVgb4ovznxXS');
INSERT INTO public.private_user (id, email, password)
VALUES (2, 'knowyuser2@kn.com', '$2a$12$JN671zrBNZeCUIajo7G/Lullr94nF4uVKY3gUVGM/kVgb4ovznxXS');
INSERT INTO public.private_user (id, email, password)
VALUES (3, 'knowyuser3@kn.com', '$2a$12$JN671zrBNZeCUIajo7G/Lullr94nF4uVKY3gUVGM/kVgb4ovznxXS');