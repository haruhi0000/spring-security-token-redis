INSERT INTO role (id, name) VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');

INSERT INTO account (id, name, password) VALUES
(1, 'admin', '$2a$10$1eNCr2bECTW2oqwAW7g84.T3lgtXAFyKDi1hL5STOz0CHezs50lNK'),
(2, 'test', '$2a$10$1eNCr2bECTW2oqwAW7g84.T3lgtXAFyKDi1hL5STOz0CHezs50lNK');

INSERT INTO account_role (id, account_id, role_id) VALUES (1, 1, 1), (2, 2, 2);

INSERT INTO `team` (id, name) VALUES (1, 'A'), (2, 'B');

INSERT INTO account_team (id, account_id, team_id) VALUES (1, 1, 1), (2, 2, 2);