INSERT INTO userx(username, create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('admin', NOW(), 'admin', 'admin@planthealth.at', TRUE, 'Administrator', '','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_username, roles) VALUES ('admin', 'ADMIN');
INSERT INTO  userx_user_role (userx_username, roles) VALUES ('admin', 'GARDENER');
INSERT INTO  userx_user_role (userx_username, roles) VALUES ('admin', 'USER');

INSERT INTO userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('gardener1', NOW(),'admin', 'gärtner.gustav@planthealth.at', TRUE, 'Gärtner', 'Gustav','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_username, roles) VALUES ('gardener1', 'GARDENER');
INSERT INTO  userx_user_role (userx_username, roles) VALUES ('gardener1', 'USER');

INSERT INTO userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('user1', NOW(),'admin', 'erik.easy@planthealth.at', TRUE, 'Erik', 'Easy','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_username, roles) VALUES ('user1', 'USER');

INSERT INTO access_point(name, location, description, create_user_username, create_date, transmission_interval_seconds) VALUES ('Access Point 1', 'Büro von Erik und Frederik', 'Bei diesem Access Point handelt es sich um einen Prototypen der uns von der Univerrsität Innsbruck zur Verfügung gestellt wurde', 'admin', NOW(), 60);

INSERT INTO greenhouse(id,name, location, description, accesspoint_uuid, create_user_username, create_date, owner_username) VALUES (27,'Ananas', 'Eriks Ananas', 'Erik kommt aus Südtirol und ist somit Italiener. Er muss seine Leidenschaft für Pizza Hawaii unbedingt geheim halten!',1, 'admin', NOW(), 'user1');

INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (25.0, 30.0, 60, 'TEMPERATURE', 1, 'admin', NOW());
INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (50, 80, 60, 'LIGHT', 1, 'admin', NOW());
INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (50, 80, 60, 'HUMIDITY_AIR', 1, 'admin', NOW());
INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (50, 80, 60, 'HUMIDITY_DIRT', 1, 'admin', NOW());
INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (50, 80, 60, 'AIR_PRESSURE', 1, 'admin', NOW());
INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (50, 80, 60, 'AIR_QUALITY', 1, 'admin', NOW());