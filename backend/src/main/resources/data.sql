INSERT INTO userx(username, create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('admin', NOW(), 'admin', 'admin@planthealth.at', TRUE, 'Administrator', '','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (1, 'ADMIN');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (1, 'GARDENER');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (1, 'USER');

INSERT INTO userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('gardener1', NOW(),'admin', 'gaertner.gustav@planthealth.at', TRUE, 'Gärtner', 'Gustav','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (2, 'GARDENER');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (2, 'USER');

INSERT INTO userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('user1', NOW(),'admin', 'erik.easy@planthealth.at', TRUE, 'Erik', 'Easy','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (3, 'USER');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (3, 'GARDENER');

INSERT INTO userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('user2', NOW(),'admin', 'daniel.danger@planthealth.at', TRUE, 'Daniel', 'Danger','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (4, 'USER');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (4, 'GARDENER');

INSERT INTO userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('user3', NOW(),'admin', 'frederik.fly@planthealth.at', TRUE, 'Frederik', 'Fly','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (5, 'USER');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (5, 'GARDENER');

INSERT INTO userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('user4', NOW(),'admin', 'georg.goenner@planthealth.at', TRUE, 'Georg', 'Gönner','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (6, 'USER');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (6, 'GARDENER');

INSERT INTO userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('user5', NOW(),'admin', 'tobias.turbo@planthealth.at', TRUE, 'Tobias', 'Turbo','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (7, 'USER');

INSERT INTO userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('user6', NOW(),'admin', 'max.mustermann@planthealth.at', TRUE, 'Max', 'Mustermann','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (8, 'USER');

INSERT INTO userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('user7', NOW(),'admin', 'maria.musterfrau@planthealth.at', TRUE, 'Maria', 'Musterfrau','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (9, 'USER');

INSERT INTO userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('user8', NOW(),'admin', 'nerd.neidig@planthealth.at', TRUE, 'Nerd', 'Neidig','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (10, 'USER');


INSERT INTO access_point(name, location, description, create_user_username, create_date, transmission_interval_seconds, published, last_contact) VALUES ('ICT EG', 'Campus Technik', 'Bei diesem Access Point handelt es sich um einen Prototypen der uns von der Univerrsität Innsbruck zur Verfügung gestellt wurde', 'admin', NOW(), 60, TRUE, NOW());
INSERT INTO access_point(name, location, description, create_user_username, create_date, transmission_interval_seconds, published, last_contact) VALUES ('Access Point 2', 'Not Published Accesspoint', 'Dieser AccessPoint wurde noch nicht freigegeben', 'admin', NOW(), 60, FALSE, NOW());
INSERT INTO access_point(name, location, description, create_user_username, create_date, transmission_interval_seconds, published, last_contact) VALUES ('Architektur EG', 'Campus Technik', 'Dieser Access Point befindet sich im Erdgeschoss des Architekturgebäudes und bietet eine zuverlässige Abdeckung in diesem Bereich.', 'admin', NOW(), 30, TRUE, NOW());
INSERT INTO access_point(name, location, description, create_user_username, create_date, transmission_interval_seconds, published, last_contact) VALUES ('Architektur 2. Stock', 'Campus Technik', 'Dieser Access Point befindet sich im zweiten Stock des Architekturgebäudes und ermöglicht eine schnelle Übertragung von Daten in diesem Bereich.', 'admin', NOW(), 60, TRUE, NULL);
INSERT INTO access_point(name, location, description, create_user_username, create_date, transmission_interval_seconds, published, last_contact) VALUES ('Bauingenieur EG', 'Campus Technik', 'Dieser Access Point befindet sich im Erdgeschoss des Bauingenieurgebäudes und deckt einen großen Bereich ab.', 'admin', NOW(), 30, TRUE, NOW());
INSERT INTO access_point(name, location, description, create_user_username, create_date, transmission_interval_seconds, published, last_contact) VALUES ('Bauingenieur 3. Stock', 'Campus Technik', 'Dieser Access Point befindet sich im dritten Stock des Bauingenieurgebäudes und ermöglicht eine schnelle Verbindung in diesem Bereich.', 'admin', NOW(), 60, TRUE, NOW());
INSERT INTO access_point(name, location, description, create_user_username, create_date, transmission_interval_seconds, published, last_contact) VALUES ('Bibliothek EG', 'Campus Technik', 'Dieser Access Point befindet sich im Erdgeschoss der Bibliothek und bietet eine zuverlässige Abdeckung in diesem Bereich.', 'admin', NOW(), 30, TRUE, NOW());
INSERT INTO access_point(name, location, description, create_user_username, create_date, transmission_interval_seconds, published, last_contact) VALUES ('Bibliothek 1. Stock', 'Campus Technik', 'Dieser Access Point befindet sich im ersten Stock der Bibliothek und ermöglicht eine schnelle Übertragung von Daten in diesem Bereich.', 'admin', NOW(), 60, TRUE, NULL);
INSERT INTO access_point(name, location, description, create_user_username, create_date, transmission_interval_seconds, published, last_contact) VALUES ('Mensa', 'Campus Technik', 'Dieser Access Point befindet sich in der Mensa und deckt einen großen Bereich auf dem Campus ab.', 'admin', NOW(), 30, TRUE, NULL);
INSERT INTO access_point(name, location, description, create_user_username, create_date, transmission_interval_seconds, published, last_contact) VALUES ('ICT EG', 'Campus Technik', 'Dieser Access Point befindet sich im Erdgeschoss des ICT-Gebäudes und bietet eine zuverlässige Abdeckung in diesem Bereich.', 'admin', NOW(), 60, TRUE,NULL);
INSERT INTO access_point(name, location, description, create_user_username, create_date, transmission_interval_seconds, published, last_contact) VALUES ('ICT 2. Stock', 'Campus Technik', 'Dieser Access Point befindet sich im zweiten Stock des ICT-Gebäudes und ermöglicht eine schnelle Verbindung in diesem Bereich.', 'admin', NOW(), 30, TRUE, NOW());


--Greenhouses
--Greenhouses for Access Point 1
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (1, 'Basil', 'North wall', 'Small basil plants', 1, 'admin', NOW(), 2, FALSE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (2, 'Mint', 'East wall', 'Small mint plants', 1, 'admin', NOW(), 3, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (3, 'Parsley', 'South wall', 'Small parsley plants', 1, 'admin', NOW(), 4, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (4, 'Chives', 'West wall', 'Small chives plants', 1, 'admin', NOW(), 5, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (5, 'Rosemary', 'Center', 'Small rosemary plants', 1, 'admin', NOW(), 6, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (6, 'Thyme', 'North wall', 'Small thyme plants', 1, 'admin', NOW(), 6, FALSE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (7, 'Cilantro', 'East wall', 'Small cilantro plants', 1, 'admin', NOW(), 2, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (8, 'Oregano', 'South wall', 'Small oregano plants', 1, 'admin', NOW(), 3, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (9, 'Sage', 'West wall', 'Small sage plants', 1, 'admin', NOW(), 4, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (10, 'Dill', 'Center', 'Small dill plants', 1, 'admin', NOW(), 5, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (11, 'Aloe Vera', 'Sunny windowsill', 'Succulent plant with medicinal properties', 1, 'admin', NOW(), 6, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (12, 'Rosemary', 'East window', 'Aromatic herb with needle-like leaves', 1, 'admin', NOW(), 3, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (13, 'Mint', 'Kitchen counter', 'Refreshing herb used in tea and cooking', 1, 'admin', NOW(), 4, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (14, 'Cactus', 'Sunny windowsill', 'Low-maintenance plant that stores water in its stem', 1, 'admin', NOW(), 5, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (15, 'Lavender', 'South-facing balcony', 'Fragrant purple flowers used for essential oils', 1, 'admin', NOW(), 6, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (16, 'Super Awesome Plant', 'ICT Ausstellung', 'Plant to showcase actual measurement data', 1, 'admin', NOW(), 6, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (17, 'Snake Plant', 'Living room corner', 'Tall, upright plant with sword-like leaves', 1, 'admin', NOW(), 2, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (18, 'Peace Lily', 'Shaded corner', 'Leafy plant with white flowers that purifies the air', 1, 'admin', NOW(), 3, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (19, 'Begonia', 'Windowsill', 'Colorful flowers in shades of red, pink, and white', 1, 'admin', NOW(), 4, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (20, 'Fern', 'Bathroom', 'Moisture-loving plant with delicate leaves', 1, 'admin', NOW(), 5, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (27, 'Test', 'TestLocation', 'Test', 1, 'admin', NOW(), 5, TRUE);

--Greenhouses for Access Point 2
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (1, 'Tomatoes', 'South-facing window', 'Vine plants that produce red fruit', 2, 'admin', NOW(), 2, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (2, 'Lettuce', 'Indoor hydroponic system', 'Leafy greens that grow in water', 2, 'admin', NOW(), 3, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (3, 'Peppers', 'West-facing window', 'Spicy fruit that grows on a bush', 2, 'admin', NOW(), 4, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (4, 'Strawberries', 'Hanging planter', 'Small, juicy fruit that grows on a runner', 2, 'admin', NOW(), 5, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (5, 'Basil', 'Kitchen counter', 'Herb with fragrant leaves used in Italian cuisine', 2, 'admin', NOW(), 6, FALSE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (6, 'Chives', 'Indoor herb garden', 'Onion-like herb with long, thin leaves', 2, 'admin', NOW(), 3, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (7, 'Oregano', 'Sunny windowsill', 'Herb with pungent leaves used in Mediterranean cuisine', 2, 'admin', NOW(), 2, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (8, 'Thyme', 'Indoor herb garden', 'Herb with tiny, fragrant leaves used in soups and stews', 2, 'admin', NOW(), 3, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (9, 'Mint', 'Mason jar planter', 'Refreshing herb used in tea and cocktails', 2, 'admin', NOW(), 4, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (10, 'Cilantro', 'Indoor herb garden', 'Pungent herb used in Mexican and Asian cuisine', 2, 'admin', NOW(), 5, TRUE);

--Greenhouses for Access Point 3
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (1, 'Succulents', 'Sunny windowsill', 'Water-storing plants with thick, fleshy leaves', 3, 'admin', NOW(), 2, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (2, 'Spider plant', 'Hanging planter', 'Hardy plant with long, thin leaves that produce "spiderettes"', 3, 'admin', NOW(), 3, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (3, 'Pothos', 'Indoor vine', 'Fast-growing plant with heart-shaped leaves', 3, 'admin', NOW(), 4, FALSE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (4, 'Snake plant', 'Sunny windowsill', 'Tall plant with stiff, upright leaves', 3, 'admin', NOW(), 5, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (5, 'Peace lily', 'Low-light area', 'Flowering plant with glossy, dark green leaves', 3, 'admin', NOW(), 6, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (6, 'Aloe vera', 'Sunny windowsill', 'Succulent plant with medicinal properties', 3, 'admin', NOW(), 3, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (7, 'Rubber plant', 'Low-light area', 'Large plant with glossy, dark green leaves', 3, 'admin', NOW(), 2, FALSE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (8, 'Boston fern', 'Hanging planter', 'Fern with arching fronds that prefer humid environments', 3, 'admin', NOW(), 3, FALSE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (9, 'English ivy', 'Hanging planter', 'Vining plant with small, variegated leaves', 3, 'admin', NOW(), 4, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (10, 'ZZ plant', 'Low-light area', 'Plant with thick, glossy leaves that require infrequent watering', 3, 'admin', NOW(), 5, TRUE);

--Greenhouses for Access Point 4
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (1, 'Tomatoes', 'South wall', 'Vining plant that produces juicy fruit', 4, 'admin', NOW(), 2, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (2, 'Lettuce', 'East wall', 'Leafy green that prefers cooler temperatures', 4, 'admin', NOW(), 3, FALSE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (3, 'Peppers', 'South wall', 'Fruit-bearing plant that can be spicy or sweet', 4, 'admin', NOW(), 4, FALSE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (4, 'Cucumbers', 'East wall', 'Vining plant that produces refreshing fruit', 4, 'admin', NOW(), 5, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (5, 'Kale', 'East wall', 'Leafy green with a slightly bitter flavor', 4, 'admin', NOW(), 6, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (6, 'Radishes', 'North wall', 'Root vegetable with a crisp, peppery taste', 4, 'admin', NOW(), 4, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (7, 'Basil', 'South wall', 'Fragrant herb with many culinary uses', 4, 'admin', NOW(), 2, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (8, 'Spinach', 'East wall', 'Leafy green that is high in iron', 4, 'admin', NOW(), 3, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (9, 'Squash', 'South wall', 'Fruit-bearing plant with a hard outer shell', 4, 'admin', NOW(), 4, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (10, 'Chives', 'North wall', 'Herb with a mild onion-like flavor', 4, 'admin', NOW(), 5, TRUE);

--Greenhouses for Access Point 5
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (1, 'Strawberries', 'West wall', 'Small, sweet fruit that grows on runners', 5, 'admin', NOW(), 2, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (2, 'Basil', 'North wall', 'Aromatic herb with many culinary uses', 5, 'admin', NOW(), 3, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (3, 'Kale', 'East wall', 'Nutritious leafy green that can be used in many dishes', 5, 'admin', NOW(), 4, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (4, 'Chives', 'North wall', 'Mildly flavored herb that adds a hint of onion to dishes', 5, 'admin', NOW(), 5, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (5, 'Lettuce', 'East wall', 'Cool and crisp leafy green that is perfect for salads', 5, 'admin', NOW(), 6, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (6, 'Bok Choy', 'North wall', 'Chinese cabbage that is high in nutrients and antioxidants', 5, 'admin', NOW(), 2, FALSE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (7, 'Peppers', 'South wall', 'Fruit-bearing plant that can be spicy or sweet', 5, 'admin', NOW(), 2, TRUE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (8, 'Mint', 'West wall', 'Refreshing herb that is perfect for drinks and desserts', 5, 'admin', NOW(), 3, FALSE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (9, 'Tomatoes', 'South wall', 'Vining plant that produces juicy fruit', 5, 'admin', NOW(), 4, FALSE);
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (10, 'Cucumbers', 'East wall', 'Refreshing fruit that is perfect for salads and sandwiches', 5, 'admin', NOW(), 5, FALSE);

--Sensors for all greenhouses
INSERT INTO sensor(limit_lower, limit_upper, limit_threshold_minutes, sensor_type, greenhouse_uuid, create_user_username, create_date)
SELECT ROUND(RAND() * 10 + 18, 1), ROUND(RAND() * 10 + 30, 1), 60, 'TEMPERATURE', uuid, 'admin', NOW() FROM greenhouse
UNION ALL
SELECT ROUND(RAND() * 400 + 100, 1), ROUND(RAND() * 1000 + 450, 1), 120, 'LIGHT', uuid, 'admin', NOW() FROM greenhouse
UNION ALL
SELECT ROUND(RAND() * 30 + 40, 1), ROUND(RAND() * 50 + 70, 1), 60, 'HUMIDITY_AIR', uuid, 'admin', NOW() FROM greenhouse
UNION ALL
SELECT ROUND(RAND() * 20 + 30, 1), ROUND(RAND() * 50 + 70, 1), 60, 'HUMIDITY_DIRT', uuid, 'admin', NOW() FROM greenhouse
UNION ALL
SELECT ROUND(RAND() * 80 + 900, 1), ROUND(RAND() * 220 + 1100, 1), 120, 'AIR_PRESSURE', uuid, 'admin', NOW() FROM greenhouse
UNION ALL
SELECT ROUND(RAND() * 20, 1), ROUND(RAND() * 30 + 20, 1), 60, 'AIR_QUALITY', uuid, 'admin', NOW() FROM greenhouse;





-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (980, 0, '2023-05-18 18:55:20.283000', '2023-05-18 20:55:00.000000', 25, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (986, 0, '2023-05-18 18:55:42.063000', '2023-05-18 20:55:00.000000', 25.04, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (992, 0, '2023-05-18 18:56:04.029000', '2023-05-18 20:56:00.000000', 25.08, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (999, 0, '2023-05-18 18:56:26.189000', '2023-05-18 20:56:00.000000', 25.1, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1004, 0, '2023-05-18 18:56:47.952000', '2023-05-18 20:56:00.000000', 25.115000000000002, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1010, 0, '2023-05-18 18:57:09.605000', '2023-05-18 20:57:00.000000', 25.125, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1016, 0, '2023-05-18 18:57:31.504000', '2023-05-18 20:57:00.000000', 25.16, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1022, 0, '2023-05-18 18:57:53.466000', '2023-05-18 20:57:00.000000', 25.185000000000002, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1027, 0, '2023-05-18 18:58:15.241000', '2023-05-18 20:58:00.000000', 25.2, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1034, 0, '2023-05-18 18:58:37.549000', '2023-05-18 20:58:00.000000', 25.21, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1040, 0, '2023-05-18 18:58:59.632000', '2023-05-18 20:58:00.000000', 25.225, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1046, 0, '2023-05-18 18:59:21.586000', '2023-05-18 20:59:00.000000', 25.25, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1052, 0, '2023-05-18 18:59:43.313000', '2023-05-18 20:59:00.000000', 25.265, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1058, 0, '2023-05-18 19:00:05.148000', '2023-05-18 21:00:00.000000', 25.27, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1062, 0, '2023-05-18 19:00:26.812000', '2023-05-18 21:00:00.000000', 25.28, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1070, 0, '2023-05-18 19:00:49.248000', '2023-05-18 21:00:00.000000', 25.29, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1076, 0, '2023-05-18 19:01:11.271000', '2023-05-18 21:01:00.000000', 25.296666666666667, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1081, 0, '2023-05-18 19:01:33.047000', '2023-05-18 21:01:00.000000', 25.3, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1088, 0, '2023-05-18 19:01:55.088000', '2023-05-18 21:01:00.000000', 25.3, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1093, 0, '2023-05-18 19:02:16.497000', '2023-05-18 21:02:00.000000', 25.3, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1100, 0, '2023-05-18 19:02:38.528000', '2023-05-18 21:02:00.000000', 25.3, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1106, 0, '2023-05-18 19:03:00.374000', '2023-05-18 21:02:00.000000', 25.3, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1114, 0, '2023-05-18 19:03:22.592000', '2023-05-18 21:03:00.000000', 25.31, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1118, 0, '2023-05-18 19:03:43.908000', '2023-05-18 21:03:00.000000', 25.31, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1124, 0, '2023-05-18 19:04:06.178000', '2023-05-18 21:04:00.000000', 25.31, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1130, 0, '2023-05-18 19:04:28.077000', '2023-05-18 21:04:00.000000', 25.31, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1136, 0, '2023-05-18 19:04:50.092000', '2023-05-18 21:04:00.000000', 25.32, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1142, 0, '2023-05-18 19:05:12.241000', '2023-05-18 21:05:00.000000', 25.320000000000004, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1149, 0, '2023-05-18 19:05:34.548000', '2023-05-18 21:05:00.000000', 25.32, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1154, 0, '2023-05-18 19:05:56.395000', '2023-05-18 21:05:00.000000', 25.32, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1158, 0, '2023-05-18 19:06:17.948000', '2023-05-18 21:06:00.000000', 25.32, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1166, 0, '2023-05-18 19:06:40.730000', '2023-05-18 21:06:00.000000', 25.32, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1172, 0, '2023-05-18 19:07:02.572000', '2023-05-18 21:07:00.000000', 25.320000000000004, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1178, 0, '2023-05-18 19:07:24.713000', '2023-05-18 21:07:00.000000', 25.32, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1186, 0, '2023-05-18 19:07:47.092000', '2023-05-18 21:07:00.000000', 25.325, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1190, 0, '2023-05-18 19:08:08.653000', '2023-05-18 21:08:00.000000', 25.32, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1196, 0, '2023-05-18 19:08:30.190000', '2023-05-18 21:08:00.000000', 25.33, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1202, 0, '2023-05-18 19:08:51.977000', '2023-05-18 21:08:00.000000', 25.33, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1208, 0, '2023-05-18 19:09:13.991000', '2023-05-18 21:09:00.000000', 25.335, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1215, 0, '2023-05-18 19:09:35.796000', '2023-05-18 21:09:00.000000', 25.34, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1220, 6.814999999999998, '2023-05-18 19:09:57.795000', '2023-05-18 21:09:00.000000', 32.915, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1228, 7.259999999999998, '2023-05-18 19:10:20.300000', '2023-05-18 21:10:00.000000', 33.36, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1232, 7.259999999999998, '2023-05-18 19:10:42.091000', '2023-05-18 21:10:00.000000', 33.36, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1238, 7.259999999999998, '2023-05-18 19:11:04.140000', '2023-05-18 21:11:00.000000', 33.36, 22);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (972, 193.2, '2023-05-18 18:54:58.206000', '2023-05-18 20:54:00.000000', 1, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (979, 193.2, '2023-05-18 18:55:20.085000', '2023-05-18 20:55:00.000000', 1, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (984, 192.7, '2023-05-18 18:55:41.671000', '2023-05-18 20:55:00.000000', 1.5, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (990, 193.2, '2023-05-18 18:56:03.640000', '2023-05-18 20:56:00.000000', 1, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (996, 192.7, '2023-05-18 18:56:25.600000', '2023-05-18 20:56:00.000000', 1.5, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1002, 43.19999999999999, '2023-05-18 18:56:47.556000', '2023-05-18 20:56:00.000000', 516.5, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1008, 156.2, '2023-05-18 18:57:09.215000', '2023-05-18 20:57:00.000000', 38, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1014, 193.2, '2023-05-18 18:57:31.114000', '2023-05-18 20:57:00.000000', 1, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1020, 193.2, '2023-05-18 18:57:53.072000', '2023-05-18 20:57:00.000000', 1, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1026, 193.2, '2023-05-18 18:58:15.009000', '2023-05-18 20:58:00.000000', 1, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1032, 193.2, '2023-05-18 18:58:37.087000', '2023-05-18 20:58:00.000000', 1, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1038, 192.7, '2023-05-18 18:58:59.171000', '2023-05-18 20:58:00.000000', 1.5, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1044, 192.7, '2023-05-18 18:59:21.127000', '2023-05-18 20:59:00.000000', 1.5, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1051, 192.7, '2023-05-18 18:59:43.082000', '2023-05-18 20:59:00.000000', 1.5, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1056, 193.2, '2023-05-18 19:00:04.687000', '2023-05-18 21:00:00.000000', 1, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1063, 192.7, '2023-05-18 19:00:27.040000', '2023-05-18 21:00:00.000000', 1.5, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1068, 192.7, '2023-05-18 19:00:48.787000', '2023-05-18 21:00:00.000000', 1.5, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1074, 192.86666666666665, '2023-05-18 19:01:10.808000', '2023-05-18 21:01:00.000000', 1.3333333333333333, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1083, 192.7, '2023-05-18 19:01:33.436000', '2023-05-18 21:01:00.000000', 1.5, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1086, 192.2, '2023-05-18 19:01:54.699000', '2023-05-18 21:01:00.000000', 2, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1092, 192.7, '2023-05-18 19:02:16.295000', '2023-05-18 21:02:00.000000', 1.5, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1098, 192.7, '2023-05-18 19:02:38.136000', '2023-05-18 21:02:00.000000', 1.5, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1104, 192.7, '2023-05-18 19:02:59.981000', '2023-05-18 21:02:00.000000', 1.5, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1110, 192.2, '2023-05-18 19:03:21.674000', '2023-05-18 21:03:00.000000', 2, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1116, 192.2, '2023-05-18 19:03:43.447000', '2023-05-18 21:03:00.000000', 2, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1122, 192.2, '2023-05-18 19:04:05.716000', '2023-05-18 21:04:00.000000', 2, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1129, 192.7, '2023-05-18 19:04:27.844000', '2023-05-18 21:04:00.000000', 1.5, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1134, 192.2, '2023-05-18 19:04:49.629000', '2023-05-18 21:04:00.000000', 2, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1140, 192.53333333333333, '2023-05-18 19:05:11.772000', '2023-05-18 21:05:00.000000', 1.6666666666666667, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1146, 192.2, '2023-05-18 19:05:33.848000', '2023-05-18 21:05:00.000000', 2, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1152, 192.2, '2023-05-18 19:05:55.929000', '2023-05-18 21:05:00.000000', 2, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1159, 192.2, '2023-05-18 19:06:18.180000', '2023-05-18 21:06:00.000000', 2, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1164, 192.2, '2023-05-18 19:06:40.268000', '2023-05-18 21:06:00.000000', 2, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1170, 192.53333333333333, '2023-05-18 19:07:02.110000', '2023-05-18 21:07:00.000000', 1.6666666666666667, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1176, 192.7, '2023-05-18 19:07:24.246000', '2023-05-18 21:07:00.000000', 1.5, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1184, 192.2, '2023-05-18 19:07:46.688000', '2023-05-18 21:07:00.000000', 2, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1188, 192.2, '2023-05-18 19:08:08.259000', '2023-05-18 21:08:00.000000', 2, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1194, 192.2, '2023-05-18 19:08:29.796000', '2023-05-18 21:08:00.000000', 2, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1200, 192.2, '2023-05-18 19:08:51.578000', '2023-05-18 21:08:00.000000', 2, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1206, 192.7, '2023-05-18 19:09:13.599000', '2023-05-18 21:09:00.000000', 1.5, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1212, 192.2, '2023-05-18 19:09:35.195000', '2023-05-18 21:09:00.000000', 2, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1218, 192.2, '2023-05-18 19:09:57.395000', '2023-05-18 21:09:00.000000', 2, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1224, 192.2, '2023-05-18 19:10:19.477000', '2023-05-18 21:10:00.000000', 2, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1230, 192.2, '2023-05-18 19:10:41.680000', '2023-05-18 21:10:00.000000', 2, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1236, 192.2, '2023-05-18 19:11:03.751000', '2023-05-18 21:11:00.000000', 2, 83);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (975, 4.5, '2023-05-18 18:54:58.819000', '2023-05-18 20:54:00.000000', 100, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (981, 13.119999999999997, '2023-05-18 18:55:20.479000', '2023-05-18 20:55:00.000000', 42.88, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (987, 13.564999999999998, '2023-05-18 18:55:42.254000', '2023-05-18 20:55:00.000000', 42.435, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (993, 14.625, '2023-05-18 18:56:04.226000', '2023-05-18 20:56:00.000000', 41.375, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1000, 15.280000000000001, '2023-05-18 18:56:26.384000', '2023-05-18 20:56:00.000000', 40.72, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1005, 15.620000000000005, '2023-05-18 18:56:48.153000', '2023-05-18 20:56:00.000000', 40.379999999999995, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1011, 15.32, '2023-05-18 18:57:09.802000', '2023-05-18 20:57:00.000000', 40.68, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1018, 5.390000000000001, '2023-05-18 18:57:31.910000', '2023-05-18 20:57:00.000000', 50.61, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1023, 0, '2023-05-18 18:57:53.672000', '2023-05-18 20:57:00.000000', 59.14, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1029, 0, '2023-05-18 18:58:15.698000', '2023-05-18 20:58:00.000000', 60.655, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1035, 0, '2023-05-18 18:58:37.779000', '2023-05-18 20:58:00.000000', 61.945, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1041, 0, '2023-05-18 18:58:59.863000', '2023-05-18 20:58:00.000000', 62.885, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1047, 0, '2023-05-18 18:59:21.816000', '2023-05-18 20:59:00.000000', 63.644999999999996, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1053, 0, '2023-05-18 18:59:43.543000', '2023-05-18 20:59:00.000000', 63.685, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1059, 0, '2023-05-18 19:00:05.378000', '2023-05-18 21:00:00.000000', 63.58, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1065, 0, '2023-05-18 19:00:27.508000', '2023-05-18 21:00:00.000000', 63.394999999999996, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1071, 0, '2023-05-18 19:00:49.477000', '2023-05-18 21:00:00.000000', 63.2, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1077, 0, '2023-05-18 19:01:11.505000', '2023-05-18 21:01:00.000000', 62.85666666666666, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1082, 0, '2023-05-18 19:01:33.242000', '2023-05-18 21:01:00.000000', 62.49, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1089, 0, '2023-05-18 19:01:55.289000', '2023-05-18 21:01:00.000000', 62.045, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1094, 0, '2023-05-18 19:02:16.698000', '2023-05-18 21:02:00.000000', 61.605000000000004, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1101, 0, '2023-05-18 19:02:38.726000', '2023-05-18 21:02:00.000000', 61.254999999999995, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1107, 0, '2023-05-18 19:03:00.577000', '2023-05-18 21:02:00.000000', 60.91, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1115, 0, '2023-05-18 19:03:22.821000', '2023-05-18 21:03:00.000000', 60.345, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1120, 0, '2023-05-18 19:03:44.372000', '2023-05-18 21:03:00.000000', 59.955, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1125, 0, '2023-05-18 19:04:06.411000', '2023-05-18 21:04:00.000000', 59.685, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1131, 0, '2023-05-18 19:04:28.319000', '2023-05-18 21:04:00.000000', 59.42, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1137, 0, '2023-05-18 19:04:50.322000', '2023-05-18 21:04:00.000000', 59.144999999999996, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1143, 0, '2023-05-18 19:05:12.475000', '2023-05-18 21:05:00.000000', 58.85, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1147, 0, '2023-05-18 19:05:34.082000', '2023-05-18 21:05:00.000000', 58.595, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1155, 0, '2023-05-18 19:05:56.627000', '2023-05-18 21:05:00.000000', 58.365, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1160, 0, '2023-05-18 19:06:18.412000', '2023-05-18 21:06:00.000000', 58.114999999999995, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1167, 0, '2023-05-18 19:06:40.960000', '2023-05-18 21:06:00.000000', 57.88, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1173, 0, '2023-05-18 19:07:02.803000', '2023-05-18 21:07:00.000000', 57.60333333333333, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1179, 0, '2023-05-18 19:07:24.945000', '2023-05-18 21:07:00.000000', 57.335, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1183, 0, '2023-05-18 19:07:46.490000', '2023-05-18 21:07:00.000000', 57.129999999999995, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1191, 0, '2023-05-18 19:08:08.850000', '2023-05-18 21:08:00.000000', 56.94, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1198, 0, '2023-05-18 19:08:30.585000', '2023-05-18 21:08:00.000000', 56.785, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1203, 0, '2023-05-18 19:08:52.177000', '2023-05-18 21:08:00.000000', 56.61, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1209, 0, '2023-05-18 19:09:14.187000', '2023-05-18 21:09:00.000000', 56.34, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1216, 0, '2023-05-18 19:09:36.010000', '2023-05-18 21:09:00.000000', 56.19, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1221, 2.0600000000000023, '2023-05-18 19:09:57.993000', '2023-05-18 21:09:00.000000', 97.56, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1227, 4.5, '2023-05-18 19:10:20.100000', '2023-05-18 21:10:00.000000', 100, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1233, 4.5, '2023-05-18 19:10:42.292000', '2023-05-18 21:10:00.000000', 100, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1239, 4.5, '2023-05-18 19:11:04.336000', '2023-05-18 21:11:00.000000', 100, 144);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (973, 0, '2023-05-18 18:54:58.407000', '2023-05-18 20:54:00.000000', 84.08203125, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (978, 0, '2023-05-18 18:55:19.890000', '2023-05-18 20:55:00.000000', 83.88671875, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (985, 0, '2023-05-18 18:55:41.868000', '2023-05-18 20:55:00.000000', 84.130859375, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (991, 0, '2023-05-18 18:56:03.836000', '2023-05-18 20:56:00.000000', 84.130859375, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (997, 0, '2023-05-18 18:56:25.801000', '2023-05-18 20:56:00.000000', 84.1796875, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1003, 0, '2023-05-18 18:56:47.755000', '2023-05-18 20:56:00.000000', 83.7890625, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1009, 0, '2023-05-18 18:57:09.411000', '2023-05-18 20:57:00.000000', 84.033203125, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1015, 0, '2023-05-18 18:57:31.311000', '2023-05-18 20:57:00.000000', 84.033203125, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1021, 0, '2023-05-18 18:57:53.269000', '2023-05-18 20:57:00.000000', 84.326171875, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1028, 0, '2023-05-18 18:58:15.470000', '2023-05-18 20:58:00.000000', 84.130859375, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1033, 0, '2023-05-18 18:58:37.318000', '2023-05-18 20:58:00.000000', 83.984375, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1039, 0, '2023-05-18 18:58:59.401000', '2023-05-18 20:58:00.000000', 84.1796875, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1045, 0, '2023-05-18 18:59:21.356000', '2023-05-18 20:59:00.000000', 84.375, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1050, 0, '2023-05-18 18:59:42.851000', '2023-05-18 20:59:00.000000', 84.375, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1057, 0, '2023-05-18 19:00:04.918000', '2023-05-18 21:00:00.000000', 84.033203125, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1064, 0, '2023-05-18 19:00:27.274000', '2023-05-18 21:00:00.000000', 84.326171875, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1069, 0, '2023-05-18 19:00:49.019000', '2023-05-18 21:00:00.000000', 84.47265625, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1075, 0, '2023-05-18 19:01:11.040000', '2023-05-18 21:01:00.000000', 84.47265625, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1080, 0, '2023-05-18 19:01:32.854000', '2023-05-18 21:01:00.000000', 84.521484375, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1087, 0, '2023-05-18 19:01:54.892000', '2023-05-18 21:01:00.000000', 84.5703125, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1095, 0, '2023-05-18 19:02:16.895000', '2023-05-18 21:02:00.000000', 84.619140625, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1099, 0, '2023-05-18 19:02:38.332000', '2023-05-18 21:02:00.000000', 84.521484375, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1105, 0, '2023-05-18 19:03:00.176000', '2023-05-18 21:02:00.000000', 84.66796875, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1111, 0, '2023-05-18 19:03:21.903000', '2023-05-18 21:03:00.000000', 84.86328125, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1119, 0, '2023-05-18 19:03:44.140000', '2023-05-18 21:03:00.000000', 84.912109375, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1123, 0, '2023-05-18 19:04:05.945000', '2023-05-18 21:04:00.000000', 84.912109375, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1128, 0, '2023-05-18 19:04:27.613000', '2023-05-18 21:04:00.000000', 84.912109375, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1135, 0, '2023-05-18 19:04:49.862000', '2023-05-18 21:04:00.000000', 84.814453125, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1141, 0, '2023-05-18 19:05:12.006000', '2023-05-18 21:05:00.000000', 84.86328125, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1150, 0, '2023-05-18 19:05:34.781000', '2023-05-18 21:05:00.000000', 84.86328125, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1153, 0, '2023-05-18 19:05:56.163000', '2023-05-18 21:05:00.000000', 84.814453125, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1161, 0, '2023-05-18 19:06:18.643000', '2023-05-18 21:06:00.000000', 84.912109375, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1165, 0, '2023-05-18 19:06:40.500000', '2023-05-18 21:06:00.000000', 84.912109375, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1171, 0, '2023-05-18 19:07:02.340000', '2023-05-18 21:07:00.000000', 84.86328125, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1177, 0, '2023-05-18 19:07:24.478000', '2023-05-18 21:07:00.000000', 84.814453125, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1182, 0, '2023-05-18 19:07:46.292000', '2023-05-18 21:07:00.000000', 84.86328125, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1189, 0, '2023-05-18 19:08:08.457000', '2023-05-18 21:08:00.000000', 84.814453125, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1197, 0, '2023-05-18 19:08:30.386000', '2023-05-18 21:08:00.000000', 84.814453125, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1201, 0, '2023-05-18 19:08:51.779000', '2023-05-18 21:08:00.000000', 84.86328125, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1207, 0, '2023-05-18 19:09:13.794000', '2023-05-18 21:09:00.000000', 84.912109375, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1213, 0, '2023-05-18 19:09:35.393000', '2023-05-18 21:09:00.000000', 84.912109375, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1219, 0, '2023-05-18 19:09:57.596000', '2023-05-18 21:09:00.000000', 84.814453125, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1225, 0, '2023-05-18 19:10:19.690000', '2023-05-18 21:10:00.000000', 85.009765625, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1231, 0, '2023-05-18 19:10:41.881000', '2023-05-18 21:10:00.000000', 84.912109375, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1237, 0, '2023-05-18 19:11:03.946000', '2023-05-18 21:11:00.000000', 84.9609375, 205);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (976, 284.80000000000007, '2023-05-18 18:54:59.016000', '2023-05-18 20:54:00.000000', 681.3, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (982, 16.758000000000038, '2023-05-18 18:55:20.675000', '2023-05-18 20:55:00.000000', 949.342, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (988, 16.754999999999995, '2023-05-18 18:55:42.450000', '2023-05-18 20:55:00.000000', 949.345, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (994, 16.8035000000001, '2023-05-18 18:56:04.425000', '2023-05-18 20:56:00.000000', 949.2964999999999, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (998, 16.76049999999998, '2023-05-18 18:56:25.995000', '2023-05-18 20:56:00.000000', 949.3395, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1006, 16.718500000000063, '2023-05-18 18:56:48.348000', '2023-05-18 20:56:00.000000', 949.3815, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1012, 16.684000000000083, '2023-05-18 18:57:09.996000', '2023-05-18 20:57:00.000000', 949.4159999999999, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1017, 16.67999999999995, '2023-05-18 18:57:31.707000', '2023-05-18 20:57:00.000000', 949.4200000000001, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1024, 16.626499999999965, '2023-05-18 18:57:53.867000', '2023-05-18 20:57:00.000000', 949.4735000000001, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1030, 16.654499999999985, '2023-05-18 18:58:15.932000', '2023-05-18 20:58:00.000000', 949.4455, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1036, 16.638500000000022, '2023-05-18 18:58:38.009000', '2023-05-18 20:58:00.000000', 949.4615, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1042, 16.64650000000006, '2023-05-18 18:59:00.093000', '2023-05-18 20:58:00.000000', 949.4535, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1048, 16.620000000000005, '2023-05-18 18:59:22.046000', '2023-05-18 20:59:00.000000', 949.48, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1054, 16.638000000000034, '2023-05-18 18:59:43.774000', '2023-05-18 20:59:00.000000', 949.462, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1060, 16.63900000000001, '2023-05-18 19:00:05.608000', '2023-05-18 21:00:00.000000', 949.461, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1066, 16.649, '2023-05-18 19:00:27.743000', '2023-05-18 21:00:00.000000', 949.451, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1072, 16.668000000000006, '2023-05-18 19:00:49.708000', '2023-05-18 21:00:00.000000', 949.432, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1078, 16.670999999999935, '2023-05-18 19:01:11.735000', '2023-05-18 21:01:00.000000', 949.4290000000001, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1084, 16.66850000000011, '2023-05-18 19:01:33.629000', '2023-05-18 21:01:00.000000', 949.4314999999999, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1090, 16.650999999999954, '2023-05-18 19:01:55.487000', '2023-05-18 21:01:00.000000', 949.4490000000001, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1096, 16.65400000000011, '2023-05-18 19:02:17.092000', '2023-05-18 21:02:00.000000', 949.4459999999999, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1102, 16.64549999999997, '2023-05-18 19:02:38.924000', '2023-05-18 21:02:00.000000', 949.4545, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1108, 16.62600000000009, '2023-05-18 19:03:00.775000', '2023-05-18 21:02:00.000000', 949.4739999999999, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1112, 16.63466666666659, '2023-05-18 19:03:22.132000', '2023-05-18 21:03:00.000000', 949.4653333333334, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1117, 16.631500000000074, '2023-05-18 19:03:43.677000', '2023-05-18 21:03:00.000000', 949.4685, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1126, 16.620000000000005, '2023-05-18 19:04:06.642000', '2023-05-18 21:04:00.000000', 949.48, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1132, 16.591999999999985, '2023-05-18 19:04:28.551000', '2023-05-18 21:04:00.000000', 949.508, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1138, 16.619500000000016, '2023-05-18 19:04:50.552000', '2023-05-18 21:04:00.000000', 949.4805, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1144, 16.627000000000066, '2023-05-18 19:05:12.707000', '2023-05-18 21:05:00.000000', 949.473, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1148, 16.63900000000001, '2023-05-18 19:05:34.315000', '2023-05-18 21:05:00.000000', 949.461, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1156, 16.689500000000066, '2023-05-18 19:05:56.859000', '2023-05-18 21:05:00.000000', 949.4105, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1162, 16.649, '2023-05-18 19:06:18.874000', '2023-05-18 21:06:00.000000', 949.451, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1168, 16.631500000000074, '2023-05-18 19:06:41.191000', '2023-05-18 21:06:00.000000', 949.4685, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1174, 16.59966666666662, '2023-05-18 19:07:03.033000', '2023-05-18 21:07:00.000000', 949.5003333333334, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1180, 16.592500000000086, '2023-05-18 19:07:25.178000', '2023-05-18 21:07:00.000000', 949.5074999999999, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1185, 16.611500000000092, '2023-05-18 19:07:46.888000', '2023-05-18 21:07:00.000000', 949.4884999999999, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1192, 16.616499999999974, '2023-05-18 19:08:09.051000', '2023-05-18 21:08:00.000000', 949.4835, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1195, 16.618499999999926, '2023-05-18 19:08:29.993000', '2023-05-18 21:08:00.000000', 949.4815000000001, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1204, 16.574999999999932, '2023-05-18 19:08:52.376000', '2023-05-18 21:08:00.000000', 949.5250000000001, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1210, 16.58050000000003, '2023-05-18 19:09:14.383000', '2023-05-18 21:09:00.000000', 949.5195, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1214, 16.531000000000063, '2023-05-18 19:09:35.594000', '2023-05-18 21:09:00.000000', 949.569, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1222, 269.89650000000006, '2023-05-18 19:09:58.190000', '2023-05-18 21:09:00.000000', 696.2035, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1226, 284.80000000000007, '2023-05-18 19:10:19.899000', '2023-05-18 21:10:00.000000', 681.3, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1234, 284.80000000000007, '2023-05-18 19:10:42.492000', '2023-05-18 21:10:00.000000', 681.3, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1240, 284.80000000000007, '2023-05-18 19:11:04.535000', '2023-05-18 21:11:00.000000', 681.3, 266);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (977, 67.7, '2023-05-18 18:54:59.216000', '2023-05-18 20:54:00.000000', 100, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (983, 63.9851, '2023-05-18 18:55:20.868000', '2023-05-18 20:55:00.000000', 96.2851, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (989, 62.27210000000001, '2023-05-18 18:55:42.649000', '2023-05-18 20:55:00.000000', 94.5721, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (995, 60.19865, '2023-05-18 18:56:04.622000', '2023-05-18 20:56:00.000000', 92.49865, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1001, 58.46034999999999, '2023-05-18 18:56:26.580000', '2023-05-18 20:56:00.000000', 90.76034999999999, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1007, 57.0373, '2023-05-18 18:56:48.543000', '2023-05-18 20:56:00.000000', 89.3373, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1013, 56.36855, '2023-05-18 18:57:10.194000', '2023-05-18 20:57:00.000000', 88.66855, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1019, 59.467200000000005, '2023-05-18 18:57:32.116000', '2023-05-18 20:57:00.000000', 91.7672, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1025, 60.54585, '2023-05-18 18:57:54.060000', '2023-05-18 20:57:00.000000', 92.84585, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1031, 60.449650000000005, '2023-05-18 18:58:16.162000', '2023-05-18 20:58:00.000000', 92.74965, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1037, 60.33800000000001, '2023-05-18 18:58:38.239000', '2023-05-18 20:58:00.000000', 92.638, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1043, 60.23315000000001, '2023-05-18 18:59:00.322000', '2023-05-18 20:58:00.000000', 92.53315, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1049, 60.0132, '2023-05-18 18:59:22.275000', '2023-05-18 20:59:00.000000', 92.3132, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1055, 59.84349999999999, '2023-05-18 18:59:44.006000', '2023-05-18 20:59:00.000000', 92.14349999999999, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1061, 59.655649999999994, '2023-05-18 19:00:05.837000', '2023-05-18 21:00:00.000000', 91.95564999999999, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1067, 59.49974999999999, '2023-05-18 19:00:27.974000', '2023-05-18 21:00:00.000000', 91.79974999999999, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1073, 59.34310000000001, '2023-05-18 19:00:49.937000', '2023-05-18 21:00:00.000000', 91.6431, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1079, 59.14633333333332, '2023-05-18 19:01:11.966000', '2023-05-18 21:01:00.000000', 91.44633333333331, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1085, 58.9555, '2023-05-18 19:01:33.826000', '2023-05-18 21:01:00.000000', 91.2555, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1091, 58.76455, '2023-05-18 19:01:55.685000', '2023-05-18 21:01:00.000000', 91.06455, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1097, 58.61540000000001, '2023-05-18 19:02:17.289000', '2023-05-18 21:02:00.000000', 90.9154, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1103, 58.398700000000005, '2023-05-18 19:02:39.126000', '2023-05-18 21:02:00.000000', 90.6987, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1109, 58.179199999999994, '2023-05-18 19:03:00.973000', '2023-05-18 21:02:00.000000', 90.47919999999999, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1113, 58.018133333333324, '2023-05-18 19:03:22.361000', '2023-05-18 21:03:00.000000', 90.31813333333332, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1121, 57.8515, '2023-05-18 19:03:44.604000', '2023-05-18 21:03:00.000000', 90.1515, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1127, 57.73975, '2023-05-18 19:04:06.874000', '2023-05-18 21:04:00.000000', 90.03975, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1133, 57.63495, '2023-05-18 19:04:28.784000', '2023-05-18 21:04:00.000000', 89.93495, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1139, 57.5309, '2023-05-18 19:04:50.781000', '2023-05-18 21:04:00.000000', 89.8309, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1145, 57.39623333333334, '2023-05-18 19:05:12.939000', '2023-05-18 21:05:00.000000', 89.69623333333334, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1151, 57.27815, '2023-05-18 19:05:35.015000', '2023-05-18 21:05:00.000000', 89.57815, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1157, 57.1922, '2023-05-18 19:05:57.090000', '2023-05-18 21:05:00.000000', 89.4922, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1163, 57.090500000000006, '2023-05-18 19:06:19.104000', '2023-05-18 21:06:00.000000', 89.3905, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1169, 57.00085, '2023-05-18 19:06:41.426000', '2023-05-18 21:06:00.000000', 89.30085, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1175, 56.87883333333333, '2023-05-18 19:07:03.264000', '2023-05-18 21:07:00.000000', 89.17883333333333, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1181, 56.762550000000005, '2023-05-18 19:07:25.412000', '2023-05-18 21:07:00.000000', 89.06255, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1187, 56.68130000000001, '2023-05-18 19:07:47.291000', '2023-05-18 21:07:00.000000', 88.9813, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1193, 56.60610000000001, '2023-05-18 19:08:09.248000', '2023-05-18 21:08:00.000000', 88.90610000000001, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1199, 56.556500000000014, '2023-05-18 19:08:30.783000', '2023-05-18 21:08:00.000000', 88.85650000000001, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1205, 56.50760000000001, '2023-05-18 19:08:52.574000', '2023-05-18 21:08:00.000000', 88.80760000000001, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1211, 56.40305000000001, '2023-05-18 19:09:14.582000', '2023-05-18 21:09:00.000000', 88.70305, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1217, 56.332750000000004, '2023-05-18 19:09:36.225000', '2023-05-18 21:09:00.000000', 88.63275, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1223, 67.06765, '2023-05-18 19:09:58.391000', '2023-05-18 21:09:00.000000', 99.36765, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1229, 67.7, '2023-05-18 19:10:20.499000', '2023-05-18 21:10:00.000000', 100, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1235, 67.7, '2023-05-18 19:10:42.698000', '2023-05-18 21:10:00.000000', 100, 327);
-- INSERT INTO db_planthealth.measurement (id, limit_exceeded_by, create_date, measurement_date, value, sensor_id) VALUES (1241, 67.7, '2023-05-18 19:11:04.737000', '2023-05-18 21:11:00.000000', 100, 327);
