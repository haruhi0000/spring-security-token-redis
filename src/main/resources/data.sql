INSERT INTO `role` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
INSERT INTO `account` VALUES (1, 'admin', '$2a$10$1eNCr2bECTW2oqwAW7g84.T3lgtXAFyKDi1hL5STOz0CHezs50lNK', 1);
INSERT INTO `account` VALUES (2, 'lql', '$2a$10$1eNCr2bECTW2oqwAW7g84.T3lgtXAFyKDi1hL5STOz0CHezs50lNK', 1);
INSERT INTO `account_role` VALUES (1, 1, 1);
INSERT INTO `account_role` VALUES (2, 1, 2);