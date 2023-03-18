-- This script clears all tables and fills them again
DELETE FROM userx_userx_role;
DELETE FROM Userx;
INSERT INTO Userx(username, create_date, create_user_username, email, enabled, first_name, last_name, password, phone)
VALUES ('admin', NOW(),'admin', 'admin@planthealth.at', TRUE, 'Administrator', '','$2a$12$WTHPMXqdUKBZ7b6p7LZZ0.a2JknpnJbAkeGvBJ62.5T1czPebq6cW','')

INSERT INTO USERX_USERX_ROLE (USERX_USERNAME, ROLES)
VALUES ('admin', 'ADMIN');
INSERT INTO USERX_USERX_ROLE (USERX_USERNAME, ROLES)
VALUES ('admin', 'GARDENER');
INSERT INTO USERX_USERX_ROLE (USERX_USERNAME, ROLES)
VALUES ('admin', 'USER');