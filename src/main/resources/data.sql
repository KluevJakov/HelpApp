INSERT INTO roles VALUES (1, 'Волонтер', 'USER') ON CONFLICT DO NOTHING;
INSERT INTO roles VALUES (2, 'Администратор', 'ADMIN') ON CONFLICT DO NOTHING;

INSERT INTO users (id, "about", "address", "email", "name", "password", "phone")
VALUES(1, 'Инфо о себе', 'г. Саратов', 'user@gmail.com', 'Марина',
'$2a$10$XDNIBDS1AWxANKP9/o9VvOZsBaJ9xloPtU648IK07Hf7oT.hFwv2y', '+79381453123') ON CONFLICT DO NOTHING;
INSERT INTO users (id, "about", "address", "email", "name", "password", "phone")
VALUES(2, 'Инфо о себе', 'г. Вольск', 'admin@gmail.com', 'Лариса',
'$2a$10$XDNIBDS1AWxANKP9/o9VvOZsBaJ9xloPtU648IK07Hf7oT.hFwv2y', '+79381453123') ON CONFLICT DO NOTHING;

INSERT INTO users_roles (user_id, roles_id) VALUES(1, 1) ON CONFLICT DO NOTHING;
INSERT INTO users_roles (user_id, roles_id) VALUES(2, 2) ON CONFLICT DO NOTHING;