INSERT INTO userx(username, create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('admin', NOW(), 'admin', 'admin@planthealth.at', TRUE, 'Administrator', '','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (1, 'ADMIN');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (1, 'GARDENER');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (1, 'USER');

INSERT INTO userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('gardener1', NOW(),'admin', 'gärtner.gustav@planthealth.at', TRUE, 'Gärtner', 'Gustav','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (2, 'GARDENER');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (2, 'USER');

INSERT INTO userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('user1', NOW(),'admin', 'erik.easy@planthealth.at', TRUE, 'Erik', 'Easy','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (3, 'USER');

INSERT INTO userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('user2', NOW(),'admin', 'daniel.danger@planthealth.at', TRUE, 'Daniel', 'Danger','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (4, 'USER');

INSERT INTO userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('user3', NOW(),'admin', 'frederik.fly@planthealth.at', TRUE, 'Frederik', 'Fly','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (5, 'USER');

INSERT INTO userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('user4', NOW(),'admin', 'georg.goenner@planthealth.at', TRUE, 'Georg', 'Gönner','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (6, 'USER');

INSERT INTO userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('user5', NOW(),'admin', 'tobias.turbo@planthealth.at', TRUE, 'Tobias', 'Turbo','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (7, 'USER');

INSERT INTO userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('user6', NOW(),'admin', 'max.mustermann@planthealth.at', TRUE, 'Max', 'Mustermann','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (8, 'USER');

INSERT INTO userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('user7', NOW(),'admin', 'maria.musterfrau@planthealth.at', TRUE, 'Maria', 'Musterfrau','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (9, 'USER');

INSERT INTO userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('user8', NOW(),'admin', 'nerd.neidig@planthealth.at', TRUE, 'Nerd', 'Neidig','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (10, 'USER');


INSERT INTO access_point(name, location, description, create_user_username, create_date, transmission_interval_seconds, published) VALUES ('Access Point 1', 'Büro von Erik und Frederik', 'Bei diesem Access Point handelt es sich um einen Prototypen der uns von der Univerrsität Innsbruck zur Verfügung gestellt wurde', 'admin', NOW(), 60, TRUE);
INSERT INTO access_point(name, location, description, create_user_username, create_date, transmission_interval_seconds, published) VALUES ('Access Point 2', 'Not Published Accesspoint', 'Dieser AccessPoint wurde noch nicht freigegeben', 'admin', NOW(), 60, FALSE);

INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (1,'Schnittlauch', '', 'Schnittlauch easy S-Tier',1, 'admin', NOW(), 3, FALSE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (2,'Kresse', '', 'Jeder mag Kresse',2, 'admin', NOW(), 3, FALSE);
INSERT INTO greenhouse(id_in_cluster,name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (27,'Ananas', 'Eriks Ananas', 'Erik kommt aus Südtirol und ist somit Italiener. Er muss seine Leidenschaft für Pizza Hawaii unbedingt geheim halten!',1, 'admin', NOW(), 3, TRUE);

INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (25.0, 30.0, 60, 'TEMPERATURE', 1, 'admin', NOW());
INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (50, 80, 60, 'LIGHT', 1, 'admin', NOW());
INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (50, 80, 60, 'HUMIDITY_AIR', 1, 'admin', NOW());
INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (50, 80, 60, 'HUMIDITY_DIRT', 1, 'admin', NOW());
INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (50, 80, 60, 'AIR_PRESSURE', 1, 'admin', NOW());
INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (50, 80, 60, 'AIR_QUALITY', 1, 'admin', NOW());


INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (25.0, 30.0, 60, 'TEMPERATURE', 2, 'admin', NOW());
INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (50, 80, 60, 'LIGHT', 2, 'admin', NOW());
INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (50, 80, 60, 'HUMIDITY_AIR', 2, 'admin', NOW());
INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (50, 80, 60, 'HUMIDITY_DIRT', 2, 'admin', NOW());
INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (50, 80, 60, 'AIR_PRESSURE', 2, 'admin', NOW());
INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (50, 80, 60, 'AIR_QUALITY', 2, 'admin', NOW());

INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (25.0, 30.0, 60, 'TEMPERATURE', 3, 'admin', NOW());
INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (50, 80, 60, 'LIGHT', 3, 'admin', NOW());
INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (50, 80, 60, 'HUMIDITY_AIR', 3, 'admin', NOW());
INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (50, 80, 60, 'HUMIDITY_DIRT', 3, 'admin', NOW());
INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (50, 80, 60, 'AIR_PRESSURE', 3, 'admin', NOW());
INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date) VALUES (50, 80, 60, 'AIR_QUALITY', 3, 'admin', NOW());