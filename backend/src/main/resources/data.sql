INSERT INTO Userx(username, create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('admin', NOW(), 'admin', 'admin@planthealth.at', TRUE, 'Administrator', '','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO USERX_USER_ROLE (USERX_USERNAME, ROLES) VALUES ('admin', 'ADMIN');
INSERT INTO USERX_USER_ROLE (USERX_USERNAME, ROLES) VALUES ('admin', 'GARDENER');
INSERT INTO USERX_USER_ROLE (USERX_USERNAME, ROLES) VALUES ('admin', 'USER');

INSERT INTO Userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('gardener1', NOW(),'admin', 'gärtner.gustav@planthealth.at', TRUE, 'Gärtner', 'Gustav','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO USERX_USER_ROLE (USERX_USERNAME, ROLES) VALUES ('gardener1', 'GARDENER');
INSERT INTO USERX_USER_ROLE (USERX_USERNAME, ROLES) VALUES ('gardener1', 'USER');

INSERT INTO Userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('user1', NOW(),'admin', 'erik.easy@planthealth.at', TRUE, 'Erik', 'Easy','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO USERX_USER_ROLE (USERX_USERNAME, ROLES) VALUES ('user1', 'USER');

INSERT INTO ACCESS_POINT(name, location, description, create_user_username, create_date) VALUES ('Access Point 1', 'Büro von Erik und Frederik', 'Bei diesem Access Point handelt es sich um einen Prototypen der uns von der Univerrsität Innsbruck zur Verfügung gestellt wurde', 'admin', NOW());

INSERT INTO GREENHOUSE(name, location, description, create_user_username, create_date, owner_username) VALUES ('Ananas', 'Eriks Ananas', 'Erik kommt aus Südtirol und ist somit Italiener. Er muss seine Leidenschaft für Pizza Hawaii unbedingt geheim halten!', 'admin', NOW(), 'user1');

INSERT INTO SENSOR(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid) VALUES (25.0, 30.0, 60, 'TEMPERATURE', 1);
INSERT INTO SENSOR(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid) VALUES (50, 80, 60, 'LIGHT', 1);
INSERT INTO SENSOR(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid) VALUES (50, 80, 60, 'HUMIDITY_AIR', 1);
INSERT INTO SENSOR(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid) VALUES (50, 80, 60, 'HUMIDITY_DIRT', 1);
INSERT INTO SENSOR(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid) VALUES (50, 80, 60, 'AIR_PRESSURE', 1);
INSERT INTO SENSOR(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid) VALUES (50, 80, 60, 'AIR_QUALITY', 1);

