-- This Script creates the database and user required for this project.
-- Also make sure that you have installed the
create database db_plantHealth; -- Creates the new database
create user 'springuser'@'%' identified by 'passwd'; -- Creates the user
grant all on db_plantHealth.* to 'springuser'@'%'; -- Gives all privileges to the new user on the newly created database