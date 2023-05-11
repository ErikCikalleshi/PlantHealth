INSERT INTO userx(username, create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('admin', NOW(), 'admin', 'admin@planthealth.at', TRUE, 'Administrator', '','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (1, 'ADMIN');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (1, 'GARDENER');
INSERT INTO  userx_user_role (userx_id, roles) VALUES (1, 'USER');

INSERT INTO userx(username,create_date, create_user_username, email, enabled, first_name, last_name, password, phone) VALUES ('gardener1', NOW(),'admin', 'gärtner.gustav@planthealth.at', TRUE, 'Gärtner', 'Gustav','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','');
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


INSERT INTO access_point(name, location, description, create_user_username, create_date, transmission_interval_seconds, published, last_contact) VALUES ('Bibliothek EG', 'Campus Technik', 'Bei diesem Access Point handelt es sich um einen Prototypen der uns von der Univerrsität Innsbruck zur Verfügung gestellt wurde', 'admin', NOW(), 60, TRUE, NOW());
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
INSERT INTO greenhouse(id_in_cluster, name, location, description, accesspoint_uuid, create_user_username, create_date, owner_id, published) VALUES (16, 'Spider Plant', 'Hanging basket', 'Easy to care for indoor plant with long leaves', 1, 'admin', NOW(), 6, TRUE);
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
SELECT ROUND(RAND() * 10 + 20, 1), ROUND(RAND() * 10 + 25, 1), 60, 'TEMPERATURE', uuid, 'admin', NOW() FROM greenhouse
UNION ALL
SELECT ROUND(RAND() * 400 + 100, 1), ROUND(RAND() * 1000 + 400, 1), 120, 'LIGHT', uuid, 'admin', NOW() FROM greenhouse
UNION ALL
SELECT ROUND(RAND() * 30 + 40, 1), ROUND(RAND() * 50 + 70, 1), 60, 'HUMIDITY_AIR', uuid, 'admin', NOW() FROM greenhouse
UNION ALL
SELECT ROUND(RAND() * 20 + 30, 1), ROUND(RAND() * 50 + 70, 1), 60, 'HUMIDITY_DIRT', uuid, 'admin', NOW() FROM greenhouse
UNION ALL
SELECT ROUND(RAND() * 100 + 900, 1), ROUND(RAND() * 200 + 900, 1), 120, 'AIR_PRESSURE', uuid, 'admin', NOW() FROM greenhouse
UNION ALL
SELECT ROUND(RAND() * 20, 1), ROUND(RAND() * 30 + 20, 1), 60, 'AIR_QUALITY', uuid, 'admin', NOW() FROM greenhouse