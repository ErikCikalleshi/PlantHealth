-- This Script creates the database user for this application.
-- If you want to use a different user please change it in the application.properties file under /backend/src/main/resources/
create user 'springuser'@'%' identified by 'passwd'; -- Creates the user
grant all on db_plantHealth.* to 'springuser'@'%'; -- Gives all privileges to the new user on the newly created database