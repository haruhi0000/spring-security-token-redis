INSERT INTO `role` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
INSERT INTO `account` VALUES (1, 'admin', '$shiro1$SHA-256$500000$xvwvQI2U/69O1AtcRf9Luw==$julRqXcbXLazxikdc3dOBYqMb6VLqA4FQn3qCdOhgBA=', 1);
INSERT INTO `account` VALUES (2, 'lql', '$2a$10$1eNCr2bECTW2oqwAW7g84.T3lgtXAFyKDi1hL5STOz0CHezs50lNK', 1);
INSERT INTO `account_role` VALUES (1, 1, 1);
INSERT INTO `account_role` VALUES (2, 1, 2);